package es.ligasnba.app.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import es.ligasnba.app.controller.util.ControllerUtils;
import es.ligasnba.app.model.arquetipoEquipo.ArquetipoEquipo;
import es.ligasnba.app.model.arquetipoEquipo.ArquetipoEquipoDto;
import es.ligasnba.app.model.arquetipoEquipo.ArquetipoEquipoService;
import es.ligasnba.app.model.competicion.MenuNavigationForm;
import es.ligasnba.app.model.equipo.Equipo;
import es.ligasnba.app.model.equipo.teamService;
import es.ligasnba.app.model.finanzas.FinanzasDto;
import es.ligasnba.app.model.finanzas.PaquetesIngresosForm;
import es.ligasnba.app.model.finanzas.finanzasService;
import es.ligasnba.app.model.jugador.FinanzasForm;
import es.ligasnba.app.model.temporada.TemporadaData;
import es.ligasnba.app.model.temporada.seasonService;
import es.ligasnba.app.util.constants.Constants;
import es.ligasnba.app.util.exceptions.InstanceNotFoundException;
import es.ligasnba.app.util.jqgrid.CustomGenericResponse;
import es.ligasnba.app.model.usuario.CustomAuthenticatedUser;
import es.ligasnba.app.model.usuario.CustomUserDetailsService;


@Controller
@RequestMapping("/finanzas")
public class FinanzasController {
	
	@Autowired
	private finanzasService finanzasservice;
	@Autowired
	private teamService teamservice;
	@Autowired
	private seasonService seasonService;
	@Autowired
	private ArquetipoEquipoService arquetipoService;
	
	private static final Logger logger = Logger.getLogger(FinanzasController.class);
	
	public void setTeamservice(teamService teamservice) {
		this.teamservice = teamservice;
	}
	public void setFinanzasService(finanzasService finanzasservice) {
		this.finanzasservice = finanzasservice;
	}
	public void setSeasonService(seasonService seasonService) {
		this.seasonService = seasonService;
	}
	public void setArquetipoService(ArquetipoEquipoService arquetipoService) {
		this.arquetipoService = arquetipoService;
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/{idEquipo}", method=RequestMethod.GET)
	public String showTabs(@PathVariable long idEquipo, Map model) throws InstanceNotFoundException{
		final Equipo equipo = teamservice.findById(idEquipo);
    	int userRole = CustomUserDetailsService.userRoleInCompetition(equipo.getCompeticion().getIdCompeticion());
		   	
		if (userRole == -1){		
			return "login";
		}
		else {
			MenuNavigationForm menuNavigationForm = ControllerUtils.getMenuNavigation(equipo);
			menuNavigationForm.setIdTemporada(equipo.getCompeticion().getIdTemporadaActual());
			model.put("menuNavigationForm", menuNavigationForm);
			
			FinanzasForm finanzasForm = new FinanzasForm();
			
			final List<TemporadaData> listaTemporadas = this.seasonService.findListaTemporadaDataByIdCompeticion(equipo.getCompeticion().getIdCompeticion());			
			finanzasForm.setListaTemporadas(listaTemporadas);
			model.put("finanzasForm", finanzasForm);
			
		}
		
		return "finanzasform";
		
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/seleccionPaquete/{idEquipo}", method=RequestMethod.GET)
	public String showPaquetesSeleccionForm(@PathVariable long idEquipo, Map model) throws InstanceNotFoundException{
		final Equipo equipo = teamservice.findById(idEquipo);
    	int userRole = CustomUserDetailsService.userRoleInCompetition(equipo.getCompeticion().getIdCompeticion());
		   	
		if (userRole == -1){		
			return "login";
		}
		else {
			MenuNavigationForm menuNavigationForm = ControllerUtils.getMenuNavigation(equipo);
			menuNavigationForm.setIdTemporada(equipo.getCompeticion().getIdTemporadaActual());
			model.put("menuNavigationForm", menuNavigationForm);
			
			PaquetesIngresosForm paquetesIngresosForm = new PaquetesIngresosForm();
			final Long idArquetipoSeleccionado = equipo.getArquetipoElegido()!=null ? equipo.getArquetipoElegido().getIdArquetipo() : 1L;
			paquetesIngresosForm.setIdArquetipoSeleccionado(idArquetipoSeleccionado);
			
			final List<ArquetipoEquipoDto> listaArquetipos = this.arquetipoService.findArquetiposActivosByCompeticion(equipo.getCompeticion().getIdCompeticion());
			paquetesIngresosForm.setArquetiposDisponibles(listaArquetipos);
			model.put("paquetesIngresosForm", paquetesIngresosForm);
			
		}
		
		return "seleccionpaqueteform";
		
	}
	
	@RequestMapping(value="/getPaqueteIngresos", method=RequestMethod.GET)
	public @ResponseBody ArquetipoEquipoDto getPaqueteIngresos(@RequestParam("idEquipo") long idEquipo, @RequestParam("idPaqueteIngresos") long idPaqueteIngresos) throws InstanceNotFoundException{

		return this.arquetipoService.findArquetipoById(idPaqueteIngresos);
 
	}
	

	@RequestMapping(value="/findAsientosEquipoTemporada", method=RequestMethod.GET)
	public @ResponseBody FinanzasDto getAsientosEquipoTemporada(@RequestParam("idEquipo") long idEquipo, @RequestParam("idTemporada") long idTemporada) throws InstanceNotFoundException{
		final Equipo equipo = teamservice.findById(idEquipo);
    	int userRole = CustomUserDetailsService.userRoleInCompetition(equipo.getCompeticion().getIdCompeticion());		   	
		if (userRole != -1){		
			final CustomAuthenticatedUser userSession = (CustomAuthenticatedUser)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			final boolean isPropietarioEquipo =  userSession.getListaEquipos().contains(equipo);		
			final boolean isAdminCompeticion = (userRole==Constants.cRolAdminCompeticion);

			if (isPropietarioEquipo || isAdminCompeticion){		
				FinanzasDto finanzasDto = new FinanzasDto();
				finanzasDto.setListaAsientos(this.finanzasservice.getListaAsientosEquipoTemporada(idEquipo, idTemporada));
				finanzasDto.setBalanceTemporada(this.finanzasservice.getBalanceEquipoTemporada(idEquipo, idTemporada));
				return finanzasDto;
			}
			
		}
		
		
		return new FinanzasDto(); 
	}
	
    
    @RequestMapping(value = "/setPaqueteIngresos", method=RequestMethod.GET) 
    public @ResponseBody CustomGenericResponse waivePlayer(@RequestParam("idEquipo") long idEquipo, @RequestParam("idPaqueteIngresos") long idPaqueteIngresos){
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
			
				this.finanzasservice.setPaqueteIngresos(equipo, idPaqueteIngresos);
				return new CustomGenericResponse(true, "Paquete de infresos seleccionado correctamente");
				
			}
			else {
				logger.info(userSession.getUsername()+" ha intentado asignar un paquete de ingresos al equipo "+ idEquipo);
				return new CustomGenericResponse(false, "Error asignando paquete de ingresos");				
			}
			
    	
	    	
		} catch (Exception e) {
			logger.error(e.getMessage());
			response.setSuccess(false);
			response.setMessage("No se ha podido seleccionar el paquete de ingresos");
			
		}

		   	
		return response;
		
    } 
}
