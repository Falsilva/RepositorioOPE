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
		
		<h4>Tarefas</h4>
		
		<ul>
			<li><a href="controller?action=formCliente&tarefa=cadastrar">Cadastrar</a></li>
			<li><a href="controller?action=formCliente&tarefa=atualizar">Atualizar</a></li>
			<li><a href="controller?action=formCliente&tarefa=excluir">Excluir</a></li>
		</ul>
		
		<c:if test="${usuarioLogado.perfil != 'VENDEDOR'}">
			<a href="index.jsp">Voltar</a>
		</c:if>
		
		<br /><br />
		
		<form id="formPesquisarCliente" action="controller?action=pesquisarCliente" method="POST">
			<fieldset>
				<legend>Pesquise informando as primeiras letras</legend>
				<label for="empresa">Empresa:</label>
				<input type="text" name="empresa" id="empresa" placeholder="Informe a empresa" />
				<input type="submit" value="Pesquisar" />
			</fieldset>
		</form>		
		
		<br /><br />
		
		<h4>Cadastro:</h4>
				
		<c:choose>
		
			<c:when test="${not empty clientes}">
											
				<div class="box-1-8">
					<div class="col-05-title">CÓD.</div>
					<div class="col-1-title">EMPRESA</div>
					<div class="col-0-title">CONTATO</div>
					<div class="col-0-title">TELEFONE</div>
					<div class="col-1-title">EMAIL</div>
					<div class="col-2-title">ENDEREÇO</div>
					<div class="col-1-title">CNPJ</div>
					<div class="col-1-title">INSC. ESTADUAL</div>
				</div>

				<c:forEach var="cliente" items="${clientes}">
					<div class="box-1-8">
						<div class="col-05">${cliente.codigo}</div>
						<div class="col-1">${cliente.empresa}</div>
						<div class="col-0">${cliente.contato}</div>
						<div class="col-0">${cliente.telefone}</div>
						<div class="col-1">${cliente.email}</div>
						<div class="col-2">${cliente.endereco}</div>
						<div class="col-1">${cliente.cnpj}</div>
						<div class="col-0">${cliente.inscEstadual}</div>
					</div>
				</c:forEach>
			</c:when>
			
			<c:otherwise>			
				<h3 class="box-1-3">${mensagem}</h3>			
			</c:otherwise>
			
		</c:choose>
		
	</div>
</body>
</html>