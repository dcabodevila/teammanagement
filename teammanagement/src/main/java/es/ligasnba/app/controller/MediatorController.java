package es.ligasnba.app.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
 
@Controller
@RequestMapping("/")
public class MediatorController {
 
	@RequestMapping
	public String getHomePage() {
	return "redirect:index";
	}
	@RequestMapping(value="/user")
	public String getUserPage() {
	return "user";
	}
	@RequestMapping(value="/admin")
	public String getAdminPage() {
	return "admin";
	}
	@RequestMapping(value="/maintenance")
	public String getMaintenancePage() {
	return "maintenance";
	}
}