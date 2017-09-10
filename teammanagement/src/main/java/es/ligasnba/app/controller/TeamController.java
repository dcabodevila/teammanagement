package es.ligasnba.app.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import es.ligasnba.app.controller.util.ControllerUtils;
import es.ligasnba.app.model.competicion.Competicion;
import es.ligasnba.app.model.competicion.MenuNavigationForm;
import es.ligasnba.app.model.contrato.ContractService;
import es.ligasnba.app.model.equipo.Equipo;
import es.ligasnba.app.model.equipo.TeamForm;
import es.ligasnba.app.model.equipo.teamService;
import es.ligasnba.app.model.jugador.Jugador;
import es.ligasnba.app.model.jugador.PlayerContractData;
import es.ligasnba.app.model.jugador.PlayerData;
import es.ligasnba.app.model.jugador.PlayerDataNextSeason;
import es.ligasnba.app.model.jugador.playerService;
import es.ligasnba.app.model.jugadordefault.JugadorDefault;
import es.ligasnba.app.util.constants.Constants;
import es.ligasnba.app.util.exceptions.InstanceNotFoundException;
import es.ligasnba.app.util.jqgrid.CustomGenericResponse;
import es.ligasnba.app.model.util.ViewMapper;
import es.ligasnba.app.model.usuario.CustomAuthenticatedUser;
import es.ligasnba.app.model.usuario.CustomUserDetailsService;


@Controller
@RequestMapping("team")
public class TeamController {
	
	@Autowired
	private teamService teamservice;
	@Autowired
	private playerService playerservice;
	@Autowired
	private ContractService contractservice;	
	
	private static final Logger logger = Logger.getLogger(TeamController.class);
	
	public void setTeamservice(teamService teamservice) {
		this.teamservice = teamservice;
	}
	public void setPlayerservice(playerService playerservice) {
		this.playerservice = playerservice;
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/", method=RequestMethod.GET)
	public String showTabs(Map model){
		return "myteamform";
		
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value="/teamcreator/{idEquipo}", method=RequestMethod.GET)
	public String showTeamCreator(@PathVariable long idEquipo, Map model) throws InstanceNotFoundException {
		
		final TeamForm teamForm= new TeamForm();
		final Equipo equipo = teamservice.findById(idEquipo);
    	int userRole = CustomUserDetailsService.userRoleInCompetition(equipo.getCompeticion().getIdCompeticion());
		   	
		if (userRole == -1){		
			return "login";
		}
								
		else{
			
			final CustomAuthenticatedUser userSession = (CustomAuthenticatedUser)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			final boolean isPropietarioEquipo =  userSession.getListaEquipos().contains(equipo);
			teamForm.setPropietarioEquipo(isPropietarioEquipo);
			final boolean isAdminCompeticion = (userRole==Constants.cRolAdminCompeticion);
			teamForm.setAdminCompeticion(isAdminCompeticion);
			
			teamForm.setIdEquipo(idEquipo);
			teamForm.setNombreEquipo(equipo.getnombre());
			teamForm.setIdCompeticion(equipo.getCompeticion().getIdCompeticion());			
			
			MenuNavigationForm menuNavigationForm = ControllerUtils.getMenuNavigation(equipo);
			model.put("menuNavigationForm", menuNavigationForm);
		
	        model.put("teamForm", teamForm);
	        return "myteamform";

		}
		      
    }
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/fa/{idEquipo}", method=RequestMethod.GET)
	public String showFreeAgents(@PathVariable long idEquipo, Map model) throws InstanceNotFoundException {
		
		final TeamForm teamForm= new TeamForm();
		final Equipo equipo = teamservice.findById(idEquipo);
    	int userRole = CustomUserDetailsService.userRoleInCompetition(equipo.getCompeticion().getIdCompeticion());
		   	
		if (userRole == -1){		
			return "login";
		}
								
		else{
			
			final CustomAuthenticatedUser userSession = (CustomAuthenticatedUser)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			final boolean isPropietarioEquipo =  userSession.getListaEquipos().contains(equipo);		
			teamForm.setPropietarioEquipo(isPropietarioEquipo);
			final boolean isAdminCompeticion = (userRole==Constants.cRolAdminCompeticion);
			teamForm.setAdminCompeticion(isAdminCompeticion);
			
			teamForm.setIdEquipo(idEquipo);
			teamForm.setNombreEquipo(equipo.getnombre());
			teamForm.setIdCompeticion(equipo.getCompeticion().getIdCompeticion());			
			
			MenuNavigationForm menuNavigationForm = ControllerUtils.getMenuNavigation(equipo);
			model.put("menuNavigationForm", menuNavigationForm);
		
	        model.put("teamForm", teamForm);
	        return "fa";

		}
		      
    }
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/{idEquipo}", method=RequestMethod.GET)
	public String showTeamTabs(@PathVariable long idEquipo, Map model) throws InstanceNotFoundException {
		
		TeamForm teamForm= new TeamForm();
		final Equipo equipo = teamservice.findById(idEquipo);		
    	int userRole = CustomUserDetailsService.userRoleInCompetition(equipo.getCompeticion().getIdCompeticion());
		   	
		if (userRole == -1){		
			return "login";
		}
								
		else{
			
			CustomAuthenticatedUser userSession = (CustomAuthenticatedUser)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			final boolean isPropietarioEquipo =  userSession.getListaEquipos().contains(equipo);	
			teamForm.setAdminCompeticion(userRole==Constants.cRolAdminCompeticion);
			
			teamForm.setIdEquipo(idEquipo);
			teamForm.setNombreEquipo(equipo.getnombre());
			teamForm.setIdCompeticion(equipo.getCompeticion().getIdCompeticion());			
			
			Competicion com = equipo.getCompeticion();
			
			MenuNavigationForm menuNavigationForm = new MenuNavigationForm(com, equipo);
			
			if (isPropietarioEquipo){
				menuNavigationForm = new MenuNavigationForm(com, equipo);
			}
			else {
				final Equipo equipoUsuario = userSession.getUserTeamInCompetition(com.getIdCompeticion());
				if (equipoUsuario!=null){
					menuNavigationForm = new MenuNavigationForm(com, equipoUsuario);
				}
			}
			
			menuNavigationForm.setEstadoCompeticion(com.getTipoEstadoCompeticion().getIdTipoEstadoCompeticion());
			model.put("menuNavigationForm", menuNavigationForm);
						
			teamForm.setPropietarioEquipo(isPropietarioEquipo);

			if (isPropietarioEquipo && com.getTipoEstadoCompeticion().getIdTipoEstadoCompeticion().equals(Constants.cTipoEstadoCompeticionDraft)){
				
			}
			else {
				//Mostrar Roster
				if (isPropietarioEquipo){
					
				}
				else {
					
				}
				
			}		
		
	        model.put("teamForm", teamForm);
	        return "teamform";

		}
		      
    }
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/{idEquipo}/{playerName}", method=RequestMethod.GET)
	public String showTeamTabs(@PathVariable long idEquipo, @PathVariable String playerName ,Map model) {
		
		TeamForm teamForm= new TeamForm();
		
		CustomAuthenticatedUser userSession = (CustomAuthenticatedUser)SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		try {
			
			Equipo equipo = teamservice.findById(idEquipo);
			
			teamForm.setIdEquipo(idEquipo);
			teamForm.setNombreEquipo(equipo.getnombre());
			teamForm.setIdCompeticion(equipo.getCompeticion().getIdCompeticion());

			Competicion com = userSession.getCompeticionByIdEquipo(idEquipo);
			MenuNavigationForm menuNavigationForm = new MenuNavigationForm(com, equipo);
			model.put("menuNavigationForm", menuNavigationForm);
			final boolean isPropietarioEquipo =  userSession.getListaEquipos().contains(equipo);

			
			if (isPropietarioEquipo){
				model.put("adminTeam", true);
			}
			
			if (isPropietarioEquipo && com.getTipoEstadoCompeticion().getIdTipoEstadoCompeticion().equals(Constants.cTipoEstadoCompeticionDraft)){
				List<PlayerData> listaJugadoresPlantilla = this.playerservice.findJugadoresByTeam(idEquipo);
				model.put("plantillaData", listaJugadoresPlantilla);				
			}
			else {
				//Mostrar Roster
				if (isPropietarioEquipo){
					
				}
				
			}
			
			
		} catch (InstanceNotFoundException e) {
			
			model.put("errormessage", "Equipo no encontrado");
			
		}		
	
        model.put("teamForm", teamForm);
        return "myteamform";
        
    }
	
//	@SuppressWarnings("rawtypes")
//	@RequestMapping(value="/{idEquipo}/waive/{idJugador}", method=RequestMethod.GET)
//	public String showWaiveForm(@PathVariable long idJugador, @PathVariable long idEquipo, Map model) throws InstanceNotFoundException {
//			
//    	try {
//
//    		Equipo equipo = teamservice.findById(idEquipo);
//        	int userRole = CustomUserDetailsService.userRoleInCompetition(equipo.getCompeticion().getIdCompeticion());
//    	   	
//    		if (userRole == -1){		
//    			return "login";
//    		}
//		   	
//			final CustomAuthenticatedUser userSession = (CustomAuthenticatedUser)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//			final boolean isPropietarioEquipo =  userSession.getListaEquipos().contains(equipo);
//			final boolean isAdminCompeticion = (userRole==Constants.cRolAdminCompeticion);
//			
//			if (isPropietarioEquipo || isAdminCompeticion){
//				
//				model.put("idEquipo", idEquipo);
//				model.put("idJugador", idJugador);
//				
//				return "waiveform";
//				
//			}
//			else {
//				logger.info(userSession.getUsername()+" ha intentado depedir al jugador "+ idJugador+ " del equipo "+ idEquipo);
//				return "login"; 				
//			}
//			
//    	
//	    	
//		} catch (Exception e) {
//			logger.error(e.getMessage());
//			
//		}
//    	return "waiveform";
//	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value="/{idEquipo}/offerContractForm/{idJugador}", method=RequestMethod.GET)
	public String showResignForm(@PathVariable long idJugador, @PathVariable long idEquipo, Map model) throws InstanceNotFoundException {
		Equipo equipo = teamservice.findById(idEquipo);
    	int userRole = CustomUserDetailsService.userRoleInCompetition(equipo.getCompeticion().getIdCompeticion());
	   	
		if (userRole == -1){		
			return "login";
		}
		
		
		CustomAuthenticatedUser userSession = (CustomAuthenticatedUser)SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		
		
		try {
			
			Jugador jugador = playerservice.findById(idJugador);
			
			if ((jugador.getEquipo()!=null) && ( !userSession.getListaEquipos().contains(jugador.getEquipo())) && (!jugador.getCompeticion().getTipoEstadoCompeticion().getIdTipoEstadoCompeticion().equals(Constants.cTipoEstadoCompeticionPostTemporada))){
				model.put("errormessage", "El jugador no pertenece al usuario");
				return "resignform";
			}
					
			if ((jugador.getContrato()!=null) && (ViewMapper.getYears(jugador)>1)){
				model.put("errormessage", "El jugador no está en su último año de contrato");
				return "resignform";
				
			}		
			
			final PlayerContractData contractdata = this.contractservice.getPlayerResignContractData(idJugador,idEquipo);
						
			model.put("offerTeam", idEquipo);
			model.put("contractData", contractdata);
					
			
		} catch (InstanceNotFoundException e) {
			
			model.put("errormessage", "Jugador no encontrado");
			
		}		
	
		

        return "resignform";
        
    }

	
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/{idEquipo}/roster", method=RequestMethod.GET)
	public String showTeamForm(@PathVariable long idEquipo ,Map model) {
		
	
		
		TeamForm teamForm= new TeamForm();
		
		CustomAuthenticatedUser userSession = (CustomAuthenticatedUser)SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		try {			
			
			Equipo equipo = teamservice.findById(idEquipo);
			
			if (userSession.getListaEquipos().contains(equipo))
				model.put("adminTeam", true);
			
		} catch (InstanceNotFoundException e) {
			
			model.put("errormessage", "Equipo no encontrado");
			
		}		
	
		
        model.put("teamForm", teamForm);
        return "teamform";
        
    }

    
	@RequestMapping(value="/filterPlayerName", method=RequestMethod.GET)
	public @ResponseBody List<PlayerData> getInfoPlayerDefaultByName(@RequestParam("idCompeticion") long idCompeticion, @RequestParam("nombreJugador") String nombreJugador) throws InstanceNotFoundException{
		List<PlayerData> listaJugadoresDefault = playerservice.findAllDefaultPlayersNotSignedByName(idCompeticion, nombreJugador);
		return listaJugadoresDefault;
	}
	
	@RequestMapping(value="/playerdefault", method=RequestMethod.GET)
	public @ResponseBody PlayerData getInfoPlayerDefault(@RequestParam("idJugador") long idJugador){
		PlayerData response = new PlayerData();
		try {
			response = this.playerservice.findDefaultPlayerById(idJugador);
			response.setSuccess(true);
		} catch (InstanceNotFoundException e) {
			response.setSuccess(false);
			response.setMessage("Jugador no disponible");
			
		}
		return response;
	}
	
	@RequestMapping(value="/player", method=RequestMethod.GET)
	public @ResponseBody PlayerData getInfoPlayer(@RequestParam("idJugador") long idJugador){
		PlayerData response = new PlayerData();
		try {
			response = this.playerservice.findPlayerDataById(idJugador);
			response.setSuccess(true);
		} catch (InstanceNotFoundException e) {
			response.setSuccess(false);
			response.setMessage("Jugador no disponible");
			
		}
		return response;
	}

	@RequestMapping(value="/faplayer", method=RequestMethod.GET)
	public @ResponseBody PlayerData getInfoFAPlayer(@RequestParam("idJugador") long idJugador){
		PlayerData response = new PlayerData();
		try {
			response = this.playerservice.findFreeAgentDataById(idJugador);
			response.setSuccess(true);
		} catch (InstanceNotFoundException e) {
			response.setSuccess(false);
			response.setMessage("Jugador no disponible");
			
		}
		return response;
	}
	
	
	@RequestMapping(value="/getPlantilla", method=RequestMethod.GET)
	public @ResponseBody List<PlayerData> getPlantillaEquipo(@RequestParam("idEquipo") long idEquipo) throws InstanceNotFoundException{
		return this.playerservice.findJugadoresByTeam(idEquipo);
	}
	
	@RequestMapping(value="/getPlantillaNextSeason", method=RequestMethod.GET)
	public @ResponseBody List<PlayerDataNextSeason> getPlantillaEquipoNextSeason(@RequestParam("idEquipo") long idEquipo) throws InstanceNotFoundException{
		return this.playerservice.findJugadoresByTeamNextSeason(idEquipo);	}	

	@RequestMapping(value="/getFANextSeason", method=RequestMethod.GET)
	public @ResponseBody List<PlayerData> getFANextSeason(@RequestParam("idCompeticion") long idCompeticion) throws InstanceNotFoundException{
		List<Jugador> listaJugadoresPlantilla = this.playerservice.findFaNextSeason(idCompeticion);
		return ViewMapper.mapPlayers(listaJugadoresPlantilla);

	}
	
	
	
    
    @RequestMapping(value = "/playerdata/signDefaultPlayer", method=RequestMethod.GET) 
    public @ResponseBody CustomGenericResponse signPlayer(@RequestParam("idEquipo") long idEquipo, @RequestParam("idJugador") long idJugador){
    	CustomGenericResponse response = new CustomGenericResponse();
    	
    	try {

			CustomAuthenticatedUser userSession = (CustomAuthenticatedUser)SecurityContextHolder.getContext().getAuthentication().getPrincipal();

			this.playerservice.signDefaultPlayer(idEquipo, idJugador, userSession.getUserId());
			response.setSuccess(true);
    	
	    	
		} catch (Exception e) {
			logger.error(e.getMessage());
			response.setSuccess(false);
			response.setMessage("No se ha podido fichar el jugador.");
			
		}

		   	
		return response;
		
    } 
    
    @RequestMapping(value = "/waive", method=RequestMethod.GET) 
    public @ResponseBody CustomGenericResponse waivePlayer(@RequestParam("idEquipo") long idEquipo, @RequestParam("idJugador") long idJugador){
    	CustomGenericResponse response = new CustomGenericResponse();
    	
    	try {

    		final Equipo equipo = this.teamservice.findById(idEquipo); 
	    	int userRole = CustomUserDetailsService.userRoleInCompetition(equipo.getCompeticion().getIdCompeticion());
		   	
			if (userRole == -1){		
				return new CustomGenericResponse(false, "Usuario no logueado");
			}
			final CustomAuthenticatedUser userSession = (CustomAuthenticatedUser)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			final boolean isPropietarioEquipo =  userSession.getListaEquipos().contains(equipo);
			final boolean isAdminCompeticion = (userRole==Constants.cRolAdminCompeticion);
			
			if (isPropietarioEquipo || isAdminCompeticion){
			
				this.playerservice.waivePlayer(idEquipo, idJugador);
				return new CustomGenericResponse(true, "Jugador despedido");
				
			}
			else {
				logger.info(userSession.getUsername()+" ha intentado depedir al jugador "+ idJugador+ " del equipo "+ idEquipo);
				return new CustomGenericResponse(false, "No eres el propietario del equipo. Reportando a admins...");				
			}
			
    	
	    	
		} catch (Exception e) {
			logger.error(e.getMessage());
			response.setSuccess(false);
			response.setMessage("No se ha podido despedir al jugador.");
			
		}

		   	
		return response;
		
    } 
    
    @RequestMapping(value = "{idEquipo}/clearRoster", method=RequestMethod.GET) 
    public String clearRoster(@PathVariable long idEquipo ,Map model) throws InstanceNotFoundException {
    	CustomAuthenticatedUser userSession = (CustomAuthenticatedUser)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Equipo equipo = teamservice.findById(idEquipo);
		Competicion com = userSession.getCompeticionByIdEquipo(idEquipo);
		MenuNavigationForm menuNavigationForm = new MenuNavigationForm(com, equipo);		
		model.put("menuNavigationForm", menuNavigationForm);
		
		final boolean isPropietarioEquipo =  userSession.getListaEquipos().contains(equipo);
		
		if (isPropietarioEquipo){
			this.playerservice.clearRoster(idEquipo);
		}
		return "redirect:/team/teamcreator/"+ idEquipo;
    }  
	
	@RequestMapping(value="/filterFreeAgentsByName", method=RequestMethod.GET)
	public @ResponseBody List<PlayerData> getFreeAgentsByName(@RequestParam("idCompeticion") long idCompeticion, @RequestParam("nombreJugador") String nombreJugador) throws InstanceNotFoundException{
		List<PlayerData> listaJugadoresDefault = playerservice.findFreeAgentsByCompetition(idCompeticion, nombreJugador);
		return listaJugadoresDefault;
	}
	
}
