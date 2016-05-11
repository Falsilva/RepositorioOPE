$(function(){	
	
	// CLIENTE - MODAL CADASTRAR
	$("#modal-form-cliente[rel=modalcadastrar]").find("#cep").change(function(){
		var cep_code = $(this).val();
		if( cep_code.length <= 0 ) return;
		$.get("http://apps.widenet.com.br/busca-cep/api/cep.json", 
				{ code: cep_code },
				function(result){
					if( result.status!=1 ){		        	   
						// Pega e Exibe o Modal
		       		   	var modal = $("#modal-form-cliente[rel=modalcep]");
		       		   	modal.modal();		        	   
		       		   	return;
					}
					$("#modal-form-cliente[rel=modalcadastrar]").find("input#cep").val( result.code );
					$("#modal-form-cliente[rel=modalcadastrar]").find("input#endereco").val( result.address );
					$("#modal-form-cliente[rel=modalcadastrar]").find("input#bairro").val( result.district );            
					$("#modal-form-cliente[rel=modalcadastrar]").find("input#cidade").val( result.city );
					$("#modal-form-cliente[rel=modalcadastrar]").find("input#estado").val( result.state );
		});   
	});
   
	// CLIENTE - MODAL ATUALIZAR
	$("#modal-form-cliente[rel=modalatualizar]").find("#cep").change(function(){
		var cep_code = $(this).val();
		if( cep_code.length <= 0 ) return;
		$.get("http://apps.widenet.com.br/busca-cep/api/cep.json", 
				{ code: cep_code },
				function(result){
					if( result.status!=1 ){		        	   
						// Pega e Exibe o Modal
			       		var modal = $("#modal-form-cliente[rel=modalcep]");
			       		modal.modal();		        	   
			       		return;
					}
					$("#modal-form-cliente[rel=modalatualizar]").find("input#cep").val( result.code );
					$("#modal-form-cliente[rel=modalatualizar]").find("input#endereco").val( result.address );
					$("#modal-form-cliente[rel=modalatualizar]").find("input#bairro").val( result.district );            
					$("#modal-form-cliente[rel=modalatualizar]").find("input#cidade").val( result.city );
					$("#modal-form-cliente[rel=modalatualizar]").find("input#estado").val( result.state );
		});   
	});
   
	
	// FORNECEDOR - MODAL CADASTRAR
	$("#modal-form-fornecedor[rel=modalcadastrar]").find("#cep").change(function(){
		var cep_code = $(this).val();
		if( cep_code.length <= 0 ) return;
		$.get("http://apps.widenet.com.br/busca-cep/api/cep.json", { code: cep_code },
				function(result){
					if( result.status!=1 ){		        	   
						// Pega e Exibe o Modal
		       		   	var modal = $("#modal-form-fornecedor[rel=modalcep]");
		       		   	modal.modal();		        	   
		       		   	return;
					}
					$("#modal-form-fornecedor[rel=modalcadastrar]").find("input#cep").val( result.code );
					$("#modal-form-fornecedor[rel=modalcadastrar]").find("input#endereco").val( result.address );
					$("#modal-form-fornecedor[rel=modalcadastrar]").find("input#bairro").val( result.district );            
					$("#modal-form-fornecedor[rel=modalcadastrar]").find("input#cidade").val( result.city );
					$("#modal-form-fornecedor[rel=modalcadastrar]").find("input#estado").val( result.state );
		});   
	});
	
	// FORNECEDOR - MODAL ATUALIZAR
	$("#modal-form-fornecedor[rel=modalatualizar]").find("#cep").change(function(){
		var cep_code = $(this).val();
		if( cep_code.length <= 0 ) return;
		$.get("http://apps.widenet.com.br/busca-cep/api/cep.json", { code: cep_code },
				function(result){
					if( result.status!=1 ){		        	   
						// Pega e Exibe o Modal
		       		   	var modal = $("#modal-form-fornecedor[rel=modalcep]");
		       		   	modal.modal();		        	   
		       		   	return;
					}
					$("#modal-form-fornecedor[rel=modalatualizar]").find("input#cep").val( result.code );
					$("#modal-form-fornecedor[rel=modalatualizar]").find("input#endereco").val( result.address );
					$("#modal-form-fornecedor[rel=modalatualizar]").find("input#bairro").val( result.district );            
					$("#modal-form-fornecedor[rel=modalatualizar]").find("input#cidade").val( result.city );
					$("#modal-form-fornecedor[rel=modalatualizar]").find("input#estado").val( result.state );
		});   
	});
});