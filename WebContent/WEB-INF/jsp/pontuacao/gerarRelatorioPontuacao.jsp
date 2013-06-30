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

.resumo{
	
	border: 1px solid #ddd;
	border-radius: 7px;
	width: 300px;
	height: 100px;
	position: absolute;
	right: 20px;
	padding: 20px;
	padding-top: 10px;
	margin-top: -40px;
	box-shadow: 4px 4px 4px #888;
}

</style>

<a class="btn" href="<c:url value="/pontuacao/acessarTelaPontuacao"/>" > Voltar </a>

<div class="resumo" >
	
	<h5 style="text-align: center" > Resumo dos totais </h5>
	
	<h6 style="margin-top: 20px" > Pontua��o pessoal: <fmt:formatNumber value="${pontuacaoPessoalUsuarioPesquisado}" />  </h6>
	
	<h6> Pontua��o da rede: <fmt:formatNumber value="${pontuacaoRede}" />  </h6>

</div>

<br><br>

<h3> Pontua��o </h3>

<h6 style="color: rgb(100, 100, 100);" >L�der da equipe: ${usuarioPesquisado.id_Codigo} - ${usuarioPesquisado.posAtual} - ${usuarioPesquisado.vNome} / ${usuarioPesquisado.vNomeTitular2} </h6>

<h6 style="color: rgb(100, 100, 100);" >Posi��o considerada: ${posicaoConsiderada}    </h6>

<h6 style="color: rgb(100, 100, 100);" >Per�odo: <fmt:formatDate value="${dataInicialPesquisada.time}" type="DATE" /> - <fmt:formatDate value="${dataFinalPesquisada.time}" type="DATE" />    </h6>

<h6 style="color: rgb(100, 100, 100);" >Possui movimenta��o? ${possuiMovimentacao} </h6>

<h6 style="color: rgb(160, 30, 30);" > Quantidade de registros: ${quantidadeElementos} <span class="dica" > (<b>Dica:</b> Para encontrar registros mais rapidamente, utilize a pesquisa atrav�s do atalho <b>CTRL + F</b>) </span> </h6> 

<c:choose>
	<c:when test="${!empty relatorioPontuacao}">

		<table class="table table-striped table-bordered">
			<thead>
		    	<tr>
                    <th> C�digo </th>
                    <th> Posi��o </th>
                    <th> Nome </th>
                    <th> Informa��es para contato</th>
                    <th> Ingresso </th>
                    <th> Produtos </th>
                    <th> B�nus </th>
                    <th> Total </th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${relatorioPontuacao}" var="item">
					<tr>
                        <td class="centralizado" > 
                        	${item.malaDireta.usuario.id_Codigo}
                        </td>
                        <td class="centralizado" > ${item.malaDireta.usuario.posAtual} </td>
                        <td> ${item.malaDireta.usuario.vNome} </td>
                        <td> 
                        	Tels fixos: ${item.malaDireta.usuario.tel} <br>
                        	Celular: ${item.malaDireta.usuario.cadCelular} <br>
                        	Email: ${item.malaDireta.usuario.eMail} <br>
                        </td>
                        <td class="centralizado" > <fmt:formatNumber value="${item.pontuacaoIngresso}" /> </td>
                        <td class="centralizado" > <fmt:formatNumber value="${item.pontuacaoProdutos}" /> </td>
                        <td class="centralizado" > <fmt:formatNumber value="${item.pontuacaoAtividade}" /> </td>
                        <td class="centralizado" > <fmt:formatNumber value="${item.total}" /> </td>
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