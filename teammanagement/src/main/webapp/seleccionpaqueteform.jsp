<%@ page language="java" contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<meta http-equiv="X-UA-Compatible" content="IE=edge"> 
<meta name="mobile-web-app-capable" content="yes" />
<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0, user-scalable=no">
<html>



<head>


<link rel="stylesheet" type="text/css" media="all" href="${pageContext.request.contextPath}/resources/css/styles-responsive.css"/>
<link rel="stylesheet" type="text/css" media="all" href="${pageContext.request.contextPath}/resources/css/bootstrap.min.css"/>
<link rel="stylesheet" type="text/css" media="all" href="${pageContext.request.contextPath}/resources/css/bootcards-desktop.min.css"/>
<link rel="stylesheet" type="text/css" media="all" href="${pageContext.request.contextPath}/resources/css/bootcards-demo.css"/>
<link rel="stylesheet" type="text/css" media="all" href="${pageContext.request.contextPath}/resources/css/font-awesome.min.css"/>
<link rel="stylesheet" type="text/css" media="all" href="${pageContext.request.contextPath}/resources/css/Animate.css"/>
<link rel="stylesheet" type="text/css" media="all" href="${pageContext.request.contextPath}/resources/css/star-rating.min.css"/>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<script type='text/javascript' src="${pageContext.request.contextPath}/resources/js/jquery-3.1.0.min.js"/></script>
<script type='text/javascript' src="${pageContext.request.contextPath}/resources/js/jquery.number.min.js"/></script>
<title>Selección paquete de ingresos</title>
<script>var isDesktop = true;</script>
<c:url value="/" var="homeUrl"/>
</head>

<body>

<nav class="navbar navbar-default navbar-fixed-top" role="navigation">
  <div class="container">
    <!-- Brand and toggle get grouped for better mobile display -->
    <div class="navbar-header">
	  <a class="navbar-brand no-break-out"  title="TeamManagement" href="/competition/${menuNavigationForm.idCompeticion}">TeamManagement</a>
    </div>
  </div>
</nav>

<div class="container bootcards-container" id="main">
	<div class="row">
	
		<div class="bootcards-cards">
		
			<div class="col-sm-6">    
			   <div class="panel panel-default bootcards-summary">
					
					<div class="panel-heading clearfix">
						<h3 class="panel-title pull-left"><b>Paquete ingresos</b></h3>
				      <button class="btn btn-primary pull-right" onClick="seleccionarPaquete();">
				        Seleccionar
				      </button>
					
					</div>

			    <div class="list-group">
			      <div class="list-group-item" id="listaAsientos">
			          <select class="form-control" name="paqueteSelect" id="paqueteSelect">	          
						<c:forEach items="${paquetesIngresosForm.arquetiposDisponibles}" var="arquetipos" varStatus="status">					
							<option value="${arquetipos.idArquetipo}">${arquetipos.nombre}</option>				    
						</c:forEach>	          
			          </select>		  		    			      
			      </div>
				<div class="list-group-item">
					<label>Descripción</label>
					<h4 class="list-group-item-heading" id="descripcion"></h4>
				</div>		    
				<div class="list-group-item">
					<label>Ingreso Fijo</label>
					<h4 class="list-group-item-heading" id="ingresoFijo"></h4>
				</div>		    
				<div class="list-group-item">
					<label>Partido jugado liga regular</label>
					<h4 class="list-group-item-heading" id="ingresoPJRS"></h4>
				</div>		    
				<div class="list-group-item">
					<label>Partido ganado liga regular</label>
					<h4 class="list-group-item-heading" id="ingresoPGRS"></h4>
				</div>																
				<div class="list-group-item">
					<label>Clasificación PlayOffs</label>
					<h4 class="list-group-item-heading" id="clasificacionPO"></h4>
				</div>					    
				<div class="list-group-item">
					<label>Partido jugado PlayOffs</label>
					<h4 class="list-group-item-heading" id="PJPO"></h4>
				</div>
				<div class="list-group-item">
					<label>Partido ganado PlayOffs</label>
					<h4 class="list-group-item-heading" id="PGPO"></h4>
				</div>
				<div class="list-group-item">
					<label>Ronda de PO ganada</label>
					<h4 class="list-group-item-heading" id="RondasGanadasPO"></h4>
				</div>				
				<div class="list-group-item">
					<label>Partidos ganados RS Objetivo 1</label>
					<h4 class="list-group-item-heading" id="PGObjetivo1"></h4>
				</div>
				<div class="list-group-item">
					<label>Premio Objetivo 1</label>
					<h4 class="list-group-item-heading" id="PremioObjetivo1"></h4>
				</div>	
				<div class="list-group-item">
					<label>Partidos ganados RS Objetivo 2</label>
					<h4 class="list-group-item-heading" id="PGObjetivo2"></h4>
				</div>
				<div class="list-group-item">
					<label>Premio Objetivo 2</label>
					<h4 class="list-group-item-heading" id="PremioObjetivo2"></h4>
				</div>				    
				<div class="list-group-item">
					<label>Rondas de PO ganadas Objetivo 3</label>
					<h4 class="list-group-item-heading" id="RondasPOObjetivo3"></h4>
				</div>
				<div class="list-group-item">
					<label>Ingreso objetivo 3</label>
					<h4 class="list-group-item-heading" id="PremioObjetivo3"></h4>
				</div>				    
				<div class="list-group-item">
					<label>Anillo</label>
					<h4 class="list-group-item-heading" id="PremioCampeon"></h4>
				</div>			    			    
		      
			    </div>			   
			   </div>
			</div>
		</div>
				   
   

				  
			    
	  	</div>
	</div>



<script type='text/javascript' src="${pageContext.request.contextPath}/resources/bootstrap-notify-master/bootstrap-notify.min.js"/></script>
<script type='text/javascript' src="${pageContext.request.contextPath}/resources/js/bootstrap.min.js"/></script>
<script type='text/javascript' src="${pageContext.request.contextPath}/resources/js/bootcards.min.js"/></script>
<script type='text/javascript' src="${pageContext.request.contextPath}/resources/js/star-rating.min.js"/></script>
<script type='text/javascript' src="${pageContext.request.contextPath}/resources/js/theme.min.js"/></script>
<script type='text/javascript' src="${pageContext.request.contextPath}/resources/js/es.js"/></script>
<script type="text/javascript">
    
    bootcards.init( {
        offCanvasHideOnMainClick : true,
        offCanvasBackdrop : true,
        enableTabletPortraitMode : true,
        disableRubberBanding : false,
        disableBreakoutSelector : 'a.no-break-out'
      });
    //fix for minimal-ui bug in Safari:
    //http://stackoverflow.com/questions/22391157/gray-area-visible-when-switching-from-portrait-to-landscape-using-ios-7-1-minima
    if (bootcards.isXS() ) {
      window.addEventListener("orientationchange", function() {
        window.scrollTo(0,0);
      }, false);

      //initial redraw - needed to fix an issue with the minimal-ui and foot location
      //when the page if first opened 
      window.scrollTo(0,0);
    }    

</script>
<script type="text/javascript">
  
    
    $( document ).ready(function() {
    	var idPaqueteIngresos = ${paquetesIngresosForm.idArquetipoSeleccionado};
    	$('select[name=paqueteSelect]').val(idPaqueteIngresos);
    	mostrarPaquetesDetalle(idPaqueteIngresos);
    	
     });
    
    $( "#paqueteSelect" ).change(function() {
    	mostrarPaquetesDetalle(($('select[name=paqueteSelect]').val()));
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
    
    function mostrarPaquetesDetalle(idPaqueteIngresos){
    	
    	var idEquipo = ${menuNavigationForm.idCompeticion};
    	$.ajax({
    		type:"get",
    		url: "/finanzas/getPaqueteIngresos",	
    		data: ({ idEquipo: idEquipo, idPaqueteIngresos: idPaqueteIngresos}),
    		dataType: "json",
    		success: function(response){
    			$( "#descripcion" ).text(response.descripcion);
    			$( "#ingresoFijo" ).text('$'+ $.number( response.ingresoFijo , 0 ));
    			$( "#ingresoPJRS" ).text('$'+ $.number( response.ingresoPartidoJugadoRS , 0 ));
    			$( "#ingresoPGRS" ).text('$'+ $.number( response.ingresoPartidoGanadoRS , 0 ));
    			$( "#clasificacionPO" ).text('$'+ $.number( response.ingresoClasificacionPO , 0 ));
    			$( "#PJPO" ).text('$'+ $.number( response.ingresoPartidoJugadoPO , 0 ));
    			$( "#PGPO" ).text('$'+ $.number( response.ingresoPartidoGanadoPO , 0 ));
    			$( "#RondasGanadasPO" ).text('$'+ $.number( response.ingresoRondasPOGanadas , 0 ));
    			$( "#PGObjetivo1" ).text(response.numPartidosGanadosObjetivo1);
    			$( "#PremioObjetivo1" ).text('$'+ $.number( response.ingresoObjetivo1 , 0 ));   			    			
    			$( "#PGObjetivo2" ).text(response.numPartidosGanadosObjetivo2);
    			$( "#PremioObjetivo2" ).text('$'+ $.number( response.ingresoObjetivo2 , 0 ));
    			$( "#RondasPOObjetivo3" ).text(response.numRondasGanadasObjetivo3);
    			$( "#PremioObjetivo3" ).text('$'+ $.number( response.ingresoObjetivo3 , 0 ));
    			$( "#PremioCampeon" ).text('$'+ $.number( response.ingresoCampeon , 0 ));
    			
    			
    			   			
    		},
    		error: function(){
    			alert('error');
    		}
    	});	 


    }
    function seleccionarPaquete(){
    	var idEquipo = ${menuNavigationForm.idEquipo};
    	var idPaqueteIngresos = ($('select[name=paqueteSelect]').val());
    	
    	$.ajax({
    		type:"get",
    		url: "/finanzas/setPaqueteIngresos",	
    		data: ({ idEquipo: idEquipo, idPaqueteIngresos: idPaqueteIngresos}),
    		dataType: "json",
    		success: function(response){
    			
    			if (response.success){
    			
					notify("Paquete de ingresos seleccionado correctamente", "success")
    			}
    			else {
    				notify("Error al guardar el paquete de ingresos", "danger")
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

