$(document).ready(function(){
	
	// VARIÁVEIS GLOBAIS - necessárias para o armazenamento temporário dos dados originais da edição de registros da tabela de preços
	var codigo_tmp;
	var produto_tmp;
	var precoCompra_tmp;
	var precoVenda_tmp;
	
	// MODAL PARA CADASTRAR - CLIQUE DO BOTÃO CADASTRAR EXIBE O MODAL
	$("a[role=cadastrar]").click(function(event){
		event.preventDefault();
		
		// Pega o Modal
		var modal = $("#modal-form-precos[rel=modalcadastrar]");
		
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
		var tdProduto = tdCodigo.next("td");
		var tdPrecoCompra = tdProduto.next("td");
		var tdPrecoVenda = tdPrecoCompra.next("td");		
		
		// Pega os Dados das TDs		
		var codigo = tdCodigo.text();
		var produto = tdProduto.text();
		var precoCompra = tdPrecoCompra.text();
		var precoVenda = tdPrecoVenda.text();
		
		// Pega o Modal
		var modal = $("#modal-form-precos[rel=modalexcluir]");
		
		// Coloca os Dados no Modal e de Forma Somente Leitura
		modal.find("#codigo").val(codigo);
		modal.find("#produto").prop("readonly", true).val(produto);
		modal.find("#precoCompra").prop("readonly", true).val(precoCompra);
		modal.find("#precoVenda").prop("readonly", true).val(precoVenda);
		
		// Exibe o Modal Form Excluir
		modal.modal();		
	});
	
	// CLIQUE DO BOTÃO EDITAR DA LINHA DE UM REGISTRO DA TABELA
	$("a[role=editar]").click(function(event){
		event.preventDefault();
		
		// Limpando as varíáveis utilizadas para preservação de dados
		codigo_tmp = "";
		produto_tmp = "";
		precoCompra_tmp = "";
		precoVenda_tmp = "";
		
		// Pega a TR
		var tr = $(this).closest("tr");
		
		// Pega as TDs da TR
		var tdCodigo = tr.find("td:first").prop("class", "hidden");
		var tdProduto = tdCodigo.next("td");
		var tdPrecoCompra = tdProduto.next("td");
		var tdPrecoVenda = tdPrecoCompra.next("td");
				
		// Pega os Dados das TDs		
		var codigo = tdCodigo.text();
		var produto = tdProduto.text();
		var precoCompra = tdPrecoCompra.text();
		var precoVenda = tdPrecoVenda.text();		
		
		// Guarda os Dados para a Recuperação em Caso de Cancelar a Edição
		codigo_tmp = codigo;
		produto_tmp = produto;
		precoCompra_tmp = precoCompra;
		precoVenda_tmp = precoVenda;
		
		// Pega o Modal
		var modal = $("#modal-form-precos[rel=modalatualizar]");
		
		// Coloca os Dados no Modal
		modal.find("#codigo").val(codigo);
		modal.find("#produto").val(produto);
		modal.find("#precoCompra").val(precoCompra);
		modal.find("#precoVenda").val(precoVenda);
						
		// Exibe o Modal Form Atualizar
		modal.modal();
	});
		
	// RECARREGA A PÁGINA AO FECHAR O MODAL CADASTRAR
	$("#modal-form-precos[rel=modalcadastrar]").on("hidden.bs.modal", function (){
		console.log("Modal Cadastrar Encerrado");
		location.reload();
	});
	
	// RECARREGA A PÁGINA AO FECHAR O MODAL EXCLUIR
	$("#modal-form-precos[rel=modalexcluir]").on("hidden.bs.modal", function (){
		console.log("Modal Excluir Encerrado");
		location.reload();
	});
	
	// RECARREGA A PÁGINA AO FECHAR O MODAL ATUALIZAR
	$("#modal-form-precos[rel=modalatualizar]").on("hidden.bs.modal", function (){
		console.log("Modal Atualizar Encerrado");
		location.reload();
	});
});