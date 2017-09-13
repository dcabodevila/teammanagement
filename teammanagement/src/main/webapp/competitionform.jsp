<%@ page language="java" contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<meta http-equiv="X-UA-Compatible" content="IE=edge"> 
<meta name="mobile-web-app-capable" content="yes" />
<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0, user-scalable=no">

<html>


<head>

<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<script type='text/javascript' src='<c:url value="/resources/js/jquery-3.1.0.min.js"/>'></script>
<script type='text/javascript' src='<c:url value="/resources/js/jquery-ui.js"/>'></script>
<script type='text/javascript' src='<c:url value="/resources/js/noty/jquery.noty.js"/>'></script>
<script type='text/javascript' src='<c:url value="/resources/js/noty/layouts/bottom.js"/>'></script>
<script type='text/javascript' src='<c:url value="/resources/js/noty/themes/default.js"/>'></script>
<script type='text/javascript' src='<c:url value="/resources/js/noty/layouts/top.js"/>'></script>
<script type='text/javascript' src='<c:url value="/resources/jquery_news_ticker/includes/jquery.ticker.js"/>'></script>
<link rel="stylesheet" type="text/css" media="all" href="<c:url value="/resources/css/bootcards-desktop.min.css" context="/teammanagement"/>">
<link rel="stylesheet" type="text/css" media="all" href="<c:url value="/resources/css/bootcards-demo.css" context="/teammanagement"/>">
<link rel="stylesheet" type="text/css" media="all" href="<c:url value="/resources/css/font-awesome.min.css" context="/teammanagement"/>">

<%-- <link rel="stylesheet" type="text/css" media="all" href="<c:url value="/resources/css/styles.css" context="/teammanagement"/>"> --%>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">

<script>


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
		url: "/activate/resend",	
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


</script>
<script>

function seasonsSelectChange(){

	var selectedIdSeason = $( "#seasonsSelect").val();

	$.ajax({

		type:"get",
		url: "/competition/getClassification/",	
		data: ({idTemporada:selectedIdSeason}),
		dataType: "json",
		success: function(response){
			LoadClassification(response);
			
		},
		error: function(){
			generateNoty("Error en la llamada al servidor",'error');
		}
	});

}

function LoadClassification(response){

	$('#classification tbody').remove();

	$("#classification").append("<tbody>");

	var count =1;			    
	
	for (var i in response){

		var clasificacion=response[i];

		
		$("#classification").append('<tr><td>'+count+'</td><td><div align=left class="teamLogoName"><ul><li><img src=<c:url value="/resources/images/'+clasificacion.image+'"/> height= "32" width="40"/></li><li>'+ clasificacion.nombreEquipo+'</li></ul></div></td><td width=120>'+clasificacion.nombreUsuario+'</td><td>'+clasificacion.victorias+'</td><td>'+clasificacion.derrotas+'</td></tr>');

		count = count+1;
	}
	$("#classification").append("</tbody>");
}


</script>


<title>TeamManager ${competitionForm.nombreEquipo}</title>

</head>
<c:url value="/" var="homeUrl"/>
<c:url value="/team" var="TeamUrl"/>
<c:url value="/" var="homeUrl"/>
<jsp:include page="menucompetition.jsp" />
<body id="bootcards">

<div class="container bootcards-container" id="main">
	<div class="row">
	
		<div class="bootcards-cards">
		
			<div class="col-sm-6">
		
				<div class="panel panel-default bootcards-summary">
		
					<div class="panel-heading">
						<h3 class="panel-title"><b>${competitionForm.nombreCompeticion}</b></h3>
					</div>
		

				    <c:if test="${not empty competitionForm.idEquipo}">
					    <div class="list-group">
					      <div class="list-group-item">
							<h4 class="list-group-item-heading"><img src="<c:url value='/resources/images/${competitionForm.logo}'/>" />${competitionForm.nombreEquipo}</h4>
					      </div>
		      			  <div class="list-group-item">
		  				    <p class="list-group-item-text">Temporada</p>
							<h4 class="list-group-item-heading">${competitionForm.temporada}</h4>
					      </div>				      
		      			  <div class="list-group-item">
		  				    <p class="list-group-item-text">Fecha</p>
							<h4 class="list-group-item-heading"><fmt:formatDate pattern="dd-MM-yy" value="${competitionForm.actualDate}" /></h4>
					      </div>
				      
		      			  <div class="list-group-item">
		  				    <p class="list-group-item-text">Estado</p>
							<h4 class="list-group-item-heading">${competitionForm.descripcionEstadoCompeticion}</h4>
					      </div>
		      			  <div class="list-group-item">
		  				    <p class="list-group-item-text">Presupuesto temporada actual</p>
							<h4 class="list-group-item-heading" >$<fmt:formatNumber type = "number" pattern = "###,###,###,###" value = "${competitionForm.presupuestoActual}" /></h4>
					      </div>
		      			  <div class="list-group-item">
		  				    <p class="list-group-item-text">Presupuesto temporada siguiente</p>
							<h4 class="list-group-item-heading">$<fmt:formatNumber type = "number" pattern = "###,###,###,###" value = "${competitionForm.presupuestoSiguiente}" /></h4>
					      </div>				      				      
					      				      				      
				        </div>
			        </c:if>				      						
		
				</div>
		
			</div>
			</div>
			</div>

	</div>


	<c:if test="${not empty errormessage}">
	<div class="errormessage">
	<ul>
	<li>${errormessage}</li>
	</ul>
	</div>
	</c:if>
   
      </div>


</body>



</html>

