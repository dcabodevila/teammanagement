package es.ligasnba.app.model.contrato;

import java.math.BigDecimal;
import java.util.Date;

public class ValoracionOfertaContratoDto {
	private long idContrato;
	private long idEquipo;
	private long idCompeticion;
	private String nombreEquipo;
	private long idJugador;
	private String nombreJugador;
	private BigDecimal salarioTemporada1;
	private BigDecimal salarioTemporada2;
	private BigDecimal salarioTemporada3;
	private BigDecimal valoracionMoney;
	private int moneyInterest;
	private BigDecimal valoracionWinning;
	private int winningInterest;
	private BigDecimal valoracionLoyalty;
	private int loyaltyInterest;
	private BigDecimal valoracionGlobal;	
	private BigDecimal valoracionGlobalExigida;
	
	private boolean esSignAndTrade;
	private Date fecha;
	
	public long getIdContrato() {
		return idContrato;
	}
	public void setIdContrato(long idContrato) {
		this.idContrato = idContrato;
	}
	public long getIdEquipo() {
		return idEquipo;
	}
	public void setIdEquipo(long idEquipo) {
		this.idEquipo = idEquipo;
	}
	public String getNombreEquipo() {
		return nombreEquipo;
	}
	public void setNombreEquipo(String nombreEquipo) {
		this.nombreEquipo = nombreEquipo;
	}
	public long getIdJugador() {
		return idJugador;
	}
	public void setIdJugador(long idJugador) {
		this.idJugador = idJugador;
	}
	public String getNombreJugador() {
		return nombreJugador;
	}
	public void setNombreJugador(String nombreJugador) {
		this.nombreJugador = nombreJugador;
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
	public BigDecimal getValoracionMoney() {
		return valoracionMoney;
	}
	public void setValoracionMoney(BigDecimal valoracionMoney) {
		this.valoracionMoney = valoracionMoney;
	}
	public BigDecimal getValoracionWinning() {
		return valoracionWinning;
	}
	public void setValoracionWinning(BigDecimal valoracionWinning) {
		this.valoracionWinning = valoracionWinning;
	}
	public BigDecimal getValoracionLoyalty() {
		return valoracionLoyalty;
	}
	public void setValoracionLoyalty(BigDecimal valoracionLoyalty) {
		this.valoracionLoyalty = valoracionLoyalty;
	}
	public BigDecimal getValoracionGlobal() {
		return valoracionGlobal;
	}
	public void setValoracionGlobal(BigDecimal valoracionGlobal) {
		this.valoracionGlobal = valoracionGlobal;
	}
	public boolean isEsSignAndTrade() {
		return esSignAndTrade;
	}
	public void setEsSignAndTrade(boolean esSignAndTrade) {
		this.esSignAndTrade = esSignAndTrade;
	}
	public long getIdCompeticion() {
		return idCompeticion;
	}
	public void setIdCompeticion(long idCompeticion) {
		this.idCompeticion = idCompeticion;
	}
	public int getMoneyInterest() {
		return moneyInterest;
	}
	public void setMoneyInterest(int moneyInterest) {
		this.moneyInterest = moneyInterest;
	}
	public int getWinningInterest() {
		return winningInterest;
	}
	public void setWinningInterest(int winningInterest) {
		this.winningInterest = winningInterest;
	}
	public int getLoyaltyInterest() {
		return loyaltyInterest;
	}
	public void setLoyaltyInterest(int loyaltyInterest) {
		this.loyaltyInterest = loyaltyInterest;
	}
	public Date getFecha() {
		return fecha;
	}
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}
	public BigDecimal getValoracionGlobalExigida() {
		return valoracionGlobalExigida;
	}
	public void setValoracionGlobalExigida(BigDecimal valoracionGlobalExigida) {
		this.valoracionGlobalExigida = valoracionGlobalExigida;
	}
	
	
	
}
