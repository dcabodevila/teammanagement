package es.ligasnba.app.model.arquetipoEquipo;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.codehaus.jackson.annotate.JsonIgnore;

import es.ligasnba.app.model.competicion.Competicion;
import es.ligasnba.app.model.equipo.Equipo;
import es.ligasnba.app.model.temporada.Temporada;

@Entity
@Table(name = "arquetipoEquipo")
public class ArquetipoEquipo {

	private long idArquetipo;
	private String nombre;
	private Competicion competicion;
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
	private short numRondasGanadasObjetivo3;
	private BigDecimal ingresoObjetivo1;
	private BigDecimal ingresoObjetivo2;
	private BigDecimal ingresoObjetivo3;
	private BigDecimal ingresoCampeon;
	
	
	public ArquetipoEquipo() {
	
	}
	
    @Column(name="idArquetipo")
    @SequenceGenerator(             
         name="ArquetipoIdGenerator",
         sequenceName="ArquetipoSeq")
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO,
                    generator="ArquetipoIdGenerator")
	public long getIdArquetipo() {
		return idArquetipo;
	}
    public void setIdArquetipo(long idArquetipo) {
		this.idArquetipo = idArquetipo;
	}
    
    @ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="idCompeticion")
	@JsonIgnore
	public Competicion getCompeticion() {
		return competicion;
	}
    public void setCompeticion(Competicion competicion) {
		this.competicion = competicion;
	}
	
	@Column(name="nombre")
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	@Column(name="descripcion")
	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	@Column(name="activo")
	public boolean isActivo() {
		return activo;
	}

	public void setActivo(boolean activo) {
		this.activo = activo;
	}
	@Column(name="ingresoPartidoJugadoRS")
	public BigDecimal getIngresoPartidoJugadoRS() {
		return ingresoPartidoJugadoRS;
	}

	public void setIngresoPartidoJugadoRS(BigDecimal ingresoPartidoJugadoRS) {
		this.ingresoPartidoJugadoRS = ingresoPartidoJugadoRS;
	}
	@Column(name="ingresoPartidoGanadoRS")
	public BigDecimal getIngresoPartidoGanadoRS() {
		return ingresoPartidoGanadoRS;
	}
	
	public void setIngresoPartidoGanadoRS(BigDecimal ingresoPartidoGanadoRS) {
		this.ingresoPartidoGanadoRS = ingresoPartidoGanadoRS;
	}
	@Column(name="ingresoClasificacionPO")
	public BigDecimal getIngresoClasificacionPO() {
		return ingresoClasificacionPO;
	}

	public void setIngresoClasificacionPO(BigDecimal ingresoClasificacionPO) {
		this.ingresoClasificacionPO = ingresoClasificacionPO;
	}
	@Column(name="ingresoPartidoJugadoPO")
	public BigDecimal getIngresoPartidoJugadoPO() {
		return ingresoPartidoJugadoPO;
	}

	public void setIngresoPartidoJugadoPO(BigDecimal ingresoPartidoJugadoPO) {
		this.ingresoPartidoJugadoPO = ingresoPartidoJugadoPO;
	}
	@Column(name="ingresoPartidoGanadoPO")
	public BigDecimal getIngresoPartidoGanadoPO() {
		return ingresoPartidoGanadoPO;
	}

	public void setIngresoPartidoGanadoPO(BigDecimal ingresoPartidoGanadoPO) {
		this.ingresoPartidoGanadoPO = ingresoPartidoGanadoPO;
	}
	@Column(name="ingresoRondasPOGanadas")
	public BigDecimal getIngresoRondasPOGanadas() {
		return ingresoRondasPOGanadas;
	}

	public void setIngresoRondasPOGanadas(BigDecimal ingresoRondasPOGanadas) {
		this.ingresoRondasPOGanadas = ingresoRondasPOGanadas;
	}
	@Column(name="ingresoFijo")
	public BigDecimal getIngresoFijo() {
		return ingresoFijo;
	}

	public void setIngresoFijo(BigDecimal ingresoFijo) {
		this.ingresoFijo = ingresoFijo;
	}
	@Column(name="numPartidosGanadosObjetivo1")
	public short getNumPartidosGanadosObjetivo1() {
		return numPartidosGanadosObjetivo1;
	}

	public void setNumPartidosGanadosObjetivo1(short numPartidosGanadosObjetivo1) {
		this.numPartidosGanadosObjetivo1 = numPartidosGanadosObjetivo1;
	}
	@Column(name="numPartidosGanadosObjetivo2")
	public short getNumPartidosGanadosObjetivo2() {
		return numPartidosGanadosObjetivo2;
	}

	public void setNumPartidosGanadosObjetivo2(short numPartidosGanadosObjetivo2) {
		this.numPartidosGanadosObjetivo2 = numPartidosGanadosObjetivo2;
	}
	@Column(name="numRondasGanadasObjetivo3")
	public short getNumRondasGanadasObjetivo3() {
		return numRondasGanadasObjetivo3;
	}

	public void setNumRondasGanadasObjetivo3(short numRondasGanadasObjetivo3) {
		this.numRondasGanadasObjetivo3 = numRondasGanadasObjetivo3;
	}
	@Column(name="ingresoCampeon")
	public BigDecimal getIngresoCampeon() {
		return ingresoCampeon;
	}

	public void setIngresoCampeon(BigDecimal ingresoCampeon) {
		this.ingresoCampeon = ingresoCampeon;
	}

	@Column(name="ingresoObjetivo1")
	public BigDecimal getIngresoObjetivo1() {
		return ingresoObjetivo1;
	}

	public void setIngresoObjetivo1(BigDecimal ingresoObjetivo1) {
		this.ingresoObjetivo1 = ingresoObjetivo1;
	}

	@Column(name="ingresoObjetivo2")
	public BigDecimal getIngresoObjetivo2() {
		return ingresoObjetivo2;
	}

	public void setIngresoObjetivo2(BigDecimal ingresoObjetivo2) {
		this.ingresoObjetivo2 = ingresoObjetivo2;
	}
	
	@Column(name="ingresoObjetivo3")
	public BigDecimal getIngresoObjetivo3() {
		return ingresoObjetivo3;
	}

	public void setIngresoObjetivo3(BigDecimal ingresoObjetivo3) {
		this.ingresoObjetivo3 = ingresoObjetivo3;
	}
	
	
	

}
