<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="br.com.mdjpapeis.entity.Usuario" %>

<!DOCTYPE html>
<html lang="pt-br">
<head>
	<meta charset="utf-8">
	
	<!-- OTIMIZA��O PARA MOBILE -->
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />    
	
	<!-- BOOTSTRAP STYLES-->
    <link href="resources/css/bootstrap.css" rel="stylesheet" />
    
     <!-- FONTAWESOME STYLES-->
    <link href="resources/font-awesome/4.6.1/css/font-awesome.css" rel="stylesheet" />
        
    <!-- GOOGLE FONTS -->
	<link rel="stylesheet" href="resources/fonts/fonts.googleapis.com.css" />
	
	<!-- ACE STYLES -->
	<link rel="stylesheet" href="resources/css/ace.min.css" class="ace-main-stylesheet" id="main-ace-style" />
	
	<!-- PERSONALIZA��O PR�PRIA -->
    <link href="resources/css/estilos.css" rel="stylesheet">
    
	<title>MDJ Papeis - Usu�rios</title>
</head>
<body>	
	<div class="nav">
	
		<!-- JSTL - Exibe o nome do usu�rio -->
		<h3 id="olaUser">Ol�, ${usuarioLogado.nome}</h3>
		
		<form id="formLogout" action="controller?action=logout" method="POST">		
			<input id="btnLogout" type="submit" value="Sair" />
		</form>
	</div>
	
	<div class="container">
		
		<h1>Usu�rios</h1>		
		 
		 
		 
		 
		<c:choose>
		
			<c:when test="${not empty param.tarefa}">
				<c:choose>
				
					<c:when test="${param.tarefa == 'cadastrar'}">
					
						<h4>Cadastrar Usu�rio</h4>
						<a href="controller?action=listarUsuarios">Voltar</a>
		
						<br /><br />
						
						<c:choose>
							<c:when test="${empty user}">
							
								<!-- FORMUL�RIO CADASTRAR USU�RIO -->						
								<form id="formCadastrar" action="controller?action=cadastrarUsuario&tarefa=cadastrar" method="POST">
									<fieldset>
										<legend>Preencha todos os campos</legend>
										
										<label for="nome">Nome:</label>			
										<input type="text" name="nome" id="nome" placeholder="Nome" />
										
										<label for="email">Email:</label>			
										<input type="text" name="email" id="email" placeholder="Email" />
										
										<label for="nomeusuario">Usu�rio:</label>			
										<input type="text" name="nomeusuario" id="nomeusuario" placeholder="Nome de usu�rio" />
										
										<label for="senha">Senha:</label>			
										<input type="password" name="senha" id="senha" placeholder="****" maxlength="4" />
										
										<label for="perfil">Perfil:</label>
										<select name="perfil" id="perfil">											
											<c:forEach var="perfil" items="<%= Usuario.Perfil.values() %>">
												<option value="${perfil}">${perfil}</option>
											</c:forEach>
										</select>
										
										<input class="btnRight" type="submit" value="Cadastrar" />
									</fieldset>
								</form>
								
								<c:if test="${not empty mensagem}">			
									<h3 class="box-1-3">${mensagem}</h3>
								</c:if>
							</c:when>
							
							<c:otherwise>
								<!-- FORMUL�RIO CADASTRAR USU�RIO -->						
								<form id="formCadastrar" action="controller?action=cadastrarUsuario&tarefa=cadastrar" method="POST">
									<fieldset>
										<legend>Preencha todos os campos</legend>
										
										<label for="nome">Nome:</label>			
										<input type="text" name="nome" id="nome" value="${user.nome}" />
										
										<label for="email">Email:</label>			
										<input type="text" name="email" id="email" value="${user.email}" />
										
										<label for="nomeusuario">Usu�rio:</label>			
										<input type="text" name="nomeusuario" id="nomeusuario" value="${user.login}" />
										
										<label for="senha">Senha:</label>			
										<input type="password" name="senha" id="senha" value="${user.senha}" maxlength="4" />
										
										<label for="perfil">Perfil:</label>
										<select name="perfil" id="perfil">
											<c:forEach var="perfil" items="<%= Usuario.Perfil.values() %>">
												<option value="${perfil}" <c:if test="${user.perfil eq perfil}">selected</c:if>>${perfil}</option>
											</c:forEach>
											<!-- 
											<option value="Administrador" <c:if test="${user.perfil eq 'ADMINISTRADOR'}">selected</c:if>>Administrador</option>
											<option value="Comprador" <c:if test="${user.perfil eq 'COMPRADOR'}">selected</c:if>>Comprador</option>
											<option value="Vendedor" <c:if test="${user.perfil eq 'VENDEDOR'}">selected</c:if>>Vendedor</option> -->
										</select>
										
										<input class="btnRight" type="submit" value="Cadastrar" />
									</fieldset>
								</form>
								
								<c:if test="${not empty mensagem}">			
									<h3 class="box-1-3">${mensagem}</h3>
								</c:if>						
							</c:otherwise>
						</c:choose>
					</c:when>
					
					<c:when test="${param.tarefa == 'atualizar'}">
											
						<h4>Atualizar Usu�rio</h4>
						<a href="controller?action=listarUsuarios">Voltar</a>
		
						<br /><br />
						
						<c:choose>
							<c:when test="${empty user}">
								
								<!-- FORMUL�RIO PESQUISAR PARA ATUALIZAR USU�RIO -->
								<form id="formPesquisar" action="controller?action=pesquisarUsuario&tarefa=atualizar" method="POST">
									<fieldset>
										<legend>Pesquisa para Atualiza��o</legend>
										<label for="email">Email:</label>			
										<input type="text" name="email" id="email" placeholder="Informe o email" />
										<input type="submit" value="Pesquisar" />
									</fieldset>
								</form>
								
								<c:if test="${not empty mensagem}">
									<h3 class="box-1-3">${mensagem}</h3>
								</c:if>
							</c:when>
							
							<c:when test="${not empty user}">
								
								<!-- FORMUL�RIO ATUALIZAR USU�RIO -->
								<form id="formAtualizar" action="controller?action=atualizarUsuario&tarefa=atualizar" method="POST">
									<fieldset>
										<legend>Edite os campos para a atualiza��o</legend>
										
										<label for="email">Email:</label>			
										<input type="text" name="email" id="email" value="${user.email}" /><input type="submit" formaction="controller?action=pesquisarUsuario&tarefa=atualizar" value="Pesquisar" />
										
										<label for="nome">Nome:</label>			
										<input type="text" name="nome" id="nome" value="${user.nome}" />								
											
										<label for="nomeusuario">Usu�rio:</label>			
										<input type="text" id="nomeusuario" value="${user.login}" disabled />
										<input type="hidden" name="nomeusuario" value="${user.login}" />
										
										<label for="senha">Senha:</label>			
										<input type="text" name="senha" id="senha" value="${user.senha}" maxlength="4" />
										
										<label for="perfil">Perfil:</label>
										<select name="perfil" id="perfil">
											<c:forEach var="perfil" items="<%= Usuario.Perfil.values() %>">
												<option value="${perfil}" <c:if test="${user.perfil eq perfil}">selected</c:if>>${perfil}</option>
											</c:forEach>
											<!-- 																	
											<option value="Administrador" <c:if test="${user.perfil eq 'ADMINISTRADOR'}">selected</c:if>>Administrador</option>
											<option value="Comprador" <c:if test="${user.perfil eq 'COMPRADOR'}">selected</c:if>>Comprador</option>
											<option value="Vendedor" <c:if test="${user.perfil eq 'VENDEDOR'}">selected</c:if>>Vendedor</option> -->
										</select>
										
										<label class="lblRight">
											<c:forEach var="perfil" items="<%= Usuario.Perfil.values() %>">
												<c:if test="${user.perfil eq perfil}">${perfil} (atual)</c:if>
											</c:forEach>
											<!-- 
											<c:if test="${user.perfil eq 'ADMINISTRADOR'}">${user.perfil} (atual)</c:if>
											<c:if test="${user.perfil eq 'COMPRADOR'}">${user.perfil} (atual)</c:if>
											<c:if test="${user.perfil eq 'VENDEDOR'}">${user.perfil} (atual)</c:if> -->	
										</label>
										
										<input class="btnRight" type="submit" value="Atualizar" />
									</fieldset>
								</form>
								
								<c:if test="${not empty mensagem}">
									<h3 class="box-1-3">${mensagem}</h3>
								</c:if>
								
							</c:when>
						</c:choose>
					</c:when>
					
					<c:otherwise>
					
						<h4>Excluir Usu�rio</h4>
						<a href="controller?action=listarUsuarios">Voltar</a>
		
						<br /><br />
					
						<c:choose>
						
							<c:when test="${empty user}">
							
								<!-- FORMUL�RIO PESQUISAR PARA EXCLUIR USU�RIO -->
								<form id="formPesquisar" action="controller?action=pesquisarUsuario&tarefa=excluir" method="POST">
									<fieldset>
										<legend>Pesquisa para Exclus�o</legend>
										
										<label for="email">Email:</label>			
										<input type="text" name="email" id="email" placeholder="Informe o email" />
										
										<input type="submit" value="Excluir" />
									</fieldset>
								</form>
								
								<c:if test="${not empty mensagem}">
									<h3 class="box-1-3">${mensagem}</h3>
								</c:if>
								
							</c:when>
							
							<c:when test="${not empty user}">
							
								<!-- FORMUL�RIO EXCLUIR USU�RIO -->
								<form id="formExcluir" action="controller?action=excluirUsuario&tarefa=excluir" method="POST">
									<fieldset id="fieldExcluir">
										<legend>Usu�rio a ser exclu�do</legend>
										
										<label for="nome">Nome:</label>			
										<input type="text" name="nome" id="nome" value="${user.nome}" disabled />
										
										<label for="email">Email:</label>			
										<input type="text" name="email" id="email" value="${user.email}" disabled />
										
										<label for="perfil">Perfil:</label>
										<input type="text" name="perfil" id="perfil" value="${user.perfil}" disabled />
																							
										<input type="hidden" name="userLogin" value="${user.login}" />
										<input type="hidden" name="userEmail" value="${user.email}" />
										
									</fieldset>
									
									<label class="lblLinha">Confirma a exclus�o?</label>	
									
									<div class="divSimNao">
										<input type="submit" value="Sim" />&nbsp;
										<input type="submit" formaction="controller?action=formUsuario&tarefa=excluir" value="N�o" />
									</div>
								</form>
							</c:when>
						</c:choose>
						
					</c:otherwise>
				</c:choose>
			</c:when>
			
			<c:otherwise>								
				
				<c:choose>
				
					<c:when test="${not empty user}">
						<a href="controller?action=listarUsuarios">Voltar</a>
		
						<br /><br />
						
						<h4>Resultado:</h4>
						
						<div class="box-1-3">
							<div class="col-1-title">NOME</div>
							<div class="col-1-title">EMAIL</div>
							<div class="col-1-title">PERFIL</div>
						</div>					
						
						<div class="box-1-3">
							<div class="col-1">${user.nome}</div>
							<div class="col-1">${user.email}</div>
							<div class="col-1">${user.perfil}</div>
						</div>				
						
					</c:when>
					
					<c:otherwise>
						<a href="controller?action=listarUsuarios">Voltar</a>
		
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


	<!-- FIM -- CONTE�DO DA P�GINA -- INCLUI MENU LATERAL E CONTE�DO PRINCIPAL-->
	
	
	<!--=======================================   SCRIPTS   ================================================-->
	

    <!-- JQUERY SCRIPTS -->
    <!--[if !IE]> -->
	<script src="resources/js/jquery.2.1.1.min.js"></script>
	<!-- <![endif]-->
	
	<!--[if IE]> 
    <script src="resources/js/jquery-1.11.1.min.js"></script>
    <![endif]-->
    
    <!--[if !IE]> -->
	<script type="text/javascript">
		window.jQuery || document.write("<script src='resources/js/jquery.min.js'>"+"<"+"/script>");
	</script>
	<!-- <![endif]-->
    
    <!--[if IE]>
	<script type="text/javascript">
 		window.jQuery || document.write("<script src='resources/js/jquery1x.min.js'>"+"<"+"/script>");
	</script>
	<![endif]-->
	
	<!-- ONTOUCH MOBILE - N�O TESTADO -->
	<script type="text/javascript">
		if('ontouchstart' in document.documentElement) document.write("<script src='resources/js/jquery.mobile.custom.min.js'>"+"<"+"/script>");
	</script>
	
    <!-- BOOTSTRAP SCRIPTS -->
    <script src="resources/js/bootstrap.min.js"></script>
    
    <!-- ACE CONFIGURA��ES DESTA P�GINA -->
	<script src="resources/js/ace-elements.min.js"></script>
	<script src="resources/js/ace.min.js"></script>
	
    <!-- METISMENU SCRIPTS -->
    <script src="resources/js/jquery.metisMenu.js"></script>
    
    <!-- CUSTOM SCRIPTS -->
    <script src="resources/js/custom.js"></script>
    
    <!-- REL�GIO -->
    <script src="resources/js/relogio.js"></script>
    
    <!-- PLUGINS DO GR�FICO -->
    <script src="resources/js/jquery.flot.min.js"></script>
	<script src="resources/js/jquery.flot.pie.min.js"></script>
	<script src="resources/js/jquery.flot.resize.min.js"></script>
	
	<!-- GR�FICO FATURAMENTO -->
	<script src="resources/js/grafico.js"></script>
	
</body>
</html>