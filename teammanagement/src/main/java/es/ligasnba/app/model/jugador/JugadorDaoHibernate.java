package es.ligasnba.app.model.jugador;

import java.util.ArrayList;
import java.util.List;

import es.ligasnba.app.model.competicion.Competicion;
import es.ligasnba.app.model.contrato.Contrato;
import es.ligasnba.app.model.generic.GenericDaoHibernate;
import es.ligasnba.app.util.exceptions.InstanceNotFoundException;
import es.ligasnba.app.model.jugador.Jugador;
import es.ligasnba.app.model.jugadordefault.JugadorDefault;

import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;
import org.hibernate.type.BigDecimalType;
import org.hibernate.type.IntegerType;
import org.hibernate.type.LongType;
import org.hibernate.type.StringType;
import org.hibernate.type.Type;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

@Repository("JugadorDao")
public class JugadorDaoHibernate extends GenericDaoHibernate<Jugador,Long> implements JugadorDao {
	
	public Jugador findByName(String nombre) throws InstanceNotFoundException {
		
		Jugador j = (Jugador) getSession().createQuery("SELECT j FROM Jugador j WHERE j.nombre = :nombre").setParameter("nombre", nombre).uniqueResult();
		if (j==null) throw new InstanceNotFoundException(nombre, Jugador.class.getName());
		else return j;	
	}
	
	@SuppressWarnings("unchecked")
	public List<Jugador> findPlayersByTeamId(long teamId, int startindex, int count) throws InstanceNotFoundException{
		
		
		List<Jugador> lj =(List<Jugador>) getSession().createQuery("SELECT j FROM Jugador j, Equipo e , Contrato c WHERE j.equipo=e AND e.idEquipo=:teamId AND j.contrato = c AND c.firmado=true").setParameter("teamId", teamId).setFirstResult(startindex).setMaxResults(count).list();	
		
		if (lj==null) 
			throw new InstanceNotFoundException(teamId, Competicion.class.getName());
		else 
			return lj;
	}

	@SuppressWarnings("unchecked")	
	public List<Jugador> getPlayersOrderedByRate(long idCompeticion, int startindex,int count) throws InstanceNotFoundException{

		List<Jugador> players = (List<Jugador>)getSession().createQuery("SELECT j FROM Jugador j, Competicion c WHERE c.idCompeticion=:idCompeticion AND j.competicion = c ORDER BY media DESC").setParameter("idCompeticion", idCompeticion).setFirstResult(startindex).setMaxResults(count).list();
		
		if (players==null) 
			throw new InstanceNotFoundException(0, Jugador.class.getName());
		else 
			return players;

		
	}
	
	public List<Jugador> getPlayersFromCompetition(long competitionId, int startindex, int count){

		List<Jugador> lj =(List<Jugador>) getSession().createQuery("SELECT j FROM Jugador j, Competicion c WHERE c.idCompeticion=:idCompeticion AND j.competicion = c").setParameter("idCompeticion", competitionId).setFirstResult(startindex).setMaxResults(count).list();
		
		if (lj==null) 
			return new ArrayList<Jugador>();
		else 
			return lj;
		
		
	}
	
	public List<Jugador> getFreePlayersFromCompetition(long competitionId, int startindex, int count){

		List<Jugador> lj =(List<Jugador>) getSession().createQuery("SELECT j FROM Jugador j, Competicion c WHERE c.idCompeticion=:idCompeticion AND j.competicion = c AND j.equipo=NULL ").setParameter("idCompeticion", competitionId).setFirstResult(startindex).setMaxResults(count).list();
		
		if (lj==null) 
			return new ArrayList<Jugador>();
		else 
			return lj;
		
		
	}

	public List<Jugador> getJugadoresConOfertasFromCompetition(long idCompeticion) {
		
		List<Jugador> lj =(List<Jugador>) getSession().createQuery("SELECT j FROM Jugador j, Contrato c WHERE c.jugador=j AND c.jugadorOfrecido is not null AND j.competicion.idCompeticion=:idCompeticion AND c.firmado = false GROUP BY j.idJugador ORDER BY j.media DESC").setParameter("idCompeticion", idCompeticion).list();
		
		if (lj==null) 
			return new ArrayList<Jugador>();
		else 
			return lj;
		
	}
	
	public List<Jugador> getJugadoresConContratosPospuestos(long idCompeticion) {
		
		List<Jugador> lj =(List<Jugador>) getSession().createQuery("SELECT j FROM Jugador j, Contrato c WHERE c.jugador=j AND c.jugadorOfrecido is not null AND j.competicion.idCompeticion=:idCompeticion AND c.firmado = false GROUP BY j.idJugador ORDER BY j.media DESC").setParameter("idCompeticion", idCompeticion).list();
		
		if (lj==null) 
			return new ArrayList<Jugador>();
		else 
			return lj;
		
	}
	
	public List<Jugador> getAllPlayersFromCompetition(long idCompeticion){
		
		List<Jugador> lj = (List<Jugador>) getSession().createQuery("SELECT j FROM Jugador j, Competicion c WHERE j.competicion=c AND c.idCompeticion= :idCompeticion ").setParameter("idCompeticion", idCompeticion).list();
		if (lj==null) 
			return new ArrayList<Jugador>();
		else 
			return lj;

	}
	
	@SuppressWarnings("unchecked")
	public List<Jugador> findPlayersByDefaultTeam(int idEquipo, long idCompeticion){		 

		 List<Jugador> lj = (List<Jugador>) getSession().createQuery("SELECT j FROM Jugador j, Competicion c WHERE j.competicion=c AND c.idCompeticion= :idCompeticion AND j.idEquipoOriginal = :idEquipo").setParameter("idCompeticion", idCompeticion).setParameter("idEquipo", idEquipo).list();

		if (lj==null) 
			return new ArrayList<Jugador>();
		else 
			return lj;		
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Jugador> findFreePlayersByDefaultTeam(int idEquipo, long idCompeticion){		 

		 List<Jugador> lj = (List<Jugador>) getSession().createQuery("SELECT j FROM Jugador j, Competicion c WHERE j.competicion=c AND c.idCompeticion= :idCompeticion AND j.idEquipoOriginal = :idEquipo AND j.equipo is null ORDER BY j.nombre").setParameter("idCompeticion", idCompeticion).setParameter("idEquipo", idEquipo).list();

		if (lj==null) 
			return new ArrayList<Jugador>();
		else 
			return lj;		
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<JugadorDefault> findAllDefaultPlayers(){		 

		 List<JugadorDefault> lj = (List<JugadorDefault>) getSession().createQuery("SELECT j FROM JugadorDefault j, EquipoDefault e WHERE j.equipoDefault=e.idEquipo OR j.equipoDefault is null").list();

		if (lj==null) 
			return new ArrayList<JugadorDefault>();
		else 
			return lj;		
	}
	

	@SuppressWarnings("unchecked")
	@Override
	public List<PlayerData> findAllDefaultPlayersNotSignedByName(long idCompeticion, String nombre){		 
		List<PlayerData> lj = (List<PlayerData>) ((SQLQuery) getSession().getNamedQuery("FIND_DEFAULT_PLAYER_BY_NAME").setParameter("idCompeticion",idCompeticion)
				.setParameter("nombre","%"+nombre+"%"))
				.setResultTransformer( Transformers.aliasToBean(PlayerData.class)).list();

		if (lj==null) 
			return new ArrayList<PlayerData>();
		else 
			return lj;		
	}	
	
	@SuppressWarnings("unchecked")
	@Override
	public List<JugadorDefault> findDefaultPlayersByDefaultTeam(int idEquipo){		 

		 List<JugadorDefault> lj = (List<JugadorDefault>) getSession().createQuery("SELECT j FROM JugadorDefault j, EquipoDefault e WHERE j.equipoDefault=e AND e.idEquipo=:idEquipo ").setParameter("idEquipo", idEquipo).list();

		if (lj==null) 
			return new ArrayList<JugadorDefault>();
		else 
			return lj;		
	}
	
	@Override
	public PlayerData findDefaultPlayerById(long idJugador) throws InstanceNotFoundException{		 

		PlayerData j = (PlayerData) ((SQLQuery) getSession().getNamedQuery("FIND_DEFAULT_PLAYER_BY_ID").setParameter("idJugadorDefault", idJugador))
				.setResultTransformer( Transformers.aliasToBean(PlayerData.class)).uniqueResult();
		if (j==null) {
			throw new InstanceNotFoundException(idJugador, JugadorDefault.class.getName());
		}
		return j;

	}
	
	@Override
	public PlayerData findPlayerDataById(long idJugador) throws InstanceNotFoundException{		 

		PlayerData j = (PlayerData) ((SQLQuery) getSession().getNamedQuery("FIND_PLAYER_DATA_BY_ID_JUGADOR").setParameter("idJugador", idJugador))
				.setResultTransformer( Transformers.aliasToBean(PlayerData.class)).uniqueResult();
		if (j==null) {
			throw new InstanceNotFoundException(idJugador, JugadorDefault.class.getName());
		}
		return j;

	}
	
	@Override
	public JugadorDefault findDefaultPlayerByIdNotSigned(long idJugador, long idCompeticion) throws InstanceNotFoundException{		 

		JugadorDefault j = (JugadorDefault) getSession().createQuery("SELECT jd FROM JugadorDefault jd "
				+ "WHERE jd.idJugadorDefault=:idJugador "
				+ "AND jd.idJugadorDefault NOT IN ("
				+ "SELECT j.jugadorDefault.idJugadorDefault FROM Jugador j inner join j.competicion c "
				+ "WHERE c.idCompeticion=:idCompeticion) ")
				.setParameter("idJugador", idJugador).setParameter("idCompeticion", idCompeticion).uniqueResult();
		if (j==null) {
			throw new InstanceNotFoundException(idJugador, JugadorDefault.class.getName());
		}
		return j;

	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<PlayerData> findPlayersByTeam(long teamId){
		

		List<PlayerData> lj = (List<PlayerData>) ((SQLQuery) getSession().getNamedQuery("FIND_PLANTILLA_PLAYER_DATA")
				.setParameter("idEquipo",teamId))
				.setResultTransformer( Transformers.aliasToBean(PlayerData.class)).list();

		if (lj==null) 
			return new ArrayList<PlayerData>();
		else 
			return lj;			

	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<PlayerDataNextSeason> findPlayersByTeamNextSeason(long teamId){
		

		List<PlayerDataNextSeason> lj = (List<PlayerDataNextSeason>) ((SQLQuery) getSession().getNamedQuery("FIND_PLANTILLA_PLAYER_DATA_NEXT_SEASON")
				.setParameter("idEquipo",teamId))
				.setResultTransformer( Transformers.aliasToBean(PlayerDataNextSeason.class)).list();

		if (lj==null) 
			return new ArrayList<PlayerDataNextSeason>();
		else 
			return lj;			

	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Jugador> findFaNextSeason(long idCompeticion){
		
		List<Jugador> lj =(List<Jugador>) getSession().createQuery("SELECT j FROM Jugador j, Contrato c, Competicion com WHERE j.contrato=c AND com.idCompeticion=:idCompeticion AND 1=(SELECT Count(linea) FROM LineaContrato linea WHERE linea.contrato=c AND c.jugador.competicion.idCompeticion=:idCompeticion AND linea.temporada.idTemporada>=com.idTemporadaActual AND c.jugadorOfrecido is null AND c.firmado=true) ORDER BY j.media DESC").setParameter("idCompeticion",idCompeticion).list();
		
		if (lj==null) 
			return new ArrayList<Jugador>();
		else 
			return lj;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Jugador> findJugadoresConContratosPospuestos(long idCompeticion){
		
		List<Jugador> lj =(List<Jugador>) getSession().createQuery("SELECT j FROM Jugador j, Contrato c, Competicion com WHERE j.contrato=c AND com.idCompeticion=:idCompeticion AND c.pendiente=true) ORDER BY j.media DESC").setParameter("idCompeticion",idCompeticion).list();
		
		if (lj==null) 
			return new ArrayList<Jugador>();
		else 
			return lj;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Jugador> findJugadoresByIds(List<Long> idsJugador){
		
		List<Jugador> lj =(List<Jugador>) getSession().createQuery("SELECT j FROM Jugador j WHERE j.idJugador IN (:idsJugador)").setParameterList("idsJugador", idsJugador).list();
		
		if (lj==null) 
			return new ArrayList<Jugador>();
		else 
			return lj;		
	}

	@Override
	public void insertJugadoresCompeticionFromDefault(long idCompeticion){
		

		((SQLQuery) getSession().getNamedQuery("INSERT_JUGADOR_DEFAULT_IN_COMPETITION")
				.setParameter("idCompeticion",idCompeticion)).executeUpdate();	

	}
	
	@Override
	public void updateJugadoresCompeticionFromDefault(long idCompeticion){
		

		((SQLQuery) getSession().getNamedQuery("UPDATE_JUGADOR_DEFAULT_IN_COMPETITION")
				.setParameter("idCompeticion",idCompeticion)).executeUpdate();	

	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<PlayerData> findFreeAgentsByCompetition(long idCompeticion, String nombre){		 
		List<PlayerData> lj = (List<PlayerData>) ((SQLQuery) getSession().getNamedQuery("FIND_FREE_AGENTS_BY_COMPETITION").setParameter("idCompeticion",idCompeticion)
				.setParameter("nombre","%"+nombre+"%"))
				.setResultTransformer( Transformers.aliasToBean(PlayerData.class)).list();

		if (lj==null) 
			return new ArrayList<PlayerData>();
		else 
			return lj;		
	}
	
	@Override
	public PlayerData findFreeAgentDataById(long idJugador) throws InstanceNotFoundException{		 

		PlayerData j = (PlayerData) ((SQLQuery) getSession().getNamedQuery("FIND_FREE_AGENT_DATA_BY_ID_JUGADOR").setParameter("idJugador", idJugador))
				.setResultTransformer( Transformers.aliasToBean(PlayerData.class)).uniqueResult();
		if (j==null) {
			throw new InstanceNotFoundException(idJugador, JugadorDefault.class.getName());
		}
		return j;

	}
	
	@Override
	public void updateCacheAgentesLibres(long idCompeticion){
		

		((SQLQuery) getSession().getNamedQuery("UPDATE_CACHE_AGENTES_LIBRES_COMPETITION")
				.setParameter("idCompeticion",idCompeticion)).executeUpdate();	

	}
	
	
}
