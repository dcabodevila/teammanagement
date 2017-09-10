package es.ligasnba.app.model.jugador;

import java.math.BigDecimal;
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
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.codehaus.jackson.annotate.JsonIgnore;

import es.ligasnba.app.model.competicion.Competicion;
import es.ligasnba.app.model.contrato.Contrato;
import es.ligasnba.app.model.equipo.Equipo;
import es.ligasnba.app.model.estadistica.Estadistica;
import es.ligasnba.app.model.jugadordefault.JugadorDefault;
import es.ligasnba.app.model.traspaso.Traspaso;
import es.ligasnba.app.util.constants.Constants;


@Entity
@Table(name="jugador")
public class Jugador {
    private long idJugador;
    private String nombre;
    private Equipo equipo;
    private Competicion competicion;
    private Contrato contrato;
    private List<Contrato> listaOfertasContrato = new ArrayList<Contrato>();
    private String imagen;
    private Integer minutos;
    private int idEquipoOriginal;
    private Integer posicion1;
    private Integer posicion2;
    private Integer media;
    private Date fechaNacimiento;
    private int moneyInterest;
    private int winningInterest;
    private int loyaltyInterest;
    private List<Estadistica> listaEstadisticas = new ArrayList<Estadistica>();    
    private List<Traspaso> listaTraspasosOfrecido = new ArrayList<Traspaso>();
    private List<Traspaso> listaTraspasosRecibido = new ArrayList<Traspaso>();
    private JugadorDefault jugadorDefault;
    private int yearsPro;
    private BigDecimal cache;
    private boolean retirado;
    
    public Jugador(){    	
    }
    
    public Jugador(String n, int m,int p1,int p2, Date age) {
    	this.nombre=n;
        this.media = m; 
        this.posicion1=p1;
        this.posicion2=p2;
        this.imagen="";
        this.minutos=0;
        this.fechaNacimiento = age;
        this.moneyInterest = Constants.cDefaultMoneyInterest;
        this.winningInterest = Constants.cDefaultWinningInterest;
    }
    
    public Jugador(String n, int m,int p1,int p2, Date age, String img, int idEquipoOriginal) {
    	this.nombre=n;
        this.media = m; 
        this.posicion1=p1;
        this.posicion2=p2;
        this.imagen="";
        this.minutos=0;
        this.fechaNacimiento = age;
        this.moneyInterest = Constants.cDefaultMoneyInterest;
        this.winningInterest = Constants.cDefaultWinningInterest;
        this.imagen = img;        
        this.idEquipoOriginal = idEquipoOriginal;
    }

    @Column(name="idJugador")
    @SequenceGenerator(             
         name="JugadorIdGenerator",
         sequenceName="JugadorSeq")
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO,
                    generator="JugadorIdGenerator")
    public long getIdJugador() {
        return idJugador;
    }
    
    @Column(name="nombreJugador")
    public String getNombre() {
        return nombre;
    }
    
    @Column(name="pos1")
    public Integer getPosicion1() {
		return posicion1;
	}
    
    public void setPosicion1(Integer posicion) {
		this.posicion1 = posicion;
	}
    
    @Column(name="pos2")
    public Integer getPosicion2() {
		return posicion2;
	}
    
    public void setPosicion2(Integer posicion2) {
		this.posicion2 = posicion2;
	}
    @Column(name="minutos")
    public Integer getMinutos() {
		return minutos;
	}
    public void setMinutos(Integer minutos) {
		this.minutos = minutos;
	}
    
    @Column(name="media")
    public Integer getMedia() {
        return media;
    }
    @Column(name="fechaNacimiento")
    public Date getFechaNacimiento() {
		return fechaNacimiento;
	}
    public void setFechaNacimiento(Date fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}    
    @Column(name="moneyInterest")
	public int getMoneyInterest() {
		return moneyInterest;
	}
    @Column(name="winningInterest")
	public int getWinningInterest() {
		return winningInterest;
	}

    
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="idEquipo")
	@JsonIgnore
	public Equipo getEquipo() {
		return this.equipo;
	}
	
	public void setEquipo(Equipo e) {
		this.equipo = e;
	}
	
	@ManyToMany(
			cascade = {CascadeType.PERSIST, CascadeType.MERGE},
			mappedBy = "listaJugadoresOfrecidos",
			targetEntity = Traspaso.class
			)
	@JsonIgnore

	public List<Traspaso> getListaTraspasosOfrecido() {
		return listaTraspasosOfrecido;
	}
	public void setListaTraspasosOfrecido(List<Traspaso> listaTraspasosOfrecido) {
		this.listaTraspasosOfrecido = listaTraspasosOfrecido;
	}
	@ManyToMany(
			cascade = {CascadeType.PERSIST, CascadeType.MERGE},
			mappedBy = "listaJugadoresRecibidos",
			targetEntity = Traspaso.class
			)
	@JsonIgnore

	public List<Traspaso> getListaTraspasosRecibido() {
		return listaTraspasosRecibido;
	}
	public void setListaTraspasosRecibido(List<Traspaso> listaTraspasosRecibido) {
		this.listaTraspasosRecibido = listaTraspasosRecibido;
	}

    public void setIdJugador(long idJugador) {
        this.idJugador = idJugador;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public void setMedia(Integer Media) {
        this.media = Media;
    }

    @OneToMany(cascade = CascadeType.PERSIST)
	@JoinColumn(name="idJugador")
	@JsonIgnore
    public List<Estadistica> getListaEstadisticas() {
		return listaEstadisticas;
	}
    public void setListaEstadisticas(List<Estadistica> listaEstadisticas) {
		this.listaEstadisticas = listaEstadisticas;
	}
    
    @OneToMany(cascade = CascadeType.PERSIST)
	@JoinColumn(name="idJugadorOfrecido")
	@JsonIgnore
	public List<Contrato> getListaOfertasContrato() {
		return listaOfertasContrato;
	}
    
    public void setListaOfertasContrato(List<Contrato> listaOfertasContrato) {
		this.listaOfertasContrato = listaOfertasContrato;
	}
    
    public void addOfertaContrato(Contrato c){
    	this.getListaOfertasContrato().add(c);
    	
    }

    public void remOfertaContrato(Contrato c){
    	this.getListaOfertasContrato().remove(c);
    }
    
    @ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="idCompeticion")
	@JsonIgnore

    public Competicion getCompeticion() {
		return competicion;
	}
    public void setCompeticion(Competicion competicion) {
		this.competicion = competicion;
	}
    

    @OneToOne(cascade = CascadeType.REMOVE ,fetch=FetchType.LAZY)
    @JoinColumn(name = "idContrato")
   	@JsonIgnore
    public Contrato getContrato(){
        return this.contrato;
    }
    
    public void setContrato(Contrato c){
        this.contrato=c;
    }
    @JoinColumn(name="imagen")
    public String getImagen() {
		return imagen;
	}
    public void setImagen(String imagen) {
		this.imagen = imagen;
	}
    
    public void setMoneyInterest(int moneyInterest) {
		this.moneyInterest = moneyInterest;
	}
    public void setWinningInterest(int winningInterest) {
		this.winningInterest = winningInterest;
	}
    
    public boolean equals(Object o){
    	Jugador jugador = (Jugador)o;
    	return (this.idJugador== jugador.idJugador);
    }
    @Column(name="idEquipoOriginal")
	public int getIdEquipoOriginal() {
		return idEquipoOriginal;
	}

	public void setIdEquipoOriginal(int idEquipoOriginal) {
		this.idEquipoOriginal = idEquipoOriginal;
	}
	@Column(name="loyaltyInterest")
	public int getLoyaltyInterest() {
		return loyaltyInterest;
	}

	public void setLoyaltyInterest(int loyaltyInterest) {
		this.loyaltyInterest = loyaltyInterest;
	}

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="idJugadorOriginal")
	@JsonIgnore
	public JugadorDefault getJugadorDefault() {
		return jugadorDefault;
	}
	public void setJugadorDefault(JugadorDefault jugadorDefault) {
		this.jugadorDefault = jugadorDefault;
	}
	@Column(name="yearsPro")
	public int getYearsPro() {
		return yearsPro;
	}

	public void setYearsPro(int yearsPro) {
		this.yearsPro = yearsPro;
	}
	@Column(name="cache")
	public BigDecimal getCache() {
		return cache;
	}

	public void setCache(BigDecimal cache) {
		this.cache = cache;
	}
	@Column(name="retirado")
	public boolean isRetirado() {
		return retirado;
	}

	public void setRetirado(boolean retirado) {
		this.retirado = retirado;
	}






}
