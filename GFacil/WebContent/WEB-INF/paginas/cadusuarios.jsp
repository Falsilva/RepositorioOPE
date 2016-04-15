<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet" type="text/css" href="resources/css/estilos.css" />
<title>MDJ Papeis - Usuários</title>
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

		<h1>Usuários</h1>
		
		<h4>Tarefas</h4>
		
		<ul>
			<li><a href="controller?action=formUsuario&tarefa=cadastrar">Cadastrar</a></li>
			<li><a href="controller?action=formUsuario&tarefa=atualizar">Atualizar</a></li>
			<li><a href="controller?action=formUsuario&tarefa=excluir">Excluir</a></li>
		</ul>
		
		<a href="index.jsp">Voltar</a>
	
		<br /><br />
		
		<form id="formPesquisar" action="controller?action=pesquisarUsuario" method="POST">
			<fieldset>
				<legend>Pesquise informando as primeiras letras</legend>
				<label for="nome">Nome:</label>			
				<input type="text" name="nome" id="nome" placeholder="Informe o nome" />
				<input type="submit" value="Pesquisar" />
			</fieldset>
		</form>
		
		<br /><br />
		
		<h4>Cadastro:</h4>
		
		<!-- JSTL e EL - Se houver algum usuário cadastrado, então é exibido, senão exibe uma mensagem -->
		<c:choose>
		
			<c:when test="${not empty usuarios}">
											
				<div class="box-1-3">
					<div class="col-1-title">NOME</div>
					<div class="col-1-title">EMAIL</div>
					<div class="col-1-title">PERFIL</div>
				</div>
					
				<c:forEach var="user" items="${usuarios}">
					<div class="box-1-3">
						<div class="col-1">${user.nome}</div>
						<div class="col-1">${user.email}</div>
						<div class="col-1">${user.perfil}</div>
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