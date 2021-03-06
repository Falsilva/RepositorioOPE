$(document).ready(function(){
	
	// PARA CARREGAMENTO DA LISTA DE CLIENTES
	var divInputCliente = "";	
	
	// MODAL PARA CADASTRAR - CLIQUE DO BOTÃO CADASTRAR EXIBE O MODAL
	$("a[role=cadastrar]").click(function(event){
		event.preventDefault();
		
		// Pega o Modal
		var modal = $("#modal-form-pedido[rel=modalcadastrar]");
		
		// Exibe o Modal Form Cadastrar
		modal.modal();		
	});
	
	// MODAL EXCLUIR - CLIQUE DO BOTÃO EXCLUIR DA LINHA DE UM REGISTRO TABELA EXIBE O MODAL
	$("a[role=excluir]").click(function(event){
		event.preventDefault();		
		
		// Pega a TR
		var tr = $(this).closest("tr");
		
		// Pega as TDs da TR
		var tdNoPedido = tr.find("td:first");
		var tdData = tdNoPedido.next("td");				
		var tdDataPagto = tdData.next("td");
		var tdCliente = tdDataPagto.next("td");
		var tdValor = tdCliente.next("td");
		var tdStatus = tdValor.next("td");
		var tdItensPV = tdStatus.next("td");
		var divItensPV = tdItensPV.find(".grupoDeItens");
		
		// Pega os Dados das TDs
		var noPedidoExcluir = tdNoPedido.text();
		var dataPedidoExcluir = tdData.text();
		var dataPedidoPagtoExcluir = tdDataPagto.text();
		var clienteExcluir = tdCliente.text();
		var valorExcluir = tdValor.text();
		var statusExcluir = tdStatus.text();
		
		// Pega o Modal
		var modal = $("#modal-form-pedido[rel=modalexcluir]");
		
		// Coloca os Dados no Modal e de Forma Somente Leitura
		modal.find("#noPedidoExcluir").prop("readonly", true).val(noPedidoExcluir);
		modal.find("#dataExcluir").prop("readonly", true).val(dataPedidoExcluir);		
		if(dataPedidoPagtoExcluir != ""){			
			modal.find("#dataPagamentoExcluir").closest(".form-group").removeClass("hidden");
			modal.find("#dataPagamentoExcluir").prop("readonly", true).val(dataPedidoPagtoExcluir);			
		}
					
		$.each(divItensPV, function(index, value){
			var divUmMaterial = $(this).find("div:first");
			var divDoisPeso = divUmMaterial.next("div");
			var divTresValor = divDoisPeso.next("div");
			
			if(index == 0){
				modal.find("#itensExcluir").append("<div class='form-inline' id=" + (index + 1) + ">" +	
						"<div class='form-group'>" +
							"<label for='item" + (index + 1) + "'>Item</label>" +
							"<div>" +
								"<input type='text' id='item" + (index + 1) + "' name='item" + (index + 1) + "' value='" + (index + 1) + "' size='1' class='text-center' readonly />" +
							"</div>" +
						"</div>&nbsp;" +
						"<div class='form-group hidden'>" +
							"<label for='codProduto" + (index + 1) + "'>Cód. Produto</label>" +
							"<div>" +
								"<input type='text' id='codProduto" + (index + 1) + "' name='codProduto" + (index + 1) + "' size='3' class='text-center' readonly />" +
							"</div>" +
						"</div>&nbsp;" +
						"<div class='form-group'>" +
							"<label for='material" + (index + 1) + "'>Material</label>" +
							"<div>" +
								"<input type='text' id='material" + (index + 1) + "' name='material" + (index + 1) + "' value='" + divUmMaterial.text() + "' size='37' readonly />" +
							"</div>" +
						"</div>&nbsp;" +
						"<div class='form-group'>" +
							"<label for='peso" + (index + 1) + "'>Peso (Kg)</label>" +
							"<div>" +
								"<input type='text' id='peso" + (index + 1) + "' class='text-right' name='peso" + (index + 1) + "' value='" + divDoisPeso.text() + "' size='9' maxlength='9' readonly />" +
							"</div>" +
						"</div>&nbsp;" +
						"<div class='form-group'>" +
							"<label for='valor" + (index + 1) + "'>Valor (R$)</label>" +
							"<div>" +
								"<input type='text' id='valor" + (index + 1) + "' class='text-right' name='valor" + (index + 1) + "' value='" + divTresValor.text() + "' size='9' maxlength='10' readonly />" +
							"</div>" +
						"</div>" +				
					"</div>" +
					"<div class='space-4'></div>");
			}else{
				modal.find("#itensExcluir").append("<div class='form-inline' id=" + (index + 1) + ">" +	
						"<div class='form-group'>" +							
							"<div>" +
								"<input type='text' id='item" + (index + 1) + "' name='item" + (index + 1) + "' value='" + (index + 1) + "' size='1' class='text-center' readonly />" +
							"</div>" +
						"</div>&nbsp;" +
						"<div class='form-group hidden'>" +							
							"<div>" +
								"<input type='text' id='codProduto" + (index + 1) + "' name='codProduto" + (index + 1) + "' size='3' class='text-center' readonly />" +
							"</div>" +
						"</div>&nbsp;" +
						"<div class='form-group'>" +							
							"<div>" +
								"<input type='text' id='material" + (index + 1) + "' name='material" + (index + 1) + "' value='" + divUmMaterial.text() + "' size='37' readonly />" +
							"</div>" +
						"</div>&nbsp;" +
						"<div class='form-group'>" +							
							"<div>" +
								"<input type='text' id='peso" + (index + 1) + "' class='text-right' name='peso" + (index + 1) + "' value='" + divDoisPeso.text() + "' size='9' maxlength='9' readonly />" +
							"</div>" +
						"</div>&nbsp;" +
						"<div class='form-group'>" +							
							"<div>" +
								"<input type='text' id='valor" + (index + 1) + "' class='text-right' name='valor" + (index + 1) + "' value='" + divTresValor.text() + "' size='9' maxlength='10' readonly />" +
							"</div>" +
						"</div>" +				
					"</div>" +
					"<div class='space-4'></div>");
			}
		});
		
		modal.find("#clienteExcluir").prop("readonly", true).val(clienteExcluir);
		modal.find("#valorExcluir").prop("readonly", true).val(valorExcluir);
		modal.find("#statusExcluir").prop("readonly", true).val(statusExcluir);
		
		// Exibe o Modal Form Excluir
		modal.modal();		
	});	
	
	// BUSCA O NÚMERO DO PRÓXIMO PEDIDO PARA O MODAL CADASTRAR PEDIDO DE VENDA
	$.ajax({
		url:"controller",
		type:"post",
		data:{			
			action:"pesquisarPedidoVenda",
			tarefa:"proximoNumero"
		},
		success:function(resultado){
			if(resultado == "0"){
				$("#noPedido").append("<h3>1</h3>");
			}else{
				$("#noPedido").append("<h3>" + resultado + "</h3>");
			}
		}
	});		
	
	// CARREGA A LISTA DE CLIENTES NO MODAL CADASTRAR PEDIDO DE VENDA
	$("#btnGerarPedido").prop("disabled", true);
	
	divInputCliente = $("#cliente").closest("div");
	
	var resultadoClientes = [];
	var resultadoCodClientes = [];
	$.ajax({
		url:"controller",
		type:"post",
		dataType:"json",
		data:{
			action:"listarClientes",
			tarefa:"pedidoVenda"
		},
		success:function(resultado){
			console.log("resultado OK");
			if(resultado.dataListaClientes == "null"){
				divInputCliente.html("<h3><b class='blue'></b></h3>");
				divInputCliente.addClass("center");
				divInputCliente.find("b").text("Não há clientes cadastrados.");
			}else{
				$.each(resultado.dataListaClientes, function(index, value){	
					
					// GUARDA OS CÓDIGOS DOS CLIENTES PARA UTILIZAÇÃO POSTERIOR
					resultadoCodClientes[index] = resultado.dataListaClientes[index].codigo;	
					
					// GUARDA A FORMA DE EXIBIÇÃO DOS NOMES DOS CLIENTES PARA EXIBIÇÃO POSTERIOR - COMO O AUTOCOMPLETE
					resultadoClientes[index] = resultado.dataListaClientes[index].empresa;					
				});
			}
		}
	});
	
	// COMPLETA O CAMPO CLIENTE DO MODAL CADASTRAR PEDIDO
	$("#cliente").autocomplete({
		source: resultadoClientes
	});	
	
	// GUARDA O CÓDIGO DO CLIENTE APÓS SELECIONADO
	var codCliente = "";	
	var encontrouCliente = false;
	$("#cliente").blur(function(){
		encontrouCliente = false;
		var cli = $(this);
		codCliente = "";
		
		if(cli.val() != null && cli.val() != ""){
			$.each(resultadoClientes, function(index, value){					
				if(cli.val() == value){					
					codCliente = resultadoCodClientes[index];					
					encontrouCliente = true;
				}
			});
		}else{
			$("#btnGerarPedido").prop("disabled", true);
		}
		if(encontrouCliente == true){
			if(i != 0 && i == ids.length){
				$("#btnGerarPedido").prop("disabled", false);						
			}
		}else{
			$("#btnGerarPedido").prop("disabled", true);
			cli.val("");
		}
	});	
	
	// CARREGA A LISTA DE PRODUTOS NO MODAL CADASTRAR PEDIDO DE VENDA	
	var resultadoCodProdutos = []; 	
	var resultadoProdutos = [];
	var resultadoPrecoVendaProdutos = [];
	var ids = [];
	$.ajax({
		url:"controller",
		type:"post",
		dataType:"json",
		data:{
			action:"listarProdutos",
			tarefa:"pedido"
		},
		success:function(resultado){
			if(resultado.dataListaProdutos == "null"){
				$("a[role=additem").parent().html("<h3><b class='blue'></b></h3>");
				$("a[role=additem").parent().addClass("center");
				$("a[role=additem").parent().find("b").text("Não há produtos cadastrados.");
			}else{
				$.each(resultado.dataListaProdutos, function(index, value){					
					resultadoCodProdutos[index] = resultado.dataListaProdutos[index].codigo;
					resultadoProdutos[index] = resultado.dataListaProdutos[index].produto;
					resultadoPrecoVendaProdutos[index] = resultado.dataListaProdutos[index].precoVenda;
				});
			}
		}
	});	
	
	// FUNCAO PARA MONTAR UMA LINHA DE ITEM
	var insereLinha = function(i){
		$("#itemNovo")
			.append("<div class='form-inline' id=" + i + ">" +	
						"<div class='form-group'>" +
							"<label for='item" + i + "'>Item</label>" +
							"<div>" +
								"<input type='text' id='item" + i + "' name='item" + i + "' value='" + i + "' size='1' class='text-center' readonly />" +
							"</div>" +
						"</div>&nbsp;" +
						"<div class='form-group hidden'>" +
							"<label for='codProduto" + i + "'>Cód. Produto</label>" +
							"<div>" +
								"<input type='text' id='codProduto" + i + "' name='codProduto" + i + "' size='3' class='text-center' />" +
							"</div>" +
						"</div>&nbsp;" +
						"<div class='form-group'>" +
							"<label for='material" + i + "'>Material</label>" +
							"<div>" +
								"<input type='text' id='material" + i + "' name='material" + i + "' size='37' />" +
							"</div>" +
						"</div>&nbsp;" +
						"<div class='form-group'>" +
							"<label for='peso" + i + "'>Peso (Kg)</label>" +
							"<div>" +
								"<input type='text' id='peso" + i + "' class='text-right' name='peso" + i + "' size='9' maxlength='9' />" +
							"</div>" +
						"</div>&nbsp;" +
						"<div class='form-group'>" +
							"<label for='valor" + i + "'>Valor (R$)</label>" +
							"<div>" +
								"<input type='text' id='valor" + i + "' class='text-right' name='valor" + i + "' size='9' maxlength='10' />" +
							"</div>" +
						"</div>&nbsp;" +
						"<div class='form-group'>" +
							"<label></label>" +
							"<div class='text-center acao'>" +
								"<div class='action-buttons'>" +
									"<a href='#' role='removerItem' id='removerItem" + i + "'>" +
										"<span class='red'>" +
											"<i class='ace-icon fa fa-times bigger-160'></i>" +
										"</span>" +
									"</a>" +
								"</div>" +
							"</div>" +
						"</div>" +
					"</div>" +
					"<div class='space-4'></div>");		
	}
	
	// CLIQUE PARA ADICIONAR UM ITEM DE PRODUTO NO MODAL CADASTRAR PEDIDO
	var i = 0;
	$("a[role=additem").click(function(e){
		e.preventDefault();
		
		i++;		
		
		if(codCliente != "" && i == ids.length){
			$("#btnGerarPedido").prop("disabled", false);
		}else{
			$("#btnGerarPedido").prop("disabled", true);
		}		
		
		// MONTA UM LINHA DE ITEM
		insereLinha(i);		
		
		// ADICIONA UM EVENTO DE REMOÇÃO DE ITEM
		$("a[id='removerItem" + i + "']").click(function(e){
			e.preventDefault();			
			
			var linhaRemovida = $(this).closest(".form-inline");			
			var id = linhaRemovida.attr("id");
					
			$.each(ajaxCodProduto, function(index, value){
				if((id - 1) == index){
					ajaxCodProduto.splice(index, 1);
					ids.splice(index, 1);					
				}
			});			
			
			// REMOVE A DIV DE ESPAÇAMENTO ENTRE LINHAS
			linhaRemovida.next(".space-4").remove();
			
			// REMOVE A DIV LINHA DE FATO
			linhaRemovida.remove();	
			
			// RENOMEANDO OS IDs			
			var lista = $("#itemNovo").find(".form-inline");
			$.each(lista, function(index, value){
				
				// RENOMEIA O ID DO FORM-INLINE
				$(this).attr("id", (index+1));
				var id = $(this).attr("id");				
				
				// RENOMEIA OS IDs DOS INPUTS
				var inputs = $(this).find("input");
				$.each(inputs, function(ind, val){
					var codProdutoRenomeado = $(this);
					var idNovo = "";
					switch(ind){
						case 0:
							idNovo = "item" + id;
							$(this).val(id);
							break;
						case 1:
							idNovo = "codProduto" + id;												
							break;
						case 2:
							idNovo = "material" + id;
							break;
						case 3:
							idNovo = "peso" + id;
							break;
						case 4:
							idNovo = "valor" + id;
							break;
					}					
					$(this).attr("id", idNovo);					
				});
				
				// RENOMEIA OS IDs DOS LABELS
				var labels = $(this).attr("label");
				$.each(labels, function(ind, val){
					var idNovo = "";
					switch(ind){
						case 0:
							idNovo = "item" + id;
							break;
						case 1:
							idNovo = "codProduto" + id;
							break;
						case 2:
							idNovo = "material" + id;
							break;
						case 3:
							idNovo = "peso" + id;
							break;
						case 4:
							idNovo = "valor" + id;
							break;
						default:
							break;
					}
					$(this).attr("id", idNovo);
				});	
				
				// RENOMEIA O ID DO BOTÃO
				$(this).find("#removerItem" + index).attr("id", id);
			});
			
			// VERIFICANDO SE HÁ ALGUM CAMPO DE MATERIAL VAZIO			
			var campoVazio = false;
			var linhasItens = $("#itemNovo").find(".form-inline");
			$.each(linhasItens, function(index, value){
				if($("#material" + (index + 1)).val() == ""){
					campoVazio = true;
				}
			});
			
			i--;
			
			if(i == 0){
				$("#btnGerarPedido").prop("disabled", true);
			}else{
				if(codCliente != "" && i == ids.length && campoVazio == false){
					$("#btnGerarPedido").prop("disabled", false);
				}else{
					$("#btnGerarPedido").prop("disabled", true);
				}
			}			
		});		
		
		// COMPLETA O CAMPO DO PRODUTO NO MODAL CADASTRAR PEDIDO	
		$("#material" + i).autocomplete({
			source: resultadoProdutos
		});		
		
		var guardaMaterial = "";
		// QUANDO O CAMPO PRODUTO GANHA O FOCO, GARANTE DE REMOVER O COD. CASO PREENCHIDO ANTERIORMENTE E A DESABILITAÇÃO DO BOTÃO
		$("#material" + i).focus(function(){
			console.log("\tMATERIAL GANHOU FOCO:\n\t\ti......................: " + i + "\n\t\tids.length.............: " + ids.length + "\n\t\tVALOR do ids...........: " + ids[i-1] + "\n\t\tVALOR do ajaxCodProduto: " + ajaxCodProduto[i-1]);
			
			guardaMaterial = $(this).val();			
			ajaxCodProduto.splice((i - 1), 1);
			ids.splice((i - 1), 1);			
		});
		
		// QUANDO O CAMPO PRODUTO PERDE O FOCO, COMPLETA OS CAMPOS PESO E VALOR DO PRODUTO NO MODAL CADASTRAR PEDIDO
		$("#material" + i).blur(function(){
			console.log("\tMATERIAL PERDEU FOCO:");			
			
			var prod = $(this);
			var encontrado = false;
			var id = prod.closest(".form-inline").attr("id");			
			if(prod.val() != null && prod.val() != ""){
				$.each(resultadoProdutos, function(index, value){
					
					// ADICIONA OS DADOS NOS INPUTs - CÓD. PRODUTO, PESO, VALOR
					if(prod.val() == value){
						
						$("#codProduto" + id).val(resultadoCodProdutos[index]);
						$("#peso" + id).val(1);
						$("#valor" + id).val(resultadoPrecoVendaProdutos[index]);
						$("#valor" + id).val(formataNumeroParaExibicao($("#valor" + id).val(), 2, ',', '.'));
						
						// MONTANDO O ARRAY DE DADOS PARA ENVIO DO AJAX - CÓD. PRODUTO						
						ajaxCodProduto.push(resultadoCodProdutos[index]);
						ids.push(id);
						
						
						$.each(ids, function(ind, val){
							console.log("\t\tids         >>> INDEX: " + ind + " VALUE: " + val);
						});
						$.each(ajaxCodProduto, function(ind, val){
							console.log("\t\tajaxCodProd >>> INDEX: " + ind + " VALUE: " + val);
						});
						
						encontrado = true;						
						
						if(codCliente != "" && i == ids.length){							
							$("#btnGerarPedido").prop("disabled", false);
						}else{							
							$("#btnGerarPedido").prop("disabled", true);
						}
					}					
				});
				if(encontrado == false){
					prod.val(guardaMaterial);
					$.each(resultadoProdutos, function(index, value){
						if(prod.val() == value){
							
							$("#codProduto" + id).val(resultadoCodProdutos[index]);
							$("#peso" + id).val(1);
							$("#valor" + id).val(resultadoPrecoVendaProdutos[index]);
							$("#valor" + id).val(formataNumeroParaExibicao($("#valor" + id).val(), 2, ',', '.'));
							
							// MONTANDO O ARRAY DE DADOS PARA ENVIO DO AJAX - CÓD. PRODUTO						
							ajaxCodProduto.push(resultadoCodProdutos[index]);
							ids.push(id);						
							
							encontrado = true;							
							
							if(codCliente != "" && i == ids.length){								
								$("#btnGerarPedido").prop("disabled", false);
							}else{								
								$("#btnGerarPedido").prop("disabled", true);
							}
						}
					});
				}
				guardaMaterial="";
			}else{
				
				prod.val(guardaMaterial);
				$.each(resultadoProdutos, function(index, value){
					if(prod.val() == value){
							
						$("#codProduto" + id).val(resultadoCodProdutos[index]);
						$("#peso" + id).val(1);
						$("#valor" + id).val(resultadoPrecoVendaProdutos[index]);
						$("#valor" + id).val(formataNumeroParaExibicao($("#valor" + id).val(), 2, ',', '.'));
							
						// MONTANDO O ARRAY DE DADOS PARA ENVIO DO AJAX - CÓD. PRODUTO						
						ajaxCodProduto.push(resultadoCodProdutos[index]);
						ids.push(id);							
							
						$.each(ids, function(ind, val){
							console.log("\t\tids         >>> INDEX: " + ind + " VALUE: " + val);
						});
						$.each(ajaxCodProduto, function(ind, val){
							console.log("\t\tajaxCodProd >>> INDEX: " + ind + " VALUE: " + val);
						});
							
						encontrado = true;							
						
						if(codCliente != "" && i == ids.length){							
							$("#btnGerarPedido").prop("disabled", false);
						}else{							
							$("#btnGerarPedido").prop("disabled", true);
						}
					}
				});
				guardaMaterial="";
			}			
		});
		
		$("#peso" + i).mask("000000,000", {reverse: true});
		$("#valor" + i).mask("00000000,00", {reverse: true});
		
		$("#peso" + i).blur(function(){
			console.log("Campo peso perdeu o foco");
			
			var id = $(this).closest(".form-inline").attr("id");
			
			var vlrPeso = $(this).val().replace('.','.').replace(',','.');
			var vlrValor = "";			
			if(vlrPeso != ""){
				$.each(resultadoCodProdutos, function(index, value){
					if(value == $("#codProduto" + id).val()){
						vlrValor = resultadoPrecoVendaProdutos[index];					
					}
				});			
				var vlrTotal = parseFloat(vlrPeso) * parseFloat(vlrValor);
				$("#valor" + id).val(formataNumeroParaExibicao(vlrTotal, 2, ',', '.'));
				$(this).val(vlrPeso.replace('.',','));
				console.log("PESO: " + vlrPeso)
			}else{
				$("#valor" + id).val("");
			}
		});
		
		function formataNumeroParaExibicao(number, decimals, dec_point, thousands_sep) {
			var n = number, c = isNaN(decimals = Math.abs(decimals)) ? 2 : decimals;
			var d = dec_point == undefined ? "," : dec_point;
			var t = thousands_sep == undefined ? "." : thousands_sep, s = n < 0 ? "-" : "";
			var i = parseInt(n = Math.abs(+n || 0).toFixed(c)) + "", j = (j = i.length) > 3 ? j % 3 : 0;
			return s + (j ? i.substr(0, j) + t : "") + i.substr(j).replace(/(\d{3})(?=\d)/g, "$1" + t) + (c ? d + Math.abs(n - i).toFixed(c).slice(2) : "");
		}
	});	
	
	// ENVIA OS DADOS PARA O CADASTRAMENTO DO PEDIDO
	var ajaxCliente = "";
	var ajaxCodProduto = [];
	var ajaxPesos = [];
	var ajaxPrecos = [];		
		
	// CADASTRA O PEDIDO
	$("#btnGerarPedido").click(function(){
		
		function formataNumeroParaEnvio(number) {
			var numberFormatado = number.replace(".", "");
			return parseFloat(numberFormatado.replace(",", "."));			
		}
		
		var lista = $("#itemNovo").find(".form-inline");
		
		// PEGA OS DADOS DOS ITENS - PELA VERICACAO DO CÓDIGO DO PRODUTO
		if((ajaxCodProduto == null || ajaxCodProduto == "") && (codCliente == null || codCliente == "")){
			console.log("nulo");
		}else{
			
			var contador = 0;
			
			// PEGA OS PESOS E PREÇOS DOS INPUTS
			$.each(lista, function(index, value){
				if (typeof value == 'undefined') {
					console.log("INDEFINIDO: index " + index);
				}else{
					var pesoFormatado = formataNumeroParaEnvio($("#peso" + (index + 1)).val());
					var valorFormatado = formataNumeroParaEnvio($("#valor" + (index + 1)).val());
					console.log("ANTES.: " + $("#valor" + (index + 1)).val());
					console.log("DEPOIS: " + valorFormatado);
					if($.isNumeric(pesoFormatado) && $.isNumeric(valorFormatado)){
						console.log("São números, PESO: " + pesoFormatado + " VALOR: " + valorFormatado);						
						ajaxPesos.push(pesoFormatado);
						ajaxPrecos.push(valorFormatado);
						contador++;
					}else{
						if(!$.isNumeric($("#peso" + (index + 1)).val()) && !$.isNumeric($("#valor" + (index + 1)).val())){
							$("#valor" + (index + 1)).closest(".form-group").addClass("has-error");
							$("#peso" + (index + 1)).closest(".form-group").addClass("has-error");
							console.log("Não são números VALOR: " + $("#valor" + (index + 1)).val() + " PESO: " + $("#peso" + (index + 1)).val());
						}else{
							if($.isNumeric($("#peso" + (index + 1)).val())){
								$("#valor" + (index + 1)).closest(".form-group").addClass("has-error");
								console.log("Não é número VALOR: " + $("#valor" + (index + 1)).val());
							}else{
								$("#peso" + (index + 1)).closest(".form-group").addClass("has-error");
								console.log("Não é número PESO: " + $("#peso" + (index + 1)).val());
							}
						}
					}		
				}				
			});
							
			// VERIFICA SE TODOS OS CAMPOS FORAM PREENCHIDOS COM O CONTADOR DE LINHAS
			if(contador == lista.length){				
				
				var jsonItens = {};
				
				// CONVERTENDO OS ARRAYS EM JSON PARA ENVIO POR AJAX 
				$.each(ajaxCodProduto, function(index, value){
					if(index == 0){						
						jsonItens = "{\"peso\":\"" + ajaxPesos[index] + "\"," +
									"\"valorItem\":\"" + ajaxPrecos[index] + "\"," +
									"\"produto\":" +
										"{\"codigo\":\"" + value + "\"}}";											
					}else{						
						jsonItens += "," + "{\"peso\":\"" + ajaxPesos[index] + "\"," +
											"\"valorItem\":\"" + ajaxPrecos[index] + "\"," +
											"\"produto\":" +
												"{\"codigo\":\"" + value + "\"}}";;
					}					
				});
				
				// AJAX - ENVIANDO OS DADOS PARA O CADASTRAMENTO DO PEDIDO
				$.ajax({
					url:"controller",
					type:"post",
					data:{
						codCliente:codCliente,
						itens:jsonItens,				
						action:"cadastrarPedidoVenda"
					},
					//antes de enviar ele alerta para esperar
			        beforeSend : function(){
			            $('#carregando').show('100');
			        },
					success:function(resultado){
						$('#carregando').hide('100');
						console.log("Resultado recebido: " + resultado);
						$("#modal-form-pedido[rel=modalcadastrar]").find(".modal-header").html("<h4 class='blue bigger'>Resultado...</h4>").prepend("<button type='button' class='close' data-dismiss='modal'>&times;</button>");
						$("#modal-form-pedido[rel=modalcadastrar]").find(".modal-body").html("<h3>" + resultado + "</h3>");
						var botao = $("#modal-form-pedido[rel=modalcadastrar]").find("#btnCancelar").html("<i class='ace-icon fa fa-times'></i> Fechar");				
						botao.text("Fechar");
						$("#modal-form-pedido[rel=modalcadastrar]").find("#btnGerarPedido").addClass("hidden");
					},
					error:function(resultado){
						$('#carregando').hide('100');
						console.log("Resultado recebido: " + resultado);
						$("#modal-form-pedido[rel=modalcadastrar]").find(".modal-header").html("<h4 class='blue bigger'>Resultado...</h4>").prepend("<button type='button' class='close' data-dismiss='modal'>&times;</button>");
						$("#modal-form-pedido[rel=modalcadastrar]").find(".modal-body").html("<h3>Desculpe, houve uma falha!</h3>");
						var botao = $("#modal-form-pedido[rel=modalcadastrar]").find("#btnCancelar").html("<i class='ace-icon fa fa-times'></i> Fechar");				
						botao.text("Fechar");
						$("#modal-form-pedido[rel=modalcadastrar]").find("#btnGerarPedido").addClass("hidden");
					}
				});
			}
		}
	});
		
	var noPedidoEditar_tmp = "";
	var dataPedidoAberturaEditar_tmp = "";
	var dataPedidoPagtoEditar_tmp = "";
	var clienteEditar_tmp = "";
	var codClienteEditar_tmp = "";
	var valorEditar_tmp = "";
	var statusEditar_tmp = "";
	var itensMaterialPV_tmp = [];
	var itensPesoPV_tmp = [];
	var itensValorItemPV_tmp = [];
		
	var j = 0;
	
	// FUNÇÃO PARA FORMATAR O PESO E OS VALORES PARA EXIBIÇÃO
	function formataNumeroParaExibicao(number, decimals, dec_point, thousands_sep) {
		var n = number, c = isNaN(decimals = Math.abs(decimals)) ? 2 : decimals;
		var d = dec_point == undefined ? "," : dec_point;
		var t = thousands_sep == undefined ? "." : thousands_sep, s = n < 0 ? "-" : "";
		var i = parseInt(n = Math.abs(+n || 0).toFixed(c)) + "", j = (j = i.length) > 3 ? j % 3 : 0;
		return s + (j ? i.substr(0, j) + t : "") + i.substr(j).replace(/(\d{3})(?=\d)/g, "$1" + t) + (c ? d + Math.abs(n - i).toFixed(c).slice(2) : "");
	}
	
	// FUNÇÃO PARA FORMATAR O PESO E OS VALORES PARA ENVIO DE DADOS OU CONVERSÃO PARA CÁLCULOS
	function formataNumeroParaEnvio(number) {
		var numberFormatado = number.replace(".", "");
		return parseFloat(numberFormatado.replace(",", "."));			
	}
	
	// FUNCAO PARA MONTAR UMA LINHA DE ITEM
	var insereLinhaEditar = function(j){	
		
		var listaParaInserir = $("#itensEditar").find(".form-inline");		
		j = listaParaInserir.length + 1;		
		
		$("#itensEditar")
			.append("<div class='form-inline' id=" + j + ">" +	
						"<div class='form-group'>" +
							"<label for='itemEditar" + j + "'>Item</label>" +
							"<div>" +
								"<input type='text' id='itemEditar" + j + "' name='itemEditar" + j + "' value='" + j + "' size='1' class='text-center' readonly />" +
							"</div>" +
						"</div>&nbsp;" +
						"<div class='form-group hidden'>" +
							"<label for='codProdutoEditar" + j + "'>Cód. Produto</label>" +
							"<div>" +
								"<input type='text' id='codProdutoEditar" + j + "' name='codProdutoEditar" + j + "' size='3' class='text-center' />" +
							"</div>" +
						"</div>&nbsp;" +
						"<div class='form-group'>" +
							"<label for='materialEditar" + j + "'>Material</label>" +
							"<div>" +
								"<input type='text' id='materialEditar" + j + "' name='materialEditar" + j + "' size='36' />" +
							"</div>" +
						"</div>&nbsp;" +
						"<div class='form-group'>" +
							"<label for='pesoEditar" + j + "'>Peso (Kg)</label>" +
							"<div>" +
								"<input type='text' id='pesoEditar" + j + "' class='text-right' name='pesoEditar" + j + "' size='9' maxlength='9' />" +
							"</div>" +
						"</div>&nbsp;" +
						"<div class='form-group'>" +
							"<label for='valorItemEditar" + j + "'>Valor (R$)</label>" +
							"<div>" +
								"<input type='text' id='valorItemEditar" + j + "' class='text-right' name='valorItemEditar" + j + "' size='9' maxlength='10' />" +
							"</div>" +
						"</div>&nbsp;" +
						"<div class='form-group'>" +
							"<label></label>" +
							"<div class='text-center acao'>" +
								"<div class='action-buttons'>" +
									"<a href='#' role='removerItemEditar' id='removerItemEditar" + j + "'>" +
										"<span class='red'>" +
											"<i class='ace-icon fa fa-times bigger-160'></i>" +
										"</span>" +
									"</a>" +
								"</div>" +
							"</div>" +
						"</div>" +
					"</div>" +
					"<div class='space-4'></div>");
		
		var valorZeroOuVazio = false;
		
		$.each($("#itensEditar").find(".form-inline"), function(index, value){						
			if($("#valorItemEditar" + (index + 1)).val() == "" || $("#valorItemEditar" + (index + 1)).val() == 0 
					|| $("#pesoEditar" + (index + 1)).val() == "" || $("#pesoEditar" + (index + 1)).val() == 0
					|| $("#materialEditar" + (index + 1)).val() == ""){							
				valorZeroOuVazio = true;
				$("#btnEditarPedido").prop("disabled", true);
				console.log("PESO FOCADO - DESATIVADO - MATERIAL, PESO ou VALOR = VAZIO");
			}
		});
		
		if(valorZeroOuVazio == false){
			console.log("PESO FOCADO - ATIVADO - PESOS e VALORES OK");
			$("#btnEditarPedido").prop("disabled", false);
		}
		
		// ADICIONA UM EVENTO DE REMOÇÃO DE ITEM
		$("a[id='removerItemEditar" + j + "']").click(function(e){
			e.preventDefault();
			
			var linhaRemovida = $(this).closest(".form-inline");			
			var id = linhaRemovida.attr("id");
			
			// REMOVE OS CODS. PRODUTOS E ID PARA O AJAX
			$.each(ajaxCodProdutoEditar, function(index, value){
				if((id - 1) == index){
					ajaxCodProdutoEditar.splice(index, 1);
					ids.splice(index, 1);					
				}
			});			
			
			// REMOVE A DIV DE ESPAÇAMENTO ENTRE LINHAS
			linhaRemovida.next(".space-4").remove();
			
			// REMOVE A DIV LINHA DE FATO
			linhaRemovida.remove();	
			
			// RENOMEANDO OS IDs			
			var lista = $("#itensEditar").find(".form-inline");
			$.each(lista, function(index, value){
				
				// RENOMEIA O ID DO FORM-INLINE
				$(this).attr("id", (index+1));
				var id = $(this).attr("id");				
				
				// RENOMEIA OS IDs DOS INPUTS
				var inputs = $(this).find("input");
				$.each(inputs, function(ind, val){
					var codProdutoRenomeado = $(this);
					var idNovo = "";
					switch(ind){
						case 0:
							idNovo = "itemEditar" + id;
							$(this).val(id);
							break;
						case 1:
							idNovo = "codProdutoEditar" + id;												
							break;
						case 2:
							idNovo = "materialEditar" + id;
							break;
						case 3:
							idNovo = "pesoEditar" + id;
							break;
						case 4:
							idNovo = "valorItemEditar" + id;
							break;
					}					
					$(this).attr("id", idNovo);					
				});
				
				// RENOMEIA OS IDs DOS LABELS
				var labels = $(this).attr("label");
				$.each(labels, function(ind, val){
					var idNovo = "";
					switch(ind){
						case 0:
							idNovo = "itemEditar" + id;
							break;
						case 1:
							idNovo = "codProdutoEditar" + id;
							break;
						case 2:
							idNovo = "materialEditar" + id;
							break;
						case 3:
							idNovo = "pesoEditar" + id;
							break;
						case 4:
							idNovo = "valorItemEditar" + id;
							break;
						default:
							break;
					}
					$(this).attr("id", idNovo);
				});	
				
				// RENOMEIA O ID DO BOTÃO
				$(this).find("#removerItemEditar" + index).attr("id", id);
			});
			
			// VERIFICANDO SE HÁ ALGUM CAMPO DE MATERIAL VAZIO		
			var linhasItens = $("#itensEditar").find(".form-inline");
			
			j = linhasItens.length;			
			
			if(j == 0){
				$("#valorEditar").val(formataNumeroParaExibicao(0, 2, ",", "."));
				$("#btnEditarPedido").prop("disabled", true);				
			}else{
				$.each($("#itensEditar").find(".form-inline"), function(index, value){						
					if($("#valorItemEditar" + (index + 1)).val() == "" || $("#valorItemEditar" + (index + 1)).val() == 0
							|| $("#pesoEditar" + (index + 1)).val() == "" || $("#pesoEditar" + (index + 1)).val() == 0
							|| $("#materialEditar" + (index + 1)).val() == ""){
						valorZeroOuVazio = true;
						$("#btnEditarPedido").prop("disabled", true);
					}else{
						$("#valorItemEditar" + (index + 1)).focus();
						$("#valorItemEditar" + (index + 1)).blur();
					}
				});
				if(valorZeroOuVazio == false){
					$("#valorItemEditar" + 1).focus();
					$("#valorItemEditar" + 1).blur();
					$("#btnEditarPedido").prop("disabled", false);
				}
			}			
		});		
		
		// COMPLETA O CAMPO DO PRODUTO NO MODAL CADASTRAR PEDIDO	
		$("#materialEditar" + j).autocomplete({
			source: resultadoProdutos
		});
			
		// COLOCA O FOCO NO CAMPO MATERIAL
		$("#materialEditar" + j).focus();
		
		// QUANDO O CAMPO PRODUTO GANHA O FOCO, GARANTE DE REMOVER O COD. CASO PREENCHIDO ANTERIORMENTE E A DESABILITAÇÃO DO BOTÃO
		$("#materialEditar" + j).focus(function(){
			guardaMaterial = $(this).val();
			ajaxCodProdutoEditar.splice((j - 1), 1);
			idsEditar.splice((j - 1), 1);			
			$.each($("#itensEditar").find(".form-inline"), function(index, value){						
				if($("#valorItemEditar" + (index + 1)).val() == "" || $("#valorItemEditar" + (index + 1)).val() == 0
						|| $("#pesoEditar" + (index + 1)).val() == "" || $("#pesoEditar" + (index + 1)).val() == 0
						|| $("#materialEditar" + (index + 1)).val() == ""){
					valorZeroOuVazio = true;
					$("#btnEditarPedido").prop("disabled", true);
				}
			});
			if(valorZeroOuVazio == false){
				$("#btnEditarPedido").prop("disabled", false);
			}
		});
		
		// QUANDO O CAMPO PRODUTO PERDE O FOCO, COMPLETA OS CAMPOS PESO E VALOR DO PRODUTO NO MODAL CADASTRAR PEDIDO
		var guardaMaterial = "";
		$("#materialEditar" + j).blur(function(){			
			var prod = $(this);
			var prodEncontrado = false;
			var id = prod.closest(".form-inline").attr("id");			
			if(prod.val() != null && prod.val() != ""){
				console.log("MATERIAL blur - DIFERENTE DE NULO ou VAZIO");
				$.each(resultadoProdutos, function(index, value){
					
					// ADICIONA OS DADOS NOS INPUTs - CÓD. PRODUTO, PESO, VALOR
					if(prod.val() == value){
						console.log("MATERIAL blur - NÃO MUDOU");
						$("#codProdutoEditar" + id).val(resultadoCodProdutos[index]);
						if($("#pesoEditar" + id).val() != "" || $("#pesoEditar" + id).val() != 0){															
							var vlrItem = parseFloat($("#pesoEditar" + id).val()) * parseFloat(resultadoPrecoVendaProdutos[index]);
							$("#valorItemEditar" + id).val(vlrItem);
						}else{
							$("#pesoEditar" + id).val(1);
							$("#valorItemEditar" + id).val(resultadoPrecoVendaProdutos[index]);
						}
						$("#valorItemEditar" + id).val(formataNumeroParaExibicao($("#valorItemEditar" + id).val(), 2, ',', '.'));
						
						// MONTANDO O ARRAY DE DADOS PARA ENVIO DO AJAX - CÓD. PRODUTO						
						ajaxCodProdutoEditar.push(resultadoCodProdutos[index]);
						idsEditar.push(id);
						
						prodEncontrado = true;
						var valorTotalEditar = parseFloat(0);
						$.each($("#itensEditar").find(".form-inline"), function(index, value){						
							if($("#valorItemEditar" + (index + 1)).val() == "" || $("#valorItemEditar" + (index + 1)).val() == 0
									|| $("#pesoEditar" + (index + 1)).val() == "" || $("#pesoEditar" + (index + 1)).val() == 0
									|| $("#materialEditar" + (index + 1)).val() == ""){
								valorZeroOuVazio = true;
								$("#btnEditarPedido").prop("disabled", true);
							}else{
								if($("#valorEditar").val() != "" && $("#valorEditar").val() != 0){
									var valorConvertido = formataNumeroParaEnvio($("#valorItemEditar" + (index + 1)).val());
									console.log("VLR. TOTAL ANTES  (valorTotalEditar): " + valorTotalEditar + " >>> vlrItemConvertido: " + valorConvertido);
									valorTotalEditar = parseFloat(valorTotalEditar) + valorConvertido;
									$("#valorEditar").val(formataNumeroParaExibicao(valorTotalEditar, 2, ",", "."));
									console.log("VLR. TOTAL DEPOIS  (valorTotalEditar): " + valorTotalEditar);
								}
							}
						});
						if(valorZeroOuVazio == false){
							$("#btnEditarPedido").prop("disabled", false);
						}
					}					
				});
				if(prodEncontrado == false){
					console.log("MATERIAL blur - NAO ENCONTRADO");
					prod.val(guardaMaterial);
					$.each(resultadoProdutos, function(index, value){
						if(prod.val() == value){
							$("#codProdutoEditar" + id).val(resultadoCodProdutos[index]);
							if($("#pesoEditar" + id).val() != "" || $("#pesoEditar" + id).val() != 0){															
								var vlrItem = parseFloat($("#pesoEditar" + id).val()) * parseFloat(resultadoPrecoVendaProdutos[index]);
								$("#valorItemEditar" + id).val(vlrItem);
							}else{
								$("#pesoEditar" + id).val(1);
								$("#valorItemEditar" + id).val(resultadoPrecoVendaProdutos[index]);
							}
							$("#valorItemEditar" + id).val(formataNumeroParaExibicao($("#valorItemEditar" + id).val(), 2, ',', '.'));																		
							
							// MONTANDO O ARRAY DE DADOS PARA ENVIO DO AJAX - CÓD. PRODUTO						
							ajaxCodProdutoEditar.push(resultadoCodProdutos[index]);
							idsEditar.push(id);						
							
							prodEncontrado = true;							
							
							var valorTotalEditar = parseFloat(0);
							$.each($("#itensEditar").find(".form-inline"), function(index, value){						
								if($("#valorItemEditar" + (index + 1)).val() == "" || $("#valorItemEditar" + (index + 1)).val() == 0
										|| $("#pesoEditar" + (index + 1)).val() == "" || $("#pesoEditar" + (index + 1)).val() == 0
										|| $("#materialEditar" + (index + 1)).val() == ""){
									valorZeroOuVazio = true;
									$("#btnEditarPedido").prop("disabled", true);
								}else{
									if($("#valorEditar").val() != "" && $("#valorEditar").val() != 0){
										console.log("VLR. TOTAL ANTES  (valorTotalEditar): " + valorTotalEditar);
										var valorConvertido = formataNumeroParaEnvio($("#valorItemEditar" + (index + 1)).val());
										valorTotalEditar = parseFloat(valorTotalEditar) + valorConvertido;
										$("#valorEditar").val(formataNumeroParaExibicao(valorTotalEditar, 2, ",", "."));
										console.log("VLR. TOTAL DEPOIS  (valorTotalEditar): " + valorTotalEditar);
									}
								}
							});
							if(valorZeroOuVazio == false){
								$("#btnEditarPedido").prop("disabled", false);
							}
						}
					});
				}
				guardaMaterial="";
			}else{
				
				prod.val(guardaMaterial);
				console.log("MATERIAL blur - NULO ou VAZIO");
				$.each(resultadoProdutos, function(index, value){
					if(prod.val() == value){
						$("#codProdutoEditar" + id).val(resultadoCodProdutos[index]);
						if($("#pesoEditar" + id).val() != "" || $("#pesoEditar" + id).val() != 0){															
							var vlrItem = parseFloat($("#pesoEditar" + id).val()) * parseFloat(resultadoPrecoVendaProdutos[index]);
							$("#valorItemEditar" + id).val(vlrItem);
						}else{
							$("#pesoEditar" + id).val(1);
							$("#valorItemEditar" + id).val(resultadoPrecoVendaProdutos[index]);
						}
						$("#valorItemEditar" + id).val(formataNumeroParaExibicao($("#valorItemEditar" + id).val(), 2, ',', '.'));						
						
						// MONTANDO O ARRAY DE DADOS PARA ENVIO DO AJAX - CÓD. PRODUTO						
						ajaxCodProdutoEditar.push(resultadoCodProdutos[index]);
						idsEditar.push(id);
						
						prodEncontrado = true;							
						
						var valorTotalEditar = parseFloat(0);
						$.each($("#itensEditar").find(".form-inline"), function(index, value){						
							if($("#valorItemEditar" + (index + 1)).val() == "" || $("#valorItemEditar" + (index + 1)).val() == 0
									|| $("#pesoEditar" + (index + 1)).val() == "" || $("#pesoEditar" + (index + 1)).val() == 0
									|| $("#materialEditar" + (index + 1)).val() == ""){
								valorZeroOuVazio = true;
								$("#btnEditarPedido").prop("disabled", true);
							}else{
								if($("#valorEditar").val() != "" && $("#valorEditar").val() != 0){
									console.log("VLR. TOTAL ANTES  (valorTotalEditar): " + valorTotalEditar);
									var valorConvertido = formataNumeroParaEnvio($("#valorItemEditar" + (index + 1)).val());
									valorTotalEditar = parseFloat(valorTotalEditar) + valorConvertido;
									$("#valorEditar").val(formataNumeroParaExibicao(valorTotalEditar, 2, ",", "."));
									console.log("VLR. TOTAL DEPOIS  (valorTotalEditar): " + valorTotalEditar);
								}
							}
						});
						if(valorZeroOuVazio == false){
							$("#btnEditarPedido").prop("disabled", false);
						}
					}
				});
				guardaMaterial="";
			}			
		});
		
		$("#pesoEditar" + j).mask("000000,000", {reverse: true});
		
		$("#pesoEditar" + j).focus(function(){
			valorZeroOuVazio = false;
			if($(this).val() != "" && $(this).val() != 0){
				$.each($("#itensEditar").find(".form-inline"), function(index, value){						
					if($("#valorItemEditar" + (index + 1)).val() == "" || $("#valorItemEditar" + (index + 1)).val() == 0 
							|| $("#pesoEditar" + (index + 1)).val() == "" || $("#pesoEditar" + (index + 1)).val() == 0
							|| $("#materialEditar" + (index + 1)).val() == ""){							
						valorZeroOuVazio = true;
						$("#btnEditarPedido").prop("disabled", true);
						console.log("PESO FOCADO - DESATIVADO - MATERIAL, PESO ou VALOR = VAZIO");
					}
				});
				if(valorZeroOuVazio == false){
					console.log("PESO FOCADO - ATIVADO - PESOS e VALORES OK");
					$("#btnEditarPedido").prop("disabled", false);
				}
			}else{
				console.log("PESO FOCADO - DESATIVADO - PESO = VAZIO");
				$("#btnEditarPedido").prop("disabled", true);
			}
		});		
		
		$("#pesoEditar" + j).blur(function(){
			valorZeroOuVazio = false;
			var id = $(this).closest(".form-inline").attr("id");
			
			var vlrPeso = $(this).val().replace('.','.').replace(',','.');
			var vlrValor = "";			
			if(vlrPeso != "" && vlrPeso != 0){
				console.log("PESO blur");
				$.each(resultadoCodProdutos, function(index, value){
					if(value == $("#codProdutoEditar" + id).val()){
						vlrValor = resultadoPrecoVendaProdutos[index];					
					}
				});			
				var vlrTotal = parseFloat(vlrPeso) * parseFloat(vlrValor);				
				$("#valorItemEditar" + id).val(formataNumeroParaExibicao(vlrTotal, 2, ',', '.'));
				$(this).val(vlrPeso.replace('.',','));
				
				var valorTotalEditar = parseFloat(0);
				$.each($("#itensEditar").find(".form-inline"), function(index, value){						
					if($("#valorItemEditar" + (index + 1)).val() == "" || $("#valorItemEditar" + (index + 1)).val() == 0 
							|| $("#pesoEditar" + (index + 1)).val() == "" || $("#pesoEditar" + (index + 1)).val() == 0
							|| $("#materialEditar" + (index + 1)).val() == ""){
						valorZeroOuVazio = true;
						$("#btnEditarPedido").prop("disabled", true);
						console.log("PESO DESFOCADO - DESATIVADO - MATERIAL, PESO ou VALOR = VAZIO");
					}else{
						if($("#valorItemEditar" + (index + 1)).val() != ""){
							if($("#valorEditar").val() != "" && $("#valorEditar").val() != 0){
								console.log("VLR. TOTAL ANTES  (valorTotalEditar): " + valorTotalEditar);
								var valorConvertido = formataNumeroParaEnvio($("#valorItemEditar" + (index + 1)).val());
								valorTotalEditar = parseFloat(valorTotalEditar) + valorConvertido;
								$("#valorEditar").val(formataNumeroParaExibicao(valorTotalEditar, 2, ",", "."));
								console.log("VLR. TOTAL DEPOIS  (valorTotalEditar): " + valorTotalEditar);
							}
						}								
					}
				});
				if(valorZeroOuVazio == false){
					console.log("PESO DESFOCADO - ATIVADO - PESOS e VALORES OK");
					$("#btnEditarPedido").prop("disabled", false);
				}
			}else{
				console.log("PESO DESFOCADO - DESATIVADO - PESO = VAZIO");
				$("#valorItemEditar" + id).val("");
				$("#btnEditarPedido").prop("disabled", true);
			}
		});
		
		$("#valorItemEditar" + j).mask("00000000,00", {reverse: true});
		
		$("#valorItemEditar" + j).focus(function(){
			valorZeroOuVazio = false;
			if($(this).val() != "" && $(this).val() != 0){
				$.each($("#itensEditar").find(".form-inline"), function(index, value){						
					if($("#valorItemEditar" + (index + 1)).val() == "" || $("#valorItemEditar" + (index + 1)).val() == 0
							|| $("#pesoEditar" + (index + 1)).val() == "" || $("#pesoEditar" + (index + 1)).val() == 0
							|| $("#materialEditar" + (index + 1)).val() == ""){
						valorZeroOuVazio = true;
						$("#btnEditarPedido").prop("disabled", true);
					}
				});
				if(valorZeroOuVazio == false){
					$("#btnEditarPedido").prop("disabled", false);
				}
			}else{
				$("#btnEditarPedido").prop("disabled", true);
			}
		});		
		
		$("#valorItemEditar" + j).blur(function(){
			console.log("VLR. ITEM blur");
			valorZeroOuVazio = false;
			if($(this).val() != "" && $(this).val() != 0){
								
				var valorTotalEditar = parseFloat(0);
					console.log("VLR. ITEM blur");
					$.each($("#itensEditar").find(".form-inline"), function(index, value){						
						if($("#valorItemEditar" + (index + 1)).val() == "" || $("#valorItemEditar" + (index + 1)).val() == 0
								|| $("#pesoEditar" + (index + 1)).val() == "" || $("#pesoEditar" + (index + 1)).val() == 0
								|| $("#materialEditar" + (index + 1)).val() == ""){
							valorZeroOuVazio = true;
							$("#btnEditarPedido").prop("disabled", true);
						}else{
							if($("#valorItemEditar" + (index + 1)).val() != ""){
								if($("#valorEditar").val() != "" && $("#valorEditar").val() != 0){
									console.log("VLR. TOTAL ANTES  (valorTotalEditar): " + valorTotalEditar);
									var valorConvertido = formataNumeroParaEnvio($("#valorItemEditar" + (index + 1)).val());
									valorTotalEditar = parseFloat(valorTotalEditar) + valorConvertido;
									$("#valorEditar").val(formataNumeroParaExibicao(valorTotalEditar, 2, ",", "."));
									console.log("VLR. TOTAL DEPOIS (valorTotalEditar): " + valorTotalEditar);
								}
							}
						}
					});					
					if(valorZeroOuVazio == false){
						$("#btnEditarPedido").prop("disabled", false);
					}
			}else{
				$("#btnEditarPedido").prop("disabled", true);
			}
		});		
	}
	
	// GUARDA O CÓDIGO DO CLIENTE APÓS SELECIONADO
	var codClienteEditar = "";	
	var encontrouClienteEditar = false;
	var idsEditar = [];
	
	// MODAL ATUALIZAR - CLIQUE DO BOTÃO EDITAR DA LINHA DE UM REGISTRO TABELA - EXIBE O MODAL
	$("a[role=editar]").click(function(event){
		event.preventDefault();		
		
		j = 0;
		
		noPedidoEditar_tmp = "";
		dataPedidoAberturaEditar_tmp = "";
		dataPedidoPagtoEditar_tmp = "";
		clienteEditar_tmp = "";
		codClienteEditar_tmp = "";
		valorPedidoEditar_tmp = "";
		statusEditar_tmp = "";
		itensMaterialPV_tmp = [];
		itensPesoPV_tmp = [];
		itensValorItemPV_tmp = [];
		
		// Pega a TR
		var tr = $(this).closest("tr");
		
		// Pega as TDs da TR
		var tdNoPedidoEditar = tr.find("td:first");
		var tdDataAbertura = tdNoPedidoEditar.next("td");				
		var tdDataPagto = tdDataAbertura.next("td");
		var tdCliente = tdDataPagto.next("td");
		var tdValor = tdCliente.next("td");
		var tdStatus = tdValor.next("td");
		
		// Pega os Dados das TDs
		var noPedidoEditar = tdNoPedidoEditar.text();
		var dataPedidoAberturaEditar = tdDataAbertura.text();
		var dataPedidoPagtoEditar = tdDataPagto.text();
		var clienteEditar = tdCliente.text();
		var valorEditar = tdValor.text();
		var statusEditar = tdStatus.text();
		
		// GUARDA OS DADOS DO PEDIDO - BACKUP
		noPedidoEditar_tmp = noPedidoEditar;
		dataPedidoAberturaEditar_tmp = dataPedidoAberturaEditar;
		dataPedidoPagtoEditar_tmp = dataPedidoPagtoEditar;
		clienteEditar_tmp = clienteEditar;
		valorPedidoEditar_tmp = valorEditar;
		statusEditar_tmp = statusEditar;
		
		// GUARDA O CÓDIGO DO CLIENTE - BACKUP
		$.each(resultadoCodClientes, function(index, value){
			if(resultadoClientes[index] == clienteEditar_tmp){
				codClienteEditar_tmp = value;
				codClienteEditar = value;
			}
		});
				
		// Pega o Modal
		var modal = $("#modal-form-pedido[rel=modaleditar]");
		
		// COLOCA OS DADOS NO MODAL CAMPOS SOMENTE LEITURA 
		modal.find("#noPedidoEditar").prop("readonly", true).val(noPedidoEditar);
		modal.find("#dataAberturaEditar").prop("readonly", true).val(dataPedidoAberturaEditar);	
		modal.find("#clienteEditar").prop("readonly", true).val(clienteEditar);
		modal.find("#valorEditar").prop("readonly", true).val(valorEditar);
		
		// COLOCA O STATUS NO MODAL
		var optionsEditar = modal.find("#statusEditar").find("option");		
		$.each(optionsEditar, function(index, value){
			if(statusEditar == $(this).val()){
				$(this).prop("selected", true);
			}
		});
		
		// COLOCA A DATA DO PAGAMENTO, CASO STATUS SEJA PAGO
		if(dataPedidoPagtoEditar != ""){			
			modal.find("#dataPagamentoEditar").closest(".form-group");
			modal.find("#dataPagamentoEditar").datepicker("setDate", dataPedidoPagtoEditar);
		}else{
			modal.find("#dataPagamentoEditar").closest(".form-group").addClass("hidden");
		}
		
		var tdItensPV = tdStatus.next("td");
		var divItensPV = tdItensPV.find(".grupoDeItens");	// TD DE ITENS DA TABELA
		var divUmMaterial = "";
		var divDoisPeso = "";
		var divTresValor = "";
		
		// CARREGA OS ITENS NO MODAL ATUALIZAR
		$.each(divItensPV, function(index, value){
			j++;			
			
			divUmMaterial = $(this).find("div:first");
			divDoisPeso = divUmMaterial.next("div");
			divTresValor = divDoisPeso.next("div");
			
			itensMaterialPV_tmp.push(divUmMaterial.text());
			itensPesoPV_tmp.push(divDoisPeso.text());
			itensValorItemPV_tmp.push(divTresValor.text());			
						
			// ADICIONA UMA NOVA LINHA
			insereLinhaEditar(j);
			
			// RECUPERANDO O CÓDIGO DO PRODUTO E COLOCANDO NO CAMPO OCULTO DA LINHA
			$.each(resultadoCodProdutos, function(index, value){
				if(resultadoProdutos[index] == divUmMaterial.text()){
					modal.find("#codProdutoEditar" + j).val(value);
					ajaxCodProdutoEditar.push(value);
					idsEditar.push(j);
				}
			});
			
			// FORMATA A EXIBIÇÃO DO PESO
			var numeroDecimal = divDoisPeso.text().split(".");
			var casasDecimais = 0;
			if(numeroDecimal.length > 1){				
				if(numeroDecimal[1] > 0){		
					casasDecimais = 3;				
				}
			}
			
			// CARREGA OS ITENS NAS LINHAS
			modal.find("#materialEditar" + j).val(divUmMaterial.text());
			modal.find("#pesoEditar" + j).val(formataNumeroParaExibicao(divDoisPeso.text(), casasDecimais, ",", "."));
			modal.find("#valorItemEditar" + j).val(formataNumeroParaExibicao(divTresValor.text(), 2, ",", "."));			
		});
		
		// ATIVA O BOTÃO ATUALIZAR
		modal.find("#btnEditarPedido").prop("disabled", false);
		
		// EXIBE O MODAL ATUALIZAR
		modal.modal();		
	});	
	
	// CLIQUE PARA ADICIONAR UM ITEM DE PRODUTO NO MODAL ATUALIZAR PEDIDO
	j = 0;
	$("a[role=additemEditar").click(function(e){
		e.preventDefault();
		
		if(codClienteEditar != "" && j == idsEditar.length){
			$("#btnEditarPedido").prop("disabled", false);
		}else{			
			$("#btnEditarPedido").prop("disabled", true);
		}		
		
		// MONTA UM LINHA DE ITEM
		insereLinhaEditar();
		
	});
	
	// ENVIA OS DADOS PARA O CADASTRAMENTO DO PEDIDO
	var ajaxClienteEditar = "";
	var ajaxCodProdutoEditar = [];
	var ajaxPesosEditar = [];
	var ajaxPrecosEditar = [];	
	
	// ATUALIZA O PEDIDO
	$("#btnEditarPedido").click(function(){
				
		function formataNumeroParaEnvio(number) {
			var numberFormatado = number.replace(".", "");
			return parseFloat(numberFormatado.replace(",", "."));			
		}
		
		var lista = $("#itensEditar").find(".form-inline");
		
		// PEGA OS DADOS DOS ITENS - PELA VERICACAO DO CÓDIGO DO PRODUTO
		if((ajaxCodProdutoEditar == null || ajaxCodProdutoEditar == "") && (codClienteEditar == null || codClienteEditar == "")){
			console.log("nulo");
		}else{
			
			var contador = 0;
			
			// PEGA OS PESOS E PREÇOS DOS INPUTS
			$.each(lista, function(index, value){
				if (typeof value == 'undefined') {
					console.log("INDEFINIDO: index " + index);
				}else{
					var pesoFormatado = formataNumeroParaEnvio($("#pesoEditar" + (index + 1)).val());
					var valorFormatado = formataNumeroParaEnvio($("#valorItemEditar" + (index + 1)).val());
					console.log("ANTES.: " + $("#valorItemEditar" + (index + 1)).val());
					console.log("DEPOIS: " + valorFormatado);
					if($.isNumeric(pesoFormatado) && $.isNumeric(valorFormatado)){
						console.log("São números, PESO: " + pesoFormatado + " VALOR: " + valorFormatado);						
						ajaxPesosEditar.push(pesoFormatado);
						ajaxPrecosEditar.push(valorFormatado);
						contador++;
					}else{
						if(!$.isNumeric($("#pesoEditar" + (index + 1)).val()) && !$.isNumeric($("#valorItemEditar" + (index + 1)).val())){
							$("#valorItemEditar" + (index + 1)).closest(".form-group").addClass("has-error");
							$("#pesoEditar" + (index + 1)).closest(".form-group").addClass("has-error");
							console.log("Não são números VALOR: " + $("#valorItemEditar" + (index + 1)).val() + " PESO: " + $("#pesoEditar" + (index + 1)).val());
						}else{
							if($.isNumeric($("#pesoEditar" + (index + 1)).val())){
								$("#valorItemEditar" + (index + 1)).closest(".form-group").addClass("has-error");
								console.log("Não é número VALOR: " + $("#valorItemEditar" + (index + 1)).val());
							}else{
								$("#pesoEditar" + (index + 1)).closest(".form-group").addClass("has-error");
								console.log("Não é número PESO: " + $("#pesoEditar" + (index + 1)).val());
							}
						}
					}		
				}				
			});
							
			// VERIFICA SE TODOS OS CAMPOS FORAM PREENCHIDOS COM O CONTADOR DE LINHAS
			if(contador == lista.length){				
				
				var jsonItens = {};
				
				// CONVERTENDO OS ARRAYS EM JSON PARA ENVIO POR AJAX 
				$.each(ajaxCodProdutoEditar, function(index, value){
					if(index == 0){						
						jsonItens = "{\"peso\":\"" + ajaxPesosEditar[index] + "\"," +
									"\"valorItem\":\"" + ajaxPrecosEditar[index] + "\"," +
									"\"produto\":" +
										"{\"codigo\":\"" + value + "\"}}";											
					}else{						
						jsonItens += "," + "{\"peso\":\"" + ajaxPesosEditar[index] + "\"," +
											"\"valorItem\":\"" + ajaxPrecosEditar[index] + "\"," +
											"\"produto\":" +
												"{\"codigo\":\"" + value + "\"}}";;
					}					
				});
				
				console.log("N. PEDIDO: " + $("#modal-form-pedido[rel=modaleditar]").find("#noPedidoEditar").val());
				console.log("DATA ABERTURA: " + $("#modal-form-pedido[rel=modaleditar]").find("#dataAberturaEditar").val());
				console.log("DATA PAGAMENTO: " + $("#modal-form-pedido[rel=modaleditar]").find("#dataPagamentoEditar").val());
				console.log("COD CLIENTE: " + codClienteEditar);
				console.log("CLIENTE: " + $("#modal-form-pedido[rel=modaleditar]").find("#clienteEditar").val());
				
				var nPed = $("#modal-form-pedido[rel=modaleditar]").find("#noPedidoEditar").val();
				var dtAbertura = $("#modal-form-pedido[rel=modaleditar]").find("#dataAberturaEditar").val();
				var dtPagto = $("#modal-form-pedido[rel=modaleditar]").find("#dataPagamentoEditar").val();				
				
				console.log("ITENS: ");
				$.each(ajaxCodProdutoEditar, function(index, value){
					console.log((index + 1) + ". " + value + " peso: " + ajaxPesosEditar[index] + " R$ " + ajaxPrecosEditar[index]);
				});
				
				console.log("VALOR DA VENDA: " + $("#modal-form-pedido[rel=modaleditar]").find("#valorEditar").val());				
				
				var status = null;
				$.each($("#statusEditar").find("option"), function(index, value){
					console.log("option = " + $(this).val());
					if($(this).prop("selected") == true){
						status = $(this).val();
					}
				});
				
				console.log("status: " + status);
				console.log("dtPagto: " + dtPagto);
				
				$.ajax({
					url:"controller",
					type:"post",
					data:{
						nPed:nPed,
						dtAbertura:dtAbertura,
						dtPagto:dtPagto,
						codCliente:codClienteEditar,
						itens:jsonItens,
						status:status,
						action:"atualizarPedidoVenda"
					},
					//antes de enviar ele alerta para esperar
			        beforeSend : function(){
			            $('#carregando').show('100');
			        },
					success:function(resultado){
						$('#carregando').hide('100');
						console.log("Resultado recebido: " + resultado);
						$("#modal-form-pedido[rel=modaleditar]").find(".modal-header").html("<h4 class='blue bigger'>Resultado...</h4>").prepend("<button type='button' class='close' data-dismiss='modal'>&times;</button>");
						$("#modal-form-pedido[rel=modaleditar]").find(".modal-body").html("<h3>" + resultado + "</h3>");
						var botao = $("#modal-form-pedido[rel=modaleditar]").find("#btnCancelar").html("<i class='ace-icon fa fa-times'></i> Fechar");				
						botao.text("Fechar");
						$("#modal-form-pedido[rel=modaleditar]").find("#btnEditarPedido").addClass("hidden");
					},
					error:function(resultado){
						$('#carregando').hide('100');
						console.log("Resultado recebido: " + resultado);
						$("#modal-form-pedido[rel=modaleditar]").find(".modal-header").html("<h4 class='blue bigger'>Resultado...</h4>").prepend("<button type='button' class='close' data-dismiss='modal'>&times;</button>");
						$("#modal-form-pedido[rel=modaleditar]").find(".modal-body").html("<h3>Desculpe, houve uma falha!</h3>");
						var botao = $("#modal-form-pedido[rel=modaleditar]").find("#btnCancelar").html("<i class='ace-icon fa fa-times'></i> Fechar");				
						botao.text("Fechar");
						$("#modal-form-pedido[rel=modaleditar]").find("#btnEditarPedido").addClass("hidden");
					}
				}); 
			}
		}
	});
	
	$("#dataPagamentoEditar").datepicker({
		dateFormat: 'dd/mm/yy',
	    dayNames: ['Domingo','Segunda','Terça','Quarta','Quinta','Sexta','Sábado'],
	    dayNamesMin: ['D','S','T','Q','Q','S','S','D'],
	    dayNamesShort: ['Dom','Seg','Ter','Qua','Qui','Sex','Sáb','Dom'],
	    monthNames: ['Janeiro','Fevereiro','Março','Abril','Maio','Junho','Julho','Agosto','Setembro','Outubro','Novembro','Dezembro'],
	    monthNamesShort: ['Jan','Fev','Mar','Abr','Mai','Jun','Jul','Ago','Set','Out','Nov','Dez'],
	    nextText: 'Próximo',
	    prevText: 'Anterior',
		inline: true,
		autoclose: true,
		todayHighlight: true
	}).next().on(ace.click_event, function(){
		$(this).prev().focus();
	});
	
	// RECARREGA A PÁGINA AO FECHAR O MODAL CADASTRAR
	$("#modal-form-pedido[rel=modalcadastrar]").on("hidden.bs.modal", function (){
		console.log("Modal Cadastrar Encerrado");
		location.reload();
	});
	
	// RECARREGA A PÁGINA AO FECHAR O MODAL EXCLUIR
	$("#modal-form-pedido[rel=modalexcluir]").on("hidden.bs.modal", function (){
		console.log("Modal Excluir Encerrado");
		location.reload();
	});
	
	// RECARREGA A PÁGINA AO FECHAR O MODAL ATUALIZAR
	$("#modal-form-pedido[rel=modaleditar]").on("hidden.bs.modal", function (){
		console.log("Modal Atualizar Encerrado");
		location.reload();
	});
});