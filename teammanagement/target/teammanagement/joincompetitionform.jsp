<%@ page language="java" contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<meta http-equiv="X-UA-Compatible" content="IE=edge"> 
<meta name="mobile-web-app-capable" content="yes" />
<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0, user-scalable=no">


<c:url value="/" var="homeUrl"/>

<html>
<%@ page import="es.ligasnba.app.util.constants.Constants" %>
<head>
<jsp:include page="menu.jsp" />
<link rel="stylesheet" type="text/css" media="all" href="<c:url value="/resources/css/styles.css" context="/teammanagement"/>">
<link rel="stylesheet" type="text/css" media="all" href="<c:url value="/resources/css/pure-min.css" context="/teammanagement"/>">
<script type='text/javascript' src='<c:url value="/resources/js/jquery-3.1.0.min.js"/>'></script>
<script type='text/javascript' src='<c:url value="/resources/js/jquery-ui.js"/>'></script>
<script type='text/javascript' src='<c:url value="/resources/validation/dist/jquery.validate.min.js"/>'></script>

<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">

<script>
var idCompeticionJoin = 0;
function JoinCompetition(){


	var validator = $("#competitionjoin-form").validate({ 
		rules: { 
		    competitionName: {
			required: true,
			minlength: 2
		    },
		    password: {
			required: true,
			minlength: 5,
			maxlength: 20
		    }
		},
			errorElement: "span" , 
		messages: { 
			competitionName: {
				required: "<font color='red'>· Complete el nombre de la competición</font>",
				minlength: "<font color='red'>· El nombre de la competición ha de tener al menos 2 caracteres</font>",
				remote: "<font color='red'>· Ya existe una competición con ese nombre</font>"
			},
			password: {
				required: "<font color='red'>· Complete la contraseña de la competición</font>",
				minlength: "<font color='red'>· La contraseña de la competición ha de tener al menos 5 caracteres</font>",
				maxlength: "<font color='red'>· La contraseña de la competición no puede tener más de 20 caracteres</font>"
			}
		},
		submitHandler: function(form){
			var compName= $('#competitionName').val();
			var pass = $('#password').val();
			var num = $('#numTeams').val();
			$('#joinButton').prop('disabled',true);
			$.ajax({
	
				type:"post",
				url: "/joincompetitionform/join",	
				data: ({nombreCompeticion:compName,password:pass}),
				dataType: "json",
				success: function(response){

					if (response.success){
						idCompeticionJoin = response.idCompeticion;
						$("#selectteam").removeClass("hidden");
						$("#selectteam").addClass("visible");
						LoadTeamOptions(response);		
						$('#competitionName').prop('disabled',true);
						$('#password').prop('disabled',true);
						
					}

					else {

						$('#joinButton').prop('disabled',false);
						$('#errorMessages').empty().append("<font color='red'>* "+response.message+"</font>");

					}


				},
				error: function(){

					alert("error");
				}
			});
		} 
	}); 

	if(validator.form()){ 


	}

}

function LoadTeamOptions(response){
	
	for (var i in response.listAvailableTeams){


		$('#availableteams').append(new Option(response.listAvailableTeams[i].nombre, response.listAvailableTeams[i].idEquipo, true, true));
	
	}

}

function SelectTeam(){

	var teamId = $( "#availableteams").val();
	$('#joinButton').prop('disabled',true);
	$('#selectTeamButton').prop('disabled',true);
	
	$.ajax({

		type:"post",
		url: "/joincompetitionform/selectteamid",	
		data: ({idEquipo:teamId, idCompeticion:idCompeticionJoin}),
		dataType: "json",
		success: function(response){
			window.location.href = "/index";
		},
		error: function(){
			$('#joinButton').prop('false',false);
			$('#selectTeamButton').prop('disabled',false);
			alert("error");
		}
	});
	
	
}

</script>


<title>Crear una competición</title>

</head>

<body>

<form class="pure-form pure-form-stacked" name="competitionjoin-form" id="competitionjoin-form" method="Post" >

<fieldset>
<legend>Unirse a una Competición</legend>

<div><label for="competitionName">Nombre de la Competición </label><input type="text" id="competitionName" name="competitionName" /></div>

<div><label for="password">Contraseña </label><input margin=50 type="password" id="password" name="password" /></div>

<p><input class="pure-button pure-button-primary" type="submit" id="joinButton" onclick="JoinCompetition();" value="Unirse" /></p>

<div class="hidden" id="selectteam"> <label for="availableteams"> Selecciona equipo </label><select id="availableteams"></select> <p><input type="button" class="pure-button pure-button-primary" id="selectTeamButton" onclick="SelectTeam();" value="Seleccionar" /></div></p>

</fieldset>

<div id="errorMessages"></div>
</form>

</center>

</body>

</html>

