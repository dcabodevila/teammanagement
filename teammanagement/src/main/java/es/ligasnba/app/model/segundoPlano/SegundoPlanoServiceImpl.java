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
	private tradeService tradeService;
	@Autowired
	private matchService matchservice;
	@Autowired
	private seasonService seasonservice;
	@Autowired
	private EquipoDao equipoDao;
	@Autowired
	private ContratoDao contratoDao;
	@Autowired
	private finanzasService finanzasservice;
	@Autowired
	private PartidoDao partidodao;
	@Autowired
	private JugadorDao jugadordao;

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
	public void setFinanzasService(finanzasService finanzasservice) {
		this.finanzasservice = finanzasservice;
	}
	public void setPartidoDao(PartidoDao partidodao) {
		this.partidodao = partidodao;
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

						this.tomarDecisionContrato(jugador.getIdJugador());
					}
				}
				
				this.playerservice.updateJugadoresCompeticionFromDefault(competicion.getIdCompeticion());
				this.playerservice.updateCacheAgentesLibres(competicion.getIdCompeticion());
				actualizarPresupuestoEquipos(competicion);
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
	@Transactional
	private void actualizarPresupuestoEquipos(Competicion c){
		if (CollectionUtils.isNotEmpty(c.getListaEquipos())){
			for (Equipo e : c.getListaEquipos()){
				this.finanzasservice.actualizarPresupuestoEquipo(e, true);
				this.finanzasservice.actualizarPresupuestoEquipo(e, false);
			}
		}
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

	@Transactional
	public Contrato tomarDecisionContrato(Long idJugador) throws Exception {

		Jugador j = this.jugadordao.find(idJugador);
		
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
					return (daysLeft>=1);						


				}				
			}
		}		
		return false;
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

	@Transactional(readOnly = true)
	private List<ResultadoValoracionContratoDto> valoracionOfertasContrato(List<Contrato> listaOfertasContrato) throws InstanceNotFoundException {

		List<ResultadoValoracionContratoDto> listaResultado = new ArrayList<ResultadoValoracionContratoDto>();
		
		if (CollectionUtils.isNotEmpty(listaOfertasContrato)) {
			
			final Competicion competicion = listaOfertasContrato.get(0).getJugador().getCompeticion();
			int maxSeasons = competitionservice.getSeasonsRemaining(competicion.getIdCompeticion()).size();
			
			if (competicion.getActualDate().compareTo(competicion.getFechaCierreMercado())>0){
				maxSeasons = maxSeasons -1;
			}
			
			for (Contrato ofertaContrato : listaOfertasContrato) {
				ResultadoValidacionContratoOfrecidoDto resultadoValidacion = contractservice.isValidOfertaContrato(ofertaContrato.getEquipo().getIdEquipo(), ofertaContrato);
				if (resultadoValidacion.isValido()){

					ResultadoValoracionContratoDto resultado = new ResultadoValoracionContratoDto(ofertaContrato);
	
					resultado.setValoracionMoney(obtenerValoracionDinero(ofertaContrato, maxSeasons));
					
					resultado.setValoracionLoyalty(obtenerValoracionLoyalty(ofertaContrato));
					
					resultado.setValoracionWinning(obtenerValoracionEquipo(ofertaContrato));										
					
					resultado = (calcularValoracionGlobal(resultado));
					
					resultado.setNotaExigida(getNotaExigida(resultado.getContrato()));
					
					if ((ofertaContrato.getEquipo().getCompeticion().getTipoEstadoCompeticion().getIdTipoEstadoCompeticion().equals(Constants.cTipoEstadoCompeticionPostTemporada))
						&& (resultado.getValoracionGlobal().compareTo(resultado.getNotaExigida())>=0)){
						resultado.setValoracionMaxima(getNotaMaxima(resultado));
						resultado.setValoracionGlobalPrevia(resultado.getValoracionGlobal());
						if (resultado.getValoracionGlobal().compareTo(resultado.getValoracionMaxima())>0){
							resultado.setValoracionGlobal(resultado.getValoracionMaxima());
						}

					}
					
					final Equipo equipoOferta = resultado.getContrato().getTraspaso()!=null ? resultado.getContrato().getTraspaso().getEquipoDestino() : resultado.getContrato().getEquipo();				
					
					ValoracionOfertaContratoDto valoracion = obtenerValoracionOfertaDto(ofertaContrato, resultado,
							equipoOferta);
					
					this.contractservice.registrarValoracionOferta(valoracion);
					
					this.newsservice.AddNewToUser(equipoOferta, "Oferta de "+equipoOferta.getnombre()+" a " +ofertaContrato.getJugador().getNombre()+". Valoración global: "+ resultado.getValoracionGlobal()+ "/10."
							+ " Valoración salario: "+ resultado.getValoracionMoney() + "/"+resultado.getMoneyInterest() + " Valoración competitiva: "+resultado.getValoracionWinning()+"/"+resultado.getWinningInterest() + 
							" Valoración lealtad: "+ resultado.getValoracionLoyalty()+"/"+resultado.getLoyaltyInterest() + " Nota exigida: "+ resultado.getNotaExigida()+ " Valoración máxima: "+ resultado.getValoracionMaxima()+
							" Valoración global previa: "+ resultado.getValoracionGlobalPrevia(),
							ofertaContrato.getEquipo().getCompeticion().getActualDate());														
					
					listaResultado.add(resultado);
				}
			
				else {
					
					final Equipo equipoOferta = ofertaContrato.getTraspaso()!=null ? ofertaContrato.getTraspaso().getEquipoDestino() : ofertaContrato.getEquipo();
					
					this.newsservice.AddNewToUser(equipoOferta, "La oferta de "+equipoOferta.getnombre()+" sobre "+ofertaContrato.getJugador().getNombre()+" no es válida: "+ resultadoValidacion.getMotivosNoValido(),
							ofertaContrato.getEquipo().getCompeticion().getActualDate());				
				}
			}
		}

		return listaResultado;

	}

	private ValoracionOfertaContratoDto obtenerValoracionOfertaDto(Contrato ofertaContrato,
			ResultadoValoracionContratoDto resultado, final Equipo equipoOferta) {
		ValoracionOfertaContratoDto valoracion = new ValoracionOfertaContratoDto();
		valoracion.setIdContrato(ofertaContrato.getIdContrato());
		valoracion.setIdCompeticion(equipoOferta.getCompeticion().getIdCompeticion());
		valoracion.setIdEquipo(equipoOferta.getIdEquipo());
		valoracion.setNombreEquipo(equipoOferta.getnombre());
		valoracion.setIdJugador(ofertaContrato.getJugador().getIdJugador());
		valoracion.setNombreJugador(ofertaContrato.getJugador().getNombre());
		
		int i = 0;
		for (LineaContrato linea : ofertaContrato.getListLineasContrato()){
			if (i==0){
				valoracion.setSalarioTemporada1(linea.getSalario());
			}
			else if (i==1){
				valoracion.setSalarioTemporada2(linea.getSalario());
			}
			else if (i==2){
				valoracion.setSalarioTemporada3(linea.getSalario());
			}
			i++;
		}
		valoracion.setValoracionMoney(resultado.getValoracionMoney());
		valoracion.setValoracionWinning(resultado.getValoracionWinning());
		valoracion.setValoracionLoyalty(resultado.getValoracionLoyalty());
		valoracion.setMoneyInterest(resultado.getMoneyInterest());
		valoracion.setWinningInterest(resultado.getWinningInterest());
		valoracion.setLoyaltyInterest(resultado.getLoyaltyInterest());
		valoracion.setValoracionGlobal(resultado.getValoracionGlobal());
		valoracion.setValoracionGlobalExigida(resultado.getNotaExigida());
		valoracion.setValoracionMaxima(resultado.getValoracionMaxima());
		valoracion.setValoracionGlobalPrevia(resultado.getValoracionGlobalPrevia());
		valoracion.setEsSignAndTrade(ofertaContrato.getTraspaso()!=null);
		valoracion.setFecha(equipoOferta.getCompeticion().getActualDate());
		return valoracion;
	}
	
	private ResultadoValoracionContratoDto calcularValoracionGlobal(ResultadoValoracionContratoDto resultadoValoracion){
		BigDecimal sumaValoracion = new BigDecimal(0);
		BigDecimal sumaCriteriosInteres = new BigDecimal(0);
		BigDecimal valoracionGlobal = new BigDecimal(0);
		
		final BigDecimal valoracionMoney = resultadoValoracion.getValoracionMoney() != null ? resultadoValoracion.getValoracionMoney() : BigDecimal.ZERO;
		final BigDecimal valoracionWinning = resultadoValoracion.getValoracionWinning() != null ? resultadoValoracion.getValoracionWinning() : BigDecimal.ZERO;
		final BigDecimal valoracionLoyalty = resultadoValoracion.getValoracionLoyalty() != null ? resultadoValoracion.getValoracionLoyalty() : BigDecimal.ZERO;
		
		
		sumaValoracion = sumaValoracion.add(valoracionMoney);
		sumaValoracion = sumaValoracion.add(valoracionWinning);
		sumaValoracion = sumaValoracion.add(valoracionLoyalty);
		
		final Integer moneyInterest = resultadoValoracion.getValoracionMoney()!=null ? resultadoValoracion.getMoneyInterest() : 0;
		final Integer winningInterest = resultadoValoracion.getValoracionWinning()!=null ? resultadoValoracion.getWinningInterest() : 0;
		final Integer loyaltyInterest = resultadoValoracion.getValoracionLoyalty()!=null ? resultadoValoracion.getLoyaltyInterest() : 0;
		
		sumaCriteriosInteres = new BigDecimal(moneyInterest + winningInterest + loyaltyInterest);
		
		if (sumaCriteriosInteres.compareTo(BigDecimal.ZERO)==0){
			resultadoValoracion.setValoracionGlobal(new BigDecimal(-1));
		}
		else {
		
			valoracionGlobal = sumaValoracion.multiply(new BigDecimal(10));
			valoracionGlobal = valoracionGlobal.divide(sumaCriteriosInteres, RoundingMode.HALF_UP);
		}
		
		resultadoValoracion.setValoracionGlobal(valoracionGlobal.setScale(2, RoundingMode.CEILING));
		
		return resultadoValoracion;
		
	}

	@Transactional(readOnly = true)
	private BigDecimal obtenerValoracionDinero(Contrato c, int maxSeasons) {

		BigDecimal result = new BigDecimal(0);
		
		Collections.sort(c.getListLineasContrato());

		if (CollectionUtils.isNotEmpty(c.getListLineasContrato())){
		
			BigDecimal salarioOfrecido = BigDecimal.ZERO;
					
			for (LineaContrato lineaContrato : c.getListLineasContrato()){
				salarioOfrecido = salarioOfrecido.add(lineaContrato.getSalario());
			}
			float moneyInterest = c.getJugador().getMoneyInterest();
			float importanciaAnhosSalario = maxSeasons;
			
			
			salarioOfrecido = salarioOfrecido.divide(new BigDecimal(importanciaAnhosSalario), RoundingMode.HALF_UP);
			BigDecimal notaExigida = getNotaBaseExigida(c);
			notaExigida = notaExigida.add(getIncrementoNotaBaseMedio(c));
			BigDecimal valorCache = notaExigida.multiply(new BigDecimal(moneyInterest)).divide(new BigDecimal(10), RoundingMode.HALF_UP);
			
	
			final BigDecimal operando= salarioOfrecido.multiply(valorCache);	
			
			result = operando.divide(c.getJugador().getCache(), RoundingMode.HALF_UP);
		}
		
		return result.setScale(2, RoundingMode.CEILING);

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
			idUsuario = c.getTraspaso().getEquipoDestino().getUsuario().getIdUsuario();
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
	
	private BigDecimal getNotaMaxima(ResultadoValoracionContratoDto valoracion){
		return new BigDecimal(CommonFunctions.getRandomIniFin(valoracion.getNotaExigida().floatValue(), valoracion.getValoracionGlobal().floatValue()));
	}
	
	private BigDecimal getNotaExigida(Contrato c){
		BigDecimal notaExigida = getNotaBaseExigida(c);
		
		// Si son FA no restringidos
		if (this.contractservice.isContratoTemporadaActual(c)){	
			notaExigida = notaExigida.multiply(new BigDecimal(CommonFunctions.getPorcentajeRandomMas(20)));
		}			

		// Si es temporada actual y estamos en post temporada 
		else if (c.getJugador().getCompeticion().getTipoEstadoCompeticion().getIdTipoEstadoCompeticion().equals(Constants.cTipoEstadoCompeticionPostTemporada)){			
			notaExigida = notaExigida.multiply(new BigDecimal(CommonFunctions.getPorcentajeRandomMas(20)));
		}
		
		//Si no estamos en post temporada, dificultad alta 
		else {
			notaExigida = notaExigida.multiply(new BigDecimal(CommonFunctions.getPorcentajeRandomMas(30)));
		}		
		
		return notaExigida.setScale(2, RoundingMode.CEILING);
		
	}
	
	private BigDecimal getIncrementoNotaBaseMedio(Contrato c){
		// Si son FA no restringidos
		if (this.contractservice.isContratoTemporadaActual(c)){	
			return new BigDecimal (0.5);
		}			

		// Si es temporada actual y estamos en post temporada 
		else if (c.getJugador().getCompeticion().getTipoEstadoCompeticion().getIdTipoEstadoCompeticion().equals(Constants.cTipoEstadoCompeticionPostTemporada)){			
			return new BigDecimal (0.8);
		}
		
		//Si no estamos en post temporada, dificultad alta 
		else {
			return new BigDecimal (1);
		}		
		
	
	}

	private BigDecimal getNotaBaseExigida(Contrato c) {
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
					notaExigida = new BigDecimal(5.7);
				}
				if (diasRestantes<=3){
					notaExigida = new BigDecimal(5);
				}								 
			}			
		}
		
		//Si no estamos en post temporada, dificultad alta 
		else {
			notaExigida = Constants.cDefaultDificultadContratoSeason;			
			final long diasRestantes = Math.max(daysBetween(c.getJugador().getCompeticion().getActualDate(), c.getJugador().getCompeticion().getOffSeasonStartDate()),1);
			
			if (diasRestantes<=4){
				notaExigida = new BigDecimal(6);
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

