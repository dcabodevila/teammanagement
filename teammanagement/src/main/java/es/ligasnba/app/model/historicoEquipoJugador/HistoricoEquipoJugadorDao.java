package es.ligasnba.app.model.historicoEquipoJugador;


import java.util.List;

import es.ligasnba.app.util.exceptions.InstanceNotFoundException;
import es.ligasnba.app.model.equipodefault.EquipoDefault;
import es.ligasnba.app.model.generic.GenericDao;

public interface HistoricoEquipoJugadorDao extends GenericDao<HistoricoEquipoJugador,Long>{

	List<HistoricoEquipoJugador> findSeasonTeamsByPlayer(long idJugador, long idTemporada);

}
