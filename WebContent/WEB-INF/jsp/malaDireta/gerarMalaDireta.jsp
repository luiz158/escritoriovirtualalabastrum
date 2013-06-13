<%@ include file="/base.jsp" %> 

<a class="btn" href="<c:url value="/malaDireta/acessarTelaMalaDireta"/>" > Voltar </a>

<br><br>

<h3> Mala direta </h3>

<h6 style="color: rgb(100, 100, 100);" > Quantidade de registros: ${quantidadeElementosMalaDireta} </h6>

<c:choose>
	<c:when test="${!empty malaDireta}">

		<table class="table table-striped table-bordered">
			<thead>
		    	<tr>
                    <th> Código </th>
                    <th> Posição </th>
                    <th> Nome </th>
                    <th> Telefone </th>
                    <th> Celular </th>
                    <th> Email </th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${malaDireta}" var="item">
					<tr>
                        <td class="centralizado" style="white-space:nowrap" > 
                        	${item.usuario.id_Codigo}
                        </td>
                        <td> ${item.usuario.posAtual} </td>
                        <td class="centralizado"> ${item.usuario.vNome} </td>
                        <td class="centralizado"> ${item.usuario.tel} </td>
                        <td class="centralizado"> ${item.usuario.cadCelular} </td>
                        <td class="centralizado"> ${item.usuario.eMail} </td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</c:when>
	<c:otherwise>
		<br>  <br>  <h4> Nenhum registro foi encontrado </h4>
	</c:otherwise>
</c:choose>

<div class="imagensLink" >
	<a href="<c:url value="/atualizacaoDados/acessarTelaAtualizacaoDados"/>"> <img class="imagemLink" src="../css/images/imagem-link-atualize-seus-dados.jpg"  name="" /> </a>
	<a href="<c:url value="/home/emails"/>"> <img class="imagemLink" src="../css/images/imagem-link-emails.jpg"  name="" /> </a>
	<a href="<c:url value="/login/trocarPropriaSenha"/>"> <img class="imagemLink" src="../css/images/imagem-link-troque-sua-senha.jpg"  name="" /> </a>
</div>