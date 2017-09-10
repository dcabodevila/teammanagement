/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package es.ligasnba.app.model.actapartido;

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
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.codehaus.jackson.annotate.JsonIgnore;

import es.ligasnba.app.model.equipo.Equipo;
import es.ligasnba.app.model.estadistica.Estadistica;
import es.ligasnba.app.model.partido.Partido;
import es.ligasnba.app.model.usuario.Usuario;



/**
 *
 * @author sir
 */


@Entity
@Table(name="actapartido")
public class ActaPartido {
	
	private long idActaPartido;
	private Partido partido;
	private List<Estadistica> listaEstadisticas = new ArrayList<Estadistica>();
	private Boolean victoria;
	private String comentario;
	private Short valoracion;
	private Equipo equipoValorador;
	private Equipo equipoValorado;
	private Usuario usuarioValorador;
	private Usuario usuarioValorado;

	public ActaPartido(){}
	
	@Column(name="idActaPartido")
	@SequenceGenerator(             
	     name="ActaPartidoIdGenerator",
	     sequenceName="ActaPartidoSeq")
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO,
	                generator="ActaPartidoIdGenerator")
	public long getIdActaPartido() {
	    return this.idActaPartido;
	}
	
	public void setIdActaPartido(long idActaPartido) {
	    this.idActaPartido = idActaPartido;
	}
	
	@ManyToOne
	@JoinColumn(name="idPartido")
	@JsonIgnore
    public Partido getPartido() {
        return partido;
    }
	
    public void setPartido(Partido partido) {
        this.partido = partido;
    }

	@OneToMany(cascade = {CascadeType.ALL})
	@JoinColumn(name="idActaPartido")
	@JsonIgnore
    public List<Estadistica> getListaEstadisticas() {
		return listaEstadisticas;
	}
    public void setListaEstadisticas(List<Estadistica> listaEstadisticas) {
		this.listaEstadisticas = listaEstadisticas;
	}
    
    public void addEstadistica(Estadistica stat){
    	if (!getListaEstadisticas().contains(stat))
    		this.listaEstadisticas.add(stat);
    }
    public void remEstadistica(Estadistica stat){ 	
    	getListaEstadisticas().remove(stat);
    }

    @Column(name="victoria")
	public Boolean getVictoria() {
		return victoria;
	}

	public void setVictoria(Boolean victoria) {
		this.victoria = victoria;
	}
	
	@Column(name="comentario")
	public String getComentario() {
		return comentario;
	}

	public void setComentario(String comentario) {
		this.comentario = comentario;
	}
	
	@Column(name="valoracion")
	public Short getValoracion() {
		return valoracion;
	}

	public void setValoracion(Short valoracion) {
		this.valoracion = valoracion;
	}
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="idUsuarioValorador")
	public Usuario getUsuarioValorador() {
		return usuarioValorador;
	}

	public void setUsuarioValorador(Usuario usuarioValorador) {
		this.usuarioValorador = usuarioValorador;
	}

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="idUsuarioValorado")
	public Usuario getUsuarioValorado() {
		return usuarioValorado;
	}

	public void setUsuarioValorado(Usuario usuarioValorado) {
		this.usuarioValorado = usuarioValorado;
	}

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="idEquipoValorador")
	public Equipo getEquipoValorador() {
		return equipoValorador;
	}

	public void setEquipoValorador(Equipo equipoValorador) {
		this.equipoValorador = equipoValorador;
	}

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="idEquipoValorado")	
	public Equipo getEquipoValorado() {
		return equipoValorado;
	}

	public void setEquipoValorado(Equipo equipoValorado) {
		this.equipoValorado = equipoValorado;
	}
    
    
    
}
