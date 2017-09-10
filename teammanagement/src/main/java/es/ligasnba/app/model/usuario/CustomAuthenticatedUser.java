package es.ligasnba.app.model.usuario;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.security.core.userdetails.User;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import es.ligasnba.app.model.competicion.Competicion;
import es.ligasnba.app.model.competitionrol.CompetitionRol;
import es.ligasnba.app.model.equipo.Equipo;
import es.ligasnba.app.model.equipodefault.EquipoDefault;
import es.ligasnba.app.util.exceptions.InstanceNotFoundException;

public class CustomAuthenticatedUser extends User{
	
	private static final long serialVersionUID = 1L;
	private long userId;
	private Usuario usuario;
	private List<EquipoDefault> listaEquiposDefault = new ArrayList<EquipoDefault>();
	private Map<Long, Equipo> mapEquipos = new HashMap<Long, Equipo>();
	private Map<Long, Competicion> mapCompeticiones = new HashMap<Long, Competicion>();
	
	private List<CompetitionRol> listCompetitionRol = new ArrayList<CompetitionRol>(); 
	public CustomAuthenticatedUser(Usuario u, List<CompetitionRol> listCompetitionRol, Map<Long, Equipo> mapEquipos, Map<Long, Competicion> mapCompeticiones,
			List<EquipoDefault> listaEquiposDefault, 
			boolean enabled, boolean accountNonExpired,
			boolean credentialsNonExpired, boolean accountNonLocked,
			Collection<? extends GrantedAuthority> authorities) {
		super(u.getLogin(), u.getPass().toLowerCase(), enabled, accountNonExpired, credentialsNonExpired,
				accountNonLocked, authorities);
		this.usuario = u;
		this.userId = u.getIdUsuario();
		this.mapEquipos = mapEquipos;
		this.listaEquiposDefault = listaEquiposDefault;
		this.listCompetitionRol= listCompetitionRol;
		this.mapCompeticiones = mapCompeticiones;
	}

	
	public long getUserId() {
		return userId;
	}
	public void setUserId(long userId) {
		this.userId = userId;
	}
	public Usuario getUsuario() {
		return usuario;
	}
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	public List<Equipo> getListaEquipos() {
		List<Equipo> listaEquipos = new ArrayList<Equipo>();
		if (mapEquipos!=null){
			for (Long idCompeticion : mapEquipos.keySet()){
				listaEquipos.add(mapEquipos.get(idCompeticion));
			}
		}
		return listaEquipos;
	}
	public void setListaEquipos(List<Equipo> listaEquipos) {
		if (listaEquipos!=null){
			for (Equipo equipo : listaEquipos){
				this.mapEquipos.put(equipo.getCompeticion().getIdCompeticion(), equipo);
			}
		}
	}
	public List<CompetitionRol> getListCompetitionRol() {
		return listCompetitionRol;
	}
	public void setListCompetitionRol(List<CompetitionRol> listCompetitionRol) {
		this.listCompetitionRol = listCompetitionRol;
	}	

	public void addCompetitionRol(CompetitionRol c){
		getListCompetitionRol().add(c);
	}
	public void remCompetitionRol(CompetitionRol c){
		getListCompetitionRol().remove(c);
	}
	public void addEquipo(Equipo e){
		this.mapEquipos.put(e.getCompeticion().getIdCompeticion(), e);
	}
	public void remEquipo(Equipo e){
		getListaEquipos().remove(e);
	}

	public Equipo getUserTeamInCompetition(long idCompeticion) throws InstanceNotFoundException{
				
		return this.mapEquipos.get(idCompeticion);
		
	}


	public Map<Long, Equipo> getMapEquipos() {
		return mapEquipos;
	}
	
	public Map<Long, Competicion> getMapCompeticiones() {
		return mapCompeticiones;
	}

	public void setMapEquipos(Map<Long, Equipo> mapEquipos) {
		this.mapEquipos = mapEquipos;
	}


	public List<EquipoDefault> getListaEquiposDefault() {
		return listaEquiposDefault;
	}


	public void setListaEquiposDefault(List<EquipoDefault> listaEquiposDefault) {
		this.listaEquiposDefault = listaEquiposDefault;
	}

	public Competicion getCompeticionByIdEquipo(Long idEquipo){
		
		if (idEquipo!=null){
	
			Set<Long> idsCompeticion = this.getMapEquipos().keySet();		
			List<Long> listaIdsCompeticion = new ArrayList<Long>(idsCompeticion);
			
			
			
			for (Long idCompeticion : listaIdsCompeticion){
				if (this.getMapEquipos().get(idCompeticion).getIdEquipo()==idEquipo){
					return this.getMapCompeticiones().get(idCompeticion);
				} 
			}			
		}
		
		return null;
	}
	

}
