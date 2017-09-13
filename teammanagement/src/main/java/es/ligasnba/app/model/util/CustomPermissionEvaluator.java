package es.ligasnba.app.model.util;


import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import es.ligasnba.app.model.competicion.Competicion;
import es.ligasnba.app.model.competitionrol.CompetitionRol;
import es.ligasnba.app.model.competitionrol.competitionRolService;
import es.ligasnba.app.model.usuario.CustomUserDetailsService;
import es.ligasnba.app.model.usuario.Usuario;
import es.ligasnba.app.model.usuario.userService;
import es.ligasnba.app.util.Log;
import es.ligasnba.app.util.exceptions.InstanceNotFoundException;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class CustomPermissionEvaluator implements PermissionEvaluator {

	@Autowired
	private userService userservice;
	@Autowired
	public competitionRolService competitionrolservice;
	
	private static final Logger logger = Logger.getLogger(CustomPermissionEvaluator.class);
	
	public userService getuserService() {
		return userservice;
	}
	public competitionRolService getCompetitionrolservice() {
		return competitionrolservice;
	}
	
	
	
    @Override
    public boolean hasPermission(Authentication authentication, Object targetDomainObject, Object permission) {
        boolean hasPermission = false;

        
        	try {
				Usuario u = userservice.findByLogin(authentication.getName());
				Competicion com = (Competicion) targetDomainObject;
				
				List<CompetitionRol> cr = competitionrolservice.getCompetitionRol(u.getIdUsuario(), com.getIdCompeticion());
				
				logger.info("permission.toString: "+ permission.toString());
				 
				for (int i=0;i<cr.size();i++){
					CompetitionRol newrol = cr.get(i);
					hasPermission = getAuthorities(newrol.getRol()).contains(permission.toString());
				}

				
				
				
			} catch (InstanceNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        
//        if (targetDomainObject.equals("Todo")) {
//            Object principal = authentication.getPrincipal();
//            if (principal instanceof UserDetails) {
//                UserDetails userDetails = (UserDetails) principal;
//                String principalRole = getRole(userDetails.getAuthorities());
//                if (principalRole.equals(SecurityRole.ROLE_USER.name())) {
//                    hasPermission = true;
//                }
//            }
//        }

        return hasPermission;
    }

    @Override
    public boolean hasPermission(Authentication authentication, Serializable targetId, String targetType, Object permission) {
        //Not required here.
        return false;
    }

    /**
    * Retrieves a collection of {@link GrantedAuthority} based on a numerical role
    * @param role the numerical role
    * @return a collection of {@link GrantedAuthority
    */
    public Collection<? extends GrantedAuthority> getAuthorities(Integer role) {
    	List<GrantedAuthority> authList = getGrantedAuthorities(getRoles(role));
    	return authList;
    }
    /**
    * Converts a numerical role to an equivalent list of roles
    * @param role the numerical role
    * @return list of roles as as a list of {@link String}
    */
    public List<String> getRoles(Integer role) {
    	
    	List<String> roles = new ArrayList<String>();
    	
    	if (role.intValue() == 1) {
    		roles.add("ROLE_COMPETITION_USER");
    		roles.add("ROLE_COMPETITION_ADMIN");
    	} else if (role.intValue() == 2) {
    		roles.add("ROLE_COMPETITION_USER");
    	}
    	return roles;
    }
    /**
    * Wraps {@link String} roles to {@link SimpleGrantedAuthority} objects
    * @param roles {@link String} of roles
    * @return list of granted authorities
    */
    public static List<GrantedAuthority> getGrantedAuthorities(List<String> roles) {
    	
    	List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
    	
    	for (String role : roles) {
    		authorities.add(new SimpleGrantedAuthority(role));
    	}
    	
    	return authorities;
    	
    	
    }
}
