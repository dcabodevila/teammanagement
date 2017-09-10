package es.ligasnba.app.model.clasificacion;

import es.ligasnba.app.util.exceptions.InstanceNotFoundException;
public interface classificationService {
	
	public ClasificacionBlock getClasificacionesTemporada(long idTemporada, int index, int count) throws InstanceNotFoundException;
	public Clasificacion getClasificacionEquipoActual(long idEquipo, long idTemporada) throws InstanceNotFoundException;

}