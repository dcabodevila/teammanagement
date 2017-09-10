package es.ligasnba.app.model.competitionrol;

import java.util.ArrayList;
import java.util.List;

import es.ligasnba.app.model.generic.GenericDaoHibernate;
import es.ligasnba.app.util.constants.Constants;
import es.ligasnba.app.util.exceptions.InstanceNotFoundException;
import org.springframework.stereotype.Repository;

@Repository("CompetitionRolDao")
public class CompetitionRolDaoHibernate extends GenericDaoHibernate<CompetitionRol,Long> implements CompetitionRolDao {
	
	public List<CompetitionRol> findByUserAndCompetition(long idUser, long idCompetition){
		
		List<CompetitionRol> cr = (List<CompetitionRol>) getSession().createQuery("SELECT cr FROM CompetitionRol cr, Usuario u, Competicion c  WHERE u.idUsuario= :userId AND c.idCompeticion= :competitionId AND cr.usuario = u AND cr.competicion=c").setParameter("userId",idUser).setParameter("competitionId",idCompetition).list();
		
		if (cr==null) 
			return new ArrayList<CompetitionRol>();
		else 
			return cr;
	}
	
	@SuppressWarnings("unchecked")
	public List<CompetitionRol> findByUser(long idUser){
		
		List<CompetitionRol> competitionroles = null;
		
		competitionroles = (List<CompetitionRol>) getSession().createQuery("SELECT cr FROM CompetitionRol cr, Usuario u WHERE u.idUsuario=:userId AND cr.usuario=u").setParameter("userId",idUser).setFirstResult(Constants.cMinCompetitionsByUser).setMaxResults(Constants.cMaxCompetitionsByUser*2).list();
		
		if (competitionroles==null) 
			return new ArrayList<CompetitionRol>();
		else 
			return competitionroles;	
	}

}
