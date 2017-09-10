package es.ligasnba.app.model.finanzas;

import java.math.BigDecimal;
import java.util.List;

import es.ligasnba.app.model.generic.GenericDao;
import es.ligasnba.app.util.exceptions.InstanceNotFoundException;

public interface AsientoDao extends GenericDao<Asiento,Long>{

	List<AsientoDto> findAsientosByEquipoTemporada(long idEquipo, long idTemporada);

	BigDecimal findBalanceByEquipoTemporada(long idEquipo, long idTemporada);	


}
