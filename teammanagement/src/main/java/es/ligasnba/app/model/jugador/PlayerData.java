package es.ligasnba.app.model.jugador;

import java.math.BigDecimal;
import es.ligasnba.app.util.jqgrid.CustomGenericResponse;

public class PlayerData extends CustomGenericResponse{
	private long idJugador;
	private Long idContrato;
	private String nombre;
	private String posicion1;
	private String posicion2;
	private Integer media;
	private Integer edad;
	private Integer minutos;
	private String imagen;
	private Long idEquipo;
	private String equipoOriginal;
	private String logoEquipoOriginal;	
	private Integer contractYears;	
	private BigDecimal currentSalary;
	private BigDecimal nextSalary;
	private int moneyInterest;
	private int winningInterest;
	private int loyaltyInterest;
	private int yearsPro;
	private BigDecimal cache;
	private Boolean pendiente;
	
	
	
	public Integer getContractYears() {
		return contractYears;
	}
	public BigDecimal getCurrentSalary() {
		return currentSalary;
	}
	public long getIdJugador() {
		return idJugador;
	}
	public String getImagen() {
		return imagen;
	}
	public Integer getMedia() {
		return media;
	}
	public Integer getMinutos() {
		return minutos;
	}
	public String getNombre() {
		return nombre;
	}
	public String getPosicion1() {
		return posicion1;
	}
	public String getPosicion2() {
		return posicion2;
	}
	public Integer getEdad() {
		return edad;
	}
	
	public void setContractYears(Integer contractYears) {
		this.contractYears = contractYears;
	}
	public void setCurrentSalary(BigDecimal currentSalary) {
		this.currentSalary = currentSalary;
	}
	public void setIdJugador(long idJugador) {
		this.idJugador = idJugador;
	}
	public void setImagen(String imagen) {
		this.imagen = imagen;
	}
	public void setMedia(Integer media) {
		this.media = media;
	}
	public void setMinutos(Integer minutos) {
		this.minutos = minutos;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public void setPosicion1(String posicion1) {
		this.posicion1 = posicion1;
	}
	public void setPosicion2(String posicion2) {
		this.posicion2 = posicion2;
	}
	public void setEdad(Integer edad) {
		this.edad = edad;
	}
	public String getEquipoOriginal() {
		return equipoOriginal;
	}
	public void setEquipoOriginal(String equipoOriginal) {
		this.equipoOriginal = equipoOriginal;
	}
	public String getLogoEquipoOriginal() {
		return logoEquipoOriginal;
	}
	public void setLogoEquipoOriginal(String logoEquipoOriginal) {
		this.logoEquipoOriginal = logoEquipoOriginal;
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
	public BigDecimal getNextSalary() {
		return nextSalary;
	}
	public void setNextSalary(BigDecimal nextSalary) {
		this.nextSalary = nextSalary;
	}
	public int getYearsPro() {
		return yearsPro;
	}
	public void setYearsPro(int yearsPro) {
		this.yearsPro = yearsPro;
	}
	public Long getIdContrato() {
		return idContrato;
	}
	public void setIdContrato(Long idContrato) {
		this.idContrato = idContrato;
	}
	public BigDecimal getCache() {
		return cache;
	}
	public void setCache(BigDecimal cache) {
		this.cache = cache;
	}
	public Boolean isPendiente() {
		return pendiente;
	}
	public void setPendiente(Boolean pendiente) {
		this.pendiente = pendiente;
	}
	public Long getIdEquipo() {
		return idEquipo;
	}
	public void setIdEquipo(Long idEquipo) {
		this.idEquipo = idEquipo;
	}	
	
}
