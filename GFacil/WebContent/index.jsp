<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<!-- <link rel="stylesheet" type="text/css" href="resources/css/estilos.css" />  -->
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" type="text/css" href="resources/pure-release-0.6.0/pure-min.css" />
<title>MDJ Papeis - Home</title>
</head>
<body>
	<!-- TESTE -->
	<!-- O CÓDIGO ABAIXO È ANTES DA APLICAÇÃO DO PURE CSS -->
	<!-- HAVIA SOMENTE A APLICAÇÂO DE CSS ATRAVÉS DO ARQUIVO estilos.css -->
	<!-- 
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
		
		<c:if test="${not empty mensagem}">
			<h3 class="msg">${mensagem}</h3>			
		</c:if>
		
	</div> -->
	
	
	
	
	
	<!-- A PARTIR DAQUI É O CÓDIGO COM A APLICAÇÃO DO PURE CSS -->
	
	<!--------------- 1a. DIV LINHA --------------->
	
	<!-- DIV LINHA PARA EXIBIÇÃO DO CONTEÚDO (MENSAGEM DE APRESENTAÇÃO E FORMULÁRIO) -->
	<div class="pure-g">
		
		<!-- DIV BORDA ESQUERDA DO BODY -->
		<div class="pure-u-4-24"></div>
		
		<!-- DIV COLUNA CENTRAL -->
		<div class="pure-u-16-24">
			
			<!-- MENSAGEM DE APRESENTAÇÃO -->
			<h1>Bem-Vindo ao Sistema Gerencia Fácil - GFacil</h1>
			<p>Faça o login para começar</p>
			
			<!-- DIV QUE ALINHA O FORMULÁRIO DE LOGIN PARA O CENTRO DA TELA -->
			<div class="pure-u-6-24"></div>
			
			<!-- FORMULÁRIO DE LOGIN -->
			<form class="pure-form pure-form-stacked pure-u-6-24" action="controller?action=login" method="POST">
				<fieldset>
					<legend>Login</legend>
					<label for="nome">Usuário:</label>			
					<input type="text" name="nomeusuario" id="nome" placeholder="Nome de usuário" />				
					<label for="pass">Senha:</label>
					<input type="password" name="senha" id="pass" placeholder="****" maxlength="4" />			
				</fieldset>
								
				<input class="pure-button pure-button-primary" type="submit" value="Entrar" />
			</form>
			
			
			<div class="pure-u-6-24"></div>
		
		</div>	<!-- FIM DA DIV COLUNA CENTRAL -->
		
		
		<!-- DIV BORDA DIREITA DO BODY -->
		<div class="pure-u-4-24"></div>
		
	</div>	<!-- FIM DA DIV LINHA PARA EXIBIÇÃO DO CONTEÚDO (MENSAGEM DE APRESENTAÇÃO E FORMULÁRIO) -->
	


	<!--------------- 2a. DIV LINHA --------------->
	
	<!-- DIV LINHA PARA EXIBIÇÃO DE MENSAGEM -->
	<div class="pure-g">
		
		<!-- DIV BORDA ESQUERDA DO BODY -->
		<div class="pure-u-4-24"></div>			
		
		<!-- DIV COLUNA CENTRAL -->
		<div class="pure-u-16-24">
		
			<!-- DIV QUE ALINHA A MENSAGEM A PARTIR DO FORMULÁRIO DE LOGIN -->
			<div class="pure-u-6-24"></div>
			
			<!-- DIV MENSAGEM -->
			<div class="pure-u-10-24">
				<c:if test="${not empty mensagem}">
					<h3 class="msg">${mensagem}</h3>			
				</c:if>
			</div>
			
			<div class="pure-u-2-24"></div>
		
		</div>	<!-- FIM DA DIV COLUNA CENTRAL -->
	
		<!-- DIV BORDA DIREITA DO BODY -->
		<div class="pure-u-4-24"></div>
	
	</div>	<!-- FIM DA DIV LINHA PARA EXIBIÇÃO DE MENSAGEM -->
	
</body>
</html>