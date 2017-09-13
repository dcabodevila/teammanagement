package es.ligasnba.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import es.ligasnba.app.model.usuario.CustomAuthenticatedUser;
import es.ligasnba.app.model.usuario.userService;
import es.ligasnba.app.model.util.SendMailSSL;
import es.ligasnba.app.util.Log;
import es.ligasnba.app.util.exceptions.InstanceNotFoundException;
import es.ligasnba.app.util.jqgrid.CustomGenericResponse;

@Controller
@RequestMapping("activate")
public class ActivateKeyController {

	@Autowired
	private userService userservice;
	
	public void setUserservice(userService userservice) {
		this.userservice = userservice;
	}
	
	@RequestMapping(value="/{activateKey}", method=RequestMethod.GET)
	public String activateAccount(@PathVariable String activateKey){

		try {
			userservice.activateAccount(activateKey);
			
			
		} catch (InstanceNotFoundException e) {
			return "activateerror";
		}
		
		if (SecurityContextHolder.getContext().getAuthentication().getName()!="anonymousUser"){
			
			CustomAuthenticatedUser userSession = (CustomAuthenticatedUser)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
						
			userSession.getUsuario().setActivated(true);
		}

		
		return "activatesuccess";
		
	}
	
	@RequestMapping(value="/resend", method=RequestMethod.POST)
	public @ResponseBody CustomGenericResponse resendActivationMail(){
		
		CustomGenericResponse response = new CustomGenericResponse();
		
		if (SecurityContextHolder.getContext().getAuthentication().getName()!="anonymousUser"){
			
			CustomAuthenticatedUser userSession = (CustomAuthenticatedUser)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			
			
			//Enviamos email de activación
			SendMailSSL mail = new SendMailSSL();
			boolean success = mail.SendActivationMail(userSession.getUsuario());
					
			if (success)
				response.setMessage("Correo de activación reenviado correctamente");
			else
				response.setMessage("No se ha podido enviar el correo de activación");
			
			response.setSuccess( success);
			
			
			
		}
		
		
		return response;
		
	}
	
}
