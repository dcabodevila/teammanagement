package es.ligasnba.app.model.equipo;

import java.util.ArrayList;
import java.util.List;



import es.ligasnba.app.model.jugador.PlayerData;
import es.ligasnba.app.model.equipodefault.EquipoDefault;
import es.ligasnba.app.model.jugador.Jugador;

public class TeamForm {

	private String nombreEquipo;
	private long idEquipo;
	private long idCompeticion;
	private List<Jugador> listaJugadores = new ArrayList<Jugador>();
	private List<PlayerData> listaInfoJugadores = new ArrayList<PlayerData>();
	private List<EquipoDefault> listaEquiposDefault = new ArrayList<EquipoDefault>();
	private Long selectedIdEquipoDefault;
	private boolean propietarioEquipo;
	private boolean adminCompeticion;	
	
	public TeamForm(List<Jugador> lista) {
		this.listaJugadores = lista;
	}
	
	
	
	public TeamForm() {
	}
	
	public List<PlayerData> getListaInfoJugadores() {
		return listaInfoJugadores;
	}
	public void setListaInfoJugadores(List<PlayerData> listaInfoJugadores) {
		this.listaInfoJugadores = listaInfoJugadores;
	}
	
	public List<Jugador> getListaJugadores() {
		return listaJugadores;
	}
	public void setListaJugadores(List<Jugador> listaJugadores) {
		this.listaJugadores = listaJugadores;
	}
	public List<EquipoDefault> getListaEquiposDefault() {
		return listaEquiposDefault;
	}
	public void setListaEquiposDefault(List<EquipoDefault> listaEquiposDefault) {
		this.listaEquiposDefault = listaEquiposDefault;
	}



	public Long getSelectedIdEquipoDefault() {
		return selectedIdEquipoDefault;
	}



	public void setSelectedIdEquipoDefault(Long selectedIdEquipoDefault) {
		this.selectedIdEquipoDefault = selectedIdEquipoDefault;
	}



	public String getNombreEquipo() {
		return nombreEquipo;
	}



	public void setNombreEquipo(String nombreEquipo) {
		this.nombreEquipo = nombreEquipo;
	}



	public long getIdEquipo() {
		return idEquipo;
	}



	public void setIdEquipo(long idEquipo) {
		this.idEquipo = idEquipo;
	}



	public long getIdCompeticion() {
		return idCompeticion;
	}



	public void setIdCompeticion(long idCompeticion) {
		this.idCompeticion = idCompeticion;
	}



	public boolean isPropietarioEquipo() {
		return propietarioEquipo;
	}



	public void setPropietarioEquipo(boolean propietarioEquipo) {
		this.propietarioEquipo = propietarioEquipo;
	}



	public boolean isAdminCompeticion() {
		return adminCompeticion;
	}



	public void setAdminCompeticion(boolean adminCompeticion) {
		this.adminCompeticion = adminCompeticion;
	}

	
	
}
