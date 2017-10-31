<%@ page language="java" contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>


<html>
<script type='text/javascript' src="${pageContext.request.contextPath}/resources/js/jquery-1.6.4.min.js"/></script>
<script type='text/javascript' src="${pageContext.request.contextPath}/resources/js/jquery-ui.js"/></script>
<script type='text/javascript' src="${pageContext.request.contextPath}/resources/js/noty/jquery.noty.js"/></script>
<script type='text/javascript' src="${pageContext.request.contextPath}/resources/js/noty/layouts/top.js"/></script>
<script type='text/javascript' src="${pageContext.request.contextPath}/resources/js/noty/themes/default.js"/></script>
<script type='text/javascript' src="${pageContext.request.contextPath}/resources/validation/dist/jquery.validate.min.js"/></script>
<head>

<link rel="stylesheet" type="text/css" media="all" href="${pageContext.request.contextPath}/resources/css/styles.css"/>  
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">


<script>

function quitCompetition(){
	
	var idComp = ${equipo.competicion.idCompeticion};

	var userId = ${equipo.usuario.idUsuario};

	var adminId = ${equipo.competicion.admin.idUsuario};

	if (userId==adminId)
		alert("No está permitido expulsar al administrador");
	else{
		$.ajax({
	
			type:"post",
			url: "/admin/removeUser",	
			data: ({idCompeticion:idComp, idUsuario:userId }),
			dataType: "json",
			success: function(response){
				if (response.success){
					window.location.href = "/index";
				}
				else
					$('#errorMsg').empty().append("<font color='red'>"+response.message+"</font>");	

			},
			error: function(){
				$('#errorMsg').empty().append("<font color='red'>"+response.message+"</font>");	
			}
		});
	}
}
</script>
<script>
function changeTeamName(){

	var idComp = ${equipo.competicion.idCompeticion};
	
	var validator = $("#changeTeamNameForm").validate({ 
		
		rules: {
			teamName:  {
				required: true,
				minlength: 2,
				remote: {
					url: "/competition/checkTeamName",
					type: "get",
					data: {
						idCompeticion: idComp
					}
				}
			}

		},		
		errorPlacement:function(error, element) { 
			error.insertAfter("#submitButton");		
		},
		messages: { 
			teamName: {
				required: "<font color='red'> Complete el nombre del equipo.</font>",
				minlength: "<font color='red'> El nombre del equipo ha de tener al menos 2 caracteres.</font>",
				remote: "<font color='red'> Ya existe un equipo con ese nombre.</font>" 			
			}
		},
		submitHandler: function(form){
			var newTeamName = $('#teamNameInp').val();
		 	var idTeam = ${equipo.idEquipo};

			$.ajax({
	
				type:"post",
				url: "/competition/changeTeamName",	
				data: ({teamName:newTeamName, idEquipo: idTeam}),
				dataType: "json",
				success: function(response){
					if (response.success)
						generateNoty(""+response.message+"",'success');
				
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



function generateNoty(msg,type){

  	var n = noty({
  		text: msg,
  		type: type,
		closeWith: ['hover'],
		timeout: 1000
  	});

}
</script>


</head>


<body>

<div>
	<form id="changeTeamNameForm" class="pure-form pure-form-stacked" name="changeTeamNameForm" method="Post" action="">
		<p><label>Cambiar nombre al equipo: </label><input type="text" id="teamNameInp" name="teamNameInp" value="${equipo.nombre}">
		<input type="submit" class="pure-button pure-button-primary" id="submitButton" value= "Guardar" onclick="changeTeamName();"></p>
		<div id="errorMessage"></div>
		<c:if test= "${equipo.usuario.idUsuario != equipo.competicion.admin.idUsuario}"	>
			<label><font color=red>Atención, Abandonar competición no tendrá vuelta atrás</font></label>					
			<input type="button" class="pure-button pure-button-error" value="Abandonar Competición" onclick="quitCompetition();">


		</c:if>
	</form>

</div>








</body>




</html>
