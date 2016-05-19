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
				
		System.out.println("Controller, INICIO, URI: " + req.getRequestURI());
		System.out.println("Controller, INICIO, ACTION: " + action);		
		
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
			case "compra":
				dispatcher = req.getRequestDispatcher("WEB-INF/paginas/pedidocompra.jsp");
				break;
			case "venda":
				dispatcher = req.getRequestDispatcher("WEB-INF/paginas/pedidovenda.jsp");
				break;
			//case "contas":
				//dispatcher = req.getRequestDispatcher("WEB-INF/paginas/contas.jsp");
				//break;
			case "caixa":
				dispatcher = req.getRequestDispatcher("WEB-INF/paginas/caixa.jsp");
				break;
			case "produtos":
				dispatcher = req.getRequestDispatcher("WEB-INF/paginas/precos.jsp");
				break;
			case "listarProdutos":
				dispatcher = req.getRequestDispatcher("listarProdutos");
				break;
			case "pesquisarProduto":
				dispatcher = req.getRequestDispatcher("pesquisarProduto");
				break;
			case "cadastrarProduto":
				dispatcher = req.getRequestDispatcher("cadastrarProduto");
				break;
			case "atualizarProduto":
				dispatcher = req.getRequestDispatcher("atualizarProduto");
				break;
			case "excluirProduto":
				dispatcher = req.getRequestDispatcher("excluirProduto");
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
			case "separaEnderecoCliente":
				dispatcher = req.getRequestDispatcher("separaEnderecoCliente");
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
			case "separaEnderecoFornecedor":
				dispatcher = req.getRequestDispatcher("separaEnderecoFornecedor");
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
			case "pegaPerfil":
				dispatcher = req.getRequestDispatcher("pegaPerfil");				
				break;
			case "listarPedidoCompra":
				dispatcher = req.getRequestDispatcher("listarPedidoCompra");
				break;
			case "pesquisarPedidoCompra":
				dispatcher = req.getRequestDispatcher("pesquisarPedidoCompra");
				break;
			case "cadastrarPedidoCompra":
				dispatcher = req.getRequestDispatcher("cadastrarPedidoCompra");
				break;
			case "atualizarPedidoCompra":
				dispatcher = req.getRequestDispatcher("atualizarPedidoCompra");
				break;
			case "excluirPedidoCompra":
				dispatcher = req.getRequestDispatcher("excluirPedidoCompra");
				break;
			case "listarPedidoVenda":
				dispatcher = req.getRequestDispatcher("listarPedidoVenda");
				break;
			case "pesquisarPedidoVenda":
				dispatcher = req.getRequestDispatcher("pesquisarPedidoVenda");
				break;
			case "cadastrarPedidoVenda":
				dispatcher = req.getRequestDispatcher("cadastrarPedidoVenda");
				break;
			case "atualizarPedidoVenda":
				dispatcher = req.getRequestDispatcher("atualizarPedidoVenda");
				break;
			case "excluirPedidoVenda":
				dispatcher = req.getRequestDispatcher("excluirPedidoVenda");
				break;
			case "listarCaixa":
				dispatcher = req.getRequestDispatcher("listarCaixa");
				break;
			case "pesquisarCaixa":
				dispatcher = req.getRequestDispatcher("pesquisarCaixa");
				break;
			case "cadastrarCaixa":
				dispatcher = req.getRequestDispatcher("cadastrarCaixa");
				break;
			case "atualizarCaixa":
				dispatcher = req.getRequestDispatcher("atualizarCaixa");
				break;
			case "excluirCaixa":
				dispatcher = req.getRequestDispatcher("excluirCaixa");
				break;
			default:
				dispatcher = req.getRequestDispatcher("WEB-INF/paginas/error.jsp");
				break;
		}
		
		System.out.println("Controller, FIM, URI: " + req.getRequestURI());
		System.out.println("Controller, FIM, ACTION: " + action);
		
		dispatcher.forward(req, resp);
	}
}
