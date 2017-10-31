<%@ page language="java" contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>


<html>
<script type='text/javascript' src="${pageContext.request.contextPath}/resources/js/jquery-1.6.4.min.js"/></script>
<script type='text/javascript' src="${pageContext.request.contextPath}/resources/js/jquery-ui.js"/></script>



<head>
<link rel="stylesheet" type="text/css" media="screen" href="${pageContext.request.contextPath}/resources/css/cupertino/jquery-ui-1.10.3.custom.css"/>  
<link rel="stylesheet" type="text/css" media="all" href="${pageContext.request.contextPath}/resources/css/styles.css"/>
<link rel="stylesheet" type="text/css" media="screen" href="${pageContext.request.contextPath}/resources/css/cupertino/jquery-ui-1.10.3.custom.css"/>  
<link rel="stylesheet" type="text/css" media="all" href="${pageContext.request.contextPath}/resources/css/bootstrap.min.css"/>
<link rel="stylesheet" type="text/css" media="all" href="${pageContext.request.contextPath}/resources/css/bootcards-desktop.min.css"/>
<link rel="stylesheet" type="text/css" media="all" href="${pageContext.request.contextPath}/resources/css/styles.css"/>
<script type='text/javascript' src="${pageContext.request.contextPath}/resources/js/jquery-3.1.0.min.js"/></script>
<script type='text/javascript' src="${pageContext.request.contextPath}/resources/js/jquery-ui-1.10.3.custom.js"/></script>
<script type='text/javascript' src="${pageContext.request.contextPath}/resources/js/bootstrap.min.js"/></script>
<script type='text/javascript' src="${pageContext.request.contextPath}/resources/js/bootcards.min.js"/></script>




<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">



<script type="text/javascript">

$( document ).ready(function() {

	getReceivedTrades();

});


function getReceivedTrades(){

	var teamId = ${idEquipo};

	$.ajax({

		type:"get",
		url: "/market/getreceivedtrades",	
		data: ({idEquipo:teamId}),
		dataType: "json",
		success: function(response){

			LoadReceivedTrades(response);
		},
		error: function(){
			alert("Ha habido un problema conectando con el servidor");
		}
	});

}


function LoadReceivedTrades(response){

	$('#receivedTrades').empty();

	if (response.listaTraspasosRecibidos.length==0)
		$('#receivedTrades').append("No hay ningún traspaso recibido.");		 

	for (var i in response.listaTraspasosRecibidos){
		
		var html = '';
		var traspaso = response.listaTraspasosRecibidos[i];


		html = '<div id="traspasoDiv'+traspaso.idTraspaso+'" class="starthidden"><table class="dataTable"><tr><th class="starthidden">'+traspaso.idTraspaso+'</th><th>'+traspaso.nombreEquipoPropone+'</th><th width="150">'+traspaso.nombreEquipoRecibe+'</th></tr>';
		

		var numberRows = Math.max(traspaso.nombresJugadoresOfrecidos.length,traspaso.nombresJugadoresRecibidos.length); 

		for (x=0;x<numberRows;x++){

			var jugadorOfrecido = ''
				if ((traspaso.nombresJugadoresOfrecidos.length-1)<x)
					jugadorOfrecido = ''

				else
					jugadorOfrecido = traspaso.nombresJugadoresOfrecidos[x];



			var jugadorRecibido = ''
				if ((traspaso.nombresJugadoresRecibidos.length-1)<x)
					jugadorRecibido = ''
				else
					jugadorRecibido = traspaso.nombresJugadoresRecibidos[x];

			html = html + '</td><td width="150">'+jugadorOfrecido+'</td><td width="150">'+jugadorRecibido+'</td></tr>';
		
			
		}
		html= html+'</table>';
		html= html+ '<input type="button" class="pure-button" value="Aceptar" onclick="acceptTrade('+traspaso.idTraspaso+');"> <input type="button" value="Rechazar" onclick="rejectTrade('+traspaso.idTraspaso+');">';
		html= html+'<div id="tradeMsg'+traspaso.idTraspaso+' style="width:250"></div></div>';
		$('#receivedTrades').append(html);
		var traspasoDiv = '#traspasoDiv'+ traspaso.idTraspaso;
		$(traspasoDiv).fadeIn();

	}
}


function acceptTrade(idTrade){
	$.ajax({

		type:"post",
		url: "/market/accepttrade",	
		data: ({idTraspaso:idTrade}),
		dataType: "json",
		success: function(response){
			var tradeMsgDiv = '#tradeMsg'+idTrade;
			$(tradeMsgDiv).append(response.message);

			if (response.success){
				$(tradeMsgDiv).addClass("ui-state-highlight");
				var traspasoDiv = '#traspasoDiv'+idTrade;				
				$(traspasoDiv).fadeOut("slow");
			}
			else
				$(tradeMsgDiv).addClass("ui-state-error");


												
			
		},
		error: function(){
			alert("Ha habido un problema conectando con el servidor");
		}
	});
}

function rejectTrade(idTrade){

	$.ajax({

		type:"post",
		url: "/market/rejecttrade",	
		data: ({idTraspaso:idTrade}),
		dataType: "json",
		success: function(response){
			var tradeMsgDiv = '#tradeMsg'+idTrade;
			$(tradeMsgDiv).append(response.message);

			if (response.success){
				$(tradeMsgDiv).addClass("ui-state-highlight");
				var traspasoDiv = '#traspasoDiv'+idTrade;				
				$(traspasoDiv).fadeOut("slow");
			}

			else
				$("#tradeMsg").addClass("ui-state-error");


												
			
		},
		error: function(){
			alert("Ha habido un problema conectando con el servidor");
		}
	});

}

</script>

</head>

<body>
<jsp:include page="navbartrades.jsp" />
<div id="receivedTrades"></div>



</body>
</html>
