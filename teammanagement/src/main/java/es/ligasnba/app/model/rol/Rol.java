package es.ligasnba.app.model.rol;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.JoinColumn;

import es.ligasnba.app.model.usuario.Usuario;
 
@Entity(name="rol")
public class Rol {

	private Long idRol;
	private Usuario usuario;
	private Integer rol;
	
	public Rol() {
	}
	
	public Rol(Usuario u, int rol) {
		this.usuario = u;
		this.rol = rol;
	}
    @Column(name="idRol")
    @SequenceGenerator(         
         name="RolIdGenerator", 
         sequenceName="RolSeq") 
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO,
                    generator="RolIdGenerator")	
	public Long getIdRol() {
		return idRol;
	}
	public Integer getRol() {
		return rol;
	}
	@OneToOne
	@JoinColumn(name="idUsuario")
	public Usuario getUsuario() {
		return usuario;
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
