<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="pt-br">
<head>
	<meta charset="utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />    
	<!-- BOOTSTRAP STYLES -->
    <link href="resources/css/bootstrap.css" rel="stylesheet" />
     <!-- FONTAWESOME STYLES -->
    <link href="resources/css/font-awesome.css" rel="stylesheet" />
    <!-- CUSTOM STYLES -->
    <link href="resources/css/custom.css" rel="stylesheet" />
     <!-- GOOGLE FONTS -->
   	<link href='http://fonts.googleapis.com/css?family=Open+Sans' rel='stylesheet' type='text/css' />
   	<!-- NOSSA PERSONALIZACAO -->
   	<link href="resources/css/styles.css" rel="stylesheet" />
	
	<title>MDJ Papeis - Home</title>
	
</head>

<body>
	<div class="container">
	
		<!-- LOGO DO SISTEMA - GERENCIA FÁCIL -->	
		<div class="row text-center">
			<div class="col-md-12">
				<img src="resources/img/logo.png" />
			</div>
		</div>
		<div class="row ">
        	<div class="col-md-4 col-md-offset-4 col-sm-6 col-sm-offset-3 col-xs-10 col-xs-offset-1">
        	
        		<!-- PAINEL DE LOGIN -->
            	<div class="panel panel-success">
                	<div class="panel-heading">
                    	<strong> Informe os dados de Login </strong>
                    </div>
                    <div class="panel-body">			    
						<form role="form" action="controller?action=login" method="POST">
							<br />
							<div class="form-group input-group has-success">
                            	<span class="input-group-addon alert-success"><i class="glyphicon glyphicon-user"></i></span>
                                <input type="text" class="form-control" name="nomeusuario" id="nome" placeholder="Seu nome de usuário" autofocus />
                            </div>
                            <div class="form-group input-group has-success">
                                <span class="input-group-addon alert-success"><i class="glyphicon glyphicon-lock"  ></i></span>
                            	<input type="password" class="form-control" name="senha" id="pass" placeholder="Sua senha" />
                            </div>
                            <div class="form-group text-right">
              					<button class="btn btn-success"><i class="glyphicon glyphicon-log-in"></i>&nbsp;&nbsp;Entrar</button>
            				</div>
          				</form>          				
					</div>
				</div>
				
				<!-- MENSAGEM - CASO LOGIN INVÁLIDO -->
				<c:if test="${not empty mensagem}">
					<!--
                	<div class="alert alert-danger alert-dismissable">
                    	<button type="button" class="close" data-dismiss="alert" aria-hidden="true">x</button>							
						<h4 class="text-center"><i class="fa fa-lock"  ></i>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;${mensagem}</h4>
                    </div>
                    -->
                    <!-- MODAL -->
                    <div class="modal fade" id="modalLoginInvalido" role="dialog">
                   		<div class="modal-dialog modal-sm">
                   		
                   			<!-- CONTEÚDO DO MODAL -->
                   			<div class="modal-content">
                   			
                   				<!-- CABEÇALHO DO MODAL -->
                   				<div class="modal-header alert-danger">                   					
                   					<h4 class="modal-title"><span class="glyphicon glyphicon-lock"></span>&nbsp;&nbsp;&nbsp;&nbsp;<strong>Login não efetuado</strong></h4>
                   				</div>
                   				
                   				<!-- CORPO DO MODAL -->
                   				<div class="modal-body text-center">
                   					<h4><strong>${mensagem}</strong></h4>                   				
                   					<button type="button" class="btn btn-danger btn-circle" id="btnFechaModal" data-dismiss="modal"><span class="glyphicon glyphicon-ok"></span></button>
                   				</div>
                   			</div>
                   		</div>        
                    </div>
                </c:if>     			    			
  			</div>
  		</div>
	</div>	

	<!-- SCRIPTS -AT THE BOTOM TO REDUCE THE LOAD TIME-->
    <!-- JQUERY SCRIPTS -->
    <script src="resources/js/jquery-1.10.2.js"></script>
    <!-- BOOTSTRAP SCRIPTS -->
    <script src="resources/js/bootstrap.min.js"></script>    
    
    <!-- SCRIPT QUE MOSTRA O MODAL QUANDO HOUVER MENSAGEM DE LOGIN INVÁLIDO -->
    <script>
		$(document).ready(function(){
		    $("#modalLoginInvalido").modal("show");		// Mostra o Modal no carregamento da página	
		    $("#btnFechaModal").modal("hide");			// Esconde o Modal
		});
	</script>
	
</body>
</html>