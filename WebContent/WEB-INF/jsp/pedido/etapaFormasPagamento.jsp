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
		Cart�o de cr�dito
	</label>
	<label class="radio">
		<input type="radio" id="formaPagamentoDepositoBancario" name="sessaoPedido.formaPagamento" value="formaPagamentoDepositoBancario" <c:if test="${sessaoPedido.formaPagamento == 'formaPagamentoDepositoBancario'}"> checked="checked" </c:if>  >
		Dep�sito banc�rio
	</label>
	<label class="radio">
		<input type="radio" id="formaPagamentoDinheiro" name="sessaoPedido.formaPagamento" value="formaPagamentoDinheiro" <c:if test="${sessaoPedido.formaPagamento == 'formaPagamentoDinheiro'}"> checked="checked" </c:if>  >
		Dinheiro (Necess�rio comparecimento presencial)
	</label>
	<label class="radio">
		<input type="radio" id="formaPagamentoCartaoDebito" name="sessaoPedido.formaPagamento" value="formaPagamentoCartaoDebito" <c:if test="${sessaoPedido.formaPagamento == 'formaPagamentoCartaoDebito'}"> checked="checked" </c:if>  >
		Cart�o de d�bito (Necess�rio comparecimento presencial)
	</label>
	<label class="radio">
		<input type="radio" id="formaPagamentoCreditoBonificacao" name="sessaoPedido.formaPagamento" value="formaPagamentoCreditoBonificacao" <c:if test="${sessaoPedido.formaPagamento == 'formaPagamentoCreditoBonificacao'}"> checked="checked" </c:if>  >
		Cr�dito (bonifica��o a receber)
	</label>
	<label class="radio" style="color: #aaa;" >
		<input type="radio" id="formaPagamentoBoleto" name="sessaoPedido.formaPagamento" disabled="disabled" >
		Boleto(Ainda n�o dispon�vel)
	</label>
	
	<br>
	
	<input type="button" class="btn" onclick="selecionarFormaPagamento()" value="Selecionar" >

	<br>
	<br>
	<br>
	
	<div id="divCartaoCredito" style="display: none" >
	
		<h5> Cart�o de cr�dito </h5>
		
		<div class="divPagamento" >
			<div class="control-group warning">
	      		<label class="control-label">Nome no cart�o:</label>
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
	      		<label class="control-label">N�mero: </label>
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
	      		<label class="control-label">C�digo de seguran�a: </label>
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
	
		<h5> Dep�sito banc�rio </h5>
		
		<div class="divPagamento" >
			
			<p style="font-size: 18px;" >  
				Instru��es para pagamento por dep�sito banc�rio:
			</p>
			<br><br>
			<p>  
				Ap�s finalizada a realiza��o do pedido aqui no escrit�rio virtual. Fa�a o dep�sito em uma das seguintes contas:
			</p>
			<p>  
				<b>Banco:</b> HSBC
			</p>
			<p>  
				<b>N�mero da ag�ncia:</b> 0491
			</p>
			<p>  
				<b>N�mero da conta:</b> 13907-18
			</p>
			<hr>
			<p>  
				<b>Banco:</b> ITA�
			</p>
			<p>  
				<b>N�mero da ag�ncia:</b> 9162
			</p>
			<p>  
				<b>N�mero da conta:</b> 16602-0
			</p>
			
			<br><br>
			<p>  
				Depois de realizado o dep�sito. Envie o comprovante do pagamento para o email: <b> pedidos@alabastrum.com.br </b> 
			</p>
			<p>  
				O assunto do email deve ser: Comprovante de dep�sito banc�rio referente ao pedido ${sessaoPedido.codigoPedido}
			</p>
			<p>  
				No conte�do do email deve conter todos os dados referentes ao dep�sito.
			</p>
			<p>  
				Voc� tamb�m deve anexar no email o comprovante do dep�sito gerado pelo banco.
			</p>
			
			<p>  
				Clique em <b> Avan�ar </b>
			</p>
			
	    </div>
	</div>
	
	<div id="divDinheiroECartaoDebito" style="display: none" >
	
		<h5> Dinheiro ou cart�o de d�bito </h5>
		
		<div class="divPagamento" >
			
			<p>  
				Os pagamentos feitos em dinheiro ou em d�bito s�o feitos somente presencialmente.
			</p>	
			
			<p>  
				� muito �til fazer o seu pedido via escrit�rio virtual antes de busc�-lo, pois agiliza o processo e voc� ser� atendido mais rapidamente.
			</p>
			
			<br>
			
			<p>  
				Escolha abaixo o centro de distribui��o que voc� prefere e tamb�m informe a data e a hora que ir� busc�-lo.
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
				Informa��es: <b id="enderecoCentroDistribuicao" >  </b>
			</p>
			
			<br>
			
			<p>  
				Data/hora/observa��es: <textarea name="sessaoPedido.dataHoraEscolhida" class="data-hora-observacoes"  > ${sessaoPedido.dataHoraEscolhida} </textarea>
			</p>
					
	    </div>
	</div>
	
	<div id="divCreditoBonificacao" style="display: none" >
	
		<h5> Cr�dito (bonifica��o a receber) </h5>
		
		<div class="divPagamento" >
			
			<p>  
				Voc� atualmente tem <b>${sessaoPedido.credito}</b> em cr�dito na Alabastrum.
			</p>	
			
			<p>  
				O valor total de sua compra n�o pode ultrapassar a quantidade de cr�ditos que voc� tem atualmente.
			</p>
					
	    </div>
	</div>

	<br>
	<br>
	<br>
	
	<a class="btn" href="<c:url value="/pedido/voltarParaSelecaoProdutos"/>" > Voltar </a>
	<button id="botaoAvancar" type="submit" class="btn btn-primary"  style="margin-left: 30px; display: none"  > Avan�ar </button>

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
