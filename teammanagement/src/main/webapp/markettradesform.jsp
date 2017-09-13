<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<%@ page language="java" contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<html>
<link rel="stylesheet" type="text/css" media="screen" href="<c:url value="/resources/css/cupertino/jquery-ui-1.10.3.custom.css" context="/teammanagement"/>">  
<link rel="stylesheet" type="text/css" media="screen" href="<c:url value="/resources/css/ui.jqgrid.css" context="/teammanagement"/>">
<link rel="stylesheet" type="text/css" media="all" href="<c:url value="/resources/css/bootstrap.min.css" context="/teammanagement"/>">
<link rel="stylesheet" type="text/css" media="all" href="<c:url value="/resources/css/bootcards-desktop.min.css" context="/teammanagement"/>">
<link rel="stylesheet" type="text/css" media="all" href="<c:url value="/resources/css/styles.css" context="/teammanagement"/>">

<%@ page import="es.ligasnba.app.util.constants.Constants" %>


<script type='text/javascript' src='<c:url value="/resources/js/jquery-3.1.0.min.js"/>'></script>
<script type='text/javascript' src='<c:url value="/resources/js/jquery-ui-1.10.3.custom.js"/>'></script>
<script type='text/javascript' src='<c:url value="/resources/js/grid.locale-es.js"/>'></script>
<script type='text/javascript' src='<c:url value="/resources/js/jquery.jqGrid.min.js"/>'></script>
<script type='text/javascript' src='<c:url value="/resources/js/noty/jquery.noty.js"/>'></script>
<script type='text/javascript' src='<c:url value="/resources/js/noty/layouts/top.js"/>'></script>
<script type='text/javascript' src='<c:url value="/resources/js/noty/themes/default.js"/>'></script>

<head>
<title>Mercado de ${teamMarketForm.equipo.competicion.nombre}</title>

 <script type="text/javascript">
     var jq = jQuery.noConflict();
 </script>

<script type="text/javascript">
jq(function() {
	jq("#teamPlayers").jqGrid({
	
	
	    onSelectRow: function(id,status){
	
		updateTeamData();
		evaluateTrade();
	
	
	
	    },
	  
	   url:'/market/players/'+${teamMarketForm.equipo.idEquipo},
	   datatype: 'json',
	   mtype: 'GET',
	      colNames:['id', 'Nombre',  'Pos','Pos2','Media','Años','Salario'],
	      colModel:[
		{name:'idJugador',index:'idJugador', width:6,hidden:true},
		{name:'nombre',index:'nombre', width:30,align:'center'},
		{name:'posicion1',index:'posicion1', width:9, align:'center', sorttype:'int'},
		{name:'posicion2',index:'posicion2', width:9, align:'center', sorttype:'int'},
		{name:'media',index:'media', width:10,align:'right', sorttype:'int'},
		{name:'contractYears',index:'contractYears', width:10, align:'right', sorttype:'int'},
		{name:'currentSalary',index:'currentSalary', width:24, align:'right', sorttype:'int', formatter:'currency', formatoptions:{decimalSeparator:",", thousandsSeparator: ",", decimalPlaces: 0, prefix: "$ "}}		
	
	      ],
	      postData: {
	   },
	      sortable: true,
	      rowNum:20,
	      rowList:[20,40,60],
	      height: 300,
	      width: 800,
	      altRows: true,
	      autowidth: true,
	
	   rownumbers: true,
	      sortname: 'puntos',
	      viewrecords: false,
	      sortorder: "desc",
	      caption:"",
	      emptyrecords: "Empty records",
	      multiselect:true,
	      loadComplete: function() {
	
	   },
	      jsonReader : {
	          root: "rows",
	          page: "page",
	          total: "total",
	          records: "records",
	          repeatitems: false,
	          cell: "cell",
	          id: "id"
	      }
	 
	
	});
	
	  jq("#otherPlayers").jqGrid({
	
	    onSelectRow: function(id,status){
		updateTeamData();
		evaluateTrade();
	
	
	    },
	
	  
	   url:'',
	   datatype: 'json',
	   mtype: 'GET',
	      colNames:['id', 'Nombre',  'Pos','Pos2','Media','Años','Salario'],
	      colModel:[
		{name:'idJugador',index:'idJugador', width:6,editable:false,editoptions:{readonly:true,size:10},hidden:true},
		{name:'nombre',index:'nombre', width:30,editable:true, editrules:{required:true}, editoptions:{size:10}, align:'center'},
		{name:'posicion1',index:'posicion1', width:9,editable:true, editrules:{required:true}, editoptions:{size:10}, align:'center', sorttype:'int'},
		{name:'posicion2',index:'posicion2', width:9,editable:true, editrules:{required:true}, editoptions:{size:10}, align:'center', sorttype:'int'},
		{name:'media',index:'media', width:10,editable:true, editrules:{required:true}, editoptions:{size:10}, align:'right', sorttype:'int'},
		{name:'contractYears',index:'contractYears', width:10,editable:true, editrules:{required:true}, editoptions:{size:10}, align:'right', sorttype:'int'},
		{name:'currentSalary',index:'currentSalary', width:24,editable:true, editrules:{required:true}, editoptions:{size:10}, align:'right', sorttype:'int', formatter:'currency', formatoptions:{decimalSeparator:",", thousandsSeparator: ",", decimalPlaces: 0, prefix: "$ "}}		
	
	      ],
	      postData: {
	   },
	      sortable: true,
	      rowNum:20,
	      rowList:[20,40,60],
	      height: 300,
	      width: 800,
	      altRows: true,
	      autowidth: true,
	
	   rownumbers: true,
	      sortname: 'puntos',
	      viewrecords: true,
	      sortorder: "desc",
	      caption:"",
	      emptyrecords: "Empty records",
	      multiselect: true,
	      loadComplete: function() {
		updateTeamData();
	   },
	      jsonReader : {
	          root: "rows",
	          page: "page",
	          total: "total",
	          records: "records",
	          repeatitems: false,
	          cell: "cell",
	          id: "id"
	      }
	 
	
	});
});


jq( document ).ready(function() {
	
	var compId = ${teamMarketForm.equipo.competicion.idCompeticion};

	getTeamData(${teamMarketForm.equipo.idEquipo});	

	jq.ajax({

		type:"get",
		url: "/market/teamslist",	
		data: ({idCompeticion:compId}),
		dataType: "json",
		success: function(response){

			LoadTeamOptions(response);
		},
		error: function(){
			generateNoty("Ha habido un problema conectando con el servidor",'error');
		}
	});

	jq('#proposeTradeButton').prop('disabled',true);


});

function getTeamData(teamId){

	jq.ajax({

		type:"get",
		url: "/market/getteamdata",	
		data: ({idEquipo: teamId}),
		dataType: "json",
		success: function(response){
			setTeamData(response);
		},
		error: function(){
			generateNoty("Ha habido un problema conectando con el servidor",'error');
		}
	});
}

function setTeamData(response){



	if (response.equipo.idEquipo == ${teamMarketForm.equipo.idEquipo}){

		jq('#idTeamFrom').text( response.equipo.idEquipo ); 

		jq('#localLabel').text( response.equipo.nombre ); 

		jq('#defaultLocalSumSalaries').text( response.salarySpace);
	
		jq('#localSumSalaries').text(response.salarySpace);

		jq('#defaultLocalNumPlayers').text(response.numPlayers);		

		jq('#localNumPlayers').text(response.numPlayers);		
	}

	else{
		jq('#visitorLabel').text( response.equipo.nombre ); 

		jq('#defaultVisitorSumSalaries').text(response.salarySpace );

		jq('#visitorSumSalaries').text(response.salarySpace );

		jq('#defaultVisitorNumPlayers').text(response.numPlayers);

		jq('#visitorNumPlayers').text(response.numPlayers);

	
	}





}


function LoadTeamOptions(response){



	jq('#selectTradeTeam').empty();

	jq('#selectTradeTeam').append(new Option("Selecciona equipo", 0, true, true));

	for (var i in response){

		var team = response[i];
		if (team.idEquipo != ${teamMarketForm.equipo.idEquipo})
			jq('#selectTradeTeam').append(new Option(team.nombre, team.idEquipo, true, true));
	
	}

	jq('#selectTradeTeam option:eq(0)').prop('selected', true)


}

function selectTradeTeamChange(){

	var selected = jq( "#selectTradeTeam").val();

	if (selected!=0){
		jq("#otherPlayers").jqGrid('setGridParam',{url:'/market/players/'+selected}).trigger('reloadGrid');				

		getTeamData(selected);

	}



}

function proposeTrade(){

	var localPlayersIds = getSelectedLocalPlayerIds();	
	var visitorPlayersIds = getSelectedVisitorPlayerIds();

	var localIdTeam = jq( "#idTeamFrom").text();

	var visitorIdTeam = jq( "#selectTradeTeam").val();

	jq.ajax({

		type:"get",
		url: "/market/validateTrade",	
		data: ({teamFrom:localIdTeam, teamTo:visitorIdTeam,localPlayers: localPlayersIds, visitorPlayers:visitorPlayersIds}),
		dataType: "json",
		traditional: true,
		success: function(response){

			if (response.success)
				generateNoty(""+response.message+"",'success');
			else
				generateNoty(""+response.message+"",'error');

		},
		error: function(){
			generateNoty("Error en la llamada al servidor",'error');
		}
	});

	

}

function checkTrade(){

	var localPlayersIds = getSelectedLocalPlayerIds();	
	var visitorPlayersIds = getSelectedVisitorPlayerIds();

	var localIdTeam = jq( "#idTeamFrom").text();

	var visitorIdTeam = jq( "#selectTradeTeam").val();

	jq.ajax({

		type:"get",
		url: "/market/checkTrade",	
		data: ({teamFrom:localIdTeam, teamTo:visitorIdTeam,localPlayers: localPlayersIds, visitorPlayers:visitorPlayersIds}),
		dataType: "json",
		traditional: true,
		success: function(response){

			if (response.success)
				generateNoty(""+response.message+"",'success');
			else
				generateNoty(""+response.message+"",'error');

		},
		error: function(){
			generateNoty("Error en la llamada al servidor",'error');
		}
	});

	

}

function getSelectedLocalSumPlayerSalaries(){

	var salOthers = jq("#otherPlayers").getGridParam('selarrrow');
	var salaries = parseFloat(0);

	
	var salTeam = jq("#teamPlayers").getGridParam('selarrrow');

	for(var i=0;i<salTeam.length;i++){

		var rowId= salTeam[i];		

		salaries = parseFloat(salaries) + parseFloat(jq("#teamPlayers").jqGrid ('getCell', rowId,'currentSalary'));

	}


	return salaries;
	

}



function getSelectedVisitorSumPlayerSalaries(){

	var salOthers = jq("#otherPlayers").getGridParam('selarrrow');
	var salaries = parseFloat(0);

	
	for(var i=0;i<salOthers.length;i++){


		var rowId= salOthers[i];		

		salaries = parseFloat(salaries) + parseFloat(jq("#otherPlayers").jqGrid ('getCell', rowId,'currentSalary'));

	}

	return salaries;
	
}

function getSelectedLocalPlayerIds(){

	var ids = jq("#teamPlayers").getGridParam('selarrrow');

	var playerIds = [];

	for(var i=0;i<ids.length;i++){

		var rowId= ids[i];		

		var idsFromRow = jq("#teamPlayers").jqGrid ('getCell', rowId,'idJugador');


		playerIds.push(idsFromRow);

	}

	return playerIds;

}




function getSelectedVisitorPlayerIds(){

	var ids = jq("#otherPlayers").getGridParam('selarrrow');

	var playerIds = [];

	for(var i=0;i<ids.length;i++){

		var rowId= ids[i];		

		var idsFromRow = jq("#otherPlayers").jqGrid ('getCell', rowId,'idJugador');


		playerIds.push(idsFromRow);

	}

	return playerIds;
}

function updateTeamData(){

	var localPlayersIds = getSelectedLocalPlayerIds();
	var visitorPlayersIds = getSelectedVisitorPlayerIds();
	
	var localsumplayersalaries = getSelectedLocalSumPlayerSalaries();
	var visitorsumplayersalaries = getSelectedVisitorSumPlayerSalaries();
	
	jq( "#localNumPlayers").text( parseInt(jq( "#defaultLocalNumPlayers").text()) - localPlayersIds.length + visitorPlayersIds.length) ;
	if (jq( "#selectTradeTeam").val() !=0)
		jq( "#visitorNumPlayers").text( parseInt(jq( "#defaultVisitorNumPlayers").text()) - visitorPlayersIds.length + localPlayersIds.length ) ;

	
	jq( "#localSumPlayerSalaries").text(localsumplayersalaries);
	jq( "#visitorSumPlayerSalaries").text(visitorsumplayersalaries);
	jq( "#localSumSalaries").text(parseFloat(jq( "#defaultLocalSumSalaries").text()) - visitorsumplayersalaries + localsumplayersalaries );
	jq( "#visitorSumSalaries").text(parseFloat(jq( "#defaultVisitorSumSalaries").text()) + parseFloat(visitorsumplayersalaries) - parseFloat(localsumplayersalaries));

}


function evaluateTrade(){

	var localPlayersIds = getSelectedLocalPlayerIds();	
	var visitorPlayersIds = getSelectedVisitorPlayerIds();

	var localIdTeam = jq( "#idTeamFrom").text();
	var visitorIdTeam = jq( "#selectTradeTeam").val();

	var validLocalNumPlayers = ( (parseInt(jq( "#localNumPlayers").text()) >= <%= Constants.cMinPlayersByTeam %>) && (parseInt(jq( "#localNumPlayers").text()) <= <%= Constants.cMaxPlayersByTeam %>));
	var validVisitorNumPlayers = ( (parseInt(jq( "#visitorNumPlayers").text()) >= <%= Constants.cMinPlayersByTeam %>) && (parseInt(jq( "#visitorNumPlayers").text()) <= <%= Constants.cMaxPlayersByTeam %>));

// 	var differenceOfSalaries = parseFloat(0);

// 	if ((parseFloat(jq( "#localSumPlayerSalaries").text() )!=0) && (parseFloat(jq("#visitorSumPlayerSalaries").text())!=0))
// 		differenceOfSalaries = (parseFloat(jq( "#localSumPlayerSalaries").text() ) / parseFloat(jq("#visitorSumPlayerSalaries").text()) );
	
<%-- 	var validDifferenceOfSalaries = ( (differenceOfSalaries<=(parseFloat(1)+parseFloat(<%= Constants.cMaxDifferenceOfSalaries %>) ) ) && (differenceOfSalaries>=(parseFloat(1)-parseFloat(<%= Constants.cMaxDifferenceOfSalaries %>)) ) ); --%>


	if ( (localIdTeam!=0) && (visitorIdTeam!=0) && (localPlayersIds.length>0) && (visitorPlayersIds.length>0) && validLocalNumPlayers && validVisitorNumPlayers){
		jq('#proposeTradeButton').prop('disabled',false);
	}
// 	else
// 		jq('#proposeTradeButton').prop('disabled',true);



}



function generateNoty(msg,type){

  	var n = noty({
  		text: msg,
  		type: type,
		closeWith: ['hover'],
		timeout: 1000
  	});

}

</script>



</head>

<body>

	<jsp:include page="navbartrades.jsp" />


	<div id="tradeData">
		<div id="localTradeData">
			<label id="idTeamFrom" class="starthidden"></label>
			<label id="localLabel">Equipo Local</label>
			<table class="dataTable">
				<tr>
					<th>Salario jugadores ofrecidos</th><td>$<label id="localSumPlayerSalaries"> </label></td>
				</tr>
				<tr>
					<th>Espacio salarial</th><td>$<label class="starthidden" id="defaultLocalSumSalaries"></label><label id="localSumSalaries"></label></td>
				</tr>
				<tr>
					<label class="starthidden" id="defaultLocalNumPlayers"></label> <th>Número de jugadores</th><td><label id="localNumPlayers"></label></td>
				</tr>
			</table>
		</div>


		<div id="visitorTradeData">

			<label id="visitorLabel">Seleccione equipo</label>
			<table class="dataTable">
				<tr>
					<th>Salario jugadores recibidos</th><td>$<label id="visitorSumPlayerSalaries"></label></td>
				</tr>
				<tr>
					<th>Espacio salarial</th><td>$<label class="starthidden" id="defaultVisitorSumSalaries"></label><label id="visitorSumSalaries"></label></td>
				</tr>
				<tr>
					<th>Número de jugadores</th><td><label id="defaultVisitorNumPlayers" class="starthidden"></label><label id="visitorNumPlayers"></label></td>
				</tr>
			</table>


		</div>
	</div>

	<table class="tradeTables">
	<tr>
	<td width="50%"></td><td width="600"><form class="pure-form"><select id="selectTradeTeam" onchange="selectTradeTeamChange();"></select></form></td>
	</tr>
	<tr>
	<td width="50%"><table id="teamPlayers"></table></td><td width="50%"><table id="otherPlayers"></table></td>
	</tr>
	</table>


	<div class="selectedPlayersTrade">
	<input type="button" class="pure-button" id="checkTradeButton" value="Trade válido?" onclick="checkTrade();"></div>
	<input type="button" class="pure-button" id="proposeTradeButton" value="Proponer Traspaso" onclick="proposeTrade();"></div>
	


	








</body>
</html>
