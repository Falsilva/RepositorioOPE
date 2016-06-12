$(function () {	

	var codigo = 1;
	var mes = null;
	var ano = null;	
	
	var dataAtual = new Date();
	mes = parseInt(dataAtual.getMonth() + 1);
	ano = parseInt(dataAtual.getFullYear());
	
	// AJAX - BUSCA PELO PERÍODO DE 12 MESES ANTERIORES AO MES ATUAL
	$.ajax({
		url:"controller",
		type:"post",
		dataType:"json",
		data:{
			codigo:codigo,
			mes:mes,
			ano:ano,
			action:"pesquisarCaixa",
			tarefa:"grafico"
		},
		success:function(resultado){
			console.log("MES ajax result: " + mes + " ANO: " + ano);
			
			if(resultado.dataCaixaMes == "null"){
				console.log("MES ajax result NULO: " + mes + " ANO: " + ano);								
			}else{
				console.log("MES ajax result OK: " + mes + " ANO: " + ano);
				
				
				resultado.dataCaixa
				
				
				
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
	
	
	// CARREGA OS DADOS NA TABELA PARA UTILIZAÇÃO DO GRÁFICO
	
	
	
	
	
	
	
	
	
	// GRÁFICO
	var tabBalanco = $("tabBalanco");
	
    $('#grafico').highcharts({
    	data: {
            table: tabBalanco
        },
        chart: {	// Tipo do Gráfico - Dual
            zoomType: 'xy'
        },
        title: {
            text: 'Valores de Compras e Vendas'
        },
        xAxis: [{
            categories: ['Jan', 'Fev', 'Mar', 'Abr', 'Mai', 'Jun',
                'Jul', 'Ago', 'Set', 'Out', 'Nov', 'Dez'],
            crosshair: true
        }],
        yAxis: [{ // Primeiro yAxis
            labels: {
                format: '{value}',
                style: {
                    color: Highcharts.getOptions().colors[1]
                }
            },
            title: {
                text: 'Compras e Vendas (R$)',
                style: {
                    color: Highcharts.getOptions().colors[1]
                }
            }
        }, { // Segundo yAxis
            title: {
                text: 'Percentual',
                style: {
                    color: Highcharts.getOptions().colors[0]
                }
            },
            labels: {
                format: '{value} mm',
                style: {
                    color: Highcharts.getOptions().colors[0]
                }
            },
            opposite: true
        }],
        tooltip: {
            shared: true
        },
        legend: {
            layout: 'vertical',
            align: 'left',
            x: 120,
            verticalAlign: 'top',
            y: 100,
            floating: true,
            backgroundColor: (Highcharts.theme && Highcharts.theme.legendBackgroundColor) || '#FFFFFF'
        },
        series: [{
            name: 'Compras',
            type: 'column',
            yAxis: 1,
            data: [49.9, 71.5, 106.4, 129.2, 144.0, 176.0, 135.6, 148.5, 216.4, 194.1, 95.6, 54.4],
            tooltip: {
                valueSuffix: ' %'
            }

        },{
            name: 'Vendas',
            type: 'column',
            yAxis: 1,
            data: [49.9, 71.5, 106.4, 129.2, 144.0, 176.0, 135.6, 148.5, 216.4, 194.1, 95.6, 54.4],
            tooltip: {
                valueSuffix: ' %'
            }

        }, {
            name: 'Balanço',
            type: 'spline',
            data: [7.0, 6.9, 9.5, 14.5, 18.2, 21.5, 25.2, 26.5, 23.3, 18.3, 13.9, 9.6],
            tooltip: {
                valueSuffix: ''
            }
        }]
    });
});