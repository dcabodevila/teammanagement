
package es.ligasnba.app.model.clasificacion;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.ManyToOne;
import javax.persistence.JoinColumn;
import javax.persistence.Table;

import org.codehaus.jackson.annotate.JsonIgnore;


import es.ligasnba.app.model.temporada.Temporada;
import es.ligasnba.app.model.equipo.Equipo;


/**
 *
 * @author sir
 */
@Entity
@Table(name = "clasificacion")
public class Clasificacion {
    private long idClasificacion;    
    private Equipo equipo;
    private Temporada temporada;
    private int victorias;
    private int empates;
    private int derrotas;
    
    public Clasificacion(){}
    public Clasificacion(Equipo e, Temporada t){
    	this.equipo = e;
    	this.temporada = t;
    	this.victorias = 0;
    	this.empates = 0;
    	this.derrotas = 0;
    	
    }

    @Column(name="idClasificacion")
    @SequenceGenerator(             
         name="ClasificacionIdGenerator",
         sequenceName="ClasificacionSeq")
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO,
                    generator="ClasificacionIdGenerator")
    public long getidClasificacion(){
        return idClasificacion;
    }
    public void setidClasificacion (long id){
        this.idClasificacion=id;
    }
    
    
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="idEquipo")
    public Equipo getEquipo() {
		return equipo;
	}
    public void setEquipo(Equipo equipo) {
		this.equipo = equipo;
	}
    
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="idTemporada")
    public Temporada getTemporada() {
		return temporada;
	}
    public void setTemporada(Temporada temporada) {
		this.temporada = temporada;
	}

    @Column(name="victorias")
	public int getVictorias() {
		return victorias;
	}
	public void setVictorias(int victorias) {
		this.victorias = victorias;
	}
	@Column(name="empates")
	public int getEmpates() {
		return empates;
	}
	public void setEmpates(int empates) {
		this.empates = empates;
	}
	@Column(name="derrotas")
	public int getDerrotas() {
		return derrotas;
	}
	public void setDerrotas(int derrotas) {
		this.derrotas = derrotas;
	}

	public void addVictoria() {
		this.victorias += 1;
	}
	
	public void remVictoria() {
		this.victorias -= 1;
	}	
	
	public void addEmpate(){
		this.empates += 1;
	}
	
	public void remEmpate(){
		this.empates -= 1;
	}
	public void addDerrota(){
		this.derrotas += 1;	
	}
	public void delDerrota(){
		this.derrotas -= 1;
	}
	
	
	
	
}
