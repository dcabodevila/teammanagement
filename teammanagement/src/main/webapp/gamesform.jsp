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
<%-- <link rel="stylesheet" type="text/css" media="all" href="${pageContext.request.contextPath}/resources/css/theme.min.css"/>"> --%>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<script type='text/javascript' src="${pageContext.request.contextPath}/resources/js/jquery-3.1.0.min.js"/></script>
<script type='text/javascript' src="${pageContext.request.contextPath}/resources/js/jquery.number.min.js"/></script>
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
 
	  <a class="navbar-brand no-break-out"  title="TeamManagement" href="/competition/${menuNavigationForm.idCompeticion}">TeamManagement</a>
    </div>
  </div>
</nav>

  <div class="container bootcards-container" id="main">
    
   
		<div class="row">
			
			<div class="col-sm-5 bootcards-list" id="listaPlantilla" data-title="Contacts">
				<div class="panel panel-default">
					<div class="panel-body" id="panelBuscar">
						<div class="search-form" id="searchTool">
							<div class="row">
							    <div class="col-xs-9">
							      <div class="form-group">
								      <input type="text" id="gameNameSearch" class="form-control" placeholder="Buscar equipo / mánager...">
								      <i class="fa fa-search"></i>
							      </div>
							    </div>
							    <div class="col-xs-3">
									<button type="button" class="btn btn-primary btn-block fa fa-search-plus" onClick="loadPartidos()">
										<span class="visible-lg-inline">Buscar</span>
									</button>										
							    </div>
							</div>						    
						</div>					
					</div>							
					<div class="list-group" id="listaPartidos" >
					</div>
				</div>
			</div>

			<div class="col-sm-5 bootcards-cards hidden" id="listDetails">
				<div id="contactCard">
					
					<div class="panel panel-default hidden" id ="gameDetails">
						<div class="panel-heading clearfix">
							<h3 class="panel-title pull-left"><b>Valoración partido</b></h3>
							  <c:if test="${matchForm.propietarioEquipo || matchForm.adminCompeticion}">
							      <button class="btn btn-primary pull-right hidden disabled" id="botonEnviarValoracion" data-loading-text="<i class='fa fa-circle-o-notch fa-spin'></i> Enviando">
							        <i class="fa fa-pencil"></i>
							        Enviar
							      </button>
						      </c:if>
						</div>
						<div class="list-group">
							<div class="list-group-item">
								<img class="img-rounded pull-left" id="logo"/>
								<label id=nombreUsuario></label>								
								<h4 class="list-group-item-heading" id="nombre"></h4>
							</div>						
							<div class="list-group-item">
								<label>Valoración</label>
								<input id="valoracion" name="valoracion" class="rating rating-loading" data-min="0" data-max="5" data-step="1" data-size="sm" data-show-clear="false" data-show-caption="false" value="0" onChange="enableBotonEnviar();">								
							</div>
							<div class="list-group-item">
								<label>Comentario</label>
								<textarea id="comentario" class="form-control" rows="6"></textarea>
							</div>
							<div class="list-group-item hidden" id="resultado">
								<label>Resultado</label>
								<h4 class="list-group-item-heading"></h4>
							</div>														
						</div>
						  <div class="panel-footer hidden" id="botoneraResultado">
						    <div class="btn-group btn-group-justified">
						      <div class="btn-group">
						        <button class="btn btn-default" id="btnVictoria" onClick="setVictoria()">
						          <i class="fa fa-check" style="color:green"></i>
						          Victoria
						        </button>
						      </div>
						      <div class="btn-group">
						        <button class="btn btn-default" id="btnDerrota" onClick="setDerrota()">
						          <i class="fa fa-close" style="color:red"></i>
						          Derrota
						        </button>
						      </div>
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

<%-- <script type='text/javascript' src="${pageContext.request.contextPath}/resources/js/fastclick.min.js"/></script> --%>
<%-- <script type='text/javascript' src="${pageContext.request.contextPath}/resources/js/raphael-min.js"/></script> --%>
<script type='text/javascript' src="${pageContext.request.contextPath}/resources/bootstrap-notify-master/bootstrap-notify.min.js"/></script>
<script type='text/javascript' src="${pageContext.request.contextPath}/resources/js/bootstrap.min.js"/></script>
<script type='text/javascript' src="${pageContext.request.contextPath}/resources/js/bootcards.min.js"/></script>
<script type='text/javascript' src="${pageContext.request.contextPath}/resources/js/star-rating.min.js"/></script>
<script type='text/javascript' src="${pageContext.request.contextPath}/resources/js/theme.min.js"/></script>
<script type='text/javascript' src="${pageContext.request.contextPath}/resources/js/es.js"/></script>
<%-- <script type='text/javascript' src="${pageContext.request.contextPath}/resources/js/jquery.pjax.js"/></script> --%>
<%-- <script type='text/javascript' src="${pageContext.request.contextPath}/resources/js/bootcards-demo-app.js"/></script> --%>
<script type="text/javascript">
    
//     if ( $('.list-group a.active').length === 0 ) {
//       $('.list-group a').first().addClass('active');
//     }

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

	function showGameDetails(idPartido){
		var gameItemId = "#gameItem"+idPartido;
		$('.list-group a.active').removeClass('active');
		$(gameItemId).addClass('active');
		transitionListToDetail();
		loadGameData(idPartido);		
	}
	
	function transitionListToDetail(){
		$('#listaPlantilla').addClass("visible-lg visible-md visible-sm hidden");
		$('#listDetails').removeClass("col-sm-5 bootcards-cards hidden-xs visible");
		$('#listDetails').addClass("col-sm-5 bootcards-cards visible");
		$('#gameDetails').removeClass("panel panel-default hidden");
		$('#gameDetails').addClass("panel panel-default visible");
		$('#botonAtras').removeClass("hidden");
		$('#botonAtras').addClass("btn btn-primary visible-xs-block visible-sm-block hidden pull-left fa fa-chevron-left");	
		
	}

	function transitionDetailToList(){
		$('#listaPlantilla').removeClass("visible-lg visible-md visible-sm hidden");
		$('#listaPlantilla').addClass("visible");
		$('#listDetails').removeClass("col-sm-5 bootcards-cards visible");
		$('#listDetails').addClass("col-sm-5 bootcards-cards hidden-xs visible");		
		$('#gameDetails').removeClass("panel panel-default visible");
		$('#gameDetails').addClass("panel panel-default hidden");		
		$('#botonAtras').removeClass("btn btn-primary visible-xs-block visible-sm-block hidden pull-left fa fa-chevron-left");
		$('#botonAtras').addClass("hidden");
	}
	
    function loadGameData(idPartido){
    	var idEquipo = ${menuNavigationForm.idEquipo};
    	var idPartido = idPartido;
		$("#listDetails").removeClass("visible");
		$("#listDetails").addClass("hidden");
		$("#btnVictoria").removeClass('active');
		$("#btnDerrota").removeClass('active');
		$("#valoracion").rating('update', 0);
		$('#botonEnviarValoracion').removeClass('enabled');
		$('#botonEnviarValoracion').addClass('disabled');
		$("#comentario").val("");
		
    	$.ajax({
    		type:"get",
    		url: "/games/getValoracion",	
    		data: ({ idEquipo:idEquipo, idPartido: idPartido }),
    		dataType: "json",
    		success: function(response){
    			$("#idPartido").text(response.matchData.idPartido);    			
    			$("#nombre").text(response.matchData.nombreEquipo);
    			$("#logo").attr("src",'${pageContext.request.contextPath}/resources/images/'+response.matchData.logo);
    			if (response.valoracion!=null){
    				$("#valoracion").rating('update', response.valoracion);
    			}
    			$("#comentario").val(response.comentario);
    			$("#nombreUsuario").text(response.matchData.nombreUsuario);
    			
    			$("#botonEnviarValoracion").attr("onClick", 'enviarValoracion('+idPartido+');');
    			if ((response.matchData.validado==null) || (response.matchData.validado==false)){
    				$("#botoneraResultado").removeClass("hidden");
    				$("#botoneraResultado").addClass("visible");
    				$("#resultado").removeClass("visible");
    				$("#resultado").addClass("hidden");
    				if (response.victoria!=null){
    					if (response.victoria){
    						$("#btnVictoria").addClass('active');
    						$("#btnDerrota").removeClass('active');
    					}
    					else {
    						$("#btnDerrota").addClass('active');
    						$("#btnVictoria").removeClass('active');
    					}
    				}
    				$("#valoracion").rating('refresh', {
    					readonly: false
    				});
    				$("#comentario").removeClass("disabled");
    				$("#comentario").addClass("enabled");
    				
    				$("#botonEnviarValoracion").removeClass("hidden");
    				$("#botonEnviarValoracion").addClass("visible");
    				
    			}
    			else {
    				$("#valoracion").rating('refresh', {
    					readonly: true
    				});
    				$("#botoneraResultado").removeClass("visible");
    				$("#botoneraResultado").addClass("hidden");
    				$("#resultado").removeClass("hidden");
    				$("#resultado").addClass("visible");      				
    				$("#botonEnviarValoracion").removeClass("visible");
    				$("#botonEnviarValoracion").addClass("hidden");
    				$("#comentario").removeClass("enabled");
    				$("#comentario").addClass("disabled");
    				
    			}
    			
    			
				$("#listDetails").removeClass("hidden");
				$("#listDetails").addClass("visible");      			    			
    			
    		},
    		error: function(){
    			alert('error');
    		}
    	});		
   	}
    
    function setVictoria(){
    	$("#btnDerrota").removeClass('active');
    	$("#btnVictoria").addClass('active');
    	$("#btnVictoria").blur();
    	enableBotonEnviar();    	        
    }
    
    function setDerrota(){
    	$("#btnVictoria").removeClass('active');
    	$("#btnDerrota").addClass('active');
    	$("#btnDerrota").blur();
    	enableBotonEnviar();        
    }
    

    
    function enableBotonEnviar(){
    	var valoracion = $('#valoracion').val();
    	
    	if (($('#btnVictoria').hasClass("active") || $('#btnDerrota').hasClass("active")) && (valoracion>0)){
    		$('#botonEnviarValoracion').removeClass('disabled');
    		$('#botonEnviarValoracion').addClass('enabled');	
    	}
    	else {
    		$('#botonEnviarValoracion').removeClass('enabled');
    		$('#botonEnviarValoracion').addClass('disabled');	
    	}
    }
    
    
    $( document ).ready(function() {  	   
		loadPartidos();	
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

    function enviarValoracion(idPartido){
    	var idEquipoValorador = ${menuNavigationForm.idEquipo};
    	var valoracion = $("#valoracion").val();
    	var comentario = $("#comentario").val();
    	var victoria = $('#btnVictoria').hasClass("active");
		$("#botonEnviarValoracion").button('loading');
    	$.ajax({
    		type:"get",
    		url: "/games/sendValoracion/",	
    		data: ({ idEquipo : idEquipoValorador, idPartido: idPartido, valoracion: valoracion, comentario: comentario, victoria: victoria }),
    		dataType: "json",
    		success: function(response){
    			if (response.success){
    				notify(response.message, "success");
    				$("#botonEnviarValoracion").button('reset');
    				loadPartidos();
    				transitionDetailToList();
    			}
    			else {
    				notify(response.message, "danger");
    				$("#botonEnviarValoracion").button('reset');
    				transitionDetailToList();
    			}
    			   			
    		},
    		error: function(){
    			notify("Error en la llamada al servidor", "danger");
    			$("#botonEnviarValoracion").button('reset');
    		}
    	});
		
		
    }
    
    function loadPartidos(){
    	var idEquipo = ${menuNavigationForm.idEquipo};
    	var nombrePartido = $( "#gameNameSearch" ).val();
		$("#listDetails").removeClass("visible");
		$("#listDetails").addClass("hidden");
    	$.ajax({
    		type:"get",
    		url: "/games/list",	
    		data: ({ idEquipo: idEquipo, texto: nombrePartido }),
    		dataType: "json",
    		success: function(response){
    			$( "#listaPartidos" ).empty();
    			for (var i in response){
    				var styleText = "";
    				if ((response[i].validado!=null) && (!response[i].validado)){
    					styleText = "style=background-color:DarkOrange!important;";
    				}
    				if ((response[i].discrepancia!=null) && (response[i].discrepancia)){
    					styleText = "style=background-color:#ff3333!important;";
    				}    			
    				
					$( "#listaPartidos" ).append('<a class="list-group-item pjax" '+styleText+' href="#" id="gameItem'+response[i].idPartido+'" onClick="showGameDetails('+response[i].idPartido+')">'+
							'<div class="hidden" id="idPartido">'+response[i].idPartido+'</div>'+
							'<img class="img-rounded pull-left" src="${pageContext.request.contextPath}/resources/images/'+response[i].logo+'"'+
							'<h4 class="list-group-item-heading">'+response[i].nombreEquipo+'</h4><p class="list-group-item-text">'+response[i].nombreUsuario+'</p></a>');
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

