$(document).ready(function(){
	
	// SUBMIT - FORM CADASTRAR CLIENTE
	$("#btnCadastrar").click(function(){		
		console.log("Modal Cadastrar, Botao Cadastrar Clicado - Form Cliente");
		
		// Pega os dados digitados		
		var empresa = $("#modal-form-cliente[rel=modalcadastrar]").find("#empresa").val();
		var contato = $("#modal-form-cliente[rel=modalcadastrar]").find("#contato").val();
		var telefone = $("#modal-form-cliente[rel=modalcadastrar]").find("#telefone").val();
		var email = $("#modal-form-cliente[rel=modalcadastrar]").find("#email").val();
		var cep = $("#modal-form-cliente[rel=modalcadastrar]").find("#cep").val();
		var endereco = $("#modal-form-cliente[rel=modalcadastrar]").find("#endereco").val();
		var numero = $("#modal-form-cliente[rel=modalcadastrar]").find("#numero").val();
		var complemento = $("#modal-form-cliente[rel=modalcadastrar]").find("#complemento").val();
		var bairro = $("#modal-form-cliente[rel=modalcadastrar]").find("#bairro").val();
		var cidade = $("#modal-form-cliente[rel=modalcadastrar]").find("#cidade").val();
		var estado = $("#modal-form-cliente[rel=modalcadastrar]").find("#estado").val();
		var cnpj = $("#modal-form-cliente[rel=modalcadastrar]").find("#cnpj").val();
		var inscEstadual = $("#modal-form-cliente[rel=modalcadastrar]").find("#inscEstadual").val();

		/* TESTE - VERIFICAÇÃO DE ERROS - EXCLUIR APÓS A CORREÇÃO
		var dados = [empresa, contato, telefone, email, cep, endereco, numero, complemento, bairro, cidade, estado];				
		$.each(dados, function(index, value){
			console.log("index " + index + ", value " + value);					
		});*/
		
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
				cnpj:cnpj,
				inscEstadual:inscEstadual,
				action:"cadastrarCliente",
				tarefa:"cadastrar"
			},
			success:function(resultado){
				console.log("Modal Cadastrar, Resultado da Acao Recebido");
				$("#modal-form-cliente[rel=modalcadastrar]").find(".modal-header").html("<h4 class='blue bigger'>Resultado...</h4>").prepend("<button type='button' class='close' data-dismiss='modal'>&times;</button>");
				$("#modal-form-cliente[rel=modalcadastrar]").find(".modal-body").html("<h3>" + resultado + "</h3>");
				var botao = $("#modal-form-cliente[rel=modalcadastrar]").find("#btnCancelar").attr("id", "btnFechar");				
				botao.text("Fechar");				
				$("#modal-form-cliente[rel=modalcadastrar]").find("#btnCadastrar").addClass("hidden");	
				$("#modal-form-cliente[rel=modalcadastrar]").find("#cmpObrigatorio").addClass("hidden");
			}
		});
	});
	
	// SUBMIT - FORM EXCLUIR CLIENTE
	$("#btnExcluir").click(function(){
		console.log("Modal Excluir, Botao Excluir Clicado");
		
		// Pega os dados do campo de Código		
		var codigo = $("#modal-form-cliente[rel=modalexcluir]").find("#codigo").val();
		
		// AJAX
		$.ajax({
			url:"controller",
			type:"post",
			data:{
				codigo:codigo,
				action:"excluirCliente",
			},
			success:function(resultado){
				console.log("Modal Excluir, Resultado da Acao Recebido");
				$("#modal-form-cliente[rel=modalexcluir]").find(".modal-header").html("<h4 class='blue bigger'>Resultado...</h4>").prepend("<button type='button' class='close' data-dismiss='modal'>&times;</button>");
				$("#modal-form-cliente[rel=modalexcluir]").find(".modal-body").html("<h3>" + resultado + "</h3>");
				var botao = $("#modal-form-cliente[rel=modalexcluir]").find("#btnCancelarExcluir").attr("id", "btnFecharExcluir");				
				botao.text("Fechar");
				$("#modal-form-cliente[rel=modalexcluir]").find("#btnExcluir").addClass("hidden");				
			}
		});
	});
	
	// SUBMIT - FORM ATUALIZAR CLIENTE
	$("#btnSalvar").click(function(){		
		console.log("Modal Atualizar, Botao Salvar Clicado - Form Cliente");
		
		// Pega os dados digitados	
		var codigo = $("#modal-form-cliente[rel=modalatualizar]").find("#codigo").val();
		var empresa = $("#modal-form-cliente[rel=modalatualizar]").find("#empresa").val();
		var contato = $("#modal-form-cliente[rel=modalatualizar]").find("#contato").val();
		var telefone = $("#modal-form-cliente[rel=modalatualizar]").find("#telefone").val();
		var email = $("#modal-form-cliente[rel=modalatualizar]").find("#email").val();
		var cep = $("#modal-form-cliente[rel=modalatualizar]").find("#cep").val();
		var endereco = $("#modal-form-cliente[rel=modalatualizar]").find("#endereco").val();
		var numero = $("#modal-form-cliente[rel=modalatualizar]").find("#numero").val();
		var complemento = $("#modal-form-cliente[rel=modalatualizar]").find("#complemento").val();
		var bairro = $("#modal-form-cliente[rel=modalatualizar]").find("#bairro").val();
		var cidade = $("#modal-form-cliente[rel=modalatualizar]").find("#cidade").val();
		var estado = $("#modal-form-cliente[rel=modalatualizar]").find("#estado").val();
		var cnpj = $("#modal-form-cliente[rel=modalatualizar]").find("#cnpj").val();
		var inscEstadual = $("#modal-form-cliente[rel=modalatualizar]").find("#inscEstadual").val();
		
		// AJAX
		$.ajax({
			url:"controller",
			type:"post",
			data:{
				codigo:codigo,
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
				cnpj:cnpj,
				inscEstadual:inscEstadual,
				action:"atualizarCliente",
				tarefa:"atualizar"
			},
			success:function(resultado){
				console.log("Modal Atualizar, Resultado da Acao Recebido");
				$("#modal-form-cliente[rel=modalatualizar]").find(".modal-header").html("<h4 class='blue bigger'>Resultado...</h4>").prepend("<button type='button' class='close' data-dismiss='modal'>&times;</button>");
				$("#modal-form-cliente[rel=modalatualizar]").find(".modal-body").html("<h3>" + resultado + "</h3>");
				var botao = $("#modal-form-cliente[rel=modalatualizar]").find("#btnCancelar").attr("id", "btnFechar");				
				botao.text("Fechar");				
				$("#modal-form-cliente[rel=modalatualizar]").find("#btnSalvar").addClass("hidden");	
				$("#modal-form-cliente[rel=modalatualizar]").find("#cmpObrigatorio2").addClass("hidden");
			}
		});
	});	
});
