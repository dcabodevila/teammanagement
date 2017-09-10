package es.ligasnba.app.model.lineacontrato;


import es.ligasnba.app.model.generic.GenericDaoHibernate;
import es.ligasnba.app.util.exceptions.InstanceNotFoundException;

import org.springframework.stereotype.Repository;

@Repository("LineaContratoDao")
public class LineaContratoDaoHibernate extends GenericDaoHibernate<LineaContrato,Long> implements LineaContratoDao {
	public LineaContrato findByTemporada(long idTemporada, long idContrato) throws InstanceNotFoundException{
		LineaContrato lc = (LineaContrato) getSession().createQuery("SELECT lc FROM LineaContrato lc WHERE lc.temporada = :idTemporada AND lc.contrato = idContrato").setParameter("idTemporada", idTemporada).setParameter("idContrato", idContrato).uniqueResult();
		if (lc==null) throw new InstanceNotFoundException(idTemporada, LineaContratoDaoHibernate.class.getName());
		else return lc;	
	}
	
	public int deleteByTemporada(long idTemporada){
		
		return getSession().createQuery("DELETE LineaContrato lc WHERE lc.temporada.idTemporada=:idTemporada").setParameter("idTemporada", idTemporada).executeUpdate();
		
		
	}
	
}
