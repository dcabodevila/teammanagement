package es.ligasnba.app.controller;

import java.util.List;
import java.util.Map;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import es.ligasnba.app.model.usuario.CustomAuthenticatedUser;
import es.ligasnba.app.model.equipo.Equipo;

@Controller
@RequestMapping("index")
public class HomeController {

	
	
	@SuppressWarnings("unchecked")
	@RequestMapping(method = RequestMethod.GET)
    public String showForm(Map model) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		
		if (SecurityContextHolder.getContext().getAuthentication().getName()!="anonymousUser"){
			
			CustomAuthenticatedUser userSession = (CustomAuthenticatedUser)SecurityContextHolder.getContext().getAuthentication().getPrincipal();

			List<Equipo> teamsOfUser = userSession.getListaEquipos();
			model.put("isActivated", userSession.getUsuario().isActivated());
            model.put("teamList", teamsOfUser);
		}

		

            return "index";
    }
	
}

