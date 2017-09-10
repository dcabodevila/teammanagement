package es.ligasnba.app.model.competitionrol;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import es.ligasnba.app.util.constants.Constants;
import es.ligasnba.app.util.constants.teams;
import es.ligasnba.app.util.exceptions.InstanceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.Transactional;

import es.ligasnba.app.model.actapartido.ActaPartido;
import es.ligasnba.app.model.competicion.Competicion;
import es.ligasnba.app.model.competicion.CompeticionDao;
import es.ligasnba.app.model.jugador.Jugador;
import es.ligasnba.app.model.jugador.JugadorDao;
import es.ligasnba.app.model.partido.Partido;
import es.ligasnba.app.model.partido.PartidoDao;
import es.ligasnba.app.model.temporada.Temporada;
import es.ligasnba.app.model.temporada.TemporadaDao;
import es.ligasnba.app.model.traspaso.Traspaso;
import es.ligasnba.app.model.traspaso.TraspasoDao;
import es.ligasnba.app.model.usuario.Usuario;
import es.ligasnba.app.model.usuario.UsuarioDao;
import es.ligasnba.app.model.equipo.EquipoDao;
import es.ligasnba.app.model.clasificacion.ClasificacionDao;


@Service("competitionRolService")
@Transactional
public class competitionRolServiceImpl implements competitionRolService{

	@SuppressWarnings("unused")
	@Autowired
	private PlatformTransactionManager transactionManager;
	@Autowired
	private CompetitionRolDao competitionroldao;
	
	public void setCompetitionRolDao(CompetitionRolDao competitionroldao) {
		this.competitionroldao = competitionroldao;
	}
	public void setTransactionManager(
			PlatformTransactionManager transactionManager) {
		this.transactionManager = transactionManager;
	}

	public CompetitionRol createCompetitionRol(Usuario u, Competicion c, int rol){
		
		CompetitionRol cr = new CompetitionRol(u,c,rol);				
		
		competitionroldao.create(cr);
		
		return cr;
		
		
	}
	@Transactional(readOnly = true)
	public List<CompetitionRol> getCompetitionRol(long idUsuario, long idCompetition) throws InstanceNotFoundException{
		
		return competitionroldao.findByUserAndCompetition(idUsuario, idCompetition);
		
	}
	
	@Transactional(readOnly = true)
	public List<CompetitionRol> getCompetitionRolesOfUser(long idUsuario){
		return competitionroldao.findByUser(idUsuario);
	}


}