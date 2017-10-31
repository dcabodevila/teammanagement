package es.ligasnba.app.model.traspaso;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;

import es.ligasnba.app.model.competicion.Competicion;
import es.ligasnba.app.model.contrato.ContractService;
import es.ligasnba.app.model.contrato.Contrato;
import es.ligasnba.app.model.contrato.ContratoDao;
import es.ligasnba.app.model.equipo.Equipo;
import es.ligasnba.app.model.equipo.EquipoDao;
import es.ligasnba.app.model.equipo.teamService;
import es.ligasnba.app.model.jugador.Jugador;
import es.ligasnba.app.model.jugador.JugadorDao;
import es.ligasnba.app.model.lineacontrato.LineaContrato;
import es.ligasnba.app.model.noticia.newsService;
import es.ligasnba.app.model.temporada.Temporada;
import es.ligasnba.app.model.temporada.seasonService;
import es.ligasnba.app.util.constants.Constants;
import es.ligasnba.app.util.exceptions.InstanceNotFoundException;
import es.ligasnba.app.util.jqgrid.CustomGenericResponse;

@Service("tradeService")
@Transactional
public class tradeServiceImpl implements tradeService{
	
	@Autowired
	private TraspasoDao traspasodao;
	@Autowired
	private JugadorDao jugadordao;
	@Autowired
	private EquipoDao equipodao;
	@Autowired
	private ContratoDao contratodao;
	@Autowired
	private ContractService contractService;
	@Autowired
	private newsService newsservice;
	@Autowired
	private teamService teamservice;
	@Autowired
	private seasonService seasonservice;
	
	@SuppressWarnings("unused")
	@Autowired
	private PlatformTransactionManager transactionManager;	
	
	public void setTraspasoDao(TraspasoDao t) {
		this.traspasodao = t;
	}
	public void setEquipoDao(EquipoDao equipodao) {
		this.equipodao = equipodao;
	}
	public void setJugadorDao(JugadorDao jugadordao){
		this.jugadordao = jugadordao;
	}
	public void setContratoDao(ContratoDao contratodao) {
		this.contratodao = contratodao;
	}
	public void setNewsservice(newsService newsservice) {
		this.newsservice = newsservice;
	}
	public void settransactionManager(PlatformTransactionManager t) {
		transactionManager = t;
	}
	public void setTeamService(teamService teamservice) {
		this.teamservice = teamservice;
	}
	public void setContractService(ContractService contractService) {
		this.contractService = contractService;
	}
	public void setSeaSonservice(seasonService seasonservice) {
		this.seasonservice = seasonservice;
	}
	
	@Transactional(readOnly=true)
	public Traspaso getTrade(long id) throws InstanceNotFoundException{
		return traspasodao.find(id);
	}
	
	@Override
	public Traspaso createTradeOfferIdsJugadores(long idEquipoOrigen, long idEquipoDestino, List<Long> idsJugadoresOfrecidos,List<Long> idsJugadoresRecibidos, String comentario, BigDecimal importe)
			throws InstanceNotFoundException {

		Equipo equipoOrigen = equipodao.find(idEquipoOrigen);
		Equipo equipoDestino = equipodao.find(idEquipoDestino);
	
		
		Traspaso t = new Traspaso(equipoOrigen,equipoDestino, comentario,importe);
		final List<Jugador> listaJugadoresOfrecidos = this.jugadordao.findJugadoresByIds(idsJugadoresOfrecidos);
		final List<Jugador> listaJugadoresRecibidos = this.jugadordao.findJugadoresByIds(idsJugadoresRecibidos);
		t.getListaJugadoresOfrecidos().addAll(listaJugadoresOfrecidos);
		t.getListaJugadoresRecibidos().addAll(listaJugadoresRecibidos);
		
		traspasodao.create(t);		
		
		return t;

		
	}
	
	@Override
	public Traspaso createTradeOffer(long idEquipoOrigen, long idEquipoDestino, List<Jugador> jugadoresOfrecidos,List<Jugador> jugadoresRecibidos, String comentario, BigDecimal importe)
			throws InstanceNotFoundException {

		Equipo equipoOrigen = equipodao.find(idEquipoOrigen);
		Equipo equipoDestino = equipodao.find(idEquipoDestino);
	
		
		Traspaso t = new Traspaso(equipoOrigen,equipoDestino, comentario,importe);
		
		t.getListaJugadoresOfrecidos().addAll(jugadoresOfrecidos);
		t.getListaJugadoresRecibidos().addAll(jugadoresRecibidos);
		
		traspasodao.create(t);		
		
		return t;

		
	}
	
	@Override
	public void removeTrade(long idTraspaso) throws InstanceNotFoundException{
		this.traspasodao.remove(idTraspaso);
	}
	
	

	@Transactional(readOnly=true)
	@Override
	public CustomGenericResponse isValidTradeIdsJugadores(long idTeamFrom, long idTeamTo, List<Long> idsJugadoresOfrecidos, List<Long> idsJugadoresRecibidos) throws InstanceNotFoundException{
		
		final List<Jugador> listaJugadoresOfrecidos = this.jugadordao.findJugadoresByIds(idsJugadoresOfrecidos);
		final List<Jugador> listaJugadoresRecibidos = this.jugadordao.findJugadoresByIds(idsJugadoresRecibidos);

		return isValidTradeJugadores(idTeamFrom, idTeamTo, listaJugadoresOfrecidos, listaJugadoresRecibidos, false);
		
	}
	
	@Transactional(readOnly=true)
	private CustomGenericResponse isValidTradeJugadores(long idTeamFrom, long idTeamTo, 
			final List<Jugador> listaJugadoresOfrecidos, final List<Jugador> listaJugadoresRecibidos, final boolean isSignAndTrade)
					throws InstanceNotFoundException {
		
		CustomGenericResponse response = new CustomGenericResponse();
		
		final List<Jugador> plantillaOrigen  = this.jugadordao.findPlayersByTeamId(idTeamFrom, 0, Constants.cMaxPlayersByTeam);
		final List<Jugador> plantillaDestino = this.jugadordao.findPlayersByTeamId(idTeamTo, 0, Constants.cMaxPlayersByTeam);

		
		if ( !plantillaOrigen.containsAll(listaJugadoresOfrecidos) || !plantillaDestino.containsAll(listaJugadoresRecibidos) ){
			response.setSuccess(false);
			response.setMessage("Algunos de los jugadores ya no están en sus equipos.");
			return response;
		}
					
		if ( (plantillaOrigen.size() + listaJugadoresRecibidos.size() - listaJugadoresOfrecidos.size()  > Constants.cMaxPlayersByTeam)  ||
			 (plantillaDestino.size() + listaJugadoresOfrecidos.size() - listaJugadoresRecibidos.size() > Constants.cMaxPlayersByTeam) ||
			 (plantillaOrigen.size() + listaJugadoresRecibidos.size() - listaJugadoresOfrecidos.size()< Constants.cMinPlayersByTeam)  ||
			 (plantillaDestino.size() + listaJugadoresOfrecidos.size() - listaJugadoresRecibidos.size() < Constants.cMinPlayersByTeam) 			 
		){
			response.setSuccess(false);
			response.setMessage("El traspaso no cumple con el mínimo/máximo de jugadores en alguno de los equipos.");
			return response;
		}
		
		response = checkLimiteSalarialTraspaso(idTeamFrom, idTeamTo, listaJugadoresOfrecidos, listaJugadoresRecibidos, isSignAndTrade);
		if (!response.getSuccess()){
			return response;
		}
		
		response.setSuccess(true);
		response.setMessage("Traspaso válido");
		return response;
	}
	
	@Transactional(readOnly=true)
	private CustomGenericResponse checkLimiteSalarialTraspaso(long idTeamFrom, long idTeamTo, List<Jugador> jugadoresOfrecidos, List<Jugador> jugadoresRecibidos, final boolean isSignAndTrade) throws InstanceNotFoundException{
		
		CustomGenericResponse response = new CustomGenericResponse();
		final Equipo equipo1 = this.teamservice.findById(idTeamFrom);
		final Equipo equipo2 = this.teamservice.findById(idTeamTo);
		final Competicion com = equipo1.getCompeticion();
		
		final BigDecimal presupuestoEquipo1 = isSignAndTrade ? equipo1.getPresupuestoProximaTemporada() : equipo1.getPresupuestoActual();
		final BigDecimal presupuestoEquipo2 = isSignAndTrade ? equipo1.getPresupuestoProximaTemporada() : equipo2.getPresupuestoActual();
		
		final Temporada temporadaSiguiente = this.seasonservice.getTemporadaSiguienteCompeticion(com);
		
		if ((temporadaSiguiente==null) && (isSignAndTrade)){
			response.setMessage("No quedan más temporadas. El traspaso S&T no puede realizarse");
			response.setSuccess(false);
			return response;
		}
		
		long idTemporada = (isSignAndTrade ? temporadaSiguiente.getIdTemporada() : com.getIdTemporadaActual()); 
		
		BigDecimal sumaSalarialEquipo1 = this.contractService.getSumaSalarialTemporadaSalarioEntranteSaliente(equipo1, idTemporada, jugadoresRecibidos, jugadoresOfrecidos);
		BigDecimal sumaSalarialEquipo2 = this.contractService.getSumaSalarialTemporadaSalarioEntranteSaliente(equipo2, idTemporada, jugadoresOfrecidos , jugadoresRecibidos);
		
		final BigDecimal multaLuxuryTaxEquipo1 = this.contractService.getMultaLuxuryTax(sumaSalarialEquipo1, com.getLimiteTope());
		sumaSalarialEquipo1.add(multaLuxuryTaxEquipo1);
		
		final BigDecimal multaLuxuryTaxEquipo2 = this.contractService.getMultaLuxuryTax(sumaSalarialEquipo2, com.getLimiteTope());
		sumaSalarialEquipo2.add(multaLuxuryTaxEquipo2);
		
		final BigDecimal sumaSalarialJugadoresSalientes = this.contractService.getSumaSalarialByIdsJugadores(jugadoresOfrecidos, idTemporada);
		final BigDecimal sumaSalarialJugadoresEntrantes = this.contractService.getSumaSalarialByIdsJugadores(jugadoresRecibidos, idTemporada);
		
		boolean isValidoPresupuestoEquipo1 = true;
//		if (sumaSalarialJugadoresEntrantes.compareTo(sumaSalarialJugadoresSalientes)>0){
//			isValidoPresupuestoEquipo1 = (sumaSalarialEquipo1.compareTo(presupuestoEquipo1)<=0);
//		}
//		else {
//			isValidoPresupuestoEquipo1 = true;
//		}
		
		boolean isValidoPresupuestoEquipo2 = true;
//		if (sumaSalarialJugadoresSalientes.compareTo(sumaSalarialJugadoresEntrantes)>0){
//			isValidoPresupuestoEquipo2 = (sumaSalarialEquipo2.compareTo(presupuestoEquipo2)<=0);
//		}
//		else {
//			isValidoPresupuestoEquipo2 = true;
//		}
		
		final boolean isValidoSalariosEquipo1 = isValidoSalarioEquipo(com.getLimiteTope(), sumaSalarialEquipo1, sumaSalarialJugadoresSalientes, sumaSalarialJugadoresEntrantes);
		final boolean isValidoSalariosEquipo2 = isValidoSalarioEquipo(com.getLimiteTope(), sumaSalarialEquipo2, sumaSalarialJugadoresEntrantes, sumaSalarialJugadoresSalientes);
		
		response.setSuccess(isValidoPresupuestoEquipo1 && isValidoPresupuestoEquipo2 && isValidoSalariosEquipo1 && isValidoSalariosEquipo2);
		
		if (!response.getSuccess()){
			if (!isValidoPresupuestoEquipo1){
				response.setMessage("El presupuesto de tu equipo no puede pagar los salarios recibidos");
			}
			else if (!isValidoPresupuestoEquipo2){
				response.setMessage("El presupuesto del otro equipo no puede pagar los salarios ofrecidos");
			}
			else if (!isValidoSalariosEquipo1){
				response.setMessage("La diferencia de salarios es demasiado grande para tu equipo");
			}
			else if (!isValidoSalariosEquipo2){
				response.setMessage("La diferencia de salarios es demasiado grande para el otro equipo");
			}
		}
		
		return response;
		
	}
	private boolean isValidoSalarioEquipo(final BigDecimal luxuryTax, BigDecimal sumaSalarialEquipo1,
			final BigDecimal sumaSalarialJugadoresSalientes, final BigDecimal sumaSalarialJugadoresEntrantes) {
		boolean isValidoSalariosEquipo1 = false;
		//Si tras el traspaso se pasa del límite salarial
		if (sumaSalarialEquipo1.compareTo(luxuryTax)>0){
			BigDecimal maximoSalariosRecibir = sumaSalarialJugadoresSalientes.multiply(new BigDecimal(1.25));
			isValidoSalariosEquipo1 = (sumaSalarialJugadoresEntrantes.compareTo(maximoSalariosRecibir)<=0);
		}
		else {
			BigDecimal maximoSalariosRecibir = new BigDecimal(0);
			if (sumaSalarialJugadoresSalientes.compareTo(new BigDecimal(6500000))<=0){
				maximoSalariosRecibir = sumaSalarialJugadoresSalientes.multiply(new BigDecimal(1.75));
			}
			else if (sumaSalarialJugadoresSalientes.compareTo(new BigDecimal(19600000))<=0){
				maximoSalariosRecibir = sumaSalarialJugadoresSalientes.add(new BigDecimal(5000000));
			}
			else {
				maximoSalariosRecibir = sumaSalarialJugadoresSalientes.multiply(new BigDecimal(1.25));
			}
			
			isValidoSalariosEquipo1 = (sumaSalarialJugadoresEntrantes.compareTo(maximoSalariosRecibir)<=0);
			
		}
		
		return isValidoSalariosEquipo1;
	}


	@Transactional(readOnly=true)
	@Override
	public CustomGenericResponse isValidTradeTomarDecisionContrato(long idTraspaso) throws InstanceNotFoundException{
		
		CustomGenericResponse response = new CustomGenericResponse();
				
		Traspaso t = traspasodao.find(idTraspaso);

		if (!t.isAprobado()) {
			response.setSuccess(false);
			response.setMessage("Traspaso no aprobado");
			return response;
		}	
		
		return isValidTradeJugadores(t.getEquipoOrigen().getIdEquipo(), t.getEquipoDestino().getIdEquipo(), t.getListaJugadoresOfrecidos(), t.getListaJugadoresRecibidos(), true);		
		
	}
	
	@Transactional(readOnly=true)
	@Override
	public CustomGenericResponse isValidTradeEnviarTrade(long idTraspaso, final boolean isSignAndTrade) throws InstanceNotFoundException{
				
		Traspaso t = traspasodao.find(idTraspaso);
		
		return isValidTradeJugadores(t.getEquipoOrigen().getIdEquipo(), t.getEquipoDestino().getIdEquipo(), t.getListaJugadoresOfrecidos(), t.getListaJugadoresRecibidos(), isSignAndTrade);		
		
	}
	
	public Traspaso sendTradeOffer(long idTraspaso) throws InstanceNotFoundException, Exception{
	
		Traspaso t = traspasodao.find(idTraspaso);			
		
		if (isValidTradeEnviarTrade(t.getIdTraspaso(), false).getSuccess()) {
		
			t.getEquipoOrigen().getListaTraspasosOfrecidos().add(t);				
			equipodao.save(t.getEquipoOrigen());
			
			t.getEquipoDestino().getListaTraspasosRecibidos().add(t);
			equipodao.save(t.getEquipoDestino());
		}
		
		return t;				
		
	}
	
	
	@Transactional(readOnly=true)
	public TraspasoBlock getTraspasosRecibidos(long idTeam,int startindex,int count){
		
		List<Traspaso> traspasosRecibidos = traspasodao.getReceivedTradesByTeam(idTeam, startindex, count);
		
		boolean existMoreTraspasos = traspasosRecibidos.size() == (Constants.cMaxListItems + 1);
	    
		if (existMoreTraspasos) {
	    	traspasosRecibidos.remove(traspasosRecibidos.size() - 1);
	    }	
	    return new TraspasoBlock(traspasosRecibidos, existMoreTraspasos);
			
	}
	
	@Transactional(readOnly=true)
	public TraspasoBlock getTraspasosOfrecidos(long idTeam,int startindex,int count){
		
		List<Traspaso> traspasosOfrecidos = traspasodao.getSentTradesByTeam(idTeam, startindex,count);
		
		boolean existMoreTraspasos = traspasosOfrecidos.size() == (Constants.cMaxListItems + 1);
	    
		if (existMoreTraspasos) {
	    	traspasosOfrecidos.remove(traspasosOfrecidos.size() - 1);
	    }	
	    return new TraspasoBlock(traspasosOfrecidos, existMoreTraspasos);
			
	}	
	
	@Override
	public CustomGenericResponse acceptTradeOffer(long idTraspaso, final boolean ejecutarTraspasoST) throws Exception{
		
		CustomGenericResponse response = new CustomGenericResponse();		
		
		Traspaso t = traspasodao.find(idTraspaso);
				
		response = isValidTradeJugadores(t.getEquipoOrigen().getIdEquipo(),t.getEquipoDestino().getIdEquipo(), t.getListaJugadoresOfrecidos(), t.getListaJugadoresRecibidos(), ejecutarTraspasoST);
		
		if (!response.getSuccess()){
			newsservice.AddNewToUser(t.getEquipoOrigen(), response.getMessage() , t.getEquipoOrigen().getCompeticion().getActualDate());
			
			t.getListaJugadoresOfrecidos().removeAll(t.getListaJugadoresOfrecidos());
			t.getListaJugadoresRecibidos().removeAll(t.getListaJugadoresRecibidos());
			traspasodao.save(t);
			traspasodao.remove(idTraspaso);				
			return response;
		}
	
		
		final Equipo equipoOrigen = t.getEquipoOrigen();
		final Equipo equipoDestino = t.getEquipoDestino();
		
		if (t.getContrato()==null || ejecutarTraspasoST){
		
			ejecutarTraspaso(t);			
			newsservice.AddNewToUser(equipoOrigen, "Se ha aceptado el traspaso entre "+equipoOrigen.getnombre()+" y "+equipoDestino.getnombre(), equipoOrigen.getCompeticion().getActualDate());
			newsservice.AddNewToUser(equipoDestino, "Se ha aceptado el traspaso entre "+equipoOrigen.getnombre()+" y "+equipoDestino.getnombre() , equipoOrigen.getCompeticion().getActualDate());

		}
		else {
			newsservice.AddNewToUser(equipoDestino, equipoOrigen.getnombre()+" Ha aceptado el traspaso propuesto de Sign And Trade, esperando a la valoración del jugador", equipoOrigen.getCompeticion().getActualDate());
		}

				
		t.setAprobado(true);
		traspasodao.save(t);
		
		
		response.setSuccess(true);
		return response;
		
		
	}
	
	
	private void ejecutarTraspaso(Traspaso t)
			throws InstanceNotFoundException {
		Equipo equipoOrigen = t.getEquipoOrigen();
		Equipo equipoDestino = t.getEquipoDestino();
		
		for (int i=0;i<t.getListaJugadoresOfrecidos().size();i++){
			Contrato contratoJugadorOfrecido = contratodao.findSignedByPlayerAndTeam(equipoOrigen.getIdEquipo(), t.getListaJugadoresOfrecidos().get(i).getIdJugador());			
			contratoJugadorOfrecido.setEquipo(equipoDestino);
			contratoJugadorOfrecido.getJugador().setEquipo(equipoDestino);
			jugadordao.save(contratoJugadorOfrecido.getJugador());
		}
		
		for (int i=0;i<t.getListaJugadoresRecibidos().size();i++){
			Contrato contratoJugadorRecibido = contratodao.findSignedByPlayerAndTeam(equipoDestino.getIdEquipo(), t.getListaJugadoresRecibidos().get(i).getIdJugador());
			contratoJugadorRecibido.setEquipo(equipoOrigen);
			contratoJugadorRecibido.getJugador().setEquipo(equipoOrigen);
			jugadordao.save(contratoJugadorRecibido.getJugador());
		}	
		
		equipoOrigen.getListaJugadores().removeAll(t.getListaJugadoresOfrecidos());
		equipoDestino.getListaJugadores().removeAll(t.getListaJugadoresRecibidos());
		equipoDestino.getListaJugadores().addAll(t.getListaJugadoresOfrecidos());
		equipoOrigen.getListaJugadores().addAll(t.getListaJugadoresRecibidos());
		
		equipoOrigen.getListaTraspasosOfrecidos().remove(t);
		equipoDestino.getListaTraspasosRecibidos().remove(t);
		
		t.getListaJugadoresOfrecidos().removeAll(t.getListaJugadoresOfrecidos());
		t.getListaJugadoresRecibidos().removeAll(t.getListaJugadoresRecibidos());
		
		equipodao.save(equipoOrigen);
		equipodao.save(equipoDestino);
		
		newsservice.AddNewToUser(equipoOrigen, "Traspaso ejecutado entre "+equipoOrigen.getnombre()+" y "+equipoDestino.getnombre(), equipoOrigen.getCompeticion().getActualDate());
	}
	
	@Override
	public CustomGenericResponse rejectTradeOffer(long idTraspaso) throws InstanceNotFoundException{
		CustomGenericResponse response = new CustomGenericResponse();
		
		Traspaso t;
		
		t = traspasodao.find(idTraspaso);
		
		Equipo equipoOrigen = t.getEquipoOrigen();
		Equipo equipoDestino = t.getEquipoDestino();
		
		equipoOrigen.getListaTraspasosOfrecidos().remove(t);
		equipoDestino.getListaTraspasosRecibidos().remove(t);
		
		t.getListaJugadoresOfrecidos().removeAll(t.getListaJugadoresOfrecidos());
		t.getListaJugadoresRecibidos().removeAll(t.getListaJugadoresRecibidos());
		
		if (t.getContrato()!=null){
			this.contractService.deleteContract(t.getContrato().getIdContrato());
		}
		
		equipodao.save(equipoOrigen);
		equipodao.save(equipoDestino);
		traspasodao.remove(t.getIdTraspaso());

		
		newsservice.AddNewToUser(equipoOrigen, "Se ha rechazado el traspaso entre " +equipoOrigen.getnombre()+ " y "+ equipoDestino.getnombre() , equipoOrigen.getCompeticion().getActualDate());
		response.setSuccess(true);
		return response;
		
	}
	




}
