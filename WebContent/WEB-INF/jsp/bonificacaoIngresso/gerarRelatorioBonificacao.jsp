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

<a class="btn" href="<c:url value="/bonificacaoIngresso/acessarTelaBonificacao"/>" > Voltar </a>

<br><br>

<h3> Bonificação por ingresso </h3>

<h6 style="color: rgb(100, 100, 100);" >Mês/ano: ${mesAno}</h6>
<h6 style="color: rgb(100, 100, 100);" >Kit do distribuidor: ${kitUsuarioLogado}</h6>
<h6 style="color: rgb(100, 100, 100);" >Porcentagem do kit: ${porcentagemKitUsuarioLogado}%</h6>

<c:choose>
	<c:when test="${!empty bonificacoes}">

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
			</tbody>
			<tfoot>
				<tr style="background-color: rgb(245, 250, 138);" >
                    <td class="centralizado" colspan="3" > <b> Total </b> </td>
					<td class="centralizado" > R$<fmt:formatNumber value="${somatorioBonificacao}" pattern="#,##0.00" /> </td>
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

	<br><br>
	
	<h3> Bonificação de diamante </h3>
	
	<br>
	
	<p> Por ser diamante, além das bonificações normais da Alabastrum, você recebe algumas outras bonificações se suas metas como diamante forem atendidas </p>
	<p> As metas são: </p>
	<p> Pontuação no mês: ${metaDiamantePontuacao} </p>
	<p> Quantidade de graduados em linhas diferentes: ${metaDiamanteGraduados} </p>
	<br>
	<p> Você alcançou, até o momento, os seguintes resultados: </p>
	<p> Pontuação no mês: ${pontuacaoAlcancadaPeloDiamante} </p>
	<c:choose>
		<c:when test="${graduadosAlcancadosPeloDiamante >= metaDiamanteGraduados}">
			<p> Quantidade de graduados em linhas diferentes: ${graduadosAlcancadosPeloDiamante} ou mais </p>
		</c:when>
		<c:otherwise>
			<p> Quantidade de graduados em linhas diferentes: ${graduadosAlcancadosPeloDiamante}</p>
			
			<table class="table table-striped table-bordered">
			<thead>
		    	<tr>
                    <th> Graduado </th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${graduadosEncontrados}" var="item">
					<tr>
                        <td> ${item.value.usuario.id_Codigo} - ${item.value.usuario.vNome} </td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
			
			
		</c:otherwise>
	</c:choose>
	
	<c:if test="${!empty diamantesComMetasAlcancadas}">  
	
		<br>
		
		<p> Talvez você não tenha recebido algumas bonificações porque alguns diamantes abaixo de você conseguiram bater suas metas.  </p>
		<p> Os diamantes que bateram a meta foram:  </p>
	
		<br>
		
		<table class="table table-striped table-bordered">
			<thead>
		    	<tr>
                    <th> Distribuidor </th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${diamantesComMetasAlcancadas}" var="item">
					<tr>
                        <td> ${item.value.usuario.id_Codigo} - ${item.value.usuario.vNome} </td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	
	</c:if>
	
	<c:if test="${!empty bonificacoesDiamante}"> 
	
		<br><br>
		
		<table class="table table-striped table-bordered">
			<thead>
		    	<tr>
                    <th> Distribuidor </th>
                    <th> Kit </th>
                    <th> Bonificação </th>
                    <th> Como foi calculado </th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${bonificacoesDiamante}" var="item">
					<tr>
                        <td> ${item.usuario.id_Codigo} - ${item.usuario.vNome} </td>
                        <td class="centralizado" > ${item.kit} </td>
                        <td class="centralizado" > R$<fmt:formatNumber value="${item.bonificacao}" pattern="#,##0.00" /> </td>
                        <td class="centralizado" > ${item.comoFoiCalculado} </td>
					</tr>
				</c:forEach>
			</tbody>
			<tfoot>
				<tr style="background-color: rgb(245, 250, 138);" >
                    <td class="centralizado" colspan="2" > <b> Total </b> </td>
					<td class="centralizado" > R$<fmt:formatNumber value="${somatorioBonificacoesDiamante}" pattern="#,##0.00" /> </td>
					<td></td>
				</tr>
			</tfoot>
		</table>
	
	</c:if>

</c:if>
