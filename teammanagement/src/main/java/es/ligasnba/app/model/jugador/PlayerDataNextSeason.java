package es.ligasnba.app.model.jugador;

import java.math.BigDecimal;
import es.ligasnba.app.util.jqgrid.CustomGenericResponse;

public class PlayerDataNextSeason extends CustomGenericResponse{
	private long idJugador;
	private String nombre;
	private BigDecimal nextSalary;

	public long getIdJugador() {
		return idJugador;
	}
	public String getNombre() {
		return nombre;
	}
	public void setIdJugador(long idJugador) {
		this.idJugador = idJugador;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public BigDecimal getNextSalary() {
		return nextSalary;
	}
	public void setNextSalary(BigDecimal nextSalary) {
		this.nextSalary = nextSalary;
	}	
	
}
