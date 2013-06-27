<%@ include file="/base.jsp" %> 

<form class="form-horizontal" action="<c:url value="/importacaoArquivo/importarArquivo"/>" method="post" enctype="multipart/form-data" >
  <fieldset>
    <legend>Atualizar o sistema</legend>
    
    <div class="control-group warning">
    	<label class="control-label">Arquivo</label>
    	<div class="controls">
    		<input type="file" name="arquivo"/>
		</div>
    </div>
            
    <button type="submit" class="btn btn-primary" onclick="this.disabled=true;this.form.submit();" >Importar</button>
    <a class="btn btn-danger" href="<c:url value="/home/home"/>" > Cancelar </a>
  </fieldset>
</form>