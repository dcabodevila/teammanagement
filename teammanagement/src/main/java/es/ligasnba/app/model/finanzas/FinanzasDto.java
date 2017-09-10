package es.ligasnba.app.model.finanzas;

import java.math.BigDecimal;
import java.util.List;

public class FinanzasDto{

	private List<AsientoDto> listaAsientos;
	private BigDecimal balanceTemporada;
	public List<AsientoDto> getListaAsientos() {
		return listaAsientos;
	}
	public void setListaAsientos(List<AsientoDto> listaAsientos) {
		this.listaAsientos = listaAsientos;
	}
	public BigDecimal getBalanceTemporada() {
		return balanceTemporada;
	}
	public void setBalanceTemporada(BigDecimal balanceTemporada) {
		this.balanceTemporada = balanceTemporada;
	}
	
	
	
}
