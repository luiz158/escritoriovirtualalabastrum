<%@ include file="/base.jsp" %> 
	
<form class="form-horizontal" action="<c:url value="/pedido/metodoaqui"/>" method="post" >
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
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${malaDireta}" var="item">
					<tr>
		                      <td class="centralizado" > 
		                      	${item.usuario.id_Codigo}
		                      </td>
		                      <td class="centralizado" > ${item.usuario.posAtual} </td>
		                      <td> ${item.usuario.vNome} </td>
		                      <td> ${item.usuario.tel} </td>
		                      <td> ${item.usuario.cadCelular} </td>
		                      <td> ${item.usuario.eMail} </td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
    </div>
    
    <button class="btn btn-primary" onclick="calcularTotal()" id="calcularTotal" style="display: none" > Calcular total </button>
  </fieldset>
</form>

<script>
	
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
		        	
		           	jQuery("#calcularTotal").show();
		           	jQuery("#produtosSelecionados").show();

		            jQuery("#tabelaProdutosSelecionados tbody").append("<tr>");
		            jQuery("#tabelaProdutosSelecionados tbody tr:last").append("<td>" + data.produto.id_Produtos + "</td>");
		        }
			});
		}
	}
	
</script>
