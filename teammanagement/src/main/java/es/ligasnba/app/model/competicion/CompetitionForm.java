package es.ligasnba.app.model.competicion;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import es.ligasnba.app.model.equipo.Equipo;
import es.ligasnba.app.model.noticia.Noticia;
import es.ligasnba.app.model.partido.Partido;

public class CompetitionForm {
	private Long idCompeticion;
	private String nombreCompeticion;
	private Short estadoCompeticion;
	private Long idEquipo;
	private String nombreEquipo;
	private String logo;
	private Date actualDate;
	private String descripcionEstadoCompeticion;
	private String temporada;
	private BigDecimal presupuestoActual;
	private BigDecimal presupuestoSiguiente;
	private String balance;
	

	public List<Partido> listaUltimosPartidos= new ArrayList<Partido>();
	public List<Noticia> listaNoticias = new ArrayList<Noticia>();
	
	public CompetitionForm() {		
	}
	
	public Long getIdCompeticion() {
		return idCompeticion;
	}

	public void setIdCompeticion(Long idCompeticion) {
		this.idCompeticion = idCompeticion;
	}

	public String getNombreCompeticion() {
		return nombreCompeticion;
	}

	public void setNombreCompeticion(String nombreCompeticion) {
		this.nombreCompeticion = nombreCompeticion;
	}

	public Short getEstadoCompeticion() {
		return estadoCompeticion;
	}

	public void setEstadoCompeticion(Short estadoCompeticion) {
		this.estadoCompeticion = estadoCompeticion;
	}

	public Long getIdEquipo() {
		return idEquipo;
	}

	public void setIdEquipo(Long idEquipo) {
		this.idEquipo = idEquipo;
	}

	public String getNombreEquipo() {
		return nombreEquipo;
	}

	public void setNombreEquipo(String nombreEquipo) {
		this.nombreEquipo = nombreEquipo;
	}

	public String getLogo() {
		return logo;
	}

	public void setLogo(String logo) {
		this.logo = logo;
	}

	public Date getActualDate() {
		return actualDate;
	}

	public void setActualDate(Date actualDate) {
		this.actualDate = actualDate;
	}

	public String getDescripcionEstadoCompeticion() {
		return descripcionEstadoCompeticion;
	}

	public void setDescripcionEstadoCompeticion(String descripcionEstadoCompeticion) {
		this.descripcionEstadoCompeticion = descripcionEstadoCompeticion;
	}

	public String getTemporada() {
		return temporada;
	}

	public void setTemporada(String temporada) {
		this.temporada = temporada;
	}

	public List<Partido> getListaUltimosPartidos() {
		return listaUltimosPartidos;
	}
	public void setListaUltimosPartidos(List<Partido> listaUltimosPartidos) {
		this.listaUltimosPartidos = listaUltimosPartidos;
	}
	
	public List<Noticia> getListaNoticias() {
		return listaNoticias;
	}

	public void setListaNoticias(List<Noticia> listaNoticias) {
		this.listaNoticias = listaNoticias;
	}

	public BigDecimal getPresupuestoActual() {
		return presupuestoActual;
	}

	public void setPresupuestoActual(BigDecimal presupuestoActual) {
		this.presupuestoActual = presupuestoActual;
	}

	public BigDecimal getPresupuestoSiguiente() {
		return presupuestoSiguiente;
	}

	public void setPresupuestoSiguiente(BigDecimal presupuestoSiguiente) {
		this.presupuestoSiguiente = presupuestoSiguiente;
	}

	public String getBalance() {
		return balance;
	}

	public void setBalance(String balance) {
		this.balance = balance;
	}
	
}

