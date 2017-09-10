package es.ligasnba.app.model.clasificacion;

import java.util.List;

import es.ligasnba.app.model.generic.GenericDaoHibernate;
import es.ligasnba.app.model.competicion.Competicion;
import es.ligasnba.app.util.exceptions.InstanceNotFoundException;
import org.springframework.stereotype.Repository;

@Repository("ClasificacionDao")
public class ClasificacionDaoHibernate extends GenericDaoHibernate<Clasificacion,Long> implements ClasificacionDao {
	
	@SuppressWarnings("unchecked")
	public List<Clasificacion>findClasifficationsByTeam(long teamId, int startIndex, int count) throws InstanceNotFoundException{
		
		List<Clasificacion> clas = null;
		
		clas = (List<Clasificacion>) getSession().createQuery("SELECT c FROM Clasificacion c , Equipo e WHERE e.idEquipo=:teamId AND c.equipo=e").setParameter("teamId",teamId).setMaxResults(count).list();
		if (clas==null) 
			throw new InstanceNotFoundException(teamId, Competicion.class.getName());
		else 
			return clas;		
			
	}
	
	@SuppressWarnings("unchecked")
	public List<Clasificacion>findClasifficationsBySeason(long seasonId, int startIndex, int count) throws InstanceNotFoundException{
		
		List<Clasificacion> clas = null;
		
		clas = (List<Clasificacion>) getSession().createQuery("SELECT c FROM Clasificacion c , Temporada t WHERE t.idTemporada=:seasonId AND c.temporada=t ORDER BY victorias DESC").setParameter("seasonId",seasonId).setMaxResults(count).list();
		if (clas==null) 
			throw new InstanceNotFoundException(seasonId, Competicion.class.getName());
		else 
			return clas;		
			
	}

	public Clasificacion findClassificationByTeamAndSeason(long teamId, long seasonId) throws InstanceNotFoundException{
		
		Clasificacion clas = null;
		
		clas = (Clasificacion) getSession().createQuery("SELECT c FROM Clasificacion c , Equipo e , Temporada t WHERE e.idEquipo=:teamId AND c.equipo=e AND t.idTemporada = :seasonId AND c.temporada=t").setParameter("teamId",teamId).setParameter("seasonId",seasonId).uniqueResult();
			
		if (clas==null) 
			throw new InstanceNotFoundException(teamId, Competicion.class.getName());
		else 
			return clas;		
			
		
	}
    
}
