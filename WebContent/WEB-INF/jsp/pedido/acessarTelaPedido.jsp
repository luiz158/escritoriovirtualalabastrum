<%@ include file="/base.jsp" %> 
	
<form class="form-horizontal" action="<c:url value="/pedido/metodoaqui"/>" method="post" >
  <fieldset>
    <legend> Realizar pedido </legend>
    
    <div class="control-group warning" > 
        <label class="control-label">Categoria do produto</label>
        <div class="controls">
          <select name="posicao" onselect="listarProdutos()" >
          	<c:forEach items="${categorias}" var="categoria" >
	          	<option value="${categoria.id}" > ${categoria.catNome} </option>
          	</c:forEach>
		  </select>
        </div>
    </div>
    
    <div class="control-group warning" style="display: none" > 
        <label class="control-label">Produto</label>
        <div class="controls">
          <select name="posicao" onselect="listarProdutos()" >
          	<c:forEach items="${categorias}" var="categoria" >
	          	<option value="${categoria.id}" > ${categoria.catNome} </option>
          	</c:forEach>
		  </select>
        </div>
    </div>
    
    <br> <br> 
            
    <button type="submit" class="btn btn-primary" onclick="this.disabled=true;this.form.submit();" style="display: none" > AFDAODIFHOSFDSIOFODASFHOSADF </button>
  </fieldset>
</form>

<script>
	
	function listarProdutos(){
		
		
	}
	
</script>
