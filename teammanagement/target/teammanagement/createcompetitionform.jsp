<%@ page language="java" contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<c:url value="/" var="homeUrl"/>

<html>
<%@ page import="es.ligasnba.app.util.constants.Constants" %>
<head>

<link rel="stylesheet" type="text/css" media="all" href="${pageContext.request.contextPath}/resources/css/styles.css"/>
<link rel="stylesheet" type="text/css" media="all" href="${pageContext.request.contextPath}/resources/css/pure-min.css"/>
<link rel="stylesheet" type="text/css" media="all" href="${pageContext.request.contextPath}/resources/css/bootstrap.min.css"/>
<link rel="stylesheet" type="text/css" media="screen" href="${pageContext.request.contextPath}/resources/css/cupertino/jquery-ui-1.10.3.custom.css"/>
<link rel="stylesheet" type="text/css" media="screen" href="${pageContext.request.contextPath}/resources/css/bootstrap-datetimepicker.min.css"/>

<script type='text/javascript' src="${pageContext.request.contextPath}/resources/js/jquery-3.1.0.min.js"/></script>
<script type='text/javascript' src="${pageContext.request.contextPath}/resources/js/jquery-ui.js"/></script>
<script type='text/javascript' src="${pageContext.request.contextPath}/resources/validation/dist/jquery.validate.min.js"/></script>
<script type='text/javascript' src="${pageContext.request.contextPath}/resources/js/bootstrap-datetimepicker.min.js"/></script>
<script type='text/javascript' src="${pageContext.request.contextPath}/resources/js/bootstrap-datetimepicker.es.js"/></script>  
<script type='text/javascript' src="${pageContext.request.contextPath}/resources/js/jquery.number.min.js"/></script>

<jsp:include page="menu.jsp" />
<script>
var $j = jQuery.noConflict();
var idCompeticionCreada = 0;
function CreateCompetition(){
	
	var validator = $("#competitioncreate-form").validate({ 
		rules: { 
		    competitionName: {
			required: true,
			minlength: 2,
			remote: {
				url: "/createcompetitionform/competitionnamecheck",
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
			var sDate = $( "#datepicker" ).datetimepicker("getDate");
			var fDate = $( "#datepicker2" ).datetimepicker("getDate");
			var salCap = $( "#salaryCap" ).slider( "value" );
			var salTop = $( "#salaryTop" ).slider( "value" );


			$('#createButton').prop('disabled',true);
			

			$.ajax({
	
				type:"post",
				url: "/createcompetitionform/create",	
				data: ({nombreCompeticion:compName,password:pass,numTeams:num,startDate:sDate,finishDate:fDate, salaryCap:salCap, salaryTop:salTop}),
				dataType: "json",
				success: function(response){

					if (response.success){
						
						idCompeticionCreada = response.idCompeticion;
						LoadTeamOptions(response);		
						$('#competitionName').prop('disabled',true);
						$('#password').prop('disabled',true);
						$('#password2').prop('disabled',true);
						$('#numTeams').prop('disabled',true);
						$('#datepicker').prop('disabled',true);
						$('#datepicker2').prop('disabled',true);
						$("#selectteam").removeClass("hidden");
						$("#selectteam").addClass("active");
						
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
	var idCompetition = idCompeticionCreada;	

	$.ajax({

		type:"post",
		url: "/joincompetitionform/selectteamid",	
		data: ({idEquipo:teamId, idCompeticion:idCompetition}),
		dataType: "json",
		success: function(response){
			window.location.href = "/index";
		},
		error: function(){
			alert("error");
		}
	});
	
	
}




</script>

<script>
$(function() {

	$( "#datepicker" ).datetimepicker({
		format: "d-m-yy",
		minDate: 0,
        language:  'es',
        weekStart: 1,
        todayBtn:  1,
		autoclose: 1,
		todayHighlight: 1,
		startView: 2,
		minView: 2,
		forceParse: 0
	});
	$( "#datepicker2" ).datetimepicker({
		format: "d-m-yy",
		minDate: 0,
        language:  'es',
        weekStart: 1,
        todayBtn:  1,
		autoclose: 1,
		todayHighlight: 1,
		startView: 2,
		minView: 2,
		forceParse: 0		
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

$(function () {
    var dateNow = new Date();
    $('#datepicker').datetimepicker({
        defaultDate:dateNow
    });
});
</script>


<title>Crear una nueva competición</title>

</head>

<body>
<div id="createcompetitionform-container">

	<form class="pure-form pure-form-stacked" name="competitioncreate-form" id="competitioncreate-form" method="Post" action="">

	<fieldset>
	<legend>Crear Nueva Competición</legend>

	<div><label for="competitionName">Nombre de la Competición </label><input type="text" id="competitionName" name="competitionName" /></div>


	<div><label for="password">Contraseña </label><input margin=50 type="password" id="password" name="password" value="12345"/></div>

	<div><label for="password2">Confirmar contraseña </label><input margin=50 type="password" id="password2" name="password2" value="12345"/></div>


	<div><label for="numTeams">Número de equipos </label><input type="number" id="numTeams" name="numTeams" value="30"/></div>

	<label for="datepicker">Fecha de comienzo de la temporada</label><input type="text" class="dateText" readonly id="datepicker" name="datepicker" />


	<label for="datepicker2">Fecha de fin de la temporada</label><input type="text" class="dateText" readonly id="datepicker2" name="datepicker2" />
	<label for="salaryCap">Límite salarial</label>

	<div class="sliderSalary"><table><tr><td><div id="salaryCap"  style="width:180; "></div></td><td><label id=salaryCapText></label></td></tr></table></div>
	<label for="salaryTop">Límite tope de salarios</label>
	<div class="sliderSalary"><table><tr><td><div id="salaryTop" style="width:180;"></div></td><td><label id=salaryTopText></label></td></tr></table></div>
	
	<div class="hidden" id="selectteam"> <label for="availableteams"> Selecciona equipo </label><select id="availableteams"></select> <input type="button" class="pure-button pure-button-primary"onclick="SelectTeam();" value="Seleccionar" /></div>	
	<input type="submit" class="pure-button pure-button-primary" id="createButton" onclick="CreateCompetition();" value="Crear"/>


	<div id="errorMessages"></div>
	</form>


	</fieldset>

</div>

</center>

</body>

</html>

