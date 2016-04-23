<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="br.com.mdjpapeis.entity.Fornecedor" %>
<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<meta name="generator" content="Bootply" />
	<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
	<!-- <link rel="stylesheet" type="text/css" href="resources/css/estilos.css" /> -->
	<link rel="stylesheet" type="text/css" href="resources/css/bootstrap.min.css" />
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
								<form class="form-horizontal" id="formCadastrarFornecedor" action="controller?action=cadastrarFornecedor&tarefa=cadastrar" method="POST">
									<fieldset>
										<legend>Preencha o formulário</legend>
										
										<div class="form-group">
											<label for="tipoFornecedores" class="col-sm-2 control-label">Tipo:</label>
    										<div class="col-sm-10">    											
												<div class="radio">										
													<label for="tipoFornecedor1" class="lblRadio">
														<input type="radio" name="tipoFornecedor" id="tipoFornecedor1" value="EMPRESA" checked />Empresa
													</label>
												</div>
												<div class="radio">
													<label for="tipoFornecedor2" class="lblRadio">
														<input type="radio" name="tipoFornecedor" id="tipoFornecedor2" value="CATADOR" />Catador
													</label>
												</div>
											</div>
										</div>
										
										<div class="form-group">
											<label for="empresa" class="col-sm-2 control-label">Empresa:</label>
											<div class="col-sm-10">			
												<input type="text" name="empresa" id="empresa" placeholder="Empresa" />
											</div>
										</div>
										
										<div class="form-group">
											<label for="contato" class="col-sm-2 control-label">Contato:</label>
											<div class="col-sm-10">			
												<input type="text" name="contato" id="contato" placeholder="Nome do contato" /> <span>*</span>
											</div>
										</div>
										
										<div class="form-group">
											<label for="telefone" class="col-sm-2 control-label">Telefone:</label>
											<div class="col-sm-10">			
												<input type="text" name="telefone" id="telefone" placeholder="(99)9.9999-9999" />
											</div>
										</div>
										
										<div class="form-group">
											<label for="email" class="col-sm-2 control-label">Email:</label>
											<div class="col-sm-10">			
												<input type="text" name="email" id="email" placeholder="Email" />
											</div>
										</div>
										
										<div class="form-group">										
											<label for="cep" class="col-sm-2 control-label">CEP:</label>
											<div class="col-sm-10">
												<input type="text" name="cep" id="cep" placeholder="00000-000" />
											</div>
										</div>
										
										<div class="form-group">
											<label for="endereco" class="col-sm-2 control-label">Logradouro:</label>
											<div class="col-sm-10">
												<input type="text" name="endereco" id="endereco" placeholder="Rua/Avenida" />
											</div>
										</div>
										
										<div class="form-group">
											<label for="numero" class="col-sm-2 control-label">Número:</label>
											<div class="col-sm-10">
												<input type="text" name="numero" id="numero" placeholder="Número" />
											</div>
										</div>
										
										<div class="form-group">
											<label for="complemento" class="col-sm-2 control-label">Complemento:</label>
											<div class="col-sm-10">
												<input type="text" name="complemento" id="complemento" placeholder="Complemento" />
											</div>
										</div>
										
										<div class="form-group">
											<label for="bairro" class="col-sm-2 control-label">Bairro:</label>
											<div class="col-sm-10">
												<input type="text" name="bairro" id="bairro" placeholder="Bairro" />
											</div>
										</div>
										
										<div class="form-group">
											<label for="cidade" class="col-sm-2 control-label">Cidade:</label>
											<div class="col-sm-10">
												<input type="text" name="cidade" id="cidade" placeholder="Cidade" />
											</div>
										</div>
										
										<div class="form-group">
											<label for="estado" class="col-sm-2 control-label">Estado:</label>
											<div class="col-sm-10">
												<input type="text" name="estado" id="estado" placeholder="Estado" />
											</div>
										</div>
										
										<div class="form-group">
											<label for="cnpj" class="col-sm-2 control-label">CNPJ:</label>
											<div class="col-sm-10">
												<input type="text" name="cnpj" id="cnpj" placeholder="00.000.000/0001-00" />
											</div>
										</div>
										
										<div class="form-group">											
											<label for="inscEstadual" class="col-sm-2 control-label">Insc. Estadual:</label>
											<div class="col-sm-10">
												<input type="text" name="inscEstadual" id="inscEstadual" placeholder="Insc. Estadual" />
											</div>
										</div>
										
										<div class="form-group">
											<div class="col-sm-offset-2 col-sm-10">										
												<input class="btn btn-success" id="botao" type="submit" value="Cadastrar" />
											</div>
										</div>
									</fieldset>
								</form>
								
								<span class="spanAsterisco">(*) Campo obrigatório</span>					
								
								<c:if test="${not empty mensagem}">			
									<h3 class="box-1-3">${mensagem}</h3>
								</c:if>
							</c:when>
							
							<c:otherwise>
								<!-- FORMULÁRIO CADASTRAR FORNECEDOR -->						
								<form class="form-horizontal" id="formCadastrarFornecedor" action="controller?action=cadastrarFornecedor&tarefa=cadastrar" method="POST">
									<fieldset>
										<legend>Preencha o formulário</legend>
										
										<div class="form-group">
											<label for="tipoFornecedores" class="col-sm-2 control-label">Tipo:</label>
    										<div class="col-sm-10">
												<div class="radio">										
													<label for="tipoFornecedor1" class="lblRadio">
														<input type="radio" name="tipoFornecedor" id="tipoFornecedor1" value="EMPRESA" <c:if test="${forn.tipo eq 'EMPRESA'}">checked</c:if> />Empresa
													</label>
												</div>
												<div class="radio">
													<label for="tipoFornecedor2" class="lblRadio">
														<input type="radio" name="tipoFornecedor" id="tipoFornecedor2" value="CATADOR" <c:if test="${forn.tipo eq 'CATADOR'}">checked</c:if> />Catador
													</label>
												</div>
											</div>
										</div>
										
										
										<div class="form-group">											
											<label for="empresa" class="col-sm-2 control-label">Empresa:</label>
											<div class="col-sm-10">			
												<input type="text" name="empresa" id="empresa" value="${forn.empresa}" />
											</div>
										</div>
										
										<div class="form-group">											
											<label for="contato" class="col-sm-2 control-label">Contato:</label>
											<div class="col-sm-10">			
												<input type="text" name="contato" id="contato" value="${forn.contato}" /> <span>*</span>
											</div>
										</div>
										
										<div class="form-group">											
											<label for="telefone" class="col-sm-2 control-label">Telefone:</label>
											<div class="col-sm-10">			
												<input type="text" name="telefone" id="telefone" value="${forn.telefone}" />
											</div>
										</div>
										
										<div class="form-group">											
											<label for="email" class="col-sm-2 control-label">Email:</label>
											<div class="col-sm-10">		
												<input type="text" name="email" id="email" value="${forn.email}" />
											</div>
										</div>
										
										<div class="form-group">											
											<label for="cep" class="col-sm-2 control-label">CEP:</label>
											<div class="col-sm-10">
												<input type="text" name="cep" id="cep" value="${forn.cep}" />
											</div>
										</div>
										
										<div class="form-group">											
											<label for="endereco" class="col-sm-2 control-label">Logradouro:</label>
											<div class="col-sm-10">
												<input type="text" name="endereco" id="endereco" value="${forn.endereco}" />
											</div>
										</div>
										
										<div class="form-group">											
											<label for="numero" class="col-sm-2 control-label">Número:</label>
											<div class="col-sm-10">
												<input type="text" name="numero" id="numero" value="${forn.numero}" />
											</div>
										</div>
										
										<div class="form-group">											
											<label for="complemento" class="col-sm-2 control-label">Complemento:</label>
											<div class="col-sm-10">
												<input type="text" name="complemento" id="complemento" value="${forn.complemento}" />
											</div>
										</div>
										
										<div class="form-group">											
											<label for="bairro" class="col-sm-2 control-label">Bairro:</label>
											<div class="col-sm-10">
												<input type="text" name="bairro" id="bairro" value="${forn.bairro}" />
											</div>
										</div>
										
										<div class="form-group">											
											<label for="cidade" class="col-sm-2 control-label">Cidade:</label>
											<div class="col-sm-10">
												<input type="text" name="cidade" id="cidade" value="${forn.cidade}" />
											</div>
										</div>
										
										<div class="form-group">											
											<label for="estado" class="col-sm-2 control-label">Estado:</label>
											<div class="col-sm-10">
												<input type="text" name="estado" id="estado" value="${forn.estado}" />
											</div>
										</div>
										
										<div class="form-group">											
											<label for="cnpj" class="col-sm-2 control-label">CNPJ:</label>
											<div class="col-sm-10">
												<input type="text" name="cnpj" id="cnpj" value="${forn.cnpj}" />
											</div>
										</div>
										
										<div class="form-group">											
											<label for="inscEstadual" class="col-sm-2 control-label">Insc. Estadual:</label>
											<div class="col-sm-10">
												<input type="text" name="inscEstadual" id="inscEstadual" value="${forn.inscEstadual}" />
											</div>
										</div>
										
										<div class="form-group">
											<div class="col-sm-offset-2 col-sm-10">										
												<input class="btn btn-success" id="botao" type="submit" value="Cadastrar" />
											</div>
										</div>
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
								<form class="form-horizontal" id="formAtualizarFornecedor" action="controller?action=atualizarFornecedor&tarefa=atualizar" method="POST">
									<fieldset>
										<legend>Edite os campos</legend>
										
										<div class="form-group">
											<label for="codigo" class="col-sm-2 control-label">Código:</label>
											<div class="col-sm-10">
												<input type="text" id="codigo" value="${forn.codigo}" disabled />
												<input type="hidden" name="codigo" id="codigo" value="${forn.codigo}" />
											</div>												
										</div>
										
										<div class="form-group">
											<label for="tipoFornecedores" class="col-sm-2 control-label">Tipo:</label>
    										<div class="col-sm-10">    										
												<div class="radio">										
													<label for="tipoFornecedor1" class="lblRadio">
														<input type="radio" name="tipoFornecedor" id="tipoFornecedor1" value="Empresa" <c:if test="${forn.tipo eq 'EMPRESA'}">checked</c:if> />Empresa
													</label>
												</div>
												<div class="radio">
													<label for="tipoFornecedor2" class="lblRadio">
														<input type="radio" name="tipoFornecedor" id="tipoFornecedor2" value="Catador" <c:if test="${forn.tipo eq 'CATADOR'}">checked</c:if> />Catador
													</label>
												</div>
											</div>
										</div>										
										
										<div class="form-group">											
											<label for="empresa" class="col-sm-2 control-label">Empresa:</label>
											<div class="col-sm-10">			
												<input type="text" name="empresa" id="empresa" value="${forn.empresa}" />
											</div>
										</div>
										
										<div class="form-group">											
											<label for="contato" class="col-sm-2 control-label">Contato:</label>
											<div class="col-sm-10">			
												<input type="text" name="contato" id="contato" value="${forn.contato}" /> <span>*</span>
											</div>
										</div>
										
										<div class="form-group">											
											<label for="telefone" class="col-sm-2 control-label">Telefone:</label>
											<div class="col-sm-10">			
												<input type="text" name="telefone" id="telefone" value="${forn.telefone}" />
											</div>
										</div>
										
										<div class="form-group">											
											<label for="email" class="col-sm-2 control-label">Email:</label>
											<div class="col-sm-10">		
												<input type="text" name="email" id="email" value="${forn.email}" />
											</div>
										</div>
										
										<div class="form-group">											
											<label for="cep" class="col-sm-2 control-label">CEP:</label>
											<div class="col-sm-10">
												<input type="text" name="cep" id="cep" value="${fornEndereco[6]}" />
											</div>
										</div>
										
										<div class="form-group">											
											<label for="endereco" class="col-sm-2 control-label">Logradouro:</label>
											<div class="col-sm-10">
												<input type="text" name="endereco" id="endereco" value="${fornEndereco[0]}" />
											</div>
										</div>
										
										<div class="form-group">											
											<label for="numero" class="col-sm-2 control-label">Número:</label>
											<div class="col-sm-10">
												<input type="text" name="numero" id="numero" value="${fornEndereco[1]}" />
											</div>
										</div>
										
										<div class="form-group">											
											<label for="complemento" class="col-sm-2 control-label">Complemento:</label>
											<div class="col-sm-10">
												<input type="text" name="complemento" id="complemento" value="${fornEndereco[2]}" />
											</div>
										</div>
										
										<div class="form-group">											
											<label for="bairro" class="col-sm-2 control-label">Bairro:</label>
											<div class="col-sm-10">
												<input type="text" name="bairro" id="bairro" value="${fornEndereco[3]}" />
											</div>
										</div>
										
										<div class="form-group">											
											<label for="cidade" class="col-sm-2 control-label">Cidade:</label>
											<div class="col-sm-10">
												<input type="text" name="cidade" id="cidade" value="${fornEndereco[4]}" />
											</div>
										</div>
										
										<div class="form-group">											
											<label for="estado" class="col-sm-2 control-label">Estado:</label>
											<div class="col-sm-10">
												<input type="text" name="estado" id="estado" value="${fornEndereco[5]}" />
											</div>
										</div>
										
										<div class="form-group">											
											<label for="cnpj" class="col-sm-2 control-label">CNPJ:</label>
											<div class="col-sm-10">
												<input type="text" name="cnpj" id="cnpj" value="${forn.cnpj}" />
											</div>
										</div>
										
										<div class="form-group">											
											<label for="inscEstadual" class="col-sm-2 control-label">Insc. Estadual:</label>
											<div class="col-sm-10">
												<input type="text" name="inscEstadual" id="inscEstadual" value="${forn.inscEstadual}" />
											</div>
										</div>
										
										<div class="form-group">
											<div class="col-sm-offset-2 col-sm-10">										
												<input class="btn btn-success" id="botao" type="submit" value="Atualizar" />
											</div>
										</div>
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
						
							<c:when test="${empty forn}">
							
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
										
										<div class="form-group">
											<label for="codigo" class="col-sm-2 control-label">Código:</label>
											<div class="col-sm-10">
												<input type="text" id="codigo" value="${forn.codigo}" disabled />
												<input type="hidden" name="codigo" id="codigo" value="${forn.codigo}" />
											</div>												
										</div>
										
										<div class="form-group">
											<label for="tipoFornecedores" class="col-sm-2 control-label">Tipo:</label>
    										<div class="col-sm-10">
    											
												<div class="radio">										
													<label for="tipoFornecedor1" class="lblRadio">
														<input type="radio" name="tipoFornecedor" id="tipoFornecedor1" value="Empresa" <c:if test="${forn.tipo eq 'EMPRESA'}">checked</c:if> disabled />Empresa
													</label>
												</div>
												<div class="radio">
													<label for="tipoFornecedor2" class="lblRadio">
														<input type="radio" name="tipoFornecedor" id="tipoFornecedor2" value="Catador" <c:if test="${forn.tipo eq 'CATADOR'}">checked</c:if> disabled />Catador
													</label>
												</div>
											</div>
										</div>										
										
										<div class="form-group">											
											<label for="empresa" class="col-sm-2 control-label">Empresa:</label>
											<div class="col-sm-10">			
												<input type="text" name="empresa" id="empresa" value="${forn.empresa}" disabled />
											</div>
										</div>
										
										<div class="form-group">											
											<label for="contato" class="col-sm-2 control-label">Contato:</label>
											<div class="col-sm-10">			
												<input type="text" name="contato" id="contato" value="${forn.contato}" disabled /> <span>*</span>
											</div>
										</div>
										
										<div class="form-group">											
											<label for="telefone" class="col-sm-2 control-label">Telefone:</label>
											<div class="col-sm-10">			
												<input type="text" name="telefone" id="telefone" value="${forn.telefone}" disabled />
											</div>
										</div>
										
										<div class="form-group">											
											<label for="email" class="col-sm-2 control-label">Email:</label>
											<div class="col-sm-10">		
												<input type="text" name="email" id="email" value="${forn.email}" disabled/>
											</div>
										</div>
										
										<div class="form-group">											
											<label for="cep" class="col-sm-2 control-label">CEP:</label>
											<div class="col-sm-10">
												<input type="text" name="cep" id="cep" value="${fornEndereco[6]}" disabled />
											</div>
										</div>
										
										<div class="form-group">											
											<label for="endereco" class="col-sm-2 control-label">Logradouro:</label>
											<div class="col-sm-10">
												<input type="text" name="endereco" id="endereco" value="${fornEndereco[0]}" disabled />
											</div>
										</div>
										
										<div class="form-group">											
											<label for="numero" class="col-sm-2 control-label">Número:</label>
											<div class="col-sm-10">
												<input type="text" name="numero" id="numero" value="${fornEndereco[1]}" disabled />
											</div>
										</div>
										
										<div class="form-group">											
											<label for="complemento" class="col-sm-2 control-label">Complemento:</label>
											<div class="col-sm-10">
												<input type="text" name="complemento" id="complemento" value="${fornEndereco[2]}" disabled />
											</div>
										</div>
										
										<div class="form-group">											
											<label for="bairro" class="col-sm-2 control-label">Bairro:</label>
											<div class="col-sm-10">
												<input type="text" name="bairro" id="bairro" value="${fornEndereco[3]}" disabled />
											</div>
										</div>
										
										<div class="form-group">											
											<label for="cidade" class="col-sm-2 control-label">Cidade:</label>
											<div class="col-sm-10">
												<input type="text" name="cidade" id="cidade" value="${fornEndereco[4]}" disabled />
											</div>
										</div>
										
										<div class="form-group">											
											<label for="estado" class="col-sm-2 control-label">Estado:</label>
											<div class="col-sm-10">
												<input type="text" name="estado" id="estado" value="${fornEndereco[5]}" disabled />
											</div>
										</div>
										
										<div class="form-group">											
											<label for="cnpj" class="col-sm-2 control-label">CNPJ:</label>
											<div class="col-sm-10">
												<input type="text" name="cnpj" id="cnpj" value="${forn.cnpj}" disabled />
											</div>
										</div>
										
										<div class="form-group">											
											<label for="inscEstadual" class="col-sm-2 control-label">Insc. Estadual:</label>
											<div class="col-sm-10">
												<input type="text" name="inscEstadual" id="inscEstadual" value="${forn.inscEstadual}" disabled />
											</div>
										</div>
										
									</fieldset>
									
									<label class="lblLinha"></label>	
									<fieldset>
										<legend>Confirma a exclusão?</legend>
										<div class="form-group">
											<div class="col-sm-offset-1 col-sm-11">										
												<input class="btn btn-success" id="botaoSim" type="submit" value="Sim" />
												<input class="btn btn-danger" id="botaoNao" type="submit" formaction="controller?action=formFornecedor&tarefa=excluir" value="Não" />
											</div>
										</div>
									</fieldset>
										
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
	
	<script type="text/javascript" src="resources/js/jquery.2.1.1.min.js"></script>
	<script type="text/javascript" src="resources/js/bootstrap.min.js"></script>	
	<script type="text/javascript" src="resources/js/buscaCEP.js"></script>
	<script type="text/javascript" src="resources/js/selecionaFornecedor.js"></script>
	
	
</body>
</html>