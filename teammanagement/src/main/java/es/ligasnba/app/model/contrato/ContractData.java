package es.ligasnba.app.model.contrato;

import java.math.BigDecimal;

public class ContractData {

	private long idJugador;
	private String nombre;
	private long idContrato;
	private BigDecimal salarioTemporada1;
	private BigDecimal salarioTemporada2;
	private BigDecimal salarioTemporada3;
	private boolean po;
	private boolean to;
	
	public long getIdContrato() {
		return idContrato;
	}
	public long getIdJugador() {
		return idJugador;
	}
	public String getNombre() {
		return nombre;
	}
	public boolean isPo() {
		return po;
	}
	public boolean isTo() {
		return to;
	}
	
	public void setIdContrato(long idContrato) {
		this.idContrato = idContrato;
	}
	public void setIdJugador(long idJugador) {
		this.idJugador = idJugador;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public void setPo(boolean po) {
		this.po = po;
	}
	public void setTo(boolean to) {
		this.to = to;
	}
	public BigDecimal getSalarioTemporada1() {
		return salarioTemporada1;
	}
	public void setSalarioTemporada1(BigDecimal salarioTemporada1) {
		this.salarioTemporada1 = salarioTemporada1;
	}
	public BigDecimal getSalarioTemporada2() {
		return salarioTemporada2;
	}
	public void setSalarioTemporada2(BigDecimal salarioTemporada2) {
		this.salarioTemporada2 = salarioTemporada2;
	}
	public BigDecimal getSalarioTemporada3() {
		return salarioTemporada3;
	}
	public void setSalarioTemporada3(BigDecimal salarioTemporada3) {
		this.salarioTemporada3 = salarioTemporada3;
	}
	
	
}
