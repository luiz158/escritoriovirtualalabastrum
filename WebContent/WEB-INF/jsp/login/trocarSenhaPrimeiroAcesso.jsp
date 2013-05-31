<%@ include file="/base.jsp" %> 

<form class="form-horizontal" action="<c:url value="/login/salvarTrocarSenhaPrimeiroAcesso"/>" method="post">
  <fieldset>
    <legend>Troca de senha</legend>
    
    <div class="control-group warning">
    	<label class="control-label">Nova senha</label>
    	<div class="controls">
    		<input type="password" class="input-xlarge required" name="senhaNova"  >
		</div>
    </div>
    
    <div class="control-group warning">
    	<label class="control-label">Confirmação de nova senha</label>
    	<div class="controls">
    		<input type="password" class="input-xlarge required" name="confirmacaoSenhaNova"  >
		</div>
    </div>
    
    <div class="control-group warning">
    	<label class="control-label">CPF</label>
    	<div class="controls">
    		<input type="text" class="input-xlarge required numero-inteiro" name="cpf"  >
		</div>
    </div>
    
    <div class="control-group warning">
    	<label class="control-label">E-mail</label>
    	<div class="controls">
    		<input type="email" class="input-xlarge required" name="email"  >
		</div>
    </div>
            
    <button type="submit" class="btn btn-primary">Salvar</button>
    <a class="btn btn-danger" href="<c:url value="/login/telaLogin"/>" > Cancelar </a>
  </fieldset>
</form>