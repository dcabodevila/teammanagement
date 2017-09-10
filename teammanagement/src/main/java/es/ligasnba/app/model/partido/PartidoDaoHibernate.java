package es.ligasnba.app.model.partido;


import es.ligasnba.app.model.generic.GenericDaoHibernate;
import es.ligasnba.app.model.jugador.PlayerData;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import es.ligasnba.app.util.exceptions.InstanceNotFoundException;

import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;

@Repository("PartidoDao")
public class PartidoDaoHibernate extends GenericDaoHibernate<Partido,Long> implements PartidoDao {

	@SuppressWarnings("unchecked")
	public List<Partido> getMatchesBySeason(long idTemporada,String field, String order, int startindex,int count) throws InstanceNotFoundException {
		
		List<Partido> lc = getSession().createQuery("SELECT p FROM Partido p, Temporada t WHERE t.idTemporada=:idTemporada AND p.temporada = t ORDER BY p."+field+" "+order).setParameter("idTemporada", idTemporada).setFirstResult(startindex).setMaxResults(count).list();
		
		if (lc==null) 
			throw new InstanceNotFoundException(idTemporada, Partido.class.getName());
		else 
			return lc;
	}
	
	@SuppressWarnings("unchecked")
	public List<Partido> getMatchesByLocalTeam(long idEquipo, int startindex, int count) throws InstanceNotFoundException {
		List<Partido> lc = getSession().createQuery("SELECT p FROM Partido p, Equipo e WHERE e.idEquipo=:idEquipo AND p.equipoLocal = e").setParameter("idEquipo", idEquipo).setFirstResult(startindex).setMaxResults(count).list();
		
		if (lc==null) 
			throw new InstanceNotFoundException(idEquipo, Partido.class.getName());
		else 
			return lc;
	}

	@SuppressWarnings("unchecked")
	public List<Partido> getMatchesByVisitorTeam(long idEquipo, int startindex, int count) throws InstanceNotFoundException {
		List<Partido> lc = getSession().createQuery("SELECT p FROM Partido p, Equipo e WHERE e.idEquipo=:idEquipo AND p.equipoVisitante = e").setParameter("idEquipo", idEquipo).setFirstResult(startindex).setMaxResults(count).list();
		
		if (lc==null) 
			throw new InstanceNotFoundException(idEquipo, Partido.class.getName());
		else 
			return lc;
	}
	@SuppressWarnings("unchecked")
	public List<Partido> getMatchesByTeam(long idEquipo,String field, String order, int startindex, int count) throws InstanceNotFoundException {

		if (field=="temporada")
			field="temporada.idTemporada";
		
		List<Partido> lc = getSession().createQuery("SELECT p FROM Partido p, Equipo e WHERE e.idEquipo=:idEquipo AND (p.equipoVisitante = e OR p.equipoLocal = e) ORDER BY p."+field+" "+order).setParameter("idEquipo", idEquipo).setFirstResult(startindex).setMaxResults(count).list();
		
		if (lc==null) 
			throw new InstanceNotFoundException(idEquipo, Partido.class.getName());
		else 
			return lc;
	}
	
	@SuppressWarnings("unchecked")
	public List<Partido> getMatchesByTeamOrderByMatchData(long idEquipo,String field, String order, int startindex, int count) throws InstanceNotFoundException {
		
		
		List<Partido> lc = getSession().createQuery("SELECT p FROM Partido p, Equipo e WHERE e.idEquipo=:idEquipo AND (p.equipoVisitante = e OR p.equipoLocal = e) ORDER BY p.actapartido."+field+" "+order).setParameter("idEquipo", idEquipo).setFirstResult(startindex).setMaxResults(count).list();
		
		if (lc==null) 
			throw new InstanceNotFoundException(idEquipo, Partido.class.getName());
		else 
			return lc;
	}
	
	public int getNumMatchesByTeam(long idEquipo) throws InstanceNotFoundException{	
		
		long numPartidos = (Long) getSession().createQuery("SELECT COUNT(p) FROM Partido p, Equipo e WHERE e.idEquipo=:idEquipo AND (p.equipoLocal=e OR p.equipoVisitante=e)").setParameter("idEquipo", idEquipo).uniqueResult();
		return (int) numPartidos;
	}
	public int getNumMatchesBySeason(long idTemporada) throws InstanceNotFoundException{	
		
		long numPartidos = (Long) getSession().createQuery("SELECT COUNT(p) FROM Partido p, Temporada t WHERE t.idTemporada=:idTemporada AND p.temporada=t").setParameter("idTemporada", idTemporada).uniqueResult();
		return (int) numPartidos;
	}
	
	@SuppressWarnings("unchecked")
	public List<Partido> getPlayedMatchesBySeason(long idTemporada, int startindex, int count) throws InstanceNotFoundException {
		List<Partido> playedMatches = getSession().createQuery("SELECT p FROM Partido p, Temporada t WHERE t.idTemporada=:idTemporada AND p.temporada=t AND p.actaPartido!=NULL").setParameter("idTemporada", idTemporada).setFirstResult(startindex).setMaxResults(count).list();
		if (playedMatches==null) 
			throw new InstanceNotFoundException(idTemporada, Partido.class.getName());
		else 
			return playedMatches;
	}
	
	@SuppressWarnings("unchecked")
	public List<Partido> getRemainingMatchesBySeason(long idTemporada,
			int startindex, int count) throws InstanceNotFoundException {
		List<Partido> remainingMatches = getSession().createQuery("SELECT p FROM Partido p, Temporada t WHERE t.idTemporada=:idTemporada AND p.temporada=t AND p.actaPartido=NULL").setParameter("idTemporada", idTemporada).setFirstResult(startindex).setMaxResults(count).list();
		if (remainingMatches==null) 
			throw new InstanceNotFoundException(idTemporada, Partido.class.getName());
		else 
			return remainingMatches;	}
	
	@SuppressWarnings("unchecked")
	public List<Partido> getRemainingMatchesBySeasonAndDate(long idTemporada, Date date){

		List<Partido> remainingMatches = getSession().createQuery("SELECT p FROM Partido p, Temporada t WHERE t.idTemporada=:idTemporada AND p.temporada=t AND p.actaPartido=NULL AND p.fecha<:date").setParameter("idTemporada", idTemporada).setParameter("date", date).list();
		if (remainingMatches==null) 
			return new ArrayList<Partido>();
		else 
			return remainingMatches;	
				
	}
	
	@SuppressWarnings("unchecked")
	public List<Partido> getPlayedMatchesByTeam(long idEquipo, int startindex, int count) throws InstanceNotFoundException {
		List<Partido> playedMatches = getSession().createQuery(
				"SELECT p FROM Partido p, Equipo e WHERE e.idEquipo=:idEquipo " +
				"AND (p.equipoLocal=e OR p.equipoVisitante=e) AND p.actaPartido!=NULL AND p.temporada.idTemporada=e.competicion.idTemporadaActual ORDER BY p.fecha DESC").
				setParameter("idEquipo", idEquipo).
				setFirstResult(startindex).setMaxResults(count).list();
		if (playedMatches==null) 
			throw new InstanceNotFoundException(idEquipo, Partido.class.getName());
		else 
			return playedMatches;
		
	}

	@SuppressWarnings("unchecked")
	public List<Partido> getRemainingMatchesByTeam(long idEquipo,
			int startindex, int count) throws InstanceNotFoundException {
		List<Partido> remainingMatches = getSession().createQuery(
				"SELECT p FROM Partido p, Equipo e WHERE e.idEquipo=:idEquipo " +
				"AND (p.equipoLocal=e OR p.equipoVisitante=e) AND p.actaPartido=NULL").
				setParameter("idEquipo", idEquipo).
				setFirstResult(startindex).setMaxResults(count).list();
		if (remainingMatches==null) 
			throw new InstanceNotFoundException(idEquipo, Partido.class.getName());
		else 
			return remainingMatches;
	}
	

	@SuppressWarnings("unchecked")
	@Override
	public List<MatchData> findGamesList(String busqueda, long idEquipo, boolean isValidado){		 
		List<MatchData> lj = (List<MatchData>) ((SQLQuery) getSession().getNamedQuery("FIND_GAMES_LIST").setParameter("texto",busqueda).setParameter("idEquipo",idEquipo).setParameter("isValidado",isValidado)
				.setParameter("texto","%"+busqueda+"%"))
				.setResultTransformer( Transformers.aliasToBean(MatchData.class)).list();

		if (lj==null) 
			return new ArrayList<MatchData>();
		else 
			return lj;		
	}
	
	@Override
	public MatchData findGamesDataByIdPartido(long idEquipo, long idPartido){		 
		return (MatchData) ((SQLQuery) getSession().getNamedQuery("FIND_GAME_DATA_BY_ID").setParameter("idPartido",idPartido)
				.setParameter("idEquipo",idEquipo)
				.setResultTransformer( Transformers.aliasToBean(MatchData.class))).uniqueResult();
	}
	
//	@SuppressWarnings("unchecked")
//	public List<Partido> findByTeamId(long teamId, int startindex, int count) throws InstanceNotFoundException {
//		
//		List<Partido> lc = getSession().createQuery("SELECT c FROM Partido c, Equipo e WHERE e.idEquipo=:teamId AND c.equipo = e").setParameter("teamId", teamId).setFirstResult(startindex).setMaxResults(count).list();
//		
//		if (lc==null) 
//			throw new InstanceNotFoundException(teamId, Partido.class.getName());
//		else 
//			return lc;
//		
//	}
//	@SuppressWarnings("unchecked")
//	public List<Partido> findBySignedTeamId(long teamId, int startindex, int count) throws InstanceNotFoundException{
//		
//		List<Partido> lc = getSession().createQuery("SELECT c FROM Partido c, Equipo e WHERE e.idEquipo=:teamId AND c.equipo = e AND c.firmado=true").setParameter("teamId", teamId).setFirstResult(startindex).setMaxResults(count).list();
//		
//		if (lc==null) 
//			throw new InstanceNotFoundException(teamId, Partido.class.getName());
//		else 
//			return lc;
//		
//	}
	
}
