<%@ page language="java" contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>


<html>

<head>
<link rel="stylesheet" type="text/css" media="all" href="<c:url value="/resources/css/styles.css" context="/teammanagement"/>">  
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">

<title>Seleccionar equipo</title>

</head>

<c:url value="team" var="TeamUrl"/>

<body>
<jsp:include page="registerNavigation.jsp" />
<br>
<br>


<p>${errormessage}</p>


</body>




</html>
