<%@ include file="/base.jsp" %> 

<form class="form-horizontal" action="<c:url value="/listaIngresso/gerarListaIngresso"/>" method="post" >
  <fieldset>
    <legend>Lista de ingresso</legend>
    
    <div class="control-group warning" style="margin-left: -50px;" > 
    
	     <label class="control-label">Data inicial</label>
	     <div class="controls">
	     	<input type="text" class="data" name="dataInicial"  >
	     </div>
	</div>
	
	<div class="control-group warning" style="margin-left: -50px; " > 
    
	     <label class="control-label">Data final</label>
	     <div class="controls">
	     	<input type="text" class="data" name="dataFinal"  >
	     </div>
	</div>

    <div style="margin-top: 50px;" >
	    <button type="submit" class="btn btn-primary" onclick="this.disabled=true;this.form.submit();" >Listar</button>
	    <a class="btn btn-danger" href="<c:url value="/home/home"/>" > Cancelar </a>
    </div>
       
  </fieldset>
</form>

<div class="imagensLink" >
	<a href="<c:url value="/atualizacaoDados/acessarTelaAtualizacaoDados"/>"> <img class="imagemLink" src="../css/images/imagem-link-atualize-seus-dados.jpg"  name="" /> </a>
	<a href="<c:url value="/home/emails"/>"> <img class="imagemLink" src="../css/images/imagem-link-emails.jpg"  name="" /> </a>
	<a href="<c:url value="/login/trocarPropriaSenha"/>"> <img class="imagemLink" src="../css/images/imagem-link-troque-sua-senha.jpg"  name="" /> </a>
</div>