<%@ page language="java" contentType="text/html;charset=UTF-8" %> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>


<html>
<%@ page import="es.ligasnba.app.util.constants.Constants" %>
<head>
<script type='text/javascript' src="${pageContext.request.contextPath}/resources/js/jquery-1.10.2.js"/></script>
<script type='text/javascript' src="${pageContext.request.contextPath}/resources/js/jquery-ui.js"/></script>
<script type='text/javascript' src="${pageContext.request.contextPath}/resources/validation/dist/jquery.validate.min.js"/></script>
<link rel="stylesheet" type="text/css" media="all" href="${pageContext.request.contextPath}/resources/css/styles.css"/>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<script type='text/javascript' src="${pageContext.request.contextPath}/resources/js/noty/jquery.noty.js"/></script>
<script type='text/javascript' src="${pageContext.request.contextPath}/resources/js/noty/layouts/top.js"/></script>
<script type='text/javascript' src="${pageContext.request.contextPath}/resources/js/noty/themes/default.js"/></script>
<script>
function submitremember(){

		
	var validator = $("#changepassword-form").validate({ 
		rules: { 
			password: {
				required: true,
				maxlength: <%= Constants.cUserPassMaxLength %>,
				minlength: <%= Constants.cUserPassMinLength %>
			},
			password2: {
				required: true,
				equalTo:"#password"			
			}
		}, 
		errorElement: "span" , 
		messages: { 
			password: {
				required: "<font color='red'>· La contraseña no puede estar vacía<br></font>",
				maxlength: "<font color='red'>· La contraseña debe contener como máximo "+ <%= Constants.cUserPassMaxLength %> +" caracteres<br></font>",
				minlength: "<font color='red'>· La contraseña debe contener al menos "+ <%= Constants.cUserPassMinLength %> +" caracteres<br></font>"
			
			},
			password2: {
				required: "<font color='red'>· La confirmación de la contraseña no puede estar vacía<br></font>",
				equalTo: "<font color='red'>· La confirmación y la contraseña no coinciden<br></font>"
			}
		}
 
	}); 
	if(validator.form()){

		var activation = "${activationKey}";
		var newPwd = $('#password').val();
		$('#sendButton').prop('disabled',true);
		$.ajax({

			type:"post",
			url: "/remember/setNewPassword",	
			data: ({activationKey: activation, newPassword: newPwd}),
			dataType: "json",
			success: function(response){

				if (response.success){
					$('#sendButton').prop('disabled',false);
					generateNoty(""+response.message+"",'success');
					window.setTimeout(function() {
						window.location.href = '/login';
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

<title>Cambiar contraseña</title>
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

<form class="pure-form pure-form-stacked" id="changepassword-form">


<br>
<fieldset>
<legend>Cambiar Contraseña</legend>

<label for="password">Nueva contraseña </label><input type="password" id="password" name="password"/>

<label for="password2">Confirme contraseña </label><input type="password" id="password2" name="password2" />

<p><input class="pure-button pure-button-primary" id="sendButton" type="button" onclick="submitremember();" value="Cambiar contraseña"/></p>
</fieldset>
</form>

</body>
</html>
