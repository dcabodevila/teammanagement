/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package es.ligasnba.app.model.valoracion;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.codehaus.jackson.annotate.JsonIgnore;

import es.ligasnba.app.model.actapartido.ActaPartido;
import es.ligasnba.app.model.equipo.Equipo;
import es.ligasnba.app.model.partido.Partido;
import es.ligasnba.app.model.temporada.Temporada;
import es.ligasnba.app.model.usuario.Usuario;


/**
 *
 * @author sir
 */


@Entity
@Table(name="valoracion")
public class Valoracion {
	
	private Long idValoracion;
	private Usuario usuario;
	private Usuario usuarioValorado;
	private Partido partido;
	private short valoracion;
	private Date fecha;
	private String comentario;

	public Valoracion(){}


	@Column(name="idValoracion")
	@SequenceGenerator(             
	     name="ValoracionIdGenerator",
	     sequenceName="ValoracionSeq")
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO,
	                generator="ValoracionIdGenerator")
	public Long getIdValoracion() {
	    return this.idValoracion;
	}
	
	public void setIdValoracion(Long idValoracion) {
	    this.idValoracion = idValoracion;
	}

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="idUsuario")
	public Usuario getUsuario() {
		return usuario;
	}
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="idUsuarioValorado")
	public Usuario getUsuarioValorado() {
		return usuarioValorado;
	}
	public void setUsuarioValorado(Usuario usuario) {
		this.usuarioValorado = usuario;
	}

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="idPartido")
	public Partido getPartido() {
		return partido;
	}
	public void setPartido(Partido partido) {
		this.partido = partido;
	}
	@Column(name="valoracion")
	public short getValoracion() {
		return valoracion;
	}
	public void setValoracion(short valoracion) {
		this.valoracion = valoracion;
	}
	@Column(name="comentario")
	public String getComentario() {
		return comentario;
	}
	public void setComentario(String comentario) {
		this.comentario = comentario;
	}

	@Column(name="fecha")
	public Date getFecha() {
		return fecha;
	}
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}	

	


}
