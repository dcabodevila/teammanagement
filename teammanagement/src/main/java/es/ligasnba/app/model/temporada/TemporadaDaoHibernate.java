package es.ligasnba.app.model.temporada;

import es.ligasnba.app.model.contrato.ContractData;
import es.ligasnba.app.model.generic.GenericDaoHibernate;
import es.ligasnba.app.model.usuario.Usuario;
import es.ligasnba.app.util.exceptions.InstanceNotFoundException;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;

@Repository("TemporadaDao")
public class TemporadaDaoHibernate extends GenericDaoHibernate<Temporada,Long> implements TemporadaDao {
	
	
    public Temporada findByName(String nombre) throws InstanceNotFoundException{
	Temporada t = (Temporada) getSession().createQuery("SELECT t FROM Temporada t WHERE t.nombre = :nombre ").setParameter("nombre",nombre ).uniqueResult();
		
		if (t==null) throw new InstanceNotFoundException(nombre, Usuario.class.getName());
		else return t;
	}
    
	@SuppressWarnings("unchecked")
	@Override
	public List<TemporadaData> findListaTemporadaDataByIdCompeticion(long idCompeticion){		 

		List<TemporadaData> j = (List<TemporadaData>) ((SQLQuery) getSession().getNamedQuery("FIND_LISTA_TEMPORADA_DATA_BY_ID_COMPETICION").setParameter("idCompeticion", idCompeticion))
				.setResultTransformer( Transformers.aliasToBean(TemporadaData.class)).list();
		if (j==null) {
			return new ArrayList<TemporadaData>();
		}
		return j;

	}
    
    


}
