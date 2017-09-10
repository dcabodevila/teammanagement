package es.ligasnba.app.model.finanzas;

import java.math.BigDecimal;
import java.util.Date;

public class AsientoDto implements Comparable<AsientoDto>{

	private long idAsiento;
	private long idEquipo;
	private String nombreEquipo;
	private String concepto;
	private Date fecha;
	private BigDecimal importe;
	private long idTemporada;
	

	
	public long getIdAsiento() {
		return idAsiento;
	}

	public Date getFecha() {
		return fecha;
	}
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}
	
	public String getConcepto() {
		return concepto;
	}
	public void setConcepto(String concepto) {
		this.concepto = concepto;
	}

	public BigDecimal getImporte() {
		return importe;
	}

	public void setImporte(BigDecimal importe) {
		this.importe = importe;
	}

	public long getIdTemporada() {
		return idTemporada;
	}


	public void setIdTemporada(long idTemporada) {
		this.idTemporada = idTemporada;
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

	@Override
	public int compareTo(AsientoDto o) {
		return this.getImporte().compareTo(o.getImporte());
	}
	
}
