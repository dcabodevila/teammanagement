<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<meta http-equiv="X-UA-Compatible" content="IE=edge"> 
<meta name="mobile-web-app-capable" content="yes" />
<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0, user-scalable=no">
<%@ page import="es.ligasnba.app.util.constants.Constants" %>
<%@ page language="java" contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>


<script type='text/javascript' src='<c:url value="/resources/js/jquery-3.1.0.min.js"/>'></script>

<link rel="stylesheet" type="text/css" media="all" href="<c:url value="/resources/css/bootstrap.min.css" context="/teammanagement"/>">
<link rel="stylesheet" type="text/css" media="all" href="<c:url value="/resources/css/bootcards-desktop.min.css" context="/teammanagement"/>">
<link rel="stylesheet" type="text/css" media="all" href="<c:url value="/resources/css/font-awesome.min.css" context="/teammanagement"/>">

<c:url value="/" var="homeUrl"/>
  
<nav class="navbar navbar-default navbar-fixed-top" role="navigation">
  <div class="container">
    <!-- Brand and toggle get grouped for better mobile display -->
    <div class="navbar-header">
      <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse">
        <span class="sr-only">Toggle navigation</span>
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>
      </button>
<!-- 	  <button type="button" class="btn btn-primary visible-xs-block visible-sm-block" onclick="history.back()">Atras</button>       -->
      <button type="button" id="botonAtras" onClick="mostarLista()" class="hidden" style="margin-top:10;margin-left:5;">
        Atrás
      </button>
      <!-- menu button to show/ hide the off canvas slider -->
      <button type="button" class="btn btn-default btn-menu navbar-left pull-left offCanvasToggle" data-toggle="offcanvas">
        <i class="fa fa-lg fa-bars"></i><span>Menu</span>
      </button>       
	  <a class="navbar-brand no-break-out"  title="TeamManager" href="${homeUrl}">TeamManager</a>
    </div>
	
    <div class="navbar-collapse collapse" id="bs-example-navbar-collapse-1">
      <ul class="nav navbar-nav">      
		<c:if test="${menuNavigationForm.estadoCompeticion eq 5}">
			<li><a href="/teammanagement/team/teamcreator/${menuNavigationForm.idEquipo}">Creador de plantilla</a></li>
		</c:if>
		<c:if test="${menuNavigationForm.estadoCompeticion eq 1}">
			<li><a href="/teammanagement/team/seleccionPaquete/${menuNavigationForm.idEquipo}">Paquetes de ingresos</a></li>
		</c:if>
		<c:if test="${menuNavigationForm.estadoCompeticion eq 4}">
			<li><a href="/teammanagement/team/fa/${menuNavigationForm.idEquipo}">Próximos FA</a></li>
		</c:if>		
		<li><a href="/teammanagement/team/${menuNavigationForm.idEquipo}">Plantilla</a></li>
		<li><a href="/teammanagement/games/${menuNavigationForm.idEquipo}">Partidos</a></li>
	    <li>	    	   
	      <a href="/teammanagement/market/${menuNavigationForm.idEquipo}/trades">Traspasos</a>
	    </li>
		<li><a href="/teammanagement/market/freeagents/${menuNavigationForm.idCompeticion}">Agentes Libres</a></li>	    
		<li><a href="/teammanagement/contracts/${menuNavigationForm.idEquipo}">Contratos</a></li>
		<li><a href="/teammanagement/finanzas/${menuNavigationForm.idEquipo}">Finanzas</a></li>
<%-- 		<li><a href="/teammanagement/competition/${menuNavigationForm.idEquipo}/configuration">Ajustes</a></li> --%>
		<c:if test="${isAdmin}">
			<li><a href="/teammanagement/admin/${menuNavigationForm.idCompeticion}/calendar">Comisionado</a></li>
		</c:if>				       
      </ul>
    </div>
  </div>
</nav>
<script type='text/javascript' src='<c:url value="/resources/js/bootstrap.min.js"/>'></script>
<script type='text/javascript' src='<c:url value="/resources/js/bootcards.min.js"/>'></script>
  <script type="text/javascript">
    //highlight first list group option (if non active yet)
    if ( $('.list-group a.active').length === 0 ) {
      $('.list-group a').first().addClass('active');
    }

    bootcards.init( {
        offCanvasHideOnMainClick : true,
        offCanvasBackdrop : true,
        enableTabletPortraitMode : true,
        disableRubberBanding : true,
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
