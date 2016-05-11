$(document).ready(function(){

	// VARIÁVEIS GLOBAIS - necessárias para o armazenamento temporário dos dados originais da edição de registros da tabela de usuários
	var nome_usuario_tmp;
	var nome_tmp;
	var email_tmp;
	var perfil_tmp;
	
	// MODAL PARA CADASTRAR - CLIQUE DO BOTÃO CADASTRAR EXIBE O MODAL
	$("a[role=cadastrar]").click(function(event){
		event.preventDefault();
		
		// Pega o Modal
		var modal = $("#modal-form-pedido[rel=modalcadastrar]");
		
		// Exibe o Modal Form Cadastrar
		modal.modal();		
	});
	
	// MODAL EXCLUIR - CLIQUE DO BOTÃO EXCLUIR DA LINHA DE UM REGISTRO TABELA EXIBE O MODAL
	$("a[role=excluir]").click(function(event){
		event.preventDefault();
		
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
		
		// Exibe o Modal Form Excluir
		modal.modal();		
	});	
	
	// CLIQUE DO BOTÃO EDITAR DA LINHA DE UM REGISTRO DA TABELA
	$("a[role=editar]").click(function(event){
		event.preventDefault();		
		
		nome_usuario_tmp = "";
		nome_tmp = "";
		email_tmp = "";
		perfil_tmp = "";
		
		// Pega a TR
		var tr = $(this).closest("tr");
		
		// Pega as TDs da TR
		var tdNomeUsuario = tr.find("td:first").prop("class", "hidden");
		var tdNome = tdNomeUsuario.next("td");
		var tdEmail = tdNome.next("td");
		var tdPerfil = tdEmail.next("td");
		
		// Pega os Dados das TDs
		var nomeusuario = tdNomeUsuario.text();
		nome_usuario_tmp = tdNomeUsuario.text();
		var nome = tdNome.text();
		nome_tmp = tdNome.text();
		var email = tdEmail.text();
		email_tmp = tdEmail.text();
		var perfil = tdPerfil.text();
		perfil_tmp = tdPerfil.text();
		
		// Troca a Exibição dos Botões de Ações
		var btnEditarEditar = $(this).addClass("hidden");
		var btnEditarExcluir = btnEditarEditar.next("a[role=excluir]").addClass("hidden");
		var btnEditarSalvar = btnEditarExcluir.next("a[role=salvar]").removeClass("hidden");
		var btnEditarCancelar = btnEditarSalvar.next("a[role=cancelar]").removeClass("hidden");			
		
		// Coloca os Inputs nas TDs
		tdNomeUsuario.html("<input type='text' name='nomeusuario' class='hidden'></input>").val(nomeusuario);
		tdNome.html("<input type='text' name='nome'></input>").val(nome);
		tdEmail.html("<input type='text' name='email'></input>").val(email);
		tdPerfil.html("<select name='perfil'></select>"); // Os options são inseridos logo abaixo			
	
		// Coloca os Dados nos Inputs
		tdNomeUsuario.find("input").val(nomeusuario);
		tdNome.find("input").val(nome);
		tdEmail.find("input").val(email);		
		
		// Coloca os Options com os Dados no Select (Campo Perfil)
		var perfis;
		$.ajax({			
			url:"controller",
			type:"post",
			data:{				
				action:"pegaPerfil"
			},
			success:function(resultado){
				// Montando um array de string com o resultado
				perfis = resultado.split(",");								
						
				// Montando o campo Perfil com o array
				var campoPerfil = "";				
				$.each(perfis, function(index, value){
					if(perfil === value) campoPerfil += "<option value='" + value + "' selected>" + value + "</option>";
					else campoPerfil += "<option value='" + value + "'>" + value + "</option>";					
				});
				
				// Adicionando os options no select
				tdPerfil.html("<select name='perfil'>" + campoPerfil + "</select>");				
			}
		});
	});
	
	// CLIQUE DO BOTÃO SALVAR - BOTÃO VISÍVEL NO MODO DE EDIÇÃO DE UM REGISTRO DA TABELA 
	$("a[role=salvar]").click(function(event){
		event.preventDefault();		
		
		// Pega a TR
		var tr = $(this).closest("tr");
		
		// Pega as TDs da TR
		var tdNomeUsuario = tr.find("td:first").prop("class", "hidden");
		var tdNome = tdNomeUsuario.next("td");
		var tdEmail = tdNome.next("td");
		var tdPerfil = tdEmail.next("td");
		//var tdAcoes = tdPerfil.next("td");
		
		// Pega os Dados dos Inputs das TDs
		var nomeusuario = tdNomeUsuario.find("input").val();
		var nome = tdNome.find("input").val();
		var email = tdEmail.find("input").val();
		var perfil = tdPerfil.find("option:selected").val();
		
		// AJAX
		$.ajax({
			url:"controller",
			type:"post",
			data:{
				nome:nome,
				email:email,
				emailParaBusca:email_tmp,
				nomeusuario:nomeusuario,				
				perfil:perfil,
				action:"atualizarUsuario",
				tarefa:"atualizar"
			},
			success:function(resultado){
				
				// Verificar se a resposta
				if(resultado === "Atualizado"){
					// Troca a Exibição dos Botões de Ações
					var btnEditarCancelar = $("a[role=cancelar]").addClass("hidden");
					var btnEditarSalvar = btnEditarCancelar.prev("a[role=salvar]").addClass("hidden");
					var btnEditarExcluir = btnEditarSalvar.prev("a[role=excluir]").removeClass("hidden");
					var btnEditarEditar = btnEditarExcluir.prev("a[role=editar]").removeClass("hidden");
							
					// Remove os Inputs das TDs e Coloca os Dados
					tdNomeUsuario.html("").text(nomeusuario);
					tdNome.html("").text(nome);
					tdEmail.html("").text(email);
					tdPerfil.html("").text(perfil);
				}else{
					alert("NÃO ATUALIZOU");										
				}	
			}
		});		
	});
	
	// CLIQUE DO BOTÃO CANCELAR - BOTÃO VISÍVEL NO MODO DE EDIÇÃO DE UM REGISTRO DA TABELA
	$("a[role=cancelar]").click(function(event){
		event.preventDefault();		
	
		// Pega a TR
		var tr = $(this).closest("tr");
		
		// Pega as TDs da TR
		var tdNomeUsuario = tr.find("td:first").prop("class", "hidden");
		var tdNome = tdNomeUsuario.next("td");
		var tdEmail = tdNome.next("td");
		var tdPerfil = tdEmail.next("td");
		var tdAcoes = tdPerfil.next("td");
		
		// Pega os Dados das Variáveis Globais
		var nomeusuario = nome_usuario_tmp;
		var nome = nome_tmp;
		var email = email_tmp;
		var perfil = perfil_tmp;
		
		// Troca a Exibição dos Botões de Ações
		var btnEditarCancelar = $(this).addClass("hidden");
		var btnEditarSalvar = btnEditarCancelar.prev("a[role=salvar]").addClass("hidden");
		var btnEditarExcluir = btnEditarSalvar.prev("a[role=excluir]").removeClass("hidden");
		var btnEditarEditar = btnEditarExcluir.prev("a[role=editar]").removeClass("hidden");
				
		// Remove os Inputs das TDs e Coloca os Dados
		tdNomeUsuario.html("").text(nomeusuario);
		tdNome.html("").text(nome);
		tdEmail.html("").text(email);
		tdPerfil.html("").text(perfil);		
	});
	
	// RECARREGA A PÁGINA AO FECHAR O MODAL CADASTRAR
	$("#modal-form[rel=modalcadastrar]").on("hidden.bs.modal", function (){
		console.log("Modal Cadastrar Encerrado");
		location.reload();
	});
	
	// RECARREGA A PÁGINA AO FECHAR O MODAL EXCLUIR
	$("#modal-form[rel=modalexcluir]").on("hidden.bs.modal", function (){
		console.log("Modal Excluir Encerrado");
		location.reload();
	});
});