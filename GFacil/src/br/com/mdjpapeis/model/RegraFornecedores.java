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
import br.com.mdjpapeis.dao.FornecedorDAO;
import br.com.mdjpapeis.entity.Cliente;
import br.com.mdjpapeis.entity.Fornecedor;

@WebServlet(urlPatterns = {"/listarFornecedores", "/pesquisarFornecedor", "/cadastrarFornecedor", "/atualizarFornecedor", "/excluirFornecedor", "/separaEnderecoFornecedor"})
public class RegraFornecedores extends HttpServlet{
	
	private static final long serialVersionUID = 1L;
	
	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		System.out.println("RegraFornecedores - ACTION: " + req.getParameter("action"));
		
		long codigo = 0;
		String tipo = null;
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
		
		Fornecedor fornecedor = new Fornecedor();
		Fornecedor forn = null;
		List<Fornecedor> fornecedores = null;
		Fornecedor verificaCNPJ = null;
		Fornecedor verificaInscEstadual = null;
		
		String[][] parametros = null;
		String tarefa = req.getParameter("tarefa");
		boolean codigoInvalido = false;
		RequestDispatcher dispatcher = null;
		
		switch(req.getParameter("action")){
			case("listarFornecedores"):
				System.out.println("RegraFornecedores, LISTANDO FORNECEDORES...");
				fornecedores = new FornecedorDAO().listar();
				
				if(tarefa != null && tarefa.equals("pedidoCompra")){						
					
					// Montando o JSON
					String dataListaFornecedores = "";
					String arrayFornecedores = "";
					
					if(fornecedores != null){
						int count = 1;
						for(Fornecedor f : fornecedores){
							if(count == 1){
								arrayFornecedores += "{"			
										+ "\"codigo\":\"" + f.getCodigo() + "\","
										+ "\"empresa\":\"" + f.getEmpresa() + "\","
										+ "\"contato\":\"" + f.getContato() + "\","
										+ "\"tipo\":\"" + f.getTipo().toString() + "\""
										+ "}";
								count = 2;
							}else{
								arrayFornecedores += ", " + "{"			
										+ "\"codigo\":\"" + f.getCodigo() + "\","
										+ "\"empresa\":\"" + f.getEmpresa() + "\","
										+ "\"contato\":\"" + f.getContato() + "\","
										+ "\"tipo\":\"" + f.getTipo().toString() + "\""
										+ "}";
							}
						}					
						dataListaFornecedores = "{\"dataListaFornecedores\":[" + arrayFornecedores + "]}";
					}else{
						dataListaFornecedores = "{\"dataListaFornecedores\":\"null\"}";
					}
					resp.setContentType("application/json");				
					resp.setCharacterEncoding("UTF-8");
					resp.getWriter().write(dataListaFornecedores);
				}else{
					if(fornecedores != null){
						System.out.println("RegraFornecedores, FORNECEDORES LISTADOS, FORMATANDO O ENDERECO PARA EXIBICAO...");
						formataEnderecoParaExibicao(fornecedores);
						System.out.println("RegraFornecedores, FORNECEDORES LISTADOS, ENDERECO FORMATADO.");
						req.setAttribute("fornecedores", fornecedores);
					}else{
						System.out.println("RegraFornecedores, NAO HA FORNECEDORES CADASTRADOS.");
						req.setAttribute("mensagem", "Não há fornecedores cadastrados");
					}
					
					dispatcher = req.getRequestDispatcher("controller?action=fornecedores");
					dispatcher.forward(req, resp);
				}
				break;
			case("pesquisarFornecedor"):
				System.out.println("RegraFornecedores, PESQUISANDO FORNECEDOR(ES)...");
				codigo = 0;
				empresa = null;
				contato = null;
				cnpj = null;
				
				fornecedor = new Fornecedor();
				forn = null;
				fornecedores = null;
				
				String pesquisa = null;
				
				if(req.getParameter("cnpj") != null){
					System.out.println("RegraFornecedores, PESQUISANDO POR CNPJ...");
					cnpj = req.getParameter("cnpj");
					fornecedor.setCnpj(cnpj);
					forn = new FornecedorDAO().buscaFornecedorPorCNPJ(fornecedor);
					pesquisa = "cnpj";				
				}else{
					if(req.getParameter("codigo") != null){
						System.out.println("RegraFornecedores, PESQUISANDO POR CODIGO...");
						if(!req.getParameter("codigo").isEmpty()){
							try{
								codigo = Long.parseLong(req.getParameter("codigo"));				
								fornecedor.setCodigo(codigo);
								forn = new FornecedorDAO().buscaFornecedorPorCodigo(fornecedor);								
							}catch(NumberFormatException ex){
								codigoInvalido = true;
								ex.printStackTrace();
							}
						}
						pesquisa = "codigo";						
					}else{
						if(req.getParameter("empresa") != null){
							System.out.println("RegraFornecedores, PESQUISANDO POR EMPRESA...");
							empresa = req.getParameter("empresa");
							fornecedor.setEmpresa(empresa);
							
							if(empresa.isEmpty()){
								System.out.println("RegraFornecedores, PESQUISANDO POR EMPRESA, EMPRESA NAO INFORMADA, LISTANDO EMPRESAS...");
								fornecedores = new FornecedorDAO().listar();
							}else{
								System.out.println("RegraFornecedores, PESQUISANDO POR EMPRESA, EMPRESA INFORMADA...");
								fornecedores = new FornecedorDAO().buscaFornecedorPorEmpresa(fornecedor);
							}							
							pesquisa = "empresa";
						}
					}
				}							
				
				switch(pesquisa){				
					case "codigo":						
						if(forn != null){
							System.out.println("RegraFornecedores, PESQUISADO POR CODIGO, FORNECEDOR ENCONTRADO, FORMATANDO ENDERECO PARA EXIBICAO...");
							formataEnderecoParaFormulario(forn, req, resp);
							System.out.println("RegraFornecedores, PESQUISADO POR CODIGO, FORNECEDOR ENCONTRADO, ENDERECO FORMATADO.");
							req.setAttribute("forn", forn);							
						}else{
							if(req.getParameter("codigo").isEmpty()){
								System.out.println("RegraFornecedores, PESQUISADO POR CODIGO, CODIGO NAO INFORMADO...");
								req.setAttribute("mensagem", "Informe o código");
							}else{
								if(codigoInvalido){
									System.out.println("RegraFornecedores, PESQUISADO POR CODIGO, CODIGO INVALIDO...");
									req.setAttribute("mensagem", "Código inválido");
								}else{									
									req.setAttribute("mensagem", "Fornecedor não encontrado");									
								}
								System.out.println("RegraFornecedores, PESQUISADO POR CODIGO, FORNECEDOR NAO ENCONTRADO.");
							}
						}						
						dispatcher = req.getRequestDispatcher("controller?action=fornecedores");						
						break;						
					case "cnpj":						
						if(forn != null){
							System.out.println("RegraFornecedores, PESQUISADO POR CNPJ, FORNECEDOR ENCONTRADO, FORMATANDO ENDERECO PARA EXIBICAO...");
							formataEnderecoParaFormulario(forn, req, resp);
							System.out.println("RegraFornecedores, PESQUISADO POR CNPJ, FORNECEDOR ENCONTRADO, ENDERECO FORMATADO.");
							req.setAttribute("forn", forn);							
						}else{
							System.out.println("RegraFornecedores, PESQUISADO POR CNPJ, FORNECEDOR NAO ENCONTRADO.");
							req.setAttribute("mensagem", "Fornecedor não encontrado");
						}						
						dispatcher = req.getRequestDispatcher("controller?action=fornecedores");						
						break;						
					default:						
						if(fornecedores != null){
							System.out.println("RegraFornecedores, PESQUISADO POR EMPRESA, FORNECEDOR(ES) ENCONTRADO(S), FORMATANDO ENDERECO PARA EXIBICAO...");
							formataEnderecoParaExibicao(fornecedores);
							System.out.println("RegraFornecedores, PESQUISADO POR EMPRESA, FORNECEDOR(ES) ENCONTRADO(S), ENDERECO FORMATADO.");
							req.setAttribute("fornecedores", fornecedores);													
						}else{
							if(empresa.isEmpty()){
								System.out.println("RegraFornecedores, PESQUISADO POR EMPRESA, NAO HA FORNECEDORES CADASTRADOS.");
								req.setAttribute("mensagem", "Não há fornecedores cadastrados");
							}else{
								System.out.println("RegraFornecedores, PESQUISADO POR EMPRESA, FORNECEDOR NAO ENCONTRADO.");
								req.setAttribute("mensagem", "Fornecedor não encontrado");
							}
						}						
						dispatcher = req.getRequestDispatcher("controller?action=fornecedores");						
						break;
				}
				dispatcher.forward(req, resp);
				break;
				
			case("cadastrarFornecedor"):
				System.out.println("RegraFornecedores, CADASTRANDO FORNECEDORES...");
				tipo = req.getParameter("tipoFornecedor");
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
				
				parametros = new String[][]{{"tipo", tipo}, {"empresa", empresa}, {"contato", contato}, {"telefone", telefone}, {"email", email}, {"endereco", endereco}, {"cnpj", cnpj}, {"inscEstadual", inscEstadual}};
				
				forn = new Fornecedor();
				
				System.out.println("RegraFornecedores, CADASTRANDO FORNECEDORES, VERIFICANDO O RECEBIMENTO DOS DADOS...");
				for(int i = 0; i < parametros.length; i++){					
					if(parametros[i][1] == null | parametros[i][1].isEmpty()){
						if(parametros[i][0].equals("cnpj") || parametros[i][0].equals("inscEstadual")) parametros[i][1] = null;
						else parametros[i][1] = "";
						System.out.println("RegraFornecedores, CADASTRANDO FORNECEDORES, CAMPO NAO PREENCHIDO: " + parametros[i][0]);
					}
					
					switch(parametros[i][0]){
						case "tipo":
							for(Fornecedor.Tipo tp : Fornecedor.Tipo.values()){
								if(parametros[i][1].toUpperCase().equals(tp.toString())){									
									forn.setTipo(tp);
								}
							}							
							break;
						case "empresa":
							forn.setEmpresa(parametros[i][1]);
							break;
						case "contato":
							forn.setContato(parametros[i][1]);
							break;
						case "telefone":
							forn.setTelefone(parametros[i][1]);
							break;
						case "email":
							forn.setEmail(parametros[i][1]);
							break;
						case "endereco":
							forn.setEndereco(parametros[i][1]);
							break;
						case "cnpj":
							forn.setCnpj(parametros[i][1]);
							break;
						case "inscEstadual":
							forn.setInscEstadual(parametros[i][1]);
							break;
					}
				}				
				
				if(contato == null || contato.isEmpty()){				
					System.out.println("RegraFornecedores, CADASTRANDO FORNECEDORES, DADOS VERIFICADOS, CONTATO NAO PREENCHIDO...");
					resp.setContentType("text/plain");
					resp.setCharacterEncoding("UTF-8");
					resp.getWriter().write("Informe o Contato");
				}else{
					if((telefone == null || telefone.isEmpty()) && (email == null || email.isEmpty())){
						System.out.println("RegraFornecedores, CADASTRANDO FORNECEDORES, DADOS VERIFICADOS, TELEFONE OU EMAIL NAO PREENCHIDOS...");
						resp.setContentType("text/plain");
						resp.setCharacterEncoding("UTF-8");
						resp.getWriter().write("Além do Contato, informe um Telefone ou Email");
					}else{
						System.out.println("RegraFornecedores, CADASTRANDO FORNECEDORES, DADOS VERIFICADOS, CAMPOS OBRIGATORIOS PREENCHIDOS...");						
						try{							
							// Caso o tipo de fornecedor seja EMPRESA, o CNPJ e Insc. Estadual são verificados
							if(forn.getTipo().equals("EMPRESA")){
								System.out.println("RegraFornecedores, CADASTRANDO FORNECEDORES, CONSULTANDO A EXISTENCIA DE FORNECEDOR PELO CNPJ E INSC. ESTADUAL...");								
								
								// Busca por fornecedores que possuam o CNPJ informado
								verificaCNPJ = new FornecedorDAO().buscaFornecedorPorCNPJ(forn);
								
								// Busca por fornecedores que possuam a Inscrição Estadual informada
								verificaInscEstadual = new FornecedorDAO().buscaFornecedorPorInscEstadual(forn);
								
								// Caso encontrado, significa que foi encontrado um fornecedor com o CNPJ ou Inscrição Estadual informados
								if(verificaCNPJ != null || verificaInscEstadual != null){
									if(verificaCNPJ != null && verificaInscEstadual != null){
										System.out.println("RegraFornecedores, CADASTRANDO FORNECEDORES, FORNECEDOR JA CADASTRADO COM ESSE CNPJ E INSC. ESTADUAL...");
										resp.setContentType("text/plain");
										resp.setCharacterEncoding("UTF-8");
										resp.getWriter().write("Já existe o CNPJ e Inscrição Estadual informados. Verifique!");
									}else{
										if(verificaCNPJ != null){
											System.out.println("RegraFornecedores, CADASTRANDO FORNECEDORES, FORNECEDOR JA CADASTRADO COM ESSE CNPJ...");
											resp.setContentType("text/plain");
											resp.setCharacterEncoding("UTF-8");
											resp.getWriter().write("Já existe o CNPJ informado. Verifique!");
										}else{
											System.out.println("RegraFornecedores, CADASTRANDO FORNECEDORES, FORNECEDOR JA CADASTRADO COM ESSA INSC. ESTADUAL...");
											resp.setContentType("text/plain");
											resp.setCharacterEncoding("UTF-8");
											resp.getWriter().write("Já existe a Inscrição Estadual informada. Verifique!");
										}
									}
								// Caso não for encontrado um fornecedor com o CNPJ informado
								}else{
									System.out.println("RegraFornecedores, CADASTRANDO FORNECEDORES, FORNECEDOR NAO ENCONTRADO, CADASTRANDO...");
									new FornecedorDAO().inserir(forn);
									System.out.println("RegraFornecedores, CADASTRANDO FORNECEDORES, FORNECEDOR CADASTRADO.");
									resp.setContentType("text/plain");
									resp.setCharacterEncoding("UTF-8");
									resp.getWriter().write("Fornecedor cadastrado com sucesso!");								
								}
								
							// Caso o tipo de fornecedor seja CATADOR
							}else{
								System.out.println("RegraFornecedores, CADASTRANDO FORNECEDORES, FORNECEDOR NAO ENCONTRADO, CADASTRANDO...");
								new FornecedorDAO().inserir(forn);
								System.out.println("RegraFornecedores, CADASTRANDO FORNECEDORES, FORNECEDOR CADASTRADO.");
								resp.setContentType("text/plain");
								resp.setCharacterEncoding("UTF-8");
								resp.getWriter().write("Fornecedor cadastrado com sucesso!");
							}							
						}catch(PersistenceException e){							
							System.out.println("RegraFornecedores, CADASTRANDO FORNECEDORES, FALHA AO CADASTRAR, PersistenceException.");
							resp.setContentType("text/plain");
							resp.setCharacterEncoding("UTF-8");
							resp.getWriter().write("Falha ao cadastrar o cliente.");
						}
					}
				}
				break;
				
			case("atualizarFornecedor"):
				System.out.println("RegraFornecedores, ATUALIZANDO FORNECEDOR...");			
				try{
					codigo = Long.parseLong(req.getParameter("codigo"));
				}catch(NumberFormatException e){
					System.out.println("RegraFornecedores, ATUALIZANDO FORNECEDOR, CODIGO INVALIDO, NumberFormatException...");
					throw new ServletException(e);					
				}
				tipo = req.getParameter("tipoFornecedor");
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
				
				parametros = new String[][]{{"tipo", tipo}, {"empresa", empresa},{"contato", contato}, {"telefone", telefone}, {"email", email}, {"endereco", endereco}, {"cnpj", cnpj}, {"inscEstadual", inscEstadual}};
								
				forn = new Fornecedor();
				forn.setCodigo(codigo);
				
				System.out.println("RegraFornecedores, ATUALIZANDO FORNECEDORES, VERIFICANDO O RECEBIMENTO DOS DADOS...");
				for(int i = 0; i < parametros.length; i++){
					System.out.println("PARAMETRO: " + parametros[i][0] + ": " + parametros[i][1]);
					if(parametros[i][1] == null | parametros[i][1].isEmpty()){
						if(parametros[i][0].equals("cnpj") || parametros[i][0].equals("inscEstadual")) parametros[i][1] = null;
						else parametros[i][1] = "";
						System.out.println("RegraFornecedores, CADASTRANDO FORNECEDORES, CAMPO NAO PREENCHIDO: " + parametros[i][0]);
					}
					
					switch(parametros[i][0]){
						case "tipo":							
							for(Fornecedor.Tipo tp : Fornecedor.Tipo.values()){
								if(parametros[i][1].toUpperCase().equals(tp.toString())){									
									forn.setTipo(tp);
								}
							}
							break;
						case "empresa":
							forn.setEmpresa(parametros[i][1]);
							break;
						case "contato":
							forn.setContato(parametros[i][1]);
							break;
						case "telefone":
							forn.setTelefone(parametros[i][1]);
							break;
						case "email":
							forn.setEmail(parametros[i][1]);
							break;
						case "endereco":
							forn.setEndereco(parametros[i][1]);
							break;
						case "cnpj":
							forn.setCnpj(parametros[i][1]);
							break;
						case "inscEstadual":
							forn.setInscEstadual(parametros[i][1]);
							break;
					}
				}
				
				if(contato == null || contato.isEmpty()){										
					System.out.println("RegraFornecedores, ATUALIZANDO FORNECEDORES, DADOS VERIFICADOS, CONTATO NAO PREENCHIDO...");
					resp.setContentType("text/plain");
					resp.setCharacterEncoding("UTF-8");
					resp.getWriter().write("Informe o Contato");
				}else{
					if((telefone == null || telefone.isEmpty()) && (email == null || email.isEmpty())){
						System.out.println("RegraFornecedores, ATUALIZANDO FORNECEDORES, DADOS VERIFICADOS, TELEFONE OU EMAIL NAO PREENCHIDOS...");
						resp.setContentType("text/plain");
						resp.setCharacterEncoding("UTF-8");
						resp.getWriter().write("Além do Contato, informe um Telefone ou Email");
					}else{						
						System.out.println("RegraFornecedores, ATUALIZANDO FORNECEDORES, DADOS VERIFICADOS, CAMPOS OBRIGATORIOS PREENCHIDOS...");
						try{
							// Caso o tipo de fornecedor seja EMPRESA, o CNPJ e Insc. Estadual são verificados
							if(forn.getTipo().equals("EMPRESA")){								
								System.out.println("RegraFornecedores, ATUALIZANDO FORNECEDORES, CONSULTANDO A EXISTENCIA DE FORNECEDOR PELO CNPJ E INSC. ESTADUAL...");
							
								// Busca por fornecedores que possuam o CNPJ informado
								verificaCNPJ = new FornecedorDAO().buscaFornecedorPorCNPJ(forn);
								
								// Busca por clientes que possuam a Inscrição Estadual informada
								verificaInscEstadual = new FornecedorDAO().buscaFornecedorPorInscEstadual(forn);
								
								// Caso encontrado, significa que foi encontrado um cliente com o CNPJ ou Inscrição Estadual informados
								if((verificaCNPJ != null && verificaCNPJ.getCodigo() != codigo) || (verificaInscEstadual != null && verificaInscEstadual.getCodigo() != codigo)){
									if((verificaCNPJ != null && verificaCNPJ.getCodigo() != codigo) && (verificaInscEstadual != null && verificaInscEstadual.getCodigo() != codigo)){
										System.out.println("RegraFornecedores, ATUALIZANDO FORNECEDORES, FORNECEDOR JA CADASTRADO COM ESSE CNPJ E INSC. ESTADUAL...");
										resp.setContentType("text/plain");
										resp.setCharacterEncoding("UTF-8");
										resp.getWriter().write("Já existe o CNPJ e Inscrição Estadual informados. Verifique!");
									}else{
										if(verificaCNPJ != null && verificaCNPJ.getCodigo() != codigo){
											System.out.println("RegraFornecedores, ATUALIZANDO FORNECEDORES, FORNECEDOR JA CADASTRADO COM ESSE CNPJ...");
											resp.setContentType("text/plain");
											resp.setCharacterEncoding("UTF-8");
											resp.getWriter().write("Já existe o CNPJ informado. Verifique!");
										}else{
											System.out.println("RegraFornecedores, ATUALIZANDO FORNECEDORES, FORNECEDOR JA CADASTRADO COM ESSA INSC. ESTADUAL...");
											resp.setContentType("text/plain");
											resp.setCharacterEncoding("UTF-8");
											resp.getWriter().write("Já existe a Inscrição Estadual informada. Verifique!");
										}
									}
								// Caso não for encontrado um fornecedor com o CNPJ informado
								}else{
									System.out.println("RegraFornecedores, ATUALIZANDO FORNECEDORES, FORNECEDOR NAO ENCONTRADO, ATUALIZANDO...");
									new FornecedorDAO().atualizar(forn);
									System.out.println("RegraFornecedores, ATUALIZANDO FORNECEDORES, FORNECEDOR ATUALIZADO.");
									resp.setContentType("text/plain");
									resp.setCharacterEncoding("UTF-8");
									resp.getWriter().write("Fornecedor atualizado com sucesso!");								
								}
							// Caso o tipo de fornecedor seja CATADOR
							}else{
								System.out.println("RegraFornecedores, ATUALIZANDO FORNECEDORES, FORNECEDOR NAO ENCONTRADO, ATUALIZANDO...");
								new FornecedorDAO().atualizar(forn);
								System.out.println("RegraFornecedores, ATUALIZANDO FORNECEDORES, FORNECEDOR ATUALIZADO.");
								resp.setContentType("text/plain");
								resp.setCharacterEncoding("UTF-8");
								resp.getWriter().write("Fornecedor atualizado com sucesso!");
							}
						}catch(PersistenceException e){
							System.out.println("RegraFornecedores, ATUALIZANDO FORNECEDORES, FALHA AO ATUALIZAR, PersistenceException.");
							resp.setContentType("text/plain");
							resp.setCharacterEncoding("UTF-8");
							resp.getWriter().write("Falha ao atualizar o fornecedor.");							
						}
					}
				}
				break;
				
			case("excluirFornecedor"):
				System.out.println("RegraFornecedores, EXCLUINDO FORNECEDOR...");			
				System.out.println("RegraFornecedores, EXCLUINDO FORNECEDOR, VERIFICANDO O RECEBIMENTO DE DADOS...");
			
				// A EXCLUSÃO É FEITA PELA "PRIMARY KEY", OU SEJA, PELO ATRIBUTO "CODIGO"
				if(req.getParameter("codigo") != null & !req.getParameter("codigo").isEmpty()){
					System.out.println("RegraFornecedores, EXCLUINDO FORNECEDOR, DADOS RECEBIDOS.");
					try{
						System.out.println("RegraFornecedores, EXCLUINDO FORNECEDOR, CONVERTENDO DADOS.");
						codigo = Long.parseLong(req.getParameter("codigo"));
						System.out.println("RegraFornecedores, EXCLUINDO FORNECEDOR, DADOS CONVERTIDOS.");
						
						forn = new Fornecedor();
						forn.setCodigo(codigo);
						
						// Realiza a exclusão
						new FornecedorDAO().excluir(forn);
						System.out.println("RegraFornecedores, EXCLUINDO FORNECEDOR, EXCLUSAO REALIZADA.");						
						resp.setContentType("text/plain");
						resp.setCharacterEncoding("UTF-8");
						resp.getWriter().write("Exclusão realizada com sucesso!");
						
					}catch(NumberFormatException e){
						System.out.println("RegraFornecedores, EXCLUINDO FORNECEDOR, DADOS NAO FORMATADOS, Código inválido.");
						e.printStackTrace();
						resp.setContentType("text/plain");
						resp.setCharacterEncoding("UTF-8");
						resp.getWriter().write("Código inválido.");
					}catch(PersistenceException e){
						System.out.println("RegraClientes, EXCLUINDO FORNECEDOR, FALHA AO EXCLUIR: PersistenceException");						
						resp.setContentType("text/plain");
						resp.setCharacterEncoding("UTF-8");
						resp.getWriter().write("Falha ao excluir o fornecedor.");
					}
				}else{
					System.out.println("RegraFornecedores, EXCLUINDO FORNECEDOR, DADOS NAO RECEBIDOS, Codigo NULO ou VAZIO.");					
					resp.setContentType("text/plain");
					resp.setCharacterEncoding("UTF-8");
					resp.getWriter().write("Informe o Código.");					
				}
				break;
			case "separaEnderecoFornecedor":
				System.out.println("RegraFornecedores, SEPARANDO ENDERECO FORNECEDOR...");
				codigo = Long.parseLong(req.getParameter("codigo"));				
				forn = new Fornecedor();
				forn.setCodigo(codigo);				
				forn = new FornecedorDAO().buscaFornecedorPorCodigo(forn);				
				formataEnderecoParaFormulario(forn, req, resp);
				break;
			default:				
				if(tarefa == null | tarefa.isEmpty()){
					System.out.println("RegraFornecedores, DEFAULT, NAO EXISTE A TAREFA OU E NULA.");					
					resp.setContentType("text/plain");
					resp.setCharacterEncoding("UTF-8");
					resp.getWriter().write("Não existe a tarefa ou é nula.");					
				}else{
					System.out.println("RegraFornecedores, DEFAULT, NAO EXISTE A TAREFA " + tarefa);					
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
	private void formataEnderecoParaExibicao(List<Fornecedor> fornecedores) {
		String endereco;
		for(Fornecedor fornEndereco : fornecedores){
			int i = fornecedores.indexOf(fornEndereco);
			fornEndereco = fornecedores.get(i);						
			endereco = fornEndereco.getEndereco();						
			endereco = endereco.replace("nulo;", "; ");
			endereco = endereco.replace(";", ", ");
			endereco = endereco.replace(";;", ", ");
			endereco = endereco.replace(", , ", ", ");
			endereco = endereco.replace(", , ,", "");			
			fornEndereco.setEndereco(endereco);
			fornecedores.set(i, fornEndereco);
		}
	}	
		
	// Método que formata o endereço do fornecedor cadastrado no banco de dados para exibição no formulário de ATUALIZAR o fornecedor
	private void formataEnderecoParaFormulario(Fornecedor fornecedor, HttpServletRequest req, HttpServletResponse resp) {
		String endereco;
			
		endereco = fornecedor.getEndereco();
		String[] fornEndereco = new String[7];
		
		// Em cada posição do vetor, armazena os dados do endereço
		// O método split, faz com que corte a string onde encontrar o ";" e
		// armazenando o dado e pulando para a posição seguinte do vetor
		fornEndereco = endereco.split(";");
			
		// Percorre o vetor e onde encontrar a palavra "nulo", substitui por um espaço vazio
		for(int i = 0; i < fornEndereco.length; i++){
			if(fornEndereco[i].equals("nulo") || fornEndereco[i].isEmpty()) fornEndereco[i] = " ";
		}
		
		// Caso a requisição seja separaEnderecoFornecedor
		if(req.getParameter("action").equals("separaEnderecoFornecedor")){			
			try {
				
				// Tipo JSON
				resp.setContentType("application/json");				
				resp.setCharacterEncoding("UTF-8");			
				
				// Montando o JSON
				String dataEndereco = "";
				dataEndereco = "{\"dataEndereco\":[{"						
						+ "\"endereco\":\"" + fornEndereco[0] + "\","
						+ "\"numero\":\"" + fornEndereco[1] + "\","
						+ "\"complemento\":\"" + fornEndereco[2] + "\","
						+ "\"bairro\":\"" + fornEndereco[3] + "\","
						+ "\"cidade\":\"" + fornEndereco[4] + "\","
						+ "\"estado\":\"" + fornEndereco[5] + "\","
						+ "\"cep\":\"" + fornEndereco[6] + "\""
						+ "}]}";
				
				// Respondendo o JSON
				resp.getWriter().write(dataEndereco);
			} catch (IOException e) {
				e.printStackTrace();
			}
		
		// Atribui na requisição o vetor de endereços para ser recuperado no formulário para ser editado os dados do fornecedor
		}else{
			req.setAttribute("fornEndereco", fornEndereco);
		}
	}	
}
