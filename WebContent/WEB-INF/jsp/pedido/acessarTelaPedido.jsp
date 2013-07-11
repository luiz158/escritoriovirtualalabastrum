<%@ include file="/base.jsp" %> 

<style>

	.quantidade{
		width: 50px;
	}

</style>
	
<form class="form-horizontal" action="<c:url value="/pedido/avancarEtapaFormaPagamento"/>" method="post" >
  <fieldset>
    <legend> Realizar pedido </legend>
    
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
    
    <div id="produtosSelecionados" style="display: none" >
    
	    <h6> Produtos selecionados  </h6>
	    
	    <table class="table" id="tabelaProdutosSelecionados" >
			<thead>
		    	<tr>
		            <th> Código </th>
		            <th> Produto </th>
		            <th> Preço unitário </th>
		            <th> Pontuação </th>
		            <th> Quantidade </th>
		            <th>  </th>
				</tr>
			</thead>
			<tbody>
			</tbody>
		</table>
		
		<h3> Total: R$<b id="total" >  </b> </h3>
		
		<button type="submit" class="btn btn-primary" onclick="this.disabled=true;this.form.submit();" >Avançar</button>
		
    </div>

  </fieldset>
</form>

<script>
	
	function calcularTotal(){
		
		var total = 0;
		
		jQuery("#tabelaProdutosSelecionados tbody tr").each(function(){
			
			total += parseFloat(jQuery(this).find(".precoUnitario").text()) * parseFloat(jQuery(this).find(".quantidade").val());
		});		
		
		jQuery("#total").text(total.toFixed(2));
	}
	
	function listarProdutos(){
		
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
		           	
		           	if(jQuery("#tabelaProdutosSelecionados #" + data.produto.id_Produtos).length == 0){
		           		
			            jQuery("#tabelaProdutosSelecionados tbody").append("<tr id='" + data.produto.id_Produtos + "' >");
			            jQuery("#tabelaProdutosSelecionados tbody tr:last").append("<td class='centralizado' >" + data.produto.id_Produtos + "</td>");
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
	
	jQuery(document).ready(function() {
		
		jQuery(document).on('click', '.removerProduto', function(){  
			
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
