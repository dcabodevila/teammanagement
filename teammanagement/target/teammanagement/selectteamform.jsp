<%@ page language="java" contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>


<html>

<head>
<link rel="stylesheet" type="text/css" media="all" href="<c:url value="/resources/css/styles.css" context="/teammanagement"/>">
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Seleccionar equipo</title>

</head>
<body>
<jsp:include page="registerNavigation.jsp" />
<br>
<br>
   <table>
      <c:forEach var="team" items="${availableTeams}">
        <tr>
          <td>${team.nombre}</td>
        </tr>
      </c:forEach>
    </table>

<br>



<form:form class="selectteam-form" method="Post" action="selectteamform" commandName="selectTeamForm">

Seleccione Equipo: 

	<form:select path="selectedIdTeam">
		<form:options items="${availableTeams}" itemValue="idEquipo" itemLabel="nombre"/>
	</form:select>



	<input type="submit" value="Seleccionar" />
</form:form>

<br>
<br>
<table>
<br>
<br>

	<tr>
		<td>
		  <FONT color="red"><form:errors path="*">
		     <c:forEach items="${messages}" var="message">
			<c:out value="* ${message}"/><br />
		     </c:forEach>
		  </form:errors></FONT>
		</td>
	</tr>

</table>

</body>




</html>

