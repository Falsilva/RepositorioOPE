<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet" type="text/css" href="resources/css/estilos.css" />
<title>MDJ Papeis - Clientes</title>
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
		
		<h1>Clientes</h1>		
		
		<c:choose>
		
			<c:when test="${not empty param.tarefa}">
				<c:choose>
				
					<c:when test="${param.tarefa == 'cadastrar'}">
					
						<h4>Cadastrar Cliente</h4>
						<a href="controller?action=listarClientes">Voltar</a>
		
						<br /><br />
						
						<c:choose>
							<c:when test="${empty cli}">
							
								<!-- FORMULÁRIO CADASTRAR CLIENTE -->						
								<form id="formCadastrarCliente" action="controller?action=cadastrarCliente&tarefa=cadastrar" method="POST">
									<fieldset>
										<legend>Preencha o formulário</legend>
										
										<label for="empresa">Empresa:</label>			
										<input type="text" name="empresa" id="empresa" placeholder="Empresa" />
										
										<label for="contato">Contato:</label>			
										<input type="text" name="contato" id="contato" placeholder="Nome do contato" /> <span>*</span>
										
										<label for="telefone">Telefone:</label>			
										<input type="text" name="telefone" id="telefone" placeholder="(99)9.9999-9999" />
										
										<label for="email">Email:</label>			
										<input type="text" name="email" id="email" placeholder="Email" />
										
										<label for="endereco">Endereço:</label>
										<input type="text" name="endereco" id="endereco" placeholder="Rua/Avenida - Cidade/Estado" />
										
										<label for="cnpj">CNPJ:</label>
										<input type="text" name="cnpj" id="cnpj" placeholder="00.000.000/0001-00" />
										
										<input class="btnRight" type="submit" value="Cadastrar" />
									</fieldset>
								</form>
								
								<span class="spanAsterisco">(*) Campo obrigatório</span>					
								
								<c:if test="${not empty mensagem}">			
									<h3 class="box-1-3">${mensagem}</h3>
								</c:if>
							</c:when>
							
							<c:otherwise>
								<!-- FORMULÁRIO CADASTRAR CLIENTE -->						
								<form id="formCadastrarCliente" action="controller?action=cadastrarCliente&tarefa=cadastrar" method="POST">
									<fieldset>
										<legend>Preencha o formulário</legend>
																				
										<label for="empresa">Empresa:</label>			
										<input type="text" name="empresa" id="empresa" value="${cli.empresa}" />
										
										<label for="contato">Contato:</label>			
										<input type="text" name="contato" id="contato" value="${cli.contato}" /> <span>*</span>
										
										<label for="telefone">Telefone:</label>			
										<input type="text" name="telefone" id="telefone" value="${cli.telefone}" />
										
										<label for="email">Email:</label>			
										<input type="text" name="email" id="email" value="${cli.email}" />
										
										<label for="endereco">Endereço:</label>			
										<input class="inputEndereco" type="text" name="endereco" id="endereco" value="${cli.endereco}" />
										
										<label for="cnpj">CNPJ:</label>
										<input type="text" name="cnpj" id="cnpj" value="${cli.cnpj}" />
										
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
											
						<h4>Atualizar Cliente</h4>
						<a href="controller?action=listarClientes">Voltar</a>
		
						<br /><br />
						
						<c:choose>
							<c:when test="${empty cli}">
								
								<!-- FORMULÁRIO PESQUISAR PARA ATUALIZAR CLIENTE -->
								<form id="formPesquisar" action="controller?action=pesquisarCliente&tarefa=atualizar" method="POST">
									<fieldset>
										<legend>Informe o código do cliente</legend>
										<label for="codigo">Código:</label>			
										<input type="text" name="codigo" id="codigo" placeholder="Código do Cliente" />
										<input type="submit" value="Pesquisar" />
									</fieldset>
								</form>
								
								<c:if test="${not empty mensagem}">
									<h3 class="box-1-3">${mensagem}</h3>
								</c:if>
							</c:when>
							
							<c:when test="${not empty cli}">
								
								<!-- FORMULÁRIO ATUALIZAR CLIENTE -->
								<form id="formAtualizarCliente" action="controller?action=atualizarCliente&tarefa=atualizar" method="POST">
									<fieldset>
										<legend>Edite os campos</legend>
										
										<label for="codigo">Código:</label>			
										<input type="text" id="codigo" value="${cli.codigo}" disabled />
										<input type="hidden" name="codigo" id="codigo" value="${cli.codigo}" />
										
										<label for="empresa">Empresa:</label>			
										<input type="text" name="empresa" id="empresa" value="${cli.empresa}" />
										
										<label for="contato">Contato:</label>			
										<input type="text" name="contato" id="contato" value="${cli.contato}" /> <span>*</span>
										
										<label for="telefone">Telefone:</label>			
										<input type="text" name="telefone" id="telefone" value="${cli.telefone}" />
										
										<label for="email">Email:</label>			
										<input type="text" name="email" id="email" value="${cli.email}" />
										
										<label for="endereco">Endereço:</label>			
										<input class="inputEndereco" type="text" name="endereco" id="endereco" value="${cli.endereco}" />
										
										<label for="cnpj">CNPJ:</label>
										<input type="text" name="cnpj" id="cnpj" value="${cli.cnpj}" />
										
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
					
						<h4>Excluir Cliente</h4>
						<a href="controller?action=listarClientes">Voltar</a>
		
						<br /><br />
					
						<c:choose>
						
							<c:when test="${empty cli}">
							
								<!-- FORMULÁRIO PESQUISAR PARA EXCLUIR CLIENTE -->
								<form id="formPesquisar" action="controller?action=pesquisarCliente&tarefa=excluir" method="POST">
									<fieldset>
										<legend>Informe o código do cliente</legend>
										<label for="codigo">Código:</label>			
										<input type="text" name="codigo" id="codigo" placeholder="Código do Cliente" />
										<input type="submit" value="Excluir" />
									</fieldset>
								</form>
																
								<c:if test="${not empty mensagem}">
									<h3 class="box-1-3">${mensagem}</h3>
								</c:if>
								
							</c:when>
							
							<c:when test="${not empty cli}">
							
								<!-- FORMULÁRIO EXCLUIR CLIENTE -->
								<form id="formAtualizarCliente" action="controller?action=excluirCliente&tarefa=excluir" method="POST">
									<fieldset>
										<legend>Cliente a ser excluído</legend>
										
										<label for="codigo">Código:</label>			
										<input type="text" id="codigo" value="${cli.codigo}" disabled />
										<input type="hidden" name="codigo" id="codigo" value="${cli.codigo}" />
										
										<label for="empresa">Empresa:</label>			
										<input type="text" name="empresa" id="empresa" value="${cli.empresa}" disabled />
										
										<label for="contato">Contato:</label>			
										<input type="text" name="contato" id="contato" value="${cli.contato}" disabled />
										
										<label for="telefone">Telefone:</label>			
										<input type="text" name="telefone" id="telefone" value="${cli.telefone}" disabled />
										
										<label for="email">Email:</label>			
										<input type="text" name="email" id="email" value="${cli.email}" disabled />
										
										<label for="endereco">Endereço:</label>			
										<input class="inputEndereco" type="text" name="endereco" id="endereco" value="${cli.endereco}" disabled />
										
										<label for="cnpj">CNPJ:</label>
										<input type="text" name="cnpj" id="cnpj" value="${cli.cnpj}" disabled />
										
									</fieldset>
									
									<label class="lblLinha">Confirma a exclusão?</label>	
									
									<div class="divSimNao">
										<input type="submit" value="Sim" />&nbsp;
										<input type="submit" formaction="controller?action=formCliente&tarefa=excluir" value="Não" />
									</div>
														
								</form>
								
							</c:when>
						</c:choose>
						
					</c:otherwise>
				</c:choose>
			</c:when>
			
			<c:otherwise>								
				
				<c:choose>
				
					<c:when test="${not empty clientes}">
						<a href="controller?action=listarClientes">Voltar</a>
		
						<br /><br />
						
						<h4>Resultado:</h4>
						
						<div class="box-1-3">
							<div class="col-1-title">EMPRESA</div>
							<div class="col-1-title">CONTATO</div>
							<div class="col-1-title">TELEFONE</div>
						</div>					
						
						<div class="box-1-3">
							<div class="col-1">${cli.empresa}</div>
							<div class="col-1">${cli.contato}</div>
							<div class="col-1">${cli.telefone}</div>
						</div>				
						
					</c:when>
					
					<c:otherwise>
						<a href="controller?action=listarClientes">Voltar</a>
		
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

</body>
</html>