<%@ include file="/base.jsp" %> 

<form class="form-horizontal" action="<c:url value="/aniversariantes/gerarAniversariantes"/>" method="post" >
  <fieldset>
    <legend>Aniversariantes</legend>
    
    <div class="control-group warning" style="margin-left: -90px; margin-bottom: 50px;" > 

        <label class="control-label">Mês</label>
        <div class="controls">
          <select name="mes" >
	      	<option value="01" > Janeiro </option>
	      	<option value="02" > Fevereiro </option>
	      	<option value="03" > Março </option>
	      	<option value="04" > Abril </option>
	      	<option value="05" > Maio </option>
	      	<option value="06" > Junho </option>
	      	<option value="07" > Julho </option>
	      	<option value="08" > Agosto </option>
	      	<option value="09" > Setembro </option>
	      	<option value="10" > Outubro </option>
	      	<option value="11" > Novembro </option>
	      	<option value="12" > Dezembro </option>
		  </select>
        </div>
    </div>
            
    <button type="submit" class="btn btn-primary" onclick="this.disabled=true;this.form.submit();" >Consultar</button>
    <a class="btn btn-danger" href="<c:url value="/home/home"/>" > Cancelar </a>
  </fieldset>
</form>