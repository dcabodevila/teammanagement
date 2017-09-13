<%@ page language="java" contentType="text/html;charset=UTF-8" %> 
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>


<html>

<head>
<link rel="stylesheet" type="text/css" media="all" href="<c:url value="/resources/css/styles.css" context="/teammanagement"/>">
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<script type='text/javascript' src='<c:url value="/resources/js/jquery-1.6.4.min.js"/>'></script>
<link rel="stylesheet" type="text/css" media="all" href="<c:url value="/resources/css/pure-min.css" context="/teammanagement"/>">

<title>Activación completada</title>
<script>
$( document ).ready(function() {
	window.setTimeout(function() {
		window.location.href = '/login';
	}, 3000);
});	
</script>
</head>

<body>
<jsp:include page="registerNavigation.jsp" />
<br>
<br>
<center>

<h3>Se ha activado la cuenta correctamente, redirigiendo a la página de login...</h3>


</center>
</body>

</html>
