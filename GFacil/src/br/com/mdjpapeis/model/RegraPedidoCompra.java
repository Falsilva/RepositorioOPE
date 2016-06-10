package br.com.mdjpapeis.model;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.json.Json;
import javax.persistence.PersistenceException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.mdjpapeis.dao.CaixaDAO;
import br.com.mdjpapeis.dao.ItemPedidoDAO;
import br.com.mdjpapeis.dao.MovimentacaoDAO;
import br.com.mdjpapeis.dao.PedidoCompraDAO;
import br.com.mdjpapeis.dao.ProdutoDAO;
import br.com.mdjpapeis.entity.Caixa;
import br.com.mdjpapeis.entity.Fornecedor;
import br.com.mdjpapeis.entity.ItemPedido;
import br.com.mdjpapeis.entity.Movimentacao;
import br.com.mdjpapeis.entity.Movimentacao.TipoLancamento;
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
		BigDecimal valorParaAtualizarCaixa = new BigDecimal(0);		
		long proximoNumero = 0;
		
		PedidoCompra pedidoCompra = null;
		PedidoCompra pedCompra = null;
		List<PedidoCompra> pedidosCompra = null;	
		PedidoCompra verificaPedido = null;
		StatusCompra statusCadastrado = null;
		
		Caixa caixa = null;
		Movimentacao movimentacao = null;
		List<Movimentacao> movimentacoes = null;
		
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
			case "atualizarPedidoCompra":
				System.out.println("RegraPedidoCompra, ATUALIZANDO PEDIDO DE COMPRA...");				

				try{
					// RECUPERA OS DADOS ENVIADOS
					if(req.getParameter("nPed") != null && !req.getParameter("nPed").isEmpty()
							&& req.getParameter("dtAbertura") != null && !req.getParameter("dtAbertura").isEmpty()							 
							&& req.getParameter("codFornecedor") != null && !req.getParameter("codFornecedor").isEmpty()
							&& req.getParameter("itens") != null && !req.getParameter("itens").isEmpty()
							&& req.getParameter("status") != null && !req.getParameter("status").isEmpty()){
						
						pedidoCompra = new PedidoCompra();
						
						// PEGA O NÚMERO DO PEDIDO						
						pedidoCompra.setnPedido(Long.parseLong(req.getParameter("nPed")));						
						
						// RECUPERA O PEDIDO
						pedidoCompra = new PedidoCompraDAO().buscaPedidoCompraPorNumeroPedido(pedidoCompra.getnPedido());
						
						// PEGA A DATA DE ABERTURA
						Calendar dtAbertura = Calendar.getInstance();						
						dtAbertura.setTime(new SimpleDateFormat("dd/MM/yyyy").parse((String)req.getParameter("dtAbertura")));
						pedidoCompra.setDataAbertura(dtAbertura);
						
						// PEGA A DATA DE PAGAMENTO SE HOUVER
						if(req.getParameter("dtPagto") != null && !req.getParameter("dtPagto").isEmpty()){
							System.out.println("DATA DE PAGAMENTO PREENCHIDA");
							Calendar dtPagto = Calendar.getInstance();						
							dtPagto.setTime(new SimpleDateFormat("dd/MM/yyyy").parse((String)req.getParameter("dtPagto")));
							pedidoCompra.setDataPagamento(dtPagto);
						}else{														
							System.out.println("DATA DE PAGAMENTO NULA OU VAZIA");
							// SE STATUS FOR PAGO E DATA PGTO FOR NULA, ADICIONA A DATA ATUAL
							if(req.getParameter("status").equals("PAGO")){
								System.out.println("DATA DE PAGAMENTO NULA E STATUS PAGO");
								Calendar dtPagto = Calendar.getInstance();
								pedidoCompra.setDataPagamento(dtPagto);
								System.out.println("DATA DE PAGAMENTO INSERIDA: " + new SimpleDateFormat("dd/MM/yyyy").format(pedidoCompra.getDataPagamento().getTime()));
							}
						}
						
						// PEGA O CÓDIGO DO FORNECEDOR
						Fornecedor fornecedor = new Fornecedor();
						fornecedor.setCodigo(Long.parseLong(req.getParameter("codFornecedor")));
						pedidoCompra.setFornecedor(fornecedor);
						
						System.out.println("COD FORNECEDOR: " + pedidoCompra.getFornecedor().getCodigo());
						
						// PEGA OS ITENS EM STRING, MAS NA ESTRUTURA JSON, E COLOCA EM UM ARRAY
						strItens = req.getParameter("itens").replace("},{", "};{");
						arrayItens = strItens.split(";");
						
						// ADICIONA OS ITENS NA LIST
						List<ItemPedido> listaItens = new ArrayList<ItemPedido>();
						contador = 0;
						for(String strItem : arrayItens){		
							Gson json = new Gson();
							ItemPedido item = json.fromJson(strItem, ItemPedido.class);						
							listaItens.add(item);
							System.out.println("\nITEM:\n"
									+ "COD_PRODUTO: " + listaItens.get(contador).getProduto().getCodigo() + "\n"
									+ "PESO.......: " + listaItens.get(contador).getPeso() + "\n"
									+ "VALOR_ITEM.: " + listaItens.get(contador).getValorItem());
							contador++;
						}
						contador = 0;					
						
						
						if(listaItens.size() < pedidoCompra.getItensPedidoCompra().size()){
							// EXCLUI OS ITENS PELO FK_PEDIDO
							System.out.println("QTD. ITENS MENOR - EXCLUIR O RESTANTE");
						}else{
							if(listaItens.size() > pedidoCompra.getItensPedidoCompra().size()){
								// PEGA OS ITENS A MAIS E CADASTRA OS ITENS COM O FK_PEDIDO
								System.out.println("QTD. ITENS MAIOR - CADASTRAR O RESTANTE");
							}else{
								// SETA OS ITENS PARA ATUALIZAR
								System.out.println("QTD. ITENS IGUAL - ATUALIZAR");
								for(ItemPedido it : pedidoCompra.getItensPedidoCompra()){
									System.out.println("INDEX it: " + pedidoCompra.getItensPedidoCompra().indexOf(it));
									listaItens.get(pedidoCompra.getItensPedidoCompra().indexOf(it)).setCodigo(it.getCodigo());
								}
							}
						}
						
						// ADICIONA A LISTA DE ITENS NO PEDIDO
						pedidoCompra.setItensPedidoCompra(listaItens);
						
						// CALCULA O VALOR TOTAL DO PEDIDO OU PEGA POR REQUISIÇÃO
						BigDecimal valorPedido = new BigDecimal(0);
						for(ItemPedido itemPed :  pedidoCompra.getItensPedidoCompra()){							
							valorPedido = valorPedido.add(itemPed.getValorItem());							
						}
						
						// GUARDA O VALOR CADASTRADO NO BANCO PARA UTILIZAÇÃO NO CASO DO STATUS DE PAGO PARA PAGO
						valorParaAtualizarCaixa = pedidoCompra.getValorTotal(); 
								
						// ADICIONA O VALOR TOTAL CALCULADO NO PEDIDO
						pedidoCompra.setValorTotal(valorPedido);
						
						System.out.println("PEDIDO PREENCHIDO:\n"
								+ "No. " + pedidoCompra.getnPedido() + "\n"
								+ "DT. ABERT: " + new SimpleDateFormat("dd/MM/yyyy").format(pedidoCompra.getDataAbertura().getTime()) + "\n"
								+ "COD. FORN: " + pedidoCompra.getFornecedor().getCodigo() + "\n"
								+ pedidoCompra.getItensPedidoCompra().size() + "ITENS: " + "\n"
								+ "VLR. PED: " + pedidoCompra.getValorTotal().toString());
						
						for(ItemPedido itemPed :  pedidoCompra.getItensPedidoCompra()){
							System.out.println("ITEM " + itemPed.getCodigo() +
									" PROD: " + itemPed.getProduto().getCodigo() +
									" PESO: " + itemPed.getPeso() +
									" VLR ITEM: " + itemPed.getValorItem());
						}
						
						// LOG 1						
						System.out.println("RegraPedidoCompra, ATUALIZANDO PEDIDO DE COMPRA, 1. PEDIDO PESQUISADO NO BANCO NAO E NULO... STATUS: " + pedidoCompra.getStatusCompra().toString());
						
						// GUARDA O STATUS CADASTRADO NO BANCO PARA COMPARAÇÃO
						statusCadastrado = pedidoCompra.getStatusCompra();
						
						// PEGA O STATUS DA REQUISIÇÃO
						switch(req.getParameter("status")){
							case "PAGO":
								System.out.println("STATUS PAGO");
								pedidoCompra.setStatusCompra(StatusCompra.PAGO);								
								
								System.out.println("case PAGO - DATA PAGTO: " + new SimpleDateFormat("dd/MM/yyyy").format(pedidoCompra.getDataPagamento().getTime()));
								
								// ATUALIZA O PEDIDO
								new PedidoCompraDAO().atualizar(pedidoCompra);
								
								// LOG 2
								System.out.println("RegraPedidoCompra, ATUALIZANDO PEDIDO DE COMPRA, 2. PEDIDO No. " + pedidoCompra.getnPedido() + " ATUALIZADO.");
								
								// RECUPERA O CAIXA PARA ATUALIZAR VALORES DE SALDO, ENTRADA OU SAÍDA
								caixa = new CaixaDAO().buscaCaixaPorCodigo(1);								
								
								// CASO O CAIXA ESTEJA NULO ADICIONA O VALOR ZERO
								if(caixa.getSaldo() == null) caixa.setSaldo(new BigDecimal(0));
								if(caixa.getTotalEntrada() == null) caixa.setTotalEntrada(new BigDecimal(0));
								if(caixa.getTotalSaida() == null) caixa.setTotalSaida(new BigDecimal(0));
								
								System.out.println("CAIXA RECUPERADO");
								
								// LISTA VAZIA - PEGA AS MOVIMENTAÇÕES DO CAIXA
								movimentacoes = caixa.getMovimentacoes();
								//System.out.println("TAMANHO DA LISTA DE MOVIMENTACOES DO CAIXA: " + movimentacoes.size());
								System.out.println("statusCadastrado é " + statusCadastrado.toString());
								
								// SE STATUS ERA PENDENTE, CADASTRA NOVA MOVIMENTAÇÃO NO CAIXA								
								if(statusCadastrado.equals(PedidoCompra.StatusCompra.PENDENTE)){
									// LOG 3.1
									System.out.println("RegraPedidoCompra, ATUALIZANDO PEDIDO DE COMPRA, 3.1 PEDIDO ESTAVA PENDENTE, AGORA E PAGO, CADASTRANDO MOVIMENTACAO");
									
									// BUSCA O PEDIDO QUE FOI ATUALIZADO
									//pedidoCompra = new PedidoCompraDAO().buscaPedidoCompraPorNumeroPedido(pedidoCompra.getnPedido());
									
									System.out.println("RegraPedidoCompra, ATUALIZANDO PEDIDO DE COMPRA, 3.1.1 BUSCA DO PEDIDO REALIZADA");
									
									// CRIA UMA MOVIMENTAÇÃO E SETA OS DADOS
									movimentacao = new Movimentacao();									
									movimentacao.setPedidoCompra(pedidoCompra);
									new MovimentacaoDAO().inserir(movimentacao);
									movimentacao = new MovimentacaoDAO().buscaPorPedidoCompra(pedidoCompra);
									
									// LOG 3.1.1
									System.out.println("RegraPedidoCompra, ATUALIZANDO PEDIDO DE COMPRA, 3.1.2 MOVIMENTACAO CADASTRADA");									
																		
									// ADICIONA A NOVA MOVIMENTAÇÃO AO CAIXA
									movimentacoes.add(movimentacao);
									caixa.setMovimentacoes(movimentacoes);
									
									// ATUALIZA O TOTAL DE SAÍDA
									caixa.setTotalSaida(caixa.getTotalSaida().add(pedidoCompra.getValorTotal()));
									
									// LOG 3.1.2
									System.out.println("RegraPedidoCompra, ATUALIZANDO PEDIDO DE COMPRA, 3.1.3 LISTA DE MOVIMENTACOES DO CAIXA ATUALIZADA");																		
								}else{
									// STATUS ERA PAGO
									
									// LOG 3.2
									System.out.println("RegraPedidoCompra, ATUALIZANDO PEDIDO DE COMPRA, 3.2 PEDIDO ESTAVA PAGO, AGORA TAMBEM, ATUALIZANDO MOVIMENTACAO");
									
									// BUSCA O PEDIDO QUE FOI ATUALIZADO
									//pedidoCompra = new PedidoCompraDAO().buscaPedidoCompraPorNumeroPedido(pedidoCompra.getnPedido());
									
									//System.out.println("PEDIDO VALOR: " + pedidoCompra.getValorTotal());
									// SENÃO, JÁ ERA PAGO, ENTÃO SÓ ATUALIZA A MOVIMENTAÇÃO NO CAIXA
									// CRIA UMA MOVIMENTAÇÃO E SETA OS DADOS
									//movimentacao = new Movimentacao();									
									//movimentacao.setPedidoCompra(pedidoCompra);
									
									movimentacao = new MovimentacaoDAO().buscaPorPedidoCompra(pedidoCompra);									
									movimentacao.setPedidoCompra(pedidoCompra);
									System.out.println("MOVIMENTACAO: VLR. TOTAL R$ " + movimentacao.getValorLancamento());
									//System.out.println("MOVIMENTACAO VALOR: " + movimentacao.getValorLancamento());
									//movimentacao.setData(pedidoCompra.getDataPagamento());
									//movimentacao.setValorLancamento(pedidoCompra.getValorTotal());									
															
									new MovimentacaoDAO().atualizar(movimentacao);
									
									// ADICIONA A NOVA MOVIMENTAÇÃO AO CAIXA
									//movimentacoes.add(movimentacao);
									//caixa.setMovimentacoes(movimentacoes);
									
									System.out.println("CAIXA SAIDA TOTAL: antes R$ " + caixa.getTotalSaida());
									// ATUALIZA O TOTAL DE SAÍDA - SUBTRAI O VALOR ANTIGO E ADICIONA O NOVO
									caixa.setTotalSaida(caixa.getTotalSaida().subtract(valorParaAtualizarCaixa));
									caixa.setTotalSaida(caixa.getTotalSaida().add(movimentacao.getValorLancamento()));
									System.out.println("CAIXA SAIDA TOTAL: depois R$ " + caixa.getTotalSaida());
									// LOG 3.2.1
									System.out.println("RegraPedidoCompra, ATUALIZANDO PEDIDO DE COMPRA, 3.2.1 MOVIMENTACAO ATUALIZADA");
								}								

								// ATUALIZA O SALDO
								caixa.setSaldo(caixa.getTotalEntrada().subtract(caixa.getTotalSaida()));
													
								// LOG 4
								System.out.println("RegraPedidoCompra, ATUALIZANDO PEDIDO DE COMPRA, 4. VALORES DO CAIXA ATUALIZADOS: SALDO = " + caixa.getTotalEntrada().toString() + " - " + caixa.getTotalSaida().toString() + " = " + caixa.getSaldo().toString());								
								
								// ATUALIZA O CAIXA
								new CaixaDAO().atualizar(caixa);
								
								// LOG 5
								System.out.println("RegraPedidoCompra, ATUALIZANDO PEDIDO DE COMPRA, 5. CAIXA ATUALIZADO");
								
								break;
							case "PENDENTE":
								// STATUS PENDENTE (REQUISIÇÃO)
								System.out.println("STATUS PENDENTE");
								pedidoCompra.setStatusCompra(StatusCompra.PENDENTE);
								
								// ATUALIZA O PEDIDO
								new PedidoCompraDAO().atualizar(pedidoCompra);
								
								// LOG 2
								System.out.println("RegraPedidoCompra, ATUALIZANDO PEDIDO DE COMPRA, 2. PEDIDO No. " + pedidoCompra.getnPedido() + " ATUALIZADO.");
								
															
								// VERIFICA SE ERA PAGO E EXCLUI A MOVIMENTAÇÃO								
								if(statusCadastrado.equals(PedidoCompra.StatusCompra.PAGO)){
									System.out.println("STATUS ERA PAGO, MOVIMENTACAO SERA EXCLUIDA");
									// CRIA UMA MOVIMENTAÇÃO
									//movimentacao = new MovimentacaoDAO().buscaPorPedidoCompra(pedidoCompra);
									
									// EXCLUI A MOVIMENTAÇÃO
									System.out.println("EXCLUI A MOVIMENTACAO");
									new MovimentacaoDAO().excluirMovimentacaoCompra(pedidoCompra);
									System.out.println("MOVIMENTACAO EXCLUIDA");
									
									// LOG 3.1
									System.out.println("RegraPedidoCompra, ATUALIZANDO PEDIDO DE COMPRA, 3.1 PEDIDO PAGO");
									
									// RECUPERA O CAIXA PARA ATUALIZAR VALORES DE SALDO, ENTRADA OU SAÍDA
									caixa = new CaixaDAO().buscaCaixaPorCodigo(1);
									System.out.println("CAIXA - TAMANHO MOVs: " + caixa.getMovimentacoes().size());
									// NULA PEGA AS MOVIMENTAÇÕES DO CAIXA E REMOVE A MOVIMENTAÇÃO EXCLUÍDA
									//movimentacoes = caixa.getMovimentacoes();
									/*for(Movimentacao mc : movimentacoes){
										if(mc.getnLancamento() == movimentacao.getnLancamento()){
											// LOG 3.2
											System.out.println("RegraPedidoCompra, ATUALIZANDO PEDIDO DE COMPRA, 3.2 REMOVENDO A MOVIMENTACAO " + movimentacao.getnLancamento() + " DA LISTA");
											movimentacoes.remove(movimentacao);
											caixa.setMovimentacoes(movimentacoes);											
										}
									}*/
									
									// CASO O CAIXA ESTEJA NULO ADICIONA O VALOR ZERO
									if(caixa.getSaldo() == null) caixa.setSaldo(new BigDecimal(0));
									if(caixa.getTotalEntrada() == null) caixa.setTotalEntrada(new BigDecimal(0));
									if(caixa.getTotalSaida() == null) caixa.setTotalSaida(new BigDecimal(0));									
									
									// ATUALIZA O TOTAL DE SAÍDA
									System.out.println("1.PAGO >>> PENDENTE (ATUAL) = SALDO: " + caixa.getSaldo());
									caixa.setTotalSaida(caixa.getTotalSaida().subtract(valorParaAtualizarCaixa));									
									
									// ATUALIZA O SALDO
									caixa.setSaldo(caixa.getTotalEntrada().subtract(caixa.getTotalSaida()));
									System.out.println("2.PAGO >>> PENDENTE (ATUAL) = SALDO: " + caixa.getSaldo());
									
									// LOG 4
									System.out.println("RegraPedidoCompra, ATUALIZANDO PEDIDO DE COMPRA, 4. VALORES DO CAIXA ATUALIZADOS: SALDO = " + caixa.getTotalEntrada().toString() + " - " + caixa.getTotalSaida().toString() + " = " + caixa.getSaldo().toString());
									
									// ATUALIZA O CAIXA
									new CaixaDAO().atualizar(caixa);
									
									// LOG 5
									System.out.println("RegraPedidoCompra, ATUALIZANDO PEDIDO DE COMPRA, 5. CAIXA ATUALIZADO");
									
									
									
									// LOG 6
									System.out.println("RegraPedidoCompra, ATUALIZANDO PEDIDO DE COMPRA, 6. MOVIMENTACAO EXCLUÍDA");
								}
								break;
						}
						
						resp.setContentType("text/plain");
						resp.setCharacterEncoding("UTF-8");
						resp.getWriter().write("Pedido atualizado com sucesso.");						
					}else{
						resp.setContentType("text/plain");
						resp.setCharacterEncoding("UTF-8");
						resp.getWriter().write("Ops! Pedido não atualizado.");
					}				
				}catch(NullPointerException e){
					System.out.println("RegraPedidoCompra, ATUALIZANDO PEDIDO DE COMPRA, FALHA AO ATUALIZAR - NullPointerException");
					e.printStackTrace();						
					resp.setContentType("text/plain");
					resp.setCharacterEncoding("UTF-8");
					resp.getWriter().write("Ops! Pedido não atualizado.");
				}catch (ParseException e) {
					// Caso as datas não sejam convertidas
					e.printStackTrace();
				}
				break;
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
							
							// EXCLUI A MOVIMENTACAO
							new MovimentacaoDAO().excluirMovimentacaoCompra(pedCompra);
							
							// BUSCA O PEDIDO PARA CALCULAR VALORES DO CAIXA
							pedCompra = new PedidoCompraDAO().buscaPedidoCompraPorNumeroPedido(nPedido);
							
							// RECUPERA O CAIXA PARA ATUALIZAR VALORES DE SALDO, ENTRADA OU SAÍDA
							caixa = new CaixaDAO().buscaCaixaPorCodigo(1);								
							
							// CASO O CAIXA ESTEJA NULO ADICIONA O VALOR ZERO
							if(caixa.getSaldo() == null) caixa.setSaldo(new BigDecimal(0));
							if(caixa.getTotalEntrada() == null) caixa.setTotalEntrada(new BigDecimal(0));
							if(caixa.getTotalSaida() == null) caixa.setTotalSaida(new BigDecimal(0));
							
							// ATUALIZA O TOTAL DE SAÍDA							
							caixa.setTotalSaida(caixa.getTotalSaida().subtract(pedCompra.getValorTotal()));									
							
							// ATUALIZA O SALDO
							caixa.setSaldo(caixa.getTotalEntrada().subtract(caixa.getTotalSaida()));							
							
							// ATUALIZA O CAIXA
							new CaixaDAO().atualizar(caixa);
							System.out.println("CAIXA ATUALIZADO: R$ " + caixa.getTotalEntrada() + " - R$ " + caixa.getTotalSaida() + " = " + caixa.getSaldo());
							
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
