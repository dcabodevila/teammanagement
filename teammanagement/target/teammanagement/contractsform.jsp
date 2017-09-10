<%@ page language="java" contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<meta http-equiv="X-UA-Compatible" content="IE=edge"> 
<meta name="mobile-web-app-capable" content="yes" />
<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0, user-scalable=no">
<html>



<head>


<link rel="stylesheet" type="text/css" media="all" href="<c:url value="/resources/css/styles-responsive.css" context="/teammanagement"/>">
<link rel="stylesheet" type="text/css" media="all" href="<c:url value="/resources/css/bootstrap.min.css" context="/teammanagement"/>">
<link rel="stylesheet" type="text/css" media="all" href="<c:url value="/resources/css/bootcards-desktop.min.css" context="/teammanagement"/>">
<link rel="stylesheet" type="text/css" media="all" href="<c:url value="/resources/css/bootcards-demo.css" context="/teammanagement"/>">
<link rel="stylesheet" type="text/css" media="all" href="<c:url value="/resources/css/font-awesome.min.css" context="/teammanagement"/>">
<link rel="stylesheet" type="text/css" media="all" href="<c:url value="/resources/css/Animate.css" context="/teammanagement"/>">
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<script type='text/javascript' src='<c:url value="/resources/js/jquery-3.1.0.min.js"/>'></script>
<script type='text/javascript' src='<c:url value="/resources/js/jquery.number.min.js"/>'></script>
<title>Contratos</title>
<script>var isDesktop = true;</script>
<c:url value="/" var="homeUrl"/>
</head>

<body>

<nav class="navbar navbar-default navbar-fixed-top" role="navigation">
  <div class="container">
    <!-- Brand and toggle get grouped for better mobile display -->
    <div class="navbar-header">
      <button type="button" id="botonAtras" onClick="transitionDetailToList()" class="hidden" style="margin-top:10;margin-left:5;">
      	Atrás
      </button>
 
	  <a class="navbar-brand no-break-out"  title="TeamManager" href="/teammanagement/competition/${menuNavigationForm.idCompeticion}">TeamManager</a>
    </div>
  </div>
</nav>

  <div class="container bootcards-container" id="main">
	<div class="col-sm-12">    
		<div class="panel panel-default bootcards-table">

		  <div class="table-responsive">
		    <table class="table table-hover" id="tablaContractData">
		      <thead>
		        <tr class="active"><th>Jugador</th><th style="text-align:right;">Temp 1</th><th style="text-align:right;">Tem2</th><th style="text-align:right;">Temp3</th></tr>
		      </thead>
		      <tbody>
		      </tbody>
		    </table>
		  </div>
		  
		  <div class="panel-footer">
	          <select class="form-control" name="teamSelect" id="teamSelect">	          
				<c:forEach items="${contractsForm.listaEquipos}" var="equipo" varStatus="status">					
					<option value="${equipo.idEquipo}">${equipo.nombre}</option>				    
				</c:forEach>	          
	          </select>		  		    
		  </div>
		</div>
		
	</div>		


  </div>
   <!-- Load the required JavaScript libraries -->
 

	

<script type='text/javascript' src='<c:url value="/resources/bootstrap-notify-master/bootstrap-notify.min.js"/>'></script>
<script type='text/javascript' src='<c:url value="/resources/js/bootstrap.min.js"/>'></script>
<script type='text/javascript' src='<c:url value="/resources/js/bootcards.min.js"/>'></script>
<script type="text/javascript">
    
    bootcards.init( {
        offCanvasHideOnMainClick : true,
        offCanvasBackdrop : true,
        enableTabletPortraitMode : true,
        disableRubberBanding : true,
        disableBreakoutSelector : 'a.no-break-out'
      });

    if (bootcards.isXS() ) {
      window.addEventListener("orientationchange", function() {
        window.scrollTo(0,0);
      }, false);

      window.scrollTo(0,0);
    }    

</script>
<script type="text/javascript">

    
    $( document ).ready(function() {
    	var idEquipo = ${contractsForm.idEquipo};
    	$('select[name=teamSelect]').val(idEquipo);    	    	
    	getTeamContractData(idEquipo);
     });
    
    $( "#teamSelect" ).change(function() {
    	getTeamContractData(($('select[name=teamSelect]').val()));
    	});

    function notify(mensaje, tipo){
    	$.notify({
    		title: "",
    		message: mensaje
    	},{
    		type: tipo,
    		delay: 1000,
    		placement: {
    			align : "center"
    		},
    		animate:{
    			enter: "animated fadeInDown",
    			exit: "animated fadeOutDown"
    		}
    	});    	
    }

	function getTeamContractData(idEquipo){
		
		$.ajax({
			type:"get",
			url: "/teammanagement/contracts/getTeamContractsData",	
			data: ({idEquipo: idEquipo}),
			dataType: "json",
			success: function(response){
				$('#tablaContractData tbody').remove();
				$("#tablaContractData").append("<tbody>");
				for (var i in response.listaContractData){
					$( "#tablaContractData" ).append('<tr><td>'+response.listaContractData[i].nombre+'</td><td align="right">'+$.number( response.listaContractData[i].salarioTemporada1 , 0 )+'</td><td align="right">'+$.number( response.listaContractData[i].salarioTemporada2 , 0 ) +'<td align="right">'+$.number( response.listaContractData[i].salarioTemporada3 , 0 ) +'</td></tr>');
				}
				
				$( "#tablaContractData" ).append('<tr><td><strong>Suma salarios</strong></td><td align="right"><strong>'+$.number( response.sumaSalarialT1 , 0 ) +'</strong></td><td align="right"><strong>'+$.number( response.sumaSalarialT2 , 0 ) +'</strong></td><td align="right"><strong>'+$.number( response.sumaSalarialT3 , 0 ) +'</strong></td></tr>');
				$("#tablaContractData").append("</tbody>");
				
			},
			error: function(){
				alert('error');
			}
		});		
	}
    
</script>    
</body>

</html>

