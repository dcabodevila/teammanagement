package es.ligasnba.app.model.competitionrol;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.JoinColumn;
import javax.persistence.Table;

import es.ligasnba.app.model.competicion.Competicion;
import es.ligasnba.app.model.usuario.Usuario;
 
@Entity
@Table(name="competitionrol")
public class CompetitionRol {

	private Long idRol;
	private Usuario usuario;
	private Competicion competicion;
	private Integer rol;
	
	public CompetitionRol() {
	}
	
	public CompetitionRol(Usuario u, Competicion c, int rol) {
		this.usuario = u;
		this.competicion = c;
		this.rol = rol;
	}
    @Column(name="idRol")
    @SequenceGenerator(         
         name="CompetitionRolIdGenerator", 
         sequenceName="CompetitionRolSeq") 
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO,
                    generator="CompetitionRolIdGenerator")	
	public Long getIdRol() {
		return idRol;
	}
	public Integer getRol() {
		return rol;
	}
    @ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="idUsuario")
	public Usuario getUsuario() {
		return usuario;
	}
    @ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="idCompeticion")
	public Competicion getCompeticion() {
		return competicion;
	}
	public void setCompeticion(Competicion competicion) {
		this.competicion = competicion;
	}
	
	public void setIdRol(Long idRol) {
		this.idRol = idRol;
	}
	public void setRol(Integer rol) {
		this.rol = rol;
	}
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	
	
}
