package br.com.mdjpapeis.model;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import br.com.mdjpapeis.dao.UsuarioDAO;
import br.com.mdjpapeis.entity.Usuario;

@WebServlet(urlPatterns = "/login")
public class Login extends HttpServlet{

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		// Recebendo os dados enviados na requisição
		String nomeusuario = req.getParameter("nomeusuario");
		String senha = req.getParameter("senha");
		
		// Utilizado para o redirecionamento da requisição
		RequestDispatcher dispatcher = null;
					
		// Busca pelo usuário no banco de dados
		Usuario usuario = new UsuarioDAO().buscaUsuarioPorLogin(new Usuario(nomeusuario, senha));
				
		// Caso o usuário NÃO seja encontrado no banco de dados
		if(usuario == null){
			
			// Destino do redirecionamento da requisição
			dispatcher = req.getRequestDispatcher("controller?action=index");
			
			// Atribuindo uma mensagem na requisição
			req.setAttribute("mensagem", "Usuário ou senha inválidos");
		
		// Caso o usuário seja encontrado no banco de dados
		}else{
			
			// Caso a senha NÃO seja válida
			if(!usuario.getSenha().equals(senha)){
				
				// Destino do redirecionamento da requisição
				dispatcher = req.getRequestDispatcher("controller?action=index");
				
				// Atribuindo uma mensagem na requisição
				req.setAttribute("mensagem", "Usuário ou senha inválidos");
			
			// Caso a senha seja válida
			}else{
				
				// Inicia uma sessão de usuário
				HttpSession session = req.getSession();
				
				// Atribuindo um cookie de usuário para marcar a sessão
				session.setAttribute("usuarioLogado", usuario);
				
				switch(usuario.getPerfil().toString().toUpperCase()){
					case "ADMINISTRADOR":
						dispatcher = req.getRequestDispatcher("controller?action=administracao");
						break;
					case "COMPRADOR":
						dispatcher = req.getRequestDispatcher("controller?action=listarFornecedores");
						break;
					case "VENDEDOR":
						dispatcher = req.getRequestDispatcher("controller?action=listarClientes");
						break;
					default:
						// USUÁRIO NÃO POSSUI UM PERFIL
						break;
				}
				
			}
			
		}
		
		// Redirecionando a requisição
		dispatcher.forward(req, resp);
	}
}
