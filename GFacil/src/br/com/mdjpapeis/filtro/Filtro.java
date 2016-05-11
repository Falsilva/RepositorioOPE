package br.com.mdjpapeis.filtro;

import java.io.IOException;

import javax.servlet.DispatcherType;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.mdjpapeis.entity.Usuario;

@WebFilter(urlPatterns = {
		"/index.jsp",
		"/controller",
		"/listarClientes", 
		"/pesquisarCliente", 
		"/cadastrarCliente", 
		"/atualizarCliente", 
		"/excluirCliente",
		"/listarFornecedores", 
		"/pesquisarFornecedor", 
		"/cadastrarFornecedor", 
		"/atualizarFornecedor", 
		"/excluirFornecedor",
		"/listarUsuarios", 
		"/pesquisarUsuario", 
		"/cadastrarUsuario", 
		"/atualizarUsuario", 
		"/excluirUsuario",
		"/pegaPerfil",
		"/separaEnderecoCliente",
		"/separaEnderecoFornecedor"
		})
public class Filtro implements Filter {

	@Override
	public void destroy() {
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {

		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse resp = (HttpServletResponse) response;

		// Recupera a sess�o e obt�m o usu�rio
		Usuario usuario = (Usuario) req.getSession().getAttribute("usuarioLogado");
		
		System.out.println("Filtro INICIO, URI...............: " + req.getRequestURI());	
		
		RequestDispatcher dispatcher = null;		
		
		// USU�RIO LOGADO
		if (usuario != null) {				
			System.out.println("Filtro, USUARIO NOME.............: " + usuario.getNome());
			System.out.println("Filtro, USUARIO PERFIL...........: " + usuario.getPerfil().toString());
			System.out.println("Filtro, ACTION...................: " + req.getParameter("action"));
			System.out.println("Filtro, TAREFA...................: " + req.getParameter("tarefa"));
				
			// Caso a requisi��o de algum usu�rio LOGADO seja para a tela de login (index.jsp), ent�o a requisi��o redirecionada para outra tela
			if(req.getRequestURI().equals("/GFacil/") || req.getRequestURI().equals("/GFacil/index.jsp") || (req.getRequestURI().equals("/GFacil/controller") & (req.getParameter("action").equals("index") || req.getParameter("action").equals("login")))){
							
				// Redireciona para a tela principal dependendo do PERFIL do usu�rio
				switch (usuario.getPerfil().toString()){
					
					case "ADMINISTRADOR":
							
						// Recebe o destino do redirecionamento da requisi��o
						dispatcher = req.getRequestDispatcher("controller?action=administracao");
							
						System.out.println("Filtro REDIRECIONAMENTO, URI.....: " + req.getRequestURI());
						System.out.println("Filtro, ACTION...................: " + req.getParameter("action"));
							
						// Reencaminha a requisi��o
						dispatcher.forward(req, resp);
							
						break;
							
					case "COMPRADOR":
							
						// Recebe o destino do redirecionamento da requisi��o
						dispatcher = req.getRequestDispatcher("controller?action=listarFornecedores");
							
						System.out.println("Filtro REDIRECIONAMENTO, URI.....: " + req.getRequestURI());
						System.out.println("Filtro, ACTION...................: " + req.getParameter("action"));
							
						// Reencaminha a requisi��o
						dispatcher.forward(req, resp);
							
						break;
							
					case "VENDEDOR":
							
						// Recebe o destino do redirecionamento da requisi��o
						dispatcher = req.getRequestDispatcher("controller?action=listarClientes");
							
						System.out.println("Filtro REDIRECIONAMENTO, URI.....: " + req.getRequestURI());
						System.out.println("Filtro, ACTION...................: " + req.getParameter("action"));
							
						// Reencaminha a requisi��o
						dispatcher.forward(req, resp);
							
						break;
							
					default:
						break;
				}
			}
				
		// USU�RIO N�O LOGADO
		}else{
			
			System.out.println("Filtro, USUARIO DESLOGADO, ACTION: " + req.getParameter("action"));
				
			// Caso a requisi��o de algum usu�rio DESLOGADO seja DIFERENTE da tela de login (index.jsp), ent�o a requisi��o redirecionada para tela de login
			if(!req.getRequestURI().equals("/GFacil/") & !req.getRequestURI().equals("/GFacil/index.jsp")){
						
				String action = req.getParameter("action");
						
				if(action == null || (!action.equals("login") & !action.equals("index"))){
						
					// Recebe o destino do redirecionamento da requisi��o
					dispatcher = req.getRequestDispatcher("index.jsp");
						
					System.out.println("Filtro REDIRECIONAMENTO, URI.....: " + req.getRequestURI());						
						
					// Reencaminha a requisi��o
					dispatcher.forward(req, resp);
				}
			}				
		}
		
		System.out.println("Filtro FIM, URI..................: " + req.getRequestURI());		
		
		// Continua com o encaminhamento da requisi��o
		chain.doFilter(req, resp);
	}
}
