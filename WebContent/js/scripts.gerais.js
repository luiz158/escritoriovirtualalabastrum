
jQuery(document).ready(function() {
	
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
		yearRange: "-70: +1",
		nextText: 'Próximo',
		prevText: 'Anterior',
		changeMonth: true,
		changeYear: true

	});

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
	
	jQuery(document).on('keypress', '.numero-inteiro', function(e){  
		
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

function formatarNumero(numero){
	
	return numero.replace(".", ",");
}

function replaceAll(string, token, newtoken) {
	
	while (string.indexOf(token) != -1) {
 		string = string.replace(token, newtoken);
	}
	
	return string;
}