<%@ include file="/base.jsp" %> 

<h4> Centros de distribuição  </h4>

<table class="table table-striped table-bordered">
	<thead>
	   	<tr>
	        <th> Nome </th>
	        <th> Endereço </th>
	        <th> Bairro </th>
	        <th> Cidade </th>
	        <th> UF </th>
	        <th> CEP </th>
	        <th> Telefone </th>
	        <th> Email </th>
		</tr>
	</thead>
	<tbody>
		<c:forEach items="${centrosDistribuicao}" var="item">
			<tr>
	            <td> ${item.estqNome} </td>
	            <td> ${item.estqEndereco} </td>
	            <td> ${item.estqBairro} </td>
	            <td> ${item.estqCidade} </td>
	            <td> ${item.estqUF} </td>
	            <td> ${item.estqCEP} </td>
	            <td> ${item.estqTelefone} </td>
	            <td> ${item.estqEmail} </td>
			</tr>
		</c:forEach>
	</tbody>
</table>