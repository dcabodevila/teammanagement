


	<nav class="navbar navbar-default navbar-fixed-top" role="navigation">
	  	<div class="container">
	    <!-- Brand and toggle get grouped for better mobile display -->
		    <div class="navbar-header">
		      <button type="button" id="botonAtras" onClick="transitionDetailToList()" class="hidden" style="margin-top:10;margin-left:5;">
		      	Atrás
		      </button>
		 	</div>
			<a class="navbar-brand no-break-out"  title="TeamManagement" href="/competition/${menuNavigationForm.idCompeticion}">TeamManagement</a>
			<div class="navbar-collapse collapse" id="bs-example-navbar-collapse-1">
				<ul class="nav navbar-nav">
					<li><a href="/market/${menuNavigationForm.idEquipo}/trades">Ofrecer traspaso</a></li>     
					<li><a href="/market/${menuNavigationForm.idEquipo}/received">Traspasos Recibidos</a></li>
					<li><a href="/market/${menuNavigationForm.idEquipo}/offered">Traspasos Ofrecidos</a></li>
					<li><a href="/market/freeagents/${menuNavigationForm.idCompeticion}">Agentes Libres</a></li>	 
				</ul>
			</div>	  
	    </div>  
	</nav>
