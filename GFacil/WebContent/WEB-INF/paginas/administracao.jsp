<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet" type="text/css" href="resources/css/estilos.css" />
<title>MDJ Papeis - Administrador</title>
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
	
		<h1>Administração</h1>
				
		<br />
		
		<h4>Módulos</h4>
		
		<ul>
			<li><a href=controller?action=listarClientes>Cadastro de Clientes</a></li>
			<li><a href=controller?action=listarFornecedores>Cadastro de Fornecedores</a></li>
			<li><a href=controller?action=listarUsuarios>Cadastro de Usuários</a></li>
		</ul>
	</div>
	
</body>
</html>