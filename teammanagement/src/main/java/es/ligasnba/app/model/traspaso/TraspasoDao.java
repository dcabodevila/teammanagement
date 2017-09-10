package es.ligasnba.app.model.traspaso;

import java.util.List;

import es.ligasnba.app.util.exceptions.InstanceNotFoundException;
import es.ligasnba.app.model.generic.GenericDao;

public interface TraspasoDao extends GenericDao<Traspaso,Long> {

	public List<Traspaso> getReceivedTradesByTeam(long equipoDestino, int startindex, int count);
	public List<Traspaso> getSentTradesByTeam(long equipoOrigen, int startindex, int count);
	
}
