<%@ include file="/base.jsp" %> 

<form class="form-horizontal" action="<c:url value="/transferenciaCredito/transferir"/>" method="post" >
  <fieldset>
    <legend>Transferência de crédito</legend>
    
    <p> Crédito atual: <b> R$ ${sessaoUsuario.usuario.cadCredito} </b> </p>  
    
    <br>
    
    <div class="control-group warning" >
	     <label class="control-label">Código do distribuidor que irá receber o crédito:</label>
	     <div class="controls">
	     	<input type="text" class="numero-inteiro" name="codigoQuemVaiReceber" style="margin-top: 10px;"  >
	     </div>
	</div>
	
	<div class="control-group warning" >
	     <label class="control-label">Quantidade de crédito a ser transferida:</label>
	     <div class="controls">
	     	<input type="text" class="numero-decimal" name="quantidadeASerTransferida" style="margin-top: 10px;"  >	
	     </div>
	</div>
            
    <button type="submit" class="btn btn-primary" onclick="this.disabled=true;this.form.submit();" > Transferir </button>
    <a class="btn btn-danger" href="<c:url value="/home/home"/>" > Cancelar </a>
  </fieldset>
</form>

<div class="imagensLink" >
	<a href="<c:url value="/atualizacaoDados/acessarTelaAtualizacaoDados"/>"> <img class="imagemLink" src="../css/images/imagem-link-atualize-seus-dados.jpg"  name="" /> </a>
	<a href="<c:url value="/home/emails"/>"> <img class="imagemLink" src="../css/images/imagem-link-emails.jpg"  name="" /> </a>
	<a href="<c:url value="/login/trocarPropriaSenha"/>"> <img class="imagemLink" src="../css/images/imagem-link-troque-sua-senha.jpg"  name="" /> </a>
</div>