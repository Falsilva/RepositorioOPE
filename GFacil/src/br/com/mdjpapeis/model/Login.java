package br.com.mdjpapeis.model;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import br.com.mdjpapeis.dao.UsuarioDAO;
import br.com.mdjpapeis.entity.PerfilUsuario;
import br.com.mdjpapeis.entity.Usuario;

@WebServlet(urlPatterns = "/login")
public class Login extends HttpServlet{

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		// Recebendo os dados enviados na requisi��o
		String nomeusuario = req.getParameter("nomeusuario");
		String senha = req.getParameter("senha");
		
		// Utilizado para o redirecionamento da requisi��o
		RequestDispatcher dispatcher = null;
					
		// Busca pelo usu�rio no banco de dados
		Usuario usuario = new UsuarioDAO().buscaUsuarioPorLogin(new Usuario(nomeusuario, senha));
				
		// Caso o usu�rio N�O seja encontrado no banco de dados
		if(usuario == null){
			
			// Destino do redirecionamento da requisi��o
			dispatcher = req.getRequestDispatcher("controller?action=index");
			
			// Atribuindo uma mensagem na requisi��o
			req.setAttribute("mensagem", "Usu�rio n�o encontrado");
		
		// Caso o usu�rio seja encontrado no banco de dados
		}else{
			
			// Caso a senha N�O seja v�lida
			if(!usuario.getSenha().equals(senha)){
				
				// Destino do redirecionamento da requisi��o
				dispatcher = req.getRequestDispatcher("controller?action=index");
				
				// Atribuindo uma mensagem na requisi��o
				req.setAttribute("mensagem", "Senha inv�lida");
			
			// Caso a senha seja v�lida
			}else{
				
				// Inicia uma sess�o de usu�rio
				HttpSession session = req.getSession();
				
				// Atribuindo um cookie de usu�rio para marcar a sess�o
				session.setAttribute("usuarioLogado", usuario);
				
				switch(usuario.getPerfil().getPerfil()){
					case "Administrador":
						dispatcher = req.getRequestDispatcher("controller?action=administracao");
						break;
					case "Comprador":
						dispatcher = req.getRequestDispatcher("controller?action=listarFornecedores");
						break;
					case "Vendedor":
						dispatcher = req.getRequestDispatcher("controller?action=listarClientes");
						break;
					default:
						// USU�RIO N�O POSSUI UM PERFIL
						break;
				}
				
			}
			
		}
		
		// Redirecionando a requisi��o
		dispatcher.forward(req, resp);
	}
}
