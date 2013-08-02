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

.dica{
	color: rgb(170, 170, 170);
	margin-left: 30px;
}

b{
	font-weight: bold;
	color: rgb(120, 120, 120);
	font-size: 14px;
}

.informacoesContato{

	font-weight: bold;
	color: rgb(90, 90, 90);
}

</style>

<a class="btn" href="<c:url value="/listaIngresso/acessarTelaListaIngresso"/>" > Voltar </a>

<br><br>

<h3> Lista de ingresso </h3>

<h6 style="color: rgb(100, 100, 100);" >Período: <fmt:formatDate value="${dataInicialPesquisada.time}" type="DATE" /> - <fmt:formatDate value="${dataFinalPesquisada.time}" type="DATE" />    </h6>

<h6 style="color: rgb(160, 30, 30);" > Quantidade de registros: ${fn:length(malaDireta)} <span class="dica" > (<b>Dica:</b> Para encontrar registros mais rapidamente, utilize a pesquisa através do atalho <b>CTRL + F</b>) </span> </h6> 

<br>

<c:choose>
	<c:when test="${!empty malaDireta}">

		<table class="table table-striped table-bordered">
			<thead>
		    	<tr>
                    <th> Código </th>
                    <th> Posição </th>
                    <th> Nome </th>
                    <th> Data de ingresso </th>
                    <th> Talefones</th>
                    <th> Email </th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${malaDireta}" var="item">
					<tr>
						<td class="centralizado" > ${item.value.usuario.id_Codigo} </td>
						<td class="centralizado" > ${item.value.usuario.posAtual} </td>
						<td> ${item.value.usuario.vNome} </td>
						<td class="centralizado" > <fmt:formatDate value="${item.value.usuario.dt_Ingresso.time}" type="DATE" /> </td>
						<td> ${item.value.usuario.tel} / ${item.value.usuario.cadCelular} </td>
						<td> ${item.value.usuario.eMail} </td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</c:when>
	<c:otherwise>
		<br>  <br>  <h4> Nenhum registro foi encontrado </h4>
	</c:otherwise>
</c:choose>

<br>

<a class="btn" href="<c:url value="/listaIngresso/acessarTelaListaIngresso"/>" > Voltar </a>