package es.ligasnba.app.model.equipo;


import java.util.List;

import es.ligasnba.app.model.dto.EquipoSeleccionDto;
import es.ligasnba.app.model.equipodefault.EquipoDefault;
import es.ligasnba.app.model.historicoEquipoJugador.HistoricoEquipoJugador;
import es.ligasnba.app.util.exceptions.InstanceNotFoundException;
public interface teamService {
	

	public EquipoBlock getTeamsOfCompetition(long idCompeticion,int startindex, int count) throws InstanceNotFoundException; 
	public Equipo findById(long id) throws InstanceNotFoundException;
	public EquipoBlock getTeams(long idUser,int startindex,int count) throws InstanceNotFoundException;
	public EquipoBlock findTeamsByUserId(long userId, int startindex, int count);
	public Equipo setUser(long userId, long teamId) throws InstanceNotFoundException;
	public Equipo changeName(long teamId, String name) throws InstanceNotFoundException;
	public Equipo addJugador(long idJugador, long idEquipo) throws InstanceNotFoundException;
	public Equipo removeJugador(long idJugador,long idEquipo) throws InstanceNotFoundException;
	public void TradeAccept(long idTeam, long idTraspaso) throws InstanceNotFoundException, Exception;
	public void TradeReject(long idTeam, long idTraspaso) throws InstanceNotFoundException, Exception;
	public Equipo setCompetition(long idTeam, long idCompeticion) throws InstanceNotFoundException;
	public Equipo findTeamNameInCompetition(String teamName, long idCompeticion) throws InstanceNotFoundException;
	
//	public void getBalanceEquipo(long idEquipo) throws InstanceNotFoundException, Exception;
//	public void getTotalBalances(long idCompeticion) throws InstanceNotFoundException, Exception;
	public Equipo getTeamOfUser(long idCompeticion, long idUsuario) throws InstanceNotFoundException;	
	public List<EquipoDefault> findEquiposDefaultByConferenceAndDivision(Short conference, Short division);
	EquipoDefault findEquipoDefaultById(Integer idEquipo);
	List<HistoricoEquipoJugador> findSeasonTeamsByPlayer(long idJugador, long idTemporada);
	List<EquipoSeleccionDto> findAllTeamsNotSelectedInCompetition(long idCompeticion);
	List<EquipoSeleccionDto> findAllEquiposFromCompetition(long idCompeticion);

}