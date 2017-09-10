package es.ligasnba.app.model.estadistica;


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

import es.ligasnba.app.model.actapartido.ActaPartido;
import es.ligasnba.app.model.jugador.Jugador;

import es.ligasnba.app.model.temporada.Temporada;

@Entity
@Table(name="estadistica")
public class Estadistica {

	private long idEstadistica;
	private ActaPartido actaPartido;
	private Jugador jugador;
	private long idEquipoJugador;
	private int puntos;
	private int asistencias;
	private int rebotes;
	private int minutos;
	
	public Estadistica() {
	}
	
	public Estadistica(ActaPartido p, Jugador j, Temporada t, int pt, int as, int rb, int min) {
		
		this.actaPartido = p;
		this.jugador = j;
		this.idEquipoJugador = j.getEquipo().getIdEquipo();
		this.puntos = pt;
		this.asistencias = as;
		this.rebotes = rb;
		this.minutos = min;
		
	}
	
	
    @Column(name="idEstadistica")
    @SequenceGenerator(             
         name="EstadisticaIdGenerator",
         sequenceName="EstadisticaSeq")
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO,
                    generator="EstadisticaIdGenerator")
	public long getIdEstadistica() {
		return idEstadistica;
	}
	public void setIdEstadistica(long idEstadistica) {
		this.idEstadistica = idEstadistica;
	}
	
    @ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="idActaPartido")	
	public ActaPartido getActaPartido() {
		return actaPartido;
	}
	public void setActaPartido(ActaPartido actapar) {
		this.actaPartido = actapar;
	}

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="idJugador")		
	public Jugador getJugador() {
		return jugador;
	}
	public void setJugador(Jugador jugador) {
		this.jugador = jugador;
	}
	
	
	@Column(name="puntos")
	public int getPuntos() {
		return puntos;
	}
	public void setPuntos(int puntos) {
		this.puntos = puntos;
	}
	@Column(name="asistencias")
	public int getAsistencias() {
		return asistencias;
	}
	public void setAsistencias(int asistencias) {
		this.asistencias = asistencias;
	}
	@Column(name="rebotes")
	public int getRebotes() {
		return rebotes;
	}
	public void setRebotes(int rebotes) {
		this.rebotes = rebotes;
	}
	@Column(name="minutos")
	public int getMinutos() {
		return minutos;
	}
	public void setMinutos(int minutos) {
		this.minutos = minutos;
	}
	@Column(name="idEquipoJugador")
	public long getIdEquipoJugador() {
		return idEquipoJugador;
	}
	public void setIdEquipoJugador(long idEquipoJugador) {
		this.idEquipoJugador = idEquipoJugador;
	}
	
}
