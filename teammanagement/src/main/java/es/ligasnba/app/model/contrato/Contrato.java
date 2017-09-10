/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package es.ligasnba.app.model.contrato;

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
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import es.ligasnba.app.model.equipo.Equipo;
import es.ligasnba.app.model.jugador.Jugador;
import es.ligasnba.app.model.lineacontrato.LineaContrato;
import es.ligasnba.app.model.traspaso.Traspaso;


/**
 *
 * @author sir
 */


@Entity
@Table(name="contrato")
public class Contrato {
	
	private long idContrato;
	private Equipo equipo;
	private Jugador jugador;
	private Jugador jugadorOfrecido;
	private boolean useMidLevelException;
	private boolean firmado;
	private List<LineaContrato> listLineasContrato = new ArrayList<LineaContrato>();
	private Date fecha;
	private Traspaso traspaso;
	private boolean pendiente;
	
	public Contrato(){
		
	}
	
	public Contrato( Equipo e, Jugador j){
	    equipo=e;
	    jugador=j;    	    
	}
	@Column(name="idContrato")
	@SequenceGenerator(             
	     name="ContratoIdGenerator",
	     sequenceName="ContratoSeq")
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO,
	                generator="ContratoIdGenerator")
	public long getIdContrato() {
	    return this.idContrato;
	}
	
	public void setIdContrato(long idContrato) {
	    this.idContrato = idContrato;
	}
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="idEquipo")
    public Equipo getEquipo() {
        return equipo;
    }

    public void setEquipo(Equipo equipo) {
        this.equipo = equipo;
    }

    @OneToOne
    @JoinColumn(name="idJugador")	
    public Jugador getJugador() {
        return this.jugador;
    }
    
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="idJugadorOfrecido")	
    public Jugador getJugadorOfrecido() {
		return jugadorOfrecido;
	}
    
    public void setJugadorOfrecido(Jugador jugadorOfrecido) {
		this.jugadorOfrecido = jugadorOfrecido;
	}
    
    @JoinColumn(name="fecha")
    public Date getFecha() {
		return fecha;
	}
    
    @OneToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "idJugador")
    public void setJugador(Jugador jugador) {
        this.jugador = jugador;
    }

	public void setFirmado(boolean firmado) {
		this.firmado = firmado;
		this.fecha = new Date();
	}

	@Column(name="firmado")
	public boolean isFirmado() {
		return firmado;
	}
	
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "idContrato")
	public List<LineaContrato> getListLineasContrato() {
		return listLineasContrato;
	}
	public void setListLineasContrato(List<LineaContrato> listLineasContrato) {
		this.listLineasContrato = listLineasContrato;
	}
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}
	
	public void addLineaContrato(LineaContrato lc){
		this.listLineasContrato.add(lc);
	}
	public void removeLineaContrato(LineaContrato lc){
		this.listLineasContrato.remove(lc);
	}
	@Column(name="useMidLevelException")
	public boolean isUseMidLevelException() {
		return useMidLevelException;
	}

	public void setUseMidLevelException(boolean useMidLevelException) {
		this.useMidLevelException = useMidLevelException;
	}

	@OneToOne(cascade = CascadeType.REMOVE ,fetch=FetchType.LAZY)
    @JoinColumn(name = "idTraspaso")
	public Traspaso getTraspaso() {
		return traspaso;
	}

	public void setTraspaso(Traspaso traspaso) {
		this.traspaso = traspaso;
	}
	@Column(name="pendiente")
	public boolean isPendiente() {
		return pendiente;
	}
	
	public void setPendiente(boolean pendiente) {
		this.pendiente = pendiente;
	}
	


}
