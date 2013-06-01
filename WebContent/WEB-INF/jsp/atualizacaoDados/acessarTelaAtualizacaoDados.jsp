<%@ include file="/base.jsp" %> 

<style>

	.divAtualizacaoDados{
		width: 700px;
		border: 1px solid rgb(152, 160, 233);
		padding-top: 10px;
		border-radius: 6px;
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

</style>

<div class="divAtualizacaoDados" >

	<div class="divInformacoesUsuarios" >
		<h4> DADOS CADASTRAIS </h4>
		<hr>
		<label class="labelEsquerda" > Código de identificação: </label>  <span class="spanDireita"> ${sessaoUsuario.usuario.id_Codigo} </span>  <br><br>
		<label class="labelEsquerda" > Nome completo: </label>  <span class="spanDireita"> ${sessaoUsuario.usuario.vNome} </span>  <br><br>
		<label class="labelEsquerda" > CPF: </label>  <span class="spanDireita"> ${sessaoUsuario.usuario.CPF} </span>  <br>
	</div>

</div>