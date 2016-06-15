$(document).ready(function(){
	
	// Código do Caixa - Só existe um único caixa
	var codigo = 1;
	var mes = null;
	var ano = null;	
	
	var dataCompleta = new Date();
	mes = parseInt(dataCompleta.getMonth() + 1);
	ano = parseInt(dataCompleta.getFullYear());	
	
	console.log("1.MES: " + mes + " ANO: " + ano);
	
	// FORMATA VALOR PARA EXIBIÇÃO
	function formataNumeroParaExibicao(number, decimals, dec_point, thousands_sep) {
		var n = number, c = isNaN(decimals = Math.abs(decimals)) ? 2 : decimals;
		var d = dec_point == undefined ? "," : dec_point;
		var t = thousands_sep == undefined ? "." : thousands_sep, s = n < 0 ? "-" : "";
		var i = parseInt(n = Math.abs(+n || 0).toFixed(c)) + "", j = (j = i.length) > 3 ? j % 3 : 0;
		return s + (j ? i.substr(0, j) + t : "") + i.substr(j).replace(/(\d{3})(?=\d)/g, "$1" + t) + (c ? d + Math.abs(n - i).toFixed(c).slice(2) : "");
	}
	
	// DIV CAIXA SALDO TOTAL E TABELA CAIXA BALANCO GERAL - PÁGS. DASHBOARD E CAIXA
	//var divCaixaSaldoTotal = $("div[role=saldoTotal");
	var divCaixaSaldoTotal = $("#saldoDashboard");
// ----------------- IDENTIFICA SE EXISTE A DIV DE SALDO -- IMPORTANTE!!! AQUI CONTÉM A LÓGICA DO CAIXA DAS DUAS PÁGINAS - DASHBOARD E CAIXA --------
	if(divCaixaSaldoTotal.length){
		
		var tabCxUltimo = $("#tabCxUltimo");
		var tbodyCxUltimo = tabCxUltimo.find("tbody");				
		
		var tabPcUltimo = $("#tabPcUltimo");
		var tbodyPcUltimo = tabPcUltimo.find("tbody");		
		
		var tabPvUltimo = $("#tabPvUltimo");
		var tbodyPvUltimo = tabPvUltimo.find("tbody");
		
		// TABELA PARA O GRÁFICO
		var tabBalanco = $("#tabBalanco");
		var tbodyTabBalanco = tabBalanco.find("tbody");
		var trs = [];
		
		console.log("AJAX - DASHBOARD E CAIXA - ANO: " + ano  + " MES: " + mes);
		
		$.ajax({
			url:"controller",
			type:"post",
			dataType:"json",
			data:{
				codigo:codigo,
				action:"pesquisarCaixa",
			},
			success:function(resultado){
				
				// SE TIVER A TABELA DOS DADOS PARA O GRAFICO
				if($('#tabBalanco').length){
					console.log("DASHBOARD - GRAFICO");
					// TRANSFORMA O MES NUMÉRICO EM TEXTUAL
					function formataMes(m) {
					    var d = new Date();
					    d.setMonth(m);
					    var month = new Array(12);
					    month[0] = "Jan";
					    month[1] = "Fev";
					    month[2] = "Mar";
					    month[3] = "Abr";
					    month[4] = "Mai";
					    month[5] = "Jun";
					    month[6] = "Jul";
					    month[7] = "Ago";
					    month[8] = "Set";
					    month[9] = "Out";
					    month[10] = "Nov";
					    month[11] = "Dez";
	
					    var n = month[d.getUTCMonth()];
					    return n;
					}
					
					var tamanho = resultado.dataCaixa[0].grafico;	// lista de dados, máx. 13 resultados - mes, entrada, saida, toal - 
					var completaTR = 0;
					var ultMesAno = "";
					var arrPriMesAno = ""; 
					var priMes = "";
					var ultMes = "";
					var anoAnterior = 0;
					var anoVigente = 0;
					
					// SE NAO TIVER OS 13 RESULTADOS, SERAO IDENTIFICADOS OS MESES SEM RESULTADO E COMPLETADOS COM ZERO
					if(tamanho.length != 13){
						completaTR = 13 - tamanho.length;	// PEGA A QUANTIDADE DE DADOS VAZIO
						console.log("TAMANHO DO RESULTADO: " + tamanho.length + " ULTIMO MES/ANO: " + resultado.dataCaixa[0].grafico[tamanho.length-1].mesAno);
						
						ultMesAno = resultado.dataCaixa[0].grafico[tamanho.length-1].mesAno;	// PEGA O ÚLTIMO MÊS DOS RESULTADOS PARA IDENTIFICÁ-LO 
						arrUltMesAno = ultMesAno.split("/");	// SEPARA O ULTIMO MES DO ULTIMO ANO
						ultMes = (arrUltMesAno[0]-1);			// JUNHO: (5) para formatacao textual (6 - 1)
						priMes = ultMes;						
						anoAnterior = arrUltMesAno[1] - 1;
						anoVigente = arrUltMesAno[1];
						
						var qtdMesesAnoVigente = ultMes + 1;
						var qtdMesesAnoAnterior = 13 - qtdMesesAnoVigente;
						
						console.log("qtd. MESES ANO(" + (arrPriMesAno[1] - 1) + ") ANTERIOR: " + qtdMesesAnoAnterior + " qtd. MESES ANO(" + (arrPriMesAno[1]) + ") VIGENTE: " + qtdMesesAnoVigente);						
												
						var priIndiceResultado = 0;
						var ultIndiceResultado = tamanho - 1;
						var cont = priMes;	// contador vai percorrer os meses
						
						// PREENCHE OS DADOS DO ANO ANTERIOR
						while(cont < 12){	
							console.log("cont: " + cont + " resultado[" + priIndiceResultado + "]mesAno - 1: " + resultado.dataCaixa[0].grafico[parseInt(priIndiceResultado)].mesAno);
							var mA = resultado.dataCaixa[0].grafico[parseInt(priIndiceResultado)].mesAno;
							var arrMA = mA.split("/");
							
							// MESES IGUAIS
							if(cont == arrMA[0] - 1){
								
								// CARREGA OS DADOS NA TABELA
								tbodyTabBalanco.append("<tr>"
													+ "<th>" + formataMes(cont) + "/" + anoAnterior + "</th>"
													+ "<td>" + resultado.dataCaixa[0].grafico[parseInt(priIndiceResultado)].entrada + "</td>"
													+ "<td>" + resultado.dataCaixa[0].grafico[parseInt(priIndiceResultado)].saida + "</td>"
													+ "<td>" + resultado.dataCaixa[0].grafico[parseInt(priIndiceResultado)].total + "</td>"
													+ "</tr>");
									
								if(priIndiceResultado != ultIndiceResultado){
									priIndiceResultado++;	// VAI PARA O PRÓXIMO ÍNDICE									
								}
							}else{
								tbodyTabBalanco.append("<tr>"
										+ "<th>" + formataMes(cont) + "/" + anoAnterior + "</th>"
										+ "<td>0</td>"
										+ "<td>0</td>"
										+ "<td>0</td>"
										+ "</tr>");
							}
							cont++;
						}						
						
						// PREENCHE OS DADOS DO ANO ANTERIOR
						cont = 0;
						while(cont < ultMes + 1){	
							console.log("cont: " + cont + " resultado[" + priIndiceResultado + "]mesAno - 1: " + (resultado.dataCaixa[0].grafico[parseInt(priIndiceResultado)].mesAno));
							var mA = resultado.dataCaixa[0].grafico[parseInt(priIndiceResultado)].mesAno;
							var arrMA = mA.split("/");
							
							// MESES IGUAIS
							if(cont == arrMA[0] - 1){
								
								// CARREGA OS DADOS NA TABELA
								tbodyTabBalanco.append("<tr>"
													+ "<th>" + formataMes(cont) + "/" + anoVigente + "</th>"
													+ "<td>" + resultado.dataCaixa[0].grafico[parseInt(priIndiceResultado)].entrada + "</td>"
													+ "<td>" + resultado.dataCaixa[0].grafico[parseInt(priIndiceResultado)].saida + "</td>"
													+ "<td>" + resultado.dataCaixa[0].grafico[parseInt(priIndiceResultado)].total + "</td>"
													+ "</tr>");
									
								if(priIndiceResultado != ultIndiceResultado){
									priIndiceResultado++;	// VAI PARA O PRÓXIMO ÍNDICE									
								}
							}else{
								tbodyTabBalanco.append("<tr>"
										+ "<th>" + formataMes(cont) + "/" + anoVigente + "</th>"
										+ "<td>0</td>"
										+ "<td>0</td>"
										+ "<td>0</td>"
										+ "</tr>");
							}
							cont++;
						}							
					}
				
					// API HIGHCHARTS - MONTA O GRAFICO NA DIV COM O ID "grafico" PELOS VALORES DA TABELA COM O ID "tabBalanco"
					$('#grafico').highcharts({
				        data: {
				            table: 'tabBalanco'
				        },
				        chart: {
				            type: 'column'
				        },
				        title: {
				            text: 'Balanço em 12 meses'
				        },
				        yAxis: {
				            allowDecimals: false,
				            title: {
				                text: 'Valor (R$)'
				            }
				        },
				        tooltip: {
				            formatter: function () {
				                return '<b>' + this.series.name + '</b><br/>' +
				                    this.point.y + ' ' + this.point.name.toLowerCase();
				            }
				        }
				    });
				}
				
				// SELECAO DE ANO - PÁG. CAIXA
				/*if($("#anoBalanco").length){
					console.log("PAG. CAIXA - SELECAO DO ANO");
					var anoAux = 0;
					var arrayAnoOption = [];
					$.each(resultado.dataCaixa[0].anos, function(index, value){												
						arrayAnoOption.push(value.ano);
						//anoBalanco.append("<option value='" + value.ano + "'>" + value.ano + "</option>");
					});
					
					// ORDENANDO OS ANOS PARA A EXIBIÇÃO
					for(var i = 0; i < arrayAnoOption.length; i++){
						if(i != arrayAnoOption.length - 1){
							if(arrayAnoOption[i - 1] < arrayAnoOption[i]){
								anoAux = arrayAnoOption[i];
								arrayAnoOption[i] = arrayAnoOption[i - 1];
								arrayAnoOption[i - 1] = anoAux;
							}
						}						
					}
					// COLOCANDO OS ANOS NO OPTIONS - SELECT
					
					$.each(arrayAnoOption, function(index, value){
						console.log("ANO SELECIONADO: " + ano + " ANO[" + index + "]: " + value);
						if(ano == null){
							var d = new Date();
							ano = d.getFullYear();
							console.log("ANO NULO PREENCHIDO: " + ano);
						}
						if(ano == value){							
							anoBalanco.append("<option value='" + value + "' selected>" + value + "</option>");
							console.log("IF ano(" + ano + ") = value(" + value + ") OPTION(selected): " + anoBalanco.find("option").prop("selected"));
						}else{							
							anoBalanco.append("<option value='" + value + "'>" + value + "</option>");
							console.log("ELSE ano(" + ano + ") != value(" + value + ") OPTION(selected): " + anoBalanco.find("option").prop("selected"));
						}						
					});
				}*/
				
// ----------------- IDENTIFICA SE ESTÁ NA PÁG DASHBOARD - DIV CAIXA SALDO TOTAL - PÁGS. DASHBOARD E CAIXA -------------------------------------------
				divCaixaSaldoTotal.html("<h2><strong><span></span></strong></h2>");
				if(resultado.dataCaixa[0].saldo < 0){				
					var saldoNegativo = resultado.dataCaixa[0].saldo - (resultado.dataCaixa[0].saldo) - (resultado.dataCaixa[0].saldo);
					divCaixaSaldoTotal.find("span").removeClass("green");
					divCaixaSaldoTotal.find("span").addClass("red");
					divCaixaSaldoTotal.find("span").text("- R$ " + formataNumeroParaExibicao(saldoNegativo, 2, ",", "."));
				}else{					
					divCaixaSaldoTotal.find("span").removeClass("red");
					divCaixaSaldoTotal.find("span").addClass("green");
					divCaixaSaldoTotal.find("span").text("R$ " + formataNumeroParaExibicao(resultado.dataCaixa[0].saldo, 2, ",", "."));
				}
				
// ----------------- IDENTIFICA SE ESTÁ NA PÁG DASHBOARD - MONTA OS ÚLTIMOS LANÇAMENTOS DO CAIXA, PEDIDOS PENDENTES - PÁG. DASHBOARD -----------------
				if(tabCxUltimo.length){
					var cont = 0;
					$.each(resultado.dataCaixa[0].ultMovimentacoes, function(index, valu){						
						if(cont < 5){
							if(resultado.dataCaixa[0].ultMovimentacoes[(resultado.dataCaixa[0].ultMovimentacoes.length-1) - index].tipoLancamento == "ENTRADA"){
								tbodyCxUltimo
									.append("<tr>" +
											"<td class='text-right'>" + (index+1) + "</td>" +
											"<td>" + resultado.dataCaixa[0].ultMovimentacoes[(resultado.dataCaixa[0].ultMovimentacoes.length-1) - index].data + "</td>" +
											"<td>" + resultado.dataCaixa[0].ultMovimentacoes[(resultado.dataCaixa[0].ultMovimentacoes.length-1) - index].descricao + "</td>" +
											"<td class='text-right'><b class='green'>" + formataNumeroParaExibicao(resultado.dataCaixa[0].ultMovimentacoes[(resultado.dataCaixa[0].ultMovimentacoes.length-1) - index].valorLancamento, 2, ",", ".") + "</b></td>" +
											"</tr>");
							}else{
								tbodyCxUltimo
									.append("<tr>" +
											"<td class='text-right'>" + (index+1) + "</td>" +
											"<td>" + resultado.dataCaixa[0].ultMovimentacoes[(resultado.dataCaixa[0].ultMovimentacoes.length-1) - index].data + "</td>" +
											"<td>" + resultado.dataCaixa[0].ultMovimentacoes[(resultado.dataCaixa[0].ultMovimentacoes.length-1) - index].descricao + "</td>" +
											"<td class='text-right'><b class='red'>" + formataNumeroParaExibicao(resultado.dataCaixa[0].ultMovimentacoes[(resultado.dataCaixa[0].ultMovimentacoes.length-1) - index].valorLancamento, 2, ",", ".") + "</b></td>" +
											"</tr>");
							}
						}
						cont++;
					});
					
					// MONTA AS ÚLTIMAS COMPRAS PENDENTES - PÁG. DASHBOARD
					$.each(resultado.dataCaixa[0].compras, function(index, value){
						tbodyPcUltimo
							.append("<tr>" +
									"<td class='text-right'>" + (index+1) + "</td>" +
									"<td>" + resultado.dataCaixa[0].compras[index].data + "</td>" +
									"<td>" + resultado.dataCaixa[0].compras[index].descricao + "</td>" +
									"<td class='text-right'><b class='red'>" + formataNumeroParaExibicao(resultado.dataCaixa[0].compras[index].valorPedido, 2, ",", ".") + "</b></td>" +
									"</tr>");
					});
					
					// MONTA AS ÚLTIMAS VENDAS PENDENTES - PÁG. DASHBOARD
					$.each(resultado.dataCaixa[0].vendas, function(index, value){
						tbodyPvUltimo
						.append("<tr>" +
								"<td class='text-right'>" + (index+1) + "</td>" +
								"<td>" + resultado.dataCaixa[0].vendas[index].data + "</td>" +
								"<td>" + resultado.dataCaixa[0].vendas[index].descricao + "</td>" +
								"<td class='text-right'><b class='green'>" + formataNumeroParaExibicao(resultado.dataCaixa[0].vendas[index].valorPedido, 2, ",", ".") + "</b></td>" +
								"</tr>");
					});					
				}				
				

				// VERIFICA SE A TABELA EXISTE E CARREGA OS DADOS
				/*if(tableCaixaBalancoGeral.length){
					$("#balancoGeralAnoVigente").text(parseInt(dataCompleta.getFullYear()));
					
					console.log("CAIXA ANO(" + ano + "): ENTRADA: " + resultado.dataCaixa[0].totalEntrada + " SAIDA: " + resultado.dataCaixa[0].totalSaida + " SALDO: " + resultado.dataCaixa[0].saldo);
					
					var tdReceitas = tableCaixaBalancoGeral.find("td[role=receitas]");
					tdReceitas.html("<h4><b></b></h4>");				
					tdReceitas.find("b").addClass("green");
					tdReceitas.find("b").text("R$ " + formataNumeroParaExibicao(resultado.dataCaixa[0].totalEntrada, 2, ",", "."));
					
					var tdDespesas = tableCaixaBalancoGeral.find("td[role=despesas]")
					tdDespesas.html("<h4><b></b></h4>");
					tdDespesas.find("b").addClass("red");
					tdDespesas.find("b").text("R$ " + formataNumeroParaExibicao(resultado.dataCaixa[0].totalSaida, 2, ",", "."));
					if(resultado.dataCaixa[0].saldo < 0){					
						var saldoNegativo = resultado.dataCaixa[0].saldo - (resultado.dataCaixa[0].saldo) - (resultado.dataCaixa[0].saldo);
						var tdSaldo = tableCaixaBalancoGeral.find("td[role=saldo]");
						tdSaldo.html("<h4><b></b></h4>");
						tdSaldo.find("b").removeClass("blue");
						tdSaldo.find("b").addClass("red");
						tdSaldo.find("b").text("R$ " + formataNumeroParaExibicao(saldoNegativo, 2, ",", "."));
					}else{					
						var tdSaldo = tableCaixaBalancoGeral.find("td[role=saldo]");
						tdSaldo.html("<h4><b></b></h4>");
						tdSaldo.find("b").removeClass("red");
						tdSaldo.find("b").addClass("blue");
						tdSaldo.find("b").text("R$ " + formataNumeroParaExibicao(resultado.dataCaixa[0].saldo, 2, ",", "."));
					}
				}*/
				
			}
		});
	}	
	
	
	
	
	
	var divPagCaixa = $("#saldoCaixa");
	if(divPagCaixa.length){
		
		var tableCaixaBalancoGeral = $("table[role=balancoGeral]");
		
		// CARREGA OS ANOS QUE POSSUEM DADOS NO BANCO NO SELECT
		var anoBalanco = $("#anoBalanco");
		console.log("ANOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOO: " + ano);
		$.ajax({
			url:"controller",
			type:"post",
			dataType:"json",
			data:{
				codigo:codigo,
				ano:ano,
				action:"pesquisarCaixaAno",
			},
			success:function(resultado){
				
				// MOSTRA O SALDO DO CAIXA
				divPagCaixa.html("<h2><strong><span></span></strong></h2>");
				if(resultado.dataCaixaAno[0].saldo < 0){				
					var saldoNegativo = resultado.dataCaixaAno[0].saldo - (resultado.dataCaixaAno[0].saldo) - (resultado.dataCaixaAno[0].saldo);
					divPagCaixa.find("span").removeClass("green");
					divPagCaixal.find("span").addClass("red");
					divPagCaixa.find("span").text("- R$ " + formataNumeroParaExibicao(saldoNegativo, 2, ",", "."));
				}else{					
					divPagCaixa.find("span").removeClass("red");
					divPagCaixa.find("span").addClass("green");
					divPagCaixa.find("span").text("R$ " + formataNumeroParaExibicao(resultado.dataCaixaAno[0].saldo, 2, ",", "."));
				}
				
				// SELECAO DE ANO - PÁG. CAIXA
				if($("#anoBalanco").length){
					console.log("PAG. CAIXA - SELECAO DO ANO");
					var anoAux = 0;
					var arrayAnoOption = [];
					$.each(resultado.dataCaixaAno[0].anos, function(index, value){												
						arrayAnoOption.push(value.ano);						
					});
					
					// ORDENANDO OS ANOS PARA A EXIBIÇÃO
					for(var i = 0; i < arrayAnoOption.length; i++){
						if(i != arrayAnoOption.length - 1){
							if(arrayAnoOption[i - 1] < arrayAnoOption[i]){
								anoAux = arrayAnoOption[i];
								arrayAnoOption[i] = arrayAnoOption[i - 1];
								arrayAnoOption[i - 1] = anoAux;
							}
						}						
					}
					// COLOCANDO OS ANOS NO OPTIONS - SELECT
					
					$.each(arrayAnoOption, function(index, value){
						console.log("ANO SELECIONADO: " + ano + " ANO[" + index + "]: " + value);
						if(ano == null){
							var d = new Date();
							ano = d.getFullYear();
							console.log("ANO NULO PREENCHIDO: " + ano);
						}
						if(ano == value){							
							anoBalanco.append("<option value='" + value + "' selected>" + value + "</option>");
							console.log("IF ano(" + ano + ") = value(" + value + ") OPTION(selected): " + anoBalanco.find("option").prop("selected"));
						}else{							
							anoBalanco.append("<option value='" + value + "'>" + value + "</option>");
							console.log("ELSE ano(" + ano + ") != value(" + value + ") OPTION(selected): " + anoBalanco.find("option").prop("selected"));
						}						
					});
				}
				
				
				// VERIFICA SE A TABELA EXISTE E CARREGA OS DADOS
				if(tableCaixaBalancoGeral.length){
					$("#balancoGeralAnoVigente").text(parseInt(ano));
					
					console.log("CAIXA ANO(" + ano + "): ENTRADA: " + resultado.dataCaixaAno[0].totalEntradaAno + " SAIDA: " + resultado.dataCaixaAno[0].totalSaidaAno + " SALDO: " + resultado.dataCaixaAno[0].saldoAno);
					
					var tdReceitas = tableCaixaBalancoGeral.find("td[role=receitas]");
					tdReceitas.html("<h4><b></b></h4>");				
					tdReceitas.find("b").addClass("green");
					tdReceitas.find("b").text("R$ " + formataNumeroParaExibicao(resultado.dataCaixaAno[0].totalEntradaAno, 2, ",", "."));
					
					var tdDespesas = tableCaixaBalancoGeral.find("td[role=despesas]")
					tdDespesas.html("<h4><b></b></h4>");
					tdDespesas.find("b").addClass("red");
					tdDespesas.find("b").text("R$ " + formataNumeroParaExibicao(resultado.dataCaixaAno[0].totalSaidaAno, 2, ",", "."));
					
					if(resultado.dataCaixaAno[0].saldoAno < 0){					
						var saldoNegativo = resultado.dataCaixaAno[0].saldoAno - (resultado.dataCaixaAno[0].saldoAno) - (resultado.dataCaixaAno[0].saldoAno);
						var tdSaldo = tableCaixaBalancoGeral.find("td[role=saldo]");
						tdSaldo.html("<h4><b></b></h4>");
						tdSaldo.find("b").removeClass("blue");
						tdSaldo.find("b").addClass("red");
						tdSaldo.find("b").text("R$ " + formataNumeroParaExibicao(saldoNegativo, 2, ",", "."));
					}else{					
						var tdSaldo = tableCaixaBalancoGeral.find("td[role=saldo]");
						tdSaldo.html("<h4><b></b></h4>");
						tdSaldo.find("b").removeClass("red");
						tdSaldo.find("b").addClass("blue");
						tdSaldo.find("b").text("R$ " + formataNumeroParaExibicao(resultado.dataCaixaAno[0].saldoAno, 2, ",", "."));
					}
				}
			
			}
		});
	}
	
	
	
	
	
	
	
	
	
	// VERIFICA SE HÁ TABELA BALANÇO ANUAL - PÁG. CAIXA
	if(tableCaixaBalancoGeral.length){
		
		// CARREGA OS ANOS QUE POSSUEM DADOS NO BANCO NO SELECT
		var anoBalanco = $("#anoBalanco");
		
		console.log("TABELA BALANCO ANO, ano: " + ano);
		// CARREGA O BALANÇO ANUAL - PÁG. CAIXA
		var exibeBalancoAnoSelecionado = function(ano){
			console.log("AJAX BALANCO ANO, ano: " + ano);
			
			$.ajax({
				url:"controller",
				type:"post",
				dataType:"json",
				data:{
					codigo:codigo,
					ano:ano,
					action:"pesquisarCaixaAno"
				},
				success:function(resultado){
					console.log("CAIXA ANO(" + ano + "): ENTRADA: " + resultado.dataCaixaAno[0].totalEntradaAno + " SAIDA: " + resultado.dataCaixaAno[0].totalSaidaAno + " SALDO: " + resultado.dataCaixaAno[0].saldoAno);
					
					$("#balancoGeralAnoVigente").text(parseInt(ano));
					
					var tdReceitas = tableCaixaBalancoGeral.find("td[role=receitas]");
					tdReceitas.html("<h4><b></b></h4>");				
					tdReceitas.find("b").addClass("green");
					tdReceitas.find("b").text("R$ " + formataNumeroParaExibicao(resultado.dataCaixaAno[0].totalEntradaAno, 2, ",", "."));
					
					var tdDespesas = tableCaixaBalancoGeral.find("td[role=despesas]")
					tdDespesas.html("<h4><b></b></h4>");
					tdDespesas.find("b").addClass("red");
					tdDespesas.find("b").text("R$ " + formataNumeroParaExibicao(resultado.dataCaixaAno[0].totalSaidaAno, 2, ",", "."));
					
					if(resultado.dataCaixaAno[0].saldoAno < 0){					
						var saldoNegativo = resultado.dataCaixaAno[0].saldoAno - (resultado.dataCaixaAno[0].saldoAno) - (resultado.dataCaixaAno[0].saldoAno);
						var tdSaldo = tableCaixaBalancoGeral.find("td[role=saldo]");
						tdSaldo.html("<h4><b></b></h4>");
						tdSaldo.find("b").removeClass("blue");
						tdSaldo.find("b").addClass("red");
						tdSaldo.find("b").text("R$ " + formataNumeroParaExibicao(saldoNegativo, 2, ",", "."));
					}else{					
						var tdSaldo = tableCaixaBalancoGeral.find("td[role=saldo]");
						tdSaldo.html("<h4><b></b></h4>");
						tdSaldo.find("b").removeClass("red");
						tdSaldo.find("b").addClass("blue");
						tdSaldo.find("b").text("R$ " + formataNumeroParaExibicao(resultado.dataCaixaAno[0].saldoAno, 2, ",", "."));
					}
				}
			});
		}
		
		// EXECUTA QUANDO A PÁG. CAIXA É CARREGADA
		//exibeBalancoAnoSelecionado(ano);
	}
	
	
	
	
	
	
	
// ------------------ IDENTIFICA SE ESTÁ NA PÁG CAIXA - CAIXA BALANCO MENSAL E MOVIMENTAÇÃO - PÁG. CAIXA --------------------------------------------
	var divAbasMeses = $("div[role=abasMeses]");
	
	if(divAbasMeses.length){
		
		console.log("2.MES entrou divAbasMeses: " + mes + " ANO: " + ano);
		mes = null;
		ano = null;
		console.log("3.MES zerou mes e ano: " + mes + " ANO: " + ano);
		
		// ABAS
		var ulMeses = divAbasMeses.find("#meses");
		var liMeses = ulMeses.find("li");
		var aMeses = ulMeses.find("a");
		var divMes = "";
		
		var rowCaixaMensal = "";		
		var tableCaixaBalancoMensal = "";
		var tbodyCaixaBalancoMensal = "";
		var theadCaixaBalancoMensal = "";
		
		var rowMovimentacaoMensal = "";
		var tableMovimentacaoMensal = "";
		var tbodyMovimentacaoMensal = "";
		var theadMovimentacaoMensal = "";
		
		// CONTEUDO DAS ABAS - FUNCAO PARA MONTAR A DIV COM CONTEUDO DO MES SEM OS DADOS
		var divConteudoMeses = divAbasMeses.find("#conteudoMeses");		
		var montaDivConteudoMeses = function(mes, ano){
			
			console.log("4.MES entrou montaDivConteudoMeses: " + mes + " ANO: " + ano);
			
			// PEGA O MES E ANO ATUAL QUANDO A PÁGINA É CARREGADA
			var dataCompleta = new Date();
			if(mes == null){				
				mes = parseInt(dataCompleta.getMonth() + 1);				
			}
			if(ano == null){
				ano = parseInt(dataCompleta.getFullYear());
			}
			
			console.log("5.MES verificou mes e ano: " + mes + " ANO: " + ano);
			
			// GARANTE QUE A DIV COM O CONTEUDO DO MES ESTEJA LIMPA ANTES DA MONTAGEM
			divConteudoMeses.html("");
			
			// ADICIONA A QTD. DE DIVS PELAS ABAS CORRESPONDENTES AOS MESES					
			aMeses.each(function(index, value){
				divConteudoMeses.append("<div class='tab-pane'></div>");
				console.log("id" + $(this).prop("id") + " mes: " + mes + " (index+1): " + (index+1));
				if(mes == (index+1)){
					$(this).parent().addClass("active");
				}else{
					$(this).parent().removeClass("active");
				}
			});			
			
			divMes = divConteudoMeses.find("div");
			divMes.each(function(index, value){
				
				console.log("6.MES adicionando abas: " + mes + " ANO: " + ano + " (index+1): " + (index+1));
				
				switch(index+1){					
					case 1:						
						$(this).prop("id", "janeiro");
						if(mes == (index+1)){
							console.log("7.MES jan ativando: " + mes + " ANO: " + ano + " (INDEX + 1): " + (index+1) + " id:" + $(this).prop("id"));
							
							$(this).addClass("active");
							montaTableCaixaBalancoMensal(mes, ano);
							montaMovimentacaoMensal(mes, ano);
						}
						break;
					case 2:
						$(this).prop("id", "fevereiro");
						if(mes == (index+1)){
							console.log("7.MES fev ativando: " + mes + " ANO: " + ano + " (INDEX + 1): " + (index+1) + " id:" + $(this).prop("id"));
							
							$(this).addClass("active");
							montaTableCaixaBalancoMensal(mes, ano);
							montaMovimentacaoMensal(mes, ano);
						}
						break;
					case 3:
						$(this).prop("id", "marco");
						if(mes == (index+1)){
							console.log("7.MES mar: ativando" + mes + " ANO: " + ano + " (INDEX + 1): " + (index+1) + " id:" + $(this).prop("id"));
							
							$(this).addClass("active");
							montaTableCaixaBalancoMensal(mes, ano);
							montaMovimentacaoMensal(mes, ano);
						}
						break;
					case 4:
						$(this).prop("id", "abril");
						if(mes == (index+1)){
							console.log("7.MES abr: ativando" + mes + " ANO: " + ano + " (INDEX + 1): " + (index+1) + " id:" + $(this).prop("id"));
							
							$(this).addClass("active");
							montaTableCaixaBalancoMensal(mes, ano);
							montaMovimentacaoMensal(mes, ano);
						}
						break;
					case 5:
						$(this).prop("id", "maio");
						if(mes == (index+1)){
							console.log("7.MES mai: ativando" + mes + " ANO: " + ano + " (INDEX + 1): " + (index+1) + " id:" + $(this).prop("id"));
							
							$(this).addClass("active");
							montaTableCaixaBalancoMensal(mes, ano);
							montaMovimentacaoMensal(mes, ano);
						}
						break;
					case 6:
						$(this).prop("id", "junho");
						if(mes == (index+1)){
							console.log("7.MES jun ativando: " + mes + " ANO: " + ano + " (INDEX + 1): " + (index+1) + " id:" + $(this).prop("id"));
							
							$(this).addClass("active");
							montaTableCaixaBalancoMensal(mes, ano);
							montaMovimentacaoMensal(mes, ano);
						}
						break;
					case 7:
						$(this).prop("id", "julho");
						if(mes == (index+1)){
							console.log("7.MES jul ativando: " + mes + " ANO: " + ano + " (INDEX + 1): " + (index+1) + " id:" + $(this).prop("id"));
							
							$(this).addClass("active");
							montaTableCaixaBalancoMensal(mes, ano);
							montaMovimentacaoMensal(mes, ano);
						}
						break;
					case 8:
						$(this).prop("id", "agosto");
						if(mes == (index+1)){
							console.log("7.MES ago ativando: " + mes + " ANO: " + ano + " (INDEX + 1): " + (index+1) + " id:" + $(this).prop("id"));
							
							$(this).addClass("active");
							montaTableCaixaBalancoMensal(mes, ano);
							montaMovimentacaoMensal(mes, ano);
						}
						break;
					case 9:
						$(this).prop("id", "setembro");
						if(mes == (index+1)){
							console.log("7.MES set ativando: " + mes + " ANO: " + ano + " (INDEX + 1): " + (index+1) + " id:" + $(this).prop("id"));
							
							$(this).addClass("active");							
							montaTableCaixaBalancoMensal(mes, ano);
							montaMovimentacaoMensal(mes, ano);
						}
						break;
					case 10:
						$(this).prop("id", "outubro");
						if(mes == (index+1)){
							console.log("7.MES out ativando: " + mes + " ANO: " + ano + " (INDEX + 1): " + (index+1) + " id:" + $(this).prop("id"));
							
							$(this).addClass("active");
							montaTableCaixaBalancoMensal(mes, ano);
							montaMovimentacaoMensal(mes, ano);
						}
						break;
					case 11:
						$(this).prop("id", "novembro");
						if(mes == (index+1)){
							console.log("7.MES nov ativando: " + mes + " ANO: " + ano + " (INDEX + 1): " + (index+1) + " id:" + $(this).prop("id"));
							
							$(this).addClass("active");
							montaTableCaixaBalancoMensal(mes, ano);
							montaMovimentacaoMensal(mes, ano);
						}
						break;
					case 12:
						$(this).prop("id", "dezembro");
						if(mes == (index+1)){
							console.log("7.MES dez ativando: " + mes + " ANO: " + ano + " (INDEX + 1): " + (index+1) + " id:" + $(this).prop("id"));
							
							$(this).addClass("active");
							montaTableCaixaBalancoMensal(mes, ano);
							montaMovimentacaoMensal(mes, ano);
						}
						break;
				}				
			});
		}
		
		// MONTA A TABELA DO CAIXA BALANCO MENSAL DO MES ATIVO		
		var montaTableCaixaBalancoMensal = function(mes, ano){
			console.log("8.MES entrou montaTableCaixaBalancoMensal: " + mes + " ANO: " + ano);
			
			divMes.append("<div class='row' role='caixaMensal'></div>");
			
			rowCaixaMensal = divMes.find("div[role=caixaMensal]");
			rowCaixaMensal.append("<div class='col-xs-6 col-xs-offset-3'></div>");
			rowCaixaMensal.find("div").append("<div class='widget-box transparent'></div>");
			
			rowCaixaMensal.find("div.widget-box").append("<div class='widget-header widget-header-flat'></div>");
			rowCaixaMensal.find("div.widget-box").append("<div class='widget-body'></div>");
			
			rowCaixaMensal.find("div.widget-header").append("<h3 class='widget-title lighter'></h3>");
			rowCaixaMensal.find("div.widget-header > h3").html("<i class='ace-icon fa fa-money'></i><strong></strong>");
			rowCaixaMensal.find("div.widget-header > h3 > strong").text(" Balanco Mensal");
			
			rowCaixaMensal.find("div.widget-body").append("<div class='widget-main no-padding'></div>");
			rowCaixaMensal.find("div.widget-main").append("<table class='table table-bordered table-striped' role='balancoMensal'></table>");
			
			tableCaixaBalancoMensal = rowCaixaMensal.find("table");
			theadCaixaBalancoMensal = tableCaixaBalancoMensal.append("<thead class='thin-border-bottom'></thead>");
			tbodyCaixaBalancoMensal = tableCaixaBalancoMensal.append("<tbody></tbody>");
			
			theadCaixaBalancoMensal.append("<tr>" +
					"<th class='text-center'><h4><b>Movimentacao</b></h4></th>" +
					"<th class='text-center'><h4><b>Valor (R$)</b></h4></th>" +
					"</tr>");
			tbodyCaixaBalancoMensal.append("<tr>" +
					"<td><b class='green'><h4>Receitas (R$)</b></h4></td>" +
					"<td class='text-right' role='receitas'></td>" +
					"</tr>");
			tbodyCaixaBalancoMensal.append("<tr>" +
					"<td><b class='red'><h4>Despesas (R$)</b></h4></td>" +
					"<td class='text-right' role='despesas'></td>" +
					"</tr>");
			tbodyCaixaBalancoMensal.append("<tr>" +
					"<td><b class='blue'><h4>Resultado (R$)</b></h4></td>" +
					"<td class='text-right' role='saldo'></td>" +
					"</tr>");
		}
		
		// MONTA AS MOVIMENTACOES DO MES ATIVO
		var montaMovimentacaoMensal = function(mes, ano){
			console.log("9.MES entrou montaMovimentacaoMensal: " + mes + " ANO: " + ano);
			
			divMes.append("<div class='row' role='movimentacaoMensal'>");
			
			rowMovimentacaoMensal = divMes.find("div[role=movimentacaoMensal]");
			rowMovimentacaoMensal.append("<div class='col-xs-12'></div>");
			rowMovimentacaoMensal.find("div").append("<div class='widget-box transparent'></div>");
			
			rowMovimentacaoMensal.find("div.widget-box").append("<div class='widget-header widget-header-flat'></div>");
			rowMovimentacaoMensal.find("div.widget-box").append("<div class='widget-body'></div>");
			
			rowMovimentacaoMensal.find("div.widget-header").append("<h3 class='widget-title lighter'></h3>");
			rowMovimentacaoMensal.find("div.widget-header > h3").html("<i class='ace-icon fa fa-money'></i><strong></strong>");
			rowMovimentacaoMensal.find("div.widget-header > h3 > strong").text(" Movimentacoes");
			
			rowMovimentacaoMensal.find("div.widget-body").append("<div class='widget-main no-padding'></div>");
			rowMovimentacaoMensal.find("div.widget-main").append("<table class='table table-bordered table-striped'></table>");
			
			tableMovimentacaoMensal = rowMovimentacaoMensal.find("table");
			theadMovimentacaoMensal = tableMovimentacaoMensal.append("<thead class='thin-border-bottom'></thead>");
			tbodyMovimentacaoMensal = tableMovimentacaoMensal.append("<tbody></tbody>");
			
			theadMovimentacaoMensal.append("<tr>" +
					"<th>Item</th>" +
					"<th>Data</th>" +
					"<th>Lancamento</th>" +
					"<th>Valor (R$)</th>" +
					"</tr>");
			
			ajaxMovimentacaoMensal(mes, ano);
		}
		
		// FUNCAO QUE EXECUTA O AJAX PARA CONSULTAR DADOS DO CAIXA E MOVIMENTACAO DO PERIODO INFORMADO
		var ajaxMovimentacaoMensal = function(mes, ano){
			console.log("10.MES entrou ajax: " + mes + " ANO: " + ano + " codigo: " + codigo);
			
			$.ajax({
				url:"controller",
				type:"post",
				dataType:"json",
				data:{
					codigo:codigo,
					mes:mes,
					ano:ano,
					action:"pesquisarCaixa"
				},
				success:function(resultado){
					console.log("11.MES ajax result: " + mes + " ANO: " + ano);
					
					
					
					
					
					
					
					
					// VERIFICA SE A TABELA EXISTE E CARREGA OS DADOS
					/*if(tableCaixaBalancoGeral.length){
						//$("#balancoGeralAnoVigente").text(parseInt(dataCompleta.getFullYear()));
						$("#balancoGeralAnoVigente").text(parseInt(ano));
						console.log("CAIXA ANO(" + ano + "): ENTRADA: " + resultado.dataCaixaMes[0].totalEntradaAno + " SAIDA: " + resultado.dataCaixaMes[0].totalSaidaAno + " SALDO: " + resultado.dataCaixaMes[0].saldoAno);
						
						var tdReceitas = tableCaixaBalancoGeral.find("td[role=receitas]");
						tdReceitas.html("<h4><b></b></h4>");				
						tdReceitas.find("b").addClass("green");
						tdReceitas.find("b").text("R$ " + formataNumeroParaExibicao(resultado.dataCaixaMes[0].totalEntradaAno, 2, ",", "."));
						
						var tdDespesas = tableCaixaBalancoGeral.find("td[role=despesas]")
						tdDespesas.html("<h4><b></b></h4>");
						tdDespesas.find("b").addClass("red");
						tdDespesas.find("b").text("R$ " + formataNumeroParaExibicao(resultado.dataCaixaMes[0].totalSaidaAno, 2, ",", "."));
						if(resultado.dataCaixaMes[0].saldoAno < 0){					
							var saldoNegativo = resultado.dataCaixaMes[0].saldoAno - (resultado.dataCaixaMes[0].saldoAno) - (resultado.dataCaixaMes[0].saldoAno);
							var tdSaldo = tableCaixaBalancoGeral.find("td[role=saldo]");
							tdSaldo.html("<h4><b></b></h4>");
							tdSaldo.find("b").removeClass("blue");
							tdSaldo.find("b").addClass("red");
							tdSaldo.find("b").text("R$ " + formataNumeroParaExibicao(saldoNegativo, 2, ",", "."));
						}else{					
							var tdSaldo = tableCaixaBalancoGeral.find("td[role=saldo]");
							tdSaldo.html("<h4><b></b></h4>");
							tdSaldo.find("b").removeClass("red");
							tdSaldo.find("b").addClass("blue");
							tdSaldo.find("b").text("R$ " + formataNumeroParaExibicao(resultado.dataCaixaMes[0].saldoAno, 2, ",", "."));
						}
					}
					*/
					
					
					
					
					
					
					
					
					
					
					
					
					
					
					// CASO NÃO HAJA MOVIMENTAÇÃO DO MÊS E ANO INFORMADOS
					if(resultado.dataCaixaMes == "null"){
						console.log("12.MES ajax result NULO: " + mes + " ANO: " + ano);
						divConteudoMeses.html("<h3><b class='blue'></b></h3>");
						divConteudoMeses.addClass("center");
						divConteudoMeses.find("b").text("Não há lançamentos.");						
					}else{	// SENÃO - EXISTE MOVIMENTAÇÃO
						console.log("12.MES ajax result OK: " + mes + " ANO: " + ano);
						
						// VERIFICA SE EXISTE A DIV COM ABAS DOS MESES
						if(divAbasMeses.length){
							divConteudoMeses.removeClass("center");
							
							console.log("13.MES ajax result balanco mensal: " + mes + " ANO: " + ano + " qtd DIV: " + divAbasMeses.length);
							
							// CAIXA MENSAL - CARREGA OS DADOS
							tbodyCaixaBalancoMensal.find("td[role=receitas]").html("<h4><b class='green'>R$ " + formataNumeroParaExibicao(resultado.dataCaixaMes[0].totalEntrada, 2, ",", ".") + "</b></h4>");
							tbodyCaixaBalancoMensal.find("td[role=despesas]").html("<h4><b class='red'>R$ " + formataNumeroParaExibicao(resultado.dataCaixaMes[0].totalSaida, 2, ",", ".") + "</b></h4>");
							if(resultado.dataCaixaMes[0].saldo < 0){					
								var saldoNegativo = resultado.dataCaixaMes[0].saldo - (resultado.dataCaixaMes[0].saldo) - (resultado.dataCaixaMes[0].saldo);
								tbodyCaixaBalancoMensal.find("td[role=saldo]").html("<h4><b class='red'>R$ " + formataNumeroParaExibicao(saldoNegativo, 2, ",", ".") + "</b></h4>");
							}else{
								tbodyCaixaBalancoMensal.find("td[role=saldo]").html("<h4><b class='blue'>R$ " + formataNumeroParaExibicao(resultado.dataCaixaMes[0].saldo, 2, ",", ".") + "</b></h4>");
							}					
							
							console.log("14.MES ajax result movimentacoes: " + mes + " ANO: " + ano);
							
							// MOVIMENTAÇÕES MENSAIS - CARREGA AS MOVIMENTAÇÕES DO MÊS NA ABA SELECIONADA
							$.each(resultado.dataCaixaMes[0].movimentacoes, function(index, value){									
								if(resultado.dataCaixaMes[0].movimentacoes[index].tipoLancamento == "SAIDA"){
									tbodyMovimentacaoMensal.append("<tr><td class='text-right' role='item'><b>" + (index+1) + "</b></td>"
											+ "<td role='data'><b>" + resultado.dataCaixaMes[0].movimentacoes[index].data + "</b></td>"
				                            + "<td role='lancamento'><b>" + resultado.dataCaixaMes[0].movimentacoes[index].descricao + "</b></td>"
				                            + "<td class='text-right red' role='valor'><b>" + formataNumeroParaExibicao(resultado.dataCaixaMes[0].movimentacoes[index].valorLancamento, 2, ",", ".") + "</b></td></tr>");                    
				                }else{
				                	tbodyMovimentacaoMensal.append("<tr><td class='text-right' role='item'><b>" + (index+1) + "</b></td>"
											+ "<td role='data'><b>" + resultado.dataCaixaMes[0].movimentacoes[index].data + "</b></td>"
				                            + "<td role='lancamento'><b>" + resultado.dataCaixaMes[0].movimentacoes[index].descricao + "</b></td>"
				                            + "<td class='text-right green' role='valor'><b>" + formataNumeroParaExibicao(resultado.dataCaixaMes[0].movimentacoes[index].valorLancamento, 2, ",", ".") + "</b></td></tr>");
				                }						
							});			
						}
					}
					console.log("15.MES ajax result fim: " + mes + " ANO: " + ano);
				}
			});			
			console.log("16.MES ajax fim: " + mes + " ANO: " + ano);
		}					
		
		// CLIQUE DE UMA ABA MES
		aMeses.on("click", function(e){			
			e.preventDefault();
			mes = "";
			mes = $(this).attr("id");
			montaDivConteudoMeses(mes, ano);
			console.log("aba clicada: " + mes + " ANO: " + ano);
		});		
		
		// ESCUTA A SELEÇÃO DO ANO
		anoBalanco.change(function(e){
			e.preventDefault();		
			console.log("ANO: " + ano + " MES: " + mes);
			
			// PEGA O ANO SELECIONADO
			ano = $(this).val();
			
			// SE ANO SELECIONADO FOR IGUAL AO ANO ATUAL, PEGA O MÊS ATUAL
			if(ano == parseInt(new Date().getFullYear())){
				mes = parseInt(new Date().getMonth() + 1);
			}else{
				// SENÃO SETA O MÊS 1 - JANEIRO
				mes = parseInt(1);
			}
			
			// CARREGA OS DADOS DO BALANÇO ANUAL
			exibeBalancoAnoSelecionado(ano);
			
			// MONTA A DIV
			montaDivConteudoMeses(mes, ano);
			
			console.log("MUDOU O ANO: " + ano + " MES: " + mes);
		});
		
		
		console.log("montaDivConteudoMeses");
		montaDivConteudoMeses(mes, ano);
		
	}	
});