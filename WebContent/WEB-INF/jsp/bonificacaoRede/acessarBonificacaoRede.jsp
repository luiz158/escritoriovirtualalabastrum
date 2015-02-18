<%@ include file="/base.jsp"%>
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
<a class="btn" onclick="window.history.back()"> Voltar </a>
<br>
<br>
<h3>Bonifica��es de toda a rede</h3>
<h6 style="color: rgb(100, 100, 100);">M�s/ano: ${InformacoesGeraisCalculoBonificacaoRede.mes}/${InformacoesGeraisCalculoBonificacaoRede.ano}</h6>
<h6 style="color: rgb(100, 100, 100);">
	Data/hora do c�lculo
	<fmt:formatDate value="${InformacoesGeraisCalculoBonificacaoRede.dataHoraCalculo.time}" type="BOTH" />
</h6>
<h6 style="color: rgb(100, 100, 100);">Tempo de processamento: ${InformacoesGeraisCalculoBonificacaoRede.tempoDeProcessamentoRelatorio} minutos</h6>
<c:choose>
	<c:when test="${!empty bonificacoesRede}">
		<table class="table table-striped table-bordered">
			<thead>
				<tr>
					<th>Distribuidor</th>
					<th>B�nus de Indica��o</th>
					<th>B�nus de Ativa��o</th>
					<th>B�nus Unilevel</th>
					<th>B�nus de Expans�o</th>
					<th>B�nus de PA</th>
					<th>B�nus de Divis�o</th>
					<th>Total</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${bonificacoesRede}" var="item">
					<tr>
						<td>${item.usuario.codigoFormatado}-${item.usuario.vNome}</td>
						<td class="centralizado">
							R$
							<fmt:formatNumber value="${item.bonificacaoInicioRapido}" pattern="#,##0.00" />
						</td>
						<td class="centralizado">
							R$
							<fmt:formatNumber value="${item.bonificacaoAtivacao3}" pattern="#,##0.00" />
						</td>
						<td class="centralizado">
							R$
							<fmt:formatNumber value="${item.bonificacaoUniLevel}" pattern="#,##0.00" />
						</td>
						<td class="centralizado">
							R$
							<fmt:formatNumber value="${item.bonificacaoExpansao}" pattern="#,##0.00" />
						</td>
						<td class="centralizado">
							R$
							<fmt:formatNumber value="${item.bonificacaoPontoDeApoio}" pattern="#,##0.00" />
						</td>
						<td class="centralizado">
							R$
							<fmt:formatNumber value="${item.bonificacaoDivisao}" pattern="#,##0.00" />
						</td>
						<td class="centralizado">
							R$
							<fmt:formatNumber value="${item.total}" pattern="#,##0.00" />
						</td>
					</tr>
				</c:forEach>
			</tbody>
			<tfoot>
				<tr style="background-color: rgb(245, 250, 138);">
					<td class="centralizado" colspan="7">
						<b> Total </b>
					</td>
					<td class="centralizado">
						R$
						<fmt:formatNumber value="${somatorioBonificacoes}" pattern="#,##0.00" />
					</td>
				</tr>
			</tfoot>
		</table>
	</c:when>
	<c:otherwise>
		<br>
		<br>
		<h4>Nenhum registro foi encontrado</h4>
	</c:otherwise>
</c:choose>
