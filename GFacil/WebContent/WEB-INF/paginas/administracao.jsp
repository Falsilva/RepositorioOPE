<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql"%>

<!DOCTYPE html>
<html lang="pt-br">
<head>	
	<meta charset="utf-8" />
	
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
	
	
	<title>MDJ Papeis</title>
</head>
<body class="no-skin">

	
	<!--=================== BLOCO DA BARRA DE NAVEGA��O SUPERIOR E CONTE�DO DA P�GINA ====================-->
	
	
	<!-- BARRA DE NAVEGA��O -->
	<div id="navbar" class="navbar navbar-default">
	
		<!-- CONTE�DO DA BARRA DE NAVEGA��O -- SUPERIOR -->
		<div id="navbar-container" class="navbar-container">
			
			<!-- BOT�O DE ACESSO AO MENU PARA TELAS PEQUENAS -- ESSE BOT�O ALTERNA A EXIBI��O DA BARRA DE NAVEGA��O LATERAL -- MENU LATERAL -->
			<button type="button" class="navbar-toggle menu-toggler pull-left" id="menu-toggler" data-target="#sidebar">
				<span class="sr-only">Toggle sidebar</span>
				<span class="icon-bar"></span>
				<span class="icon-bar"></span>
				<span class="icon-bar"></span>
			</button>
			
			<!-- �CONE DA BARRA DE NAVEGA��O -->
			<div class="navbar-header pull-left">
				<a href="#" class="navbar-brand">
					<small>
						<i class="fa fa-recycle"></i> MDJ Pap�is
					</small>
				</a>
			</div>
			
			<!-- BOT�ES DA BARRA DE NAVEGA��O -->
			<div class="navbar-buttons navbar-header pull-right" role="navigation">
				<ul class="nav ace-nav">				
										
					<!-- BOT�O AZUL COM NOME DE USU�RIO -->
					<li class="azul-escuro-nosso">
					
						<!-- DESIGN DO BOT�O -->
						<a data-toggle="dropdown" class="dropdown-toggle" href="#">
							<i class="ace-icon fa fa-user"></i>
							<span class="user-info">
								<small>Bem-vindo,</small> ${usuarioLogado.nome}
							</span>
							<i class="ace-icon fa fa-caret-down"></i>
						</a>
						
						<!-- LISTA DE OP��ES DO BOT�O -->
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
		<!-- FIM -- CONTE�DO DA BARRA DE NAVEGA��O -- SUPERIOR -->		
	</div>
	<!-- FIM -- BARRA DE NAVEGA��O -->
		
		
	<!--============= DIVISA DOS BLOCOS DE BARRA DE NAVEGA��O SUPERIOR E CONTE�DO DA P�GINA ==============-->
		
		
	<!-- CONTE�DO DA P�GINA -- INCLUI MENU LATERAL E CONTE�DO PRINCIPAL-->
	<div class="main-container" id="main-container">
		
		<!--===================================== BLOCO MENU LATERAL =====================================-->
		
		
		<!-- BARRA DE NAVEGA��O LATERAL -- MENU LATERAL -- NO MODO TELA PEQUENA, ESSE MENU � ACESSADO PELO BOT�O L� EMCIMA COM O id="menu-toggler"  -->
		<div id="sidebar" class="sidebar responsive">
		
			<!-- BARRA LATERAL DE ATALHOS -->
			<div class="sidebar-shortcuts" id="sidebar-shortcuts">
					
				<!-- REL�GIO - QUANDO EXIBI��O TOTAL DO MENU  -->
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

				<!-- REL�GIO - QUANDO EXIBI��O RECOLHIDA DO MENU  -->
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
								<i class="menu-icon fa fa-caret-right"></i>	Usu�rios
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
				
				<!-- ITEM 5 -- TABELA DE PRE�OS -->
				<li class="">
					<a href="#" onclick="document.getElementById('form_precos').submit()">
						<form id="form_precos" action="controller?action=listarProdutos" method="post"></form>
						<i class="menu-icon fa fa-dollar"></i>
						<span class="menu-text"> Tabela de Pre�os </span>
					</a>
					<b class="arrow"></b>
				</li>
								
			</ul>
			<!-- FIM -- LISTA DE ITENS DO MENU -->
			
			<!-- �CONE DE SETA QUE RECOLHE O MENU LATERAL PARA A ESQUERDA -->
			<div class="sidebar-toggle sidebar-collapse" id="sidebar-collapse">
				<i class="ace-icon fa fa-angle-double-left" data-icon1="ace-icon fa fa-angle-double-left" data-icon2="ace-icon fa fa-angle-double-right"></i>
			</div>
						
		</div>
		<!-- FIM -- BARRA DE NAVEGA��O LATERAL -- MENU LATERAL -->
		
		
		<!--=================== DIVISA DOS BLOCOS DO MENU LATERAL E CONTE�DO PRINCIPAL ===================-->
		
		
		<!-- CONTE�DO PRINCIPAL DA P�GINA -->
		<div class="main-content">
			<div class="main-content-inner">
			
				<!-- BARRA DE NAVEGA��O DE CATEGORIAS LISTADAS -->
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
				<!-- FIM -- BARRA DE NAVEGA��O DE CATEGORIAS LISTADAS -->
				
				<!-- CONTE�DO DA P�GINA -->
				<div class="page-content">
				
					<!-- CABE�ALHO DO CONTE�DO -->
					<div class="page-header">
						<h1><strong>Dashboard</strong>
							<small>
								<i class="ace-icon fa fa-angle-double-right"></i> <strong>vis�o geral &amp; estat�sticas</strong>
							</small>
							
						</h1>
					</div>
					<!-- FIM -- CABE�ALHO DO CONTE�DO -->
					
					<!-- MENSAGEM DE APRESENTA��O DA VIS�O GERAL - DASHBOARD -->
					<div class="row">
						<div class="col-xs-12">						
							<div class="alert alert-block alert-success">
								<button type="button" class="close" data-dismiss="alert">
									<i class="ace-icon fa fa-times"></i>
								</button>
								<i class="ace-icon fa fa-check green"></i> Aqui � onde tudo come�a! Nesta p�gina, voc� possui uma vis�o geral do que � mais importante para gerenciar melhor o seu neg�cio, como o saldo em caixa, os �ltimos lan�amentos e pend�ncias,<!--  e uma vis�o gr�fica de como anda o balan�o geral,--> al�m de acesso ao menu com as principais funcionalidades que sempre o acompanha durante a navega��o do sistema.
								<!-- MENSAGEM, SE HOUVER ALERTAS: Aqui � onde tudo come�a! Nesta p�gina, voc� possui uma vis�o geral do que � mais importante para gerenciar melhor o seu neg�cio, como notifica��es importantes, tarefas pendentes, o saldo em caixa e uma vis�o gr�fica de como anda o faturamento, al�m de acesso ao menu com as principais funcionalidades que sempre o acompanha durante a navega��o do sistema. -->
							</div>
						</div>
					</div>					
					<!-- FIM -- MENSAGEM DE APRESENTA��O DA VIS�O GERAL - DASHBOARD -->
					
					<!-- NOTIFICA��ES 
					<div class="row">
						<div class="col-xs-12">						
							<div class="widget-box transparent">
							
								<!-- T�TULO DOS PAIN�IS 
		                		<div class="widget-header widget-header-flat">									
				                    <h3 class="widget-title lighter"><strong>Avisos</strong></h3>
				                </div>
				                
				                <!-- PAIN�IS 
				                <div class="widget-body">
									<div class="widget-main no-padding">
									
										<!-- PAINEL DE NOTIFICA��ES IMPORTANTES 		                    
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
	                <!-- FIM -- NOTIFICA��ES -->
	                
	                <!-- SALDO -- �LTIMOS LAN�AMENTOS -- GR�FICO FATURAMENTO MENSAL -->
	                <div class="row">
	                
	                	<!-- SALDO -- �LTIMOS LAN�AMENTOS -->
	                	<div class="col-lg-4">
	                		
	                		<!-- CAIXA - SALDO -->
	                		<div class="widget-box transparent">
	                		
	                			<!-- T�TULO DO SALDO -->
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
	                		<!-- CAIXA - �LTIMOS LAN�AMENTOS -->
	                		<div class="widget-box transparent">
			                    
								<!-- T�TULO DOS LAN�AMENTOS -->
		                		<div class="widget-header widget-header-flat">
									<h3 class="widget-title lighter">
										<i class="ace-icon fa fa-money"></i><strong> �ltimos Lan�amentos</strong>
									</h3>
								</div>
								
								<!-- LAN�AMENTOS -->
		                        <div class="widget-body">
									<div class="widget-main no-padding">
										<table class="table table-bordered table-striped" id="tabCxUltimo">
											<thead class="thin-border-bottom">
			                                	<tr>
			                                    	<th>Item</th>
			                                        <th>Data</th>
			                                        <th>Descri��o</th>
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
		                		                    
		                    <!-- PEDIDOS DE COMPRA - �LTIMOS PENDENTES -->
	                		<div class="widget-box transparent">
			                    
								<!-- T�TULO DOS LAN�AMENTOS -->
		                		<div class="widget-header widget-header-flat">
									<h3 class="widget-title lighter">
										<i class="ace-icon fa fa-cart-arrow-down red bigger-130"></i><strong> Compras Pendentes</strong>
									</h3>
								</div>								
								
								<!-- LAN�AMENTOS -->
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
		                    
		                    <!-- PEDIDOS DE VENDA - �LTIMOS PENDENTES -->
	                		<div class="widget-box transparent">
			                    
								<!-- T�TULO DOS LAN�AMENTOS -->
		                		<div class="widget-header widget-header-flat">
									<h3 class="widget-title lighter">
										<i class="ace-icon fa fa-cart-plus green bigger-130"></i><strong> Vendas Pendentes</strong>
									</h3>
								</div>
								
								<!-- LAN�AMENTOS -->
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
	                    <!-- FIM -- SALDO -- CAIXA - �LTIMOS LAN�AMENTOS -->
	                </div>
	                <div class="row">
	                    <!-- GR�FICO FATURAMENTO MENSAL -->
	                    <div class="col-lg-12">
							<div class="widget-box transparent">
							
								<!-- T�TULO DO GR�FICO -->
								<div class="widget-header widget-header-flat">
									<h3 class="widget-title lighter">
										<i class="ace-icon fa fa-signal"></i><strong> Balan�o</strong>
									</h3>
								</div>
								
								<!-- GR�FICO -->
								<div class="widget-body">
									<div class="widget-main padding-4">
										<div class="chart" id="grafico"></div>										
									</div>									
								</div>
							</div>
						</div>	    
	                    <!-- FIM GR�FICO FATURAMENTO MENSAL -->	                                    
	                </div>
				</div>
				<!-- FIM -- CONTE�DO DA P�GINA -->
			</div>
		</div>
		<!-- FIM -- CONTE�DO PRINCIPAL DA P�GINA -->
		
		
		<!--======================= DIVISA DOS BLOCOS DO CONTE�DO PRINCIPAL E RODAP� =======================-->
		
		
		<!-- RODAP� DA P�GINA -->
		<div class="footer">
			<div class="footer-inner">
			
				<!-- CONTE�DO DO RODAP� -->
				<div class="footer-content">
				
					<!-- MARCA REGISTRADA - COPYRIGHT -->
					<span class="bigger-120">
						<span class="green bolder">Gerencia F�cil</span> &reg; 2014-2016
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
		 
		<!-- FIM -- RODAP� DA P�GINA -->		
		
		<!-- �CONE NO CANTO DIREITO INFERIOR DA TELA AO DESCER - ELE APARECE QUANDO DESCE - FAZ VOLTAR PARA O TOPO -->
		<a href="#" id="btn-scroll-up" class="btn btn-sm btn-scroll-up btn-inverse">
			<i class="ace-icon fa fa-angle-double-up icon-only bigger-110"></i>
		</a>	
	</div>
	<!-- FIM -- CONTE�DO DA P�GINA -- INCLUI MENU LATERAL E CONTE�DO PRINCIPAL-->
	
	
	
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
	
	<!-- ONTOUCH MOBILE - N�O TESTADO -->
	<script type="text/javascript">
		if('ontouchstart' in document.documentElement) document.write("<script src='resources/js/jquery.mobile.custom.min.js'>"+"<"+"/script>");
	</script>
	
    <!-- BOOTSTRAP SCRIPTS -->
    <script src="resources/js/bootstrap.min.js"></script>
    
    <!-- ACE CONFIGURA��ES DESTA P�GINA -->
	<script src="resources/js/ace-elements.min.js"></script>
	<script src="resources/js/ace.min.js"></script>	
    
    <!-- REL�GIO -->
    <script src="resources/js/relogio.js"></script>
    
    <!-- CARREGA DADOS DO CAIXA -->
	<script src="resources/js/ajaxcaixa.js"></script>
	
    <!-- PLUGINS DO GR�FICO - HIGHCHARTS -->
    <script src="resources/js/highcharts.js"></script>
    <script src="resources/js/data.js"></script>
	<script src="resources/js/exporting.js"></script> 
	
	<!-- GR�FICO FATURAMENTO  
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