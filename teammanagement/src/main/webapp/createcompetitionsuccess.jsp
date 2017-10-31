<%@ page language="java" contentType="text/html;charset=UTF-8" %> 
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<html>

<head>
<link rel="stylesheet" type="text/css" media="all" href="${pageContext.request.contextPath}/resources/css/styles.css"/>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">

<title>Competición Creada</title>

</head>

<body>
<jsp:include page="registerNavigation.jsp" />
<br>
<br>
<h3>La competición se ha creado correctamente</h3>

<table>

<tr>

<td>Nombre de la competición :</td>



<td><c:out value="${competitionForm.competitionName}" /></td>

</tr>


Seleccione equipo:  

<form:form method="POST" commandname="selectTeam">  
<table>  
    <tbody><tr>  
    <td>  
        <ul>  
            <form:radiobuttons element="li" path="selectedTeam" items="${competitionForm.availableTeams}">  
        </form:radiobuttons></ul>  
    </td>  
    </tr>  
    <tr>  
        <td>  
            <input value="Elegir Equipo" type="submit">  
        </td>  
    </tr>  
</tbody></table>    
</form:form>

</body>

</html>
