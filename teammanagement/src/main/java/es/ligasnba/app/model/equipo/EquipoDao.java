package es.ligasnba.app.model.equipo;


import java.util.List;

import es.ligasnba.app.util.exceptions.InstanceNotFoundException;
import es.ligasnba.app.model.dto.EquipoSeleccionDto;
import es.ligasnba.app.model.equipodefault.EquipoDefault;
import es.ligasnba.app.model.generic.GenericDao;

public interface EquipoDao extends GenericDao<Equipo,Long>{
	public Equipo findByNameInCompetition(String nombre, long idCompeticion) throws InstanceNotFoundException;
	public List<Equipo> findByUserId(long userId, int startindex, int count);	
	public List<Equipo> getTeamsOfUser(long idUsuario, int startIndex, int count);
	public Equipo getTeamOfUserInCompetition(long idUsuario, long idCompeticion) throws InstanceNotFoundException;
	public List<EquipoDefault> selectEquiposDefaultByConferenceAndDivision(Short conference, Short division);
	EquipoDefault findEquipoDefaultById(Integer idEquipo);
	List<EquipoSeleccionDto> findAllTeamsNotSelectedInCompetition(long idCompeticion);
	List<EquipoSeleccionDto> findAllEquiposFromCompetition(long idCompeticion);
	


}
