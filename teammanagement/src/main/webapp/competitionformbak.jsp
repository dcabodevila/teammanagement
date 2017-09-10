<%@ page language="java" contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>


<html>


<head>

<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<link rel="stylesheet" type="text/css" media="all" href="<c:url value="/resources/css/bootstrap.min.css" context="/teammanagement"/>">  
<script type='text/javascript' src='<c:url value="/resources/js/jquery-1.6.4.min.js"/>'></script>
<script type='text/javascript' src='<c:url value="/resources/js/jquery-ui.js"/>'></script>
<script type='text/javascript' src='<c:url value="/resources/js/noty/jquery.noty.js"/>'></script>
<script type='text/javascript' src='<c:url value="/resources/js/noty/layouts/bottom.js"/>'></script>
<script type='text/javascript' src='<c:url value="/resources/js/noty/themes/default.js"/>'></script>
<script type='text/javascript' src='<c:url value="/resources/js/noty/layouts/top.js"/>'></script>


<script>

$( document ).ready(function() {
	var isActivated = ${isActivated};
	if( !isActivated )
		generateNoty("La cuenta no ha sido activada. Por favor, compruebe su email.",'error');

		
});

function generateNoty(msg,type){

  	var n = noty({
		layout: 'bottom',
  		text: msg,
		closeWith: ['button'],
  		type: type,
		buttons: [    
			{addClass: 'btn btn-primary', text: 'He perdido mi correo de activación', onClick: function($noty) {			
			$noty.close();
			resendActivationMail();
			
			
		      }
		    }
		]
  	});

}

function resendActivationMail(){

	$.ajax({

		type:"post",
		url: "/teammanagement/activate/resend",	
		dataType: "json",
		success: function(response){
			
			if (response.success){
				
				noty({layout: 'bottom',text: "Correo reenviado correctamente" ,closeWith: ['hover'], timeout: 1000 ,type: 'success'});
			}
			else
				noty({layout: 'bottom',text: "No se ha podido reenviar el correo" ,closeWith: ['hover'], timeout: 1000 ,type: 'error'});

		},
		error: function(){
			noty({layout: 'bottom',text: "Error en la llamada al servidor" ,closeWith: ['hover'], timeout: 1000 ,type: 'error'});
		}
	});

}


$(function() {
	$( "#tabs" ).tabs({
		beforeLoad: function( event, ui ) {
			ui.jqXHR.error(function() {
				ui.panel.html(
					"No se ha podido cargar la pestaña. Volveremos lo antes posible :)");
				});
		}
	});

$("#tabs").tabs().css({
   'min-height': '400px',
   'overflow': 'auto'
});
});


</script>

<script>
$(function() {
	$( "#markettabs" ).tabs({
		beforeLoad: function( event, ui ) {
			ui.jqXHR.error(function() {
				ui.panel.html(
					"No se ha podido cargar la pestaña. Volveremos lo antes posible :)");
				});
		}
	});

$("#markettabs").tabs().css({
   'min-height': '400px',
   'overflow': 'auto'
});
});

</script>

<script>
$(function() {
	$( "#admintabs" ).tabs({
	        
		beforeLoad: function( event, ui ) {
			ui.jqXHR.error(function() {
				ui.panel.html(
					"No se ha podido cargar la pestaña. Volveremos lo antes posible :)");
				});
		}
	});

$("#admintabs").tabs().css({
   'min-height': '400px',
   'overflow': 'auto'
});
});
</script>




<link rel="stylesheet" type="text/css" media="screen" href="<c:url value="/resources/css/cupertino/jquery-ui-1.10.3.custom.css" context="/teammanagement"/>">  
<link rel="stylesheet" type="text/css" media="all" href="<c:url value="/resources/css/styles.css" context="/teammanagement"/>">  
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">


<title>${competitionForm.equipo.competicion.nombre}</title>

</head>

<c:url value="/team" var="TeamUrl"/>

<body>

<c:url value="/" var="homeUrl"/>
<c:url value="/admin/${competitionForm.equipo.competicion.idCompeticion}/deleteCompetition" var="deleteCompetitionUrl"/>

<jsp:include page="menu.jsp" />

<div id="tabs" class="MainContainer">
	<ul>

	<li><a href="/teammanagement/competition/${competitionForm.equipo.competicion.idCompeticion}/main">Competición</a></li>
	<li><a href="/teammanagement/team/${competitionForm.equipo.idEquipo}">Rotación</a></li>
	<li><a href="/teammanagement/games/${competitionForm.equipo.idEquipo}">Partidos</a></li>
	<li><a href="#marketform">Mercado</a></li>
	<li><a href="/teammanagement/contracts/${competitionForm.equipo.idEquipo}">Contratos</a></li>
	<li><a href="/teammanagement/competition/${competitionForm.equipo.idEquipo}/configuration">Ajustes</a></li>
	<c:if test="${isAdmin}">
	<li><a href="#adminform">Comisionado</a></li>
	</c:if>
	</ul>



<div id="marketform">
	<div id="markettabs">
		<ul>
			<li><a href="/teammanagement/market/${competitionForm.equipo.idEquipo}/received">Traspasos Recibidos</a></li>
			<li><a href="/teammanagement/market/${competitionForm.equipo.idEquipo}/trades">Ofrecer traspaso</a></li>
			<li><a href="/teammanagement/market/${competitionForm.equipo.idEquipo}/offered">Traspasos Ofrecidos</a></li>
			<li><a href="/teammanagement/market/freeagents/${competitionForm.equipo.competicion.idCompeticion}">Agentes Libres</a></li>
		</ul>
	</div>
</div>

<c:if test="${isAdmin}">
	<div id="adminform">
		<div id="admintabs">
			<ul>
			<li><a href="/teammanagement/admin/${competitionForm.equipo.competicion.idCompeticion}/calendar">Calendario</a></li>
			<li><a href="/teammanagement/admin/${competitionForm.equipo.competicion.idCompeticion}/users">Usuarios</a></li>
			<li><a href="/teammanagement/admin/${competitionForm.equipo.competicion.idCompeticion}/games">Partidos</a></li>
			<li><a href="#admintabs-2">Configuración Competición</a></li>
			</ul>


			<div id="admintabs-2">


				<form class="pure-form pure-form-stacked">
					<p><label> <font color=red>Atención, esto no tendrá vuelta atrás</font><label></p>
					<p><a class="pure-button" href="${deleteCompetitionUrl}">Eliminar Competición</a></p>
				</form>
			</div>


		</div>
	</div>
</c:if>	

</div>






</body>



</html>

