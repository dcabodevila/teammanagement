package es.ligasnba.app.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import es.ligasnba.app.model.actapartido.ActaPartido;
import es.ligasnba.app.model.competicion.Competicion;
import es.ligasnba.app.model.competicion.MenuNavigationForm;
import es.ligasnba.app.model.competicion.competitionService;
import es.ligasnba.app.model.equipo.Equipo;
import es.ligasnba.app.model.equipo.teamService;
import es.ligasnba.app.model.estadistica.CustomStatResponse;
import es.ligasnba.app.model.estadistica.Estadistica;
import es.ligasnba.app.model.estadistica.statsService;
import es.ligasnba.app.model.partido.CustomMatchResponse;
import es.ligasnba.app.model.partido.MatchData;
import es.ligasnba.app.util.Log;
import es.ligasnba.app.util.constants.Constants;
import es.ligasnba.app.util.exceptions.InstanceNotFoundException;
import es.ligasnba.app.util.jqgrid.CustomGenericResponse;
import es.ligasnba.app.model.partido.MatchForm;
import es.ligasnba.app.model.util.ViewMapper;
import es.ligasnba.app.model.partido.Partido;
import es.ligasnba.app.model.partido.PartidoBlock;
import es.ligasnba.app.model.partido.ValoracionData;
import es.ligasnba.app.model.partido.matchService;
import es.ligasnba.app.model.usuario.CustomAuthenticatedUser;
import es.ligasnba.app.model.usuario.CustomUserDetailsService;



@Controller
@RequestMapping("/games")
public class GamesController {
	
	@Autowired
	private teamService teamservice;

	@Autowired
	private matchService matchservice;
	@Autowired
	private statsService statsservice;
	
	public void setTeamservice(teamService teamservice) {
		this.teamservice = teamservice;
	}
	public void setMatchService(matchService matchservice) {
		this.matchservice = matchservice;
	}
	public void setStatsService(statsService statsservice) {
		this.statsservice = statsservice;
	}	
	

//	@RequestMapping(value="/list/{idEquipo}", method=RequestMethod.GET)
//	public @ResponseBody CustomMatchResponse getGames(@PathVariable long idEquipo, @RequestParam("_search") Boolean search,
//			@RequestParam(value="filters", required=false) String filters,
//			@RequestParam(value="page", required=false) Integer page,
//			@RequestParam(value="rows", required=false) Integer rows,
//			@RequestParam(value="sidx", required=false) String sidx,
//			@RequestParam(value="sord", required=false) String sord){
//		
//		
//		
//		PartidoBlock partidoblock;
//		CustomMatchResponse response = new CustomMatchResponse();
//		try {
//			
//			partidoblock = matchservice.getPartidos(idEquipo, sidx, sord ,(page-1)*rows , rows );
//			response.setRows(ViewMapper.mapMatches(partidoblock.getPartidos()));
//			response.setPage(Long.valueOf(page).toString());
//			response.setTotal(Long.valueOf((matchservice.getNumTotalPartidosEquipo(idEquipo)/rows)+1).toString());
//			response.setRecords(String.valueOf( partidoblock.getPartidos().size() ));
//			
//		} catch (InstanceNotFoundException e) {
//			e.printStackTrace();
//		}
//		
//		return response;
//		
//		
//		
//	}
	
	
	@RequestMapping(value="/list", method=RequestMethod.GET)
	public @ResponseBody List<MatchData> getGames(@RequestParam("idEquipo") long idEquipo, @RequestParam("texto") String texto){

		return matchservice.findGamesList(texto, idEquipo, false);

	}
	
	@RequestMapping(value="/list/{idActaPartido}/{idEquipo}", method=RequestMethod.GET)
	public @ResponseBody CustomStatResponse getStats(@PathVariable(value="idActaPartido") long idActaPartido, @PathVariable(value="idEquipo") long idEquipo, @RequestParam("_search") Boolean search,
			@RequestParam(value="filters", required=false) String filters,
			@RequestParam(value="page", required=false) Integer page,
			@RequestParam(value="rows", required=false) Integer rows,
			@RequestParam(value="sidx", required=false) String sidx,
			@RequestParam(value="sord", required=false) String sord){
		
		CustomStatResponse response = new CustomStatResponse();		
		
		List<Estadistica> stats = statsservice.getStatsByGame(idActaPartido, sidx, sord, idEquipo, 0, Constants.cMaxPlayersByTeam);
				
		response.setRows(ViewMapper.mapStats(stats));
		response.setPage(Long.valueOf(page).toString());
		response.setTotal("1");
		response.setRecords(String.valueOf( stats.size() ));
			

		
		return response;
		
		
		
	}	

	
	//**borrar cuando no se necesite
	
	@RequestMapping(value="/list", method=RequestMethod.POST)
	public @ResponseBody List<Partido> getListGames(@RequestParam(value="selectedList", required=true) String selectedList, 
			@RequestParam(value="team", required=true) long idTeam, @RequestParam(value="page", required=true) int pageNumber ) throws InstanceNotFoundException{

						
		PartidoBlock partidoblock = new PartidoBlock(new ArrayList<Partido>(),false); 
		
		if (selectedList.compareTo(Constants.cMatchListJugados)==0){
			
			partidoblock = matchservice.getPartidosJugadosEquipo(idTeam, pageNumber, Constants.cMaxMatchesByPage+1 );																	
			
		}
		else
			if (selectedList.compareTo(Constants.cMatchListPendientes)==0){
				
				partidoblock = matchservice.getPartidosPendientesEquipo(idTeam, pageNumber, Constants.cMaxMatchesByPage+1) ;	
			}
		List<Partido> listaPartidos = partidoblock.getPartidos();
		
		if (partidoblock.getExistenMasPartidos())
			listaPartidos.add(new Partido());
			
		
		return listaPartidos;
		
		
	}	

	@RequestMapping(value="/getValoracion", method=RequestMethod.GET)
	public @ResponseBody ValoracionData getGameData(@RequestParam(value="idEquipo", required=true) long idEquipo, @RequestParam(value="idPartido", required=true) long idPartido){

						
		
		try {
			
			CustomAuthenticatedUser userSession = (CustomAuthenticatedUser)SecurityContextHolder.getContext().getAuthentication().getPrincipal();

			Competicion c = userSession.getCompeticionByIdEquipo(idEquipo);
			Equipo e = userSession.getUserTeamInCompetition(c.getIdCompeticion());
			final boolean isPropietarioEquipo =  userSession.getListaEquipos().contains(e);
			int userRole = CustomUserDetailsService.userRoleInCompetition(e.getCompeticion().getIdCompeticion());
			final boolean isAdminCompeticion = (userRole==Constants.cRolAdminCompeticion);	    	
		   	
			if (userRole == -1){		
				return null;
			}
			
			if ((e.getUsuario()!=null) && (isPropietarioEquipo || isAdminCompeticion)){
			
				return this.matchservice.getValoracionFromPartido(e.getIdEquipo() ,idPartido, e.getUsuario().getIdUsuario());
								
			}

			
		} catch (InstanceNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
		return new ValoracionData();
	}
	
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/{idEquipo}", method=RequestMethod.GET)
	public String showGamesForm(@PathVariable long idEquipo ,Map model) {
	
		
		try {
			final Equipo equipo = teamservice.findById(idEquipo);
	    	int userRole = CustomUserDetailsService.userRoleInCompetition(equipo.getCompeticion().getIdCompeticion());
			   	
			if (userRole == -1){		
				return "login";
			}
			
			
			CustomAuthenticatedUser userSession = (CustomAuthenticatedUser)SecurityContextHolder.getContext().getAuthentication().getPrincipal();

			Competicion c = userSession.getCompeticionByIdEquipo(idEquipo);
			Equipo e = userSession.getUserTeamInCompetition(c.getIdCompeticion());
			MenuNavigationForm menuNavigationForm = new MenuNavigationForm(c,e);
			MatchForm matchForm = new MatchForm();
			
			final boolean isPropietarioEquipo =  userSession.getListaEquipos().contains(equipo);		
			matchForm.setPropietarioEquipo(isPropietarioEquipo);
			final boolean isAdminCompeticion = (userRole==Constants.cRolAdminCompeticion);
			matchForm.setAdminCompeticion(isAdminCompeticion);
			
			model.put("matchForm", matchForm);
			model.put("menuNavigationForm", menuNavigationForm);
			
		} catch (InstanceNotFoundException e) {			
			model.put("errormessage", "Competición no encontrada");
		}
        
        return "gamesform";
        
    }
	
	@RequestMapping(value="/sendValoracion", method=RequestMethod.GET)
	public @ResponseBody CustomGenericResponse sendValoracion(@RequestParam(value="idEquipo", required=true) long idEquipoValorador, @RequestParam(value="idPartido", required=true) long idPartido, 
			@RequestParam(value="valoracion", required=false) Short valoracion, @RequestParam(value="comentario", required=false) String comentario, @RequestParam(value="victoria", required=true) boolean victoria){
		
		CustomGenericResponse response = new CustomGenericResponse();
		
		try {
			final Equipo equipo = teamservice.findById(idEquipoValorador);
	    	int userRole = CustomUserDetailsService.userRoleInCompetition(equipo.getCompeticion().getIdCompeticion());
			   	
			if (userRole == -1){
				response.setSuccess(false);
				response.setMessage("Usuario no logueado");
			}			
			
			CustomAuthenticatedUser userSession = (CustomAuthenticatedUser)SecurityContextHolder.getContext().getAuthentication().getPrincipal();

			final boolean isPropietarioEquipo =  userSession.getListaEquipos().contains(equipo);		
			final boolean isAdminCompeticion = (userRole==Constants.cRolAdminCompeticion);
			
			if (isPropietarioEquipo || isAdminCompeticion ){
				return this.matchservice.sendValoracion(equipo, idPartido, valoracion, comentario, victoria);
			}
			else {
				return new CustomGenericResponse(false, "El usuario no es propietario del equipo. Reportando a los admins...");
			}
			
		} catch (InstanceNotFoundException e) {			
			return new CustomGenericResponse(false, "Error recuperando los datos del equipo");
		}
        		
	}

    

	
	
	
}
