package es.ligasnba.app.model.jugador;
import java.util.List;

import es.ligasnba.app.util.exceptions.InstanceNotFoundException;
import es.ligasnba.app.model.generic.GenericDao;
import es.ligasnba.app.model.jugador.Jugador;
import es.ligasnba.app.model.jugadordefault.JugadorDefault;

public interface JugadorDao extends GenericDao<Jugador,Long> {
	public Jugador findByName(String name) throws InstanceNotFoundException;
	public List<Jugador> findPlayersByTeamId(long teamId, int startindex, int count) throws InstanceNotFoundException;
	public List<Jugador> getPlayersOrderedByRate(long idCompeticion, int startindex,int count) throws InstanceNotFoundException;
	public List<Jugador> getPlayersFromCompetition(long teamId, int startindex, int count);
	public List<Jugador> getFreePlayersFromCompetition(long teamId, int startindex, int count);
	public List<Jugador> getJugadoresConOfertasFromCompetition(long idCompeticion);
	public List<Jugador> getAllPlayersFromCompetition(long idCompeticion);
	public List<Jugador> findPlayersByDefaultTeam(int idEquipo, long idCompeticion);
	List<Jugador> findFreePlayersByDefaultTeam(int idEquipo, long idCompeticion);
	List<JugadorDefault> findAllDefaultPlayers();
	List<JugadorDefault> findDefaultPlayersByDefaultTeam(int idEquipo);
	PlayerData findDefaultPlayerById(long idJugador) throws InstanceNotFoundException;
	List<PlayerData> findPlayersByTeam(long teamId);
	List<PlayerData> findAllDefaultPlayersNotSignedByName(long idCompeticion, String name);
	JugadorDefault findDefaultPlayerByIdNotSigned(long idJugador, long idCompeticion) throws InstanceNotFoundException;
	List<Jugador> findFaNextSeason(long teamId);
	List<Jugador> findJugadoresByIds(List<Long> idsJugador);
	List<PlayerDataNextSeason> findPlayersByTeamNextSeason(long teamId);
	PlayerData findPlayerDataById(long idJugador) throws InstanceNotFoundException;
	void insertJugadoresCompeticionFromDefault(long idCompeticion);
	void updateJugadoresCompeticionFromDefault(long idCompeticion);
	List<PlayerData> findFreeAgentsByCompetition(long idCompeticion, String nombre);
	PlayerData findFreeAgentDataById(long idJugador) throws InstanceNotFoundException;
	List<Jugador> findJugadoresConContratosPospuestos(long idCompeticion);


}
