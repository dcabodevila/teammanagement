package es.ligasnba.app.model.segundoPlano;

import java.math.BigDecimal;

import es.ligasnba.app.model.contrato.Contrato;

public class ResultadoValoracionContratoDto implements Comparable<ResultadoValoracionContratoDto>{
	
	private Contrato contrato;
	private BigDecimal valoracionMoney;
	private Integer moneyInterest; 
	private BigDecimal valoracionWinning;
	private Integer winningInterest;
	private BigDecimal valoracionLoyalty;
	private Integer loyaltyInterest;
	private BigDecimal valoracionGlobal;
	private BigDecimal notaExigida;
	private BigDecimal valoracionMaxima;
	private BigDecimal valoracionGlobalPrevia;
	
	public ResultadoValoracionContratoDto(Contrato c) {
		this.contrato = c;
		if (c!=null){
			this.moneyInterest = c.getJugador().getMoneyInterest();
			this.winningInterest = c.getJugador().getWinningInterest();
			this.loyaltyInterest = c.getJugador().getLoyaltyInterest();
		}
		
		this.valoracionMoney = new BigDecimal(0);
		this.valoracionWinning = new BigDecimal(0);
		this.valoracionLoyalty = new BigDecimal(0);
		this.valoracionGlobal = new BigDecimal(0);
	}
	
	public Contrato getContrato() {
		return contrato;
	}



	public void setContrato(Contrato contrato) {
		this.contrato = contrato;
	}



	public BigDecimal getValoracionMoney() {
		return valoracionMoney;
	}



	public void setValoracionMoney(BigDecimal valoracionMoney) {
		this.valoracionMoney = valoracionMoney;
	}



	public Integer getMoneyInterest() {
		return moneyInterest;
	}



	public void setMoneyInterest(Integer moneyInterest) {
		this.moneyInterest = moneyInterest;
	}



	public BigDecimal getValoracionWinning() {
		return valoracionWinning;
	}



	public void setValoracionWinning(BigDecimal valoracionWinning) {
		this.valoracionWinning = valoracionWinning;
	}



	public Integer getWinningInterest() {
		return winningInterest;
	}



	public void setWinningInterest(Integer winningInterest) {
		this.winningInterest = winningInterest;
	}



	public BigDecimal getValoracionLoyalty() {
		return valoracionLoyalty;
	}



	public void setValoracionLoyalty(BigDecimal valoracionLoyalty) {
		this.valoracionLoyalty = valoracionLoyalty;
	}



	public Integer getLoyaltyInterest() {
		return loyaltyInterest;
	}



	public void setLoyaltyInterest(Integer loyaltyInterest) {
		this.loyaltyInterest = loyaltyInterest;
	}



	public BigDecimal getValoracionGlobal() {
		return valoracionGlobal;
	}



	public void setValoracionGlobal(BigDecimal valoracionGlobal) {
		this.valoracionGlobal = valoracionGlobal;
	}


	@Override
	public int compareTo(ResultadoValoracionContratoDto o) {
		return this.valoracionGlobal.compareTo(o.getValoracionGlobal());	
	}

	public BigDecimal getNotaExigida() {
		return notaExigida;
	}

	public void setNotaExigida(BigDecimal notaExigida) {
		this.notaExigida = notaExigida;
	}

	public BigDecimal getValoracionMaxima() {
		return valoracionMaxima;
	}

	public void setValoracionMaxima(BigDecimal valoracionMaxima) {
		this.valoracionMaxima = valoracionMaxima;
	}

	public BigDecimal getValoracionGlobalPrevia() {
		return valoracionGlobalPrevia;
	}

	public void setValoracionGlobalPrevia(BigDecimal valoracionGlobalPrevia) {
		this.valoracionGlobalPrevia = valoracionGlobalPrevia;
	}
	

}
