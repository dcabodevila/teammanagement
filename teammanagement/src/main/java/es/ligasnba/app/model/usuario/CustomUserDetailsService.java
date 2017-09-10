package es.ligasnba.app.model.usuario;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import es.ligasnba.app.model.equipo.Equipo;
import es.ligasnba.app.model.competicion.Competicion;
import es.ligasnba.app.model.competitionrol.CompetitionRol;
import es.ligasnba.app.model.competitionrol.competitionRolService;
import es.ligasnba.app.model.equipo.teamService;
import es.ligasnba.app.model.equipodefault.EquipoDefault;
import es.ligasnba.app.model.usuario.userService;
import es.ligasnba.app.util.Log;
import es.ligasnba.app.util.constants.Constants;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
 
/**
* A custom {@link UserDetailsService} where user information
* is retrieved from a JPA repository
*/


@Service
@Transactional(readOnly = true)
public class CustomUserDetailsService implements UserDetailsService {

	@Autowired
	private userService userservice;
	@Autowired
	private teamService teamservice;
	@Autowired
	private competitionRolService competitionrolservice;
	
	public void setTeamservice(teamService teamservice) {
		this.teamservice = teamservice;
	}
	public void setUserservice(userService userservice) {
		this.userservice = userservice;
	}
	public void setCompetitionrolservice(competitionRolService competitionrolservice) {
		this.competitionrolservice = competitionrolservice;
	}	
/**
* Returns a populated {@link UserDetails} object.
* The username is first retrieved from the database and then mapped to
* a {@link UserDetails} object.
*/
public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
	try {
		
			Usuario domainUser = userservice.findByLogin(username);
			boolean enabled = true;
			boolean accountNonExpired = true;
			boolean credentialsNonExpired = true;
			boolean accountNonLocked = true;
			
			/*
			 * Cargamos los permisos de acceso a las competiciones (con el nivel de acceso) y los equipos que son propiedad del usuario
			 */			
			
			List<CompetitionRol> listCompetitionRol = competitionrolservice.getCompetitionRolesOfUser(domainUser.getIdUsuario());
			
			List<Equipo> teamList = teamservice.getTeams(domainUser.getIdUsuario(), Constants.cMinCompetitionsByUser, Constants.cMaxCompetitionsByUser*2).getEquipos();
			
			List<EquipoDefault> defaultTeamList = teamservice.findEquiposDefaultByConferenceAndDivision(null, null);			
			
			Map<Long, Equipo> mapTeamsUser = new HashMap<Long, Equipo>();
			Map<Long, Competicion> mapCompeticiones = new HashMap<Long, Competicion>();
			if (teamList!=null){
				for (Equipo equipo : teamList){
					mapTeamsUser.put(equipo.getCompeticion().getIdCompeticion(), equipo);
					mapCompeticiones.put(equipo.getCompeticion().getIdCompeticion(), equipo.getCompeticion());
				}
			}
			
			
			return new CustomAuthenticatedUser(domainUser, listCompetitionRol,
			mapTeamsUser,
			mapCompeticiones,
			defaultTeamList,
			enabled,
			accountNonExpired,
			credentialsNonExpired,
			accountNonLocked,
			getAuthorities(domainUser.getRol().getRol()));
			
			
			
		} catch (Exception e) {
			Log.LogFile(e.getMessage());
		throw new RuntimeException(e);
		}
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
		roles.add("ROLE_USER");
		roles.add("ROLE_ADMIN");
	} else if (role.intValue() == 2) {
		roles.add("ROLE_USER");
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


//Devuelve el rol más alto que el usuario tiene para esa competición en la sesión actual

public static int userRoleInCompetition(long idCompeticion){				
	
	int highestRol=0;
	
			
	if (SecurityContextHolder.getContext().getAuthentication().getPrincipal().getClass()== String.class){
		
		return -1;
		
		
	}
		
		CustomAuthenticatedUser userSession = (CustomAuthenticatedUser)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		List<CompetitionRol> listaCompetitionRol = userSession.getListCompetitionRol();
		
		for (int i=0;i< listaCompetitionRol.size();i++){

			if (listaCompetitionRol.get(i).getCompeticion().getIdCompeticion()==idCompeticion){
			
				if (listaCompetitionRol.get(i).getRol()>highestRol)
					highestRol = listaCompetitionRol.get(i).getRol();												
			}				
			
		}
	

	return highestRol;
}

public static boolean checkUserIsAdminOfCompetition(long idCompeticion){
	
	if (SecurityContextHolder.getContext().getAuthentication().getPrincipal().getClass()== String.class){
		
		return false;	
		
	}
		
	CustomAuthenticatedUser userSession = (CustomAuthenticatedUser)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	List<CompetitionRol> listaCompetitionRol = userSession.getListCompetitionRol();

	for (CompetitionRol competitionRol : listaCompetitionRol){
		
		if (competitionRol.getCompeticion().getIdCompeticion()==idCompeticion){
			if (competitionRol.getRol().equals(Constants.cRolAdminCompeticion)){
				return true;
			}
		}
	}
	
	return false;
	
}

public static boolean checkUserIsPropietarioEquipo(long idEquipo){
	if (SecurityContextHolder.getContext().getAuthentication().getPrincipal().getClass()== String.class){		
		return false;			
	}
	
	CustomAuthenticatedUser userSession = (CustomAuthenticatedUser)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	
	for (Equipo e : userSession.getListaEquipos()){
		if (e.getIdEquipo()==idEquipo){
			return true;
		}
	}
	
	return false;
	
}





}