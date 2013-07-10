<%@ include file="/base.jsp" %> 
	
<form class="form-horizontal" action="<c:url value="/pedido/metodoaqui"/>" method="post" >
  <fieldset>
    <legend> Realizar pedido </legend>
    
    <div class="control-group warning" > 

        <label class="control-label">Categoria do produto</label>
        <div class="controls">
          <select name="posicao" >
          	<c:forEach items="${categorias}" var="categoria" >
	          	<option value="${categoria.id}" > ${categoria.catNome} </option>
          	</c:forEach>
		  </select>
        </div>
    </div>
            
    <button type="submit" class="btn btn-primary" onclick="this.disabled=true;this.form.submit();" > AFDAODIFHOSFDSIOFODASFHOSADF </button>
  </fieldset>
</form>