
package es.ligasnba.app.controller;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import es.ligasnba.app.model.competicion.Competicion;
import es.ligasnba.app.model.competicion.CreateCompetitionForm;
import es.ligasnba.app.model.competicion.competitionService;
import es.ligasnba.app.model.competitionrol.CompetitionRol;
import es.ligasnba.app.model.equipo.SelectTeamForm;
import es.ligasnba.app.model.equipo.teamService;
import es.ligasnba.app.model.usuario.CustomAuthenticatedUser;
import es.ligasnba.app.model.usuario.Usuario;
import es.ligasnba.app.model.usuario.userService;
import es.ligasnba.app.util.constants.Constants;
import es.ligasnba.app.util.exceptions.InstanceNotFoundException;
import es.ligasnba.app.util.exceptions.DuplicateInstanceException;


@Controller
@RequestMapping("createcompetitionform")
public class CreateCompetitionController {
    
	@Autowired
	private competitionService competitionservice;
	@Autowired
	private teamService teamservice;
	@Autowired
	private userService userservice;

	
	
	public void setUserservice(userService userservice) {
		this.userservice = userservice;
	}
	
	public void setCompetitionservice(competitionService competitionservice) {
		this.competitionservice = competitionservice;
	}
	public void setTeamService(teamService teamservice) {
		this.teamservice = teamservice;
	}

	
	@SuppressWarnings("unchecked")
	@RequestMapping(method = RequestMethod.GET)
    public String showForm(Map model) {

			CreateCompetitionForm competitionform= new CreateCompetitionForm();
            model.put("createCompetitionForm", competitionform);
            return "createcompetitionform";
    }
	
	
    @RequestMapping(value="create", method = RequestMethod.POST)
    public @ResponseBody SelectTeamForm processCompetition( @RequestParam(value="nombreCompeticion") String nombreCompeticion, 
												    		@RequestParam(value="password") String password, 
												    		@RequestParam(value="numTeams")int numTeams,
												    		@RequestParam(value="startDate") Date startDate,
												    		@RequestParam(value="finishDate") Date finishDate,
												    		@RequestParam(value="salaryCap") BigDecimal salaryCap,
												    		@RequestParam(value="salaryTop") BigDecimal salaryTop) {


    		SelectTeamForm selectteamform = new SelectTeamForm();

        	Usuario admin;
        	
			try {
				
				admin = userservice.findByLogin(SecurityContextHolder.getContext().getAuthentication().getName());
				try {

					
					Competicion c = this.competitionservice.createCompetition(nombreCompeticion ,password, admin.getIdUsuario(), startDate, finishDate, salaryCap, salaryTop);
					CustomAuthenticatedUser userSession = (CustomAuthenticatedUser)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
					userSession.addCompetitionRol(new CompetitionRol(admin,c,Constants.cRolAdminCompeticion));
									
					selectteamform.setListAvailableTeams( this.teamservice.findAllTeamsNotSelectedInCompetition(c.getIdCompeticion()));
					selectteamform.setIdCompeticion(c.getIdCompeticion());
					
					selectteamform.setSuccess(true);
					
				} catch (DuplicateInstanceException e) {
					
					selectteamform.setSuccess(false);
					selectteamform.setMessage("El nombre de la competición ya está escogido");
										
				}
			} catch (InstanceNotFoundException e) {
				
				selectteamform.setSuccess(false);
				selectteamform.setMessage("Ha habido un error mientras se creaba la competición");

				
			}
            	            
			return selectteamform;

                        
            
    }	
	

    @RequestMapping(value="competitionnamecheck", method = RequestMethod.GET)
    public @ResponseBody boolean processCompetition( @RequestParam(value="nombreCompeticion") String nombreCompeticion) {

    	boolean allowCreate;
    	try {
			competitionservice.findByName(nombreCompeticion);
			allowCreate = false;
		} catch (InstanceNotFoundException e) {
			allowCreate = true;
		}
		return allowCreate;
		
    }
    
    
}