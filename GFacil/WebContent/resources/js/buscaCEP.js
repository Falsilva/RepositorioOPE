$(function(){
	
   $("#cep").change(function(){
	   
	  // Guarda o valor do cep digitado na variável cep_code
      var cep_code = $(this).val();
      if( cep_code.length <= 0 ) return;
      
      // AJAX
      $.get("http://apps.widenet.com.br/busca-cep/api/cep.json", { code: cep_code },
         function(result){
            if( result.status!=1 ){
               alert(result.message || "Houve um erro desconhecido");
               return;
            }
            $("input#cep").val( result.code );
            $("input#endereco").val( result.address );
            $("input#bairro").val( result.district );            
            $("input#cidade").val( result.city );
            $("input#estado").val( result.state );
         });
   });
});

//AJAX - $.get(URL,data,function(data,status,xhr),dataType):
// URL - obrigatório - url a ser requisitada
// data - opcional - envia os dados para o servidor na requisição da URL
// function(data, status, xhr) - opcional - executa a função, caso obteve sucesso na requisição
//    data - contém os dados do resultado (result) da requisição
//	status - contém o status da requisição ("sucess", "notmodified", "error", "timeout", "parsererror")
//	xhr - contém o objeto XMLHttpRequest
// dataType - opcional - especifica o tipo de dado esperado na resposta do servidor. Por padrão, o jQuery apresenta uma sugestão automática:
// 	Possíveis tipos de dados:
//		"xml" - Um documento XML
//		"html" - HTML como plain text
//		"text" - Uma string plain text
//		"script" - Executa o response como JavaScript, e o returna como plain text
//		"json" - Executa o response como JSON, e returna um objeto JavaScript
//		"jsonp" - Carrega num bloco JSON usando JSONP. Adicionará um "?callback=?" para a URL especificar o callback