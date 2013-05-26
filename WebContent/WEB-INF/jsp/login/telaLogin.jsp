
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
	<head>
		<title> Login </title>
		<link type="text/css" href="css/bootstrap.css" rel="stylesheet" />	
	</head>

	<style type="text/css">
	
      body {
      
    	background-image: url("http://imagensface.com.br/imagens/imagens-imagem-paisagem-e03050.jpg");
        background-repeat: no-repeat;
        background-size: 100%;
        padding-top: 40px;
        padding-bottom: 40px;
      }
      
      .alert-error {
		margin: auto;
		width: 500px;
	  }
      
      .btn {
      	float: right;
      }
      
      .inputLoginSenha{
      	width: 200px;
      	border-color: rgb(17, 109, 162);
      }
      
      .labelLoginSenha{
      	
      	display: inline;
      	color: rgb(17, 109, 162);
      }

      .form-signin {
      	
      	right: 10px;
      	top: 10px;
		position: fixed;
      	
        max-width: 300px;
        padding: 19px 29px 29px;
        margin: 0 auto 20px;
        background-color: #fff;
        border: 1px solid #e5e5e5;
        -webkit-border-radius: 5px;
           -moz-border-radius: 5px;
                border-radius: 5px;
        -webkit-box-shadow: 0 1px 2px rgba(0,0,0,.05);
           -moz-box-shadow: 0 1px 2px rgba(0,0,0,.05);
                box-shadow: 0 1px 2px rgba(0,0,0,.05);
      }
      .form-signin .form-signin-heading,
      .form-signin .checkbox {
        margin-bottom: 10px;
      }
      .form-signin input[type="text"],
      .form-signin input[type="password"] {
        font-size: 16px;
        height: auto;
        margin-bottom: 15px;
        padding: 7px 9px;
      }

    </style>
	
	<body>
	
		<c:if test="${not empty errors}">
			<div class="alert alert-error">
				<c:forEach items="${errors }" var="error">
					<strong>${error.category }</strong> - ${error.message } <br>
				</c:forEach>
			</div>
		</c:if>
	
		<div class="container">
	
	      <form class="form-signin" action="<c:url value="/login/efetuarLogin"/>" method="post" >
	      
		      <label class="labelLoginSenha" > Código: </label>  
		      <input type="text" class="inputLoginSenha" name="usuario.login" >
		      <br>
		      <label class="labelLoginSenha" > Senha:&nbsp;</label>  
		      <input type="password" class="inputLoginSenha" name="usuario.senha" >
		      
		      <br> <br>
	        
	      	  <button class="btn btn-large btn-primary" type="submit" onclick="this.disabled=true;this.form.submit();" >Entrar</button>
	      </form>
	      
	
	    </div>
		
	</body>
</html>