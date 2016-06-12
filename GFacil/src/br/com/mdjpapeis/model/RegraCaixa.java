package br.com.mdjpapeis.model;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.mdjpapeis.dao.CaixaDAO;
import br.com.mdjpapeis.dao.PedidoCompraDAO;
import br.com.mdjpapeis.dao.PedidoVendaDAO;
import br.com.mdjpapeis.entity.Caixa;
import br.com.mdjpapeis.entity.Fornecedor.Tipo;
import br.com.mdjpapeis.entity.Movimentacao;
import br.com.mdjpapeis.entity.Movimentacao.TipoLancamento;
import br.com.mdjpapeis.entity.PedidoCompra;
import br.com.mdjpapeis.entity.PedidoCompra.StatusCompra;
import br.com.mdjpapeis.entity.PedidoVenda;
import br.com.mdjpapeis.entity.PedidoVenda.StatusVenda;

@WebServlet(urlPatterns = {"/listarCaixa", "/pesquisarCaixa", "/cadastrarCaixa", "/atualizarCaixa", "/excluirCaixa"})
public class RegraCaixa extends HttpServlet {

	private static final long serialVersionUID = 1L;
	
	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) 
			throws ServletException, IOException {
	
		System.out.println("RegraCaixa - ACTION: " + req.getParameter("action"));
		
		long codigo = 0;
		Caixa caixa = new Caixa();
		
		Caixa caixaMes = new Caixa();
		caixaMes.setTotalEntrada(new BigDecimal(0));
		caixaMes.setTotalSaida(new BigDecimal(0));
		caixaMes.setSaldo(new BigDecimal(0));
		
		List<Movimentacao> movimentacoes = null;
		List<Movimentacao> movUltLanc = null;
		List<PedidoCompra> pedPCUltPendentes = null;
		List<PedidoVenda> pedPVUltPendentes = null;
		
		int mes = 0;
		String json = "";
		
		switch(req.getParameter("action")){			
			case("pesquisarCaixa"):
				System.out.println("RegraCaixa, PESQUISANDO CAIXA...");				
				
				try{
					if(req.getParameter("codigo") != null){
						System.out.println("RegraCaixa, PESQUISANDO POR CODIGO...");
						codigo = Long.parseLong(req.getParameter("codigo"));
						caixa = new CaixaDAO().buscaCaixaPorCodigo(codigo);
						
						System.out.println("RegraCaixa, PESQUISANDO POR CODIGO, VERIFICANDO SE HA MOVIMENTACOES...");
						
						// VERIFICA SE HÁ MOVIMENTAÇÕES
						if(caixa.getMovimentacoes() != null || !caixa.getMovimentacoes().isEmpty()){
							System.out.println("RegraCaixa, PESQUISANDO POR CODIGO, MOVIMENTACOES VERIFICADAS, HA MOVIMENTACOES...");
							System.out.println("RegraCaixa, PESQUISANDO POR CODIGO, VERIFICANDO SE UM PERIODO FOI INFORMADO...");
							
							// CAIXA E MOVIMENTAÇÃO DO PERÍODO MENSAL INFORMADO
							if(req.getParameter("mes") != null){
								System.out.println("RegraCaixa, PESQUISANDO POR CODIGO, PERIODO VERIFICADO, INFORMADO O MES: " + req.getParameter("mes"));
								
								mes = Integer.parseInt(req.getParameter("mes")) - 1;
								movimentacoes = new ArrayList<Movimentacao>();
								
								if(req.getParameter("tarefa") != null && req.getParameter("tarefa").equals("grafico")){
									/*for(Movimentacao mov : caixa.getMovimentacoes()){
										
										// PEGA OS DADOS SOMENTE DO PERÍODO INFORMADO
										if(mov.getData().get(Calendar.MONTH) >= (mes-12) && mov.getData().get(Calendar.MONTH) <= mes){
											System.out.println("MES: " + mov.getData().MONTH);
											
											// VERIFICA O TIPO DE MOVIMENTACAO - ENTRADA OU SAIDA
											if(mov.getTipoLancamento().equals(Movimentacao.TipoLancamento.ENTRADA)){
												System.out.println("MOVIMENTACAO TIPO ENTRADA");
												
												// CAIXA - ADICIONANDO O VALOR TOTAL DE ENTRADA
												caixaMes.setTotalEntrada(caixaMes.getTotalEntrada().add(mov.getValorLancamento()));										
											}else{
												System.out.println("MOVIMENTACAO TIPO SAIDA");
												
												// CAIXA - ADICIONANDO O VALOR TOTAL DE SAÍDA
												caixaMes.setTotalSaida(caixaMes.getTotalSaida().add(mov.getValorLancamento()));
											}									
											movimentacoes.add(mov);
											// CAIXA - ADICIONANDO O SALDO DO PERÍODO INFORMADO
											caixaMes.setSaldo(caixaMes.getTotalEntrada().subtract(caixaMes.getTotalSaida()));
											
											// CAIXA - ADICIONANDO A MOVIMENTACAO DO PERÍODO INFORMADO
											caixaMes.setMovimentacoes(movimentacoes);
											
											// VERIFICA SE HÁ MOVIMENTAÇÕES DO PERÍODO INFORMADO
											if(!caixaMes.getMovimentacoes().isEmpty()){
												
												// PEGA O JSON CAIXA MENSAL - IDENTIFICADOR: "dataCaixaMes"
												json = montaJsonGrafico("dataCaixaGrafico", caixaMes, mes);
											}else{									
												
												// PEGA O JSON CAIXA VAZIO - IDENTIFICADOR: "dataCaixaMes"
												json = montaJsonVazio("dataCaixaGrafico");
											}
										}
									}
									*/									
								}else{
									for(Movimentacao mov : caixa.getMovimentacoes()){
										
										// PEGA OS DADOS SOMENTE DO PERÍODO INFORMADO
										if(mov.getData().get(Calendar.MONTH) == mes){
											System.out.println("DATA: " + new SimpleDateFormat("dd/MM/yyyy").format(mov.getData().getTime()));
											
											// VERIFICA O TIPO DE MOVIMENTACAO - ENTRADA OU SAIDA
											if(mov.getTipoLancamento().equals(Movimentacao.TipoLancamento.ENTRADA)){
												System.out.println("MOVIMENTACAO TIPO ENTRADA");
												
												// CAIXA - ADICIONANDO O VALOR TOTAL DE ENTRADA
												caixaMes.setTotalEntrada(caixaMes.getTotalEntrada().add(mov.getValorLancamento()));										
											}else{
												System.out.println("MOVIMENTACAO TIPO SAIDA");
												
												// CAIXA - ADICIONANDO O VALOR TOTAL DE SAÍDA
												caixaMes.setTotalSaida(caixaMes.getTotalSaida().add(mov.getValorLancamento()));
											}									
											movimentacoes.add(mov);
										}
									}
									// CAIXA - ADICIONANDO O SALDO DO PERÍODO INFORMADO
									caixaMes.setSaldo(caixaMes.getTotalEntrada().subtract(caixaMes.getTotalSaida()));
									
									// CAIXA - ADICIONANDO A MOVIMENTACAO DO PERÍODO INFORMADO
									caixaMes.setMovimentacoes(movimentacoes);
									
									// VERIFICA SE HÁ MOVIMENTAÇÕES DO PERÍODO INFORMADO
									if(!caixaMes.getMovimentacoes().isEmpty()){
										
										// PEGA O JSON CAIXA MENSAL - IDENTIFICADOR: "dataCaixaMes"
										json = montaJsonCaixa("dataCaixaMes", caixaMes);
									}else{									
										
										// PEGA O JSON CAIXA VAZIO - IDENTIFICADOR: "dataCaixaMes"
										json = montaJsonVazio("dataCaixaMes");
									}
								}
								
							// CAIXA E MOVIMENTAÇÃO DE TODOS OS PERÍODOS
							}else{
								System.out.println("RegraCaixa, PESQUISANDO POR CODIGO, PERIODO VERIFICADO, NAO INFORMADO");
								
								List<Movimentacao> listaOrdenada = caixa.getMovimentacoes();
								movUltLanc = new ArrayList<Movimentacao>();
								pedPCUltPendentes = new PedidoCompraDAO().listaUltimosPendentes(StatusCompra.PENDENTE);
								pedPVUltPendentes = new PedidoVendaDAO().listaUltimosPendentes(StatusVenda.PENDENTE);
								
								// ORDENACAO DAS MOVIMENTAÇÕES POR DATA								
								for(int i = 0; i < listaOrdenada.size(); i++){
									Movimentacao ms = listaOrdenada.get(i);									
									// SE FOR DIFERENTE DO ÚLTIMO ÍNDICE
									if(i != listaOrdenada.size() - 1){				
										// SE ESTA DATA FOR MAIOR QUE A DATA SEGUINTE
										if(ms.getData().after(listaOrdenada.get(i + 1).getData())){
											listaOrdenada.remove(i);				// REMOVE ESTA DATA
											listaOrdenada.add(i + 1, ms);			// ADICIONA ESTA DATA NA POSIÇÃO SEGUINTE
											i = 0;
										}				
									}
								}
								
								// MOSTRA OS ULTIMOS LANÇAMENTOS
								//for(int i = listaOrdenada.size() - 1; i >= listaOrdenada.size() - 5; i--){
									//System.out.println("DATA: " + new SimpleDateFormat("dd/MM/yyyy").format(listaOrdenada.get(i).getData().getTime()));
								//}		
								
								// SE A LISTA TIVER 5 MOVIMENTAÇOES OU MENOS
								if(listaOrdenada.size() <= 5){
									for(int i = 0; i < listaOrdenada.size(); i++){
										movUltLanc.add(listaOrdenada.get(i));
									}
								}else{	// SE A LISTA TIVER MAIS QUE 5 MOVIMENTAÇÕES									
									for(int i = listaOrdenada.size() - 5; i < listaOrdenada.size(); i++){
										movUltLanc.add(listaOrdenada.get(i));
									}
								}
								/*
								if(caixa.getMovimentacoes().size() <= 5){
									for(int i = 0; i < caixa.getMovimentacoes().size(); i++){
										movUltLanc.add(caixa.getMovimentacoes().get(i));
									}
								}else{
									for(int i = caixa.getMovimentacoes().size() - 5; i < caixa.getMovimentacoes().size(); i++){
										movUltLanc.add(caixa.getMovimentacoes().get(i));
									}
								}								
								*/
// ---------------------------------------- CHAMA O montaJsonCaixaUltimo-------------------------------------------------------------------------------------------------------
								
								// PEGA O JSON CAIXA - IDENTIFICADOR: "dataCaixa"
								json = montaJsonCaixaUltimo("dataCaixa", caixa, movUltLanc, pedPCUltPendentes, pedPVUltPendentes);						
							}
						}else{
							System.out.println("RegraCaixa, PESQUISANDO POR CODIGO, MOVIMENTACOES VERIFICADAS, NAO HA MOVIMENTACOES...");
							
							// PEGA O JSON VAZIO - IDENTIFICADOR: "dataCaixa"
							json = montaJsonVazio("dataCaixa");
						}
						System.out.println("RegraCaixa, PESQUISANDO POR CODIGO, PESQUISA CONCLUIDA.");
						
						// RESPOSTA TIPO JSON						
						resp.setContentType("application/json");				
						resp.setCharacterEncoding("UTF-8");
						resp.getWriter().write(json);
					}else{
						resp.setContentType("text/plain");				
						resp.setCharacterEncoding("UTF-8");
						resp.getWriter().write("Não há um caixa disponível");
					}					
				}catch(NumberFormatException e){
					e.printStackTrace();
					resp.setContentType("text/plain");				
					resp.setCharacterEncoding("UTF-8");
					resp.getWriter().write("Falha ao verificar o código do caixa");					
				}	
				break;			
		}
	}

	// MONTA O JSON DAS MOVIMENTACOES
	private String montaJsonMovimentacao(List<Movimentacao> movs){
		System.out.println("RegraCaixa, PESQUISANDO POR CODIGO, MONTANDO O JSON DE MOVIMENTACOES...");
		
		String jsonMovs = "";
		int controle = 1;
		for(Movimentacao m : movs){
			
			// CASO SEJA A PRIMEIRA MOVIMENTACAO CHAVES {} SEM VIRGULA		
			if(controle == 1){
				
				// VERIFICA O TIPO DE MOVIMENTACAO - ENTRADA OU SAIDA
				if(m.getTipoLancamento().equals(Movimentacao.TipoLancamento.ENTRADA)){
					System.out.println("MOVIMENTACAO TIPO ENTRADA");					
					jsonMovs += "{\"nLancamento\":\"" + m.getnLancamento() + "\","
						+ "\"data\":\"" + new SimpleDateFormat("dd/MM/yyyy").format(m.getData().getTime()) + "\","
						+ "\"tipoLancamento\":\"" + m.getTipoLancamento().toString() + "\","
						+ "\"descricao\":\"" + "Pedido Venda No. " + m.getPedidoVenda().getnPedido() + " do Cliente " + m.getPedidoVenda().getCliente().getEmpresa() + "\","
						+ "\"valorLancamento\":\"" + m.getValorLancamento() + "\""											
						+ "}";
				}else{
					if(m.getPedidoCompra().getFornecedor().getTipo().equals(Tipo.CATADOR)){
						jsonMovs += "{\"nLancamento\":\"" + m.getnLancamento() + "\","
							+ "\"data\":\"" + new SimpleDateFormat("dd/MM/yyyy").format(m.getData().getTime()) + "\","
							+ "\"tipoLancamento\":\"" + m.getTipoLancamento().toString() + "\","
							+ "\"descricao\":\"" + "Pedido Compra No. " + m.getPedidoCompra().getnPedido() + " do Catador: " + m.getPedidoCompra().getFornecedor().getContato() + "\","
							+ "\"valorLancamento\":\"" + m.getValorLancamento() + "\""											
							+ "}";
					}else{
						jsonMovs += "{\"nLancamento\":\"" + m.getnLancamento() + "\","
								+ "\"data\":\"" + new SimpleDateFormat("dd/MM/yyyy").format(m.getData().getTime()) + "\","
								+ "\"tipoLancamento\":\"" + m.getTipoLancamento().toString() + "\","
								+ "\"descricao\":\"" + "Pedido Compra No. " + m.getPedidoCompra().getnPedido() + " do Fornecedor " + m.getPedidoCompra().getFornecedor().getEmpresa() + "\","
								+ "\"valorLancamento\":\"" + m.getValorLancamento() + "\""											
								+ "}";
					}
				}				
				controle = 2;
				
			// CASO NAO SEJA A PRIMEIRA MOVIMENTACAO CHAVES ,{} INICIAM COM VIRGULA
			}else{
				
				// VERIFICA O TIPO DE MOVIMENTACAO - ENTRADA OU SAIDA
				if(m.getTipoLancamento().equals(Movimentacao.TipoLancamento.ENTRADA)){
					System.out.println("MOVIMENTACAO TIPO ENTRADA");					
					jsonMovs += "," + "{\"nLancamento\":\"" + m.getnLancamento() + "\","
						+ "\"data\":\"" + new SimpleDateFormat("dd/MM/yyyy").format(m.getData().getTime()) + "\","
						+ "\"tipoLancamento\":\"" + m.getTipoLancamento().toString() + "\","
						+ "\"descricao\":\"" + "Pedido Venda No. " + m.getPedidoVenda().getnPedido() + " do Cliente " + m.getPedidoVenda().getCliente().getEmpresa() + "\","
						+ "\"valorLancamento\":\"" + m.getValorLancamento() + "\""											
						+ "}";
				}else{
					if(m.getPedidoCompra().getFornecedor().getTipo().equals(Tipo.CATADOR)){
						jsonMovs += "," + "{\"nLancamento\":\"" + m.getnLancamento() + "\","
						+ "\"data\":\"" + new SimpleDateFormat("dd/MM/yyyy").format(m.getData().getTime()) + "\","
						+ "\"tipoLancamento\":\"" + m.getTipoLancamento().toString() + "\","
						+ "\"descricao\":\"" + "Pedido Compra No. " + m.getPedidoCompra().getnPedido() + " do Catador: " + m.getPedidoCompra().getFornecedor().getContato() + "\","
						+ "\"valorLancamento\":\"" + m.getValorLancamento() + "\""											
						+ "}";
					}else{
						jsonMovs += "," + "{\"nLancamento\":\"" + m.getnLancamento() + "\","
								+ "\"data\":\"" + new SimpleDateFormat("dd/MM/yyyy").format(m.getData().getTime()) + "\","
								+ "\"tipoLancamento\":\"" + m.getTipoLancamento().toString() + "\","
								+ "\"descricao\":\"" + "Pedido Compra No. " + m.getPedidoCompra().getnPedido() + " do Fornecedor " + m.getPedidoCompra().getFornecedor().getEmpresa() + "\","
								+ "\"valorLancamento\":\"" + m.getValorLancamento() + "\""											
								+ "}";
					}
				}
			}
		}
		System.out.println("RegraCaixa, PESQUISANDO POR CODIGO, JSON DE MOVIMENTACOES MONTADO...");
		return jsonMovs;
	}
	
	// MONTA O JSON DO CAIXA
	private String montaJsonCaixa(String identificador, Caixa caixa){
		System.out.println("RegraCaixa, PESQUISANDO POR CODIGO, MONTANDO O JSON DO CAIXA...");
		
		String jsonCaixa = "";
		String jsonMovs = montaJsonMovimentacao(caixa.getMovimentacoes());
		
		jsonCaixa = "{\"" + identificador + "\":[{"						
				+ "\"totalEntrada\":\"" + caixa.getTotalEntrada() + "\","
				+ "\"totalSaida\":\"" + caixa.getTotalSaida() + "\","
				+ "\"saldo\":\"" + caixa.getSaldo() + "\","
				+ "\"movimentacoes\":[" + jsonMovs + "]"
				+ "}]}";
		System.out.println("RegraCaixa, PESQUISANDO POR CODIGO, JSON DO CAIXA MONTADO...");
		return jsonCaixa;
	}
	
	// MONTA O JSON DO CAIXA - PÁG. DASHBOARD
	private String montaJsonCaixaUltimo(String identificador, Caixa caixa, List<Movimentacao> movUltLanc, List<PedidoCompra> pedPCUltPendentes, List<PedidoVenda> pedPVUltPendentes){
		System.out.println("RegraCaixa, PESQUISANDO POR CODIGO, MONTANDO O JSON DO CAIXA...");
			
		String jsonCaixa = "";
		String jsonMovs = montaJsonMovimentacao(caixa.getMovimentacoes());
		String jsonUltMovs = montaJsonUltMovimentacao(movUltLanc);
		String jsonCompras = montaJsonCompras(pedPCUltPendentes);
		String jsonVendas = montaJsonVendas(pedPVUltPendentes);
			
		jsonCaixa = "{\"" + identificador + "\":[{"						
				+ "\"totalEntrada\":\"" + caixa.getTotalEntrada() + "\","
				+ "\"totalSaida\":\"" + caixa.getTotalSaida() + "\","
				+ "\"saldo\":\"" + caixa.getSaldo() + "\","
				+ "\"movimentacoes\":[" + jsonMovs + "],"
				+ "\"ultMovimentacoes\":[" + jsonUltMovs + "],"
				+ "\"compras\":[" + jsonCompras + "],"
				+ "\"vendas\":[" + jsonVendas + "]"
				+ "}]}";
		System.out.println("RegraCaixa, PESQUISANDO POR CODIGO, JSON DO CAIXA MONTADO...");
		return jsonCaixa;
	}
	
	private String montaJsonUltMovimentacao(List<Movimentacao> movUltLanc) {
		System.out.println("RegraCaixa, PESQUISANDO POR CODIGO, MONTANDO O JSON DAS ULTIMAS MOVIMENTACOES...");
		
		String jsonUltMovs = "";
		int controle = 1;
		for(Movimentacao m : movUltLanc){
			
			// CASO SEJA A PRIMEIRA MOVIMENTACAO CHAVES {} SEM VIRGULA		
			if(controle == 1){
				
				// VERIFICA O TIPO DE MOVIMENTACAO - ENTRADA OU SAIDA
				if(m.getTipoLancamento().equals(Movimentacao.TipoLancamento.ENTRADA)){
					System.out.println("MOVIMENTACAO TIPO ENTRADA");					
					jsonUltMovs += "{\"nLancamento\":\"" + m.getnLancamento() + "\","
						+ "\"data\":\"" + new SimpleDateFormat("dd/MM/yyyy").format(m.getData().getTime()) + "\","
						+ "\"tipoLancamento\":\"" + m.getTipoLancamento().toString() + "\","
						+ "\"descricao\":\"" + "Pedido Venda No. " + m.getPedidoVenda().getnPedido() + " do Cliente " + m.getPedidoVenda().getCliente().getEmpresa() + "\","
						+ "\"valorLancamento\":\"" + m.getValorLancamento() + "\""											
						+ "}";
				}else{
					if(m.getPedidoCompra().getFornecedor().getTipo().equals(Tipo.CATADOR)){
						jsonUltMovs += "{\"nLancamento\":\"" + m.getnLancamento() + "\","
							+ "\"data\":\"" + new SimpleDateFormat("dd/MM/yyyy").format(m.getData().getTime()) + "\","
							+ "\"tipoLancamento\":\"" + m.getTipoLancamento().toString() + "\","
							+ "\"descricao\":\"" + "Pedido Compra No. " + m.getPedidoCompra().getnPedido() + " do Catador: " + m.getPedidoCompra().getFornecedor().getContato() + "\","
							+ "\"valorLancamento\":\"" + m.getValorLancamento() + "\""											
							+ "}";
					}else{
						jsonUltMovs += "{\"nLancamento\":\"" + m.getnLancamento() + "\","
								+ "\"data\":\"" + new SimpleDateFormat("dd/MM/yyyy").format(m.getData().getTime()) + "\","
								+ "\"tipoLancamento\":\"" + m.getTipoLancamento().toString() + "\","
								+ "\"descricao\":\"" + "Pedido Compra No. " + m.getPedidoCompra().getnPedido() + " do Fornecedor " + m.getPedidoCompra().getFornecedor().getEmpresa() + "\","
								+ "\"valorLancamento\":\"" + m.getValorLancamento() + "\""											
								+ "}";
					}
				}				
				controle = 2;
				
			// CASO NAO SEJA A PRIMEIRA MOVIMENTACAO CHAVES ,{} INICIAM COM VIRGULA
			}else{
				
				// VERIFICA O TIPO DE MOVIMENTACAO - ENTRADA OU SAIDA
				if(m.getTipoLancamento().equals(Movimentacao.TipoLancamento.ENTRADA)){
					System.out.println("MOVIMENTACAO TIPO ENTRADA");					
					jsonUltMovs += "," + "{\"nLancamento\":\"" + m.getnLancamento() + "\","
						+ "\"data\":\"" + new SimpleDateFormat("dd/MM/yyyy").format(m.getData().getTime()) + "\","
						+ "\"tipoLancamento\":\"" + m.getTipoLancamento().toString() + "\","
						+ "\"descricao\":\"" + "Pedido Venda No. " + m.getPedidoVenda().getnPedido() + " do Cliente " + m.getPedidoVenda().getCliente().getEmpresa() + "\","
						+ "\"valorLancamento\":\"" + m.getValorLancamento() + "\""											
						+ "}";
				}else{
					if(m.getPedidoCompra().getFornecedor().getTipo().equals(Tipo.CATADOR)){
						jsonUltMovs += "," + "{\"nLancamento\":\"" + m.getnLancamento() + "\","
						+ "\"data\":\"" + new SimpleDateFormat("dd/MM/yyyy").format(m.getData().getTime()) + "\","
						+ "\"tipoLancamento\":\"" + m.getTipoLancamento().toString() + "\","
						+ "\"descricao\":\"" + "Pedido Compra No. " + m.getPedidoCompra().getnPedido() + " do Catador: " + m.getPedidoCompra().getFornecedor().getContato() + "\","
						+ "\"valorLancamento\":\"" + m.getValorLancamento() + "\""											
						+ "}";
					}else{
						jsonUltMovs += "," + "{\"nLancamento\":\"" + m.getnLancamento() + "\","
								+ "\"data\":\"" + new SimpleDateFormat("dd/MM/yyyy").format(m.getData().getTime()) + "\","
								+ "\"tipoLancamento\":\"" + m.getTipoLancamento().toString() + "\","
								+ "\"descricao\":\"" + "Pedido Compra No. " + m.getPedidoCompra().getnPedido() + " do Fornecedor " + m.getPedidoCompra().getFornecedor().getEmpresa() + "\","
								+ "\"valorLancamento\":\"" + m.getValorLancamento() + "\""											
								+ "}";
					}
				}
			}
		}
		System.out.println("RegraCaixa, PESQUISANDO POR CODIGO, JSON DAS ULTIMAS MOVIMENTACOES MONTADO...");
		return jsonUltMovs;
	}

	private String montaJsonCompras(List<PedidoCompra> compras) {
		System.out.println("RegraCaixa, PESQUISANDO POR CODIGO, MONTANDO O JSON DE COMPRAS...");
		
		String jsonCompras = "";
		int controle = 1;
		
		if(compras == null){
			jsonCompras = "{\"nPedido\":\"0\"}";
		}else{
			for(PedidoCompra pc : compras){
				
				// CASO SEJA A PRIMEIRA MOVIMENTACAO CHAVES {} SEM VIRGULA		
				if(controle == 1){				
					if(pc.getFornecedor().getTipo().equals(Tipo.CATADOR)){
						jsonCompras = "{\"nPedido\":\"" + pc.getnPedido() + "\","
								+ "\"data\":\"" + new SimpleDateFormat("dd/MM/yyyy").format(pc.getDataAbertura().getTime()) + "\","
								+ "\"descricao\":\"" + "Catador: " + pc.getFornecedor().getContato() + "\","
								+ "\"valorPedido\":\"" + pc.getValorTotal() + "\""											
								+ "}";
					}else{
						jsonCompras = "{\"nPedido\":\"" + pc.getnPedido() + "\","
								+ "\"data\":\"" + new SimpleDateFormat("dd/MM/yyyy").format(pc.getDataAbertura().getTime()) + "\","
								+ "\"descricao\":\"" + pc.getFornecedor().getEmpresa() + "\","
								+ "\"valorPedido\":\"" + pc.getValorTotal() + "\""											
								+ "}";
					}
					controle = 2;
					
				// CASO NAO SEJA A PRIMEIRA COMPRA
				}else{
					if(pc.getFornecedor().getTipo().equals(Tipo.CATADOR)){
						jsonCompras += "," + "{\"nPedido\":\"" + pc.getnPedido() + "\","
								+ "\"data\":\"" + new SimpleDateFormat("dd/MM/yyyy").format(pc.getDataAbertura().getTime()) + "\","
								+ "\"descricao\":\"" + "Catador: " + pc.getFornecedor().getContato() + "\","
								+ "\"valorPedido\":\"" + pc.getValorTotal() + "\""											
								+ "}";
					}else{
						jsonCompras += "," + "{\"nPedido\":\"" + pc.getnPedido() + "\","
								+ "\"data\":\"" + new SimpleDateFormat("dd/MM/yyyy").format(pc.getDataAbertura().getTime()) + "\","
								+ "\"descricao\":\"" + pc.getFornecedor().getEmpresa() + "\","
								+ "\"valorPedido\":\"" + pc.getValorTotal() + "\""											
								+ "}";
					}
				}
			}
		}		
		return jsonCompras;
	}
	
	private String montaJsonVendas(List<PedidoVenda> vendas) {
		System.out.println("RegraCaixa, PESQUISANDO POR CODIGO, MONTANDO O JSON DE VENDAS...");
		
		String jsonVendas = "";
		int controle = 1;
		
		if(vendas == null){
			jsonVendas = "{\"nPedido\":\"0\"}";
		}else{
			for(PedidoVenda pv : vendas){
				
				// CASO SEJA A PRIMEIRA MOVIMENTACAO CHAVES {} SEM VIRGULA		
				if(controle == 1){				
					jsonVendas = "{\"nPedido\":\"" + pv.getnPedido() + "\","
							+ "\"data\":\"" + new SimpleDateFormat("dd/MM/yyyy").format(pv.getDataAbertura().getTime()) + "\","
							+ "\"descricao\":\"" + pv.getCliente().getEmpresa() + "\","
							+ "\"valorPedido\":\"" + pv.getValorTotal() + "\""											
							+ "}";
					
					controle = 2;
					
				// CASO NAO SEJA A PRIMEIRA COMPRA
				}else{				
					jsonVendas += "," + "{\"nPedido\":\"" + pv.getnPedido() + "\","
							+ "\"data\":\"" + new SimpleDateFormat("dd/MM/yyyy").format(pv.getDataAbertura().getTime()) + "\","
							+ "\"descricao\":\"" + pv.getCliente().getEmpresa() + "\","
							+ "\"valorPedido\":\"" + pv.getValorTotal() + "\""											
							+ "}";
					
				}
			}
		}		
		return jsonVendas;
	}

	// MONTA O JSON VAZIO - CASO NÃO HAJA MOVIMENTACOES
	private String montaJsonVazio(String identificador) {
		System.out.println("RegraCaixa, PESQUISANDO POR CODIGO, MONTANDO O JSON VAZIO...");		
		String jsonVazio = "{\"" + identificador + "\":\"null\"}";
		System.out.println("RegraCaixa, PESQUISANDO POR CODIGO, JSON VAZIO MONTADO...");
		return jsonVazio;
	}
	
	
	
	
	/*
	// MONTA O JSON DAS MOVIMENTACOES
	private String montaJsonMovimentacaoGrafico(List<Movimentacao> movs, int mes){
		System.out.println("RegraCaixa, PESQUISANDO POR CODIGO, MONTANDO O JSON DE MOVIMENTACOES...");
			
		String jsonMovs = "";						
		
		BigDecimal tEntrada1 = new BigDecimal(0);
		BigDecimal tSaida1 = new BigDecimal(0);	
		String mes1 = "{\"mes\":\"\","
				+ "\"totalEntrada\":\"" + tEntrada1 + "\","
				+ "\"totalSaida\":\"" + tSaida1 + "\""																		
				+ "}";		
		
		BigDecimal tEntrada2 = new BigDecimal(0);
		BigDecimal tSaida2 = new BigDecimal(0);
		String mes2 = "{\"mes\":\"\","
				+ "\"totalEntrada\":\"" + tEntrada2 + "\","
				+ "\"totalSaida\":\"" + tSaida2 + "\""																		
				+ "}";
		
		BigDecimal tEntrada3 = new BigDecimal(0);
		BigDecimal tSaida3 = new BigDecimal(0);
		String mes3 = "{\"mes\":\"\","
				+ "\"totalEntrada\":\"" + tEntrada3 + "\","
				+ "\"totalSaida\":\"" + tSaida3 + "\""																		
				+ "}";
		
		BigDecimal tEntrada4 = new BigDecimal(0);
		BigDecimal tSaida4 = new BigDecimal(0);
		String mes4 = "{\"mes\":\"\","
				+ "\"totalEntrada\":\"" + tEntrada4 + "\","
				+ "\"totalSaida\":\"" + tSaida4 + "\""																		
				+ "}";
		
		BigDecimal tEntrada5 = new BigDecimal(0);
		BigDecimal tSaida5 = new BigDecimal(0);
		String mes5 = "{\"mes\":\"\","
				+ "\"totalEntrada\":\"" + tEntrada5 + "\","
				+ "\"totalSaida\":\"" + tSaida5 + "\""																		
				+ "}";
		
		BigDecimal tEntrada6 = new BigDecimal(0);
		BigDecimal tSaida6 = new BigDecimal(0);
		String mes6 = "{\"mes\":\"\","
				+ "\"totalEntrada\":\"" + tEntrada6 + "\","
				+ "\"totalSaida\":\"" + tSaida6 + "\""																		
				+ "}";
		
		BigDecimal tEntrada7 = new BigDecimal(0);
		BigDecimal tSaida7 = new BigDecimal(0);
		String mes7 = "{\"mes\":\"\","
				+ "\"totalEntrada\":\"" + tEntrada7 + "\","
				+ "\"totalSaida\":\"" + tSaida7 + "\""																		
				+ "}";
		
		BigDecimal tEntrada8 = new BigDecimal(0);
		BigDecimal tSaida8 = new BigDecimal(0);
		String mes8 = "{\"mes\":\"\","
				+ "\"totalEntrada\":\"" + tEntrada8 + "\","
				+ "\"totalSaida\":\"" + tSaida8 + "\""																		
				+ "}";
		
		BigDecimal tEntrada9 = new BigDecimal(0);
		BigDecimal tSaida9 = new BigDecimal(0);
		String mes9 = "{\"mes\":\"\","
				+ "\"totalEntrada\":\"" + tEntrada9 + "\","
				+ "\"totalSaida\":\"" + tSaida9 + "\""																		
				+ "}";
		
		BigDecimal tEntrada10 = new BigDecimal(0);
		BigDecimal tSaida10 = new BigDecimal(0);
		String mes10 = "{\"mes\":\"\","
				+ "\"totalEntrada\":\"" + tEntrada10 + "\","
				+ "\"totalSaida\":\"" + tSaida10 + "\""																		
				+ "}";
		
		BigDecimal tEntrada11 = new BigDecimal(0);
		BigDecimal tSaida11 = new BigDecimal(0);
		String mes11 = "{\"mes\":\"\","
				+ "\"totalEntrada\":\"" + tEntrada11 + "\","
				+ "\"totalSaida\":\"" + tSaida11 + "\""																		
				+ "}";
		
		BigDecimal tEntrada12 = new BigDecimal(0);
		BigDecimal tSaida12 = new BigDecimal(0);
		String mes12 = "{\"mes\":\"\","
				+ "\"totalEntrada\":\"" + tEntrada12 + "\","
				+ "\"totalSaida\":\"" + tSaida12 + "\""																		
				+ "}";
		
		BigDecimal tEntrada13 = new BigDecimal(0);
		BigDecimal tSaida13 = new BigDecimal(0);
		String mes13 = "{\"mes\":\"\","
				+ "\"totalEntrada\":\"" + tEntrada13 + "\","
				+ "\"totalSaida\":\"" + tSaida13 + "\""																		
				+ "}";
		
		int cont = 0;		
				
		for(Movimentacao m : movs){
			
			switch(m.getData().MONTH){
				case (mes-12):					
					if(m.getTipoLancamento() == TipoLancamento.ENTRADA)
						tEntrada1 = tEntrada1.add(m.getValorLancamento()); 
					else
						tSaida1 = tSaida1.add(m.getValorLancamento());					
					mes1 = "{\"mes\":\"" + m.getData().MONTH + "\","
							+ "\"totalEntrada\":\"" + tEntrada1 + "\","
							+ "\"totalSaida\":\"" + tSaida1 + "\""																		
							+ "}";
					if(cont == 0){
						cont++;
					}
					break;
				case (mes-11):					
					if(m.getTipoLancamento() == TipoLancamento.ENTRADA)
						tEntrada2 = tEntrada2.add(m.getValorLancamento()); 
					else
						tSaida2 = tSaida2.add(m.getValorLancamento());
					mes2 = "{\"mes\":\"" + m.getData().MONTH + "\","
							+ "\"totalEntrada\":\"" + tEntrada2 + "\","
							+ "\"totalSaida\":\"" + tSaida2 + "\""																		
							+ "}";
					if(cont == 0){
						cont++;
					}
					break;
				case (mes-10):
					if(m.getTipoLancamento() == TipoLancamento.ENTRADA)
						tEntrada3 = tEntrada3.add(m.getValorLancamento()); 
					else
						tSaida3 = tSaida3.add(m.getValorLancamento());
					mes3 = "{\"mes\":\"" + m.getData().MONTH + "\","
							+ "\"totalEntrada\":\"" + tEntrada3 + "\","
							+ "\"totalSaida\":\"" + tSaida3 + "\""																		
							+ "}";
					if(cont == 0){
						cont++;
					}
					break;
				case (mes-9):
					if(m.getTipoLancamento() == TipoLancamento.ENTRADA)
						tEntrada4 = tEntrada4.add(m.getValorLancamento()); 
					else
						tSaida4 = tSaida4.add(m.getValorLancamento());
					mes4 = "{\"mes\":\"" + m.getData().MONTH + "\","
							+ "\"totalEntrada\":\"" + tEntrada4 + "\","
							+ "\"totalSaida\":\"" + tSaida4 + "\""																		
							+ "}";
					if(cont == 0){
						cont++;
					}
					break;
				case (mes-8):
					if(m.getTipoLancamento() == TipoLancamento.ENTRADA)
						tEntrada5 = tEntrada5.add(m.getValorLancamento()); 
					else
						tSaida5 = tSaida5.add(m.getValorLancamento());
					mes5 = "{\"mes\":\"" + m.getData().MONTH + "\","
							+ "\"totalEntrada\":\"" + tEntrada5 + "\","
							+ "\"totalSaida\":\"" + tSaida5 + "\""																		
							+ "}";
					if(cont == 0){
						cont++;
					}
					break;
				case (mes-7):
					if(m.getTipoLancamento() == TipoLancamento.ENTRADA)
						tEntrada6 = tEntrada6.add(m.getValorLancamento()); 
					else
						tSaida6 = tSaida6.add(m.getValorLancamento());
					mes6 = "{\"mes\":\"" + m.getData().MONTH + "\","
							+ "\"totalEntrada\":\"" + tEntrada6 + "\","
							+ "\"totalSaida\":\"" + tSaida6 + "\""																		
							+ "}";
					if(cont == 0){
						cont++;
					}
					break;
				case (mes-6):
					if(m.getTipoLancamento() == TipoLancamento.ENTRADA)
						tEntrada7 = tEntrada7.add(m.getValorLancamento()); 
					else
						tSaida7 = tSaida7.add(m.getValorLancamento());
					mes7 = "{\"mes\":\"" + m.getData().MONTH + "\","
							+ "\"totalEntrada\":\"" + tEntrada7 + "\","
							+ "\"totalSaida\":\"" + tSaida7 + "\""																		
							+ "}";
					if(cont == 0){
						cont++;
					}
					break;
				case (mes-5):
					if(m.getTipoLancamento() == TipoLancamento.ENTRADA)
						tEntrada8 = tEntrada8.add(m.getValorLancamento()); 
					else
						tSaida8 = tSaida8.add(m.getValorLancamento());
					mes8 = "{\"mes\":\"" + m.getData().MONTH + "\","
							+ "\"totalEntrada\":\"" + tEntrada8 + "\","
							+ "\"totalSaida\":\"" + tSaida8 + "\""																		
							+ "}";
					if(cont == 0){
						cont++;
					}
					break;
				case (mes-4):
					if(m.getTipoLancamento() == TipoLancamento.ENTRADA)
						tEntrada9 = tEntrada9.add(m.getValorLancamento()); 
					else
						tSaida9 = tSaida9.add(m.getValorLancamento());
					mes9 = "{\"mes\":\"" + m.getData().MONTH + "\","
							+ "\"totalEntrada\":\"" + tEntrada9 + "\","
							+ "\"totalSaida\":\"" + tSaida9 + "\""																		
							+ "}";
					if(cont == 0){
						cont++;
					}
					break;
				case (mes-3):
					if(m.getTipoLancamento() == TipoLancamento.ENTRADA)
						tEntrada10 = tEntrada10.add(m.getValorLancamento()); 
					else
						tSaida10 = tSaida10.add(m.getValorLancamento());
					mes10 = "{\"mes\":\"" + m.getData().MONTH + "\","
							+ "\"totalEntrada\":\"" + tEntrada10 + "\","
							+ "\"totalSaida\":\"" + tSaida10 + "\""																		
							+ "}";
					if(cont == 0){
						cont++;
					}
					break;
				case (mes-2):
					if(m.getTipoLancamento() == TipoLancamento.ENTRADA)
						tEntrada11 = tEntrada11.add(m.getValorLancamento()); 
					else
						tSaida11 = tSaida11.add(m.getValorLancamento());
					mes11 = "{\"mes\":\"" + m.getData().MONTH + "\","
							+ "\"totalEntrada\":\"" + tEntrada11 + "\","
							+ "\"totalSaida\":\"" + tSaida11 + "\""																		
							+ "}";
					if(cont == 0){
						cont++;
					}
					break;
				case (mes-1):
					if(m.getTipoLancamento() == TipoLancamento.ENTRADA)
						tEntrada12 = tEntrada12.add(m.getValorLancamento()); 
					else
						tSaida12 = tSaida12.add(m.getValorLancamento());
					mes12 = "{\"mes\":\"" + m.getData().MONTH + "\","
							+ "\"totalEntrada\":\"" + tEntrada12 + "\","
							+ "\"totalSaida\":\"" + tSaida12 + "\""																		
							+ "}";
					if(cont == 0){
						cont++;
					}
					break;
				case (mes):
					if(m.getTipoLancamento() == TipoLancamento.ENTRADA)
						tEntrada13 = tEntrada13.add(m.getValorLancamento()); 
					else
						tSaida13 = tSaida13.add(m.getValorLancamento());
					mes13 = "{\"mes\":\"" + m.getData().MONTH + "\","
							+ "\"totalEntrada\":\"" + tEntrada13 + "\","
							+ "\"totalSaida\":\"" + tSaida13 + "\""																		
							+ "}";
					if(cont == 0){
						cont++;
					}
					break;
				default:
					break;
			}
		}
		
		jsonMovs = mes1 + ","
				+ mes2 + ","
				+ mes3 + ","
				+ mes4 + ","
				+ mes5 + ","
				+ mes6 + ","
				+ mes7 + ","
				+ mes8 + ","
				+ mes9 + ","
				+ mes10 + ","
				+ mes11 + ","
				+ mes12 + ","
				+ mes13;
			
		System.out.println("RegraCaixa, PESQUISANDO POR CODIGO, JSON DE MOVIMENTACOES MONTADO...");
		return jsonMovs;
	}
		
	// MONTA O JSON GRAFICO
	private String montaJsonGrafico(String identificador, Caixa caixa, int mes){
		System.out.println("RegraCaixa, PESQUISANDO POR CODIGO, MONTANDO O JSON DO GRAFICO...");
			
		String jsonGrafico = "";
		String jsonMovs = montaJsonMovimentacaoGrafico(caixa.getMovimentacoes(), mes);
		jsonGrafico = "{\"" + identificador + "\":[" + jsonMovs + "]}";
		System.out.println("RegraCaixa, PESQUISANDO POR CODIGO, JSON DO CAIXA MONTADO...");
		return jsonGrafico;
	}
	*/
	
	
}
