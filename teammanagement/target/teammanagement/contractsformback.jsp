<%@ page language="java" contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<html>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<head>


<script type='text/javascript' src="${pageContext.request.contextPath}/resources/js/jquery-1.6.4.min.js"/></script>
<script src="http://code.jquery.com/ui/1.10.3/jquery-ui.js"></script>

<link rel="stylesheet" type="text/css" media="screen" href="${pageContext.request.contextPath}/resources/css/cupertino/jquery-ui-1.10.3.custom.css"/>  
<link rel="stylesheet" type="text/css" media="all" href="${pageContext.request.contextPath}/resources/css/styles.css"/>
<link rel="stylesheet" type="text/css" media="all" href="${pageContext.request.contextPath}/resources/css/styles-responsive.css"/>
<link rel="stylesheet" type="text/css" media="all" href="${pageContext.request.contextPath}/resources/css/bootstrap.min.css"/>
<link rel="stylesheet" type="text/css" media="all" href="${pageContext.request.contextPath}/resources/css/bootcards-desktop.min.css"/>
<link rel="stylesheet" type="text/css" media="all" href="${pageContext.request.contextPath}/resources/css/bootcards-demo.css"/>
<link rel="stylesheet" type="text/css" media="all" href="${pageContext.request.contextPath}/resources/css/font-awesome.min.css"/>
<link rel="stylesheet" type="text/css" media="all" href="${pageContext.request.contextPath}/resources/css/Animate.css"/>

<script type='text/javascript' src="${pageContext.request.contextPath}/resources/js/noty/jquery.noty.js"/></script>
<script type='text/javascript' src="${pageContext.request.contextPath}/resources/js/noty/layouts/top.js"/></script>
<script type='text/javascript' src="${pageContext.request.contextPath}/resources/js/noty/themes/default.js"/></script>

<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<c:if test="${not empty teamForm}">
<title>${teamForm.nombreEquipo}</title>
</c:if>

<script type="text/javascript">

function reSign(idJugador){

var w = 800;
var h = 550;

var left = (screen.width/2)-(w/2);
var top = (screen.height/2)-(h/2);

  window.open('/contracts/'+idJugador+'/resign/'+${teamForm.idEquipo}, 'Ofrecer nuevo contrato', 'toolbar=no, location=no,directories=no, status=no, menubar=no, scrollbars=no, resizable=no,copyhistory=no, width='+w+', height='+h+', top='+top+', left='+left );
  return false;

}

</script>


</head>


<body>

<nav class="navbar navbar-default navbar-fixed-top" role="navigation">
  <div class="container">
    <!-- Brand and toggle get grouped for better mobile display -->
    <div class="navbar-header">
      <button type="button" id="botonAtras" onClick="transitionDetailToList()" class="hidden" style="margin-top:10;margin-left:5;">
      	Atrás
      </button>
 
	  <a class="navbar-brand no-break-out"  title="TeamManagement" href="/competition/${menuNavigationForm.idCompeticion}">TeamManagement</a>
    </div>
  </div>
</nav>


	 <!-- Capa destinada al header -->
	 <div id="cabecera">
	 </div>


	<div class="cuerpo">

	 


		<sec:authorize access="isAuthenticated()">
		<c:if test="${not empty teamForm}">
		
			<div class="updateMessage" align=center>${updateMessage}</div>



			<table class="datatable" class="playerminutes">
				<caption>Plantilla</caption>
				    <tr>

					<th>Nombre</th>
					<th>Media</th>
					<th>Edad</th>
					<th>Min. Rotación</th>
					<th>Salario Temporada Actual</th>
					<th>Salario Temporada 2</th>
					<th>Salario Temporada 3</th>
					<th>Años Contrato</th>

				    </tr>

				   <c:forEach items="${teamForm.listaInfoJugadores}" var="jugador" varStatus="status">
				    <tr height=44>
					<fmt:setLocale value="en_US"/>
					<td>${jugador.nombre}</td><td>${jugador.media}</td><td>${jugador.edad}</td><td>${jugador.minutos}</td>
					<c:set var="fontColorSal0" value="black"/>

             			        <td align="right"><font color="${fontColorSal0}"><fmt:formatNumber value="${jugador.contractlines[0].salario}" type="number" /></font></td>
					<td align="right"><font color="${fontColorSal1}"><fmt:formatNumber value="${jugador.contractlines[1].salario}" type="number" /></font></td>
					<td align="right"><font color="${fontColorSal2}"><fmt:formatNumber value="${jugador.contractlines[2].salario}" type="number" /></font></td>
					<td>${jugador.contractYears}</td><c:if test="${jugador.contractYears==1}"><td><input type="button" class="pure-button" onclick="reSign(${jugador.idJugador});" value="Ofrecer renovación"></td></c:if>

				    </tr>
				   </c:forEach>

			</table> 

		</sec:authorize>
		</c:if>

		<c:if test="${not empty errorMsg}">
		<div class="errormessage">
		<ul>
		<li>${errorMsg}</li>
		</ul>
		</div>
		</c:if>




	 
	 </div>
 






</body>


</html>


