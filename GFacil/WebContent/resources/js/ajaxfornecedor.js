$(document).ready(function(){
	
	// SUBMIT - FORM CADASTRAR FORNECEDOR
	$("#btnCadastrar").click(function(){		
		console.log("Modal Cadastrar, Botão Cadastrar Clicado - Form Fornecedor");
		
		// Pega os dados digitados
		var tipoFornecedor = $("#modal-form-fornecedor[rel=modalcadastrar]").find("input[name=tipoFornecedor]:checked").val();
		var empresa = $("#modal-form-fornecedor[rel=modalcadastrar]").find("#empresa").val();
		var contato = $("#modal-form-fornecedor[rel=modalcadastrar]").find("#contato").val();
		var telefone = $("#modal-form-fornecedor[rel=modalcadastrar]").find("#telefone").val();
		var email = $("#modal-form-fornecedor[rel=modalcadastrar]").find("#email").val();
		var cep = $("#modal-form-fornecedor[rel=modalcadastrar]").find("#cep").val();
		var endereco = $("#modal-form-fornecedor[rel=modalcadastrar]").find("#endereco").val();
		var numero = $("#modal-form-fornecedor[rel=modalcadastrar]").find("#numero").val();
		var complemento = $("#modal-form-fornecedor[rel=modalcadastrar]").find("#complemento").val();
		var bairro = $("#modal-form-fornecedor[rel=modalcadastrar]").find("#bairro").val();
		var cidade = $("#modal-form-fornecedor[rel=modalcadastrar]").find("#cidade").val();
		var estado = $("#modal-form-fornecedor[rel=modalcadastrar]").find("#estado").val();
		var cnpj = $("#modal-form-fornecedor[rel=modalcadastrar]").find("#cnpj").val();
		var inscEstadual = $("#modal-form-fornecedor[rel=modalcadastrar]").find("#inscEstadual").val();
		
		// Caso o tipo de fornecedor seja CATADOR, apaga os dados desnecessários
		if(tipoFornecedor == "CATADOR"){
			empresa = "(CATADOR DE MATERIAL)";	
			cep = "";
			endereco = "";
			numero = "";
			complemento = "";
			bairro = "";
			cidade = "";
			estado = "";
			cnpj = "";
			inscEstadual = "";			
		}
		
		// AJAX
		$.ajax({
			url:"controller",
			type:"post",
			data:{
				tipoFornecedor:tipoFornecedor,
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
				action:"cadastrarFornecedor",
				tarefa:"cadastrar"
			},
			success:function(resultado){
				console.log("Modal Cadastrar, Resultado da Ação Recebido");
				$("#modal-form-fornecedor[rel=modalcadastrar]").find(".modal-header").html("<h4 class='blue bigger'>Resultado...</h4>").prepend("<button type='button' class='close' data-dismiss='modal'>&times;</button>");
				$("#modal-form-fornecedor[rel=modalcadastrar]").find(".modal-body").html("<h3>" + resultado + "</h3>");
				var botao = $("#modal-form-fornecedor[rel=modalcadastrar]").find("#btnCancelar").attr("id", "btnFechar");				
				botao.text("Fechar");				
				$("#modal-form-fornecedor[rel=modalcadastrar]").find("#btnCadastrar").addClass("hidden");				
			}
		});
	});
	
	// SUBMIT - FORM EXCLUIR FORNECEDOR
	$("#btnExcluir").click(function(){
		console.log("Modal Excluir, Botão Excluir Clicado");
		
		// Pega os dados do campo de Código		
		var codigo = $("#modal-form-fornecedor[rel=modalexcluir]").find("#codigo").val();
		
		// AJAX
		$.ajax({
			url:"controller",
			type:"post",
			data:{
				codigo:codigo,
				action:"excluirFornecedor",
			},
			success:function(resultado){
				console.log("Modal Excluir, Resultado da Ação Recebido");
				$("#modal-form-fornecedor[rel=modalexcluir]").find(".modal-header").html("<h4 class='blue bigger'>Resultado...</h4>").prepend("<button type='button' class='close' data-dismiss='modal'>&times;</button>");
				$("#modal-form-fornecedor[rel=modalexcluir]").find(".modal-body").html("<h3>" + resultado + "</h3>");
				var botao = $("#modal-form-fornecedor[rel=modalexcluir]").find("#btnCancelarExcluir").attr("id", "btnFecharExcluir");				
				botao.text("Fechar");
				$("#modal-form-fornecedor[rel=modalexcluir]").find("#btnExcluir").addClass("hidden");				
			}
		});
	});	
	
	// SUBMIT - FORM ATUALIZAR CLIENTE
	$("#btnSalvar").click(function(){		
		console.log("Modal Atualizar, Botao Salvar Clicado - Form Fornecedor");
		
		// Pega os dados digitados
		var codigo = $("#modal-form-fornecedor[rel=modalatualizar]").find("#codigo").val();
		var tipoFornecedor = $("#modal-form-fornecedor[rel=modalatualizar]").find("input:checked").val();		
		var empresa = $("#modal-form-fornecedor[rel=modalatualizar]").find("#empresa").val();
		var contato = $("#modal-form-fornecedor[rel=modalatualizar]").find("#contato").val();
		var telefone = $("#modal-form-fornecedor[rel=modalatualizar]").find("#telefone").val();
		var email = $("#modal-form-fornecedor[rel=modalatualizar]").find("#email").val();
		var cep = $("#modal-form-fornecedor[rel=modalatualizar]").find("#cep").val();
		var endereco = $("#modal-form-fornecedor[rel=modalatualizar]").find("#endereco").val();
		var numero = $("#modal-form-fornecedor[rel=modalatualizar]").find("#numero").val();
		var complemento = $("#modal-form-fornecedor[rel=modalatualizar]").find("#complemento").val();
		var bairro = $("#modal-form-fornecedor[rel=modalatualizar]").find("#bairro").val();
		var cidade = $("#modal-form-fornecedor[rel=modalatualizar]").find("#cidade").val();
		var estado = $("#modal-form-fornecedor[rel=modalatualizar]").find("#estado").val();
		var cnpj = $("#modal-form-fornecedor[rel=modalatualizar]").find("#cnpj").val();
		var inscEstadual = $("#modal-form-fornecedor[rel=modalatualizar]").find("#inscEstadual").val();
		
		// Caso o tipo de fornecedor seja CATADOR, apaga os dados desnecessários
		if(tipoFornecedor == "CATADOR"){
			empresa = "(CATADOR DE MATERIAL)";	
			cep = "";
			endereco = "";
			numero = "";
			complemento = "";
			bairro = "";
			cidade = "";
			estado = "";
			cnpj = "";
			inscEstadual = "";
			console.log("EMPRESA: " + empresa + " CEP: " + cep);
		}
		
		// AJAX
		$.ajax({
			url:"controller",
			type:"post",
			data:{
				codigo:codigo,
				tipoFornecedor:tipoFornecedor,
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
				action:"atualizarFornecedor",
				tarefa:"atualizar"
			},
			success:function(resultado){
				console.log("Modal Atualizar, Resultado da Acao Recebido");
				$("#modal-form-fornecedor[rel=modalatualizar]").find(".modal-header").html("<h4 class='blue bigger'>Resultado...</h4>").prepend("<button type='button' class='close' data-dismiss='modal'>&times;</button>");
				$("#modal-form-fornecedor[rel=modalatualizar]").find(".modal-body").html("<h3>" + resultado + "</h3>");
				var botao = $("#modal-form-fornecedor[rel=modalatualizar]").find("#btnCancelar").attr("id", "btnFechar");				
				botao.text("Fechar");				
				$("#modal-form-fornecedor[rel=modalatualizar]").find("#btnSalvar").addClass("hidden");				
			}
		});
	});
});
