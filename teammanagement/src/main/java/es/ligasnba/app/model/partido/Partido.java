/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package es.ligasnba.app.model.partido;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.codehaus.jackson.annotate.JsonIgnore;

import es.ligasnba.app.model.actapartido.ActaPartido;
import es.ligasnba.app.model.equipo.Equipo;
import es.ligasnba.app.model.temporada.Temporada;


/**
 *
 * @author sir
 */


@Entity
@Table(name="partido")
public class Partido {
	
	private long idPartido;
	private Equipo equipoLocal;
	private Equipo equipoVisitante;
	private Temporada temporada;
	private List<ActaPartido> actasPartido = new ArrayList<ActaPartido>();
	private Date fecha;
	private Boolean validado;
	private Boolean discrepancia;
	private boolean playoff;
	

	public Partido(){}
	public Partido(Equipo eLocal, Equipo eVisitante,Temporada t) {
		equipoLocal=eLocal;
		equipoVisitante=eVisitante;
		temporada = t;
	}
	

	@Column(name="idPartido")
	@SequenceGenerator(             
	     name="PartidoIdGenerator",
	     sequenceName="PartidoSeq")
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO,
	                generator="PartidoIdGenerator")
	public long getIdPartido() {
	    return this.idPartido;
	}
	
	public void setIdPartido(long idPartido) {
	    this.idPartido = idPartido;
	}
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="idEquipoLocal")
	    public Equipo getEquipoLocal() {
	        return equipoLocal;
	    }
	
	    public void setEquipoLocal(Equipo equipolocal) {
	        this.equipoLocal = equipolocal;
	    }
	    
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="idEquipoVisitante")
	    public Equipo getEquipoVisitante() {
	        return equipoVisitante;
	    }
	
	    public void setEquipoVisitante(Equipo equipoVisitante) {
	        this.equipoVisitante = equipoVisitante;
	    }
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="idTemporada")
	@JsonIgnore
	    public Temporada getTemporada() {
	        return this.temporada;
	    }
	    public void setTemporada(Temporada temporada) {
	        this.temporada = temporada;
	    }

	@OneToMany(cascade=CascadeType.REMOVE,fetch=FetchType.LAZY)
	@JoinColumn(name="idPartido")
	public List<ActaPartido> getActasPartido() {
		return actasPartido;
	}
	public void setActasPartido(List<ActaPartido> actasPartido) {
		this.actasPartido = actasPartido;
	}
	@Column(name="fecha")
	public Date getFecha() {
		return fecha;
	}
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}
	@Column(name="validado")
	public Boolean getValidado() {
		return validado;
	}
	public void setValidado(Boolean validado) {
		this.validado = validado;
	}
	@Column(name="discrepancia")
	public Boolean getDiscrepancia() {
		return discrepancia;
	}
	public void setDiscrepancia(Boolean discrepancia) {
		this.discrepancia = discrepancia;
	}
	@Column(name="playoff")
	public boolean isPlayoff() {
		return playoff;
	}
	public void setPlayoff(boolean playoff) {
		this.playoff = playoff;
	}	

	


}
