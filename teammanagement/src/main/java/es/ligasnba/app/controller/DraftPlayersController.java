
package es.ligasnba.app.controller;

import java.util.List;
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
import es.ligasnba.app.model.jugador.DraftPlayersForm;
import es.ligasnba.app.model.jugador.Jugador;
import es.ligasnba.app.model.jugador.playerService;
import es.ligasnba.app.model.usuario.CustomAuthenticatedUser;
import es.ligasnba.app.model.usuario.Usuario;
import es.ligasnba.app.model.usuario.userService;
import es.ligasnba.app.util.constants.Constants;
import es.ligasnba.app.util.exceptions.InstanceNotFoundException;




@Controller
@RequestMapping("draftplayers")
public class DraftPlayersController {
	

	@Autowired
	private userService userservice;
	@Autowired
	private teamService teamservice;	
	@Autowired
	private competitionService competitionService;
	@Autowired
	private playerService playerservice;
	

	public void setUserservice(userService userservice) {
		this.userservice = userservice;
	}
	public void setTeamservice(teamService teamservice) {
		this.teamservice = teamservice;
	}

	
	
	@SuppressWarnings("unchecked")
	@RequestMapping(method = RequestMethod.GET)
    public String showForm(@ModelAttribute("idCompetition") long idCompetition, Map model) throws InstanceNotFoundException {
			
			DraftPlayersForm draftPlayersForm = new DraftPlayersForm(); 	                      
			
			CustomAuthenticatedUser userSession = (CustomAuthenticatedUser)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			Equipo e = userSession.getUserTeamInCompetition(idCompetition);			
			draftPlayersForm.setidEquipo(e.getIdEquipo());

			draftPlayersForm.setListAvailableTeams(userSession.getListaEquiposDefault());
			
			if (!draftPlayersForm.getListAvailableTeams().isEmpty()){
				Integer selectedIdTeam = draftPlayersForm.getListAvailableTeams().get(0).getIdEquipo();
				draftPlayersForm.setSelectedIdTeam(selectedIdTeam);
				
				List<Jugador> listaJugadores = this.playerservice.findFreePlayersByDefaultTeam(selectedIdTeam.intValue(), idCompetition);
				draftPlayersForm.setListAvailablePlayers(listaJugadores);				
			}
			
			draftPlayersForm.setListaJugadoresSeleccionados(playerservice.getPlantilla(e.getIdEquipo(), 0, Constants.cMaxPlayersByTeam).getJugadores()); 
			
			
            model.put("draftPlayersForm", draftPlayersForm);
            return "draftplayers";
            
    }
	public playerService getPlayerservice() {
		return playerservice;
	}
	public void setPlayerservice(playerService playerservice) {
		this.playerservice = playerservice;
	}
	
    
    

    
    
}