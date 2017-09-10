
package es.ligasnba.app.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMethod;

import es.ligasnba.app.model.competicion.competitionService;
import es.ligasnba.app.model.equipo.Equipo;
import es.ligasnba.app.model.equipo.SelectTeamForm;
import es.ligasnba.app.model.equipo.teamService;
import es.ligasnba.app.model.equipodefault.EquipoDefault;
import es.ligasnba.app.model.usuario.Usuario;
import es.ligasnba.app.model.usuario.userService;
import es.ligasnba.app.util.exceptions.InstanceNotFoundException;




@Controller
@RequestMapping("selectteamform")
public class SelectTeamController {
	

	@Autowired
	private userService userservice;
	@Autowired
	private teamService teamservice;	
	@Autowired
	private competitionService competitionService;

	

	public void setUserservice(userService userservice) {
		this.userservice = userservice;
	}
	public void setTeamservice(teamService teamservice) {
		this.teamservice = teamservice;
	}

	
	
	@SuppressWarnings("unchecked")
	@RequestMapping(method = RequestMethod.GET)
    public String showForm(@ModelAttribute("idCompetition") long idCompetition, Map model) {
			
            SelectTeamForm selectteamform = new SelectTeamForm();                       

            model.put("selectTeamForm", selectteamform);
            return "selectteamform";
            
    }
	
	
	//**Borrar cuando esté listo por ajax
    @RequestMapping(value="/selectteamform", method = RequestMethod.POST)
    public String processJoinCompetition( SelectTeamForm selectteamform,
                    BindingResult result, ModelMap model) {
    	
		try {
			
		   	Usuario u = userservice.findByLogin(SecurityContextHolder.getContext().getAuthentication().getName());		   

		   	EquipoDefault equipoDefaultSelected = teamservice.findEquipoDefaultById(selectteamform.getSelectedIdTeam());
		   	
		   	Equipo nuevoEquipo = competitionService.teamDefaultRegister(equipoDefaultSelected, selectteamform.getIdCompeticion());
		   			   	
			userservice.joinUserCompetition(u.getIdUsuario(), nuevoEquipo.getIdEquipo(), selectteamform.getIdCompeticion());
			

			
		} catch (InstanceNotFoundException e) {
		
			
			result.rejectValue("selectedIdTeam","CompNameOrPass.competition", "Usuario o competicion no encontrada");			
			return "selectteamform"; 

		}
        	
    	
    	return "index";
    	
    }
	public competitionService getCompetitionService() {
		return competitionService;
	}
	public void setCompetitionService(competitionService competitionService) {
		this.competitionService = competitionService;
	}
    
    

    
    
}