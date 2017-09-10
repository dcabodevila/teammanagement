<%@ page language="java" contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>


<html>


<head>

<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<script type='text/javascript' src='<c:url value="/resources/js/jquery-3.1.0.min.js"/>'></script>
<script type='text/javascript' src='<c:url value="/resources/jquery_news_ticker/includes/jquery.ticker.js"/>'></script>
<link rel="stylesheet" type="text/css" media="screen" href="<c:url value="/resources/jquery_news_ticker/styles/ticker-style.css" context="/teammanagement"/>">  

<link rel="stylesheet" type="text/css" media="all" href="<c:url value="/resources/css/styles.css" context="/teammanagement"/>">  
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">



<title>${competitionForm.competicion.nombre}</title>

<script>

function seasonsSelectChange(){

	var selectedIdSeason = $( "#seasonsSelect").val();

	$.ajax({

		type:"get",
		url: "/teammanagement/competition/getClassification/",	
		data: ({idTemporada:selectedIdSeason}),
		dataType: "json",
		success: function(response){
			loadClassification(response);
			
		},
		error: function(){
			generateNoty("Error en la llamada al servidor",'error');
		}
	});

}

function loadClassification(response){

	$('#classification tbody').remove();

	$("#classification").append("<tbody>");

	var count =1;			    
	
	for (var i in response){

		var clasificacion=response[i];

		
		$("#classification").append('<tr><td>'+count+'</td><td><div align=left class="teamLogoName"><ul><li><img src=<c:url value="/resources/images/'+clasificacion.image+'"/> height= "32" width="40"/></li><li>'+ clasificacion.nombreEquipo+'</li></ul></div></td><td width=120>'+clasificacion.nombreUsuario+'</td><td>'+clasificacion.victorias+'</td><td>'+clasificacion.derrotas+'</td></tr>');

		count = count+1;
	}
	$("#classification").append("</tbody>");
}

</script>

</head>

<c:url value="/team" var="TeamUrl"/>

<body>
<div id="news-container">
	<c:if test="${not empty competitionForm.listaNoticias}">
		<ul id="js-news" class="js-hidden">
			<c:forEach items="${competitionForm.listaNoticias}" var="noticia" varStatus="status">
			    <li class="news-item"><a href="#"><fmt:formatDate pattern="dd-MM-yy" value="${noticia.fecha}"/> - ${noticia.texto}</a></li>
			</c:forEach>
		</ul>

	</c:if>

</div>
	<div class="teamData">

		<sec:authorize access="isAuthenticated()">
		    <div id="teamLogoName">
			    <c:if test="${not empty competitionForm.equipo}">
				    <div id="logo"><img src="<c:url value='/resources/images/${competitionForm.equipo.logo}'/>" /></div><div id="teamName">${competitionForm.equipo.nombre}</div>
			    </c:if>
		    </div>		
		    <div>${competitionForm.competicion.nombre}</div>
		    <div><fmt:formatDate pattern="dd-MM-yy" value="${competitionForm.competicion.actualDate}" /><c:if test='${competitionForm.competicion.paused}'> [PAUSADA]</c:if></div>



	</div>
		
	
		<div id=seasons> 

			<form class="pure-form">
				<select id="seasonsSelect"  onchange="seasonsSelectChange();">
					<c:forEach items="${competitionForm.competicion.listaTemporadas}" var="temporada">
					    <c:choose>	
						    <c:when test="${temporada.idTemporada == competitionForm.competicion.idTemporadaActual}">
							<option selected value="${temporada.idTemporada}">${temporada.nombre}</option>				    
						    </c:when>
						    <c:when test= "${temporada.idTemporada < competitionForm.competicion.idTemporadaActual}">
							    <option value="${temporada.idTemporada}">${temporada.nombre}</option>				
						    </c:when>
					    </c:choose>

					</c:forEach>
				</select> 
			</form>
		<div>
		
	<div class="competitionPanelCenter">




		<div class="standings">

			<table class="datatable" id="classification">
			<caption>Clasificación</caption>
			    <thead>
				<th>Puesto</th>
				<th>Equipo</th>
				<th>Usuario</th>
				<th>V</th>
				<th>D</th>
			    </thead>
			    <c:forEach items="${competitionForm.listaClasificaciones}" var="clasificacion" varStatus="status">
				<tr>
				    <td align="center">${status.count}</td>
				    <td><div align=left class="teamLogoName"><ul>
					<li><img src="<c:url value='/resources/images/${clasificacion.equipo.logo}'/>" height= "32" width="40"/></li><li>${clasificacion.equipo.nombre}</li></ul></div></td>
				    <td width=120>${clasificacion.equipo.usuario.login}</td>
				    <td >${clasificacion.victorias}</td>
				    <td>${clasificacion.derrotas}</td>

				</tr>
			    </c:forEach>
			</table>
		</div>

   	<div class="competitionPanelRight">

			<c:if test="${not empty competitionForm.listaUltimosPartidos}">
				<table class="datatable">
				    <caption>Últimos partidos</caption>
				    <thead>        
					<th>Local</th>
					<th></th>
					<th>Visitante</th>
				    </thead>


				    <c:forEach items="${competitionForm.listaUltimosPartidos}" var="partido" varStatus="status">
					<tr>
					    <td width="70"><img src="<c:url value='/resources/images/${partido.equipoLocal.logo}'/>" height= "32" width="40"/></td>
					    <td>${partido.actaPartido.resultadoLocal} - ${partido.actaPartido.resultadoVisitante}</td>
					    <td width="70"><img src="<c:url value='/resources/images/${partido.equipoVisitante.logo}'/>" height= "32" width="40"/></td>
					</tr>
				    </c:forEach>
				</table>
			</c:if>
	</div>

	</div>





	
	</sec:authorize>

	<c:if test="${not empty errormessage}">
	<div class="errormessage">
	<ul>
	<li>${errormessage}</li>
	</ul>
	</div>
	</c:if>





</body>




</html>

