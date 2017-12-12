<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ page import="org.springframework.security.core.context.SecurityContextHolder" %>
<%@ page language="java" contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix='fn' uri='http://java.sun.com/jsp/jstl/functions' %>
<%@ page import="es.ligasnba.app.util.constants.Constants" %>

<%-- <link rel="stylesheet" type="text/css" media="all" href="<c:url value="/resources/css/bootcards-ios.min.css" />"> --%>
<link rel="stylesheet" type="text/css" media="all" href="${pageContext.request.contextPath}/resources/css/bootstrap.min.css" />
<link rel="stylesheet" type="text/css" media="all" href="${pageContext.request.contextPath}/resources/css/bootcards-desktop.min.css" />
<link rel="stylesheet" type="text/css" media="all" href="${pageContext.request.contextPath}/resources/css/bootcards-demo.css" />
 

<c:url value="/" var="homeUrl"/>
<c:url value="/user" var="userUrl"/>
<c:url value="/logout" var="logoutUrl"/>
<c:url value="/profile" var="profile"/>
<c:url value="createcompetitionform" var="createCompetitionUrl"/>
<c:url value="joincompetitionform" var="joinCompetitionUrl"/>
<c:url value="competition" var="CompetitionUrl"/>

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
      <a class="navbar-brand no-break-out"  title="TeamManagement" href="${homeUrl}">TeamManagement</a>
    </div>
	
    <div class="navbar-collapse collapse">
      <ul class="nav navbar-nav">
		<sec:authorize access="hasRole('ROLE_ADMIN')">
		<li><a href="${adminUrl}">Admin</a></li>
		</sec:authorize>
		<sec:authorize access="isAnonymous()">
		<li role="presentation"><a href="${homeUrl}login">Login</a></li>
		<li><a href="${homeUrl}register">Registrarse</a></li>
		</sec:authorize>
<%-- 		<sec:authorize access="isAuthenticated()"> --%>
<%-- 		<li role="presentation"><a href="${profile}">Perfil</a></li> --%>
<%-- 		</sec:authorize> --%>
		<sec:authorize access="isAuthenticated()">				
<%-- 		<li role="presentation"><a href="${createCompetitionUrl}">Nueva competición</a></li> --%>
		<li role="presentation"><a href="${joinCompetitionUrl}">Unirse a competición</a></li>		
		<li role="presentation"><a href="${logoutUrl}">Logout</a></li>
		</sec:authorize>
				        
      </ul>
    </div><!-- /.navbar-collapse -->
  </div><!-- /.container-fluid -->
</nav>
<script type='text/javascript' src='${pageContext.request.contextPath}/resources/js/jquery-3.1.0.min.js'></script>
<script type='text/javascript' src='${pageContext.request.contextPath}/resources/js/jquery-ui.js'></script>
<script type='text/javascript' src='${pageContext.request.contextPath}/resources/js/bootstrap.min.js'></script>
<script type='text/javascript' src='${pageContext.request.contextPath}/resources/js/bootcards.min.js'></script>


