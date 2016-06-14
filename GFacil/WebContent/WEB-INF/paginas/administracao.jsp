<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql"%>

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
	
	
	<title>MDJ Papeis</title>
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
				<li class="active">
					<a href="index.jsp">
						<i class="menu-icon fa fa-tachometer"></i>
						<span class="menu-text"> Dashboard </span>
					</a>
					<b class="arrow"></b>
				</li>
				</c:if>

				<!-- ITEM 2 -- CADASTROS -->
				<li class="">
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
								<form id="form_venda" action="controller?action=listarPedidoVenda" method="post"></form>
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
						<li class="active">Dashboard</li>
					</ul>				
					
				</div>
				<!-- FIM -- BARRA DE NAVEGAÇÃO DE CATEGORIAS LISTADAS -->
				
				<!-- CONTEÚDO DA PÁGINA -->
				<div class="page-content">
				
					<!-- CABEÇALHO DO CONTEÚDO -->
					<div class="page-header">
						<h1><strong>Dashboard</strong>
							<small>
								<i class="ace-icon fa fa-angle-double-right"></i> <strong>visão geral &amp; estatísticas</strong>
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
								<i class="ace-icon fa fa-check green"></i> Aqui é onde tudo começa! Nesta página, você possui uma visão geral do que é mais importante para gerenciar melhor o seu negócio, como o saldo em caixa, os últimos lançamentos e pendências,<!--  e uma visão gráfica de como anda o balanço geral,--> além de acesso ao menu com as principais funcionalidades que sempre o acompanha durante a navegação do sistema.
								<!-- MENSAGEM, SE HOUVER ALERTAS: Aqui é onde tudo começa! Nesta página, você possui uma visão geral do que é mais importante para gerenciar melhor o seu negócio, como notificações importantes, tarefas pendentes, o saldo em caixa e uma visão gráfica de como anda o faturamento, além de acesso ao menu com as principais funcionalidades que sempre o acompanha durante a navegação do sistema. -->
							</div>
						</div>
					</div>					
					<!-- FIM -- MENSAGEM DE APRESENTAÇÃO DA VISÃO GERAL - DASHBOARD -->
					
					<!-- NOTIFICAÇÕES 
					<div class="row">
						<div class="col-xs-12">						
							<div class="widget-box transparent">
							
								<!-- TÍTULO DOS PAINÉIS 
		                		<div class="widget-header widget-header-flat">									
				                    <h3 class="widget-title lighter"><strong>Avisos</strong></h3>
				                </div>
				                
				                <!-- PAINÉIS 
				                <div class="widget-body">
									<div class="widget-main no-padding">
									
										<!-- PAINEL DE NOTIFICAÇÕES IMPORTANTES 		                    
					                    <div class="col-lg-3 col-md-6">
					                        <div class="panel-nosso panel-red-nosso">
					                            <div class="panel-heading-nosso">
					                                <div class="row">
					                                    <div class="col-xs-3">
					                                        <i class="fa fa-exclamation-triangle fa-5x"></i>
					                                    </div>
					                                    <div class="col-xs-9 text-right">
					                                        <div class="huge-nosso">13</div>
					                                        <div>Importante!</div>
					                                    </div>
					                                </div>
					                            </div>
					                            <a href="#">
					                                <div class="panel-footer-nosso">
					                                    <span class="pull-left-nosso">Verifique</span>
					                                    <span class="pull-right-nosso"><i class="fa fa-arrow-circle-right"></i></span>
					                                    <div class="clearfix"></div>
					                                </div>
					                            </a>
					                        </div>
					                    </div>
					                    
					                    <!-- PAINEL DE TAREFAS 
					                    <div class="col-lg-3 col-md-6">
					                        <div class="panel-nosso panel-green-nosso">
					                            <div class="panel-heading-nosso">
					                                <div class="row">
					                                    <div class="col-xs-3">
					                                        <i class="fa fa-tasks fa-5x"></i>
					                                    </div>
					                                    <div class="col-xs-9 text-right">
					                                        <div class="huge-nosso">12</div>
					                                        <div>Tarefas Pendentes!</div>
					                                    </div>
					                                </div>
					                            </div>
					                            <a href="#">
					                                <div class="panel-footer-nosso">
					                                    <span class="pull-left-nosso">Veja com detalhes</span>
					                                    <span class="pull-right-nosso"><i class="fa fa-arrow-circle-right"></i></span>
					                                    <div class="clearfix"></div>
					                                </div>
					                            </a>
					                        </div>
					                    </div>
					                    
					                    <!-- PAINEL DE PEDIDOS PENDENTES 
					                    <div class="col-lg-3 col-md-6">
					                        <div class="panel-nosso panel-yellow-nosso">
					                            <div class="panel-heading-nosso">
					                                <div class="row">
					                                    <div class="col-xs-3">
					                                        <i class="fa fa-pencil-square-o fa-5x"></i>
					                                    </div>
					                                    <div class="col-xs-9 text-right">
					                                        <div class="huge-nosso">124</div>
					                                        <div>Pedidos Pendentes!</div>
					                                    </div>
					                                </div>
					                            </div>
					                            <a href="#">
					                                <div class="panel-footer-nosso">
					                                    <span class="pull-left-nosso">Veja com detalhes</span>
					                                    <span class="pull-right-nosso"><i class="fa fa-arrow-circle-right"></i></span>
					                                    <div class="clearfix"></div>
					                                </div>
					                            </a>
					                        </div>
					                    </div>
					                    
					                    <!-- PAINEL DE NOVOS EMAILS -->
					                    <!-- <div class="col-lg-3 col-md-6">
					                        <div class="panel-nosso panel-primary-nosso">
					                            <div class="panel-heading-nosso">
					                                <div class="row">
					                                    <div class="col-xs-3">
					                                        <i class="fa fa-envelope fa-5x"></i>
					                                    </div>
					                                    <div class="col-xs-9 text-right">
					                                        <div class="huge-nosso">26</div>
					                                        <div>Novos e-mails!</div>
					                                    </div>
					                                </div>
					                            </div>
					                            <a href="#">
					                                <div class="panel-footer-nosso">
					                                    <span class="pull-left-nosso">Veja a caixa de entrada</span>
					                                    <span class="pull-right-nosso"><i class="fa fa-arrow-circle-right"></i></span>
					                                    <div class="clearfix"></div>
					                                </div>
					                            </a>
					                        </div>
					                    </div> 		                    			
		                    		</div>
		                    	</div>
		                    </div>
		            	</div>
	                </div>
	                <!-- FIM -- NOTIFICAÇÕES -->
	                
	                <!-- SALDO -- ÚLTIMOS LANÇAMENTOS -- GRÁFICO FATURAMENTO MENSAL -->
	                <div class="row">
	                
	                	<!-- SALDO -- ÚLTIMOS LANÇAMENTOS -->
	                	<div class="col-lg-4">
	                		
	                		<!-- CAIXA - SALDO -->
	                		<div class="widget-box transparent">
	                		
	                			<!-- TÍTULO DO SALDO -->
		                		<div class="widget-header widget-header-flat">
									<h3 class="widget-title lighter">
										<i class="ace-icon fa fa-money"></i><strong> Caixa - Saldo</strong>
									</h3>
								</div>
								
								<!-- SALDO -->
								<div class="widget-body">
									<div class="widget-main no-padding">
										<div class="text-right" role="saldoTotal"></div>
									</div>
								</div>
	                		</div>
	                	</div>
	                </div>
	                <div class="row">
	                	<div class="col-lg-4"> 
	                		<!-- CAIXA - ÚLTIMOS LANÇAMENTOS -->
	                		<div class="widget-box transparent">
			                    
								<!-- TÍTULO DOS LANÇAMENTOS -->
		                		<div class="widget-header widget-header-flat">
									<h3 class="widget-title lighter">
										<i class="ace-icon fa fa-money"></i><strong> Últimos Lançamentos</strong>
									</h3>
								</div>
								
								<!-- LANÇAMENTOS -->
		                        <div class="widget-body">
									<div class="widget-main no-padding">
										<table class="table table-bordered table-striped" id="tabCxUltimo">
											<thead class="thin-border-bottom">
			                                	<tr>
			                                    	<th>Item</th>
			                                        <th>Data</th>
			                                        <th>Descrição</th>
			                                        <th>Valor (R$)</th>
			                                	</tr>
		                           			</thead>
			                           		<tbody></tbody>
			                        	</table>
			                        </div>
		                        </div>
		                    	<div class="text-right">
		                        	<h5>
		                        		<a href="#" onclick="document.getElementById('form_caixa').submit()">
											<form id="form_caixa" action="controller?action=caixa" method="post"></form>
											Ver o caixa <i class="fa fa-arrow-circle-right"></i>											
										</a>
									</h5>
		                    	</div>
		                    </div>
		                </div>
		                
		                <div class="col-lg-4"> 
		                		                    
		                    <!-- PEDIDOS DE COMPRA - ÚLTIMOS PENDENTES -->
	                		<div class="widget-box transparent">
			                    
								<!-- TÍTULO DOS LANÇAMENTOS -->
		                		<div class="widget-header widget-header-flat">
									<h3 class="widget-title lighter">
										<i class="ace-icon fa fa-cart-arrow-down red bigger-130"></i><strong> Compras Pendentes</strong>
									</h3>
								</div>								
								
								<!-- LANÇAMENTOS -->
		                        <div class="widget-body">
									<div class="widget-main no-padding">
										<table class="table table-bordered table-striped" id="tabPcUltimo">
											<thead class="thin-border-bottom">
			                                	<tr>
			                                    	<th>No.</th>
			                                        <th>Data</th>
			                                        <th>Fornecedor</th>
			                                        <th>Valor (R$)</th>
			                                	</tr>
		                           			</thead>
			                           		<tbody></tbody>
			                        	</table>
			                        </div>
		                        </div>
		                    	<div class="text-right">
		                        	<h5>
		                        		<a href="#" onclick="document.getElementById('form_caixa').submit()">
											<form id="form_caixa" action="controller?action=listarPedidoCompra" method="post"></form>
											Ver todos os pedidos <i class="fa fa-arrow-circle-right"></i>											
										</a>
									</h5>
		                    	</div>
		                    </div>
		                </div>
		                
		                <div class="col-lg-4"> 
		                    
		                    <!-- PEDIDOS DE VENDA - ÚLTIMOS PENDENTES -->
	                		<div class="widget-box transparent">
			                    
								<!-- TÍTULO DOS LANÇAMENTOS -->
		                		<div class="widget-header widget-header-flat">
									<h3 class="widget-title lighter">
										<i class="ace-icon fa fa-cart-plus green bigger-130"></i><strong> Vendas Pendentes</strong>
									</h3>
								</div>
								
								<!-- LANÇAMENTOS -->
		                        <div class="widget-body">
									<div class="widget-main no-padding">
										<table class="table table-bordered table-striped" id="tabPvUltimo">
											<thead class="thin-border-bottom">
			                                	<tr>
			                                    	<th>No.</th>
			                                        <th>Data</th>
			                                        <th>Cliente</th>
			                                        <th>Valor (R$)</th>
			                                	</tr>
		                           			</thead>
			                           		<tbody></tbody>
			                        	</table>
			                        </div>
		                        </div>
		                    	<div class="text-right">
		                        	<h5>
		                        		<a href="#" onclick="document.getElementById('form_caixa').submit()">
											<form id="form_caixa" action="controller?action=listarPedidoVenda" method="post"></form>
											Ver todos pedidos <i class="fa fa-arrow-circle-right"></i>											
										</a>
									</h5>
		                    	</div>
		                    </div>
	                    </div>
	                    <!-- FIM -- SALDO -- CAIXA - ÚLTIMOS LANÇAMENTOS -->
	                </div>
	                <div class="row">
	                    <!-- GRÁFICO FATURAMENTO MENSAL -->
	                    <div class="col-lg-12">
							<div class="widget-box transparent">
							
								<!-- TÍTULO DO GRÁFICO -->
								<div class="widget-header widget-header-flat">
									<h3 class="widget-title lighter">
										<i class="ace-icon fa fa-signal"></i><strong> Balanço</strong>
									</h3>
								</div>
								
								<!-- GRÁFICO -->
								<div class="widget-body">
									<div class="widget-main padding-4">
										<div class="chart" id="grafico"></div>										
									</div>									
								</div>
							</div>
						</div>	    
	                    <!-- FIM GRÁFICO FATURAMENTO MENSAL -->	                                    
	                </div>
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
		
		
		<table id="tabBalanco" class="hidden">
			<thead>
				<tr>												
					<th></th>
					<th>Vendas</th>
					<th>Compras</th>
					<th>Balanco</th>
				</tr>
			</thead>
			<tbody></tbody>
		</table>
		 
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
	<script src="resources/js/jquery.2.2.4.min.js"></script>
	<!-- <script src="resources/js/jquery.2.1.1.min.js"></script> -->
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
    
    <!-- CARREGA DADOS DO CAIXA -->
	<script src="resources/js/ajaxcaixa.js"></script>
	
    <!-- PLUGINS DO GRÁFICO - HIGHCHARTS -->
    <script src="resources/js/highcharts.js"></script>
    <script src="resources/js/data.js"></script>
	<script src="resources/js/exporting.js"></script> 
	
	<!-- GRÁFICO FATURAMENTO  
	<script src="resources/js/grafico.js"></script>-->	
    
    <!-- <script>
	$(function () {
		
	    $('#grafico').highcharts({
	    	data:{table:document.getElementById('tabBalanco')},
	    	colors:['#999999','#FF0000','#000000'],
	        chart: {
	            zoomType: 'xy'
	        },
	        title: {
	            text: 'Average Monthly Temperature and Rainfall in Tokyo'
	        },
	        subtitle: {
	            text: 'Source: WorldClimate.com'
	        },
	        /*xAxis:[{
	        	categories:['Jan', 'Fev'],
	        }],*/
	        yAxis: [{ // Primary yAxis
	            labels: {
	                format: '{value}',
	                style: {
	                    color: Highcharts.getOptions().colors[1]
	                }
	            },
	            title: {
	                text: 'Balanco',
	                style: {
	                    color: Highcharts.getOptions().colors[1]
	                }
	            }
	        },{
	        	min:0,
	        	max:110,
	        	tickInterval:25,
	        	title:{
	        		text:'% Balanco',
	        		style:{
	        			color: Highcharts.getOptions().colors[0]
	        		}
	        	},
	        	labels:{
	        		format:'{value}%',
	        		style:{
	        			color: Highcharts.getOptions().colors[0]
	        		}
	        	},
	        	opposite:true
	        }],
	        tooltip: {
	            shared: true
	        },       
	        series: [{
	            name: 'Entrada',
	            type: 'column',
	            data: {table:document.getElementById('tabBalanco')},
	            dataLabels:{
	            	enable: true,
	            	rotation:-90,
	            	style:{
	            		fontSize:'13px',
	            		color:'#333333',
	            		align:'center',
	            	}
	            },
	        }, 
	        {
	            name: 'Saida',
	            type: 'column',
	            data: {table:document.getElementById('tabBalanco')}, 
	            dataLabels:{
	            	enable: true,
	            	rotation:-90,
	            	style:{
	            		fontSize:'13px',
	            		color:'#333333',
	            		align:'center',
	            	}
	            },
	        },
	        {
	            name: 'Balanco',
	            type: 'spline',

	            yAxis: 1,
	            data: {table:document.getElementById('tabBalanco')},            
	            dataLabels:{
	            	enable:true,
	            	style:{
	            		fontSize: '12px',
	            		color: '#FF0000',
	            	},
	            	formatter:function(){
	            		return Highchart.numberFormat(this.y, 0) + '%';
	            	},
	            	rotation:0,
	            	style:{
	            		fontSize:'13px',
	            		color:'#333333',
	            		align:'left',
	            	}
	            },
	            tooltip: {
	                valueSuffix: '%'
	            },
	        }]
	    });
	});
	</script> -->
</body>
</html>