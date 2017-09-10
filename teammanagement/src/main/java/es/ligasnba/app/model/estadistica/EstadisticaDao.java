package es.ligasnba.app.model.estadistica;
import java.util.List;

import es.ligasnba.app.util.exceptions.InstanceNotFoundException;
import es.ligasnba.app.model.generic.GenericDao;

public interface EstadisticaDao extends GenericDao<Estadistica,Long> {

	public List<Estadistica> findStatsByPlayerAndGame(long idActaPartido, long idJugador,int startIndex, int count) throws InstanceNotFoundException;
	public List<Estadistica> findStatsByGameAndTeam(long idActaPartido, String field, String order, long idTeam, int startIndex, int count);
	public List<Estadistica> findStatsByPlayer(long idJugador);
	

}
