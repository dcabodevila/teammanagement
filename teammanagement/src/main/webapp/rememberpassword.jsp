<%@ page language="java" contentType="text/html;charset=UTF-8" %> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>


<html>
<%@ page import="es.ligasnba.app.util.constants.Constants" %>
<head>
<script type='text/javascript' src='<c:url value="/resources/js/jquery-1.10.2.js"/>'></script>
<script type='text/javascript' src='<c:url value="/resources/js/jquery-ui.js"/>'></script>
<script type='text/javascript' src='<c:url value="/resources/validation/dist/jquery.validate.min.js"/>'></script>
<link rel="stylesheet" type="text/css" media="all" href="<c:url value="/resources/css/styles.css" context="/teammanagement"/>">
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<script type='text/javascript' src='<c:url value="/resources/js/noty/jquery.noty.js"/>'></script>
<script type='text/javascript' src='<c:url value="/resources/js/noty/layouts/top.js"/>'></script>
<script type='text/javascript' src='<c:url value="/resources/js/noty/themes/default.js"/>'></script>
<script>
function submitremember(){

		
	var validator = $("#remember-form").validate({ 
		rules: { 
			registerEmail: {
				required: true,
				email: true
			}
		}, 
		errorElement: "span" , 
		messages: { 
			registerEmail: {
				required: "<font color='red'>· La dirección de correo no puede estar vacía</font>",
				email: "<font color='red'>· La dirección debe ser válida.</font>"
			}
		}
 
	}); 
	if(validator.form()){

		var mail = $('#registerEmail').val();
		$('#sendButton').prop('disabled',true);
		$.ajax({

			type:"post",
			url: "/teammanagement/remember/send",	
			data: ({registerEmail:mail}),
			dataType: "json",
			success: function(response){

				if (response.success){
					$('#sendButton').prop('disabled',false);
					generateNoty(""+response.message+"",'success');
					window.setTimeout(function() {
						window.location.href = '/teammanagement/login';
					}, 3000);
				}

				else {
					$('#sendButton').prop('disabled',false);
					generateNoty(""+response.message+"",'error');

				}


			},
			error: function(){
				$('#sendButton').prop('disabled',false);
				generateNoty("Error en la llamada al servidor",'error');
			}
		});			
	}
}

function generateNoty(msg,type){

  	var n = noty({
  		text: msg,
  		type: type,
		closeWith: ['hover'],
		timeout: 1000
  	});

}
</script>

<title>Recordar Contraseña</title>
</head>
 
<body>
<c:url value="/" var="homeUrl"/>
<c:url value="/register" var="registerUrl"/>


<div class="loginNavigation">
<ul>
<li><a href="${homeUrl}">Volver</a></li>
<li><a href="${registerUrl}">Registrarse</a></li>
</ul>

</div>

<form class="pure-form pure-form-stacked" id="remember-form">


<br>
<fieldset>
<legend>Recordar Contraseña</legend>

<label for="registerEmail">Email con el que se registró: </label>
<input id="registerEmail" name="registerEmail" size="20" maxlength="50" type="text"/>
<input class="pure-button pure-button-primary" id="sendButton" type="button" onclick="submitremember();" value="Enviar"/>
</fieldset>
</form>

</body>
</html>
