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

<a class="btn" onclick="window.history.back()" > Voltar </a>

<br><br>

<h3> Bônus de Primeira Compra </h3>

<h6 style="color: rgb(100, 100, 100);" >Mês/ano: ${mesAno}</h6>
<h6 style="color: rgb(100, 100, 100);" >Kit do distribuidor: ${kitUsuarioLogado}</h6>
<h6 style="color: rgb(100, 100, 100);" >Porcentagem do kit: ${porcentagemKitUsuarioLogado}%</h6>

<c:choose>
	<c:when test="${!empty bonificacoes || !empty bonificacoesDiamante}">

		<table class="table table-striped table-bordered">
			<thead>
		    	<tr>
                    <th> Distribuidor </th>
                    <th> Geração </th>
                    <th> Kit </th>
                    <th> Bonificação </th>
                    <th> Como foi calculado </th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${bonificacoes}" var="item">
					<tr>
                        <td> ${item.usuario.id_Codigo} - ${item.usuario.vNome} </td>
                        <td class="centralizado" > ${item.geracao} </td>
                        <td class="centralizado" > ${item.kit} </td>
                        <td class="centralizado" > R$<fmt:formatNumber value="${item.bonificacao}" pattern="#,##0.00" /> </td>
                        <td class="centralizado" > ${item.comoFoiCalculado} </td>
					</tr>
				</c:forEach>
				<c:forEach items="${bonificacoesDiamante}" var="item">
					<tr>
                        <td> ${item.usuario.id_Codigo} - ${item.usuario.vNome} </td>
                        <td></td>
                        <td class="centralizado" > ${item.kit} </td>
                        <td class="centralizado" > R$<fmt:formatNumber value="${item.bonificacao}" pattern="#,##0.00" /> </td>
                        <td class="centralizado" > Repasse de ${item.comoFoiCalculado} pontos </td>
					</tr>
				</c:forEach>
			</tbody>
			<tfoot>
				<tr style="background-color: rgb(245, 250, 138);" >
                    <td class="centralizado" colspan="3" > <b> Total </b> </td>
					<td class="centralizado" > R$<fmt:formatNumber value="${somatorioBonificacao + somatorioBonificacoesDiamante}" pattern="#,##0.00" /> </td>
					<td></td>
				</tr>
			</tfoot>
		</table>

	</c:when>
	<c:otherwise>
		<br>  <br>  <h4> Nenhum registro foi encontrado </h4>
	</c:otherwise>
</c:choose>

<c:if test="${isDiamante}">

	<br>
	
	<p> Por ser diamante, além das bonificações normais da Alabastrum, você recebe algumas outras bonificações se suas metas como diamante forem atendidas </p>
	<p> <b> As metas são: </b> </p>
	<p> Pontuação no mês: ${metaDiamantePontuacao} </p>
	<p> Quantidade de graduados em linhas diferentes: ${metaDiamanteGraduados} </p>
	<br>
	<p> <b> Você alcançou, até o momento, os seguintes resultados: </b> </p>
	<p> Pontuação no mês: <fmt:formatNumber value="${pontuacaoAlcancadaPeloDiamante}" pattern="#,##0.00" /> </p>

	<p> Quantidade de graduados em linhas diferentes: ${graduadosAlcancadosPeloDiamante}</p>
	
	<br>
	
	<p> <b> Graduados até o momento: </b> </p>
	
	<ul>
		<c:forEach items="${graduadosEncontrados}" var="item">
			<li>
	         	${item.value.usuario.id_Codigo} - ${item.value.usuario.vNome}
			</li>
	    </c:forEach>
	</ul>
	
	<c:if test="${!empty diamantesComMetasAlcancadas}">  
	
		<br>
		
		<p> <b> Diamante(s) qualificado(s) que pertence(m) à sua linha descendente: </b>  </p>
		
		<ul>
			<c:forEach items="${diamantesComMetasAlcancadas}" var="item">
				<li>
	            	${item.value.usuario.id_Codigo} - ${item.value.usuario.vNome}
				</li>
           	</c:forEach>
        </ul>
     </c:if>
  </c:if>
