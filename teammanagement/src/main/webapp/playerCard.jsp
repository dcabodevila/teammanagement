<%@ page language="java" contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<meta http-equiv="X-UA-Compatible" content="IE=edge"> 
  <!--<link rel="shortcut icon" href="../../docs-assets/ico/favicon.png">-->
<meta name="mobile-web-app-capable" content="yes" />
<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0, user-scalable=no">
  <meta name="apple-mobile-web-app-capable" content="yes" />
  <meta name="apple-mobile-web-app-status-bar-style" content="black-translucent">

<meta http-equiv="X-UA-Compatible" content="IE=edge">
<link rel="stylesheet" type="text/css" media="all" href="${pageContext.request.contextPath}/resources/css/styles-responsive.css"/>
<link rel="stylesheet" type="text/css" media="all" href="${pageContext.request.contextPath}/resources/css/bootstrap.min.css"/>
<link rel="stylesheet" type="text/css" media="all" href="${pageContext.request.contextPath}/resources/css/bootcards-desktop.min.css"/>
<link rel="stylesheet" type="text/css" media="all" href="${pageContext.request.contextPath}/resources/css/bootcards-demo.css"/>
<link rel="stylesheet" type="text/css" media="all" href="${pageContext.request.contextPath}/resources/css/font-awesome.min.css"/>
<script type='text/javascript' src="${pageContext.request.contextPath}/resources/js/jquery-3.1.0.min.js"/></script>

	<div id="contactCard">
		
		<div class="panel panel-default">
			<div class="panel-heading clearfix">
				<h3 class="panel-title pull-left"><b>Ficha jugador</b></h3>
			      <a class="btn btn-success pull-right" href="/team/${idEquipo}/playerdata/sign/${playerData.idJugador}">
			        <i class="fa fa-pencil"></i>
			        Contratar
			      </a>
			</div>
			<div class="list-group">
				<div class="list-group-item">
					<img class="img-rounded pull-left" id="imagen" src="<c:url value='/resources/images/players/${playerData.imagen}'/>" onError="this.onerror=null;this.src='<c:url value='/resources/images/unknown.jpg'/>';"/>
					<label>Nombre</label>								
					<h4 class="list-group-item-heading" id="nombre">${playerData.nombre}</h4>
				</div>
				<div class="list-group-item">
					<img class="img-rounded pull-left" src="<c:url value='/resources/images/${playerData.logoEquipoOriginal}'/>" onError="this.onerror=null;this.src='<c:url value='/resources/images/unknown.jpg'/>';"/>
					<label>Equipo real</label>
					<h4 class="list-group-item-heading" id="equipoOriginal">${playerData.equipoOriginal}</h4>
				</div>
				<div class="list-group-item">
					<label>Media</label>
					<h4 class="list-group-item-heading" id="media">${playerData.media}</h4>
				</div>
				<div class="list-group-item">
					<label>Edad</label>
					<h4 class="list-group-item-heading" id="edad">${playerData.edad}</h4>
				</div>
<!-- 				<div class="list-group-item"> -->
<!-- 					<label>Posición</label> -->
<%-- 					<h4 class="list-group-item-heading" id="edad">${playerData.posicion1}</h4> --%>
<!-- 				</div>							 -->
				<div class="list-group-item">
					<label>Años restantes de contrato</label>
					<h4 class="list-group-item-heading" id="contractYears">${playerData.contractYears}</h4>
				</div>
				<div class="list-group-item">
					<label>Salario actual</label>
					<h4 class="list-group-item-heading" id="salary">
						<fmt:formatNumber value="${playerData.currentSalary}" type="currency" currencySymbol="$"  pattern="¤#,##0;¤ -#,##0"/>									
					</h4>
				</div>
			</div>
			<div class="panel-footer">
				<small class="pull-left">Powered by LigasNBA.es</small>
			</div>		
		</div>
		
		<!--list of notes-->
		
		<div>
		
			<div class="panel panel-default">
				<div class="panel-heading clearfix">
					<h3 class="panel-title pull-left"><b>Plantilla</b></h3>
				      <a class="btn btn-danger pull-right" href="#">
				        <i class="fa fa-trash-o"></i>
				        Reiniciar
				      </a>														
				</div>					
				<div class="list-group">
				   <c:forEach items="${plantillaData}" var="jugadorPlantilla" varStatus="status">
						<div class="list-group-item pjax" 
				 			href="/team/${idEquipo}/playerdata/${jugadorPlantilla.idJugador}">
							<img class="img-rounded pull-left" src="<c:url value='/resources/images/players/${jugadorPlantilla.imagen}'/>" onError="this.onerror=null;this.src='<c:url value='/resources/images/unknown.jpg'/>';"/>					 			
							<h4 class="list-group-item-heading">${jugadorPlantilla.nombre}</h4>
							<p class="list-group-item-text">${jugadorPlantilla.media}</p>
													
						</div>				   
				   </c:forEach>
				</div>		
			</div>
		
		</div>					
	
	</div>
	<script type='text/javascript' src="${pageContext.request.contextPath}/resources/js/fastclick.min.js"/></script>
<script type='text/javascript' src="${pageContext.request.contextPath}/resources/js/raphael-min.js"/></script>
<script type='text/javascript' src="${pageContext.request.contextPath}/resources/js/morris.min.js"/></script>
<script type='text/javascript' src="${pageContext.request.contextPath}/resources/js/bootstrap.min.js"/></script>
<script type='text/javascript' src="${pageContext.request.contextPath}/resources/js/bootcards.min.js"/></script>
<script type='text/javascript' src="${pageContext.request.contextPath}/resources/js/jquery.pjax.js"/></script>
<script type='text/javascript' src="${pageContext.request.contextPath}/resources/js/bootcards-demo-app.js"/></script>
<script type="text/javascript">
    
    if ( $('.list-group a.active').length === 0 ) {
      $('.list-group a').first().addClass('active');
    }

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
