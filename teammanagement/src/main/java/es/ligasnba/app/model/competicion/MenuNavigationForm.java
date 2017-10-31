package es.ligasnba.app.model.competicion;

import es.ligasnba.app.model.equipo.Equipo;

public class MenuNavigationForm {
	private Long idCompeticion;
	private String nombreCompeticion;
	private Short estadoCompeticion;
	private Long idEquipo;
	private Long idTemporada;
	private boolean mercadoAbierto;
	
	public MenuNavigationForm() {
		// TODO Auto-generated constructor stub
	}
	public MenuNavigationForm(Competicion c, Equipo e) {
		this.idCompeticion = c.getIdCompeticion();
		this.nombreCompeticion = c.getNombre();
		this.estadoCompeticion = c.getTipoEstadoCompeticion().getIdTipoEstadoCompeticion();
		this.idEquipo = e.getIdEquipo();
	}
	
	public Long getIdCompeticion() {
		return idCompeticion;
	}
	public void setIdCompeticion(Long idCompeticion) {
		this.idCompeticion = idCompeticion;
	}
	public String getNombreCompeticion() {
		return nombreCompeticion;
	}
	public void setNombreCompeticion(String nombreCompeticion) {
		this.nombreCompeticion = nombreCompeticion;
	}
	public Short getEstadoCompeticion() {
		return estadoCompeticion;
	}
	public void setEstadoCompeticion(Short estadoCompeticion) {
		this.estadoCompeticion = estadoCompeticion;
	}
	public Long getIdEquipo() {
		return idEquipo;
	}
	public void setIdEquipo(Long idEquipo) {
		this.idEquipo = idEquipo;
	}
	public Long getIdTemporada() {
		return idTemporada;
	}
	public void setIdTemporada(Long idTemporada) {
		this.idTemporada = idTemporada;
	}
	public boolean isMercadoAbierto() {
		return mercadoAbierto;
	}
	public void setMercadoAbierto(boolean mercadoAbierto) {
		this.mercadoAbierto = mercadoAbierto;
	}
	
}
