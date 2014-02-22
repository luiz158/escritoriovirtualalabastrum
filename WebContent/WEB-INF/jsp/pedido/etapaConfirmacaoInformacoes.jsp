<%@ include file="/base.jsp" %>

<style>

	.formConfirmacaoPedido{
		border: 1px solid #ddd;
		border-radius: 5px;
		padding: 20px;
		padding-top: 30px;
	}
	
	.subTitulos{
		font-size: 16px;
		color: rgb(98, 113, 224);
	}

</style> 
	
<form class="form-horizontal formConfirmacaoPedido" action="<c:url value="/pedido/finalizarPedido"/>" method="post"  >
  <fieldset>
  
    <h3 style="text-align: center" > Confirmação do pedido </h3>
    
    <br>
    
    <b> Código do pedido via Escritório Virtual: </b> <span> ${sessaoPedido.codigoPedido} </span>

    <c:if test="${sessaoPedido.tipoPedido == 'realizarPedidoPorOutroDistribuidor'}">
    
    	<br> <br> <br> 
    
    	<b> Tipo do pedido: </b> <span> Realizar pedido por outro distribuidor </span> <br>
    	<b> Código do distribuidor: </b> <span> ${sessaoPedido.codigoOutroDistribuidor} </span> <br>
    	<b> Nome do distribuidor: </b> <span> ${nomeOutroDistribuidor} </span>
    	
    </c:if>  
    
    <c:if test="${sessaoPedido.tipoPedido == 'realizarPedidoParaUmCentroDeDistribuicao'}">
    
    	<br> <br> <br> 
    
    	<b> Tipo do pedido: </b> <span> Realizar pedido para um centro de distribuição </span> <br>
    	<b> Centro de distribuição: </b> <span> ${sessaoPedido.centroDistribuicaoDoResponsavel} </span>
    	
    </c:if>   
    
    <br> <br> <br>     
    
    <h5 class="subTitulos" > Produtos </h5>
    <table class="table" >
		<thead>
	    	<tr>
	            <th> Código </th>
	            <th> Produto </th>
	            <th> Preço unitário </th>
	            <th> Pontuação unitária </th>
	            <th> Quantidade </th>
	            <th> Preço total </th>
	            <th> Pontuação total </th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${produtos}" var="produto" >
				<tr>
		            <td class='centralizado' >${produto.id_Produtos}</td>
		            <td class='centralizado' > ${produto.prdNome} </td>
		            <td class='centralizado' > ${produto.prdPreco_Unit} </td>
		            <td class='centralizado' > ${produto.prdPontos} </td>
		            <td class='centralizado' > ${produto.quantidade}  </td>
		            <td class='centralizado' > <fmt:formatNumber value="${produto.total}" /> </td>
		            <td class='centralizado' > <fmt:formatNumber value="${produto.pontuacaoTotal}" /> </td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	
	<b> Preço total: </b> <span> R$ <fmt:formatNumber value="${total}" />  (Frete não incluído) </span> <br>
	<b> Pontuação total: </b> <span> <fmt:formatNumber value="${pontuacaoTotal}" /> </span>
	
	<br> <br> <br> 
	
	<h5 class="subTitulos" > Forma de pagamento </h5>
	<c:if test="${sessaoPedido.formaPagamento == 'formaPagamentoCartaoCredito'}">
		
		<p style="font-size: 16px;" > Cartão de crédito </p>
		<b> Nome no cartão: </b> <span> ${sessaoPedido.nomeNoCartao} </span> <br>
		<b> Bandeira: </b> <span> ${sessaoPedido.bandeiraCartao} </span> <br>
		<b> Número: </b> <span> ${sessaoPedido.numeroCartao} </span> <br>
		<b> Data de validade: </b> <span> ${sessaoPedido.dataValidadeCartao} </span> <br>
		<b> Código de segurança: </b> <span> ${sessaoPedido.codigoSegurancaCartao} </span> <br>
		<b> Quantidade de parcelas: </b> <span> ${sessaoPedido.quantidadeParcelas} </span> <br>

	</c:if>
	
	<c:if test="${sessaoPedido.formaPagamento == 'formaPagamentoDepositoBancario'}">
		
		<p style="font-size: 16px;" > Depósito bancário </p>

	</c:if>
	
	<c:if test="${sessaoPedido.formaPagamento == 'formaPagamentoDinheiro'}">
		
		<p style="font-size: 16px;" > Dinheiro </p>
		<b> Local: </b> <span> ${sessaoPedido.centroDistribuicao} </span> <br>
		<b> Data/hora/observações: </b> <span> ${sessaoPedido.dataHoraEscolhida} </span> <br>

	</c:if>
	
	<c:if test="${sessaoPedido.formaPagamento == 'formaPagamentoCartaoDebito'}">
		
		<p style="font-size: 16px;" > Cartão de débito </p>
		<b> Local: </b> <span> ${sessaoPedido.centroDistribuicao} </span> <br>
		<b> Data/hora/observações: </b> <span> ${sessaoPedido.dataHoraEscolhida} </span> <br>

	</c:if>
	
	<c:if test="${sessaoPedido.formaPagamento == 'formaPagamentoCreditoBonificacao'}">
		
		<p style="font-size: 16px;" > Crédito (bonificação a receber) </p>

	</c:if>

	<c:if test="${(sessaoPedido.formaPagamento == 'formaPagamentoCartaoCredito')  || (sessaoPedido.formaPagamento == 'formaPagamentoDepositoBancario') || (sessaoPedido.formaPagamento == 'formaPagamentoCreditoBonificacao')}">
   		
		<br> <br> <br> 
			
		<h5 class="subTitulos" > Entrega </h5>
		
		<c:if test="${sessaoPedido.comoDesejaReceberOsProdutos == 'Sedex'}">
			
			<p style="font-size: 16px;" > Sedex </p>
			<b> CEP: </b> <span> ${sessaoPedido.cep} </span> <br>
			<b> Endereço para entrega: </b> <span> ${sessaoPedido.enderecoEntrega} </span> <br>
	
		</c:if>	
		
		<c:if test="${sessaoPedido.comoDesejaReceberOsProdutos == 'PAC'}">
			
			<p style="font-size: 16px;" > PAC </p>
			<b> CEP: </b> <span> ${sessaoPedido.cep} </span> <br>
			<b> Endereço para entrega: </b> <span> ${sessaoPedido.enderecoEntrega} </span> <br>
	
		</c:if>	
		
		<c:if test="${sessaoPedido.comoDesejaReceberOsProdutos == 'MeiosProprios'}">
			
			<p style="font-size: 16px;" > Meios próprios </p>
			<b> Local: </b> <span> ${sessaoPedido.centroDistribuicao} </span> <br>
			<b> Data/hora/observações: </b> <span> ${sessaoPedido.dataHoraEscolhida} </span> <br>
	
		</c:if>
		
	</c:if>

	<br> <br> <br> 
	
	<b> E-mail para contato: </b> <span> ${sessaoPedido.email} </span> <br>
	
	<br> <br> <br>

	<a class="btn" href="<c:url value="/pedido/etapaConfirmacaoEmail"/>" > Voltar </a>
	<button type="submit" class="btn btn-primary" onclick="this.disabled=true;this.form.submit();" style="margin-left: 30px"  > Concluir </button>

  </fieldset>
</form>