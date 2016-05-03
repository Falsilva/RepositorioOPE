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

@WebServlet(urlPatterns = {"/listarClientes", "/pesquisarCliente", "/cadastrarCliente", "/atualizarCliente", "/excluirCliente"})
public class RegraClientes extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) 
			throws ServletException, IOException {
		
		System.out.println("RegraClientes - ACTION: " + req.getParameter("action"));
		
		long codigo = 0;
		String empresa = null;
		String contato = null;	// Campo obrigat�rio, n�o pode ser nulo no banco de dados
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
		
		String[][] parametros = null;
		String tarefa = req.getParameter("tarefa");
		boolean parametroVazio = false;
		boolean codigoInvalido = false;
		RequestDispatcher dispatcher = null;
		
		switch(req.getParameter("action")){
			case("listarClientes"):
				
				clientes = new ClienteDAO().listar();
				
				if(clientes != null){
					
					formataEnderecoParaExibicao(clientes);
					
					req.setAttribute("clientes", clientes);
				}else{
					req.setAttribute("mensagem", "N�o h� clientes cadastrados");
				}
				
				dispatcher = req.getRequestDispatcher("controller?action=clientes");
				dispatcher.forward(req, resp);
				break;
			case("pesquisarCliente"):
				
				codigo = 0;
				empresa = null;
				contato = null;
				cnpj = null;
				
				cliente = new Cliente();
				cli = null;
				clientes = null;
				
				String pesquisa = null;
				
				if(req.getParameter("cnpj") != null){
					cnpj = req.getParameter("cnpj");
					cliente.setCnpj(cnpj);
					cli = new ClienteDAO().buscaClientePorCNPJ(cliente);
										
					pesquisa = "cnpj";				
				}else{
					if(req.getParameter("codigo") != null){
						
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
							
							empresa = req.getParameter("empresa");
							cliente.setEmpresa(empresa);
							
							if(empresa.isEmpty()){			
								clientes = new ClienteDAO().listar();
							}else{
								clientes = new ClienteDAO().buscaClientePorEmpresa(cliente);
							}
							
							pesquisa = "empresa";
						}
					}
				}							
				
				switch(pesquisa){
				
					case "codigo":
						
						if(cli != null){
							
							formataEnderecoParaFormulario(cli, req);
							
							req.setAttribute("cli", cli);							
						}else{
							if(req.getParameter("codigo").isEmpty()){
								req.setAttribute("mensagem", "Informe o c�digo");
							}else{
								if(codigoInvalido){
									req.setAttribute("mensagem", "C�digo inv�lido");
								}else{									
									req.setAttribute("mensagem", "Cliente n�o encontrado");									
								}
							}
						}						
						dispatcher = req.getRequestDispatcher("controller?action=formCliente&tarefa="+tarefa);
						
						break;
						
					case "cnpj":
						
						if(cli != null){
							
							formataEnderecoParaFormulario(cli, req);
							
							req.setAttribute("cli", cli);							
						}else{
							req.setAttribute("mensagem", "Cliente n�o encontrado");
						}						
						dispatcher = req.getRequestDispatcher("controller?action=formCliente&tarefa="+tarefa);
						
						break;
						
					default:
						
						if(clientes != null){
							
							formataEnderecoParaExibicao(clientes);
							
							req.setAttribute("clientes", clientes);													
						}else{
							if(empresa.isEmpty()){
								req.setAttribute("mensagem", "N�o h� clientes cadastrados");
							}else{
								req.setAttribute("mensagem", "Cliente n�o encontrado");
							}
						}	
						
						dispatcher = req.getRequestDispatcher("controller?action=clientes");
						
						break;				
				}
				
				break;
				
			case("cadastrarCliente"):
				
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
				
				parametroVazio = false;
				
				for(int i = 0; i < parametros.length; i++){
					
					if(parametros[i][1] == null | parametros[i][1].isEmpty()){
						parametros[i][1] = "";
						parametroVazio = true;
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
					req.setAttribute("mensagem", "Informe o Contato");
					req.setAttribute("cli", cli);
					dispatcher = req.getRequestDispatcher("controller?action=formCliente&tarefa="+tarefa);
				}else{
					
					try{
						
						Cliente verificaCli = null;
						
						// Busca por clientes que possuam o CNPJ informado
						verificaCli = new ClienteDAO().buscaClientePorCNPJ(cli);
						
						// Caso encontrado, significa que foi encontrado um cliente com o CNPJ informado
						if(verificaCli != null){
																						
							req.setAttribute("mensagem", "J� existe o CNPJ informado");												
							req.setAttribute("cli", cli);							
							
						// Caso n�o for encontrado um cliente com o CNPJ informado
						}else{
							
							new ClienteDAO().inserir(cli);							
							req.setAttribute("mensagem", "Cliente cadastrado com sucesso");
							
						}
					}catch(PersistenceException e){
						req.setAttribute("mensagem", "Falha ao cadastrar o cliente");
						
					// Reencaminha a requisi��o
					}finally{	
						
						// Recebe o destino do redirecionamento da requisi��o
						dispatcher = req.getRequestDispatcher("controller?action=formCliente&tarefa="+tarefa);						
					}
				}

				break;
				
			case("atualizarCliente"):
				
				codigo = Long.parseLong(req.getParameter("codigo"));
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
				
				parametroVazio = false;
				
				for(int i = 0; i < parametros.length; i++){
					
					if(parametros[i][1] == null | parametros[i][1].isEmpty()){
						parametros[i][1] = "";
						parametroVazio = true;
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
										
					req.setAttribute("mensagem", "Informe o contato");						
					req.setAttribute("cli", cli);
					
					dispatcher = req.getRequestDispatcher("controller?action=formCliente&tarefa="+tarefa);					
					
				}else{
					
					try{
						
						Cliente verificaCli = null;
						
						// Busca por clientes que possuam o CNPJ informado
						verificaCli = new ClienteDAO().buscaClientePorCNPJ(cli);						
						
						// Caso encontrado, significa que foi encontrado um cliente com o CNPJ informado
						if(verificaCli != null && verificaCli.getCodigo() != codigo){
							
							req.setAttribute("mensagem", "J� existe o CNPJ informado");														
							req.setAttribute("cli", cli);							
							
						// Caso n�o for encontrado um cliente com o CNPJ informado
						}else{
							
							new ClienteDAO().atualizar(cli);							
							req.setAttribute("mensagem", "Cliente atualizado com sucesso");
							
						}
					}catch(PersistenceException e){
						req.setAttribute("mensagem", "Falha ao atualizar dados");
						
					// Reencaminha a requisi��o
					}finally{
						
						// Recebe o destino do redirecionamento da requisi��o
						dispatcher = req.getRequestDispatcher("controller?action=formCliente&tarefa="+tarefa);
						
					}
				}
				break;
				
			case("excluirCliente"):
				
				// A EXCLUS�O � FEITA PELA "PRIMARY KEY", OU SEJA, PELO ATRIBUTO "CODIGO"
				if(req.getParameter("codigo") != null & !req.getParameter("codigo").isEmpty()){
					
					try{
						codigo = Long.parseLong(req.getParameter("codigo"));
						
						cli = new Cliente();
						cli.setCodigo(codigo);
						
						// Realiza a exclus�o
						new ClienteDAO().excluir(cli);
						
						req.setAttribute("mensagem", "Exclus�o realizada com sucesso");
						
					}catch(NumberFormatException ex){
						ex.printStackTrace();
						req.setAttribute("mensagem", "C�digo inv�lido");
					}catch(PersistenceException e){
						req.setAttribute("mensagem", "Falha ao excluir o cliente");
					}
				}else{
					
					req.setAttribute("mensagem", "Informe o c�digo");					
					
				}
				
				// Recebe o destino do redirecionamento da requisi��o
				dispatcher = req.getRequestDispatcher("controller?action=formCliente&tarefa="+tarefa);
			
				break;
				
			default:
				
				if(tarefa == null | tarefa.isEmpty()){
					req.setAttribute("mensagem", "N�o existe a tarefa ou � nula");
				}else{
					req.setAttribute("mensagem", "N�o existe a tarefa " + tarefa);
				}
				
				dispatcher = req.getRequestDispatcher("controller?action=formCliente");
				
				break;
		}
		
		// Reencaminha a requisi��o
		//dispatcher.forward(req, resp);
		
	}

	// M�todo que formata o endere�o recebido para ser armazenado no banco de dados
	private String formataEnderecoParaBancoDeDados(String endereco, String cep, String logradouro, String numero,
			String complemento, String bairro, String cidade, String estado, String[] paramEndereco) {
		paramEndereco[0] = logradouro;
		paramEndereco[1] = numero;
		paramEndereco[2] = complemento;
		paramEndereco[3] = bairro;
		paramEndereco[4] = cidade;
		paramEndereco[5] = estado;
		paramEndereco[6] = cep;
		
		// Montando o endere�o para ser armazenado no Banco de Dados
		// Percorre os par�metros do endere�o e verifica se foi preenchido
		for(int i = 0; i < paramEndereco.length; i++){
			
			// Caso o par�metro n�o foi preenchido, inclui a palavra "nulo;"
			if(paramEndereco[i] == null){
				endereco += "nulo;";		
				
			// Caso preenchido
			}else{
				// Se n�o for o �ltimo par�metro, inclui um ";" para separar os dados e facilitar na recupera��o dos dados do banco, quando solicitado
				if(i != (paramEndereco.length - 1)){							
					endereco += paramEndereco[i] + ";";
				}else{
					endereco += paramEndereco[i];
				}
			}					
		}
		return endereco;
	}

	// M�todo para formatar o endere�o da lista de clientes para exibi��o
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
	
	// M�todo que formata o endere�o do cliente cadastrado no banco de dados para exibi��o no formul�rio de ATUALIZAR o cliente
	private void formataEnderecoParaFormulario(Cliente cliente, HttpServletRequest req) {
		String endereco;
		
		endereco = cliente.getEndereco();
		String[] cliEndereco = new String[7];
		
		// Em cada posi��o do vetor, armazena os dados do endere�o
		// O m�todo split, faz com que corte a string onde encontrar o ";" e
		// armazenando o dado e pulando para a posi��o seguinte do vetor
		cliEndereco = endereco.split(";");
		
		// Percorre o vetor e onde encontrar a palavra "nulo", substitui por um espa�o vazio
		for(int i = 0; i < cliEndereco.length; i++){
			if(cliEndereco[i].equals("nulo")) cliEndereco[i] = " ";
		}
		
		// Atribui na requisi��o o vetor de endere�os para ser recuperado no formul�rio para ser editado os dados do cliente
		req.setAttribute("cliEndereco", cliEndereco);
	}

}
