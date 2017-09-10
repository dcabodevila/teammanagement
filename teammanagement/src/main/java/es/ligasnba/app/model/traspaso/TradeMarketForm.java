package es.ligasnba.app.model.traspaso;

import java.util.List;

import es.ligasnba.app.model.equipo.Equipo;

public class TradeMarketForm {

	private Equipo equipo;
	private List<TradeData> listaTraspasosRecibidos;
	private List<TradeData> listaTraspasosOfrecidos;
	
	
	public Equipo getEquipo() {
		return equipo;
	}
	public List<TradeData> getListaTraspasosOfrecidos() {
		return listaTraspasosOfrecidos;
	}
	public List<TradeData> getListaTraspasosRecibidos() {
		return listaTraspasosRecibidos;
	}
	
	public void setEquipo(Equipo equipo) {
		this.equipo = equipo;
	}
	public void setListaTraspasosOfrecidos(
			List<TradeData> listaTraspasosOfrecidos) {
		this.listaTraspasosOfrecidos = listaTraspasosOfrecidos;
	}
	public void setListaTraspasosRecibidos(
			List<TradeData> listaTraspasosRecibidos) {
		this.listaTraspasosRecibidos = listaTraspasosRecibidos;
	}
	
}
