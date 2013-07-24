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

<a class="btn" href="<c:url value="/aniversariantes/acessarTelaAniversariantes"/>" > Voltar </a>

<br><br>

<h3> Aniversariantes </h3>
<h6 style="color: #aaa;" > Obs: Dados ordenados por data de aniversário </h6>

<c:choose>
	<c:when test="${!empty aniversariantes}">

		<table class="table table-striped table-bordered">
			<thead>
		    	<tr>
                    <th> Código </th>
                    <th> Posição </th>
                    <th> Nome </th>
                    <th> Aniversário </th>
                    <th> Telefones </th>
                    <th> Celular </th>
                    <th> Email </th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${aniversariantes}" var="item">
					<tr>
                        <td class="centralizado" > 
                        	${item.usuario.id_Codigo}
                        </td>
                        <td class="centralizado" > ${item.usuario.posAtual} </td>
                        <td> ${item.usuario.vNome} </td>
                        <td class="centralizado" > ${item.usuario.aniversario} </td>
                        <td> ${item.usuario.tel} </td>
                        <td> ${item.usuario.cadCelular} </td>
                        <td> ${item.usuario.eMail} </td>
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

<a class="btn" href="<c:url value="/aniversariantes/acessarTelaAniversariantes"/>" > Voltar </a>