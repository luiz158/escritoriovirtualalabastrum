<%@ include file="/base.jsp" %> 

<form class="form-horizontal trocarSenha"  action="<c:url value="/login/salvarTrocarPropriaSenha"/>" method="post">
  <fieldset>
    <p class="tituloTrocarSenha" > TROQUE SUA SENHA </p>
    
    <div class="control-group warning">
    	<label class="control-label labelTrocarSenha" style="margin-top: 5px;" >Senha antiga</label>
    	<div class="controls">
    		<input type="password" class="senhaAntiga input-xlarge" name="senhaAntiga"  >
		</div>
    </div>
    
    <div class="control-group warning" style="margin-bottom: 40px;">
    	<label class="control-label labelTrocarSenha " style="margin-top: 15px;" >Nova senha</label>
    	<div class="controls">
    		<input type="password" class="novaSenha input-xlarge" name="senhaNova"  >
		</div>
    </div>
            
    <button type="submit" class="btn btn-primary" style="margin-left: 15px;" onclick="this.disabled=true;this.form.submit();" >Salvar</button>
    <a class="btn btn-danger" href="<c:url value="/home/home"/>" > Cancelar </a>
  </fieldset>
</form>

<div class="imagensLink" >
	<a href="<c:url value="/atualizacaoDados/acessarTelaAtualizacaoDados"/>"> <img class="imagemLink" src="../css/images/imagem-link-atualize-seus-dados.jpg"  name="" /> </a>
	<a href="<c:url value="/home/emails"/>"> <img class="imagemLink" src="../css/images/imagem-link-emails.jpg"  name="" /> </a>
	<a href="<c:url value="/malaDireta/acessarTelaMalaDireta"/>"> <img class="imagemLink" src="../css/images/imagem-link-mala-direta.jpg"  name="" /> </a>
</div>
