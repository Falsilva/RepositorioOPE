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

@WebServlet(urlPatterns = "/logout")
public class Logout extends HttpServlet {

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		// Destino do redirecionamento da requisi��o
		RequestDispatcher dispatcher = req.getRequestDispatcher("index.jsp");
				
		try{
			
			// Invalida a sess�o
			HttpSession sessao = req.getSession();
			sessao.invalidate();
			
			// Atribuindo uma mensagem na requisi��o
			req.setAttribute("mensagem", "Usu�rio deslogado com sucesso");			
			
		
		// Caso N�O haja uma sess�o aberta e por algum acesso incomum do logout
		}catch(IllegalStateException ex){
			
			// Atribuindo uma mensagem na requisi��o
			req.setAttribute("mensagem", "O usu�rio j� est� deslogado.");
		}		
		
		// Redirecionando a requisi��o
		dispatcher.forward(req, resp);
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		// Destino do redirecionamento da requisi��o
		RequestDispatcher dispatcher = req.getRequestDispatcher("index.jsp");
		
		// Redirecionando a requisi��o
		dispatcher.forward(req, resp);
	}
}
