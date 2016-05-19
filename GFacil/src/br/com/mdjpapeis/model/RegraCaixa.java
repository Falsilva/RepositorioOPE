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
import br.com.mdjpapeis.entity.Caixa;
import br.com.mdjpapeis.entity.Movimentacao;

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
							// CAIXA E MOVIMENTAÇÃO DE TODOS OS PERÍODOS
							}else{
								System.out.println("RegraCaixa, PESQUISANDO POR CODIGO, PERIODO VERIFICADO, NAO INFORMADO");
								
								// PEGA O JSON CAIXA - IDENTIFICADOR: "dataCaixa"
								json = montaJsonCaixa("dataCaixa", caixa);						
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
					jsonMovs += "{\"nLancamento\":\"" + m.getnLancamento() + "\","
						+ "\"data\":\"" + new SimpleDateFormat("dd/MM/yyyy").format(m.getData().getTime()) + "\","
						+ "\"tipoLancamento\":\"" + m.getTipoLancamento().toString() + "\","
						+ "\"descricao\":\"" + "Pedido Compra No. " + m.getPedidoCompra().getnPedido() + " do Fornecedor " + m.getPedidoCompra().getFornecedor().getEmpresa() + "\","
						+ "\"valorLancamento\":\"" + m.getValorLancamento() + "\""											
						+ "}";
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
					jsonMovs += "," + "{\"nLancamento\":\"" + m.getnLancamento() + "\","
						+ "\"data\":\"" + new SimpleDateFormat("dd/MM/yyyy").format(m.getData().getTime()) + "\","
						+ "\"tipoLancamento\":\"" + m.getTipoLancamento().toString() + "\","
						+ "\"descricao\":\"" + "Pedido Compra No. " + m.getPedidoCompra().getnPedido() + " do Fornecedor " + m.getPedidoCompra().getFornecedor().getEmpresa() + "\","
						+ "\"valorLancamento\":\"" + m.getValorLancamento() + "\""											
						+ "}";
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
	
	// MONTA O JSON VAZIO - CASO NÃO HAJA MOVIMENTACOES
	private String montaJsonVazio(String identificador) {
		System.out.println("RegraCaixa, PESQUISANDO POR CODIGO, MONTANDO O JSON VAZIO...");		
		String jsonVazio = "{\"" + identificador + "\":\"null\"}";
		System.out.println("RegraCaixa, PESQUISANDO POR CODIGO, JSON VAZIO MONTADO...");
		return jsonVazio;
	}
}
