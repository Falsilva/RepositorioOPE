$(document).ready(function(){

	// MODAL PARA CADASTRAR
	$("a[role=cadastrar]").click(function(event){
		event.preventDefault();
		console.log("Clicado em Novo Usuário");
		// Pega o Modal
		var modal = $("#modal-form[rel=modalcadastrar]");
		modal.modal();
		console.log("Modal Cadastrar Iniciado");
	});
	
	// MODAL EXCLUIR
	$("a[role=excluir]").click(function(event){
		event.preventDefault();
		console.log("Clicado no Ícone Excluir da Tabela");
		// Pega a TR
		var tr = $(this).closest("tr");
		
		// Pega as TDs da TR
		var tdNomeUsuario = tr.find("td:first").prop("class", "hidden");
		var tdNome = tdNomeUsuario.next("td");
		var tdEmail = tdNome.next("td");
		var tdPerfil = tdEmail.next("td");
		
		// Pega os Dados das TDs
		var nomeusuario = tdNomeUsuario.text();
		var nome = tdNome.text();
		var email = tdEmail.text();
		var perfil = tdPerfil.text();
		
		// Pega o Modal
		var modal = $("#modal-form[rel=modalexcluir]");
		// Coloca os Dados no Modal e de Forma Somente Leitura
		modal.find("#nomeusuarioExcluir").prop("readonly", true).val(nomeusuario);
		modal.find("#nomeExcluir").prop("readonly", true).val(nome);
		modal.find("#emailExcluir").prop("readonly", true).val(email);
		modal.find("#perfilExcluir").prop("disabled", true).val(perfil);
		//modal.find("#senha").prop("readonly", true).closest("div .form-group").addClass("hidden");
		
		// Configura o Modal
		/*modal.find(".modal-header").html("<h4 class='red bigger'> Excluir... Tem certeza?</h4>").prepend("<button type='button' class='close' data-dismiss='modal'>&times;</button>");	
		var botao = $("#modal-form").find("#btnCadastrar");		
		botao.removeAttr("id");
		botao.attr("id", "btnExcluir");
		botao.text("Excluir")
		botao.prepend("<i class='ace-icon fa fa-trash-o'>&nbsp;");
		botao.removeClass("btn-primary");
		botao.addClass("btn-danger");*/
		
		modal.modal();
		console.log("Modal Excluir Iniciado");
		/*
		tr.fadeOut(500, function(event){
			
			tr.remove();
		});*/
	});
	
	// RECARREGA A PÁGINA AO FECHAR O MODAL
	$("#modal-form[rel=modalcadastrar]").on("hidden.bs.modal", function (){
		console.log("Modal Cadastrar Encerrado");
		location.reload();
	});
	$("#modal-form[rel=modalexcluir]").on("hidden.bs.modal", function (){
		console.log("Modal Excluir Encerrado");
		location.reload();
	});
});