<%@ page language="java" contentType="text/html;charset=UTF-8" %> 
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>


<html>

<head>
<link rel="stylesheet" type="text/css" media="all" href="${pageContext.request.contextPath}/resources/css/styles.css"/>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">

<title>Usuario registrado</title>

</head>

<body>
<jsp:include page="registerNavigation.jsp" />
<br>
<br>
<center>

<h3>Se ha registrado correctamente</h3>

<table>

<tr>

<td>Su nombre de usuario :</td>

<td><c:out value="${registration.userName}" /></td>

</tr>

</table>

</center>
</body>

</html>
