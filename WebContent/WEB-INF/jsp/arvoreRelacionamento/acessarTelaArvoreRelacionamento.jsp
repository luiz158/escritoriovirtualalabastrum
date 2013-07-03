<%@ include file="/base.jsp" %> 

<link type="text/css" href="<c:url value="/css/jquery.treeview.css"/>" rel="stylesheet" />
<link type="text/css" href="<c:url value="/css/screen.css"/>" rel="stylesheet" />
<script type="text/javascript" src="<c:url value="/js/jquery.cookie.js"/>"></script>
<script type="text/javascript" src="<c:url value="/js/jquery.treeview.js"/>"></script>

<style>
	
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
		margin-left: 30px;
		font-size: 12px;
	}

</style>

<h3 style="font-size: 20px; display: inline-block;" > Árvore de relacionamentos </h3>  <span class="dica" > (Clique no nome para ver informações) </span> 

<br>

<ul id="gray" class="treeview-gray">
	<li class="closed" id="${sessaoUsuario.usuario.id_Codigo}" >
		<span class="nomeUsuario" >${sessaoUsuario.usuario.vNome}</span>
		<ul> </ul>
	</li>
</ul>

<script>

	jQuery(document).ready(function(){
		
		jQuery("#gray").treeview({
			
			control: "#treecontrol",
		});

		jQuery(document).on('click', '.nomeUsuario', function(){  
			
			jQuery(".usuarioSelecionado").each(function(i, item){
				
				jQuery(item).removeClass("usuarioSelecionado");
			});
			
			jQuery(this).addClass("usuarioSelecionado");

			buscarUsuariosPatrocinados(jQuery(this).parent().attr("id"));
		});
		
		jQuery(document).on('click', '.hitarea', function(){  

			buscarUsuariosPatrocinados(jQuery(this).parent().attr("id"));
		});
		
		function buscarUsuariosPatrocinados(id){
			
			if(jQuery("#" + id).find(".hitarea:first").hasClass("expandable-hitarea") || jQuery("#gray").find("li:first").attr("id") == id  ){
				
				jQuery("#" + id).find(".hitarea:first").removeClass("expandable-hitarea");
				jQuery("#" + id).find(".hitarea:first").addClass("collapsable-hitarea");
				
				if(jQuery("#" + id).find("li").size() == 0){
	
					jQuery.ajax({ 
				        type: 'GET',
				        url: "/arvoreRelacionamento/buscarUsuariosPatrocinados?codigoUsuario=" + id,
				        dataType: 'json', 
				        success: function(data) { 
				        	
				        	if(data.list.length == 0){
				        		
				        		jQuery("#" + id).text(jQuery("#" + id).find("span:first").text());
				        		jQuery("#" + id).removeClass("expandable");
				        		var texto = jQuery("#" + id).text();
				        		jQuery("#" + id).text('');
				        		jQuery("#" + id).append("<span>");
				        		jQuery("#" + id).find("span:first").addClass("usuarioSelecionado nomeUsuario");
				        		jQuery("#" + id).find("span:first").text(texto);
				        	}
	
				            jQuery.each(data.list, function(i, item){
				            	
				            	jQuery("#" + id).find("ul:first").append("<li class='closed'>");
				            	jQuery("#" + id).find("ul:first").find("li:last").append("<div class='hitarea closed-hitarea expandable-hitarea'>");
				            	jQuery("#" + id).find("ul:first").find("li:last").addClass("closed expandable");			            	
				            	jQuery("#" + id).find("ul:first").find("li:last").attr("id", item.id_Codigo);
				            	jQuery("#" + id).find("ul:first").find("li:last").append("<span class='nomeUsuario' >");
				            	jQuery("#" + id).find("ul:first").find("li:last").find("span").text(item.vNome);
				            	jQuery("#" + id).find("ul:first").find("li:last").append("<ul>");
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

