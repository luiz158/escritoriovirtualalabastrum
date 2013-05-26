
jQuery(document).ready(function() {

	jQuery(".numero-decimal").keypress(function(e) {

		var tecla = (window.event) ? event.keyCode : e.which;

		if ((tecla > 47 && tecla < 58)){

			return true;
		}
		
		if(tecla == 8 || tecla == 0){
			
			return true;
		}
		
		if(tecla == 44){
			
			if(this.value.indexOf(",") == -1){
				
				return true;					
			}
			else{
				return false;
			}				
		}
		
		else{
			return false;
		}
	});

	jQuery(".numero-inteiro").keypress(function(e) {

		var tecla = (window.event) ? event.keyCode : e.which;

		if ((tecla > 47 && tecla < 58))
			return true;
		else {
			if (tecla == 8 || tecla == 0)
				return true;

			else
				return false;
		}
	});
	
	jQuery(".data").keypress(function(e) {
		
		var tecla = (window.event) ? event.keyCode : e.which;
		
		if(tecla == 8){
			
			return true;			
		}

		return false;
	});
	
	jQuery("#marcarDesmarcar").click(function() {
		
		if(jQuery("#marcarDesmarcar").attr("checked") === undefined){

			jQuery(".chekboxTabela").each( function() {
				
				jQuery(this).removeAttr("checked");
			});
		}
		
		else{
			
			jQuery(".chekboxTabela").each( function() {

				jQuery(this).attr("checked", "checked");
			});
		}
	});
	
});

function deletar(link) {  
	
	if (confirm('Tem certeza que deseja excluir?')) {  
		gerarLinkCompleto(link);
	}  
}  