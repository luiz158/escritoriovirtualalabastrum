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

<h3> Bônus de Graduação </h3>

<h6 style="color: rgb(100, 100, 100);" >Mês/ano: ${mesAno}</h6>
<h6 style="color: rgb(100, 100, 100);" >Bonificação: R$<fmt:formatNumber value="${bonificacaoGraduacao.bonificacao}" pattern="#,##0.00" /> </h6>

<br>
<h5> Detalhes </h5>

<h6 style="color: rgb(100, 100, 100);" >Porcentagem: ${bonificacaoGraduacao.porcentagemUsuarioLogado}%</h6>
<h6 style="color: rgb(100, 100, 100);" >Somatório de pedidos feitos pela rede: R$<fmt:formatNumber value="${bonificacaoGraduacao.somatorioPedidosrede}" pattern="#,##0.00" /> </h6>


<c:if test="${!empty bonificacaoGraduacao.graduadosEPorcentagens}">

	<br>
	<h6 style="text-align: center;"> Distribuidores graduados </h6>

	<table class="table table-striped table-bordered">
		<thead>
	    	<tr>
                   <th> Distribuidor </th>
                   <th> Porcentagem </th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${bonificacaoGraduacao.graduadosEPorcentagens}" var="item">
				<tr>
                       <td> ${item.key.id_Codigo} - ${item.key.vNome} </td>
                       <td class="centralizado" > ${item.value}% </td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
</c:if>