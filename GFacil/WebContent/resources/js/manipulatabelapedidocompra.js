$(document).ready(function(){
	
	// VARIÁVEIS GLOBAIS - necessárias para o armazenamento temporário dos dados originais da edição de registros da tabela de usuários
	var nome_usuario_tmp;
	var nome_tmp;
	var email_tmp;
	var perfil_tmp;
	
	// PARA CARREGAMENTO DA LISTA DE FORNECEDORES
	var divInputFornecedor = "";
	//var listaFornecedores = "";
	
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
		var tdFornecedor = tdData.next("td");
		var tdValor = tdFornecedor.next("td");
		var tdStatus = tdValor.next("td");
		
		// Pega os Dados das TDs
		var noPedidoExcluir = tdNoPedido.text();
		var dataPedidoExcluir = tdData.text();
		var fornecedorExcluir = tdFornecedor.text();
		var valorExcluir = tdValor.text();
		var statusExcluir = tdStatus.text();
		
		// Pega o Modal
		var modal = $("#modal-form-pedido[rel=modalexcluir]");
		
		// Coloca os Dados no Modal e de Forma Somente Leitura
		modal.find("#noPedidoExcluir").prop("readonly", true).val(noPedidoExcluir);
		modal.find("#dataExcluir").prop("readonly", true).val(dataPedidoExcluir);
		modal.find("#fornecedorExcluir").prop("readonly", true).val(fornecedorExcluir);
		modal.find("#valorExcluir").prop("readonly", true).val(valorExcluir);
		modal.find("#statusExcluir").prop("readonly", true).val(statusExcluir);
		
		// Exibe o Modal Form Excluir
		modal.modal();		
	});	
	
	/* CLIQUE DO BOTÃO EDITAR DA LINHA DE UM REGISTRO DA TABELA
	$("a[role=editar]").click(function(event){
		event.preventDefault();		
		
		nome_usuario_tmp = "";
		nome_tmp = "";
		email_tmp = "";
		perfil_tmp = "";
		
		// Pega a TR
		var tr = $(this).closest("tr");
		
		// Pega as TDs da TR
		var tdNomeUsuario = tr.find("td:first").prop("class", "hidden");
		var tdNome = tdNomeUsuario.next("td");
		var tdEmail = tdNome.next("td");
		var tdPerfil = tdEmail.next("td");
		
		// Pega os Dados das TDs
		var nomeusuario = tdNomeUsuario.text();
		nome_usuario_tmp = tdNomeUsuario.text();
		var nome = tdNome.text();
		nome_tmp = tdNome.text();
		var email = tdEmail.text();
		email_tmp = tdEmail.text();
		var perfil = tdPerfil.text();
		perfil_tmp = tdPerfil.text();
		
		// Troca a Exibição dos Botões de Ações
		var btnEditarEditar = $(this).addClass("hidden");
		var btnEditarExcluir = btnEditarEditar.next("a[role=excluir]").addClass("hidden");
		var btnEditarSalvar = btnEditarExcluir.next("a[role=salvar]").removeClass("hidden");
		var btnEditarCancelar = btnEditarSalvar.next("a[role=cancelar]").removeClass("hidden");			
		
		// Coloca os Inputs nas TDs
		tdNomeUsuario.html("<input type='text' name='nomeusuario' class='hidden'></input>").val(nomeusuario);
		tdNome.html("<input type='text' name='nome'></input>").val(nome);
		tdEmail.html("<input type='text' name='email'></input>").val(email);
		tdPerfil.html("<select name='perfil'></select>"); // Os options são inseridos logo abaixo			
	
		// Coloca os Dados nos Inputs
		tdNomeUsuario.find("input").val(nomeusuario);
		tdNome.find("input").val(nome);
		tdEmail.find("input").val(email);		
		
		// Coloca os Options com os Dados no Select (Campo Perfil)
		var perfis;
		$.ajax({			
			url:"controller",
			type:"post",
			data:{				
				action:"pegaPerfil"
			},
			success:function(resultado){
				// Montando um array de string com o resultado
				perfis = resultado.split(",");								
						
				// Montando o campo Perfil com o array
				var campoPerfil = "";				
				$.each(perfis, function(index, value){
					if(perfil === value) campoPerfil += "<option value='" + value + "' selected>" + value + "</option>";
					else campoPerfil += "<option value='" + value + "'>" + value + "</option>";					
				});
				
				// Adicionando os options no select
				tdPerfil.html("<select name='perfil'>" + campoPerfil + "</select>");				
			}
		});
	});
	
	/* CLIQUE DO BOTÃO SALVAR - BOTÃO VISÍVEL NO MODO DE EDIÇÃO DE UM REGISTRO DA TABELA 
	$("a[role=salvar]").click(function(event){
		event.preventDefault();		
		
		// Pega a TR
		var tr = $(this).closest("tr");
		
		// Pega as TDs da TR
		var tdNomeUsuario = tr.find("td:first").prop("class", "hidden");
		var tdNome = tdNomeUsuario.next("td");
		var tdEmail = tdNome.next("td");
		var tdPerfil = tdEmail.next("td");
		//var tdAcoes = tdPerfil.next("td");
		
		// Pega os Dados dos Inputs das TDs
		var nomeusuario = tdNomeUsuario.find("input").val();
		var nome = tdNome.find("input").val();
		var email = tdEmail.find("input").val();
		var perfil = tdPerfil.find("option:selected").val();
		
		// AJAX
		$.ajax({
			url:"controller",
			type:"post",
			data:{
				nome:nome,
				email:email,
				emailParaBusca:email_tmp,
				nomeusuario:nomeusuario,				
				perfil:perfil,
				action:"atualizarUsuario",
				tarefa:"atualizar"
			},
			success:function(resultado){
				
				// Verificar se a resposta
				if(resultado === "Atualizado"){
					// Troca a Exibição dos Botões de Ações
					var btnEditarCancelar = $("a[role=cancelar]").addClass("hidden");
					var btnEditarSalvar = btnEditarCancelar.prev("a[role=salvar]").addClass("hidden");
					var btnEditarExcluir = btnEditarSalvar.prev("a[role=excluir]").removeClass("hidden");
					var btnEditarEditar = btnEditarExcluir.prev("a[role=editar]").removeClass("hidden");
							
					// Remove os Inputs das TDs e Coloca os Dados
					tdNomeUsuario.html("").text(nomeusuario);
					tdNome.html("").text(nome);
					tdEmail.html("").text(email);
					tdPerfil.html("").text(perfil);
				}else{
					alert("NÃO ATUALIZOU");										
				}	
			}
		});		
	});
	
	// CLIQUE DO BOTÃO CANCELAR - BOTÃO VISÍVEL NO MODO DE EDIÇÃO DE UM REGISTRO DA TABELA
	$("a[role=cancelar]").click(function(event){
		event.preventDefault();		
	
		// Pega a TR
		var tr = $(this).closest("tr");
		
		// Pega as TDs da TR
		var tdNomeUsuario = tr.find("td:first").prop("class", "hidden");
		var tdNome = tdNomeUsuario.next("td");
		var tdEmail = tdNome.next("td");
		var tdPerfil = tdEmail.next("td");
		var tdAcoes = tdPerfil.next("td");
		
		// Pega os Dados das Variáveis Globais
		var nomeusuario = nome_usuario_tmp;
		var nome = nome_tmp;
		var email = email_tmp;
		var perfil = perfil_tmp;
		
		// Troca a Exibição dos Botões de Ações
		var btnEditarCancelar = $(this).addClass("hidden");
		var btnEditarSalvar = btnEditarCancelar.prev("a[role=salvar]").addClass("hidden");
		var btnEditarExcluir = btnEditarSalvar.prev("a[role=excluir]").removeClass("hidden");
		var btnEditarEditar = btnEditarExcluir.prev("a[role=editar]").removeClass("hidden");
				
		// Remove os Inputs das TDs e Coloca os Dados
		tdNomeUsuario.html("").text(nomeusuario);
		tdNome.html("").text(nome);
		tdEmail.html("").text(email);
		tdPerfil.html("").text(perfil);		
	});	
	*/	
	
	// BUSCA O NÚMERO DO PRÓXIMO PEDIDO PARA O MODAL CADASTRAR PEDIDO DE COMPRA
	$.ajax({
		url:"controller",
		type:"post",
		data:{			
			action:"pesquisarPedidoCompra",
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
	
	// CARREGA A LISTA DE FORNECEDORES NO MODAL CADASTRAR PEDIDO DE COMPRA
	$("#btnGerarPedido").prop("disabled", true);
	
	divInputFornecedor = $("#fornecedor").closest("div");
	var resultadoFornecedores = [];
	var resultadoCodFornecedores = [];
	$.ajax({
		url:"controller",
		type:"post",
		dataType:"json",
		data:{
			action:"listarFornecedores",
			tarefa:"pedidoCompra"
		},
		success:function(resultado){
			if(resultado.dataListaFornecedores == "null"){
				divInputFornecedor.html("<h3><b class='blue'></b></h3>");
				divInputFornecedor.addClass("center");
				divInputFornecedor.find("b").text("Não há fornecedores cadastrados.");
			}else{
				$.each(resultado.dataListaFornecedores, function(index, value){	
					
					// GUARDA OS CÓDIGOS DOS FORNECEDORES PARA UTILIZAÇÃO POSTERIOR
					resultadoCodFornecedores[index] = resultado.dataListaFornecedores[index].codigo;	
					
					// GUARDA A FORMA DE EXIBIÇÃO DOS NOMES DOS FORNECEDORES(CAMPO EMPRESA) PARA EXIBIÇÃO POSTERIOR
					if(resultado.dataListaFornecedores[index].tipo == "CATADOR")						
						resultadoFornecedores[index] = "Catador: " + resultado.dataListaFornecedores[index].contato;
					else 
						resultadoFornecedores[index] = resultado.dataListaFornecedores[index].empresa;
					
				});
			}
		}
	});
	
	// COMPLETA O CAMPO FORNECEDOR DO MODAL CADASTRAR PEDIDO
	$("#fornecedor").autocomplete({
		source: resultadoFornecedores
	});	
	
	// GUARDA O CÓDIGO DO FORNECEDOR APÓS SELECIONADO
	var codFornecedor = "";
	$("#fornecedor").blur(function(){
		var forn = $(this);
		codFornecedor = "";
		
		if(forn.val() != null && forn.val() != ""){
			$.each(resultadoFornecedores, function(index, value){					
				if(forn.val() == value){					
					codFornecedor = resultadoCodFornecedores[index];
					if(i != 0)
						$("#btnGerarPedido").prop("disabled", false);
				}
			});
		}else{
			$("#btnGerarPedido").prop("disabled", true);
		}
		
		// DESABILITA O BOTÃO GERAR PEDIDO QUANDO O CAMPO FORNECEDOR PERDE O FOCO E NO CASO DO FORNECEDOR NÃO TER SIDO INFORMADO
		//if(codFornecedor == "") $("#btnGerarPedido").prop("disabled", true);
	});
	
	// INICIA O FORMULÁRIO DO PEDIDO DE COMPRA COM O BOTÃO GERAR PEDIDO DESABILITADO
	//if(codFornecedor == "") $("#btnGerarPedido").prop("disabled", true);	
	
	// CARREGA A LISTA DE PRODUTOS NO MODAL CADASTRAR PEDIDO DE COMPRA	
	var resultadoCodProdutos = []; 	
	var resultadoProdutos = [];
	var resultadoPrecoCompraProdutos = [];
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
					resultadoPrecoCompraProdutos[index] = resultado.dataListaProdutos[index].precoCompra;
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
		console.log("i = " + i + " - ADD ITEM INICIO");
		
		// MONTA UM LINHA DE ITEM
		insereLinha(i);		
		
		// ADICIONA UM EVENTO DE REMOÇÃO DE ITEM
		$("a[id='removerItem" + i + "']").click(function(e){
			e.preventDefault();
			console.log("i = " + i + " - REMOVE ITEM INICIO");
			
			var linhaRemovida = $(this).closest(".form-inline");
			console.log("ID DA LINHA QUE FOI REMOVIDA: " + linhaRemovida.attr("id") + " - i = " + i);
			var id = linhaRemovida.attr("id");
			
			// VERIFICANDO A REMOÇÃO DO COD. PRODUTO
			console.log("INICIO - PERCORRE OS COD. PROD:");
			$.each(ajaxCodProduto, function(index, value){
				console.log("\tINDEX: " + index + " COD. PRODUTO: " + value);				
			});
									
			console.log("MEIO - PERCORRE OS COD. PROD:");			
			$.each(ajaxCodProduto, function(index, value){				
				console.log("\tINDEX: " + index + " COD. PRODUTO: " + value + " ID: " + id);
				if((id - 1) == index){
					console.log("\t\tINDEX: " + index + " COD. PRODUTO: " + value + " - REMOVIDO");
					ajaxCodProduto.splice(index, 1);
					ids.splice(index, 1);
				}
			});
			
			console.log("FIM - PERCORRE OS COD. PROD:");
			$.each(ajaxCodProduto, function(index, value){				
				console.log("\tINDEX: " + index + " COD. PRODUTO: " + value);				
			});
			// VERIFIÇÃO CONCLUÍDA - FUNCIONANDO PERFEITAMENTE
			
			console.log("THE END\n");
			
			// REMOVE A DIV DE ESPAÇAMENTO ENTRE LINHAS
			linhaRemovida.next(".space-4").remove();
			
			// REMOVE A DIV LINHA DE FATO
			linhaRemovida.remove();	
			
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
			
			i--;
			if(i == 0)
				$("#btnGerarPedido").prop("disabled", true);
			else
				if(codFornecedor != "" && i == ids.length){
					console.log("FORNECEDOR PREENCHIDO E i: " + i + " = qtd. ids: " + ids.length);
					$("#btnGerarPedido").prop("disabled", false);
				}else{
					console.log("FORNECEDOR NAO PREENCHIDO OU i: " + i + " != qtd. ids: " + ids.length);
					$("#btnGerarPedido").prop("disabled", true);
				}
			console.log("i = " + i + " - REMOVE ITEM FIM");
		});
		
		console.log("i = " + i + " - ADD ITEM FIM");
		// COMPLETA O CAMPO DO PRODUTO NO MODAL CADASTRAR PEDIDO	
		$("#material" + i).autocomplete({
			source: resultadoProdutos
		});
		
		//$("#btnGerarPedido").prop("disabled", true);
		
		// QUANDO O CAMPO PRODUTO GANHA O FOCO, GARANTE DE REMOVER O COD. CASO PREENCHIDO ANTERIORMENTE E A DESABILITAÇÃO DO BOTÃO
		$("#material" + i).focus(function(){			
			ajaxCodProduto.splice((i - 1), 1);
			ids.splice((i - 1), 1);
			console.log("MATERIAL GANHOU FOCO, i: " + i + " qtd. ids: " + ids.length);
		});
		
		// QUANDO O CAMPO PRODUTO PERDE O FOCO, COMPLETA OS CAMPOS PESO E VALOR DO PRODUTO NO MODAL CADASTRAR PEDIDO
		$("#material" + i).blur(function(){
			
			var prod = $(this);
			var id = prod.closest(".form-inline").attr("id");			
			if(prod.val() != null && prod.val() != ""){
				$.each(resultadoProdutos, function(index, value){
					
					// ADICIONA OS DADOS NOS INPUTs - CÓD. PRODUTO, PESO, VALOR
					if(prod.val() == value){						
						$("#codProduto" + id).val(resultadoCodProdutos[index]);
						$("#peso" + id).val(1);
						$("#valor" + id).val(resultadoPrecoCompraProdutos[index]);
						
						// MONTANDO O ARRAY DE DADOS PARA ENVIO DO AJAX - CÓD. PRODUTO						
						ajaxCodProduto.push(resultadoCodProdutos[index]);
						ids.push(id);
						console.log("ID é o mesmo que i? ID: " + id + " i: " + i + " qtd. ids: " + ids.length);
						if(codFornecedor != "" && i == ids.length){
							console.log("MATERIAL PERDEU FOCO - BOTAO HABILITADO, i: " + i + " = qtd. ids: " + ids.length + " - FORNECEDOR PREENCHIDO");
							$("#btnGerarPedido").prop("disabled", false);
						}else{
							console.log("MATERIAL PERDEU FOCO - BOTAO DESABILITADO, i: " + i + " = qtd. ids: " + ids.length + " - FORNECEDOR TALVEZ NAO PREENCHIDO")
							$("#btnGerarPedido").prop("disabled", true);
						}
					}
				});
			}else{
				console.log("MATERIAL PERDEU FOCO 'VAZIO' - BOTAO DESABILITADO, i: " + i + " = qtd. ids: " + ids.length)
				$("#btnGerarPedido").prop("disabled", true);
			}
		});
		
		/*
		$("#peso" + 1).keyup(function(){
			var $this = $(this); //armazeno o ponteiro em uma variavel
			var valor = $this.val().replace(/[^0-9]+/gi,'');[0-9]{2}[\.]?[0-9]{3}[\.]?[0-9]{3}[\/]?[0-9]{4}[-]?[0-9]{2})|([0-9]{3}[\.]?[0-9]{3}[\.]?[0-9]{3}[-]?[0-9]{2}
			$this.val(valor);
		});
		
		$("#valor" + 1).keyup(function(){
			var $this = $(this); //armazeno o ponteiro em uma variavel
			var valor = $this.val().replace(/[^0-9]+/gi,'');
			$this.val(valor);
		});*/
	});
	
	// ENVIA OS DADOS PARA O CADASTRAMENTO DO PEDIDO
	var ajaxFornecedor = "";
	var ajaxCodProduto = [];
	var ajaxPesos = [];
	var ajaxPrecos = [];
	var inputMaterial = [];	
		
	// CADASTRA O PEDIDO
	$("#btnGerarPedido").click(function(){
		
		var lista = $("#itemNovo").find(".form-inline");
		
		// PEGA OS DADOS DOS ITENS - PELA VERICACAO DO CÓDIGO DO PRODUTO
		if(ajaxCodProduto == null || ajaxCodProduto == ""){
			console.log("nulo");
		}else{
			
			// PEGA OS PESOS E PREÇOS DOS INPUTS
			$.each(lista, function(index, value){
				if (typeof value == 'undefined') {
					console.log("INDEFINIDO: index " + index);
				}else{						
					if($.isNumeric($("#peso" + ids[index]).val()) && $.isNumeric($("#valor" + ids[index]).val())){
						console.log("São números, PESO: " + $("#peso" + ids[index]).val() + " VALOR: " + $("#valor" + ids[index]).val());						
						ajaxPesos.push($("#peso" + ids[index]).val());
						ajaxPrecos.push($("#valor" + ids[index]).val());
					}else{
						if(!$.isNumeric($("#peso" + ids[index]).val()) && !$.isNumeric($("#valor" + ids[index]).val())){
							$("#valor" + ids[index]).closest(".form-group").addClass("has-error");
							$("#peso" + ids[index]).closest(".form-group").addClass("has-error");
							console.log("Não são números VALOR: " + $("#valor" + ids[index]).val() + " PESO: " + $("#peso" + ids[index]).val());
						}else{
							if($.isNumeric($("#peso" + ids[index]).val())){
								$("#valor" + ids[index]).closest(".form-group").addClass("has-error");
								console.log("Não é número VALOR: " + $("#valor" + ids[index]).val());
							}else{
								$("#peso" + ids[index]).closest(".form-group").addClass("has-error");
								console.log("Não é número PESO: " + $("#peso" + ids[index]).val());
							}
						}
					}		
				}			
			});
			
			// TESTE PARA VER SE PEGOU OS DADOS DO PEDIDO
			console.log("COD. FORNECEDOR. " + codFornecedor);
			$.each(ajaxCodProduto, function(index, value){
				console.log("LINHA: " + ids[index] + 
						"\n\tCOD. PRODUTO: " + value +  
						"\n\tPESO: " + ajaxPesos[index] + 
						"\n\tVALOR: " + ajaxPrecos[index] + "\n");
			});
		}
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
});