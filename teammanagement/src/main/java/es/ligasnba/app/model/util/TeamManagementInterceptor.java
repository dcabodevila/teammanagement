package es.ligasnba.app.model.util;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;


public class TeamManagementInterceptor extends HandlerInterceptorAdapter{
	
	@Value("${maintenancemode}")
	private String maintenancemode;
	@Autowired
	private DynamicPropertiesFileReader properties;
		
	
	public void setProperties(DynamicPropertiesFileReader properties) {
		this.properties = properties;
	}
	
	//before the actual handler will be executed
	public boolean preHandle(HttpServletRequest request, 
			HttpServletResponse response, Object handler)
	    throws Exception {
		
		maintenancemode=properties.getProperties();

		
		
		if ((Integer.valueOf(maintenancemode)==1) && (!request.getRequestURI().contains("maintenance")) && (!request.getRequestURI().contains("style")) ){
			
				//maintenance time, send to maintenance page
				
				response.sendRedirect("/teammanagement/maintenance");
				return false;
		}
		else {
			return true;
		}
 
	}


}
