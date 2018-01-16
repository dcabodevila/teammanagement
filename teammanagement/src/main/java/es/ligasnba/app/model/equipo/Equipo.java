
package es.ligasnba.app.model.equipo;

import org.codehaus.jackson.annotate.JsonIgnore;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.ManyToOne;
import javax.persistence.JoinColumn;
import javax.persistence.Table;
import javax.persistence.Version;
import es.ligasnba.app.model.partido.Partido;
import es.ligasnba.app.model.traspaso.Traspaso;
import es.ligasnba.app.model.noticia.Noticia;
import es.ligasnba.app.model.usuario.Usuario;
import es.ligasnba.app.model.competicion.Competicion;
import es.ligasnba.app.model.contrato.Contrato;
import es.ligasnba.app.model.jugador.Jugador;
import es.ligasnba.app.model.arquetipoEquipo.ArquetipoEquipo;
import es.ligasnba.app.model.clasificacion.Clasificacion;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author sir
 */
@Entity
@Table(name = "equipo")
public class Equipo {
    private long idEquipo;
    private String nombre;
    private Usuario usuario;
    private String logo;
    private List<Jugador> listaJugadores=new ArrayList<Jugador>();
    private List<Contrato> listaContratos=new ArrayList<Contrato>();
    private List<Partido> listaPartidosLocal=new ArrayList<Partido>();
    private List<Partido> listaPartidosVisitante=new ArrayList<Partido>();
    private List<Traspaso> listaTraspasosOfrecidos=new ArrayList<Traspaso>();
    private List<Traspaso> listaTraspasosRecibidos=new ArrayList<Traspaso>();
    private List<Clasificacion> listaClasificaciones = new ArrayList<Clasificacion>();     
    private List<Noticia> listaNoticias = new ArrayList<Noticia>();
    private Date fechaUltimoContrato;    
    private Competicion competicion;
    private BigDecimal presupuestoActual;
    private BigDecimal presupuestoProximaTemporada;
    private short conference;
    private short division;
    private int idEquipoOriginal;
    private boolean midLevelExceptionUsed = false;
    private ArquetipoEquipo arquetipoElegido;


private long version;
    public Equipo(){}
    public Equipo(String n){
        nombre=n;
        logo="";        
    }
    
    @Column(name="idEquipo")
    @SequenceGenerator(             
         name="EquipoIdGenerator",
         sequenceName="EquipoSeq")
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO,
                    generator="EquipoIdGenerator")
    public long getIdEquipo(){
        return idEquipo;
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
    
    public void addJugador(Jugador j){
    	this.getListaJugadores().add(j);
    	j.setEquipo(this);
    }
    public void remJugador(Jugador j){
    	this.getListaJugadores().remove(j);
    	j.setEquipo(null);
    	j.setMinutos(0);
    }
    public void addContrato(Contrato c){
        getListaContratos().add(c);
        c.setEquipo(this);
    }
    public void remContrato(Contrato c){
        getListaContratos().remove(c);
        c.setEquipo(null);
    }
    
    public void setidEquipo (long id){
        this.idEquipo=id;
    }
    
    @Column(name="nombreEquipo")
    public String getnombre(){
        return nombre;
    }
    public void setnombre(String nombre){
        this.nombre=nombre;
    }

    @ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="idUsuario")
	@JsonIgnore
    public Usuario getUsuario(){
        return usuario;
    }   
    public void setUsuario(Usuario u) {
        this.usuario=u;        
    }
    
	@OneToMany(cascade=CascadeType.ALL)
	@JoinColumn(name="idEquipo")
	@JsonIgnore
	public List<Jugador> getListaJugadores() {
		return this.listaJugadores;
	}
	public void setListaJugadores(List<Jugador> l) {
		this.listaJugadores = l;
	}
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy="equipo")
	@JsonIgnore
	public List<Contrato> getListaContratos() {
		return this.listaContratos;
	}
	public void setListaContratos(List<Contrato> listaContratos) {
		this.listaContratos = listaContratos;
	}
	
	@OneToMany(mappedBy="equipoLocal")
	@JsonIgnore
	public List<Partido> getListaPartidosLocal() {
		return this.listaPartidosLocal;
	}
	public void setListaPartidosLocal(List<Partido> listaPartidosLocal) {
		this.listaPartidosLocal = listaPartidosLocal;
	}
	@OneToMany(mappedBy="equipoVisitante")
	@JsonIgnore
	public List<Partido> getListaPartidosVisitante() {
		return this.listaPartidosVisitante;
	}
	public void setListaPartidosVisitante(List<Partido> listaPartidosVisitante) {
		this.listaPartidosVisitante = listaPartidosVisitante;
	}

	public void addPartidoLocal(Partido p){
		if (!this.listaPartidosLocal.contains(p))		
			this.listaPartidosLocal.add(p);		
	}
	public void remPartidoLocal(Partido p){
		this.listaPartidosLocal.remove(p);
	}
	
	public void addPartidoVisitante(Partido p){
		if (!this.listaPartidosVisitante.contains(p))
			this.listaPartidosVisitante.add(p);	
	}
	
	public void remPartidoVisitante(Partido p){
		this.listaPartidosVisitante.remove(p);	
	}
	
	@OneToMany(cascade=CascadeType.ALL)
	@JoinColumn(name="idEquipoOrigen")
	@JsonIgnore
	public List<Traspaso> getListaTraspasosOfrecidos() {
		return listaTraspasosOfrecidos;
	}
	public void setListaTraspasosOfrecidos(List<Traspaso> listaTraspasosOfrecidos) {
		this.listaTraspasosOfrecidos = listaTraspasosOfrecidos;
	}

	@OneToMany(cascade=CascadeType.ALL)
	@JoinColumn(name="idEquipoDestino")
	@JsonIgnore
	public List<Traspaso> getListaTraspasosRecibidos() {
		return listaTraspasosRecibidos;
	}
	public void setListaTraspasosRecibidos(
			List<Traspaso> listaTraspasosRecibidos) {
		this.listaTraspasosRecibidos = listaTraspasosRecibidos;
	}
	
	
	@OneToMany(cascade=CascadeType.ALL)	
	@JoinColumn(name="idEquipo")
	@JsonIgnore
	public List<Clasificacion> getListaClasificaciones() {
		return listaClasificaciones;
	}
	
	public void setListaClasificaciones(List<Clasificacion> listaClasificaciones) {
		this.listaClasificaciones = listaClasificaciones;
	}
	@JoinColumn(name="logo")
	public String getLogo() {
		return logo;
	}
	public void setLogo(String logo) {
		this.logo = logo;
	}
	public boolean addClasificacion(Clasificacion cl){
		return this.listaClasificaciones.add(cl);
	}
	
	public boolean remClasificacion(Clasificacion cl){
		return this.listaClasificaciones.remove(cl);
	}


    public boolean equals(Object o){
    	Equipo e = (Equipo)o;
    	return (this.idEquipo== e.idEquipo);
    }

    @OneToMany(cascade = {CascadeType.ALL})
	@JoinColumn(name="idEquipo")   
    public List<Noticia> getListaNoticias() {
		return listaNoticias;
	}

	public void setListaNoticias(List<Noticia> listaNoticias) {
		this.listaNoticias = listaNoticias;
	}
    
	public void addNoticia(Noticia n){
		getListaNoticias().add(n);
		n.setEquipo(this);
	}
	public void remNoticia(Noticia n){
		getListaNoticias().remove(n);		
	}
	@Column(name="fechaUltimoContrato")
	public Date getFechaUltimoContrato() {
		return fechaUltimoContrato;
	}
	public void setFechaUltimoContrato(Date fechaUltimoContrato) {
		this.fechaUltimoContrato = fechaUltimoContrato;
	}
	
	@Version
	public long getVersion() {
	    return version;
	}
	public void setVersion(long version) {
		this.version = version;
	}
	@Column(name="conference")
	public short getConference() {
		return conference;
	}
	public void setConference(short conference) {
		this.conference = conference;
	}
	@Column(name="division")
	public short getDivision() {
		return division;
	}
	public void setDivision(short division) {
		this.division = division;
	}
	@Column(name="presupuestoProximaTemporada")
	public BigDecimal getPresupuestoProximaTemporada() {
		return presupuestoProximaTemporada;
	}
	public void setPresupuestoProximaTemporada(BigDecimal presupuesto) {
		this.presupuestoProximaTemporada = presupuesto;
	}
	@Column(name="idEquipoOriginal")
	public int getIdEquipoOriginal() {
		return idEquipoOriginal;
	}
	public void setIdEquipoOriginal(int idEquipoOriginal) {
		this.idEquipoOriginal = idEquipoOriginal;
	}
	@Column(name="midLevelExceptionUsed")
	public boolean isMidLevelExceptionUsed() {
		return midLevelExceptionUsed;
	}
	public void setMidLevelExceptionUsed(boolean midLevelExceptionUsed) {
		this.midLevelExceptionUsed = midLevelExceptionUsed;
	}
	@Column(name="presupuestoActual")
	public BigDecimal getPresupuestoActual() {
		return presupuestoActual;
	}
	public void setPresupuestoActual(BigDecimal presupuestoActual) {
		this.presupuestoActual = presupuestoActual;
	}
    @ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="idArquetipo")
	@JsonIgnore
	public ArquetipoEquipo getArquetipoElegido() {
		return arquetipoElegido;
	}
	public void setArquetipoElegido(ArquetipoEquipo arquetipoElegido) {
		this.arquetipoElegido = arquetipoElegido;
	}
	


}
