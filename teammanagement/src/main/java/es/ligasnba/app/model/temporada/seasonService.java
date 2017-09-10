package es.ligasnba.app.model.temporada;


import java.util.List;

import es.ligasnba.app.model.competicion.Competicion;
import es.ligasnba.app.util.exceptions.InstanceNotFoundException;
public interface seasonService {
	
public Temporada findById(long id) throws InstanceNotFoundException;

Temporada getTemporadaActualCompeticion(Competicion c);

Temporada getTemporadaSiguienteCompeticion(Competicion c);

List<TemporadaData> findListaTemporadaDataByIdCompeticion(long idCompeticion);
}