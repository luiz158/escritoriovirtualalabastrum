<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE HTML>
<html lang="pt-br">
	<head>
		<title> Escrit�rio virtual Alabastrum </title>
		<link type="text/css" href="<c:url value="/css/bootstrap.css"/>" rel="stylesheet" />
		<link type="text/css" href="<c:url value="/css/bootstrap-alterado.css"/>" rel="stylesheet" />
		<link type="text/css" href="<c:url value="/css/bootstrap-responsive.css"/>" rel="stylesheet" />
		<link type="text/css" href="<c:url value="/css/estilo.css"/>" rel="stylesheet" />
		<link type="text/css" href="<c:url value="/css/jquery-ui-1.9.2.custom.min.css"/>" rel="stylesheet" />
		<link type="text/css" href="<c:url value="/css/menu-accordion.css"/>" rel="stylesheet" />
		<script type="text/javascript" src="<c:url value="/js/jquery-1.8.3.js"/>"></script>
		<script type="text/javascript" src="<c:url value="/js/jquery-ui-1.9.2.custom.min.js"/>"></script>
		<script type="text/javascript" src="<c:url value="/js/jquery.collapse.js"/>"></script>
		<script type="text/javascript" src="<c:url value="/js/jquery.collapse_storage.js"/>"></script>
		<script type="text/javascript" src="<c:url value="/js/jquery.collapse_cookie_storage.js"/>"></script>		
		<script type="text/javascript" src="<c:url value="/js/jquery.validate.js"/>"></script>
		<script type="text/javascript" charset="utf-8" src="<c:url value="/js/scripts.gerais.js"/>"></script>			
		<script type="text/javascript" src="<c:url value="/js/submenu.js"/>"></script>
		<script type="text/javascript" src="<c:url value="/js/form.requerido.js"/>"></script>	
	</head>
	
	<noscript>
		<meta http-equiv="Refresh" content="0;url=<c:url value="/javascriptDesabilitado.jsp"/>">
	</noscript>

	 <body data-spy="scroll" data-target=".subnav" data-offset="50" style="position: relative;">

		<div class="topo" >
		
		</div>
		
		<p class="bem-vindo" > ${sessaoUsuario.usuario.nome}, bem vindo(a) ao seu escrit�rio virtual!  </p>

 		<div class="menu" data-collapse="persist">
	
	      	<h3 class="menu-accordion" > MEUS DADOS </h3>
	        <div>
	        	<a class="submenu-accordion" > Dados cadastrais </a>
	        	<a href="<c:url value="/login/trocarPropriaSenha"/>" class="submenu-accordion" > Troque sua senha </a>
	        </div>
	        <c:if test="${sessaoUsuario.usuario.administrador}">
		        <h3 class="menu-accordion" >IMPORTA��O DE ARQUIVOS</h3>
		        <div>
		        	<a href="<c:url value="/importacaoArquivo/acessarTelaImportacaoArquivoTabelaRelacionamentos"/>" class="submenu-accordion" > Importar tabela de relacionamentos </a>
		        </div>
	        </c:if>
		</div>
		
		<a id="sair" style="float: right; padding-right: 15px; font-weight: bold; margin-top: -20px; font-size: 12px; cursor: pointer; " href="<c:url value="/login/logout"/>"> Sair </a>
		
		<div class="conteudo">


		<c:if test="${not empty sucesso}">
			<div class="alert alert-success">
				${sucesso}
			</div>
		</c:if>
		
		<c:if test="${not empty errors}">
			<div class="alert alert-error">
				<c:forEach items="${errors }" var="error">
					<strong>${error.category }</strong> - ${error.message } <br>
				</c:forEach>
			</div>
		</c:if>
		
 		<br>