<%@ page isErrorPage="true" language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />    
	<!-- BOOTSTRAP STYLES-->
    <link href="resources/css/bootstrap.css" rel="stylesheet" />
     <!-- FONTAWESOME STYLES-->
    <link href="resources/css/font-awesome.css" rel="stylesheet" />
        <!-- CUSTOM STYLES-->
    <link href="resources/css/custom.css" rel="stylesheet" />
     <!-- GOOGLE FONTS-->
   	<link href='http://fonts.googleapis.com/css?family=Open+Sans' rel='stylesheet' type='text/css' />   	
   	<!-- NOSSA PERSONALIZACAO -->
   	<link href="resources/css/styles.css" rel="stylesheet" />
   	
	<title>ERROR</title>
</head>
<body>
	
	<div class="container">
		<div class="row text-center">
			<div class="col-md-12">
				<img src="resources/img/logo.png" />
			</div>
		</div>
		<div class="row">
			<div class="panel-body">
				<div class="alert alert-info">
					<c:choose>
						<c:when test="${pageContext.errorData.statusCode eq 404}">
							<h4><strong>A página solicitada não existe!</strong></h4>
							<p>Dados técnicos: <strong>ERROR 404</strong> - página não encontrada.</p>							
						</c:when>	
						<c:when test="${pageContext.errorData.statusCode eq 405}">							
							<h4><strong>Desculpe, houve um erro!</strong></h4>							
							<p>Dados técnicos: <strong>ERROR 405</strong> - o método GET não é suportado por esta URL.</p>
						</c:when>
						<c:when test="${pageContext.errorData.statusCode eq 500}">
							<h4><strong>Deculpe, houve um erro!</strong></h4>
							<p>Dados técnicos: <strong>ERROR 500</strong> - ocorreu um erro interno no servidor que impediu a continuidade da requisição.</p>																	
						</c:when>				
						<c:otherwise>
							<h4><strong>Desculpe, houve um erro!</strong></h4>
							<p>Dados técnicos: <strong>EXCEPTION</strong> - ocorreu uma exception.</p>
						</c:otherwise>
					</c:choose>
					<br />
					<br />
					<a href="index.jsp"><strong>Página Inicial</strong></a>
				</div>
			</div>			
		</div>
	</div>
	
	<!-- SCRIPTS -AT THE BOTOM TO REDUCE THE LOAD TIME-->
    <!-- JQUERY SCRIPTS -->
    <script src="resources/js/jquery-1.10.2.js"></script>
      <!-- BOOTSTRAP SCRIPTS -->
    <script src="resources/js/bootstrap.min.js"></script>
    <!-- METISMENU SCRIPTS -->
    <script src="resources/js/jquery.metisMenu.js"></script>
      <!-- CUSTOM SCRIPTS -->
    <script src="resources/js/custom.js"></script>
    
	<script type="text/javascript" src="selecionaFornecedor.js"></script>
</body>
</html>