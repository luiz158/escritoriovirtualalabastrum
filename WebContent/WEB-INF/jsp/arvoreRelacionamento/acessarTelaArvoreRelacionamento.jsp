<%@ include file="/base.jsp" %> 

<link type="text/css" href="<c:url value="/css/jquery.treeview.css"/>" rel="stylesheet" />
<link type="text/css" href="<c:url value="/css/screen.css"/>" rel="stylesheet" />
<script type="text/javascript" src="<c:url value="/js/jquery.cookie.js"/>"></script>
<script type="text/javascript" src="<c:url value="/js/jquery.treeview.js"/>"></script>

<h3 style="font-size: 20px;" > Árvore de relacionamentos </h3>

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

			buscarUsuariosPatrocinados(jQuery(this).parent().attr("id"));
		});
		
		jQuery(document).on('click', '.hitarea', function(){  

			buscarUsuariosPatrocinados(jQuery(this).parent().attr("id"));
		});
		
		function buscarUsuariosPatrocinados(id){
			
			if(jQuery("#" + id).find("li").size() == 0){

				jQuery.ajax({ 
			        type: 'GET',
			        url: "/arvoreRelacionamento/buscarUsuariosPatrocinados?codigoUsuario=" + id,
			        dataType: 'json', 
			        success: function(data) { 

			            jQuery.each(data.list, function(i, item){
			            	
			            	jQuery("#" + id).find("ul:first").append("<li class='closed'>");
			            	jQuery("#" + id).find("ul:first").find("li:last").attr("id", item.id_Codigo);
			            	jQuery("#" + id).find("ul:first").find("li:last").append("<span class='nomeUsuario' >");
			            	jQuery("#" + id).find("ul:first").find("li:last").find("span").text(item.vNome);
			            	jQuery("#" + id).find("ul:first").find("li:last").append("<ul>");
			            	jQuery("#" + id).find("ul:first").find("li:last").append("<div class='hitarea closed-hitarea expandable-hitarea lastExpandable-hitarea'>");
			            	jQuery("#" + id).find("ul:first").find("li:last").addClass("closed collapsable lastCollapsable");			            	
			 	    	});
			        }
			    });	 
			}
		}
		
	});
	
</script>

