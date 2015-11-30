<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet" type="text/css" href="resources/css/estilos.css" />
<title>MDJ Papeis - Home</title>
</head>
<body>

	<div class="container">
		<h1>Bem-Vindo ao Sistema Gerencia Fácil - GFacil</h1>
		<p>Faça o login para começar</p>
	
		<form id="formLogin" action="controller?action=login" method="POST">
			<fieldset>
				<legend>Login</legend>
				<label for="nome">Usuário:</label>			
				<input type="text" name="nomeusuario" id="nome" placeholder="Nome de usuário" />				
				<label for="pass">Senha:</label>
				<input type="password" name="senha" id="pass" placeholder="****" maxlength="4" />			
			</fieldset>
						
			<input class="btnRight" type="submit" value="Entrar" />
		</form>
		
		<br /><br />
		
		<!-- JSTL e EL - Se houver alguma mensagem, então é exibida -->
		<c:if test="${not empty mensagem}">
			<h3 class="msg">${mensagem}</h3>			
		</c:if>
		
	</div>
	
</body>
</html>