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

<h3> Bonificação de todos distribuidores ativos </h3>

<h6 style="color: rgb(100, 100, 100);" >Mês/ano: ${InformacoesGeraisCalculoBonificacaoRede.mes}/${InformacoesGeraisCalculoBonificacaoRede.ano}</h6>
<h6 style="color: rgb(100, 100, 100);" > Data/hora do cálculo <fmt:formatDate value="${InformacoesGeraisCalculoBonificacaoRede.dataHoraCalculo.time}" type="BOTH" /> </h6>
<h6 style="color: rgb(100, 100, 100);" > Tempo de processamento: ${InformacoesGeraisCalculoBonificacaoRede.tempoDeProcessamentoRelatorio} minutos </h6>

<c:choose>
	<c:when test="${!empty bonificacoesRede}">

		<table class="table table-striped table-bordered">
			<thead>
		    	<tr>
                    <th> Distribuidor </th>
                    <th> Qualificação calculada </th>
                    <th> Bônus de Compra Pessoal </th>
                    <th> Bônus de Primeira Compra </th>
                    <th> Bônus de Ativação </th>
                    <th> Bônus de Graduação </th>
                    <th> Total </th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${bonificacoesRede}" var="item">
					<tr>
						<td> ${item.usuario.id_Codigo} - ${item.usuario.vNome} </td>
                        <td class="centralizado" > ${item.qualificacao} </td>
                        <td class="centralizado" > R$<fmt:formatNumber value="${item.bonificacaoCompraPessoal}" pattern="#,##0.00" /> </td>
                        <td class="centralizado" > R$<fmt:formatNumber value="${item.bonificacaoIngresso}" pattern="#,##0.00" /> </td>
                        <td class="centralizado" > R$<fmt:formatNumber value="${item.bonificacaoAtivacao}" pattern="#,##0.00" /> </td>
                        <td class="centralizado" > R$<fmt:formatNumber value="${item.bonificacaoGraduacao}" pattern="#,##0.00" /> </td>
                        <td class="centralizado" > R$<fmt:formatNumber value="${item.total}" pattern="#,##0.00" /> </td>
					</tr>
				</c:forEach>
			</tbody>
			<tfoot>
				<tr style="background-color: rgb(245, 250, 138);" >
                    <td class="centralizado" colspan="6" > <b> Total </b> </td>
					<td class="centralizado" > R$<fmt:formatNumber value="${somatorioBonificacoes}" pattern="#,##0.00" /> </td>
				</tr>
			</tfoot>
		</table>

	</c:when>
	<c:otherwise>
		<br>  <br>  <h4> Nenhum registro foi encontrado </h4>
	</c:otherwise>
</c:choose>
