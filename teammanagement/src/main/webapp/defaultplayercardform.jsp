<%@ page language="java" contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<meta name="viewport" content="width=device−width, initial−scale=1.0" />
<html>



<head>


<link rel="stylesheet" type="text/css" media="all" href="<c:url value="/resources/css/styles-responsive.css" context="/teammanagement"/>">
<script type='text/javascript' src='<c:url value="/resources/js/jquery-3.1.0.min.js"/>'></script>
<script type='text/javascript' src='<c:url value="/resources/js/jquery.pjax.js"/>'></script>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<script type='text/javascript' src='<c:url value="/resources/js/jquery-3.1.0.min.js"/>'></script>
<script type='text/javascript' src='<c:url value="/resources/js/bootstrap.min.js"/>'></script>
<script type='text/javascript' src='<c:url value="/resources/js/bootcards.min.js"/>'></script>
<link rel="stylesheet" type="text/css" media="all" href="<c:url value="/resources/css/bootstrap.min.css" context="/teammanagement"/>">
<link rel="stylesheet" type="text/css" media="all" href="<c:url value="/resources/css/bootcards-desktop.min.css" context="/teammanagement"/>">
<link rel="stylesheet" type="text/css" media="all" href="<c:url value="/resources/css/bootcards-demo.css" context="/teammanagement"/>">
<link rel="stylesheet" type="text/css" media="all" href="<c:url value="/resources/css/font-awesome.min.css" context="/teammanagement"/>">
<title>${teamForm.equipo.nombre}</title>

</head>

<body>
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

  </script>
  <div class="container bootcards-container" id="main">
    
   
		<div class="row">
				
  			<div class="container bootcards-container" id="main">

				<!--contact details & notes-->
				
				<div id="contactCard">
					
					<div class="panel panel-default">
						<div class="panel-heading clearfix">
							<h3 class="panel-title pull-left">Ficha jugador</h3>
							<div class="btn-group pull-right visible-xs">
								<a class="btn btn-primary" href="/contacts/VBMJHwcLX3Vx/edit"
									data-toggle="modal"
									data-target="#editModal">
									<i class="fa fa-pencil"></i>
									<span>Editar</span>
								</a>
							</div>	
							<a class="btn btn-primary pull-right hidden-xs" href="/contacts/VBMJHwcLX3Vx/edit"
								data-toggle="modal"
								data-target="#editModal">
								<i class="fa fa-pencil"></i>
								<span>Edit</span>
							</a>
						</div>
						<div class="list-group">
							<div class="list-group-item">
								<img class="img-rounded pull-left" src="<c:url value='/resources/images/players/${teamForm.listaInfoJugadores[0].imagen}'/>" onError="this.onerror=null;this.src='<c:url value='/resources/images/unknown.jpg'/>';"/>
								<label>Nombre</label>
								<h4 class="list-group-item-heading">${teamForm.listaInfoJugadores[0].nombre}</h4>
							</div>
							<div class="list-group-item">
								<img class="img-rounded pull-left" src="<c:url value='/resources/images/${teamForm.listaInfoJugadores[0].logoEquipoOriginal}'/>" onError="this.onerror=null;this.src='<c:url value='/resources/images/unknown.jpg'/>';"/>
								<label>Equipo real</label>
								<h4 class="list-group-item-heading">${teamForm.listaInfoJugadores[0].equipoOriginal}</h4>
							</div>
							<div class="list-group-item">
								<label>Media</label>
								<h4 class="list-group-item-heading">${teamForm.listaInfoJugadores[0].media}</h4>
							</div>
							<div class="list-group-item">
								<label>Edad</label>
								<h4 class="list-group-item-heading">${teamForm.listaInfoJugadores[0].edad}</h4>
							</div>
							<a class="list-group-item">
								<label>Años restantes de contrato</label>
								<h4 class="list-group-item-heading">${teamForm.listaInfoJugadores[0].contractYears}</h4>
							</a>
							<a class="list-group-item">
								<label>Salario actual</label>
								<h4 class="list-group-item-heading">									
         							<fmt:formatNumber value="${teamForm.listaInfoJugadores[0].currentSalary}" type="currency" currencySymbol="$"  pattern="¤#,##0;¤ -#,##0"/>
								</h4>
							</a>
						</div>
						<div class="panel-footer">
							<small class="pull-left">Powered by LigasNBA.es</small>
						</div>		
					</div>
				
				</div>
			

			</div>
		</div>


  </div>
  <div class="modal fade" id="editModal" tabindex="-1" role="dialog" aria-labelledby="editModal" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content"></div>
		</div>
	</div>
  <div class="modal fade" id="docsModal" tabindex="-1" role="dialog" aria-labelledby="docsModal" aria-hidden="true">
    <div class="modal-dialog">
      <div class="modal-content"></div>
    </div>
  </div>
  <script type="text/javascript">


    bootcards.init( {
        offCanvasHideOnMainClick : true,
        offCanvasBackdrop : true,
        enableTabletPortraitMode : true,
        disableRubberBanding : true,
        disableBreakoutSelector : 'a.no-break-out'
      });

  </script>
</body>

</html>

