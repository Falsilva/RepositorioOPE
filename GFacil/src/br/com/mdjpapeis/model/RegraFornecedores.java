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
		String contato = null;	// Campo obrigatório, não pode ser nulo no banco de dados
		String telefone = null;
		String email = null;
		String endereco = null;
		String cnpj = null;
		
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
					req.setAttribute("fornecedores", fornecedores);
				}else{
					req.setAttribute("mensagem", "Não há fornecedores cadastrados");
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
							req.setAttribute("forn", forn);							
						}else{
							if(req.getParameter("codigo").isEmpty()){
								req.setAttribute("mensagem", "Informe o código");
							}else{
								if(codigoInvalido){
									req.setAttribute("mensagem", "Código inválido");
								}else{									
									req.setAttribute("mensagem", "Fornecedor não encontrado");									
								}
							}
						}						
						dispatcher = req.getRequestDispatcher("controller?action=formFornecedor&tarefa="+tarefa);
						
						break;
						
					case "cnpj":
						
						if(forn != null){
							req.setAttribute("forn", forn);							
						}else{
							req.setAttribute("mensagem", "Fornecedor não encontrado");
						}						
						dispatcher = req.getRequestDispatcher("controller?action=formFornecedor&tarefa="+tarefa);
						
						break;
						
					default:
						
						if(fornecedores != null){							
							req.setAttribute("fornecedores", fornecedores);													
						}else{
							if(empresa.isEmpty()){
								req.setAttribute("mensagem", "Não há fornecedores cadastrados");
							}else{
								req.setAttribute("mensagem", "Fornecedor não encontrado");
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
				endereco = req.getParameter("endereco");
				cnpj = req.getParameter("cnpj");
				
				parametros = new String[][]{{"tipo", tipo}, {"empresa", empresa}, {"contato", contato}, {"telefone", telefone}, {"email", email}, {"endereco", endereco}, {"cnpj", cnpj}};
				
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
																						
							req.setAttribute("mensagem", "Já existe o CNPJ informado");												
							req.setAttribute("forn", forn);							
							
						// Caso não for encontrado um cliente com o CNPJ informado
						}else{
							
							new FornecedorDAO().inserir(forn);							
							req.setAttribute("mensagem", "Fornecedor cadastrado com sucesso");
							
						}
					}catch(PersistenceException e){
						req.setAttribute("mensagem", "Falha ao cadastrar o fornecedor");
						
					// Reencaminha a requisição
					}finally{
						
						// Recebe o destino do redirecionamento da requisição
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
				endereco = req.getParameter("endereco");
				cnpj = req.getParameter("cnpj");
				
				parametros = new String[][]{{"tipo", tipo}, {"empresa", empresa}, {"contato", contato}, {"telefone", telefone}, {"email", email}, {"endereco", endereco}, {"cnpj", cnpj}};
								
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
							
							req.setAttribute("mensagem", "Já existe o CNPJ informado");														
							req.setAttribute("forn", forn);							
							
						// Caso não for encontrado um fornecedor com o CNPJ informado
						}else{
							
							new FornecedorDAO().atualizar(forn);							
							req.setAttribute("mensagem", "Fornecedor atualizado com sucesso");
							
						}
					}catch(PersistenceException e){
						req.setAttribute("mensagem", "Falha ao atualizar dados");
						
					// Reencaminha a requisição
					}finally{
						
						// Recebe o destino do redirecionamento da requisição
						dispatcher = req.getRequestDispatcher("controller?action=formFornecedor&tarefa="+tarefa);
						
					}
				}
				break;
				
			case("excluirFornecedor"):
				
				// A EXCLUSÃO É FEITA PELA "PRIMARY KEY", OU SEJA, PELO ATRIBUTO "CODIGO"
				if(req.getParameter("codigo") != null & !req.getParameter("codigo").isEmpty()){
					
					try{
						codigo = Long.parseLong(req.getParameter("codigo"));
						
						forn = new Fornecedor();
						forn.setCodigo(codigo);
						
						// Realiza a exclusão
						new FornecedorDAO().excluir(forn);
						
						req.setAttribute("mensagem", "Exclusão realizada com sucesso");
						
					}catch(NumberFormatException ex){
						ex.printStackTrace();
						req.setAttribute("mensagem", "Código inválido");
					}catch(PersistenceException e){
						req.setAttribute("mensagem", "Falha ao excluir o fornecedor");
					}
				}else{
					
					req.setAttribute("mensagem", "Informe o código");					
					
				}
				
				// Recebe o destino do redirecionamento da requisição
				dispatcher = req.getRequestDispatcher("controller?action=formFornecedor&tarefa="+tarefa);
			
				break;
				
			default:
				
				if(tarefa == null | tarefa.isEmpty()){
					req.setAttribute("mensagem", "Não existe a tarefa ou é nula");
				}else{
					req.setAttribute("mensagem", "Não existe a tarefa " + tarefa);
				}
				
				dispatcher = req.getRequestDispatcher("controller?action=formFornecedor");
				
				break;
		}
		
		// Reencaminha a requisição
		dispatcher.forward(req, resp);
	}
}
