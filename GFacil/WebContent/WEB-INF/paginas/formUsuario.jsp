<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="br.com.mdjpapeis.entity.Usuario" %>

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
		
		<c:choose>
		
			<c:when test="${not empty param.tarefa}">
				<c:choose>
				
					<c:when test="${param.tarefa == 'cadastrar'}">
					
						<h4>Cadastrar Usuário</h4>
						<a href="controller?action=listarUsuarios">Voltar</a>
		
						<br /><br />
						
						<c:choose>
							<c:when test="${empty user}">
							
								<!-- FORMULÁRIO CADASTRAR USUÁRIO -->						
								<form id="formCadastrar" action="controller?action=cadastrarUsuario&tarefa=cadastrar" method="POST">
									<fieldset>
										<legend>Preencha todos os campos</legend>
										
										<label for="nome">Nome:</label>			
										<input type="text" name="nome" id="nome" placeholder="Nome" />
										
										<label for="email">Email:</label>			
										<input type="text" name="email" id="email" placeholder="Email" />
										
										<label for="nomeusuario">Usuário:</label>			
										<input type="text" name="nomeusuario" id="nomeusuario" placeholder="Nome de usuário" />
										
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
								<!-- FORMULÁRIO CADASTRAR USUÁRIO -->						
								<form id="formCadastrar" action="controller?action=cadastrarUsuario&tarefa=cadastrar" method="POST">
									<fieldset>
										<legend>Preencha todos os campos</legend>
										
										<label for="nome">Nome:</label>			
										<input type="text" name="nome" id="nome" value="${user.nome}" />
										
										<label for="email">Email:</label>			
										<input type="text" name="email" id="email" value="${user.email}" />
										
										<label for="nomeusuario">Usuário:</label>			
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
											
						<h4>Atualizar Usuário</h4>
						<a href="controller?action=listarUsuarios">Voltar</a>
		
						<br /><br />
						
						<c:choose>
							<c:when test="${empty user}">
								
								<!-- FORMULÁRIO PESQUISAR PARA ATUALIZAR USUÁRIO -->
								<form id="formPesquisar" action="controller?action=pesquisarUsuario&tarefa=atualizar" method="POST">
									<fieldset>
										<legend>Pesquisa para Atualização</legend>
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
								
								<!-- FORMULÁRIO ATUALIZAR USUÁRIO -->
								<form id="formAtualizar" action="controller?action=atualizarUsuario&tarefa=atualizar" method="POST">
									<fieldset>
										<legend>Edite os campos para a atualização</legend>
										
										<label for="email">Email:</label>			
										<input type="text" name="email" id="email" value="${user.email}" /><input type="submit" formaction="controller?action=pesquisarUsuario&tarefa=atualizar" value="Pesquisar" />
										
										<label for="nome">Nome:</label>			
										<input type="text" name="nome" id="nome" value="${user.nome}" />								
											
										<label for="nomeusuario">Usuário:</label>			
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
					
						<h4>Excluir Usuário</h4>
						<a href="controller?action=listarUsuarios">Voltar</a>
		
						<br /><br />
					
						<c:choose>
						
							<c:when test="${empty user}">
							
								<!-- FORMULÁRIO PESQUISAR PARA EXCLUIR USUÁRIO -->
								<form id="formPesquisar" action="controller?action=pesquisarUsuario&tarefa=excluir" method="POST">
									<fieldset>
										<legend>Pesquisa para Exclusão</legend>
										
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
							
								<!-- FORMULÁRIO EXCLUIR USUÁRIO -->
								<form id="formExcluir" action="controller?action=excluirUsuario&tarefa=excluir" method="POST">
									<fieldset id="fieldExcluir">
										<legend>Usuário a ser excluído</legend>
										
										<label for="nome">Nome:</label>			
										<input type="text" name="nome" id="nome" value="${user.nome}" disabled />
										
										<label for="email">Email:</label>			
										<input type="text" name="email" id="email" value="${user.email}" disabled />
										
										<label for="perfil">Perfil:</label>
										<input type="text" name="perfil" id="perfil" value="${user.perfil}" disabled />
																							
										<input type="hidden" name="userLogin" value="${user.login}" />
										<input type="hidden" name="userEmail" value="${user.email}" />
										
									</fieldset>
									
									<label class="lblLinha">Confirma a exclusão?</label>	
									
									<div class="divSimNao">
										<input type="submit" value="Sim" />&nbsp;
										<input type="submit" formaction="controller?action=formUsuario&tarefa=excluir" value="Não" />
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

</body>
</html>