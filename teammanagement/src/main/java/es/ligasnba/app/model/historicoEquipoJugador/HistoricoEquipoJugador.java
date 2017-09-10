
package es.ligasnba.app.model.historicoEquipoJugador;

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
import es.ligasnba.app.model.jugador.Jugador;
import es.ligasnba.app.model.temporada.Temporada;

/**
 *
 * @author sir
 */
@Entity
@Table(name = "historico_equipos_jugador")
public class HistoricoEquipoJugador {
    private long idHistoricoEquipos;
    private Equipo equipo;
    private Jugador jugador;
    private Temporada temporada;
    private Date fecha;
    
    @Column(name="idHistoricoEquipos")
    @SequenceGenerator(             
         name="idHistoricoEquiposGenerator",
         sequenceName="idHistoricoEquiposSeq")
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO,
                    generator="idHistoricoEquiposGenerator")
	public long getIdHistoricoEquipos() {
		return idHistoricoEquipos;
	}
	public void setIdHistoricoEquipos(long idHistoricoEquipos) {
		this.idHistoricoEquipos = idHistoricoEquipos;
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
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="idJugador")
	@JsonIgnore
	public Jugador getJugador() {
		return jugador;
	}
	public void setJugador(Jugador jugador) {
		this.jugador = jugador;
	}
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="idTemporada")
	@JsonIgnore
	public Temporada getTemporada() {
		return temporada;
	}
	public void setTemporada(Temporada temporada) {
		this.temporada = temporada;
	}
	@Column(name="fecha")
	public Date getFecha() {
		return fecha;
	}
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}
    
    
    

}
