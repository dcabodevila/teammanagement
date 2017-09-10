package es.ligasnba.app.model.segundoPlano;

import es.ligasnba.app.model.competicion.Competicion;

public interface SegundoPlanoService {
	
	boolean ejecutarActualizacionCompeticion(Competicion competicion, boolean forced);

	boolean simulateDays(Competicion com, int numDays) throws Exception;
 	
}
