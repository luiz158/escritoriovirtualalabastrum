<%@ include file="/base.jsp" %> 

<a class="btn" href="<c:url value="/malaDireta/acessarTelaMalaDireta"/>" > Voltar </a>

<br><br>

<h3> Mala direta </h3>

<c:choose>
	<c:when test="${!empty malaDireta}">

		<table class="table table-striped table-bordered">
			<thead>
		    	<tr>
                    <th> Código </th>
                    <th> Nível </th>
                    <th> Posição </th>
                    <th> Nome </th>
                    <th> Nome </th>
                    <th> Nome </th>
                    <th> Nome </th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${malaDireta}" var="item">
					<tr>
                        <td class="centralizado" style="white-space:nowrap" > 
                        	<c:forEach begin="2" end="${item.nivel}" >
                        		&nbsp;&nbsp;&nbsp;&nbsp;
                        	</c:forEach>
                        	${item.usuario.id_Codigo}
                        </td>
                         <td> ${item.nivel}º </td>
                        <td> ${item.usuario.posAtual} </td>
                        <td class="centralizado"> ${item.usuario.vNome} </td>
                         <td class="centralizado"> ${item.usuario.vNome} </td>
                          <td class="centralizado"> ${item.usuario.vNome} </td>
                           <td class="centralizado"> ${item.usuario.vNome} </td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</c:when>
	<c:otherwise>
		<br>  <br>  <h4> Nenhum registro foi encontrado </h4>
	</c:otherwise>
</c:choose>