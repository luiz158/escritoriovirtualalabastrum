<%@ include file="/base.jsp" %> 

<form class="form-horizontal" action="<c:url value="/malaDireta/gerarMalaDireta"/>" method="post" >
  <fieldset>
    <legend>Mala direta</legend>
    
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
          	<option value="Todas"> Todas </option>
          	<option value="id_Patroc" selected="selected" > Patrocinador </option>
          	<option value="id_Dem"> Demonstrador </option>
          	<option value="id_S"> Sênior </option>
          	<option value="id_GB"> Gerente bronze </option>
          	<option value="id_GP"> Gerente prata </option>
          	<option value="id_GO"> Gerente ouro </option>
          	<option value="id_GE"> Gerente executivo </option>
          	<option value="id_M"> M </option>
          	<option value="id_M1"> M1 </option>
          	<option value="id_M2"> M2 </option>
          	<option value="id_M3"> M3 </option>
          	<option value="id_M4"> M4 </option>
          	<option value="id_M5"> M5 </option>
          	<option value="id_LA"> LA </option>
          	<option value="id_LA1"> LA1 </option>
          	<option value="id_LA2"> LA2 </option>
          	<option value="id_CR"> CR </option>
          	<option value="id_CR1"> CR1 </option>
          	<option value="id_CR2"> CR2 </option>
          	<option value="id_DR"> Diretor regional </option>
          	<option value="id_DD"> Diretor divisional </option>
          	<option value="id_DS"> Diretor superintendente </option>
          	<option value="id_Pres"> Presidente </option>
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