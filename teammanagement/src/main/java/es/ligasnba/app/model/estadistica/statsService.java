package es.ligasnba.app.model.estadistica;

import java.util.List;

import es.ligasnba.app.model.competicion.Competicion;

public interface statsService {

	public List<Estadistica> getStatsByGame(long idActaPartido, String field, String order, long idEquipo, int startIndex, int count);

	
	
}
