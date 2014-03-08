<%@ include file="/base.jsp" %> 

<form class="form-horizontal" action="<c:url value="/extratoSimplificado/gerarExtratoSimplificado"/>" id='form-extrato' method="post" >
  <fieldset>
    <legend>Extrato simplificado</legend>
    
    <div class="control-group warning" style="margin-left: -50px;" > 

        <label class="control-label">Ano</label>
        <div class="controls">
          <select name="ano" id='ano' >
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
    
    <div class="control-group warning" style="margin-left: -50px;" > 

        <label class="control-label">Mês</label>
        <div class="controls">
          <select name="mes" id='mes' >
	      	<option value="1" > Janeiro </option>
	      	<option value="2" > Fevereiro </option>
	      	<option value="3" > Março </option>
	      	<option value="4" > Abril </option>
	      	<option value="5" > Maio </option>
	      	<option value="6" > Junho </option>
	      	<option value="7" > Julho </option>
	      	<option value="8" > Agosto </option>
	      	<option value="9" > Setembro </option>
	      	<option value="10" > Outubro </option>
	      	<option value="11" > Novembro </option>
	      	<option value="12" > Dezembro </option>
		  </select>
        </div>
    </div>
    
    <div style="margin-top: 50px;" >
	    <button id='gerar-extrato' type="submit" class="btn btn-primary" onclick="gerarExtrato()" >Gerar extrato</button>
    </div>
       
  </fieldset>
</form>

<script>

	$('#ano').val(new Date().getFullYear());
	$('#mes').val(new Date().getMonth()+1);
	
	function gerarExtrato(){
		
		alert("Atenção. Este relatório realiza uma quantidade muito grande de cálculos e, dependendo do tamanho da sua rede, o resultado pode demorar alguns minutos. Por favor, aguarde.");
		
		$('#gerar-extrato').attr("disabled", "disabled");
		$('#form-extrato').submit();
	}

</script>