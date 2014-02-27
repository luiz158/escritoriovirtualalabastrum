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

<a class="btn" href="<c:url value="/bonificacaoIngresso/acessarTelaBonificacao"/>" > Voltar </a>

<br><br>

<h3> Bonificação por ingresso </h3>

<h6 style="color: rgb(100, 100, 100);" >Mês/ano: ${mesAno}</h6>
<h6 style="color: rgb(100, 100, 100);" >Kit do distribuidor: ${kitUsuarioLogado}</h6>
<h6 style="color: rgb(100, 100, 100);" >Porcentagem do kit: ${porcentagemKitUsuarioLogado}%</h6>

<c:choose>
	<c:when test="${!empty bonificacoes}">

		<table class="table table-striped table-bordered">
			<thead>
		    	<tr>
                    <th> Distribuidor </th>
                    <th> Geração </th>
                    <th> Kit </th>
                    <th> Bonificação </th>
                    <th> Como foi calculado </th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${bonificacoes}" var="item">
					<tr>
                        <td> ${item.usuario.id_Codigo} - ${item.usuario.vNome} </td>
                        <td class="centralizado" > ${item.geracao} </td>
                        <td class="centralizado" > ${item.kit} </td>
                        <td class="centralizado" > <fmt:formatNumber value="${item.bonificacao}" /> </td>
                        <td class="centralizado" > ${item.comoFoiCalculado} </td>
					</tr>
				</c:forEach>
			</tbody>
			<tfoot>
				<tr style="background-color: rgb(245, 250, 138);" >
                    <td class="centralizado" colspan="3" > <b> Total </b> </td>
					<td class="centralizado" > <fmt:formatNumber value="${somatorioBonificacao}" /> </td>
					<td></td>
				</tr>
			</tfoot>
		</table>
	</c:when>
	<c:otherwise>
		<br>  <br>  <h4> Nenhum registro foi encontrado </h4>
	</c:otherwise>
</c:choose>
