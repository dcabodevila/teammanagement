package es.ligasnba.app.model.competicion;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;

import es.ligasnba.app.model.equipo.Equipo;
import es.ligasnba.app.model.equipo.EquipoDao;
import es.ligasnba.app.model.equipodefault.EquipoDefault;
import es.ligasnba.app.model.finanzas.finanzasService;
import es.ligasnba.app.model.jugador.Jugador;
import es.ligasnba.app.model.jugador.JugadorDao;
import es.ligasnba.app.model.jugador.playerService;
import es.ligasnba.app.model.temporada.Temporada;
import es.ligasnba.app.model.temporada.TemporadaDao;
import es.ligasnba.app.model.tipo_competicion.TipoCompeticion;
import es.ligasnba.app.model.tipo_estado_competicion.TipoEstadoCompeticion;
import es.ligasnba.app.model.usuario.Usuario;
import es.ligasnba.app.model.usuario.UsuarioDao;
import es.ligasnba.app.model.util.CommonFunctions;
import es.ligasnba.app.util.Log;
import es.ligasnba.app.util.constants.Constants;
import es.ligasnba.app.util.constants.Logos;
import es.ligasnba.app.util.constants.teams;
import es.ligasnba.app.model.partido.Partido;
import es.ligasnba.app.model.partido.PartidoDao;
import es.ligasnba.app.model.partido.matchService;
import es.ligasnba.app.model.clasificacion.Clasificacion;
import es.ligasnba.app.model.clasificacion.ClasificacionBlock;
import es.ligasnba.app.model.clasificacion.ClasificacionDao;
import es.ligasnba.app.model.competitionrol.CompetitionRol;
import es.ligasnba.app.model.competitionrol.CompetitionRolDao;
import es.ligasnba.app.model.contrato.Contrato;
import es.ligasnba.app.model.contrato.ContractService;



import es.ligasnba.app.util.exceptions.DuplicateInstanceException;
import es.ligasnba.app.util.exceptions.InstanceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.Transactional;

@Service("competitionService")
@Transactional
public class competitionServiceImpl implements competitionService{

	@Autowired
	private CompeticionDao competiciondao;
	@Autowired
	private UsuarioDao usuariodao;
	@Autowired
	private EquipoDao equipodao;
	@Autowired
	private TemporadaDao temporadadao;
	@Autowired
	private JugadorDao jugadordao;
	@Autowired 
	private PartidoDao partidodao;
	@Autowired
	private ClasificacionDao clasificaciondao;
	@Autowired
	private CompetitionRolDao competitionroldao;

	@SuppressWarnings("unused")
	@Autowired
	private PlatformTransactionManager transactionManager;

	@Autowired
	private matchService matchservice;
	@Autowired
	private playerService playerservice;
	@Autowired
	private ContractService contractservice;
	@Autowired
	private finanzasService finanzasservice;
	
	public void setCompeticionDao(CompeticionDao c) {
		competiciondao = c;
	}
	public void setUsuarioDao(UsuarioDao usuariodao) {
		this.usuariodao = usuariodao;
	}
	public void setEquipoDao(EquipoDao equipodao) {
		this.equipodao = equipodao;
	}
	public void setTemporadaDao(TemporadaDao temporadadao){
		this.temporadadao = temporadadao;
	}
	public void setClasificacionDao(ClasificacionDao cl){
		this.clasificaciondao = cl;
	}
	public void setJugadorDao(JugadorDao j){
		this.jugadordao = j;
	}
	public void setPartidoDao(PartidoDao p){
		this.partidodao = p;
	}

	public void settransactionManager(
			PlatformTransactionManager transactionManager) {
		this.transactionManager = transactionManager;
	}	
	public void setMatchservice(matchService matchservice) {
		this.matchservice = matchservice;
	}	
	public void setPlayerservice(playerService playerservice) {
		this.playerservice = playerservice;
	}	
	public void setContractservice(ContractService contractservice){
		this.contractservice = contractservice;
	}
	public void setFinanzasService(finanzasService finanzasservice) {
		this.finanzasservice = finanzasservice;
	}
	
	@Transactional(readOnly = true)
	@Override
	public MenuNavigationForm findMenuNavigationInfo(long idCompeticion, long idEquipo){
		return this.competiciondao.findMenuNavigationInfo(idCompeticion, idEquipo);
	}
	
	@Transactional(readOnly = true)
	@Override
	public CompetitionForm findCompetitionInfo(long idCompeticion, long idEquipo){
		return this.competiciondao.findCompetitionInfo(idCompeticion, idEquipo);
	}

	@Transactional(readOnly = true)
	public Competicion findById(long idCompeticion)	throws InstanceNotFoundException {
		Competicion c = competiciondao.find(idCompeticion);
		return c;
	}
	
	@Transactional(readOnly = true)
	public Competicion findByName(String nombreCompeticion) throws InstanceNotFoundException {
		Competicion c = competiciondao.findByName(nombreCompeticion);
		return c;
	}
	
	@Transactional(readOnly = true)
	public List<Competicion> getAllCompetitions(){
		
		return competiciondao.getAllCompetitions();
		
		
	}
	
	public void SetActualDate(Competicion c, Date actualDate){
		
		c.setActualDate(actualDate);
		competiciondao.save(c);
		
	}
	
	@Transactional(readOnly = true)
	public CompeticionBlock getCompetitionsOfUser(long idUser,int startindex,int count)
			throws InstanceNotFoundException {
		List<Competicion> listaComp = competiciondao.getCompetitionsOfUser(idUser, startindex, count);
		
        boolean existMoreCompetitions = listaComp.size() == (Constants.cMaxListItems + 1);
        if (existMoreCompetitions) {
        	listaComp.remove(listaComp.size() - 1);
        }
        return new CompeticionBlock(listaComp, existMoreCompetitions);
	}
	
	

	@Transactional(readOnly = true)
	public CompeticionBlock getAdminCompetitionsOfUser(long idUser,int startindex,int count) throws InstanceNotFoundException {
		List<Competicion> listaCompAdmin = competiciondao.competitionsAdministratedByUser(idUser, startindex, count);
		
        boolean existMoreCompetitions = listaCompAdmin.size() == (Constants.cMaxListItems + 1);
        if (existMoreCompetitions) {
        	listaCompAdmin.remove(listaCompAdmin.size() - 1);
        }
        return new CompeticionBlock(listaCompAdmin, existMoreCompetitions);
	}
	
	@Transactional(readOnly = true)
	public Temporada getTemporadaActual(long idCompeticion) throws InstanceNotFoundException{

		Competicion c = competiciondao.find(idCompeticion);
		
		return temporadadao.find(c.getIdTemporadaActual());
	}
	
	public Competicion createEmptyCompetition(String nombre, String web, long idAdmin) throws DuplicateInstanceException, InstanceNotFoundException{
		
		Competicion c = null;
		Usuario u;
		
		try{ 
			competiciondao.findByName(nombre);
			throw new DuplicateInstanceException(Competicion.class,"La competicion ya está creada");
		}catch(InstanceNotFoundException e){

			u = usuariodao.find(idAdmin);
			
			
			c = new Competicion(nombre, web, u);
			competiciondao.create(c);
			
			//Hay que crearla junto a una nueva temporada
			Temporada t = null;
			for (int i=0; i<Constants.cMaxSeasonsPerCompetition;i++){
				Short orden = (short) (c.getListaTemporadas().size()+1);
				t = new Temporada("Temporada " +c.getListaTemporadas().size()+1,c, orden);
				
				temporadadao.create(t);
				c.getListaTemporadas().add(t);
			}
			c.setIdTemporadaActual(c.getListaTemporadas().get(0).getIdTemporada());
			
			//TODO: Traer el tipo de competición desde la pantallad de configuración
			
			c.setTipoCompeticion(new TipoCompeticion(Constants.cTipoCompeticionDraft));
			
			if (c.getTipoCompeticion().equals(Constants.cTipoCompeticionDraft)){ 
				c.setTipoEstadoCompeticion(new TipoEstadoCompeticion(Constants.cTipoEstadoCompeticionDraft));
			}
			else if (c.getTipoCompeticion().equals(Constants.cTipoCompeticionReal)){
				c.setTipoEstadoCompeticion(new TipoEstadoCompeticion(Constants.cTipoEstadoCompeticionPretemporada));
			}
			
			
			competiciondao.save(c);
			
			return c;				
			
			
		}
		
					
		
	}
	
	public long newSeason(long idCompeticion) throws InstanceNotFoundException, Exception{
		Competicion c = competiciondao.find(idCompeticion);
		
		Iterator<Temporada> temporadas = c.getListaTemporadas().iterator();
		Temporada tsiguiente = (Temporada) temporadas.next();
							
		
		List<Contrato> listaContratos = contractservice.findExpiringContracts(idCompeticion);
		
		for (int i=0;i<listaContratos.size();i++){
			
			Contrato contract = listaContratos.get(i);
			
			contract.getJugador().setContrato(null);
			contract.getEquipo().getListaContratos().remove(contract);
			contract.getEquipo().remJugador(contract.getJugador());
			
		}
			
		
		
		while (tsiguiente.getIdTemporada()!= c.getIdTemporadaActual()){
			tsiguiente = (Temporada) temporadas.next();
			if( !temporadas.hasNext())
				throw new Exception("Se ha alcanzado el límite de Temporadas para esta competición.");
		}
		
		c.setIdTemporadaActual( temporadas.next().getIdTemporada());
		
		setNewDates(c);
		resetVariablesEquipos(c);
		//Generamos el calendario de la temporada siguiente:
		
		matchservice.generateCalendar(idCompeticion, c.getIdTemporadaActual());
				
		return c.getIdTemporadaActual();	
		
	}
	
	private void resetVariablesEquipos(Competicion c){
		
		for (Equipo equipo : c.getListaEquipos()){
			equipo.setMidLevelExceptionUsed(false);
			equipo.setPresupuestoActual(equipo.getPresupuestoProximaTemporada());
			equipo.setPresupuestoProximaTemporada(BigDecimal.ZERO);
		
			this.equipodao.update(equipo);
		}
		
	}
	
	private void setNewDates( Competicion c){
		
		c.setActualDate(new Date());
		c.setStartDate(new Date());

		c.setEstado(Constants.cStateNotStarted);

		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		cal.add(Calendar.DAY_OF_MONTH, 7);
		c.setFechaComienzoRS(cal.getTime());
		cal.add(Calendar.MONTH, 1);
		c.setFechaCierreMercado(cal.getTime());		
		cal.add(Calendar.MONTH, 1);
		c.setFechaComienzoPO(cal.getTime());
		cal.add(Calendar.MONTH, 1);
		c.setOffSeasonStartDate(cal.getTime());
		cal.add(Calendar.DAY_OF_MONTH, 7);
		c.setOffSeasonFinishDate(cal.getTime());
		cal.add(Calendar.DAY_OF_MONTH, 1);
		c.setFinishDate(cal.getTime());
		
	}
	
	@Transactional
	@Override
	public Competicion createCompetition(String nombre, String web, long idAdmin, Date startDate, Date finishDate, BigDecimal salaryCap, BigDecimal salaryTop) throws DuplicateInstanceException, InstanceNotFoundException {
		
		final Competicion c = this.createCompetitionWithoutTeams(nombre, web, idAdmin, startDate, finishDate, salaryCap, salaryTop);
		final List<EquipoDefault> equiposDefault = equipodao.selectEquiposDefaultByConferenceAndDivision(null, null);
		
		for (EquipoDefault equipoDefault : equiposDefault){
			this.teamDefaultRegister(equipoDefault, c.getIdCompeticion());	
		}
		
		matchservice.generateCalendar(c.getIdCompeticion(), c.getIdTemporadaActual());
		this.competiciondao.save(c);
		
		this.finanzasservice.ingresarPresupuestoInicial(c);
		
		return c;
				
	}

	
	@Transactional
	public Competicion createCompetitionWithoutTeams(String nombre, String web, long idAdmin, Date startDate, Date finishDate, BigDecimal salaryCap, BigDecimal salaryTop) throws DuplicateInstanceException, InstanceNotFoundException {
		Competicion c = null;
				
		c = createEmptyCompetition(nombre,web,idAdmin);
		c.setNumVueltas(1);
		c.setActualDate(new Date());
		c.setStartDate(startDate);
		c.setFinishDate(finishDate);
		c.setEstado(Constants.cStateNotStarted);
		c.setLimiteSalarial(salaryCap);
		c.setLimiteTope(salaryTop);

		Calendar cal = Calendar.getInstance();
		cal.setTime(startDate);
		cal.add(Calendar.DAY_OF_MONTH, 7);
		c.setFechaComienzoRS(cal.getTime());
		cal.add(Calendar.MONTH, 1);
		c.setFechaCierreMercado(cal.getTime());		
		cal.add(Calendar.MONTH, 1);
		c.setFechaComienzoPO(cal.getTime());
		cal.add(Calendar.MONTH, 1);
		c.setOffSeasonStartDate(cal.getTime());
		cal.add(Calendar.DAY_OF_MONTH, 7);
		c.setOffSeasonFinishDate(cal.getTime());
		
		c.setTipoEstadoCompeticion(new TipoEstadoCompeticion(Constants.cTipoEstadoCompeticionDraft));
		competiciondao.save(c);
		
		Usuario u = usuariodao.find(idAdmin); 

		//Creamos el rol directamente en e dao (para no inyectar todo el service
		CompetitionRol cr = new CompetitionRol(u,c,Constants.cRolAdminCompeticion);				
		competitionroldao.create(cr);			
		
		

		//Los jugadores se crean en cuanto los selecciona el user en la pantalla de Draft
//		playerservice.createPlayers(idCompeticion);
		
	
		return c;	
	}
	
	
	public void DraftPlayers(long idCompeticion) throws InstanceNotFoundException{
		AutoDraftPlayers(idCompeticion);		
		
	}
	
	public Competicion addTeamToCompetition(Equipo e, long idCompeticion) throws InstanceNotFoundException{
		
				
		Competicion	c = competiciondao.find(idCompeticion);
		
		c.addEquipo(e);
		
		//Creamos una clasificacion por cada temporada restante.
		List<Temporada> temporadasRestantes = getSeasonsRemaining(idCompeticion);
				
		
		for (int i =0;i<temporadasRestantes.size();i++){
			

			
			Clasificacion cl = new Clasificacion(e,temporadasRestantes.get(i));
			clasificaciondao.save(cl);
			
			
			e.addClasificacion(cl);
			temporadasRestantes.get(i).addClasificacion(cl);
			
			temporadadao.save(temporadasRestantes.get(i));
			
			
			
		}
		
		Calendar cal = Calendar.getInstance();
		cal.setTime(c.getActualDate());
		cal.add( Calendar.DATE, -1);
		
		e.setFechaUltimoContrato(cal.getTime());
		
		
		competiciondao.save(c);
		equipodao.save(e);			
		
		

		return c;
		
	}
	
	public Equipo addNewTeamToCompetition(String nombreEquipo, long idCompeticion) throws InstanceNotFoundException{			
		
		Equipo e = new Equipo(nombreEquipo);
		equipodao.create(e);
		
		
		addTeamToCompetition(e,idCompeticion);
		matchservice.addTeamToCalendar(e.getIdEquipo(), idCompeticion);
		
		return e;
		
	}
	
	public boolean removeTeamFromCompetition(long idEquipo, long idCompeticion) throws InstanceNotFoundException{
		
				
		Equipo e = equipodao.find(idEquipo);		
		Competicion	c = e.getCompeticion();
				
		if ((e.getUsuario()!= null) && (e.getUsuario().getIdUsuario() == c.getAdmin().getIdUsuario()))
			return false;
		
		List<Clasificacion> lc = clasificaciondao.findClasifficationsByTeam(idEquipo, 0, Constants.cMaxSeasonsPerCompetition);
		List<Temporada> lt = getSeasonsRemaining(idCompeticion);
		
		for (int i =0;i<lt.size();i++){
			
			lt.get(i).getListaClasificaciones().removeAll(lc);
			
//			//Si la temporada pertenece a las temporadas restantes (no las anteriores) borramos la clasificacion dentro del equipo y la temporada.
//			if (lt.contains( lc.get(i).getTemporada())){
//								
//				lt.get(i).remClasificacion(lc.get(i));								
//				temporadadao.save(lt.get(i));
//				e.remClasificacion(lc.get(i));
//				clasificaciondao.remove(lc.get(i).getidClasificacion());						
//			}

		}

 	    	   

		
		Iterator<Partido> partidosLocal = e.getListaPartidosLocal().iterator();
		
		while(partidosLocal.hasNext()){
			Partido p = partidosLocal.next();
			
			p.getEquipoVisitante().remPartidoVisitante(p);
			
			equipodao.save(p.getEquipoVisitante());
		    		
		}		
		
		
		
		
				
		e.getListaPartidosLocal().clear();		

		

			
		Iterator<Partido> partidosVisitante = e.getListaPartidosVisitante().iterator();
		
		while(partidosVisitante.hasNext()){
			Partido p = partidosVisitante.next();
			
			p.getEquipoLocal().remPartidoLocal(p);
								
			equipodao.save(p.getEquipoLocal());
		    		
		}

		e.getListaPartidosVisitante().clear();
		

		List<Partido> removedMatches = matchservice.getPartidos(e.getIdEquipo(),"fecha","ASC", 0, matchservice.getNumTotalPartidosEquipo(e.getIdEquipo())).getPartidos();

		
		for (int i=0;i< removedMatches.size();i++)
			partidodao.remove(removedMatches.get(i).getIdPartido());
		
		Temporada t = getTemporadaActual(c.getIdCompeticion());
		t.getListaPartidos().removeAll(removedMatches);		
		
		//Liberar jugadores:
		
		List<Jugador> plantilla = playerservice.getPlantilla(e.getIdEquipo(), 0, Constants.cMaxPlayersByTeam).getJugadores();
		
		for (int i=0;i<plantilla.size();i++){
			
			Jugador j =plantilla.get(i); 
			
			contractservice.removeContract(j.getContrato().getIdContrato());

			
		}
		
		c.remEquipo(e);
				
		equipodao.save(e);
		competiciondao.save(c);

		return true;
		
	}	
	
	@Transactional(readOnly = true)
	public ClasificacionBlock getClassificationFromSeason(long idSeason,int startindex,int count) throws InstanceNotFoundException{

		Temporada t = temporadadao.find(idSeason);
		
		List<Clasificacion> listClassification = t.getListaClasificaciones();
		
		boolean existMoreClassifications = listClassification.size() == (Constants.cMaxListItems + 1);
        if (existMoreClassifications) {
        	listClassification.remove(listClassification.size() - 1);
        }
        return new ClasificacionBlock(listClassification, existMoreClassifications);		
		
	}
	
	@Transactional(readOnly = true)
	public ClasificacionBlock getActualClassification(long idCompeticion,int startindex,int count) throws InstanceNotFoundException{
        

		
		Temporada t = getTemporadaActual(idCompeticion);
					
		List<Clasificacion> listClassification = t.getListaClasificaciones();
		
		boolean existMoreClassifications = listClassification.size() == (Constants.cMaxListItems + 1);
        if (existMoreClassifications) {
        	listClassification.remove(listClassification.size() - 1);
        }
        return new ClasificacionBlock(listClassification, existMoreClassifications);		
	}
	
	
	public boolean banUserFromCompetition(long idUsuario, long idCompeticion)throws InstanceNotFoundException{
		try{
			Usuario u = usuariodao.find(idUsuario);
			
			Competicion c = competiciondao.find(idCompeticion);
			
			if (c.getAdmin().getIdUsuario()==idUsuario) return false;
			
			for (int i=0;i<u.getListaEquipos().size();i++){				
				if (u.getListaEquipos().get(i).getCompeticion() == c ){
					u.getListaEquipos().get(i).setUsuario(null);
					
				}								
			}

			List<CompetitionRol> competitionrol= competitionroldao.findByUserAndCompetition(idUsuario, idCompeticion);			
			
			for (int i=0;i< competitionrol.size();i++)
				competitionroldao.remove( competitionrol.get(i).getIdRol() );
			
			c.getListaUsuarios().remove(u);		
		
			u.getListaCompeticiones().remove(c);
//			u.setuserValue(Constants.cValueKickedUserFromCompetition);
			
//			competiciondao.save(c);
//			usuariodao.save(u);
			
			return true;
			

		}catch(InstanceNotFoundException e){
			return false;

		}			

		
	}
	
	
	public void removeCompetition(long idCompeticion, long idUsuario) throws InstanceNotFoundException, Exception{
		
		Competicion c = competiciondao.find(idCompeticion);
		
		if (c.getAdmin().getIdUsuario() != idUsuario){
			throw new Exception("El usuario no es el administrador de la competición");
			}
		else {
			Iterator i = c.getListaUsuarios().iterator();
			while (i.hasNext()){
				Usuario u = (Usuario)i.next();
				u.getListaCompeticiones().remove(c);


			}
			
			
			competiciondao.remove(idCompeticion);
			
			competiciondao.removeFreePlayers(idCompeticion);
			
			
			}
		
		
		
	}
	
	
	public boolean pauseCompetition(long idCompeticion){
		
		try {
			Competicion c = competiciondao.find(idCompeticion);
			c.setPaused(true);
			
		} catch (InstanceNotFoundException e) {
			return false;
		}
		return true;
		
	}
	
	public boolean playCompetition(long idCompeticion){
		
		try {
			Competicion c = competiciondao.find(idCompeticion);
			c.setPaused(false);
			
		} catch (InstanceNotFoundException e) {
			return false;
		}
		return true;
		
	}

	
	
	@Transactional(readOnly = true)	
	public List<String> getRealTeams(int numTeams){
		List<String> totalDefaultTeams = new ArrayList<String>();
		List<String> selectedDefaultTeams = new ArrayList<String>();
		
		
		totalDefaultTeams.add(teams.bobcats);
		totalDefaultTeams.add(teams.bucks);		
		totalDefaultTeams.add(teams.bulls);
		totalDefaultTeams.add( teams.cavaliers);
		totalDefaultTeams.add( teams.celtics);
		totalDefaultTeams.add( teams.clippers);
		totalDefaultTeams.add( teams.grizzlies);
		totalDefaultTeams.add( teams.hawks);
		totalDefaultTeams.add( teams.heat);
		totalDefaultTeams.add( teams.pelicans);
		totalDefaultTeams.add( teams.jazz);
		totalDefaultTeams.add( teams.kings);
		totalDefaultTeams.add( teams.knicks);
		totalDefaultTeams.add( teams.lakers);
		totalDefaultTeams.add( teams.magic);
		totalDefaultTeams.add( teams.mavericks);
		totalDefaultTeams.add( teams.nets);
		totalDefaultTeams.add( teams.nuggets);
		totalDefaultTeams.add( teams.pacers);
		totalDefaultTeams.add( teams.pistons);
		totalDefaultTeams.add( teams.raptors);
		totalDefaultTeams.add( teams.rockets);
		totalDefaultTeams.add( teams.sixers);
		totalDefaultTeams.add( teams.spurs);
		totalDefaultTeams.add( teams.suns);
		totalDefaultTeams.add( teams.thunder);
		totalDefaultTeams.add( teams.timberwolves);
		totalDefaultTeams.add( teams.trailblazers);
		totalDefaultTeams.add( teams.warriors);
		totalDefaultTeams.add( teams.wizards);
		
		for ( int i=0; i<numTeams;i++){
			selectedDefaultTeams.add(totalDefaultTeams.get(i));			
		}
		return selectedDefaultTeams;
		
	}
	

	public void defaultTeamRegister(long idCompeticion,int numTeams) throws InstanceNotFoundException{						

		List<String> defaultTeams = getRealTeams(numTeams);
		
		for (int i=0;i<defaultTeams.size();i++)
			teamRegister(defaultTeams.get(i),idCompeticion);
						
		
	}
	
	public Equipo teamRegister(String nombreEquipo, long idCompeticion) throws InstanceNotFoundException{		
			
		
		Equipo e = new Equipo(nombreEquipo);
						
		e.setLogo(Logos.getLogos().get(e.getnombre()));  
		
		equipodao.create(e);

		addTeamToCompetition(e,idCompeticion);
		
									
		return e;
		
	}
	@Override
	public Equipo teamDefaultRegister(EquipoDefault equipoDefault, long idCompeticion) throws InstanceNotFoundException{		
		
		Equipo e = new Equipo(equipoDefault.getnombre());
						
		e.setLogo(equipoDefault.getLogo());
		e.setConference(equipoDefault.getConference());
		e.setDivision(equipoDefault.getDivision());
		e.setIdEquipoOriginal(equipoDefault.getIdEquipo());
		e.setPresupuestoProximaTemporada(Constants.cDefaultPresupuestoProximaTemporada);

		equipodao.create(e);
		addTeamToCompetition(e,idCompeticion);
		this.finanzasservice.setPaqueteIngresos(e, 1);		
									
		return e;
		
	}	
	
	
	public List<Temporada> getSeasonsRemaining(long idCompeticion) throws InstanceNotFoundException{
		Competicion com = competiciondao.find(idCompeticion);
		List<Temporada> temporadasRemaining = new ArrayList<Temporada>();
		
		Iterator it = com.getListaTemporadas().iterator();		
		Temporada t = (Temporada)it.next();

		
			
		while (t.getIdTemporada()!=com.getIdTemporadaActual()) {
			t = (Temporada)it.next(); 
		}
		while (it.hasNext()){	
			temporadasRemaining.add(t);
			t = (Temporada)it.next();
		}
		if (t!=null) 
			temporadasRemaining.add(t);
		
		return temporadasRemaining;
		
		
	}	

	
	public boolean AutoDraftPlayers(long idCompeticion) throws InstanceNotFoundException{
		Competicion com = competiciondao.find(idCompeticion);	
			
			Iterator<Jugador> jugadores = jugadordao.getPlayersOrderedByRate(idCompeticion,0,com.getListaEquipos().size()*Constants.cMaxPlayersByTeam).iterator(); 
			
				
				for (int x = 0; x< Constants.cMaxPlayersByTeam;x++){		
					for (int i =0 ;i<com.getListaEquipos().size();i++){
						
						if (jugadores.hasNext()){
							Jugador j = jugadores.next();
							
							//Asignamos minutos por defecto en la rotacion:
							if (x<5)
								j.setMinutos(Constants.cMaxMinutesForPlayerByGame);
							else if (x<10)
								j.setMinutos(Constants.cTotalMinutesOfGame-Constants.cMaxMinutesForPlayerByGame);
							
							
							
							long idJugador = j.getIdJugador();
							Contrato c;
							try {
								c = contractservice.generateDefaultContract(com.getListaEquipos().get(i).getIdEquipo(), idJugador );
								
								if (c!=null)
									contractservice.signContract(idJugador, c.getIdContrato());

								
							} catch (Exception e) {
								
								return false;
							}
							
						}
						
					}
				}				
				
		return true;
	}
	
	@Transactional(readOnly = true)	
	public List<Equipo> getAvailableTeams(long idCompeticion) throws InstanceNotFoundException {
		Competicion com = competiciondao.find(idCompeticion);
		List<Equipo> availableTeams = new ArrayList<Equipo>();
		
		Iterator<Equipo> it = com.getListaEquipos().iterator();
		
		while (it.hasNext()){
			Equipo e = (Equipo) it.next();
			if (e.getUsuario() == null)
				availableTeams.add(e);
		}
		return availableTeams;
			
		
	}
	
	@Transactional(readOnly = true)
	@Override
	public List<EquipoDefault> getDefaultTeams() throws InstanceNotFoundException {
		
		return equipodao.selectEquiposDefaultByConferenceAndDivision(null, null);			
		
	}
	
	@Transactional(readOnly = true)
	@Override
	public List<EquipoDefault> getDefaultRemainingTeams(long idCompeticion) throws InstanceNotFoundException {
		
		List<EquipoDefault> equiposDefault = equipodao.selectEquiposDefaultByConferenceAndDivision(null, null);
		Competicion c = competiciondao.find(idCompeticion);
		List<Equipo> listaEquiposLiga = c.getListaEquipos();
		List<EquipoDefault> equiposRemaining = new ArrayList<EquipoDefault>();
		
		for (EquipoDefault equipoDefault : equiposDefault){
			if ((listaEquiposLiga.isEmpty())){
				equiposRemaining.add(equipoDefault);
			}
			else{
				boolean isElegido = false;
				for(Equipo equipo : listaEquiposLiga){
					if (equipoDefault.getnombre().equals(equipo.getnombre())){
						isElegido = true;
						break;
					}					
				}
				
				if (!isElegido){
					 equiposRemaining.add(equipoDefault);
				}
			}
		}
		
		
		return equiposRemaining;
	}
	
	
	
	public boolean setCompetitionState(Competicion com, int estado){
		
		boolean success=false;
		
		com.setEstado(estado);
		competiciondao.save(com);
		success=true;
		
		return success;
		
	}
	@Override
	@Transactional
	public boolean actualizarEstadoCompeticionSegunCalendario(long idCompeticion) throws InstanceNotFoundException{
		boolean result = false;
		Competicion c = this.competiciondao.find(idCompeticion);
		final Date actualDate = CommonFunctions.getStartOfDay(c.getActualDate());
		final Date startDate = CommonFunctions.getStartOfDay(c.getStartDate());
		final Date finishDate = CommonFunctions.getStartOfDay(c.getFinishDate());
		
		//Si la competicion aun no ha empezado estamos en modo Draft
		if (actualDate.compareTo(startDate)<0){
			if (!Constants.cTipoEstadoCompeticionDraft.equals(c.getTipoEstadoCompeticion().getIdTipoEstadoCompeticion())){
				final TipoEstadoCompeticion estadoCompeticion = new TipoEstadoCompeticion();
				estadoCompeticion.setIdTipoEstadoCompeticion(Constants.cTipoEstadoCompeticionDraft);
				c.setTipoEstadoCompeticion(estadoCompeticion);
				c.setMercadoAbierto(true);
			}
		}
		//Si ya ha empezado la liga:
		else {
			
			if ( actualDate.equals(startDate)){
				this.playerservice.insertJugadoresCompeticionFromDefault(c.getIdCompeticion());
				
				for (Equipo equipoCompeticion : c.getListaEquipos()){
					this.finanzasservice.ingresarParteFijaPrincipioTemporada(equipoCompeticion);
				}
				
			}
			
			if ((c.getFechaComienzoRS()!=null) && (actualDate.compareTo(c.getFechaComienzoRS())<0)){
				final TipoEstadoCompeticion estadoCompeticion = new TipoEstadoCompeticion();
				estadoCompeticion.setIdTipoEstadoCompeticion(Constants.cTipoEstadoCompeticionPretemporada);
				c.setTipoEstadoCompeticion(estadoCompeticion);						
			}
			else if ((c.getFechaComienzoRS()!=null) && (actualDate.compareTo(c.getFechaComienzoRS())>=0)){
				final TipoEstadoCompeticion estadoCompeticion = new TipoEstadoCompeticion();
				estadoCompeticion.setIdTipoEstadoCompeticion(Constants.cTipoEstadoCompeticionRS);
				c.setTipoEstadoCompeticion(estadoCompeticion);			
			}
			if ((c.getFechaComienzoPO()!=null) && (actualDate.compareTo(c.getFechaComienzoPO())>=0)){
				
				if (actualDate.compareTo(c.getFechaComienzoPO())==0){
					simularPartidosPendientes(c);
					
				}
				
				final TipoEstadoCompeticion estadoCompeticion = new TipoEstadoCompeticion();
				estadoCompeticion.setIdTipoEstadoCompeticion(Constants.cTipoEstadoCompeticionPlayOffs);
				c.setTipoEstadoCompeticion(estadoCompeticion);
			}
			
			if (actualDate.compareTo(finishDate)==0){
				for (Equipo equipoCompeticion : c.getListaEquipos()){
					this.finanzasservice.ingresarObjetivosPlayOffs(equipoCompeticion);
				}				
			}
			
			if (c.getFechaCierreMercado()!=null){
				c.setMercadoAbierto((actualDate.compareTo(c.getFechaCierreMercado())<=0));
			}

			if (actualDate.compareTo(c.getOffSeasonStartDate())==0){
				this.finanzasservice.hacerBalanceTemporadaCompeticion(c);
			}
			
			if ((c.getOffSeasonStartDate()!=null) && (actualDate.compareTo(c.getOffSeasonStartDate())>=0) && (c.getOffSeasonFinishDate()!=null) && (actualDate.compareTo(c.getOffSeasonFinishDate())<0)){
				final TipoEstadoCompeticion estadoCompeticion = new TipoEstadoCompeticion();
				estadoCompeticion.setIdTipoEstadoCompeticion(Constants.cTipoEstadoCompeticionPostTemporada);
				c.setTipoEstadoCompeticion(estadoCompeticion);
				c.setMercadoAbierto(true);
			}			
			
		}
		
		competiciondao.save(c);

		
		return result;
	}
	
	private void simularPartidosPendientes(Competicion c){
		
		for (Equipo e : c.getListaEquipos()){
			List<Partido> listaPartidos = this.matchservice.getPartidosPendientesEquipo(e.getIdEquipo());
			
			for(Partido p : listaPartidos){
				if ( CollectionUtils.isNotEmpty(p.getActasPartido())){
					if (p.getActasPartido().size()==0){
						//Se deja sin jugar
					}
					else if (p.getActasPartido().size()==1){
						p.setValidado(true);
						this.partidodao.update(p);
						this.finanzasservice.ingresarPartido(p.getActasPartido().get(0));						
					}
				}
			}
			this.finanzasservice.ingresarObjetivosTemporadaRegular(e);	
			
		} 
		
		
	}

	
	
}