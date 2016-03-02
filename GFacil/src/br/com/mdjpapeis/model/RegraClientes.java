package br.com.mdjpapeis.model;

import java.io.IOException;
import java.util.ArrayList;
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
		String contato = null;	// Campo obrigatório, não pode ser nulo no banco de dados
		String telefone = null;
		String email = null;
		String cep = null;
		String logradouro = null;
		String numero = null;
		String complemento = null;
		String bairro = null;
		String cidade = null;
		String estado = null;		
		String endereco = null;
		String cnpj = null;
		String inscEstadual = null;
		
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
					req.setAttribute("clientes", clientes);
				}else{
					req.setAttribute("mensagem", "Não há clientes cadastrados");
				}
				
				dispatcher = req.getRequestDispatcher("controller?action=clientes");
				
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
							req.setAttribute("cli", cli);							
						}else{
							if(req.getParameter("codigo").isEmpty()){
								req.setAttribute("mensagem", "Informe o código");
							}else{
								if(codigoInvalido){
									req.setAttribute("mensagem", "Código inválido");
								}else{									
									req.setAttribute("mensagem", "Cliente não encontrado");									
								}
							}
						}						
						dispatcher = req.getRequestDispatcher("controller?action=formCliente&tarefa="+tarefa);
						
						break;
						
					case "cnpj":
						
						if(cli != null){
							req.setAttribute("cli", cli);							
						}else{
							req.setAttribute("mensagem", "Cliente não encontrado");
						}						
						dispatcher = req.getRequestDispatcher("controller?action=formCliente&tarefa="+tarefa);
						
						break;
						
					default:
						
						if(clientes != null){							
							req.setAttribute("clientes", clientes);													
						}else{
							if(empresa.isEmpty()){
								req.setAttribute("mensagem", "Não há clientes cadastrados");
							}else{
								req.setAttribute("mensagem", "Cliente não encontrado");
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
				cnpj = req.getParameter("cnpj");
				inscEstadual = req.getParameter("inscEstadual");
				
				String[] parametrosEndereco = {						
						req.getParameter("logradouro"),
						req.getParameter("numero"),
						req.getParameter("complemento"),
						req.getParameter("bairro"),
						req.getParameter("cidade"),
						req.getParameter("estado"),
						req.getParameter("cep")
				};				
				
				String parametroEndereco = null;
				for(int i = 0; i < parametrosEndereco.length; i++){
					if(parametrosEndereco[i] == null){
						endereco += "nulo";
					}else{
						if(i == parametrosEndereco.length - 1){
							endereco += parametrosEndereco[i];
						}
						endereco += parametrosEndereco[i] + "+++espaco+++";
					}
				}
				
				parametros = new String[][]{{"empresa", empresa},{"contato", contato}, {"telefone", telefone}, {"email", email}, {"endereco", endereco}, {"cnpj", cnpj}};
				
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
							cli.setCnpj(parametros[i][1]);
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
																						
							req.setAttribute("mensagem", "Já existe o CNPJ informado");												
							req.setAttribute("cli", cli);							
							
						// Caso não for encontrado um cliente com o CNPJ informado
						}else{
							
							new ClienteDAO().inserir(cli);							
							req.setAttribute("mensagem", "Cliente cadastrado com sucesso");
							
						}
					}catch(PersistenceException e){
						req.setAttribute("mensagem", "Falha ao cadastrar o cliente");
						
					// Reencaminha a requisição
					}finally{
						
						// Recebe o destino do redirecionamento da requisição
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
				endereco = req.getParameter("endereco");
				cnpj = req.getParameter("cnpj");
				
				parametros = new String[][]{{"empresa", empresa},{"contato", contato}, {"telefone", telefone}, {"email", email}, {"endereco", endereco}, {"cnpj", cnpj}};
								
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
							
							req.setAttribute("mensagem", "Já existe o CNPJ informado");														
							req.setAttribute("cli", cli);							
							
						// Caso não for encontrado um cliente com o CNPJ informado
						}else{
							
							new ClienteDAO().atualizar(cli);							
							req.setAttribute("mensagem", "Cliente atualizado com sucesso");
							
						}
					}catch(PersistenceException e){
						req.setAttribute("mensagem", "Falha ao atualizar dados");
						
					// Reencaminha a requisição
					}finally{
						
						// Recebe o destino do redirecionamento da requisição
						dispatcher = req.getRequestDispatcher("controller?action=formCliente&tarefa="+tarefa);
						
					}
				}
				break;
				
			case("excluirCliente"):
				
				// A EXCLUSÃO É FEITA PELA "PRIMARY KEY", OU SEJA, PELO ATRIBUTO "CODIGO"
				if(req.getParameter("codigo") != null & !req.getParameter("codigo").isEmpty()){
					
					try{
						codigo = Long.parseLong(req.getParameter("codigo"));
						
						cli = new Cliente();
						cli.setCodigo(codigo);
						
						// Realiza a exclusão
						new ClienteDAO().excluir(cli);
						
						req.setAttribute("mensagem", "Exclusão realizada com sucesso");
						
					}catch(NumberFormatException ex){
						ex.printStackTrace();
						req.setAttribute("mensagem", "Código inválido");
					}catch(PersistenceException e){
						req.setAttribute("mensagem", "Falha ao excluir o cliente");
					}
				}else{
					
					req.setAttribute("mensagem", "Informe o código");					
					
				}
				
				// Recebe o destino do redirecionamento da requisição
				dispatcher = req.getRequestDispatcher("controller?action=formCliente&tarefa="+tarefa);
			
				break;
				
			default:
				
				if(tarefa == null | tarefa.isEmpty()){
					req.setAttribute("mensagem", "Não existe a tarefa ou é nula");
				}else{
					req.setAttribute("mensagem", "Não existe a tarefa " + tarefa);
				}
				
				dispatcher = req.getRequestDispatcher("controller?action=formCliente");
				
				break;
		}
		
		// Reencaminha a requisição
		dispatcher.forward(req, resp);
		
	}

}
