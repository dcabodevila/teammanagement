package es.ligasnba.app.model.arquetipoEquipo;

import java.math.BigDecimal;

public class ArquetipoEquipoDto {

	private long idArquetipo;
	private String nombre;
	private long idCompeticion;
	private String descripcion;	
	private boolean activo;
	private BigDecimal ingresoFijo;
	private BigDecimal ingresoPartidoJugadoRS;
	private BigDecimal ingresoPartidoGanadoRS;
	private BigDecimal ingresoClasificacionPO;
	private BigDecimal ingresoPartidoJugadoPO;
	private BigDecimal ingresoPartidoGanadoPO;
	private BigDecimal ingresoRondasPOGanadas;
	private short numPartidosGanadosObjetivo1;
	private short numPartidosGanadosObjetivo2;
	private BigDecimal ingresoObjetivo1;
	private BigDecimal ingresoObjetivo2;
	private short numRondasGanadasObjetivo3;
	private BigDecimal ingresoObjetivo3;
	private BigDecimal ingresoCampeon;
	
	
	public ArquetipoEquipoDto() {
	
	}
	
	public long getIdArquetipo() {
		return idArquetipo;
	}
    public void setIdArquetipo(long idArquetipo) {
		this.idArquetipo = idArquetipo;
	}
    
	
    public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public boolean isActivo() {
		return activo;
	}

	public void setActivo(boolean activo) {
		this.activo = activo;
	}
	public BigDecimal getIngresoPartidoJugadoRS() {
		return ingresoPartidoJugadoRS;
	}

	public void setIngresoPartidoJugadoRS(BigDecimal ingresoPartidoJugadoRS) {
		this.ingresoPartidoJugadoRS = ingresoPartidoJugadoRS;
	}
	public BigDecimal getIngresoPartidoGanadoRS() {
		return ingresoPartidoGanadoRS;
	}
	
	public void setIngresoPartidoGanadoRS(BigDecimal ingresoPartidoGanadoRS) {
		this.ingresoPartidoGanadoRS = ingresoPartidoGanadoRS;
	}
	public BigDecimal getIngresoClasificacionPO() {
		return ingresoClasificacionPO;
	}

	public void setIngresoClasificacionPO(BigDecimal ingresoClasificacionPO) {
		this.ingresoClasificacionPO = ingresoClasificacionPO;
	}
	public BigDecimal getIngresoPartidoJugadoPO() {
		return ingresoPartidoJugadoPO;
	}

	public void setIngresoPartidoJugadoPO(BigDecimal ingresoPartidoJugadoPO) {
		this.ingresoPartidoJugadoPO = ingresoPartidoJugadoPO;
	}
	public BigDecimal getIngresoPartidoGanadoPO() {
		return ingresoPartidoGanadoPO;
	}

	public void setIngresoPartidoGanadoPO(BigDecimal ingresoPartidoGanadoPO) {
		this.ingresoPartidoGanadoPO = ingresoPartidoGanadoPO;
	}
	public BigDecimal getIngresoRondasPOGanadas() {
		return ingresoRondasPOGanadas;
	}

	public void setIngresoRondasPOGanadas(BigDecimal ingresoRondasPOGanadas) {
		this.ingresoRondasPOGanadas = ingresoRondasPOGanadas;
	}

	public long getIdCompeticion() {
		return idCompeticion;
	}

	public void setIdCompeticion(long idCompeticion) {
		this.idCompeticion = idCompeticion;
	}

	public BigDecimal getIngresoFijo() {
		return ingresoFijo;
	}

	public void setIngresoFijo(BigDecimal ingresoFijo) {
		this.ingresoFijo = ingresoFijo;
	}

	public short getNumPartidosGanadosObjetivo1() {
		return numPartidosGanadosObjetivo1;
	}

	public void setNumPartidosGanadosObjetivo1(short numPartidosGanadosObjetivo1) {
		this.numPartidosGanadosObjetivo1 = numPartidosGanadosObjetivo1;
	}

	public short getNumPartidosGanadosObjetivo2() {
		return numPartidosGanadosObjetivo2;
	}

	public void setNumPartidosGanadosObjetivo2(short numPartidosGanadosObjetivo2) {
		this.numPartidosGanadosObjetivo2 = numPartidosGanadosObjetivo2;
	}

	public BigDecimal getIngresoObjetivo1() {
		return ingresoObjetivo1;
	}

	public void setIngresoObjetivo1(BigDecimal ingresoObjetivo1) {
		this.ingresoObjetivo1 = ingresoObjetivo1;
	}

	public BigDecimal getIngresoObjetivo2() {
		return ingresoObjetivo2;
	}

	public void setIngresoObjetivo2(BigDecimal ingresoObjetivo2) {
		this.ingresoObjetivo2 = ingresoObjetivo2;
	}

	public short getNumRondasGanadasObjetivo3() {
		return numRondasGanadasObjetivo3;
	}

	public void setNumRondasGanadasObjetivo3(short numRondasGanadasObjetivo3) {
		this.numRondasGanadasObjetivo3 = numRondasGanadasObjetivo3;
	}

	public BigDecimal getIngresoObjetivo3() {
		return ingresoObjetivo3;
	}

	public void setIngresoObjetivo3(BigDecimal ingresoObjetivo3) {
		this.ingresoObjetivo3 = ingresoObjetivo3;
	}

	public BigDecimal getIngresoCampeon() {
		return ingresoCampeon;
	}

	public void setIngresoCampeon(BigDecimal ingresoCampeon) {
		this.ingresoCampeon = ingresoCampeon;
	}
	
	
	
	

}
