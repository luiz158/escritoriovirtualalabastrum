<%@ include file="/base.jsp" %> 

<form class="form-horizontal" action="<c:url value="/transferenciaCredito/confirmarTransferencia"/>" method="post" >
  <fieldset>
    <legend>Confirmação de transferência</legend>
    
    <p> Código do distribuidor que irá receber: <b> ${sessaoGeral.valor.get('codigoQuemVaiReceber')} </b> </p>
    <p> Nome do distribuidor que irá receber: <b> ${sessaoGeral.valor.get('nomeQuemVaiReceber')} </b> </p>
    <p> Quantidade a ser transferida: <b> ${sessaoGeral.valor.get('quantidadeASerTransferida')} </b> </p>        
    
    <br>
      
    <button type="submit" class="btn btn-primary" onclick="this.disabled=true;this.form.submit();" > Transferir </button>
    <a class="btn btn-danger" href="<c:url value="/home/home"/>" > Cancelar </a>
  </fieldset>
</form>