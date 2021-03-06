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
import br.com.mdjpapeis.entity.Fornecedor;
import br.com.mdjpapeis.entity.Produto;

@WebServlet(urlPatterns = {"/listarProdutos", "/pesquisarProduto", "/cadastrarProduto", "/atualizarProduto", "/excluirProduto"})
public class RegraProdutos extends HttpServlet {

	private static final long serialVersionUID = 1L;
	
	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) 
			throws ServletException, IOException {		
		
		System.out.println("RegraProdutos - ACTION: " + req.getParameter("action"));
		
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
			case("listarProdutos"):
				System.out.println("RegraProdutos, LISTANDO PRODUTOS...");
				produtos = new ProdutoDAO().listar();
				
				if(tarefa != null && tarefa.equals("pedido")){						
					
					// Montando o JSON
					String dataListaProdutos = "";
					String arrayProdutos = "";
					
					if(produtos != null){
						int count = 1;
						for(Produto p : produtos){
							if(count == 1){
								arrayProdutos += "{"			
										+ "\"codigo\":\"" + p.getCodigo() + "\","
										+ "\"produto\":\"" + p.getProduto() + "\","
										+ "\"precoCompra\":\"" + p.getPrecoCompra() + "\","
										+ "\"precoVenda\":\"" + p.getPrecoVenda() + "\""
										+ "}";
								count = 2;
							}else{
								arrayProdutos += ", " + "{"			
										+ "\"codigo\":\"" + p.getCodigo() + "\","
										+ "\"produto\":\"" + p.getProduto() + "\","
										+ "\"precoCompra\":\"" + p.getPrecoCompra() + "\","
										+ "\"precoVenda\":\"" + p.getPrecoVenda() + "\""
										+ "}";
							}
						}					
						dataListaProdutos = "{\"dataListaProdutos\":[" + arrayProdutos + "]}";
					}else{
						dataListaProdutos = "{\"dataListaProdutos\":\"null\"}";
					}
					resp.setContentType("application/json");				
					resp.setCharacterEncoding("UTF-8");
					resp.getWriter().write(dataListaProdutos);
				}else{
					if(produtos != null){
						System.out.println("RegraProdutos, PRODUTOS LISTADOS.");
						req.setAttribute("produtos", produtos);
					}else{
						System.out.println("RegraProdutos, NAO HA PRODUTOS CADASTRADOS.");
						req.setAttribute("mensagem", "N�o h� produtos cadastrados");
					}
					dispatcher = req.getRequestDispatcher("controller?action=produtos");
					dispatcher.forward(req, resp);
				}
				break;
			case("cadastrarProduto"):
				System.out.println("RegraProdutos, CADASTRANDO PRODUTO...");
				strProduto = req.getParameter("produto");
				strPrecoCompra = req.getParameter("precoCompra");
				strPrecoVenda = req.getParameter("precoVenda");
				
				parametros = new String[][]{{"produto", strProduto}, {"precoCompra", strPrecoCompra}, {"precoVenda", strPrecoVenda}};
				
				prod = new Produto();
				
				System.out.println("RegraProdutos, CADASTRANDO PRODUTO, VERIFICANDO O RECEBIMENTO DOS DADOS...");
				for(int i = 0; i < parametros.length; i++){
					if(parametros[i][1] == null | parametros[i][1].isEmpty()){						
						parametros[i][1] = "";
						System.out.println("RegraProdutos, CADASTRANDO PRODUTO, CAMPO NAO PREENCHIDO: " + parametros[i][0]);
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
								System.out.println("RegraProdutos, CADASTRANDO PRODUTO, NAO E NUMERO.");
								resp.setContentType("text/plain");
								resp.setCharacterEncoding("UTF-8");
								resp.getWriter().write("Informe somente n�meros!");
								e.printStackTrace();
							}														
							break;
						case "precoVenda":
							try{
								dblPrecoVenda = Double.parseDouble(parametros[i][1]);
								precoVenda = new BigDecimal(dblPrecoVenda);
								prod.setPrecoVenda(precoVenda);
							}catch(NumberFormatException e){
								System.out.println("RegraProdutos, CADASTRANDO PRODUTO, NAO E NUMERO.");
								resp.setContentType("text/plain");
								resp.setCharacterEncoding("UTF-8");
								resp.getWriter().write("Informe somente n�meros!");
								e.printStackTrace();
							}														
							break;
					}
				}
				
				System.out.println("RegraProdutos, CADASTRANDO PRODUTO, DADOS VERIFICADOS, CAMPOS PREENCHIDOS...");
				try{
					System.out.println("RegraProdutos, CADASTRANDO PRODUTO, CONSULTANDO A EXISTENCIA DE PRODUTO PELO CODIGO...");
												
					// Busca por produto pelo produto informado
					verificaProduto = new ProdutoDAO().buscaProdutoPorProduto(prod);
					
					if(verificaProduto != null){
						System.out.println("RegraProdutos, CADASTRANDO PRODUTO, PRODUTO JA CADASTRADO COM ESSE NOME...");
						resp.setContentType("text/plain");
						resp.setCharacterEncoding("UTF-8");
						resp.getWriter().write("J� existe o produto informado.");
					}else{
						System.out.println("RegraProdutos, CADASTRANDO PRODUTO, PRODUTO NAO ENCONTRADO, CADASTRANDO...");
						new ProdutoDAO().inserir(prod);
						System.out.println("RegraProdutos, CADASTRANDO PRODUTO, PRODUTO CADASTRADO.");
						resp.setContentType("text/plain");
						resp.setCharacterEncoding("UTF-8");
						resp.getWriter().write("Produto cadastrado com sucesso!");
					}
					
				}catch(PersistenceException e){
					System.out.println("RegraProdutos, CADASTRANDO PRODUTO, FALHA AO CADASTRAR, PersistenceException.");
					resp.setContentType("text/plain");
					resp.setCharacterEncoding("UTF-8");
					resp.getWriter().write("Falha ao cadastrar o produto.");							
				}
				break;
			case "atualizarProduto":
				System.out.println("RegraProdutos, ATUALIZANDO PRODUTO...");				
				strProduto = req.getParameter("produto");
				strPrecoCompra = req.getParameter("precoCompra");
				strPrecoVenda = req.getParameter("precoVenda");
				
				parametros = new String[][]{{"produto", strProduto}, {"precoCompra", strPrecoCompra}, {"precoVenda", strPrecoVenda}};
				
				prod = new Produto();
				
				System.out.println("RegraProdutos, ATUALIZANDO PRODUTO, VERIFICANDO O RECEBIMENTO DOS DADOS...");
				for(int i = 0; i < parametros.length; i++){
					if(parametros[i][1] == null | parametros[i][1].isEmpty()){						
						parametros[i][1] = "";
						System.out.println("RegraProdutos, ATUALIZANDO PRODUTO, CAMPO NAO PREENCHIDO: " + parametros[i][0]);
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
								System.out.println("RegraProdutos, CADASTRANDO PRODUTO, NAO E NUMERO.");
								resp.setContentType("text/plain");
								resp.setCharacterEncoding("UTF-8");
								resp.getWriter().write("Informe somente n�meros!");
								e.printStackTrace();
							}														
							break;
						case "precoVenda":							
							try{
								dblPrecoVenda = Double.parseDouble(parametros[i][1]);
								precoVenda = new BigDecimal(dblPrecoVenda);
								prod.setPrecoVenda(precoVenda);
							}catch(NumberFormatException e){
								System.out.println("RegraProdutos, CADASTRANDO PRODUTO, NAO E NUMERO.");
								resp.setContentType("text/plain");
								resp.setCharacterEncoding("UTF-8");
								resp.getWriter().write("Informe somente n�meros!");
								e.printStackTrace();
							}														
							break;
					}
				}
				
				if(req.getParameter("codigo") != null && !req.getParameter("codigo").isEmpty()){
					try{
						System.out.println("RegraProdutos, ATUALIZANDO PRODUTO, CONVERTENDO DADOS.");
						codigo = Long.parseLong(req.getParameter("codigo"));
						System.out.println("RegraProdutos, ATUALIZANDO PRODUTO, DADOS CONVERTIDOS." + codigo);
												
						prod.setCodigo(codigo);
						
						System.out.println("RegraProdutos, ATUALIZANDO PRODUTO, DADOS VERIFICADOS, CAMPOS PREENCHIDOS...");
						new ProdutoDAO().atualizar(prod);
						System.out.println("RegraProdutos, ATUALIZANDO PRODUTO, PRODUTO ATUALIZADO.");
						resp.setContentType("text/plain");
						resp.setCharacterEncoding("UTF-8");
						resp.getWriter().write("Produto atualizado com sucesso!");
					}catch(NumberFormatException e){
						System.out.println("RegraProdutos, ATUALIZANDO PRODUTO, DADOS NAO FORMATADOS, CODIGO INVALIDO, NumberFormatException.");						
						e.printStackTrace();
					}catch(PersistenceException e){
						System.out.println("RegraProdutos, ATUALIZANDO PRODUTO, FALHA AO ATUALIZAR, PersistenceException.");
						resp.setContentType("text/plain");
						resp.setCharacterEncoding("UTF-8");
						resp.getWriter().write("Falha ao atualizar o produto.");
						e.printStackTrace();
					}
				}else{
					System.out.println("RegraProdutos, ATUALIZANDO PRODUTO, DADOS NAO RECEBIDOS, Codigo NULO ou VAZIO.");					
					resp.setContentType("text/plain");
					resp.setCharacterEncoding("UTF-8");
					resp.getWriter().write("Desculpe, falha ao atualizar, c�d. produto inv�lido.");
				}
				break;
			case("excluirProduto"):
				System.out.println("RegraProdutos, EXCLUINDO PRODUTO...");			
				System.out.println("RegraProdutos, EXCLUINDO PRODUTO, VERIFICANDO O RECEBIMENTO DE DADOS...");
				
				// A EXCLUS�O � FEITA PELA "PRIMARY KEY", OU SEJA, PELO ATRIBUTO "CODIGO"				
				if(req.getParameter("codigo") != null && !req.getParameter("codigo").isEmpty()){
					System.out.println("RegraProdutos, EXCLUINDO PRODUTO, DADOS RECEBIDOS.");
					try{
						System.out.println("RegraProdutos, EXCLUINDO PRODUTO, CONVERTENDO DADOS.");
						codigo = Long.parseLong(req.getParameter("codigo"));
						System.out.println("RegraProdutos, EXCLUINDO PRODUTO, DADOS CONVERTIDOS." + codigo);
						
						prod = new Produto();
						prod.setCodigo(codigo);
						
						// Realiza a exclus�o
						new ProdutoDAO().excluir(prod);
						System.out.println("RegraProdutos, EXCLUINDO PRODUTO, EXCLUSAO REALIZADA.");						
						resp.setContentType("text/plain");
						resp.setCharacterEncoding("UTF-8");
						resp.getWriter().write("Exclus�o realizada com sucesso!");
						
					}catch(NumberFormatException e){
						System.out.println("RegraProdutos, EXCLUINDO PRODUTO, DADOS NAO FORMATADOS, CODIGO INVALIDO, NumberFormatException.");
						e.printStackTrace();
						resp.setContentType("text/plain");
						resp.setCharacterEncoding("UTF-8");
						resp.getWriter().write("C�digo inv�lido.");
					}catch(PersistenceException e){
						System.out.println("RegraProdutos, EXCLUINDO PRODUTO, FALHA AO EXCLUIR: PersistenceException");						
						resp.setContentType("text/plain");
						resp.setCharacterEncoding("UTF-8");
						resp.getWriter().write("Falha ao excluir o produto.");
					}
				}else{
					System.out.println("RegraProdutos, EXCLUINDO PRODUTO, DADOS NAO RECEBIDOS, Codigo NULO ou VAZIO.");					
					resp.setContentType("text/plain");
					resp.setCharacterEncoding("UTF-8");
					resp.getWriter().write("Informe o C�digo.");
				}			
				break;
		}
	}
}
