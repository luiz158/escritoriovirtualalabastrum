<%@ include file="/base.jsp" %>

<style>

	.divPagamento{
		border: 1px solid #ddd;
		border-radius: 5px;
		padding: 20px;
		padding-top: 30px;
	}

</style> 
	
<form class="form-horizontal" action="<c:url value="/pedido/etapaComoDesejaReceberOsProdutos"/>" method="post" id="formFormasPagamento" >
  <fieldset>
    <legend> Forma de pagamento </legend>
    
    <label class="radio">
		<input type="radio" id="formaPagamentoCartaoCredito" name="sessaoPedido.formaPagamento" value="formaPagamentoCartaoCredito" <c:if test="${sessaoPedido.formaPagamento == 'formaPagamentoCartaoCredito'}"> checked="checked" </c:if>  >
		Cartão de crédito
	</label>
	<label class="radio">
		<input type="radio" id="formaPagamentoDepositoBancario" name="sessaoPedido.formaPagamento" value="formaPagamentoDepositoBancario" <c:if test="${sessaoPedido.formaPagamento == 'formaPagamentoDepositoBancario'}"> checked="checked" </c:if>  >
		Depósito bancário
	</label>
	<label class="radio">
		<input type="radio" id="formaPagamentoDinheiro" name="sessaoPedido.formaPagamento" value="formaPagamentoDinheiro" <c:if test="${sessaoPedido.formaPagamento == 'formaPagamentoDinheiro'}"> checked="checked" </c:if>  >
		Dinheiro (Necessário comparecimento presencial)
	</label>
	<label class="radio">
		<input type="radio" id="formaPagamentoCartaoDebito" name="sessaoPedido.formaPagamento" value="formaPagamentoCartaoDebito" <c:if test="${sessaoPedido.formaPagamento == 'formaPagamentoCartaoDebito'}"> checked="checked" </c:if>  >
		Cartão de débito (Necessário comparecimento presencial)
	</label>
	<label class="radio">
		<input type="radio" id="formaPagamentoCreditoBonificacao" name="sessaoPedido.formaPagamento" value="formaPagamentoCreditoBonificacao" <c:if test="${sessaoPedido.formaPagamento == 'formaPagamentoCreditoBonificacao'}"> checked="checked" </c:if>  >
		Crédito (bonificação a receber)
	</label>
	<label class="radio" style="color: #aaa;" >
		<input type="radio" id="formaPagamentoBoleto" name="sessaoPedido.formaPagamento" disabled="disabled" >
		Boleto(Ainda não disponível)
	</label>
	
	<br>
	
	<input type="button" class="btn" onclick="selecionarFormaPagamento()" value="Selecionar" >

	<br>
	<br>
	<br>
	
	<div id="divCartaoCredito" style="display: none" >
	
		<h5> Cartão de crédito </h5>
		
		<div class="divPagamento" >
			<div class="control-group warning">
	      		<label class="control-label">Nome no cartão:</label>
	      		<div class="controls">
	        		<input type="text" class="input-xlarge" name="sessaoPedido.nomeNoCartao" value="${sessaoPedido.nomeNoCartao}"  >
	        	</div>
	      	</div>
	      	<div class="control-group warning">
	      		<label class="control-label">Bandeira: <span style="color: #ccc" > (Visa, Mastercard etc) </span></label>
	      		<div class="controls">
	        		<input type="text" class="input-medium" name="sessaoPedido.bandeiraCartao" value="${sessaoPedido.bandeiraCartao}"  >
	        	</div>
	      	</div>
	      	<div class="control-group warning">
	      		<label class="control-label">Número: </label>
	      		<div class="controls">
	        		<input type="text" class="input-medium numero-inteiro" name="sessaoPedido.numeroCartao" value="${sessaoPedido.numeroCartao}"  >
	        	</div>
	      	</div>
	      	<div class="control-group warning">
	      		<label class="control-label">Data de validade: </label>
	      		<div class="controls">
	        		<input type="text" class="input-medium" name="sessaoPedido.dataValidadeCartao" value="${sessaoPedido.dataValidadeCartao}"  >
	        	</div>
	      	</div>
	      	<div class="control-group warning">
	      		<label class="control-label">Código de segurança: </label>
	      		<div class="controls">
	        		<input type="text" class="input-medium numero-inteiro" name="sessaoPedido.codigoSegurancaCartao" value="${sessaoPedido.codigoSegurancaCartao}"  >
	        	</div>
	      	</div>
	      	
	      	<div class="control-group warning">
	      		<label class="control-label">Quantidade de parcelas: </label>
	      		<div class="controls">
	        		<select name="sessaoPedido.quantidadeParcelas" style="width: 100px;" >
	        			<option value="1" <c:if test="${sessaoPedido.quantidadeParcelas == '1'}"> selected='selected' </c:if> >1</option>
	        			<option value="2" <c:if test="${sessaoPedido.quantidadeParcelas == '2'}"> selected='selected' </c:if> >2</option>
	        			<option value="3" <c:if test="${sessaoPedido.quantidadeParcelas == '3'}"> selected='selected' </c:if> >3</option>
	        			<option value="4" <c:if test="${sessaoPedido.quantidadeParcelas == '4'}"> selected='selected' </c:if> >4</option>
	        			<option value="5" <c:if test="${sessaoPedido.quantidadeParcelas == '5'}"> selected='selected' </c:if> >5</option>
	        			<option value="6" <c:if test="${sessaoPedido.quantidadeParcelas == '6'}"> selected='selected' </c:if> >6</option>
	        		</select>
	        		<span style="color: #ccc" > (Juros da administradora) </span>
	        	</div>
	      	</div>
	    </div>
	</div>
	
	<div id="divDepositoBancario" style="display: none" >
	
		<h5> Depósito bancário </h5>
		
		<div class="divPagamento" >
			
			<p style="font-size: 18px;" >  
				Instruções para pagamento por depósito bancário:
			</p>
			<br><br>
			<p>  
				Após finalizada a realização do pedido aqui no escritório virtual. Faça o depósito em uma das seguintes contas:
			</p>
			<p>  
				<b>Banco:</b> HSBC
			</p>
			<p>  
				<b>Número da agência:</b> 0491
			</p>
			<p>  
				<b>Número da conta:</b> 13907-18
			</p>
			<hr>
			<p>  
				<b>Banco:</b> ITAÚ
			</p>
			<p>  
				<b>Número da agência:</b> 9162
			</p>
			<p>  
				<b>Número da conta:</b> 16602-0
			</p>
			
			<br><br>
			<p>  
				Depois de realizado o depósito. Envie o comprovante do pagamento para o email: <b> pedidos@alabastrum.com.br </b> 
			</p>
			<p>  
				O assunto do email deve ser: Comprovante de depósito bancário referente ao pedido ${sessaoPedido.codigoPedido}
			</p>
			<p>  
				No conteúdo do email deve conter todos os dados referentes ao depósito.
			</p>
			<p>  
				Você também deve anexar no email o comprovante do depósito gerado pelo banco.
			</p>
			
			<p>  
				Clique em <b> Avançar </b>
			</p>
			
	    </div>
	</div>
	
	<div id="divDinheiroECartaoDebito" style="display: none" >
	
		<h5> Dinheiro ou cartão de débito </h5>
		
		<div class="divPagamento" >
			
			<p>  
				Os pagamentos feitos em dinheiro ou em débito são feitos somente presencialmente.
			</p>	
			
			<p>  
				É muito útil fazer o seu pedido via escritório virtual antes de buscá-lo, pois agiliza o processo e você será atendido mais rapidamente.
			</p>
			
			<br>
			
			<p>  
				Escolha abaixo o centro de distribuição que você prefere e também informe a data e a hora que irá buscá-lo.
			</p>
			
			<select id="centroDistribuicao" name="sessaoPedido.centroDistribuicao" onchange="escolherCentroDistribuicao()"  >
          		<option value="" > Selecione </option>
          		<c:forEach items="${centrosDistribuicao}" var="centroDistribuicao" >
          			<option value="${centroDistribuicao.estqNome}" <c:if test="${sessaoPedido.centroDistribuicao == centroDistribuicao.estqNome}"> selected='selected' </c:if> > ${centroDistribuicao.estqNome} </option>
          		</c:forEach>
		  	</select>
		  	
		  	<c:forEach items="${centrosDistribuicao}" var="centroDistribuicao" >
		  		<input type="hidden" id="${centroDistribuicao.nomeCentroSemEspacos}" value="${centroDistribuicao.estqEndereco} - ${centroDistribuicao.estqBairro} - ${centroDistribuicao.estqCidade} - ${centroDistribuicao.estqUF} - ${centroDistribuicao.estqCEP} - ${centroDistribuicao.estqTelefone} - ${centroDistribuicao.estqEmail}" />
          	</c:forEach>
		  	
		  	<br><br>
		  	
		  	<p>  
				Informações: <b id="enderecoCentroDistribuicao" >  </b>
			</p>
			
			<br>
			
			<p>  
				Data/hora/observações: <textarea name="sessaoPedido.dataHoraEscolhida" class="data-hora-observacoes"  > ${sessaoPedido.dataHoraEscolhida} </textarea>
			</p>
					
	    </div>
	</div>
	
	<div id="divCreditoBonificacao" style="display: none" >
	
		<h5> Crédito (bonificação a receber) </h5>
		
		<div class="divPagamento" >
			
			<p>  
				Você atualmente tem <b>${sessaoPedido.credito}</b> em crédito na Alabastrum.
			</p>	
			
			<p>  
				O valor total de sua compra não pode ultrapassar a quantidade de créditos que você tem atualmente.
			</p>
					
	    </div>
	</div>

	<br>
	<br>
	<br>
	
	<a class="btn" href="<c:url value="/pedido/voltarParaSelecaoProdutos"/>" > Voltar </a>
	<button id="botaoAvancar" type="submit" class="btn btn-primary"  style="margin-left: 30px; display: none"  > Avançar </button>

  </fieldset>
</form>

<script>

	jQuery(document).ready(function(){
		
		selecionarFormaPagamento();
		escolherCentroDistribuicao();
	});

	function escolherCentroDistribuicao(){
			
		jQuery("#enderecoCentroDistribuicao").text(jQuery("#" + replaceAll(jQuery("#centroDistribuicao").val(), " ", "")).val());
	}
	
	function selecionarFormaPagamento(){
		
		jQuery("#divCartaoCredito").hide();
		jQuery("#divDepositoBancario").hide();
		jQuery("#divDinheiroECartaoDebito").hide();
		jQuery("#divCreditoBonificacao").hide();
		jQuery("#botaoAvancar").hide();

		if(jQuery("#formFormasPagamento input[type='radio']:checked").attr("id") == 'formaPagamentoCartaoCredito'){
			
			jQuery("#divCartaoCredito").show();
			jQuery("#botaoAvancar").show();
		}
		
		if(jQuery("#formFormasPagamento input[type='radio']:checked").attr("id") == 'formaPagamentoDepositoBancario'){
			
			jQuery("#divDepositoBancario").show();
			jQuery("#botaoAvancar").show();
		}
		
		if(jQuery("#formFormasPagamento input[type='radio']:checked").attr("id") == 'formaPagamentoCreditoBonificacao'){
			
			jQuery("#divCreditoBonificacao").show();
			jQuery("#botaoAvancar").show();
		}
		
		if(jQuery("#formFormasPagamento input[type='radio']:checked").attr("id") == 'formaPagamentoCartaoDebito' || jQuery("#formFormasPagamento input[type='radio']:checked").attr("id") == 'formaPagamentoDinheiro' ){
			
			jQuery("#divDinheiroECartaoDebito").show();
			jQuery("#botaoAvancar").show();
		}
	}

</script>
