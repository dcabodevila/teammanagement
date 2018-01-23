package es.ligasnba.app.model.equipo;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
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
import es.ligasnba.app.model.dto.EquipoSeleccionDto;
import es.ligasnba.app.model.equipodefault.EquipoDefault;
import es.ligasnba.app.model.finanzas.finanzasService;
import es.ligasnba.app.model.historicoEquipoJugador.HistoricoEquipoJugador;
import es.ligasnba.app.model.historicoEquipoJugador.HistoricoEquipoJugadorDao;
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
import es.ligasnba.app.model.util.CommonFunctions;



@Service("teamService")
@Transactional
public class teamServiceImpl implements teamService{
	
	@Autowired
	private UsuarioDao usuariodao;
	
	@SuppressWarnings("unused")
	@Autowired
	private PlatformTransactionManager transactionManager;
		
	@Autowired 
	private EquipoDao equipodao;
	
	@Autowired
	private JugadorDao jugadordao;
	
	@Autowired
	private CompeticionDao competiciondao;
	
	@Autowired
	private TraspasoDao traspasodao;
	
	@Autowired
	private PartidoDao partidodao;
	@Autowired
	private TemporadaDao temporadadao;
	
	@Autowired
	private finanzasService finanzasservice;
	@Autowired
	private HistoricoEquipoJugadorDao historicoEquipoJugadorDao;
	
	
	
	public void setEquipoDao(EquipoDao e) {
		equipodao = e;
	}
	public void setUsuarioDao(UsuarioDao u) {
		usuariodao = u;
	}
	public void setJugadorDao(JugadorDao jugadordao) {
		this.jugadordao = jugadordao;
	}
	public void setTraspasoDao(TraspasoDao traspasodao) {
		this.traspasodao = traspasodao;
	}
	public void settransactionManager(PlatformTransactionManager t) {
		transactionManager = t;
	}
	public void setCompeticionDao(CompeticionDao competiciondao) {
		this.competiciondao = competiciondao;
	}
	public void setPartidoDao(PartidoDao partidodao) {
		this.partidodao = partidodao;
	}
	public void setTemporadaDao(TemporadaDao temporadadao) {
		this.temporadadao = temporadadao;
	}
	public void setHistoricoEquipoJugadorDao(HistoricoEquipoJugadorDao historicoEquipoJugadorDao) {
		this.historicoEquipoJugadorDao = historicoEquipoJugadorDao;
	}
	

	
	@Transactional(readOnly=true)
	public EquipoBlock getTeamsOfCompetition(long idCompeticion, int startindex, int count) throws InstanceNotFoundException {
		
		Competicion c = competiciondao.find(idCompeticion);
		
		List<Equipo> equipos = c.getListaEquipos();
		
		boolean existsMoreTeams = equipos.size() == (count +1);

		if (existsMoreTeams) {
			equipos.remove(equipos.size()-1);
		}
		return new EquipoBlock(equipos, existsMoreTeams);
		
			
	}
	
	@Transactional(readOnly=true)
	public EquipoBlock getTeams(long idUser,int startindex,int count) throws InstanceNotFoundException {
	
		try{
			Usuario u = usuariodao.find(idUser);
			List<Equipo> teams = equipodao.getTeamsOfUser(idUser, startindex, count);
	
			return new EquipoBlock(teams, false);
			
		}catch(InstanceNotFoundException e){
			throw new InstanceNotFoundException(new String("Usuario no encontrado al pedir la lista de equipos"),Usuario.class.getName());
		}		
	}
	
	public Equipo teamRegister(String nombre){
		
		Equipo e=null;
					
		e = new Equipo(nombre);
		equipodao.create(e);		
		return e;
	}
	public void teamRemove(long idUsuario, long idTeam) throws InstanceNotFoundException, Exception {
		
		Usuario u = usuariodao.find(idUsuario);
		
		Equipo e = equipodao.find(idTeam);
		
		if (e.getCompeticion().getAdmin()!=u)
			throw new Exception("Solo un administrador puede eliminar el equipo");
		equipodao.remove(idTeam);		
		
	}
	@Transactional(readOnly=true)	
	public Equipo findById(long id) throws InstanceNotFoundException {
		
		Equipo e = equipodao.find(id);	
		return e;
		
	}


	@Transactional(readOnly = true)
	public EquipoBlock findTeamsByUserId(long userId, int startindex, int count) {
			
			List<Equipo> equipos = equipodao.findByUserId(userId, startindex, count);
			
			boolean existsMoreTeams = equipos.size() == (count +1);
	
			if (existsMoreTeams) {
				equipos.remove(equipos.size()-1);
			}
			return new EquipoBlock(equipos, existsMoreTeams);
			
			
		}
	@Transactional(readOnly = true)
	public Equipo findTeamNameInCompetition(String teamName, long idCompeticion) throws InstanceNotFoundException{
		return equipodao.findByNameInCompetition(teamName, idCompeticion);
	}
	
	public Equipo setUser(long userId, long teamId) throws InstanceNotFoundException {
	
		//if (!equipodao.exists(teamId)) throw new InstanceNotFoundException(teamId, Equipo.class.getName());
		//if (!usuariodao.exists(userId)) throw new InstanceNotFoundException(userId, Usuario.class.getName());
		
		Equipo e = equipodao.find(teamId);
		e.setUsuario(usuariodao.find(userId));
		equipodao.update(e);
		return e;			
				
	}
	
	public Equipo changeName(long teamId, String name) throws InstanceNotFoundException{
		
		Equipo e = equipodao.find(teamId);
		e.setnombre(name);
		equipodao.save(e);
		return e;
	
	}
	
	public Equipo addJugador(long idJugador, long idEquipo) throws InstanceNotFoundException {
		Jugador j = jugadordao.find(idJugador);	
		Equipo e = equipodao.find(idEquipo);
		e.addJugador(j);
		equipodao.save(e);
		return e;	
	}
	
	
	
	public Equipo removeJugador(long idJugador,long idEquipo) throws InstanceNotFoundException{
		Jugador j = jugadordao.find(idJugador);
		Equipo e = equipodao.find(idEquipo);
		e.remJugador(j);	
		equipodao.save(e);
		return e;	
	}
	
	
	
	
	//Solo puede aprobar el traspaso el equipo que lo recibe.
	public void TradeAccept(long idTeam, long idTraspaso) throws InstanceNotFoundException, Exception {
		Equipo e = equipodao.find(idTeam);
		Traspaso t = traspasodao.find(idTraspaso);
		if (e != t.getEquipoDestino())
			throw new Exception("El traspaso solo puede ser aprobado por el equipo al que fue enviado.");
		
		t.setAprobado(true);
		traspasodao.save(t);
		//t.getEquipoOrigen().getListaJugadores().removeAll(t.getListaJugadoresRecibidos());
		equipodao.save(t.getEquipoOrigen());
		//e.getListaJugadores().addAll(t.getListaJugadoresOfrecidos());
		equipodao.save(e);
		
		
	}
	
	public void TradeReject(long idTeam, long idTraspaso) throws InstanceNotFoundException, Exception {
	
		Equipo e = equipodao.find(idTeam);
		Traspaso t = traspasodao.find(idTraspaso);
		if (e != t.getEquipoDestino())
			throw new Exception("El traspaso solo puede ser aprobado por el equipo al que fue enviado.");
		e.getListaTraspasosRecibidos().remove(t);
		equipodao.save(e);
		t.getEquipoOrigen().getListaTraspasosOfrecidos().remove(t);
		equipodao.save(t.getEquipoOrigen());
		traspasodao.remove(t.getIdTraspaso());
			
	}
	
	
	public Equipo setCompetition(long idTeam, long idCompeticion) throws InstanceNotFoundException{
		Equipo e = equipodao.find(idTeam);
		Competicion c = competiciondao.find(idCompeticion);
		e.setCompeticion(c);
		equipodao.save(e);
		
		return e;
		
	}
		
//	public void getBalanceEquipo(long idEquipo) throws InstanceNotFoundException, Exception {
//		int numVictorias = 0;
//		int numEmpates = 0;
//		int numDerrotas = 0;
//		
//		Equipo e = equipodao.find(idEquipo);
//		int numPartidosTotalesEquipo = partidodao.getNumMatchesByTeam(idEquipo);
//		
//		List<Partido> listaPartidos = partidodao.getPlayedMatchesByTeam(idEquipo, 0, numPartidosTotalesEquipo);
//		
//		
//		System.out.println("Partidos Jugados por E1: "+ listaPartidos.size());
//		for (int i=0;i<listaPartidos.size();i++){
//			ActaPartido a = listaPartidos.get(i).getActasPartido();
//			
//			if (a == null)
//				throw new Exception("El partido "+listaPartidos.get(i).getEquipoLocal().getnombre()+" vs " + 
//						listaPartidos.get(i).getEquipoVisitante().getnombre() +" no contiene acta.");
//				
//			if (listaPartidos.get(i).getEquipoLocal().getIdEquipo() == idEquipo){
//					
//				if (a.getResultadoLocal()>a.getResultadoVisitante())
//					numVictorias++;
//			
//		    
//
//
//				if (a.getResultadoLocal()<a.getResultadoVisitante())
//					numDerrotas++;
//				
//			}
//			else 
//				if (listaPartidos.get(i).getEquipoVisitante().getIdEquipo() == idEquipo){
//					if (a.getResultadoLocal()<a.getResultadoVisitante())
//						numVictorias++;				    
//		
//					if (a.getResultadoLocal()>a.getResultadoVisitante())
//						numDerrotas++;	
//				}
//
//		
//		}
//		
//		System.out.println("Victorias temporada "+numVictorias);
//		equipodao.save(e);
//		
//		
//	}
	
//	public void getTotalBalances(long idCompeticion) throws InstanceNotFoundException, Exception{
//		Competicion c = competiciondao.find(idCompeticion);
//		List<Equipo> listaEquipos = c.getListaEquipos();
//		
//		for (int i=0;i<listaEquipos.size();i++){
//			getBalanceEquipo(listaEquipos.get(i).getIdEquipo());
//		}						
//		
//		
//	}
	@Transactional(readOnly=true)
	public Equipo getTeamOfUser(long idCompeticion, long idUsuario) throws InstanceNotFoundException{
		return equipodao.getTeamOfUserInCompetition(idUsuario, idCompeticion);
	}
	
	@Transactional(readOnly=true)
	public List<EquipoDefault> findEquiposDefaultByConferenceAndDivision(Short conference, Short division){
		return this.equipodao.selectEquiposDefaultByConferenceAndDivision(conference, division);
	}
	
	@Transactional(readOnly=true)
	@Override
	public EquipoDefault findEquipoDefaultById(Integer idEquipo){
		return this.equipodao.findEquipoDefaultById(idEquipo);
	}

	@Transactional(readOnly=true)
	@Override	
	public List<HistoricoEquipoJugador> findSeasonTeamsByPlayer(long idJugador, long idTemporada) {
		return this.historicoEquipoJugadorDao.findSeasonTeamsByPlayer(idJugador, idTemporada);
	}

	@Transactional(readOnly=true)
	@Override	
	public List<EquipoSeleccionDto> findAllTeamsNotSelectedInCompetition(long idCompeticion){
		return this.equipodao.findAllTeamsNotSelectedInCompetition(idCompeticion);
	}
	
	@Transactional(readOnly=true)
	@Override	
	public List<EquipoSeleccionDto> findAllEquiposFromCompetition(long idCompeticion){
		return this.equipodao.findAllEquiposFromCompetition(idCompeticion);
	}
	
	@Override
	public boolean isPermitidoRenovarJugadores(Competicion com){
		int numDiasToPostSeason = CommonFunctions.daysBetween(com.getActualDate(), com.getOffSeasonStartDate());
		
		if ((numDiasToPostSeason>0) && (numDiasToPostSeason<=7)){
			return true;
		}
		
		return false;
		
	}
	@Override
	public boolean isMercadoAbierto(Competicion com){
		
		return (com.isMercadoAbierto());
		
	}
	
	@Transactional
	public void actualizarPresupuestoEquipos(Competicion c) throws InstanceNotFoundException{
		
		Competicion com = this.competiciondao.find(c.getIdCompeticion());
		
		if (CollectionUtils.isNotEmpty(com.getListaEquipos())){
			for (Equipo e : com.getListaEquipos()){
				this.finanzasservice.actualizarPresupuestoEquipo(e, true);
				this.finanzasservice.actualizarPresupuestoEquipo(e, false);
			}
		}
	}
	

}