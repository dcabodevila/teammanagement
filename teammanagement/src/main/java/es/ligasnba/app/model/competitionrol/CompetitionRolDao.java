package es.ligasnba.app.model.competitionrol;


import java.util.List;

import es.ligasnba.app.util.exceptions.InstanceNotFoundException;
import es.ligasnba.app.model.generic.GenericDao;

public interface CompetitionRolDao extends GenericDao<CompetitionRol,Long>{

	public List<CompetitionRol> findByUserAndCompetition(long idUser, long idCompetition);
	public List<CompetitionRol> findByUser(long idUser);
	
}
