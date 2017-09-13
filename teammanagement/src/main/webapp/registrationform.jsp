<%@ page language="java" contentType="text/html;charset=UTF-8" %> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<meta http-equiv="X-UA-Compatible" content="IE=edge"> 
<meta name="mobile-web-app-capable" content="yes" />
<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0, user-scalable=no">


<html>
<%@ page import="es.ligasnba.app.util.constants.Constants" %>
<head>
<jsp:include page="menu.jsp" />
<link rel="stylesheet" type="text/css" media="all" href="${pageContext.request.contextPath}/resources/css/styles.css"/>
<script type='text/javascript' src='${pageContext.request.contextPath}/resources/js/jquery-1.10.2.js'></script>
<script type='text/javascript' src='${pageContext.request.contextPath}/resources/js/jquery-ui.js'></script>
<script type='text/javascript' src='${pageContext.request.contextPath}/resources/validation/dist/jquery.validate.min.js'></script>

<link rel="stylesheet" type="text/css" media="all" href="${pageContext.request.contextPath}/resources/css/pure-min.css"/>



<script>

function submitRegistration(){
	
	var validator = $("#register-form").validate({ 

		rules: { 
			userName: {
				required: true,
				maxlength: <%= Constants.cUserNameMaxLength %>,
				minlength: <%= Constants.cUserNameMinLength %>,
				remote: {
					url: "/register/usernamecheck",
					type: "get",
					data: {
						userName: function() {
							return $( "#userName" ).val();
						}
					}

				}
			},
			password: {
				required: true,
				maxlength: <%= Constants.cUserPassMaxLength %>,
				minlength: <%= Constants.cUserPassMinLength %>
			},
			password2: {
				required: true,
				equalTo:"#password"			
			},
			email: {
				required: true,
				email: true

			}
		}, 
		errorElement: "span" , 
		messages: { 
			userName: {
				required: "<font color='red'>· El nombre de usuario no puede estar vacío<br></font>",
				maxlength: "<font color='red'>· El nombre de usuario debe contener como máximo "+ <%= Constants.cUserNameMaxLength %> +" caracteres<br></font>",
				minlength: "<font color='red'>· El nombre de usuario debe contener al menos "+ <%= Constants.cUserNameMinLength %> +" caracteres<br></font>",
				remote: "<font color='red'>* El nombre de usuario ya pertenece a otro usuario.<br></font>"
			},
			password: {
				required: "<font color='red'>· La contraseña no puede estar vacía<br></font>",
				maxlength: "<font color='red'>· La contraseña debe contener como máximo "+ <%= Constants.cUserPassMaxLength %> +" caracteres<br></font>",
				minlength: "<font color='red'>· La contraseña debe contener al menos "+ <%= Constants.cUserPassMinLength %> +" caracteres<br></font>"
			
			},
			password2: {
				required: "<font color='red'>· La confirmación de la contraseña no puede estar vacía<br></font>",
				equalTo: "<font color='red'>· La confirmación y la contraseña no coinciden<br></font>"
			},
			email: {
				required: "<font color='red'>· El email no puede estar vacío<br></font>",
				email: "<font color='red'>· El email debe ser una dirección real de correo<br></font>",
			}
			
		},
		submitHandler: function(form){

			$('#registerButton').prop('disabled',true);

			var uname= $('#userName').val();
			var pass = $('#password').val();
			var mail = $('#email').val();

			$.ajax({

				type:"post",
				url: "/register/userregister",	
				data: ({userName:uname,password:pass,email:mail}),
				dataType: "json",
				success: function(response){

					if (response.success){
						
						$('#errorMessages').append("<font color='green'>· "+response.message+"</font><br> Redirigiendo a la página de login...");
						window.setTimeout(function() {
							window.location.href = '/login';
						}, 3000);	
					}

					else {
						$('#errorMessages').append("<font color='red'>· "+response.message+"</font>");

					}


				},
				error: function(){

					alert("error");
				}
			});	

		} 
	}); 
	validator.form();

	
}
</script>




<title>Registro de usuario</title>

</head>

<body>

<form class="pure-form pure-form-stacked" name="register-form" id="register-form" method="Post" action="">

<fieldset>
<legend>Registro de Usuario</legend>

<label for="userName">Nombre de Usuario </label><input type="text" id="userName" name="userName"/>


<label for="password">Contraseña </label><input type="password" id="password" name="password"/>

<label for="password2">Confirme Contraseña </label><input type="password" id="password2" name="password2" />
<label for="email">Email</label><input type="text" id="email" name="email"/>


<p><input class="pure-button pure-button-primary" id="registerButton" type="submit" onclick="submitRegistration();" value="Registrarse" /></p>

</fieldset>
<div id="errorMessages"></div>

</form>



</body>

</html>


