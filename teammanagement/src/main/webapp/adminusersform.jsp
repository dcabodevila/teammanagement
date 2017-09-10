<%@ page language="java" contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>



<html>
<%@ page import="es.ligasnba.app.util.constants.Constants" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<script type='text/javascript' src='<c:url value="/resources/js/jquery-1.6.4.min.js"/>'></script>
<script type='text/javascript' src='<c:url value="/resources/js/jquery-ui.js"/>'></script>
<script type='text/javascript' src='<c:url value="/resources/validation/dist/jquery.validate.min.js"/>'></script>

<script type='text/javascript' src='<c:url value="/resources/js/noty/jquery.noty.js"/>'></script>
<script type='text/javascript' src='<c:url value="/resources/js/noty/layouts/top.js"/>'></script>
<script type='text/javascript' src='<c:url value="/resources/js/noty/themes/default.js"/>'></script>

<head>
<link rel="stylesheet" type="text/css" media="screen" href="<c:url value="/resources/css/cupertino/jquery-ui-1.10.3.custom.css" context="/teammanagement"/>">  
<link rel="stylesheet" type="text/css" media="all" href="<c:url value="/resources/css/styles.css" context="/teammanagement"/>">


<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">

<script>


$( document ).ready(function() {

	jQuery.validator.addMethod("maxTeams", function(value,  element) {
		var numTeams = $('#userTable tr').length-1;
		var maxTeamsConstant = <%= Constants.cMaxTeamsInCompetition %>;
		return (numTeams< maxTeamsConstant);
	}, "Ya se ha alcanzado el número máximo de equipos");


	getUsersList(${adminCompetitionForm.competition.idCompeticion});
});

function getUsersList(idCompeticion){


	$.ajax({
	
		type:"get",
		url: "/teammanagement/admin/userslist",	
		data: ({idCompeticion:idCompeticion }),
		dataType: "json",
		success: function(data){

			loadUsers(data);

		},
		error: function(){
			generateNoty("Error en la llamada al servidor",'error');
		}
	});

}

function loadUsers(data){
	
	$('#userTable tbody').remove();
	$('#removeUser').empty();
	$('#removedTeam').empty();
	$('#removedTeamMsg').empty();




	$("#userTable").append("<tbody>");


	
	for (var i in data){

		var user=data[i];

		$("#userTable").append("<tr><td>"+user.nombreEquipo+"</td><td>"+user.nombreUsuario+"</td></tr>");
		$('#removedTeam').append(new Option(user.nombreEquipo, user.idEquipo, true, true));
		if (user.idUsuario!=0)
			$('#removeUser').append(new Option(user.nombreUsuario, user.idUsuario, true, true));


	
	}
	$("#userTable").append("</tbody>");

}


function removeUser(){

	var idComp = ${adminCompetitionForm.competition.idCompeticion};

	var userId = $( "#removeUser").val();

	var adminId = ${adminCompetitionForm.competition.admin.idUsuario};

	if (userId==adminId)
		generateNoty("No está permitido expulsar al administrador",'alert');
	else{
		$.ajax({
	
			type:"post",
			url: "/teammanagement/admin/removeUser",	
			data: ({idCompeticion:idComp, idUsuario:userId }),
			dataType: "json",
			success: function(response){
				if (response.success){
					generateNoty(""+response.message+"",'success');
					getUsersList(idComp);
				}
				else
					generateNoty(""+response.message+"",'error');

			},
			error: function(){
				generateNoty("Error en la llamada al servidor",'error');
			}
		});
	}

}


function addTeam(){

	var idComp = ${adminCompetitionForm.competition.idCompeticion};	



	var validator = $("#addteam-form").validate({ 
		onkeyup: false,
		onclick: true,
		rules:{
			newTeamName: {
				required: true,
				maxTeams: true
			}
			
		},
		errorElement: "addTeamMsg" ,
		messages:{
			newTeamName: {
				required: "<font color='red'>· Complete el nombre del nuevo equipo</font>",
				maxTeams: "<font color='red'>· No se pueden agregar más equipos, el máximo es "+<%= Constants.cMaxTeamsInCompetition %>+"</font>"

			}
		},
		submitHandler:function(form){
			var idTeam = $( "#newTeamName" ).val();
			$.ajax({
	
				type:"post",
				url: "/teammanagement/admin/addTeam",	
				data: ({idCompeticion:idComp, nombreEquipo:idTeam }),
				dataType: "json",
				success: function(response){
					if (response.success){
						generateNoty(""+response.message+"",'success');
						$('#newTeamName').val("");
						getUsersList(idComp);
					}
					else
						generateNoty(""+response.message+"",'error');

				},
				error: function(){
					generateNoty("Error en la llamada al servidor",'error');
				}
			});			
		}

	

	});

	if(validator.form()){ 

	}

}

function removeTeam(){


	var rowCount = $('#userTable tr').length-1;

	if (rowCount= (<%= Constants.cMinTeamsInCompetition %>) )
		generateNoty("No se pueden eliminar más equipos",'alert');

	else{
		var idComp = ${adminCompetitionForm.competition.idCompeticion};	
		var teamId = $( "#removedTeam").val();
		$.ajax({
	
			type:"post",
			url: "/teammanagement/admin/removeTeam",	
			data: ({idCompeticion:idComp, idEquipo:teamId }),
			dataType: "json",
			success: function(response){
				if (response.success){
					generateNoty(""+response.message+"",'success');
					$('#addTeamMsg').empty();
					$('#removeUserMsg').empty();
					$('#newTeamName').val("");
					getUsersList(idComp);
				}
				else
					generateNoty(""+response.message+"",'error');

			},
			error: function(){
					generateNoty("Error en la llamada al servidor",'error');
			}
		});		
	}

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

<title>Admin</title>

</head>


<body>

<div id="divLeft" class="adminUsersTableDiv">

	<table class="datatable" id="userTable">
	<caption>Usuarios </caption>
	    <thead>
		<th>Equipo</th>
		<th>Usuario</th>
	    </thead>
	</table>
</div>

<div id="divMain"class="adminUsersOptionsDiv">




<div class="adminUsersFunctions">
	<form name="addteam-form" class="pure-form" name="addteam-form" id="addteam-form" method="get" action="">	
		<fieldset>
			<legend>Añadir equipo a la competición</legend>

				<p><label for="newTeamName">Nombre del equipo: </label><input id="newTeamName" name="newTeamName" type="text"/></p>
				<p><input type="submit" class="pure-button" onclick="addTeam();" value="Añadir" /></p>

		</fieldset>
	</form>

</div>
<div class="adminUsersFunctions">
	<form class="pure-form">
	<fieldset>
	<legend>Expulsar Equipo</legend>

		<p><label for="removedTeam">Selecciona equipo: </label><select id="removedTeam"></select></p>
		<p><input type="button" class="pure-button" onclick="removeTeam();" value="Expulsar" /></p>

	</fieldset>
	</form>

</div>
<div class="adminUsersFunctions">
	<form class="pure-form">
	<fieldset>
		<legend>Expulsar usuario</legend>
		<p><label for=removeUser>Seleccione el Usuario a expulsar de la competición: </label><select id="removeUser" ></select></p>

	</fieldset>
	</form>
		<p><input type="button" class="pure-button" onclick="removeUser();" value="Expulsar" /></p>	


	

</div>

</body>



</html>
