package es.ligasnba.app.model.segundoPlano;

import es.ligasnba.app.model.competicion.Competicion;
import es.ligasnba.app.util.exceptions.InstanceNotFoundException;

public interface SegundoPlanoService {
	
	boolean simulateDays(Competicion com, int numDays) throws Exception;

	boolean ejecutarActualizacionCompeticion(long idCompeticion, boolean forced) throws InstanceNotFoundException;
 	
}
