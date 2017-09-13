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
<title>${teamForm.nombreEquipo}</title>
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
 
	  <a class="navbar-brand no-break-out"  title="TeamManager" href="/competition/${menuNavigationForm.idCompeticion}">TeamManager</a>
    </div>
  </div>
</nav>

  <div class="container bootcards-container" id="main">
    
   
		<div class="row">
			
			<div class="col-sm-5 bootcards-list" id="listaPlantilla" data-title="Contacts">
				<div class="panel panel-default">
					<div class="panel-heading clearfix">
						<h3 class="panel-title pull-left"><b>Plantilla</b></h3>
					</div>								
					<div class="list-group" id="listaJugadores" >
					</div>
				</div>
			</div>

			<div class="col-sm-5 bootcards-cards" id="listDetails">
				<div id="contactCard">
					
					<div class="panel panel-default hidden" id ="playerDetails">
						<div class="panel-heading clearfix">
							<h3 class="panel-title pull-left"><b>Ficha jugador</b></h3>
							  <c:if test="${teamForm.propietarioEquipo || teamForm.adminCompeticion}">
							      <a class="btn btn-primary pull-right hidden" id="botonOfrecerContratoModal" href="#" data-toggle="modal" data-target="#ofrecerContratoModal">
							        <i class="fa fa-pencil"></i>
							        Renovar
							      </a>
						      </c:if>
						</div>
						<div class="list-group">
							<div class="list-group-item">
								<img class="img-rounded pull-left" id="imagen"/>
								<label>Nombre</label>								
								<h4 class="list-group-item-heading" id="nombre"></h4>
							</div>
							<div class="list-group-item">
								<img class="img-rounded pull-left" id="logoEquipoOriginal"/>
								<label>Equipo real</label>
								<h4 class="list-group-item-heading" id="equipoOriginal"></h4>
							</div>
							<div class="list-group-item">
								<label>Media</label>
								<h4 class="list-group-item-heading" id="media"></h4>
							</div>
							<div class="list-group-item">
								<label>Edad</label>
								<h4 class="list-group-item-heading" id="edad"></h4>
							</div>
							<div class="list-group-item">
								<label>Años restantes de contrato</label>
								<h4 class="list-group-item-heading" id="contractYears"></h4>
							</div>
							<div class="list-group-item">
								<label>Salario actual</label>
								<h4 class="list-group-item-heading" id="salary">								
								</h4>
							</div>
							<div class="list-group-item">
								<label>Caché</label>
								<h4 class="list-group-item-heading" id="cache">								
								</h4>
							</div>
							<div class="list-group-item">
								<label>Interés económico</label>
								<h4 class="list-group-item-heading" id="moneyInterest">								
								</h4>
							</div>
							<div class="list-group-item">
								<label>Interés competitivo</label>
								<h4 class="list-group-item-heading" id="winningInterest">								
								</h4>
							</div>														
							<div class="list-group-item">
								<label>Lealtad</label>
								<h4 class="list-group-item-heading" id="loyaltyInterest">								
								</h4>
							</div>														
							<div class="list-group-item hidden" id="pendienteDiv">
								<label>Firma de contrato pendiente:</label>
								<h4 class="list-group-item-heading" id="pendiente">								
								</h4>
							</div>																					
						</div>
						<div class="panel-footer">
							<small class="pull-left">Powered by LigasNBA.es</small>
							      <a class="btn btn-danger pull-right" id="botonDespedirModal" href="#" data-toggle="modal" data-target="#despedirModal">
							        <i class="fa fa-thumbs-down"></i>
							        Despedir
							      </a>							
						</div> 		
					</div>
														
				</div>
						
			</div>

		</div>
		
		


  </div>
   <!-- Load the required JavaScript libraries -->
 
  	<!--modals-->
	<div class="modal fade" id="ofrecerContratoModal" tabindex="-1" role="dialog" aria-labelledby="ofrecerContratoModal" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content"></div>
		</div>
	</div>
	
	<div class="modal fade" id="despedirModal" tabindex="-1" role="dialog" aria-labelledby="despedirModal" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
		      <form class="form-horizontal">
		
		        <div class="modal-header">	
		          <h3 class="modal-title">Despedir jugador</h3>
		        </div>
		
		        <div class="modal-body">
		        
		          <div class="form-group">
    				<div class="col-xs-12">							
						<h4 class="list-group-item-heading" id="capSpace">Desea despedir al jugador? Se pagará la mitad de su salario durante cada una de las temporadas restantes.</h4>
					</div>		          
		          </div>
		          <div class="modal-footer">
			          <div class="btn-group pull-left">
			            <button type="submit" class="btn btn-danger" id="btnDespedirJugadorConfirmado">
			              AL CARRER
			            </button>		          
			          </div>
			          <div class="btn-group pull-right">
			            <button class="btn btn-primary" data-dismiss="modal">
			              Cancelar
			            </button>
			          </div>
			      </div>    

		        </div>
		
		
		      </form>			
			</div>
		</div>
	</div>
	

<script type='text/javascript' src='<c:url value="/resources/bootstrap-notify-master/bootstrap-notify.min.js"/>'></script>
<script type='text/javascript' src='<c:url value="/resources/js/bootstrap.min.js"/>'></script>
<script type='text/javascript' src='<c:url value="/resources/js/bootcards.min.js"/>'></script>
<script type="text/javascript">
    
    bootcards.init( {
        offCanvasHideOnMainClick : true,
        offCanvasBackdrop : true,
        enableTabletPortraitMode : true,
        disableRubberBanding : false,
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

	function showPlayerDetails(idJugador){
		var playerItemId = "#playerItem"+idJugador;
		$('.list-group a.active').removeClass('active');
		$(playerItemId).addClass('active');
		transitionListToDetail();
		loadPlayerData(idJugador);		
	}
	
	function transitionListToDetail(){
		$('#listaPlantilla').addClass("visible-lg visible-md visible-sm hidden");
		$('#listDetails').removeClass("col-sm-5 bootcards-cards hidden-xs visible");
		$('#listDetails').addClass("col-sm-5 bootcards-cards visible");
		$('#playerDetails').removeClass("panel panel-default hidden");
		$('#playerDetails').addClass("panel panel-default visible");
		$('#botonAtras').removeClass("hidden");
		$('#botonAtras').addClass("btn btn-primary visible-xs-block visible-sm-block hidden pull-left fa fa-chevron-left");	
		
	}

	function transitionDetailToList(){
		$('#listaPlantilla').removeClass("visible-lg visible-md visible-sm hidden");
		$('#listaPlantilla').addClass("visible");
		$('#listDetails').removeClass("col-sm-5 bootcards-cards visible");
		$('#listDetails').addClass("col-sm-5 bootcards-cards hidden-xs visible");		
		$('#playerDetails').removeClass("panel panel-default visible");
		$('#playerDetails').addClass("panel panel-default hidden");		
		$('#botonAtras').removeClass("btn btn-primary visible-xs-block visible-sm-block hidden pull-left fa fa-chevron-left");
		$('#botonAtras').addClass("hidden");
	}
	
    function loadPlayerData(idJugador){
    	var idEquipo = ${teamForm.idEquipo};
    	$.ajax({
    		type:"get",
    		url: "/team/player",	
    		data: ({ idJugador: idJugador }),
    		dataType: "json",
    		success: function(response){
    			$("#idJugador").text(response.idJugador);    			
    			$("#nombre").text(response.nombre);
    			$("#imagen").attr("src",'<c:url value="/resources/images/players/'+response.imagen+'"/>');
    			$("#logoEquipoOriginal").attr("src", '<c:url value="/resources/images/'+response.logoEquipoOriginal+'"/>');
    			$("#equipoOriginal").text(response.equipoOriginal);
    			$("#media").text(response.media);
    			$("#edad").text(response.edad);
    			$("#contractYears").text(response.contractYears);
    			var salaryFormatted = $.number( response.currentSalary , 0 ) +"$";
    			$("#salary").text(salaryFormatted);
    			var cacheFormatted = $.number( response.cache , 0 ) +"$";
    			$("#cache").text(cacheFormatted);
    			$("#moneyInterest").text(response.moneyInterest);
    			$("#winningInterest").text(response.winningInterest);
    			$("#loyaltyInterest").text(response.loyaltyInterest);
    			
    			if (response.pendiente){
    				$("#pendiente").text("Pendiente de firma")
    				$("#pendienteDiv").removeClass("hidden");
    				$("#pendienteDiv").addClass("visible");    				
    			}
    			else {
    				$("#pendiente").text("")
    				$("#pendienteDiv").removeClass("visible");
    				$("#pendienteDiv").addClass("hidden");
    			}
    			
    			
    			$("#botonOfrecerContratoModal").attr("href", '/team/'+idEquipo+'/offerContractForm/'+response.idJugador);
     			$("#btnDespedirJugadorConfirmado").attr("onClick", 'despedirJugador('+response.idJugador+','+idEquipo+');');
    			if (response.contractYears<=1){
    				$("#botonOfrecerContratoModal").removeClass("hidden");
    				$("#botonOfrecerContratoModal").addClass("visible");
    			}
    			else {
    				$("#botonOfrecerContratoModal").removeClass("visible");
    				$("#botonOfrecerContratoModal").addClass("hidden");    				
    			}
    			
    		},
    		error: function(){
    			alert('error');
    		}
    	});		
   	}
    
    $( document ).ready(function() {  	   
		loadPlantilla();	
     });
    
	function despedirJugador(idJugador, idEquipo){

    	$.ajax({
    		type:"get",
    		url: "/team/waive",	
    		data: ({ idEquipo: idEquipo, idJugador: idJugador }),
    		dataType: "json",
    		success: function(response){
    			
    			if (response.success){
    				notify("Jugador despedido correctamente", "success")
    				loadPlantilla();
    				transitionDetailToList();
    			}
    			else {
    				notify("Error despidiendo a jugador", "danger");
    			}
    			
    		},
    		error: function(){
    			notify("Error en la llamada al servidor", "danger");
    		}
    	});	

	}
    
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
        
    function loadPlantilla(){
    	var idEquipo = ${teamForm.idEquipo};
    	$.ajax({
    		type:"get",
    		url: "/team/getPlantilla",	
    		data: ({ idEquipo: idEquipo }),
    		dataType: "json",
    		success: function(response){
    			$( "#listaJugadores" ).empty();
    			for (var i in response){
					$( "#listaJugadores" ).append('<a class="list-group-item pjax" href="#" id="playerItem'+response[i].idJugador+'" onClick="showPlayerDetails('+response[i].idJugador+')">'+
							'<div class="hidden" id="idJugador">'+response[i].idJugador+'</div>'+
							'<img class="img-rounded pull-left" src="<c:url value="/resources/images/players/'+response[i].imagen+'"/>"/>'+
							'<h4 class="list-group-item-heading">'+response[i].nombre+'</h4><p class="list-group-item-text">'+response[i].media+'</p></a>');
					}
    			   			
    		},
    		error: function(){
    			alert('error');
    		}
    	});	    	
    }
    
    
</script>    
</body>

</html>

