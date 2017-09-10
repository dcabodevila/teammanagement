package es.ligasnba.app.model.jugador;

import java.util.ArrayList;
import java.util.List;

import es.ligasnba.app.model.equipodefault.EquipoDefault;

public class DraftPlayersForm {

	private long idEquipo;
	private Integer selectedIdTeam;
	private long idCompeticion;
	private List<EquipoDefault> listAvailableTeams = new ArrayList<EquipoDefault>();
	private List<Jugador> listAvailablePlayers = new ArrayList<Jugador>();
	private List<Jugador> listaJugadoresSeleccionados = new ArrayList<Jugador>();
	private boolean success;
	private String message;
	
	public DraftPlayersForm() {
		this.idEquipo = 0;
		this.idCompeticion = 0;
	}
	
	public long getIdCompeticion() {
		return idCompeticion;
	}
	public void setIdCompeticion(long idCompeticion) {
		this.idCompeticion = idCompeticion;
	}
	public long getidEquipo() {
		return idEquipo;
	}
	public void setidEquipo(long idEquipo) {
		this.idEquipo = idEquipo;
	}
	public List<EquipoDefault> getListAvailableTeams() {
		return listAvailableTeams;
	}
	public void setListAvailableTeams(List<EquipoDefault> listAvailableTeams) {
		this.listAvailableTeams = listAvailableTeams;
	}
	public void setSuccess(boolean success) {
		this.success = success;
	}
	public boolean isSuccess() {
		return success;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}

	public List<Jugador> getListaJugadoresSeleccionados() {
		return listaJugadoresSeleccionados;
	}

	public void setListaJugadoresSeleccionados(List<Jugador> listaJugadoresSeleccionados) {
		this.listaJugadoresSeleccionados = listaJugadoresSeleccionados;
	}

	public List<Jugador> getListAvailablePlayers() {
		return listAvailablePlayers;
	}

	public void setListAvailablePlayers(List<Jugador> listAvailablePlayers) {
		this.listAvailablePlayers = listAvailablePlayers;
	}

	public Integer getSelectedIdTeam() {
		return selectedIdTeam;
	}

	public void setSelectedIdTeam(Integer selectedIdTeam) {
		this.selectedIdTeam = selectedIdTeam;
	}
	
	
	
}
