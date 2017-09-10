package es.ligasnba.app.model.noticia;

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

@Entity
@Table(name = "noticia")
public class Noticia {

	private long idNoticia;
	private Equipo equipo;
	private String texto;
	private Date fecha;
	
	public Noticia() {
	
	}
	public Noticia(Equipo e, String text, Date f) {
		this.equipo = e;
		this.texto = text;
		this.fecha = f;
		
	}
	
    @Column(name="idNoticia")
    @SequenceGenerator(             
         name="NoticiaIdGenerator",
         sequenceName="NoticiaSeq")
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO,
                    generator="NoticiaIdGenerator")
	public long getIdNoticia() {
		return idNoticia;
	}
    @Column(name="texto")
	public String getTexto() {
		return texto;
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
	
	
	public void setIdNoticia(long idNoticia) {
		this.idNoticia = idNoticia;
	}
	public void setTexto(String texto) {
		this.texto = texto;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}
	
}
