<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ page import="org.springframework.security.core.context.SecurityContextHolder" %>
<%@ page language="java" contentType="text/html;charset=UTF-8" %>
 <link rel="stylesheet" type="text/css" media="all" href="<c:url value="/resources/css/bootstrap.min.css" context="/teammanagement"/>">
<c:url value="/" var="homeUrl"/>
<c:url value="/user" var="userUrl"/>
<c:url value="/logout" var="logoutUrl"/>
<c:url value="/profile" var="profile"/>
 
<div class="nav nav-tabs">
<ul class="nav nav-tabs">
<li role="presentation" class="active"><a href="${homeUrl}">Inicio</a></li>
<sec:authorize access="hasRole('ROLE_ADMIN')">
<li><a href="${adminUrl}">Admin</a></li>
</sec:authorize>
<sec:authorize access="isAnonymous()">
<li role="presentation"><a href="${homeUrl}login">Login</a></li>
<li><a href="${homeUrl}register">Registrarse</a></li>
</sec:authorize>
<sec:authorize access="isAuthenticated()">
<li role="presentation"><a href="${profile}">Perfil</a></li>
</sec:authorize>
<sec:authorize access="isAuthenticated()">
<li role="presentation"><a href="${logoutUrl}">Logout</a></li>
</sec:authorize>
</ul>

</div>
