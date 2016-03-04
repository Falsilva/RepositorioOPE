<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet" type="text/css" href="resources/css/estilos.css" />
<title>MDJ Papeis - Fornecedores</title>
</head>
<body>
	
	<div class="nav">
	
		<!-- JSTL - Exibe o nome do usuário -->
		<h3 id="olaUser">Olá, ${usuarioLogado.nome}</h3>
		
		<form id="formLogout" action="controller?action=logout" method="POST">		
			<input id="btnLogout" type="submit" value="Sair" />
		</form>
	</div>
	
	<div class="container">
		
		<h1>Fornecedores</h1>		
		
		<c:choose>
		
			<c:when test="${not empty param.tarefa}">
				<c:choose>
				
					<c:when test="${param.tarefa == 'cadastrar'}">
					
						<h4>Cadastrar Fornecedor</h4>
						<a href="controller?action=listarFornecedores">Voltar</a>
		
						<br /><br />
						
						<c:choose>
							<c:when test="${empty forn}">
							
								<!-- FORMULÁRIO CADASTRAR FORNECEDOR -->						
								<form id="formCadastrarFornecedor" action="controller?action=cadastrarFornecedor&tarefa=cadastrar" method="POST">
									<fieldset>
										<legend>Preencha o formulário</legend>
										
										<label>Tipo:</label>
										<div class="box-radio">
											<input type="radio" name="tipoFornecedor" id="tipoFornecedor1" value="Empresa" checked /><label for="tipoFornecedor1" class="lblRadio">Empresa</label>
											<input type="radio" name="tipoFornecedor" id="tipoFornecedor2" value="Catador" /><label for="tipoFornecedor2" class="lblRadio">Catador</label>
										</div>
										
										<label for="empresa">Empresa:</label>			
										<input type="text" name="empresa" id="empresa" placeholder="Empresa" />
										
										<label for="contato">Contato:</label>			
										<input type="text" name="contato" id="contato" placeholder="Nome do contato" /> <span>*</span>
										
										<label for="telefone">Telefone:</label>			
										<input type="text" name="telefone" id="telefone" placeholder="(99)9.9999-9999" />
										
										<label for="email">Email:</label>			
										<input type="text" name="email" id="email" placeholder="Email" />
										
										
										<label for="cep">CEP:</label>
										<input type="text" name="cep" id="cep" placeholder="00000-000" />
											
										<label for="endereco">Logradouro:</label>
										<input type="text" name="endereco" id="endereco" placeholder="Rua/Avenida" />
											
										<label for="numero">Número:</label>
										<input type="text" name="numero" id="numero" placeholder="Número" />
											
										<label for="complemento">Complemento:</label>
										<input type="text" name="complemento" id="complemento" placeholder="Complemento" />
											
										<label for="bairro">Bairro:</label>
										<input type="text" name="bairro" id="bairro" placeholder="Bairro" />
											
										<label for="cidade">Cidade:</label>
										<input type="text" name="cidade" id="cidade" placeholder="Cidade" />
											
										<label for="estado">Estado:</label>
										<input type="text" name="estado" id="estado" placeholder="Estado" />
											
										<label for="cnpj">CNPJ:</label>
										<input type="text" name="cnpj" id="cnpj" placeholder="00.000.000/0001-00" />
																						
										<label for="inscEstadual">Insc. Estadual:</label>
										<input type="text" name="inscEstadual" id="inscEstadual" placeholder="Insc. Estadual" />
																					
										<input class="btnRight" type="submit" value="Cadastrar" />
									</fieldset>
								</form>
								
								<span class="spanAsterisco">(*) Campo obrigatório</span>					
								
								<c:if test="${not empty mensagem}">			
									<h3 class="box-1-3">${mensagem}</h3>
								</c:if>
							</c:when>
							
							<c:otherwise>
								<!-- FORMULÁRIO CADASTRAR FORNECEDOR -->						
								<form id="formCadastrarFornecedor" action="controller?action=cadastrarFornecedor&tarefa=cadastrar" method="POST">
									<fieldset>
										<legend>Preencha o formulário</legend>
										
										<label>Tipo:</label>
										<div class="box-radio">
											<input type="radio" name="tipoFornecedor1" id="tipoFornecedor1" value="Empresa" <c:if test="${forn.tipo.tipo eq 'Empresa'}">checked</c:if>><label for="tipoFornecedor1" class="lblRadio">Empresa</label>
											<input type="radio" name="tipoFornecedor2" id="tipoFornecedor2" value="Catador" <c:if test="${forn.tipo.tipo eq 'Catador'}">checked</c:if>><label for="tipoFornecedor2" class="lblRadio">Catador</label>
										</div>
																			
										<label for="empresa">Empresa:</label>			
										<input type="text" name="empresa" id="empresa" value="${forn.empresa}" />
										
										<label for="contato">Contato:</label>			
										<input type="text" name="contato" id="contato" value="${forn.contato}" /> <span>*</span>
										
										<label for="telefone">Telefone:</label>			
										<input type="text" name="telefone" id="telefone" value="${forn.telefone}" />
										
										<label for="email">Email:</label>			
										<input type="text" name="email" id="email" value="${forn.email}" />
										
										<label for="cep">CEP:</label>
										<input type="text" name="cep" id="cep" value="${forn.cep}" />
											
										<label for="endereco">Logradouro:</label>
										<input type="text" name="endereco" id="endereco" value="${forn.endereco}" />
											
										<label for="numero">Número:</label>
										<input type="text" name="numero" id="numero" value="${forn.numero}" />
											
										<label for="complemento">Complemento:</label>
										<input type="text" name="complemento" id="complemento" value="${forn.complemento}" />
											
										<label for="bairro">Bairro:</label>
										<input type="text" name="bairro" id="bairro" value="${forn.bairro}" />
											
										<label for="cidade">Cidade:</label>
										<input type="text" name="cidade" id="cidade" value="${forn.cidade}" />
										
										<label for="estado">Estado:</label>
										<input type="text" name="estado" id="estado" value="${forn.estado}" />
											
										<label for="cnpj">CNPJ:</label>
										<input type="text" name="cnpj" id="cnpj" value="${forn.cnpj}" />
											
										<label for="inscEstadual">Insc. Estadual:</label>
										<input type="text" name="inscEstadual" id="inscEstadual" value="${forn.inscEstadual}" />
										
										<input class="btnRight" type="submit" value="Cadastrar" />
									</fieldset>									
								</form>								
								
								<span class="spanAsterisco">(*) Campo obrigatório</span>
								
								<c:if test="${not empty mensagem}">			
									<h3 class="box-1-3">${mensagem}</h3>
								</c:if>						
							</c:otherwise>
						</c:choose>
					</c:when>
					
					<c:when test="${param.tarefa == 'atualizar'}">
											
						<h4>Atualizar Fornecedor</h4>
						<a href="controller?action=listarFornecedores">Voltar</a>
		
						<br /><br />
						
						<c:choose>
							<c:when test="${empty forn}">
								
								<!-- FORMULÁRIO PESQUISAR PARA ATUALIZAR FORNECEDOR -->
								<form id="formPesquisar" action="controller?action=pesquisarFornecedor&tarefa=atualizar" method="POST">
									<fieldset>
										<legend>Informe o código do Fornecedor</legend>
										<label for="codigo">Código:</label>			
										<input type="text" name="codigo" id="codigo" placeholder="Código do Fornecedor" />
										<input type="submit" value="Pesquisar" />
									</fieldset>
								</form>
								
								<c:if test="${not empty mensagem}">
									<h3 class="box-1-3">${mensagem}</h3>
								</c:if>
							</c:when>
							
							<c:when test="${not empty forn}">
								
								<!-- FORMULÁRIO ATUALIZAR FORNECEDOR -->
								<form id="formAtualizarFornecedor" action="controller?action=atualizarFornecedor&tarefa=atualizar" method="POST">
									<fieldset>
										<legend>Edite os campos</legend>
										
										<label for="codigo">Código:</label>			
										<input type="text" id="codigo" value="${forn.codigo}" disabled />
										<input type="hidden" name="codigo" id="codigo" value="${forn.codigo}" />
										
										<label>Tipo:</label>
										<div class="box-radio">
											<input type="radio" name="tipoFornecedor" id="tipoFornecedor1" value="Empresa" <c:if test="${forn.tipo.tipo eq 'Empresa'}">checked</c:if>><label for="tipoFornecedor1" class="lblRadio">Empresa</label>
											<input type="radio" name="tipoFornecedor" id="tipoFornecedor2" value="Catador" <c:if test="${forn.tipo.tipo eq 'Catador'}">checked</c:if>><label for="tipoFornecedor2" class="lblRadio">Catador</label>
										</div>
										
										<label for="empresa">Empresa:</label>			
										<input type="text" name="empresa" id="empresa" value="${forn.empresa}" />
										
										<label for="contato">Contato:</label>			
										<input type="text" name="contato" id="contato" value="${forn.contato}" /> <span>*</span>
										
										<label for="telefone">Telefone:</label>			
										<input type="text" name="telefone" id="telefone" value="${forn.telefone}" />
										
										<label for="email">Email:</label>			
										<input type="text" name="email" id="email" value="${forn.email}" />
										
										<label for="cep">CEP:</label>
										<input type="text" name="cep" id="cep" value="${fornEndereco[6]}" />
											
										<label for="endereco">Logradouro:</label>
										<input type="text" name="endereco" id="endereco" value="${fornEndereco[0]}" />
											
										<label for="numero">Número:</label>
										<input type="text" name="numero" id="numero" value="${fornEndereco[1]}" />
											
										<label for="complemento">Complemento:</label>
										<input type="text" name="complemento" id="complemento" value="${fornEndereco[2]}" />
											
										<label for="bairro">Bairro:</label>
										<input type="text" name="bairro" id="bairro" value="${fornEndereco[3]}" />
											
										<label for="cidade">Cidade:</label>
										<input type="text" name="cidade" id="cidade" value="${fornEndereco[4]}" />
											
										<label for="estado">Estado:</label>
										<input type="text" name="estado" id="estado" value="${fornEndereco[5]}" />
											
										<label for="cnpj">CNPJ:</label>
										<input type="text" name="cnpj" id="cnpj" value="${forn.cnpj}" />
										
										<label for="inscEstadual">Insc. Estadual:</label>
										<input type="text" name="inscEstadual" id="inscEstadual" value="${forn.inscEstadual}" />
										
										<input class="btnRight" type="submit" value="Atualizar" />
									</fieldset>															
								</form>
								
								<span class="spanAsterisco">(*) Campo obrigatório</span>
								
								<c:if test="${not empty mensagem}">
									<h3 class="box-1-3">${mensagem}</h3>
								</c:if>
								
							</c:when>
						</c:choose>
					</c:when>
					
					<c:otherwise>
					
						<h4>Excluir Fornecedor</h4>
						<a href="controller?action=listarFornecedores">Voltar</a>
		
						<br /><br />
					
						<c:choose>
						
							<c:when test="${empty cli}">
							
								<!-- FORMULÁRIO PESQUISAR PARA EXCLUIR FORNECEDOR -->
								<form id="formPesquisar" action="controller?action=pesquisarFornecedor&tarefa=excluir" method="POST">
									<fieldset>
										<legend>Informe o código do fornecedor</legend>
										<label for="codigo">Código:</label>			
										<input type="text" name="codigo" id="codigo" placeholder="Código do Fornecedor" />
										<input type="submit" value="Excluir" />
									</fieldset>
								</form>
																
								<c:if test="${not empty mensagem}">
									<h3 class="box-1-3">${mensagem}</h3>
								</c:if>
								
							</c:when>
							
							<c:when test="${not empty forn}">
							
								<!-- FORMULÁRIO EXCLUIR FORNECEDOR -->
								<form id="formAtualizarFornecedor" action="controller?action=excluirFornecedor&tarefa=excluir" method="POST">
									<fieldset>
										<legend>Fornecedor a ser excluído</legend>
										
										<label for="codigo">Código:</label>			
										<input type="text" id="codigo" value="${forn.codigo}" disabled />
										<input type="hidden" name="codigo" id="codigo" value="${forn.codigo}" />
										
										<label for="tipoFornecedor">Tipo:</label>
										<input type="text" name="tipoFornecedor" value="${forn.tipo.tipo}" disabled />
																				
										<label for="empresa">Empresa:</label>			
										<input type="text" name="empresa" id="empresa" value="${forn.empresa}" disabled />
										
										<label for="contato">Contato:</label>			
										<input type="text" name="contato" id="contato" value="${forn.contato}" disabled />
										
										<label for="telefone">Telefone:</label>			
										<input type="text" name="telefone" id="telefone" value="${forn.telefone}" disabled />
										
										<label for="email">Email:</label>			
										<input type="text" name="email" id="email" value="${forn.email}" disabled />
										
										<label for="cep">CEP:</label>
										<input type="text" name="cep" id="cep" value="${fornEndereco[6]}" disabled />
											
										<label for="endereco">Logradouro:</label>
										<input type="text" name="endereco" id="endereco" value="${fornEndereco[0]}" disabled />
											
										<label for="numero">Número:</label>
										<input type="text" name="numero" id="numero" value="${fornEndereco[1]}" disabled />
											
										<label for="complemento">Complemento:</label>
										<input type="text" name="complemento" id="complemento" value="${fornEndereco[2]}" disabled />
											
										<label for="bairro">Bairro:</label>
										<input type="text" name="bairro" id="bairro" value="${fornEndereco[3]}" disabled />
											
										<label for="cidade">Cidade:</label>
										<input type="text" name="cidade" id="cidade" value="${fornEndereco[4]}" disabled />
											
										<label for="estado">Estado:</label>
										<input type="text" name="estado" id="estado" value="${fornEndereco[5]}" disabled />
											
										<label for="cnpj">CNPJ:</label>
										<input type="text" name="cnpj" id="cnpj" value="${forn.cnpj}" disabled />
										
										<label for="inscEstadual">Insc. Estadual:</label>
										<input type="text" name="inscEstadual" id="inscEstadual" value="${forn.inscEstadual}" disabled />
										
									</fieldset>
									
									<label class="lblLinha">Confirma a exclusão?</label>	
									
									<div class="divSimNao">
										<input type="submit" value="Sim" />&nbsp;
										<input type="submit" formaction="controller?action=formFornecedor&tarefa=excluir" value="Não" />
									</div>
														
								</form>
								
							</c:when>
						</c:choose>
						
					</c:otherwise>
				</c:choose>
			</c:when>
			
			<c:otherwise>								
				
				<c:choose>
				
					<c:when test="${not empty fornecedores}">
						<a href="controller?action=listarFornecedores">Voltar</a>
		
						<br /><br />
						
						<h4>Resultado:</h4>
						
						<div class="box-1-3">
							<div class="col-1-title">EMPRESA</div>
							<div class="col-1-title">CONTATO</div>
							<div class="col-1-title">TELEFONE</div>
						</div>					
						
						<div class="box-1-3">
							<div class="col-1">${forn.empresa}</div>
							<div class="col-1">${forn.contato}</div>
							<div class="col-1">${forn.telefone}</div>
						</div>				
						
					</c:when>
					
					<c:otherwise>
						<a href="controller?action=listarFornecedores">Voltar</a>
		
						<br /><br />
						
						<h4>Resultado:</h4>
						
						<c:if test="${not empty mensagem}">			
							<h3 class="box-1-3">${mensagem}</h3>
						</c:if>
					</c:otherwise>					
				</c:choose>	
				
			</c:otherwise>
		</c:choose>
	</div>
	
	<script type="text/javascript" src="resources/js/jquery.js"></script>
	<script type="text/javascript" src="resources/js/buscaCEP.js"></script>

</body>
</html>