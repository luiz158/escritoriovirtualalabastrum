<%@ include file="/base.jsp" %> 

<link type="text/css" href="<c:url value="/css/jquery.treeview.css"/>" rel="stylesheet" />
<link type="text/css" href="<c:url value="/css/screen.css"/>" rel="stylesheet" />
<script type="text/javascript" src="<c:url value="/js/jquery.cookie.js"/>"></script>
<script type="text/javascript" src="<c:url value="/js/jquery.treeview.js"/>"></script>

<style>

	.menu {
		display: none;
	    width: 0px;
		min-width: 0px;
	}
	
	.conteudo {
	    width: 94%;
	}
	
	.nomeUsuario:hover { 
		color: red; 
		cursor: pointer; 
	}
	
	.usuarioSelecionado{
		background-color: rgb(210, 210, 210);
		padding: 5px;
		border-radius: 4px;
	}
	
	.dica{
		color: rgb(170, 170, 170);
		font-size: 12px;
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
	
	.nomeUsuario.ativo {
	
		font-weight: bold;
	}
	
</style>

<a class="btn" href="<c:url value="/arvoreRelacionamento/acessarTelaArvoreRelacionamento"/>" > Voltar </a>

<div class="informacoes" >

	<h4 style="text-align: center;font-size: 14px;" > Informações </h4>
	
	<label class="labelInformacoes" > Nome: </label>  <span id="informacaoNome" >  </span>  <br>
	<label class="labelInformacoes" > Codigo: </label>  <span id="informacaoCodigo" >  </span>  <br>
	<label class="labelInformacoes" > Endereço: </label>  <span id="informacaoEndereco" >  </span>  <br>
	<label class="labelInformacoes" > Bairro: </label>  <span id="informacaoBairro" >  </span>  <br>
	<label class="labelInformacoes" > Cidade: </label>  <span id="informacaoCidade" >  </span>  <br>
	<label class="labelInformacoes" > Telefones: </label>  <span id="informacaoTelefones" >  </span>  <br>
	<label class="labelInformacoes" > Celular: </label>  <span id="informacaoCelular" >  </span>  <br>
	<label class="labelInformacoes" > Email: </label>  <span id="informacaoEmail" >  </span>  <br>
	<br>
	<label class="labelInformacoes" > Pontuação total deste mês: </label>  <span id="informacaoPontuacaoTotal" >  </span>  <br>
	<label class="labelInformacoes" > Ativo: </label>  <span id="informacaoAtivo" >  </span>  <br>

</div>

<h3 style="font-size: 20px; margin-bottom: 0px;" > Árvore de relacionamentos </h3>  <span class="dica" > (Clique no nome para ver informações) </span> 

<br>

<ul id="gray" class="treeview-gray" style="margin-top: 20px;" >
	<li class="closed" id="${usuarioSelecionado.id_Codigo}" >
		<c:set var="ativo" value="" /> 
		<c:if test="${usuarioSelecionado.pontuacaoAuxiliar.ativo}">
			<c:set var="ativo" value="ativo" /> 
		</c:if>
		<span class="nomeUsuario ${ativo}" >${usuarioSelecionado.id_Codigo} - ${usuarioSelecionado.posAbrev} - ${usuarioSelecionado.vNome}</span>
		<ul> </ul>
	</li>
</ul>

<script>

	jQuery(document).ready(function(){
		
		jQuery("#gray").treeview({
			
			control: "#treecontrol",
		});

		jQuery(document).on('click', '.nomeUsuario', function(){  
			
			removerClassesUsuariosSelecionados();
			
			jQuery(this).addClass("usuarioSelecionado");
			
			var idUsuario = jQuery(this).parent().attr("id");

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

			buscarUsuariosPatrocinados(idUsuario, this);
		});
		
		jQuery(document).on('click', '.hitarea', function(){  

			buscarUsuariosPatrocinados(jQuery(this).parent().attr("id"), this);
		});
		
		function removerClassesUsuariosSelecionados(){
			
			jQuery(".usuarioSelecionado").each(function(i, item){
				
				jQuery(item).removeClass("usuarioSelecionado");
			});
		}
		
		function buscarUsuariosPatrocinados(id, elementoClicado){
			
			if(jQuery("#" + id).find(".hitarea:first").hasClass("expandable-hitarea") || jQuery("#gray").find("li:first").attr("id") == id  ){
				
				jQuery("#" + id).find(".hitarea:first").removeClass("expandable-hitarea");
				jQuery("#" + id).find(".hitarea:first").addClass("collapsable-hitarea");				
				
				if(jQuery("#" + id).find("li").size() == 0 && jQuery("#" + id).hasClass("jaClicado") == false){
					
					jQuery("#" + id).addClass("jaClicado");
	
					jQuery.ajax({ 
				        type: 'GET',
				        url: '<c:url value="/arvoreRelacionamento/buscarUsuariosPatrocinados?codigoUsuario='+id + ' "/>',
				        dataType: 'json', 
				        success: function(data) { 
				        	
				        	if(data.list.length == 0){
				        		
				        		var isAtivo = false;
				        		
				        		if(jQuery("#" + id).find("span:first").hasClass("ativo")){
				        			
				        			isAtivo = true;
				        		}
				        		
				        		jQuery("#" + id).text(jQuery("#" + id).find("span:first").text());
				        		jQuery("#" + id).removeClass("expandable");
				        		var texto = jQuery("#" + id).text();
				        		jQuery("#" + id).text('');
				        		jQuery("#" + id).append("<span>");
				        		jQuery("#" + id).find("span:first").addClass("nomeUsuario");
				        		
				        		if(jQuery(elementoClicado).is("span")){
				        			
				        			jQuery("#" + id).find("span:first").addClass("usuarioSelecionado");
				        		}
				        		
				        		if(isAtivo){
				        			
				        			jQuery("#" + id).find("span:first").addClass("ativo");
				        		}
				        		
				        		jQuery("#" + id).find("span:first").text(texto);
				        	}
	
				            jQuery.each(data.list, function(i, item){
				            	
				            	jQuery("#" + id).find("ul:first").append("<li class='closed'>");
				            	jQuery("#" + id).find("ul:first").find("li:last").append("<div class='hitarea closed-hitarea expandable-hitarea'>");
				            	jQuery("#" + id).find("ul:first").find("li:last").addClass("closed expandable");			            	
				            	jQuery("#" + id).find("ul:first").find("li:last").attr("id", item.id_Codigo);
				            	jQuery("#" + id).find("ul:first").find("li:last").append("<span class='nomeUsuario' >");
				            	jQuery("#" + id).find("ul:first").find("li:last").find("span").text(item.id_Codigo + ' - ' + item.PosAbrev + ' - ' + item.vNome);
				            	jQuery("#" + id).find("ul:first").find("li:last").append("<ul>");
				            	
				            	if(item.pontuacaoAuxiliar.ativo == "Sim"){
				            		
				            		jQuery("#" + id).find("ul:first").find("li:last").find("span").addClass("ativo");
				            	}
				 	    	});
				        }
				    });	 
				}
				else{
					
					jQuery("#" + id).find("ul:first").show();
				}
			}
			else{
				
				jQuery("#" + id).find(".hitarea:first").removeClass("collapsable-hitarea");
				jQuery("#" + id).find(".hitarea:first").addClass("expandable-hitarea");
				
				jQuery("#" + id).find("ul:first").hide();
			}
		}
	});
	
</script>

