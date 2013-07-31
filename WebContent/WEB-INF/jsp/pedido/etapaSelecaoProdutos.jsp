<%@ include file="/base.jsp" %> 

<style>

	.quantidade{
		width: 50px;
	}
	
	.pedidoFeitoPorResponsavel {
		border: 2px solid rgb(109, 103, 103);
		border-radius: 5px;
		padding: 20px;
	}
	
	.pedidoFeitoPorResponsavel p {
		font-size: 16px;
	}

</style>
	
<form id="formSelecaoProdutos" class="form-horizontal" action="" method="post" >
  <fieldset>
    <legend> Realizar pedido </legend>

    <c:if test="${!empty sessaoPedido.centroDistribuicaoDoResponsavel}" >

	    <br>
	    
    	<div class="pedidoFeitoPorResponsavel" >

    		<p> Percebemos que você é responsável pelo centro de distribuição: <b>${sessaoPedido.centroDistribuicaoDoResponsavel}.</b> </p>
    		<p> Como responsável, você tem a possibilidade de realizar pedidos por outros distribuidores e também tem a possibilidade de realizar pedidos para um centro de distribuição específico. </p>
    		<p> O que você deseja fazer? </p>
    		<label class="radio">
				<input type="radio" id="realizarPedidoPorOutroDistribuidor" name="sessaoPedido.tipoPedido" value="realizarPedidoPorOutroDistribuidor" <c:if test="${sessaoPedido.tipoPedido == 'realizarPedidoPorOutroDistribuidor'}"> checked="checked" </c:if>  >
				Realizar pedido por outro distribuidor
			</label>
			<label class="radio">
				<input type="radio" id="realizarPedidoParaUmCentroDeDistribuicao" name="sessaoPedido.tipoPedido" value="realizarPedidoParaUmCentroDeDistribuicao" <c:if test="${sessaoPedido.tipoPedido == 'realizarPedidoParaUmCentroDeDistribuicao'}"> checked="checked" </c:if>  >
				Realizar pedido para um centro de distribuição
			</label>
			<label class="radio">
				<input type="radio" id="realizarPedidoParaVoceMesmo" name="sessaoPedido.tipoPedido" value="realizarPedidoParaVoceMesmo" <c:if test="${sessaoPedido.tipoPedido == 'realizarPedidoParaVoceMesmo'}"> checked="checked" </c:if>  >
				Realizar pedido para mim mesmo
			</label>
			
			<br>
	
			<input type="button" class="btn" onclick="selecionarTipoPedido()" value="Selecionar" >
			
			<div id="divRealizarPedidoPorOutroDistribuidor" style="display: none; margin-left: -100px;" >
			
				<br><br>
				
				<div class="control-group warning">
		      		<label class="control-label">Código:</label>
		      		<div class="controls">
		        		<input type="text" class="numero-inteiro" id="codigoOutroDistribuidor" name="sessaoPedido.codigoOutroDistribuidor" value="${sessaoPedido.codigoOutroDistribuidor}" onkeyup="buscarNomeDistribuidor()"  >
		        	</div>
		      	</div>
		      	
		      	<div class="control-group warning">
		      		<label class="control-label">Nome:</label>
		      		<div class="controls">
		        		<input type="text" class="input-xxlarge" id="nomeOutroDistribuidor" disabled="disabled" >
		        	</div>
		      	</div>
				
			</div>
			
			<div id="divRealizarPedidoParaUmCentroDeDistribuicao" style="display: none" >
			
				<br><br>
				
				<div class="control-group warning">
		      		<label class="control-label">Centro de distribuição:</label>
		      		<div class="controls">
		        		<select id="centroDistribuicaoDoResponsavel" name="sessaoPedido.centroDistribuicaoDoResponsavel"  >
			          		<option value="" > Selecione </option>
			          		<c:forEach items="${todosCentrosDistribuicao}" var="centroDistribuicao" >
			          			<option value="${centroDistribuicao.estqNome}" <c:if test="${sessaoPedido.centroDistribuicaoDoResponsavel == centroDistribuicao.estqNome}"> selected='selected' </c:if> > ${centroDistribuicao.estqNome} </option>
			          		</c:forEach>
					  	</select>
		        	</div>
		      	</div>
				
			</div>

    	</div>
    	
	    <br><br>

    </c:if>
    
    
    <div class="control-group warning" > 
        <label class="control-label">Categoria do produto</label>
        <div class="controls">
          <select id="categoria" onchange="listarProdutos()" >
          	<option value="" > Selecione </option>
          	<c:forEach items="${categorias}" var="categoria" >
	          	<option value="${categoria.id_Categoria}" > ${categoria.catNome} </option>
          	</c:forEach>
		  </select>
        </div>
    </div>
    
    <div class="control-group warning" style="display: none" id="divprodutos" > 
        <label class="control-label">Produto</label>
        <div class="controls">
          <select id="produto" onchange="selecionarProduto()" >
          </select>
        </div>
    </div>
    
    <br>
    
    <c:set var="displayProdutosSelecionados" value="none" />
    <c:if test="${!empty produtos}">
    	<c:set var="displayProdutosSelecionados" value="block" />
    </c:if>
    
    <div id="produtosSelecionados" style="display: ${displayProdutosSelecionados}" >
    
	    <h6> Produtos selecionados  </h6>
	    
	    <table class="table" id="tabelaProdutosSelecionados" >
			<thead>
		    	<tr>
		            <th> Código </th>
		            <th> Produto </th>
		            <th> Preço unitário </th>
		            <th> Pontuação unitária </th>
		            <th> Quantidade </th>
		            <th>  </th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${produtos}" var="produto" >
					<tr id="${produto.id_Produtos}" >
			            <td class='centralizado idProduto' >${produto.id_Produtos}</td>
			            <td class='centralizado' > ${produto.prdNome} </td>
			            <td class='centralizado precoUnitario' > ${produto.prdPreco_Unit} </td>
			            <td class='centralizado' > ${produto.prdPontos} </td>
			            <td class='centralizado' > <input type='number' min='1' value='${produto.quantidade}' class='numero-inteiro quantidade' >  </td>
			            <td class='centralizado removerProduto' > <a title='Remover' > <img src='../css/images/excluir.gif' /> </a> </td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
		
		<h3 style="display: inline-block;" > Total: R$<b id="total" >  </b> </h3>  <span> (Frete ainda não incluído) </span>
		
		<br>
		
		<input type="button" class="btn btn-primary" onclick="avancarEtapaFormaPagamento()" value="Avançar" >
		
    </div>

  </fieldset>
</form>

<script>

	function buscarNomeDistribuidor(){
		
		var codigoDistribuidor = jQuery("#codigoOutroDistribuidor").val();
		
		if(codigoDistribuidor != undefined){

			jQuery.ajax({ 
		        type: 'GET',
		        url: '<c:url value="/pedido/buscarNomeDistribuidor?codigoDistribuidor='+codigoDistribuidor + ' "/>',
		        dataType: 'json', 
		        success: function(data) { 
		        	
		        	jQuery("#nomeOutroDistribuidor").val(data.string);
		        }
			});
		}
	}

	function selecionarTipoPedido(){
		
		jQuery("#divRealizarPedidoPorOutroDistribuidor").hide();
		jQuery("#divRealizarPedidoParaUmCentroDeDistribuicao").hide();

		if(jQuery("#formSelecaoProdutos input[type='radio']:checked").attr("id") == 'realizarPedidoPorOutroDistribuidor'){

			jQuery("#divRealizarPedidoPorOutroDistribuidor").fadeIn("slow");
		}	
		
		if(jQuery("#formSelecaoProdutos input[type='radio']:checked").attr("id") == 'realizarPedidoParaUmCentroDeDistribuicao'){

			jQuery("#divRealizarPedidoParaUmCentroDeDistribuicao").fadeIn("slow");
		}
		
		if(jQuery("#formSelecaoProdutos input[type='radio']:checked").attr("id") == 'realizarPedidoParaVoceMesmo'){

			jQuery(".pedidoFeitoPorResponsavel").fadeOut("slow");
		}
	}

	function avancarEtapaFormaPagamento(){
		
		if(jQuery("#total").text().replace(",", ".") < 50){
			
			alert("Atenção. O valor mínimo para compra é de R$50,00 reais. Para continuar, efetue uma compra onde o valor total ultrapasse R$50,00 reais.");			
		}
		
		else{

			var hashProdutosEQuantidades = '';
			
			jQuery("#tabelaProdutosSelecionados tbody tr").each(function(i, item){
				    
				var idProduto = jQuery(item).find(".idProduto").text();
				var quantidade = parseFloat(jQuery(item).find(".quantidade").val());
				
				hashProdutosEQuantidades += idProduto + "=" + quantidade + ",";
			});	
			
			var tipoPedido = jQuery("#formSelecaoProdutos input[type='radio']:checked").attr("id");
			var codigoOutroDistribuidor = jQuery("#codigoOutroDistribuidor").val();
			var centroDistribuicaoDoResponsavel = jQuery("#centroDistribuicaoDoResponsavel").val();
			
			window.location = "<c:url value='/pedido/etapaFormasPagamento?hashProdutosEQuantidades=" + hashProdutosEQuantidades + "&tipoPedido=" + tipoPedido + "&codigoOutroDistribuidor=" + codigoOutroDistribuidor + "&centroDistribuicaoDoResponsavel=" + centroDistribuicaoDoResponsavel + "'/> ";  
		}
	}
	
	function calcularTotal(){
		
		var total = 0;
		
		jQuery("#tabelaProdutosSelecionados tbody tr").each(function(){
			
			total += parseFloat(jQuery(this).find(".precoUnitario").text()) * parseFloat(jQuery(this).find(".quantidade").val());
		});		
		
		jQuery("#total").text(formatarNumero(total.toFixed(2)));
	}
	
	function listarProdutos(){

		jQuery(".alert").hide();
		
		var idCategoria = jQuery("#categoria").val();
		
		if(idCategoria != ''){
			
			jQuery.ajax({ 
		        type: 'GET',
		        url: '<c:url value="/pedido/listarProdutos?idCategoria='+idCategoria + ' "/>',
		        dataType: 'json', 
		        success: function(data) { 
		        	
		           	jQuery("#divprodutos").show();
		           	jQuery("#produto").empty();
		           	jQuery("#produto").append("<option value='' > Selecione </option>");
		        	
		        	jQuery.each(data.list, function(i, item){

		            	jQuery("#produto").append("<option value='" + item.id_Produtos + "' > " + item.id_Produtos + " - " + item.prdNome + " </option>");
		 	    	});
		        }
			});
		}
	}
	
	function selecionarProduto(){
		
		var idProduto = jQuery("#produto").val();
		
		if(idProduto != ''){
			
			jQuery.ajax({ 
		        type: 'GET',
		        url: '<c:url value="/pedido/buscarInformacoesProdutoSelecionado?idProduto='+idProduto + ' "/>',
		        dataType: 'json', 
		        success: function(data) { 

		           	jQuery("#produtosSelecionados").show();
		           	
		           	var idProdutoCaracteresSubstituidos = substituirCaracteresEspeciais(data.produto.id_Produtos);
		           	
		           	if(jQuery("#tabelaProdutosSelecionados #" + idProdutoCaracteresSubstituidos).length == 0){

			            jQuery("#tabelaProdutosSelecionados tbody").append("<tr id='" + idProdutoCaracteresSubstituidos + "' >");
			            jQuery("#tabelaProdutosSelecionados tbody tr:last").append("<td class='centralizado idProduto' >" + data.produto.id_Produtos + "</td>");
			            jQuery("#tabelaProdutosSelecionados tbody tr:last").append("<td class='centralizado' >" + data.produto.prdNome + "</td>");
			            jQuery("#tabelaProdutosSelecionados tbody tr:last").append("<td class='centralizado precoUnitario' >" + data.produto.prdPreco_Unit + "</td>");
			            jQuery("#tabelaProdutosSelecionados tbody tr:last").append("<td class='centralizado' >" + data.produto.prdPontos + "</td>");
			            jQuery("#tabelaProdutosSelecionados tbody tr:last").append("<td class='centralizado' > <input type='number' min='1' value='1' class='numero-inteiro quantidade' >  </td>");
			            jQuery("#tabelaProdutosSelecionados tbody tr:last").append("<td class='centralizado removerProduto' > <a title='Remover' > <img src='../css/images/excluir.gif' /> </a>  </td>");
			            
			            calcularTotal();
		           	}
		        }
			});
		}
	}
	
	function substituirCaracteresEspeciais(string){
		
		while (string.indexOf("/") != -1) {
			
	 		string = string.replace("/", "---barra---");
		}
		
		return string;
	}
	
	jQuery(document).ready(function() {
		
		calcularTotal();
		selecionarTipoPedido();
		buscarNomeDistribuidor();
		
		jQuery(document).on('click', '.removerProduto', function(){  
			
			jQuery(this).parent("tr").remove();
			calcularTotal();
		});
		
		jQuery(".removerProduto").click(function(){  
			
			jQuery(this).parent("tr").remove();
			calcularTotal();
		});
		
		jQuery(document).on('change', '.quantidade', function(){  
			
			calcularTotal();
		});
		
		jQuery(document).on('keyup', '.quantidade', function(){  
			
			calcularTotal();
		});
	});
	
</script>
