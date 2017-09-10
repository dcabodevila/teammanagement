<%@ page language="java" contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<c:url value="/" var="homeUrl"/>

<html>
<%@ page import="es.ligasnba.app.util.constants.Constants" %>
<head>
	<link rel="stylesheet" type="text/css" media="all" href="<c:url value="/resources/css/styles.css" context="/teammanagement"/>">
	<link rel="stylesheet" type="text/css" media="screen" href="<c:url value="/resources/css/cupertino/jquery-ui-1.10.3.custom.css" context="/teammanagement"/>">  
	<script type='text/javascript' src='<c:url value="/resources/js/jquery-1.10.2.js"/>'></script>
	<script type='text/javascript' src='<c:url value="/resources/js/jquery-ui.js"/>'></script>
	<script type='text/javascript' src='<c:url value="/resources/js/jquery.number.min.js"/>'></script>
	<script type='text/javascript' src='<c:url value="/resources/validation/dist/jquery.validate.min.js"/>'></script>
 	<link rel="stylesheet" type="text/css" media="all" href="<c:url value="/resources/css/pure-min.css" context="/teammanagement"/>">

	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">

<script>

function CreateCompetition(){

	var validator = $("#competitioncreate-form").validate({ 
		rules: { 
		    competitionName: {
			required: true,
			minlength: 2,
			remote: {
				url: "/teammanagement/createcompetitionform/competitionnamecheck",
				type: "get",
				data: {
					nombreCompeticion: function() {
						return $( "#competitionName" ).val();
					}
				}
			}
		    },
		    password: {
			required: true,
			minlength: 5,
			maxlength: 20
		    },
		    password2: {
			required:true,
			equalTo:"#password"
		    },
		    numTeams: {
			required:true,
			min: <%= Constants.cMinTeamsInCompetition %>,
			max: <%= Constants.cMaxTeamsInCompetition %>
			},
		    datepicker: {
			required:true
			},
		    datepicker2: {
			required:true
			},

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
			},
			password2: {
				required: "<font color='red'>· Confirme la contraseña</font>",
				equalTo: "<font color='red'>· La contraseña y su confirmación no coinciden</font>"
			},
			numTeams: {
				required:"<font color='red'>· El número de equipos no puede estar vacío</font>",
				min: "<font color='red'>· El número de equipos mínimo es "+<%= Constants.cMinTeamsInCompetition %>+"</font>",
				max: "<font color='red'>· El número de equipos máximo es "+<%= Constants.cMaxTeamsInCompetition %>+"</font>"
			},
			datepicker:{
				required: "<font color='red'>· Se debe establecer una fecha para el comienzo de la temporada</font>",
			},
			datepicker2:{
				required: "<font color='red'>· Se debe establecer una fecha para el final de la temporada</font>",
			}

		},
		submitHandler: function(form){
			var compName= $('#competitionName').val();
			var pass = $('#password').val();
			var num = $('#numTeams').val();
			var sDate = $( "#datepicker" ).datepicker("getDate");
			var fDate = $( "#datepicker2" ).datepicker("getDate");
			var salCap = $( "#salaryCap" ).slider( "value" );
			var salTop = $( "#salaryTop" ).slider( "value" );


			$('#createButton').prop('disabled',true);


			$.ajax({
	
				type:"post",
				url: "/teammanagement/createcompetitionform/create",	
				data: ({nombreCompeticion:compName,password:pass,numTeams:num,startDate:sDate,finishDate:fDate, salaryCap:salCap, salaryTop:salTop}),
				dataType: "json",
				success: function(response){
					if (response.success){
					
						LoadTeamOptions(response);		
						$('#competitionName').prop('disabled',true);
						$('#password').prop('disabled',true);
						$('#password2').prop('disabled',true);
						$('#numTeams').prop('disabled',true);
						$('#datepicker').prop('disabled',true);
						$('#datepicker2').prop('disabled',true);

						$("#selectteam").show("fast");

						
					}

					else {
						$('#errorMessages').append("<font color='red'>* "+response.message+"</font>");

					}


				},
				error: function(){
					$('#createButton').prop('disabled',false);
					$('#errorMessage').empty().append("<font color='red'>Ha habido un error al crear la competición</font>")

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

	$.ajax({

		type:"post",
		url: "/teammanagement/joincompetitionform/selectteamid",	
		data: ({idEquipo:teamId}),
		dataType: "json",
		success: function(response){
			window.location.href = "/teammanagement/index";
		},
		error: function(){
			alert("error");
		}
	});
	
	
}




</script>

<script>
$(function() {

	

	$( "#datepicker" ).datepicker({
		dateFormat: "dd-mm-yy",
		minDate: 0,
		onClose: function( selectedDate ) {
		$( "#datepicker2" ).datepicker( "option", "minDate", selectedDate );
		}
	});

	$( "#datepicker2" ).datepicker({
		dateFormat: "dd-mm-yy",
		minDate: 0
	});

	$( "#salaryCap" ).slider({
		min: 20000000,
		max: 80000000,
		value: <%= Constants.cDefaultSalaryCap %>,
		slide:function(event, ui){
			var salaryTop = $( "#salaryTop" ).slider( "value" );
			$( "#salaryCapText" ).text($.number( ui.value, 2 )+"$");
			
			if (ui.value>salaryTop ){
				$( "#salaryTop" ).slider( "option", "value",ui.value);
				$( "#salaryTopText" ).text($.number( ui.value, 2 )+"$");	
			}
			
		}

	});
	$( "#salaryCapText" ).text( $.number( $( "#salaryCap" ).slider( "value" ), 2 )+"$" );

	
	$( "#salaryTop" ).slider({
		min: 20000000,
		max: 120000000,
		value: <%= Constants.cDefaultSalaryTop %>,
		slide:function(event, ui){
			var salaryCap = $( "#salaryCap" ).slider( "value" );
			$( "#salaryTopText" ).text($.number( ui.value, 2 )+"$");
				if (ui.value<salaryCap ){
				$( "#salaryCap" ).slider( "option", "value",ui.value);
				$( "#salaryCapText" ).text($.number( ui.value, 2 )+"$");	
			}
		}	
	});

					

	$( "#salaryTopText" ).text( $.number( $( "#salaryTop" ).slider( "value" ), 2 )+"$"  );


});
</script>


<title>Crear una nueva competición</title>

</head>

<body>
<div class="pure-menu pure-menu-open pure-menu-horizontal">
<ul>
<li><a href="${homeUrl}">Volver</a></li>
</ul>
</div>
<div id="createcompetitionform-container">

	<form class="pure-form pure-form-stacked" name="competitioncreate-form" id="competitioncreate-form" method="Post" action="">

	<fieldset>
	<legend>Crear Nueva Competición</legend>

	<div><label for="competitionName">Nombre de la Competición </label><input type="text" id="competitionName" name="competitionName" /></div>


	<div><label for="password">Contraseña </label><input margin=50 type="password" id="password" name="password" /></div>

	<div><label for="password2">Confirmar contraseña </label><input margin=50 type="password" id="password2" name="password2" /></div>


	<div><label for="numTeams">Número de equipos </label><input type="number" id="numTeams" name="numTeams" /></div>

	<label for="datepicker">Fecha de comienzo de la temporada</label><input type="text" class="dateText" readonly id="datepicker" name="datepicker" />

	<label for="datepicker2">Fecha de fin de la temporada</label><input type="text" class="dateText" readonly id="datepicker2" name="datepicker2" />
	<label for="salaryCap">Límite salarial</label>

	<div class="sliderSalary"><table><tr><td><div id="salaryCap"  style="width:180; "></div></td><td><label id=salaryCapText></label></td></tr></table></div>
	<label for="salaryTop">Límite tope de salarios</label>
	<div class="sliderSalary"><table><tr><td><div id="salaryTop" style="width:180;"></div></td><td><label id=salaryTopText></label></td></tr></table></div>

	<div class="starthidden" id="selectteam"> <label for="availableteams"> Selecciona equipo </label><select id="availableteams"></select> <input type="button" class="pure-button pure-button-primary"onclick="SelectTeam();" value="Seleccionar" /></div>



	<input type="submit" class="pure-button pure-button-primary" id="createButton" onclick="CreateCompetition();" value="Crear"/>


	<div id="errorMessages"></div>
	</form>


	</fieldset>

</div>

</center>

</body>

</html>

