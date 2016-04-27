$(document).ready(function(){
	
	// SUBMIT - FORM CADASTRAR USUÁRIO
	$("#btnCadastrar").click(function(){
		
		// Pega os dados digitados
		var nome = $("#modal-form").find("#nome").val();
		var email = $("#email").val();
		var nomeusuario = $("#nomeusuario").val();
		var senha = $("#senha").val();
		var perfil = $("#perfil").val();
		console.log("AJAX RECEBIDO O NOME: " + nome);
		
		// AJAX
		$.ajax({
			url:"controller",
			type:"post",
			data:{
				nome:nome,
				email:email,
				nomeusuario:nomeusuario,
				senha:senha,
				perfil:perfil,
				action:"cadastrarUsuario",
				tarefa:"cadastrar"
			},
			success:function(resultado){				
				$("#modal-form").find(".modal-header").html("<h4>Resultado...</h4>").prepend("<button type='button' class='close' data-dismiss='modal'>&times;</button>");
				$("#modal-form").find(".modal-body").html("<h3>" + resultado + "</h3>");
				var botao = $("#modal-form").find("#btnCancelar").attr("id", "btnFechar");				
				botao.text("Fechar");
				//botao.removeAttr("data-dismiss");
				$("#modal-form").find("#btnCadastrar").addClass("hidden");								
			}
		});
	});
	
	// RECARREGA A PÁGINA AO FECHAR O MODAL
	$('#modal-form').on('hidden.bs.modal', function () {
		 location.reload();
	});	
});
