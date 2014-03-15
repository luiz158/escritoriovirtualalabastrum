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

<h3> Bônus de Ativação </h3>

<h6 style="color: rgb(100, 100, 100);" >Mês/ano: ${mesAno}</h6>

<c:choose>
	<c:when test="${!empty bonificacoes}">

		<table class="table table-striped table-bordered">
			<thead>
		    	<tr>
                    <th> Distribuidor </th>
                    <th> Geração </th>
                    <th> Bonificação </th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${bonificacoes}" var="item">
					<tr>
                        <td> ${item.malaDireta.usuario.id_Codigo} - ${item.malaDireta.usuario.vNome} </td>
                        <td class="centralizado" > ${item.malaDireta.nivel} </td>
                        <td class="centralizado" > R$<fmt:formatNumber value="${item.bonificacao}" pattern="#,##0.00" /> </td>
					</tr>
				</c:forEach>
			</tbody>
			<tfoot>
				<tr style="background-color: rgb(245, 250, 138);" >
                    <td class="centralizado" colspan="2" > <b> Total </b> </td>
					<td class="centralizado" > R$<fmt:formatNumber value="${somatorioBonificacao}" pattern="#,##0.00" /> </td>
				</tr>
			</tfoot>
		</table>

	</c:when>
	<c:otherwise>
		<br>  <br>  <h4> Nenhum registro foi encontrado </h4>
	</c:otherwise>
</c:choose>