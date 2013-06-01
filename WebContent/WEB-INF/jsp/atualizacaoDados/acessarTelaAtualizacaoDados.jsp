<%@ include file="/base.jsp" %> 

<style>

	.divAtualizacaoDados{
		width: 700px;
		border: 1px solid rgb(152, 160, 233);
		padding-top: 10px;
		border-radius: 6px;
	}
	
	.divInformacoesUsuarios{
		padding-left: 20px;
		padding-right: 20px;
	}
	
	.labelEsquerda{
		display: inline;
		font-weight: bold;
	}
	.spanDireita{ 
		float: none;
		margin-left: 10px;
	}
	
	hr{
		margin: 10px 0;
		margin-bottom: 20px;
		border: 0;
		border-top: 1px solid #ccc;
		border-bottom: 1px solid #ffffff;
	}
	
	.atualizacaoDadosCadastrais{
		margin-top: 30px;
		height: 27px;
		padding-top: 8px;
		padding-left: 20px;
		margin-bottom: 1px;
	}
	
	.corForte{
		background-color: rgb(165, 165, 165);
	}
	
	.corMedia{
		background-color: rgb(210, 210, 210);
	}
	
	.corClara{
		background-color: rgb(233, 233, 233);
	}
	
	.labelFormulario{
		display: inline-block;
		margin-left: 20px;
	}
	
	.divFormulario{
		padding: 10px;
		padding-bottom: 5px;
		margin-bottom: 1px;
	}

</style>

<div class="divAtualizacaoDados" >

	<div class="divInformacoesUsuarios" >
		<h4> DADOS CADASTRAIS </h4>
		<hr>
		<label class="labelEsquerda" > Código de identificação: </label>  <span class="spanDireita"> ${sessaoUsuario.usuario.id_Codigo} </span>  <br><br>
		<label class="labelEsquerda" > Nome completo: </label>  <span class="spanDireita"> ${sessaoUsuario.usuario.vNome} </span>  <br><br>
		<label class="labelEsquerda" > CPF: </label>  <span class="spanDireita"> ${sessaoUsuario.usuario.CPF} </span>  <br>
	</div>
	
	<h5 class="corForte atualizacaoDadosCadastrais" > ATUALIZAÇÃO DE DADOS CADASTRAIS </h5>
	
	<form class='requerido' action="<c:url value="/atualizacaoDados/salvarAtualizacaoDados"/>" method="post" >
	
	<input type="text" style="width: 90px;" class="required" name="sessaoAtualizacaoDados.CEP" value="${sessaoAtualizacaoDados.CEP}" >	
	
	
		<div class="corClara divFormulario" >
			<label class="labelFormulario" >Data nasc. </label> 
			<input type="text" style="width: 90px;" class="data required" name="sessaoAtualizacaoDados.dataNascimento" value="${sessaoAtualizacaoDados.dataNascimento}" >
			<label class="labelFormulario" >Sexo </label> 
			<select name="sessaoAtualizacaoDados.sexo" style="width: 120px;" >
				<option value="Masculino" > Masculino </option>
				<option value="Feminino" > Feminino </option>
			</select>
			<label class="labelFormulario" >Estado civil </label>
			<select name="sessaoAtualizacaoDados.estadoCivil" style="width: 130px;" >
				<option value="Solteiro(a)" > Solteiro(a) </option>
				<option value="Casado(a)" > Casado(a) </option>
				<option value="Divorciado(a)" > Divorciado(a) </option>
				<option value="Viúvo(a)" > Viúvo(a) </option>
			</select> 		
		</div>
		
		<div class="corMedia divFormulario" >
			<label class="labelFormulario" >CEP </label> 
			<input type="text" style="width: 90px;" class="required" name="sessaoAtualizacaoDados.CEP" value="${sessaoAtualizacaoDados.CEP}" >	
		</div>
		
		<div style="margin-top: 20px; margin-left: 20px" >
			<button type="submit" class="btn btn-primary" onclick="this.disabled=true;this.form.submit();" >Enviar</button>
	   		<a class="btn btn-danger" href="<c:url value="/home/home"/>" > Cancelar </a>
		</div>
	
	</form>

</div>

<script>

jQuery(".data").datepicker({

	dateFormat: 'dd/mm/yy',
	dayNames: [
	'Domingo','Segunda','Terça','Quarta','Quinta','Sexta','Sábado','Domingo'
	],
	dayNamesMin: [
	'D','S','T','Q','Q','S','S','D'
	],
	dayNamesShort: [
	'Dom','Seg','Ter','Qua','Qui','Sex','Sáb','Dom'
	],
	monthNames: [
	'Janeiro','Fevereiro','Marco','Abril','Maio','Junho','Julho','Agosto','Setembro',
	'Outubro','Novembro','Dezembro'
	],
	monthNamesShort: [
	'Jan','Fev','Mar','Abr','Mai','Jun','Jul','Ago','Set',
	'Out','Nov','Dez'
	],
	yearRange: "-70: -10",
	nextText: 'Próximo',
	prevText: 'Anterior',
	changeMonth: true,
	changeYear: true

});

</script>
