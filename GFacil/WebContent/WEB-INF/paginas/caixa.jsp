<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

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
								<form id="form_compra" action="controller?action=compra" method="post"></form>
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
				<li class="active">
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
						<li class="active">Caixa</li>
					</ul>				
					
				</div>
				<!-- FIM -- BARRA DE NAVEGAÇÃO DE CATEGORIAS LISTADAS -->
				
				<!-- CONTEÚDO DA PÁGINA -->
				<div class="page-content">
				
					<!-- CABEÇALHO DO CONTEÚDO -->
					<div class="page-header">
						<h1><strong>Caixa</strong>
							<small>
								<i class="ace-icon fa fa-angle-double-right"></i> <strong>entradas &amp; saídas financeiras</strong>
							</small>
							
						</h1>
					</div>
					<!-- FIM -- CABEÇALHO DO CONTEÚDO -->
					
					<!-- MENSAGEM DE APRESENTAÇÃO DA PÁGINA -->
					<div class="row">
						<div class="col-xs-12">						
							<div class="alert alert-block alert-success">
								<button type="button" class="close" data-dismiss="alert">
									<i class="ace-icon fa fa-times"></i>
								</button>
								<i class="ace-icon fa fa-check green"></i> Como estão as finanças? Veja como estão os gastos e recebimentos. Um saldo positivo é ótimo, mas cuidado com o limite do fluxo de caixa estabelecido. Fique atento! 
							</div>
						</div>
					</div>					
					<!-- FIM -- MENSAGEM DE APRESENTAÇÃO DA PÁGINA -->					
					
					
					<div class="row">
					
						<!-- CAIXA - SALDO -->
						<div class="col-xs-4">							
	                		<div class="widget-box transparent">
	                		
	                			<!-- TÍTULO DO SALDO -->
		                		<div class="widget-header widget-header-flat">
									<h3 class="widget-title lighter">
										<i class="ace-icon fa fa-money"></i><strong> Saldo</strong>
									</h3>
								</div>
								
								<!-- SALDO -->
								<div class="widget-body">
									<div class="widget-main no-padding">
										<div class="text-right">
											<h2><strong><span class="green">R$ 3395,00</span></strong></h2>
										</div>
									</div>
								</div>
	                		</div>
	                	</div>
	                </div>
	                
	                <!-- LINHA DIVISORA -->
	                <div class="hr hr-double hr-dotted hr18"></div>	                
	                
	                <!-- ABAS DE MOVIMENTAÇÕES MENSAIS -->
	                <div class="row">	                
	                
	                	<!-- ABAS -->
	                	<div class="col-xs-12">
							<div class="tabbable">
							
								<!-- TÍTULO DAS ABAS -->
								<ul class="nav nav-tabs padding-12 tab-color-blue background-blue" id="meses">
									<li>
										<a data-toggle="tab" href="#janeiro">Janeiro</a>
									</li>
									<li>
										<a data-toggle="tab" href="#fevereiro">Fevereiro</a>
									</li>
									<li>
										<a data-toggle="tab" href="#marco">Março</a>
									</li>
									<li>
										<a data-toggle="tab" href="#abril">Abril</a>
									</li>
									<li class="active">
										<a data-toggle="tab" href="#maio">Maio</a>
									</li>
									<li>
										<a data-toggle="tab" href="#junho">Junho</a>
									</li>
									<li>
										<a data-toggle="tab" href="#julho">Julho</a>
									</li>
									<li>
										<a data-toggle="tab" href="#agosto">Agosto</a>
									</li>
									<li>
										<a data-toggle="tab" href="#setembro">Setembro</a>
									</li>
									<li>
										<a data-toggle="tab" href="#outubro">Outubro</a>
									</li>
									<li>
										<a data-toggle="tab" href="#novembro">Novembro</a>
									</li>
									<li>
										<a data-toggle="tab" href="#dezembro">Dezembro</a>
									</li>
								</ul>
								<!-- FIM -- TÍTULO DAS ABAS -->
								
								<!-- CONTEÚDO DAS ABAS -->
								<div class="tab-content">
									
									<!-- ABA JANEIRO -->
									<div id="janeiro" class="tab-pane">
										<h1>Sem Movimentações</h1>
									</div>
									<!-- FIM -- ABA JANEIRO -->
									
									<!-- ABA FEVEREIRO -->
									<div id="fevereiro" class="tab-pane">
										<h1>Sem Movimentações</h1>
									</div>
									<!-- FIM -- ABA FEVEREIRO -->
									
									<!-- ABA MARÇO -->
									<div id="marco" class="tab-pane">
										<h1>Sem Movimentações</h1>
									</div>
									<!-- FIM -- ABA MARÇO -->
									
									<!-- ABA ABRIL -->
									<div id="abril" class="tab-pane">
										<h1>Sem Movimentações</h1>
									</div>
									<!-- FIM -- ABA ABRIL -->
									
									<!-- ABA MAIO -->
									<div id="maio" class="tab-pane in active">
									
										<!-- BALANÇOS -->
										<div class="row">
										
											<!-- CAIXA - BALANÇO MENSAL -->
						                	<div class="col-xs-6 pull-left">							
						                		<div class="widget-box transparent">
						                		
						                			<!-- TÍTULO DO BALANÇO MENSAL -->
							                		<div class="widget-header widget-header-flat">
														<h3 class="widget-title lighter">
															<i class="ace-icon fa fa-money"></i><strong> Balanço Mensal</strong>
														</h3>
													</div>
													
													<!-- BALANÇO GERAL MENSAL -->
													<div class="widget-body">
														<div class="widget-main no-padding">
															<table class="table table-bordered table-striped">
																<thead class="thin-border-bottom">
									                               	<tr>
									                                   	<th class="text-center"><h4><b>Movimentação</b></h4></th>
									                                   	<th class="text-center"><h4><b>Valor (R$)</b></h4></th>
									                              	</tr>
								                       			</thead>
									                       		<tbody>
									                       			<tr>
									                                    <td><b class="green"><h4>Receitas (R$)</b></h4></td>
									                                    <td class="text-right"><h4><b class="green">+ 1500,00</b></h4></td>				                                    
									                                </tr>
									                                <tr>
									                                    <td><b class="red"><h4>Despesas (R$)</b></h4></td>
									                                    <td class="text-right"><h4><b class="red">- 1460,00</b></h4></td>				                                    
									                                </tr>
									                                <tr>
									                                    <td><b class="blue"><h4>Resultado (R$)</b></h4></td>
									                                    <td class="text-right"><h4><b class="blue">+ 40,00</b></h4></td>				                                    
									                                </tr>			                                
									                            </tbody>
									                       	</table>
														</div>
													</div>
						                		</div>
						                	</div>
						                	<!-- FIM -- CAIXA - BALANÇO MENSAL -->
						                	
						                	<!-- CAIXA - BALANÇO GERAL -->
						                	<div class="col-xs-6 pull-right">							
						                		<div class="widget-box transparent">
						                		
						                			<!-- TÍTULO DO BALANÇO GERAL -->
							                		<div class="widget-header widget-header-flat">
														<h3 class="widget-title lighter">
															<i class="ace-icon fa fa-money"></i><strong> Balanço Geral - Ano 2016</strong>
														</h3>
													</div>
													
													<!-- BALANÇO GERAL - ANO 2016 -->
													<div class="widget-body">
														<div class="widget-main no-padding">
															<table class="table table-bordered table-striped">
																<thead class="thin-border-bottom">
									                               	<tr>
									                                   	<th class="text-center"><h4><b>Movimentação</b></h4></th>
									                                   	<th class="text-center"><h4><b>Valor (R$)</b></h4></th>
									                              	</tr>
								                       			</thead>
									                       		<tbody>
									                       			<tr>
									                                    <td><b class="green"><h4>Receitas (R$)</b></h4></td>
									                                    <td class="text-right"><h4><b class="green">+ 300395,00</b></h4></td>				                                    
									                                </tr>
									                                <tr>
									                                    <td><b class="red"><h4>Despesas (R$)</b></h4></td>
									                                    <td class="text-right"><h4><b class="red">- 250798,00</b></h4></td>				                                    
									                                </tr>
									                                <tr>
									                                    <td><b class="blue"><h4>Resultado (R$)</b></h4></td>
									                                    <td class="text-right"><h4><b class="blue">+ 49597,00</b></h4></td>				                                    
									                                </tr>			                                
									                            </tbody>
									                       	</table>
														</div>
													</div>
						                		</div>
						                	</div>
						                	<!-- FIM -- CAIXA - BALANÇO GERAL -->
						                </div>
						                <!-- FIM BALANÇOS -->
						                
						                <!-- LINHA DIVISORA -->
										<div class="hr hr-double hr-dotted hr18"></div>
										
						                <!-- TABELA DE MOVIMENTAÇÕES - MAIO -->
						                <div class="row">	                	
						                	<div class="col-xs-12">									                	
						                	
							                	<div class="widget-box transparent">
									                    
													<!-- TÍTULO DAS MOVIMENTAÇÕES -->
								                	<div class="widget-header widget-header-flat">
														<h3 class="widget-title lighter">
															<i class="ace-icon fa fa-money"></i><strong> Movimentações</strong>
														</h3>
													</div>
														
													<!-- MOVIMENTAÇÕES -->
								                    <div class="widget-body">
														<div class="widget-main no-padding">
															<table class="table table-bordered table-striped">
																<thead class="thin-border-bottom">
									                               	<tr>
									                                   	<th>Item</th>
									                                   	<th>Data</th>
									                                    <th>Descrição</th>
									                                    <th>Valor (R$)</th>
									                              	</tr>
								                       			</thead>
									                       		<tbody>
									                       			<tr>
									                                   	<td class="text-right"><b>1</b></td>
									                                    <td><b>10/05/2016</b></td>
									                                    <td><b>Pedido de Compra nº 12</b></td>
									                                    <td class="text-right"><b class="red">- 800,00</b></td>				                                    
									                                </tr>
									                                <tr>
									                                   	<td class="text-right"><b>2</b></td>
									                                    <td><b>23/05/2016</b></td>
									                                    <td><b>Manutenção da Empilhadeira NF. 34254</b></td>
									                                    <td class="text-right"><b class="red">- 540,00</b></td>				                                    
									                                </tr>
									                                <tr>
									                                   	<td class="text-right"><b>3</b></td>
									                                    <td><b>23/05/2016</b></td>
									                                    <td><b>Pedido de Venda nº 6</b></td>
									                                    <td class="text-right"><b class="green">+ 1500,00</b></td>				                                    
									                                </tr>
									                                <tr>
									                                   	<td class="text-right"><b>4</b></td>
									                                    <td><b>29/05/2016</b></td>
									                                    <td><b>Água</b></td>
									                                    <td class="text-right"><b class="red">- 120,00</b></td>				                                    
									                                </tr>			                                
									                            </tbody>
									                       	</table>
									                    </div>
								                    </div>
								                </div>
								        	</div>
						                </div>
						                <!-- FIM -- TABELA DE MOVIMENTAÇÕES - MAIO -->
									</div>
									<!-- FIM -- ABA MAIO -->
									
									<!-- ABA JUNHO -->
									<div id="junho" class="tab-pane">
										<h1>Sem Movimentações</h1>
									</div>
									<!-- FIM -- ABA JUNHO -->
									
									<!-- ABA JULHO -->
									<div id="julho" class="tab-pane">
										<h1>Sem Movimentações</h1>
									</div>
									<!-- FIM -- ABA JULHO -->
									
									<!-- ABA AGOSTO -->
									<div id="agosto" class="tab-pane">
										<h1>Sem Movimentações</h1>
									</div>
									<!-- FIM -- ABA AGOSTO -->
									
									<!-- ABA SETEMBRO -->
									<div id="setembro" class="tab-pane">
										<h1>Sem Movimentações</h1>
									</div>
									<!-- FIM -- ABA SETEMBRO -->
									
									<!-- ABA OUTUBRO -->
									<div id="outubro" class="tab-pane">
										<h1>Sem Movimentações</h1>
									</div>
									<!-- FIM -- ABA OUTUBRO -->
									
									<!-- ABA NOVEMBRO -->
									<div id="novembro" class="tab-pane">
										<h1>Sem Movimentações</h1>
									</div>
									<!-- FIM -- ABA NOVEMBRO -->
									
									<!-- ABA DEZEMBRO -->
									<div id="dezembro" class="tab-pane">
										<h1>Sem Movimentações</h1>
									</div>
									<!-- FIM -- ABA DEZEMBRO -->
								</div>
								<!-- FIM -- CONTEÚDO DAS ABAS -->
							</div>
						</div>
						<!-- FIM -- ABAS -->	                	
					</div>					
					<!-- FIM -- ABAS DE MOVIMENTAÇÕES MENSAIS -->
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
    
</body>
</html>