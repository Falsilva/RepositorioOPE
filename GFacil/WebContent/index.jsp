<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta charset="utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
        
	<!-- ESTILOS DO BOOTSTRAP -->
    <link href="resources/css/bootstrap.css" rel="stylesheet" />
    
    <!-- ESTILOS DE FONTES INCRÍVEIS (FONTAWESOME) -->
    <link href="resources/css/font-awesome.css" rel="stylesheet" />
    
    <!-- ESTILOS PERSONALIZADOS (CUSTOM) -->
    <link href="resources/css/custom.css" rel="stylesheet" />
    
    <!-- FONTES DO GOOGLE -->
   	<link href="http://fonts.googleapis.com/css?family=Open+Sans" rel="stylesheet" type="text/css" />   	
   	
	<title>MDJ Papeis - Home</title>
</head>
<body>

	<div class="container">	
        <div class="row text-center ">
            <div class="col-md-12">
                <br /><br />
                <h1>Seja bem vindo</h1>
                <br />
            </div>
        </div>
        <div class="row ">
        	<div class="col-md-4 col-md-offset-4 col-sm-6 col-sm-offset-3 col-xs-10 col-xs-offset-1">
            	<div class="panel panel-default">
                	<div class="panel-heading">
                    	<strong>   Enter Details To Login </strong>  
                    </div>
                    <div class="panel-body">
                    	<form role="form" action="controller?action=login" method="post">
                        	<br />
                            <div class="form-group input-group">
                            	<span class="input-group-addon"><i class="fa fa-tag"  ></i></span>
                                <input type="text" class="form-control" name="nomeusuario" id="nome" placeholder="Seu usuário " />
                            </div>
                            <div class="form-group input-group">
                            	<span class="input-group-addon"><i class="fa fa-lock"  ></i></span>
                                <input type="password" class="form-control" name="senha" id="pass" placeholder="Sua senha" />
                            </div>
                            <div class="form-group">
                            	<label class="checkbox-inline">
                                	<input type="checkbox" /> Remember me
                                </label>
                                <span class="pull-right">
                                	<a href="#" >Forget password ? </a> 
                                </span>
                            </div>
                           	<a href="index.html" class="btn btn-primary ">Entrar</a>
                            <hr />
                            Not register ? <a href="registeration.html" >click here </a> 
                    	</form>
                    	<c:if test="${not empty mensagem}">
							<h3 class="text-center">${mensagem}</h3>			
						</c:if>
                    </div>
                </div>
            </div>
        </div>
    </div>
    
	<!-- SCRIPTS - FICAM AQUI EMBAIXO PARA REDUZIR O TEMPO DE CARREGAMENTO DA PÁGINA -->
    <!-- JQUERY SCRIPTS -->
    <script src="resources/js/jquery-1.10.2.js"></script>
    
    <!-- SCRIPTS DO BOOTSTRAP -->
    <script src="resources/js/bootstrap.min.js"></script>
    
    <!-- SCRIPTS DO METISMENU -->
    <script src="resources/js/jquery.metisMenu.js"></script>
    
    <!-- SCRIPTS PERSONALIZADOS (CUSTOM) -->
    <script src="resources/js/custom.js"></script>	
	
</body>
</html>