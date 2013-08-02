<%@ include file="/base.jsp" %> 

<h4> Linha ascendente  </h4>

<table class="table table-striped table-bordered">
	<thead>
	   	<tr>
	        <th> C�digo </th>
	        <th> Posi��o </th>
	        <th> Nome </th>
	        <th> Telefones </th>
	        <th> Email </th>
		</tr>
	</thead>
	<tbody>
		<c:forEach items="${linhaAscendente}" var="item">
			<tr>
	            <td> ${item.value.id_Codigo} </td>
	            <td> ${item.value.posAtual} </td>
	            <td> ${item.value.vNome} - ${item.value.vNomeTitular2} </td>
	            <td> ${item.value.tel} / ${item.value.cadCelular} </td>
	            <td> ${item.value.eMail} </td>
			</tr>
		</c:forEach>
	</tbody>
</table>