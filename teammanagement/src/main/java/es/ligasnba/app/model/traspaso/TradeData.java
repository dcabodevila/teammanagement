package es.ligasnba.app.model.traspaso;

import java.util.ArrayList;
import java.util.List;

public class TradeData {

	private long idTraspaso;
	private List<String> nombresJugadoresOfrecidos = new ArrayList<String>();
	private List<String> nombresJugadoresRecibidos = new ArrayList<String>();
	private long idEquipoPropone;
	private String nombreEquipoPropone;
	private long idEquipoRecibe;
	private String nombreEquipoRecibe;
	
	
	public long getIdEquipoPropone() {
		return idEquipoPropone;
	}
	public long getIdEquipoRecibe() {
		return idEquipoRecibe;
	}
	public long getIdTraspaso() {
		return idTraspaso;
	}
	public String getNombreEquipoPropone() {
		return nombreEquipoPropone;
	}
	public String getNombreEquipoRecibe() {
		return nombreEquipoRecibe;
	}
	public List<String> getNombresJugadoresOfrecidos() {
		return nombresJugadoresOfrecidos;
	}
	public List<String> getNombresJugadoresRecibidos() {
		return nombresJugadoresRecibidos;
	}
	
	public void setIdEquipoPropone(long idEquipoPropone) {
		this.idEquipoPropone = idEquipoPropone;
	}
	public void setIdEquipoRecibe(long idEquipoRecibe) {
		this.idEquipoRecibe = idEquipoRecibe;
	}
	public void setIdTraspaso(long idTraspaso) {
		this.idTraspaso = idTraspaso;
	}
	public void setNombreEquipoPropone(String nombreEquipoPropone) {
		this.nombreEquipoPropone = nombreEquipoPropone;
	}
	public void setNombreEquipoRecibe(String nombreEquipoRecibe) {
		this.nombreEquipoRecibe = nombreEquipoRecibe;
	}
	public void setNombresJugadoresOfrecidos(
			List<String> nombresJugadoresOfrecidos) {
		this.nombresJugadoresOfrecidos = nombresJugadoresOfrecidos;
	}
	public void setNombresJugadoresRecibidos(
			List<String> nombresJugadoresRecibidos) {
		this.nombresJugadoresRecibidos = nombresJugadoresRecibidos;
	}
	
}
