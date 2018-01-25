package es.ligasnba.app.model.segundoPlano;

import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Timer;

import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import es.ligasnba.app.model.actapartido.actaPartidoService;
import es.ligasnba.app.model.competicion.Competicion;
import es.ligasnba.app.model.competicion.competitionService;
import es.ligasnba.app.model.contrato.ContractService;
import es.ligasnba.app.model.contrato.Contrato;
import es.ligasnba.app.model.contrato.ContratoDao;
import es.ligasnba.app.model.contrato.ResultadoValidacionContratoOfrecidoDto;
import es.ligasnba.app.model.contrato.ValoracionOfertaContratoDto;
import es.ligasnba.app.model.equipo.Equipo;
import es.ligasnba.app.model.equipo.EquipoDao;
import es.ligasnba.app.model.equipo.teamService;
import es.ligasnba.app.model.finanzas.finanzasService;
import es.ligasnba.app.model.jugador.Jugador;
import es.ligasnba.app.model.jugador.JugadorDao;
import es.ligasnba.app.model.jugador.playerService;
import es.ligasnba.app.model.lineacontrato.LineaContrato;
import es.ligasnba.app.model.noticia.newsService;
import es.ligasnba.app.model.partido.MatchData;
import es.ligasnba.app.model.partido.Partido;
import es.ligasnba.app.model.partido.PartidoDao;
import es.ligasnba.app.model.partido.matchService;
import es.ligasnba.app.model.temporada.Temporada;
import es.ligasnba.app.model.temporada.seasonService;
import es.ligasnba.app.model.traspaso.tradeService;
import es.ligasnba.app.model.util.CommonFunctions;
import es.ligasnba.app.util.Log;
import es.ligasnba.app.util.Scheduler.SyncWorker;
import es.ligasnba.app.util.constants.Constants;
import es.ligasnba.app.util.exceptions.InstanceNotFoundException;

@Service("segundoPlanoService")
public class SegundoPlanoServiceImpl implements SegundoPlanoService {

	@SuppressWarnings("unused")
	@Autowired
	private PlatformTransactionManager transactionManager;
	@Autowired
	private ContractService contractservice;
	@Autowired
	private playerService playerservice;
	@Autowired
	private competitionService competitionservice;
	@Autowired
	private newsService newsservice;

	@Autowired
	private ContratoDao contratoDao;

	@Autowired
	private teamService teamservice;

	private static final Logger logger = Logger.getLogger(SegundoPlanoService.class);
	
	public void settransactionManager(PlatformTransactionManager t) {
		transactionManager = t;
	}

	public void setContractService(ContractService contractservice) {
		this.contractservice = contractservice;
	}

	public void setPlayerService(playerService playerservice) {
		this.playerservice = playerservice;
	}

	public void setCompetitionService(competitionService competitionservice) {
		this.competitionservice = competitionservice;
	}

	public void setNewsService(newsService newsservice) {
		this.newsservice = newsservice;
	}

	public void setContratoDao(ContratoDao contratoDao) {
		this.contratoDao = contratoDao;
	}


	@Override	
	public boolean ejecutarActualizacionCompeticion(long idCompeticion, final boolean forced) throws InstanceNotFoundException {

		Competicion competicion = this.competitionservice.findById(idCompeticion);
		
		logger.info("Ejecutando actualización competición "+ competicion.getIdCompeticion() + " Nombre: "+ competicion.getNombre()+ " Automática: "+ (!forced));
		
		if ((!competicion.isPaused()) || forced) {			
			
			try {
				
				List<Jugador> jugadoresContratosPospuestos = this.playerservice.findJugadoresConContratosPospuestos(competicion.getIdCompeticion());
				if (CollectionUtils.isNotEmpty(jugadoresContratosPospuestos)) {

					for (Jugador jugador : jugadoresContratosPospuestos) {
						logger.info("Jugador con contrato pospuesto: " +jugador.getNombre()+" ID:" +jugador.getIdJugador());
						firmarContratoPospuesto(jugador);
					}
				}
				
				

				List<Jugador> playersWithOffersList = this.playerservice.getAllResigningPlayers(competicion.getIdCompeticion());

				if (CollectionUtils.isNotEmpty(playersWithOffersList)) {

					for (Jugador jugador : playersWithOffersList) {

						this.contractservice.tomarDecisionContrato(jugador.getIdJugador());
					}
				}
				
				this.playerservice.updateJugadoresCompeticionFromDefault(competicion.getIdCompeticion());
				this.playerservice.updateCacheAgentesLibres(competicion.getIdCompeticion());
				this.teamservice.actualizarPresupuestoEquipos(competicion);
			} catch (Exception e) {
				logger.info(e.getMessage());
				return false;
			}
			
			forwardCalendar(competicion, Constants.cNumDaysForwardByRealDay);
			
			
			
		}

		return true;
	}

	@Transactional
	private void firmarContratoPospuesto(Jugador j) throws InstanceNotFoundException{
		
		if (!j.getContrato().isFirmado()){
			final Date fechaActual = CommonFunctions.getStartOfDay(j.getCompeticion().getActualDate());
			final Date fechaFinOffSeason = CommonFunctions.getStartOfDay(j.getCompeticion().getOffSeasonFinishDate());
			
			if (daysBetween(fechaActual, fechaFinOffSeason)<=1){
				ResultadoValidacionContratoOfrecidoDto resultadoValidacion = this.contractservice.isValidOfertaContrato(j.getEquipo().getIdEquipo(), j.getContrato());
				
				if (resultadoValidacion.isValido()){
					j.getContrato().setFirmado(true);
					j.getContrato().setPendiente(false);
					this.contratoDao.update(j.getContrato());
				}
				else {
					
					this.newsservice.AddNewToUser(j.getEquipo(), j.getNombre()+" no ha podido firmar su contrato. Motivos: "+ resultadoValidacion.getMotivosNoValido()+". El jugador ha decidido irse a agentes libres al incumplirse la promesa.", j.getCompeticion().getActualDate());
					final Long idContrato = j.getContrato().getIdContrato();					
					j.setContrato(null);
					j.setEquipo(null);					
					
					this.contractservice.deleteContract(idContrato);
					
				}
				
			}
			
		}
		
	}


	

	

	@Override
	public boolean simulateDays(Competicion com, int numDays) throws Exception {

		return ejecutarActualizacionCompeticion(com.getIdCompeticion(), true);

	}

	public Date forwardCalendar(Competicion com, int numDays) throws InstanceNotFoundException {

		Calendar newDate = Calendar.getInstance();

		newDate.setTime(com.getActualDate());

		newDate.add(Calendar.DATE, numDays);

		this.competitionservice.SetActualDate(com, newDate.getTime());
		
		this.competitionservice.actualizarEstadoCompeticionSegunCalendario(com.getIdCompeticion());

		return newDate.getTime();

	}

	
	private long daysBetween(Date date1, Date date2){
		Calendar fechaStart = Calendar.getInstance();
		fechaStart.setTime(date1);
		
		Calendar fechaEnd = Calendar.getInstance();
		fechaEnd.setTime(date2);
		
        // Get the represented date in milliseconds
        long millis1 = fechaStart.getTimeInMillis();
        long millis2 = fechaEnd.getTimeInMillis();

        // Calculate difference in milliseconds
        long diff = millis2 - millis1;
        
        // Calculate difference in days
        long diffDays = diff / (24 * 60 * 60 * 1000);
        
        return diffDays;
		
	}

	public teamService getTeamservice() {
		return teamservice;
	}

	public void setTeamservice(teamService teamservice) {
		this.teamservice = teamservice;
	}
	

	
	

}

