

<html lang="es">
<%@ page language="java" contentType="text/html;charset=UTF-8" %> 
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix='fn' uri='http://java.sun.com/jsp/jstl/functions' %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="org.springframework.security.core.context.SecurityContextHolder" %>
<%@ page import="es.ligasnba.app.util.constants.Constants" %>


<head>
	<meta charset="utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge"> 
	<!--<link rel="shortcut icon" href="../../docs-assets/ico/favicon.png">-->
	<meta name="mobile-web-app-capable" content="yes" />
	<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0, user-scalable=no">
  
	<script type='text/javascript' src='<c:url value="/resources/js/jquery-3.1.0.min.js"/>'></script>
	<script type='text/javascript' src='<c:url value="/resources/js/noty/jquery.noty.js"/>'></script>
	<script type='text/javascript' src='<c:url value="/resources/js/noty/layouts/bottom.js"/>'></script>
	<script type='text/javascript' src='<c:url value="/resources/js/noty/themes/default.js"/>'></script> 
	

	
	    
		

<jsp:include page="menu.jsp" />
<script>var isDesktop = true;</script>
<script>
$( document ).ready(function() {
// 	var isActivated = ${isActivated};
// 	if( !isActivated )
// 		generateNoty("La cuenta no ha sido activada. Por favor, compruebe su email.",'error');

		
});

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


<title>Team Manager</title>
</head>

<c:url value="createcompetitionform" var="createCompetitionUrl"/>
<c:url value="joincompetitionform" var="joinCompetitionUrl"/>
<c:url value="competition" var="CompetitionUrl"/>

<body id="bootcards">
	<div class="container bootcards-container" id="main">
	  <div class="row">
	  	<div class="col-sm-6">
			<c:if test="${not empty teamList}">					
			<sec:authorize access="isAuthenticated()">
	  	
		  		<div class="panel panel-default bootcards-summary">
	
							
					<div class="panel-heading">
						<h3 class="panel-title">Selecciona competición</h3>
					</div>
					<div class="panel-body">			
					
						<sec:authorize access="isAuthenticated()">				
							<p>Bienvenido <span id="username"><%=SecurityContextHolder.getContext().getAuthentication().getName()%></span>!</p>
						</sec:authorize>
						
						
						
						<div class="row">
							
						      <c:forEach var="team" items="${teamList}">
						      	<div class="col-xs-12 col-sm-12">
								<a class="bootcards-summary-item" href="${CompetitionUrl}/${team.competicion.idCompeticion}" data-pjax="#main"
									data-title="${team.competicion.nombre}" style="padding-top:18px;">
										<i class="fa fa-3x fa-building-o"></i>
										<h4>											
											<div align = center><img src="<c:url value='/resources/images/${team.logo}'/>" /></div>
											${team.competicion.nombre}
										
										</h4>
								</a>							      
								</div>
						      </c:forEach>
			          		
		          		</div>					
						</div>
				
				</div>
			</sec:authorize>
			</c:if>				
		</div>
	</div>
</div>
 




</body>
</html>
