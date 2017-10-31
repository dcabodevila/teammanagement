package es.ligasnba.app.model.jugador;

import java.util.Date;
import java.util.List;

import es.ligasnba.app.model.equipo.Equipo;
import es.ligasnba.app.model.jugadordefault.JugadorDefault;
import es.ligasnba.app.util.exceptions.InstanceNotFoundException;

public interface playerService {
	public Jugador playerRegister(String nombre, int media,int pos1, int pos2, Date fechaNacimiento) throws InstanceNotFoundException;
	
	public Jugador findById(long id) throws InstanceNotFoundException;
	public Jugador findByName(String name) throws InstanceNotFoundException;
	public JugadorBlock getPlantilla(long idTeam,int startindex, int count) throws InstanceNotFoundException;
	//public JugadorBlock findPlayerByTeamId(long teamId,int startindex,int count) throws InstanceNotFoundException;
	public void  playerRemove(long idPlayer) throws InstanceNotFoundException;
	public void cancelContract(long idPlayer, long idContract) throws InstanceNotFoundException; 
	public JugadorBlock getAgentesLibres(long idCompeticion, int startindex, int count) throws InstanceNotFoundException; 
	public void createPlayers(long idCompeticion) throws InstanceNotFoundException;
	public boolean setPlayerRotation(List<Jugador> listaJugadores);
	public List<Jugador> getAllResigningPlayers(long idCompeticion);	
	public List<Jugador> findPlayersFromDefaultTeam(int idCompeticion, long idEquipoOriginal);

	List<Jugador> findFreePlayersByDefaultTeam(int idEquipo, long idCompeticion);

	List<JugadorDefault> findAllDefaultPlayers();

	List<JugadorDefault> findAllDefaultPlayers(int idEquipo);
	PlayerData findDefaultPlayerById(long idJugador) throws InstanceNotFoundException;

	List<PlayerData> findJugadoresByTeam(long idTeam) throws InstanceNotFoundException;

	void clearRoster(long idEquipo) throws InstanceNotFoundException;

	List<PlayerData> findAllDefaultPlayersNotSignedByName(long idCompeticion, String name);

	void signDefaultPlayer(long idEquipo, long idJugadorDefault, long idUsuario) throws Exception;

	JugadorDefault findDefaultPlayerByIdNotSigned(long idJugador, long idCompeticion) throws InstanceNotFoundException;

	List<Jugador> findFaNextSeason(long idEquipo);

	List<Jugador> findJugadoresByIds(List<Long> idsJugador);

	List<PlayerDataNextSeason> findJugadoresByTeamNextSeason(long idTeam) throws InstanceNotFoundException;

	PlayerData findPlayerDataById(long idJugador) throws InstanceNotFoundException;

	void insertJugadoresCompeticionFromDefault(long idCompeticion);

	void updateJugadoresCompeticionFromDefault(long idCompeticion);

	List<PlayerData> findFreeAgentsByCompetition(long idCompeticion, String name);

	PlayerData findFreeAgentDataById(long idJugador) throws InstanceNotFoundException;

	void waivePlayer(long idEquipo, long idJugadorDefault) throws Exception;

	List<Jugador> findJugadoresConContratosPospuestos(long idCompeticion);

	void updateCacheAgentesLibres(long idCompeticion);
 	
}
