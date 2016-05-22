$(document).ready(function(){
	
	// Código do Caixa - Só existe um único caixa
	var codigo = 1;
	var mes = null;
	var ano = null;
	
	// DIV CAIXA SALDO TOTAL E TABELA CAIXA BALANCO GERAL - PÁGS. DASHBOARD E CAIXA
	var divCaixaSaldoTotal = $("div[role=saldoTotal");
	if(divCaixaSaldoTotal.length){
		var tableCaixaBalancoGeral = $("table[role=balancoGeral]");		

		$.ajax({
			url:"controller",
			type:"post",
			dataType:"json",
			data:{
				codigo:codigo,
				action:"pesquisarCaixa"
			},
			success:function(resultado){
				
				// DIV CAIXA SALDO TOTAL - PÁGS. DASHBOARD E CAIXA
				divCaixaSaldoTotal.html("<h2><strong><span></span></strong></h2>");
				if(resultado.dataCaixa[0].saldo < 0){				
					var saldoNegativo = resultado.dataCaixa[0].saldo - (resultado.dataCaixa[0].saldo) - (resultado.dataCaixa[0].saldo);
					divCaixaSaldoTotal.find("span").removeClass("green");
					divCaixaSaldoTotal.find("span").addClass("red");
					divCaixaSaldoTotal.find("span").text("- R$ " + saldoNegativo);
				}else{					
					divCaixaSaldoTotal.find("span").removeClass("red");
					divCaixaSaldoTotal.find("span").addClass("green");
					divCaixaSaldoTotal.find("span").text("R$ " + resultado.dataCaixa[0].saldo);
				}
				
				// TABELA CAIXA BALANCO GERAL - PÁG. CAIXA
				// VERIFICA SE A TABELA EXISTE E CARREGA OS DADOS
				if(tableCaixaBalancoGeral.length){
					var tdReceitas = tableCaixaBalancoGeral.find("td[role=receitas]");
					tdReceitas.html("<h4><b></b></h4>");				
					tdReceitas.find("b").addClass("green");
					tdReceitas.find("b").text("R$ " + resultado.dataCaixa[0].totalEntrada);
					
					var tdDespesas = tableCaixaBalancoGeral.find("td[role=despesas]")
					tdDespesas.html("<h4><b></b></h4>");
					tdDespesas.find("b").addClass("red");
					tdDespesas.find("b").text("R$ " + resultado.dataCaixa[0].totalSaida);
					if(resultado.dataCaixa[0].saldo < 0){					
						var saldoNegativo = resultado.dataCaixa[0].saldo - (resultado.dataCaixa[0].saldo) - (resultado.dataCaixa[0].saldo);
						var tdSaldo = tableCaixaBalancoGeral.find("td[role=saldo]");
						tdSaldo.html("<h4><b></b></h4>");
						tdSaldo.find("b").removeClass("blue");
						tdSaldo.find("b").addClass("red");
						tdSaldo.find("b").text("R$ " + saldoNegativo);
					}else{					
						var tdSaldo = tableCaixaBalancoGeral.find("td[role=saldo]");
						tdSaldo.html("<h4><b></b></h4>");
						tdSaldo.find("b").removeClass("red");
						tdSaldo.find("b").addClass("blue");
						tdSaldo.find("b").text("R$ " + resultado.dataCaixa[0].saldo);
					}
				}
			}
		});
	}	
	
	
	
	// CAIXA BALANCO MENSAL E MOVIMENTAÇÃO - PÁG. CAIXA
	var divAbasMeses = $("div[role=abasMeses]");
	
	if(divAbasMeses.length){
		mes = null;
		ano = null;
		
		// ABAS
		var ulMeses = divAbasMeses.find("#meses");
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
		var montaDivConteudoMeses = function(mes){
			
			// PEGA O MES E ANO ATUAL QUANDO A PÁGINA É CARREGADA
			if(mes == null){
				var dataCompleta = new Date();
				mes = parseInt(dataCompleta.getMonth() + 1);
				ano = parseInt(dataCompleta.getFullYear());
			}
			
			// GARANTE QUE A DIV COM O CONTEUDO DO MES ESTEJA LIMPA ANTES DA MONTAGEM
			divConteudoMeses.html("");
			
			// ADICIONA A QTD. DE DIVS PELAS ABAS CORRESPONDENTES AOS MESES					
			aMeses.each(function(index, value){
				divConteudoMeses.append("<div class='tab-pane'></div>");
			});
			
			divMes = divConteudoMeses.find("div");
			divMes.each(function(index, value){
				switch(index+1){					
					case 1:						
						$(this).prop("id", "janeiro");
						if(mes == (index+1)){
							$(this).addClass("active");
							montaTableCaixaBalancoMensal(mes);
							montaMovimentacaoMensal(mes);
						}
						break;
					case 2:
						$(this).prop("id", "fevereiro");
						if(mes == (index+1)){
							$(this).addClass("active");
							montaTableCaixaBalancoMensal(mes);
							montaMovimentacaoMensal(mes);
						}
						break;
					case 3:
						$(this).prop("id", "marco");
						if(mes == (index+1)){
							$(this).addClass("active");
							montaTableCaixaBalancoMensal(mes);
							montaMovimentacaoMensal(mes);
						}
						break;
					case 4:
						$(this).prop("id", "abril");
						if(mes == (index+1)){
							$(this).addClass("active");
							montaTableCaixaBalancoMensal(mes);
							montaMovimentacaoMensal(mes);
						}
						break;
					case 5:
						$(this).prop("id", "maio");
						if(mes == (index+1)){
							$(this).addClass("active");
							montaTableCaixaBalancoMensal(mes);
							montaMovimentacaoMensal(mes);
						}
						break;
					case 6:
						$(this).prop("id", "junho");
						if(mes == (index+1)){
							$(this).addClass("active");
							montaTableCaixaBalancoMensal(mes);
							montaMovimentacaoMensal(mes);
						}
						break;
					case 7:
						$(this).prop("id", "julho");
						if(mes == (index+1)){
							$(this).addClass("active");
							montaTableCaixaBalancoMensal(mes);
							montaMovimentacaoMensal(mes);
						}
						break;
					case 8:
						$(this).prop("id", "agosto");
						if(mes == (index+1)){
							$(this).addClass("active");
							montaTableCaixaBalancoMensal(mes);
							montaMovimentacaoMensal(mes);
						}
						break;
					case 9:
						$(this).prop("id", "setembro");
						if(mes == (index+1)){
							$(this).addClass("active");
							console.log("5.SWITCH CASE " + (index+1) + ", ADCIONADA A CLASSE ACTIVE");
							montaTableCaixaBalancoMensal(mes);
							montaMovimentacaoMensal(mes);
						}
						break;
					case 10:
						$(this).prop("id", "outubro");
						if(mes == (index+1)){
							$(this).addClass("active");
							montaTableCaixaBalancoMensal(mes);
							montaMovimentacaoMensal(mes);
						}
						break;
					case 11:
						$(this).prop("id", "novembro");
						if(mes == (index+1)){
							$(this).addClass("active");
							montaTableCaixaBalancoMensal(mes);
							montaMovimentacaoMensal(mes);
						}
						break;
					case 12:
						$(this).prop("id", "dezembro");
						if(mes == (index+1)){
							$(this).addClass("active");
							montaTableCaixaBalancoMensal(mes);
							montaMovimentacaoMensal(mes);
						}
						break;
				}				
			});
		}
		
		// MONTA A TABELA DO CAIXA BALANCO MENSAL DO MES ATIVO		
		var montaTableCaixaBalancoMensal = function(mes){
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
		var montaMovimentacaoMensal = function(mes){
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
					if(resultado.dataCaixaMes == "null"){
						divConteudoMeses.html("<h3><b class='blue'></b></h3>");
						divConteudoMeses.addClass("center");
						divConteudoMeses.find("b").text("Não há lançamentos.");						
					}else{
						if(divAbasMeses.length){
							divConteudoMeses.removeClass("center");
							
							// CAIXA MENSAL - CARREGA OS DADOS
							tbodyCaixaBalancoMensal.find("td[role=receitas]").html("<h4><b class='green'>R$ " + resultado.dataCaixaMes[0].totalEntrada + "</b></h4>");
							tbodyCaixaBalancoMensal.find("td[role=despesas]").html("<h4><b class='red'>R$ " + resultado.dataCaixaMes[0].totalSaida + "</b></h4>");
							if(resultado.dataCaixaMes[0].saldo < 0){					
								var saldoNegativo = resultado.dataCaixaMes[0].saldo - (resultado.dataCaixaMes[0].saldo) - (resultado.dataCaixaMes[0].saldo);
								tbodyCaixaBalancoMensal.find("td[role=saldo]").html("<h4><b class='red'>R$ " + saldoNegativo + "</b></h4>");
							}else{
								tbodyCaixaBalancoMensal.find("td[role=saldo]").html("<h4><b class='blue'>R$ " + resultado.dataCaixaMes[0].saldo + "</b></h4>");
							}					
							
							$.each(resultado.dataCaixaMes[0].movimentacoes, function(index, value){					
								
								if(resultado.dataCaixaMes[0].movimentacoes[index].tipoLancamento == "SAIDA"){
									tbodyMovimentacaoMensal.append("<tr><td class='text-right' role='item'><b>" + (index+1) + "</b></td>"
											+ "<td role='data'><b>" + resultado.dataCaixaMes[0].movimentacoes[index].data + "</b></td>"
				                            + "<td role='lancamento'><b>" + resultado.dataCaixaMes[0].movimentacoes[index].descricao + "</b></td>"
				                            + "<td class='text-right red' role='valor'><b>" + resultado.dataCaixaMes[0].movimentacoes[index].valorLancamento + "</b></td></tr>")                    
				                }else{
				                	tbodyMovimentacaoMensal.append("<tr><td class='text-right' role='item'><b>" + (index+1) + "</b></td>"
											+ "<td role='data'><b>" + resultado.dataCaixaMes[0].movimentacoes[index].data + "</b></td>"
				                            + "<td role='lancamento'><b>" + resultado.dataCaixaMes[0].movimentacoes[index].descricao + "</b></td>"
				                            + "<td class='text-right green' role='valor'><b>" + resultado.dataCaixaMes[0].movimentacoes[index].valorLancamento + "</b></td></tr>")
				                }						
							});				
						}
					}
				}
			});
		}	
		
		montaDivConteudoMeses(mes);
		
		// CLIQUE DE UMA ABA MES
		aMeses.on("click", function(e){			
			e.preventDefault();
			mes = "";
			mes = $(this).attr("id");
			montaDivConteudoMeses(mes);			
		});
	}	
});