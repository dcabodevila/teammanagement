<%@ page language="java" contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>



<html>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<script type='text/javascript' src='<c:url value="/resources/js/jquery-1.6.4.min.js"/>'></script>
<script type='text/javascript' src='<c:url value="/resources/js/jquery-ui.js"/>'></script>

<script type='text/javascript' src='<c:url value="/resources/js/noty/jquery.noty.js"/>'></script>
<script type='text/javascript' src='<c:url value="/resources/js/noty/layouts/top.js"/>'></script>
<script type='text/javascript' src='<c:url value="/resources/js/noty/themes/default.js"/>'></script>




<head>
<link rel="stylesheet" type="text/css" media="screen" href="<c:url value="/resources/css/cupertino/jquery-ui-1.10.3.custom.css" context="/teammanagement"/>">  
<link rel="stylesheet" type="text/css" media="all" href="<c:url value="/resources/css/styles.css" context="/teammanagement"/>">


<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">





<script type="text/javascript">

$( document ).ready(function() {

	
	$('#forwardButton').prop('disabled',false);
	var actualDate = getActualCompetitionDate();
	checkCompetitionState();
	
});

function checkCompetitionState(){

	var isPaused = "${adminCompetitionForm.competition.paused}"
	
	var stateNotStarted = "${adminCompetitionForm.competition.estado}"

	actualDateCheck();

	if (stateNotStarted=='true'){
		$('#playButton').prop('disabled',true);
		$('#pauseButton').prop('disabled',true);
		$('#forwardButton').prop('disabled',true);
	}
	else{
		if (isPaused=='true'){

			$('#playButton').prop('disabled',false);
			$('#pauseButton').prop('disabled',true);
		}
		else{

			$('#playButton').prop('disabled',true);
			$('#pauseButton').prop('disabled',false);
		}	



	}

}

function getActualCompetitionDate(){
	var actualCompetitionDate = $('#actualDate').val();
	return actualCompetitionDate;
}


function forwardCalendar(){

	var idComp = ${adminCompetitionForm.competition.idCompeticion};

	var nDays = $( "#forwardselect" ).val();

	$('#forwardButton').prop('disabled',true);
	
	$.ajax({
	
		type:"post",
		url: "/teammanagement/admin/forward",	
		data: ({idCompeticion:idComp , numDays: nDays }),
		dataType: "json",
		success: function(response){
			var newDate= getDateFromJSonDate(response.fecha);

			$('#forwardButton').prop('disabled',false);
			$('#actualDate').val( newDate );
			generateNoty(""+response.message+"",'success');

		},
		error: function(){

			$('#forwardButton').prop('disabled',false);
			generateNoty("Error al avanzar la competición",'error');

		}
	});

}

function actualDateCheck(){
	var fechaActual = $('#actualDate').val();
	var partsActual = fechaActual.split("-");	
	
	fechaActual = new Date(partsActual[2], partsActual[1] - 1, partsActual[0]);

	var fechaFin = $('#finishDate').val();
	var partsFin = fechaFin.split("-");
	fechaFin = new Date(partsFin[2], partsFin[1] - 1, partsFin[0]);
	
 
	if (fechaActual>fechaFin){

		$('#forwardButton').prop('disabled',true);
	}
	
}

function pauseCompetition(idCompeticion){

	$.ajax({
	
		type:"post",
		url: "/teammanagement/admin/pause/",	
		data: ({id:idCompeticion}),
		dataType: "json",
		success: function(response){
			$('#pauseButton').prop('disabled',true);
			$('#playButton').prop('disabled',false);
			generateNoty(""+response.message+"",'success');
		},
		error: function(){
			generateNoty("Error al detener la competición",'error');
		}
	});

}

function playCompetition(idCompeticion){

	$.ajax({
	
		type:"post",
		url: "/teammanagement/admin/play/",	
		data: ({id:idCompeticion}),
		dataType: "json",
		success: function(response){
			
			$('#pauseButton').prop('disabled',false);
			$('#playButton').prop('disabled',true);
			generateNoty(""+response.message+"",'success');
		},
		error: function(){
			generateNoty("Error al reanudar la competición",'error');
		}
	});

}


function getDateFromJSonDate(jsondate){
	var fecha="";	
	if (jsondate!=null){
	
		fecha= new Date(jsondate);
		fecha= fecha.getDate()+"/"+(fecha.getMonth()+1)+"/"+fecha.getFullYear();
		
	}
	return fecha;
}

function Draft(){
	var idComp = ${adminCompetitionForm.competition.idCompeticion};
	$('#draftButton').prop('disabled',true);
	$.ajax({
	
		type:"post",
		url: "/teammanagement/admin/draft",	
		data: ({idCompeticion:idComp}),
		dataType: "json",
		success: function(response){
			if (response.success){
				generateNoty(""+response.message+"",'success');
				$('#pauseButton').prop('disabled',false);
				$('#forwardButton').prop('disabled',false);
				$('#draftButton').hide();
				
			}
			else{
				generateNoty(""+response.message+"",'error');
			}

		},
		error: function(){
			generateNoty("Error en la llamada al servidor",'error');

		}
	});
	$('#forwardButton').prop('disabled',false);

}

function nextSeason(){

	var idComp = ${adminCompetitionForm.competition.idCompeticion};

	$.ajax({
	
		type:"post",
		url: "/teammanagement/admin/nextSeason",	
		data: ({idCompeticion:idComp}),
		dataType: "json",
		success: function(response){
			if (response.success){
				generateNoty(""+response.message+"",'success');
			}
			else{
				generateNoty(""+response.message+"",'error');
			}

		},
		error: function(){
			generateNoty("Error en la llamada al servidor"+response.message+"",'error');

		}
	});


}


function test(){

	var idComp = ${adminCompetitionForm.competition.idCompeticion};

	$.ajax({
	
		type:"get",
		url: "/teammanagement/admin/test",	
		data: ({idCompeticion:idComp}),
		dataType: "json",
		success: function(response){
			if (response.success){
				generateNoty(""+response.message+"",'success');
			}
			else{
				generateNoty(""+response.message+"",'error');
			}

		},
		error: function(){
			generateNoty("Error en la llamada al servidor"+response.message+"",'error');

		}
	});


}

function generateNoty(msg,type){

  	var n = noty({
  		text: msg,
  		type: type,
		closeWith: ['hover'],
		timeout: 1000
  	});

}

</script>

<title>${adminCompetitionForm.competition.nombre}</title>

</head>

<c:url value="team" var="TeamUrl"/>

<body>

<c:url value="/" var="homeUrl"/>
<c:url value="/admin/${adminCompetitionForm.competition.idCompeticion}/deleteCompetition" var="deleteCompetitionUrl"/>


<div>
	
	<c:set var="actualDate" value="" />
	<fmt:formatDate pattern="dd-MM-yyyy" value="${adminCompetitionForm.competition.actualDate}" var="actualDate" />

	<c:set var="finishDate" value="" />
	<fmt:formatDate pattern="dd-MM-yyyy" value="${adminCompetitionForm.competition.finishDate}" var="finishDate" />



	<div>
		<form class="pure-form">		
			<fieldset>
			<legend>Opciones de calendario</legend>


				<c:if test="${adminCompetitionForm.competition.estado==0}">
				<p><input type="button" class="pure-button" id="draftButton" onclick="Draft();" value="Realizar Draft" /></p>
				</c:if>
				<p>Fecha Actual: <input type="text" readonly id="actualDate" onchange="actualDateCheck();" value="${actualDate}" /></p>
				<p>Fecha Fin de temporada: <input type="text" readonly id="finishDate" value="${finishDate}" /></li>
				<p><input type="button" class="pure-button" id='pauseButton' onclick="pauseCompetition(${adminCompetitionForm.competition.idCompeticion});" value="Pausar Competición" />
				<input type="button" class="pure-button" id='playButton' onclick="playCompetition(${adminCompetitionForm.competition.idCompeticion});" value="Reanudar Competición" /></p>
				<p><label for="forwardselect"> Avanzar calendario </label><select id="forwardselect"><option value=1>1 día</option><option value=7>7 días</option><option value=30>30 días</option></select>			
				<input type="button" class="pure-button" id="forwardButton" onclick="forwardCalendar();" value="Avanzar" /></p>
				<input type="button" class="pure-button" id="nextSeasonButton" value="Avanzar a siguiente temporada" onclick="nextSeason();"><div id="nextSeasonMsg"></div>			
				<input type="button" class="pure-button"  onclick="test();" value="Test" /></p>
				</p>


			</fieldset>
		</form>

	</sec:authorize>
	</div>
	

</div>





	





</body>




</html>


