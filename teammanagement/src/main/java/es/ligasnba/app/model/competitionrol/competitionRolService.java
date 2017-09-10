package es.ligasnba.app.model.competitionrol;
import java.util.List;

import es.ligasnba.app.model.competicion.Competicion;
import es.ligasnba.app.model.usuario.Usuario;
import es.ligasnba.app.util.exceptions.InstanceNotFoundException;
public interface competitionRolService {
	
	public CompetitionRol createCompetitionRol(Usuario u , Competicion c, int rol);
	public List<CompetitionRol> getCompetitionRol(long idUsuario, long idCompetiticion) throws InstanceNotFoundException;
	public List<CompetitionRol> getCompetitionRolesOfUser(long idUsuario);
}