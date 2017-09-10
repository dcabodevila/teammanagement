package es.ligasnba.app.model.partido;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import es.ligasnba.app.model.actapartido.ActaPartido;
import es.ligasnba.app.model.competicion.Competicion;
import es.ligasnba.app.model.equipo.Equipo;
import es.ligasnba.app.model.usuario.Usuario;
import es.ligasnba.app.util.exceptions.InstanceNotFoundException;
import es.ligasnba.app.util.jqgrid.CustomGenericResponse;

public interface matchService {
	
	public PartidoBlock getPartidosTemporada(long idTemporada,String field, String order, int startindex,int count) throws InstanceNotFoundException;
	public PartidoBlock getPartidosLocal(long idEquipo, int startindex, int count) throws InstanceNotFoundException;
	public PartidoBlock getPartidosVisitante(long idEquipo,int startindex,int count) throws InstanceNotFoundException;
	public PartidoBlock getPartidos(long idEquipo,String field, String order, int startindex, int count) throws InstanceNotFoundException;
	public List<Partido> getPartidosByDate(long idTemporada, Date date);
	public Partido createMatch(long idEquipoLocal, long idEquipoVisitante, long idTemporada) throws InstanceNotFoundException;
	public Date getMatchDate(Competicion com);
	public List<Partido> generateCalendar(long idCompeticion, long idTemporada) throws InstanceNotFoundException;
	public List<Partido> addTeamToCalendar(long idEquipo, long idCompeticion) throws InstanceNotFoundException;
	public int getNumTotalPartidosTemporada(long idTemporada) throws InstanceNotFoundException;
	public int getNumTotalPartidosEquipo(long idEquipo) throws InstanceNotFoundException;
	public PartidoBlock getPartidosJugadosEquipo(long idEquipo,int startindex, int count) throws InstanceNotFoundException;
	public PartidoBlock getPartidosPendientesEquipo(long idEquipo,int startindex, int count) throws InstanceNotFoundException;
	ActaPartido createActaPartido(long idPartido, Usuario usuarioValorador, boolean victoria, short valoracion,
			String comentario) throws InstanceNotFoundException, Exception;
	List<MatchData> findGamesList(String busqueda, long idEquipo, boolean isValidado);
	ValoracionData getValoracionFromPartido(long idEquipo, long idPartido, Long idUsuario);
	CustomGenericResponse sendValoracion(Equipo equipoValorador, long idPartido, Short valoracion, String comentario,
			boolean victoria);
	BigDecimal getValoracionesRecibidasManager(Long idUsuario);
	BigDecimal getValoracionesRealizadasManager(Long idUsuario);
	BigDecimal getValoracionEquipo(long idEquipo, int valoracionWinning);
	BigDecimal getValoracionLoyaltyUsuario(Long idUsuario, int loyaltyInterest);
}
