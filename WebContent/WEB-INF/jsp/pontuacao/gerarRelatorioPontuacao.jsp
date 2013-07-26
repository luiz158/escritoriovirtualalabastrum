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
	position: absolute;
	right: 20px;
	padding: 20px;
	padding-top: 10px;
	margin-top: -40px;
	box-shadow: 4px 4px 4px #888;
}

.informacoesContato{

	font-weight: bold;
	color: rgb(90, 90, 90);
}

</style>

<a class="btn" href="<c:url value="/pontuacao/acessarTelaPontuacao"/>" > Voltar </a>

<div class="resumo" >
	
	<h5 style="text-align: center" > RESUMO </h5>
	<h6 style="margin-top: 20px" > Situação pessoal:  ${situacaoPessoalAtividade}  </h6>
	<h6> Pontuação pessoal: <fmt:formatNumber value="${pontuacaoPessoalUsuarioPesquisado}" />  </h6>
	<h6> Pontuação da rede: <fmt:formatNumber value="${pontuacaoRede}" />  </h6>
	<h6> Ativos diretos: <fmt:formatNumber value="${ativosDiretos}" />  </h6>
	<h6> Ativos na rede: <fmt:formatNumber value="${todosAtivos}" />  </h6>

</div>

<br><br>

<h3> Pontuação </h3>

<h6 style="color: rgb(100, 100, 100);" >Líder da equipe: ${usuarioPesquisado.id_Codigo} - ${usuarioPesquisado.posAtual} - ${usuarioPesquisado.vNome} / ${usuarioPesquisado.vNomeTitular2} </h6>

<h6 style="color: rgb(100, 100, 100);" >Posição considerada: ${posicaoConsiderada}    </h6>

<h6 style="color: rgb(100, 100, 100);" >Período: <fmt:formatDate value="${dataInicialPesquisada.time}" type="DATE" /> - <fmt:formatDate value="${dataFinalPesquisada.time}" type="DATE" />    </h6>

<h6 style="color: rgb(100, 100, 100);" >Possui movimentação? ${possuiMovimentacao} </h6>

<h6 style="color: rgb(100, 100, 100);" >Ativo? ${ativo} </h6>

<h6 style="color: rgb(160, 30, 30);" > Quantidade de registros: ${quantidadeElementos} <span class="dica" > (<b>Dica:</b> Para encontrar registros mais rapidamente, utilize a pesquisa através do atalho <b>CTRL + F</b>) </span> </h6> 

<br>

<c:choose>
	<c:when test="${!empty relatorioPontuacao}">

		<table class="table table-striped table-bordered">
			<thead>
		    	<tr>
                    <th> Código </th>
                    <th> Posição </th>
                    <th> Geração </th>
                    <th> Nome </th>
                    <th> Informações para contato</th>
                    <th> Ingresso </th>
                    <th> Produtos </th>
                    <th> Atividade </th>
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
                        <td class="centralizado" > ${item.malaDireta.nivel} </td>
                        <td> ${item.malaDireta.usuario.vNome} </td>
                        <td> 
                        	<span class="informacoesContato" >Tels fixos:</span> ${item.malaDireta.usuario.tel} <br>
                        	<span class="informacoesContato" >Celular:</span> ${item.malaDireta.usuario.cadCelular} <br>
                        	<span class="informacoesContato" >Email:</span> ${item.malaDireta.usuario.eMail} <br>
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