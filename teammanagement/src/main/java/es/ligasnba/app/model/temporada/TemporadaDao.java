package es.ligasnba.app.model.temporada;

import es.ligasnba.app.util.exceptions.InstanceNotFoundException;

import java.util.List;

import es.ligasnba.app.model.generic.GenericDao;


public interface TemporadaDao extends GenericDao<Temporada,Long>{
	public Temporada findByName(String nombre) throws InstanceNotFoundException;

	List<TemporadaData> findListaTemporadaDataByIdCompeticion(long idCompeticion);

}
