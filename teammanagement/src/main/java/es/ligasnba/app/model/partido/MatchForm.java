package es.ligasnba.app.model.partido;

public class MatchForm{
	private boolean propietarioEquipo;
	private boolean adminCompeticion;
	
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
