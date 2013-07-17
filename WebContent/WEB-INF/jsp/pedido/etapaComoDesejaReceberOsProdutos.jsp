<%@ include file="/base.jsp" %>

<style>

	.divComoDesejaReceberOsProdutos{
		border: 1px solid #ddd;
		border-radius: 5px;
		padding: 20px;
		padding-top: 30px;
	}

</style> 
	
<form class="form-horizontal" action="<c:url value="/pedido/etapaConfirmacaoEmail"/>" method="post" id="divComoDesejaReceberOsProdutos"  >
  <fieldset>
    <legend> Como deseja receber os produtos? </legend>
    
    <label class="radio">
		<input type="radio" id="Sedex" name="sessaoPedido.comoDesejaReceberOsProdutos" value="Sedex" <c:if test="${sessaoPedido.comoDesejaReceberOsProdutos == 'Sedex'}"> checked="checked" </c:if>  >
		Sedex
	</label>
	<label class="radio">
		<input type="radio" id="PAC" name="sessaoPedido.comoDesejaReceberOsProdutos" value="PAC" <c:if test="${sessaoPedido.comoDesejaReceberOsProdutos == 'PAC'}"> checked="checked" </c:if>  >
		PAC
	</label>
	<label class="radio">
		<input type="radio" id="meiosProprios" name="sessaoPedido.comoDesejaReceberOsProdutos" value="MeiosProprios" <c:if test="${sessaoPedido.comoDesejaReceberOsProdutos == 'MeiosProprios'}"> checked="checked" </c:if>  >
		Meios próprios
	</label>
	
	<br>
	
	<input type="button" class="btn" onclick="selecionarComoDesejaReceberOsProdutos()" value="Selecionar" >

	<br>
	<br>
	<br>
	
	<div id="divSedexOuPAC" style="display: none" >
	
		<h5 id="tituloSedex" style="display: none;" > Sedex </h5>
		<h5 id="tituloPAC" style="display: none;" > PAC </h5>
		
		<div class="divComoDesejaReceberOsProdutos" >
			
			<p>  
				Você receberá por e-mail o valor do frete para o seu pedido juntamente com o valor total (Produtos + frete)
			</p>
			<p>  
				Esses valores serão calculados levando em consideração o peso dos produtos escolhidos e também a distância do local de entrega informado.
			</p>
			<p>  
				Você deverá responder o e-mail confirmando se concorda ou não com os valores
			</p>
			<br>
			<p>  
				Informe o CEP para entrega: <input type="text" name="sessaoPedido.cep" value="${sessaoPedido.cep}" class="input-medium"  >
				<a style="margin-left: 30px;" href="http://www.buscacep.correios.com.br" target="_blank" > Pesquisar CEP </a>
			</p>
			<p>  
				Informe o endereço completo para entrega: <input type="text" name="sessaoPedido.enderecoEntrega" value="${sessaoPedido.enderecoEntrega}" class="input-xxlarge"  >
			</p>

	    </div>
	</div>
	
	<div id="divMeiosProprios" style="display: none" >
	
		<h5> Meios próprios </h5>
		
		<div class="divComoDesejaReceberOsProdutos" >

			<p>  
				Obs: Para pegar os produtos presencialmente, é necessária a apresentação da identidade(RG) e do comprovante de pagamento.
			</p>
			
			<p>  
				Escolha abaixo o centro de distribuição que você prefere e também informe a data e a hora que irá buscá-lo.
			</p>
			
			<select id="centroDistribuicao" name="sessaoPedido.centroDistribuicao" onchange="escolherCentroDistribuicao()"  >
          		<option value="" > Selecione </option>
          		<option value="Madureira" <c:if test="${sessaoPedido.centroDistribuicao == 'Madureira'}"> selected='selected' </c:if> > Madureira </option>
		  	</select>
		  	
		  	<br><br>
		  	
		  	<p>  
				Endereço: <b id="enderecoCentroDistribuicao" >  </b>
			</p>
			
			<br>
			
			<p>  
				Data e hora: <input type="text" name="sessaoPedido.dataHoraEscolhida" value="${sessaoPedido.dataHoraEscolhida}" class="input-xlarge"  >
			</p>
					
	    </div>
	</div>
	
	<br>
	<br>
	<br>
	
	<a class="btn" href="<c:url value="/pedido/etapaFormasPagamento"/>" > Voltar </a>
	<button id="botaoAvancar" type="submit" class="btn btn-primary"  style="margin-left: 30px; display: none"  > Avançar </button>

  </fieldset>
</form>

<script>

	jQuery(document).ready(function(){
		
		selecionarComoDesejaReceberOsProdutos();
	});

	function escolherCentroDistribuicao(){
		
		if(jQuery("#centroDistribuicao").val() == 'Madureira'){
			
			jQuery("#enderecoCentroDistribuicao").text("R. Carolina Machado, 380 - Madureira  Rio de Janeiro, 21351-021 - Tel: (21) 3390-4463");
		}
	}
	
	function selecionarComoDesejaReceberOsProdutos(){
		
		jQuery("#divSedexOuPAC").hide();
		jQuery("#tituloSedex").hide();
		jQuery("#tituloPAC").hide();		
		jQuery("#divMeiosProprios").hide();
		jQuery("#botaoAvancar").hide();

		if(jQuery("#divComoDesejaReceberOsProdutos input[type='radio']:checked").attr("id") == 'Sedex'){
			
			jQuery("#divSedexOuPAC").show();
			jQuery("#tituloSedex").show();
			jQuery("#botaoAvancar").show();
		}
		
		if(jQuery("#divComoDesejaReceberOsProdutos input[type='radio']:checked").attr("id") == 'PAC'){
			
			jQuery("#divSedexOuPAC").show();
			jQuery("#tituloPAC").show();
			jQuery("#botaoAvancar").show();
		}
		
		if(jQuery("#divComoDesejaReceberOsProdutos input[type='radio']:checked").attr("id") == 'meiosProprios'){
			
			jQuery("#divMeiosProprios").show();
			jQuery("#botaoAvancar").show();
		}
	}

</script>
