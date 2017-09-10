package es.ligasnba.app.model.arquetipoEquipo;


import es.ligasnba.app.model.generic.GenericDaoHibernate;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;

@Repository("ArquetipoEquipoDao")
public class ArquetipoEquipoDaoHibernate extends GenericDaoHibernate<ArquetipoEquipo,Long> implements ArquetipoEquipoDao {

	@Override
	public List<ArquetipoEquipoDto> findArquetiposActivosByCompeticion(long idCompeticion){		 

		@SuppressWarnings("unchecked")
		List<ArquetipoEquipoDto> listaArquetipos = (List<ArquetipoEquipoDto>) ((SQLQuery) getSession().getNamedQuery("FIND_ARQUETIPOS_BY_COMPETICION").setParameter("idCompeticion", idCompeticion))
				.setResultTransformer( Transformers.aliasToBean(ArquetipoEquipoDto.class)).list();
		if (listaArquetipos==null) {
			return new ArrayList<ArquetipoEquipoDto>();
		}
		return listaArquetipos;

	}
	
}
