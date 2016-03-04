var alternaFormulario = function(event){
	
	var tipoFornecedor = $("input:radio");	
	var empresa = $("#tipoFornecedor1");
	var catador = $("#tipoFornecedor2");
	var formCadastrar = $("#formCadastrarFornecedor");
	var labels = formCadastrar.find("label");
	var inputs = formCadastrar.find("input");
	
	tipoFornecedor.change(function(){
		if(empresa.is(":checked")){ 
			$(montaFormEmpresa(labels, inputs));
		}else{			
			$(montaFormCatador(labels, inputs));			
		}
	});	
};


// Monta Formulário de Catador
var montaFormCatador = function(labels, inputs){
	
	// Percorre as labels
	labels.each(function(){
		
		$(this).hide();
		
		var atributo = $(this).attr("for");
		
		switch(atributo){
			case "tipoFornecedores":
				$(this).show();
				break;
			case "tipoFornecedor1":
				$(this).show();
				break;
			case "tipoFornecedor2":
				$(this).show();
				break;
			case "contato":
				$(this).text("Nome:");
				$(this).show();
				break;
			default:
				$(this).hide();
				break;						
		}
	});
	
	// Percorre os inputs
	inputs.each(function(){
		
		$(this).hide();
		
		var atributo = $(this).attr("id");
		
		switch(atributo){					
			case "tipoFornecedor1":
				$(this).show();
				break;
			case "tipoFornecedor2":
				$(this).show();
				break;
			case "empresa":
				$(this).val("(CATADOR DE RUA)");				
				break;
			case "contato":
				$(this).attr("placeholder", "Nome do catador");
				$(this).show();
				break;
			case "botao":
				$(this).show();
				break;
			default:
				$(this).hide();
				break;						
		}											
	});	
}

//Monta Formulário de Empresa
var montaFormEmpresa = function(labels, inputs){
	
	// Percorre as labels
	labels.each(function(){
		
		$(this).show();
		
		var atributo = $(this).attr("for");
		
		switch(atributo){			
			case "contato":
				$(this).text("Contato:");
				break;
			default:
				break;						
		}
	});
	
	// Percorre os inputs
	inputs.each(function(){
		
		$(this).show();
		
		var atributo = $(this).attr("id");
		
		switch(atributo){
			case "contato":
				$(this).attr("placeholder", "Nome do contato");
				break;
			case "botao":
				$(this).show();
				break;
			default:
				break;						
		}											
	});	
}


$(alternaFormulario);
