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
		String nomeusuario = null;	// esse é o nome de login
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
				System.out.println("RegraUsuarios, LISTANDO USUARIOS...");
				usuarios = new UsuarioDAO().listar();
				
				if(usuarios != null){
					System.out.println("RegraUsuarios, USUARIOS LISTADOS");
					req.setAttribute("usuarios", usuarios);
				}else{
					System.out.println("RegraUsuarios, USUARIOS NAO LISTADOS");
					req.setAttribute("mensagem", "Não há usuários cadastrados");
				}
				
				dispatcher = req.getRequestDispatcher("controller?action=usuarios");
				dispatcher.forward(req, resp);	// Só adicionado aqui porque foi comentado no final - TESTE - FUNCIONOU
				break;
			case("pesquisarUsuario"):
				System.out.println("RegraUsuarios, PESQUISANDO USUARIO(S)...");			
				nome = null;
				nomeusuario = null;	// esse é o nome de login
				email = null;
				
				usuario = new Usuario();
				user = null;
				usuarios = null;
				
				String pesquisa = null;
				
				if(req.getParameter("email") != null){
					System.out.println("RegraUsuarios, PESQUISANDO POR EMAIL...");
					email = req.getParameter("email");
					usuario.setEmail(email);
					user = new UsuarioDAO().buscaUsuarioPorEmail(usuario);
					pesquisa = "email";				
				}else{
					if(req.getParameter("nomeusuario") != null){
						System.out.println("RegraUsuarios, PESQUISANDO POR NOMEUSUARIO...");
						nomeusuario = req.getParameter("nomeusuario");
						usuario.setLogin(nomeusuario);
						user = new UsuarioDAO().buscaUsuarioPorLogin(usuario);
						pesquisa = "nomeusuario";
					}else{
						if(req.getParameter("nome") != null){
							System.out.println("RegraUsuarios, PESQUISANDO POR NOME...");
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
						System.out.println("RegraUsuarios, PESQUISADO POR NOMEUSUARIO");
						if(user != null){
							System.out.println("RegraUsuarios, PESQUISA POR NOMEUSUARIO, USUÁRIO ENCONTRADO");
							req.setAttribute("user", user);							
						}else{
							System.out.println("RegraUsuarios, PESQUISA POR NOMEUSUARIO, USUÁRIO NÃO ENCONTRADO");
							req.setAttribute("mensagem", "Usuário não encontrado");
						}						
						dispatcher = req.getRequestDispatcher("controller?action=formUsuario&tarefa="+tarefa);
						
						break;
						
					case "email":
						System.out.println("RegraUsuarios, PESQUISADO POR EMAIL");
						if(user != null){
							System.out.println("RegraUsuarios, PESQUISA POR EMAIL, USUÁRIO ENCONTRADO");
							req.setAttribute("user", user);							
						}else{
							System.out.println("RegraUsuarios, PESQUISA POR EMAIL, USUÁRIO NÃO ENCONTRADO");
							req.setAttribute("mensagem", "Usuário não encontrado");
						}						
						dispatcher = req.getRequestDispatcher("controller?action=formUsuario&tarefa="+tarefa);
						
						break;
						
					default:
						System.out.println("RegraUsuarios, PESQUISADO POR NOME");
						if(usuarios != null){
							System.out.println("RegraUsuarios, PESQUISA POR NOME, USUÁRIO(S) ENCONTRADO(S)");
							/*PrintWriter out = resp.getWriter();
							String csq = "";
							for (Usuario usu : usuarios) {
								csq = "{" + usu.getNome() + "}";
							}
							out.append(csq);*/
							req.setAttribute("usuarios", usuarios);													
						}else{
							if(nome.isEmpty()){
								System.out.println("RegraUsuarios, PESQUISADO POR NOME, USUÁRIO(S) NÃO CADASTRADO(S)");
								req.setAttribute("mensagem", "Não há usuários cadastrados");
							}else{
								System.out.println("RegraUsuarios, PESQUISADO POR NOME, USUÁRIO(S) NÃO ENCONTRADO(S)");
								req.setAttribute("mensagem", "Usuário não encontrado");
							}
						}	
						
						dispatcher = req.getRequestDispatcher("controller?action=usuarios");
						dispatcher.forward(req, resp); // APÓS TESTE COM AJAX QUANDO CADASTRAR
						break;				
				}
				
				break;
				
			case("cadastrarUsuario"):
				System.out.println("RegraUsuarios, CADASTRANDO USUARIOS...");
				nome = req.getParameter("nome");
				nomeusuario = req.getParameter("nomeusuario");	// esse é o nome de login
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
				
				System.out.println("RegraUsuarios, CADASTRANDO USUARIOS, VERIFICANDO O RECEBIMENTO DOS DADOS...");
				for(int i = 0; i < parametros.length; i++){
										
					if(parametros[i][1] == null | parametros[i][1].isEmpty()){
						parametros[i][1] = "";
						parametroVazio = true;
						System.out.println("RegraUsuarios, CADASTRANDO USUARIOS, CAMPO NAO PREENCHIDO: " + parametros[i][0]);
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
					System.out.println("RegraUsuarios, CADASTRANDO USUARIOS, DADOS VERIFICADOS, CAMPOS NAO PREENCHIDOS...");
					resp.setContentType("text/plain");
					resp.setCharacterEncoding("UTF-8");
					resp.getWriter().write("Preencha todos os campos");
					/*req.setAttribute("mensagem", "Preencha todos os campos");
					req.setAttribute("user", user);
					dispatcher = req.getRequestDispatcher("controller?action=formUsuario&tarefa="+tarefa);*/
					//dispatcher.forward(req, resp);
					//dispatcher = req.getRequestDispatcher("controller?action=usuarios&tarefa="+tarefa);
				}else{
					System.out.println("RegraUsuarios, CADASTRANDO USUARIOS, DADOS VERIFICADOS, CAMPOS PREENCHIDOS...");
					try{
						System.out.println("RegraUsuarios, CADASTRANDO USUARIOS, CONSULTANDO A EXISTÊNCIA DE USUÁRIO PELO EMAIL...");
						Usuario verificaUser = null;
						
						// Busca por usuários que possuam o EMAIL informado
						verificaUser = new UsuarioDAO().buscaUsuarioPorEmail(user);
						
						// Caso encontrado, significa que foi encontrado um usuário com o EMAIL informado
						if(verificaUser != null){							
							
							// Caso o LOGIN seja igual, significa que além do EMAIL, o LOGIN informado também já existe
							if(verificaUser.getLogin().equals(nomeusuario)){
								System.out.println("RegraUsuarios, CADASTRANDO USUARIOS, USUARIO JA CADASTRADO COM ESSE EMAIL E LOGIN...");
								//req.setAttribute("mensagem", "Já existe o USUARIO e EMAIL informados");
								resp.setContentType("text/plain");
								resp.setCharacterEncoding("UTF-8");
								resp.getWriter().write("Já existe o USUARIO e EMAIL informados");
															
							// Caso contrário, significa que somente o EMAIL informado já existe
							}else{
								System.out.println("RegraUsuarios, CADASTRANDO USUARIOS, USUARIO JA CADASTRADO COM ESSE EMAIL...");
								//req.setAttribute("mensagem", "Já existe o EMAIL informado");
								resp.setContentType("text/plain");
								resp.setCharacterEncoding("UTF-8");
								resp.getWriter().write("Já existe o EMAIL informado");
							}
							
							req.setAttribute("user", user);							
							
						// Caso não for encontrado um usuário com o EMAIL informado
						}else{
							System.out.println("RegraUsuarios, CADASTRANDO USUARIOS, CONSULTANDO A EXISTENCIA DE USUARIO PELO LOGIN...");
							// Busca por usuários que possuem o LOGIN informado
							verificaUser = new UsuarioDAO().buscaUsuarioPorLogin(user);
							
							// Verifica se foi encontrado algum usuário com o LOGIN informado
							if(verificaUser != null){
								System.out.println("RegraUsuarios, CADASTRANDO USUARIOS, USUARIO JA CADASTRADO COM ESSE LOGIN...");
								//req.setAttribute("mensagem", "Já existe o USUÁRIO informado");
								//req.setAttribute("user", user);
								resp.setContentType("text/plain");
								resp.setCharacterEncoding("UTF-8");
								resp.getWriter().write("Já existe o USUÁRIO informado");
								
							// Caso contrário, o cadastramento é realizado
							}else{							
								System.out.println("RegraUsuarios, CADASTRANDO USUARIOS, USUARIO ENCONTRADO, CADASTRANDO...");
								new UsuarioDAO().inserir(user);					
								
								System.out.println("RegraUsuarios, CADASTRANDO USUARIOS, USUARIO CADASTRADO.");
								
								resp.setContentType("text/plain");
								resp.setCharacterEncoding("UTF-8");
								resp.getWriter().write("Usuário cadastrado com sucesso");
								//req.setAttribute("mensagem", "Usuário cadastrado com sucesso");
							}
						}
					}catch(PersistenceException e){
						resp.setContentType("text/plain");
						resp.setCharacterEncoding("UTF-8");
						resp.getWriter().write("Falha ao cadastrar o usuário");
						
						//req.setAttribute("mensagem", "Falha ao cadastrar o usuário");
						
					// Reencaminha a requisição
					}finally{
						
						// Recebe o destino do redirecionamento da requisição
						// dispatcher = req.getRequestDispatcher("controller?action=formUsuario&tarefa="+tarefa);
						//PrintWriter out = resp.getWriter();
						//out.print("Usuário cadastrado com sucesso");
						/*PrintWriter out = resp.getWriter();
							String csq = "";
							for (Usuario usu : usuarios) {
								csq = "{" + usu.getNome() + "}";
							}
							out.append(csq);*/
						//dispatcher = req.getRequestDispatcher("controller?action=usuarios");
						// Reencaminha a requisição
						//dispatcher.forward(req, resp);
					}
				}

				break;
				
			case("atualizarUsuario"):
				System.out.println("RegraUsuarios, ATUALIZANDO USUARIO...");
			
				nome = req.getParameter("nome");
				nomeusuario = req.getParameter("nomeusuario");	// esse é o nome de login
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
						
						// Busca por usuários que possuam o EMAIL informado
						verificaUser = new UsuarioDAO().buscaUsuarioPorEmail(user);						
						

						// Caso encontrado, significa que foi encontrado um usuário com o EMAIL informado e
						// Caso o LOGIN seja diferente do informado, significa, que o EMAIL encontrado é de outro usuário
						if(verificaUser != null && !verificaUser.getLogin().equals(nomeusuario)){
							
							req.setAttribute("mensagem", "Já existe o EMAIL informado");
							req.setAttribute("user", user);
							
						// Caso contrário, a atualização é realizada
						}else{
							
							// Recupera os dados do usuário que está logado
							Usuario userSession = (Usuario) req.getSession().getAttribute("usuarioLogado");
							
							// Caso a atualização seja do próprio usuário que está logado
							if(userSession.getLogin().equals(user.getLogin())){
								
								// Realiza a atualização
								new UsuarioDAO().atualizar(user);
								
								// Atualiza em tempo real os dados da sessão do usuário logado
								userSession.setEmail(user.getEmail());
								userSession.setLogin(user.getLogin());
								userSession.setNome(user.getNome());
								userSession.setPerfil(user.getPerfil());
								userSession.setSenha(user.getSenha());
							
							// Caso contrário, ou seja, a atualização seja de outro usuário
							}else{
								
								// Realiza a atualização
								new UsuarioDAO().atualizar(user);
							}
							
							req.setAttribute("mensagem", "Usuário atualizado com sucesso");
						}
					}catch(PersistenceException e){
						req.setAttribute("mensagem", "Falha ao atualizar dados");
						
					// Reencaminha a requisição
					}finally{
						
						// Recebe o destino do redirecionamento da requisição
						dispatcher = req.getRequestDispatcher("controller?action=formUsuario&tarefa="+tarefa);
						
						// Reencaminha a requisição
						//dispatcher.forward(req, resp);
					}
				}
				break;
				
			case("excluirUsuario"):
				System.out.println("RegraUsuarios, EXCLUINDO USUARIO...");
			
				// A EXCLUSÃO É FEITA PELA "PRIMARY KEY", OU SEJA, PELO ATRIBUTO "LOGIN"				
				//email = req.getParameter("userEmail");
				//nomeusuario = req.getParameter("userLogin");
				nomeusuario = req.getParameter("nomeusuario");
				//if(email == null | email.isEmpty()){
				System.out.println("RegraUsuarios, EXCLUINDO USUARIO, VERIFICANDO O RECEBIMENTO DE DADOS...");
				if(req.getParameter("nomeusuario") == null || req.getParameter("nomeusuario").isEmpty()){
					System.out.println("RegraUsuarios, EXCLUINDO USUARIO, DADOS NAO RECEBIDOS, Nome de Usuario NULO ou VAZIO.");
					
					resp.setContentType("text/plain");
					resp.setCharacterEncoding("UTF-8");
					resp.getWriter().write("Informe o Nome de Usuário");
					//dispatcher = req.getRequestDispatcher("controller?action=usuarios");
					
					//req.setAttribute("mensagem", "Informe o email");
					//dispatcher = req.getRequestDispatcher("controller?action=formUsuario&tarefa="+tarefa);					
					//dispatcher.forward(req, resp);
				}
				System.out.println("RegraUsuarios, EXCLUINDO USUARIO, DADOS RECEBIDOS.");
				user = new Usuario();
				user.setLogin(nomeusuario);
				
				try{					
					// Realiza a exclusão
					new UsuarioDAO().excluir(user);
					System.out.println("RegraUsuarios, EXCLUINDO USUARIO, EXCLUSÃO REALIZADA.");
					//req.setAttribute("mensagem", "Exclusão realizada com sucesso");
					resp.setContentType("text/plain");
					resp.setCharacterEncoding("UTF-8");
					resp.getWriter().write("Exclusão realizada com sucesso");					
					
				}catch(PersistenceException e){
					System.out.println("RegraUsuarios, EXCLUINDO USUARIO, EXCLUSÃO NÃO REALIZADA: PersistenceException");
					//req.setAttribute("mensagem", "Falha ao excluir o usuário");
					resp.setContentType("text/plain");
					resp.setCharacterEncoding("UTF-8");
					resp.getWriter().write("Falha ao excluir o usuário");					
				}catch(NullPointerException e){
					//req.setAttribute("mensagem", "Falha ao excluir o usuário");
					System.out.println("RegraUsuarios, EXCLUINDO USUARIO, EXCLUSÃO NÃO REALIZADA: NullPointerException");
					resp.setContentType("text/plain");
					resp.setCharacterEncoding("UTF-8");
					resp.getWriter().write("Falha ao excluir o usuário: NULO");
					
					System.out.println("Login nulo.");
					e.printStackTrace();
				}catch(IllegalArgumentException ex){
					System.out.println("RegraUsuarios, EXCLUINDO USUARIO, EXCLUSÃO NÃO REALIZADA: IllegalArgumentException");
					resp.setContentType("text/plain");
					resp.setCharacterEncoding("UTF-8");
					resp.getWriter().write("Falha ao excluir o usuário");					
				// Reencaminha a requisição
				}finally{
					System.out.println("RegraUsuarios, EXCLUINDO USUARIO, Redirecionando URL...");
					// Recebe o destino do redirecionamento da requisição					
					//dispatcher = req.getRequestDispatcher("controller?action=usuarios");
					//dispatcher.forward(req, resp); // APÓS TESTE COM AJAX QUANDO CADASTRAR
					
					//dispatcher = req.getRequestDispatcher("controller?action=formUsuario&tarefa="+tarefa);
					
					// Reencaminha a requisição
					//dispatcher.forward(req, resp);
				}
				
				break;
				
			default:
				
				if(tarefa == null | tarefa.isEmpty()){
					req.setAttribute("mensagem", "Não existe a tarefa ou é nula");
				}else{
					req.setAttribute("mensagem", "Não existe a tarefa " + tarefa);
				}
				
				dispatcher = req.getRequestDispatcher("controller?action=formUsuario");
				//dispatcher.forward(req, resp);
				
				break;
		}
		
		//dispatcher.forward(req, resp);
	}
}
