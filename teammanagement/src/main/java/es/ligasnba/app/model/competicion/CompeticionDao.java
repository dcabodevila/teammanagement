package es.ligasnba.app.model.competicion;
import java.util.List;

import es.ligasnba.app.model.equipo.Equipo;
import es.ligasnba.app.model.usuario.Usuario;
import es.ligasnba.app.model.generic.GenericDao;
import es.ligasnba.app.model.jugador.PlayerData;
import es.ligasnba.app.model.competicion.Competicion;
import es.ligasnba.app.util.exceptions.InstanceNotFoundException;

public interface CompeticionDao extends GenericDao<Competicion,Long>{
	public Competicion findByName(String idCompeticion) throws InstanceNotFoundException;
	public List<Competicion> getCompetitionsOfUser(long userId, int startIndex, int count);
	public List<Competicion> getAllCompetitions();
	//GetTemporadas?	
	public Equipo findUserInCompetition(long userId, long competitionId) throws InstanceNotFoundException;
	public List<Competicion> competitionsAdministratedByUser(long userId, int startIndex, int count) throws InstanceNotFoundException;
	public List<Usuario> findBestAdmin(long competitionId,int count) throws InstanceNotFoundException;
	public void removeFreePlayers(long idCompeticion);
	MenuNavigationForm findMenuNavigationInfo(long idCompeticion, long idEquipo);
	CompetitionForm findCompetitionInfo(long idCompeticion, long idEquipo);
	

	
	}