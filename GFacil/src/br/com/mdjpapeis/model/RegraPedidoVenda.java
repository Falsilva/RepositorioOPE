package br.com.mdjpapeis.model;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.persistence.PersistenceException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import br.com.mdjpapeis.dao.CaixaDAO;
import br.com.mdjpapeis.dao.MovimentacaoDAO;
import br.com.mdjpapeis.dao.PedidoVendaDAO;
import br.com.mdjpapeis.entity.Caixa;
import br.com.mdjpapeis.entity.Cliente;
import br.com.mdjpapeis.entity.ItemPedido;
import br.com.mdjpapeis.entity.Movimentacao;
import br.com.mdjpapeis.entity.PedidoVenda;
import br.com.mdjpapeis.entity.PedidoVenda.StatusVenda;

@WebServlet(urlPatterns = {"/listarPedidoVenda", "/pesquisarPedidoVenda", "/cadastrarPedidoVenda", "/atualizarPedidoVenda", "/excluirPedidoVenda"})
public class RegraPedidoVenda extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	
	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) 
			throws ServletException, IOException {
	
		System.out.println("RegraPedidoVenda - ACTION: " + req.getParameter("action"));
		
		long nPedido = 0;
		String status = null;		
		String strItens = null;
		String[] arrayItens = null;
		int contador = 0;		
		
		BigDecimal valorTotalPedido = new BigDecimal(0);
		BigDecimal valorParaAtualizarCaixa = new BigDecimal(0);		
		long proximoNumero = 0;
		
		PedidoVenda pedidoVenda = null;
		PedidoVenda pedVenda = null;
		List<PedidoVenda> pedidosVenda = null;	
		StatusVenda statusCadastrado = null;
		
		Caixa caixa = null;
		Movimentacao movimentacao = null;
		List<Movimentacao> movimentacoes = null;
		
		String tarefa = req.getParameter("tarefa");
		RequestDispatcher dispatcher = null;
		
		switch(req.getParameter("action")){
			case("listarPedidoVenda"):
				System.out.println("RegraPedidoVenda, LISTANDO PEDIDOS DE VENDA...");
				pedidosVenda = new PedidoVendaDAO().listar();				
				if(pedidosVenda != null){
					System.out.println("RegraPedidoVenda, PEDIDOS DE VENDA LISTADOS.");
					req.setAttribute("pedidosVenda", pedidosVenda);
				}else{
					System.out.println("RegraPedidoVenda, NAO HA PEDIDOS DE VENDA CADASTRADOS.");
					req.setAttribute("mensagem", "Nenhum pedido de venda cadastrado.");
				}			
				dispatcher = req.getRequestDispatcher("controller?action=venda");
				dispatcher.forward(req, resp);
				break;
			case("pesquisarPedidoVenda"):
				System.out.println("RegraPedidoVenda, PESQUISANDO PEDIDO DE VENDA...");
				if(tarefa != null && tarefa.equals("proximoNumero")){
					try{
						System.out.println("");
						pedidosVenda = new PedidoVendaDAO().listar();
						for(PedidoVenda ped : pedidosVenda){
							if(pedidosVenda.indexOf(ped) == pedidosVenda.size() - 1){
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
					System.out.println("RegraPedidoVenda, PESQUISANDO PEDIDO DE VENDA, VERIFICANDO NO. PEDIDO...");
					
					if(req.getParameter("nPedido") == null || req.getParameter("nPedido").isEmpty()){
						System.out.println("RegraPedidoVenda, PESQUISANDO PEDIDO DE VENDA, PEDIDO NULO OU VAZIO...");
						dispatcher = req.getRequestDispatcher("controller?action=listarPedidoVenda");
					}else{
						try{
							System.out.println("RegraPedidoVenda, PESQUISANDO PEDIDO DE VENDA, NO. PEDIDO VERIFICADO: " + req.getParameter("nPedido"));
							nPedido = Long.parseLong(req.getParameter("nPedido"));
							System.out.println("RegraPedidoVenda, PESQUISANDO PEDIDO DE VENDA, NO. PEDIDO CONVERTIDO: " + nPedido);
							pedidoVenda = new PedidoVendaDAO().buscaPedidoVendaPorNumeroPedido(nPedido);
							System.out.println("RegraPedidoVenda, PESQUISANDO PEDIDO DE VENDA, RETORNO DO DAO, PEDIDO: " + pedidoVenda);
							if(pedidoVenda == null){								
								req.setAttribute("mensagem", "Pedido não encontrado.");
							}else{
								pedidosVenda = new ArrayList<PedidoVenda>();
								pedidosVenda.add(pedidoVenda);
								req.setAttribute("pedidosVenda", pedidosVenda);
							}
						}catch(NullPointerException e){
							System.out.println("RegraPedidoVenda, PESQUISANDO PEDIDO DE VENDA, PEDIDO NAO ENCONTRADO: NullPinterException");
							req.setAttribute("mensagem", "Pedido não encontrado.");							
						}catch(NumberFormatException e){
							System.out.println("RegraPedidoVenda, PESQUISANDO PEDIDO DE VENDA, NO. PEDIDO INVALIDO: NumberFormatException");
							req.setAttribute("mensagem", "Número do Pedido inválido.");							
						}
						System.out.println("RegraPedidoVenda, PESQUISA DO PEDIDO DE VENDA CONCLUIDO.");
						dispatcher = req.getRequestDispatcher("controller?action=venda");
					}
					dispatcher.forward(req, resp);					
				}
				break;
			case("cadastrarPedidoVenda"):
				System.out.println("RegraPedidoVenda, CADASTRANDO PEDIDO DE VENDA...");
				
				try{
					if(req.getParameter("itens") != null && !req.getParameter("itens").isEmpty() && req.getParameter("codCliente") != null && !req.getParameter("codCliente").isEmpty()){
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
						
						Cliente cliente = new Cliente();
						cliente.setCodigo(Long.parseLong(req.getParameter("codCliente")));
							
						PedidoVenda pv = new PedidoVenda();
						pv.setDataAbertura(Calendar.getInstance());
						pv.setCliente(cliente);
						pv.setItensPedidoVenda(listaItens);						
						pv.setStatusVenda(StatusVenda.PENDENTE);
						
						System.out.println("  \n" + "PEDIDO.........:\n"
								+ "DATA...........: " + new SimpleDateFormat("dd/MM/yyyy").format(pv.getDataAbertura().getTime()) + "\n"
								+ "COD. CLIENTE: " + pv.getCliente().getCodigo() + "\n"
								+ "STATUS.........: " + pv.getStatusVenda().toString() + "\n"
								+ "ITENS:");
						System.out.println("\tCOD. PRODUTO  |  PESO  |  VALOR ITEM\n");
						for(ItemPedido ivenda : pv.getItensPedidoVenda()){
							System.out.println("  \n" + "\t" + ivenda.getProduto().getCodigo() + " | "
									+ ivenda.getPeso() + " | "
									+ "R$ " + ivenda.getValorItem() + "\n");
							valorTotalPedido = valorTotalPedido.add(ivenda.getValorItem());
						}
						
						pv.setValorTotal(valorTotalPedido);
						System.out.println("VALOR DO PEDIDO: R$ " + pv.getValorTotal());
						
						new PedidoVendaDAO().inserir(pv);
						
						resp.setContentType("text/plain");
						resp.setCharacterEncoding("UTF-8");
						resp.getWriter().write("Pedido gerado com sucesso.");						
					}else{
						resp.setContentType("text/plain");
						resp.setCharacterEncoding("UTF-8");
						resp.getWriter().write("Ops! Pedido não cadastrado.");
					}				
				}catch(NullPointerException e){
					System.out.println("RegraPedidoVenda, CADASTRANDO PEDIDO DE VENDA, FALHA AO CADASTRAR - NullPointerException");
					e.printStackTrace();						
					resp.setContentType("text/plain");
					resp.setCharacterEncoding("UTF-8");
					resp.getWriter().write("Ops! Pedido não cadastrado.");
				}
				break;
			case "atualizarPedidoVenda":
				System.out.println("RegraPedidoVenda, ATUALIZANDO PEDIDO DE VENDA...");				

				try{
					// RECUPERA OS DADOS ENVIADOS
					if(req.getParameter("nPed") != null && !req.getParameter("nPed").isEmpty()
							&& req.getParameter("dtAbertura") != null && !req.getParameter("dtAbertura").isEmpty()							 
							&& req.getParameter("codCliente") != null && !req.getParameter("codCliente").isEmpty()
							&& req.getParameter("itens") != null && !req.getParameter("itens").isEmpty()
							&& req.getParameter("status") != null && !req.getParameter("status").isEmpty()){
						
						pedidoVenda = new PedidoVenda();
						
						// PEGA O NÚMERO DO PEDIDO						
						pedidoVenda.setnPedido(Long.parseLong(req.getParameter("nPed")));						
						
						// RECUPERA O PEDIDO
						pedidoVenda = new PedidoVendaDAO().buscaPedidoVendaPorNumeroPedido(pedidoVenda.getnPedido());
						
						// PEGA A DATA DE ABERTURA
						Calendar dtAbertura = Calendar.getInstance();						
						dtAbertura.setTime(new SimpleDateFormat("dd/MM/yyyy").parse((String)req.getParameter("dtAbertura")));
						pedidoVenda.setDataAbertura(dtAbertura);
						
						// PEGA A DATA DE PAGAMENTO SE HOUVER
						if(req.getParameter("dtPagto") != null && !req.getParameter("dtPagto").isEmpty()){
							System.out.println("DATA DE PAGAMENTO PREENCHIDA");
							Calendar dtPagto = Calendar.getInstance();						
							dtPagto.setTime(new SimpleDateFormat("dd/MM/yyyy").parse((String)req.getParameter("dtPagto")));
							pedidoVenda.setDataPagamento(dtPagto);
						}else{														
							System.out.println("DATA DE PAGAMENTO NULA OU VAZIA");
							// SE STATUS FOR PAGO E DATA PGTO FOR NULA, ADICIONA A DATA ATUAL
							if(req.getParameter("status").equals("PAGO")){
								System.out.println("DATA DE PAGAMENTO NULA E STATUS PAGO");
								Calendar dtPagto = Calendar.getInstance();
								pedidoVenda.setDataPagamento(dtPagto);
								System.out.println("DATA DE PAGAMENTO INSERIDA: " + new SimpleDateFormat("dd/MM/yyyy").format(pedidoVenda.getDataPagamento().getTime()));
							}
						}
						
						// PEGA O CÓDIGO DO CLIENTE
						Cliente cliente = new Cliente();
						cliente.setCodigo(Long.parseLong(req.getParameter("codCliente")));
						pedidoVenda.setCliente(cliente);
						
						System.out.println("COD CLIENTE: " + pedidoVenda.getCliente().getCodigo());				
												
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
						
						// SE ITENS DIMINUI
						if(listaItens.size() < pedidoVenda.getItensPedidoVenda().size()){
							// EXCLUI OS ITENS PELO FK_PEDIDO
							System.out.println("QTD. ITENS MENOR - EXCLUIR O RESTANTE");
						}else{
							// SE ITENS AUMENTOU
							if(listaItens.size() > pedidoVenda.getItensPedidoVenda().size()){
								// PEGA OS ITENS A MAIS E CADASTRA OS ITENS COM O FK_PEDIDO
								System.out.println("QTD. ITENS MAIOR - CADASTRAR O RESTANTE");
							}else{
								// SE MANTEVE A MESMA QUANTIDADE - SERÁ QUE SÃO OS MESMOS???
								// SETA OS ITENS PARA ATUALIZAR
								System.out.println("QTD. ITENS IGUAL - ATUALIZAR");
								for(ItemPedido it : pedidoVenda.getItensPedidoVenda()){
									System.out.println("INDEX it: " + pedidoVenda.getItensPedidoVenda().indexOf(it));
									listaItens.get(pedidoVenda.getItensPedidoVenda().indexOf(it)).setCodigo(it.getCodigo());
								}
							}
						}
						
						// ADICIONA A LISTA DE ITENS NO PEDIDO
						pedidoVenda.setItensPedidoVenda(listaItens);
						
						// CALCULA O VALOR TOTAL DO PEDIDO OU PEGA POR REQUISIÇÃO
						BigDecimal valorPedido = new BigDecimal(0);
						for(ItemPedido itemPed :  pedidoVenda.getItensPedidoVenda()){							
							valorPedido = valorPedido.add(itemPed.getValorItem());							
						}
						
						// GUARDA O VALOR CADASTRADO NO BANCO PARA UTILIZAÇÃO NO CASO DO STATUS DE PAGO PARA PAGO
						valorParaAtualizarCaixa = pedidoVenda.getValorTotal(); 
								
						// ADICIONA O VALOR TOTAL CALCULADO NO PEDIDO
						pedidoVenda.setValorTotal(valorPedido);
						
						System.out.println("PEDIDO PREENCHIDO:\n"
								+ "No. " + pedidoVenda.getnPedido() + "\n"
								+ "DT. ABERT: " + new SimpleDateFormat("dd/MM/yyyy").format(pedidoVenda.getDataAbertura().getTime()) + "\n"
								+ "COD. CLI: " + pedidoVenda.getCliente().getCodigo() + "\n"
								+ pedidoVenda.getItensPedidoVenda().size() + "ITENS: " + "\n"
								+ "VLR. PED: " + pedidoVenda.getValorTotal().toString());
						
						for(ItemPedido itemPed :  pedidoVenda.getItensPedidoVenda()){
							System.out.println("ITEM " + itemPed.getCodigo() +
									" PROD: " + itemPed.getProduto().getCodigo() +
									" PESO: " + itemPed.getPeso() +
									" VLR ITEM: " + itemPed.getValorItem());
						}
						
						// LOG 1						
						System.out.println("RegraPedidoVenda, ATUALIZANDO PEDIDO DE VENDA, 1. PEDIDO PESQUISADO NO BANCO NAO E NULO... STATUS: " + pedidoVenda.getStatusVenda().toString());
						
						// GUARDA O STATUS CADASTRADO NO BANCO PARA COMPARAÇÃO
						statusCadastrado = pedidoVenda.getStatusVenda();
						
						// PEGA O STATUS DA REQUISIÇÃO
						switch(req.getParameter("status")){
							case "PAGO":
								System.out.println("STATUS PAGO");
								pedidoVenda.setStatusVenda(StatusVenda.PAGO);								
								
								System.out.println("case PAGO - DATA PAGTO: " + new SimpleDateFormat("dd/MM/yyyy").format(pedidoVenda.getDataPagamento().getTime()));
								
								// ATUALIZA O PEDIDO
								new PedidoVendaDAO().atualizar(pedidoVenda);
								
								// LOG 2
								System.out.println("RegraPedidoVenda, ATUALIZANDO PEDIDO DE VENDA, 2. PEDIDO No. " + pedidoVenda.getnPedido() + " ATUALIZADO.");
								
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
								if(statusCadastrado.equals(PedidoVenda.StatusVenda.PENDENTE)){
									// LOG 3.1
									System.out.println("RegraPedidoVenda, ATUALIZANDO PEDIDO DE VENDA, 3.1 PEDIDO ESTAVA PENDENTE, AGORA E PAGO, CADASTRANDO MOVIMENTACAO");																	
									
									
									// CRIA UMA MOVIMENTAÇÃO E SETA OS DADOS
									movimentacao = new Movimentacao();									
									movimentacao.setPedidoVenda(pedidoVenda);
									new MovimentacaoDAO().inserir(movimentacao);
									movimentacao = new MovimentacaoDAO().buscaPorPedidoVenda(pedidoVenda);
									
									// LOG 3.1.1
									System.out.println("RegraPedidoVenda, ATUALIZANDO PEDIDO DE VENDA, 3.1.1 MOVIMENTACAO CADASTRADA");									
																		
									// ADICIONA A NOVA MOVIMENTAÇÃO AO CAIXA
									movimentacoes.add(movimentacao);
									caixa.setMovimentacoes(movimentacoes);
									
									// ATUALIZA O TOTAL DE ENTRADA
									caixa.setTotalEntrada(caixa.getTotalEntrada().add(pedidoVenda.getValorTotal()));
									
									// LOG 3.1.2
									System.out.println("RegraPedidoVenda, ATUALIZANDO PEDIDO DE VENDA, 3.1.2 LISTA DE MOVIMENTACOES DO CAIXA ATUALIZADA");																		
								}else{
									// STATUS ERA PAGO
									
									// LOG 3.2
									System.out.println("RegraPedidoVenda, ATUALIZANDO PEDIDO DE VENDA, 3.2 PEDIDO ESTAVA PAGO, AGORA TAMBEM, ATUALIZANDO MOVIMENTACAO");
																		
									movimentacao = new MovimentacaoDAO().buscaPorPedidoVenda(pedidoVenda);									
									movimentacao.setPedidoVenda(pedidoVenda);
									System.out.println("MOVIMENTACAO: VLR. TOTAL R$ " + movimentacao.getValorLancamento());							
															
									new MovimentacaoDAO().atualizar(movimentacao);									
									
									System.out.println("CAIXA ENTRADA TOTAL: antes R$ " + caixa.getTotalEntrada());
									// ATUALIZA O TOTAL DE ENTRADA - SUBTRAI O VALOR ANTIGO E ADICIONA O NOVO
									caixa.setTotalEntrada(caixa.getTotalEntrada().subtract(valorParaAtualizarCaixa));
									caixa.setTotalEntrada(caixa.getTotalEntrada().add(movimentacao.getValorLancamento()));
									System.out.println("CAIXA ENTRADA TOTAL: depois R$ " + caixa.getTotalEntrada());
									// LOG 3.2.1
									System.out.println("RegraPedidoVenda, ATUALIZANDO PEDIDO DE VENDA, 3.2.1 MOVIMENTACAO ATUALIZADA");
								}								

								// ATUALIZA O SALDO
								caixa.setSaldo(caixa.getTotalEntrada().subtract(caixa.getTotalSaida()));
													
								// LOG 4
								System.out.println("RegraPedidoVenda, ATUALIZANDO PEDIDO DE VENDA, 4. VALORES DO CAIXA ATUALIZADOS: SALDO = " + caixa.getTotalEntrada().toString() + " - " + caixa.getTotalSaida().toString() + " = " + caixa.getSaldo().toString());								
								
								// ATUALIZA O CAIXA
								new CaixaDAO().atualizar(caixa);
								
								// LOG 5
								System.out.println("RegraPedidoVenda, ATUALIZANDO PEDIDO DE VENDA, 5. CAIXA ATUALIZADO");
								
								break;
							case "PENDENTE":
								// STATUS PENDENTE (REQUISIÇÃO)
								System.out.println("STATUS PENDENTE");
								pedidoVenda.setStatusVenda(StatusVenda.PENDENTE);
								
								// ATUALIZA O PEDIDO
								new PedidoVendaDAO().atualizar(pedidoVenda);
								
								// LOG 2
								System.out.println("RegraPedidoVenda, ATUALIZANDO PEDIDO DE VENDA, 2. PEDIDO No. " + pedidoVenda.getnPedido() + " ATUALIZADO.");
								
															
								// VERIFICA SE ERA PAGO E EXCLUI A MOVIMENTAÇÃO								
								if(statusCadastrado.equals(PedidoVenda.StatusVenda.PAGO)){
									System.out.println("STATUS ERA PAGO, MOVIMENTACAO SERA EXCLUIDA");
									
									// EXCLUI A MOVIMENTAÇÃO
									System.out.println("EXCLUI A MOVIMENTACAO");
									new MovimentacaoDAO().excluirMovimentacaoVenda(pedidoVenda);
									System.out.println("MOVIMENTACAO EXCLUIDA");
									
									// LOG 3.1
									System.out.println("RegraPedidoVenda, ATUALIZANDO PEDIDO DE VENDA, 3.1 PEDIDO PAGO");
									
									// RECUPERA O CAIXA PARA ATUALIZAR VALORES DE SALDO, ENTRADA OU SAÍDA
									caixa = new CaixaDAO().buscaCaixaPorCodigo(1);
									System.out.println("CAIXA - TAMANHO MOVs: " + caixa.getMovimentacoes().size());									
									
									// CASO O CAIXA ESTEJA NULO ADICIONA O VALOR ZERO
									if(caixa.getSaldo() == null) caixa.setSaldo(new BigDecimal(0));
									if(caixa.getTotalEntrada() == null) caixa.setTotalEntrada(new BigDecimal(0));
									if(caixa.getTotalSaida() == null) caixa.setTotalSaida(new BigDecimal(0));									
									
									// ATUALIZA O TOTAL DE ENTRADA
									System.out.println("1.PAGO >>> PENDENTE (ATUAL) = SALDO: " + caixa.getSaldo());
									caixa.setTotalEntrada(caixa.getTotalEntrada().subtract(valorParaAtualizarCaixa));									
									
									// ATUALIZA O SALDO
									caixa.setSaldo(caixa.getTotalEntrada().subtract(caixa.getTotalSaida()));
									System.out.println("2.PAGO >>> PENDENTE (ATUAL) = SALDO: " + caixa.getSaldo());
									
									// LOG 4
									System.out.println("RegraPedidoVenda, ATUALIZANDO PEDIDO DE VENDA, 4. VALORES DO CAIXA ATUALIZADOS: SALDO = " + caixa.getTotalEntrada().toString() + " - " + caixa.getTotalSaida().toString() + " = " + caixa.getSaldo().toString());
									
									// ATUALIZA O CAIXA
									new CaixaDAO().atualizar(caixa);
									
									// LOG 5
									System.out.println("RegraPedidoVenda, ATUALIZANDO PEDIDO DE VENDA, 5. CAIXA ATUALIZADO");
																	
									// LOG 6
									System.out.println("RegraPedidoVenda, ATUALIZANDO PEDIDO DE VENDA, 6. MOVIMENTACAO EXCLUÍDA");
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
					System.out.println("RegraPedidoVenda, ATUALIZANDO PEDIDO DE VENDA, FALHA AO ATUALIZAR - NullPointerException");
					e.printStackTrace();						
					resp.setContentType("text/plain");
					resp.setCharacterEncoding("UTF-8");
					resp.getWriter().write("Ops! Pedido não atualizado.");
				}catch (ParseException e) {
					// Caso as datas não sejam convertidas
					e.printStackTrace();
				}
				break;
			case("excluirPedidoVenda"):
				System.out.println("RegraPedidoVenda, EXCLUINDO PEDIDO DE VENDA...");			
				System.out.println("RegraPedidoVenda, EXCLUINDO PEDIDO DE VENDA, VERIFICANDO O RECEBIMENTO DE DADOS...");
				
				// A EXCLUSÃO É FEITA PELA "PRIMARY KEY", OU SEJA, PELO ATRIBUTO "NPEDIDO"				
				if(req.getParameter("nPedido") != null && !req.getParameter("nPedido").isEmpty()
						&& req.getParameter("statusExcluir") != null && !req.getParameter("statusExcluir").isEmpty()){
					System.out.println("RegraPedidoVenda, EXCLUINDO PEDIDO DE VENDA, DADOS RECEBIDOS.");
					try{
						System.out.println("RegraPedidoVenda, EXCLUINDO PEDIDO DE VENDA, CONVERTENDO DADOS.");
						nPedido = Long.parseLong(req.getParameter("nPedido"));
						status = req.getParameter("statusExcluir");
						System.out.println("RegraPedidoVenda, EXCLUINDO PEDIDO DE VENDA, DADOS CONVERTIDOS." + nPedido);
						
						pedVenda = new PedidoVenda();
						pedVenda.setnPedido(nPedido);						
						System.out.println("RegraPedidoVenda, EXCLUINDO PEDIDO DE VENDA, No. Ped. Venda: " + pedVenda.getnPedido());
						// CASO O STATUS SEJA PAGO, EXCLUI A MOVIMENTACAO RELACIONADA
						if(status.equals(PedidoVenda.StatusVenda.PAGO.toString())){
							System.out.println("RegraPedidoVenda, EXCLUINDO PEDIDO DE VENDA, EXCLUINDO A MOVIMENTACAO...");	
							
							// EXCLUI A MOVIMENTACAO
							new MovimentacaoDAO().excluirMovimentacaoVenda(pedVenda);
							
							// BUSCA O PEDIDO PARA CALCULAR VALORES DO CAIXA
							pedVenda = new PedidoVendaDAO().buscaPedidoVendaPorNumeroPedido(nPedido);
							
							// RECUPERA O CAIXA PARA ATUALIZAR VALORES DE SALDO, ENTRADA OU SAÍDA
							caixa = new CaixaDAO().buscaCaixaPorCodigo(1);								
							
							// CASO O CAIXA ESTEJA NULO ADICIONA O VALOR ZERO
							if(caixa.getSaldo() == null) caixa.setSaldo(new BigDecimal(0));
							if(caixa.getTotalEntrada() == null) caixa.setTotalEntrada(new BigDecimal(0));
							if(caixa.getTotalSaida() == null) caixa.setTotalSaida(new BigDecimal(0));
							
							// ATUALIZA O TOTAL DE ENTRADA							
							caixa.setTotalEntrada(caixa.getTotalEntrada().subtract(pedVenda.getValorTotal()));									
							
							// ATUALIZA O SALDO
							caixa.setSaldo(caixa.getTotalEntrada().subtract(caixa.getTotalSaida()));							
							
							// ATUALIZA O CAIXA
							new CaixaDAO().atualizar(caixa);
							System.out.println("CAIXA ATUALIZADO: R$ " + caixa.getTotalEntrada() + " - R$ " + caixa.getTotalSaida() + " = " + caixa.getSaldo());
							
						}
						
						System.out.println("RegraPedidoVenda, EXCLUINDO PEDIDO DE VENDA...");
						new PedidoVendaDAO().excluir(pedVenda);
						
						System.out.println("RegraPedidoVenda, EXCLUINDO PEDIDO DE VENDA, EXCLUSAO REALIZADA.");						
						resp.setContentType("text/plain");
						resp.setCharacterEncoding("UTF-8");
						resp.getWriter().write("Exclusão realizada com sucesso!");
						
					}catch(NumberFormatException e){
						System.out.println("RegraPedidoVenda, EXCLUINDO PEDIDO DE VENDA, DADOS NAO FORMATADOS, NUMERO PEDIDO INVALIDO, NumberFormatException.");
						e.printStackTrace();
						resp.setContentType("text/plain");
						resp.setCharacterEncoding("UTF-8");
						resp.getWriter().write("Número do Pedido inválido.");
					}catch(PersistenceException e){
						System.out.println("RegraPedidoVenda, EXCLUINDO PEDIDO DE VENDA, FALHA AO EXCLUIR: PersistenceException");						
						resp.setContentType("text/plain");
						resp.setCharacterEncoding("UTF-8");
						resp.getWriter().write("Falha ao excluir o pedido de venda.");
					}
				}else{
					System.out.println("RegraPedidoVenda, EXCLUINDO PEDIDO DE VENDA, DADOS NAO RECEBIDOS, Numero do Pedido de Compra NULO ou VAZIO.");					
					resp.setContentType("text/plain");
					resp.setCharacterEncoding("UTF-8");
					resp.getWriter().write("Informe o Número do Pedido.");
				}			
				break;
		}
	}
}
