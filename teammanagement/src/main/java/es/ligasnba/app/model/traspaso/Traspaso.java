package es.ligasnba.app.model.traspaso;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
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
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import es.ligasnba.app.model.contrato.Contrato;
import es.ligasnba.app.model.equipo.Equipo;
import es.ligasnba.app.model.jugador.Jugador;

@Entity
@Table(name="traspaso")
public class Traspaso {
    private long idTraspaso;
    private Equipo equipoOrigen;
    private Equipo equipoDestino;
    private List<Jugador> listaJugadoresOfrecidos = new ArrayList<Jugador>();
    private List<Jugador> listaJugadoresRecibidos = new ArrayList<Jugador>();    
    private String comentario;
    private BigDecimal importe;
    private boolean aprobado;
    private Date fecha;
    private Contrato contrato;
    
    
    public Traspaso(){    	
    }
    
    public Traspaso(Equipo equipoOr,Equipo equipoDest, String com, BigDecimal imp) {
    	
    	equipoOrigen=equipoOr;
    	equipoDestino=equipoDest;
    	comentario = com;
    	importe = imp;
    	fecha = new Date();
    }
    public Traspaso(Equipo equipoOr,Equipo equipoDest, String com) {
    	
    	equipoOrigen=equipoOr;
    	equipoDestino=equipoDest;
    	comentario = com;
    	importe = new BigDecimal(0);
    	fecha = new Date();
    }
    @Column(name="idTraspaso")
    @SequenceGenerator(             
         name="TraspasoIdGenerator",
         sequenceName="TraspasoSeq")
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO,
                    generator="TraspasoIdGenerator")
    public long getIdTraspaso() {
        return idTraspaso;
    }
	public void setIdTraspaso(long idTraspaso) {
        this.idTraspaso = idTraspaso;
    }

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="idEquipoOrigen")
	public Equipo getEquipoOrigen() {
		return equipoOrigen;
	}
	public void setEquipoOrigen(Equipo equipoOrigen) {
		this.equipoOrigen = equipoOrigen;
	}
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="idEquipoDestino")	
	public Equipo getEquipoDestino() {
		return equipoDestino;
	}
	public void setEquipoDestino(Equipo equipoDestino) {
		this.equipoDestino = equipoDestino;
	}
    

    
    @ManyToMany(
    		targetEntity=es.ligasnba.app.model.jugador.Jugador.class,
    		cascade={CascadeType.ALL}
    )
	@JoinTable(
			name="jugadores_ofrecidos",
			joinColumns=@JoinColumn(name="idTraspaso"),
			inverseJoinColumns=@JoinColumn(name="idJugador")
	)
	public List<Jugador> getListaJugadoresOfrecidos() {
		return listaJugadoresOfrecidos;
	}
	public void setListaJugadoresOfrecidos(List<Jugador> listaJugadoresOfrecidos) {
		this.listaJugadoresOfrecidos = listaJugadoresOfrecidos;
	}	
	
    @ManyToMany(
    		targetEntity=es.ligasnba.app.model.jugador.Jugador.class,
    		cascade={CascadeType.ALL}
    )
	@JoinTable(
			name="jugadores_recibidos",
			joinColumns=@JoinColumn(name="idTraspaso"),
			inverseJoinColumns=@JoinColumn(name="idJugador")
	)
	public List<Jugador> getListaJugadoresRecibidos() {
		return listaJugadoresRecibidos;
	}
	public void setListaJugadoresRecibidos(List<Jugador> listaJugadoresRecibidos) {
		this.listaJugadoresRecibidos = listaJugadoresRecibidos;
	}

	
    @Column(name="importe")
    public BigDecimal getImporte() {
		return this.importe;
	}    
    public void setImporte(BigDecimal importe) {
		this.importe = importe;
	}
    
    @Column(name="comentario")
    public String getComentario() {
		return comentario;
	}
    public void setComentario(String comentario) {
		this.comentario = comentario;
	}
    
    @Column(name="aprobado")
    public boolean isAprobado() {
		return aprobado;
	}
    public void setAprobado(boolean aprobado) {
		this.aprobado = aprobado;
	}
    @Column(name="fecha")
    public Date getFecha() {
		return fecha;
	}
    public void setFecha(Date fecha) {
		this.fecha = fecha;
	}
    
    public boolean equals(Object o){
    	Traspaso jugador = (Traspaso)o;
    	return (this.idTraspaso == jugador.idTraspaso);
    }
    
    @OneToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "idContrato")
	public Contrato getContrato() {
		return contrato;
	}

	public void setContrato(Contrato contrato) {
		this.contrato = contrato;
	}

}
