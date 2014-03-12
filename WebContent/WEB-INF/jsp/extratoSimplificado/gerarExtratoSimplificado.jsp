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

<h6 style="color: rgb(100, 100, 100);" >Mês/ano: ${mes}/${ano}</h6>
<h6 style="color: rgb(100, 100, 100);" >Distribuidor: ${sessaoUsuario.usuario.id_Codigo} - ${sessaoUsuario.usuario.posAtual} - ${sessaoUsuario.usuario.vNome}</h6>

<h6 style="color: rgb(100, 100, 100);" > Até o momento, você alcançou <fmt:formatNumber value="${pontuacao}" pattern="#,##0.00" /> pontos e ${quantidadeGraduados} graduados em linhas diferentes na sua rede. </h6>
<h6 style="color: rgb(100, 100, 100);" > Para conferir em qual qualificação você está neste mês. <a target="_blank" href="<c:url value="/home/qualificacao"/>"> Clique aqui </a> </h6>

<br> <br>

<table class="table">
	<tr>
		<td>
			Bônus de Compra Pessoal
		</td>
		<td>
			(AINDA EM CONSTRUÇÃO)
		</td>
		<td> </td>		
	</tr>
	<tr>
		<td>
			Bônus de Início Rápido
		</td>
		<td>
			 R$<fmt:formatNumber value="${bonificacaoIngresso}" pattern="#,##0.00" />
		</td>	
		<td> <a href="<c:url value="/bonificacaoIngresso/gerarRelatorioBonificacaoIngresso?ano=${ano}&mes=${mes}"/>"> Clique para ver detalhes </a> </td>		
	</tr>
	<tr>
		<td>
			Bônus de Ativação
		</td>
		<td>
			 R$<fmt:formatNumber value="${bonificacaoAtivacao}" pattern="#,##0.00" />
		</td>	
		<td> <a href="<c:url value="/bonificacaoAtivacao/gerarRelatorioBonificacaoAtivacao?ano=${ano}&mes=${mes}"/>"> Clique para ver detalhes </a> </td>
	</tr>
	<tr>
		<td>
			Bônus de Graduação
		</td>
		<td>
			 (AINDA EM CONSTRUÇÃO)
		</td>	
		<td> </td>		
	</tr>
	<tr>
		<td>
			Bônus de Liderança
		</td>
		<td>
			 (AINDA EM CONSTRUÇÃO)
		</td>	
		<td> </td>		
	</tr>
	<tr>
		<td>
			Bônus Global
		</td>
		<td>
			 (AINDA EM CONSTRUÇÃO)
		</td>	
		<td> </td>		
	</tr>
	<tr style="background-color: rgb(245, 250, 138);" >
		<td class="centralizado"> <b> Total </b> </td>
		<td > R$<fmt:formatNumber value="${bonificacaoIngresso + bonificacaoAtivacao}" pattern="#,##0.00" /> </td>
		<td></td>
	</tr>
</table>
