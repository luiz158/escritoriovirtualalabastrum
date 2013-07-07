<%@ include file="/base.jsp" %> 

<form action="<c:url value="/arvoreRelacionamento/gerarArvoreRelacionamento"/>" method="post" >
	<fieldset>
    	<legend>Árvore de relacionamentos</legend>	

		<div class="control-group warning">
	    	<label class="control-label">Código</label> 
	    	<div class="controls">
	    		<input type="text" class="numero-inteiro" name="codigo" value="${sessaoUsuario.usuario.id_Codigo}" />
			</div>
	    </div>

		<button type="submit" class="btn btn-primary" onclick="this.disabled=true;this.form.submit();" >Gerar árvore de relacionamentos</button>
		<a class="btn btn-danger" href="<c:url value="/home/home"/>" > Cancelar </a>
		
	</fieldset>
</form>

