<%@ include file="/base.jsp" %>

<style>

	.divPagamento{
		border: 1px solid #ddd;
		border-radius: 5px;
		padding: 20px;
		padding-top: 30px;
	}

</style> 

	
<form class="form-horizontal" action="<c:url value="/pedido/ETAPAAINDASEMNOME"/>" method="post" id="formFormasPagamento" >
  <fieldset>
    <legend> Forma de pagamento </legend>
    
    <label class="radio">
		<input type="radio" id="formaPagamentoCartaoCredito" name="formaPagamento" value="formaPagamentoCartaoCredito" >
		Cartão de crédito
	</label>
	<label class="radio">
		<input type="radio" id="formaPagamentoDepositoBancario" name="formaPagamento" value="formaPagamentoDepositoBancario" >
		Depósito bancário
	</label>
	<label class="radio">
		<input type="radio" id="formaPagamentoDinheiro" name="formaPagamento" value="formaPagamentoDinheiro" >
		Dinheiro (Necessário comparecimento presencial)
	</label>
	<label class="radio">
		<input type="radio" id="formaPagamentoCartaoDebito" name="formaPagamento" value="formaPagamentoCartaoDebito" >
		Cartão de débito (Necessário comparecimento presencial)
	</label>
	<label class="radio" style="color: #aaa;" >
		<input type="radio" id="formaPagamentoCartaoDebito" name="formaPagamento" disabled="disabled" >
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
	        		<input type="text" class="input-xxlarge" name="nomeNoCartao" required="required" >
	        	</div>
	      	</div>
	      	<div class="control-group warning">
	      		<label class="control-label">Bandeira: <span style="color: #ccc" > (Visa, Mastercard, etc) </span></label>
	      		<div class="controls">
	        		<input type="text" class="input-medium" name="bandeiraCartao" required="required" >
	        	</div>
	      	</div>
	      	<div class="control-group warning">
	      		<label class="control-label">Número: </label>
	      		<div class="controls">
	        		<input type="text" class="input-large numero-inteiro" name="numeroCartao" required="required" >
	        	</div>
	      	</div>
	      	<div class="control-group warning">
	      		<label class="control-label">Data de validade: </label>
	      		<div class="controls">
	        		<input type="text" class="input-medium" name="dataValidadeCartao" required="required" >
	        	</div>
	      	</div>
	      	<div class="control-group warning">
	      		<label class="control-label">Código de segurança: </label>
	      		<div class="controls">
	        		<input type="text" class="input numero-inteiro" name="codigoSegurancaCartao" required="required" >
	        	</div>
	      	</div>
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
	
	function selecionarFormaPagamento(){
		
		jQuery("#divCartaoCredito").hide();
		
		if(jQuery("#formFormasPagamento input[type='radio']:checked").attr("id") == 'formaPagamentoCartaoCredito'){
			
			jQuery("#divCartaoCredito").show();
			jQuery("#botaoAvancar").show();
		}	
	}

</script>
