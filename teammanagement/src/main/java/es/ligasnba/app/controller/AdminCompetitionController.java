package es.ligasnba.app.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import es.ligasnba.app.model.actapartido.actaPartidoService;
import es.ligasnba.app.model.competicion.AdminCompetitionForm;
import es.ligasnba.app.model.competicion.Competicion;
import es.ligasnba.app.model.competicion.competitionService;
import es.ligasnba.app.model.contrato.ContractService;
import es.ligasnba.app.model.equipo.Equipo;
import es.ligasnba.app.model.equipo.teamService;
import es.ligasnba.app.model.partido.CustomMatchResponse;
import es.ligasnba.app.model.partido.PartidoBlock;
import es.ligasnba.app.model.partido.matchService;
import es.ligasnba.app.model.segundoPlano.SegundoPlanoService;
import es.ligasnba.app.model.usuario.CustomAuthenticatedUser;
import es.ligasnba.app.model.usuario.CustomUserDetailsService;
import es.ligasnba.app.model.usuario.UserData;
import es.ligasnba.app.model.usuario.Usuario;
import es.ligasnba.app.model.usuario.userService;
import es.ligasnba.app.model.util.ViewMapper;
import es.ligasnba.app.util.Log;
import es.ligasnba.app.util.Scheduler.Worker;
import es.ligasnba.app.util.constants.Constants;
import es.ligasnba.app.util.exceptions.InstanceNotFoundException;
import es.ligasnba.app.util.jqgrid.CustomGenericResponse;

@Controller
@RequestMapping("admin")
public class AdminCompetitionController {
	
	@Autowired
	private competitionService competitionservice;
	@Autowired
	private teamService teamservice;
	@Autowired
	private userService userservice;
	@Autowired
	private matchService matchservice;
	@Autowired
	private actaPartidoService actapartidoservice;
	@Autowired
	private SegundoPlanoService segundoPlanoService;
	@Autowired
	private ContractService contractservice;
	
	public void setMatchservice(matchService matchservice) {
		this.matchservice = matchservice;
	}	
	public void setUserService(userService userservice) {
		this.userservice = userservice;
	}
	public void setCompetitionservice(competitionService competitionservice) {
		this.competitionservice = competitionservice;
	}	
	public void setTeamservice(teamService teamservice) {
		this.teamservice = teamservice;
	}
	public void setActapartidoservice(actaPartidoService actapartidoservice) {
		this.actapartidoservice = actapartidoservice;
	}	
	public void setContractservice(ContractService contractservice) {
		this.contractservice = contractservice;
	}
	public void setSegundoPlanoService(SegundoPlanoService segundoPlanoService) {
		this.segundoPlanoService = segundoPlanoService;
	}

	
	@RequestMapping(value="/{idCompeticion}/gameslist", method=RequestMethod.GET)
	public @ResponseBody CustomMatchResponse getGames(@PathVariable long idCompeticion, @RequestParam("_search") Boolean search,
			@RequestParam(value="filters", required=false) String filters,
			@RequestParam(value="page", required=false) Integer page,
			@RequestParam(value="rows", required=false) Integer rows,
			@RequestParam(value="sidx", required=false) String sidx,
			@RequestParam(value="sord", required=false) String sord){
		
		
		PartidoBlock partidoblock;
		CustomMatchResponse response = new CustomMatchResponse();
		try {
			Competicion com = competitionservice.findById(idCompeticion);
			long idTemporada = com.getIdTemporadaActual();
			partidoblock = matchservice.getPartidosTemporada( idTemporada, sidx,sord, (page-1)*rows, rows);
		
			response.setRows(ViewMapper.mapMatches(partidoblock.getPartidos()));
			response.setPage(Long.valueOf(page).toString());
			response.setTotal(Long.valueOf((matchservice.getNumTotalPartidosTemporada(idTemporada)/rows)+1).toString());
			response.setRecords(String.valueOf( partidoblock.getPartidos().size() ));
			
		} catch (InstanceNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return response;
		
		
		
	}	
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/{idCompeticion}/calendar", method=RequestMethod.GET)
	public String getCalendarForm(@PathVariable long idCompeticion, Map model){
		
		AdminCompetitionForm admincompetitionform = new AdminCompetitionForm();
		
		Competicion com;
		try {
			com = competitionservice.findById(idCompeticion);
			admincompetitionform.setCompetition( com );
		} catch (InstanceNotFoundException e) {
				
		}
		
		model.put("adminCompetitionForm", admincompetitionform);
		
		return "admincalendarform";
		
	}
	
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/{idCompeticion}/users", method=RequestMethod.GET)
	public String getAdminUsers(@PathVariable long idCompeticion, Map model){
		
		AdminCompetitionForm admincompetitionform = new AdminCompetitionForm();
		
		Competicion com;
		try {
			com = competitionservice.findById(idCompeticion);
			admincompetitionform.setCompetition( com );
		} catch (InstanceNotFoundException e) {
				
		}
		
		model.put("adminCompetitionForm", admincompetitionform);
		
		return "adminusersform";
		
	}
	
	
	
	
	
	@RequestMapping(value="/userslist", method=RequestMethod.GET)
	public @ResponseBody List<UserData> getUserData(@RequestParam("idCompeticion") long idCompeticion){
			
		List<UserData> listdata = new ArrayList<UserData>();
		
		try {
			listdata = ViewMapper.mapUsers(teamservice.getTeamsOfCompetition(idCompeticion, 0, Constants.cMaxTeamsInCompetition).getEquipos());
		} catch (InstanceNotFoundException e) {
			return new ArrayList<UserData>();
		}
		
		return listdata;
		
	}
	

	
//	@RequestMapping(value="/simulate", method=RequestMethod.POST)
//	public @ResponseBody CustomGenericResponse simulate( @RequestParam(value="idPartido") long idPartido){
//		
//		Boolean success;
//			
//		success = actapartidoservice.Simulate(idPartido);
//						
//		CustomGenericResponse response = new CustomGenericResponse();
//	
//		response.setSuccess(success);
//		
//        if (success) {
//			response.setMessage("Partido simulado con éxito");             
//        } 
//        else {
//			response.setMessage("Error al simular el partido");
//        }
//		
//		
//		return response;				
//		
//	}
//	
//	
//	@RequestMapping(value="/reset", method=RequestMethod.POST)
//	public @ResponseBody CustomGenericResponse reset( @RequestParam(value="idPartido") long idPartido){
//				
//		Boolean success;
//			
//		success = actapartidoservice.Reset(idPartido);
//						
//		CustomGenericResponse response = new CustomGenericResponse();
//	
//		response.setSuccess(success);
//		
//        if (success) {
//			response.setMessage("Partido reiniciado con éxito");             
//        } 
//        else {
//			response.setMessage("Error al reiniciar el partido");
//        }
//		
//		
//		return response;				
//		
//	}
	
	
	@RequestMapping(value="/forward", method=RequestMethod.POST)
	public @ResponseBody CustomGenericResponse forward( @RequestParam(value="idCompeticion") long idCompeticion, 
			@RequestParam(value="numDays") int numDays) throws Exception{

		
		Boolean success;
		Competicion competicion = this.competitionservice.findById(idCompeticion);
		success = this.segundoPlanoService.simulateDays(competicion, numDays);
						
		CustomGenericResponse response = new CustomGenericResponse();
	
		response.setSuccess(success);
		
        if (success) {
			response.setMessage("Competición avanzada con éxito");
			
			
			try {
				response.setFecha(competitionservice.findById(idCompeticion).getActualDate());
			} catch (InstanceNotFoundException e) {
				response.setSuccess(false);
				response.setMessage("No se ha encontrado la competición");
			}
        } 
        else {
			response.setMessage("Error al avanzar la competición");
        }
				
		return response;				
		
	}
	
	
	
	@RequestMapping(value="/draft", method=RequestMethod.POST)
	public @ResponseBody CustomGenericResponse draft( @RequestParam(value="idCompeticion") long idCompeticion){

		CustomGenericResponse response = new CustomGenericResponse();
		
		Boolean success;
			
		try {
			success = competitionservice.AutoDraftPlayers(idCompeticion);

			Competicion com = competitionservice.findById(idCompeticion);
			
			competitionservice.setCompetitionState(com, Constants.cStateStarted);
	
			
		} catch (InstanceNotFoundException e1) {
			success=false;
		}
						
		response.setSuccess(success);
		
        if (success) {
			response.setMessage("Draft realizado con éxito");			
			
        } 
        else {
			response.setMessage("Error en el draft");
        }
				
		return response;				
		
	}
	
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/{idCompeticion}", method=RequestMethod.GET)
	public String showAdminUsersForm(@PathVariable long idCompeticion, Map model) {
	
		int userRole = CustomUserDetailsService.userRoleInCompetition(idCompeticion);
		
		if (userRole == -1)
			return "login";
		
		else 
			if (userRole<Constants.cRolAdminCompeticion){
			model.put("errormessage", "Solo el administrador de la competición puede acceder a las funciones de Admin");
			return "competitionformdenied";
		}


		CustomAuthenticatedUser userSession = (CustomAuthenticatedUser)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
		AdminCompetitionForm admincompetitionform = new AdminCompetitionForm();
		

		for (int i = 0;i < userSession.getListaEquipos().size();i++)
		
			try {
	
				Competicion com = competitionservice.findById(idCompeticion);
				
				admincompetitionform.setCompetition(com);
				
				List<Equipo> teamsList = teamservice.getTeamsOfCompetition(idCompeticion, 0, Constants.cMaxTeamsInCompetition).getEquipos();
						
				//Quitamos el equipo del admin
				for (int x = 0;x<teamsList.size();x++){
					
					if ( (teamsList.get(x).getUsuario()!=null) && (teamsList.get(x).getUsuario().getIdUsuario() == com.getAdmin().getIdUsuario()))
						teamsList.remove(x);					
				}
					
				
				admincompetitionform.setListaEquipos(teamsList);							

				List<Usuario> usersList = userservice.getUsersOfCompetition(idCompeticion, 0, Constants.cMaxTeamsInCompetition).getUsuarios();
				
				usersList.remove(com.getAdmin());
				
				admincompetitionform.setListaUsuarios(usersList);
				
				//Quitamos al admin de esta lista ya que no permitimos expulsar al admin de la competición
				admincompetitionform.getListaUsuarios().remove(com.getAdmin());
				
				admincompetitionform.setTemporada(competitionservice.getTemporadaActual(idCompeticion));
			
				
			} catch (InstanceNotFoundException e) {			
				e.printStackTrace();
			}
		
       model.put("adminCompetitionForm", admincompetitionform);
       return "admincompetitionform";			
		
        
    }	
	

	
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/{idCompeticion}/games", method=RequestMethod.GET)
	public String showAdminGamesForm(@PathVariable long idCompeticion, Map model) {
		
		int userRole = CustomUserDetailsService.userRoleInCompetition(idCompeticion);
		
		if (userRole == -1)
			return "login";
		
		else 
			if (userRole<Constants.cRolAdminCompeticion){
			model.put("errormessage", "Solo el administrador de la competición puede acceder a las funciones de Admin");
			return "competitionformdenied";
		}


		
		
		AdminCompetitionForm admincompetitionform = new AdminCompetitionForm();
		

		
		try {

			Competicion com = competitionservice.findById(idCompeticion);
			
			admincompetitionform.setCompetition(com);		
					
			admincompetitionform.setListaEquipos(teamservice.getTeamsOfCompetition(idCompeticion, 0, Constants.cMaxTeamsInCompetition).getEquipos());							

			
			admincompetitionform.setListaUsuarios(userservice.getUsersOfCompetition(idCompeticion, 0, Constants.cMaxTeamsInCompetition).getUsuarios());
			
			//Quitamos al admin de esta lista ya que no permitimos expulsar al admin de la competición
			admincompetitionform.getListaUsuarios().remove(com.getAdmin());
			
			admincompetitionform.setTemporada(competitionservice.getTemporadaActual(idCompeticion));
				
				

				
				
		} catch (InstanceNotFoundException e) {			
			e.printStackTrace();
		}
	

        model.put("adminCompetitionForm", admincompetitionform);
        return "admingamesform";

			
		
        
    }
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/{idCompeticion}/deleteCompetition", method=RequestMethod.GET)
	public String processDeleteCompetition(@PathVariable long idCompeticion ,Map model) {				
		
		int userRole = CustomUserDetailsService.userRoleInCompetition(idCompeticion);
		
		if (userRole == -1)
			return "login";
		
		else 
			if (userRole<Constants.cRolAdminCompeticion){
			model.put("errormessage", "Solo el administrador de la competición puede acceder a las funciones de Admin");
			return "competitionformdenied";
		}
		
		
		CustomAuthenticatedUser userSession = (CustomAuthenticatedUser)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
		
		
		try {
			
			Equipo usersessionteam = teamservice.getTeamOfUser(idCompeticion, userSession.getUserId());
			userSession.getListaEquipos().remove(usersessionteam);
			competitionservice.removeCompetition(idCompeticion, userSession.getUserId());
			
	        return "redirect:/index";
		} catch (InstanceNotFoundException e1) {
			model.put("errorMessage", "No se encuentra la competición a eliminar");
		} catch (Exception e1) {
			model.put("errorMessage", "Sólo el administrador puede eliminar la competición");
		}
	
		return "redirect:/index";

			
		
        
    }
	
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/pause/", method=RequestMethod.POST)
	public @ResponseBody CustomGenericResponse processPauseCompetition(@RequestParam (value="id")  long idCompeticion ,Map model) {
	
		
		int userRole = CustomUserDetailsService.userRoleInCompetition(idCompeticion);
		
		CustomGenericResponse response = new CustomGenericResponse();
		
		if (userRole == -1){
			
			response.setSuccess(false);
			
		}
			
		
		else 
			if (userRole<Constants.cRolAdminCompeticion){
				
				model.put("errormessage", "Solo el administrador de la competición puede acceder a las funciones de Admin");
				response.setMessage("Solo el administrador de la competición puede acceder a las funciones de Admin");
				response.setSuccess(false);
		}
		
		
		boolean success = competitionservice.pauseCompetition(idCompeticion);
		
		response.setSuccess(success);
		
		if (success)
			response.setMessage("Competición pausada");
		else
			response.setMessage("Error al detener la competición");
		
		
		
	
		return response;
		
		
		
		
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/play/", method=RequestMethod.POST)
	public @ResponseBody CustomGenericResponse processPlayCompetition(@RequestParam (value="id")  long idCompeticion ,Map model) {
	
				
		int userRole = CustomUserDetailsService.userRoleInCompetition(idCompeticion);
		
		CustomGenericResponse response = new CustomGenericResponse();
		
		if (userRole == -1){
			
			response.setSuccess(false);
			
		}
			
		
		else 
			if (userRole<Constants.cRolAdminCompeticion){
			model.put("errormessage", "Solo el administrador de la competición puede acceder a las funciones de Admin");
			response.setMessage("Solo el administrador de la competición puede acceder a las funciones de Admin");
			response.setSuccess(false);
		}
		
		
		boolean success = competitionservice.playCompetition(idCompeticion);
		
		response.setSuccess(success);
		
		if (success)
			response.setMessage("Competición reanudada");
		else
			response.setMessage("Error al reanudar la competición");
		
		
		
	
		return response;
		
		
		
		
	}

	
	
	
    @RequestMapping(value="/removeUser", method = RequestMethod.POST)
    public @ResponseBody CustomGenericResponse processRemoveUser( @RequestParam("idCompeticion") long idCompeticion, @RequestParam("idUsuario") long idUsuario) {
    	
    	CustomGenericResponse response = new CustomGenericResponse();
    	
    	int userRole = CustomUserDetailsService.userRoleInCompetition(idCompeticion);
		
    	boolean success=false;
    	
    	CustomAuthenticatedUser userSession = (CustomAuthenticatedUser)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    	
		if (userRole == -1){		
			response.setMessage("Debe estar autenticado para poder realizar esta operación");
			return response;
		}
						
		
		else {			
				
			
			if ((userRole<Constants.cRolAdminCompeticion) && (userSession.getUserId()!=idUsuario)) {
				response.setMessage("Solo el administrador de la competición puede acceder a las funciones de Admin");
				return response;
			}
		}
								
			
		try {
			
			Equipo equipousuario = teamservice.getTeamOfUser(idCompeticion, idUsuario);
			
			success = competitionservice.banUserFromCompetition(idUsuario, idCompeticion);
			response.setMessage("Usuario expulsado correctamente");
			
			if (!success){
				response.setMessage("No se ha podido expulsar al usuario");				
			}
			else 
				if (userSession.getUserId()==idUsuario){
					
					userSession.getListaEquipos().remove( equipousuario );
					
				}
			
			
		} catch (InstanceNotFoundException e) {
			
			response.setMessage("Usuario o competicion no encontrada");						
			
		}					
        
		
    	response.setSuccess(success);
    	

    	return response;
    	
    	
    	
    	
    }	


    
    @RequestMapping(value="/addTeam", method = RequestMethod.POST)
    public @ResponseBody CustomGenericResponse processAddTeam( @RequestParam("idCompeticion") long idCompeticion, @RequestParam("nombreEquipo") String nombreEquipo) {
    	
    	CustomGenericResponse response = new CustomGenericResponse();
    	
    	int userRole = CustomUserDetailsService.userRoleInCompetition(idCompeticion);
		
    	boolean success=false;
    	
		if (userRole == -1){		
			response.setMessage("Debe estar autenticado para poder realizar esta operación");
			return response;
		}
								
		else
			
			if (userRole<Constants.cRolAdminCompeticion){
				response.setMessage("Solo el administrador de la competición puede acceder a las funciones de Admin");
				return response;
			}
    	
	    	
	    	
		try {
				
			List<Equipo> listaEquipos = teamservice.getTeamsOfCompetition(idCompeticion, 0, Constants.cMaxTeamsInCompetition).getEquipos();

    	

	    	for (int i=0;i<listaEquipos.size();i++){
	    		
	    		//Comprobamos que no exista ya el nombre del equipo con los de la BD
	    		if (listaEquipos.get(i).getnombre().compareToIgnoreCase(nombreEquipo)==0){
	    			response.setMessage("Ya existe un equipo con ese nombre");
	    			return response;
	    		}
	    	}

			
			
			competitionservice.addNewTeamToCompetition(nombreEquipo, idCompeticion);
			success=true;
			response.setMessage("Equipo añadido correctamente");

		} catch (InstanceNotFoundException e) {
			response.setMessage("Competición no encontrada");
		}
		
		response.setSuccess(success);											        
    	return response;
    	
    }    
      
    
    
    @RequestMapping(value="/removeTeam", method = RequestMethod.POST)
    public @ResponseBody CustomGenericResponse processRemoveTeam( @RequestParam("idCompeticion") long idCompeticion, @RequestParam("idEquipo") long idEquipo) {
    	
    	CustomGenericResponse response = new CustomGenericResponse();
    	
    	int userRole = CustomUserDetailsService.userRoleInCompetition(idCompeticion);
		
    	boolean success=false;
    	
		if (userRole == -1){		
			response.setMessage("Debe estar autenticado para poder realizar esta operación");
			return response;
		}
								
		else
			
			if (userRole<Constants.cRolAdminCompeticion){
				response.setMessage("Solo el administrador de la competición puede acceder a las funciones de Admin");
				return response;
			}
			
		try {
			success = competitionservice.removeTeamFromCompetition(idEquipo, idCompeticion);
			if (success)
				response.setMessage("Equipo expulsado correctamente");
			else
				response.setMessage("Error al expulsar el equipo");
											
		} catch (InstanceNotFoundException e) {
			response.setMessage("Equipo o competicion no encontrada");						
			
		}					
        	
    	response.setSuccess(success);    	
    	
    	return response;
    	
    }		
    
    @RequestMapping(value="/test", method = RequestMethod.GET)
    public @ResponseBody CustomGenericResponse test( @RequestParam("idCompeticion") long idCompeticion) {
    	
    	CustomGenericResponse response = new CustomGenericResponse();
		
//		for (int i=0;i<listaContratos.size();i++)
//			Log.LogFile( "Contrato: "+listaContratos.get(i).getIdContrato());
		
		response.setSuccess(true);
		response.setMessage("Info recibida");
			
    
	return response;
			
    }    
    
    @RequestMapping(value="/nextSeason", method = RequestMethod.POST)
    public @ResponseBody CustomGenericResponse processnextSeason( @RequestParam("idCompeticion") long idCompeticion) {
    	
    	CustomGenericResponse response = new CustomGenericResponse();
    	
    	int userRole = CustomUserDetailsService.userRoleInCompetition(idCompeticion);    	
    	
		if (userRole == -1){		
			response.setMessage("Debe estar autenticado para poder realizar esta operación");
		}
								
		else
			
			if (userRole<Constants.cRolAdminCompeticion){
				response.setMessage("Solo el administrador de la competición puede acceder a las funciones de Admin");
			}

		
		try {
			
			competitionservice.newSeason(idCompeticion);
			response.setSuccess(true);
			response.setMessage("La competición ha avanzado a la siguiente temporada");
			
			} catch (InstanceNotFoundException e) {
					
				response.setMessage("No se ha encontrado la competición");
				
					
			} catch (Exception e) {
				response.setMessage(e.getMessage());
			}
    
	return response;
			
    }   
    
	
	
}
