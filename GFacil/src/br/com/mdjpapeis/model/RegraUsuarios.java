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

import br.com.mdjpapeis.dao.UsuarioDAO;
import br.com.mdjpapeis.entity.Usuario;

@WebServlet(urlPatterns = {"/listarUsuarios", "/pesquisarUsuario", "/cadastrarUsuario", "/atualizarUsuario", "/excluirUsuario"})
public class RegraUsuarios extends HttpServlet{

	private static final long serialVersionUID = 1L;
	
	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
				
		System.out.println("RegraUsuarios, ACTION: " + req.getParameter("action"));
		
		String nome = null;
		String nomeusuario = null;	// esse � o nome de login
		String email = null;
		String senha = null;
		String perfil = null;		
		
		Usuario usuario = new Usuario();
		Usuario user = null;
		List<Usuario> usuarios = null;
		
		String[][] parametros = null;
		String tarefa = req.getParameter("tarefa");
		boolean parametroVazio = false;
		RequestDispatcher dispatcher = null;
		
		switch(req.getParameter("action")){
			case("listarUsuarios"):
				
				usuarios = new UsuarioDAO().listar();
				
				if(usuarios != null){
					req.setAttribute("usuarios", usuarios);
				}else{
					req.setAttribute("mensagem", "N�o h� usu�rios cadastrados");
				}
				
				dispatcher = req.getRequestDispatcher("controller?action=usuarios");
				
				break;
			case("pesquisarUsuario"):
								
				nome = null;
				nomeusuario = null;	// esse � o nome de login
				email = null;
				
				usuario = new Usuario();
				user = null;
				usuarios = null;
				
				String pesquisa = null;
				
				if(req.getParameter("email") != null){
					email = req.getParameter("email");
					usuario.setEmail(email);
					user = new UsuarioDAO().buscaUsuarioPorEmail(usuario);
					pesquisa = "email";				
				}else{
					if(req.getParameter("nomeusuario") != null){
						nomeusuario = req.getParameter("nomeusuario");
						usuario.setLogin(nomeusuario);
						user = new UsuarioDAO().buscaUsuarioPorLogin(usuario);
						pesquisa = "nomeusuario";
					}else{
						if(req.getParameter("nome") != null){
							nome = req.getParameter("nome");
							usuario.setNome(nome);
							
							if(nome.isEmpty()){
								usuarios = new UsuarioDAO().listar();
							}else{
								usuarios = new UsuarioDAO().buscaUsuarioPorNome(usuario);
							}
							
							pesquisa = "nome";
						}
					}
				}							
				
				switch(pesquisa){
				
					case "nomeusuario":
						
						if(user != null){
							req.setAttribute("user", user);							
						}else{
							req.setAttribute("mensagem", "Usu�rio n�o encontrado");
						}						
						dispatcher = req.getRequestDispatcher("controller?action=formUsuario&tarefa="+tarefa);
						
						break;
						
					case "email":
						
						if(user != null){
							req.setAttribute("user", user);							
						}else{
							req.setAttribute("mensagem", "Usu�rio n�o encontrado");
						}						
						dispatcher = req.getRequestDispatcher("controller?action=formUsuario&tarefa="+tarefa);
						
						break;
						
					default:
						
						if(usuarios != null){							
							req.setAttribute("usuarios", usuarios);													
						}else{
							if(nome.isEmpty()){
								req.setAttribute("mensagem", "N�o h� usu�rios cadastrados");
							}else{
								req.setAttribute("mensagem", "Usu�rio n�o encontrado");
							}
						}	
						
						dispatcher = req.getRequestDispatcher("controller?action=usuarios");
						
						break;				
				}
				
				break;
				
			case("cadastrarUsuario"):
				
				nome = req.getParameter("nome");
				nomeusuario = req.getParameter("nomeusuario");	// esse � o nome de login
				email = req.getParameter("email");
				senha = req.getParameter("senha");
				perfil = req.getParameter("perfil");
				
				parametros = new String[][]{
					{"nome",nome},
					{"nomeusuario", nomeusuario}, 
					{"email", email}, 
					{"senha", senha}, 
					{"perfil", perfil}
					};
				
				user = new Usuario();
				
				parametroVazio = false;
				
				for(int i = 0; i < parametros.length; i++){
					
					if(parametros[i][1] == null | parametros[i][1].isEmpty()){
						parametros[i][1] = "";
						parametroVazio = true;
					}
					
					switch(parametros[i][0]){
						case "nome":
							user.setNome(parametros[i][1]);
							break;
						case "nomeusuario":
							user.setLogin(parametros[i][1]);
							break;
						case "email":
							user.setEmail(parametros[i][1]);
							break;
						case "senha":
							user.setSenha(parametros[i][1]);
							break;
						case "perfil":
							//PerfilUsuario pu = new PerfilUsuario();
							//pu.setPerfil(parametros[i][1]);
							for(Usuario.Perfil pf : Usuario.Perfil.values()){
								if(parametros[i][1].toUpperCase().equals(pf.toString())){									
									user.setPerfil(pf);
								}
							}							
							break;
					}
				}				
				
				if(parametroVazio){
					req.setAttribute("mensagem", "Preencha todos os campos");
					req.setAttribute("user", user);
					dispatcher = req.getRequestDispatcher("controller?action=formUsuario&tarefa="+tarefa);
					//dispatcher.forward(req, resp);
				}else{
					
					try{
						
						Usuario verificaUser = null;
						
						// Busca por usu�rios que possuam o EMAIL informado
						verificaUser = new UsuarioDAO().buscaUsuarioPorEmail(user);
						
						// Caso encontrado, significa que foi encontrado um usu�rio com o EMAIL informado
						if(verificaUser != null){
							
							// Caso o LOGIN seja igual, significa que al�m do EMAIL, o LOGIN informado tamb�m j� existe
							if(verificaUser.getLogin().equals(nomeusuario)){
								
								req.setAttribute("mensagem", "J� existe o USUARIO e EMAIL informados");
															
							// Caso contr�rio, significa que somente o EMAIL informado j� existe
							}else{
								
								req.setAttribute("mensagem", "J� existe o EMAIL informado");								
							}
							
							req.setAttribute("user", user);							
							
						// Caso n�o for encontrado um usu�rio com o EMAIL informado
						}else{
							
							// Busca por usu�rios que possuem o LOGIN informado
							verificaUser = new UsuarioDAO().buscaUsuarioPorLogin(user);
							
							// Verifica se foi encontrado algum usu�rio com o LOGIN informado
							if(verificaUser != null){
								
								req.setAttribute("mensagem", "J� existe o USU�RIO informado");
								req.setAttribute("user", user);																
								
							// Caso contr�rio, o cadastramento � realizado
							}else{							
							
								new UsuarioDAO().inserir(user);					
							
								req.setAttribute("mensagem", "Usu�rio cadastrado com sucesso");
							}
						}
					}catch(PersistenceException e){
						req.setAttribute("mensagem", "Falha ao cadastrar o usu�rio");
						
					// Reencaminha a requisi��o
					}finally{
						
						// Recebe o destino do redirecionamento da requisi��o
						dispatcher = req.getRequestDispatcher("controller?action=formUsuario&tarefa="+tarefa);
						
						// Reencaminha a requisi��o
						//dispatcher.forward(req, resp);
					}
				}

				break;
				
			case("atualizarUsuario"):
								
				nome = req.getParameter("nome");
				nomeusuario = req.getParameter("nomeusuario");	// esse � o nome de login
				email = req.getParameter("email");
				senha = req.getParameter("senha");
				perfil = req.getParameter("perfil");
				
				parametros = new String[][]{{"nome",nome},{"nomeusuario", nomeusuario}, {"email", email}, {"senha", senha}, {"perfil", perfil}};
								
				user = new Usuario();
				
				parametroVazio = false;
				
				for(int i = 0; i < parametros.length; i++){
					
					if(parametros[i][1] == null | parametros[i][1].isEmpty()){
						parametros[i][1] = "";
						parametroVazio = true;
					}
					
					switch(parametros[i][0]){
						case "nome":
							user.setNome(parametros[i][1]);
							break;
						case "nomeusuario":
							user.setLogin(parametros[i][1]);
							break;
						case "email":
							user.setEmail(parametros[i][1]);
							break;
						case "senha":
							user.setSenha(parametros[i][1]);
							break;
						case "perfil":
							for(Usuario.Perfil pf : Usuario.Perfil.values()){
								if(parametros[i][1].toUpperCase().equals(pf.toString())){
									user.setPerfil(pf);
								}
							}
							break;
					}
				}				
				
				if(parametroVazio){
					req.setAttribute("mensagem", "Preencha todos os campos");
					req.setAttribute("user", user);
					dispatcher = req.getRequestDispatcher("controller?action=formUsuario&tarefa="+tarefa);
					//dispatcher.forward(req, resp);
				}else{
					
					try{
						
						Usuario verificaUser = null;
						
						// Busca por usu�rios que possuam o EMAIL informado
						verificaUser = new UsuarioDAO().buscaUsuarioPorEmail(user);						
						

						// Caso encontrado, significa que foi encontrado um usu�rio com o EMAIL informado e
						// Caso o LOGIN seja diferente do informado, significa, que o EMAIL encontrado � de outro usu�rio
						if(verificaUser != null && !verificaUser.getLogin().equals(nomeusuario)){
							
							req.setAttribute("mensagem", "J� existe o EMAIL informado");
							req.setAttribute("user", user);
							
						// Caso contr�rio, a atualiza��o � realizada
						}else{
							
							// Recupera os dados do usu�rio que est� logado
							Usuario userSession = (Usuario) req.getSession().getAttribute("usuarioLogado");
							
							// Caso a atualiza��o seja do pr�prio usu�rio que est� logado
							if(userSession.getLogin().equals(user.getLogin())){
								
								// Realiza a atualiza��o
								new UsuarioDAO().atualizar(user);
								
								// Atualiza em tempo real os dados da sess�o do usu�rio logado
								userSession.setEmail(user.getEmail());
								userSession.setLogin(user.getLogin());
								userSession.setNome(user.getNome());
								userSession.setPerfil(user.getPerfil());
								userSession.setSenha(user.getSenha());
							
							// Caso contr�rio, ou seja, a atualiza��o seja de outro usu�rio
							}else{
								
								// Realiza a atualiza��o
								new UsuarioDAO().atualizar(user);
							}
							
							req.setAttribute("mensagem", "Usu�rio atualizado com sucesso");
						}
					}catch(PersistenceException e){
						req.setAttribute("mensagem", "Falha ao atualizar dados");
						
					// Reencaminha a requisi��o
					}finally{
						
						// Recebe o destino do redirecionamento da requisi��o
						dispatcher = req.getRequestDispatcher("controller?action=formUsuario&tarefa="+tarefa);
						
						// Reencaminha a requisi��o
						//dispatcher.forward(req, resp);
					}
				}
				break;
				
			case("excluirUsuario"):
				
				// A EXCLUS�O � FEITA PELA "PRIMARY KEY", OU SEJA, PELO ATRIBUTO "LOGIN"				
				email = req.getParameter("userEmail");
				nomeusuario = req.getParameter("userLogin");
				
				if(email == null | email.isEmpty()){
					System.out.println("EMAIL: ");
					req.setAttribute("mensagem", "Informe o email");
					dispatcher = req.getRequestDispatcher("controller?action=formUsuario&tarefa="+tarefa);
					//dispatcher.forward(req, resp);
				}
				
				user = new Usuario();
				user.setLogin(nomeusuario);
				
				try{
					
					// Realiza a exclus�o
					new UsuarioDAO().excluir(user);
					
					req.setAttribute("mensagem", "Exclus�o realizada com sucesso");
					
				}catch(PersistenceException e){
					req.setAttribute("mensagem", "Falha ao excluir o usu�rio");
				}catch(NullPointerException e){
					req.setAttribute("mensagem", "Falha ao excluir o usu�rio");
					System.out.println("Login nulo.");
					e.printStackTrace();
				
				// Reencaminha a requisi��o
				}finally{
					
					// Recebe o destino do redirecionamento da requisi��o
					dispatcher = req.getRequestDispatcher("controller?action=formUsuario&tarefa="+tarefa);
					
					// Reencaminha a requisi��o
					//dispatcher.forward(req, resp);
				}
				
				break;
				
			default:
				
				if(tarefa == null | tarefa.isEmpty()){
					req.setAttribute("mensagem", "N�o existe a tarefa ou � nula");
				}else{
					req.setAttribute("mensagem", "N�o existe a tarefa " + tarefa);
				}
				
				dispatcher = req.getRequestDispatcher("controller?action=formUsuario");
				//dispatcher.forward(req, resp);
				
				break;
		}
		
		dispatcher.forward(req, resp);
	}
}
