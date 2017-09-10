<%@ page language="java" contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>


<html>

<link rel="stylesheet" type="text/css" media="screen" href="<c:url value="/resources/css/cupertino/jquery-ui-1.10.3.custom.css" context="/teammanagement"/>">  
<link rel="stylesheet" type="text/css" media="all" href="<c:url value="/resources/css/styles.css" context="/teammanagement"/>">
<link rel="stylesheet" type="text/css" media="screen" href="<c:url value="/resources/css/cupertino/jquery-ui-1.10.3.custom.css" context="/teammanagement"/>">  
<link rel="stylesheet" type="text/css" media="all" href="<c:url value="/resources/css/bootstrap.min.css" context="/teammanagement"/>">
<link rel="stylesheet" type="text/css" media="all" href="<c:url value="/resources/css/bootcards-desktop.min.css" context="/teammanagement"/>">
<script type='text/javascript' src='<c:url value="/resources/js/jquery-3.1.0.min.js"/>'></script>
<script type='text/javascript' src='<c:url value="/resources/js/jquery-ui-1.10.3.custom.js"/>'></script>
<script type='text/javascript' src='<c:url value="/resources/js/bootstrap.min.js"/>'></script>
<script type='text/javascript' src='<c:url value="/resources/js/bootcards.min.js"/>'></script>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">



<script type="text/javascript">

$( document ).ready(function() {


	getOfferedTrades();



});

function generateNoty(msg,type){

  	var n = noty({
  		text: msg,
  		type: type,
		closeWith: ['hover'],
		timeout: 1000
  	});

}

function getOfferedTrades(){

	var teamId = ${idEquipo};

	$.ajax({

		type:"get",
		url: "/teammanagement/market/getofferedtrades",	
		data: ({idEquipo:teamId}),
		dataType: "json",
		success: function(response){

			LoadReceivedTrades(response);
		},
		error: function(){
			generateNoty("Ha habido un problema conectando con el servidor",'error');
		}
	});

}


function LoadReceivedTrades(response){

	$('#receivedTrades').empty();

	if (response.listaTraspasosRecibidos.length==0)
		$('#offeredTrades').append("No hay ningún traspaso ofrecido.");		 


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
		html= html+ '<input type="button" class="pure-button" value="Cancelar" onclick="cancelTrade('+traspaso.idTraspaso+');">';
		html= html+'<div id="tradeMsg'+traspaso.idTraspaso+' style="width:250"></div></div>';
		$('#offeredTrades').append(html);
		var traspasoDiv = '#traspasoDiv'+ traspaso.idTraspaso;
		$(traspasoDiv).fadeIn();

	}
}



function cancelTrade(idTrade){

	$.ajax({

		type:"post",
		url: "/teammanagement/market/rejecttrade",	
		data: ({idTraspaso:idTrade}),
		dataType: "json",
		success: function(response){
			var tradeMsgDiv = '#traspasoDiv'+idTrade;
			if (response.success){				
				
				if (response.success){								
					$(tradeMsgDiv).fadeOut("slow");
				}
				else
					$(tradeMsgDiv).addClass("ui-state-error");
				
				generateNoty(""+response.message+"",'success');
			}
			else {
				generateNoty(""+response.message+"",'error');			
			}												
			
		},
		error: function(){
			generateNoty("Ha habido un problema conectando con el servidor",'error');
		}
	});

}

</script>


<head>
</head>

<body>

<jsp:include page="navbartrades.jsp" />
<div id="offeredTrades"></div>



</body>
</html>
