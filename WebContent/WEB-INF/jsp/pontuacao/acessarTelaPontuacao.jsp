<%@ include file="/base.jsp" %> 

<form class="form-horizontal" action="<c:url value="/pontuacao/gerarRelatorioPontuacao"/>" method="post" >
  <fieldset>
    <legend>Pontua��o</legend>
    
    <div class="control-group warning" style="margin-left: -90px;" > 
    
	     <label class="control-label">C�digo</label>
	     <div class="controls">
	     	<input type="text" class="numero-inteiro" name="codigoUsuario" value="${sessaoUsuario.usuario.id_Codigo}"  >
	     </div>
	</div>
    
    <div class="control-group warning" style="margin-left: -90px;" > 

        <label class="control-label">Posi��o</label>
        <div class="controls">
          <select name="posicao" >
          	<c:forEach items="${posicoes}" var="posicao" >
	          	<option value="${posicao.key}" <c:if test="${posicao.key == 'id_Patroc'}" > selected="selected" </c:if> > ${posicao.value} </option>
          	</c:forEach>
		  </select>
        </div>
    </div>
    
    <div class="control-group warning" style="margin-left: -90px;" > 
    
	     <label class="control-label">Data inicial</label>
	     <div class="controls">
	     	<input type="text" class="data" name="dataInicial" value="<fmt:formatDate value="${primeiroDiaMes.time}" type="DATE" />"  >
	     </div>
	</div>
	
	<div class="control-group warning" style="margin-left: -90px; margin-bottom: 50px; " > 
    
	     <label class="control-label">Data final</label>
	     <div class="controls">
	     	<input type="text" class="data" name="dataFinal" value="<fmt:formatDate value="${dataAtual.time}" type="DATE" />"  >
	     </div>
	</div>
            
    <button type="submit" class="btn btn-primary" onclick="this.disabled=true;this.form.submit();" >Gerar relat�rio</button>
    <a class="btn btn-danger" href="<c:url value="/home/home"/>" > Cancelar </a>
  </fieldset>
</form>

<div class="imagensLink" >
	<a href="<c:url value="/atualizacaoDados/acessarTelaAtualizacaoDados"/>"> <img class="imagemLink" src="../css/images/imagem-link-atualize-seus-dados.jpg"  name="" /> </a>
	<a href="<c:url value="/home/emails"/>"> <img class="imagemLink" src="../css/images/imagem-link-emails.jpg"  name="" /> </a>
	<a href="<c:url value="/login/trocarPropriaSenha"/>"> <img class="imagemLink" src="../css/images/imagem-link-troque-sua-senha.jpg"  name="" /> </a>
</div>