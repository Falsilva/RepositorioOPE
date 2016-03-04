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
import br.com.mdjpapeis.entity.TipoFornecedor;

@WebServlet(urlPatterns = {"/listarFornecedores", "/pesquisarFornecedor", "/cadastrarFornecedor", "/atualizarFornecedor", "/excluirFornecedor"})
public class RegraFornecedores extends HttpServlet{
	
	private static final long serialVersionUID = 1L;
	
	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		System.out.println("RegraFornecedores - ACTION: " + req.getParameter("action"));
		
		long codigo = 0;
		String tipo = null;
		String empresa = null;
		String contato = null;	// Campo obrigat�rio, n�o pode ser nulo no banco de dados
		String telefone = null;
		String email = null;
		String endereco = null;
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
		
		String[][] parametros = null;
		String tarefa = req.getParameter("tarefa");
		boolean parametroVazio = false;
		boolean codigoInvalido = false;
		RequestDispatcher dispatcher = null;
		
		switch(req.getParameter("action")){
			case("listarFornecedores"):
				
				fornecedores = new FornecedorDAO().listar();
				
				if(fornecedores != null){
					
					formataEnderecoParaExibicao(fornecedores);
					
					req.setAttribute("fornecedores", fornecedores);
				}else{
					req.setAttribute("mensagem", "N�o h� fornecedores cadastrados");
				}
				
				dispatcher = req.getRequestDispatcher("controller?action=fornecedores");
				
				break;
			case("pesquisarFornecedor"):
				
				codigo = 0;
				empresa = null;
				contato = null;
				cnpj = null;
				
				fornecedor = new Fornecedor();
				forn = null;
				fornecedores = null;
				
				String pesquisa = null;
				
				if(req.getParameter("cnpj") != null){
					cnpj = req.getParameter("cnpj");
					fornecedor.setCnpj(cnpj);
					forn = new FornecedorDAO().buscaFornecedorPorCNPJ(fornecedor);
					pesquisa = "cnpj";				
				}else{
					if(req.getParameter("codigo") != null){
						
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
							
							empresa = req.getParameter("empresa");
							fornecedor.setEmpresa(empresa);
							
							if(empresa.isEmpty()){			
								fornecedores = new FornecedorDAO().listar();
							}else{
								fornecedores = new FornecedorDAO().buscaFornecedorPorEmpresa(fornecedor);
							}
							
							pesquisa = "empresa";
						}
					}
				}							
				
				switch(pesquisa){
				
					case "codigo":
						
						if(forn != null){
							formataEnderecoParaFormulario(forn, req);
							req.setAttribute("forn", forn);							
						}else{
							if(req.getParameter("codigo").isEmpty()){
								req.setAttribute("mensagem", "Informe o c�digo");
							}else{
								if(codigoInvalido){
									req.setAttribute("mensagem", "C�digo inv�lido");
								}else{									
									req.setAttribute("mensagem", "Fornecedor n�o encontrado");									
								}
							}
						}						
						dispatcher = req.getRequestDispatcher("controller?action=formFornecedor&tarefa="+tarefa);
						
						break;
						
					case "cnpj":
						
						if(forn != null){
							formataEnderecoParaFormulario(forn, req);
							req.setAttribute("forn", forn);							
						}else{
							req.setAttribute("mensagem", "Fornecedor n�o encontrado");
						}						
						dispatcher = req.getRequestDispatcher("controller?action=formFornecedor&tarefa="+tarefa);
						
						break;
						
					default:
						
						if(fornecedores != null){	
							formataEnderecoParaExibicao(fornecedores);
							req.setAttribute("fornecedores", fornecedores);													
						}else{
							if(empresa.isEmpty()){
								req.setAttribute("mensagem", "N�o h� fornecedores cadastrados");
							}else{
								req.setAttribute("mensagem", "Fornecedor n�o encontrado");
							}
						}	
						
						dispatcher = req.getRequestDispatcher("controller?action=fornecedores");
						
						break;				
				}
				
				break;
				
			case("cadastrarFornecedor"):
				
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
				
				parametroVazio = false;
				
				for(int i = 0; i < parametros.length; i++){
					
					if(parametros[i][1] == null | parametros[i][1].isEmpty()){
						parametros[i][1] = "";
						parametroVazio = true;
					}
					
					switch(parametros[i][0]){
						case "tipo":							
							forn.setTipo(new TipoFornecedor(parametros[i][1]));
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
					req.setAttribute("mensagem", "Informe o Contato");
					req.setAttribute("forn", forn);
					dispatcher = req.getRequestDispatcher("controller?action=formFornecedor&tarefa="+tarefa);
				}else{
					
					try{
						
						Fornecedor verificaForn = null;
						
						// Busca por fornecedores que possuam o CNPJ informado
						verificaForn = new FornecedorDAO().buscaFornecedorPorCNPJ(forn);
						
						// Caso encontrado, significa que foi encontrado um fornecedor com o CNPJ informado
						if(verificaForn != null){
																						
							req.setAttribute("mensagem", "J� existe o CNPJ informado");												
							req.setAttribute("forn", forn);							
							
						// Caso n�o for encontrado um cliente com o CNPJ informado
						}else{
							
							new FornecedorDAO().inserir(forn);							
							req.setAttribute("mensagem", "Fornecedor cadastrado com sucesso");
							
						}
					}catch(PersistenceException e){
						req.setAttribute("mensagem", "Falha ao cadastrar o fornecedor");
						
					// Reencaminha a requisi��o
					}finally{
						
						// Recebe o destino do redirecionamento da requisi��o
						dispatcher = req.getRequestDispatcher("controller?action=formFornecedor&tarefa="+tarefa);						
					}
				}

				break;
				
			case("atualizarFornecedor"):
				
				codigo = Long.parseLong(req.getParameter("codigo"));
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
				
				parametroVazio = false;
				
				for(int i = 0; i < parametros.length; i++){
					
					if(parametros[i][1] == null | parametros[i][1].isEmpty()){
						parametros[i][1] = "";
						parametroVazio = true;
					}					
					
					switch(parametros[i][0]){
						case "tipo":							
							forn.setTipo(new TipoFornecedor(parametros[i][1]));
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
										
					req.setAttribute("mensagem", "Informe o contato");						
					req.setAttribute("forn", forn);
					
					dispatcher = req.getRequestDispatcher("controller?action=formFornecedor&tarefa="+tarefa);					
					
				}else{
					
					try{
						
						Fornecedor verificaForn = null;
						
						// Busca por fornecedores que possuam o CNPJ informado
						verificaForn = new FornecedorDAO().buscaFornecedorPorCNPJ(forn);						
						
						// Caso encontrado, significa que foi encontrado um fornecedor com o CNPJ informado
						if(verificaForn != null && verificaForn.getCodigo() != codigo){
							
							req.setAttribute("mensagem", "J� existe o CNPJ informado");														
							req.setAttribute("forn", forn);							
							
						// Caso n�o for encontrado um fornecedor com o CNPJ informado
						}else{
							
							new FornecedorDAO().atualizar(forn);							
							req.setAttribute("mensagem", "Fornecedor atualizado com sucesso");
							
						}
					}catch(PersistenceException e){
						req.setAttribute("mensagem", "Falha ao atualizar dados");
						
					// Reencaminha a requisi��o
					}finally{
						
						// Recebe o destino do redirecionamento da requisi��o
						dispatcher = req.getRequestDispatcher("controller?action=formFornecedor&tarefa="+tarefa);
						
					}
				}
				break;
				
			case("excluirFornecedor"):
				
				// A EXCLUS�O � FEITA PELA "PRIMARY KEY", OU SEJA, PELO ATRIBUTO "CODIGO"
				if(req.getParameter("codigo") != null & !req.getParameter("codigo").isEmpty()){
					
					try{
						codigo = Long.parseLong(req.getParameter("codigo"));
						
						forn = new Fornecedor();
						forn.setCodigo(codigo);
						
						// Realiza a exclus�o
						new FornecedorDAO().excluir(forn);
						
						req.setAttribute("mensagem", "Exclus�o realizada com sucesso");
						
					}catch(NumberFormatException ex){
						ex.printStackTrace();
						req.setAttribute("mensagem", "C�digo inv�lido");
					}catch(PersistenceException e){
						req.setAttribute("mensagem", "Falha ao excluir o fornecedor");
					}
				}else{
					
					req.setAttribute("mensagem", "Informe o c�digo");					
					
				}
				
				// Recebe o destino do redirecionamento da requisi��o
				dispatcher = req.getRequestDispatcher("controller?action=formFornecedor&tarefa="+tarefa);
			
				break;
				
			default:
				
				if(tarefa == null | tarefa.isEmpty()){
					req.setAttribute("mensagem", "N�o existe a tarefa ou � nula");
				}else{
					req.setAttribute("mensagem", "N�o existe a tarefa " + tarefa);
				}
				
				dispatcher = req.getRequestDispatcher("controller?action=formFornecedor");
				
				break;
		}
		
		// Reencaminha a requisi��o
		dispatcher.forward(req, resp);
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
	private void formataEnderecoParaExibicao(List<Fornecedor> fornecedores) {
		String endereco;
		for(Fornecedor fornEndereco : fornecedores){
			int i = fornecedores.indexOf(fornEndereco);
			fornEndereco = fornecedores.get(i);						
			endereco = fornEndereco.getEndereco();						
			String endPalavraNulo = endereco.replace("nulo;", "; ");
			String enderecoPalavraEspaco = endPalavraNulo.replace(";", ", ");
			String enderecoFinal = enderecoPalavraEspaco.replace(", , ", ", ");
			fornEndereco.setEndereco(enderecoFinal);
			fornecedores.set(i, fornEndereco);
		}
	}	
		
	// M�todo que formata o endere�o do cliente cadastrado no banco de dados para exibi��o no formul�rio de ATUALIZAR o cliente
	private void formataEnderecoParaFormulario(Fornecedor fornecedor, HttpServletRequest req) {
		String endereco;
			
		endereco = fornecedor.getEndereco();
		String[] fornEndereco = new String[7];
			
		// Em cada posi��o do vetor, armazena os dados do endere�o
		// O m�todo split, faz com que corte a string onde encontrar o ";" e
		// armazenando o dado e pulando para a posi��o seguinte do vetor
		fornEndereco = endereco.split(";");
			
		// Percorre o vetor e onde encontrar a palavra "nulo", substitui por um espa�o vazio
		for(int i = 0; i < fornEndereco.length; i++){
			if(fornEndereco[i].equals("nulo")) fornEndereco[i] = " ";
		}
			
		// Atribui na requisi��o o vetor de endere�os para ser recuperado no formul�rio para ser editado os dados do cliente
		req.setAttribute("fornEndereco", fornEndereco);
	}	
}
