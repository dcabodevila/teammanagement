package es.ligasnba.app.model.finanzas;


import es.ligasnba.app.model.generic.GenericDaoHibernate;
import es.ligasnba.app.model.jugador.PlayerData;
import es.ligasnba.app.model.jugadordefault.JugadorDefault;
import es.ligasnba.app.util.exceptions.InstanceNotFoundException;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;

@Repository("AsientoDao")
public class AsientoDaoHibernate extends GenericDaoHibernate<Asiento,Long> implements AsientoDao {

	@SuppressWarnings("unchecked")
	@Override
	public List<AsientoDto> findAsientosByEquipoTemporada(long idEquipo, long idTemporada) {		 

		List<AsientoDto> j = (List<AsientoDto>) ((SQLQuery) getSession().getNamedQuery("FIND_ASIENTOS_BY_EQUIPO_TEMPORADA").setParameter("idEquipo", idEquipo).setParameter("idTemporada", idTemporada))
				.setResultTransformer( Transformers.aliasToBean(AsientoDto.class)).list();
		if (j==null) {
			return new ArrayList<AsientoDto>();
		}
		return j;

	}
	
	@Override
	public BigDecimal findBalanceByEquipoTemporada(long idEquipo, long idTemporada){		 

		return (BigDecimal) ((SQLQuery) getSession().getNamedQuery("FIND_BALANCE_BY_EQUIPO_TEMPORADA").setParameter("idEquipo", idEquipo).setParameter("idTemporada", idTemporada)).uniqueResult();

	}
	
}
