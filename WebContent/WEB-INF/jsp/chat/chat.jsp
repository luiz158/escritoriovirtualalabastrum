<%@ include file="/base.jsp" %> 

<form id="formescritoriovirtualalabastrum" method="post" action="http://localhost:8080/escritoriovirtualalabastrum/login/loginVindoDoPCP" target="novaAbaescritoriovirtualalabastrum" style="display: none" >
	<input type="hidden" id='loginescritoriovirtualalabastrum' name="loginescritoriovirtualalabastrum" value="renan" />
	<input type="hidden" id='senhaescritoriovirtualalabastrum' name="senhaescritoriovirtualalabastrum" value="1234" />
	<input type="hidden" id="nomeescritoriovirtualalabastrum" name="nomeescritoriovirtualalabastrum" value="Renan Vaz" />
	<input type="hidden" id="nomeEmpresaescritoriovirtualalabastrum" name="nomeEmpresaescritoriovirtualalabastrum" value="Projetec" />
	<input type="hidden" id="enderecoescritoriovirtualalabastrum" name="enderecoescritoriovirtualalabastrum" value="http://localhost:8080/escritoriovirtualalabastrum" />
</form>

<div id='usuariosLogados'>
	<h5> Usuários online </h5>  
	<ul> </ul>	
</div>

<script type="text/javascript">

	jQuery(document).ready(function(){
    	
		if(navigator.appName != 'Microsoft Internet Explorer'){
    	
			loginescritoriovirtualalabastrum = jQuery("#loginescritoriovirtualalabastrum").val();
    		senhaescritoriovirtualalabastrum = jQuery("#senhaescritoriovirtualalabastrum").val();
    		nomeescritoriovirtualalabastrum = jQuery("#nomeescritoriovirtualalabastrum").val();
    		nomeEmpresaescritoriovirtualalabastrum = jQuery("#nomeEmpresaescritoriovirtualalabastrum").val();
    		enderecoescritoriovirtualalabastrum = jQuery("#enderecoescritoriovirtualalabastrum").val();
		    
		    var broadcastMessageCallback = function(nomeDestinatario, codigoDestinatario, msg) {
		        
		    jQuery("#chat"+codigoDestinatario).chatbox("option", "boxManager").addMsg(nomeDestinatario, msg);
		
				jQuery.ajax({ 
			        type: 'GET',
			        url: enderecoescritoriovirtualalabastrum + "/chat/recebeMensagem?loginescritoriovirtualalabastrum=" + loginescritoriovirtualalabastrum + "&senhaescritoriovirtualalabastrum="+ senhaescritoriovirtualalabastrum + "&nomeescritoriovirtualalabastrum="+ nomeescritoriovirtualalabastrum + "&nomeEmpresaescritoriovirtualalabastrum="+ nomeEmpresaescritoriovirtualalabastrum + "&destinatario=" + codigoDestinatario + "&mensagem="+ msg,
			        dataType: 'jsonp', 
			        jsonp: false,
					jsonpCallback: "jsonMensagemEnviada",
			        success: function(data) { 
			        }
		    	});
		    }
	
			chatboxManager.init({messageSent : broadcastMessageCallback});
	
			exibirUsuariosLogados();
	
			intervaloVerificacaoExistenciaNovasMensagens = setInterval(verificaExistenciaNovasMensagens, 1000);
			setInterval(exibirUsuariosLogados, 10000);

			jQuery(document).on('click', '#usuariosLogados li', function(){  
				
				var keyRemetente = jQuery(this).attr("id");
				var nomeRemetente = jQuery(this).text();
				
				chatboxManager.addBox("chat"+keyRemetente, {dest: keyRemetente, title: '' , first_name: nomeRemetente, last_name: '' });
			});
    	}
    	
    	else{
    		
    		alert("Chat ainda não suportado no internet explorer. Por favor, utilize outro navegador.");
    	}
 
	});
    
    function exibirUsuariosLogados(){
		
	    jQuery.ajax({ 
	        type: 'GET',
	        url: enderecoescritoriovirtualalabastrum + "/chat/exibirUsuariosLogados?loginescritoriovirtualalabastrum=" + loginescritoriovirtualalabastrum + "&senhaescritoriovirtualalabastrum="+ senhaescritoriovirtualalabastrum + "&nomeescritoriovirtualalabastrum="+ nomeescritoriovirtualalabastrum + "&nomeEmpresaescritoriovirtualalabastrum="+ nomeEmpresaescritoriovirtualalabastrum,
	        dataType: 'jsonp', 
	        jsonp: false,
		    jsonpCallback: "jsonUsuariosLogados",
	        success: function(data) { 
	        	
	        	jQuery("#usuariosLogados ul").empty();
	        	
	            jQuery.each(data.list, function(i, item){

	               jQuery("#usuariosLogados ul").append("<li>");
	               jQuery("#usuariosLogados li:last").attr("id", item.empresa.nome + "_" + item.login);
	               jQuery("#usuariosLogados li:last").append(item.nome);

	 	    	});
	        }
	    });	    
	}
    
    function verificaExistenciaNovasMensagens(){
		
	    jQuery.ajax({ 
	        type: 'GET',
	        url: enderecoescritoriovirtualalabastrum + "/chat/verificaExistenciaNovasMensagens?loginescritoriovirtualalabastrum=" + loginescritoriovirtualalabastrum + "&senhaescritoriovirtualalabastrum="+ senhaescritoriovirtualalabastrum + "&nomeescritoriovirtualalabastrum="+ nomeescritoriovirtualalabastrum + "&nomeEmpresaescritoriovirtualalabastrum="+ nomeEmpresaescritoriovirtualalabastrum,
	        dataType: 'jsonp', 
	        jsonp: false,
			jsonpCallback: "jsonVerificacaoExistenciaNovasMensagens",
	        success: function(data) { 
	            jQuery.each(data.list, function(i, item){

	               chatboxManager.addBox("chat"+item.remetente, {dest: item.remetente, title: '' , first_name: item.nome, last_name: '' });
	               jQuery("#chat"+item.remetente).chatbox("option", "boxManager").addMsg(item.nome, item.mensagem);

	 	    	});
	        }
	    });
	    
	    var existePeloMenosUmChatAberto = false;
	    
	    jQuery(".ui-chatbox").each(function(){
	    	
	    	if(jQuery(this).css("display") == "block"){
	    		
	    		existePeloMenosUmChatAberto = true;
	    		return;
	    	}	    	
	    });
	    
	    clearInterval(intervaloVerificacaoExistenciaNovasMensagens);
	    
	    if(existePeloMenosUmChatAberto){
	    	
		    intervaloVerificacaoExistenciaNovasMensagens = setInterval(verificaExistenciaNovasMensagens, 1000);
	    }
	    else{
	    	
	    	intervaloVerificacaoExistenciaNovasMensagens = setInterval(verificaExistenciaNovasMensagens, 10000);
	    }	    
	} 
</script>