<%@ include file="/base.jsp" %> 

<a class="btn" onclick="window.history.back()" > Voltar </a>

<h3> Pedidos da rede </h3>

<h6 style="color: rgb(100, 100, 100);" >Distribuidor escolhido: ${usuarioPesquisado.id_Codigo} - ${usuarioPesquisado.posAtual} - ${usuarioPesquisado.vNome} </h6>

<h6 style="color: rgb(100, 100, 100);" >Período: <fmt:formatDate value="${dataInicialPesquisada.time}" type="DATE" /> - <fmt:formatDate value="${dataFinalPesquisada.time}" type="DATE" />    </h6>

<c:choose>
	<c:when test="${!empty controlesPedidos}">

		<table class="table table-striped table-bordered">
			<thead>
		    	<tr>
		    		<th> Distribuidor </th>
                    <th> Pedido </th>
                    <th> Data </th>
                    <th> Base Cálculo </th>
                    <th> Outros valores </th>
                    <th> Líquido</th>
                    <th> Quantidade de produtos </th>
                    <th> Pontuação produtos </th>
                    <th> Pontuação atividade </th>
                    <th> Pontuação ingresso </th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${controlesPedidos}" var="item">
					<tr>
						<td> ${item.usuario.id_Codigo} - ${item.usuario.vNome} </td>
                        <td class="centralizado" > ${item.pedNumero} </td>
						<td class="centralizado" > <fmt:formatDate value="${item.pedData.time}" type="DATE" /> </td>
						<td class="centralizado" > <fmt:formatNumber value="${item.baseCalculo}" /> </td>
						<td class="centralizado" > <fmt:formatNumber value="${item.pedOutrosValores}" /> </td>
						<td class="centralizado" > <fmt:formatNumber value="${item.pedValorPago}" /> </td>
						<td class="centralizado" > <fmt:formatNumber value="${item.quantProdutos}" /> </td>
						<td class="centralizado" > <fmt:formatNumber value="${item.pontoProduto}" /> </td>
						<td class="centralizado" > <fmt:formatNumber value="${item.pontoAtividade}" /> </td>
						<td class="centralizado" > <fmt:formatNumber value="${item.pontoIngresso}" /> </td>
					</tr>
				</c:forEach>
			</tbody>
			<tfoot>
				<tr style="background-color: rgb(245, 250, 138);" >
                    <td class="centralizado" colspan="3" > <b> Total </b> </td>
					<td class="centralizado" > <fmt:formatNumber value="${somatorioControlePedido.somatorioBaseCalculo}" /> </td>
					<td class="centralizado" > <fmt:formatNumber value="${somatorioControlePedido.somatorioPedOutrosValores}" /> </td>
					<td class="centralizado" > <fmt:formatNumber value="${somatorioControlePedido.somatorioPedValorPago}" /> </td>
					<td class="centralizado" > <fmt:formatNumber value="${somatorioControlePedido.somatorioQuantProdutos}" /> </td>
					<td class="centralizado" > <fmt:formatNumber value="${somatorioControlePedido.somatorioPontoProduto}" /> </td>
					<td class="centralizado" > <fmt:formatNumber value="${somatorioControlePedido.somatorioPontoAtividade}" /> </td>
					<td class="centralizado" > <fmt:formatNumber value="${somatorioControlePedido.somatorioPontoIngresso}" /> </td>
				</tr>
			</tfoot>
		</table>
	</c:when>
	<c:otherwise>
		<br>  <br>  <h4> Nenhum registro foi encontrado </h4>
	</c:otherwise>
</c:choose>