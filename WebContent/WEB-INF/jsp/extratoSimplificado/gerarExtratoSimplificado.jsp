<%@ include file="/base.jsp" %> 

<style>

.menu {
	display: none;
    width: 0px;
	min-width: 0px;
}

.conteudo {
    width: 94%;
}

</style>

<a class="btn" onclick="window.history.back()" > Voltar </a>

<br><br>

<h3> Extrato simplificado </h3>

<h6 style="color: rgb(100, 100, 100);" >M�s/ano: ${mes}/${ano}</h6>
<h6 style="color: rgb(100, 100, 100);" >Distribuidor: ${sessaoUsuario.usuario.id_Codigo} - ${sessaoUsuario.usuario.posAtual} - ${sessaoUsuario.usuario.vNome}</h6>

<h6 style="color: rgb(100, 100, 100);" > At� o momento, voc� alcan�ou <fmt:formatNumber value="${pontuacao}" pattern="#,##0.00" /> pontos e ${quantidadeGraduados} graduados em linhas diferentes na sua rede. </h6>
<c:if test="${!empty graduacaoAtual}">
	<h6 style="color: rgb(100, 100, 100);" > De acordo com sua pontua��o e quantidade de graduados neste m�s. A sua gradua��o neste m�s � ${graduacaoAtual} </h6>
</c:if>
<h6 style="color: rgb(100, 100, 100);" > Para um detalhamento das qualifica��es. <a target="_blank" href="<c:url value="/home/qualificacao"/>"> Clique aqui </a> </h6>

<br> <br>

<table class="table">
	<tr>
		<td>
			B�nus de Compra Pessoal
		</td>
		<td>
			R$<fmt:formatNumber value="${bonificacaoCompraPessoal}" pattern="#,##0.00" />  (${porcentagemCompraPessoal}%)
		</td>
		<td> <a href="<c:url value="/controlePedido/gerarRecebendoAnoEMes?ano=${ano}&mes=${mes}"/>"> Clique para ver detalhes </a> </td>		
	</tr>
	<tr>
		<td>
			B�nus de Primeira Compra
		</td>
		<td>
			 R$<fmt:formatNumber value="${bonificacaoIngresso}" pattern="#,##0.00" />
		</td>	
		<td> <a href="<c:url value="/bonificacaoIngresso/gerarRelatorioBonificacaoIngresso?ano=${ano}&mes=${mes}"/>"> Clique para ver detalhes </a> </td>		
	</tr>
	<tr>
		<td>
			B�nus de Ativa��o
		</td>
		<td>
			 R$<fmt:formatNumber value="${bonificacaoAtivacao}" pattern="#,##0.00" />
		</td>	
		<td> <a href="<c:url value="/bonificacaoAtivacao/gerarRelatorioBonificacaoAtivacao?ano=${ano}&mes=${mes}"/>"> Clique para ver detalhes </a> </td>
	</tr>
	<tr>
		<td>
			B�nus de Gradua��o
		</td>
		<td>
			 R$<fmt:formatNumber value="${bonificacaoGraduacao.bonificacao}" pattern="#,##0.00" />
		</td>	
		<td> <a href="<c:url value="/bonificacaoGraduacao/gerarRelatorioBonificacaoGraduacao?ano=${ano}&mes=${mes}"/>"> Clique para ver detalhes </a> </td>
	</tr>
	<tr>
		<td>
			B�nus de Lideran�a
		</td>
		<td>
			 (AINDA EM CONSTRU��O)
		</td>	
		<td> </td>		
	</tr>
	<tr>
		<td>
			B�nus Global
		</td>
		<td>
			 (AINDA EM CONSTRU��O)
		</td>	
		<td> </td>		
	</tr>
	<tr style="background-color: rgb(245, 250, 138);" >
		<td class="centralizado"> <b> Total </b> </td>
		<td > R$<fmt:formatNumber value="${bonificacaoIngresso + bonificacaoAtivacao + bonificacaoCompraPessoal + bonificacaoGraduacao.bonificacao}" pattern="#,##0.00" /> </td>
		<td></td>
	</tr>
</table>
