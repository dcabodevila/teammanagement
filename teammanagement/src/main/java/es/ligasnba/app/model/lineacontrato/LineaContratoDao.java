package es.ligasnba.app.model.lineacontrato;

import es.ligasnba.app.model.generic.GenericDao;
import es.ligasnba.app.util.exceptions.InstanceNotFoundException;


public interface LineaContratoDao extends GenericDao<LineaContrato,Long>{

	public LineaContrato findByTemporada(long idTemporada, long idContrato) throws InstanceNotFoundException;
	public int deleteByTemporada(long idTemporada);
}
