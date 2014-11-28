<%@ include file="/base.jsp" %> 

<link type="text/css" href="<c:url value="/css/tree.css"/>" rel="stylesheet" />
<script type="text/javascript" src="<c:url value="/js/tree.js"/>"></script>

<style>

	.menu {
		display: none;
	    width: 0px;
		min-width: 0px;
	}
	
	.conteudo {
	    width: 94%;
	}
	
	.dica{
		color: rgb(69, 65, 65);
		font-size: 13px;
		margin-bottom: 20px;
	}
	
	div.informacoes{
		display: none;
		box-shadow: 5px 5px 5px #888;
		border-radius: 5px;
		border: 1px solid #ddd;
		width: 400px;
		right: 20px;
		position: fixed;
		bottom: 20px;
		padding: 20px;
		background-color: white;
		z-index: 100;
	}
	
	div.informacoes span{
		font-size: 40px;
		font-size: 12px;
	}
	
	label.labelInformacoes{
		display: inline-block;
		font-weight: bold;
		font-size: 12px;
	}
	
	.topo{
		display: none;	
	}
	
	.bem-vindo{
		display: none
	}
	
	#fechar{
		float: right;
		font-size: 18px;
		font-weight: bold;
		color: grey;
		cursor: pointer;
	}
	
</style>

<a class="btn" href="<c:url value="/arvoreRelacionamento2/acessarTelaArvoreRelacionamento"/>" > Voltar </a>

<div class="informacoes" >

	<p id='fechar'> x </p>

	<h4 style="text-align: center;font-size: 14px;" > Informa��es </h4>
	
	<label class="labelInformacoes" > Nome: </label>  <span id="informacaoNome" >  </span>  <br>
	<label class="labelInformacoes" > Codigo: </label>  <span id="informacaoCodigo" >  </span>  <br>
	<label class="labelInformacoes" > Endere�o: </label>  <span id="informacaoEndereco" >  </span>  <br>
	<label class="labelInformacoes" > Bairro: </label>  <span id="informacaoBairro" >  </span>  <br>
	<label class="labelInformacoes" > Cidade: </label>  <span id="informacaoCidade" >  </span>  <br>
	<label class="labelInformacoes" > Telefones: </label>  <span id="informacaoTelefones" >  </span>  <br>
	<label class="labelInformacoes" > Celular: </label>  <span id="informacaoCelular" >  </span>  <br>
	<label class="labelInformacoes" > Email: </label>  <span id="informacaoEmail" >  </span>  <br>
	<br>
	<label class="labelInformacoes" > Pontua��o total deste m�s: </label>  <span id="informacaoPontuacaoTotal" >  </span>  <br>
	<label class="labelInformacoes" > Ativo: </label>  <span id="informacaoAtivo" >  </span>  <br>

</div>

<h3 style="font-size: 20px; margin-bottom: 0px;" > �rvore de relacionamentos </h3>  <span class="dica" > (Clique no nome para ver informa��es) </span> 

<br>

<div style='width: 4000px;'>
		<ul class="tree">
			<li>
				${htmlUsuarios}
			</li>
		</ul>
	</div>

<script>

	jQuery(document).ready(function(){
		
		$('.tree').tree_structure({
			'add_option': false,
			'edit_option': false,
			'delete_option': false,
			'confirm_before_delete' : false,
			'animate_option': [false, 0],
			'fullwidth_option': true,
			'align_option': 'center',
			'draggable_option': false
		});
		
		jQuery(document).on('click', '#fechar', function(){  
			
			jQuery(".informacoes").hide();				
		});

		jQuery(document).on('click', '.current', function(){  
			
			var idUsuario = jQuery(this).attr("id");

			jQuery(".informacoes").show();			
			
			jQuery.ajax({ 
		        type: 'GET',
		        url: '<c:url value="/arvoreRelacionamento/buscarInformacoesUsuarioSelecionado?codigoUsuario='+idUsuario + ' "/>',
		        dataType: 'json', 
		        success: function(data) { 
		        	
		        	jQuery("#informacaoNome").text(data.usuario.vNome);
		        	jQuery("#informacaoCodigo").text(data.usuario.id_Codigo);
		        	jQuery("#informacaoEndereco").text(data.usuario.cadEndereco);
		        	jQuery("#informacaoBairro").text(data.usuario.cadBairro);
		        	jQuery("#informacaoCidade").text(data.usuario.cadCidade);
		        	jQuery("#informacaoTelefones").text(data.usuario.Tel);
		        	jQuery("#informacaoCelular").text(data.usuario.cadCelular);
		        	jQuery("#informacaoEmail").text(data.usuario.eMail);
		        	jQuery("#informacaoPontuacaoTotal").text(data.usuario.pontuacaoAuxiliar.totalFormatado);
		        	jQuery("#informacaoAtivo").text(data.usuario.pontuacaoAuxiliar.ativo);
		        }
			});
		});
	});
	
</script>

