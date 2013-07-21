<%@ include file="/base.jsp" %> 

<style>

	.menu {
		display: none;
	    width: 0px;
		min-width: 0px;
	}
	
	.topo, .bem-vindo, #sair {
		display: none;
	}
	
	.conteudo {
	    width: 720px;
		float: none;
	}

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
		<h4 style="text-align: center" > Pr�-cadastro de distribuidor </h4>
		<br>
	</div>
	
	<form action="<c:url value="/atualizacaoDados/salvarPreCadastroDistribuidorPeloSite"/>" method="post" >	
	
		<div class="corMedia divFormulario" >
			<label class="labelFormulario" >Nome: </label> 
			<input type="text" style="width: 585px;" name="sessaoAtualizacaoDados.vnome" value="${sessaoAtualizacaoDados.vNome}" required="required" >
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
			<label class="labelFormulario" > CPF: </label> 
			<input type="text" style="width: 150px;"  name="sessaoAtualizacaoDados.CPF" value="${sessaoAtualizacaoDados.CPF}" required="required" >
			<label class="labelFormulario" > RG: </label> 
			<input type="text" style="width: 150px;"  name="sessaoAtualizacaoDados.cadRG" value="${sessaoAtualizacaoDados.cadRG}" >
			<label class="labelFormulario" > Emissor: </label> 
			<input type="text" style="width: 140px;"  name="sessaoAtualizacaoDados.cadOrgaoExpedidor" value="${sessaoAtualizacaoDados.cadOrgaoExpedidor}" >
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
			<select name="sessaoAtualizacaoDados.cadUF" style="width: 160px;" >
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
				<option value="RJ" selected="selected" >Rio de Janeiro</option>
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
			<input type="text" style="width: 585px;" name="sessaoAtualizacaoDados.email" value="${sessaoAtualizacaoDados.eMail}" required="required" >
		</div>
		
		<div class="divFormulario" >
			<label class="labelFormulario" > <b> Dados do 2� titular </b></label> 
		</div>
		
		<div class="corMedia divFormulario" >
			<label class="labelFormulario" >Nome: </label> 
			<input type="text" style="width: 580px;"  name="sessaoAtualizacaoDados.vnomeTitular2" value="${sessaoAtualizacaoDados.vNomeTitular2}" >
		</div>
		
		<div class="corClara divFormulario" >
			<label class="labelFormulario" >Data nasc.: </label> 
			<input type="text" style="width: 90px;" class="data" name="sessaoAtualizacaoDados.dt_NascTitular2" value="${sessaoAtualizacaoDados.dt_NascTitular2}" >
			<label class="labelFormulario" >Sexo: </label> 
			<select name="sessaoAtualizacaoDados.sexoTitular2" style="width: 130px;" >
				<option value="Masculino" <c:if test="${sessaoAtualizacaoDados.sexoTitular2 == 'Masc'}"> selected="selected" </c:if>  > Masc </option>
				<option value="Feminino" <c:if test="${sessaoAtualizacaoDados.sexoTitular2 == 'Fem'}"> selected="selected" </c:if> > Fem </option>
			</select>
			<label class="labelFormulario" >Estado civil: </label>
			<select name="sessaoAtualizacaoDados.EstCivilTitular2" style="width: 165px;" >
				<option value="Solteiro(a)" <c:if test="${sessaoAtualizacaoDados.estCivilTitular2 == 'Solteiro(a)'}"> selected="selected" </c:if>  > Solteiro(a) </option>
				<option value="Casado(a)" <c:if test="${sessaoAtualizacaoDados.estCivilTitular2 == 'Casado(a)'}"> selected="selected" </c:if> > Casado(a) </option>
				<option value="Divorciado(a)" <c:if test="${sessaoAtualizacaoDados.estCivilTitular2 == 'Divorciado(a)'}"> selected="selected" </c:if> > Divorciado(a) </option>
				<option value="Vi�vo(a)" <c:if test="${sessaoAtualizacaoDados.estCivilTitular2 == 'Vi�vo(a)'}"> selected="selected" </c:if> > Vi�vo(a) </option>
			</select> 		
		</div>
		
		<div class="corMedia divFormulario" >
			<label class="labelFormulario" > CPF: </label> 
			<input type="text" style="width: 140px;"  name="sessaoAtualizacaoDados.CPFTitular2" value="${sessaoAtualizacaoDados.CPFTitular2}" >
			<label class="labelFormulario" > RG: </label> 
			<input type="text" style="width: 140px;"  name="sessaoAtualizacaoDados.RGTitular2" value="${sessaoAtualizacaoDados.RGTitular2}" >
			<label class="labelFormulario" > Emissor: </label> 
			<input type="text" style="width: 145px;"  name="sessaoAtualizacaoDados.emissorTitular2" value="${sessaoAtualizacaoDados.emissorTitular2}" >
		</div>
		
		<div class="corClara divFormulario" >
			<label class="labelFormulario" >Tel. resid.: </label> 
			<input type="text" style="width: 225px;" name="sessaoAtualizacaoDados.telTitular2" value="${sessaoAtualizacaoDados.telTitular2}" >
			
			<label class="labelFormulario" >Tel. Cel.: </label> 
			<input type="text" style="width: 230px;" name="sessaoAtualizacaoDados.celTitular2" value="${sessaoAtualizacaoDados.celTitular2}" >

		</div>
		
		<div class="corMedia divFormulario" >
			<label class="labelFormulario" >Email: </label> 
			<input type="text" style="width: 580px; " name="sessaoAtualizacaoDados.emailTitular2" value="${sessaoAtualizacaoDados.eMailTitular2}">
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
		
		<div class="divFormulario" >
			<label class="labelFormulario" > <b> Quem te indicou? </b></label> 
		</div>
		
		<div class="corMedia divFormulario" >
			<label class="labelFormulario" >C�digo: </label> 
			<input type="text" style="width: 100px;;" name="sessaoAtualizacaoDados.codigoQuemIndicou" value="${sessaoAtualizacaoDados.codigoQuemIndicou}">
			<label class="labelFormulario" >Nome: </label> 
			<input type="text" style="width: 350px;" name="sessaoAtualizacaoDados.nomeQuemIndicou" value="${sessaoAtualizacaoDados.nomeQuemIndicou}">
		</div>
		
		<div class="corClara divFormulario" >
			<label class="labelFormulario" >Observa��es gerais: </label> 
			<textarea name="sessaoAtualizacaoDados.observacoes" rows="5" style="width: 450px" > </textarea>
		</div>
		
		<c:if test="${exibirMensagemAgradecimento != true}" > 
			<div style="margin-top: 20px; margin-left: 20px" >
				<button type="submit" class="btn btn-primary" >Enviar</button>
			</div>
		</c:if>
		
		
		<c:if test="${exibirMensagemAgradecimento == true}" > 
		
			<br><br>
			
			<div style="padding-left: 40px;" >
				<h4> Pr�-cadastro realizado com sucesso. Entraremos em contato em breve. </h4>
				
				<img style="margin-left: 30px;" src="http://www.alabastrum.com.br/images/banner1.jpg" />
			</div>
			
			
			<br><br>
			
		</c:if>

	</form>

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

</script>
