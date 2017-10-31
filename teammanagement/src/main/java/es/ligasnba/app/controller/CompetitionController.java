package es.ligasnba.app.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import es.ligasnba.app.model.actapartido.actaPartidoService;
import es.ligasnba.app.model.clasificacion.ClasificacionData;
import es.ligasnba.app.model.clasificacion.classificationService;
import es.ligasnba.app.model.competicion.Competicion;
import es.ligasnba.app.model.competicion.CompetitionForm;
import es.ligasnba.app.model.competicion.MenuNavigationForm;
import es.ligasnba.app.model.competicion.competitionService;
import es.ligasnba.app.model.equipo.Equipo;
import es.ligasnba.app.model.equipo.teamService;
import es.ligasnba.app.model.partido.ResumenBalance;
import es.ligasnba.app.util.constants.Constants;
import es.ligasnba.app.util.exceptions.InstanceNotFoundException;
import es.ligasnba.app.util.jqgrid.CustomGenericResponse;
import es.ligasnba.app.model.usuario.CustomAuthenticatedUser;
import es.ligasnba.app.model.usuario.CustomUserDetailsService;
import es.ligasnba.app.model.util.ViewMapper;


@Controller
@RequestMapping("competition")
public class CompetitionController {
	
	@Autowired
	private competitionService competitionservice;
	@Autowired
	private teamService teamservice;
	@Autowired
	private classificationService classificationservice;
	@Autowired
	private actaPartidoService actapartidoservice;
	
	public void setCompetitionservice(competitionService competitionservice) {
		this.competitionservice = competitionservice;
	}	
	public void setTeamservice(teamService teamservice) {
		this.teamservice = teamservice;
	}
	public void setClassificationservice(
			classificationService classificationservice) {
		this.classificationservice = classificationservice;
	}
	public void setActapartidoService(actaPartidoService actapartidoservice) {
		this.actapartidoservice = actapartidoservice;
	}
	
	@RequestMapping(value="/games")
	public String showGames(){
		return "redirect:/games";
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/{idCompeticion}", method=RequestMethod.GET)
	public String showForm(@PathVariable long idCompeticion ,Map model) throws InstanceNotFoundException {
		
		int userRole = CustomUserDetailsService.userRoleInCompetition(idCompeticion);
		
		if (userRole == -1)
			return "login";
		else 
			if (userRole<Constants.cRolUsuarioCompeticion){
			model.put("errormessage", "Solo pueden acceder a la competición los usuarios inscritos en ella");
			return "competitionformdenied";
		}
		else 
			if (CustomUserDetailsService.userRoleInCompetition(idCompeticion)== Constants.cRolAdminCompeticion)
				model.put("isAdmin", true);
		
			
		
		CustomAuthenticatedUser userSession = (CustomAuthenticatedUser)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Equipo equipo = userSession.getMapEquipos().get(idCompeticion);
		if (equipo !=null){
			
			Competicion com = userSession.getMapCompeticiones().get(idCompeticion);
			
			CompetitionForm competitionform = this.competitionservice.findCompetitionInfo(idCompeticion, equipo.getIdEquipo());
			MenuNavigationForm menuNavigationForm = this.competitionservice.findMenuNavigationInfo(idCompeticion, equipo.getIdEquipo());
			menuNavigationForm.setMercadoAbierto(com.isMercadoAbierto());
			if ((com.getAdmin()!=null) && (com.getAdmin().getIdUsuario()==userSession.getUserId()))
				model.put("isAdmin", true);
			
			final ResumenBalance balance = this.actapartidoservice.findBalanceEquipo(equipo.getIdEquipo(), equipo.getCompeticion().getIdTemporadaActual(), false);
			competitionform.setBalance(balance.getNumeroVictorias().toString()+"-"+balance.getNumeroDerrotas().toString());
	        model.put("competitionForm", competitionform);
	        model.put("menuNavigationForm", menuNavigationForm);

		}

        return "competitionform";
        
    }

    
	
	@RequestMapping(value="/getClassification", method=RequestMethod.GET)
	public @ResponseBody List<ClasificacionData> getClassification(@RequestParam("idTemporada") long idTemporada){

		List<ClasificacionData> clasificacion = new ArrayList<ClasificacionData>();	
		
		
		try {
			
			clasificacion = ViewMapper.mapClasificaciones(classificationservice.getClasificacionesTemporada(idTemporada, 0, Constants.cMaxTeamsInCompetition).getClasificaciones());
			
			
			
		} catch (InstanceNotFoundException e) {
			return clasificacion;
		}		
		
		return clasificacion;			
		
	}
	
    
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/{idEquipo}/configuration", method=RequestMethod.GET)
	public String showConfigurationForm(@PathVariable long idEquipo ,Map model) {
		
		
		
		Equipo equipo;
		try {
			equipo = teamservice.findById(idEquipo);
			
			int userRole = CustomUserDetailsService.userRoleInCompetition(equipo.getCompeticion().getIdCompeticion());
			
			if (userRole == -1)
				return "login";

		
			CustomAuthenticatedUser userSession = (CustomAuthenticatedUser)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			
			if (!userSession.getListaEquipos().contains(equipo)){
				model.put("errorMsg", "Solo el propietario del equipo puede acceder a la configuración.");
				return "teamconfigform";								
			}
			
			model.put("equipo", equipo);
				
			
		} catch (InstanceNotFoundException e) {
			model.put("errorMsg", "No se ha encontrado el equipo");
			
		}
		

		
		return "teamconfigform";
	}
	
	@RequestMapping(value="/checkTeamName", method=RequestMethod.GET)
	public @ResponseBody boolean checkTeamName(@RequestParam(value="teamName") String teamName, @RequestParam(value="idCompeticion") long idCompeticion){
		
		try {
			teamservice.findTeamNameInCompetition(teamName, idCompeticion);
			return false;
		} catch (InstanceNotFoundException e) {
			return true;
		}
		
	}
	
	@RequestMapping(value="/changeTeamName", method=RequestMethod.POST)
	public @ResponseBody CustomGenericResponse changeTeamName(@RequestParam(value="teamName") String teamName, @RequestParam(value="idEquipo") long idEquipo){
		
		CustomGenericResponse response = new CustomGenericResponse();
		
		
		try {
			teamservice.changeName(idEquipo, teamName);
			
			response.setSuccess(true);
			response.setMessage("Equipo cambiado de nombre correctamente.");
			
		} catch (InstanceNotFoundException e) {
			response.setSuccess(false);
			response.setMessage("Equipo no encontrado");
			return response;
		}		
			
			
		return response;
		
	} 

	
	@RequestMapping(value="/quit", method=RequestMethod.POST)
	public CustomGenericResponse exitCompetitionForm(@RequestParam(value="idEquipo") long idEquipo ) {
		
		CustomGenericResponse response = new CustomGenericResponse();
		boolean success;		
		Equipo equipo;
		String message="";
		try {
			equipo = teamservice.findById(idEquipo);

			CustomAuthenticatedUser userSession = (CustomAuthenticatedUser)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			
			if (!userSession.getListaEquipos().contains(equipo)){
				message = "Solo el propietario del equipo puede acceder a la configuración.";
				success=false;
			}				
			
			if (equipo.getCompeticion().getAdmin().getIdUsuario() == userSession.getUserId()){
				message = "El administrador no puede abandonar la competición.";
				success=false;
		
			}
				
			success = competitionservice.banUserFromCompetition(equipo.getUsuario().getIdUsuario(), equipo.getCompeticion().getIdCompeticion());
			if (success){
				userSession.getListaEquipos().remove(equipo);
				message = "Usuario expulsado correctamente";
			}
			
			if (!success)
				message = "No se ha podido abandonar la competición"; 
			
			
		} catch (InstanceNotFoundException e) {
			success=false;
			response.setMessage("No se ha encontrado el equipo");
			return response;
			
		}
		
		response.setMessage(message);
		response.setSuccess(success);
		
		return response;
	}
	
	
	
}
