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

</style>

<a class="btn" href="<c:url value="/pontuacao/acessarTelaPontuacao"/>" > Voltar </a>

<br><br>

<h3> Pontuação </h3>

<h6 style="color: rgb(100, 100, 100);" >Líder da equipe: ${usuarioPesquisado.id_Codigo} - ${usuarioPesquisado.posAtual} - ${usuarioPesquisado.vNome} / ${usuarioPesquisado.vNomeTitular2} </h6>

<h6 style="color: rgb(100, 100, 100);" >Posição considerada: ${posicaoConsiderada}    </h6>

<h6 style="color: rgb(160, 30, 30);" > Quantidade de registros: ${quantidadeElementosMalaDireta} <span class="dica" > (<b>Dica:</b> Para encontrar registros mais rapidamente, utilize a pesquisa através do atalho <b>CTRL + F</b>) </span> </h6> 

<c:choose>
	<c:when test="${!empty relatorioPontuacao}">

		<table class="table table-striped table-bordered">
			<thead>
		    	<tr>
                    <th> Código </th>
                    <th> Posição </th>
                    <th> Nome </th>
                    <th> Telefones </th>
                    <th> Celular </th>
                    <th> Email </th>
                    <th> Ingresso </th>
                    <th> Produtos </th>
                    <th> Bônus </th>
                    <th> Total </th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${relatorioPontuacao}" var="item">
					<tr>
                        <td class="centralizado" > 
                        	${item.malaDireta.usuario.id_Codigo}
                        </td>
                        <td class="centralizado" > ${item.usuario.posAtual} </td>
                        <td> ${item.malaDireta.usuario.vNome} </td>
                        <td> ${item.malaDireta.usuario.tel} </td>
                        <td> ${item.malaDireta.usuario.cadCelular} </td>
                        <td> ${item.malaDireta.usuario.eMail} </td>
                        <td> ${item.pontuacaoIngresso} </td>
                        <td> ${item.pontuacaoProdutos} </td>
                        <td> ${item.pontuacaoAtividade} </td>
                        <td> ${item.total} </td>
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

<a class="btn" href="<c:url value="/pontuacao/acessarTelaPontuacao"/>" > Voltar </a>