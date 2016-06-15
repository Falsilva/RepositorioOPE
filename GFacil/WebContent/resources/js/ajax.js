$(document).ready(function(){
	
	// SUBMIT - FORM CADASTRAR USUÁRIO
	$("#btnCadastrar").click(function(){		
		console.log("Modal Cadastrar, Botão Cadastrar Clicado");
		
		// Pega os dados digitados
		var nome = $("#modal-form[rel=modalcadastrar]").find("#nome").val();
		var email = $("#modal-form[rel=modalcadastrar]").find("#email").val();
		var nomeusuario = $("#modal-form[rel=modalcadastrar]").find("#nomeusuario").val();
		var senha = $("#modal-form[rel=modalcadastrar]").find("#senha").val();
		var perfil = $("#modal-form[rel=modalcadastrar]").find("#perfil").val();		
		
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
				console.log("Modal Cadastrar, Resultado da Ação Recebido");
				$("#modal-form[rel=modalcadastrar]").find(".modal-header").html("<h4 class='blue bigger'>Resultado...</h4>").prepend("<button type='button' class='close' data-dismiss='modal'>&times;</button>");
				$("#modal-form[rel=modalcadastrar]").find(".modal-body").html("<h3>" + resultado + "</h3>");
				var botao = $("#modal-form[rel=modalcadastrar]").find("#btnCancelar").attr("id", "btnFechar");				
				botao.text("Fechar");				
				$("#modal-form[rel=modalcadastrar]").find("#btnCadastrar").addClass("hidden");		
				$("#modal-form[rel=modalcadastrar]").find("#cmpObrigatorio").addClass("hidden");
			}
		});
	});
	
	// SUBMIT - FORM EXCLUIR USUÁRIO
	$("#btnExcluir").click(function(){
		console.log("Modal Excluir, Botão Excluir Clicado");
		
		// Pega os dados do campo de Login (Nome de Usuário)		
		var nomeusuario = $("#modal-form[rel=modalexcluir]").find("#nomeusuarioExcluir").val();
		
		// AJAX
		$.ajax({
			url:"controller",
			type:"post",
			data:{
				nomeusuario:nomeusuario,
				//action:"pesquisarUsuario",
				action:"excluirUsuario",
				//tarefa:"excluir"
			},
			success:function(resultado){
				console.log("Modal Excluir, Resultado da Ação Recebido");
				$("#modal-form[rel=modalexcluir]").find(".modal-header").html("<h4 class='blue bigger'>Resultado...</h4>").prepend("<button type='button' class='close' data-dismiss='modal'>&times;</button>");
				$("#modal-form[rel=modalexcluir]").find(".modal-body").html("<h3>" + resultado + "</h3>");
				var botao = $("#modal-form[rel=modalexcluir]").find("#btnCancelarExcluir").attr("id", "btnFecharExcluir");				
				botao.text("Fechar");
				$("#modal-form[rel=modalexcluir]").find("#btnExcluir").addClass("hidden");				
			}
		});
	});	
	
});
