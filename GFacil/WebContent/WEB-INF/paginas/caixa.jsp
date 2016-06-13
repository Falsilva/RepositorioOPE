<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

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
				<li class="">
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
				<li class="active">
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
						<li class="active">Caixa</li>
					</ul>				
					
				</div>
				<!-- FIM -- BARRA DE NAVEGA��O DE CATEGORIAS LISTADAS -->
				
				<!-- CONTE�DO DA P�GINA -->
				<div class="page-content">
				
					<!-- CABE�ALHO DO CONTE�DO -->
					<div class="page-header">
						<h1><strong>Caixa</strong>
							<small>
								<i class="ace-icon fa fa-angle-double-right"></i> <strong>entradas &amp; sa�das financeiras</strong>
							</small>
							
						</h1>
					</div>
					<!-- FIM -- CABE�ALHO DO CONTE�DO -->
					
					<!-- MENSAGEM DE APRESENTA��O DA P�GINA -->
					<div class="row">
						<div class="col-xs-12">						
							<div class="alert alert-block alert-success">
								<button type="button" class="close" data-dismiss="alert">
									<i class="ace-icon fa fa-times"></i>
								</button>
								<i class="ace-icon fa fa-check green"></i> Como est�o as finan�as? Veja como est�o os gastos e recebimentos. Um saldo positivo � �timo, mas cuidado com o limite do fluxo de caixa estabelecido. Fique atento! 
							</div>
						</div>
					</div>					
					<!-- FIM -- MENSAGEM DE APRESENTA��O DA P�GINA -->					
					
					
					<div class="row">
					
						<!-- CAIXA - SALDO -->
						<div class="col-xs-4 pull-left">							
	                		<div class="widget-box transparent">
	                		
	                			<!-- T�TULO DO SALDO -->
		                		<div class="widget-header widget-header-flat">
									<h3 class="widget-title lighter">
										<i class="ace-icon fa fa-money"></i><strong> Saldo</strong>
									</h3>
								</div>
								
								<!-- SALDO -->
								<div class="widget-body">
									<div class="widget-main no-padding">
										<div class="text-right" role="saldoTotal"></div>
									</div>
								</div>
	                		</div>
	                		
	                		<!-- SELECAO DE VISUALIZA��O DO BALAN�O E MOVIMENTA��ES POR ANO -->
	                		<div class="widget-box transparent">
	                			<!-- T�TULO DA VISUALIZA��O -->
		                		<div class="widget-header widget-header-flat">
									<h3 class="widget-title lighter">
										<i class="ace-icon fa fa-clock-o"></i><strong> ANO</strong>
									</h3>
								</div>
								<div class="widget-body">
									<div class="widget-main no-padding">
										<div class="form-group">
											<!-- <label for="anoBalanco">Ano</label> -->								
											<div class="text-right">
												<h4>
												<select class="chosen-select" data-placeholder="Escolha o ano..." name="anoBalanco" id="anoBalanco"></select>
				                				</h4>
				                			</div>
			                			</div>
	                				</div>
	                			</div>
	                		</div>
	                	</div>
	                	
	                	<!-- CAIXA - BALAN�O GERAL -->
						<div class="col-xs-6 pull-right">							
						  	<div class="widget-box transparent">
						                		
						        <!-- T�TULO DO BALAN�O GERAL -->
							    <div class="widget-header widget-header-flat">
									<h3 class="widget-title lighter">
										<i class="ace-icon fa fa-money"></i><strong> Balan�o Geral - Ano <span id="balancoGeralAnoVigente"></span></strong>
									</h3>
								</div>
													
								<!-- BALAN�O GERAL - ANO 2016 -->
								<div class="widget-body">
									<div class="widget-main no-padding">
										<table class="table table-bordered table-striped" role="balancoGeral">
											<thead class="thin-border-bottom">
								        	   	<tr>
								                   	<th class="text-center"><h4><b>Movimenta��o</b></h4></th>
								            		<th class="text-center"><h4><b>Valor (R$)</b></h4></th>
								    			</tr>
									        </thead>
									        <tbody>
										       	<tr>
									              	<td><b class="green"><h4>Receitas (R$)</b></h4></td>
									                <td class="text-right" role="receitas"></td>				                                    
									            </tr>
									        	<tr>
									              	<td><b class="red"><h4>Despesas (R$)</b></h4></td>
									                <td class="text-right" role="despesas"></td>				                                    
									            </tr>
									            <tr>
									              	<td><b class="blue"><h4>Resultado (R$)</b></h4></td>
									                <td class="text-right" role="saldo"></td>				                                    
									            </tr>			                                
									        </tbody>
										</table>
									</div>
								</div>
						    </div>
						</div>
						<!-- FIM -- CAIXA - BALAN�O GERAL -->	                	
	                </div>
	                
	                <!-- LINHA DIVISORA -->
	                <div class="hr hr-double hr-dotted hr18"></div>	                
	                
	                <!-- ABAS DE MOVIMENTA��ES MENSAIS -->
	                <div class="row">	                
	                
	                	<!-- ABAS -->
	                	<div class="col-xs-12">
							<div class="tabbable" role="abasMeses">
							
								<!-- T�TULO DAS ABAS -->
								<ul class="nav nav-tabs padding-12 tab-color-blue background-blue" id="meses">
									<li>
										<a data-toggle="tab" href="#janeiro" role="mes" id="1">Janeiro</a>
									</li>
									<li>
										<a data-toggle="tab" href="#fevereiro" role="mes" id="2">Fevereiro</a>
									</li>
									<li>
										<a data-toggle="tab" href="#marco" role="mes" id="3">Mar�o</a>
									</li>
									<li>
										<a data-toggle="tab" href="#abril" role="mes" id="4">Abril</a>
									</li>
									<li>
										<a data-toggle="tab" href="#maio" role="mes" id="5">Maio</a>
									</li>
									<li>
										<a data-toggle="tab" href="#junho" role="mes" id="6">Junho</a>
									</li>
									<li>
										<a data-toggle="tab" href="#julho" role="mes" id="7">Julho</a>
									</li>
									<li>
										<a data-toggle="tab" href="#agosto" role="mes" id="8">Agosto</a>
									</li>
									<li>
										<a data-toggle="tab" href="#setembro" role="mes" id="9">Setembro</a>
									</li>
									<li>
										<a data-toggle="tab" href="#outubro" role="mes" id="10">Outubro</a>
									</li>
									<li>
										<a data-toggle="tab" href="#novembro" role="mes" id="11">Novembro</a>
									</li>
									<li>
										<a data-toggle="tab" href="#dezembro" role="mes" id="12">Dezembro</a>
									</li>
								</ul>
								<!-- FIM -- T�TULO DAS ABAS -->
								
								<!-- CONTE�DO DAS ABAS -->
								<div class="tab-content" id="conteudoMeses"></div>
								<!-- FIM -- CONTE�DO DAS ABAS -->
							</div>
						</div>
						<!-- FIM -- ABAS -->	                	
					</div>					
					<!-- FIM -- ABAS DE MOVIMENTA��ES MENSAIS -->
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
    
    <!-- REL�GIO -->
    <script src="resources/js/relogio.js"></script>     
    
	<!-- CARREGA DADOS DO CAIXA -->
	<script src="resources/js/ajaxcaixa.js"></script>
	
</body>
</html>