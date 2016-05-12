$(document).ready(function(){
	
	// SUBMIT - FORM CADASTRAR PRODUTOS
	$("#btnCadastrar").click(function(){		
		console.log("Modal Cadastrar, Botao Cadastrar Clicado - Form Precos");
		
		// Pega os dados digitados		
		var produto = $("#modal-form-precos[rel=modalcadastrar]").find("#produto").val();
		var precoCompra = $("#modal-form-precos[rel=modalcadastrar]").find("#precoCompra").val();
		var precoVenda = $("#modal-form-precos[rel=modalcadastrar]").find("#precoVenda").val();	
		
		// AJAX
		$.ajax({
			url:"controller",
			type:"post",
			data:{
				produto:produto,
				precoCompra:precoCompra,
				precoVenda:precoVenda,				
				action:"cadastrarProduto"
			},
			success:function(resultado){
				console.log("Modal Cadastrar, Resultado da Acao Recebido");
				$("#modal-form-precos[rel=modalcadastrar]").find(".modal-header").html("<h4 class='blue bigger'>Resultado...</h4>").prepend("<button type='button' class='close' data-dismiss='modal'>&times;</button>");
				$("#modal-form-precos[rel=modalcadastrar]").find(".modal-body").html("<h3>" + resultado + "</h3>");
				var botao = $("#modal-form-precos[rel=modalcadastrar]").find("#btnCancelar").attr("id", "btnFechar");				
				botao.text("Fechar");				
				$("#modal-form-precos[rel=modalcadastrar]").find("#btnCadastrar").addClass("hidden");				
			}
		});
	});
	
	// SUBMIT - FORM EXCLUIR PRODUTOS
	$("#btnExcluir").click(function(){
		console.log("Modal Excluir, Botao Excluir Clicado");
		
		// Pega os dados do campo de Código		
		var codigo = $("#modal-form-precos[rel=modalexcluir]").find("#codigo").val();
		
		// AJAX
		$.ajax({
			url:"controller",
			type:"post",
			data:{
				codigo:codigo,
				action:"excluirProduto"
			},
			success:function(resultado){
				console.log("Modal Excluir, Resultado da Acao Recebido");
				$("#modal-form-precos[rel=modalexcluir]").find(".modal-header").html("<h4 class='blue bigger'>Resultado...</h4>").prepend("<button type='button' class='close' data-dismiss='modal'>&times;</button>");
				$("#modal-form-precos[rel=modalexcluir]").find(".modal-body").html("<h3>" + resultado + "</h3>");
				var botao = $("#modal-form-precos[rel=modalexcluir]").find("#btnCancelarExcluir").attr("id", "btnFecharExcluir");				
				botao.text("Fechar");
				$("#modal-form-precos[rel=modalexcluir]").find("#btnExcluir").addClass("hidden");				
			}
		});
	});
	
	// SUBMIT - FORM ATUALIZAR PRODUTOS
	$("#btnSalvar").click(function(){		
		console.log("Modal Atualizar, Botao Salvar Clicado - Form Precos");
		
		// Pega os dados digitados	
		var codigo = $("#modal-form-precos[rel=modalatualizar]").find("#codigo").val();
		var produto = $("#modal-form-precos[rel=modalatualizar]").find("#produto").val();
		var precoCompra = $("#modal-form-precos[rel=modalatualizar]").find("#precoCompra").val();
		var precoVenda = $("#modal-form-precos[rel=modalatualizar]").find("#precoVenda").val();
		
		// AJAX
		$.ajax({
			url:"controller",
			type:"post",
			data:{
				codigo:codigo,
				produto:produto,
				precoCompra:precoCompra,
				precoVenda:precoVenda,
				action:"atualizarProduto"
			},
			success:function(resultado){
				console.log("Modal Atualizar, Resultado da Acao Recebido");
				$("#modal-form-precos[rel=modalatualizar]").find(".modal-header").html("<h4 class='blue bigger'>Resultado...</h4>").prepend("<button type='button' class='close' data-dismiss='modal'>&times;</button>");
				$("#modal-form-precos[rel=modalatualizar]").find(".modal-body").html("<h3>" + resultado + "</h3>");
				var botao = $("#modal-form-precos[rel=modalatualizar]").find("#btnCancelar").attr("id", "btnFechar");				
				botao.text("Fechar");				
				$("#modal-form-precos[rel=modalatualizar]").find("#btnSalvar").addClass("hidden");				
			}
		});
	});	
});
