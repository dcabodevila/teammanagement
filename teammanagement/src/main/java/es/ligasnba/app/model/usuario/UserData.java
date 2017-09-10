package es.ligasnba.app.model.usuario;

public class UserData {

	private long idUsuario;
	private String nombreUsuario;
	private long idEquipo;
	private String nombreEquipo;
	
	
	public long getIdEquipo() {
		return idEquipo;
	}
	public long getIdUsuario() {
		return idUsuario;
	}
	public String getNombreEquipo() {
		return nombreEquipo;
	}
	public String getNombreUsuario() {
		return nombreUsuario;
	}
	
	public void setIdEquipo(long idEquipo) {
		this.idEquipo = idEquipo;
	}
	public void setIdUsuario(long idUsuario) {
		this.idUsuario = idUsuario;
	}
	public void setNombreEquipo(String nombreEquipo) {
		this.nombreEquipo = nombreEquipo;
	}
	public void setNombreUsuario(String nombreUsuario) {
		this.nombreUsuario = nombreUsuario;
	}
}
