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
				<li class="active">
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
						<li class="active">Tabela de Preços</li>
					</ul>				
					
				</div>
				<!-- FIM -- BARRA DE NAVEGAÇÃO DE CATEGORIAS LISTADAS -->
				
				<!-- CONTEÚDO DA PÁGINA -->
				<div class="page-content">
				
					<!-- CABEÇALHO DO CONTEÚDO -->
					<div class="page-header">
						<h1><strong>Tabela de Preços</strong>
							<small>
								<i class="ace-icon fa fa-angle-double-right"></i> <strong>preços de compra &amp; venda dos materiais</strong>
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
								<i class="ace-icon fa fa-check green"></i> Não lembra dos preços? Sem problemas, você está no lugar certo. Como pode ver, esta é a página a qual pode-se consultar a margem de preços negociados na compra ou venda dos materiais. 
							</div>
						</div>
					</div>					
					<!-- FIM -- MENSAGEM DE APRESENTAÇÃO DA PÁGINA -->					
					
	                <!-- TABELA DE PREÇOS -->
	                <div class="row">
	                	<div class="col-xs-12">
	                	
	                		<c:if test="${usuarioLogado.perfil == 'ADMINISTRADOR'}">
	                		<!-- BARRA DE FERRAMENTAS PARA A TABELA - VIA PLUGINS DATATABLES, TOOLTABLES  -->
							<div class="tableTools-container">
								<div class="btn-group btn-over-lap">
										
									<!-- BOTÃO NOVO MATERIAL -->
									<a href="#" class="btn btn-app btn-success btn-xs" role="cadastrar">
										<i class="ace-icon fa fa-plus bigger-130">&nbsp;<i class="ace-icon fa fa-dollar bigger-130"></i></i>Novo
									</a>
								</div>
							</div>										                	
	                		</c:if>
	                			                		
	                		<!-- JSTL -- CONDIÇÃO PARA EXIBIR A TABELA -->							
							<c:choose>
								<c:when test="${not empty produtos}">
								<!-- CASO HAJA PRODUTO -->
								
									<!-- RESULTADO DO NÚMEROS DE PRODUTOS -->
									<div class="table-header">
										<c:choose>
											<c:when test="${produtos.size() > 0}">
												<strong>Resultado: " ${produtos.size()} materiais cadastrados "</strong>
											</c:when>
											<c:otherwise>
												<strong>Resultado: " ${produtos.size()} material cadastrado "</strong>
											</c:otherwise>
										</c:choose>
									</div>	                		
	                			    	
	                			    <div class="col-lg-8 col-lg-offset-2 col-md-8 col-md-offset-2 col-sm-8 col-sm-offset-2"> 		
					                	<div class="widget-box transparent">
							                    
											<!-- TÍTULO DOS PREÇOS -->
						                	<div class="widget-header widget-header-flat">
												<h3 class="widget-title lighter">
													<i class="ace-icon fa fa-dollar"></i><strong> Preços Comercializados</strong>
												</h3>
											</div>
												
											<!-- PREÇOS -->
						                    <div class="widget-body">
												<div class="widget-main no-padding">
												
													<!-- TABELA -->
													<table class="table table-bordered table-striped">
													
														<!-- CABEÇALHO DA TABELA -->
														<thead class="thin-border-bottom">
							                               	<tr>
							                                   	<th class="hidden">Cód.</th>
							                                    <th>Material</th>
							                                    <c:if test="${usuarioLogado.perfil == 'ADMINISTRADOR' || usuarioLogado.perfil == 'COMPRADOR'}">
							                                    <th>Vlr. Compra (R$ / kg)</th>
							                                    </c:if>
							                                    <c:if test="${usuarioLogado.perfil == 'ADMINISTRADOR' || usuarioLogado.perfil == 'VENDEDOR'}">
							                                    <th>Vlr. Venda (R$ / kg)</th>
							                                    </c:if>
							                                    <c:if test="${usuarioLogado.perfil == 'ADMINISTRADOR'}">
							                                    <th>Ações</th>
							                                    </c:if>
							                              	</tr>
						                       			</thead>					                       			
						                       			
						                       			<!-- CORPO DA TABELA - REGISTROS E BOTÕES DE FUNCIONALIDADES -->
							                       		<tbody>
							                       		
							                       			<!-- MONTA AS LINHAS CONFORME A EXISTÊNCIA DE PRODUTOS -->
															<c:forEach var="produto" items="${produtos}">
																<tr>														
																	<!-- COLUNA ESCONDIDA DE CÓDIGO DOS REGISTROS -->
																	<td class="text-right hidden">${produto.codigo}</td>
																	
																	<!-- COLUNAS DE REGISTROS -->
																	<td><b>${produto.produto}</b></td>
																	<c:if test="${usuarioLogado.perfil == 'ADMINISTRADOR' || usuarioLogado.perfil == 'COMPRADOR'}">
																	<td class="text-right"><b class="blue">${produto.precoCompra}</b></td>
																	</c:if>
																	<c:if test="${usuarioLogado.perfil == 'ADMINISTRADOR' || usuarioLogado.perfil == 'VENDEDOR'}">
																	<td class="text-right"><b class="green">${produto.precoVenda}</b></td>
							                                    	</c:if>
							                                    	<c:if test="${usuarioLogado.perfil == 'ADMINISTRADOR'}">
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
																	</c:if>
																	<!-- COLUNA DE AÇÕES -->
							                                	</tr>
							                                </c:forEach>
							                                <!-- FIM -- MONTA AS LINHAS CONFORME A EXISTÊNCIA DE PRODUTOS -->			                                
							                            </tbody>
							                            <!-- FIM -- CORPO DA TABELA - REGISTROS E BOTÕES DE FUNCIONALIDADES -->
							                       	</table>
							                       	<!-- FIM -- TABELA -->
							                    </div>
						                    </div>
						                </div>						        	
						        	</div>
					            </c:when>								
								<c:otherwise>
								<!-- CASO NÃO HAJA PRODUTOS -->									
									<div class="table-header">
										Resultado: "${mensagem}"
									</div>		
								</c:otherwise>								
							</c:choose>
							<!-- JSTL -- CONDIÇÃO PARA EXIBIR A TABELA -->
							
							<!-- MODAL FORMULÁRIO PARA CADASTRAR -->
							<div id="modal-form-precos" class="modal fade" tabindex="-1" rel="modalcadastrar">
								<div class="modal-dialog">
									<div class="modal-content">
										<div class="modal-header">
											<button type="button" class="close" data-dismiss="modal">&times;</button>
											<h4 class="blue bigger">Por favor, preencha os campos...</h4>
										</div>

										<div class="modal-body">
											<div class="form-group">
												<label for="produto">Material *</label>
												<div>
													<input type="text" id="produto" name="produto" placeholder="Informe o produto..." size="48" />
												</div>
											</div>
											<div class="space-4"></div>
											<div class="form-group">
												<label for="precoCompra">Vlr. Compra (R$ / kg) *</label>
												<div>
													<input type="number" class="text-right" id="precoCompra" name="precoCompra" placeholder="Margem do preço de compra" size="25" />
												</div>
											</div>													
											<div class="space-4"></div>
											<div class="form-group">
												<label for="precoVenda">Vlr. Venda (R$ / kg) *</label>
												<div>
													<input type="number" class="text-right" id="precoVenda" name="precoVenda" placeholder="Margem do preço de venda" size="25" />
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
							<div id="modal-form-precos" class="modal fade" tabindex="-1" rel="modalexcluir">
								<div class="modal-dialog">
									<div class="modal-content">
										<div class="modal-header">
											<button type="button" class="close" data-dismiss="modal">&times;</button>
											<h4 class='red bigger'> Excluir... Tem certeza?</h4>
										</div>

										<div class="modal-body">
											<div class="form-group hidden">
												<label for="codigo">Cód.</label>
												<div>													
													<input type="text" id="codigo" name="codigo" size="8" />
												</div>
											</div>
											<div class="form-group">
												<label for="produto">Produto</label>
												<div>
													<input type="text" id="produto" name="produto" placeholder="Informe o produto..." size="48" />
												</div>
											</div>
											<div class="space-4"></div>
											<div class="form-group">
												<label for="precoCompra">Vlr. Compra (R$ / kg)</label>
												<div>
													<input type="number" class="text-right" id="precoCompra" name="precoCompra" placeholder="Margem do preço de compra" size="25" />
												</div>
											</div>													
											<div class="space-4"></div>
											<div class="form-group">
												<label for="precoVenda">Vlr. Venda (R$ / kg)</label>
												<div>
													<input type="number" class="text-right" id="precoVenda" name="precoVenda" placeholder="Margem do preço de venda" size="25" />
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
							
							<!-- MODAL FORMULÁRIO PARA ATUALIZAR -->
							<div id="modal-form-precos" class="modal fade" tabindex="-1" rel="modalatualizar">
								<div class="modal-dialog">
									<div class="modal-content">
										<div class="modal-header">
											<button type="button" class="close" data-dismiss="modal">&times;</button>
											<h4 class="blue bigger">Edite os campos desejados...</h4>
										</div>

										<div class="modal-body">
											<div class="form-group hidden">
												<label for="codigo">Cód.</label>
												<div>													
													<input type="text" id="codigo" name="codigo" size="8" />
												</div>
											</div>
											<div class="form-group">
												<label for="produto">Produto</label>
												<div>
													<input type="text" id="produto" name="produto" placeholder="Informe o produto..." size="48" />
												</div>
											</div>
											<div class="space-4"></div>
											<div class="form-group">
												<label for="precoCompra">Vlr. Compra (R$ / kg)</label>
												<div>
													<input type="number" class="text-right" id="precoCompra" name="precoCompra" placeholder="Margem do preço de compra" size="25" />
												</div>
											</div>													
											<div class="space-4"></div>
											<div class="form-group">
												<label for="precoVenda">Vlr. Venda (R$ / kg)</label>
												<div>
													<input type="number" class="text-right" id="precoVenda" name="precoVenda" placeholder="Margem do preço de venda" size="25" />
												</div>
											</div>										
										</div>

										<div class="modal-footer">
											<button id="btnCancelar" class="btn btn-sm" data-dismiss="modal">
												<i class="ace-icon fa fa-times"></i> Cancelar
											</button>
											<button id="btnSalvar" class="btn btn-sm btn-primary">
												<i class="ace-icon fa fa-check"></i> Salvar
											</button>											
										</div>										
									</div>									
								</div>
							</div>
							<!-- FIM -- MODAL FORMULÁRIO PARA ATUALIZAR -->
							
			        	</div>
	                </div>
	                <!-- FIM -- TABELA DE PREÇOS -->
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
    
    <!-- AJAX - SUBMIT FORMS - RECARREGA A PÁGINA QUANDO O MODAL CADASTRAR E EXCLUIR SÃO FECHADOS -->
    <script src="resources/js/ajaxprecos.js"></script>
    
    <!-- EDITAR EXCLUIR -->
    <script src="resources/js/manipulatabelaprecos.js"></script>
    
</body>
</html>