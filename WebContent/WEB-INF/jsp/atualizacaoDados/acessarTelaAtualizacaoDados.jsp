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
	
		<div class="corClara divFormulario" >
			<label class="labelFormulario" >Data nasc. </label> 
			<input type="text" style="width: 90px;" class="data" name="sessaoAtualizacaoDados.dataNascimento" value="${sessaoAtualizacaoDados.dataNascimento}" >
			<label class="labelFormulario" >Sexo </label> 
			<select name="sessaoAtualizacaoDados.sexo" style="width: 130px;" >
				<option value="Masculino" > Masculino </option>
				<option value="Feminino" > Feminino </option>
			</select>
			<label class="labelFormulario" >Estado civil </label>
			<select name="sessaoAtualizacaoDados.estadoCivil" style="width: 170px;" >
				<option value="Solteiro(a)" > Solteiro(a) </option>
				<option value="Casado(a)" > Casado(a) </option>
				<option value="Divorciado(a)" > Divorciado(a) </option>
				<option value="Viúvo(a)" > Viúvo(a) </option>
			</select> 		
		</div>
		
		<div class="corMedia divFormulario" >
			<label class="labelFormulario" >CEP </label> 
			<input type="text" style="width: 90px;" name="sessaoAtualizacaoDados.CEP" value="${sessaoAtualizacaoDados.CEP}" >	
			<a style="margin-left: 30px;" href="http://www.buscacep.correios.com.br" target="_blank" > Pesquisar CEP </a>			
		</div>
		
		<div class="corClara divFormulario" >
			<label class="labelFormulario" >End. Resid. </label> 
			<input type="text" style="width: 430px;" name="sessaoAtualizacaoDados.endereco" value="${sessaoAtualizacaoDados.endereco}" >
			<label class="labelFormulario" >N.º </label> 
			<input type="text" style="width: 50px;" name="sessaoAtualizacaoDados.numeroEndereco" value="${sessaoAtualizacaoDados.numeroEndereco}" >
		</div>
		
		<div class="corMedia divFormulario" >
			<label class="labelFormulario" >Complemento </label> 
			<input type="text" style="width: 220px;" name="sessaoAtualizacaoDados.complementoEndereco" value="${sessaoAtualizacaoDados.complementoEndereco}" >
			<label class="labelFormulario" >Bairro </label> 
			<input type="text" style="width: 225px;" name="sessaoAtualizacaoDados.bairro" value="${sessaoAtualizacaoDados.bairro}" >
		</div>
		
		<div class="corClara divFormulario" >
			<label class="labelFormulario" >Cidade </label> 
			<input type="text" style="width: 340px;" name="sessaoAtualizacaoDados.cidade" value="${sessaoAtualizacaoDados.cidade}" >
			<label class="labelFormulario" >Estado </label> 
			<select name="sessaoAtualizacaoDados.estado" style="width: 155px;" >
				<option value="AC">Acre</option>
				<option value="AL">Alagoas</option>
				<option value="AP">Amapá</option>
				<option value="AM">Amazonas</option>
				<option value="BA">Bahia</option>
				<option value="CE">Ceará</option>
				<option value="DF">Distrito Federal</option>
				<option value="ES">Espirito Santo</option>
				<option value="GO">Goiás</option>
				<option value="MA">Maranhão</option>
				<option value="MT">Mato Grosso</option>
				<option value="MS">Mato Grosso do Sul</option>
				<option value="MG">Minas Gerais</option>
				<option value="PA">Pará</option>
				<option value="PB">Paraiba</option>
				<option value="PR">Paraná</option>
				<option value="PE">Pernambuco</option>
				<option value="PI">Piauí</option>
				<option value="RJ" selected="selected" >Rio de Janeiro</option>
				<option value="RN">Rio Grande do Norte</option>
				<option value="RS">Rio Grande do Sul</option>
				<option value="RO">Rondônia</option>
				<option value="RR">Roraima</option>
				<option value="SC">Santa Catarina</option>
				<option value="SP">São Paulo</option>
				<option value="SE">Sergipe</option>
				<option value="TO">Tocantis</option>
			</select> 	
		</div>
		
		<div class="corMedia divFormulario" >
			<label class="labelFormulario" >Tel. resid. </label> 
			<input type="text" style="width: 20px; margin-left: 2px;" name="sessaoAtualizacaoDados.telefoneResidencialDDD" value="${sessaoAtualizacaoDados.telefoneResidencialDDD}" maxlength="2" >
			<input type="text" style="width: 150px;" name="sessaoAtualizacaoDados.telefoneResidencial" value="${sessaoAtualizacaoDados.telefoneResidencial}" maxlength="9" >
			
			<label class="labelFormulario" style="margin-left: 85px;" >Tel. Com. </label> 
			<input type="text" style="width: 20px; margin-left: 4px;" name="sessaoAtualizacaoDados.telefoneComercialDDD" value="${sessaoAtualizacaoDados.telefoneComercialDDD}" maxlength="2" >
			<input type="text" style="width: 150px;" name="sessaoAtualizacaoDados.telefoneComercial" value="${sessaoAtualizacaoDados.telefoneComercial}" maxlength="9" >
			
			<br>
			
			<label class="labelFormulario" >Tel. Cel. 1 </label> 
			<input type="text" style="width: 20px;" name="sessaoAtualizacaoDados.telefoneCelular1DDD" value="${sessaoAtualizacaoDados.telefoneCelular1DDD}" maxlength="2" >
			<input type="text" style="width: 150px;" name="sessaoAtualizacaoDados.telefoneCelular1" value="${sessaoAtualizacaoDados.telefoneCelular1}" maxlength="9" >
			
			<label class="labelFormulario" style="margin-left: 85px;" >Tel. Cel. 2 </label> 
			<input type="text" style="width: 20px;" name="sessaoAtualizacaoDados.telefoneCelular2DDD" value="${sessaoAtualizacaoDados.telefoneCelular2DDD}" maxlength="2" >
			<input type="text" style="width: 150px;" name="sessaoAtualizacaoDados.telefoneCelular2" value="${sessaoAtualizacaoDados.telefoneCelular2}" maxlength="9" >
			
		</div>
		
		<div class="corClara divFormulario" >
			<label class="labelFormulario" >Email </label> 
			<input type="text" style="width: 580px; " name="sessaoAtualizacaoDados.email" value="${sessaoAtualizacaoDados.email}">
		</div>
		
		<div class="divFormulario" id="entregaMercadoria" >
			<label class="labelFormulario" > <b> Endereço de ENTREGA da MERCADORIA </b> - Clique aqui caso seja o endereço residencial </label> 
		</div>
		
		<div class="corMedia divFormulario" >
			<label class="labelFormulario" >CEP </label> 
			<input type="text" style="width: 90px;" name="sessaoAtualizacaoDados.CEPEntregaMercadoria" value="${sessaoAtualizacaoDados.CEPEntregaMercadoria}" >	
		</div>
		
		<div class="corClara divFormulario" >
			<label class="labelFormulario" >End. Resid. </label> 
			<input type="text" style="width: 430px;" name="sessaoAtualizacaoDados.enderecoEntregaMercadoria" value="${sessaoAtualizacaoDados.enderecoEntregaMercadoria}" >
			<label class="labelFormulario" >N.º </label> 
			<input type="text" style="width: 50px;" name="sessaoAtualizacaoDados.numeroEnderecoEntregaMercadoria" value="${sessaoAtualizacaoDados.numeroEnderecoEntregaMercadoria}" >
		</div>
		
		<div class="corMedia divFormulario" >
			<label class="labelFormulario" >Complemento </label> 
			<input type="text" style="width: 220px;" name="sessaoAtualizacaoDados.complementoEnderecoEntregaMercadoria" value="${sessaoAtualizacaoDados.complementoEnderecoEntregaMercadoria}" >
			<label class="labelFormulario" >Bairro </label> 
			<input type="text" style="width: 225px;" name="sessaoAtualizacaoDados.bairroEntregaMercadoria" value="${sessaoAtualizacaoDados.bairroEntregaMercadoria}" >
		</div>
		
		<div class="corClara divFormulario" >
			<label class="labelFormulario" >Cidade </label> 
			<input type="text" style="width: 340px;" name="sessaoAtualizacaoDados.cidadeEntregaMercadoria" value="${sessaoAtualizacaoDados.cidadeEntregaMercadoria}" >
			<label class="labelFormulario" >Estado </label> 
			<select name="sessaoAtualizacaoDados.estadoEntregaMercadoria" style="width: 155px;" >
				<option value="AC">Acre</option>
				<option value="AL">Alagoas</option>
				<option value="AP">Amapá</option>
				<option value="AM">Amazonas</option>
				<option value="BA">Bahia</option>
				<option value="CE">Ceará</option>
				<option value="DF">Distrito Federal</option>
				<option value="ES">Espirito Santo</option>
				<option value="GO">Goiás</option>
				<option value="MA">Maranhão</option>
				<option value="MT">Mato Grosso</option>
				<option value="MS">Mato Grosso do Sul</option>
				<option value="MG">Minas Gerais</option>
				<option value="PA">Pará</option>
				<option value="PB">Paraiba</option>
				<option value="PR">Paraná</option>
				<option value="PE">Pernambuco</option>
				<option value="PI">Piauí</option>
				<option value="RJ" selected="selected" >Rio de Janeiro</option>
				<option value="RN">Rio Grande do Norte</option>
				<option value="RS">Rio Grande do Sul</option>
				<option value="RO">Rondônia</option>
				<option value="RR">Roraima</option>
				<option value="SC">Santa Catarina</option>
				<option value="SP">São Paulo</option>
				<option value="SE">Sergipe</option>
				<option value="TO">Tocantis</option>
			</select> 	
		</div>
		
		<div class="divFormulario" >
			<label class="labelFormulario" > <b> Dados do 2º titular </b></label> 
		</div>
		
		<div class="corMedia divFormulario" >
			<label class="labelFormulario" >Nome </label> 
			<input type="text" style="width: 575px;;"  name="sessaoAtualizacaoDados.nomeSegundoTitular" value="${sessaoAtualizacaoDados.nomeSegundoTitular}" >
		</div>
		
		<div class="corClara divFormulario" >
			<label class="labelFormulario" >Data nasc. </label> 
			<input type="text" style="width: 90px;" class="data" name="sessaoAtualizacaoDados.dataNascimentoSegundoTitular" value="${sessaoAtualizacaoDados.dataNascimentoSegundoTitular}" >
			<label class="labelFormulario" >Sexo </label> 
			<select name="sessaoAtualizacaoDados.sexoSegundoTitular" style="width: 130px;" >
				<option value="Masculino" > Masculino </option>
				<option value="Feminino" > Feminino </option>
			</select>
			<label class="labelFormulario" >Estado civil </label>
			<select name="sessaoAtualizacaoDados.estadoCivilSegundoTitular" style="width: 170px;" >
				<option value="Solteiro(a)" > Solteiro(a) </option>
				<option value="Casado(a)" > Casado(a) </option>
				<option value="Divorciado(a)" > Divorciado(a) </option>
				<option value="Viúvo(a)" > Viúvo(a) </option>
			</select> 		
		</div>
		
		<div class="corMedia divFormulario" >
			<label class="labelFormulario" > CPF </label> 
			<input type="text" style="width: 140px;"  name="sessaoAtualizacaoDados.CPFSegundoTitular" value="${sessaoAtualizacaoDados.CPFSegundoTitular}" >
			<label class="labelFormulario" > RG </label> 
			<input type="text" style="width: 140px;"  name="sessaoAtualizacaoDados.RGSegundoTitular" value="${sessaoAtualizacaoDados.RGSegundoTitular}" >
			<label class="labelFormulario" > Emissor </label> 
			<input type="text" style="width: 150px;"  name="sessaoAtualizacaoDados.emissorSegundoTitular" value="${sessaoAtualizacaoDados.emissorSegundoTitular}" >
		</div>
		
		<div class="corClara divFormulario" >
			<label class="labelFormulario" >Email </label> 
			<input type="text" style="width: 580px; " name="sessaoAtualizacaoDados.emailSegundoTitular" value="${sessaoAtualizacaoDados.emailSegundoTitular}">
		</div>
		
		<div class="divFormulario" >
			<label class="labelFormulario" > <b> Dados bancários para pagamento de bonificação </b></label> 
		</div>
		
		<div class="corMedia divFormulario" >
			<label class="labelFormulario" >Titular da conta </label> 
			<input type="text" style="width: 515px; " name="sessaoAtualizacaoDados.titularConta" value="${sessaoAtualizacaoDados.titularConta}">
		</div>
		
		<div class="corClara divFormulario" >
			<label class="labelFormulario" >Banco </label> 
			<input type="text" style="width: 200px; " name="sessaoAtualizacaoDados.banco" value="${sessaoAtualizacaoDados.banco}">
			<label class="labelFormulario" >Tipo de conta </label> 
			<select name="sessaoAtualizacaoDados.tipoConta" style="width: 200px;" >
				<option value="Conta corrente">Conta corrente</option>
				<option value="Conta poupança">Conta poupança</option>
				<option value="Conta salário">Conta salário</option>
			</select> 
		</div>
		
		<div class="corMedia divFormulario" >
			<label class="labelFormulario" >Nº Agência </label> 
			<input type="text" style="width: 170px;;" name="sessaoAtualizacaoDados.numeroAgencia" value="${sessaoAtualizacaoDados.numeroAgencia}">
			<label class="labelFormulario" >Nº da Conta </label> 
			<input type="text" style="width: 200px;" name="sessaoAtualizacaoDados.numeroConta" value="${sessaoAtualizacaoDados.numeroConta}">
		</div>

		
		<div style="margin-top: 20px; margin-left: 20px" >
			<button type="submit" class="btn btn-primary" onclick="this.disabled=true;this.form.submit();" >Enviar</button>
	   		<a class="btn btn-danger" href="<c:url value="/home/home"/>" > Cancelar </a>
		</div>

	</form>

</div>

<div class="imagensLink" >
	<a href="<c:url value="/malaDireta/acessarTelaMalaDireta"/>"> <img class="imagemLink" src="../css/images/imagem-link-mala-direta.jpg"  name="" /> </a>
	<a href="<c:url value="/home/emails"/>"> <img class="imagemLink" src="../css/images/imagem-link-emails.jpg"  name="" /> </a>
	<a href="<c:url value="/login/trocarPropriaSenha"/>"> <img class="imagemLink" src="../css/images/imagem-link-troque-sua-senha.jpg"  name="" /> </a>
</div>

<script>

jQuery("#entregaMercadoria").click(function(){
	
	jQuery('input[name="sessaoAtualizacaoDados.CEPEntregaMercadoria"]').val(jQuery('input[name="sessaoAtualizacaoDados.CEP"]').val());
	jQuery('input[name="sessaoAtualizacaoDados.enderecoEntregaMercadoria"]').val(jQuery('input[name="sessaoAtualizacaoDados.endereco"]').val());
	jQuery('input[name="sessaoAtualizacaoDados.numeroEnderecoEntregaMercadoria"]').val(jQuery('input[name="sessaoAtualizacaoDados.numeroEndereco"]').val());
	jQuery('input[name="sessaoAtualizacaoDados.complementoEnderecoEntregaMercadoria"]').val(jQuery('input[name="sessaoAtualizacaoDados.complementoEndereco"]').val());
	jQuery('input[name="sessaoAtualizacaoDados.bairroEntregaMercadoria"]').val(jQuery('input[name="sessaoAtualizacaoDados.bairro"]').val());
	jQuery('input[name="sessaoAtualizacaoDados.cidadeEntregaMercadoria"]').val(jQuery('input[name="sessaoAtualizacaoDados.cidade"]').val());
	jQuery('input[name="sessaoAtualizacaoDados.estadoEntregaMercadoria"]').val(jQuery('input[name="sessaoAtualizacaoDados.estado"]').val());			
});

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
