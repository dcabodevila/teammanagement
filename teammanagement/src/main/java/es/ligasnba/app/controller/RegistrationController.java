package es.ligasnba.app.controller;

import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import es.ligasnba.app.model.usuario.Usuario;
import es.ligasnba.app.model.usuario.userService;
import es.ligasnba.app.model.util.SendMailSSL;
import es.ligasnba.app.util.constants.Constants;
import es.ligasnba.app.util.exceptions.DuplicateInstanceException;
import es.ligasnba.app.util.exceptions.InstanceNotFoundException;
import es.ligasnba.app.util.jqgrid.CustomGenericResponse;

@Controller
@RequestMapping("register")
public class RegistrationController {
    	@Autowired
    	private userService userservice;
    	
    	public void setuserService(userService userservice) {
    		this.userservice = userservice;
    	}


        @SuppressWarnings("unchecked")
		@RequestMapping(method = RequestMethod.GET)
        public String showRegistration(Map model) {

                return "registrationform";
        }


        
        @RequestMapping(value="/userregister", method = RequestMethod.POST)
        public @ResponseBody CustomGenericResponse  processRegister( @RequestParam(value="userName") String userName, 
        															 @RequestParam(value="password") String password,
        															 @RequestParam(value="email") String email) {

                CustomGenericResponse response = new CustomGenericResponse();
                
   


            		try {
						Usuario u = userservice.userRegister(userName, password, email);
						response.setSuccess(true);
						response.setMessage("Usuario registrado correctamente");
												
					
						
					} catch (DuplicateInstanceException e) {
						// TODO Auto-generated catch block
						response.setSuccess(false);
						response.setMessage("Usuario duplicado");
					}

                
                return response;	            	
                               	                	                                                  
        }
        
        
        
        @RequestMapping(value="/usernamecheck", method = RequestMethod.GET)
        public @ResponseBody boolean processRegister( @RequestParam(value="userName") String userName) {
       	
        		boolean allowRegister;
            	try {

            		userservice.findByLogin(userName);
            		allowRegister = false;
            		
				
            	} catch (InstanceNotFoundException e) {
            		
            		allowRegister = true;
				}      			
            		                
                return allowRegister;	            	
                               	                	                                                  
        }   
        

}
