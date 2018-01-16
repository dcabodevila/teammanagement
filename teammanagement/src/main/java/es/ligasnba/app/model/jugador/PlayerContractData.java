package es.ligasnba.app.model.jugador;

import java.math.BigDecimal;

public class PlayerContractData {
	
	private String titulo;
	private long idJugador;
	private String nombre;
	private String posicion1;
	private String posicion2;
	private int media;
	private int edad;
	private int tipoOferta;
	private String imagen;

	private BigDecimal minSalary;
	private BigDecimal currentSalary;
	private BigDecimal maxSalary;
	private BigDecimal topSalary;
	private BigDecimal capSpace;	
	private BigDecimal presupuestoTotal;
	private BigDecimal sumaSalarial;
	private BigDecimal presupuestoRestante;
	private BigDecimal salaryCap;
	private BigDecimal luxuryTax;
	private BigDecimal midLevelException;
	private BigDecimal capConsumido = BigDecimal.ZERO;
	
	
	private int increase;
	private int maxSeasons;
	private boolean visibleMidLevelException;	
	
	public BigDecimal getCurrentSalary() {
		return currentSalary;
	}
	public BigDecimal getMaxSalary() {
		return maxSalary;
	}
	public long getIdJugador() {
		return idJugador;
	}
	public String getImagen() {
		return imagen;
	}
	public int getMedia() {
		return media;
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

	public int getMaxSeasons() {
		return maxSeasons;
	}
	public int getEdad() {
		return edad;
	}
	
	
	public void setCurrentSalary(BigDecimal actualSalary) {
		this.currentSalary = actualSalary;
	}
	public void setMaxSalary(BigDecimal maxSalary) {
		this.maxSalary = maxSalary;
	}	
	public void setIdJugador(long idJugador) {
		this.idJugador = idJugador;
	}
	public void setImagen(String imagen) {
		this.imagen = imagen;
	}
	public void setMedia(int media) {
		this.media = media;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public void setPosicion1(int posicion1) {

		this.posicion1 = getPosicionStr(posicion1);
	}
	public void setPosicion2(int posicion2) {

		this.posicion2 = getPosicionStr(posicion2);
	}

	public void setMaxSeasons(int maxSeasons) {
		this.maxSeasons = maxSeasons;
	}
	public void setEdad(int edad) {
		this.edad = edad;
	}
	
	private String getPosicionStr(int posicion){

		String posStr ="";
		if (posicion == 1)
			posStr = "B";
		else if (posicion == 2)
			posStr = "ES";
		else if (posicion == 3)
			posStr = "AL";
		else if (posicion == 4)
			posStr = "AP";
		else if (posicion == 5)
			posStr = "P";
		
		return posStr;
	}
	public BigDecimal getMinSalary() {
		return minSalary;
	}
	public void setMinSalary(BigDecimal minSalary) {
		this.minSalary = minSalary;
	}
	public int getIncrease() {
		return increase;
	}
	public void setIncrease(int increase) {
		this.increase = increase;
	}
	public BigDecimal getCapSpace() {
		return capSpace;
	}
	public void setCapSpace(BigDecimal capSpace) {
		this.capSpace = capSpace;
	}
	public BigDecimal getPresupuestoTotal() {
		return presupuestoTotal;
	}
	public void setPresupuestoTotal(BigDecimal presupuesto) {
		this.presupuestoTotal = presupuesto;
	}
	public BigDecimal getPresupuestoRestante() {
		return presupuestoRestante;
	}
	public void setPresupuestoRestante(BigDecimal presupuestoRestante) {
		this.presupuestoRestante = presupuestoRestante;
	}
	public BigDecimal getSumaSalarial() {
		return sumaSalarial;
	}
	public void setSumaSalarial(BigDecimal sumaSalarial) {
		this.sumaSalarial = sumaSalarial;
	}
	public String getTitulo() {
		return titulo;
	}
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
	public int getTipoOferta() {
		return tipoOferta;
	}
	public void setTipoOferta(int tipoOferta) {
		this.tipoOferta = tipoOferta;
	}
	public boolean isVisibleMidLevelException() {
		return visibleMidLevelException;
	}
	public void setVisibleMidLevelException(boolean visibleMidLevelException) {
		this.visibleMidLevelException = visibleMidLevelException;
	}
	public BigDecimal getSalaryCap() {
		return salaryCap;
	}
	public void setSalaryCap(BigDecimal salaryCap) {
		this.salaryCap = salaryCap;
	}
	public BigDecimal getLuxuryTax() {
		return luxuryTax;
	}
	public void setLuxuryTax(BigDecimal luxuryTax) {
		this.luxuryTax = luxuryTax;
	}
	public BigDecimal getMidLevelException() {
		return midLevelException;
	}
	public void setMidLevelException(BigDecimal midLevelException) {
		this.midLevelException = midLevelException;
	}
	public BigDecimal getTopSalary() {
		return topSalary;
	}
	public void setTopSalary(BigDecimal topSalary) {
		this.topSalary = topSalary;
	}
	public BigDecimal getCapConsumido() {
		return capConsumido;
	}
	public void setCapConsumido(BigDecimal capConsumido) {
		this.capConsumido = capConsumido;
	}

}
