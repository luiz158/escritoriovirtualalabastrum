<%@ include file="/base.jsp" %> 

<style>

	.divAtualizacaoDados{
		width: 710px;
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
		<label class="labelEsquerda" > C�digo de identifica��o: </label>  <span class="spanDireita"> ${sessaoUsuario.usuario.codigoFormatado} </span>  <br><br>
		<label class="labelEsquerda" > Nome completo: </label>  <span class="spanDireita"> ${sessaoUsuario.usuario.vNome} </span>  <br><br>
		<label class="labelEsquerda" > CPF: </label>  <span class="spanDireita"> ${sessaoUsuario.usuario.CPF} </span>  <br>
	</div>
	
	<h5 class="corForte atualizacaoDadosCadastrais" > ATUALIZA��O DE DADOS CADASTRAIS </h5>
	
	<form class='requerido' action="<c:url value="/atualizacaoDados/salvarAtualizacaoDados"/>" method="post" >	
	
		<div class="corMedia divFormulario" >
			<label class="labelFormulario" >Nome: </label> 
			<input type="text" style="width: 585px;" name="sessaoAtualizacaoDados.vnome" value="${sessaoAtualizacaoDados.vNome}" >
		</div>
	
		<div class="corClara divFormulario" >
			<label class="labelFormulario" >Data nasc.: </label> 
			<input type="text" style="width: 90px;" class="data" name="sessaoAtualizacaoDados.dt_Nasc" value="${sessaoAtualizacaoDados.dt_Nasc}" >
			<label class="labelFormulario" >Sexo: </label> 
			<select name="sessaoAtualizacaoDados.cadSexo" style="width: 130px;" >
				<option value="Masculino" <c:if test="${sessaoAtualizacaoDados.cadSexo == 'Masc'}"> selected="selected" </c:if>  > Masc </option>
				<option value="Feminino" <c:if test="${sessaoAtualizacaoDados.cadSexo == 'Fem'}"> selected="selected" </c:if> > Fem </option>
			</select>
			<label class="labelFormulario" >Estado civil: </label>
			<select name="sessaoAtualizacaoDados.cadEstCivil" style="width: 170px;" >
				<option value="Solteiro(a)" <c:if test="${sessaoAtualizacaoDados.cadEstCivil == 'Solteiro(a)'}"> selected="selected" </c:if>  > Solteiro(a) </option>
				<option value="Casado(a)" <c:if test="${sessaoAtualizacaoDados.cadEstCivil == 'Casado(a)'}"> selected="selected" </c:if> > Casado(a) </option>
				<option value="Divorciado(a)" <c:if test="${sessaoAtualizacaoDados.cadEstCivil == 'Divorciado(a)'}"> selected="selected" </c:if> > Divorciado(a) </option>
				<option value="Vi�vo(a)" <c:if test="${sessaoAtualizacaoDados.cadEstCivil == 'Vi�vo(a)'}"> selected="selected" </c:if> > Vi�vo(a) </option>
			</select> 		
		</div>
		
		<div class="corMedia divFormulario" >
			<label class="labelFormulario" > RG: </label> 
			<input type="text" style="width: 250px;"  name="sessaoAtualizacaoDados.cadRG" value="${sessaoAtualizacaoDados.cadRG}" >
			<label class="labelFormulario" > Emissor: </label> 
			<input type="text" style="width: 260px;"  name="sessaoAtualizacaoDados.cadOrgaoExpedidor" value="${sessaoAtualizacaoDados.cadOrgaoExpedidor}" >
		</div>
		
		<div class="corClara divFormulario" >
			<label class="labelFormulario" >CEP: </label> 
			<input type="text" style="width: 90px;" name="sessaoAtualizacaoDados.cadCEP" value="${sessaoAtualizacaoDados.cadCEP}" >	
			<a style="margin-left: 30px;" href="http://www.buscacep.correios.com.br" target="_blank" > Pesquisar CEP </a>			
		</div>
		
		<div class="corMedia divFormulario" >
			<label class="labelFormulario" >End. Resid.: </label> 
			<input type="text" style="width: 550px;" name="sessaoAtualizacaoDados.cadEndereco" value="${sessaoAtualizacaoDados.cadEndereco}" >
		</div>
		
		<div class="corClara divFormulario" >
			<label class="labelFormulario" >Bairro: </label> 
			<input type="text" style="width: 585px;" name="sessaoAtualizacaoDados.cadBairro" value="${sessaoAtualizacaoDados.cadBairro}" >
		</div>
		
		<div class="corMedia divFormulario" >
			<label class="labelFormulario" >Cidade: </label> 
			<input type="text" style="width: 340px;" name="sessaoAtualizacaoDados.cadCidade" value="${sessaoAtualizacaoDados.cadCidade}" >
			<label class="labelFormulario" >Estado: </label> 
			<select id='uf' name="sessaoAtualizacaoDados.cadUF" style="width: 160px;" >
				<option value="AC">Acre</option>
				<option value="AL">Alagoas</option>
				<option value="AP">Amap�</option>
				<option value="AM">Amazonas</option>
				<option value="BA">Bahia</option>
				<option value="CE">Cear�</option>
				<option value="DF">Distrito Federal</option>
				<option value="ES">Espirito Santo</option>
				<option value="GO">Goi�s</option>
				<option value="MA">Maranh�o</option>
				<option value="MT">Mato Grosso</option>
				<option value="MS">Mato Grosso do Sul</option>
				<option value="MG">Minas Gerais</option>
				<option value="PA">Par�</option>
				<option value="PB">Paraiba</option>
				<option value="PR">Paran�</option>
				<option value="PE">Pernambuco</option>
				<option value="PI">Piau�</option>
				<option value="RJ">Rio de Janeiro</option>
				<option value="RN">Rio Grande do Norte</option>
				<option value="RS">Rio Grande do Sul</option>
				<option value="RO">Rond�nia</option>
				<option value="RR">Roraima</option>
				<option value="SC">Santa Catarina</option>
				<option value="SP">S�o Paulo</option>
				<option value="SE">Sergipe</option>
				<option value="TO">Tocantis</option>
			</select> 	
		</div>
		
		<div class="corClara divFormulario" >
			<label class="labelFormulario" >Tel. resid.: </label> 
			<input type="text" style="width: 225px;" name="sessaoAtualizacaoDados.tel" value="${sessaoAtualizacaoDados.tel}" >
			
			<label class="labelFormulario" >Tel. Cel.: </label> 
			<input type="text" style="width: 235px;" name="sessaoAtualizacaoDados.cadCelular" value="${sessaoAtualizacaoDados.cadCelular}" >

		</div>
		
		<div class="corMedia divFormulario" >
			<label class="labelFormulario" >Email: </label> 
			<input type="text" style="width: 585px;" name="sessaoAtualizacaoDados.email" value="${sessaoAtualizacaoDados.eMail}" >
		</div>
		
		<div class="divFormulario" >
			<label class="labelFormulario" > <b> Dados banc�rios para pagamento de bonifica��o </b></label> 
		</div>
		
		<div class="corClara divFormulario" >
			<label class="labelFormulario" >Banco: </label> 
			<input type="text" style="width: 200px; " name="sessaoAtualizacaoDados.cadBanco" value="${sessaoAtualizacaoDados.cadBanco}">
			<label class="labelFormulario" >Tipo de conta: </label> 
			<select name="sessaoAtualizacaoDados.cadTipoConta" style="width: 200px;" >
				<option value="Conta corrente"  <c:if test="${sessaoAtualizacaoDados.cadTipoConta == 'Conta corrente'}"> selected="selected" </c:if> >Conta corrente</option>
				<option value="Conta poupan�a" <c:if test="${sessaoAtualizacaoDados.cadTipoConta == 'Conta poupan�a'}"> selected="selected" </c:if> >Conta poupan�a</option>
				<option value="Conta sal�rio" <c:if test="${sessaoAtualizacaoDados.cadTipoConta == 'Conta sal�rio'}"> selected="selected" </c:if> >Conta sal�rio</option>
			</select> 
		</div>
		
		<div class="corMedia divFormulario" >
			<label class="labelFormulario" >N� Ag�ncia: </label> 
			<input type="text" style="width: 170px;;" name="sessaoAtualizacaoDados.cadAgencia" value="${sessaoAtualizacaoDados.cadAgencia}">
			<label class="labelFormulario" >N� da Conta: </label> 
			<input type="text" style="width: 200px;" name="sessaoAtualizacaoDados.cadCCorrente" value="${sessaoAtualizacaoDados.cadCCorrente}">
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

$('#uf').val('${sessaoAtualizacaoDados.cadUF}');

jQuery("#entregaMercadoria").click(function(){
	
	jQuery('input[name="sessaoAtualizacaoDados.CEPEntregaMercadoria"]').val(jQuery('input[name="sessaoAtualizacaoDados.CEP"]').val());
	jQuery('input[name="sessaoAtualizacaoDados.enderecoEntregaMercadoria"]').val(jQuery('input[name="sessaoAtualizacaoDados.endereco"]').val());
	jQuery('input[name="sessaoAtualizacaoDados.numeroEnderecoEntregaMercadoria"]').val(jQuery('input[name="sessaoAtualizacaoDados.numeroEndereco"]').val());
	jQuery('input[name="sessaoAtualizacaoDados.complementoEnderecoEntregaMercadoria"]').val(jQuery('input[name="sessaoAtualizacaoDados.complementoEndereco"]').val());
	jQuery('input[name="sessaoAtualizacaoDados.bairroEntregaMercadoria"]').val(jQuery('input[name="sessaoAtualizacaoDados.bairro"]').val());
	jQuery('input[name="sessaoAtualizacaoDados.cidadeEntregaMercadoria"]').val(jQuery('input[name="sessaoAtualizacaoDados.cidade"]').val());
	jQuery('input[name="sessaoAtualizacaoDados.estadoEntregaMercadoria"]').val(jQuery('input[name="sessaoAtualizacaoDados.estado"]').val());			
});

</script>
