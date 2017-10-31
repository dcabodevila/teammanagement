package es.ligasnba.app.model.partido;

import java.util.Date;
import java.util.List;

import es.ligasnba.app.util.exceptions.InstanceNotFoundException;
import es.ligasnba.app.model.generic.GenericDao;

public interface PartidoDao extends GenericDao<Partido,Long>{	

	public List<Partido> getMatchesBySeason(long idTemporada,String field, String order, int startindex,int count) throws InstanceNotFoundException;
	public List<Partido> getMatchesByTeam(long idEquipo,String field, String order, int startindex, int count) throws InstanceNotFoundException;
	public List<Partido> getMatchesByTeamOrderByMatchData(long idEquipo,String field, String order, int startindex, int count) throws InstanceNotFoundException;
	public List<Partido> getMatchesByLocalTeam(long idEquipo, int startindex,int count) throws InstanceNotFoundException;
	public List<Partido> getMatchesByVisitorTeam(long idEquipo, int startindex,int count) throws InstanceNotFoundException;
	public List<Partido> getPlayedMatchesBySeason(long idTemporada, int startindex,int count) throws InstanceNotFoundException;
	public int getNumMatchesByTeam(long idEquipo) throws InstanceNotFoundException;
	public List<Partido> getRemainingMatchesBySeason(long idTemporada, int startindex,int count) throws InstanceNotFoundException;	
	public List<Partido> getRemainingMatchesBySeasonAndDate(long idTemporada, Date date);
	public int getNumMatchesBySeason(long idTemporada) throws InstanceNotFoundException;
	public List<Partido> getPlayedMatchesByTeam(long idEquipo, int startindex,int count) throws InstanceNotFoundException;
	public List<Partido> getRemainingMatchesByTeam(long idEquipo, int startindex,int count) throws InstanceNotFoundException;
	List<MatchData> findGamesList(String busqueda, long idEquipo, boolean isValidado);
	MatchData findGamesDataByIdPartido(long idEquipo,long idPartido);
	List<Partido> getPartidosPendientesEquipo(long idEquipo);
	
	

}
