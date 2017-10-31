package es.ligasnba.app.model.arquetipoEquipo;

import java.util.List;

import es.ligasnba.app.model.generic.GenericDao;

public interface ArquetipoEquipoDao extends GenericDao<ArquetipoEquipo,Long>{

	List<ArquetipoEquipoDto> findArquetiposActivosByCompeticion(long idCompeticion);

	ArquetipoEquipoDto findArquetiposActivosById(long idArquetipo);
}
