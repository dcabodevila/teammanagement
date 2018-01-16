package es.ligasnba.app.model.jugador;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;

import es.ligasnba.app.model.competicion.Competicion;
import es.ligasnba.app.model.competicion.CompeticionDao;
import es.ligasnba.app.model.contrato.Contrato;
import es.ligasnba.app.model.contrato.ContratoDao;
import es.ligasnba.app.model.contrato.ContractService;
import es.ligasnba.app.model.equipo.Equipo;
import es.ligasnba.app.model.equipo.EquipoDao;
import es.ligasnba.app.model.finanzas.finanzasService;
import es.ligasnba.app.model.historicoEquipoJugador.HistoricoEquipoJugador;
import es.ligasnba.app.model.historicoEquipoJugador.HistoricoEquipoJugadorDao;
import es.ligasnba.app.model.jugadordefault.JugadorDefault;
import es.ligasnba.app.model.lineacontrato.LineaContrato;
import es.ligasnba.app.model.temporada.Temporada;
import es.ligasnba.app.model.usuario.CustomUserDetailsService;
import es.ligasnba.app.model.util.CommonFunctions;
import es.ligasnba.app.util.constants.Constants;
import es.ligasnba.app.util.exceptions.InstanceNotFoundException;

@Service("playerService")
@Transactional
public class playerServiceImpl implements playerService{
	
@Autowired
private JugadorDao jugadordao;

@Autowired
private EquipoDao equipodao;
@Autowired
private ContratoDao contratodao;
@Autowired
private CompeticionDao competiciondao;
@Autowired
private ContractService contractservice;
@Autowired
private HistoricoEquipoJugadorDao historicoEquipoJugadorDao;
@Autowired
private finanzasService finanzasservice;

@SuppressWarnings("unused")
@Autowired
private PlatformTransactionManager transactionManager;	

public void setJugadorDao(JugadorDao j) {
	jugadordao = j;
}
public void setEquipoDao(EquipoDao equipodao) {
	this.equipodao = equipodao;
}
public void setCompeticionDao(CompeticionDao c) {
	this.competiciondao = c;
}
public void setContractService(ContractService contractservice) {
	this.contractservice = contractservice;
}
public void setFinanzasService(finanzasService finanzasservice) {
	this.setFinanzasservice(finanzasservice);
}

public void settransactionManager(PlatformTransactionManager t) {
	transactionManager = t;
}


public Jugador findById(long id) throws InstanceNotFoundException{
	return jugadordao.find(id);
}
public Jugador findByName(String name) throws InstanceNotFoundException{
	return jugadordao.findByName(name);
}


public Jugador playerRegister(String nombre, int media,int pos1, int pos2, Date fechaNacimiento) throws InstanceNotFoundException{
	Jugador j = null;
	
	j = new Jugador(nombre,media,pos1,pos2,fechaNacimiento);
	jugadordao.create(j);
		
	return j;
}
public void playerRemove(long idPlayer) throws InstanceNotFoundException{

	jugadordao.remove(idPlayer);				
		

}
@Transactional(readOnly=true)
public JugadorBlock findPlayerByTeamId(long teamId,int startindex,int count) throws InstanceNotFoundException{
	
	List<Jugador> plantilla= new ArrayList<Jugador>();
	Equipo e = equipodao.find(teamId);
	
	plantilla = e.getListaJugadores();

	boolean existsMorePlayers = plantilla.size() == (count +1);

	if (existsMorePlayers) {
		plantilla.remove(plantilla.size()-1);
	}
	return new JugadorBlock(plantilla, existsMorePlayers);
}


@Transactional(readOnly = true)
public JugadorBlock getPlantilla(long idTeam,int startindex, int count) throws InstanceNotFoundException{
	List<Jugador> plantilla = jugadordao.findPlayersByTeamId(idTeam, startindex, count);
	
	boolean existsMoreTeams = plantilla.size() == (count +1);

	if (existsMoreTeams) {
		plantilla.remove(plantilla.size()-1);
	}
	return new JugadorBlock(plantilla, existsMoreTeams);
}



public void cancelContract(long idPlayer, long idContract) throws InstanceNotFoundException {
	Jugador j =jugadordao.find(idPlayer);
	Contrato c = contratodao.find(idContract);
	
	j.setContrato(null);
	jugadordao.save(j);
	c.getEquipo().remContrato(c);
	equipodao.save(c.getEquipo());
	
	contratodao.remove(idContract);
	
	
	
}


@Transactional(readOnly=true)
public JugadorBlock getAgentesLibres(long idCompeticion, int startindex, int count) throws InstanceNotFoundException{
		
	
	List<Jugador> agentesLibres = jugadordao.getFreePlayersFromCompetition(idCompeticion,0,(Constants.cMaxPlayersByTeam * Constants.cMaxTeamsInCompetition) );
	
	
	boolean existsMorePlayers = agentesLibres.size() == (count +1);

	if (existsMorePlayers) {
		agentesLibres.remove(agentesLibres.size()-1);
	}
	return new JugadorBlock(agentesLibres, existsMorePlayers);
		
	
}


public void createPlayers(long idCompeticion) throws InstanceNotFoundException{
	
	Competicion c = competiciondao.find(idCompeticion);
	List<JugadorDefault> listaJugadorDefault = findAllDefaultPlayers();
	List<Jugador> jugadoresPersist = new ArrayList<Jugador>();
	for (JugadorDefault jugadorDefault : listaJugadorDefault){
		Jugador j = new Jugador();
		j.setCompeticion(c);
		j.setIdEquipoOriginal(jugadorDefault.getEquipoDefault().getIdEquipo());
		j.setNombre(jugadorDefault.getNombre());
		j.setFechaNacimiento(jugadorDefault.getFechaNacimiento());
		j.setMedia(jugadorDefault.getMedia());
		j.setEquipo(null);
		j.setImagen(jugadorDefault.getImagen());
		j.setMoneyInterest(getMoneyInterest(j.getFechaNacimiento()));
		j.setPosicion1(j.getPosicion1());
		j.setPosicion2(j.getPosicion2());
		jugadoresPersist.add(j);
		this.jugadordao.create(j);
	}
	
	
}

@Transactional(readOnly=true)
private int getMoneyInterest(Date fechaNacimiento){
	
	int moneyInterest = Constants.cDefaultMoneyInterest;
	
	if (fechaNacimiento!=null) { 
		Integer age = CommonFunctions.calcularEdad(fechaNacimiento);
		
		if (age<25)
			moneyInterest = 5;
		
		if ((age>=25) && (age<28))
			moneyInterest = 7;
		if ((age>=28) && (age<=30))
			moneyInterest = 10;
		if ((age>30) && (age<=38))
			moneyInterest = 5;
	}
	
	return moneyInterest;
	
			
}

public boolean setPlayerRotation(List<Jugador> listaJugadores){
	
	//Ojo, la lista que viene como parámetro viene de la vista y solo contiene idJugador y Minutos
	
	for(int i=0;i<listaJugadores.size();i++)
		try {
			
			jugadordao.find(listaJugadores.get(i).getIdJugador()).setMinutos(listaJugadores.get(i).getMinutos());
			
		} catch (InstanceNotFoundException e) {
			return false;
		}

	return true;
		
}
@Transactional(readOnly=true)
public List<Jugador> getAllResigningPlayers(long idCompeticion){
	
	return jugadordao.getJugadoresConOfertasFromCompetition(idCompeticion);
	
}
@Transactional(readOnly=true)
@Override
public List<Jugador> findJugadoresConContratosPospuestos(long idCompeticion){
	
	return jugadordao.findJugadoresConContratosPospuestos(idCompeticion);
	
}


@Transactional(readOnly=true)
@Override
public List<Jugador> findPlayersFromDefaultTeam(int idCompeticion, long idEquipoOriginal){
	return this.jugadordao.findPlayersByDefaultTeam(idCompeticion, idEquipoOriginal);
}

@Transactional(readOnly=true)
@Override
public List<Jugador> findFreePlayersByDefaultTeam(int idEquipo, long idCompeticion){
	return this.jugadordao.findPlayersByDefaultTeam(idEquipo, idCompeticion);
}

@Transactional(readOnly=true)
@Override
public List<JugadorDefault> findAllDefaultPlayers(){
	return this.jugadordao.findAllDefaultPlayers();
}

@Transactional(readOnly=true)
@Override
public List<JugadorDefault> findAllDefaultPlayers(int idEquipo){
	return this.jugadordao.findDefaultPlayersByDefaultTeam(idEquipo);
}
@Transactional(readOnly=true)
@Override
public PlayerData findDefaultPlayerById(long idJugador) throws InstanceNotFoundException{
	return this.jugadordao.findDefaultPlayerById(idJugador);
}

@Transactional(readOnly=true)
@Override
public JugadorDefault findDefaultPlayerByIdNotSigned(long idJugador, long idCompeticion) throws InstanceNotFoundException{
	return this.jugadordao.findDefaultPlayerByIdNotSigned(idJugador, idCompeticion);
}



	@Transactional
	@Override
	public void signDefaultPlayer(long idEquipo, long idJugadorDefault, long idUsuario) throws Exception{
	
		Equipo e = this.equipodao.find(idEquipo);
		
	    if (CustomUserDetailsService.checkUserIsPropietarioEquipo(idEquipo) || CustomUserDetailsService.checkUserIsAdminOfCompetition(e.getCompeticion().getIdCompeticion())){
			JugadorDefault jugadorDefault = findDefaultPlayerByIdNotSigned(idJugadorDefault, e.getCompeticion().getIdCompeticion());
			
			
			Jugador j = crearJugadorDesdeJugadorDefault(e, jugadorDefault);	
			jugadordao.create(j);
			
			Contrato c = this.contractservice.generateDefaultPlayerContract(e, j, jugadorDefault);
			j.setContrato(c);
			
			jugadordao.save(j);
			
			saveHistoricoJugadorEquipo(e, j);
			
	    }
	    else {
	    	throw new Exception("error signDefaultPlayer");
	    }

	}
	
	@Transactional
	@Override
	public void waivePlayer(long idEquipo, long idJugador) throws Exception{
	
		Equipo e = this.equipodao.find(idEquipo);
		
	    if (CustomUserDetailsService.checkUserIsPropietarioEquipo(idEquipo) || CustomUserDetailsService.checkUserIsAdminOfCompetition(e.getCompeticion().getIdCompeticion())){
			
	    	Jugador j = this.jugadordao.find(idJugador);
			
	    	if ((j.getContrato()!=null) && (j.getContrato().isFirmado())){
	    		
	    		if (CollectionUtils.isNotEmpty(j.getContrato().getListLineasContrato())){
	    			    		
		    		for ( LineaContrato linea : j.getContrato().getListLineasContrato()){
		    			
		    			if (Constants.cTipoEstadoCompeticionPostTemporada.equals(e.getCompeticion().getTipoEstadoCompeticion().getIdTipoEstadoCompeticion())){
	    					if (linea.getTemporada().getIdTemporada() != e.getCompeticion().getIdTemporadaActual()){
				    			final BigDecimal importe = BigDecimal.ZERO.subtract(linea.getSalario().multiply(new BigDecimal(0.5)));		    			
			    				this.finanzasservice.nuevoAsiento(j.getEquipo(), linea.getTemporada(), "Despido de "+ j.getNombre(), j.getCompeticion().getActualDate(), importe);		    					    						
	    					}
		    			}
		    			else {
			    			final BigDecimal importe = BigDecimal.ZERO.subtract(linea.getSalario().multiply(new BigDecimal(0.5)));		    			
		    				this.finanzasservice.nuevoAsiento(j.getEquipo(), linea.getTemporada(), "Despido de "+ j.getNombre(), j.getCompeticion().getActualDate(), importe);		    				
		    			}
		    			

		    		
		    		}
	    		}
	    	}
	    	
	    	
	    	
			j.setEquipo(null);
			j.setCompeticion(e.getCompeticion());
			Long idContrato = j.getContrato().getIdContrato();
			j.setContrato(null);
			final BigDecimal minSalary = this.contractservice.getMinSalary(j);
			final BigDecimal cacheMitad = j.getCache().divide(new BigDecimal(2), RoundingMode.HALF_UP);
			final BigDecimal newCache = minSalary.compareTo(cacheMitad)>=0 ? minSalary : cacheMitad;
			j.setCache(newCache);
			this.jugadordao.update(j);
			this.contractservice.deleteContract(idContrato);
			
			
			
			
	    }
	    else {
	    	throw new Exception("error signDefaultPlayer");
	    }

	}
	
	@Override
	@Transactional
	public void insertJugadoresCompeticionFromDefault(long idCompeticion){
		this.jugadordao.insertJugadoresCompeticionFromDefault(idCompeticion);
	}
	
	@Override
	@Transactional
	public void updateJugadoresCompeticionFromDefault(long idCompeticion){
		this.jugadordao.updateJugadoresCompeticionFromDefault(idCompeticion);
	}
	
	
	private Jugador crearJugadorDesdeJugadorDefault(Equipo e, JugadorDefault jugadorDefault) {
		Jugador j = new Jugador();
		j.setNombre(jugadorDefault.getNombre());
		j.setImagen(jugadorDefault.getImagen());
		j.setEquipo(e);
		j.setCompeticion(e.getCompeticion());
		j.setFechaNacimiento(jugadorDefault.getFechaNacimiento());
		j.setMedia(jugadorDefault.getMedia());
		j.setPosicion1(jugadorDefault.getPos1());
		j.setPosicion2(jugadorDefault.getPos2());
		j.setMoneyInterest(jugadorDefault.getMoneyInterest());
		j.setWinningInterest(jugadorDefault.getWinningInterest());
		j.setLoyaltyInterest(jugadorDefault.getLoyaltyInterest());
		j.setJugadorDefault(jugadorDefault);
		j.setCache(jugadorDefault.getCache());
		j.setYearsPro(jugadorDefault.getYearsPro());
		

		if (jugadorDefault.getEquipoDefault()!=null){
			j.setIdEquipoOriginal(jugadorDefault.getEquipoDefault().getIdEquipo());
		}
		return j;
	}
	private void saveHistoricoJugadorEquipo(Equipo e, Jugador j) {
		HistoricoEquipoJugador historicoJugador = new HistoricoEquipoJugador();
		historicoJugador.setJugador(j);
		historicoJugador.setEquipo(e);
		Temporada temporadaActual = null;
		for (Temporada t : e.getCompeticion().getListaTemporadas()){
			if (t.getIdTemporada()==e.getCompeticion().getIdTemporadaActual()){
				temporadaActual = t;
			}
		}
		
		historicoJugador.setTemporada(temporadaActual);
		historicoJugador.setFecha(new Date());			
		historicoEquipoJugadorDao.create(historicoJugador);
	}
	
	@Transactional
	@Override
	public void clearRoster(long idEquipo) throws InstanceNotFoundException{
		List<PlayerData> listaJugadores = findJugadoresByTeam(idEquipo);
		if (listaJugadores!=null){
			for(PlayerData j : listaJugadores){

				if (j.getIdContrato()!=null){
//					this.contratodao.remove(j.getIdContrato());
					removeHistoricoJugadorTemporada(j.getIdJugador());
				}
				this.jugadordao.remove(j.getIdJugador());				
			}
		}
	}
	private void removeHistoricoJugadorTemporada(Long idJugador) throws InstanceNotFoundException {
		Jugador j = this.jugadordao.find(idJugador);
		
		List<HistoricoEquipoJugador> listaHistorico = this.historicoEquipoJugadorDao.findSeasonTeamsByPlayer(j.getIdJugador(), j.getCompeticion().getIdTemporadaActual());
		for (HistoricoEquipoJugador historico : listaHistorico){
			this.historicoEquipoJugadorDao.remove(historico.getIdHistoricoEquipos());
		}
	}
	
	@Transactional(readOnly=true)
	@Override	
	public List<PlayerData> findJugadoresByTeam(long idTeam) throws InstanceNotFoundException{
		return this.jugadordao.findPlayersByTeam(idTeam);
	}
	
	@Transactional(readOnly=true)
	@Override	
	public List<PlayerDataNextSeason> findJugadoresByTeamNextSeason(long idTeam) throws InstanceNotFoundException{
		return this.jugadordao.findPlayersByTeamNextSeason(idTeam);
	}
	
	@Transactional(readOnly=true)
	@Override
	public List<PlayerData> findAllDefaultPlayersNotSignedByName(long idCompeticion, String name){
		if (name==null) {
			name = "";
		}
		
		return this.jugadordao.findAllDefaultPlayersNotSignedByName(idCompeticion, StringUtils.trimAllWhitespace(name));
	
	}
	public HistoricoEquipoJugadorDao getHistoricoEquipoJugadorDao() {
		return historicoEquipoJugadorDao;
	}
	public void setHistoricoEquipoJugadorDao(HistoricoEquipoJugadorDao historicoEquipoJugadorDao) {
		this.historicoEquipoJugadorDao = historicoEquipoJugadorDao;
	}
	
	@Transactional(readOnly=true)
	@Override
	public List<Jugador> findFaNextSeason(long idCompeticion){
		return this.jugadordao.findFaNextSeason(idCompeticion);
	}
	
	@Transactional(readOnly=true)
	@Override
	public List<Jugador> findJugadoresByIds(List<Long> idsJugador){
		return this.jugadordao.findJugadoresByIds(idsJugador);
	}

	@Transactional(readOnly=true)
	@Override
	public PlayerData findPlayerDataById(long idJugador) throws InstanceNotFoundException{
		return this.jugadordao.findPlayerDataById(idJugador);
	}

	@Transactional(readOnly=true)
	@Override
	public PlayerData findFreeAgentDataById(long idJugador) throws InstanceNotFoundException{
		return this.jugadordao.findFreeAgentDataById(idJugador);
	}
	
	@Transactional(readOnly=true)
	@Override
	public List<PlayerData> findFreeAgentsByCompetition(long idCompeticion, String name){
		if (name==null) {
			name = "";
		}
		
		return this.jugadordao.findFreeAgentsByCompetition(idCompeticion, StringUtils.trimAllWhitespace(name));
	
	}
	public finanzasService getFinanzasservice() {
		return finanzasservice;
	}
	public void setFinanzasservice(finanzasService finanzasservice) {
		this.finanzasservice = finanzasservice;
	}

	@Override
	public void updateCacheAgentesLibres(long idCompeticion){
		this.jugadordao.updateCacheAgentesLibres(idCompeticion);
	}
}
