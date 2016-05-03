<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="br.com.mdjpapeis.entity.Cliente" %>

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
					
					<!-- BOTÃO CINZA DE TAREFAS -->
					<li class="grey">
					
						<!-- DESIGN DO BOTÃO -->
						<a data-toggle="dropdown" class="dropdown-toggle" href="#">
						
							<!-- ÍCONE DA TAREFA -->
							<i class="ace-icon fa fa-tasks"></i>
							
							<!-- QUANTIDADE DE TAREFAS PENDENTES - BUSCA OS DADOS NO BANCO -->
							<span class="badge badge-grey">4</span>
						</a>
						
						<!-- LISTA DE OPÇÕES DO BOTÃO DE TAREFAS -->
						<ul class="dropdown-menu dropdown-menu-right dropdown-navbar navbar-grey dropdown-caret dropdown-close">
							
							<!-- ITEM 1 - CABEÇALHO DOS ITENS DA LISTA -->
							<li class="dropdown-header">
								<i class="ace-icon fa fa-check"></i> 4 Tarefas pendentes
							</li>
							
							<!-- ITEM 2 - CORPO DOS ITENS DA LISTA -->
							<li class="dropdown-content">
							
								<!-- TAREFAS PENDENTES - BARRAS DE PROGRESSO -->
								<ul class="dropdown-menu dropdown-navbar navbar-grey">																		
									<li>
										<a href="#">
											<div class="clearfix">
												<span class="pull-left">Atualização do Software</span>
												<span class="pull-right">65%</span>
											</div>
											<div class="progress progress-mini">
												<div style="width:65%" class="progress-bar"></div>
											</div>
										</a>
									</li>									
									<li>
										<a href="#">
											<div class="clearfix">
												<span class="pull-left">Atualização do Hardware</span>
												<span class="pull-right">35%</span>
											</div>
											<div class="progress progress-mini">
												<div style="width:35%" class="progress-bar progress-bar-danger"></div>
											</div>
										</a>
									</li>									
									<li>
										<a href="#">
											<div class="clearfix">
												<span class="pull-left">Teste Unitário</span>
												<span class="pull-right">15%</span>
											</div>
											<div class="progress progress-mini">
												<div style="width:15%" class="progress-bar progress-bar-warning"></div>
											</div>
										</a>
									</li>									
									<li>
										<a href="#">
											<div class="clearfix">
												<span class="pull-left">Resoluções de Bugs</span>
												<span class="pull-right">90%</span>
											</div>
											<div class="progress progress-mini progress-striped active">
												<div style="width:90%" class="progress-bar progress-bar-success"></div>
											</div>
										</a>
									</li>
								</ul>
							</li>
							
							<!-- ITEM 3 - RODAPÉ DOS ITENS DA LISTA -->
							<li class="dropdown-footer">
								<a href="#">
									Veja todos os detalhes <i class="ace-icon fa fa-arrow-right"></i>
								</a>
							</li>
						</ul>
					</li>
					
					<!-- BOTÃO PINK DE NOTIFICAÇÕES -->
					<li class="purple">
					
						<!-- DESIGN DO BOTÃO -->
						<a data-toggle="dropdown" class="dropdown-toggle" href="#">
							<i class="ace-icon fa fa-bell icon-animated-bell"></i>
							<span class="badge badge-important">8</span>
						</a>
						
						<!-- LISTA DE OPÇÕES DO BOTÃO DE NOTIFICAÇÕES -->
						<ul class="dropdown-menu dropdown-menu-right dropdown-navbar navbar-pink dropdown-caret dropdown-close">						
							
							<!-- ITEM 1 - CABEÇALHO DOS ITENS DA LISTA -->
							<li class="dropdown-header">
								<i class="ace-icon fa fa-exclamation-triangle"></i> 8 Notificações
							</li>
							
							<!-- ITEM 2 - CORPO DOS ITENS DA LISTA -->
							<li class="dropdown-content">
							
								<!-- NOTIFICAÇÕES -->
								<ul class="dropdown-menu dropdown-navbar navbar-pink">
									<li>
										<a href="#">
											<div class="clearfix">
												<span class="pull-left">
													<i class="btn btn-xs btn-pink no-hover fa fa-comment"></i> Novos Comentários
												</span>
												<span class="pull-right badge badge-info">+12</span>
											</div>
										</a>
									</li>
									<li>
										<a href="#">
											<i class="btn btn-xs btn-primary fa fa-user"></i> Bob cadastrou-se como Editor
										</a>
									</li>
									<li>
										<a href="#">
											<div class="clearfix">
												<span class="pull-left">
													<i class="btn btn-xs btn-success no-hover fa fa-shopping-cart"></i> Novos Pedidos
												</span>
												<span class="pull-right badge badge-success">+8</span>
											</div>
										</a>
									</li>
									<li>
										<a href="#">
											<div class="clearfix">
												<span class="pull-left">
													<i class="btn btn-xs btn-info no-hover fa fa-twitter"></i> Seguidores
												</span>
												<span class="pull-right badge badge-info">+11</span>
											</div>
										</a>
									</li>									
								</ul>
							</li>
							
							<!-- ITEM 3 - RODAPÉ DOS ITENS DA LISTA -->
							<li class="dropdown-footer">
								<a href="#">
									Veja todas as notificações <i class="ace-icon fa fa-arrow-right"></i>
								</a>
							</li>
						</ul>
					</li>
					
					<!-- BOTÃO VERDE DE EMAILS -->
					<li class="green">
						
						<!-- DESIGN DO BOTÃO -->
						<a data-toggle="dropdown" class="dropdown-toggle" href="#">
							<i class="ace-icon fa fa-envelope icon-animated-vertical"></i>
							<span class="badge badge-success">5</span>
						</a>
						
						<!-- LISTA DE OPÇÕES DO BOTÃO DE EMAILS -->
						<ul class="dropdown-menu dropdown-menu-right dropdown-navbar navbar-green dropdown-caret dropdown-close">
							
							<!-- ITEM 1 - CABEÇALHO DOS ITENS DA LISTA -->
							<li class="dropdown-header">
								<i class="ace-icon fa fa-envelope-o"></i> 5 Mensagens
							</li>
							
							<!-- ITEM 2 - CORPO DOS ITENS DA LISTA -->
							<li class="dropdown-content">
							
								<!-- EMAILS -->
								<ul class="dropdown-menu dropdown-navbar navbar-green">
									<li>
										<a class="clearfix" href="#">
											<img src="resources/avatars/avatar.png" class="msg-photo" alt="Avatar do Alex" />
											<span class="msg-body">
												<span class="msg-title">
													<span class="blue">Alex:</span> João de Santo Cristo mesmo autor...
												</span>
												<span class="msg-time">
													<i class="ace-icon fa fa-clock-o"></i>
													<span>&nbsp;um momento atrás</span>
												</span>
											</span>
										</a>
									</li>
									<li>
										<a class="clearfix" href="#">
											<img src="resources/avatars/avatar3.png" class="msg-photo" alt="Avatar da Susana" />
											<span class="msg-body">
												<span class="msg-title">
													<span class="blue">Susana:</span>
													Desempenho do vestibular no portão de entrada...
												</span>
												<span class="msg-time">
													<i class="ace-icon fa fa-clock-o"></i>
													<span>&nbsp;20 minutos atrás</span>
												</span>
											</span>										
										</a>
									</li>
									<li>
										<a class="clearfix" href="#">
											<img src="resources/avatars/avatar4.png" class="msg-photo" alt="Avatar do Bob" />
											<span class="msg-body">
												<span class="msg-title">
													<span class="blue">Bob:</span>
													A imigrição precisa ser vista com um pouco mais de suavidade...
												</span>
												<span class="msg-time">
													<i class="ace-icon fa fa-clock-o"></i>
													<span>&nbsp;3:15 pm</span>
												</span>
											</span>										
										</a>
									</li>
									<li>
										<a href="#" class="clearfix">
											<img src="resources/avatars/avatar2.png" class="msg-photo" alt="Avatar da Kate" />
											<span class="msg-body">
												<span class="msg-title">
													<span class="blue">Kate:</span>
													Caros amigos, se houverem a necessidade de dar boas risadas...
												</span>
												<span class="msg-time">
													<i class="ace-icon fa fa-clock-o"></i>
													<span>&nbsp;1:33 pm</span>
												</span>
											</span>
										</a>
									</li>
									<li>
										<a href="#" class="clearfix">
											<img src="resources/avatars/avatar5.png" class="msg-photo" alt="Avatar do Fred" />
											<span class="msg-body">
												<span class="msg-title">
													<span class="blue">Fred:</span>
													Desempenho no vestibular pelo autor...
												</span>
												<span class="msg-time">
													<i class="ace-icon fa fa-clock-o"></i>
													<span>&nbsp;10:09 am</span>
												</span>
											</span>
										</a>
									</li>								
								</ul>
							</li>
							
							<!-- ITEM 3 - RODAPÉ DOS ITENS DA LISTA -->
							<li class="dropdown-footer">
								<a href="#">
									Veja todas as mensagens <i class="ace-icon fa fa-arrow-right"></i>
								</a>
							</li>
						</ul>
					</li>
					
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
			
				<!-- ITEM 1 -- DASHBOARD -->
				<li class="">
					<a href="index.jsp">
						<i class="menu-icon fa fa-tachometer"></i>
						<span class="menu-text"> Dashboard </span>
					</a>
					<b class="arrow"></b>
				</li>

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
						<li class="active">
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
						<li class="">
							<a href="#" onclick="document.getElementById('form_usuarios').submit()">								
								<form id="form_usuarios" action="controller?action=listarUsuarios" method="post"></form>
								<i class="menu-icon fa fa-caret-right"></i>	Usuários
								<span class="badge badge-transparent">
									<i class="ace-icon fa fa-users blue bigger-130"></i>
								</span>
							</a>
							<b class="arrow"></b>
						</li>						
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
						<li class="">
							<a href="#">
								<i class="menu-icon fa fa-caret-right"></i> Compra 
								<span class="badge badge-transparent">
									<i class="ace-icon fa fa-cart-arrow-down red bigger-130"></i>
								</span>
							</a>
							<b class="arrow"></b>
						</li>
						<li class="">
							<a href="#">
								<i class="menu-icon fa fa-caret-right"></i>	Venda
								<span class="badge badge-transparent">									
									<i class="ace-icon fa fa-cart-plus green bigger-130"></i>
								</span>
							</a>
							<b class="arrow"></b>
						</li>												
					</ul>
				</li>
				
				<!-- ITEM 4 -- CONTAS -->
				<li class="">
					<a href="#" onclick="document.getElementById('form_contas').submit()">
						<form id="form_contas" action="controller?action=contas" method="post"></form>						
						<i class="menu-icon fa fa-credit-card"></i>
						<span class="menu-text"> Contas </span>
					</a>
					<b class="arrow"></b>
				</li>
				
				<!-- ITEM 5 -- CAIXA -->
				<li class="">
					<a href="#">
						<i class="menu-icon fa fa-money"></i>
						<span class="menu-text"> Caixa </span>
					</a>
					<b class="arrow"></b>
				</li>
				
				<!-- ITEM 6 -- TABELA DE PREÇOS -->
				<li class="">
					<a href="#" onclick="document.getElementById('form_precos').submit()">
						<form id="form_precos" action="controller?action=precos" method="post"></form>
						<i class="menu-icon fa fa-dollar"></i>
						<span class="menu-text"> Tabela de Preços </span>
					</a>
					<b class="arrow"></b>
				</li>
				
				<!-- ITEM 7 -- E-MAIL 
				<li class="">
					<a href="#" class="dropdown-toggle">
						<i class="menu-icon fa fa-envelope-o"></i>
						<span class="menu-text"> E-Mail </span>
						<b class="arrow fa fa-angle-down"></b>
					</a>
					<b class="arrow"></b>

					<!-- SUBMENU DO ITEM E-MAIL 
					<ul class="submenu">	
						<li class="">
							<a href="#">
								<i class="menu-icon fa fa-caret-right"></i> Escrever
								<span class="badge badge-transparent">									
									<i class="ace-icon fa fa-pencil blue bigger-130"></i>
								</span>
							</a>
							<b class="arrow"></b>
						</li>					
						<li class="">
							<a href="#">
								<i class="menu-icon fa fa-caret-right"></i> Entrada
								<span class="badge badge-transparent">									
									<i class="ace-icon fa fa-inbox blue bigger-130"></i>
								</span>
							</a>
							<b class="arrow"></b>
						</li>
						<li class="">
							<a href="#">
								<i class="menu-icon fa fa-caret-right"></i> Enviados
								<span class="badge badge-transparent">									
									<i class="ace-icon fa fa-external-link blue bigger-130"></i>
								</span>
							</a>
							<b class="arrow"></b>
						</li>
						<li class="">
							<a href="#">
								<i class="menu-icon fa fa-caret-right"></i> Rascunhos
								<span class="badge badge-transparent">									
									<i class="ace-icon fa fa-file-text-o blue bigger-130"></i>
								</span>
							</a>
							<b class="arrow"></b>
						</li>						
					</ul>
				</li>
				
				<!-- ITEM 8 -- MAPA GERAL 
				<li class="">
					<a href="#">
						<i class="menu-icon fa fa-map-o"></i>
						<span class="menu-text"> Mapa Geral</span>							
					</a>
					<b class="arrow"></b>
				</li>
				
				<!-- ITEM 9 -- CALENDÁRIO 
				<li class="">
					<a href="#">
						<i class="menu-icon fa fa-calendar"></i>
						<span class="menu-text"> Calendário</span>							
					</a>
					<b class="arrow"></b> -->
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
						<li class="active">Clientes</li>
					</ul>				
					
				</div>
				<!-- FIM -- BARRA DE NAVEGAÇÃO DE CATEGORIAS LISTADAS -->
				
				<!-- CONTEÚDO DA PÁGINA -->
				<div class="page-content">
				
					<!-- CABEÇALHO DO CONTEÚDO -->
					<div class="page-header">
						<h1><strong>Clientes</strong>
							<small>
								<i class="ace-icon fa fa-angle-double-right"></i> <strong>gestão de clientes</strong>
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
								<i class="ace-icon fa fa-check green"></i> Todos os clientes cadastrados estão aqui. Você procura por algum em específico? Digite ali, na pesquisa, informando as primeiras letras já é possível localizar os resultados. Se preferir, cadastre um novo cliente, atualize as informações ou exclua, assim você possui os dados sempre atualizados.
							</div>
						</div>
					</div>					
					<!-- FIM -- MENSAGEM DE APRESENTAÇÃO DA VISÃO GERAL - DASHBOARD -->										
					
					<!-- TABELA DO CADASTRO -->
					<div class="row">
						<div class="col-xs-12">	
							 
							 <ul>
								<li><a href="controller?action=formCliente&tarefa=cadastrar">Cadastrar</a></li>
								<li><a href="controller?action=formCliente&tarefa=atualizar">Atualizar</a></li>
								<li><a href="controller?action=formCliente&tarefa=excluir">Excluir</a></li>
							</ul>
							
							
							<!-- IMPORTANTE LEMBRAR --- A PÁGINA INICIAL DO VENDEDOR DEVE SER ESTA E DEVE DESABILITAR ALGUMAS FUNÇÕES DO MENU -->
							<c:if test="${usuarioLogado.perfil != 'VENDEDOR'}">
								<a href="index.jsp">Voltar</a>
							</c:if>
							
							<!-- JSTL -- CONDIÇÃO PARA EXIBIR A TABELA -->							
							<c:choose>
								<c:when test="${not empty clientes}">
								<!-- CASO HAJA CLIENTE -->
									
									<!-- BARRA DE FERRAMENTAS PARA A TABELA - VIA PLUGINS DATATABLES, TOOLTABLES  -->
									<div class="tableTools-container">
										<div class="btn-group btn-over-lap">
										
											<!-- BOTÃO NOVO CLIENTE -->
											<a href="#" class="btn btn-app btn-success btn-xs" role="cadastrar">
												<i class="ace-icon fa fa-plus bigger-130">&nbsp;<i class="ace-icon fa fa-child bigger-130"></i><i class="ace-icon fa fa-child bigger-130"></i></i>Novo
											</a>
										</div>
									</div>
									<div class="breadcrumbs">
										<ul class="breadcrumb">
											<li class="active">Pesquise informando as primeiras letras</li>
										</ul>
										<div class="nav-search" id="nav-search">
											<form class="form-search" id="formPesquisarCliente" action="controller?action=pesquisarCliente" method="POST">
												<span class="input-icon">													
													<input class="nav-search-input" type="text" name="empresa" id="empresa" placeholder="Informe a empresa..." />
													<i class="ace-icon fa fa-search nav-search-icon"></i>
												</span>												
											</form>
										</div>	
									</div>
										
									<!-- RESULTADO DO NÚMEROS DE USUÁRIOS -->
									<div class="table-header">
										<strong>Resultado: " ${clientes.size()} cliente(s) cadastrado(s) "</strong>
									</div>										
									
									<div>
										<!-- TABELA 1 - SIMPLES -->
										<table id="simple-table" class="table table-striped table-bordered table-hover">
										
											<!-- CABEÇALHO DA TABELA -->
											<thead>
												<tr>
													<th class="hidden">Cód.</th>
													<th>Empresa</th>
													<th>Contato</th>
													<th>Telefone</th>
													<th>Email</th>
													<th>Endereço</th>
													<th>CNPJ</th>
													<th>Insc. Estadual</th>
													<th>Ações</th>
												</tr>
											</thead>

											<!-- CORPO DA TABELA - REGISTROS E BOTÕES DE FUNCIONALIDADES -->
											<tbody>
																				
												<!-- MONTA AS LINHAS CONFORME A EXISTÊNCIA DE CLIENTES -->
												<c:forEach var="cliente" items="${clientes}">
													<tr>														
														<!-- COLUNA ESCONDIDA DE CÓDIGO DOS REGISTROS -->
														<td class="hidden">${cliente.codigo}</td>
														
														<!-- COLUNAS DE REGISTROS -->
														<td>${cliente.empresa}</td>
														<td>${cliente.contato}</td>
														<td>${cliente.telefone}</td>
														<td>${cliente.email}</td>
														<td>${cliente.endereco}</td>
														<td>${cliente.cnpj}</td>
														<td>${cliente.inscEstadual}</td>
														
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
															<div class="hidden-md hidden-lg">
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
																			<a href="#" class="tooltip-error hidden" data-rel="tooltip" title="Salvar" role="salvar">
																				<span class="blue">
																					<i class="ace-icon fa fa-save bigger-120"></i>
																				</span>
																			</a>
																		</li>
																		
																		<!-- BOTÃO CANCELAR -->
																		<li>
																			<a href="#" class="tooltip-error hidden" data-rel="tooltip" title="Cancelar" role="cancelar">
																				<span class="red">
																					<i class="ace-icon fa fa-close bigger-130"></i>
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
								<!-- CASO NÃO HAJA CLIENTES -->									
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
												<label for="empresa">Empresa</label>
												<div>													
													<input type="text" id="empresa" name="empresa" placeholder="Informe a Empresa..." size="40" />
												</div>
											</div>
											<div class="space-4"></div>
											<div class="form-group">
												<label for="contato">Contato:</label>
												<div>
													<input type="text" id="contato" name="contato" placeholder="Inform o nome do contato..." size="40" /> <!-- <span>*</span> -->													
												</div>
											</div>
											<div class="space-4"></div>
											<div class="form-group">
												<label for="telefone">Telefone:</label>
												<div>			
													<input type="text" id="telefone" name="telefone" placeholder="(99)9.9999-9999" size="40" />
												</div>
											</div>					
											<div class="space-4"></div>
											<div class="form-group">
												<label for="email">E-mail</label>
												<div>
													<input type="text" id="email" name="email" placeholder="Informe o seu e-mail..." size="40" />													
												</div>
											</div>
											<div class="space-4"></div>
											<div class="form-group">
												<label for="cep">CEP:</label>											
												<div>
													<input type="text" name="cep" id="cep" placeholder="00000-000" />
												</div>
											</div>
											<div class="form-group">
												<label for="endereco">Logradouro:</label>											
												<div>
													<input type="text" name="endereco" id="endereco" placeholder="Rua/Avenida" />
												</div>
											</div>
											<div class="form-group">
												<label for="numero">Número:</label>											
												<div>
													<input type="text" name="numero" id="numero" placeholder="Número" />
												</div>
											</div>
											<div class="form-group">
												<label for="complemento">Complemento:</label>												
												<div>
													<input type="text" name="complemento" id="complemento" placeholder="Complemento" />
												</div>
											</div>
											<div class="form-group">
												<label for="bairro">Bairro:</label>											
												<div>
													<input type="text" name="bairro" id="bairro" placeholder="Bairro" />
												</div>
											</div>
											<div class="form-group">
												<label for="cidade">Cidade:</label>											
												<div>
													<input type="text" name="cidade" id="cidade" placeholder="Cidade" />
												</div>
											</div>
											<div class="form-group">
												<label for="estado">Estado:</label>											
												<div>
													<input type="text" name="estado" id="estado" placeholder="Estado" />
												</div>
											</div>				
											<div class="space-4"></div>
											<div class="form-group">
												<label for="cnpj">CNPJ:</label>
												<div>
													<input type="text" id="cnpj" name="cnpj" placeholder="00.000.000/0001-00" />
												</div>
											</div>
											<div class="space-4"></div>
											<div class="form-group">
												<label for="inscEstadual">Insc. Estadual:</label>
												<div>
													<input type="text" id="inscEstadual" name="inscEstadual" placeholder="Insc. Estadual" />
												</div>
											</div>												
										</div>

										<div class="modal-footer">
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
												<label for="codigo">Código:</label>
												<div>													
													<input type="text" id="codigo" name="codigo" size="40" />
												</div>
											</div>
											<div class="space-4"></div>
											<div class="form-group">
												<label for="empresa">Empresa</label>
												<div>													
													<input type="text" id="empresa" name="empresa" size="40" />
												</div>
											</div>
											<div class="space-4"></div>
											<div class="form-group">
												<label for="contato">Contato:</label>
												<div>
													<input type="text" id="contato" name="contato" size="40" /> <!-- <span>*</span> -->													
												</div>
											</div>
											<div class="space-4"></div>
											<div class="form-group">
												<label for="telefone">Telefone:</label>
												<div>			
													<input type="text" id="telefone" name="telefone" size="40" />
												</div>
											</div>					
											<div class="space-4"></div>
											<div class="form-group">
												<label for="email">E-mail</label>
												<div>
													<input type="text" id="email" name="email" size="40" />													
												</div>
											</div>
											<div class="space-4"></div>
											<div class="form-group">
												<label for="cep">CEP:</label>											
												<div>
													<input type="text" name="cep" id="cep" />
												</div>
											</div>
											<div class="form-group">
												<label for="endereco">Logradouro:</label>											
												<div>
													<input type="text" name="endereco" id="endereco" />
												</div>
											</div>
											<div class="form-group">
												<label for="numero">Número:</label>											
												<div>
													<input type="text" name="numero" id="numero" />
												</div>
											</div>
											<div class="form-group">
												<label for="complemento">Complemento:</label>												
												<div>
													<input type="text" name="complemento" id="complemento" />
												</div>
											</div>
											<div class="form-group">
												<label for="bairro">Bairro:</label>											
												<div>
													<input type="text" name="bairro" id="bairro" />
												</div>
											</div>
											<div class="form-group">
												<label for="cidade">Cidade:</label>											
												<div>
													<input type="text" name="cidade" id="cidade" />
												</div>
											</div>
											<div class="form-group">
												<label for="estado">Estado:</label>											
												<div>
													<input type="text" name="estado" id="estado" />
												</div>
											</div>				
											<div class="space-4"></div>
											<div class="form-group">
												<label for="cnpj">CNPJ:</label>
												<div>
													<input type="text" id="cnpj" name="cnpj" />
												</div>
											</div>
											<div class="space-4"></div>
											<div class="form-group">
												<label for="inscEstadual">Insc. Estadual:</label>
												<div>
													<input type="text" id="inscEstadual" name="inscEstadual" />
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
    
    <!-- RELÓGIO -->
    <script src="resources/js/relogio.js"></script>
    
    <!-- AJAX - SUBMIT FORMS - RECARREGA A PÁGINA QUANDO O MODAL É FECHADO -->
    <script src="resources/js/ajaxcliente.js"></script>
    
    <!-- EDITAR EXCLUIR -->
    <script src="resources/js/manipulatabelacliente.js"></script>
    
    <!-- BUSCA ENDEREÇO PELO CEP -->
    <script type="text/javascript" src="resources/js/buscaCEP.js"></script>
            	
</body>
</html>