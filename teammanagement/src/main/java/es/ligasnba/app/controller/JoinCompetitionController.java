
package es.ligasnba.app.controller;


import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;


import es.ligasnba.app.model.competicion.Competicion;
import es.ligasnba.app.model.competicion.competitionService;
import es.ligasnba.app.model.competitionrol.CompetitionRol;
import es.ligasnba.app.model.equipo.Equipo;
import es.ligasnba.app.model.equipo.SelectTeamForm;
import es.ligasnba.app.model.equipo.teamService;
import es.ligasnba.app.model.equipodefault.EquipoDefault;
import es.ligasnba.app.model.usuario.CustomAuthenticatedUser;
import es.ligasnba.app.model.usuario.Usuario;
import es.ligasnba.app.model.usuario.userService;
import es.ligasnba.app.util.constants.Constants;
import es.ligasnba.app.util.exceptions.InstanceNotFoundException;
import es.ligasnba.app.util.jqgrid.CustomGenericResponse;



@Controller
@RequestMapping("joincompetitionform")
public class JoinCompetitionController {
    
	@Autowired
	private competitionService competitionservice;
	@Autowired
	private userService userservice;
	@Autowired
	private teamService teamservice;	
	
	
	public void setUserservice(userService userservice) {
		this.userservice = userservice;
	}
	
	public void setCompetitionservice(competitionService competitionservice) {
		this.competitionservice = competitionservice;
	}	
	public void setTeamservice(teamService teamservice) {
		this.teamservice = teamservice;
	}		


	@RequestMapping(method = RequestMethod.GET)
    public String showForm(Map model) {
            
            return "joincompetitionform";
    }
	

	@RequestMapping(value= "/join", method = RequestMethod.POST)
	public @ResponseBody SelectTeamForm processJoin(@RequestParam(value="nombreCompeticion") String nombreCompeticion, 
    		@RequestParam(value="password") String password){
		
		SelectTeamForm selectteamform = new SelectTeamForm();
		
    	
		try {
			long idUser = userservice.findByLogin(SecurityContextHolder.getContext().getAuthentication().getName()).getIdUsuario();
			
			if (competitionservice.getCompetitionsOfUser( idUser , 0, Constants.cMaxCompetitionsByUser).getCompeticiones().size()==Constants.cMaxCompetitionsByUser ){
				selectteamform.setMessage("El usuario no puede inscribirse en más competiciones");
				selectteamform.setSuccess(false);
				return selectteamform;
			}
			
			Competicion com = competitionservice.findByName(nombreCompeticion);
			if (!com.getClave2k().equals(password) ) {
				
				selectteamform.setSuccess(false);
				selectteamform.setMessage("Nombre de la competición o contraseña incorrectas");
				return selectteamform;
			
			}
			
			if (userservice.userHasTeamInCompetition(idUser, com.getIdCompeticion())){
				
				if (com.getEstado() == Constants.cStateStarted){
					selectteamform.setSuccess(false);
					selectteamform.setMessage("El usuario ya tiene un equipo dentro de esta competición.");
					return selectteamform;
				}
				else {
					
				}
					
			}
			
			if (userservice.userIsKickedFromCompetition(idUser, com.getIdCompeticion())){
				selectteamform.setSuccess(false);
				selectteamform.setMessage("Has sido expulsado de la competición.");
				return selectteamform;
				
			}
			
			selectteamform.setSuccess(true);
			selectteamform.setIdCompeticion(com.getIdCompeticion());
			selectteamform.setListAvailableTeams(this.teamservice.findAllTeamsNotSelectedInCompetition(com.getIdCompeticion()));
			
			
		} catch (InstanceNotFoundException e1) {
			selectteamform.setMessage("Competición no encontrada");
			selectteamform.setSuccess(false);
			return selectteamform;
		}
		
		return selectteamform;
		
	}
	


    //AJAX POST
    
    @RequestMapping(value="/selectteamid", method = RequestMethod.POST)
    public @ResponseBody CustomGenericResponse processJoinCompetition( @RequestParam(value="idEquipo") int idEquipo, 
    		@RequestParam(value="idCompeticion") long idCompeticion ) {
    	
    	CustomGenericResponse response = new CustomGenericResponse();
    	
		try {
			
		   	Usuario u = userservice.findByLogin(SecurityContextHolder.getContext().getAuthentication().getName());
		   	
		   	
		   	Equipo equipoSelected = teamservice.findById(idEquipo);
		   	
			userservice.joinUserCompetition(u.getIdUsuario(), idEquipo, equipoSelected.getCompeticion().getIdCompeticion());
			
			//Añadimos la competición a la sesión para poder entrar sin volver a hacer login
			CustomAuthenticatedUser userSession = (CustomAuthenticatedUser)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			userSession.addCompetitionRol(new CompetitionRol(u,equipoSelected.getCompeticion(),Constants.cRolUsuarioCompeticion));			
			userSession.addEquipo(equipoSelected);			
			userSession.getMapCompeticiones().put(idCompeticion, equipoSelected.getCompeticion());
			
			response.setSuccess(true);
			response.setMessage("La Competición ha sido creada correctamente.");
			
			
		} catch (InstanceNotFoundException e) {
		
					
			response.setSuccess(false);
			response.setMessage("Competición no encontrada");

		}
        	
    	
    	return response;
    	
    }
    

    
    
}