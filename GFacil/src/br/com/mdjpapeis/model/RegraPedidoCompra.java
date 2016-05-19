package br.com.mdjpapeis.model;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

import javax.persistence.PersistenceException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.mdjpapeis.dao.ProdutoDAO;
import br.com.mdjpapeis.entity.Produto;

@WebServlet(urlPatterns = {"/listarPedidoCompra", "/pesquisarPedidoCompra", "/cadastrarPedidoCompra", "/atualizarPedidoCompra", "/excluirPedidoCompra"})
public class RegraPedidoCompra extends HttpServlet {

	private static final long serialVersionUID = 1L;
	
	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) 
			throws ServletException, IOException {
		
		System.out.println("RegraPedidoCompra - ACTION: " + req.getParameter("action"));
		
		long codigo = 0;
		String strProduto = null;
		String strPrecoCompra = null;
		String strPrecoVenda = null;
		
		double dblPrecoCompra = 0;
		double dblPrecoVenda = 0;
		BigDecimal precoCompra = null;
		BigDecimal precoVenda = null;
		
		Produto produto = new Produto();
		Produto prod = null;
		List<Produto> produtos = null;	
		Produto verificaProduto = null;
		
		String[][] parametros = null;
		String tarefa = req.getParameter("tarefa");
		boolean codigoInvalido = false;
		RequestDispatcher dispatcher = null;
		
		switch(req.getParameter("action")){
			case("listarPedidoCompra"):
				System.out.println("RegraPedidoCompra, LISTANDO PRODUTOS...");
				produtos = new ProdutoDAO().listar();				
				if(produtos != null){
					System.out.println("RegraPedidoCompra, PRODUTOS LISTADOS.");
					req.setAttribute("produtos", produtos);
				}else{
					System.out.println("RegraPedidoCompra, NAO HA PRODUTOS CADASTRADOS.");
					req.setAttribute("mensagem", "Não há produtos cadastrados");
				}				
				dispatcher = req.getRequestDispatcher("controller?action=produtos");
				dispatcher.forward(req, resp);
				break;
			case("cadastrarPedidoCompra"):
				System.out.println("RegraPedidoCompra, CADASTRANDO PRODUTO...");
				strProduto = req.getParameter("produto");
				strPrecoCompra = req.getParameter("precoCompra");
				strPrecoVenda = req.getParameter("precoVenda");
				
				parametros = new String[][]{{"produto", strProduto}, {"precoCompra", strPrecoCompra}, {"precoVenda", strPrecoVenda}};
				
				prod = new Produto();
				
				System.out.println("RegraPedidoCompra, CADASTRANDO PRODUTO, VERIFICANDO O RECEBIMENTO DOS DADOS...");
				for(int i = 0; i < parametros.length; i++){
					if(parametros[i][1] == null | parametros[i][1].isEmpty()){						
						parametros[i][1] = "";
						System.out.println("RegraPedidoCompra, CADASTRANDO PRODUTO, CAMPO NAO PREENCHIDO: " + parametros[i][0]);
						resp.setContentType("text/plain");
						resp.setCharacterEncoding("UTF-8");
						resp.getWriter().write("Preencha todos os campos.");
						break;
					}
					
					switch(parametros[i][0]){
						case "produto":
							prod.setProduto(parametros[i][1]);
							break;
						case "precoCompra":
							try{
								dblPrecoCompra = Double.parseDouble(parametros[i][1]);
								precoCompra = new BigDecimal(dblPrecoCompra);
								prod.setPrecoCompra(precoCompra);
							}catch(NumberFormatException e){
								System.out.println("RegraPedidoCompra, CADASTRANDO PRODUTO, NAO E NUMERO.");
								resp.setContentType("text/plain");
								resp.setCharacterEncoding("UTF-8");
								resp.getWriter().write("Informe somente números!");
								e.printStackTrace();
							}														
							break;
						case "precoVenda":
							try{
								dblPrecoVenda = Double.parseDouble(parametros[i][1]);
								precoVenda = new BigDecimal(dblPrecoVenda);
								prod.setPrecoVenda(precoVenda);
							}catch(NumberFormatException e){
								System.out.println("RegraPedidoCompra, CADASTRANDO PRODUTO, NAO E NUMERO.");
								resp.setContentType("text/plain");
								resp.setCharacterEncoding("UTF-8");
								resp.getWriter().write("Informe somente números!");
								e.printStackTrace();
							}														
							break;
					}
				}
				
				System.out.println("RegraPedidoCompra, CADASTRANDO PRODUTO, DADOS VERIFICADOS, CAMPOS PREENCHIDOS...");
				try{
					System.out.println("RegraPedidoCompra, CADASTRANDO PRODUTO, CONSULTANDO A EXISTENCIA DE PRODUTO PELO CODIGO...");
												
					// Busca por produto pelo produto informado
					verificaProduto = new ProdutoDAO().buscaProdutoPorProduto(prod);
					
					if(verificaProduto != null){
						System.out.println("RegraPedidoCompra, CADASTRANDO PRODUTO, PRODUTO JA CADASTRADO COM ESSE NOME...");
						resp.setContentType("text/plain");
						resp.setCharacterEncoding("UTF-8");
						resp.getWriter().write("Já existe o produto informado.");
					}else{
						System.out.println("RegraPedidoCompra, CADASTRANDO PRODUTO, PRODUTO NAO ENCONTRADO, CADASTRANDO...");
						new ProdutoDAO().inserir(prod);
						System.out.println("RegraPedidoCompra, CADASTRANDO PRODUTO, PRODUTO CADASTRADO.");
						resp.setContentType("text/plain");
						resp.setCharacterEncoding("UTF-8");
						resp.getWriter().write("Produto cadastrado com sucesso!");
					}
					
				}catch(PersistenceException e){
					System.out.println("RegraPedidoCompra, CADASTRANDO PRODUTO, FALHA AO CADASTRAR, PersistenceException.");
					resp.setContentType("text/plain");
					resp.setCharacterEncoding("UTF-8");
					resp.getWriter().write("Falha ao cadastrar o produto.");							
				}
				break;
			case "atualizarPedidoCompra":
				System.out.println("RegraPedidoCompra, ATUALIZANDO PRODUTO...");				
				strProduto = req.getParameter("produto");
				strPrecoCompra = req.getParameter("precoCompra");
				strPrecoVenda = req.getParameter("precoVenda");
				
				parametros = new String[][]{{"produto", strProduto}, {"precoCompra", strPrecoCompra}, {"precoVenda", strPrecoVenda}};
				
				prod = new Produto();
				
				System.out.println("RegraPedidoCompra, ATUALIZANDO PRODUTO, VERIFICANDO O RECEBIMENTO DOS DADOS...");
				for(int i = 0; i < parametros.length; i++){
					if(parametros[i][1] == null | parametros[i][1].isEmpty()){						
						parametros[i][1] = "";
						System.out.println("RegraPedidoCompra, ATUALIZANDO PRODUTO, CAMPO NAO PREENCHIDO: " + parametros[i][0]);
						resp.setContentType("text/plain");
						resp.setCharacterEncoding("UTF-8");
						resp.getWriter().write("Preencha todos os campos.");
						break;
					}
					
					switch(parametros[i][0]){
						case "produto":							
							prod.setProduto(parametros[i][1]);
							break;
						case "precoCompra":							
							try{
								dblPrecoCompra = Double.parseDouble(parametros[i][1]);
								precoCompra = new BigDecimal(dblPrecoCompra);
								prod.setPrecoCompra(precoCompra);
							}catch(NumberFormatException e){
								System.out.println("RegraPedidoCompra, CADASTRANDO PRODUTO, NAO E NUMERO.");
								resp.setContentType("text/plain");
								resp.setCharacterEncoding("UTF-8");
								resp.getWriter().write("Informe somente números!");
								e.printStackTrace();
							}														
							break;
						case "precoVenda":							
							try{
								dblPrecoVenda = Double.parseDouble(parametros[i][1]);
								precoVenda = new BigDecimal(dblPrecoVenda);
								prod.setPrecoVenda(precoVenda);
							}catch(NumberFormatException e){
								System.out.println("RegraPedidoCompra, CADASTRANDO PRODUTO, NAO E NUMERO.");
								resp.setContentType("text/plain");
								resp.setCharacterEncoding("UTF-8");
								resp.getWriter().write("Informe somente números!");
								e.printStackTrace();
							}														
							break;
					}
				}
				
				if(req.getParameter("codigo") != null && !req.getParameter("codigo").isEmpty()){
					try{
						System.out.println("RegraPedidoCompra, ATUALIZANDO PRODUTO, CONVERTENDO DADOS.");
						codigo = Long.parseLong(req.getParameter("codigo"));
						System.out.println("RegraPedidoCompra, ATUALIZANDO PRODUTO, DADOS CONVERTIDOS." + codigo);
												
						prod.setCodigo(codigo);
						
						System.out.println("RegraPedidoCompra, ATUALIZANDO PRODUTO, DADOS VERIFICADOS, CAMPOS PREENCHIDOS...");
						new ProdutoDAO().atualizar(prod);
						System.out.println("RegraProdutos, ATUALIZANDO PRODUTO, PRODUTO ATUALIZADO.");
						resp.setContentType("text/plain");
						resp.setCharacterEncoding("UTF-8");
						resp.getWriter().write("Produto atualizado com sucesso!");
					}catch(NumberFormatException e){
						System.out.println("RegraPedidoCompra, ATUALIZANDO PRODUTO, DADOS NAO FORMATADOS, CODIGO INVALIDO, NumberFormatException.");						
						e.printStackTrace();
					}catch(PersistenceException e){
						System.out.println("RegraPedidoCompra, ATUALIZANDO PRODUTO, FALHA AO ATUALIZAR, PersistenceException.");
						resp.setContentType("text/plain");
						resp.setCharacterEncoding("UTF-8");
						resp.getWriter().write("Falha ao atualizar o produto.");
						e.printStackTrace();
					}
				}else{
					System.out.println("RegraPedidoCompra, ATUALIZANDO PRODUTO, DADOS NAO RECEBIDOS, Codigo NULO ou VAZIO.");					
					resp.setContentType("text/plain");
					resp.setCharacterEncoding("UTF-8");
					resp.getWriter().write("Desculpe, falha ao atualizar, cód. produto inválido.");
				}
				break;
			case("excluirPedidoCompra"):
				System.out.println("RegraPedidoCompra, EXCLUINDO PRODUTO...");			
				System.out.println("RegraPedidoCompra, EXCLUINDO PRODUTO, VERIFICANDO O RECEBIMENTO DE DADOS...");
				
				// A EXCLUSÃO É FEITA PELA "PRIMARY KEY", OU SEJA, PELO ATRIBUTO "CODIGO"				
				if(req.getParameter("codigo") != null && !req.getParameter("codigo").isEmpty()){
					System.out.println("RegraPedidoCompra, EXCLUINDO PRODUTO, DADOS RECEBIDOS.");
					try{
						System.out.println("RegraPedidoCompra, EXCLUINDO PRODUTO, CONVERTENDO DADOS.");
						codigo = Long.parseLong(req.getParameter("codigo"));
						System.out.println("RegraPedidoCompra, EXCLUINDO PRODUTO, DADOS CONVERTIDOS." + codigo);
						
						prod = new Produto();
						prod.setCodigo(codigo);
						
						// Realiza a exclusão
						new ProdutoDAO().excluir(prod);
						System.out.println("RegraPedidoCompra, EXCLUINDO PRODUTO, EXCLUSAO REALIZADA.");						
						resp.setContentType("text/plain");
						resp.setCharacterEncoding("UTF-8");
						resp.getWriter().write("Exclusão realizada com sucesso!");
						
					}catch(NumberFormatException e){
						System.out.println("RegraPedidoCompra, EXCLUINDO PRODUTO, DADOS NAO FORMATADOS, CODIGO INVALIDO, NumberFormatException.");
						e.printStackTrace();
						resp.setContentType("text/plain");
						resp.setCharacterEncoding("UTF-8");
						resp.getWriter().write("Código inválido.");
					}catch(PersistenceException e){
						System.out.println("RegraPedidoCompra, EXCLUINDO PRODUTO, FALHA AO EXCLUIR: PersistenceException");						
						resp.setContentType("text/plain");
						resp.setCharacterEncoding("UTF-8");
						resp.getWriter().write("Falha ao excluir o produto.");
					}
				}else{
					System.out.println("RegraPedidoCompra, EXCLUINDO PRODUTO, DADOS NAO RECEBIDOS, Codigo NULO ou VAZIO.");					
					resp.setContentType("text/plain");
					resp.setCharacterEncoding("UTF-8");
					resp.getWriter().write("Informe o Código.");
				}			
				break;
		}
		
	}

}
