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

<a class="btn" href="<c:url value="/home/home"/>" > Voltar </a>

<br><br>

<c:choose>
	<c:when test="${!empty usuarios}">

		<table class="table table-striped table-bordered">
			<thead>
		    	<tr>
                    <th> Código </th>
                    <th> Posição </th>
                    <th> Nome </th>
                    <th> Telefones </th>
                    <th> Celular </th>
                    <th> Email </th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${usuarios}" var="item">
					<tr class="usuario" id="${item.id_Codigo}" >
                        <td class="centralizado" > 
                        	${item.id_Codigo}
                        </td>
                        <td class="centralizado" > ${item.posAtual} </td>
                        <td> ${item.vNome} </td>
                        <td> ${item.tel} </td>
                        <td> ${item.cadCelular} </td>
                        <td> ${item.eMail} </td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</c:when>
	<c:otherwise>
		<br>  <br>  <h4> Nenhum registro foi encontrado </h4>
	</c:otherwise>
</c:choose>