/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package es.ligasnba.app.model.temporada;

import java.util.ArrayList;
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



import es.ligasnba.app.model.clasificacion.Clasificacion;
import es.ligasnba.app.model.competicion.Competicion;


import es.ligasnba.app.model.lineacontrato.LineaContrato;
import es.ligasnba.app.model.partido.Partido;


/**
 *
 * @author sir
 */
@Entity
@Table(name = "temporada")
public class Temporada implements Comparable<Temporada>{
    private long idTemporada;
    private String nombre;
    private Competicion competicion;
    private Short orden;
    private List<LineaContrato> listaLineasContrato = new ArrayList<LineaContrato>();
    private List<Partido> listaPartidos = new ArrayList<Partido>();
    private List<Clasificacion> listaClasificaciones = new ArrayList<Clasificacion>();    
    

    public Temporada(){};
    public Temporada(String n, Competicion com, Short orden) {
        nombre = n;
        competicion = com;
        this.orden = orden;
    }
    
    @Column(name="idTemporada")
    @SequenceGenerator(             
         name="TemporadaIdGenerator",
         sequenceName="TemporadaSeq")
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO,
                    generator="TemporadaIdGenerator")
    public long getIdTemporada() {
        return idTemporada;
    }

    public void setIdTemporada(long idTemporada) {
        this.idTemporada = idTemporada;
    }
    
    @Column(name="nombre")
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    @OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name="idTemporada")
    public List<Partido> getListaPartidos() {
		return listaPartidos;
	}
    public void setListaPartidos(List<Partido> listaPartidos) {
		this.listaPartidos = listaPartidos;
	}
    
    public void addPartido(Partido p){
    	this.listaPartidos.add(p);
    	p.setTemporada(this);
    }
    
    public void remPartido(Partido p){
    	this.listaPartidos.remove(p);
    	p.setTemporada(null);
    }
    
    @OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name="idTemporada")
    public List<LineaContrato> getListaLineasContrato() {
		return listaLineasContrato;
	}
    public void setListaLineasContrato(List<LineaContrato> listaLineasContrato) {
		this.listaLineasContrato = listaLineasContrato;
	}    
    
    
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="idCompeticion")
    public Competicion getCompeticion() {
        return competicion;
    }

    public void setCompeticion(Competicion competicion) {
        this.competicion = competicion;
    }

	@OneToMany(cascade = {CascadeType.PERSIST})
	@JoinColumn(name="idTemporada")
    public List<Clasificacion> getListaClasificaciones() {
		return listaClasificaciones;
	}
	
	public void setListaClasificaciones(List<Clasificacion> listaClasificaciones) {
		this.listaClasificaciones = listaClasificaciones;
	}
    
	public boolean addClasificacion(Clasificacion c){

		return this.listaClasificaciones.add(c);
		
	}

	public boolean remClasificacion(Clasificacion c){

		return this.listaClasificaciones.remove(c);
		
	}
	@Override
	public int compareTo(Temporada t) {
		return Long.compare(this.idTemporada, t.getIdTemporada());
	}
	
	@Column(name="orden")
	public Short getOrden() {
		return orden;
	}
	public void setOrden(Short orden) {
		this.orden = orden;
	}

}

