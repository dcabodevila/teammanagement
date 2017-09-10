package es.ligasnba.app.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import es.ligasnba.app.model.usuario.userService;
import es.ligasnba.app.util.exceptions.InstanceNotFoundException;
import es.ligasnba.app.util.jqgrid.CustomGenericResponse;

@Controller
@RequestMapping("remember")
public class RememberPasswordController {
	
	@Autowired
	private userService userservice;
	
	public void setUserservice(userService userservice) {
		this.userservice = userservice;
	}
	@RequestMapping( method=RequestMethod.GET)
	public String showRemember(){
		return "rememberpassword";
	}
	
	@RequestMapping(value="/{activationKey}", method=RequestMethod.GET)
	public String showChangePassword(@PathVariable String activationKey, Map model){
		
		try {
			userservice.findByActivationKey(activationKey);
		} catch (InstanceNotFoundException e) {
			return "activateerror";
		}
		model.put("activationKey", activationKey);
		return "changepassword";
	}
	
	@RequestMapping(value="/send", method=RequestMethod.POST)
	public @ResponseBody CustomGenericResponse activateAccount(@RequestParam("registerEmail") String registerEmail){
		
		
		CustomGenericResponse response = new CustomGenericResponse();
		boolean success;
		try {
			success = userservice.rememberPwd(registerEmail);
		} catch (InstanceNotFoundException e) {
			success = false;
			response.setMessage("La dirección de correo es incorrecta");
			
		}
		
			
		if (success)
			response.setMessage("Correo enviado correctamente");
		else
			response.setMessage("No se ha podido enviar el correo.");
		
		response.setSuccess( success);		
		
		
		return response;
	}
	
	@RequestMapping(value="/setNewPassword", method=RequestMethod.POST)
	public @ResponseBody CustomGenericResponse activateAccount(@RequestParam("activationKey") String activationKey, @RequestParam("newPassword") String pwd){
		
		
		CustomGenericResponse response = new CustomGenericResponse();
		boolean success;
		try {
			success = userservice.changePass(activationKey, pwd);
		} catch (InstanceNotFoundException e) {
			success = false;
			response.setMessage("La dirección de correo es incorrecta.");
			
		}
		
			
		if (success)
			response.setMessage("Contraseña cambiada correctamente.");
		else
			response.setMessage("No se ha podido cambiar la contraseña.");
		
		response.setSuccess( success);		
		
		
		return response;
	}	
	

}
