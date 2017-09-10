/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package es.ligasnba.app.model.lineacontrato;

import java.math.BigDecimal;

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

import es.ligasnba.app.model.contrato.Contrato;
import es.ligasnba.app.model.temporada.Temporada;


/**
 *
 * @author sir
 */


@Entity
@Table(name="lineacontrato")
public class LineaContrato implements Comparable<LineaContrato>{
	
	private long idLineaContrato;
	private Contrato contrato;
	private Temporada temporada;
	private BigDecimal salario;
	private boolean opcionEquipo;
	private boolean opcionJugador;
	
	
	public LineaContrato() {	
	}	
	public LineaContrato( Contrato c, Temporada t){
		contrato = c;
	    temporada = t;
	}
	@Column(name="idLineaContrato")
	@SequenceGenerator(             
	     name="LineaContratoIdGenerator",
	     sequenceName="LineaContratoSeq")
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO,
	                generator="LineaContratoIdGenerator")
	public long getIdLineaContrato() {
	    return this.idLineaContrato;
	}
	
	public void setIdLineaContrato(long idLineaContrato) {
	    this.idLineaContrato = idLineaContrato;
	}
	@JsonIgnore
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="idContrato")
	public Contrato getContrato() {
		return contrato;
	}
	public void setContrato(Contrato contrato) {
		this.contrato = contrato;
	}
	@JsonIgnore
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="idTemporada")
	public Temporada getTemporada() {
		return temporada;
	}
	public void setTemporada(Temporada temporada) {
		this.temporada = temporada;
	}
	
	@Column(name="salario")
	public BigDecimal getSalario() {
		return salario;
	}
	public void setSalario(BigDecimal salario) {
		this.salario = salario;
	}
	@Column(name="opcionEquipo")
	public boolean isOpcionEquipo() {
		return opcionEquipo;
	}
	public void setOpcionEquipo(boolean opcionEquipo) {
		this.opcionEquipo = opcionEquipo;
	}
	@Column(name="opcionJugador")
	public boolean isOpcionJugador() {
		return opcionJugador;
	}
	public void setOpcionJugador(boolean opcionJugador) {
		this.opcionJugador = opcionJugador;
	}
	@Override
	public int compareTo(LineaContrato o) {
		if (this.getTemporada().getIdTemporada()>o.getTemporada().getIdTemporada()){
			return 1;
		}
		return 0;
	}



}
