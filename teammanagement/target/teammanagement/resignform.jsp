<%@ page language="java" contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>


<html>
<%@ page import="es.ligasnba.app.util.constants.Constants" %>

<head>

	<link rel="stylesheet" type="text/css" media="screen" href="${pageContext.request.contextPath}/resources/css/cupertino/jquery-ui-1.10.3.custom.css"/>
	<link rel="stylesheet" type="text/css" media="all" href="${pageContext.request.contextPath}/resources/css/Animate.css"/>
	<link rel="stylesheet" type="text/css" media="all" href="${pageContext.request.contextPath}/resources/css/bootstrap.min.css"/>  
	<script type='text/javascript' src="${pageContext.request.contextPath}/resources/js/jquery-3.1.0.min.js"/></script>
	<script type='text/javascript' src="${pageContext.request.contextPath}/resources/js/jquery-ui.js"/></script>
	<script type='text/javascript' src="${pageContext.request.contextPath}/resources/js/jquery.ui.touch-punch.min.js"/></script>
	<script type='text/javascript' src="${pageContext.request.contextPath}/resources/js/jquery.number.min.js"/></script>
	<script type='text/javascript' src="${pageContext.request.contextPath}/resources/bootstrap-notify-master/bootstrap-notify.min.js"/></script>
	<script type='text/javascript' src="${pageContext.request.contextPath}/resources/js/bootstrap.min.js"/></script>

	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">



<script>

function notify(mensaje, tipo){
	$.notify({
		title: "",
		message: mensaje
	},{
		type: tipo,
		delay: 1500,
		placement: {
			align : "center"
		},
		animate:{
			enter: "animated fadeInDown",
			exit: "animated fadeOutDown"
		}
	});    	
}

function changeSignAndTradeModal(){
	loadPlantillaNextSeason();
	showTipoOfertaInfoEconomica(3);	
}
function cancelResign(){
	$('#modalContent').removeClass("visible");	
	$('#modalContent').addClass('hidden');
}
function offerResign(){

    
	$('#offerResignBtn').prop('disabled',true);

	var playerId = ${contractData.idJugador};
	var teamId = ${offerTeam};
	var bSalary = $( "#baseSalary" ).slider( "value" );
	var yrs = $( "#years" ).slider( "value" );
	var inc = $( "#increase" ).slider( "value" );
	var useMidLevel = $( "#useMidLevel" ).prop( "checked" );
	if (useMidLevel==null){
		useMidLevel = false;
	}
	var listaJugadoresST = getListaJugadoresSignAndTrade();
	$.ajax({

		type:"post",
		url: "/contracts/resignoffer",	
		data: ({ idJugador: playerId, idEquipo:teamId, baseSalary:bSalary, years : yrs, increase:inc, useMidLevel : useMidLevel, listaJugadoresST : listaJugadoresST }),
		dataType: "json",
		traditional: true,
		success: function(response){

			if (response.success){
				notify(""+response.message+"", "success")
				
			}
			else{
				notify(""+response.message+"", "danger")			
			}

		},
		error: function(){
			notify("Error en la llamada al servidor", "danger")
		}
	});



}
</script>

<script>

$( document ).ready(function() {
	var minSalary = ${contractData.minSalary};
	var maxSalary = ${contractData.maxSalary};
	var currentSalary = ${contractData.currentSalary};	
	var maxSeasons = ${contractData.maxSeasons};
    var capSpace = ${contractData.capSpace};
    var presupuestoTotal = ${contractData.presupuestoTotal};
    var sumaSalarial = ${contractData.sumaSalarial};
    var luxuryTax = ${contractData.topSalary};
    var presupuestoRestante = ${contractData.presupuestoRestante};
    var midLevelException = ${contractData.midLevelException};
    var capConsumido = ${contractData.capConsumido};
    	
	$( "#baseSalary" ).slider({
		min : minSalary,	
		max : maxSalary,
		value: currentSalary,
		slide:function(event, ui){

			$( "#baseSalaryText" ).text($.number( ui.value, 0 ) +"$");
		
		}
	});
	$("#baseSalary").draggable();
	$( "#baseSalaryText" ).text( $.number( $( "#baseSalary" ).slider( "value" ), 0 ) +"$" );

	$( "#years" ).slider({
		min : 1,
		max : maxSeasons,
		value : maxSeasons,
		slide:function(event, ui){

			$( "#yearsText" ).text( ui.value );
						
		}
		
	});
	$("#years").draggable();
	$( "#yearsText" ).text( $( "#years" ).slider( "value" ) );


	$( "#increase" ).slider({
		min : -5,
		max : 5,
		value : 5,
		slide:function(event, ui){

			$( "#increaseText" ).text( ui.value + "%");
						
		}
	});
	$("#increase").draggable();
	$( "#increaseText" ).text( $( "#increase" ).slider( "value" )+"%" );

	$( "#capSpace" ).text($.number( capSpace, 0 ) +"$");
	$( "#presupuestoTotal" ).text($.number( presupuestoTotal, 0 ) +"$");
	$( "#sumaSalarial" ).text($.number( sumaSalarial, 0 ) +"$");
	$( "#presupuestoRestante" ).text($.number( presupuestoRestante, 0 ) +"$");
	$( "#capConsumido" ).text($.number( capConsumido, 0 ) +"$");
	
    $('#useMidLevel').change(function() {
        if($(this).is(":checked")) {
        	modifyBaseSalarySliders(midLevelException, midLevelException, midLevelException);
        }else {
        	modifyBaseSalarySliders(minSalary, maxSalary, currentSalary);
        }
        
       
    });
    
    var tipoOferta = ${contractData.tipoOferta};
    showTipoOfertaInfoEconomica(tipoOferta);
    
    if (($.number( presupuestoTotal, 0 ))>($.number( luxuryTax, 0 ))){

	    $('#infoLuxuryTax').removeClass("hidden");
	    $('#infoLuxuryTax').addClass("visible");
    }
    else {

	    $('#infoLuxuryTax').removeClass("visible");
	    $('#infoLuxuryTax').addClass("hidden");
    }
    
    if (($.number( capConsumido, 0 ))>($.number( 0, 0 ))){
        $('#capConsumidoDiv').removeClass('hidden');
        $('#capConsumidoDiv').addClass("visible");
    }
    else {
        $('#capConsumidoDiv').removeClass('visible');
        $('#capConsumidoDiv').addClass("hidden");    	
    }
    
    $('#modalContent').removeClass('hidden');
    $('#modalContent').addClass("visible");
    
 });
 
 function modifyBaseSalarySliders(minimo, maximo, valor){ 	
	 
		$("#baseSalary").slider('option',{min: minimo, max: maximo});
    	$("#baseSalary").slider('value',valor); 
    	$( "#baseSalaryText" ).text($.number( valor, 0 ) +"$");
 }
 
 function showTipoOfertaInfoEconomica(idTipoOferta){
	 if ((idTipoOferta==1)){
		$('#espacioSalarialDiv').removeClass("visible");
		$('#espacioSalarialDiv').addClass("hidden");
		$('#buttonSignAndTrade').addClass("hidden");
	 }
	 if (idTipoOferta==2){
			$('#espacioSalarialDiv').removeClass("hidden");
			$('#espacioSalarialDiv').addClass("visible");
		 
			$('#presupuestoTotalDiv').removeClass("visible");
			$('#presupuestoTotalDiv').addClass("hidden");
			
			$('#presupuestoRestanteDiv').removeClass("visible");
			$('#presupuestoRestanteDiv').addClass("hidden");
			$('#buttonSignAndTrade').removeClass("hidden");
			$('#buttonSignAndTrade').addClass("visible");
			
	 }	 
	 if (idTipoOferta==3){
		 	$('#fotoJugador').addClass("hidden");
			$('#espacioSalarialDiv').removeClass("hidden");
			$('#espacioSalarialDiv').addClass("visible");
		 
			$('#presupuestoTotalDiv').removeClass("visible");
			$('#presupuestoTotalDiv').addClass("hidden");
			$('#presupuestoRestanteDiv').removeClass("visible");
			$('#presupuestoRestanteDiv').addClass("hidden");			
			
			$('#presupuestoRestanteDiv').removeClass("visible");
			$('#presupuestoRestanteDiv').addClass("hidden");
			$('#useMidLevel').prop('checked', false);
			$('#midLevelExceptionDiv').addClass("hidden");
			$('#buttonSignAndTrade').addClass("hidden");
			$('#modalTitle').text("Sign and Trade");
			$('#infoMidLevel').addClass("hidden");
			$('#infoUnaOfertaDia').addClass("hidden");
			$('#tablaPlantillaDiv').removeClass("hidden");
			$('#tablaPlantillaDiv').addClass("visible");
			
	 }
	 if (idTipoOferta==4) {
			$('#espacioSalarialDiv').removeClass("hidden");
			$('#espacioSalarialDiv').addClass("visible");
		 
			$('#presupuestoTotalDiv').removeClass("visible");
			$('#presupuestoTotalDiv').addClass("hidden");
			
			$('#presupuestoRestanteDiv').removeClass("visible");
			$('#presupuestoRestanteDiv').addClass("hidden");
			$('#buttonSignAndTrade').removeClass("visible");
			$('#buttonSignAndTrade').addClass("hidden");
	 }
	 
 }
 
 
 function getListaJugadoresSignAndTrade(){

	var idsJugadores = [];
	
    $('#tablaPlantilla').find('tr').each(function () {
        var row = $(this);
        if (row.find('input[type="checkbox"]').is(':checked')) {
            var idJugadorSeleccionado = ($(this).find("td").eq(0).html());
            idsJugadores.push(idJugadorSeleccionado);
        }
    });
    
    return idsJugadores;
     	 
 }
 function add(a, b) {
	    return a + b;
	}
 function substract(a, b) {
	    return a - b;
	}
 
 function reloadControlsFromSignAndTrade(){
		var salarios = [];
		var capSpace = [];
		var sumaSalarial = Number(${contractData.sumaSalarial});
		var minSalary = ${contractData.minSalary};
		var topSalary = ${contractData.topSalary};
		
		capSpace.push(Number(${contractData.capSpace}));		
		
	    $('#tablaPlantilla').find('tr').each(function () {
	        var row = $(this);
	        if (row.find('input[type="checkbox"]').is(':checked')) {
	            var salarioJugadorSeleccionado = ($(this).find("td").eq(3).html());
	            salarios.push(Number(salarioJugadorSeleccionado));
	        }
	    });
	    
   	    var salarioOfrecer = minSalary;
   	        
	    var sumaJugadoresTrade = Number(salarios.reduce(add, 0));
	    capSpace.push(sumaJugadoresTrade);
	    var capSpaceDespuesSignAndTrade = capSpace.reduce(add, 0);
	    $( "#capSpace" ).text($.number( capSpaceDespuesSignAndTrade, 0 ) +"$");

	    sumaSalarial = sumaSalarial - sumaJugadoresTrade;
   	    $( "#sumaSalarial" ).text($.number( sumaSalarial, 0 ) +"$");
   	    
   	    if (capSpaceDespuesSignAndTrade>minSalary){
   	    	
   	    	salarioOfrecer = capSpaceDespuesSignAndTrade;
   	    	
   	    	if (salarioOfrecer>topSalary){
   	    		salarioOfrecer = topSalary;
   	    	}
   	    	
   	    }
   	    
   	 	modifyBaseSalarySliders(minSalary, salarioOfrecer, salarioOfrecer);
	    
	    
 }
 
 function loadPlantillaNextSeason(){
	 var idEquipo = ${offerTeam};
 	$.ajax({
 		type:"get",
 		url: "/team/getPlantillaNextSeason",	
 		data: ({ idEquipo: idEquipo }),
 		dataType: "json",
 		success: function(response){
 			$( "#tablaPlantilla" ).empty();
 			$( "#tablaPlantilla" ).append('<thead><tr class="active"><th class="hidden">ID</th><th></th><th>Nombre</th><th>Salario</th></tr></thead><tbody>');
 			for (var i in response){
 				$( "#tablaPlantilla" ).append('<tr><td class="hidden">'+response[i].idJugador+'</td><td><input id="player'+response[i].idJugador+'" onclick="reloadControlsFromSignAndTrade();" type="checkbox" value=""></td><td>'+response[i].nombre+'</td><td align="right">'+response[i].nextSalary +'</td></tr>');
			}
 			$( "#tablaPlantilla" ).append('</tbody>'); 			
 			   			
 		},
 		error: function(){
 			alert('error');
 		}
 	});	    	
 } 
 
</script>


</head>

<body>
		<div id="modalContent">			
		<form class="form-horizontal"  action="">
			
		<div class="modal-header">
		
				<div class="btn-group pull-left">
					<button class="btn btn-danger" data-dismiss="modal" onClick="cancelResign();">
						Cancelar
					</button>
				</div>
			
				<div class="btn-group pull-right">
					<button class="btn btn-success" data-dismiss="modal" onClick="offerResign();">
						Enviar
					</button>
				</div>
			<h4 class="modal-title" id="modalTitle">
					${contractData.titulo}
			</h4>		
		</div>
				
		<div class="modal-body" id="modalBody">
		
			
			
		
				<div class="form-group">
					<img id="fotoJugador" class="img-responsive center-block" src="${pageContext.request.contextPath}/resources/images/players/${contractData.imagen}" height= "150" width="200"/>
				</div>
				<div class="form-group">
					<label class="col-xs-4 control-label">Nombre</label>
					<div class="col-xs-5">							
						<h4 class="list-group-item-heading" id="nombre">${contractData.nombre}</h4>
					</div>
				</div>
				<div class="form-group">
					<label class="col-xs-4 control-label">Salario</label>
					<div class="col-xs-6" id="baseSalary">						
						<span id=baseSalaryText style="position:absolute; padding-top:10"></span>	
					</div>				
				</div>
				<c:if test="${contractData.visibleMidLevelException}">
					<div class="form-group" id="midLevelExceptionDiv">
					<label class="col-xs-4 control-label"></label>
						<div class="col-xs-6" id="midLevelException">
						<input id="useMidLevel" type="checkbox" value=""> Usar excepción nivel medio
						</div>	
					</div>				
				</c:if>
							
						  

								
				<div class="form-group">
					<label class="col-xs-4 control-label">Incremento</label>
					<div class="col-xs-6" id="increase">
						<span id=increaseText style="position:absolute; padding-top:10"></span>
					</div>
				</div>
				<div class="form-group">
					<label class="col-xs-4 control-label">Años</label>
					<div class="col-xs-6" id="years">
						<span id=yearsText style="position:absolute; padding-top:10"></span>
					</div>
				</div>

			  <div class="table-responsive hidden" id="tablaPlantillaDiv">
			    <table class="table table-hover" id="tablaPlantilla">
			    </table>
			  </div>
			  	
									
				<h4>Info económica equipo:</h4>

				<div class="form-group" id="espacioSalarialDiv">
					<label class="col-xs-4 control-label">Espacio salarial</label>
					<div class="col-xs-6" align="right">							
						<h4 class="list-group-item-heading" id="capSpace"></h4>
					</div>
				</div>
												
				<div class="form-group" id="presupuestoTotalDiv">
					<label class="col-xs-4 control-label">Presupuesto total</label>
					<div class="col-xs-6" align="right">							
						<h4 class="list-group-item-heading" id="presupuestoTotal"></h4>
					</div>
				</div>
				
				<div class="form-group" id="sumaSalarialDiv">
					<label class="col-xs-4 control-label">Suma salarial</label>
					<div class="col-xs-6" align="right">							
						<h4 class="list-group-item-heading" id="sumaSalarial"></h4>
					</div>
				</div>

				<div class="form-group" id="presupuestoRestanteDiv">
					<label class="col-xs-4 control-label">Presupuesto restante</label>
					<div class="col-xs-6" align="right">							
						<h4 class="list-group-item-heading" id="presupuestoRestante"></h4>
					</div>
				</div>
				<div class="form-group" id="capConsumidoDiv">
					<label class="col-xs-4 control-label">Espacio salarial consumido</label>
					<div class="col-xs-6" align="right">							
						<h4 class="list-group-item-heading" id="capConsumido"></h4>
					</div>
				</div>				

							
			</div>

		

		
		</form>

		<div class="panel panel-default" id="buttonSignAndTrade">
		  <div class="panel-heading clearfix">
		    <h3 class="panel-title pull-left" style="padding-top:7"><strong>Ofrecer Sign And Trade</strong></h3>

				<button class="btn btn-primary pull-right col-xs-4" style="padding-left:10;padding-right:10 " onClick="changeSignAndTradeModal()">
					<i class="fa fa-handshake-o"></i>
				</button>

		  </div>
		</div>		
		

		<div class="modal-footer">
			<div class="alert alert-info col-xs-12" align="left" id="infoUnaOfertaDia">
			  <strong>Info:</strong> Solo se podrá realizar una oferta por día al mismo jugador. 
			</div>
			<c:if test="${contractData.visibleMidLevelException}">
				<div class="alert alert-info col-xs-12" align="left" id="infoMidLevel">
				  <strong>Info:</strong> La excepción de nivel medio solo se puede utilizar 1 vez por temporada. 
				</div>
			</c:if>
			<div class="alert alert-warning col-xs-12" align="left" id="infoLuxuryTax">
			  <strong>Aviso:</strong> Al exceder la tasa de lujo se paga 1,5$ de multa por cada 1$ excedido. (Se muestra descontado del presupuesto restante)   
			</div>
		</div>		
	</div>	
</body>

</html>


