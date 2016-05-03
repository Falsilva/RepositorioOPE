$(document).ready(function(){
	
	// SUBMIT - FORM CADASTRAR USUÁRIO
	$("#btnCadastrar").click(function(){		
		console.log("Modal Cadastrar, Botão Cadastrar Clicado");
		
		// Pega os dados digitados		
		var empresa = $("#modal-form[rel=modalcadastrar]").find("#empresa").val();
		var contato = $("#modal-form[rel=modalcadastrar]").find("#contato").val();
		var telefone = $("#modal-form[rel=modalcadastrar]").find("#telefone").val();
		var email = $("#modal-form[rel=modalcadastrar]").find("#email").val();
		var cep = $("#modal-form[rel=modalcadastrar]").find("#cep").val();
		var endereco = $("#modal-form[rel=modalcadastrar]").find("#endereco").val();
		var numero = $("#modal-form[rel=modalcadastrar]").find("#numero").val();
		var complemento = $("#modal-form[rel=modalcadastrar]").find("#complemento").val();
		var bairro = $("#modal-form[rel=modalcadastrar]").find("#bairro").val();
		var cidade = $("#modal-form[rel=modalcadastrar]").find("#cidade").val();
		var estado = $("#modal-form[rel=modalcadastrar]").find("#estado").val();
		
		// AJAX
		$.ajax({
			url:"controller",
			type:"post",
			data:{
				empresa:empresa,
				contato:contato,
				telefone:telefone,
				email:email,
				cep:cep,
				endereco:endereco,
				numero:numero,
				complemento:complemento,
				bairro:bairro,
				cidade:cidade,
				estado:estado,
				action:"cadastrarCliente",
				tarefa:"cadastrar"
			},
			success:function(resultado){
				console.log("Modal Cadastrar, Resultado da Ação Recebido");
				$("#modal-form[rel=modalcadastrar]").find(".modal-header").html("<h4 class='blue bigger'>Resultado...</h4>").prepend("<button type='button' class='close' data-dismiss='modal'>&times;</button>");
				$("#modal-form[rel=modalcadastrar]").find(".modal-body").html("<h3>" + resultado + "</h3>");
				var botao = $("#modal-form[rel=modalcadastrar]").find("#btnCancelar").attr("id", "btnFechar");				
				botao.text("Fechar");				
				$("#modal-form[rel=modalcadastrar]").find("#btnCadastrar").addClass("hidden");				
			}
		});
	});
	
	// SUBMIT - FORM EXCLUIR USUÁRIO
	$("#btnExcluir").click(function(){
		console.log("Modal Excluir, Botão Excluir Clicado");
		
		// Pega os dados do campo de Código		
		var codigo = $("#modal-form[rel=modalexcluir]").find("#codigo").val();
		
		// AJAX
		$.ajax({
			url:"controller",
			type:"post",
			data:{
				codigo:codigo,
				//action:"pesquisarUsuario",
				action:"excluirCliente",
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
