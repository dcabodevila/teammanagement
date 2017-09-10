/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package es.ligasnba.app.model.competicion;


/**
 *
 * @author sir
 */
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.ArrayList;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.CascadeType;


import es.ligasnba.app.model.temporada.Temporada;
import es.ligasnba.app.model.tipo_competicion.TipoCompeticion;
import es.ligasnba.app.model.tipo_estado_competicion.TipoEstadoCompeticion;
import es.ligasnba.app.model.competitionrol.CompetitionRol;
import es.ligasnba.app.model.equipo.Equipo;
import es.ligasnba.app.model.usuario.Usuario;
import es.ligasnba.app.util.constants.Constants;

@Entity
@Table(name="competicion")

public class Competicion {

private long idCompeticion;
private String nombre;
private String clave2k;
private Usuario admin;
private List<CompetitionRol> competitionrol = new ArrayList<CompetitionRol>();
private List <Usuario> listaUsuarios = new ArrayList<Usuario>();
private TipoCompeticion tipoCompeticion;
private TipoEstadoCompeticion tipoEstadoCompeticion;
private List<Equipo> listaEquiposActual= new ArrayList<Equipo>();
private List<Temporada> listaTemporadas= new ArrayList<Temporada>();
private long idTemporadaActual;
private int numVueltas;
private Date actualDate;
private Date startDate;
private Date finishDate;
private Date offSeasonStartDate;
private Date offSeasonFinishDate;
private boolean paused;
private int estado;
private BigDecimal limiteSalarial;
private BigDecimal limiteTope;
private boolean mercadoAbierto;
private Date fechaCierreMercado;
private Date fechaComienzoRS;
private Date fechaComienzoPO;
private int minPartidosSemana;
private int maxPartidosSemana;



public Competicion() {
}
	public Competicion( String n, Usuario a) {
	    nombre = n;
	    clave2k="";
	    admin=a;
	    numVueltas = 1;
	    paused=false;
	    
	}
    public Competicion( String n, String clave, Usuario a) {
        nombre = n;
        clave2k=clave;
        admin=a;
        numVueltas = 1;
        paused=false;
        
    }
    public Competicion( String n, String clave, Usuario a, int numV) {
        nombre = n;
        clave2k=clave;
        admin=a;
        numVueltas = numV;
        paused=false;
        
    }  
    public Competicion( String n, String web, Usuario a, int numV, boolean empate, int PV, int PE, int PD) {
        nombre = n;
        clave2k=web;
        admin=a;
        numVueltas = numV;
        paused=false;
        
    } 
    @Column(name="idCompeticion")
    @SequenceGenerator(             
         name="CompeticionIdGenerator",
         sequenceName="CompeticionSeq")
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO,
                    generator="CompeticionIdGenerator")
    public long getIdCompeticion() {
        return idCompeticion;
    }

    public void setIdCompeticion(long idCompeticion) {
        this.idCompeticion = idCompeticion;
    }
    @Column(name="clave2k")
    public String getClave2k() {
		return clave2k;
	}
    public void setClave2k(String clave2k) {
		this.clave2k = clave2k;
	}
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "idCompeticion")
    public List<Equipo> getListaEquipos() {
        return listaEquiposActual;
    }

    public void setListaEquipos(List<Equipo> listaEquipos) {
        this.listaEquiposActual = listaEquipos;
    }
	@OneToMany(cascade = {CascadeType.ALL})
	@JoinColumn(name="idCompeticion")
    public List<CompetitionRol> getCompetitionrol() {
		return competitionrol;
	}
    public void setCompetitionrol(List<CompetitionRol> competitionrol) {
		this.competitionrol = competitionrol;
	}
    
    @ManyToMany(
    		targetEntity=es.ligasnba.app.model.usuario.Usuario.class,
    		cascade={CascadeType.PERSIST, CascadeType.MERGE}
    )
    @JoinTable(
    		name="usuarios_liga",
    		joinColumns=@JoinColumn(name="idCompeticion"),
    		inverseJoinColumns=@JoinColumn(name="idUsuario")
    )    public List<Usuario> getListaUsuarios() {
		return listaUsuarios;
	}
    public void setListaUsuarios(List<Usuario> listaUsuarios) {
		this.listaUsuarios = listaUsuarios;
	}
    @Column(name="nombre")
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="idAdmin")
    public Usuario getAdmin() {
    	return admin;
    }
    public void setAdmin(Usuario admin) {
    	this.admin = admin;
    }
    
    
    public void addEquipo(Equipo e){
        this.getListaEquipos().add(e);
        e.setCompeticion(this);
    }
    public void remEquipo(Equipo e){
        this.getListaEquipos().remove(e);
        e.setCompeticion(null);
    }


    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "idCompeticion")
    public List<Temporada> getListaTemporadas() {
        return listaTemporadas;
    }

    public void setListaTemporadas(List<Temporada> listaTemporadas) {
        this.listaTemporadas = listaTemporadas;
    }
    
    public void addTemporada(Temporada t){
        getListaTemporadas().add(t);
        
    }
    public void remTemporada(Temporada t){
        getListaTemporadas().remove(t);
    }
    
    @Column(name="idTemporadaActual")
    public long getIdTemporadaActual() {
		return idTemporadaActual;
	}
    public void setIdTemporadaActual(long idTemporadaActual) {
		this.idTemporadaActual = idTemporadaActual;
	}
    
    public void addUsuario(Usuario u){
        getListaUsuarios().add(u);
    }
    public void remUsuario(Usuario u){
        getListaUsuarios().remove(u);
    }
    @Column(name="numVueltas")
	public int getNumVueltas() {
		return numVueltas;
	}
	public void setNumVueltas(int numVueltas) {
		this.numVueltas = numVueltas;
	}
	@Column(name="actualdate")
	public Date getActualDate() {
		return actualDate;
	}
	public void setActualDate(Date actualDate) {
		this.actualDate = actualDate;
	}
	@Column(name="paused")
	public boolean isPaused() {
		return paused;
	}
	public void setPaused(boolean paused) {
		this.paused = paused;
	}
	@Column(name="estado")
	public int getEstado() {
		return estado;
	}
	public void setEstado(int estado) {
		
		this.estado = estado;
//		//Pausamos la liga si aun no ha empezado o si ha terminado.
//		this.setPaused( ((estado==Constants.cStateNotStarted) || (estado==Constants.cStateFinished))); 
			
		
	}
	@Column(name="finishDate")
	public Date getFinishDate() {
		return finishDate;
	}
	public void setFinishDate(Date finishDate) {
		this.finishDate = finishDate;
	}
	@Column(name="startDate")
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	
	@Column(name="salaryCap")
	public BigDecimal getLimiteSalarial() {
		return limiteSalarial;
	}
	public void setLimiteSalarial(BigDecimal limiteSalarial) {
		this.limiteSalarial = limiteSalarial;
	}

	@Column(name="salaryTop")
	public BigDecimal getLimiteTope() {
		return limiteTope;
	}
	public void setLimiteTope(BigDecimal limiteTope) {
		this.limiteTope = limiteTope;
	}
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="idTipoCompeticion")
	public TipoCompeticion getTipoCompeticion() {
		return tipoCompeticion;
	}
	public void setTipoCompeticion(TipoCompeticion tipoCompeticion) {
		this.tipoCompeticion = tipoCompeticion;
	}
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="idTipoEstado")
	public TipoEstadoCompeticion getTipoEstadoCompeticion() {
		return tipoEstadoCompeticion;
	}
	public void setTipoEstadoCompeticion(TipoEstadoCompeticion tipoEstadoCompeticion) {
		this.tipoEstadoCompeticion = tipoEstadoCompeticion;
	}
	@Column(name="mercadoAbierto")
	public boolean isMercadoAbierto() {
		return mercadoAbierto;
	}
	public void setMercadoAbierto(boolean mercadoAbierto) {
		this.mercadoAbierto = mercadoAbierto;
	}
	@Column(name="fechaCierreMercado")
	public Date getFechaCierreMercado() {
		return fechaCierreMercado;
	}
	public void setFechaCierreMercado(Date fechaCierreMercado) {
		this.fechaCierreMercado = fechaCierreMercado;
	}
	
	@Column(name="fechaComienzoRS")
	public Date getFechaComienzoRS() {
		return fechaComienzoRS;
	}
	public void setFechaComienzoRS(Date fechaComienzoRS) {
		this.fechaComienzoRS = fechaComienzoRS;
	}
	
	@Column(name="fechaComienzoPO")
	public Date getFechaComienzoPO() {
		return fechaComienzoPO;
	}	
	public void setFechaComienzoPO(Date fechaComienzoPO) {
		this.fechaComienzoPO = fechaComienzoPO;
	}
	
	@Column(name="minPartidosSemana")
	public int getMinPartidosSemana() {
		return minPartidosSemana;
	}
	public void setMinPartidosSemana(int minPartidosSemana) {
		this.minPartidosSemana = minPartidosSemana;
	}

	@Column(name="maxPartidosSemana")	
	public int getMaxPartidosSemana() {
		return maxPartidosSemana;
	}
	public void setMaxPartidosSemana(int maxPartidosSemana) {
		this.maxPartidosSemana = maxPartidosSemana;
	}
	
	@Column(name="offSeasonStartDate")	
	public Date getOffSeasonStartDate() {
		return offSeasonStartDate;
	}
	public void setOffSeasonStartDate(Date offSeasonStartDate) {
		this.offSeasonStartDate = offSeasonStartDate;
	}
	
	@Column(name="offSeasonFinishDate")
	public Date getOffSeasonFinishDate() {
		return offSeasonFinishDate;
	}
	public void setOffSeasonFinishDate(Date offSeasonFinishDate) {
		this.offSeasonFinishDate = offSeasonFinishDate;
	}
	
}

