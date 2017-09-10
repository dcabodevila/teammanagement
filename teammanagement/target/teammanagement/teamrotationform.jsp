<%@ page language="java" contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<html>


<head>


<link rel="stylesheet" type="text/css" media="screen" href="<c:url value="/resources/css/cupertino/jquery-ui-1.10.3.custom.css" context="/teammanagement"/>"> 
<script type='text/javascript' src='<c:url value="/resources/js/jquery-1.6.4.min.js"/>'></script>
<script type='text/javascript' src='<c:url value="/resources/js/jquery-ui.js"/>'></script>
<link rel="stylesheet" type="text/css" media="all" href="<c:url value="/resources/css/styles.css" context="/teammanagement"/>">  
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">

<style>
#minutos span {
width:160px; float:left; margin:15px
}
.numero {
width: 25;

}
.id{width:25}

.minTotales{
width: 500;
}

.text{
width: 25;

}

.hidden{
visibility : hidden;

}

.rotationPanel{

height : auto;
}



</style>

<script>

$( document ).ready(function() {

	ajaxCall(${teamForm.equipo.idEquipo});
});

function ajaxCall(idTeam){

	$.ajax({
	
		type:"post",
		url: "/teammanagement/team/list",	
		data: ({ team: idTeam}),
		dataType: "json",
		success: function(data){
			
			LoadPlayers(data);
		},
		error: function(){
			alert("error");
		}
	});
}


function setTotalMinutes(){

	var total = getTotalMinutes();

	$("#minutosTotalText").val(total+"/240");
	$("#minTotalesPogressBar").progressbar("value",total);

}

function getTotalMinutes(){
	var total=0;
	$('.numero').each(function(idx, elm) { 

		var value = $('#' + elm.id).val();
		total += Number(value);
	});
	return total;	
	
}
function LoadPlayers(data){

	$("#players tbody").remove();
	$("#players").append("<tbody>");

	var minTotales=0;			

	for (var i in data){
		var player = data[i];

	
		minTotales= minTotales+ player.minutos;		


		var sliderName= "#minutos"+i;
		var idPlayerName= "#idJugador"+i;
		var sliderText= "#numero"+i;
		var sliderValue= player.minutos;
		
		$( idPlayerName ).val(player.idJugador);

		$(function() {

		

			$( sliderName ).slider({
				range: "min",
				value: sliderValue,
				min: 0,
				max: 48,
				slide: function( event, ui ) {				
					
				}
			});
			$( sliderText ).val( $( sliderName ).slider( "value" ) );

		});
		

	}



	$("#players").append("</tbody>");

	$('.minSlider').each(function(idx, elm) {
		var name = elm.id.replace('minutos', 'numero');
		$('#' + elm.id).slider({

	        	slide: function(event, ui) {
				
			    var prevValue = $( '#' + elm.id ).slider( "value" );
			    var nextValue = ui.value;
			    var actualTotalMinutes = getTotalMinutes();
			    var increase = (nextValue-prevValue);


			    if (nextValue==-1)
				nextValue=0;
				

				if (nextValue > prevValue ){
					if (actualTotalMinutes + increase>240){
						$( '#' + elm.id ).value( prevValue );		
					}
					else{
					    $('#' + name).val(nextValue);                                   							    	
					    setTotalMinutes();				    

					}

					
				}
				else{
					

				    $('#' + name).val(nextValue);                                   							    	
				    setTotalMinutes();				    


				}

				
	        	}
	    	});
	});

	$("#minTotalesPogressBar").progressbar({
		min: 0,
		max: 240,
		value: minTotales,
		complete: function() {


		}
	});


	
	$("#minutosTotalText").val(minTotales+"/240");
}


</script>


</head>


<body>



 
 <!-- Capa destinada al header -->
 <div id="cabecera">
 </div>


<div class="cuerpo" align="center">

 


	<sec:authorize access="isAuthenticated()">



	<c:if test="${not empty teamForm}">
	
	<c:set var="idEquipo" value="${teamForm.equipo.idEquipo}" />

	<form:form class="setRotation-form"  action="/teammanagement/team/${teamForm.equipo.idEquipo}/editteamrotation" modelAttribute="teamForm" method="post">

		<table class="datatable" class="playerminutes">
		<caption>Rotación</caption>
		    <tr>
			<th class="hidden"></th>
			<th>Nombre</th>
			<th>Posición</th>
			<th>Media</th>
			<th>Rotación</th>
			<th>Minutos</th>

		    </tr>

		   <c:forEach items="${teamForm.listaJugadores}" var="jugador" varStatus="status">
		    <tr>

			<td class="hidden" align="center"><form:input type="hidden" class="text" path="listaJugadores[${status.index}].idJugador" id="id${status.index}"/></td>
			<td>${jugador.nombre}</td><td>${jugador.posicion1}</td><td>${jugador.media}</td><td width=200><div class="minSlider" id="minutos${status.index}"></div></td>
			<td align="center"><form:input type="number" readonly="true" class="numero" path="listaJugadores[${status.index}].minutos" id="numero${status.index}"/></td>

		    </tr>
		   </c:forEach>

		</table> 
		<div align="center"><input type="submit" class="pure-button" value="Guardar Rotación" id="submit"/></div>
	</form:form>


	<div align=center>
		<p>
			<label for="minutosTotalText">Minutos Totales:</label>
			<input type="number" class="mintotales" id="minutosTotalText" style="border: 0; color: #f6931f; font-weight: bold;" />	
		</p>
	
		<div id="minTotalesPogressBar" class="minTotales"></div>


	</div>

	<div class="updateMessage" align=center><p><font color="green">${updateMessage}</p></font></div>

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

