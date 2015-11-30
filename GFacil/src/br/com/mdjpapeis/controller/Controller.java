package br.com.mdjpapeis.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import br.com.mdjpapeis.entity.Usuario;

@WebServlet(urlPatterns = "/controller")
public class Controller extends HttpServlet{

	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		String action = (String) req.getParameter("action");
		RequestDispatcher dispatcher = null;
				
		switch(action){
			case "index":
				dispatcher = req.getRequestDispatcher("index.jsp");
				break;
			case "login":
				dispatcher = req.getRequestDispatcher("login");
				break;
			case "logout":
				dispatcher = req.getRequestDispatcher("logout");
				break;			
			case "administracao":
				dispatcher = req.getRequestDispatcher("WEB-INF/paginas/administracao.jsp");
				break;
			case "clientes":				
				dispatcher = req.getRequestDispatcher("WEB-INF/paginas/cadclientes.jsp");
				break;
			case "fornecedores":				
				dispatcher = req.getRequestDispatcher("WEB-INF/paginas/cadfornecedores.jsp");
				break;
			case "usuarios":
				dispatcher = req.getRequestDispatcher("WEB-INF/paginas/cadusuarios.jsp");
				break;			
			case "formCliente":
				dispatcher = req.getRequestDispatcher("WEB-INF/paginas/formCliente.jsp");
				break;
			case "formFornecedor":
				dispatcher = req.getRequestDispatcher("WEB-INF/paginas/formFornecedor.jsp");
				break;
			case "formUsuario":				
				dispatcher = req.getRequestDispatcher("WEB-INF/paginas/formUsuario.jsp");
				break;
			case "listarClientes":
				dispatcher = req.getRequestDispatcher("listarClientes");
				break;
			case "pesquisarCliente":
				dispatcher = req.getRequestDispatcher("pesquisarCliente");
				break;
			case "cadastrarCliente":
				dispatcher = req.getRequestDispatcher("cadastrarCliente");
				break;
			case "atualizarCliente":
				dispatcher = req.getRequestDispatcher("atualizarCliente");
				break;
			case "excluirCliente":
				dispatcher = req.getRequestDispatcher("excluirCliente");
				break;
			case "listarFornecedores":
				dispatcher = req.getRequestDispatcher("listarFornecedores");
				break;
			case "pesquisarFornecedor":
				dispatcher = req.getRequestDispatcher("pesquisarFornecedor");
				break;
			case "cadastrarFornecedor":
				dispatcher = req.getRequestDispatcher("cadastrarFornecedor");
				break;
			case "atualizarFornecedor":
				dispatcher = req.getRequestDispatcher("atualizarFornecedor");
				break;
			case "excluirFornecedor":
				dispatcher = req.getRequestDispatcher("excluirFornecedor");
				break;
			case "listarUsuarios":
				dispatcher = req.getRequestDispatcher("listarUsuarios");
				break;
			case "pesquisarUsuario":
				dispatcher = req.getRequestDispatcher("pesquisarUsuario");
				break;
			case "cadastrarUsuario":
				dispatcher = req.getRequestDispatcher("cadastrarUsuario");
				break;
			case "atualizarUsuario":
				dispatcher = req.getRequestDispatcher("atualizarUsuario");
				break;
			case "excluirUsuario":
				dispatcher = req.getRequestDispatcher("excluirUsuario");
				break;
			default:
				dispatcher = req.getRequestDispatcher("WEB-INF/paginas/error.jsp");
				break;
		}
		
		dispatcher.forward(req, resp);
	}
}
