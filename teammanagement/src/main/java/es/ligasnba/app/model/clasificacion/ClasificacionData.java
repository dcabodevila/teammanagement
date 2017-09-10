package es.ligasnba.app.model.clasificacion;

public class ClasificacionData {
    private long idEquipo;
	private String nombreEquipo;
	private String nombreUsuario;
	private String image;
	private int victorias;
	private int empates;
	private int derrotas;
	
	public ClasificacionData() {
		// TODO Auto-generated constructor stub
	}
	public long getIdEquipo() {
		return idEquipo;
	}
	public int getDerrotas() {
		return derrotas;
	}
	public int getEmpates() {
		return empates;
	}
	public String getImage() {
		return image;
	}
	public String getNombreEquipo() {
		return nombreEquipo;
	}
	public int getVictorias() {
		return victorias;
	}
	public String getNombreUsuario() {
		return nombreUsuario;
	}
	
	public void setIdEquipo(long idEquipo) {
		this.idEquipo = idEquipo;
	}
	public void setDerrotas(int derrotas) {
		this.derrotas = derrotas;
	}
	public void setEmpates(int empates) {
		this.empates = empates;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public void setNombreEquipo(String nombreEquipo) {
		this.nombreEquipo = nombreEquipo;
	}
	public void setVictorias(int victorias) {
		this.victorias = victorias;
	}
	public void setNombreUsuario(String nombreUsuario) {
		this.nombreUsuario = nombreUsuario;
	}
	
	
	
}
