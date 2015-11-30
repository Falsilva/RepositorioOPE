package br.com.mdjpapeis.filtro;

import java.io.IOException;

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

@WebFilter(urlPatterns = "/*")
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

		// Recupera a sessão e obtém o usuário
		Usuario usuario = (Usuario) req.getSession().getAttribute("usuarioLogado");
		
		System.out.println("Filtro INICIO, URI...............: " + req.getRequestURI());	
		
		RequestDispatcher dispatcher = null;
		
		// Caso a REQUISIÇÃO (gerada pelas páginas com o "link" para a página de estilos) seja DIFERENTE da página de estilos
		if(!req.getRequestURI().equals("/GFacil/resources/css/estilos.css")){			
		
			// Caso exista USUÁRIO LOGADO
			if (usuario != null) {
				
				System.out.println("Filtro, USUARIO NOME........: " + usuario.getNome());
				System.out.println("Filtro, USUARIO PERFIL......: " + usuario.getPerfil().getPerfil());
				System.out.println("Filtro, ACTION..............: " + req.getParameter("action"));
				System.out.println("Filtro, TAREFA..............: " + req.getParameter("tarefa"));
				
				// Caso a requisição de algum usuário LOGADO seja para a tela de login (index.jsp), então a requisição redirecionada para outra tela
				if(req.getRequestURI().equals("/GFacil/") || req.getRequestURI().equals("/GFacil/index.jsp") || (req.getRequestURI().equals("/GFacil/controller") & (req.getParameter("action").equals("index") || req.getParameter("action").equals("login")))){
							
					// Redireciona para a tela principal dependendo do PERFIL do usuário
					switch (usuario.getPerfil().getPerfil()){
					
						case "Administrador":
							
							// Recebe o destino do redirecionamento da requisição
							dispatcher = req.getRequestDispatcher("controller?action=administracao");
							
							System.out.println("Filtro REDIRECIONAMENTO, URI: " + req.getRequestURI());
							System.out.println("Filtro, ACTION..............: " + req.getParameter("action"));
							
							// Reencaminha a requisição
							dispatcher.forward(req, resp);
							
							break;
							
						case "Comprador":
							
							// Recebe o destino do redirecionamento da requisição
							dispatcher = req.getRequestDispatcher("controller?action=listarFornecedores");
							
							System.out.println("Filtro REDIRECIONAMENTO, URI: " + req.getRequestURI());
							System.out.println("Filtro, ACTION..............: " + req.getParameter("action"));
							
							// Reencaminha a requisição
							dispatcher.forward(req, resp);
							
							break;
							
						case "Vendedor":
							
							// Recebe o destino do redirecionamento da requisição
							dispatcher = req.getRequestDispatcher("controller?action=listarClientes");
							
							System.out.println("Filtro REDIRECIONAMENTO, URI: " + req.getRequestURI());
							System.out.println("Filtro, ACTION..............: " + req.getParameter("action"));
							
							// Reencaminha a requisição
							dispatcher.forward(req, resp);
							
							break;
							
						default:
							break;
					}
				}
				
			// Caso contrário, o USUÁRIO ESTÁ DESLOGADO
			}else{
			
				System.out.println("Filtro, USUARIO DESLOGADO, ACTION: " + req.getParameter("action"));
				
				// Caso a requisição de algum usuário DESLOGADO seja DIFERENTE da tela de login (index.jsp), então a requisição redirecionada para tela de login
				if(!req.getRequestURI().equals("/GFacil/") & !req.getRequestURI().equals("/GFacil/index.jsp")){
						
					String action = req.getParameter("action");
						
					if(action == null || (!action.equals("login") & !action.equals("index"))){
						
						// Recebe o destino do redirecionamento da requisição
						dispatcher = req.getRequestDispatcher("index.jsp");
						
						System.out.println("Filtro REDIRECIONAMENTO, URI: " + req.getRequestURI());
						System.out.println("Filtro, ACTION..............: " + req.getParameter("action"));
						
						// Reencaminha a requisição
						dispatcher.forward(req, resp);
					}
				}				
			}
		}
		
		System.out.println("Filtro FIM, URI..................: " + req.getRequestURI());		
		
		// Continua com o encaminhamento da requisição
		chain.doFilter(req, resp);
	}
}
