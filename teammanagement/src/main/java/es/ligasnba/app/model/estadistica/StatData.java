package es.ligasnba.app.model.estadistica;

public class StatData {

	private long idEstadistica;
	private String nombreJugador;
	private int puntos;
	private int asistencias;
	private int rebotes;
	private int minutos;
	
	public int getAsistencias() {
		return asistencias;
	}
	public long getIdEstadistica() {
		return idEstadistica;
	}
	public String getNombreJugador() {
		return nombreJugador;
	}
	public int getPuntos() {
		return puntos;
	}
	public int getRebotes() {
		return rebotes;
	}
	public int getMinutos() {
		return minutos;
	}
	
	public void setAsistencias(int asistencias) {
		this.asistencias = asistencias;
	}
	public void setIdEstadistica(long idEstadistica) {
		this.idEstadistica = idEstadistica;
	}
	public void setNombreJugador(String nombreJugador) {
		this.nombreJugador = nombreJugador;
	}
	public void setPuntos(int puntos) {
		this.puntos = puntos;
	}
	public void setRebotes(int rebotes) {
		this.rebotes = rebotes;
	}
	public void setMinutos(int minutos) {
		this.minutos = minutos;
	}
	
}
