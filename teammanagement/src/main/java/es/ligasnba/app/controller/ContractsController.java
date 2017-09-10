package es.ligasnba.app.controller;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Iterator;
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

import es.ligasnba.app.controller.util.ControllerUtils;
import es.ligasnba.app.model.competicion.MenuNavigationForm;
import es.ligasnba.app.model.competicion.competitionService;
import es.ligasnba.app.model.contrato.ContractService;
import es.ligasnba.app.model.contrato.ContractsForm;
import es.ligasnba.app.model.contrato.Contrato;
import es.ligasnba.app.model.contrato.ResultadoValidacionContratoOfrecidoDto;
import es.ligasnba.app.model.contrato.TeamContractData;
import es.ligasnba.app.model.dto.EquipoSeleccionDto;
import es.ligasnba.app.model.equipo.Equipo;
import es.ligasnba.app.model.equipo.TeamForm;
import es.ligasnba.app.model.equipo.teamService;
import es.ligasnba.app.model.jugador.Jugador;
import es.ligasnba.app.model.jugador.PlayerContractData;
import es.ligasnba.app.model.jugador.playerService;
import es.ligasnba.app.model.temporada.Temporada;
import es.ligasnba.app.model.util.ViewMapper;
import es.ligasnba.app.model.usuario.CustomAuthenticatedUser;
import es.ligasnba.app.model.usuario.CustomUserDetailsService;
import es.ligasnba.app.util.Log;
import es.ligasnba.app.util.constants.Constants;
import es.ligasnba.app.util.exceptions.InstanceNotFoundException;
import es.ligasnba.app.util.jqgrid.CustomGenericResponse;

@Controller
@RequestMapping("contracts")
public class ContractsController {
	
	@Autowired
	private teamService teamservice;
	@Autowired
	private playerService playerservice;
	@Autowired
	private ContractService contractservice;
	@Autowired
	private competitionService competitionservice;
	
	public void setTeamservice(teamService teamservice) {
		this.teamservice = teamservice;
	}
	public void setPlayerservice(playerService playerservice) {
		this.playerservice = playerservice;
	}
	public void setContractService(ContractService contractservice) {
		this.contractservice = contractservice;
	}
	public void setCompetitionService(competitionService competitionservice) {
		this.competitionservice = competitionservice;
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/{idEquipo}", method=RequestMethod.GET)
	public String showTeamTabs(@PathVariable long idEquipo ,Map model) throws InstanceNotFoundException {
		
		Equipo equipo = teamservice.findById(idEquipo);
    	int userRole = CustomUserDetailsService.userRoleInCompetition(equipo.getCompeticion().getIdCompeticion());
	   	
		if (userRole == -1){		
			return "login";
		}
		
		ContractsForm contractsForm= new ContractsForm();
		
		CustomAuthenticatedUser userSession = (CustomAuthenticatedUser)SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		
		
		try {
			
			
			contractsForm.setNombreEquipo(equipo.getnombre());
			contractsForm.setLogoEquipo(equipo.getLogo());
			contractsForm.setIdEquipo(idEquipo);
					   			
			MenuNavigationForm menuNavigationForm = ControllerUtils.getMenuNavigation(equipo);
			model.put("menuNavigationForm", menuNavigationForm);
						
			if (userSession.getListaEquipos().contains(equipo))
				model.put("adminTeam", true);
			
			contractsForm.setListaEquipos(this.teamservice.findAllEquiposFromCompetition(equipo.getCompeticion().getIdCompeticion()));
			model.put("contractsForm", contractsForm);
			
			
		} catch (InstanceNotFoundException e) {
			
			model.put("errormessage", "Equipo no encontrado");
			
		}		
        
        return "contractsform";
        
    }
	
	@RequestMapping(value="/getTeamContractsData", method=RequestMethod.GET)
	public @ResponseBody TeamContractData getTeamContractsData(@RequestParam("idEquipo") long idEquipo) throws InstanceNotFoundException {
		return this.contractservice.findTeamContractDataByIdEquipo(idEquipo);
	}

	
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/{idJugador}/resign/{idEquipo}", method=RequestMethod.GET)
	public String showResign(@PathVariable long idJugador , @PathVariable long idEquipo, Map model) {
		
	
		
		CustomAuthenticatedUser userSession = (CustomAuthenticatedUser)SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		
		
		try {
			
			Jugador jugador = playerservice.findById(idJugador);
			
			
			
			if ((jugador.getEquipo()!=null) && ( !userSession.getListaEquipos().contains(jugador.getEquipo()) )){
				model.put("errormessage", "El jugador no pertenece al usuario");
				return "resignform";
			}
					
			if ((jugador.getContrato()!=null) && (ViewMapper.getYears(jugador)>1)){
				model.put("errormessage", "El jugador no está en su último año de contrato");
				return "resignform";
				
			}
			
			
			PlayerContractData contractdata = new PlayerContractData();
			
			contractdata = contractservice.getPlayerResignContractData(idJugador,idEquipo);
						
			model.put("offerTeam", idEquipo);
			model.put("contractData", contractdata);
					
			
		} catch (InstanceNotFoundException e) {
			
			model.put("errormessage", "Jugador no encontrado");
			
		}		
	
		

        return "resignform";
        
    }

	
	
	@RequestMapping(value="/resignoffer", method=RequestMethod.POST)
	public @ResponseBody CustomGenericResponse processResignOffer(@RequestParam("idJugador") long idJugador ,
													@RequestParam("idEquipo") long idEquipo,
													@RequestParam("baseSalary") BigDecimal baseSalary,
													@RequestParam("years") int years ,													
													@RequestParam("increase") int increase,
													@RequestParam("useMidLevel") boolean useMidLevelException,
													@RequestParam(value="listaJugadoresST", required = false) List<Long> listaJugadoresST) {
			
		
		CustomAuthenticatedUser userSession = (CustomAuthenticatedUser)SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		
		CustomGenericResponse response = new CustomGenericResponse();
		boolean success=false;
		String message = "";
		try {
			
			Jugador jugador = playerservice.findById(idJugador);
			Equipo equipo = teamservice.findById(idEquipo);
			
			if ((!jugador.getCompeticion().getTipoEstadoCompeticion().getIdTipoEstadoCompeticion().equals(Constants.cTipoEstadoCompeticionPostTemporada)) && (jugador.getEquipo()!=null) && ( !userSession.getListaEquipos().contains(equipo))){
				message = "El jugador no pertenece al usuario";
				response.setMessage(message);
				response.setSuccess(false);
				return response;
			}						
							
			try {
				
				Iterator<Temporada> remainingSeasons = competitionservice.getSeasonsRemaining(equipo.getCompeticion().getIdCompeticion()).iterator();			
				
				long idTemporada = 0;
				
				while (remainingSeasons.hasNext()){
					
					Temporada t = remainingSeasons.next();
					if (t.getIdTemporada()==jugador.getCompeticion().getIdTemporadaActual()){
						if (remainingSeasons.hasNext()){
							t = remainingSeasons.next();
							idTemporada= t.getIdTemporada();
						}						
						
					}						
					
				}
				
				if (idTemporada==0){
					message="No quedan temporadas para renovar el contrato del jugador";
					response.setMessage(message);
					response.setSuccess(false);
					return response;
				}
				
				//En el caso de los agentes libres
				if (jugador.getEquipo()==null){
					idTemporada = jugador.getCompeticion().getIdTemporadaActual();
				}										
				
				response = this.contractservice.generateGlobalContractOffer(idEquipo, idJugador, idTemporada, years,  baseSalary, new BigDecimal(increase), false, false, useMidLevelException, listaJugadoresST);
				
				if (response.getSuccess()){
					response.setMessage("Oferta enviada correctamente");
				}
				
				return response;
				
			} catch (Exception e) {
			
				message = e.getMessage();
			}
			
					
			
		} catch (InstanceNotFoundException e) {

			message = "No se ha podido enviar la oferta ";
			
		}		
	
		
		response.setSuccess(success);
		response.setMessage(message);

		return response;
        
    }
	
	
	
	
}
