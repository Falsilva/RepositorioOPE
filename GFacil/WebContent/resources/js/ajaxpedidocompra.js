$(document).ready(function(){		
		
	// SUBMIT - FORM EXCLUIR PEDIDO
	$("#btnExcluir").click(function(){
		console.log("Modal Excluir, Botao Excluir Clicado");
		
		// Pega o código do campo		
		var nPedido = $("#modal-form-pedido[rel=modalexcluir]").find("#noPedidoExcluir").val();
		var statusExcluir = $("#modal-form-pedido[rel=modalexcluir]").find("#statusExcluir").val();
		console.log("No. PEDIDO: " + nPedido + " STATUS: " + statusExcluir);
		// AJAX
		$.ajax({
			url:"controller",
			type:"post",
			data:{
				nPedido:nPedido,
				statusExcluir:statusExcluir,
				action:"excluirPedidoCompra",
			},
			success:function(resultado){
				console.log("Modal Excluir, Resultado da Acao Recebido");
				$("#modal-form-pedido[rel=modalexcluir]").find(".modal-header").html("<h4 class='blue bigger'>Resultado...</h4>").prepend("<button type='button' class='close' data-dismiss='modal'>&times;</button>");
				$("#modal-form-pedido[rel=modalexcluir]").find(".modal-body").html("<h3>" + resultado + "</h3>");
				var botao = $("#modal-form-pedido[rel=modalexcluir]").find("#btnCancelarExcluir").attr("id", "btnFecharExcluir");				
				botao.text("Fechar");
				$("#modal-form-pedido[rel=modalexcluir]").find("#btnExcluir").addClass("hidden");				
			}
		});
	});	
});
