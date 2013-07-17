<%@ include file="/base.jsp" %>
	
<form class="form-horizontal" action="<c:url value="/pedido/etapaConfirmacaoInformacoes"/>" method="post"  >
  <fieldset>
    <legend> Confirme seu e-mail </legend>
    
    <div class="control-group warning">
    	<label class="control-label">E-mail</label>
    	<div class="controls">
    		<input type="text" name="sessaoPedido.email" value="${sessaoPedido.email}" />
		</div>
    </div>

	<a class="btn" href="<c:url value="/pedido/etapaFormasPagamento"/>" > Voltar </a>
	<button id="botaoAvancar" type="submit" class="btn btn-primary"  style="margin-left: 30px"  > Avançar </button>

  </fieldset>
</form>