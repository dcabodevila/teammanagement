<%@ page language="java" contentType="text/html;charset=UTF-8" %> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<meta http-equiv="X-UA-Compatible" content="IE=edge"> 
<meta name="mobile-web-app-capable" content="yes" />
<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0, user-scalable=no">

<html>
<head>
<link rel="stylesheet" type="text/css" media="all" href="<c:url value="/resources/css/styles.css" context="/teammanagement"/>">
<title>Inicio</title>
</head>

<body>
<jsp:include page="menu.jsp" />
<h1 id="banner">Unauthorized</h1>
<hr/>

<p class="message">Access denied!</p>
</body>
</html>
