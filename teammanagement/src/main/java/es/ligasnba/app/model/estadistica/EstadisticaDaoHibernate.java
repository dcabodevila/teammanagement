package es.ligasnba.app.model.estadistica;

import java.util.ArrayList;
import java.util.List;

import es.ligasnba.app.model.generic.GenericDaoHibernate;
import es.ligasnba.app.util.exceptions.InstanceNotFoundException;
import org.springframework.stereotype.Repository;

@Repository("EstadisticaDao")
public class EstadisticaDaoHibernate extends GenericDaoHibernate<Estadistica,Long> implements EstadisticaDao {
	
	@SuppressWarnings("unchecked")
	public List<Estadistica> findStatsByPlayerAndGame(long idActaPartido, long idJugador,int startIndex, int count) throws InstanceNotFoundException{
		
		List<Estadistica> stats = new ArrayList<Estadistica>();
		
		stats = (List<Estadistica>) getSession().createQuery("SELECT e FROM Estadistica e , ActaPartido a, Jugador j WHERE j.idJugador =:idJugador AND a.idActaPartido =:idActaPartido AND e.jugador=j AND e.actaPartido=a").setParameter("idActaPartido",idActaPartido).setParameter("idJugador",idJugador).setMaxResults(count).list();

		if (stats==null) 
			throw new InstanceNotFoundException(idActaPartido, Estadistica.class.getName());
		else 
			return stats;
	}

	@SuppressWarnings("unchecked")
	public List<Estadistica> findStatsByGameAndTeam(long idActaPartido, String field, String order, long idTeam, int startIndex, int count){

		List<Estadistica> stats = new ArrayList<Estadistica>();
		
		stats = (List<Estadistica>) getSession().createQuery("SELECT e FROM Estadistica e , ActaPartido a, Jugador j WHERE e.idEquipoJugador=:idEquipo AND a.idActaPartido =:idActaPartido AND e.actaPartido = a AND e.jugador=j ORDER BY e."+field+" "+order).setParameter("idActaPartido",idActaPartido).setParameter("idEquipo",idTeam).setMaxResults(count).list();

		if (stats==null) 
			return new ArrayList<Estadistica>();
		else 
			return stats;
		
		
	}
	
	public List<Estadistica> findStatsByPlayer(long idJugador){
		
		List<Estadistica> stats = new ArrayList<Estadistica>();
		
		stats = (List<Estadistica>) getSession().createQuery("SELECT e FROM Estadistica e , Jugador j WHERE j.idJugador=:idJugador AND e.jugador=j").setParameter("idJugador",idJugador).list();

		if (stats==null) 
			return new ArrayList<Estadistica>();
		else 
			return stats;
		
		
	}
	
}
