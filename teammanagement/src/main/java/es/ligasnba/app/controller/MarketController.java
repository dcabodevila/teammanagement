package es.ligasnba.app.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
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
import es.ligasnba.app.model.contrato.ContractService;
import es.ligasnba.app.model.equipo.Equipo;
import es.ligasnba.app.model.equipo.TeamForm;
import es.ligasnba.app.model.equipo.TeamMarketForm;
import es.ligasnba.app.model.equipo.teamService;
import es.ligasnba.app.model.jugador.CustomPlayerResponse;
import es.ligasnba.app.model.jugador.Jugador;
import es.ligasnba.app.model.jugador.JugadorBlock;
import es.ligasnba.app.model.jugador.PlayerData;
import es.ligasnba.app.model.jugador.playerService;
import es.ligasnba.app.model.util.ViewMapper;
import es.ligasnba.app.model.traspaso.TradeData;
import es.ligasnba.app.model.traspaso.TradeMarketForm;
import es.ligasnba.app.model.traspaso.Traspaso;
import es.ligasnba.app.model.traspaso.tradeService;
import es.ligasnba.app.model.usuario.CustomAuthenticatedUser;
import es.ligasnba.app.model.usuario.CustomUserDetailsService;
import es.ligasnba.app.util.constants.Constants;
import es.ligasnba.app.util.exceptions.InstanceNotFoundException;
import es.ligasnba.app.util.jqgrid.CustomGenericResponse;


@Controller
@RequestMapping("/market")

public class MarketController {
	
	@Autowired
	private teamService teamservice;
	@Autowired
	private playerService playerservice;
	@Autowired
	private ContractService contractservice;
	@Autowired
	private tradeService tradeservice;
	
	public void setTeamService(teamService teamservice) {
		this.teamservice = teamservice;
	}
	public void setPlayerService(playerService playerservice) {
		this.playerservice = playerservice;
	}
	public void setContractService(ContractService contractservice) {
		this.contractservice = contractservice;
	}
	public void setTradeService(tradeService tradeservice) {
		this.tradeservice = tradeservice;
	}
	
	
	@RequestMapping(value="/getteamdata", method=RequestMethod.GET)
	public @ResponseBody TeamMarketForm getTeamData(@RequestParam("idEquipo") long idEquipo) {
		
		TeamMarketForm teammarketform = new TeamMarketForm();
		
		try {
			Equipo equipo = teamservice.findById(idEquipo);
			
			teammarketform.setEquipo(equipo);			
			
			teammarketform.setTotalSalaries( equipo.getCompeticion().getLimiteSalarial().subtract( contractservice.getSumSalaries(idEquipo, equipo.getCompeticion().getIdTemporadaActual(), true) ).intValue() );
			
			teammarketform.setNumPlayers( playerservice.getPlantilla(idEquipo, 0, Constants.cMaxPlayersByTeam).getJugadores().size() );

			
			
		} catch (InstanceNotFoundException e) {
			
		}
		
		
		
		return teammarketform;
		
	}
	
	@RequestMapping(value="/checkTrade", method=RequestMethod.GET)
	public @ResponseBody CustomGenericResponse checkTrade(@RequestParam(value="teamFrom") long teamFrom,
											  @RequestParam(value="teamTo") long teamTo,
											  @RequestParam(value="localPlayers" , required = false) List<String> localPlayers, 
											  @RequestParam(value= "visitorPlayers", required=false) List<String> visitorPlayers) {
		
		CustomGenericResponse response = new CustomGenericResponse();
		response.setSuccess(false);
				
		try {
			
			//Validamos si el equipo pertenece al usuario
			if (SecurityContextHolder.getContext().getAuthentication().getPrincipal().getClass()== String.class){
				response.setMessage("Debes estar autenticado para realizar esta operación");
				return response;
			}
			
			CustomAuthenticatedUser userSession = (CustomAuthenticatedUser)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			Equipo userTeam = teamservice.findById(teamFrom);
			
			if (!userSession.getListaEquipos().contains(userTeam)){
				response.setMessage("Sólo el propietario del equipo puede proponer traspasos.");
				return response;
			}
			
			if (CollectionUtils.isNotEmpty(localPlayers) && CollectionUtils.isNotEmpty(visitorPlayers)){
				List<Long> idsJugadoresLocal = new ArrayList<Long>();	
				List<Long> idsJugadoresVisitantes = new ArrayList<Long>();
				
				for(String idLocalPlayer : localPlayers){
					idsJugadoresLocal.add(Long.parseLong(idLocalPlayer));
				}
				for(String idVisitorPlayer : visitorPlayers){
					idsJugadoresVisitantes.add(Long.parseLong(idVisitorPlayer));
				}
				
				return this.tradeservice.isValidTradeIdsJugadores(teamFrom, teamTo, idsJugadoresLocal, idsJugadoresVisitantes);
				
			}
	
		} catch (InstanceNotFoundException e) {

			response.setMessage("Alguno de los jugadores no ha sido encontrado");
			return response;
		}
	
			
		return response;
		
	}
	
	@RequestMapping(value="/validateTrade", method=RequestMethod.GET)
	public @ResponseBody CustomGenericResponse getTradeInfo(@RequestParam(value="teamFrom") long teamFrom,
											  @RequestParam(value="teamTo") long teamTo,
											  @RequestParam(value="localPlayers" , required = false) List<String> localPlayers, 
											  @RequestParam(value= "visitorPlayers", required=false) List<String> visitorPlayers) {
		
		CustomGenericResponse response = new CustomGenericResponse();
		response.setSuccess(false);
					
		try {
			
			//Validamos si el equipo pertenece al usuario
			if (SecurityContextHolder.getContext().getAuthentication().getPrincipal().getClass()== String.class){
				response.setMessage("Debes estar autenticado para realizar esta operación");
				return response;
			}
			
			CustomAuthenticatedUser userSession = (CustomAuthenticatedUser)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			Equipo userTeam = teamservice.findById(teamFrom);
			
			if (!userSession.getListaEquipos().contains(userTeam)){
				response.setMessage("Sólo el propietario del equipo puede proponer traspasos.");
				return response;
			}

			
			try {
				if (CollectionUtils.isNotEmpty(localPlayers) && CollectionUtils.isNotEmpty(visitorPlayers)){
					List<Long> idsJugadoresLocal = new ArrayList<Long>();	
					List<Long> idsJugadoresVisitantes = new ArrayList<Long>();
					
					for(String idLocalPlayer : localPlayers){
						idsJugadoresLocal.add(Long.parseLong(idLocalPlayer));
					}
					for(String idVisitorPlayer : visitorPlayers){
						idsJugadoresVisitantes.add(Long.parseLong(idVisitorPlayer));
					}
					
					CustomGenericResponse responseValidTrade = this.tradeservice.isValidTradeIdsJugadores(teamFrom, teamTo, idsJugadoresLocal, idsJugadoresVisitantes);
					
					if (responseValidTrade.getSuccess()){
						
						Traspaso trade = tradeservice.createTradeOfferIdsJugadores(teamFrom, teamTo, idsJugadoresLocal, idsJugadoresVisitantes, "", new BigDecimal(0));
						tradeservice.sendTradeOffer(trade.getIdTraspaso());
						responseValidTrade.setMessage("Traspaso enviado");
						return responseValidTrade;
					}
					else {						
						responseValidTrade.setSuccess(false);
						return responseValidTrade;
					}				
				}
				
			} catch (Exception e) {
				response.setMessage("Alguno de los jugadores ya no pertenece al equipo. Actualiza la página para comprobarlo.");
				return response;
			}
			
			
	
		} catch (InstanceNotFoundException e) {

			response.setMessage("Alguno de los jugadores no ha sido encontrado");
			return response;
		}
		response.setSuccess(true);		
			
		return response;
		
	}
	
	
	
	
	@RequestMapping(value="/players/{idEquipo}", method=RequestMethod.GET)
	public @ResponseBody CustomPlayerResponse getPlayerInfo(@PathVariable long idEquipo, @RequestParam("_search") Boolean search,
			@RequestParam(value="filters", required=false) String filters,
			@RequestParam(value="page", required=false) Integer page,
			@RequestParam(value="rows", required=false) Integer rows,
			@RequestParam(value="sidx", required=false) String sidx,
			@RequestParam(value="sord", required=false) String sord){
		
				
		CustomPlayerResponse response = new CustomPlayerResponse();
		try {
//			List<Jugador> listajugadores = playerservice.getPlantilla(idEquipo, 0, Constants.cMaxPlayersByTeam).getJugadores();				
		
			List<PlayerData> listaInfoJugadores = this.playerservice.findJugadoresByTeam(idEquipo);
					
			response.setRows(listaInfoJugadores);
			response.setPage(Long.valueOf(page).toString());
			response.setTotal("1");
			response.setRecords(String.valueOf(Constants.cMaxPlayersByTeam));							
			
			
		} catch (InstanceNotFoundException e) {
			e.printStackTrace();
		}
		
		return response;
		
		
		
	}	
	
	
	
	@RequestMapping(value="/teamslist", method=RequestMethod.GET)
	public @ResponseBody List<Equipo> getTeams(@RequestParam("idCompeticion") long idCompeticion){
			
		List<Equipo> listdata = new ArrayList<Equipo>();
		
		try {
			listdata = teamservice.getTeamsOfCompetition(idCompeticion, 0, Constants.cMaxTeamsInCompetition).getEquipos();			
			
		} catch (InstanceNotFoundException e) {
			return new ArrayList<Equipo>();
		}
		
		return listdata;
		
	}
	
	
	//Traspasos Recibidos
	
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/{idEquipo}/received", method=RequestMethod.GET)
	public String showReceivedTrades(@PathVariable long idEquipo,Map model) throws InstanceNotFoundException {
		Equipo equipo = teamservice.findById(idEquipo);
		int userRole = CustomUserDetailsService.userRoleInCompetition(equipo.getCompeticion().getIdCompeticion());
	   	
		if (userRole == -1){		
			return "login";
		}
		else {
			MenuNavigationForm menuNavigationForm = ControllerUtils.getMenuNavigation(equipo);
			model.put("menuNavigationForm", menuNavigationForm);
		}
		
		model.put("idEquipo", idEquipo);
		return "marketreceivedform";
		
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/{idEquipo}/offered", method=RequestMethod.GET)
	public String showOfferedTrades(@PathVariable long idEquipo,Map model) throws InstanceNotFoundException {

		Equipo equipo = teamservice.findById(idEquipo);
		int userRole = CustomUserDetailsService.userRoleInCompetition(equipo.getCompeticion().getIdCompeticion());
	   	
		if (userRole == -1){		
			return "login";
		}
		else {
			MenuNavigationForm menuNavigationForm = ControllerUtils.getMenuNavigation(equipo);
			model.put("menuNavigationForm", menuNavigationForm);
		}
		
		model.put("idEquipo", idEquipo);
		
		return "marketofferedform";
		
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/{idEquipo}/freeagents", method=RequestMethod.GET)
	public String showFreeAgents(@PathVariable long idEquipo,Map model) throws InstanceNotFoundException {
		Equipo equipo = teamservice.findById(idEquipo);
		int userRole = CustomUserDetailsService.userRoleInCompetition(equipo.getCompeticion().getIdCompeticion());
	   	
		if (userRole == -1){		
			return "login";
		}
		else {
			MenuNavigationForm menuNavigationForm = ControllerUtils.getMenuNavigation(equipo);
			model.put("menuNavigationForm", menuNavigationForm);
		}					
		model.put("idEquipo", idEquipo);
		
		return "marketfaform";
		
	}		
	
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/{idEquipo}/trades", method=RequestMethod.GET)
	public String showTrades(@PathVariable long idEquipo,Map model) {
		
		
		try {
			
			Equipo equipo = teamservice.findById(idEquipo);
			
	    	int userRole = CustomUserDetailsService.userRoleInCompetition(equipo.getCompeticion().getIdCompeticion());
		   	
			if (userRole == -1){		
				return "login";
			}
			else {
				MenuNavigationForm menuNavigationForm = ControllerUtils.getMenuNavigation(equipo);
				model.put("menuNavigationForm", menuNavigationForm);
				
				TeamMarketForm teammarketform = new TeamMarketForm();
				teammarketform.setEquipo(equipo);
				
				
				long idTemporada = equipo.getCompeticion().getIdTemporadaActual();
				teammarketform.setTotalSalaries( equipo.getCompeticion().getLimiteSalarial().subtract(contractservice.getSumSalaries(idEquipo, idTemporada, true)).intValue());
				
				model.put("teamMarketForm", teammarketform);
				
			}
			
			
		} catch (InstanceNotFoundException e) {
			model.put("errorMsg", "Equipo no encontrado");
		}
		
		
		
		return "markettradesform";
		
	}
	
	
	
	@RequestMapping(value="/getreceivedtrades", method=RequestMethod.GET)
	public @ResponseBody TradeMarketForm getReceivedTrades(@RequestParam("idEquipo") long idEquipo) {
		
		TradeMarketForm trademarketform = new TradeMarketForm();
		
		try {
			Equipo equipo = teamservice.findById(idEquipo);
			
			trademarketform.setEquipo(equipo);			
			
			List<Traspaso> receivedTrades = tradeservice.getTraspasosRecibidos(idEquipo, 0, Constants.cMaxTrades).getTraspasos();
								
			List<TradeData> tradedata = ViewMapper.mapTrades(receivedTrades);
			
			trademarketform.setListaTraspasosRecibidos( tradedata );
			
			
		} catch (InstanceNotFoundException e) {
			return trademarketform;
		}
		
		
		
		return trademarketform;
		
	}

	@RequestMapping(value="/getofferedtrades", method=RequestMethod.GET)
	public @ResponseBody TradeMarketForm getOfferedTrades(@RequestParam("idEquipo") long idEquipo) {
		
		TradeMarketForm trademarketform = new TradeMarketForm();
		
		try {
			Equipo equipo = teamservice.findById(idEquipo);
			
			trademarketform.setEquipo(equipo);			
			
			List<Traspaso> receivedTrades = tradeservice.getTraspasosOfrecidos(idEquipo, 0, Constants.cMaxTrades).getTraspasos();
								
			List<TradeData> tradedata = ViewMapper.mapTrades(receivedTrades);
			
			trademarketform.setListaTraspasosRecibidos( tradedata );
			
			
		} catch (InstanceNotFoundException e) {
			return trademarketform;
		}		
		
		return trademarketform;
		
	}	
		
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/freeagents/{idCompeticion}", method=RequestMethod.GET)
	public String getFreeAgents(@PathVariable long idCompeticion, Map model) {
		
		final TeamForm teamForm= new TeamForm();
    	int userRole = CustomUserDetailsService.userRoleInCompetition(idCompeticion);
		   	
		if (userRole == -1){		
			return "login";
		}
								
		else{
			try {
				final CustomAuthenticatedUser userSession = (CustomAuthenticatedUser)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
				Equipo equipo = userSession.getUserTeamInCompetition(idCompeticion);
						
				final boolean isPropietarioEquipo =  userSession.getListaEquipos().contains(equipo);
				teamForm.setPropietarioEquipo(isPropietarioEquipo);
				final boolean isAdminCompeticion = (userRole==Constants.cRolAdminCompeticion);
				teamForm.setAdminCompeticion(isAdminCompeticion);
				
				teamForm.setIdEquipo(equipo.getIdEquipo());
				teamForm.setNombreEquipo(equipo.getnombre());
				teamForm.setIdCompeticion(equipo.getCompeticion().getIdCompeticion());			
				
				MenuNavigationForm menuNavigationForm = ControllerUtils.getMenuNavigation(equipo);
				model.put("menuNavigationForm", menuNavigationForm);
			
		        model.put("teamForm", teamForm);
		        return "marketfaform";

			} catch (InstanceNotFoundException e) {
				return "marketfaform";
			}


		}
			
	}
	
	
	@RequestMapping(value="/accepttrade", method=RequestMethod.POST)
	public @ResponseBody CustomGenericResponse proccessAcceptTrade( @RequestParam("idTraspaso") long idTraspaso ){
		
		CustomGenericResponse response = new CustomGenericResponse();
		response.setSuccess(false);
		
		try {
			tradeservice.acceptTradeOffer(idTraspaso, false);			
			
			response.setMessage("Traspaso completado");
			response.setSuccess(true);
			
			
				
		} catch (Exception e) {
			response.setMessage(e.getMessage());
			return response;
		}
		
		
		
		return response;
		
		
	}
	
	@RequestMapping(value="/rejecttrade", method=RequestMethod.POST)
	public @ResponseBody CustomGenericResponse proccessReTrade( @RequestParam("idTraspaso") long idTraspaso ){
		
		CustomGenericResponse response = new CustomGenericResponse();
		response.setSuccess(false);
		
		try {
			
			tradeservice.rejectTradeOffer(idTraspaso);
			response.setMessage("Traspaso rechazado");
			response.setSuccess(true);
				
		} catch (Exception e) {
			response.setMessage(e.getMessage());
			return response;
		}
		
		
		return response;
		
		
	}	
		
	
}
