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

@WebServlet(urlPatterns = {"/listarUsuarios", "/pesquisarUsuario", "/cadastrarUsuario", "/atualizarUsuario", "/excluirUsuario", "/pegaPerfil"})
public class RegraUsuarios extends HttpServlet{

	private static final long serialVersionUID = 1L;
	
	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
				
		System.out.println("RegraUsuarios, ACTION: " + req.getParameter("action"));
		
		String nome = null;
		String nomeusuario = null;	// esse é o nome de login
		String email = null;
		String emailParaBusca = null;
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
							System.out.println("RegraUsuarios, PESQUISADO POR NOMEUSUARIO, USUARIO ENCONTRADO");
							req.setAttribute("user", user);							
						}else{
							System.out.println("RegraUsuarios, PESQUISADO POR NOMEUSUARIO, USUARIO NAO ENCONTRADO");
							req.setAttribute("mensagem", "Usuário não encontrado");
						}
						dispatcher = req.getRequestDispatcher("controller?action=usuarios");
						break;
						
					case "email":
						System.out.println("RegraUsuarios, PESQUISADO POR EMAIL");
						if(user != null){
							System.out.println("RegraUsuarios, PESQUISADO POR EMAIL, USUARIO ENCONTRADO");
							req.setAttribute("user", user);							
						}else{
							System.out.println("RegraUsuarios, PESQUISA POR EMAIL, USUARIO NAO ENCONTRADO");
							req.setAttribute("mensagem", "Usuário não encontrado");
						}
						dispatcher = req.getRequestDispatcher("controller?action=usuarios");
						break;
						
					default:
						System.out.println("RegraUsuarios, PESQUISADO POR NOME");
						if(usuarios != null){
							System.out.println("RegraUsuarios, PESQUISADO POR NOME, USUARIO(S) ENCONTRADO(S)");							
							req.setAttribute("usuarios", usuarios);													
						}else{
							if(nome.isEmpty()){
								System.out.println("RegraUsuarios, PESQUISADO POR NOME, USUARIO(S) NAO CADASTRADO(S)");
								req.setAttribute("mensagem", "Não há usuários cadastrados");
							}else{
								System.out.println("RegraUsuarios, PESQUISADO POR NOME, USUARIO(S) NAO ENCONTRADO(S)");
								req.setAttribute("mensagem", "Usuário não encontrado");
							}
						}						
						dispatcher = req.getRequestDispatcher("controller?action=usuarios");						 
						break;				
				}
				dispatcher.forward(req, resp);
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
								resp.setContentType("text/plain");
								resp.setCharacterEncoding("UTF-8");
								resp.getWriter().write("Já existe o USUARIO e EMAIL informados");
															
							// Caso contrário, significa que somente o EMAIL informado já existe
							}else{
								System.out.println("RegraUsuarios, CADASTRANDO USUARIOS, USUARIO JA CADASTRADO COM ESSE EMAIL...");
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
								resp.setContentType("text/plain");
								resp.setCharacterEncoding("UTF-8");
								resp.getWriter().write("Já existe o USUÁRIO informado");
								
							// Caso contrário, o cadastramento é realizado
							}else{							
								System.out.println("RegraUsuarios, CADASTRANDO USUARIOS, USUARIO NAO ENCONTRADO, CADASTRANDO...");
								new UsuarioDAO().inserir(user);								
								System.out.println("RegraUsuarios, CADASTRANDO USUARIOS, USUARIO CADASTRADO.");								
								resp.setContentType("text/plain");
								resp.setCharacterEncoding("UTF-8");
								resp.getWriter().write("Usuário cadastrado com sucesso");
							}
						}
					}catch(PersistenceException e){
						System.out.println("RegraUsuarios, CADASTRANDO USUARIOS, FALHA AO CADASTRAR, PersistenceException.");
						resp.setContentType("text/plain");
						resp.setCharacterEncoding("UTF-8");
						resp.getWriter().write("Falha ao cadastrar o usuário");						
					}
				}
				break;
				
			case("atualizarUsuario"):
				System.out.println("RegraUsuarios, ATUALIZANDO USUARIO...");			
				nome = req.getParameter("nome");
				nomeusuario = req.getParameter("nomeusuario");	// esse é o nome de login				
				email = req.getParameter("email");
				emailParaBusca = req.getParameter("emailParaBusca");
				senha = req.getParameter("senha");
				perfil = req.getParameter("perfil");
				
				parametros = new String[][]{{"nome",nome},{"nomeusuario", nomeusuario}, {"emailParaBusca", emailParaBusca}, {"email", email}, {"senha", senha}, {"perfil", perfil}};
								
				user = new Usuario();				
				parametroVazio = false;
				
				System.out.println("RegraUsuarios, ATUALIZANDO USUARIO, VERIFICANDO O RECEBIMENTO DE PARÂMETROS...");
				for(int i = 0; i < parametros.length; i++){
					
					if(parametros[i][1] == null || parametros[i][1].isEmpty()){
						System.out.println("RegraUsuarios, ATUALIZANDO USUARIO, PARAMETRO NULO: " + parametros[i][0]);
						parametros[i][1] = "";
						if(parametros[i][0].equals("senha")){
							Usuario userEmail = new Usuario();
							userEmail.setEmail(emailParaBusca);
							System.out.println("RegraUsuarios, ATUALIZANDO USUARIO, EMAIL PARA BUSCA: " + emailParaBusca);
							String senhaUser = new UsuarioDAO().buscaUsuarioPorEmail(userEmail).getSenha();
							if(senhaUser != null){
								parametros[i][1] = senhaUser;
							}else{
								parametroVazio = true;
							}
						}else{
							parametroVazio = true;
						}
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
					dispatcher = req.getRequestDispatcher("controller?action=usuarios");
				}else{					
					try{						
						Usuario verificaUser = null;
						
						// Busca por usuários que possuam o EMAIL informado
						verificaUser = new UsuarioDAO().buscaUsuarioPorEmail(user);						
						

						// Caso encontrado, significa que foi encontrado um usuário com o EMAIL informado e
						// Caso o LOGIN seja diferente do informado, significa, que o EMAIL encontrado é de outro usuário
						if(verificaUser != null && !verificaUser.getLogin().equals(nomeusuario)){							
							resp.setContentType("text/plain");
							resp.setCharacterEncoding("UTF-8");
							resp.getWriter().write("Já existe o EMAIL informado!");
							
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
							resp.setContentType("text/plain");
							resp.setCharacterEncoding("UTF-8");
							resp.getWriter().write("Atualizado");
						}
					}catch(PersistenceException e){
						resp.setContentType("text/plain");
						resp.setCharacterEncoding("UTF-8");
						resp.getWriter().write("Desculpe, houve uma falha ao atualizar!");
					}
				}
				break;
				
			case("excluirUsuario"):
				System.out.println("RegraUsuarios, EXCLUINDO USUARIO...");
			
				// A EXCLUSÃO É FEITA PELA "PRIMARY KEY", OU SEJA, PELO ATRIBUTO "LOGIN"	
				nomeusuario = req.getParameter("nomeusuario");
				System.out.println("RegraUsuarios, EXCLUINDO USUARIO, VERIFICANDO O RECEBIMENTO DE DADOS...");
				if(req.getParameter("nomeusuario") == null || req.getParameter("nomeusuario").isEmpty()){
					System.out.println("RegraUsuarios, EXCLUINDO USUARIO, DADOS NAO RECEBIDOS, Nome de Usuario NULO ou VAZIO.");					
					resp.setContentType("text/plain");
					resp.setCharacterEncoding("UTF-8");
					resp.getWriter().write("Informe o Nome de Usuário");
				}
				System.out.println("RegraUsuarios, EXCLUINDO USUARIO, DADOS RECEBIDOS.");
				user = new Usuario();
				user.setLogin(nomeusuario);
				
				try{					
					// Realiza a exclusão
					new UsuarioDAO().excluir(user);
					System.out.println("RegraUsuarios, EXCLUINDO USUARIO, EXCLUSAO REALIZADA.");
					resp.setContentType("text/plain");
					resp.setCharacterEncoding("UTF-8");
					resp.getWriter().write("Exclusão realizada com sucesso");
				}catch(PersistenceException e){
					System.out.println("RegraUsuarios, EXCLUINDO USUARIO, EXCLUSAO NAO REALIZADA: PersistenceException");
					resp.setContentType("text/plain");
					resp.setCharacterEncoding("UTF-8");
					resp.getWriter().write("Falha ao excluir o usuário");					
				}catch(NullPointerException e){
					System.out.println("RegraUsuarios, EXCLUINDO USUARIO, EXCLUSAO NAO REALIZADA: NullPointerException");
					resp.setContentType("text/plain");
					resp.setCharacterEncoding("UTF-8");
					resp.getWriter().write("Falha ao excluir o usuário: NULO");					
					System.out.println("Login nulo.");
					e.printStackTrace();
				}catch(IllegalArgumentException ex){
					System.out.println("RegraUsuarios, EXCLUINDO USUARIO, EXCLUSAO NAO REALIZADA: IllegalArgumentException");
					resp.setContentType("text/plain");
					resp.setCharacterEncoding("UTF-8");
					resp.getWriter().write("Falha ao excluir o usuário");
				}				
				break;
			case ("pegaPerfil"):
				System.out.println("RegraUsuario, PEGANDO OS PERFIS...");
				String[] ajaxPerfis = {Usuario.Perfil.ADMINISTRADOR.toString(), Usuario.Perfil.COMPRADOR.toString(), Usuario.Perfil.VENDEDOR.toString()};
				System.out.println("RegraUsuario, PERFIS PRONTOS: " + ajaxPerfis[0] + ", " + ajaxPerfis[1] + ", " + ajaxPerfis[2]);
				
				// USANDO JSON
				//resp.setContentType("application/json");
				//resp.setCharacterEncoding("UTF-8");
				/*String str = "";
				str = "{\"data\":[{\"nome\":\"Eloi\",\"sobrenome\":\"Fonseca\"},{\"nome\":\"Fer\",\"sobrenome\":\"Fonseca\"}]}";
				resp.getWriter().write(str);*/
				
				// SEM JSON - SÓ STRING
				resp.setContentType("text/plain");
				resp.setCharacterEncoding("UTF-8");
				resp.getWriter().write(ajaxPerfis[0] + "," + ajaxPerfis[1] + "," + ajaxPerfis[2]);
				break;
			default:				
				if(tarefa == null | tarefa.isEmpty()){
					req.setAttribute("mensagem", "Não existe a tarefa ou é nula");
				}else{
					req.setAttribute("mensagem", "Não existe a tarefa " + tarefa);
				}
				dispatcher = req.getRequestDispatcher("controller?action=usuarios");
				break;
		}
	}
}
