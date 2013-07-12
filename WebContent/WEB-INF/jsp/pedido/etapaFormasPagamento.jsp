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
		Cart�o de cr�dito
	</label>
	<label class="radio">
		<input type="radio" id="formaPagamentoDepositoBancario" name="formaPagamento" value="formaPagamentoDepositoBancario" >
		Dep�sito banc�rio
	</label>
	<label class="radio">
		<input type="radio" id="formaPagamentoDinheiro" name="formaPagamento" value="formaPagamentoDinheiro" >
		Dinheiro (Necess�rio comparecimento presencial)
	</label>
	<label class="radio">
		<input type="radio" id="formaPagamentoCartaoDebito" name="formaPagamento" value="formaPagamentoCartaoDebito" >
		Cart�o de d�bito (Necess�rio comparecimento presencial)
	</label>
	<label class="radio" style="color: #aaa;" >
		<input type="radio" id="formaPagamentoCartaoDebito" name="formaPagamento" disabled="disabled" >
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
	        		<input type="text" class="input-xlarge" name="nomeNoCartao" required="required" >
	        	</div>
	      	</div>
	      	<div class="control-group warning">
	      		<label class="control-label">Bandeira: <span style="color: #ccc" > (Visa, Mastercard, etc) </span></label>
	      		<div class="controls">
	        		<input type="text" class="input-medium" name="bandeiraCartao" required="required" >
	        	</div>
	      	</div>
	      	<div class="control-group warning">
	      		<label class="control-label">N�mero: </label>
	      		<div class="controls">
	        		<input type="text" class="input-medium numero-inteiro" name="numeroCartao" required="required" >
	        	</div>
	      	</div>
	      	<div class="control-group warning">
	      		<label class="control-label">Data de validade: </label>
	      		<div class="controls">
	        		<input type="text" class="input-medium" name="dataValidadeCartao" required="required" >
	        	</div>
	      	</div>
	      	<div class="control-group warning">
	      		<label class="control-label">C�digo de seguran�a: </label>
	      		<div class="controls">
	        		<input type="text" class="input-medium numero-inteiro" name="codigoSegurancaCartao" required="required" >
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
				Ap�s finalizada a realiza��o do pedido aqui no escrit�rio virtual. Fa�a o dep�sito na seguinte conta:
			</p>
			<p>  
				<b>N�mero da conta:</b> N�MERO AQUI
			</p>
			<p>  
				<b>N�mero da ag�ncia:</b> N�MERO AQUI
			</p>
			<p>  
				<b>Banco:</b> BANCO AQUI
			</p>
			<p>  
				<b>Outros dados:</b>: DADOS AQUI
			</p>
			<br><br>
			<p>  
				Depois de realizado o dep�sito. Envie o comprovante do pagamento para o email: <b> EMAIL AQUI </b> 
			</p>
			<p>  
				O assunto do email deve ser: Comprovante de dep�sito banc�rio referente ao pedido ${sessaoPedido.codigoPedido}
			</p>
			<p>  
				No conte�do do email deve conter os dados referentes ao dep�sito.
			</p>
			<p>  
				Voc� tamb�m deve anexar no email o comprovante do dep�sito gerado pelo banco.
			</p>
			
			<p>  
				Clique em <b> Avan�ar </b>
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
	
	function selecionarFormaPagamento(){
		
		jQuery("#divCartaoCredito").hide();
		jQuery("#divDepositoBancario").hide();
		
		if(jQuery("#formFormasPagamento input[type='radio']:checked").attr("id") == 'formaPagamentoCartaoCredito'){
			
			jQuery("#divCartaoCredito").show();
			jQuery("#botaoAvancar").show();
		}
		
		if(jQuery("#formFormasPagamento input[type='radio']:checked").attr("id") == 'formaPagamentoDepositoBancario'){
			
			jQuery("#divDepositoBancario").show();
			jQuery("#botaoAvancar").show();
		}
	}

</script>
