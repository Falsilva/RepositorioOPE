package br.com.mdjpapeis.model;

import java.io.IOException;
import java.util.List;

import javax.persistence.PersistenceException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.mdjpapeis.dao.ClienteDAO;
import br.com.mdjpapeis.entity.Cliente;
import br.com.mdjpapeis.entity.Fornecedor;

@WebServlet(urlPatterns = {"/listarClientes", "/pesquisarCliente", "/cadastrarCliente", "/atualizarCliente", "/excluirCliente", "/separaEnderecoCliente"})
public class RegraClientes extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) 
			throws ServletException, IOException {
		
		System.out.println("RegraClientes - ACTION: " + req.getParameter("action"));
		
		long codigo = 0;
		String empresa = null;
		String contato = null;	// Campo obrigatório, não pode ser nulo no banco de dados
		String telefone = null;
		String email = null;
		String endereco = "";
		String cnpj = null;
		String inscEstadual = null;
		
		String cep = null;
		String logradouro = null;
		String numero = null;
		String complemento = null;
		String bairro = null;
		String cidade = null;
		String estado = null;
		String[] paramEndereco = new String[7];
		
		Cliente cliente = new Cliente();
		Cliente cli = null;
		List<Cliente> clientes = null;
		Cliente verificaCNPJ = null;
		Cliente verificaInscEstadual = null;
		
		String[][] parametros = null;
		String tarefa = req.getParameter("tarefa");
		boolean codigoInvalido = false;
		RequestDispatcher dispatcher = null;
		
		switch(req.getParameter("action")){
			case("listarClientes"):
				System.out.println("RegraClientes, LISTANDO CLIENTES...");
				clientes = new ClienteDAO().listar();
				
				if(tarefa != null && tarefa.equals("pedidoVenda")){						
					
					// Montando o JSON
					String dataListaClientes = "";
					String arrayClientes = "";
					
					if(clientes != null){
						int count = 1;
						for(Cliente c : clientes){
							if(count == 1){
								arrayClientes += "{"			
										+ "\"codigo\":\"" + c.getCodigo() + "\","
										+ "\"empresa\":\"" + c.getEmpresa() + "\","
										+ "\"contato\":\"" + c.getContato() + "\""
										+ "}";
								count = 2;
							}else{
								arrayClientes += ", " + "{"			
										+ "\"codigo\":\"" + c.getCodigo() + "\","
										+ "\"empresa\":\"" + c.getEmpresa() + "\","
										+ "\"contato\":\"" + c.getContato() + "\""
										+ "}";
							}
						}					
						dataListaClientes = "{\"dataListaClientes\":[" + arrayClientes + "]}";
					}else{
						dataListaClientes = "{\"dataListaClientes\":\"null\"}";
					}
					resp.setContentType("application/json");				
					resp.setCharacterEncoding("UTF-8");
					resp.getWriter().write(dataListaClientes);
				}else{
					if(clientes != null){
						System.out.println("RegraClientes, CLIENTES LISTADOS, FORMATANDO O ENDERECO PARA EXIBICAO...");
						formataEnderecoParaExibicao(clientes);
						System.out.println("RegraClientes, CLIENTES LISTADOS,  ENDERECO FORMATADO.");
						req.setAttribute("clientes", clientes);
					}else{
						System.out.println("RegraClientes, NAO HA CLIENTES CADASTRADOS.");
						req.setAttribute("mensagem", "Não há clientes cadastrados");
					}
					
					dispatcher = req.getRequestDispatcher("controller?action=clientes");
					dispatcher.forward(req, resp);
				}
				break;
			case("pesquisarCliente"):
				System.out.println("RegraClientes, PESQUISANDO CLIENTE(S)...");
				codigo = 0;
				empresa = null;
				contato = null;
				cnpj = null;
				
				cliente = new Cliente();
				cli = null;
				clientes = null;
				
				String pesquisa = null;
				
				if(req.getParameter("cnpj") != null){
					System.out.println("RegraClientes, PESQUISANDO POR CNPJ...");
					cnpj = req.getParameter("cnpj");
					cliente.setCnpj(cnpj);
					cli = new ClienteDAO().buscaClientePorCNPJ(cliente);										
					pesquisa = "cnpj";				
				}else{
					if(req.getParameter("codigo") != null){
						System.out.println("RegraClientes, PESQUISANDO POR CODIGO...");
						if(!req.getParameter("codigo").isEmpty()){
							try{
								codigo = Long.parseLong(req.getParameter("codigo"));				
								cliente.setCodigo(codigo);
								cli = new ClienteDAO().buscaClientePorCodigo(cliente);
							}catch(NumberFormatException ex){
								codigoInvalido = true;
								ex.printStackTrace();
							}
						}
						pesquisa = "codigo";						
					}else{
						if(req.getParameter("empresa") != null){
							System.out.println("RegraClientes, PESQUISANDO POR EMPRESA...");
							empresa = req.getParameter("empresa");
							cliente.setEmpresa(empresa);
							
							if(empresa.isEmpty()){
								System.out.println("RegraClientes, PESQUISANDO POR EMPRESA, EMPRESA NAO INFORMADA, LISTANDO EMPRESAS...");
								clientes = new ClienteDAO().listar();								
							}else{
								System.out.println("RegraClientes, PESQUISANDO POR EMPRESA, EMPRESA INFORMADA...");
								clientes = new ClienteDAO().buscaClientePorEmpresa(cliente);								
							}							
							pesquisa = "empresa";
						}
					}
				}
				
				switch(pesquisa){				
					case "codigo":
						if(cli != null){
							System.out.println("RegraClientes, PESQUISADO POR CODIGO, CLIENTE ENCONTRADO, FORMATANDO ENDERECO PARA EXIBICAO...");
							formataEnderecoParaFormulario(cli, req, resp);
							System.out.println("RegraClientes, PESQUISADO POR CODIGO, CLIENTE ENCONTRADO, ENDERECO FORMATADO.");
							req.setAttribute("cli", cli);							
						}else{							
							if(req.getParameter("codigo").isEmpty()){
								System.out.println("RegraClientes, PESQUISADO POR CODIGO, CODIGO NAO INFORMADO...");
								req.setAttribute("mensagem", "Informe o código");
							}else{
								if(codigoInvalido){
									System.out.println("RegraClientes, PESQUISADO POR CODIGO, CODIGO INVALIDO...");
									req.setAttribute("mensagem", "Código inválido");
								}else{									
									req.setAttribute("mensagem", "Cliente não encontrado");									
								}
								System.out.println("RegraClientes, PESQUISADO POR CODIGO, CLIENTE NAO ENCONTRADO.");
							}
						}
						dispatcher = req.getRequestDispatcher("controller?action=clientes");						
						break;						
					case "cnpj":				
						if(cli != null){
							System.out.println("RegraClientes, PESQUISADO POR CNPJ, CLIENTE ENCONTRADO, FORMATANDO ENDERECO PARA EXIBICAO...");
							formataEnderecoParaFormulario(cli, req, resp);
							System.out.println("RegraClientes, PESQUISADO POR CNPJ, CLIENTE ENCONTRADO, ENDERECO FORMATADO.");
							req.setAttribute("cli", cli);							
						}else{
							System.out.println("RegraClientes, PESQUISADO POR CNPJ, CLIENTE NAO ENCONTRADO.");
							req.setAttribute("mensagem", "Cliente não encontrado");
						}
						dispatcher = req.getRequestDispatcher("controller?action=clientes");
						break;						
					default:						
						if(clientes != null){
							System.out.println("RegraClientes, PESQUISADO POR EMPRESA, CLIENTE(S) ENCONTRADO(S), FORMATANDO ENDERECO PARA EXIBICAO...");
							formataEnderecoParaExibicao(clientes);
							System.out.println("RegraClientes, PESQUISADO POR EMPRESA, CLIENTE(S) ENCONTRADO(S), ENDERECO FORMATADO.");
							req.setAttribute("clientes", clientes);													
						}else{
							if(empresa.isEmpty()){
								System.out.println("RegraClientes, PESQUISADO POR EMPRESA, NAO HA CLIENTES CADASTRADOS.");
								req.setAttribute("mensagem", "Não há clientes cadastrados");
							}else{
								System.out.println("RegraClientes, PESQUISADO POR EMPRESA, CLIENTE NAO ENCONTRADO.");
								req.setAttribute("mensagem", "Cliente não encontrado");
							}
						}		
						dispatcher = req.getRequestDispatcher("controller?action=clientes");						
						break;				
				}
				dispatcher.forward(req, resp);
				break;
				
			case("cadastrarCliente"):
				System.out.println("RegraClientes, CADASTRANDO CLIENTES...");
				empresa = req.getParameter("empresa");
				contato = req.getParameter("contato");				
				telefone = req.getParameter("telefone");
				email = req.getParameter("email");
				
				logradouro = req.getParameter("endereco");
				numero = req.getParameter("numero");
				complemento = req.getParameter("complemento");
				bairro = req.getParameter("bairro");
				cidade = req.getParameter("cidade");
				estado = req.getParameter("estado");
				cep = req.getParameter("cep");
				
				endereco = formataEnderecoParaBancoDeDados(endereco, cep, logradouro, numero, complemento, bairro, cidade,
					estado, paramEndereco);
				
				cnpj = req.getParameter("cnpj");
				inscEstadual = req.getParameter("inscEstadual");
				
				parametros = new String[][]{{"empresa", empresa},{"contato", contato}, {"telefone", telefone}, {"email", email}, {"endereco", endereco}, {"cnpj", cnpj}, {"inscEstadual", inscEstadual}};
				
				cli = new Cliente();
				
				System.out.println("RegraClientes, CADASTRANDO CLIENTES, VERIFICANDO O RECEBIMENTO DOS DADOS...");
				for(int i = 0; i < parametros.length; i++){
					if(parametros[i][1] == null | parametros[i][1].isEmpty()){
						if(parametros[i][0].equals("cnpj") || parametros[i][0].equals("inscEstadual")) parametros[i][1] = null;
						else parametros[i][1] = "";
						System.out.println("RegraClientes, CADASTRANDO CLIENTES, CAMPO NAO PREENCHIDO: " + parametros[i][0]);
					}
					
					switch(parametros[i][0]){
						case "empresa":
							cli.setEmpresa(parametros[i][1]);
							break;
						case "contato":
							cli.setContato(parametros[i][1]);
							break;
						case "telefone":
							cli.setTelefone(parametros[i][1]);
							break;
						case "email":
							cli.setEmail(parametros[i][1]);
							break;
						case "endereco":
							cli.setEndereco(parametros[i][1]);
							break;
						case "cnpj":
							cli.setCnpj(parametros[i][1]);
							break;
						case "inscEstadual":
							cli.setInscEstadual(parametros[i][1]);
							break;
					}
				}
				
				if(contato == null || contato.isEmpty()){
					System.out.println("RegraClientes, CADASTRANDO CLIENTES, DADOS VERIFICADOS, CONTATO NAO PREENCHIDO...");
					resp.setContentType("text/plain");
					resp.setCharacterEncoding("UTF-8");
					resp.getWriter().write("Informe o Contato");
				}else{
					if((telefone == null || telefone.isEmpty()) && (email == null || email.isEmpty())){
						System.out.println("RegraClientes, CADASTRANDO CLIENTES, DADOS VERIFICADOS, TELEFONE OU EMAIL NAO PREENCHIDOS...");
						resp.setContentType("text/plain");
						resp.setCharacterEncoding("UTF-8");
						resp.getWriter().write("Além do Contato, informe um Telefone ou Email");
					}else{
						System.out.println("RegraClientes, CADASTRANDO CLIENTES, DADOS VERIFICADOS, CAMPOS OBRIGATORIOS PREENCHIDOS...");
						try{
							System.out.println("RegraClientes, CADASTRANDO CLIENTES, CONSULTANDO A EXISTENCIA DE CLIENTE PELO CNPJ E INSC. ESTADUAL...");
														
							// Busca por clientes que possuam o CNPJ informado
							verificaCNPJ = new ClienteDAO().buscaClientePorCNPJ(cli);
							
							// Busca por clientes que possuam a Inscrição Estadual informada
							verificaInscEstadual = new ClienteDAO().buscaClientePorInscEstadual(cli);
							
							// Caso encontrado, significa que foi encontrado um cliente com o CNPJ ou Inscrição Estadual informados
							if(verificaCNPJ != null || verificaInscEstadual != null){
								if(verificaCNPJ != null && verificaInscEstadual != null){
									System.out.println("RegraClientes, CADASTRANDO CLIENTES, CLIENTE JA CADASTRADO COM ESSE CNPJ E INSC. ESTADUAL...");
									resp.setContentType("text/plain");
									resp.setCharacterEncoding("UTF-8");
									resp.getWriter().write("Já existe o CNPJ e Inscrição Estadual informados. Verifique!");
								}else{
									if(verificaCNPJ != null){
										System.out.println("RegraClientes, CADASTRANDO CLIENTES, CLIENTE JA CADASTRADO COM ESSE CNPJ...");
										resp.setContentType("text/plain");
										resp.setCharacterEncoding("UTF-8");
										resp.getWriter().write("Já existe o CNPJ informado. Verifique!");
									}else{
										System.out.println("RegraClientes, CADASTRANDO CLIENTES, CLIENTE JA CADASTRADO COM ESSA INSC. ESTADUAL...");
										resp.setContentType("text/plain");
										resp.setCharacterEncoding("UTF-8");
										resp.getWriter().write("Já existe a Inscrição Estadual informada. Verifique!");
									}
								}
							// Caso não for encontrado um cliente com o CNPJ informado
							}else{
								System.out.println("RegraClientes, CADASTRANDO CLIENTES, CLIENTE NAO ENCONTRADO, CADASTRANDO...");
								new ClienteDAO().inserir(cli);
								System.out.println("RegraClientes, CADASTRANDO CLIENTES, CLIENTE CADASTRADO.");
								resp.setContentType("text/plain");
								resp.setCharacterEncoding("UTF-8");
								resp.getWriter().write("Cliente cadastrado com sucesso!");
								
							}
						}catch(PersistenceException e){
							System.out.println("RegraClientes, CADASTRANDO CLIENTES, FALHA AO CADASTRAR, PersistenceException.");
							resp.setContentType("text/plain");
							resp.setCharacterEncoding("UTF-8");
							resp.getWriter().write("Falha ao cadastrar o cliente.");							
						}
					}
				}
				break;
				
			case("atualizarCliente"):
				System.out.println("RegraClientes, ATUALIZANDO CLIENTE...");			
				try{
					codigo = Long.parseLong(req.getParameter("codigo"));
				}catch(NumberFormatException e){
					System.out.println("RegraClientes, ATUALIZANDO CLIENTE, CODIGO INVALIDO, NumberFormatException...");
					throw new ServletException(e);					
				}
				empresa = req.getParameter("empresa");
				contato = req.getParameter("contato");
				telefone = req.getParameter("telefone");
				email = req.getParameter("email");
				
				logradouro = req.getParameter("endereco");
				numero = req.getParameter("numero");
				complemento = req.getParameter("complemento");
				bairro = req.getParameter("bairro");
				cidade = req.getParameter("cidade");
				estado = req.getParameter("estado");
				cep = req.getParameter("cep");
				
				endereco = formataEnderecoParaBancoDeDados(endereco, cep, logradouro, numero, complemento, bairro, cidade,
					estado, paramEndereco);
				
				cnpj = req.getParameter("cnpj");
				inscEstadual = req.getParameter("inscEstadual");
				
				parametros = new String[][]{{"empresa", empresa},{"contato", contato}, {"telefone", telefone}, {"email", email}, {"endereco", endereco}, {"cnpj", cnpj}, {"inscEstadual", inscEstadual}};
								
				cli = new Cliente();
				cli.setCodigo(codigo);				
				
				System.out.println("RegraClientes, ATUALIZANDO CLIENTES, VERIFICANDO O RECEBIMENTO DOS DADOS...");
				for(int i = 0; i < parametros.length; i++){
					
					if(parametros[i][1] == null | parametros[i][1].isEmpty()){
						if(parametros[i][0].equals("cnpj") || parametros[i][0].equals("inscEstadual")) parametros[i][1] = null;
						else parametros[i][1] = "";
						System.out.println("RegraClientes, CADASTRANDO CLIENTES, CAMPO NAO PREENCHIDO: " + parametros[i][0]);						
					}					
					
					switch(parametros[i][0]){
						case "empresa":
							cli.setEmpresa(parametros[i][1]);
							break;
						case "contato":
							cli.setContato(parametros[i][1]);
							break;
						case "telefone":
							cli.setTelefone(parametros[i][1]);
							break;
						case "email":
							cli.setEmail(parametros[i][1]);
							break;
						case "endereco":
							cli.setEndereco(parametros[i][1]);
							break;
						case "cnpj":
							cli.setCnpj(parametros[i][1]);
							break;
						case "inscEstadual":
							cli.setInscEstadual(parametros[i][1]);
							break;
					}
				}				
				
				if(contato == null || contato.isEmpty()){
					System.out.println("RegraClientes, ATUALIZANDO CLIENTES, DADOS VERIFICADOS, CONTATO NAO PREENCHIDO...");
					resp.setContentType("text/plain");
					resp.setCharacterEncoding("UTF-8");
					resp.getWriter().write("Informe o Contato");
				}else{
					if((telefone == null || telefone.isEmpty()) && (email == null || email.isEmpty())){
						System.out.println("RegraClientes, ATUALIZANDO CLIENTES, DADOS VERIFICADOS, TELEFONE OU EMAIL NAO PREENCHIDOS...");
						resp.setContentType("text/plain");
						resp.setCharacterEncoding("UTF-8");
						resp.getWriter().write("Além do Contato, informe um Telefone ou Email");
					}else{
						System.out.println("RegraClientes, ATUALIZANDO CLIENTES, DADOS VERIFICADOS, CAMPOS OBRIGATORIOS PREENCHIDOS...");
						try{
							System.out.println("RegraClientes, ATUALIZANDO CLIENTES, CONSULTANDO A EXISTENCIA DE CLIENTE PELO CNPJ E INSC. ESTADUAL...");
														
							// Busca por clientes que possuam o CNPJ informado
							verificaCNPJ = new ClienteDAO().buscaClientePorCNPJ(cli);
							
							// Busca por clientes que possuam a Inscrição Estadual informada
							verificaInscEstadual = new ClienteDAO().buscaClientePorInscEstadual(cli);
							
							// Caso encontrado, significa que foi encontrado um cliente com o CNPJ ou Inscrição Estadual informados
							if((verificaCNPJ != null && verificaCNPJ.getCodigo() != codigo) || (verificaInscEstadual != null && verificaInscEstadual.getCodigo() != codigo)){
								if((verificaCNPJ != null && verificaCNPJ.getCodigo() != codigo) && (verificaInscEstadual != null && verificaInscEstadual.getCodigo() != codigo)){
									System.out.println("RegraClientes, ATUALIZANDO CLIENTES, CLIENTE JA CADASTRADO COM ESSE CNPJ E INSC. ESTADUAL...");
									resp.setContentType("text/plain");
									resp.setCharacterEncoding("UTF-8");
									resp.getWriter().write("Já existe o CNPJ e Inscrição Estadual informados. Verifique!");
								}else{
									if(verificaCNPJ != null && verificaCNPJ.getCodigo() != codigo){
										System.out.println("RegraClientes, ATUALIZANDO CLIENTES, CLIENTE JA CADASTRADO COM ESSE CNPJ...");
										resp.setContentType("text/plain");
										resp.setCharacterEncoding("UTF-8");
										resp.getWriter().write("Já existe o CNPJ informado. Verifique!");
									}else{
										System.out.println("RegraClientes, ATUALIZANDO CLIENTES, CLIENTE JA CADASTRADO COM ESSA INSC. ESTADUAL...");
										resp.setContentType("text/plain");
										resp.setCharacterEncoding("UTF-8");
										resp.getWriter().write("Já existe a Inscrição Estadual informada. Verifique!");
									}
								}
							// Caso não for encontrado um cliente com o CNPJ informado
							}else{
								System.out.println("RegraClientes, ATUALIZANDO CLIENTES, CLIENTE NAO ENCONTRADO, ATUALIZANDO...");
								new ClienteDAO().atualizar(cli);
								System.out.println("RegraClientes, ATUALIZANDO CLIENTES, CLIENTE ATUALIZADO.");
								resp.setContentType("text/plain");
								resp.setCharacterEncoding("UTF-8");
								resp.getWriter().write("Cliente atualizado com sucesso!");
								
							}
						}catch(PersistenceException e){
							System.out.println("RegraClientes, ATUALIZANDO CLIENTES, FALHA AO ATUALIZAR, PersistenceException.");
							resp.setContentType("text/plain");
							resp.setCharacterEncoding("UTF-8");
							resp.getWriter().write("Falha ao atualizar o cliente.");							
						}
					}					
				}
				break;
				
			case("excluirCliente"):
				System.out.println("RegraClientes, EXCLUINDO CLIENTE...");			
				System.out.println("RegraClientes, EXCLUINDO CLIENTE, VERIFICANDO O RECEBIMENTO DE DADOS...");
				
				// A EXCLUSÃO É FEITA PELA "PRIMARY KEY", OU SEJA, PELO ATRIBUTO "CODIGO"				
				if(req.getParameter("codigo") != null & !req.getParameter("codigo").isEmpty()){
					System.out.println("RegraClientes, EXCLUINDO CLIENTE, DADOS RECEBIDOS.");
					try{
						System.out.println("RegraClientes, EXCLUINDO CLIENTE, CONVERTENDO DADOS.");
						codigo = Long.parseLong(req.getParameter("codigo"));
						System.out.println("RegraClientes, EXCLUINDO CLIENTE, DADOS CONVERTIDOS." + codigo);
						
						cli = new Cliente();
						cli.setCodigo(codigo);
						
						// Realiza a exclusão
						new ClienteDAO().excluir(cli);
						System.out.println("RegraClientes, EXCLUINDO CLIENTE, EXCLUSAO REALIZADA.");						
						resp.setContentType("text/plain");
						resp.setCharacterEncoding("UTF-8");
						resp.getWriter().write("Exclusão realizada com sucesso!");
						
					}catch(NumberFormatException e){
						System.out.println("RegraClientes, EXCLUINDO CLIENTE, DADOS NAO FORMATADOS, Código inválido.");
						e.printStackTrace();
						resp.setContentType("text/plain");
						resp.setCharacterEncoding("UTF-8");
						resp.getWriter().write("Código inválido.");
					}catch(PersistenceException e){
						System.out.println("RegraClientes, EXCLUINDO CLIENTE, FALHA AO EXCLUIR: PersistenceException");						
						resp.setContentType("text/plain");
						resp.setCharacterEncoding("UTF-8");
						resp.getWriter().write("Falha ao excluir o cliente.");
					}
				}else{
					System.out.println("RegraClientes, EXCLUINDO CLIENTE, DADOS NAO RECEBIDOS, Codigo NULO ou VAZIO.");					
					resp.setContentType("text/plain");
					resp.setCharacterEncoding("UTF-8");
					resp.getWriter().write("Informe o Código.");
				}			
				break;
			case "separaEnderecoCliente":
				System.out.println("RegraClientes, SEPARANDO ENDERECO CLIENTE...");
				codigo = Long.parseLong(req.getParameter("codigo"));				
				cli = new Cliente();
				cli.setCodigo(codigo);				
				cli = new ClienteDAO().buscaClientePorCodigo(cli);				
				formataEnderecoParaFormulario(cli, req, resp);
				break;
			default:				
				if(tarefa == null | tarefa.isEmpty()){
					System.out.println("RegraClientes, DEFAULT, NAO EXISTE A TAREFA OU E NULA.");					
					resp.setContentType("text/plain");
					resp.setCharacterEncoding("UTF-8");
					resp.getWriter().write("Não existe a tarefa ou é nula.");
				}else{
					System.out.println("RegraClientes, DEFAULT, NAO EXISTE A TAREFA " + tarefa);					
					resp.setContentType("text/plain");
					resp.setCharacterEncoding("UTF-8");
					resp.getWriter().write("Não existe a tarefa " + tarefa);
				}				
				break;
		}
	}

	// Método que formata o endereço recebido para ser armazenado no banco de dados
	private String formataEnderecoParaBancoDeDados(String endereco, String cep, String logradouro, String numero,
			String complemento, String bairro, String cidade, String estado, String[] paramEndereco) {
		paramEndereco[0] = logradouro;
		paramEndereco[1] = numero;
		paramEndereco[2] = complemento;
		paramEndereco[3] = bairro;
		paramEndereco[4] = cidade;
		paramEndereco[5] = estado;
		paramEndereco[6] = cep;
		
		// Montando o endereço para ser armazenado no Banco de Dados
		// Percorre os parâmetros do endereço e verifica se foi preenchido
		for(int i = 0; i < paramEndereco.length; i++){
			
			// Caso o parâmetro não foi preenchido, inclui a palavra "nulo;"
			if(paramEndereco[i] == null || paramEndereco[i] == ""){
				endereco += "nulo;";		
				
			// Caso preenchido
			}else{
				// Se não for o último parâmetro, inclui um ";" para separar os dados e facilitar na recuperação dos dados do banco, quando solicitado
				if(i != (paramEndereco.length - 1)){							
					endereco += paramEndereco[i] + ";";
				}else{
					endereco += paramEndereco[i];
				}
			}					
		}
		return endereco;
	}

	// Método para formatar o endereço da lista de clientes para exibição
	private void formataEnderecoParaExibicao(List<Cliente> clientes) {
		String endereco;
		for(Cliente cliEndereco : clientes){
			int i = clientes.indexOf(cliEndereco);
			cliEndereco = clientes.get(i);						
			endereco = cliEndereco.getEndereco();						
			endereco = endereco.replace("nulo;", "; ");
			endereco = endereco.replace(";", ", ");
			endereco = endereco.replace(";;", ", ");
			endereco = endereco.replace(", , ", ", ");
			endereco = endereco.replace(", , ,", "");
			cliEndereco.setEndereco(endereco);
			clientes.set(i, cliEndereco);
		}
	}	
	
	// Método que formata o endereço do cliente cadastrado no banco de dados para exibição no formulário de ATUALIZAR o cliente
	private void formataEnderecoParaFormulario(Cliente cliente, HttpServletRequest req, HttpServletResponse resp) {
		String endereco;
		
		endereco = cliente.getEndereco();
		String[] cliEndereco = new String[7];
		
		// Em cada posição do vetor, armazena os dados do endereço
		// O método split, faz com que corte a string onde encontrar o ";" e
		// armazenando o dado e pulando para a posição seguinte do vetor
		cliEndereco = endereco.split(";");
		
		// Percorre o vetor e onde encontrar a palavra "nulo", substitui por um espaço vazio
		for(int i = 0; i < cliEndereco.length; i++){
			if(cliEndereco[i].equals("nulo") || cliEndereco[i].isEmpty()) cliEndereco[i] = " ";
		}
		
		// Caso a requisição seja separaEnderecoCliente
		if(req.getParameter("action").equals("separaEnderecoCliente")){			
			try {
				
				// Tipo JSON
				resp.setContentType("application/json");				
				resp.setCharacterEncoding("UTF-8");			
				
				// Montando o JSON
				String dataEndereco = "";
				dataEndereco = "{\"dataEndereco\":[{"						
						+ "\"endereco\":\"" + cliEndereco[0] + "\","
						+ "\"numero\":\"" + cliEndereco[1] + "\","
						+ "\"complemento\":\"" + cliEndereco[2] + "\","
						+ "\"bairro\":\"" + cliEndereco[3] + "\","
						+ "\"cidade\":\"" + cliEndereco[4] + "\","
						+ "\"estado\":\"" + cliEndereco[5] + "\","
						+ "\"cep\":\"" + cliEndereco[6] + "\""
						+ "}]}";
				
				// Respondendo o JSON
				resp.getWriter().write(dataEndereco);
			} catch (IOException e) {
				e.printStackTrace();
			}
		
		// Atribui na requisição o vetor de endereços para ser recuperado no formulário para ser editado os dados do cliente
		}else{
			req.setAttribute("cliEndereco", cliEndereco);
		}
	}
}
