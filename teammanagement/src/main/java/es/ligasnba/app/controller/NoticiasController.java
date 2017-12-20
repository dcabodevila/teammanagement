package es.ligasnba.app.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
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
import es.ligasnba.app.model.noticia.Noticia;
import es.ligasnba.app.model.noticia.NoticiaDto;
import es.ligasnba.app.model.noticia.newsService;
import es.ligasnba.app.model.temporada.TemporadaData;
import es.ligasnba.app.model.temporada.seasonService;
import es.ligasnba.app.util.constants.Constants;
import es.ligasnba.app.util.exceptions.InstanceNotFoundException;
import es.ligasnba.app.util.jqgrid.CustomGenericResponse;
import es.ligasnba.app.model.usuario.CustomAuthenticatedUser;
import es.ligasnba.app.model.usuario.CustomUserDetailsService;


@Controller
@RequestMapping("/noticias")
public class NoticiasController {

	
	@Autowired
	private teamService teamservice;
	@Autowired
	private newsService newsservice;
	private static final Logger logger = Logger.getLogger(NoticiasController.class);
	
	
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
			
//			FinanzasForm finanzasForm = new FinanzasForm();
			
//			final List<TemporadaData> listaTemporadas = this.seasonService.findListaTemporadaDataByIdCompeticion(equipo.getCompeticion().getIdCompeticion());			
//			finanzasForm.setListaTemporadas(listaTemporadas);
//			model.put("finanzasForm", finanzasForm);
			
		}
		
		return "noticiasform";
		
	}
	

	

	@RequestMapping(value="/findUltimasNoticias", method=RequestMethod.GET)
	public @ResponseBody List<NoticiaDto> getUltimasNoticias(@RequestParam("idEquipo") long idEquipo) throws InstanceNotFoundException{
		final Equipo equipo = teamservice.findById(idEquipo);
    	int userRole = CustomUserDetailsService.userRoleInCompetition(equipo.getCompeticion().getIdCompeticion());

		if (userRole != -1){		
			return this.newsservice.getAllNews(idEquipo);		
		}		
		
		return new ArrayList<NoticiaDto>();
	}




	public newsService getNewsService() {
		return newsservice;
	}




	public void setNewsService(newsService newsservice) {
		this.newsservice = newsservice;
	}
	
}
