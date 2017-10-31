package es.ligasnba.app.controller.util;

import org.springframework.security.core.context.SecurityContextHolder;

import es.ligasnba.app.model.competicion.Competicion;
import es.ligasnba.app.model.competicion.MenuNavigationForm;
import es.ligasnba.app.model.equipo.Equipo;
import es.ligasnba.app.model.usuario.CustomAuthenticatedUser;
import es.ligasnba.app.util.exceptions.InstanceNotFoundException;

public class ControllerUtils {
	public static MenuNavigationForm getMenuNavigation(final Equipo equipo) throws InstanceNotFoundException {
		Competicion com = equipo.getCompeticion();
			MenuNavigationForm menuNavigationForm = null;
		final CustomAuthenticatedUser userSession = (CustomAuthenticatedUser)SecurityContextHolder.getContext().getAuthentication().getPrincipal();			
		final boolean isPropietarioEquipo =  userSession.getListaEquipos().contains(equipo);
		
		if (isPropietarioEquipo){
			menuNavigationForm = new MenuNavigationForm(com, equipo);
		}
		else {
			final Equipo equipoUsuario = userSession.getUserTeamInCompetition(com.getIdCompeticion());
			if (equipoUsuario!=null){
				menuNavigationForm = new MenuNavigationForm(com, equipoUsuario);
			}
		}
		menuNavigationForm.setEstadoCompeticion(com.getTipoEstadoCompeticion().getIdTipoEstadoCompeticion());
		menuNavigationForm.setMercadoAbierto(com.isMercadoAbierto());
		return menuNavigationForm;
	}
}
