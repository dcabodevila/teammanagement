package es.ligasnba.app.model.clasificacion;


import java.util.List;

import es.ligasnba.app.util.exceptions.InstanceNotFoundException;
import es.ligasnba.app.model.generic.GenericDao;

public interface ClasificacionDao extends GenericDao<Clasificacion,Long>{

	public List<Clasificacion>findClasifficationsByTeam(long teamId, int startIndex, int count) throws InstanceNotFoundException;
	public Clasificacion findClassificationByTeamAndSeason(long teamId, long seasonId) throws InstanceNotFoundException;
	public List<Clasificacion>findClasifficationsBySeason(long seasonId, int startIndex, int count) throws InstanceNotFoundException;

}
