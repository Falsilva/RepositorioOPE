<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="br.com.mdjpapeis.entity.Usuario" %>

<!DOCTYPE html>
<html lang="pt-br">
<head>
	<meta charset="utf-8" />
	
	<!-- OTIMIZAÇÃO PARA MOBILE -->
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />	
	
	<!-- BOOTSTRAP STYLES-->
    <link href="resources/css/bootstrap.css" rel="stylesheet" />
    
     <!-- FONTAWESOME STYLES-->
    <link href="resources/font-awesome/4.6.1/css/font-awesome.css" rel="stylesheet" />
        
    <!-- GOOGLE FONTS -->
	<link rel="stylesheet" href="resources/fonts/fonts.googleapis.com.css" />
	
	<!-- ACE STYLES -->
	<link rel="stylesheet" href="resources/css/ace.min.css" class="ace-main-stylesheet" id="main-ace-style" />
	
	<!-- PERSONALIZAÇÃO PRÓPRIA -->
    <link href="resources/css/estilos.css" rel="stylesheet">
	
	<title>MDJ Papeis - Usuários</title>
</head>
<body class="no-skin">

	
	<!--=================== BLOCO DA BARRA DE NAVEGAÇÃO SUPERIOR E CONTEÚDO DA PÁGINA ====================-->
	
	
	<!-- BARRA DE NAVEGAÇÃO -->
	<div id="navbar" class="navbar navbar-default">
	
		<!-- CONTEÚDO DA BARRA DE NAVEGAÇÃO -- SUPERIOR -->
		<div id="navbar-container" class="navbar-container">
			
			<!-- BOTÃO DE ACESSO AO MENU PARA TELAS PEQUENAS -- ESSE BOTÃO ALTERNA A EXIBIÇÃO DA BARRA DE NAVEGAÇÃO LATERAL -- MENU LATERAL -->
			<button type="button" class="navbar-toggle menu-toggler pull-left" id="menu-toggler" data-target="#sidebar">
				<span class="sr-only">Toggle sidebar</span>
				<span class="icon-bar"></span>
				<span class="icon-bar"></span>
				<span class="icon-bar"></span>
			</button>
			
			<!-- ÍCONE DA BARRA DE NAVEGAÇÃO -->
			<div class="navbar-header pull-left">
				<a href="#" class="navbar-brand">
					<small>
						<i class="fa fa-recycle"></i> MDJ Papéis
					</small>
				</a>
			</div>
			
			<!-- BOTÕES DA BARRA DE NAVEGAÇÃO -->
			<div class="navbar-buttons navbar-header pull-right" role="navigation">
				<ul class="nav ace-nav">					
					
					<!-- BOTÃO AZUL COM NOME DE USUÁRIO -->
					<li class="azul-escuro-nosso">
					
						<!-- DESIGN DO BOTÃO -->
						<a data-toggle="dropdown" class="dropdown-toggle" href="#">
							<i class="ace-icon fa fa-user"></i>
							<span class="user-info">
								<small>Bem-vindo,</small> ${usuarioLogado.nome}
							</span>
							<i class="ace-icon fa fa-caret-down"></i>
						</a>
						
						<!-- LISTA DE OPÇÕES DO BOTÃO -->
						<ul class="user-menu dropdown-menu dropdown-menu-right dropdown-yellow dropdown-caret dropdown-close">
							<li>				
								<a href="#" onclick="document.getElementById('form_logout').submit()">
									<form id="form_logout" action="controller?action=logout" method="post"></form>									
									<i class="ace-icon fa fa-power-off"></i> Sair																
								</a>												
							</li>
						</ul>
					</li>					
				</ul>
			</div>
		</div>
		<!-- FIM -- CONTEÚDO DA BARRA DE NAVEGAÇÃO -- SUPERIOR -->		
	</div>
	<!-- FIM -- BARRA DE NAVEGAÇÃO -->
		
		
	<!--============= DIVISA DOS BLOCOS DE BARRA DE NAVEGAÇÃO SUPERIOR E CONTEÚDO DA PÁGINA ==============-->
		
		
	<!-- CONTEÚDO DA PÁGINA -- INCLUI MENU LATERAL E CONTEÚDO PRINCIPAL-->
	<div class="main-container" id="main-container">
		
		<!--===================================== BLOCO MENU LATERAL =====================================-->
		
		
		<!-- BARRA DE NAVEGAÇÃO LATERAL -- MENU LATERAL -- NO MODO TELA PEQUENA, ESSE MENU É ACESSADO PELO BOTÃO LÁ EMCIMA COM O id="menu-toggler"  -->
		<div id="sidebar" class="sidebar responsive">
		
			<!-- BARRA LATERAL DE ATALHOS -->
			<div class="sidebar-shortcuts" id="sidebar-shortcuts">
					
				<!-- RELÓGIO - QUANDO EXIBIÇÃO TOTAL DO MENU  -->
				<div class="sidebar-shortcuts-large" id="sidebar-shortcuts-large">																
					<div class="widget-box-relogio transparent">
	                	<div class="widget-header widget-header-flat">
	                		
							<h3 class="widget-title stronger">
								<i class="ace-icon fa fa-clock-o"></i>
								<span id="relogio" class=""></span>
							</h3>
							
						</div>
					</div>
				</div>

				<!-- RELÓGIO - QUANDO EXIBIÇÃO RECOLHIDA DO MENU  -->
				<div class="sidebar-shortcuts-mini" id="sidebar-shortcuts-mini">
					<span class="badge badge-primary">
						<i class="ace-icon fa fa-clock-o"></i>
					</span>
				</div>					
			</div>		
			<!-- FIM -- BARRA LATERAL DE ATALHOS -->
			
			<!-- LISTA DE ITENS DO MENU -->
			<ul class="nav nav-list">
			
				<c:if test="${usuarioLogado.perfil == 'ADMINISTRADOR'}">
				<!-- ITEM 1 -- DASHBOARD -->
				<li class="">
					<a href="index.jsp">
						<i class="menu-icon fa fa-tachometer"></i>
						<span class="menu-text"> Dashboard </span>
					</a>
					<b class="arrow"></b>
				</li>
				</c:if>

				<!-- ITEM 2 -- CADASTROS -->
				<li class="active open">
					<a href="#" class="dropdown-toggle">
						<i class="menu-icon fa fa-tty"></i>
						<span class="menu-text"> Cadastros </span>
						<b class="arrow fa fa-angle-down"></b>
					</a>
					<b class="arrow"></b>

					<!-- SUBMENU DO ITEM CADASTROS -->
					<ul class="submenu">
						<c:if test="${usuarioLogado.perfil == 'ADMINISTRADOR' || usuarioLogado.perfil == 'VENDEDOR'}">
						<li class="">
							<a href="#" onclick="document.getElementById('form_clientes').submit()">
								<form id="form_clientes" action="controller?action=listarClientes" method="post"></form>
								<i class="menu-icon fa fa-caret-right"></i> Clientes
								<span class="badge badge-transparent">
									<i class="ace-icon fa fa-child blue bigger-130"></i>
									<i class="ace-icon fa fa-child blue bigger-130"></i>
								</span>
							</a>
							<b class="arrow"></b>
						</li>
						</c:if>
						<c:if test="${usuarioLogado.perfil == 'ADMINISTRADOR' || usuarioLogado.perfil == 'COMPRADOR'}">
						<li class="">
							<a href="#" onclick="document.getElementById('form_fornecedores').submit()">
								<form id="form_fornecedores" action="controller?action=listarFornecedores" method="post"></form>
								<i class="menu-icon fa fa-caret-right"></i>	Fornecedores
								<span class="badge badge-transparent">
									<i class="ace-icon fa fa-truck blue bigger-130"></i>
								</span>
							</a>
							<b class="arrow"></b>
						</li>
						</c:if>
						<c:if test="${usuarioLogado.perfil == 'ADMINISTRADOR'}">
						<li class="active">
							<a href="#" onclick="document.getElementById('form_usuarios').submit()">								
								<form id="form_usuarios" action="controller?action=listarUsuarios" method="post"></form>
								<i class="menu-icon fa fa-caret-right"></i>	Usuários
								<span class="badge badge-transparent">
									<i class="ace-icon fa fa-users blue bigger-130"></i>
								</span>
							</a>
							<b class="arrow"></b>
						</li>
						</c:if>						
					</ul>
				</li>
				
				<!-- ITEM 3 -- PEDIDOS -->
				<li class="">
					<a href="#" class="dropdown-toggle">
						<i class="menu-icon fa fa-pencil-square-o"></i>
						<span class="menu-text"> Pedidos </span>
						<b class="arrow fa fa-angle-down"></b>
					</a>
					<b class="arrow"></b>

					<!-- SUBMENU DO ITEM PEDIDOS -->
					<ul class="submenu">
						<c:if test="${usuarioLogado.perfil == 'ADMINISTRADOR' || usuarioLogado.perfil == 'COMPRADOR'}">
						<li class="">
							<a href="#" onclick="document.getElementById('form_compra').submit()">
								<form id="form_compra" action="controller?action=listarPedidoCompra" method="post"></form>
								<i class="menu-icon fa fa-caret-right"></i> Compra 
								<span class="badge badge-transparent">
									<i class="ace-icon fa fa-cart-arrow-down red bigger-130"></i>
								</span>
							</a>
							<b class="arrow"></b>
						</li>
						</c:if>
						<c:if test="${usuarioLogado.perfil == 'ADMINISTRADOR' || usuarioLogado.perfil == 'VENDEDOR'}">
						<li class="">
							<a href="#" onclick="document.getElementById('form_venda').submit()">
								<form id="form_venda" action="controller?action=venda" method="post"></form>
								<i class="menu-icon fa fa-caret-right"></i>	Venda
								<span class="badge badge-transparent">									
									<i class="ace-icon fa fa-cart-plus green bigger-130"></i>
								</span>
							</a>
							<b class="arrow"></b>
						</li>
						</c:if>											
					</ul>
				</li>
								
				<c:if test="${usuarioLogado.perfil == 'ADMINISTRADOR'}">
				<!-- ITEM 4 -- CAIXA -->
				<li class="">
					<a href="#" onclick="document.getElementById('form_caixa').submit()">
						<form id="form_caixa" action="controller?action=caixa" method="post"></form>
						<i class="menu-icon fa fa-money"></i>
						<span class="menu-text"> Caixa </span>
					</a>
					<b class="arrow"></b>
				</li>
				</c:if>
				
				<!-- ITEM 5 -- TABELA DE PREÇOS -->
				<li class="">
					<a href="#" onclick="document.getElementById('form_precos').submit()">
						<form id="form_precos" action="controller?action=listarProdutos" method="post"></form>
						<i class="menu-icon fa fa-dollar"></i>
						<span class="menu-text"> Tabela de Preços </span>
					</a>
					<b class="arrow"></b>
				</li>
								
			</ul>
			<!-- FIM -- LISTA DE ITENS DO MENU -->
			
			<!-- ÍCONE DE SETA QUE RECOLHE O MENU LATERAL PARA A ESQUERDA -->
			<div class="sidebar-toggle sidebar-collapse" id="sidebar-collapse">
				<i class="ace-icon fa fa-angle-double-left" data-icon1="ace-icon fa fa-angle-double-left" data-icon2="ace-icon fa fa-angle-double-right"></i>
			</div>
						
		</div>
		<!-- FIM -- BARRA DE NAVEGAÇÃO LATERAL -- MENU LATERAL -->
		
		
		<!--=================== DIVISA DOS BLOCOS DO MENU LATERAL E CONTEÚDO PRINCIPAL ===================-->
		
		
		<!-- CONTEÚDO PRINCIPAL DA PÁGINA -->
		<div class="main-content">
			<div class="main-content-inner">
			
				<!-- BARRA DE NAVEGAÇÃO DE CATEGORIAS LISTADAS -->
				<div class="breadcrumbs" id="breadcrumbs">
					
					<!-- CATEGORIAS LISTADAS -->
					<ul class="breadcrumb">
						<li>
							<i class="ace-icon fa fa-home home-icon"></i>
							<a href="index.jsp">Home</a>
						</li>						
						<li class="">Cadastros</li>
						<li class="active">Usuários</li>
					</ul>				
					
				</div>
				<!-- FIM -- BARRA DE NAVEGAÇÃO DE CATEGORIAS LISTADAS -->
				
				<!-- CONTEÚDO DA PÁGINA -->
				<div class="page-content">
				
					<!-- CABEÇALHO DO CONTEÚDO -->
					<div class="page-header">
						<h1><strong>Usuários</strong>
							<small>
								<i class="ace-icon fa fa-angle-double-right"></i> <strong>gestão de usuários</strong>
							</small>							
						</h1>
					</div>
					<!-- FIM -- CABEÇALHO DO CONTEÚDO -->
					
					<!-- MENSAGEM DE APRESENTAÇÃO DA VISÃO GERAL - DASHBOARD -->
					<div class="row">
						<div class="col-xs-12">						
							<div class="alert alert-block alert-success">
								<button type="button" class="close" data-dismiss="alert">
									<i class="ace-icon fa fa-times"></i>
								</button>
								<i class="ace-icon fa fa-check green"></i> Todos os usuários do sistema cadastrados estão aqui. Você procura por algum em específico? Digite ali, na pesquisa, só o começo do nome para localizar os resultados. Se preferir, cadastre um novo usuário, atualize as informações ou exclua, assim você evita o acesso indevido.
							</div>
						</div>
					</div>					
					<!-- FIM -- MENSAGEM DE APRESENTAÇÃO DA VISÃO GERAL - DASHBOARD -->										
					
					<!-- TABELA DO CADASTRO -->
					<div class="row">
						<div class="col-xs-12">	
							 
							 <!-- BARRA DE FERRAMENTAS PARA A TABELA - VIA PLUGINS DATATABLES, TOOLTABLES  -->
							<div class="tableTools-container">
								<div class="btn-group btn-over-lap">
										
									<!-- BOTÃO NOVO USUÁRIO -->
									<a href="#" class="btn btn-app btn-success btn-xs" role="cadastrar">
										<i class="ace-icon fa fa-user-plus bigger-160"></i>Novo
									</a>
								</div>
							</div>
							<div class="breadcrumbs">
								<ul class="breadcrumb">
									<li class="active">
										<b>Pesquise informando as primeiras letras... <i class="ace-icon fa fa-angle-double-right"></i></b>
									</li>
								</ul>
								<div class="nav-search" id="nav-search">
									<form class="form-search" id="formPesquisar" action="controller?action=pesquisarUsuario" method="POST">
										<span class="input-icon">													
											<input class="nav-search-input" type="text" name="nome" id="nome" placeholder="Informe o nome..." />
											<i class="ace-icon fa fa-search nav-search-icon"></i>
										</span>												
									</form>
								</div>	
							</div>
									
							<!-- JSTL -- CONDIÇÃO PARA EXIBIR A TABELA -->
							<c:choose>
								<c:when test="${not empty usuarios}">
								<!-- CASO HAJA USUÁRIO -->
																
									<!-- RESULTADO DO NÚMEROS DE USUÁRIOS -->
									<div class="table-header">
										<strong>Resultado: " ${usuarios.size()} usuário(s) cadastrado(s) "</strong>
									</div>										
									
									<div>
										<!-- TABELA 1 - SIMPLES -->
										<table id="simple-table" class="table table-striped table-bordered table-hover">
										
											<!-- CABEÇALHO DA TABELA -->
											<thead>
												<tr>
													<th class="hidden"></th>
													<th>Nome</th>
													<th>E-mail</th>
													<th>Perfil</th>
													<th class="col-xs-1">Ações</th>	
												</tr>
											</thead>

											<!-- CORPO DA TABELA - REGISTROS E BOTÕES DE FUNCIONALIDADES -->
											<tbody>											
												<!-- MONTA AS LINHAS CONFORME A EXISTÊNCIA DE USUÁRIOS -->
												<c:forEach var="user" items="${usuarios}">
													<tr>														
														<!-- COLUNA ESCONDIDA DE ID(LOGIN) DOS REGISTROS -->
														<td class="hidden">${user.login}</td>
														
														<!-- COLUNAS DE REGISTROS -->
														<td>${user.nome}</td>
														<td>${user.email}</td>
														<td>${user.perfil}</td>
														
														<!-- COLUNA DE AÇÕES -->
														<td>														
															<!-- EXIBIÇÃO EM TELAS GRANDES -->															
															<div class="hidden-sm hidden-xs action-buttons">
																
																<!-- BOTÃO EDITAR -->													
																<a href="#" role="editar">
																	<span class="green">
																		<i class="ace-icon fa fa-pencil bigger-130"></i>
																	</span>
																</a>
																
																<!-- BOTÃO EXCLUIR -->
																<a href="#" role="excluir">
																	<span class="red">
																		<i class="ace-icon fa fa-trash-o bigger-130"></i>
																	</span>
																</a>
																
																<!-- BOTÃO SALVAR -->													
																<a href="#" role="salvar" class="hidden">
																	<span class="blue">
																		<i class="ace-icon fa fa-save bigger-130"></i>
																	</span>
																</a>
																
																<!-- BOTÃO EXCLUIR -->
																<a href="#" role="cancelar" class="hidden">
																	<span class="red">
																		<i class="ace-icon fa fa-close bigger-130"></i>
																	</span>
																</a>																
																
															</div>

															<!-- EXIBIÇÃO EM TELAS PEQUENAS -->
															<div class="hidden-md hidden-lg action-buttons">
																<div class="inline pos-rel">
																
																	<!-- BOTÃO DROPDOWN-TOGGLE AMARELO -->
																	<button class="btn btn-minier btn-yellow dropdown-toggle" data-toggle="dropdown" data-position="auto">
																		<i class="ace-icon fa fa-caret-down icon-only bigger-120"></i>
																	</button>																	
																	
																	<ul class="dropdown-menu dropdown-only-icon dropdown-yellow dropdown-menu-right dropdown-caret dropdown-close">																	
																		
																		<!-- BOTÃO EDITAR -->
																		<li>
																			<a href="#" class="tooltip-success" data-rel="tooltip" title="Editar" role="editar">
																				<span class="green">
																					<i class="ace-icon fa fa-pencil-square-o bigger-120"></i>
																				</span>
																			</a>
																		</li>
																		
																		<!-- BOTÃO EXCLUIR -->
																		<li>
																			<a href="#" class="tooltip-error" data-rel="tooltip" title="Excluir" role="excluir">
																				<span class="red">
																					<i class="ace-icon fa fa-trash-o bigger-120"></i>
																				</span>
																			</a>
																		</li>
																		
																		<!-- BOTÃO SALVAR -->
																		<li>																																
																			<a href="#" class="tooltip-success hidden" data-rel="tooltip" title="Salvar" role="salvar">
																				<span class="blue">
																					<i class="ace-icon fa fa-save bigger-120"></i>
																				</span>
																			</a>
																		</li>
																		
																		<!-- BOTÃO CANCELAR -->
																		<li>
																			<a href="#" class="tooltip-error hidden" data-rel="tooltip" title="Cancelar" role="cancelar">
																				<span class="red">
																					<i class="ace-icon fa fa-close bigger-120"></i>
																				</span>
																			</a>
																		</li>																		
																	</ul>																	
																</div>
															</div>															
														</td>
														<!-- COLUNA DE AÇÕES -->
													</tr>													
												</c:forEach>
												<!-- FIM -- MONTA AS LINHAS CONFORME A EXISTÊNCIA DE USUÁRIOS -->																						
											</tbody>
											<!-- FIM -- CORPO DA TABELA - REGISTROS E BOTÕES DE FUNCIONALIDADES -->											
										</table>
										<!-- FIM -- TABELA 1 - DINÂMICA -->									
									</div>									
								</c:when>								
								<c:otherwise>
								<!-- CASO NÃO HAJA USUÁRIOS -->									
									<div class="table-header">
										Resultado: "${mensagem}"
									</div>		
								</c:otherwise>								
							</c:choose>
							<!-- JSTL -- CONDIÇÃO PARA EXIBIR A TABELA -->
							
							<!-- MODAL FORMULÁRIO PARA CADASTRAR -->
							<div id="modal-form" class="modal fade" tabindex="-1" rel="modalcadastrar">
								<div class="modal-dialog">
									<div class="modal-content">
										<div class="modal-header">
											<button type="button" class="close" data-dismiss="modal">&times;</button>
											<h4 class="blue bigger">Por favor, preencha os campos...</h4>
										</div>

										<div class="modal-body">
											<div class="form-group">
												<label for="nome">Nome *</label>
												<div>
													<input type="text" id="nome" name="nome" placeholder="Informe o nome completo..." size="40" />
												</div>
											</div>
											<div class="space-4"></div>
											<div class="form-group">
												<label for="email">E-mail *</label>
												<div>
													<input type="text" id="email" name="email" placeholder="Informe o seu e-mail..." size="40" />
												</div>
											</div>													
											<div class="space-4"></div>
											<div class="form-group">
												<label for="nomeusuario">Usuário *</label>
												<div>
													<input type="text" id="nomeusuario" name="nomeusuario" placeholder="Informe o nome de usuário..." size="35" />
												</div>
											</div>
											<div class="space-4"></div>
											<div class="form-group">
												<label for="senha">Senha *</label>
												<div>
													<input type="password" id="senha" name="senha" placeholder="Informe uma senha..." maxlength="4" size="35" />
												</div>
											</div>
											<div class="form-group">
												<label for="perfil">Perfil *</label>
												<div>															
													<select class="chosen-select" data-placeholder="Escolha o perfil..." name="perfil" id="perfil">											
														<c:forEach var="perfil" items="<%= Usuario.Perfil.values() %>">
															<option value="${perfil}">${perfil}</option>
														</c:forEach>
													</select>
												</div>
											</div>	
										</div>

										<div class="modal-footer">
											<div class="pull-left text-left">
												<b class="blue">* campos obrigatórios</b>												
											</div>
											<div class="pull-right">
												<button id="btnCancelar" class="btn btn-sm" data-dismiss="modal">
													<i class="ace-icon fa fa-times"></i> Cancelar
												</button>
												<button id="btnCadastrar" class="btn btn-sm btn-primary">
													<i class="ace-icon fa fa-check"></i> Cadastrar
												</button>
											</div>										
										</div>										
									</div>									
								</div>
							</div>
							<!-- FIM -- MODAL FORMULÁRIO PARA CADASTRAR -->
							
							<!-- MODAL FORMULÁRIO PARA EXCLUIR -->
							<div id="modal-form" class="modal fade" tabindex="-1" rel="modalexcluir">
								<div class="modal-dialog">
									<div class="modal-content">
										<div class="modal-header">
											<button type="button" class="close" data-dismiss="modal">&times;</button>
											<h4 class='red bigger'> Excluir... Tem certeza?</h4>
										</div>

										<div class="modal-body">
											<div class="form-group">
												<label for="nome">Nome</label>
												<div>
													<input type="text" id="nomeExcluir" name="nome" placeholder="Informe o nome completo..." size="40" />
												</div>
											</div>
											<div class="space-4"></div>
											<div class="form-group">
												<label for="email">E-mail</label>
												<div>
													<input type="text" id="emailExcluir" name="email" placeholder="Informe o seu e-mail..." size="40" />
												</div>
											</div>													
											<div class="space-4"></div>
											<div class="form-group">
												<label for="nomeusuario">Usuário</label>
												<div>
													<input type="text" id="nomeusuarioExcluir" name="nomeusuario" placeholder="Informe o nome de usuário..." size="35" />
												</div>
											</div>
											<div class="space-4"></div>											
											<div class="form-group">
												<label for="perfil">Perfil</label>
												<div>															
													<select class="chosen-select" data-placeholder="Escolha o perfil..." name="perfil" id="perfilExcluir">											
														<c:forEach var="perfil" items="<%= Usuario.Perfil.values() %>">
															<option value="${perfil}">${perfil}</option>
														</c:forEach>
													</select>
												</div>
											</div>												
										</div>

										<div class="modal-footer">
											<button id="btnCancelarExcluir" class="btn btn-sm" data-dismiss="modal">
												<i class="ace-icon fa fa-times"></i> Cancelar
											</button>
											<button id="btnExcluir" class="btn btn-sm btn-danger">
												<i class="ace-icon fa fa-trash-o"></i> Excluir
											</button>											
										</div>										
									</div>									
								</div>
							</div>
							<!-- FIM -- MODAL FORMULÁRIO PARA EXCLUIR -->
												
						</div>
					</div>
					<!-- FIM -- TABELA DO CADASTRO -->					      
				</div>
				<!-- FIM -- CONTEÚDO DA PÁGINA -->
			</div>
		</div>
		<!-- FIM -- CONTEÚDO PRINCIPAL DA PÁGINA -->
		
		
		<!--======================= DIVISA DOS BLOCOS DO CONTEÚDO PRINCIPAL E RODAPÉ =======================-->
		
		
		<!-- RODAPÉ DA PÁGINA -->
		<div class="footer">
			<div class="footer-inner">
			
				<!-- CONTEÚDO DO RODAPÉ -->
				<div class="footer-content">
				
					<!-- MARCA REGISTRADA - COPYRIGHT -->
					<span class="bigger-120">
						<span class="green bolder">Gerencia Fácil</span> &reg; 2014-2016
					</span>
				</div>
			</div>
		</div>
		<!-- FIM -- RODAPÉ DA PÁGINA -->		
		
		<!-- ÍCONE NO CANTO DIREITO INFERIOR DA TELA AO DESCER - ELE APARECE QUANDO DESCE - FAZ VOLTAR PARA O TOPO -->
		<a href="#" id="btn-scroll-up" class="btn btn-sm btn-scroll-up btn-inverse">
			<i class="ace-icon fa fa-angle-double-up icon-only bigger-110"></i>
		</a>	
	</div>
	<!-- FIM -- CONTEÚDO DA PÁGINA -- INCLUI MENU LATERAL E CONTEÚDO PRINCIPAL-->
	
	
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
	
	<!-- ONTOUCH MOBILE - NÃO TESTADO -->
	<script type="text/javascript">
		if('ontouchstart' in document.documentElement) document.write("<script src='resources/js/jquery.mobile.custom.min.js'>"+"<"+"/script>");
	</script>
	
    <!-- BOOTSTRAP SCRIPTS -->
    <script src="resources/js/bootstrap.min.js"></script>
    
    <!-- ACE CONFIGURAÇÕES DESTA PÁGINA -->
	<script src="resources/js/ace-elements.min.js"></script>
	<script src="resources/js/ace.min.js"></script>
	
	<!-- PLUGINS ESPECÍFICOS DESTA PÁGINA
	<script src="resources/js/jquery.dataTables.min.js"></script>
	<script src="resources/js/jquery.dataTables.bootstrap.min.js"></script>
	<script src="resources/js/dataTables.tableTools.min.js"></script>
	<script src="resources/js/dataTables.colVis.min.js"></script>-->
    
    <!-- RELÓGIO -->
    <script src="resources/js/relogio.js"></script>
    
    <!-- AJAX - SUBMIT FORMS - RECARREGA A PÁGINA QUANDO O MODAL É FECHADO -->
    <script src="resources/js/ajax.js"></script>
    
    <!-- EDITAR EXCLUIR -->
    <script src="resources/js/manipulatabela.js"></script>
    
    
    
    
    
    
    
    
    
    
    
    <!-- FORMULÁRIO - MODAL
    <script type="text/javascript">
		jQuery(function($) {
			
			if(!ace.vars['touch']) {
				$('.chosen-select').chosen({allow_single_deselect:true}); 
				//redimensiona o chosen conforme redimensionamento da janela
			
				$(window)
				.off('resize.chosen')
				.on('resize.chosen', function() {
					$('.chosen-select').each(function() {
						 var $this = $(this);
						 $this.next().css({'width': $this.parent().width()});
					})
				}).trigger('resize.chosen');
				// redimensiona o chosen conforme o recolhimento/expansão do menu lateral
				$(document).on('settings.ace.chosen', function(e, event_name, event_val) {
					if(event_name != 'sidebar_collapsed') return;
					$('.chosen-select').each(function() {
						 var $this = $(this);
						 $this.next().css({'width': $this.parent().width()});
					})
				});			
			
				$('#chosen-multiple-style .btn').on('click', function(e){
					var target = $(this).find('input[type=radio]');
					var which = parseInt(target.val());
					if(which == 2) $('#form-field-select-4').addClass('tag-input-style');
					 else $('#form-field-select-4').removeClass('tag-input-style');
				});
			}			
			
			// AJUDA - TOOLTIP E POPOVER
			$('[data-rel=tooltip]').tooltip({container:'body'});
			$('[data-rel=popover]').popover({container:'body'});
			
			// TEXTAREA - AUTOREDIMENSIONAMENTO E LIMITE
			$('textarea[class*=autosize]').autosize({append: "\n"});
			$('textarea.limited').inputlimiter({
				remText: '%n caracter%s remanescente...',
				limitText: 'max allowed : %n.'
			});
			
			// MÁSCARAS
			$.mask.definitions['~']='[+-]';
			$('.input-mask-date').mask('99/99/9999');
			$('.input-mask-phone').mask('(999) 999-9999');
			$('.input-mask-eyescript').mask('~9.99 ~9.99 999');
			$(".input-mask-product").mask("a*-999-a999",{placeholder:" ",completed:function(){alert("Você digitou o seguinte: "+this.val());}});
			
			
			// INPUT FILE - MODAL
			$('#modal-form input[type=file]').ace_file_input({
				style:'well',
				btn_choose:'Arraste os arquivos aqui ou clique para escolher',
				btn_change:null,
				no_icon:'ace-icon fa fa-cloud-upload',
				droppable:true,
				thumbnail:'large'
			});
				
			// O plugin chosen de dentro de um modal terá sempre a largura zero, porque o elemento select 
			// é originalmente escondido e a sua largura não foi determinada.
			// então configuramos a largura só depois que o modal é mostrado
			/*$('#modal-form').on('shown.bs.modal', function () {
				if(!ace.vars['touch']) {
					$(this).find('.chosen-container').each(function(){
						$(this).find('a:first-child').css('width' , '210px');
						$(this).find('.chosen-drop').css('width' , '210px');
						$(this).find('.chosen-search input').css('width' , '200px');
					});
				}
			});*/
			/**
			// ou você pode ativar o plugin chosen depois que o modal é mostrado
			// deste jeito o elemento select torna-se visível com as dimensões e o chosen trabalha como esperado
			$('#modal-form').on('shown', function () {
				$(this).find('.modal-chosen').chosen();
			});
			*/
			/*
			$(document).one('ajaxloadstart.page', function(e) {
				$('textarea[class*=autosize]').trigger('autosize.destroy');
				$('.limiterBox,.autosizejs').remove();
				$('.daterangepicker.dropdown-menu,.colorpicker.dropdown-menu,.bootstrap-datetimepicker-widget.dropdown-menu').remove();
			});*/	
		});
	</script> -->
    
    
    
    
    
    <!-- TABELA -->
    <!-- inline scripts related to this page -->
	<!-- <script type="text/javascript">
			jQuery(function($) {


				//initiate dataTables plugin
				var oTable1 = 
				$('#dynamic-table')
				//.wrap("<div class='dataTables_borderWrap' />")   //if you are applying horizontal scrolling (sScrollX)
				.dataTable( {
					bAutoWidth: false,
					"aoColumns": [
					  { "bSortable": false },
					  null, null,null, null, null,
					  { "bSortable": false }
					],
					"aaSorting": [],
			
					//,
					//"sScrollY": "200px",
					//"bPaginate": false,
			
					//"sScrollX": "100%",
					//"sScrollXInner": "120%",
					//"bScrollCollapse": true,
					//Note: if you are applying horizontal scrolling (sScrollX) on a ".table-bordered"
					//you may want to wrap the table inside a "div.dataTables_borderWrap" element
			
					//"iDisplayLength": 50
			    } );
				//oTable1.fnAdjustColumnSizing();
			
			
				//TableTools settings
				TableTools.classes.container = "btn-group btn-overlap";
				TableTools.classes.print = {
					"body": "DTTT_Print",
					"info": "tableTools-alert gritter-item-wrapper gritter-info gritter-center white",
					"message": "tableTools-print-navbar"
				}
			
				//initiate TableTools extension
				var tableTools_obj = new $.fn.dataTable.TableTools( oTable1, {
					"sSwfPath": "resources/swf/copy_csv_xls_pdf.swf",
					
					"sRowSelector": "td:not(:last-child)",
					"sRowSelect": "multi",
					"fnRowSelected": function(row) {
						//check checkbox when row is selected
						try { $(row).find('input[type=checkbox]').get(0).checked = true }
						catch(e) {}
					},
					"fnRowDeselected": function(row) {
						//uncheck checkbox
						try { $(row).find('input[type=checkbox]').get(0).checked = false }
						catch(e) {}
					},
			
					"sSelectedClass": "success",
			        "aButtons": [
						{
							"sExtends": "copy",
							"sToolTip": "Copy to clipboard",
							"sButtonClass": "btn btn-white btn-primary btn-bold",
							"sButtonText": "<i class='fa fa-copy bigger-110 pink'></i>",
							"fnComplete": function() {
								this.fnInfo( "<h3 class="no-margin-top smaller">Table copied</h3>\n"+
									"<p>Copied "+(oTable1.fnSettings().fnRecordsTotal())+" row(s) to the clipboard.</p>",
									1500
								);
							}
						},
						
						{
							"sExtends": "csv",
							"sToolTip": "Export to CSV",
							"sButtonClass": "btn btn-white btn-primary  btn-bold",
							"sButtonText": "<i class='fa fa-file-excel-o bigger-110 green'></i>"
						},
						
						{
							"sExtends": "pdf",
							"sToolTip": "Export to PDF",
							"sButtonClass": "btn btn-white btn-primary  btn-bold",
							"sButtonText": "<i class='fa fa-file-pdf-o bigger-110 red'></i>"
						},
						
						{
							"sExtends": "print",
							"sToolTip": "Print view",
							"sButtonClass": "btn btn-white btn-primary  btn-bold",
							"sButtonText": "<i class='fa fa-print bigger-110 grey'></i>",
							
							"sMessage": "<div class='navbar navbar-default'><div class='navbar-header pull-left'><a class='navbar-brand' href='#'><small>Optional Navbar &amp; Text</small></a></div></div>",
							
							"sInfo": "<h3 class='no-margin-top'>Print view</h3>\n"+
								"<p>Please use your browser's print function to\n"+
								"print this table.\n"+
								"<br />Press <b>escape</b> when finished.</p>",
						}
			        ]
			    });

				// colocamos um container antes da nossa table e append o elemento TableTools a ele
			    $(tableTools_obj.fnContainer()).appendTo($('.tableTools-container'));
				
				//also add tooltips to table tools buttons
				//addding tooltips directly to "A" buttons results in buttons disappearing (weired! don't know why!)
				//so we add tooltips to the "DIV" child after it becomes inserted
				//flash objects inside table tools buttons are inserted with some delay (100ms) (for some reason)
				setTimeout(function() {
					$(tableTools_obj.fnContainer()).find('a.DTTT_button').each(function() {
						var div = $(this).find('> div');
						if(div.length > 0) div.tooltip({container: 'body'});
						else $(this).tooltip({container: 'body'});
					});
				}, 200);
				
				
				
				//ColVis extension
				var colvis = new $.fn.dataTable.ColVis( oTable1, {
					"buttonText": "<i class='fa fa-search'></i>",
					"aiExclude": [0, 6],
					"bShowAll": true,
					//"bRestore": true,
					"sAlign": "right",
					"fnLabel": function(i, title, th) {
						return $(th).text();//remove icons, etc
					}					
				}); 
				
				//style it
				$(colvis.button()).addClass('btn-group').find('button').addClass('btn btn-white btn-info btn-bold')
				
				//and append it to our table tools btn-group, also add tooltip
				$(colvis.button())
				.prependTo('.tableTools-container .btn-group')
				.attr('title', 'Show/hide columns').tooltip({container: 'body'});
				
				//and make the list, buttons and checkboxed Ace-like
				$(colvis.dom.collection)
				.addClass('dropdown-menu dropdown-light dropdown-caret dropdown-caret-right')
				.find('li').wrapInner('<a href="javascript:void(0)" />') //'A' tag is required for better styling
				.find('input[type=checkbox]').addClass('ace').next().addClass('lbl padding-8');
			
			
				
				/////////////////////////////////
				//table checkboxes
				$('th input[type=checkbox], td input[type=checkbox]').prop('checked', false);
				
				//select/deselect all rows according to table header checkbox
				$('#dynamic-table > thead > tr > th input[type=checkbox]').eq(0).on('click', function(){
					var th_checked = this.checked;//checkbox inside "TH" table header
					
					$(this).closest('table').find('tbody > tr').each(function(){
						var row = this;
						if(th_checked) tableTools_obj.fnSelect(row);
						else tableTools_obj.fnDeselect(row);
					});
				});
				
				//select/deselect a row when the checkbox is checked/unchecked
				$('#dynamic-table').on('click', 'td input[type=checkbox]' , function(){
					var row = $(this).closest('tr').get(0);
					if(!this.checked) tableTools_obj.fnSelect(row);
					else tableTools_obj.fnDeselect($(this).closest('tr').get(0));
				});		
				
				$(document).on('click', '#dynamic-table .dropdown-toggle', function(e) {
					e.stopImmediatePropagation();
					e.stopPropagation();
					e.preventDefault();
				});
				
				
				//And for the first simple table, which doesn't have TableTools or dataTables
				//select/deselect all rows according to table header checkbox
				var active_class = 'active';
				$('#simple-table > thead > tr > th input[type=checkbox]').eq(0).on('click', function(){
					var th_checked = this.checked;//checkbox inside "TH" table header
					
					$(this).closest('table').find('tbody > tr').each(function(){
						var row = this;
						if(th_checked) $(row).addClass(active_class).find('input[type=checkbox]').eq(0).prop('checked', true);
						else $(row).removeClass(active_class).find('input[type=checkbox]').eq(0).prop('checked', false);
					});
				});
				
				//select/deselect a row when the checkbox is checked/unchecked
				$('#simple-table').on('click', 'td input[type=checkbox]' , function(){
					var $row = $(this).closest('tr');
					if(this.checked) $row.addClass(active_class);
					else $row.removeClass(active_class);
				});
			
				
			
				/********************************/
				//add tooltip for small view action buttons in dropdown menu
				$('[data-rel="tooltip"]').tooltip({placement: tooltip_placement});
				
				//tooltip placement on right or left
				function tooltip_placement(context, source) {
					var $source = $(source);
					var $parent = $source.closest('table')
					var off1 = $parent.offset();
					var w1 = $parent.width();
			
					var off2 = $source.offset();
					//var w2 = $source.width();
			
					if( parseInt(off2.left) < parseInt(off1.left) + parseInt(w1 / 2) ) return 'right';
					return 'left';
				}
			
			});
		</script>
		-->
            	
</body>
</html>