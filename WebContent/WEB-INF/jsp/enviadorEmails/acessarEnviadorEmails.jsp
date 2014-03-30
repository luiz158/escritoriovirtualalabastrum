<%@ include file="/base.jsp" %> 

<h4>  Enviar emails para a rede  </h4>

<br>

<c:choose>

	<c:when test="${empty emailsEnviados}">

		<form action="<c:url value="/enviadorEmails/enviarEmails"/>" method="post" >
			<fieldset>
		
				<div class="control-group">
			    	<label class="control-label">Assunto</label>
			    	<div class="controls">
			    		<input type="text" class="input-xxlarge" value="${assunto}" name="assunto"/>
					</div>
			    </div>
			    
			    <div class="control-group">
			    	<label class="control-label">Corpo</label>
			    	<div class="controls">
			    		<textarea name="corpo" rows="20" style="width: 100%" > ${corpo} </textarea>
					</div>
			    </div>
			    
			     <div class="control-group">
			    	<a onclick="abrirDicasHtml()" style="cursor: pointer"> Dicas HTML  </a>
					 <div id='dicas-html'>
					 	<textarea name="corpo" rows="10" style="width: 75%; rgb(243, 247, 239);" >
<br>  Pular linha

<b> Texto aqui </b>  Negrito

<b style='color: red'> Texto aqui </b>  Cor do texto (Use green para verde, blue para azul, etc)

<b style='font-size: 15px'> Texto aqui </b>  Tamanho do texto em pixels
		
					 	</textarea>
					 </div>
			    </div>
		
				<input type="submit" class="btn btn-primary" name="action" value="Preview" />
				<input type="submit" class="btn btn-success" name="action" value="Enviar" />
				
			</fieldset>
		</form>
		
		<br>
		
		<c:if test="${!empty corpo}">
		
			<p>Preview: </p>
			
			<br>
			
			<div>
			
				${corpo}
			
			</div>
		</c:if>
	</c:when>
	
	<c:otherwise>

		<meta http-equiv="refresh" content="10 ; url=<c:url value="/enviadorEmails/acessarEnviadorEmails"/> " />
	
		O email já foi enviado para os seguintes distribuidores:
		
		<c:forEach items="${emailsEnviados}" var='item' >
		
			${item.id_Codigo} - ${item.vNome} - ${item.eMail}
			<br>
		
		</c:forEach>
	
	</c:otherwise>
	
</c:choose>


<script>

	function abrirDicasHtml(){
		
		$('#dicas-html').show();
	}
	
</script>
