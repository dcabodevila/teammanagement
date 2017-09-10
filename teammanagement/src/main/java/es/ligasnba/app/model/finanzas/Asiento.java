package es.ligasnba.app.model.finanzas;

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

import es.ligasnba.app.model.equipo.Equipo;
import es.ligasnba.app.model.temporada.Temporada;

@Entity
@Table(name = "asiento")
public class Asiento {

	private long idAsiento;
	private Equipo equipo;
	private String concepto;
	private Date fecha;
	private BigDecimal importe;
	private Temporada temporada;
	private boolean regular;
	
	public Asiento() {
	
	}
	
    @Column(name="idAsiento")
    @SequenceGenerator(             
         name="AsientoIdGenerator",
         sequenceName="AsientoSeq")
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO,
                    generator="AsientoIdGenerator")
	public long getIdAsiento() {
		return idAsiento;
	}
    public void setIdAsiento(long idAsiento) {
		this.idAsiento = idAsiento;
	}
    
    @ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="idEquipo")
	@JsonIgnore
	public Equipo getEquipo() {
		return equipo;
	}
    public void setEquipo(Equipo equipo) {
		this.equipo = equipo;
	}
	@Column(name="fecha")
	public Date getFecha() {
		return fecha;
	}
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}
	
	@Column(name="concepto")
	public String getConcepto() {
		return concepto;
	}
	public void setConcepto(String concepto) {
		this.concepto = concepto;
	}

	@Column(name="importe")
	public BigDecimal getImporte() {
		return importe;
	}

	public void setImporte(BigDecimal importe) {
		this.importe = importe;
	}
    @ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="idTemporada")
	@JsonIgnore
	public Temporada getTemporada() {
		return temporada;
	}

	public void setTemporada(Temporada temporada) {
		this.temporada = temporada;
	}

	@Column(name="regular")
	public boolean isRegular() {
		return regular;
	}

	public void setRegular(boolean regular) {
		this.regular = regular;
	}
	
}
