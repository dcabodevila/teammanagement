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
import es.ligasnba.app.model.equipo.Equipo;
import es.ligasnba.app.model.equipo.EquipoDao;
import es.ligasnba.app.model.jugador.Jugador;
import es.ligasnba.app.model.jugador.playerService;
import es.ligasnba.app.model.lineacontrato.LineaContrato;
import es.ligasnba.app.model.noticia.newsService;
import es.ligasnba.app.model.partido.Partido;
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
@Transactional
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
	private tradeService tradeService;
	@Autowired
	private matchService matchservice;
	@Autowired
	private seasonService seasonservice;
	@Autowired
	private EquipoDao equipoDao;
	@Autowired
	private ContratoDao contratoDao;

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
	public void setTradeService(tradeService tradeService) {
		this.tradeService = tradeService;
	}
	public void setMatchService(matchService matchservice) {
		this.matchservice = matchservice;
	}
	public void setSeasonService(seasonService seasonservice) {
		this.seasonservice = seasonservice;
	}
	public void setEquipoDao(EquipoDao equipoDao) {
		this.equipoDao = equipoDao;
	}
	public void setContratoDao(ContratoDao contratoDao) {
		this.contratoDao = contratoDao;
	}
	

	@Override
	@Transactional
	public boolean ejecutarActualizacionCompeticion(Competicion competicion, final boolean forced) {

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

						this.tomarDecisionContrato(jugador);
					}
				}
				
				this.playerservice.updateJugadoresCompeticionFromDefault(competicion.getIdCompeticion());
			} catch (Exception e) {
				logger.info(e.getMessage());
				return false;
			}
			
			forwardCalendar(competicion, Constants.cNumDaysForwardByRealDay);
			logger.info("Fecha siguiente: " +competicion.getActualDate());
			logger.info("Estado competición: " +competicion.getTipoEstadoCompeticion().getNombre());
		}

		return true;
	}
	
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

	public Contrato tomarDecisionContrato(Jugador j) throws Exception {

		List<Contrato> listaofertas = j.getListaOfertasContrato();

		Contrato contratoelegido = new Contrato();

		if (listaofertas.size() > 0) {

			ResultadoValoracionContratoDto valoracionElegida = elegirOfertaContrato(j);
			
			contratoelegido = valoracionElegida.getContrato();

		}

		if (contratoelegido == null) {
			for (int i = 0; i < j.getListaOfertasContrato().size(); i++) {
				Equipo e = j.getListaOfertasContrato().get(i).getEquipo();
				newsservice.AddNewToUser(e, j.getNombre() + " ha rechazado la oferta propuesta.",
						e.getCompeticion().getActualDate());
			}
			j.getListaOfertasContrato().clear();
			return null;

		} else {

			// Si el contrato se firma con el equipo en el que ya está el
			// jugador (y no es S&T)
					
			if (contratoelegido.getTraspaso() == null){
						
				if ((j.getEquipo() != null) && (j.getContrato() != null)
						&& (contratoelegido.getEquipo().getIdEquipo() == j.getEquipo().getIdEquipo())) {
					
					final boolean isPendiente = postponerFirmaContrato(contratoelegido);					
					this.contractservice.anadeLineasContrato(contratoelegido, isPendiente);
						
					if (isPendiente){
						final Temporada tSiguiente = this.seasonservice.getTemporadaSiguienteCompeticion(contratoelegido.getJugador().getCompeticion());
						
						BigDecimal salario = null;
						
						for (LineaContrato lc : contratoelegido.getListLineasContrato()){
							if (lc.getTemporada().getIdTemporada()==tSiguiente.getIdTemporada()){
								salario = lc.getSalario();		
							}
						}
					 						
						newsservice.AddNewToUser(contratoelegido.getEquipo(), j.getNombre() + " ha aceptado tu oferta de renovación. Además, postpondrá la firma de su contrato para que puedas fichar a jugadores hasta el límite salarial. Vigila no pasarte del presupuesto para poder pagarle el salario de "+salario.toString()+" o se irá a agentes libres.",
								j.getCompeticion().getActualDate());												
					}
					else {
						newsservice.AddNewToUser(contratoelegido.getEquipo(), j.getNombre() + " ha renovado su contrato.",
								j.getCompeticion().getActualDate());						
					}
					
					
					
				}			
				// Si no tiene equipo, firma un contrato para la presente temporada
				// en adelante
				else {
					this.contractservice.firmaContrato(contratoelegido);
					
					newsservice.AddNewToUser(contratoelegido.getEquipo(), j.getNombre() + " ha firmado un contrato con " + contratoelegido.getEquipo().getnombre() ,
							j.getCompeticion().getActualDate());
					
	
				}
				
				if (contratoelegido.isUseMidLevelException()){
					contratoelegido.getEquipo().setMidLevelExceptionUsed(true);
					this.equipoDao.update(contratoelegido.getEquipo());					
				}

			}
			// Si es S&T
			else if ((contratoelegido.getTraspaso() != null) && (contratoelegido.getTraspaso().isAprobado())) {		

				if (this.tradeService.isValidTradeTomarDecisionContrato(contratoelegido.getTraspaso().getIdTraspaso()).getSuccess()){
					
					this.contractservice.anadeLineasContrato(contratoelegido, false);								
					this.tradeService.acceptTradeOffer(contratoelegido.getTraspaso().getIdTraspaso(), true);

					if (contratoelegido.isUseMidLevelException()){
						contratoelegido.getTraspaso().getEquipoDestino().setMidLevelExceptionUsed(true);
						this.equipoDao.update(contratoelegido.getTraspaso().getEquipoDestino());					
					}
					
					newsservice.AddNewToUser(contratoelegido.getTraspaso().getEquipoOrigen(),
							j.getNombre() + " ha aceptado la oferta de Sign And Trade.", j.getCompeticion().getActualDate());

					
					newsservice.AddNewToUser(contratoelegido.getTraspaso().getEquipoDestino(),
							j.getNombre() + " ha aceptado la oferta de Sign And Trade.", j.getCompeticion().getActualDate());
					
				}
				else {
					newsservice.AddNewToUser(contratoelegido.getTraspaso().getEquipoDestino(),
							j.getNombre() + " la oferta de S&T no se ha aceptado porque el traspaso no es posible actualmente", j.getCompeticion().getActualDate());
					newsservice.AddNewToUser(contratoelegido.getTraspaso().getEquipoOrigen(),
							j.getNombre() + " la oferta de S&T no se ha aceptado porque el traspaso no es posible actualmente", j.getCompeticion().getActualDate());				
					
				}

			}			
			
			

			


		}

		// Si el jugador ya estaba en nuestro equipo y no se trata de un
		// contrato S&T es que le renovamos el contrato
		if ((j.getEquipo() != null) && (contratoelegido.getTraspaso() == null)
				&& (contratoelegido.getEquipo().getIdEquipo() == j.getEquipo().getIdEquipo())) {
			j.setEquipo(contratoelegido.getEquipo());

		} else if ((contratoelegido.getTraspaso() != null) && (contratoelegido.getTraspaso().isAprobado())) {		
				
			//

		} else {
			newsservice.AddNewToUser(contratoelegido.getEquipo(), "Has contratado a " + j.getNombre(),
					j.getCompeticion().getActualDate());
			j.setEquipo(contratoelegido.getEquipo());
		}

		j.getListaOfertasContrato().remove(contratoelegido);

		for (Contrato contrato : j.getListaOfertasContrato()) {

			Equipo e = null;

			if (contrato.getTraspaso() == null) {

				e = contrato.getEquipo();

			} else {
				e = contrato.getTraspaso().getEquipoDestino();
			}

			newsservice.AddNewToUser(e, "El jugador " + j.getNombre() + " ha rechazado la oferta.",
					e.getCompeticion().getActualDate());
		}

		j.getListaOfertasContrato().clear();

		return contratoelegido;

	}
	
	private boolean postponerFirmaContrato(Contrato c){
		if (c.getTraspaso()==null){
			
			final Competicion competicion = c.getJugador().getCompeticion();
			final Temporada tSiguiente = this.seasonservice.getTemporadaSiguienteCompeticion(competicion);
			if (tSiguiente!=null){
				final BigDecimal sumaSalarial = this.contractservice.getSumSalaries(c.getEquipo().getIdEquipo(), tSiguiente.getIdTemporada(), true);
				
				if (sumaSalarial.compareTo(competicion.getLimiteSalarial())<0){
					
					final int daysLeft = CommonFunctions.daysBetween(competicion.getActualDate(), competicion.getOffSeasonFinishDate());
					
					if (daysLeft>=1){
						return true;						
					}

				}				
			}
		}		
		return false;
	}
	

	@Override
	public boolean simulateDays(Competicion com, int numDays) throws Exception {

		return ejecutarActualizacionCompeticion(com, true);

	}

	public Date forwardCalendar(Competicion com, int numDays) {

		Calendar newDate = Calendar.getInstance();

		newDate.setTime(com.getActualDate());

		newDate.add(Calendar.DATE, numDays);

		this.competitionservice.SetActualDate(com, newDate.getTime());
		
		this.competitionservice.actualizarEstadoCompeticionSegunCalendario(com);

		return newDate.getTime();

	}

	@Transactional(readOnly = true)
	private List<ResultadoValoracionContratoDto> valoracionOfertasContrato(List<Contrato> listaOfertasContrato) throws InstanceNotFoundException {

		List<ResultadoValoracionContratoDto> listaResultado = new ArrayList<ResultadoValoracionContratoDto>();

		if (CollectionUtils.isNotEmpty(listaOfertasContrato)) {

			for (Contrato ofertaContrato : listaOfertasContrato) {
				
				if (contractservice.isValidOfertaContrato(ofertaContrato.getEquipo().getIdEquipo(), ofertaContrato).isValido()){

					ResultadoValoracionContratoDto resultado = new ResultadoValoracionContratoDto(ofertaContrato);
	
					resultado.setValoracionMoney(obtenerValoracionDinero(ofertaContrato));
					
					resultado.setValoracionLoyalty(obtenerValoracionLoyalty(ofertaContrato));
					
					resultado.setValoracionWinning(obtenerValoracionEquipo(ofertaContrato));
					
					resultado = (calcularValoracionGlobal(resultado));
					String nombreEquipoOferta = resultado.getContrato().getTraspaso()!=null ? resultado.getContrato().getTraspaso().getEquipoDestino().getnombre() : resultado.getContrato().getEquipo().getnombre();
					this.newsservice.AddNewToUser(resultado.getContrato().getEquipo(), "Oferta de "+nombreEquipoOferta+" a " +ofertaContrato.getJugador().getNombre()+". Valoración global: "+ resultado.getValoracionGlobal()+ "/10. Valoración salario: "+ resultado.getValoracionMoney() + "/"+resultado.getMoneyInterest() ,
							ofertaContrato.getEquipo().getCompeticion().getActualDate());														
					
					listaResultado.add(resultado);
				}
			
				else {
					
					this.newsservice.AddNewToUser(ofertaContrato.getEquipo(), "La oferta " +ofertaContrato.getIdContrato()+" sobre "+ofertaContrato.getJugador().getNombre()+" no es válida.",
							ofertaContrato.getEquipo().getCompeticion().getActualDate());				
				}
			}
		}

		return listaResultado;

	}
	
	private ResultadoValoracionContratoDto calcularValoracionGlobal(ResultadoValoracionContratoDto resultadoValoracion){
		BigDecimal sumaValoracion = new BigDecimal(0);
		BigDecimal sumaCriteriosInteres = new BigDecimal(0);
		BigDecimal valoracionGlobal = new BigDecimal(0);
		
		sumaValoracion = sumaValoracion.add(resultadoValoracion.getValoracionMoney());
		sumaValoracion = sumaValoracion.add(resultadoValoracion.getValoracionWinning());
		sumaValoracion = sumaValoracion.add(resultadoValoracion.getValoracionLoyalty());
		
		sumaCriteriosInteres = new BigDecimal(resultadoValoracion.getMoneyInterest()+ resultadoValoracion.getWinningInterest().intValue() + resultadoValoracion.getLoyaltyInterest().intValue());
		
		valoracionGlobal = sumaValoracion.multiply(new BigDecimal(10));
		valoracionGlobal = valoracionGlobal.divide(sumaCriteriosInteres, RoundingMode.HALF_UP);
		
		resultadoValoracion.setValoracionGlobal(valoracionGlobal);
		
		return resultadoValoracion;
		
	}

	@Transactional(readOnly = true)
	private BigDecimal obtenerValoracionDinero(Contrato c) {

		BigDecimal result = new BigDecimal(0);
		
		Collections.sort(c.getListLineasContrato());

		if (CollectionUtils.isNotEmpty(c.getListLineasContrato())){
		
			// En principio sólo se valorará la oferta del primer año, más adelante
			// veremos		
			LineaContrato lineaContratoOferta = c.getListLineasContrato().get(0);
				
	
			final float moneyInterest = c.getJugador().getMoneyInterest();
			final float valorCache = moneyInterest / 2;
			final BigDecimal salarioOfrecido = lineaContratoOferta.getSalario();
	
			final BigDecimal operando= salarioOfrecido.multiply(new BigDecimal(valorCache));	
			
			result = operando.divide(c.getJugador().getCache(), RoundingMode.HALF_UP);
		}
		
		return result;

	}
	
	@Transactional(readOnly = true)
	private BigDecimal obtenerValoracionEquipo(Contrato c){
		Long idEquipo = null;
		if (c.getTraspaso()==null){
			idEquipo = c.getEquipo().getIdEquipo();
		}
		else {
			idEquipo = c.getTraspaso().getEquipoDestino().getIdEquipo();
		}
		
		return this.matchservice.getValoracionEquipo(idEquipo, c.getJugador().getWinningInterest());
	}
	
	@Transactional(readOnly = true)
	private BigDecimal obtenerValoracionLoyalty(Contrato c){
		Long idUsuario = null;
		if ((c.getTraspaso()==null) && (c.getEquipo().getUsuario()!=null)){
			idUsuario = c.getEquipo().getUsuario().getIdUsuario();
		}
		else if (c.getTraspaso().getEquipoDestino().getUsuario()!=null){
			idUsuario = c.getTraspaso().getEquipoDestino().getIdEquipo();
		}
				
		return this.matchservice.getValoracionLoyaltyUsuario(idUsuario, c.getJugador().getLoyaltyInterest());
		
		
	}

	private ResultadoValoracionContratoDto elegirOfertaContrato(Jugador j) throws InstanceNotFoundException {
		
		if (CollectionUtils.isNotEmpty(j.getListaOfertasContrato())){
			List<ResultadoValoracionContratoDto> listaValoraciones = valoracionOfertasContrato(j.getListaOfertasContrato());
			Collections.sort(listaValoraciones, Collections.reverseOrder());
			
			for (ResultadoValoracionContratoDto mejorValoracion : listaValoraciones){
					
				if ((mejorValoracion.getContrato().getTraspaso()!=null)){
					
					if (this.tradeService.isValidTradeTomarDecisionContrato(mejorValoracion.getContrato().getTraspaso().getIdTraspaso()).getSuccess()){
				
						mejorValoracion.setNotaExigida(getNotaExigida(mejorValoracion.getContrato()));
									
						if (mejorValoracion.getValoracionGlobal().compareTo(mejorValoracion.getNotaExigida())>0){
							
							this.newsservice.AddNewToUser(mejorValoracion.getContrato().getEquipo(),"La oferta realizada a " +j.getNombre()+" ha sido aceptada. La valoración del jugador sobre la oferta es de "+ mejorValoracion.getValoracionGlobal()+ ". La valoración exigida es de "+mejorValoracion.getNotaExigida(),
							mejorValoracion.getContrato().getEquipo().getCompeticion().getActualDate());
							return mejorValoracion;
							
						}
					}
					else {
						this.newsservice.AddNewToUser(mejorValoracion.getContrato().getEquipo(),  "El traspaso ofrecido en S&T por " +j.getNombre()+ " no es válido.",
								mejorValoracion.getContrato().getEquipo().getCompeticion().getActualDate());								
					}
				}
				else {
					//Si es un contrato para la actual temporada o estamos en post temporada, dificultad normal
					mejorValoracion.setNotaExigida(getNotaExigida(mejorValoracion.getContrato()));
								
					if (mejorValoracion.getValoracionGlobal().compareTo(mejorValoracion.getNotaExigida())>0){
						this.newsservice.AddNewToUser(mejorValoracion.getContrato().getEquipo(), "La oferta realizada a " +j.getNombre()+" ha sido aceptada. La valoración del jugador sobre la oferta es de "+ mejorValoracion.getValoracionGlobal()+ ". La valoración exigida es de "+mejorValoracion.getNotaExigida(),
						mejorValoracion.getContrato().getEquipo().getCompeticion().getActualDate());							
						return mejorValoracion;
					}						
				}
								
			}

		}
		return new ResultadoValoracionContratoDto(null);
	}
	
	private BigDecimal getNotaExigida(Contrato c){
		BigDecimal notaExigida = new BigDecimal(0);

		// Si son FA no restringidos
		if (this.contractservice.isContratoTemporadaActual(c)){	
			notaExigida = Constants.cDefaultDificultadContratoFA;
		}			

		// Si es temporada actual y estamos en post temporada 
		else if (c.getJugador().getCompeticion().getTipoEstadoCompeticion().getIdTipoEstadoCompeticion().equals(Constants.cTipoEstadoCompeticionPostTemporada)){
			notaExigida = Constants.cDefaultDificultadContratoPostSeason;
			final long diasTotales = Math.max(daysBetween(c.getJugador().getCompeticion().getOffSeasonStartDate(), c.getJugador().getCompeticion().getOffSeasonFinishDate()), 1);
			final long diasRestantes = Math.max(daysBetween(c.getJugador().getCompeticion().getActualDate(), c.getJugador().getCompeticion().getOffSeasonFinishDate()),1);
			
			if (diasRestantes<diasTotales){
				if (diasRestantes<=5){
					notaExigida = new BigDecimal(5.5);
				}
				if (diasRestantes<=3){
					notaExigida = new BigDecimal(5);
				}					
				if (diasRestantes==1){
					notaExigida = new BigDecimal(4.5);
				}					 
			}
		}
		
		//Si no estamos en post temporada, dificultad alta 
		else {
			notaExigida = Constants.cDefaultDificultadContratoSeason;			
			final long diasRestantes = Math.max(daysBetween(c.getJugador().getCompeticion().getActualDate(), c.getJugador().getCompeticion().getOffSeasonStartDate()),1);
			
			if (diasRestantes<=4){
				notaExigida = new BigDecimal(6.5);
			}
			if (diasRestantes<=2){
				notaExigida = new BigDecimal(6.0);
			}
			
		}
		
		return notaExigida;
		
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
	

}

