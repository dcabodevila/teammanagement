package es.ligasnba.app.model.arquetipoEquipo;

import java.util.List;

import es.ligasnba.app.util.exceptions.InstanceNotFoundException;

public interface ArquetipoEquipoService {

	List<ArquetipoEquipoDto> findArquetiposActivosByCompeticion(long idCompeticion);

	ArquetipoEquipoDto findArquetipoById(long idPaquete) throws InstanceNotFoundException;
}
