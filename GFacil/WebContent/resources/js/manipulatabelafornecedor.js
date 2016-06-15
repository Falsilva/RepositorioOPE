$(document).ready(function(){
	
	// VARIÁVEIS GLOBAIS - necessárias para o armazenamento temporário dos dados originais da edição de registros da tabela de usuários
	var codigo_tmp;
	var tipoFornecedor_tmp;
	var empresa_tmp;
	var contato_tmp;
	var telefone_tmp;
	var email_tmp;
	var cep_tmp;
	var endereco_tmp;
	var numero_tmp;
	var complemento_tmp;
	var bairro_tmp;
	var cidade_tmp;
	var estado_tmp;
	var cnpj_tmp;
	var inscEstadual_tmp;
	
	/*
	var options =  {onKeyPress: function(telefone, e, field, options){
		var masks = ["(00)0.0000-0000", "(00)0000-0000"];
		mask = (telefone.length > 13) ? masks[1] : masks[0];
		$("#telefone").mask(mask, options);
	}};
	$("#telefone").mask("(00)0000-0000", options);
	*/
	var options =  {onKeyPress: function(telefone, e, field, options){
		var masks = ["(00) 0.0000-0000", "(00) 0000-00009"];
		mask = (telefone.length > 14) ? masks[0] : masks[1];
		$("#telefone").mask(mask, options);
	}};
	$("#telefone").mask("(00)0000-0000", options);
	
	$("#cep").mask("00000-000", {reverse: true});
	
	$("#cnpj").mask("00.000.000/0000-00", {reverse: true});
	
	$("#cnpj").blur(function(){
		validate_cnpj($(this).val());
	});
	
	$("#inscEstadual").focus(function(){
		var estado = $("#estado").val();
		switch(estado){
			case "RS":
				$("#inscEstadual").mask("000-0000000", {reverse: true});				
				break;
			case "SC":
				$("#inscEstadual").mask("000.000.000", {reverse: true});				
				break;
			case "PR":
				$("#inscEstadual").mask("00000000-00", {reverse: true});
				break;
			case "SP":
				$("#inscEstadual").mask("000.000.000.000", {reverse: true});
				break;
			case "MG":
				$("#inscEstadual").mask("000.000.000/0000", {reverse: true});
				break;
			case "RJ":
				$("#inscEstadual").mask("00.000.00-0", {reverse: true});
				break;
			case "ES":
				$("#inscEstadual").mask("000.000.00-0", {reverse: true});
				break;
			case "BA":
				$("#inscEstadual").mask("000.000.00-0", {reverse: true});
				break;
			case "SE":
				$("#inscEstadual").mask("000000000-0", {reverse: true});
				break;
			case "AL":
				$("#inscEstadual").mask("000000000", {reverse: true});
				break;
			case "PE":
				$("#inscEstadual").mask("00.0.000.0000000-0", {reverse: true});
				break;
			case "PB":
				$("#inscEstadual").mask("00000000-0", {reverse: true}); 
				break;
			case "RN":
				$("#inscEstadual").mask("00.000.000-0", {reverse: true});
				break;
			case "PI":
				$("#inscEstadual").mask("000000000", {reverse: true});
				break;
			case "MA":
				$("#inscEstadual").mask("000000000", {reverse: true});
				break;
			case "CE":
				$("#inscEstadual").mask("00000000-0", {reverse: true});
				break;
			case "GO":
				$("#inscEstadual").mask("00.000.000-0", {reverse: true});
				break;
			case "TO":
				$("#inscEstadual").mask("00000000000", {reverse: true});
				break;
			case "MT":
				$("#inscEstadual").mask("000000000", {reverse: true});
				break;
			case "MS":
				$("#inscEstadual").mask("000000000", {reverse: true});
				break;
			case "DF":
				$("#inscEstadual").mask("00000000000-00", {reverse: true});
				break;
			case "AM":
				$("#inscEstadual").mask("00.000.000-0", {reverse: true});
				break;
			case "AC":
				$("#inscEstadual").mask("00.000.000/000-00", {reverse: true});
				break;
			case "PA":
				$("#inscEstadual").mask("00-000000-0", {reverse: true});
				break;
			case "RO":
				$("#inscEstadual").mask("000.00000-0", {reverse: true});
				break;
			case "RR":
				$("#inscEstadual").mask("00000000-0", {reverse: true});
				break;
			case "AP":
				$("#inscEstadual").mask("000000000", {reverse: true});
				break;
			default:
				$("#inscEstadual").mask("000.000.000.000", {reverse: true});	// MÁSCARA SP
				break;
		}		
	});
	
	$("#estado").blur(function(){
		var estado = $("#estado").val();
		switch(estado){
			case "RS":
				$("#inscEstadual").mask("000-0000000", {reverse: true});				
				break;
			case "SC":
				$("#inscEstadual").mask("000.000.000", {reverse: true});				
				break;
			case "PR":
				$("#inscEstadual").mask("00000000-00", {reverse: true});
				break;
			case "SP":
				$("#inscEstadual").mask("000.000.000.000", {reverse: true});
				break;
			case "MG":
				$("#inscEstadual").mask("000.000.000/0000", {reverse: true});
				break;
			case "RJ":
				$("#inscEstadual").mask("00.000.00-0", {reverse: true});
				break;
			case "ES":
				$("#inscEstadual").mask("000.000.00-0", {reverse: true});
				break;
			case "BA":
				$("#inscEstadual").mask("000.000.00-0", {reverse: true});
				break;
			case "SE":
				$("#inscEstadual").mask("000000000-0", {reverse: true});
				break;
			case "AL":
				$("#inscEstadual").mask("000000000", {reverse: true});
				break;
			case "PE":
				$("#inscEstadual").mask("00.0.000.0000000-0", {reverse: true});
				break;
			case "PB":
				$("#inscEstadual").mask("00000000-0", {reverse: true}); 
				break;
			case "RN":
				$("#inscEstadual").mask("00.000.000-0", {reverse: true});
				break;
			case "PI":
				$("#inscEstadual").mask("000000000", {reverse: true});
				break;
			case "MA":
				$("#inscEstadual").mask("000000000", {reverse: true});
				break;
			case "CE":
				$("#inscEstadual").mask("00000000-0", {reverse: true});
				break;
			case "GO":
				$("#inscEstadual").mask("00.000.000-0", {reverse: true});
				break;
			case "TO":
				$("#inscEstadual").mask("00000000000", {reverse: true});
				break;
			case "MT":
				$("#inscEstadual").mask("000000000", {reverse: true});
				break;
			case "MS":
				$("#inscEstadual").mask("000000000", {reverse: true});
				break;
			case "DF":
				$("#inscEstadual").mask("00000000000-00", {reverse: true});
				break;
			case "AM":
				$("#inscEstadual").mask("00.000.000-0", {reverse: true});
				break;
			case "AC":
				$("#inscEstadual").mask("00.000.000/000-00", {reverse: true});
				break;
			case "PA":
				$("#inscEstadual").mask("00-000000-0", {reverse: true});
				break;
			case "RO":
				$("#inscEstadual").mask("000.00000-0", {reverse: true});
				break;
			case "RR":
				$("#inscEstadual").mask("00000000-0", {reverse: true});
				break;
			case "AP":
				$("#inscEstadual").mask("000000000", {reverse: true});
				break;
			default:
				$("#inscEstadual").mask("000.000.000.000", {reverse: true});	// MÁSCARA SP
				break;
		}		
	});
	
	// MODAL PARA CADASTRAR - CLIQUE DO BOTÃO CADASTRAR EXIBE O MODAL
	$("a[role=cadastrar]").click(function(event){
		event.preventDefault();
		
		// Pega o Modal
		var modal = $("#modal-form-fornecedor[rel=modalcadastrar]");
		
		// Exibe o Modal Form Cadastrar
		modal.modal();		
	});
	
	// MODAL EXCLUIR - CLIQUE DO BOTÃO EXCLUIR DA LINHA DE UM REGISTRO TABELA EXIBE O MODAL
	$("a[role=excluir]").click(function(event){
		event.preventDefault();
		
		// Pega a TR
		var tr = $(this).closest("tr");
		
		// Pega as TDs da TR
		var tdCodigo = tr.find("td:first").prop("class", "hidden");
		var tdTipoFornecedor = tdCodigo.next("td");
		var tdEmpresa = tdTipoFornecedor.next("td");
		var tdContato = tdEmpresa.next("td");
		var tdTelefone = tdContato.next("td");
		var tdEmail = tdTelefone.next("td");
		var tdEndereco = tdEmail.next("td");
		var tdCnpj = tdEndereco.next("td");
		var tdInscEstadual = tdCnpj.next("td");
		
		// Pega os Dados das TDs		
		var codigo = tdCodigo.text();
		var tipoFornecedor = tdTipoFornecedor.text();
		var empresa = tdEmpresa.text();
		var contato = tdContato.text();
		var telefone = tdTelefone.text();
		var email = tdEmail.text();		
		var endereco = tdEndereco.text();		
		var cnpj = tdCnpj.text();
		var inscEstadual = tdInscEstadual.text();
		
		// Pega o Modal
		var modal = $("#modal-form-fornecedor[rel=modalexcluir]");
		
		// Coloca os Dados no Modal e de Forma Somente Leitura
		modal.find("#codigo").val(codigo);		
				
		if(modal.find("#tipoFornecedor1").val() === tipoFornecedor){
			modal.find("#tipoFornecedor1").prop("checked", true);
			modal.find("label[for=tipoFornecedor2]").prop("class", "hidden");
			modal.find("#tipoFornecedor2").prop("class", "hidden");
		}else{
			modal.find("#tipoFornecedor2").prop("checked", true);
			modal.find("label[for=tipoFornecedor1]").prop("class", "hidden");
			modal.find("#tipoFornecedor1").prop("class", "hidden");
		}		
		
		modal.find("#contato").prop("readonly", true).val(contato);
		modal.find("#telefone").prop("readonly", true).val(telefone);
		modal.find("#email").prop("readonly", true).val(email);		
		
		if(tipoFornecedor === "CATADOR"){			
			var labels = modal.find("label");
			labels.each(function(){		
				if($(this).attr("for") != "tipoFornecedor" && $(this).attr("for") != "tipoFornecedor1" && $(this).attr("for") != "tipoFornecedor2" && $(this).attr("for") != "contato" && $(this).attr("for") != "telefone" && $(this).attr("for") != "email"){
					$(this).parent().addClass("hidden");
				}
				if($(this).attr("for") == "contato") $(this).text("Nome do Catador");
			});
		}else{		
			modal.find("#empresa").prop("readonly", true).val(empresa);			
			modal.find("#endereco").prop("readonly", true).val(endereco);
			modal.find("#cnpj").prop("readonly", true).val(cnpj);
			modal.find("#inscEstadual").prop("readonly", true).val(inscEstadual);		
		}
		
		// Exibe o Modal Form Excluir
		modal.modal();		
	});
	
	// CLIQUE DO BOTÃO EDITAR DA LINHA DE UM REGISTRO DA TABELA
	$("a[role=editar]").click(function(event){
		event.preventDefault();		
		
		// Limpando as varíáveis utilizadas para preservação de dados
		codigo_tmp = "";
		tipoFornecedor_tmp = "";
		empresa_tmp = "";
		contato_tmp = "";
		telefone_tmp = "";
		email_tmp = "";		
		endereco_tmp = "";
		cnpj_tmp = "";
		inscEstadual_tmp = "";
		
		// Pega a TR
		var tr = $(this).closest("tr");
		
		// Pega as TDs da TR
		var tdCodigo = tr.find("td:first").prop("class", "hidden");
		var tdTipoFornecedor = tdCodigo.next("td");
		var tdEmpresa = tdTipoFornecedor.next("td");
		var tdContato = tdEmpresa.next("td");
		var tdTelefone = tdContato.next("td");
		var tdEmail = tdTelefone.next("td");
		var tdEndereco = tdEmail.next("td");
		var tdCnpj = tdEndereco.next("td");
		var tdInscEstadual = tdCnpj.next("td");
		
		// Pega os Dados das TDs		
		var codigo = tdCodigo.text();
		var tipoFornecedor = tdTipoFornecedor.text();
		var empresa = tdEmpresa.text();
		var contato = tdContato.text();
		var telefone = tdTelefone.text();
		var email = tdEmail.text();		
		var endereco = tdEndereco.text();		
		var cnpj = tdCnpj.text();
		var inscEstadual = tdInscEstadual.text();
		
		// Guarda os Dados para a Recuperação em Caso de Cancelar a Edição
		codigo_tmp = codigo;
		tipoFornecedor_tmp = tipoFornecedor;
		empresa_tmp = empresa;
		contato_tmp = contato;
		telefone_tmp = telefone;
		email_tmp = email;		
		endereco_tmp = endereco;
		cnpj_tmp = cnpj;
		inscEstadual_tmp = inscEstadual;
		
		// Pega o Modal
		var modal = $("#modal-form-fornecedor[rel=modalatualizar]");
		
		// Coloca os Dados no Modal
		modal.find("#codigo").val(codigo);
		
		if(modal.find("#tipoFornecedor1").val() === tipoFornecedor){
			modal.find("#tipoFornecedor1").prop("checked", true);
		}else{
			modal.find("#tipoFornecedor2").prop("checked", true);
		}		
		
		// Formata o endereço somente se o tipo de fornecedor for EMPRESA
		if(tipoFornecedor === "EMPRESA"){
			
			modal.find("#empresa").val(empresa);
			modal.find("#contato").val(contato);
			modal.find("#telefone").val(telefone);
			modal.find("#email").val(email);
			modal.find("#cnpj").val(cnpj);
			modal.find("#inscEstadual").val(inscEstadual);
			
			// Recupera os dados do endereço em campos separados 
			$.ajax({
				url:"controller",
				type:"post",
				dataType:"json",
				data:{
					codigo:codigo,				
					action:"separaEnderecoFornecedor"
				},			
				success:function(resultado){
					modal.find("#endereco").val(resultado.dataEndereco[0].endereco);
					modal.find("#numero").val(resultado.dataEndereco[0].numero);
					modal.find("#complemento").val(resultado.dataEndereco[0].complemento);
					modal.find("#bairro").val(resultado.dataEndereco[0].bairro);
					modal.find("#cidade").val(resultado.dataEndereco[0].cidade);
					modal.find("#estado").val(resultado.dataEndereco[0].estado);
					modal.find("#cep").val(resultado.dataEndereco[0].cep);
				}		
			});
		}else{			
			modal.find("#contato").val(contato);
			modal.find("#telefone").val(telefone);
			modal.find("#email").val(email);			
			
			var labels = modal.find("label");
			labels.each(function(){		
				if($(this).attr("for") != "tipoFornecedor" && $(this).attr("for") != "tipoFornecedor1" && $(this).attr("for") != "tipoFornecedor2" && $(this).attr("for") != "contato" && $(this).attr("for") != "telefone" && $(this).attr("for") != "email"){
					$(this).parent().addClass("hidden");
				}
			});
		}
		
		// Exibe o Modal Form Atualizar
		modal.modal();
	});
	
	
	// Ao carregar o Modal Cadastrar, é adicionado a escuta da alternância de seleção do Tipo de Fornecedor
	$("#modal-form-fornecedor[rel=modalcadastrar]").on("shown.bs.modal", function (){
		var modal = $(this);		
		var tipoFornecedor = modal.find("input:radio");	
		var tipoEmpresa = modal.find("#tipoFornecedor1");
		var labels = modal.find("label");
		var inputs = modal.find("input");
		
		// Escuta da Alternância da Seleção do Tipo de Fornecedor
		tipoFornecedor.change(function(){
			
			// Caso selecionado EMPRESA 
			if(tipoEmpresa.is(":checked")){
				
				// Exibe o Modal com Formulário para Empresa
				var labels = modal.find("label");
				labels.each(function(){		
					if($(this).attr("for") != "codigo" && $(this).attr("for") != "tipoFornecedor" && $(this).attr("for") != "tipoFornecedor1" && $(this).attr("for") != "tipoFornecedor2" && $(this).attr("for") != "contato" && $(this).attr("for") != "telefone" && $(this).attr("for") != "email"){
						$(this).parent().removeClass("hidden");
					}
					if($(this).attr("for") == "contato") $(this).text("Contato");
				});
				// Caso selecionado CATADOR 
			}else{
				
				// Exibe o Modal com Formulário para Catador
				var labels = modal.find("label");
				labels.each(function(){		
					if($(this).attr("for") != "tipoFornecedor" && $(this).attr("for") != "tipoFornecedor1" && $(this).attr("for") != "tipoFornecedor2" && $(this).attr("for") != "contato" && $(this).attr("for") != "telefone" && $(this).attr("for") != "email"){
						$(this).parent().addClass("hidden");
					}					
					if($(this).attr("for") == "contato") $(this).text("Nome do Catador");
				});				
			}
		});	
	});
	
	$("#modal-form-fornecedor[rel=modalatualizar]").on("shown.bs.modal", function (){
		var modal = $(this);		
		var tipoFornecedor = modal.find("input:radio");	
		var tipoEmpresa = modal.find("#tipoFornecedor1");		
		var labels = modal.find("label");
		var inputs = modal.find("input");
		
		// Escuta a seleção do tipo de fornecedor e faz as alterações necessárias
		tipoFornecedor.change(function(){
			
			// Caso selecionado EMPRESA 
			if(tipoEmpresa.is(":checked")){
				
				// Exibe o Modal com Formulário para Empresa
				var labels = modal.find("label");
				labels.each(function(){		
					if($(this).attr("for") != "codigo" && $(this).attr("for") != "tipoFornecedor" && $(this).attr("for") != "tipoFornecedor1" && $(this).attr("for") != "tipoFornecedor2" && $(this).attr("for") != "contato" && $(this).attr("for") != "telefone" && $(this).attr("for") != "email"){
						$(this).parent().removeClass("hidden");
					}
					if($(this).attr("for") == "contato") $(this).text("Contato");
				});
			
			// Caso selecionado CATADOR 
			}else{
				
				// Exibe o Modal com Formulário para Catador
				var labels = modal.find("label");
				labels.each(function(){		
					if($(this).attr("for") != "tipoFornecedor" && $(this).attr("for") != "tipoFornecedor1" && $(this).attr("for") != "tipoFornecedor2" && $(this).attr("for") != "contato" && $(this).attr("for") != "telefone" && $(this).attr("for") != "email"){
						$(this).parent().addClass("hidden");
					}
					if($(this).attr("for") == "contato") $(this).text("Nome do Catador");
				});				
			}
		});
	});	
	
	// RECARREGA A PÁGINA AO FECHAR O MODAL CADASTRAR
	$("#modal-form-fornecedor[rel=modalcadastrar]").on("hidden.bs.modal", function (){
		console.log("Modal Cadastrar Encerrado");
		location.reload();
	});
	
	// RECARREGA A PÁGINA AO FECHAR O MODAL EXCLUIR
	$("#modal-form-fornecedor[rel=modalexcluir]").on("hidden.bs.modal", function (){
		console.log("Modal Excluir Encerrado");
		location.reload();
	});
	
	// RECARREGA A PÁGINA AO FECHAR O MODAL ATUALIZAR
	$("#modal-form-fornecedor[rel=modalatualizar]").on("hidden.bs.modal", function (){
		console.log("Modal Atualizar Encerrado");
		location.reload();
	});
});