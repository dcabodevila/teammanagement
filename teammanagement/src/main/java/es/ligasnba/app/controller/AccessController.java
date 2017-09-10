package es.ligasnba.app.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

 
@Controller
@RequestMapping
public class AccessController {
 
	@RequestMapping("/login")
	public String login(Model model, @RequestParam(required=false) String message) {
	model.addAttribute("message", message);	
	return "/login";
	}
	@RequestMapping(value = "/denied")
	public String denied() {
	return "/denied";
	}
	@RequestMapping(value = "/login/failure")
	public String loginFailure() {
	String message = "Error en el Login";
	return "redirect:/login?message="+message;
	}
	@RequestMapping(value = "/logout/success")
	public String logoutSuccess() {
	String message = "Deslogueado correctamente";
	return "redirect:/login?message="+message;
	}
}