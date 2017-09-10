package es.ligasnba.app.model.historicoEquipoJugador;

import java.util.ArrayList;
import java.util.List;

import es.ligasnba.app.model.generic.GenericDaoHibernate;
import es.ligasnba.app.model.usuario.Usuario;
import es.ligasnba.app.model.equipo.Equipo;
import es.ligasnba.app.model.equipodefault.EquipoDefault;
import es.ligasnba.app.util.exceptions.InstanceNotFoundException;
import org.springframework.stereotype.Repository;

@Repository("HistoricoEquipoJugadorDao")
public class HistoricoEquipoJugadorDaoHibernate extends GenericDaoHibernate<HistoricoEquipoJugador,Long> implements HistoricoEquipoJugadorDao {
	
	@SuppressWarnings("unchecked")
	@Override
	public List<HistoricoEquipoJugador> findSeasonTeamsByPlayer(long idJugador, long idTemporada) {
		
		List<HistoricoEquipoJugador> listaHistorico = getSession().createQuery("SELECT h FROM HistoricoEquipoJugador h, Temporada t, Jugador j WHERE " + 
		" h.temporada = t AND t.idTemporada=:idTemporada AND h.jugador=j AND j.idJugador=:idJugador ").setParameter("idJugador", idJugador).setParameter("idTemporada", idTemporada).list();
		
		if (listaHistorico == null){
			return new ArrayList<HistoricoEquipoJugador>();			
		}
		
		return listaHistorico;
				
	}

}
