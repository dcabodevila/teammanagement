package es.ligasnba.app.model.contrato;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.Transactional;

import es.ligasnba.app.model.competicion.Competicion;
import es.ligasnba.app.model.competicion.competitionService;
import es.ligasnba.app.model.equipo.Equipo;
import es.ligasnba.app.model.equipo.EquipoDao;
import es.ligasnba.app.model.historicoEquipoJugador.HistoricoEquipoJugador;
import es.ligasnba.app.model.historicoEquipoJugador.HistoricoEquipoJugadorDao;
import es.ligasnba.app.model.jugador.Jugador;
import es.ligasnba.app.model.jugador.JugadorDao;
import es.ligasnba.app.model.jugador.PlayerContractData;
import es.ligasnba.app.model.jugador.playerService;
import es.ligasnba.app.model.jugadordefault.JugadorDefault;
import es.ligasnba.app.model.lineacontrato.LineaContrato;
import es.ligasnba.app.model.lineacontrato.LineaContratoBlock;
import es.ligasnba.app.model.lineacontrato.LineaContratoDao;
import es.ligasnba.app.model.temporada.Temporada;
import es.ligasnba.app.model.temporada.TemporadaDao;
import es.ligasnba.app.model.temporada.seasonService;
import es.ligasnba.app.model.traspaso.Traspaso;
import es.ligasnba.app.model.traspaso.TraspasoDao;
import es.ligasnba.app.model.traspaso.tradeService;
import es.ligasnba.app.model.util.CommonFunctions;
import es.ligasnba.app.util.constants.Constants;
import es.ligasnba.app.util.exceptions.InstanceNotFoundException;
import es.ligasnba.app.util.jqgrid.CustomGenericResponse;
import es.ligasnba.app.util.constants.DefaultContract;


@Service("contractService")
@Transactional
public class contractServiceImpl implements ContractService{
	@SuppressWarnings("unused")
	@Autowired
	private PlatformTransactionManager transactionManager;
	@Autowired
	private ContratoDao contratodao;
	@Autowired
	private LineaContratoDao lineacontratodao;
	@Autowired 
	private EquipoDao equipodao;
	@Autowired
	private JugadorDao jugadordao;
	@Autowired
	private TemporadaDao temporadadao;
	@Autowired
	private competitionService competitionservice;
	@Autowired
	private HistoricoEquipoJugadorDao historicoEquipoJugadorDao;
	@Autowired
	private tradeService tradeservice;
	@Autowired
	private playerService playerService;
	@Autowired
	private seasonService seasonService;
	@Autowired
	private TraspasoDao traspasoDao;
	
	public void setEquipoDao(EquipoDao e) {
		equipodao = e;
	}
	public void setJugadorDao(JugadorDao jugadordao) {
		this.jugadordao = jugadordao;
	}
	public void settransactionManager(PlatformTransactionManager t) {
		transactionManager = t;
	}
	public void setContratoDao(ContratoDao contratodao) {
		this.contratodao = contratodao;
	}
	public void setLineaContratoDao(LineaContratoDao lineacontratodao) {
		this.lineacontratodao = lineacontratodao;
	}
	public void setTemporadaDao(TemporadaDao temporadadao) {
		this.temporadadao = temporadadao;
	}	
	public void setCompetitionService(competitionService competitionservice) {
		this.competitionservice = competitionservice;
	}
	public void setSeasonService(seasonService seasonService) {
		this.seasonService = seasonService;
	}
	public void setTradeService(tradeService tradeservice) {
		this.tradeservice = tradeservice;
	}
	public void setTraspasoDao(TraspasoDao traspasoDao) {
		this.traspasoDao = traspasoDao;
	}


	
	@Transactional(readOnly=true)	
	public Contrato findById(long id) throws InstanceNotFoundException {
		
		Contrato e = contratodao.find(id);	
		return e;
		
	}
	
	@Override
	public LineaContratoBlock getLineasContrato(long idContrato,int startindex,int count) throws InstanceNotFoundException {

		List<LineaContrato> lineascontrato = contratodao.findLineaContratoByContrato(idContrato, startindex, count);
		
		boolean existsMoreLineContracts = lineascontrato.size() == (Constants.cMaxListItems +1);

		if (existsMoreLineContracts) {
			lineascontrato.remove(lineascontrato.size()-1);
		}
		return new LineaContratoBlock(lineascontrato, existsMoreLineContracts);	
	}
	
	public Contrato createEmptyContract(long idTeam, long idPlayer) throws InstanceNotFoundException{
		
		Contrato c=null;
		
			Equipo e = equipodao.find(idTeam);
			Jugador j = jugadordao.find(idPlayer);
			
			
			c = new Contrato(e,j);
			contratodao.create(c);
			
			e.addContrato(c);

	
		return c;
	}
	
	@Transactional(rollbackFor = Exception.class)
	@Override
	public CustomGenericResponse generateGlobalContractOffer(long idEquipo, long idJugador, long idTemporada, int years, BigDecimal baseSalary, BigDecimal increase, boolean teamOption, boolean playerOption, boolean useMidLevelException, List<Long> listaJugadoresST) throws InstanceNotFoundException, Exception{
		
		CustomGenericResponse response = new CustomGenericResponse();
		response.setSuccess(true);
		
		ResultadoValidacionContratoOfrecidoDto resultado = new ResultadoValidacionContratoOfrecidoDto();
		Contrato contratoCreado = null;		 
		if (CollectionUtils.isEmpty(listaJugadoresST)){
			
			contratoCreado = this.generateContractOffer(idEquipo, idJugador, idTemporada, years,  baseSalary, increase, false, false, useMidLevelException);
			resultado = isValidOfertaContrato(contratoCreado.getEquipo().getIdEquipo(), contratoCreado);
		}
		else {
			
			contratoCreado = this.generateContractOfferSignAndTrade(idEquipo, idJugador, idTemporada, years,  baseSalary, increase, false, false, useMidLevelException, listaJugadoresST);
			if (contratoCreado.getTraspaso()!=null){
				response = this.tradeservice.isValidTradeEnviarTrade(contratoCreado.getTraspaso().getIdTraspaso(), true);
				if (!response.getSuccess()){
					Jugador j = contratoCreado.getJugador();
					j.getListaOfertasContrato().remove(contratoCreado);
					contratoCreado.setJugadorOfrecido(null);
					this.contratodao.update(contratoCreado);
					this.jugadordao.update(j);
					
					response.setSuccess(false);
					return response;
				}
			}
			resultado = isValidOfertaContrato(contratoCreado.getTraspaso().getEquipoDestino().getIdEquipo(), contratoCreado);

			
		}
		
		if (!resultado.isValido()){
			Jugador j = contratoCreado.getJugador();
			j.getListaOfertasContrato().remove(contratoCreado);
			contratoCreado.setJugadorOfrecido(null);
			this.contratodao.update(contratoCreado);
			this.jugadordao.update(j);
			
			response.setSuccess(false);
			response.setMessage(resultado.getMotivosNoValido());
		}
		

		
		if (response.getSuccess()){
			response.setMessage("Oferta enviada correctamente");
		}
		
		return response;
	}	
	
	@Transactional
	@Override
	public Contrato generateContractOffer(long idTeam, long idPlayer, long idTemporada, int years, BigDecimal baseSalary, BigDecimal salaryIncrease, boolean teamOption, boolean playerOption, boolean useMidLevelException) throws InstanceNotFoundException, Exception{
		Jugador j = jugadordao.find(idPlayer);
		Equipo e = equipodao.find(idTeam);
		
		Contrato contract = generateContract(idTeam, idPlayer, idTemporada, years, baseSalary, salaryIncrease, teamOption, playerOption);
		
		contract.setJugadorOfrecido(j);
		contract.setUseMidLevelException(useMidLevelException);
		
		Calendar cal= Calendar.getInstance();
		cal.setTime(e.getCompeticion().getActualDate());
		
		e.setFechaUltimoContrato(cal.getTime());
		
		
		Random r = new Random();
		
		if (j.getCompeticion().getTipoEstadoCompeticion().getIdTipoEstadoCompeticion().equals(Constants.cTipoEstadoCompeticionPostTemporada)){
			cal.add( Calendar.DATE, Constants.cMaxDaysPlayerContractDecisionPostSeason);			
		}
		else {
			cal.add( Calendar.DATE, r.nextInt(Constants.cMaxDaysPlayerContractDecisionRS)+1 );
		}
		
		contract.setFecha( cal.getTime() );	
			
		j.addOfertaContrato(contract);		
		
		jugadordao.save(j);
		contratodao.save(contract);		
			
		return contract;
		
	} 
	
	@Transactional(rollbackFor = Exception.class)
	@Override
	public Contrato generateContractOfferSignAndTrade(long idTeam, long idPlayer, long idTemporada, int years, BigDecimal baseSalary, BigDecimal salaryIncrease, boolean teamOption, boolean playerOption, boolean useMidLevelException, List<Long> idsJugadores) throws InstanceNotFoundException, Exception{
		
		Jugador jugadorOferta = this.playerService.findById(idPlayer);
		
		List<Jugador> jugadoresST = this.playerService.findJugadoresByIds(idsJugadores);	
		Contrato contrato = generateContractOffer(jugadorOferta.getEquipo().getIdEquipo(), idPlayer, idTemporada, years, baseSalary, salaryIncrease, teamOption, playerOption, useMidLevelException);				
			
		Traspaso trade = this.tradeservice.createTradeOffer(jugadorOferta.getEquipo().getIdEquipo(), idTeam, Arrays.asList(jugadorOferta), jugadoresST, "", new BigDecimal(0));
		 
//		for (Jugador j : jugadoresST){
//			j.addOfertaContrato(contrato);
//			jugadordao.save(j);
//		}
		
		contrato.setTraspaso(trade);
		trade.setContrato(contrato);
		contrato.setJugadorOfrecido(jugadorOferta);
		this.contratodao.save(contrato);
		this.traspasoDao.save(trade);		
		
		return contrato;
	}	
	
	public Contrato generateContract(long idTeam, long idPlayer, long idTemporada, int years, BigDecimal baseSalary, BigDecimal salaryIncrease, boolean teamOption, boolean playerOption) throws InstanceNotFoundException, Exception{
		
		Contrato c=null;
		BigDecimal newSalary;
		boolean teamOptionLine ;
		boolean playerOptionLine;
		
		Equipo e = equipodao.find(idTeam);

		teamOptionLine   = false;
		playerOptionLine = false;
		
		newSalary = baseSalary;
		
		
//		try{
				
//			Contrato contratoactual = contratodao.findSignedByPlayerAndTeam(idTeam, idPlayer);
//			
//			
//			
//			if (contratoactual.getListLineasContrato().size()>1)
//				//TODO Hacer función
//				throw new Exception("Los jugadores solo pueden renovar en su último año de contrato");
//	
//
//			if ( years + contratoactual.getListLineasContrato().size() > e.getCompeticion().getListaTemporadas().size())
//				//TODO Hacer función
//				throw new Exception("El contrato ofrecido es superior al de temporadas que quedan de la competición.");
//			
//			Contrato contractOffer = createEmptyContract(idTeam,idPlayer);		
//			for (int i=0;i<years;i++){
//				
//				if (i==years-1) {
//					teamOptionLine = teamOption;
//					playerOptionLine = playerOption;
//				}
//				
//				if (contratoactual.getListLineasContrato().size()>0){
//					List<Temporada> temporadasComp = competitionservice.getSeasonsRemaining(e.getCompeticion().getIdCompeticion());
//									
//					LineaContrato lc = createLineContract(contratoactual.getIdContrato(),temporadasComp.get(i+1).getIdTemporada(), newSalary, teamOptionLine, playerOptionLine);
//					contratoactual.addLineaContrato(lc);
//					newSalary = newSalary.add(newSalary.multiply((salaryIncrease.divide(new BigDecimal("100.00")))));
//				}
//				
//			}
//			contratoactual.setFirmado(false);
//			return contractOffer;
//		}catch(InstanceNotFoundException e1){
			
					
		c = createEmptyContract(idTeam,idPlayer);		
				
		
		List<Temporada> remainingSeasons = competitionservice.getSeasonsRemaining(e.getCompeticion().getIdCompeticion());
		
		
		
		for(int i=0;i<remainingSeasons.size();i++){
			
			Temporada t = remainingSeasons.get(i);
			
			if (t.getIdTemporada() == idTemporada){
				
				
				for(int x=i;x<remainingSeasons.size();x++){
					if (years>0){
						t = remainingSeasons.get(x);						
						createLineContract(c.getIdContrato(), t.getIdTemporada() ,newSalary, teamOptionLine, playerOptionLine);
						newSalary = newSalary.add(newSalary.multiply((salaryIncrease.divide(new BigDecimal("100")))));
						years--;
					}
					
					
				}
				
				
			}
			
		}
				
		return c;
	}
	
	@Override
	public Contrato generateDefaultPlayerContract(Equipo e, Jugador j, JugadorDefault jd) throws InstanceNotFoundException{
		Contrato c = createEmptyContract(e.getIdEquipo(), j.getIdJugador());
		final Competicion com = e.getCompeticion();
		
		final List<Temporada> listaTemporadas = e.getCompeticion().getListaTemporadas();
		Collections.sort(listaTemporadas);
		final List<Temporada> remainingSeasions = new ArrayList<Temporada>();
		for (Temporada t : listaTemporadas){
			if (com.getIdTemporadaActual()<=t.getIdTemporada()){
				remainingSeasions.add(t);
			}
		}
		Collections.sort(remainingSeasions);
		
		int numTemporada = 1;
		for(Temporada t : remainingSeasions){
					
			if ((t.getIdTemporada()>=com.getIdTemporadaActual())&&(numTemporada==1) && (jd.getSalario1()!=null)){
				createLineContract(c.getIdContrato(), e.getCompeticion().getIdTemporadaActual() , jd.getSalario1(), false, false);			
			}
			else if ((t.getIdTemporada()>=com.getIdTemporadaActual()) && (numTemporada==2) && (jd.getSalario2()!=null)){
				createLineContract(c.getIdContrato(), t.getIdTemporada() , jd.getSalario2(), false, false);			
			}
			else if ((t.getIdTemporada()>=com.getIdTemporadaActual()) && (numTemporada==3) && (jd.getSalario3()!=null)){
				createLineContract(c.getIdContrato(), t.getIdTemporada() , jd.getSalario3(), false, false);			
			}
			else if ((t.getIdTemporada()>=com.getIdTemporadaActual()) && (numTemporada==4) && (jd.getSalario4()!=null)){
				createLineContract(c.getIdContrato(), t.getIdTemporada() , jd.getSalario4(), false, false);			
			}
			
			numTemporada++;
			
			
		}
		c.setFirmado(true);
		this.contratodao.save(c);
		return c;
	}
	
	public Contrato generateDefaultContract(long idTeam, long idPlayer) throws InstanceNotFoundException, Exception{
		
		Jugador j = jugadordao.find(idPlayer);
		Equipo e = equipodao.find(idTeam);	
		
		HashMap <String,DefaultContract> defaultcontractsmap = DefaultContract.getDefaultContracts();
		
		Contrato newContrato = null;
		
		DefaultContract defaultcontract = new DefaultContract();  
		
		if (defaultcontractsmap.containsKey(j.getNombre())){
			
			defaultcontract = defaultcontractsmap.get(j.getNombre());
			
			
		}
		
		else {

			defaultcontract = new DefaultContract( Constants.cDefaultContractYears, Constants.cDefaultContractSalary, Constants.cDefaultIncrement, Constants.cDefaultContractTeamOption, Constants.cDefaultContractPlayerOption);
			
		}
		newContrato = generateContract(e.getIdEquipo(), j.getIdJugador(), e.getCompeticion().getIdTemporadaActual() , defaultcontract.getYears(), defaultcontract.getBaseSalary(), defaultcontract.getSalaryIncrease(), defaultcontract.isTeamOption(), defaultcontract.isPlayerOption());
		
		return newContrato;
		
		
					
		
	}
	
	public LineaContrato createLineContract(long idContrato,long idTemporada, BigDecimal salario, boolean teamOption, boolean playerOption) throws InstanceNotFoundException{
		
		Contrato c = contratodao.find(idContrato);
		Temporada t = temporadadao.find(idTemporada);
		
		LineaContrato lc = new LineaContrato(c,t);
		lc.setSalario(salario);
		lc.setOpcionEquipo(teamOption);
		lc.setOpcionJugador(playerOption);
		lineacontratodao.create(lc);
		
		t.getListaLineasContrato().add(lc);
		c.addLineaContrato(lc);
		contratodao.save(c);
		
		
		
		return lc;
		
		
	}
	
	public LineaContrato getLineContractBySeason(long idContrato, long idTemporada) throws InstanceNotFoundException{
		LineaContrato lc = lineacontratodao.findByTemporada(idTemporada, idContrato);
		return lc;
	}
	
	public void removeLineContract(long idContrato, long idTemporada) throws InstanceNotFoundException{
		Contrato c = contratodao.find(idContrato);
		LineaContrato lc = lineacontratodao.findByTemporada(idTemporada, idContrato);
		
		c.getListLineasContrato().remove(lc);
		contratodao.save(c);
		lineacontratodao.remove(idTemporada);
	}
	
	@Transactional(readOnly=true)
	public ContratoBlock findContractsByTeam(long idTeam,int startindex,int count) throws InstanceNotFoundException {
		
		List<Contrato> contratos = contratodao.findByTeamId(idTeam, startindex, count);
		
		boolean existsMoreContracts = contratos.size() == (count +1);

		if (existsMoreContracts) {
			contratos.remove(contratos.size()-1);
		}
		return new ContratoBlock(contratos, existsMoreContracts);			
	}
	
	@Transactional(readOnly=true)
	public ContratoBlock findSignedContractsByTeam(long idTeam,int startindex,int count) throws InstanceNotFoundException {
		
		List<Contrato> contratos = contratodao.findBySignedTeamId(idTeam, startindex, count);
		
		boolean existsMoreContracts = contratos.size() == (count +1);

		if (existsMoreContracts) {
			contratos.remove(contratos.size()-1);
		}
		return new ContratoBlock(contratos, existsMoreContracts);			
	}
	
	@Transactional
	public BigDecimal getSumSalaries(long idEquipo, long idTemporada, boolean pendiente){
		
		return this.contratodao.getSumSalaries(idEquipo, idTemporada, pendiente);
		
	}

	@Override
	public Contrato anadeLineasContrato(Contrato contrato, final boolean isPendiente){
		int years = contrato.getListLineasContrato().size();

		Contrato contratoactual = contrato.getJugador().getContrato();

		// aÃ±adimos las lineas del contrato ofrecido al contrato que ya
		// tiene
		if (contratoactual==null){
			contratoactual = contrato;
		}
		
		for (int i = 0; i < years; i++) {

			if (contratoactual.getListLineasContrato().size() > 0) {

				LineaContrato lc = contrato.getListLineasContrato().get(i);
				contratoactual.addLineaContrato(lc);
				lc.setContrato(contratoactual);
				this.lineacontratodao.save(lc);

			}

		}
		
		contratoactual.setFirmado(!isPendiente);
		contratoactual.setPendiente(isPendiente);
		
		if (isPendiente){			
			contratoactual.setFecha(CommonFunctions.getStartOfDay(contrato.getJugador().getCompeticion().getActualDate()));
		}
		
		this.contratodao.save(contratoactual);
		
		return contratoactual;
		
	}
	
	@Override
	@Transactional
	public Contrato firmaContrato(Contrato nuevoContrato) throws InstanceNotFoundException{

		Contrato contratoActualizado =anadeLineasContrato(nuevoContrato, false);
		
		Jugador j = this.jugadordao.find(contratoActualizado.getJugador().getIdJugador());
		contratoActualizado.setEquipo(nuevoContrato.getEquipo());
		contratoActualizado.getEquipo().addJugador(j);
		contratoActualizado.getEquipo().addContrato(contratoActualizado);
		j.setEquipo(nuevoContrato.getEquipo());		
		j.setContrato(contratoActualizado);

		this.contratodao.save(contratoActualizado);
		this.jugadordao.save(j);
		this.equipodao.save(contratoActualizado.getEquipo());
		
		
		
//		Contrato contratoactual = nuevoContrato.getJugador().getContrato();
//
//		if (contratoactual!=null){
//		
//			for (LineaContrato lineaContrato : nuevoContrato.getListLineasContrato()) {				
//					contratoactual.addLineaContrato(lineaContrato);
//			}
//			
//			contratoactual.getJugador().setEquipo(nuevoContrato.getEquipo());
//			contratoactual.getEquipo().addJugador(nuevoContrato.getJugador());
//			contratoactual.setFirmado(true);
//			
//			for (LineaContrato lineaContrato : contratoactual.getListLineasContrato()){
//				this.lineacontratodao.save(lineaContrato);
//			}
//			this.contratodao.save(contratoactual);
//			this.equipodao.save(contratoactual.getEquipo());
//			this.jugadordao.save(contratoactual.getJugador());
//			
//		}
//		else {
//			return signContract(nuevoContrato.getJugador().getIdJugador(), nuevoContrato.getIdContrato());
//		}
		
		return contratoActualizado;
		
	}
	
	@Override
	public Contrato signContract(long idPlayer, long idContract) throws InstanceNotFoundException {
		Jugador j =jugadordao.find(idPlayer);
		Contrato c = contratodao.find(idContract);
		j.setContrato(c);
		c.setFirmado(true);
		contratodao.save(c);
		
		List<Jugador> plantilla = jugadordao.findPlayersByTeamId(c.getEquipo().getIdEquipo(), 0,Constants.cMaxPlayersByTeam );
						
		if (!plantilla.contains(j)){
		
			c.getEquipo().addJugador(j);
			equipodao.save(c.getEquipo());
		}
		return c; 	
	}
	
	public void removeContract(long idContrato) throws InstanceNotFoundException{
		Contrato c = contratodao.find(idContrato);
		c.getEquipo().getListaContratos().remove(c);
		c.getEquipo().remJugador(c.getJugador());
		c.getJugador().setContrato(null);
		
//		if ( c.getTraspaso()!=null ){
//			traspasoDao.remove(c.getTraspaso().getIdTraspaso());
//		}
		
		equipodao.save(c.getEquipo());		
		jugadordao.save(c.getJugador());
		contratodao.remove(idContrato);
		
	}
	
	@Override
	public void deleteContract(long idContrato) throws InstanceNotFoundException{
		Contrato c = this.contratodao.find(idContrato);
		for (LineaContrato lc : c.getListLineasContrato()){
			this.lineacontratodao.remove(lc.getIdLineaContrato());
		}

		this.contratodao.remove(idContrato);		
	} 
	
	
	@Transactional(readOnly=true)
	public List<Contrato> findExpiringContracts(long idCompeticion){
		
		return contratodao.findExpiringContracts(idCompeticion);	
		
	}
	
	public boolean clearExpiringContracts(List<Contrato> listaContratos){
		
		for (int i=0;i< listaContratos.size();i++){
			
			try {
				contratodao.remove(listaContratos.get(i).getIdContrato());
			} catch (InstanceNotFoundException e) {
				
				return false;
				
			}
			
		}
		return true;
	}
	
	
	@Transactional
	public PlayerContractData getPlayerResignContractData(long idPlayer, long idEquipo) throws InstanceNotFoundException{
		
		Jugador j = jugadordao.find(idPlayer);				
		PlayerContractData contractdata = new PlayerContractData();		
		Equipo e = equipodao.find(idEquipo);

		if (j.getEquipo()==null){
			contractdata = getContractDataAgenteLibreNoRestringido(j, e);
			contractdata.setTitulo("Oferta FA no restringido");
			contractdata.setTipoOferta(Constants.cTipoOfertaOfertaFANoRestringido);
			
			
		} else if ((j.getEquipo().getIdEquipo()==idEquipo)){
			final Temporada temporada = this.seasonService.getTemporadaSiguienteCompeticion(j.getCompeticion());
			contractdata = getContractDataAgenteLibrePropio(j, temporada);
			contractdata.setTitulo("Renovación");
			contractdata.setTipoOferta(Constants.cTipoOfertaRenovacion);
		}
		else {
			contractdata = getContractDataAgente(j, e);
			contractdata.setTitulo("Oferta FA");
			contractdata.setTipoOferta(Constants.cTipoOfertaOfertaFA);
		}
					
		return contractdata;
		
	}
	
	@Transactional(readOnly=true)
	@Override
	public PlayerContractData getContractDataAgenteLibrePropio(Jugador j, Temporada t) throws InstanceNotFoundException{
		PlayerContractData contractdata = new PlayerContractData();
		contractdata.setImagen(j.getImagen());
		contractdata.setIdJugador(j.getIdJugador());
		contractdata.setNombre(j.getNombre());

		contractdata.setMinSalary(getMinSalary(j));
		
		//Salario
		BigDecimal maxSalary = new BigDecimal(0);
		BigDecimal sumaSalarial = new BigDecimal(0);
		//Info económica
		final BigDecimal salaryTop = getSalaryTop(j);
		Temporada temporadaSiguiente = this.seasonService.getTemporadaSiguienteCompeticion(j.getCompeticion());		
		Temporada temporadaActual = this.seasonService.getTemporadaActualCompeticion(j.getCompeticion());
		
		if (temporadaActual.getIdTemporada()==t.getIdTemporada()){
			sumaSalarial = getSumaSalarialTemporada(j.getEquipo(), j.getCompeticion().getIdTemporadaActual(), true);
			
			final BigDecimal multaLuxury = getMultaLuxuryTax(sumaSalarial, j.getCompeticion().getLimiteTope());			
			sumaSalarial = sumaSalarial.add(multaLuxury);
			
			contractdata.setPresupuestoTotal(j.getEquipo().getPresupuestoActual());
			BigDecimal presupuestoRestante = getPresupuestoRestante(j.getCompeticion().getLimiteTope(), j.getEquipo().getPresupuestoProximaTemporada(), sumaSalarial);
			contractdata.setPresupuestoRestante(presupuestoRestante);
			maxSalary = presupuestoRestante;
			//Nº temporadas
			int maxSeasons = competitionservice.getSeasonsRemaining(j.getCompeticion().getIdCompeticion()).size();
			contractdata.setMaxSeasons( maxSeasons );						
		}else {
			Long idTemporada = temporadaSiguiente!=null ? temporadaSiguiente.getIdTemporada() : null;
			
			final boolean isContractDataJugadorPendienteFirma = j.getContrato()!=null ? j.getContrato().isPendiente() : false;
			
			sumaSalarial = getSumaSalarialTemporada(j.getEquipo(), idTemporada, !isContractDataJugadorPendienteFirma);
			
			final BigDecimal multaLuxury = getMultaLuxuryTax(sumaSalarial, j.getCompeticion().getLimiteTope());
			sumaSalarial = sumaSalarial.add(multaLuxury);
			
			contractdata.setPresupuestoTotal(j.getEquipo().getPresupuestoProximaTemporada());
			
			BigDecimal presupuestoRestante = getPresupuestoRestante(j.getCompeticion().getLimiteTope(), j.getEquipo().getPresupuestoProximaTemporada(), sumaSalarial);			
			contractdata.setPresupuestoRestante(presupuestoRestante);
			maxSalary = presupuestoRestante;
			//Nº temporadas
			int maxSeasons = competitionservice.getSeasonsRemaining(j.getCompeticion().getIdCompeticion()).size()-1;
			contractdata.setMaxSeasons( maxSeasons );			
			
		}
		
		if (maxSalary.compareTo(getMinSalary(j))<0){
			maxSalary = getMinSalary(j);
		}				
		
		contractdata.setMaxSalary(maxSalary.compareTo(salaryTop)> 0 ? salaryTop : maxSalary);		
		contractdata.setCapSpace(j.getCompeticion().getLimiteSalarial().subtract(sumaSalarial));		
		contractdata.setSumaSalarial(sumaSalarial);		
		contractdata.setSalaryCap(j.getCompeticion().getLimiteSalarial());
		contractdata.setLuxuryTax(j.getCompeticion().getLimiteTope());
		contractdata.setTopSalary(salaryTop);						

		
		final BigDecimal defaultOffer = getDefaultSalaryOffer(j);		  
		contractdata.setCurrentSalary(((maxSalary.compareTo(defaultOffer)<0) ?  maxSalary : defaultOffer));
					

		// Si se pasa de la tasa de lujo
		BigDecimal midLevelException = new BigDecimal(0);
		if (j.getCompeticion().getLimiteTope().compareTo(sumaSalarial)<0){
			midLevelException =  Constants.cSalaryExcepcionNivelMedioOTax;
		}
		else {
			midLevelException =  Constants.cSalaryExcepcionNivelMedioUTax;
		}
		contractdata.setVisibleMidLevelException(!j.getEquipo().isMidLevelExceptionUsed() && (maxSalary.compareTo(midLevelException)<0));
		contractdata.setMidLevelException(midLevelException);
		
		
		return contractdata;
	}
	private BigDecimal getPresupuestoRestante(BigDecimal luxuryTax, BigDecimal presupuestoTemporada, BigDecimal sumaSalarial) {
		BigDecimal presupuestoRestante = new BigDecimal(0);
		
		if (presupuestoTemporada.compareTo(luxuryTax)>0){
			BigDecimal salarioHastaLimite = luxuryTax.subtract(sumaSalarial);
			
			presupuestoRestante = salarioHastaLimite.add((presupuestoTemporada.subtract(luxuryTax)).divide(new BigDecimal(2.5), RoundingMode.HALF_UP));
		}
		else {
			presupuestoRestante = presupuestoTemporada.subtract(sumaSalarial);
		}
		return presupuestoRestante;
	}
	
	@Transactional(readOnly=true)
	@Override
	public PlayerContractData getContractDataAgenteLibreNoRestringido(Jugador j, Equipo e) throws InstanceNotFoundException{
		PlayerContractData contractdata = new PlayerContractData();
		contractdata.setImagen(j.getImagen());
		contractdata.setIdJugador(j.getIdJugador());
		contractdata.setNombre(j.getNombre());

		contractdata.setMinSalary(getMinSalary(j));
		
		//Salario
		BigDecimal maxSalary = new BigDecimal(0);
		BigDecimal sumaSalarial = new BigDecimal(0);
		//Info económica
		final BigDecimal salaryTop = getSalaryTop(j);
					
		sumaSalarial = getSumaSalarialTemporada(e, j.getCompeticion().getIdTemporadaActual(), true);
		
		final BigDecimal multaLuxury = getMultaLuxuryTax(sumaSalarial, j.getCompeticion().getLimiteTope());			
		sumaSalarial = sumaSalarial.add(multaLuxury);
		
		contractdata.setPresupuestoTotal(e.getPresupuestoActual());
		BigDecimal presupuestoRestante = getPresupuestoRestante(j.getCompeticion().getLimiteTope(), e.getPresupuestoActual(), sumaSalarial);
		BigDecimal capSpace = j.getCompeticion().getLimiteSalarial().subtract(sumaSalarial);
		contractdata.setPresupuestoRestante(presupuestoRestante);
		maxSalary = capSpace;
		//Nº temporadas
		int maxSeasons = competitionservice.getSeasonsRemaining(j.getCompeticion().getIdCompeticion()).size();
		contractdata.setMaxSeasons( maxSeasons );						
		
		if (maxSalary.compareTo(getMinSalary(j))<0){
			maxSalary = getMinSalary(j);
		}				
		
		contractdata.setMaxSalary(maxSalary.compareTo(salaryTop)> 0 ? salaryTop : maxSalary);		
		contractdata.setCapSpace(capSpace);		
		contractdata.setSumaSalarial(sumaSalarial);		
		contractdata.setSalaryCap(j.getCompeticion().getLimiteSalarial());
		contractdata.setLuxuryTax(j.getCompeticion().getLimiteTope());
		contractdata.setTopSalary(salaryTop);						

		
		final BigDecimal defaultOffer = getDefaultSalaryOffer(j);		  
		contractdata.setCurrentSalary(((maxSalary.compareTo(defaultOffer)<0) ?  maxSalary : defaultOffer));
					

		// Si se pasa de la tasa de lujo
		BigDecimal midLevelException = new BigDecimal(0);
		if (j.getCompeticion().getLimiteTope().compareTo(sumaSalarial)<0){
			midLevelException =  Constants.cSalaryExcepcionNivelMedioOTax;
		}
		else {
			midLevelException =  Constants.cSalaryExcepcionNivelMedioUTax;
		}
		contractdata.setVisibleMidLevelException(!e.isMidLevelExceptionUsed() && (presupuestoRestante.compareTo(midLevelException)>0));
		contractdata.setMidLevelException(midLevelException);
		
		
		return contractdata;
	}
	
	@Override
	@Transactional
	public PlayerContractData getContractDataAgente(Jugador j, Equipo e) throws InstanceNotFoundException{
		PlayerContractData contractdata = new PlayerContractData();
		contractdata.setImagen(j.getImagen());
		contractdata.setIdJugador(j.getIdJugador());
		contractdata.setNombre(j.getNombre());

		contractdata.setMinSalary(getMinSalary(j));
		
		//Salario
		BigDecimal maxSalary = new BigDecimal(0);
		Temporada temporadaSiguiente = this.seasonService.getTemporadaSiguienteCompeticion(j.getCompeticion());
		Temporada temporadaActual = this.seasonService.getTemporadaActualCompeticion(j.getCompeticion());
		Long idTemporada = (j.getEquipo()!=null && (temporadaSiguiente!=null)) ? temporadaSiguiente.getIdTemporada() : temporadaActual.getIdTemporada(); 
		BigDecimal sumaSalarial = getSumaSalarialTemporada(e, ((temporadaSiguiente!=null) ? idTemporada : null), false);
		BigDecimal sumaSalarialConPospuestos = getSumaSalarialTemporada(e, ((temporadaSiguiente!=null) ? idTemporada : null), true);
		BigDecimal presupuestoTotalTemporada = (j.getEquipo()!= null) ? e.getPresupuestoProximaTemporada() : e.getPresupuestoActual();
		BigDecimal presupuestoRestante = (j.getEquipo()!= null) ? e.getPresupuestoProximaTemporada() : e.getPresupuestoActual();
		
		BigDecimal limiteMinimo = j.getCompeticion().getLimiteSalarial().compareTo(presupuestoRestante)>0 ? presupuestoRestante : j.getCompeticion().getLimiteSalarial();
		
		//Calculamos el Cap Space general
		
		BigDecimal capSpace = new BigDecimal(0);
		// Si la suma de salarios ya se pasa del límite salarial
		if (limiteMinimo.compareTo(sumaSalarial)<0){
			capSpace = new BigDecimal(0);
		}
		// Si estamos por debajo del limite salarial, permitimos completar hasta el límite. 
		else {
			capSpace = limiteMinimo.subtract( sumaSalarial );
		}
		if (presupuestoRestante.compareTo(new BigDecimal(0))<=0){
			presupuestoRestante = new BigDecimal(0);
		}
		
		final BigDecimal salaryTop = getSalaryTop(j);
		
		//Si hay contratos pospuestos
		if (sumaSalarialConPospuestos.compareTo(sumaSalarial)>0){			
			presupuestoRestante = getPresupuestoRestante(j.getCompeticion().getLimiteTope(), e.getPresupuestoProximaTemporada(), sumaSalarialConPospuestos);
			BigDecimal sumaSalarialPospuestos =  sumaSalarialConPospuestos.subtract(sumaSalarial);
			BigDecimal capSpaceUtilizado = new BigDecimal(0);
			//A continuación debemos restar el CAP gastado en las renovaciones. (Renovaciones - (Presupuesto-100M))
			//			capSpace = capSpace- capSpaceUtilizado;
			BigDecimal presupuestoOverCap = presupuestoTotalTemporada.subtract(j.getCompeticion().getLimiteSalarial());
			
			if (presupuestoOverCap.compareTo(BigDecimal.ZERO)<=0){
				capSpace = new BigDecimal(0);
			}
			else {
				
				if (sumaSalarialPospuestos.compareTo(presupuestoOverCap)>0){
					capSpaceUtilizado = sumaSalarialPospuestos.subtract(presupuestoOverCap);
					capSpace = capSpace.subtract(capSpaceUtilizado);
					contractdata.setCapConsumido(capSpaceUtilizado);
				}
			}
			capSpace = capSpace.compareTo(presupuestoRestante)>0 ? presupuestoRestante : capSpace;
		}
		//Si NO hay contratos pospuestos
		else {
			presupuestoRestante = getPresupuestoRestante(j.getCompeticion().getLimiteTope(), e.getPresupuestoProximaTemporada(), sumaSalarial);
			capSpace = capSpace.compareTo(presupuestoRestante)>0 ? presupuestoRestante : capSpace;
		}
		
		maxSalary = capSpace;
		if (maxSalary.compareTo(getMinSalary(j))<0){
			maxSalary = getMinSalary(j);
		}
		contractdata.setMaxSalary(maxSalary.compareTo(salaryTop)< 0 ? maxSalary : salaryTop);
		contractdata.setTopSalary(salaryTop);
		
		final BigDecimal defaultOffer = getDefaultSalaryOffer(j);		  
		contractdata.setCurrentSalary(((maxSalary.compareTo(defaultOffer)<0) ?  maxSalary : defaultOffer));

		//Nº temporadas
		int maxSeasons = competitionservice.getSeasonsRemaining(j.getCompeticion().getIdCompeticion()).size()-1;
		contractdata.setMaxSeasons( maxSeasons );
		
		//Info económica
		contractdata.setCapSpace(capSpace);
		contractdata.setPresupuestoTotal(presupuestoTotalTemporada);
		contractdata.setSumaSalarial(sumaSalarial);
		contractdata.setPresupuestoRestante(presupuestoRestante);
		contractdata.setSalaryCap(j.getCompeticion().getLimiteSalarial());
		contractdata.setLuxuryTax(j.getCompeticion().getLimiteTope());

		BigDecimal midLevelException = new BigDecimal(0);
		if (j.getCompeticion().getLimiteTope().compareTo(sumaSalarial)<0){
			midLevelException =  Constants.cSalaryExcepcionNivelMedioOTax;
		}
		else {
			midLevelException =  Constants.cSalaryExcepcionNivelMedioUTax;
		}
		contractdata.setVisibleMidLevelException(!e.isMidLevelExceptionUsed() && (presupuestoRestante.compareTo(midLevelException)>=0));
		contractdata.setMidLevelException(midLevelException);		
		
		
		return contractdata;
		
		
	}	
	
	@Override
	public BigDecimal getMinSalary(Jugador j){
		BigDecimal minSalary = Constants.cMinSalaryPlayer;
		
		if (j.getYearsPro()==0){			
			minSalary = new BigDecimal(815615);
		}
		else if (j.getYearsPro()==1){			
			minSalary = new BigDecimal(1312611);
		}
		else if (j.getYearsPro()==2){			
			minSalary = new BigDecimal(1471382);
		}
		else if (j.getYearsPro()==3){			
			minSalary = new BigDecimal(1524305);
		}
		else if (j.getYearsPro()==4){			
			minSalary = new BigDecimal(1577230);
		}
		else if (j.getYearsPro()==5){			
			minSalary = new BigDecimal(1709538);
		}
		else if (j.getYearsPro()==6){			
			minSalary = new BigDecimal(1841849);
		}
		else if (j.getYearsPro()==7){			
			minSalary = new BigDecimal(1974159);
		}
		else if (j.getYearsPro()==8){			
			minSalary = new BigDecimal(2106470);
		}
		else if (j.getYearsPro()==9){			
			minSalary = new BigDecimal(2116955);
		}
		else if (j.getYearsPro()>=10){			
			minSalary = new BigDecimal(2328652);
		}
		
		return minSalary;
	}
	
	@Transactional
	private BigDecimal getSumaSalarialTemporada(Equipo e, Long idTemporada, boolean pendiente) {
		if (idTemporada==null){
			return new BigDecimal(200000000);
		}
		BigDecimal sumaSalarial = this.contratodao.getSumSalaries(e.getIdEquipo(), idTemporada, pendiente);
		return sumaSalarial;
	}
	

	@Override
	public BigDecimal getSumaSalarialTemporadaSalarioEntranteSaliente(Equipo e, Long idTemporada, List<Jugador> listaJugadoresEntran, List<Jugador> listaJugadoresSalen) {
		
		if (idTemporada==null){
			return new BigDecimal(200000000);
		}
		
		BigDecimal sumSalary = contratodao.getSumSalaries(e.getIdEquipo(), idTemporada, false);
				
		if (CollectionUtils.isNotEmpty(listaJugadoresEntran)){
			
			final List<Long> idsJugadoresEntran = new ArrayList<Long>();			
			for (Jugador j : listaJugadoresEntran){
				idsJugadoresEntran.add(j.getIdJugador());
			}
			
			BigDecimal sumaSalarioJugadoresEntran = this.contratodao.findSumaSalarialJugadoresTemporada(idsJugadoresEntran, idTemporada);

			sumSalary = sumSalary.add(sumaSalarioJugadoresEntran);
			
		}		
		

		if (CollectionUtils.isNotEmpty(listaJugadoresSalen)){
			
			final List<Long> idsJugadoresSalen = new ArrayList<Long>();			
			for (Jugador j : listaJugadoresSalen){
				idsJugadoresSalen.add(j.getIdJugador());
			}
			
			BigDecimal sumaSalarioJugadoresSalen = this.contratodao.findSumaSalarialJugadoresTemporada(idsJugadoresSalen, idTemporada);

			sumSalary = sumSalary.subtract(sumaSalarioJugadoresSalen);
			
		}
		
		return sumSalary;
		

	}

	@Override
	public BigDecimal getSumaSalarialByIdsJugadores(List<Jugador> listaJugadores, long idTemporada){
		
		if (CollectionUtils.isNotEmpty(listaJugadores)){
			
			final List<Long> idsJugadoresEntran = new ArrayList<Long>();			
			for (Jugador j : listaJugadores){
				idsJugadoresEntran.add(j.getIdJugador());
			}
			
			return this.contratodao.findSumaSalarialJugadoresTemporada(idsJugadoresEntran, idTemporada);
			
		}
		
		return new BigDecimal(0);
	}
	
	private BigDecimal getSalaryTop(Jugador j) {
		float porcentaje = 0.25F;
		
		if (j.getYearsPro()<6){
			porcentaje = 0.25F;			
		}
		else if (j.getYearsPro()<10){
			porcentaje = 0.30F;
		}
		else if (j.getYearsPro()>=10){
			porcentaje = 0.35F;
		}
		
		BigDecimal maxSalary = j.getCompeticion().getLimiteSalarial().multiply(new BigDecimal(porcentaje));
		return maxSalary;
	}
	
	private boolean haJugadoUnaTemporadaCompleta(Jugador j) {
		List<HistoricoEquipoJugador> listaHistorico = this.historicoEquipoJugadorDao.findSeasonTeamsByPlayer(j.getIdJugador(), j.getCompeticion().getIdTemporadaActual());
		if (listaHistorico.size()==1){
			if (listaHistorico.get(0).getFecha().compareTo(j.getCompeticion().getStartDate())<=0 ){
				return true;
			}
		}
		return false;
	}
	
	public static BigDecimal getDefaultSalaryOffer(Jugador j){			
		
		final double randomFactor = CommonFunctions.getPorcentajeRandomMasMenos(15);
		final BigDecimal cache = j.getCache().multiply(new BigDecimal(randomFactor));
		
		return cache;		
		
	}
	public int getContractYearsRemaining(long idJugador){
		
		return contratodao.getContractYearsRemaining(idJugador);
		
	}
	public playerService getPlayerService() {
		return playerService;
	}
	public void setPlayerService(playerService playerService) {
		this.playerService = playerService;
	}
	
	@Override
	public boolean isContratoTemporadaActual(Contrato c){
		List<LineaContrato> listLineasContrato = c.getListLineasContrato();
		Collections.sort(listLineasContrato);
			
		for (LineaContrato linea : listLineasContrato){
			if (linea.getTemporada().getIdTemporada()==c.getJugador().getCompeticion().getIdTemporadaActual()){
				return true;
			}
		}
		return false;
		
	}

	@Override
	@Transactional(readOnly=true)
	public ResultadoValidacionContratoOfrecidoDto isValidOfertaContrato(long idEquipo, Contrato c) throws InstanceNotFoundException{
		
		ResultadoValidacionContratoOfrecidoDto resultado = new ResultadoValidacionContratoOfrecidoDto();
		
		resultado.setValido(true);
		Temporada temporadaSiguiente = this.seasonService.getTemporadaSiguienteCompeticion(c.getJugador().getCompeticion());
		Temporada temporadaActual = this.seasonService.getTemporadaActualCompeticion(c.getJugador().getCompeticion());
		Long idTemporada = (c.getJugador().getEquipo()!=null && (temporadaSiguiente!=null)) ? temporadaSiguiente.getIdTemporada() : temporadaActual.getIdTemporada(); 
		
		BigDecimal salarioOfrecido = c.getListLineasContrato().get(0).getSalario();
		for (LineaContrato lc : c.getListLineasContrato()){
			if (lc.getTemporada().getIdTemporada()==idTemporada){
				salarioOfrecido = lc.getSalario();
			}
		}
		
		 
		BigDecimal cache = c.getJugador().getCache();
		if (salarioOfrecido.compareTo(cache.multiply(new BigDecimal(0.75)))<0){
			resultado.setValido(false);
			resultado.setMotivosNoValido("El salario ofrecido inferior al 75% del caché del jugador: ");
			return resultado;
		}
		
		//Si no es S&T
		if (c.getTraspaso()==null){
			int count = 0;
			for (Contrato ofertaContrato : c.getJugador().getListaOfertasContrato()){
				
				if ((ofertaContrato.getTraspaso()==null)&&(ofertaContrato.getEquipo().getIdEquipo()==idEquipo)){
					count++;
				}
				if (count>1){
					resultado.setValido(false);
					resultado.setMotivosNoValido("Ya se ha enviado una oferta a este jugador. Espera a que te responda.");
					return resultado;
				}
			}
					
			
			if (c.getEquipo().isMidLevelExceptionUsed() && c.isUseMidLevelException()){
				resultado.setValido(false);
				resultado.setMotivosNoValido("La excepción de nivel medio ya ha sido utilizada esta temporada");
				return resultado;
			}
			
			final PlayerContractData contractData = getPlayerResignContractData(c.getJugador().getIdJugador(), c.getEquipo().getIdEquipo());
			
			for (LineaContrato lc : c.getListLineasContrato()){

				final BigDecimal salarioOfrecidoTemporada =  lc.getSalario();

				// Permitirmos siempre ofrecer el mínimo
				if (salarioOfrecidoTemporada.compareTo(getMinSalary(c.getJugador()))<=0){
					resultado.setValido(true);
					return resultado;
				}
				
				if (c.getJugador().getEquipo()!=null){
				
					final Temporada tSiguiente = this.seasonService.getTemporadaSiguienteCompeticion(c.getJugador().getCompeticion());
					
					if ((tSiguiente!=null) && (tSiguiente.getIdTemporada()==lc.getTemporada().getIdTemporada())){
						
						//Si es un jugador propio:
						if ((c.getJugador().getEquipo().getIdEquipo()==c.getEquipo().getIdEquipo()) && (!c.isUseMidLevelException())){
							
							if (lc.getSalario().compareTo(contractData.getPresupuestoRestante())>0){
								resultado.setValido(false);
								resultado.setMotivosNoValido("El salario ofrecido es superior al que se puede permitir la franquicia (presupuesto disponible: "+contractData.getPresupuestoRestante().toString()+" $)");									
							}
	
						}
						//Si es un jugador de otro equipo o si el jugador no tiene equipo
						else {
							if ((lc.getSalario().compareTo(contractData.getCapSpace())>0) && (!c.isUseMidLevelException())){
								resultado.setValido(false);
								resultado.setMotivosNoValido("El salario ofrecido es superior al espacio salarial restante (espacio salarial restante: "+contractData.getCapSpace().toString()+" $)");
							}
							if (lc.getSalario().compareTo(contractData.getPresupuestoRestante())>0){
								resultado.setValido(false);
								resultado.setMotivosNoValido("El salario ofrecido es superior al que se puede permitir la franquicia (presupuesto disponible: "+contractData.getPresupuestoRestante().toString()+" $)");									
							}
							
						}					
	
						
					}
				}
				else {
					if ((lc.getTemporada().getIdTemporada()==c. getJugador().getCompeticion().getIdTemporadaActual()) && (lc.getSalario().compareTo(contractData.getPresupuestoRestante())>0) && (!c.isUseMidLevelException())){
						resultado.setValido(false);
						resultado.setMotivosNoValido("El salario ofrecido es superior al espacio salarial restante (espacio salarial restante: "+contractData.getCapSpace().toString()+" $)");
					}					
				}
																											


			} 
		}
		//Si es S&T
		else {		
//			List<Contrato> listaOfertasContrato = c.getJugador().getListaOfertasContrato();
//			int count=0;
//			for (Contrato ofertaContrato : listaOfertasContrato){
//				if ((ofertaContrato.getTraspaso()!=null) && (ofertaContrato.getTraspaso().getEquipoDestino()!=null) &&(idEquipo==ofertaContrato.getTraspaso().getEquipoDestino().getIdEquipo())){
//					count++;
//				}
//			}
//			if (count>1){
//				resultado.setValido(false);
//				resultado.setMotivosNoValido("Ya se ha enviado una oferta a este jugador. Espera a que te responda.");
//				return resultado;
//			}

								
			if (c.getTraspaso().getEquipoDestino().isMidLevelExceptionUsed() && c.isUseMidLevelException()){
				resultado.setValido(false);
				resultado.setMotivosNoValido("La excepción de nivel medio ya ha sido utilizada esta temporada");
			}
			

			if (!isTraspasoSignAndTradeValidoReceptor(c)){
				resultado.setValido(false);
				resultado.setMotivosNoValido("El equipo que ofrece el traspaso no puede asumir los contratos entrantes");				
			}
			if (!isTraspasoSignAndTradeValidoOfertor(c)){
				resultado.setValido(false);
				resultado.setMotivosNoValido("El equipo que recibe el traspaso no tiene suficiente espacio salarial para asumir el nuevo contrato");
			}
		}
		
		return resultado;
		

	}
	private boolean isTraspasoSignAndTradeValidoOfertor(Contrato c) {
		Temporada temporadaSiguiente = this.seasonService.getTemporadaSiguienteCompeticion(c.getJugador().getCompeticion());
		if (temporadaSiguiente!=null){
			BigDecimal sumaSalarialEquipo = getSumaSalarialTemporadaSalarioEntranteSaliente(c.getTraspaso().getEquipoOrigen(), temporadaSiguiente.getIdTemporada(), c.getTraspaso().getListaJugadoresRecibidos(), c.getTraspaso().getListaJugadoresOfrecidos());
			
			
			// Si la suma de salarios + los salarios que entran se pasan del presupuesto no puede hacer el trade.
			if (sumaSalarialEquipo.compareTo(c.getTraspaso().getEquipoDestino().getPresupuestoProximaTemporada())>0){
				return false;
			}
			else return true;
		}
		else return false;
		

	}
	
	
	private boolean isTraspasoSignAndTradeValidoReceptor(Contrato c){
		Temporada temporadaSiguiente = this.seasonService.getTemporadaSiguienteCompeticion(c.getJugador().getCompeticion());
		
		if (temporadaSiguiente==null){
			return false;
		}
		
		BigDecimal sumaSalarial = getSumaSalarialTemporadaSalarioEntranteSaliente(c.getTraspaso().getEquipoDestino(), temporadaSiguiente.getIdTemporada(), c.getTraspaso().getListaJugadoresOfrecidos(), c.getTraspaso().getListaJugadoresRecibidos());				
		sumaSalarial = sumaSalarial.add(c.getListLineasContrato().get(0).getSalario());
		// Si nos pasamos del límite salarial con los salarios comprometidos + entrantes - salientes
		return (sumaSalarial.compareTo(c.getJugador().getCompeticion().getLimiteSalarial())<=0);
		
	}
	
	@Override
	public BigDecimal getMultaLuxuryTax(BigDecimal sumaSalarial, BigDecimal luxuryTax){
		BigDecimal resultado = new BigDecimal(0);
		
		if (sumaSalarial.compareTo(luxuryTax)>0){
			resultado = sumaSalarial.subtract(luxuryTax);
			resultado = resultado.multiply(new BigDecimal(1.5));
		}
		
		return resultado;
	}
	
	@Override
	public TeamContractData findTeamContractDataByIdEquipo(long idEquipo) throws InstanceNotFoundException{
		
		Equipo e = this.equipodao.find(idEquipo);
		
		TeamContractData teamContractData = new TeamContractData();
		teamContractData.setIdCompeticion(e.getCompeticion().getIdCompeticion());
		teamContractData.setIdEquipo(idEquipo);		
		
		List<ContractData> listaContractData = this.contratodao.findContractDataByTeamID(idEquipo);		
		teamContractData.setListaContractData(listaContractData);
		
		BigDecimal sumaSalarialT1 = new BigDecimal(0);
		BigDecimal sumaSalarialT2 = new BigDecimal(0);
		BigDecimal sumaSalarialT3 = new BigDecimal(0);

		for (ContractData contractData : listaContractData){
			sumaSalarialT1 = sumaSalarialT1.add(contractData.getSalarioTemporada1());
			sumaSalarialT2 = sumaSalarialT2.add(contractData.getSalarioTemporada2());
			sumaSalarialT3 = sumaSalarialT3.add(contractData.getSalarioTemporada3());
		}
		
		teamContractData.setSumaSalarialT1(sumaSalarialT1);
		teamContractData.setSumaSalarialT2(sumaSalarialT2);
		teamContractData.setSumaSalarialT3(sumaSalarialT3);
		
		
		return teamContractData;
		
		
	}
	

	@Override
	public void registrarValoracionOferta(ValoracionOfertaContratoDto valoracionOferta){
		this.contratodao.registrarValoracionOferta(valoracionOferta);
	}
	
}

