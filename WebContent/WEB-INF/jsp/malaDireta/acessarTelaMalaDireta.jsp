<%@ include file="/base.jsp" %> 

<form class="form-horizontal" action="<c:url value="/malaDireta/gerarMalaDireta"/>" method="post" >
  <fieldset>
    <legend>Mala Direta</legend>
    
    <div class="control-group warning" style="margin-left: -90px;" > 
    
	     <label class="control-label">Código</label>
	     <div class="controls">
	     	<input type="text" class="numero-inteiro" name="codigoUsuario" value="${sessaoUsuario.usuario.id_Codigo}"  >
	     </div>
	</div>
    
    <div class="control-group warning" style="margin-left: -90px; margin-bottom: 50px;" > 

        <label class="control-label">Posição</label>
        <div class="controls">
          <select name="posicao" >
          	<c:forEach items="${posicoes}" var="posicao" >
	          	<option value="${posicao.key}" <c:if test="${posicao.key == 'id_Patroc'}" > selected="selected" </c:if> > ${posicao.value} </option>
          	</c:forEach>
		  </select>
        </div>
    </div>
            
    <button type="submit" class="btn btn-primary" onclick="this.disabled=true;this.form.submit();" >Gerar mala direta</button>
    <a class="btn btn-danger" href="<c:url value="/home/home"/>" > Cancelar </a>
  </fieldset>
</form>

<div class="imagensLink" >
	<a href="<c:url value="/atualizacaoDados/acessarTelaAtualizacaoDados"/>"> <img class="imagemLink" src="../css/images/imagem-link-atualize-seus-dados.jpg"  name="" /> </a>
	<a href="<c:url value="/home/emails"/>"> <img class="imagemLink" src="../css/images/imagem-link-emails.jpg"  name="" /> </a>
	<a href="<c:url value="/login/trocarPropriaSenha"/>"> <img class="imagemLink" src="../css/images/imagem-link-troque-sua-senha.jpg"  name="" /> </a>
</div>