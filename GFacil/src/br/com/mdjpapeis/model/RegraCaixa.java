package br.com.mdjpapeis.model;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
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
		int ano = 0;
		String json = "";
		
		switch(req.getParameter("action")){			
			case("pesquisarCaixa"):
				System.out.println("1.RegraCaixa, PESQUISANDO CAIXA...");				
				
				try{
					if(req.getParameter("codigo") != null){
						System.out.println("2.RegraCaixa, PESQUISANDO POR CODIGO...");
						codigo = Long.parseLong(req.getParameter("codigo"));
						caixa = new CaixaDAO().buscaCaixaPorCodigo(codigo);
						
						// VERIFICA SE HÁ MOVIMENTAÇÕES
						if(caixa.getMovimentacoes() != null || !caixa.getMovimentacoes().isEmpty()){
							System.out.println("3.RegraCaixa, PESQUISANDO POR CODIGO, MOVIMENTACOES VERIFICADAS, HA MOVIMENTACOES...");
							System.out.println("RegraCaixa, PESQUISANDO POR CODIGO, VERIFICANDO SE UM PERIODO FOI INFORMADO...");
							
							// MES E ANO - NÃO SÃO NULOS - FORAM INFORMADOS - A REQUISIÇÃO VEIO DA PÁG. CAIXA
							// CAIXA E MOVIMENTAÇÃO DO PERÍODO MENSAL INFORMADO
							if(req.getParameter("mes") != null && req.getParameter("ano") != null){
								System.out.println("3.1.if.RegraCaixa, 1. PESQUISANDO POR CODIGO, PERIODO VERIFICADO, MES: " + req.getParameter("mes") + " ANO: " + req.getParameter("ano"));
								
								mes = Integer.parseInt(req.getParameter("mes")) - 1;
								ano = Integer.parseInt(req.getParameter("ano"));
								movimentacoes = new ArrayList<Movimentacao>();
								
								/*if(req.getParameter("tarefa") != null && req.getParameter("tarefa").equals("grafico")){
									for(Movimentacao mov : caixa.getMovimentacoes()){
										
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
																		
								}else{*/
									System.out.println("-------------------------------- INICIO - PEGANDO AS MOVIMENTACOES DO PERIODO ---------------------------------------");
									for(Movimentacao mov : caixa.getMovimentacoes()){
										System.out.println("3.1.if.1.for.Linha 123 - ENTROU NA LISTA DE MOVIMENTACOES DO CAIXA");
										// VERIFICA O ANO INFORMADO
										if(mov.getData().get(Calendar.YEAR) == ano){											
											System.out.println("3.1.if.1.for.2.if.Linha 126 - ENTROU IF ANO(" + ano + ") = ANO INFORMADO (" + mov.getData().get(Calendar.YEAR) + ")");
											// VERIFICA O MÊS INFORMADO
											if(mov.getData().get(Calendar.MONTH) == mes){
												System.out.println("3.1.if.1.for.3.if.Linha 129 - ENTROU IF MES(" + mes + ") = MES INFORMADO (" + mov.getData().get(Calendar.MONTH) + ")");
												//System.out.println("Linha 130, DATA: " + new SimpleDateFormat("dd/MM/yyyy").format(mov.getData().getTime()));
												
												// VERIFICA O TIPO DE MOVIMENTACAO - ENTRADA OU SAIDA
												if(mov.getTipoLancamento().equals(Movimentacao.TipoLancamento.ENTRADA)){
													System.out.println("3.1.if.1.for.4.if.TIPO ENTRADA");
													
													// CAIXA - ADICIONANDO O VALOR TOTAL DE ENTRADA
													caixaMes.setTotalEntrada(caixaMes.getTotalEntrada().add(mov.getValorLancamento()));										
												}else{
													System.out.println("3.1.if.1.for.4.else.TIPO SAIDA");
													
													// CAIXA - ADICIONANDO O VALOR TOTAL DE SAÍDA
													caixaMes.setTotalSaida(caixaMes.getTotalSaida().add(mov.getValorLancamento()));
												}									
												movimentacoes.add(mov);
												System.out.println("3.1.if.1.for.Linha 145, ADD NAS MOVS(" + new SimpleDateFormat("dd/MM/yyyy").format(mov.getData().getTime()) + ")");
											}
										}
									}
									System.out.println("----------- LINHA 149 ----------- FIM -  PEGOU AS MOVIMENTACOES DO PERIODO ---------------------------------------");
									System.out.println("----------- LINHA 150 ----------- FIM -  SETANDO O CAIXA PARA O dataCaixaMes -------------------------------------");
									// CAIXA - ADICIONANDO O SALDO DO PERÍODO INFORMADO
									caixaMes.setSaldo(caixaMes.getTotalEntrada().subtract(caixaMes.getTotalSaida()));
									
									// CAIXA - ADICIONANDO A MOVIMENTACAO DO PERÍODO INFORMADO
									caixaMes.setMovimentacoes(movimentacoes);
									
									// VERIFICA SE HÁ MOVIMENTAÇÕES DO PERÍODO INFORMADO
									if(!caixaMes.getMovimentacoes().isEmpty()){
										System.out.println("3.1.if.2.if.MOVIMENTACOES DO PERIODO INFORMADO OK (mes " + mes + ", ano" + ano + ") - preparando jsonCaixa");
										// PEGA O JSON CAIXA MENSAL - IDENTIFICADOR: "dataCaixaMes"
										json = montaJsonCaixa("dataCaixaMes", caixaMes);
									}else{									
										System.out.println("3.1.if.2.else.NAO HA MOVIMENTACOES - preparando jsonVazio");
										// PEGA O JSON CAIXA VAZIO - IDENTIFICADOR: "dataCaixaMes"
										json = montaJsonVazio("dataCaixaMes");
									}
								//}
								
							// CAIXA E MOVIMENTAÇÃO DE TODOS OS PERÍODOS
							}else{
								System.out.println("3.1.else.RegraCaixa, 2. PESQUISANDO POR CODIGO, PERIODO VERIFICADO, NAO INFORMADO: ANO: " + ano + " MES: " + mes);
								
								List<Movimentacao> listaOrdenada = caixa.getMovimentacoes();
								movUltLanc = new ArrayList<Movimentacao>();
								pedPCUltPendentes = new PedidoCompraDAO().listaUltimosPendentes(StatusCompra.PENDENTE);
								pedPVUltPendentes = new PedidoVendaDAO().listaUltimosPendentes(StatusVenda.PENDENTE);
								
								System.out.println("------- LINHA 178 ---------- INICIO - ORDENANDO A LISTA --------------------------------------------");
								// ORDENACAO DAS MOVIMENTAÇÕES POR DATA								
								for(int i = 0; i < listaOrdenada.size(); i++){									
									Movimentacao ms = listaOrdenada.get(i);									
									// SE FOR DIFERENTE DO ÚLTIMO ÍNDICE
									if(i != (listaOrdenada.size() - 1)){				
										// SE ESTA DATA FOR MAIOR QUE A DATA SEGUINTE
										System.out.println("3.1.else.1.for.if.ORDENANDO A LISTA, i = " + i + " === DATA: " + new SimpleDateFormat("dd/MM/yyyy").format(ms.getData().getTime()) + " =====  DATA SEGUINTE: " + new SimpleDateFormat("dd/MM/yyyy").format(listaOrdenada.get(i + 1).getData().getTime()));
										if(ms.getData().after(listaOrdenada.get(i + 1).getData())){
											listaOrdenada.remove(i);				// REMOVE ESTA DATA
											listaOrdenada.add(i + 1, ms);			// ADICIONA ESTA DATA NA POSIÇÃO SEGUINTE
											i = -1;
											System.out.println("3.1.else.1.for.if.if.CONTADOR i ZERADO");
										}				
									}									
								}									
								System.out.println("-------- LINHA 193 ---------- FIM - ORDENANDO A LISTA -----------------------------------------------");
								
								System.out.println("---------------------------- INICIO - LISTA ORDENADA - GRAFICO --------------------------------------------");
								// LISTA ORDENADA - PEGA OS 12 MESES ANTERIORES
								Caixa cxGrafico = new Caixa();
								cxGrafico.setTotalEntrada(new BigDecimal(0));
								cxGrafico.setTotalSaida(new BigDecimal(0));
								cxGrafico.setSaldo(new BigDecimal(0));								
								
								Calendar dataVigente = Calendar.getInstance();
								Calendar dataUmAnoAnterior = Calendar.getInstance();
								int comparaDataAtual = 0;
								int comparaDataAnoAnterior = 0;
								
								dataUmAnoAnterior.set((dataVigente.get(Calendar.YEAR) - 1), dataVigente.get(Calendar.MONTH), 1);
								
								System.out.println("DATA ANTES: " + new SimpleDateFormat("dd/MM/yyyy").format(dataUmAnoAnterior.getTime()) + " ATUAL:" + new SimpleDateFormat("dd/MM/yyyy").format(dataVigente.getTime()));
								
								List<Caixa> listaCxsGrafico = new ArrayList<Caixa>();
								for(Movimentacao mGrafico : listaOrdenada){
									System.out.println("3.1.else.2.for.LISTA ORDENADA: " + listaOrdenada.indexOf(mGrafico) + " DATA: " + new SimpleDateFormat("MM/yyyy").format(mGrafico.getData().getTime()) + " " + mGrafico.getTipoLancamento().toString() + ": " + mGrafico.getValorLancamento());															
									
									comparaDataAtual = mGrafico.getData().compareTo(dataVigente);
									comparaDataAnoAnterior = mGrafico.getData().compareTo(dataUmAnoAnterior);
									
									// SE FOR A PRIMEIRA MOVIMENTACAO
									if(listaOrdenada.indexOf(mGrafico) == 0){
										
										// SE TAMANHO DA LISTA = 1 										
										if(listaOrdenada.size() == 1){
											System.out.println("3.1.else.2.for.if.if.LISTA ORDENADA, POSICAO 1, TAMANHO = 1, ANO(" + mGrafico.getData().get(Calendar.YEAR) + ") <= ANO ATUAL(" + Calendar.getInstance().get(Calendar.YEAR) + ")");
											// SE DATA <= ANO VIGENTE - ADICIONA MOVIMENTACAO PARA GUARDAR A DATA
											
											
											if(comparaDataAtual <= 0 && comparaDataAnoAnterior >= 0){
												
												// VERIFICA TIPO E PEGA OS DADOS - ENTRADA, SAIDA, SALDO
												if(mGrafico.getTipoLancamento() == TipoLancamento.ENTRADA){
													cxGrafico.setTotalEntrada(cxGrafico.getTotalEntrada().add(mGrafico.getValorLancamento()));
												}else{
													cxGrafico.setTotalSaida(cxGrafico.getTotalSaida().add(mGrafico.getValorLancamento()));
												}
												cxGrafico.setSaldo(cxGrafico.getTotalEntrada().subtract(cxGrafico.getTotalSaida()));
													
												// ADICIONA A MOVIMENTACAO NA LISTA DO GRÁFICO - PARA ADICIONAR A DATA
												List<Movimentacao> listaMovGrafico = new ArrayList<Movimentacao>();
												listaMovGrafico.add(mGrafico);
												cxGrafico.setMovimentacoes(listaMovGrafico);
												listaCxsGrafico.add(cxGrafico);
												cxGrafico = null;												
											}
										}else{	// SE O TAMANHO DA LISTA > 1 CONTINUA
											
											System.out.println("3.1.else.2.for.if.if.LISTA ORDENADA, POSICAO 1, TAMANHO > 1, ANO(" + mGrafico.getData().get(Calendar.YEAR) + ") <= ANO ATUAL(" + Calendar.getInstance().get(Calendar.YEAR) + ")");
											
											if(comparaDataAtual <= 0 && comparaDataAnoAnterior >= 0){												
												
												// VERIFICA TIPO E PEGA OS DADOS - ENTRADA, SAIDA, SALDO
												if(mGrafico.getTipoLancamento() == TipoLancamento.ENTRADA){
													cxGrafico.setTotalEntrada(cxGrafico.getTotalEntrada().add(mGrafico.getValorLancamento()));
												}else{
													cxGrafico.setTotalSaida(cxGrafico.getTotalSaida().add(mGrafico.getValorLancamento()));
												}
												cxGrafico.setSaldo(cxGrafico.getTotalEntrada().subtract(cxGrafico.getTotalSaida()));											
												
												// VERIFICA SE O MES ATUAL É DIFERENTE DO MES SEGUINTE
												if(mGrafico.getData().get(Calendar.MONTH) != listaOrdenada.get(listaOrdenada.indexOf(mGrafico)+1).getData().get(Calendar.MONTH)){
													List<Movimentacao> listaMovGrafico = new ArrayList<Movimentacao>();
													listaMovGrafico.add(mGrafico);
													cxGrafico.setMovimentacoes(listaMovGrafico);
													
													listaCxsGrafico.add(cxGrafico);
													
													cxGrafico = new Caixa();
													cxGrafico.setTotalEntrada(new BigDecimal(0));
													cxGrafico.setTotalSaida(new BigDecimal(0));
													cxGrafico.setSaldo(new BigDecimal(0));
												}
											}
										}										
									}else{
										// SE FOR A ULTIMA MOVIMENTACAO
										if(listaOrdenada.indexOf(mGrafico) == listaOrdenada.size() - 1){
											if(comparaDataAtual <= 0 && comparaDataAnoAnterior >= 0){
												if(mGrafico.getTipoLancamento() == TipoLancamento.ENTRADA){
													cxGrafico.setTotalEntrada(cxGrafico.getTotalEntrada().add(mGrafico.getValorLancamento()));
												}else{
													cxGrafico.setTotalSaida(cxGrafico.getTotalSaida().add(mGrafico.getValorLancamento()));
												}
												cxGrafico.setSaldo(cxGrafico.getTotalEntrada().subtract(cxGrafico.getTotalSaida()));										
												
												// ADICIONA A MOVIMENTACAO NA LISTA DO GRÁFICO - PARA ADICIONAR A DATA
												List<Movimentacao> listaMovGrafico = new ArrayList<Movimentacao>();
												listaMovGrafico.add(mGrafico);
												cxGrafico.setMovimentacoes(listaMovGrafico);
												listaCxsGrafico.add(cxGrafico);
												cxGrafico = null;
											}
										}else{	// DEMAIS MOVIMENTAÇÕES
											
											if(comparaDataAtual <= 0 && comparaDataAnoAnterior >= 0){
												if(mGrafico.getTipoLancamento() == TipoLancamento.ENTRADA){
													cxGrafico.setTotalEntrada(cxGrafico.getTotalEntrada().add(mGrafico.getValorLancamento()));
												}else{
													cxGrafico.setTotalSaida(cxGrafico.getTotalSaida().add(mGrafico.getValorLancamento()));
												}
												cxGrafico.setSaldo(cxGrafico.getTotalEntrada().subtract(cxGrafico.getTotalSaida()));
	
												// VERIFICA SE O MES ATUAL É DIFERENTE DO MES SEGUINTE
												if(mGrafico.getData().get(Calendar.MONTH) != listaOrdenada.get(listaOrdenada.indexOf(mGrafico)+1).getData().get(Calendar.MONTH)){												
													List<Movimentacao> listaMovGrafico = new ArrayList<Movimentacao>();
													listaMovGrafico.add(mGrafico);
													cxGrafico.setMovimentacoes(listaMovGrafico);
													
													listaCxsGrafico.add(cxGrafico);
													
													cxGrafico = new Caixa();
													cxGrafico.setTotalEntrada(new BigDecimal(0));
													cxGrafico.setTotalSaida(new BigDecimal(0));
													cxGrafico.setSaldo(new BigDecimal(0));
												}
											}
										}										
									}
								}
								
								
								/*
								List<Caixa> listaCxsGrafico = new ArrayList<Caixa>();
								for(Movimentacao mGrafico : listaOrdenada){
									System.out.println("3.1.else.2.for.LISTA ORDENADA: " + listaOrdenada.indexOf(mGrafico) + " DATA: " + new SimpleDateFormat("MM/yyyy").format(mGrafico.getData().getTime()) + " " + mGrafico.getTipoLancamento().toString() + ": " + mGrafico.getValorLancamento());															
									
									// SE FOR A PRIMEIRA MOVIMENTACAO
									if(listaOrdenada.indexOf(mGrafico) == 0){
										
										// SE TAMANHO DA LISTA = 1 										
										if(listaOrdenada.size() == 1){
											
											
											
												// VERIFICA TIPO E PEGA OS DADOS - ENTRADA, SAIDA, SALDO
												if(mGrafico.getTipoLancamento() == TipoLancamento.ENTRADA){
													cxGrafico.setTotalEntrada(cxGrafico.getTotalEntrada().add(mGrafico.getValorLancamento()));
												}else{
													cxGrafico.setTotalSaida(cxGrafico.getTotalSaida().add(mGrafico.getValorLancamento()));
												}
												cxGrafico.setSaldo(cxGrafico.getTotalEntrada().subtract(cxGrafico.getTotalSaida()));
												
												// ADICIONA A MOVIMENTACAO NA LISTA DO GRÁFICO - PARA ADICIONAR A DATA
												List<Movimentacao> listaMovGrafico = new ArrayList<Movimentacao>();
												listaMovGrafico.add(mGrafico);
												cxGrafico.setMovimentacoes(listaMovGrafico);
												listaCxsGrafico.add(cxGrafico);
												cxGrafico = null;
											
										}else{
											// SE O TAMANHO DA LISTA > 1 CONTINUA
											// VERIFICA TIPO E PEGA OS DADOS - ENTRADA, SAIDA, SALDO
											if(mGrafico.getTipoLancamento() == TipoLancamento.ENTRADA){
												cxGrafico.setTotalEntrada(cxGrafico.getTotalEntrada().add(mGrafico.getValorLancamento()));
											}else{
												cxGrafico.setTotalSaida(cxGrafico.getTotalSaida().add(mGrafico.getValorLancamento()));
											}
											cxGrafico.setSaldo(cxGrafico.getTotalEntrada().subtract(cxGrafico.getTotalSaida()));											
											
											// VERIFICA SE O MES ATUAL É DIFERENTE DO MES SEGUINTE
											if(mGrafico.getData().get(Calendar.MONTH) != listaOrdenada.get(listaOrdenada.indexOf(mGrafico)+1).getData().get(Calendar.MONTH)){
												List<Movimentacao> listaMovGrafico = new ArrayList<Movimentacao>();
												listaMovGrafico.add(mGrafico);
												cxGrafico.setMovimentacoes(listaMovGrafico);
												
												listaCxsGrafico.add(cxGrafico);
												
												cxGrafico = new Caixa();
												cxGrafico.setTotalEntrada(new BigDecimal(0));
												cxGrafico.setTotalSaida(new BigDecimal(0));
												cxGrafico.setSaldo(new BigDecimal(0));
											}
										}										
									}else{
										// SE FOR A ULTIMA MOVIMENTACAO
										if(listaOrdenada.indexOf(mGrafico) == listaOrdenada.size() - 1){
											if(mGrafico.getTipoLancamento() == TipoLancamento.ENTRADA){
												cxGrafico.setTotalEntrada(cxGrafico.getTotalEntrada().add(mGrafico.getValorLancamento()));
											}else{
												cxGrafico.setTotalSaida(cxGrafico.getTotalSaida().add(mGrafico.getValorLancamento()));
											}
											cxGrafico.setSaldo(cxGrafico.getTotalEntrada().subtract(cxGrafico.getTotalSaida()));										
											
											// ADICIONA A MOVIMENTACAO NA LISTA DO GRÁFICO - PARA ADICIONAR A DATA
											List<Movimentacao> listaMovGrafico = new ArrayList<Movimentacao>();
											listaMovGrafico.add(mGrafico);
											cxGrafico.setMovimentacoes(listaMovGrafico);
											listaCxsGrafico.add(cxGrafico);
											cxGrafico = null;
										}else{
											// DEMAIS MOVIMENTAÇÕES
											if(mGrafico.getTipoLancamento() == TipoLancamento.ENTRADA){
												cxGrafico.setTotalEntrada(cxGrafico.getTotalEntrada().add(mGrafico.getValorLancamento()));
											}else{
												cxGrafico.setTotalSaida(cxGrafico.getTotalSaida().add(mGrafico.getValorLancamento()));
											}
											cxGrafico.setSaldo(cxGrafico.getTotalEntrada().subtract(cxGrafico.getTotalSaida()));

											// VERIFICA SE O MES ATUAL É DIFERENTE DO MES SEGUINTE
											if(mGrafico.getData().get(Calendar.MONTH) != listaOrdenada.get(listaOrdenada.indexOf(mGrafico)+1).getData().get(Calendar.MONTH)){												
												List<Movimentacao> listaMovGrafico = new ArrayList<Movimentacao>();
												listaMovGrafico.add(mGrafico);
												cxGrafico.setMovimentacoes(listaMovGrafico);
												
												listaCxsGrafico.add(cxGrafico);
												
												cxGrafico = new Caixa();
												cxGrafico.setTotalEntrada(new BigDecimal(0));
												cxGrafico.setTotalSaida(new BigDecimal(0));
												cxGrafico.setSaldo(new BigDecimal(0));
											}
										}										
									}
								}*/
								System.out.println("---------------------------- FIM - LISTA ORDENADA - GRAFICO ---------------------------------------------");
								
								List<Caixa> listaGrafico = new ArrayList<Caixa>();								
								// SE A LISTA TIVER 13 MOVIMENTAÇOES OU MENOS
								if(listaCxsGrafico.size() <= 13){
									for(int i = 0; i < listaCxsGrafico.size(); i++){
										listaGrafico.add(listaCxsGrafico.get(i));
									}
								}else{	// SE A LISTA TIVER MAIS QUE 13 MOVIMENTAÇÕES									
									for(int i = listaOrdenada.size() - 13; i < listaOrdenada.size(); i++){
										listaGrafico.add(listaCxsGrafico.get(i));
									}
								}
								
								System.out.println("---------------------------- INICIO - VERIFICACAO DA CAPTACAO DE DADOS -----------------------------");
								// TESTE - VISUALIZANDO OS DADOS CAPTADOS PARA O GRAFICO								
								for(Caixa c : listaGrafico){
									System.out.println("3.1.else.3.for.\n" + listaGrafico.indexOf(c) + ". "
											+ " tamanho da lista de movimentacoes: " + c.getMovimentacoes().size() + "\n"
											+ "\tMES/ANO: " + new SimpleDateFormat("MMM/yy").format(c.getMovimentacoes().get(0).getData().getTime()) + "\n"
											+ "\tENTRADA: " + c.getTotalEntrada() + "\n"
											+ "\tSAIDA: " + c.getTotalSaida() + "\n"
											+ "\tSALDO: " + c.getSaldo());
								}
								System.out.println("---------------------------- FIM - VERIFICACAO DA CAPTACAO DE DADOS ---------------------------");
								/*
								System.out.println("---------------------------- INICIO - CAPTACAO DE ANOS ---------------------------");
								List<Integer> anos = new ArrayList<Integer>();
								System.out.println("QTD. ANOS INICIAL ....... " + anos.size());
								for(Iterator<Movimentacao> movIt = listaOrdenada.iterator(); movIt.hasNext();){
									Movimentacao movAno = movIt.next();
									if(listaOrdenada.indexOf(movAno) == 0){
										anos.add(movAno.getData().get(Calendar.YEAR));
									}else{
										// SE O ANO DESTA MOVIMENTAÇÃO FOR DIFERENTE DA ANTERIOR
										if(movAno.getData().get(Calendar.YEAR) != listaOrdenada.get(listaOrdenada.indexOf(movAno) - 1).getData().get(Calendar.YEAR)){
											anos.add(movAno.getData().get(Calendar.YEAR));
										}
									}
								}
								
								System.out.println("QTD. ANOS FINAL ....... " + anos.size());
								System.out.println("---------------------------- FIM - CAPTACAO DE ANOS ---------------------------");
								*/
								
								System.out.println("------------------------------ PEGANDO OS ANOS DA LISTA ORDENADA -------------------------");
								// PEGA OS ANOS - listaOrdenada.get(i).getData().get(Calendar.YEAR)
								List<Integer> anos = new ArrayList<Integer>();
								System.out.println("3.1.else.4.TAMANHO DA LISTA DE ANOS - INICIAL ....... " + anos.size());
								int anoListaAtual = 0, anoListaAnterior = 0;
								for(int i = 0; i < listaOrdenada.size(); i++){
									System.out.println("3.1.else.4.for.ANO da Lista Ordenada: " + listaOrdenada.get(i).getData().get(Calendar.YEAR));
									if(anos.size() == 0){
										System.out.println("3.1.else.4.for.1.if.TAMANHO DA LISTA DE ANOS = ZERO, ADICIONANDO ANO: " + listaOrdenada.get(i).getData().get(Calendar.YEAR));
										anos.add(listaOrdenada.get(i).getData().get(Calendar.YEAR));										
									}else{
										anoListaAtual = listaOrdenada.get(i).getData().get(Calendar.YEAR);
										anoListaAnterior = listaOrdenada.get(i - 1).getData().get(Calendar.YEAR);
										System.out.println("3.1.else.4.for.2.else.ANO ANTERIOR: " + anoListaAnterior + ", ANO ATUAL: " + anoListaAtual);
										if(anoListaAtual != anoListaAnterior){
											anos.add(listaOrdenada.get(i).getData().get(Calendar.YEAR));
										}
										/*
										System.out.println("3.1.else.4.for.2.else.TAMANHO DA LISTA DE ANOS: " + anos.size());
										//int j = 0;
										//for(Integer anoOrdenacao : anos){
										for(Iterator<Integer> j = anos.iterator(); j.hasNext();){
											Integer anoOrdenacao = j.next();	// PEGA O ANO DA LISTA
											System.out.println("3.1.else.4.for.2.else.1.for.PERCORRENDO LISTA DE ANOS: " + anoOrdenacao + ", listaOrdenada ANO: " + listaOrdenada.get(i).getData().get(Calendar.YEAR));											
											if(anoOrdenacao != listaOrdenada.get(i).getData().get(Calendar.YEAR)){
												System.out.println("3.1.else.4.for.2.else.1.for.1.if.ANOS DIFERENTES: " + anoOrdenacao + " e " + listaOrdenada.get(i).getData().get(Calendar.YEAR));
												
												//if(j == 0){
												//if(j.equals(0)){	// SE TIVER UM ANO ADICIONADO, ADICIONA ESSE DIFERENTE
													//System.out.println("ACHEI!!!! VAI APAGAR");
													//anos.add(listaOrdenada.get(i).getData().get(Calendar.YEAR));
												//}else{	// SENAO, SE T
													//for(Integer a : anos){
													System.out.println("3.1.else.4.for.2.else.1.for.1.if.");
													for(Iterator<Integer> k = anos.iterator(); k.hasNext();){
														Integer a = k.next();
														if(a != anoOrdenacao){
															anos.add(listaOrdenada.get(i).getData().get(Calendar.YEAR));
															System.out.println("TAMANHO MODIFICADO AQUI DA Lista ANOS: " + anos.size());
														}
													}
												//}
											}
											//j++;
										}
										*/
									}
								}
								System.out.println("------------------------------ ANOS DA LISTA ORDENADA PEGOS -------------------------");
								System.out.println("3.1.else.5.TAMANHO DA LISTA DE ANOS - FIM....... " + anos.size());
								
								System.out.println("------------------------------ TIRANDO A DUPLICIDADE DE ANOS -------------------------");
								List<Integer> anos2 = new ArrayList<Integer>();
								if(anos.size() > 1){
									int contAno = 0;
									int anoAux = 0;
									for(Iterator<Integer> z = anos.iterator(); z.hasNext();){
										Integer anoDuplo = z.next();
										if(contAno == 0){
											anoAux = anoDuplo;
											anos2.add(anoDuplo);
											contAno++;
										}else{
											if(anoDuplo != anoAux){
												anos2.add(anoDuplo);
											}
										}
									}
								}else{
									anos2 = anos;
								}
								System.out.println("------------------------------ DUPLICIDADE DE ANOS RETIRADA -- tamanho: " + anos2.size() + "-----------------------");
								
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
// ---------------------------------------- CHAMA O montaJsonCaixaUltimo-------------------------------------------------------------------------------------------------------
								
								// PEGA O JSON CAIXA - IDENTIFICADOR: "dataCaixa"
								json = montaJsonCaixaUltimo("dataCaixa", caixa, movUltLanc, pedPCUltPendentes, pedPVUltPendentes, anos2, listaGrafico);						
							}
						}else{
							System.out.println("3.RegraCaixa, PESQUISANDO POR CODIGO, MOVIMENTACOES VERIFICADAS, NAO HA MOVIMENTACOES...");
							
							// PEGA O JSON VAZIO - IDENTIFICADOR: "dataCaixa"
							json = montaJsonVazio("dataCaixa");
						}
						System.out.println("4.RegraCaixa, PESQUISANDO POR CODIGO, PESQUISA CONCLUIDA.");
						
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
	private String montaJsonCaixaUltimo(String identificador, Caixa caixa, List<Movimentacao> movUltLanc, List<PedidoCompra> pedPCUltPendentes, List<PedidoVenda> pedPVUltPendentes, List<Integer> anosLista, List<Caixa> listaCxsGrafico){
		System.out.println("RegraCaixa, PESQUISANDO POR CODIGO, MONTANDO O JSON DO CAIXA...");
			System.out.println("RegraCaixa, PESQUISANDO POR CODIGO, MONTANDO O JSON DO CAIXA, ANOS QTD.: " + anosLista.size());
		String jsonCaixa = "";
		String jsonMovs = montaJsonMovimentacao(caixa.getMovimentacoes());
		String jsonUltMovs = montaJsonUltMovimentacao(movUltLanc);
		String jsonCompras = montaJsonCompras(pedPCUltPendentes);
		String jsonVendas = montaJsonVendas(pedPVUltPendentes);
		String jsonAnos = montaJsonAnos(anosLista);
		String jsonGrafico = montaJsonGrafico(listaCxsGrafico);
		
		jsonCaixa = "{\"" + identificador + "\":[{"						
				+ "\"totalEntrada\":\"" + caixa.getTotalEntrada() + "\","
				+ "\"totalSaida\":\"" + caixa.getTotalSaida() + "\","
				+ "\"saldo\":\"" + caixa.getSaldo() + "\","
				+ "\"movimentacoes\":[" + jsonMovs + "],"
				+ "\"ultMovimentacoes\":[" + jsonUltMovs + "],"
				+ "\"compras\":[" + jsonCompras + "],"
				+ "\"vendas\":[" + jsonVendas + "],"
				+ "\"anos\":[" + jsonAnos + "],"
				+ "\"grafico\":[" + jsonGrafico + "]"
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
	
	private String montaJsonAnos(List<Integer> anosListaJson) {
		System.out.println("RegraCaixa, PESQUISANDO POR CODIGO, MONTANDO O JSON DE ANOS, tamanho da lista: " + anosListaJson.size());
		
		String jsonAnosMontando = "";
		int controle = 1;
		
		if(anosListaJson.size() == 0){
			jsonAnosMontando = "{}";
		}else{
			for(Integer anoIteracao : anosListaJson){
				System.out.println("Linha 701 - montaJsonAnos ANO: " + anoIteracao);
				// CASO SEJA O PRIMEIRO INDICE CHAVES {} SEM VIRGULA		
				if(controle == 1){				
					jsonAnosMontando = "{\"ano\":\"" + anoIteracao + "\"}";
					
					controle = 2;
					
				// CASO NAO SEJA O PRIMEIRO INDICE
				}else{				
					jsonAnosMontando += "," + "{\"ano\":\"" + anoIteracao + "\"}";
				}
			}
		}
		System.out.println("RegraCaixa, PESQUISANDO POR CODIGO, JSON DE ANOS MONTADO...");
		return jsonAnosMontando;
	}

	private String montaJsonGrafico(List<Caixa> listaCxsGrafico) {
		System.out.println("RegraCaixa, PESQUISANDO POR CODIGO, MONTANDO O JSON DE VENDAS...");
		
		String jsonGrafico = "";
		int controle = 1;
		
		if(listaCxsGrafico.size() == 0 || listaCxsGrafico == null){
			jsonGrafico = "{}";
		}else{
			for(Caixa cx : listaCxsGrafico){
				
				// CASO SEJA A PRIMEIRA MOVIMENTACAO CHAVES {} SEM VIRGULA		
				if(controle == 1){				
					jsonGrafico = "{\"mesAno\":\"" + new SimpleDateFormat("MM/yy").format(cx.getMovimentacoes().get(0).getData().getTime()) + "\","
							+ "\"entrada\":\"" + cx.getTotalEntrada() + "\","
							+ "\"saida\":\"" + cx.getTotalSaida() + "\","
							+ "\"total\":\"" + cx.getSaldo() + "\""											
							+ "}";
					
					controle = 2;
					
				// CASO NAO SEJA A PRIMEIRA COMPRA
				}else{				
					jsonGrafico += "," + "{\"mesAno\":\"" + new SimpleDateFormat("MM/yy").format(cx.getMovimentacoes().get(0).getData().getTime()) + "\","
							+ "\"entrada\":\"" + cx.getTotalEntrada() + "\","
							+ "\"saida\":\"" + cx.getTotalSaida() + "\","
							+ "\"total\":\"" + cx.getSaldo() + "\""											
							+ "}";
					
				}
			}
		}		
		return jsonGrafico;
	}
	
	// MONTA O JSON VAZIO - CASO NÃO HAJA MOVIMENTACOES
	private String montaJsonVazio(String identificador) {
		System.out.println("RegraCaixa, PESQUISANDO POR CODIGO, MONTANDO O JSON VAZIO...");		
		String jsonVazio = "{\"" + identificador + "\":\"null\"}";
		System.out.println("RegraCaixa, PESQUISANDO POR CODIGO, JSON VAZIO MONTADO...");
		return jsonVazio;
	}	
}
