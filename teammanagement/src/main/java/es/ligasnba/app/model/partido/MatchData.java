package es.ligasnba.app.model.partido;

import java.util.Date;

public class MatchData {

	private long idPartido;
	private long idEquipo;
	private String nombreEquipo;
	private String logo;
	private Date fecha;
	private Boolean validado;
	private Boolean discrepancia;
	private String nombreUsuario;

	public Date getFecha() {
		return fecha;
	}
	public long getIdPartido() {
		return idPartido;
	}
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}
	public void setIdPartido(long idPartido) {
		this.idPartido = idPartido;
	}
	public Boolean getValidado() {
		return validado;
	}
	public void setValidado(Boolean validado) {
		this.validado = validado;
	}
	public long getIdEquipo() {
		return idEquipo;
	}
	public void setIdEquipo(long idEquipo) {
		this.idEquipo = idEquipo;
	}

	public String getLogo() {
		return logo;
	}
	public void setLogo(String logo) {
		this.logo = logo;
	}
	public String getNombreEquipo() {
		return nombreEquipo;
	}
	public void setNombreEquipo(String nombreEquipo) {
		this.nombreEquipo = nombreEquipo;
	}
	public String getNombreUsuario() {
		return nombreUsuario;
	}
	public void setNombreUsuario(String nombreUsuario) {
		this.nombreUsuario = nombreUsuario;
	}
	public Boolean getDiscrepancia() {
		return discrepancia;
	}
	public void setDiscrepancia(Boolean discrepancia) {
		this.discrepancia = discrepancia;
	}
	
}
