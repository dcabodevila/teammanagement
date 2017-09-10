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
<link rel="stylesheet" type="text/css" media="all" href="<c:url value="/resources/css/star-rating.min.css" context="/teammanagement"/>">
<%-- <link rel="stylesheet" type="text/css" media="all" href="<c:url value="/resources/css/theme.min.css" context="/teammanagement"/>"> --%>
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
 
	  <a class="navbar-brand no-break-out"  title="TeamManager" href="/teammanagement/competition/${menuNavigationForm.idCompeticion}">TeamManager</a>
    </div>
  </div>
</nav>

  <div class="container bootcards-container" id="main">
    
   <div class="col-sm-5 bootcards-list" >
		<div class="row">
			
			<div class="bootcards-list">
			  <div class="panel panel-default">
				<div class="panel-heading clearfix">
					<h3 class="panel-title pull-left"><b>Ingresos y gastos</b></h3>
				</div>				  
			    <div class="list-group">
			      <div class="list-group-item" id="listaAsientos">
			      </div>
			    </div>
			    <div class="list-group">
			      <div class="list-group-item" id="balance">			      	
			      </div>
			    </div>			    
   				  <div class="panel-footer">
   				  	  	
			          <select class="form-control" name="seasonSelect" id="seasonSelect">	          
						<c:forEach items="${finanzasForm.listaTemporadas}" var="temporada" varStatus="status">					
							<option value="${temporada.idTemporada}">${temporada.nombreTemporada}</option>				    
						</c:forEach>	          
			          </select>		  		    
				  </div>			    
			  </div>
			</div>

  		</div>
	</div>
  </div>


<%-- <script type='text/javascript' src='<c:url value="/resources/js/fastclick.min.js"/>'></script> --%>
<%-- <script type='text/javascript' src='<c:url value="/resources/js/raphael-min.js"/>'></script> --%>
<script type='text/javascript' src='<c:url value="/resources/bootstrap-notify-master/bootstrap-notify.min.js"/>'></script>
<script type='text/javascript' src='<c:url value="/resources/js/bootstrap.min.js"/>'></script>
<script type='text/javascript' src='<c:url value="/resources/js/bootcards.min.js"/>'></script>
<script type='text/javascript' src='<c:url value="/resources/js/star-rating.min.js"/>'></script>
<script type='text/javascript' src='<c:url value="/resources/js/theme.min.js"/>'></script>
<script type='text/javascript' src='<c:url value="/resources/js/es.js"/>'></script>
<%-- <script type='text/javascript' src='<c:url value="/resources/js/jquery.pjax.js"/>'></script> --%>
<%-- <script type='text/javascript' src='<c:url value="/resources/js/bootcards-demo-app.js"/>'></script> --%>
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
  
    
    $( document ).ready(function() {
    	var idTemporada = ${menuNavigationForm.idTemporada};
    	$('select[name=seasonSelect]').val(idTemporada);
		loadPartidos(idTemporada);	
     });
    
    $( "#seasonSelect" ).change(function() {
    	loadPartidos(($('select[name=seasonSelect]').val()));
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
    
    function loadPartidos(idTemporada){
    	var idEquipo = ${menuNavigationForm.idEquipo};
    	 
    	$.ajax({
    		type:"get",
    		url: "/teammanagement/finanzas/findAsientosEquipoTemporada",	
    		data: ({ idEquipo: idEquipo, idTemporada: idTemporada}),
    		dataType: "json",
    		success: function(response){
    			$( "#listaAsientos" ).empty();
    			
    			for (var i in response.listaAsientos){
    		
    				var icono = '<i class="fa fa-3x fa-plus pull-left" style="color:green"></i>';
    				
    				if (response.listaAsientos[i].importe<0){
    					icono = '<i class="fa fa-3x fa-minus pull-left" style="color:red"></i>';
    				}
    				var date = new Date(response.listaAsientos[i].fecha);
    				var dateFormatted = date.getDate() + '/'+(date.getMonth() + 1) + '/' +  +  date.getFullYear();
    				
    				$( "#listaAsientos" ).append('<div class="list-group-item"><div class="row"><div class="col-sm-6">'+
			            icono +
			            '<h4 class="list-group-item-heading">'+response.listaAsientos[i].concepto+'</h4>'+
			            '<p class="list-group-item-text"></p>'+
			          '</div>'+
			          '<div class="col-sm-6">'+
			            '<p class="list-group-item-text">'+dateFormatted+'</p>'+
			            '<p class="list-group-item-text">$'+$.number( response.listaAsientos[i].importe , 0 )+'</p>'+
			          '</div></div></div></div>');
    			}
    			$( "#balance" ).text('Balance temporada: $'+ $.number( response.balanceTemporada , 0 ));

    			
    			   			
    		},
    		error: function(){
    			alert('error');
    		}
    	});	    	
    }
    
    
</script>    
</body>

</html>

