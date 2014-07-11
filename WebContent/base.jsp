<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<!DOCTYPE HTML>
<html lang="pt-br">
	<head>
		 <meta charset="utf-8">
		<title> Escritório Virtual Alabastrum </title>
		<link rel="SHORTCUT ICON" href="<c:url value="/css/images/favico.png"/>" type="image/x-icon">
		<link type="text/css" href="<c:url value="/css/bootstrap.css"/>" rel="stylesheet" />
		<link type="text/css" href="<c:url value="/css/bootstrap-alterado.css"/>" rel="stylesheet" />
		<link type="text/css" href="<c:url value="/css/bootstrap-responsive.css"/>" rel="stylesheet" />
		<link type="text/css" href="<c:url value="/css/estilo.css?ver=4"/>" rel="stylesheet" />
		<link type="text/css" href="<c:url value="/css/jquery-ui-1.9.2.custom.min.css"/>" rel="stylesheet" />
		<link type="text/css" href="<c:url value="/css/menu-accordion.css"/>" rel="stylesheet" />
		<script type="text/javascript" src="<c:url value="/js/jquery-1.8.3.js"/>"></script>
		<script type="text/javascript" src="<c:url value="/js/jquery-ui-1.9.2.custom.min.js"/>"></script>
		<script type="text/javascript" src="<c:url value="/js/jquery.collapse.js"/>"></script>
		<script type="text/javascript" src="<c:url value="/js/jquery.collapse_storage.js"/>"></script>
		<script type="text/javascript" src="<c:url value="/js/jquery.collapse_cookie_storage.js"/>"></script>		
		<script type="text/javascript" src="<c:url value="/js/jquery.validate.js"/>"></script>
		<script type="text/javascript" charset="utf-8" src="<c:url value="/js/scripts.gerais.js?ver=4"/>"></script>			
		<script type="text/javascript" src="<c:url value="/js/submenu.js"/>"></script>
		<script type="text/javascript" src="<c:url value="/js/form.requerido.js"/>"></script>	
	</head>
	
	<noscript>
		<meta http-equiv="Refresh" content="0;url=<c:url value="/javascriptDesabilitado.jsp"/>">
	</noscript>

	 <body data-spy="scroll" data-target=".subnav" data-offset="50" style="position: relative;">

		<div class="topo" >
		
		</div>
		
		<p class="bem-vindo" > ${sessaoUsuario.usuario.vNome}, bem vindo(a) ao seu escritório virtual!  </p>

 		<div class="menu" data-collapse="persist">

	      	<h3 class="menu-accordion" > MEUS DADOS </h3>
	        <div>
	        	<a href="<c:url value="/home/home"/>" class="submenu-accordion" > Página inicial </a>
	        	<a href="<c:url value="/atualizacaoDados/acessarTelaAtualizacaoDados"/>" class="submenu-accordion" > Dados cadastrais </a>
	        	<a href="<c:url value="/login/trocarPropriaSenha"/>" class="submenu-accordion" > Troque sua senha </a>
	        </div>
	        <h3 class="menu-accordion" > REDE </h3>
	        <div>
	        	<a href="<c:url value="/malaDireta/acessarTelaMalaDireta"/>" class="submenu-accordion" > Mala direta </a>
	        	<a href="<c:url value="/pontuacao/acessarTelaPontuacao"/>" class="submenu-accordion" > Pontuação </a>
	        	<a href="<c:url value="/pontuacaoAnual/acessarTelaPontuacaoAnual"/>" class="submenu-accordion" > Pontuação anual</a>
	        	<a href="<c:url value="/arvoreRelacionamento/acessarTelaArvoreRelacionamento"/>" class="submenu-accordion" > Árvore de relacionamentos </a>
	        	<a href="<c:url value="/aniversariantes/acessarTelaAniversariantes"/>" class="submenu-accordion" > Aniversariantes </a>
	        	<a href="<c:url value="/listaIngresso/acessarTelaListaIngresso"/>" class="submenu-accordion" > Lista de ingresso </a>
	        </div>
	        <h3 class="menu-accordion" > PEDIDOS </h3>
	        <div>
	        	<a href="<c:url value="/pedido/acessarTelaPedido"/>" class="submenu-accordion" > Realizar pedido </a>
	        	<a href="<c:url value="/controlePedido/acessarTelaControlePedido"/>" class="submenu-accordion" > Pedidos pessoais </a>
	        	<a href="<c:url value="/controlePedidoRede/acessarTelaControlePedidoRede"/>" class="submenu-accordion" > Pedidos da rede </a>
	        	<a href="<c:url value="/transferenciaCredito/acessarTelaTransferenciaCredito"/>" class="submenu-accordion" > Transferência de crédito </a>
	        </div>
	         <h3 class="menu-accordion" > BONIFICAÇÃO </h3>
	        <div>
				<a href="<c:url value="/extratoSimplificado/acessarTelaExtratoSimplificado"/>" class="submenu-accordion" > Extrato Simplificado </a>
	        </div>
	        <h3 class="menu-accordion" > CONSULTA </h3>
	        <div>
	        	<a href="<c:url value="/home/downloads"/>" class="submenu-accordion" > Downloads </a>
	        	<a href="<c:url value="/home/kitIngresso"/>" class="submenu-accordion" > Kits de ingresso </a>
	        	<a href="<c:url value="/home/qualificacao"/>" class="submenu-accordion" > Qualificação </a>
	        	<a href="<c:url value="/centroDistribuicao/listarCentrosDistribuicao"/>" class="submenu-accordion" > Centros de distribuição </a>
	        	<a href="<c:url value="/linhaAscendente/exibirLinhaAscendente"/>" class="submenu-accordion" > Linha Ascendente </a>
	        	<a href="<c:url value="/dataQualificacoes/acessarTelaDataQualificacoes"/>" class="submenu-accordion" > Datas de qualificações </a>
	        </div>
	        <h3 class="menu-accordion" > INFORMAÇÕES ÚTEIS </h3>
	        <div>
	        	<a href="<c:url value="/home/emails"/>" class="submenu-accordion" > E-mails </a>
	        </div>
	        <c:if test="${sessaoUsuario.usuario.informacoesFixasUsuario.administrador}">
		        <h3 class="menu-accordion" >ADMINISTRATIVO </h3>
		        <div>
		        	<a href="<c:url value="/assumirIdentidade/acessarTelaAssumirIdentidade"/>" class="submenu-accordion" > Assumir identidade </a>
		        	<a href="<c:url value="/bonificacaoRede/acessarBonificacaoRede"/>" class="submenu-accordion" > Bonificação dos ativos </a>
		        	<a href="<c:url value="/enviadorEmails/acessarEnviadorEmails"/>" class="submenu-accordion" > Enviar emails para a rede </a>
		        	<a href="<c:url value="/configuracao/configuracoes"/>" class="submenu-accordion" > Configurações </a>
		        	<a href="<c:url value="/importacaoArquivo/acessarTelaImportacaoArquivo"/>" class="submenu-accordion" > Atualizar o sistema </a>
		        </div>
	        </c:if>
		</div>
		
		<a id="sair" style="float: right; padding-right: 15px; font-weight: bold; margin-top: -30px; font-size: 12px; cursor: pointer; " href="<c:url value="/login/logout"/>"> Sair </a>
		
		<div class="conteudo">


		<c:if test="${not empty sucesso}">
			<div class="alert alert-success">
				${sucesso}
			</div>
		</c:if>
		
		<c:if test="${not empty alerta}">
			<div class="alert alert-alerta">
				${alerta}
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
