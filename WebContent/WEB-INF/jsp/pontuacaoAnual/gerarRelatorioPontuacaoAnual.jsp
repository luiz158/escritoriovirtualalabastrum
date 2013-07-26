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

<a class="btn" href="<c:url value="/pontuacaoAnual/acessarTelaPontuacaoAnual"/>" > Voltar </a>

<br><br>

<h3> Pontuação Anual </h3>

<h6 style="color: rgb(100, 100, 100);" >Líder da equipe: ${usuarioPesquisado.id_Codigo} - ${usuarioPesquisado.posAtual} - ${usuarioPesquisado.vNome} / ${usuarioPesquisado.vNomeTitular2} </h6>

<h6 style="color: rgb(100, 100, 100);" >Posição considerada: ${posicaoConsiderada}    </h6>

<h6 style="color: rgb(100, 100, 100);" >Possui movimentação? ${possuiMovimentacao} </h6>

<h6 style="color: rgb(100, 100, 100);" >Ano: ${ano}    </h6>

<h6 style="color: rgb(160, 30, 30);" > Quantidade de registros: ${quantidadeElementos} <span class="dica" > (<b>Dica:</b> Para encontrar registros mais rapidamente, utilize a pesquisa através do atalho <b>CTRL + F</b>) </span> </h6> 

<br>

<c:choose>
	<c:when test="${!empty pontuacoes}">

		<table class="table table-striped table-bordered">
			<thead>
				<tr>
					<th colspan="3" > Distribuidor </th>
					<th colspan="24" > Pontuação mensal </th>
					<th colspan="2" > Total </th>
				</tr>
		    	<tr>
                    <th rowspan="2" > Código </th>
                    <th rowspan="2" > Nome </th>
                    <th rowspan="2" > Posição </th>
                    
                    <th colspan="2" > Jan </th>
                    <th colspan="2" > Fev </th>
                    <th colspan="2" > Mar </th>
                    <th colspan="2" > Abr </th>
                    <th colspan="2" > Mai </th>
                    <th colspan="2" > Jun </th>
                    <th colspan="2" > Jul </th>
                    <th colspan="2" > Ago </th>
                    <th colspan="2" > Set </th>
                    <th colspan="2" > Out </th>
                    <th colspan="2" > Nov </th>
                    <th colspan="2" > Dez </th>   
                    
                    <th rowspan="2" > Prod. </th>
                    <th rowspan="2" > Ativ. </th>                 
				</tr>
				<tr>
					<th> P </th>
					<th> A </th>
					<th> P </th>
					<th> A </th>
					<th> P </th>
					<th> A </th>
					<th> P </th>
					<th> A </th>
					<th> P </th>
					<th> A </th>
					<th> P </th>
					<th> A </th>
					<th> P </th>
					<th> A </th>
					<th> P </th>
					<th> A </th>
					<th> P </th>
					<th> A </th>
					<th> P </th>
					<th> A </th>
					<th> P </th>
					<th> A </th>
					<th> P </th>
					<th> A </th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${pontuacoes}" var="item">
					<tr>
                        <td class="centralizado" > 
                        	${item.malaDireta.usuario.id_Codigo}
                        </td>
                        <td> ${item.malaDireta.usuario.vNome} </td>
						<td class="centralizado" > ${item.malaDireta.usuario.posAbrev} </td>
						
                        <td class="centralizado" > <c:if test="${item.pontuacaoProdutosJaneiro > 0}"> <fmt:formatNumber value="${item.pontuacaoProdutosJaneiro}" /> </c:if> </td>
                        <td class="centralizado" > <c:if test="${item.pontuacaoAtividadeJaneiro > 0}"> <fmt:formatNumber value="${item.pontuacaoAtividadeJaneiro}" /> </c:if> </td>
                        
                        <td class="centralizado" > <c:if test="${item.pontuacaoProdutosFevereiro > 0}"> <fmt:formatNumber value="${item.pontuacaoProdutosFevereiro}" /> </c:if> </td>
                        <td class="centralizado" > <c:if test="${item.pontuacaoAtividadeFevereiro > 0}"> <fmt:formatNumber value="${item.pontuacaoAtividadeFevereiro}" /> </c:if> </td>
                        
                        <td class="centralizado" > <c:if test="${item.pontuacaoProdutosMarco > 0}"> <fmt:formatNumber value="${item.pontuacaoProdutosMarco}" /> </c:if> </td>
                        <td class="centralizado" > <c:if test="${item.pontuacaoAtividadeMarco > 0}"> <fmt:formatNumber value="${item.pontuacaoAtividadeMarco}" /> </c:if> </td>
                        
                        <td class="centralizado" > <c:if test="${item.pontuacaoProdutosAbril > 0}"> <fmt:formatNumber value="${item.pontuacaoProdutosAbril}" /> </c:if> </td>
                        <td class="centralizado" > <c:if test="${item.pontuacaoAtividadeAbril > 0}"> <fmt:formatNumber value="${item.pontuacaoAtividadeAbril}" /> </c:if> </td>
                        
                        <td class="centralizado" > <c:if test="${item.pontuacaoProdutosMaio > 0}"> <fmt:formatNumber value="${item.pontuacaoProdutosMaio}" /> </c:if> </td>
                        <td class="centralizado" > <c:if test="${item.pontuacaoAtividadeMaio > 0}"> <fmt:formatNumber value="${item.pontuacaoAtividadeMaio}" /> </c:if> </td>
                        
                        <td class="centralizado" > <c:if test="${item.pontuacaoProdutosJunho > 0}"> <fmt:formatNumber value="${item.pontuacaoProdutosJunho}" /> </c:if> </td>
                        <td class="centralizado" > <c:if test="${item.pontuacaoAtividadeJunho > 0}"> <fmt:formatNumber value="${item.pontuacaoAtividadeJunho}" /> </c:if> </td>
                        
                        <td class="centralizado" > <c:if test="${item.pontuacaoProdutosJulho > 0}"> <fmt:formatNumber value="${item.pontuacaoProdutosJulho}" /> </c:if> </td>
                        <td class="centralizado" > <c:if test="${item.pontuacaoAtividadeJulho > 0}"> <fmt:formatNumber value="${item.pontuacaoAtividadeJulho}" /> </c:if> </td>
                        
                        <td class="centralizado" > <c:if test="${item.pontuacaoProdutosAgosto > 0}"> <fmt:formatNumber value="${item.pontuacaoProdutosAgosto}" /> </c:if> </td>
                        <td class="centralizado" > <c:if test="${item.pontuacaoAtividadeAgosto > 0}"> <fmt:formatNumber value="${item.pontuacaoAtividadeAgosto}" /> </c:if> </td>
                        
                        <td class="centralizado" > <c:if test="${item.pontuacaoProdutosSetembro > 0}"> <fmt:formatNumber value="${item.pontuacaoProdutosSetembro}" /> </c:if> </td>
                        <td class="centralizado" > <c:if test="${item.pontuacaoAtividadeSetembro > 0}"> <fmt:formatNumber value="${item.pontuacaoAtividadeSetembro}" /> </c:if> </td>
                        
                        <td class="centralizado" > <c:if test="${item.pontuacaoProdutosOutubro > 0}"> <fmt:formatNumber value="${item.pontuacaoProdutosOutubro}" /> </c:if> </td>
                        <td class="centralizado" > <c:if test="${item.pontuacaoAtividadeOutubro > 0}"> <fmt:formatNumber value="${item.pontuacaoAtividadeOutubro}" /> </c:if> </td>
                        
                        <td class="centralizado" > <c:if test="${item.pontuacaoProdutosNovembro > 0}"> <fmt:formatNumber value="${item.pontuacaoProdutosNovembro}" /> </c:if> </td>
                        <td class="centralizado" > <c:if test="${item.pontuacaoAtividadeNovembro > 0}"> <fmt:formatNumber value="${item.pontuacaoAtividadeNovembro}" /> </c:if> </td>
                        
                        <td class="centralizado" > <c:if test="${item.pontuacaoProdutosDezembro > 0}"> <fmt:formatNumber value="${item.pontuacaoProdutosDezembro}" /> </c:if> </td>
                        <td class="centralizado" > <c:if test="${item.pontuacaoAtividadeDezembro > 0}"> <fmt:formatNumber value="${item.pontuacaoAtividadeDezembro}" /> </c:if> </td>
                        
                        <td class="centralizado" > <c:if test="${item.pontuacaoProdutosTotal > 0}"> <fmt:formatNumber value="${item.pontuacaoProdutosTotal}" /> </c:if> </td>
                        <td class="centralizado" > <c:if test="${item.pontuacaoAtividadeTotal > 0}"> <fmt:formatNumber value="${item.pontuacaoAtividadeTotal}" /> </c:if> </td>
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

<a class="btn" href="<c:url value="/pontuacaoAnual/acessarTelaPontuacaoAnual"/>" > Voltar </a>
