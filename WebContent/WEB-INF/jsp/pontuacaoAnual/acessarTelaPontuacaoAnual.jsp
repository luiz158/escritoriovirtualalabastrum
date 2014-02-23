<%@ include file="/base.jsp" %> 

<form class="form-horizontal" action="<c:url value="/pontuacaoAnual/gerarRelatorioPontuacaoAnual"/>" method="post" >
  <fieldset>
    <legend>Pontuação anual</legend>
    
    <div class="control-group warning" style="margin-left: -50px;" > 
	     <label class="control-label">Código</label>
	     <div class="controls">
	     	<input type="text" class="numero-inteiro" name="codigoUsuario" value="${sessaoUsuario.usuario.id_Codigo}"  >
	     </div>
	</div>
    
    <div class="control-group warning" style="margin-left: -50px;" > 
        <label class="control-label">Posição</label>
        <div class="controls">
          <select name="posicao" >
          	<c:forEach items="${posicoes}" var="posicao" >
	          	<option value="${posicao.key}" > ${posicao.value} </option>
          	</c:forEach>
		  </select>
        </div>
    </div>
    
    <div class="control-group warning" style="margin-left: -50px;" > 
        <label class="control-label">Ano</label>
        <div class="controls">
          <select name="ano" >
	      	<option value="2013" > 2013 </option>
	      	<option value="2014" > 2014 </option>
	      	<option value="2015" > 2015 </option>
	      	<option value="2016" > 2016 </option>
	      	<option value="2017" > 2017 </option>
	      	<option value="2018" > 2018 </option>
	      	<option value="2019" > 2019 </option>
	      	<option value="2020" > 2020 </option>
	      	<option value="2021" > 2021 </option>
	      	<option value="2022" > 2022 </option>
	      	<option value="2023" > 2023 </option>
	      	<option value="2024" > 2024 </option>
	      	<option value="2025" > 2025 </option>
		  </select>
        </div>
    </div>
    
    <div class="control-group warning" style="margin-left: -50px; " > 
	     <label class="control-label">Possui movimentação?</label>
	     <div class="controls">
          <select name="possuiMovimentacao" >
	          <option value="Todos" > Todos </option>
	          <option value="Sim" > Sim </option>
	          <option value="Não" > Não </option>
		  </select>
        </div>
	</div>
    
    <div style="margin-top: 50px;" >
	    <button type="submit" class="btn btn-primary" onclick="this.disabled=true;this.form.submit();" >Gerar relatório</button>
	    <a class="btn btn-danger" href="<c:url value="/home/home"/>" > Cancelar </a>
    </div>
       
  </fieldset>
</form>

<div class="imagensLink" >
	<a href="<c:url value="/atualizacaoDados/acessarTelaAtualizacaoDados"/>"> <img class="imagemLink" src="../css/images/imagem-link-atualize-seus-dados.jpg"  name="" /> </a>
	<a href="<c:url value="/home/emails"/>"> <img class="imagemLink" src="../css/images/imagem-link-emails.jpg"  name="" /> </a>
	<a href="<c:url value="/login/trocarPropriaSenha"/>"> <img class="imagemLink" src="../css/images/imagem-link-troque-sua-senha.jpg"  name="" /> </a>
</div>