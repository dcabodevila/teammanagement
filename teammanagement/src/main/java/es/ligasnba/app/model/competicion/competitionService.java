package es.ligasnba.app.model.competicion;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import es.ligasnba.app.model.clasificacion.ClasificacionBlock;
import es.ligasnba.app.model.equipo.Equipo;
import es.ligasnba.app.model.equipodefault.EquipoDefault;
import es.ligasnba.app.model.temporada.Temporada;
import es.ligasnba.app.util.exceptions.DuplicateInstanceException;
import es.ligasnba.app.util.exceptions.InstanceNotFoundException;

public interface competitionService {
	
	public Competicion findById(long idCompeticion) throws InstanceNotFoundException;
	public Competicion findByName(String nombreCompeticion) throws InstanceNotFoundException;
	public CompeticionBlock getCompetitionsOfUser(long idUser,int startindex,int count) throws InstanceNotFoundException;
	
	public List<Competicion> getAllCompetitions();
	
	public void SetActualDate(Competicion c, Date actualDate);
	
	public CompeticionBlock getAdminCompetitionsOfUser(long idUser,int startindex,int count) throws InstanceNotFoundException;
	
	public Temporada getTemporadaActual(long idCompeticion) throws InstanceNotFoundException;
	
	public Competicion createEmptyCompetition(String nombre, String web, long idAdmin) throws DuplicateInstanceException, InstanceNotFoundException;
	
	public Competicion addTeamToCompetition(Equipo e, long idCompeticion) throws InstanceNotFoundException;
	public Equipo addNewTeamToCompetition(String nombreEquipo, long idCompeticion) throws InstanceNotFoundException;
	public boolean banUserFromCompetition(long idUsuario, long idCompeticion)throws InstanceNotFoundException;
	
	
	public long newSeason(long idCompeticion) throws InstanceNotFoundException, Exception;
	public void removeCompetition(long idCompeticion, long idUsuario) throws InstanceNotFoundException, Exception;
	
	public boolean pauseCompetition(long idCompeticion);
	public boolean playCompetition(long idCompeticion);
	
	public void defaultTeamRegister(long idCompeticion,int numTeams) throws InstanceNotFoundException;
	Equipo teamRegister(String nombreEquipo, long idCompeticion) throws InstanceNotFoundException;
	public boolean removeTeamFromCompetition(long idEquipo, long idCompeticion) throws InstanceNotFoundException;
	
	public ClasificacionBlock getClassificationFromSeason(long idSeason,int startindex,int count) throws InstanceNotFoundException;
	public ClasificacionBlock getActualClassification(long idCompeticion,int startindex,int count) throws InstanceNotFoundException;
	
	public List<Temporada> getSeasonsRemaining(long idCompeticion) throws InstanceNotFoundException;
	
	
	public boolean AutoDraftPlayers(long idCompeticion) throws InstanceNotFoundException;
	
	public List<Equipo> getAvailableTeams(long idCompeticion) throws InstanceNotFoundException;
	public boolean setCompetitionState(Competicion com, int estado);
	public List<EquipoDefault> getDefaultTeams() throws InstanceNotFoundException;
	public Competicion createCompetitionWithoutTeams(String nombre, String web, long idAdmin, Date startDate, Date finishDate, BigDecimal salaryCap, BigDecimal salaryTop) throws DuplicateInstanceException, InstanceNotFoundException;	
	Equipo teamDefaultRegister(EquipoDefault equipoDefault, long idCompeticion) throws InstanceNotFoundException;
	List<EquipoDefault> getDefaultRemainingTeams(long idCompeticion) throws InstanceNotFoundException;
	boolean actualizarEstadoCompeticionSegunCalendario(Competicion c);
	MenuNavigationForm findMenuNavigationInfo(long idCompeticion, long idEquipo);
	CompetitionForm findCompetitionInfo(long idCompeticion, long idEquipo);
	Competicion createCompetition(String nombre, String web, long idAdmin, Date startDate, Date finishDate,
			BigDecimal salaryCap, BigDecimal salaryTop) throws DuplicateInstanceException, InstanceNotFoundException;
	

}