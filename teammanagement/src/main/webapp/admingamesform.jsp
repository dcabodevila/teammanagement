<%@ page language="java" contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>



<html>

<script type='text/javascript' src='<c:url value="/resources/js/jquery-1.6.4.min.js"/>'></script>
<script type='text/javascript' src='<c:url value="/resources/js/jquery-ui-1.10.3.custom.js"/>'></script>
<script type='text/javascript' src='<c:url value="/resources/js/grid.locale-es.js"/>'></script>
<script type='text/javascript' src='<c:url value="/resources/js/jquery.jqGrid.min.js"/>'></script>

<head>

<link rel="stylesheet" type="text/css" media="screen" href="<c:url value="/resources/css/cupertino/jquery-ui-1.10.3.custom.css" context="/teammanagement"/>">  
<link rel="stylesheet" type="text/css" media="screen" href="<c:url value="/resources/css/ui.jqgrid.css" context="/teammanagement"/>">  					
<link rel="stylesheet" type="text/css" media="all" href="<c:url value="/resources/css/styles.css" context="/teammanagement"/>">   

<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">


<title>${adminCompetitionForm.competition.nombre}</title>
 <script type="text/javascript">
     var jq = jQuery.noConflict();
 </script>

<script type="text/javascript">

function getDateFromJSonDate(jsondate){
	var fecha="";	
	if (jsondate!=null){
	
		fecha= new Date(jsondate);
		fecha= fecha.getDate()+"/"+(fecha.getMonth()+1)+"/"+fecha.getFullYear();
		
	}
	return fecha;
}


  
 jq(function() {


  var idCompeticion = ${adminCompetitionForm.competition.idCompeticion};

  jq("#grid").jqGrid({
  


   onSelectRow: function(id){

     var sel_id = jq("#grid").jqGrid('getGridParam', 'selrow');
     var isPlayedGame = jq("#grid").jqGrid('getCell', sel_id, 'revisado');
      

	if (isPlayedGame == 'true'){

		jq("#simulate").addClass('ui-state-disabled'); 
		jq("#reset").removeClass('ui-state-disabled'); 		
	}
	else {

		jq("#reset").addClass('ui-state-disabled'); 
		jq("#simulate").removeClass('ui-state-disabled'); 
	}
	

   },

   url:'/teammanagement/admin/'+idCompeticion+'/gameslist',
   datatype: 'json',
   mtype: 'GET',
      colNames:['Id', 'idActaPartido', 'idEquipoLocal', 'Local', '' , '' , 'idEquipoVisitante', 'Visitante' ,'Fecha','Temporada','revisado'],
      colModel:[
	{name:'idPartido',index:'idPartido', width:6,editable:true,editoptions:{readonly:true,size:10},hidden:true},
	{name:'idActaPartido',index:'idActaPartido', width:6,editable:false,editoptions:{readonly:true,size:10},hidden:true},
	{name:'idEquipoLocal',index:'idEquipoLocal', width:6,editable:false,editoptions:{readonly:true,size:10},hidden:true},
	{name:'equipoLocal',index:'equipoLocal', width:20,editable:false, editrules:{required:true}, editoptions:{size:10}, align:'center', sortable:true},
	{name:'resultadoLocal',index:'actaPartido.resultadoLocal', width:5,editable:false, editrules:{required:true}, editoptions:{size:3}, align:'center',sortable:true},
	{name:'resultadoVisitante',index:'actaPartido.resultadoVisitante', width:5,editable:false, editrules:{required:true}, editoptions:{size:3}, align:'center',sortable:true},
	{name:'idEquipoVisitante',index:'idEquipoVisitante', width:6,editable:false,editoptions:{readonly:true,size:10},hidden:true},
	{name:'equipoVisitante',index:'equipoVisitante', width:20,editable:false, editrules:{required:true}, editoptions:{size:10}, align:'center',sortable:true},
	{name:'fecha',index:'fecha', width:10,editable:false, editrules:{required:true}, editoptions:{size:10}, sorttype:'date', align:'center', formatter: getDateFromJSonDate, sortable:true },
	{name:'temporada',index:'temporada.idTemporada', width:10,editable:false, editrules:{required:true}, editoptions:{size:10}, align:'center', sortable:true},
	{name:'revisado',index:'revisado', width:4,editable:false, editrules:{required:true}, editoptions:{size:10}, align:'center', sortable:false, hidden:true}
	

      ],
      postData: {
   },
   rowNum:20,
      rowList:[20,40,60],
      height: 600,
      altRows: true,
      autowidth: true,
   rownumbers: true,
      pager: '#pager',
      sortname: 'fecha',
      viewrecords: true,
      sortorder: "asc",
      caption:"Partidos",
      emptyrecords: "Empty records",
      loadonce: false,
      loadComplete: function() {

	jq("#simulate").addClass('ui-state-disabled');
	jq("#reset").addClass('ui-state-disabled');  	 

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
  })
/**
   .navGrid('#pager',{edit:false,add:false,del:false,search:false})
	.navButtonAdd('#pager',{
	   id:"simulate",	
	   caption:"Simular", 
	   buttonicon:"ui-icon-circle-triangle-e", 
	   onClickButton: simulateGame, 
	   position:"last"
	})
	.navButtonAdd('#pager',{
	   id: "reset",
	   caption:"Reiniciar", 
	   buttonicon:"ui-icon-circle-close", 
	   onClickButton: resetGame,
	   position:"last"
	});
	
**/



});

</script>

<script type="text/javascript">

var idCompeticion = ${adminCompetitionForm.competition.idCompeticion};
 
function simulateGame() {
	

	
	var row = jq("#grid").jqGrid('getGridParam','selrow');
	if( row != null )
  		jq("#grid").jqGrid('editGridRow',row,
		{ url: '/teammanagement/admin/simulate', editData: {},
	          recreateForm: true,
		  resize: false,
		  width : 400,
		  topinfo: "¿Desea simular el partido?"	,
        	  beforeShowForm: function(form) {},
		  closeAfterEdit: true,
		  reloadAfterSubmit:true,
		  viewPagerButtons:false
   });

 else jq( "#dialogSelectRow" ).dialog();


}


function resetGame() {
	var row = jq("#grid").jqGrid('getGridParam','selrow');
	if( row != null )
  		jq("#grid").jqGrid('editGridRow',row,
		{ url: '/teammanagement/admin/reset', editData: {},
	          recreateForm: true,
		  resize: false,
		  width : 400,
		  topinfo: "¿Desea reiniciar el partido?",
        	  beforeShowForm: function(form) {},
		  closeAfterEdit: true,
		  reloadAfterSubmit:true,
		  viewPagerButtons:false
   });

 else jq( "#dialogSelectRow" ).dialog();
}

</script>

</head>


<body>



<div id="jqgrid">
 <table id="grid"></table>
 <div id="pager"></div>
</div>

<div id="dialog" title="Feature not supported" style="display:none">
 <p>Característica no soportada</p>
</div>
<div id="dialogSelectRow" title="Warning" style="display:none">
 <p>Please select row</p>
</div>

</body>
</html>
