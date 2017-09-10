package es.ligasnba.app.model.competicion;

import java.util.ArrayList;
import java.util.List;


import es.ligasnba.app.model.equipo.Equipo;
import es.ligasnba.app.model.partido.Partido;
import es.ligasnba.app.model.temporada.Temporada;
import es.ligasnba.app.model.usuario.Usuario;
public class AdminCompetitionForm {

	private Temporada temporada;
	
	private Competicion competition;
	
	private List<Usuario> listaUsuarios= new ArrayList<Usuario>();
	private List<Equipo> listaEquipos= new ArrayList<Equipo>();
	private List<Partido> listaPartidos= new ArrayList<Partido>();
	private List<Long> simulateMatches= new ArrayList<Long>();
	private List<Long> resetMatches= new ArrayList<Long>();
	private long idRemovedUser;
	private long idRemovedTeam;
	
	private String addedTeam;
	
	
	public Competicion getCompetition() {
		return competition;
	}
	public void setCompetition(Competicion competition) {
		this.competition = competition;
	}
	public Temporada getTemporada() {
		return temporada;
	}
	public void setTemporada(Temporada temporada) {
		this.temporada = temporada;
	}
	public List<Equipo> getListaEquipos() {
		return listaEquipos;
	}
	public List<Partido> getListaPartidos() {
		return listaPartidos;
	}
	public List<Usuario> getListaUsuarios() {
		return listaUsuarios;
	}
	public void setListaEquipos(List<Equipo> listaEquipos) {
		this.listaEquipos = listaEquipos;
	}
	public void setListaPartidos(List<Partido> listaPartidos) {
		this.listaPartidos = listaPartidos;
	}
	public void setListaUsuarios(List<Usuario> listaUsuarios) {
		this.listaUsuarios = listaUsuarios;
	}
	public long getidRemovedUser() {
		return idRemovedUser;
	}
	public void setidRemovedUser(long idRemovedUser) {
		this.idRemovedUser = idRemovedUser;
	}
	public long getIdRemovedTeam() {
		return idRemovedTeam;
	}
	public void setIdRemovedTeam(long idRemovedTeam) {
		this.idRemovedTeam = idRemovedTeam;
	}
	public String getAddedTeam() {
		return addedTeam;
	}
	public void setAddedTeam(String addedTeam) {
		this.addedTeam = addedTeam;
	}
	public List<Long> getSimulateMatches() {
		return simulateMatches;
	}
	public void setSimulateMatches(List<Long> simulateMatches) {
		this.simulateMatches = simulateMatches;
	}
	public List<Long> getResetMatches() {
		return resetMatches;
	}
	public void setResetMatches(List<Long> resetMatches) {
		this.resetMatches = resetMatches;
	}
	
}
