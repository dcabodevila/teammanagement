<%@ page language="java" contentType="text/html;charset=UTF-8" %> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<meta http-equiv="X-UA-Compatible" content="IE=edge"> 
<meta name="mobile-web-app-capable" content="yes" />
<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0, user-scalable=no">


<html>
<%@ page import="es.ligasnba.app.util.constants.Constants" %>
<head>
<jsp:include page="menu.jsp" />

<script type='text/javascript' src='${pageContext.request.contextPath}/resources/js/jquery-3.1.0.min.js'></script>
<script type='text/javascript' src='${pageContext.request.contextPath}/resources/js/jquery-ui.js'></script>
<script type='text/javascript' src='${pageContext.request.contextPath}/resources/validation/dist/jquery.validate.min.js'></script>

<link href="${pageContext.request.contextPath}/resources/css/styles.css" rel="stylesheet" >
<link href="${pageContext.request.contextPath}/resources/css/pure-min.css" rel="stylesheet" >
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<script>
function submitLogin(){

		
	var validator = $("#login-form").validate({ 
		rules: { 
			j_username: {
				required: true,
				maxlength: <%= Constants.cUserNameMaxLength %>,
				minlength: <%= Constants.cUserNameMinLength %>
			},
			j_password: {
				required: true,
				maxlength: <%= Constants.cUserPassMaxLength %>,
				minlength: <%= Constants.cUserPassMinLength %>

			}
		}, 
		errorElement: "span" , 
		messages: { 
			j_username: {
				required: "<font color='red'>· El nombre de usuario no puede estar vacío</font>",
				maxlength: "<font color='red'>· El nombre de usuario debe contener como máximo "+ <%= Constants.cUserNameMaxLength %> +" caracteres<br></font>",
				minlength: "<font color='red'>· El nombre de usuario debe contener al menos "+ <%= Constants.cUserNameMinLength %> +" caracteres<br></font>"
			},
			j_password: {
				required: "<font color='red'>· La contraseña no puede estar vacía</font>",
				maxlength: "<font color='red'>· La contraseña debe contener como máximo "+ <%= Constants.cUserPassMaxLength %> +" caracteres<br></font>",
				minlength: "<font color='red'>· La contraseña debe contener al menos "+ <%= Constants.cUserPassMinLength %> +" caracteres<br></font>"
			
			}
		}
 
	}); 
	if(validator.form()){

		$('#login-form').submit(); 
	}
}
</script>

<title>Login</title>
</head>
 
<body>
<div class="loginForm">
	<form class="pure-form pure-form-stacked" id="login-form" action="/j_spring_security_check" method="post" >

	<fieldset>
	<legend>Login</legend>

	<label for="j_username">Nombre de Usuario </label>
	<input id="j_username" name="j_username" size="20" maxlength="50" type="text"/>


	<label for="j_password">Contraseña</label>
	<input id="j_password" name="j_password" size="20" maxlength="50" type="password"/>
	<p class="messageOK">${message}</p>
	<input class="pure-button pure-button-primary" type="button" onclick="submitLogin();" value="Login"/>
	</fieldset>
	<p><a href="/remember"><font size=2> No recuerdo la contraseña </font></a></p>
	</form>
</div>
</body>
</html>
