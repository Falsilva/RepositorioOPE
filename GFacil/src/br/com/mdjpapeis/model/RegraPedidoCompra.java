package br.com.mdjpapeis.model;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.json.Json;
import javax.persistence.PersistenceException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.mdjpapeis.dao.MovimentacaoDAO;
import br.com.mdjpapeis.dao.PedidoCompraDAO;
import br.com.mdjpapeis.dao.ProdutoDAO;
import br.com.mdjpapeis.entity.Fornecedor;
import br.com.mdjpapeis.entity.ItemPedido;
import br.com.mdjpapeis.entity.PedidoCompra;
import br.com.mdjpapeis.entity.PedidoCompra.StatusCompra;
import br.com.mdjpapeis.entity.Produto;

@WebServlet(urlPatterns = {"/listarPedidoCompra", "/pesquisarPedidoCompra", "/cadastrarPedidoCompra", "/atualizarPedidoCompra", "/excluirPedidoCompra"})
public class RegraPedidoCompra extends HttpServlet {

	private static final long serialVersionUID = 1L;
	
	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) 
			throws ServletException, IOException {
		
		System.out.println("RegraPedidoCompra - ACTION: " + req.getParameter("action"));
		
		long nPedido = 0;
		String status = null;		
		String strItens = null;
		String[] arrayItens = null;
		int contador = 0;
		
		double dblPrecoCompra = 0;
		double dblPrecoVenda = 0;
		BigDecimal precoCompra = null;
		BigDecimal precoVenda = null;
		BigDecimal valorTotalPedido = new BigDecimal(0);
		
		long proximoNumero = 0;
		
		PedidoCompra pedidoCompra = null;
		PedidoCompra pedCompra = null;
		List<PedidoCompra> pedidosCompra = null;	
		PedidoCompra verificaPedido = null;
		
		String[][] parametros = null;
		String tarefa = req.getParameter("tarefa");
		boolean codigoInvalido = false;
		RequestDispatcher dispatcher = null;
		
		switch(req.getParameter("action")){
			case("listarPedidoCompra"):
				System.out.println("RegraPedidoCompra, LISTANDO PEDIDOS DE COMPRA...");
				pedidosCompra = new PedidoCompraDAO().listar();				
				if(pedidosCompra != null){
					System.out.println("RegraPedidoCompra, PEDIDOS DE COMPRA LISTADOS.");
					req.setAttribute("pedidosCompra", pedidosCompra);
				}else{
					System.out.println("RegraPedidoCompra, NAO HA PEDIDOS DE COMPRA CADASTRADOS.");
					req.setAttribute("mensagem", "Nenhum pedido de compra cadastrado.");
				}			
				dispatcher = req.getRequestDispatcher("controller?action=compra");
				dispatcher.forward(req, resp);
				break;
			case("pesquisarPedidoCompra"):
				System.out.println("RegraPedidoCompra, PESQUISANDO PEDIDO DE COMPRA...");
				if(tarefa != null && tarefa.equals("proximoNumero")){
					try{
						System.out.println("");
						pedidosCompra = new PedidoCompraDAO().listar();
						for(PedidoCompra ped : pedidosCompra){
							if(pedidosCompra.indexOf(ped) == pedidosCompra.size() - 1){
								proximoNumero = ped.getnPedido() + 1;
							}
						}
					}catch(NullPointerException e){
						proximoNumero = 0;
					}finally{
						resp.setContentType("text/plain");
						resp.setCharacterEncoding("UTF-8");
						resp.getWriter().write("" + proximoNumero);
					}
				}else{			
					System.out.println("RegraPedidoCompra, PESQUISANDO PEDIDO DE COMPRA, VERIFICANDO NO. PEDIDO...");
					
					if(req.getParameter("nPedido") == null || req.getParameter("nPedido").isEmpty()){
						System.out.println("RegraPedidoCompra, PESQUISANDO PEDIDO DE COMPRA, PEDIDO NULO OU VAZIO...");
						dispatcher = req.getRequestDispatcher("controller?action=listarPedidoCompra");
					}else{
						try{
							System.out.println("RegraPedidoCompra, PESQUISANDO PEDIDO DE COMPRA, NO. PEDIDO VERIFICADO: " + req.getParameter("nPedido"));
							nPedido = Long.parseLong(req.getParameter("nPedido"));
							System.out.println("RegraPedidoCompra, PESQUISANDO PEDIDO DE COMPRA, NO. PEDIDO CONVERTIDO: " + nPedido);
							pedidoCompra = new PedidoCompraDAO().buscaPedidoCompraPorNumeroPedido(nPedido);
							System.out.println("RegraPedidoCompra, PESQUISANDO PEDIDO DE COMPRA, RETORNO DO DAO, PEDIDO: " + pedidoCompra);
							if(pedidoCompra == null){								
								req.setAttribute("mensagem", "Pedido não encontrado.");
							}else{
								pedidosCompra = new ArrayList<PedidoCompra>();
								pedidosCompra.add(pedidoCompra);
								req.setAttribute("pedidosCompra", pedidosCompra);
							}
						}catch(NullPointerException e){
							System.out.println("RegraPedidoCompra, PESQUISANDO PEDIDO DE COMPRA, PEDIDO NAO ENCONTRADO: NullPinterException");
							req.setAttribute("mensagem", "Pedido não encontrado.");							
						}catch(NumberFormatException e){
							System.out.println("RegraPedidoCompra, PESQUISANDO PEDIDO DE COMPRA, NO. PEDIDO INVALIDO: NumberFormatException");
							req.setAttribute("mensagem", "Número do Pedido inválido.");							
						}
						System.out.println("RegraPedidoCompra, PESQUISA DO PEDIDO DE COMPRA CONCLUIDO.");
						dispatcher = req.getRequestDispatcher("controller?action=compra");
					}
					dispatcher.forward(req, resp);					
				}
				break;
			case("cadastrarPedidoCompra"):
				System.out.println("RegraPedidoCompra, CADASTRANDO PEDIDO DE COMPRA...");
				
				try{
					if(req.getParameter("itens") != null && !req.getParameter("itens").isEmpty() && req.getParameter("codFornecedor") != null && !req.getParameter("codFornecedor").isEmpty()){
						strItens = req.getParameter("itens").replace("},{", "};{");
						arrayItens = strItens.split(";");
						
						List<ItemPedido> listaItens = new ArrayList<ItemPedido>();
						
						contador = 0;
						for(String strItem : arrayItens){		
							Gson json = new Gson();
							ItemPedido item = json.fromJson(strItem, ItemPedido.class);						
							listaItens.add(item);
							System.out.println("\nITEM:\nCOD_PRODUTO: " + listaItens.get(contador).getProduto().getCodigo() + "\n" 
									+ "PRODUTO....: " + listaItens.get(contador).getProduto().getProduto() + "\n"
									+ "PESO.......: " + listaItens.get(contador).getPeso() + "\n"
									+ "VALOR_ITEM.: " + listaItens.get(contador).getValorItem());
							contador++;
						}
						contador = 0;
						
						Fornecedor fornecedor = new Fornecedor();
						fornecedor.setCodigo(Long.parseLong(req.getParameter("codFornecedor")));
							
						PedidoCompra pc = new PedidoCompra();
						pc.setDataAbertura(Calendar.getInstance());
						pc.setFornecedor(fornecedor);
						pc.setItensPedidoCompra(listaItens);						
						pc.setStatusCompra(StatusCompra.PENDENTE);
						
						System.out.println("  \n" + "PEDIDO.........:\n"
								+ "DATA...........: " + new SimpleDateFormat("dd/MM/yyyy").format(pc.getDataAbertura().getTime()) + "\n"
								+ "COD. FORNECEDOR: " + pc.getFornecedor().getCodigo() + "\n"
								+ "STATUS.........: " + pc.getStatusCompra().toString() + "\n"
								+ "ITENS:");
						System.out.println("\tCOD. PRODUTO  |  PESO  |  VALOR ITEM\n");
						for(ItemPedido icompra : pc.getItensPedidoCompra()){
							System.out.println("  \n" + "\t" + icompra.getProduto().getCodigo() + " | "
									+ icompra.getPeso() + " | "
									+ "R$ " + icompra.getValorItem() + "\n");
							valorTotalPedido = valorTotalPedido.add(icompra.getValorItem());
						}
						
						pc.setValorTotal(valorTotalPedido);
						System.out.println("VALOR DO PEDIDO: R$ " + pc.getValorTotal());
						
						new PedidoCompraDAO().inserir(pc);
						
						resp.setContentType("text/plain");
						resp.setCharacterEncoding("UTF-8");
						resp.getWriter().write("Pedido gerado com sucesso.");						
					}else{
						resp.setContentType("text/plain");
						resp.setCharacterEncoding("UTF-8");
						resp.getWriter().write("Ops! Pedido não cadastrado.");
					}				
				}catch(NullPointerException e){
					System.out.println("RegraPedidoCompra, CADASTRANDO PEDIDO DE COMPRA, FALHA AO CADASTRAR - NullPointerException");
					e.printStackTrace();						
					resp.setContentType("text/plain");
					resp.setCharacterEncoding("UTF-8");
					resp.getWriter().write("Ops! Pedido não cadastrado.");
				}
				break;
				/*
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
				break;*/
			case("excluirPedidoCompra"):
				System.out.println("RegraPedidoCompra, EXCLUINDO PEDIDO DE COMPRA...");			
				System.out.println("RegraPedidoCompra, EXCLUINDO PEDIDO DE COMPRA, VERIFICANDO O RECEBIMENTO DE DADOS...");
				
				// A EXCLUSÃO É FEITA PELA "PRIMARY KEY", OU SEJA, PELO ATRIBUTO "NPEDIDO"				
				if(req.getParameter("nPedido") != null && !req.getParameter("nPedido").isEmpty()
						&& req.getParameter("statusExcluir") != null && !req.getParameter("statusExcluir").isEmpty()){
					System.out.println("RegraPedidoCompra, EXCLUINDO PEDIDO DE COMPRA, DADOS RECEBIDOS.");
					try{
						System.out.println("RegraPedidoCompra, EXCLUINDO PEDIDO DE COMPRA, CONVERTENDO DADOS.");
						nPedido = Long.parseLong(req.getParameter("nPedido"));
						status = req.getParameter("statusExcluir");
						System.out.println("RegraPedidoCompra, EXCLUINDO PEDIDO DE COMPRA, DADOS CONVERTIDOS." + nPedido);
						
						pedCompra = new PedidoCompra();
						pedCompra.setnPedido(nPedido);						
						
						// CASO O STATUS SEJA PAGO, EXCLUI A MOVIMENTACAO RELACIONADA
						if(status.equals(PedidoCompra.StatusCompra.PAGO.toString())){
							System.out.println("RegraPedidoCompra, EXCLUINDO PEDIDO DE COMPRA, EXCLUINDO A MOVIMENTACAO...");
							new MovimentacaoDAO().excluirMovimentacaoCompra(pedCompra);
						}
						
						System.out.println("RegraPedidoCompra, EXCLUINDO PEDIDO DE COMPRA...");
						new PedidoCompraDAO().excluir(pedCompra);
						
						System.out.println("RegraPedidoCompra, EXCLUINDO PEDIDO DE COMPRA, EXCLUSAO REALIZADA.");						
						resp.setContentType("text/plain");
						resp.setCharacterEncoding("UTF-8");
						resp.getWriter().write("Exclusão realizada com sucesso!");
						
					}catch(NumberFormatException e){
						System.out.println("RegraPedidoCompra, EXCLUINDO PEDIDO DE COMPRA, DADOS NAO FORMATADOS, NUMERO PEDIDO INVALIDO, NumberFormatException.");
						e.printStackTrace();
						resp.setContentType("text/plain");
						resp.setCharacterEncoding("UTF-8");
						resp.getWriter().write("Número do Pedido inválido.");
					}catch(PersistenceException e){
						System.out.println("RegraPedidoCompra, EXCLUINDO PEDIDO DE COMPRA, FALHA AO EXCLUIR: PersistenceException");						
						resp.setContentType("text/plain");
						resp.setCharacterEncoding("UTF-8");
						resp.getWriter().write("Falha ao excluir o pedido de compra.");
					}
				}else{
					System.out.println("RegraPedidoCompra, EXCLUINDO PEDIDO DE COMPRA, DADOS NAO RECEBIDOS, Numero do Pedido de Compra NULO ou VAZIO.");					
					resp.setContentType("text/plain");
					resp.setCharacterEncoding("UTF-8");
					resp.getWriter().write("Informe o Número do Pedido.");
				}			
				break;
		}
		
	}

}
