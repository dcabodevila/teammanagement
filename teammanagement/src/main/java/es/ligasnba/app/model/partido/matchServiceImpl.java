package es.ligasnba.app.model.partido;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.Transactional;
import es.ligasnba.app.model.actapartido.ActaPartido;
import es.ligasnba.app.model.actapartido.ActaPartidoDao;
import es.ligasnba.app.model.competicion.Competicion;
import es.ligasnba.app.model.competicion.CompeticionDao;
import es.ligasnba.app.model.equipo.Equipo;
import es.ligasnba.app.model.equipo.EquipoDao;
import es.ligasnba.app.model.finanzas.finanzasService;
import es.ligasnba.app.model.temporada.Temporada;
import es.ligasnba.app.model.temporada.TemporadaDao;
import es.ligasnba.app.model.usuario.Usuario;
import es.ligasnba.app.util.constants.Constants;
import es.ligasnba.app.util.exceptions.InstanceNotFoundException;
import es.ligasnba.app.util.jqgrid.CustomGenericResponse;

@Service("matchService")
@Transactional
public class matchServiceImpl implements matchService{
	@SuppressWarnings("unused")
	@Autowired
	private PlatformTransactionManager transactionManager;
	@Autowired 
	private EquipoDao equipodao;
	@Autowired
	private TemporadaDao temporadadao;
	@Autowired
	private PartidoDao partidodao;
	@Autowired
	private CompeticionDao competiciondao;
	@Autowired
	private ActaPartidoDao actapartidodao;
	@Autowired
	private finanzasService finanzasservice;
	
	

	
	public void setEquipoDao(EquipoDao e) {
		equipodao = e;
	}
	public void settransactionManager(PlatformTransactionManager t) {
		transactionManager = t;
	}
	public void setTemporadaDao(TemporadaDao temporadadao) {
		this.temporadadao = temporadadao;
	}
	public void setPartidoDao(PartidoDao partidodao) {
		this.partidodao = partidodao;
	}
	public void setCompeticionDao(CompeticionDao competiciondao){
		this.competiciondao = competiciondao;
	}
	public void setActaPartidoDao(ActaPartidoDao actapartidodao){
		this.actapartidodao = actapartidodao;
	}
	

	@Transactional(readOnly=true)
	public PartidoBlock getPartidosTemporada(long idTemporada,String field, String order, int startindex,int count) throws InstanceNotFoundException {
		
		List<Partido> listaPartidos = partidodao.getMatchesBySeason(idTemporada,field,order, startindex, count);
		
		boolean existsMoreMatches = listaPartidos.size() == (Constants.cMaxListItems +1);

		if (existsMoreMatches) {
			listaPartidos.remove(listaPartidos.size()-1);
		}
		return new PartidoBlock(listaPartidos, existsMoreMatches);	
		
	}
	
	@Transactional(readOnly=true)
	public PartidoBlock getPartidosLocal(long idEquipo, int startindex, int count) throws InstanceNotFoundException {
		List<Partido> listaPartidos = partidodao.getMatchesByLocalTeam(idEquipo, startindex, count);
		
		boolean existsMoreMatches = listaPartidos.size() == (Constants.cMaxListItems +1);

		if (existsMoreMatches) {
			listaPartidos.remove(listaPartidos.size()-1);
		}
		return new PartidoBlock(listaPartidos, existsMoreMatches);	
	}
	@Transactional(readOnly=true)
	public PartidoBlock getPartidosVisitante(long idEquipo,int startindex,int count) throws InstanceNotFoundException {
		List<Partido> listaPartidos = partidodao.getMatchesByVisitorTeam(idEquipo, startindex, count);
		
		boolean existsMoreMatches = listaPartidos.size() == (Constants.cMaxListItems +1);

		if (existsMoreMatches) {
			listaPartidos.remove(listaPartidos.size()-1);
		}
		return new PartidoBlock(listaPartidos, existsMoreMatches);	
	}	
	@Transactional(readOnly=true)
	public PartidoBlock getPartidos(long idEquipo,String field, String order, int startindex, int count) throws InstanceNotFoundException {
		
			
		List<Partido> listaPartidos = partidodao.getMatchesByTeam(idEquipo,field,order, startindex, count);		
		
		
		
		boolean existsMoreMatches = listaPartidos.size() == (Constants.cMaxListItems +1);

		if (existsMoreMatches) {
			listaPartidos.remove(listaPartidos.size()-1);
		}
		return new PartidoBlock(listaPartidos, existsMoreMatches);	
	}	
	
	@Transactional(readOnly=true)
	public List<Partido> getPartidosByDate(long idTemporada, Date date){
		
		return partidodao.getRemainingMatchesBySeasonAndDate(idTemporada, date);
		
	}
	
	
	public Partido createMatch(long idEquipoLocal, long idEquipoVisitante, long idTemporada) throws InstanceNotFoundException{
		
		Equipo el = equipodao.find(idEquipoLocal);
		Equipo ev = equipodao.find(idEquipoVisitante);
			
		Temporada t = temporadadao.find(idTemporada);
		
		
		Partido p = new Partido(el,ev,t);
		partidodao.create(p);
		
		el.getListaPartidosLocal().add(p);
		ev.getListaPartidosVisitante().add(p);
		equipodao.save(el);
		equipodao.save(ev);
		return p;
	}
	
	
	public Date getMatchDate(Competicion com) {
		
        Calendar cal= Calendar.getInstance();
        cal.setTime(com.getStartDate());
        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(com.getFinishDate());         
		
        Long value1 = cal.getTimeInMillis();

        
        Long value2 = cal2.getTimeInMillis();

        long value3 = (long)(value1 + Math.random()*(value2 - value1));
        cal.setTimeInMillis(value3);  
                
        return cal.getTime();
		
	}
	
	
	public List<Partido> generateCalendar(long idCompeticion, long idTemporada) throws InstanceNotFoundException {
		
		Competicion c = competiciondao.find(idCompeticion);
		List<Partido> listaPartidos = new ArrayList<Partido>();
		List<Equipo> listaEquipos = new ArrayList<Equipo>();
						
		listaEquipos = c.getListaEquipos();	
		Collections.shuffle(listaEquipos);		
		
		for (int i=0; i< listaEquipos.size();i++){
			for(int j=i;j<listaEquipos.size();j++){
				if (i!=j){
					
					List<Equipo> listaEquiposPartido = new ArrayList<Equipo>();
					listaEquiposPartido.add(listaEquipos.get(i));
					listaEquiposPartido.add(listaEquipos.get(j));
					Collections.shuffle(listaEquiposPartido);
					
					Partido p = createMatch(listaEquiposPartido.get(0).getIdEquipo(),listaEquiposPartido.get(1).getIdEquipo(),idTemporada);
					listaPartidos.add(p);
					
					partidodao.save(p);
					
				}
					
			}
		}

		return listaPartidos;
		
		
		
	}
	
	
	public List<Partido> addTeamToCalendar(long idEquipo, long idCompeticion) throws InstanceNotFoundException {
		
		Competicion c = competiciondao.find(idCompeticion);
		Equipo newTeam = equipodao.find(idEquipo);
		
		
		List<Partido> listaPartidos = new ArrayList<Partido>();
		List<Equipo> listaEquipos = new ArrayList<Equipo>();
				
		listaEquipos = c.getListaEquipos();
		
		
		Temporada temporadaActual = temporadadao.find(c.getIdTemporadaActual());
		
		for (int numVueltas=0;numVueltas<c.getNumVueltas();numVueltas++){
			for (int i=0; i< listaEquipos.size();i++){
	
				
				if (listaEquipos.get(i).getIdEquipo() != idEquipo ){ 
	
						Partido pLocal = createMatch(newTeam.getIdEquipo(), listaEquipos.get(i).getIdEquipo(), c.getIdTemporadaActual());
						listaPartidos.add(pLocal);
						
						partidodao.save(pLocal);
						listaEquipos.get(i).addPartidoVisitante(pLocal);				
						equipodao.save(listaEquipos.get(i));
						newTeam.addPartidoLocal(pLocal);
						equipodao.save(newTeam);
						
						temporadaActual.addPartido(pLocal);
						
						Partido pVisitante = createMatch(listaEquipos.get(i).getIdEquipo(), newTeam.getIdEquipo(), c.getIdTemporadaActual());
						listaPartidos.add(pVisitante);
						
						partidodao.save(pVisitante);
						listaEquipos.get(i).addPartidoLocal(pVisitante);				
						equipodao.save(listaEquipos.get(i));
						newTeam.addPartidoVisitante(pVisitante);
						equipodao.save(newTeam);			
						
						temporadaActual.addPartido(pVisitante);
						
				}						
	
			
			}
		}
		
		return listaPartidos;
		
		
		
	}	

	@Override
	public ActaPartido createActaPartido(long idPartido, Usuario usuarioValorador, boolean victoria, short valoracion, String comentario) throws InstanceNotFoundException, Exception{

		Partido p = partidodao.find(idPartido);
		
		ActaPartido ac = new ActaPartido();
		ac.setUsuarioValorador(usuarioValorador);
		if ((p.getEquipoLocal().getUsuario()!=null) && (p.getEquipoVisitante().getUsuario()!=null)){
			if (p.getEquipoLocal().getUsuario().getIdUsuario()==usuarioValorador.getIdUsuario()){
				ac.setUsuarioValorador(p.getEquipoVisitante().getUsuario());
				ac.setUsuarioValorado(p.getEquipoLocal().getUsuario());				
			}
			else if (p.getEquipoVisitante().getUsuario().getIdUsuario()==usuarioValorador.getIdUsuario()){
				ac.setUsuarioValorador(p.getEquipoLocal().getUsuario());
				ac.setUsuarioValorado(p.getEquipoVisitante().getUsuario());				
			}
		}
		ac.setValoracion(valoracion);
		ac.setVictoria(victoria);
		ac.setComentario(comentario);
		ac.setPartido(p);
		
		actapartidodao.create(ac);
		p.getActasPartido().add(ac);
		p.setFecha(new Date());
		partidodao.save(p);
		
		return ac;
	}
	
	
	
	
	@Transactional(readOnly=true)
	public boolean isUserLocalTeam(long idUsuario, Partido p){	
		return p.getEquipoLocal().getUsuario().getIdUsuario() == idUsuario;						
	}
	@Transactional(readOnly=true)
	public boolean isUserVisitorTeam(long idUsuario, Partido p){		
		return p.getEquipoVisitante().getUsuario().getIdUsuario() == idUsuario;						
	}	
	
	public void setComentario(long idActa,String comentario) throws InstanceNotFoundException{

		ActaPartido a = actapartidodao.find(idActa);			
		a.setComentario(comentario);		
		
	}
	
	
	@Transactional(readOnly=true)
	@Override
	public ValoracionData getValoracionFromPartido(long idEquipo, long idPartido, Long idUsuario) {

		ValoracionData valoracionData = this.actapartidodao.findValoracionByPartidoUsuario(idPartido, idUsuario);				
		MatchData matchData = this.partidodao.findGamesDataByIdPartido(idEquipo, idPartido);
		valoracionData.setMatchData(matchData);
		
		return valoracionData;
	}
	@Transactional(readOnly=true)
	public int getNumTotalPartidosEquipo(long idEquipo) throws InstanceNotFoundException{
		return partidodao.getNumMatchesByTeam(idEquipo);		
	}
	
	@Transactional(readOnly=true)
	public int getNumTotalPartidosTemporada(long idTemporada) throws InstanceNotFoundException{
		return partidodao.getNumMatchesBySeason(idTemporada);		
	}

	@Transactional(readOnly=true)
	public PartidoBlock getPartidosJugadosTemporada(long idTemporada,int startindex, int count) throws InstanceNotFoundException{
		List<Partido> listaPartidos = partidodao.getPlayedMatchesBySeason(idTemporada, startindex, count );
		
		boolean existsMoreMatches = listaPartidos.size() == (Constants.cMaxListItems +1);

		if (existsMoreMatches) {
			listaPartidos.remove(listaPartidos.size()-1);
		}
		return new PartidoBlock(listaPartidos, existsMoreMatches);	
	}
	
	@Transactional(readOnly=true)
	public PartidoBlock getPartidosPendientesTemporada(long idTemporada,int startindex, int count) throws InstanceNotFoundException{
		List<Partido> listaPartidos = partidodao.getRemainingMatchesBySeason(idTemporada, startindex, count );
		
		boolean existsMoreMatches = listaPartidos.size() == (Constants.cMaxListItems +1);

		if (existsMoreMatches) {
			listaPartidos.remove(listaPartidos.size()-1);
		}
		return new PartidoBlock(listaPartidos, existsMoreMatches);	
	}
	
	@Transactional(readOnly=true)
	public PartidoBlock getPartidosJugadosEquipo(long idEquipo,int startindex, int count) throws InstanceNotFoundException{
		List<Partido> listaPartidos = partidodao.getPlayedMatchesByTeam(idEquipo, startindex, count );		
		
		boolean existsMoreMatches = listaPartidos.size() == (Constants.cMaxListItems +1);

		if (existsMoreMatches) {
			listaPartidos.remove(listaPartidos.size()-1);
		}
		return new PartidoBlock(listaPartidos, existsMoreMatches);	
	}
	
	@Transactional(readOnly=true)
	public PartidoBlock getPartidosPendientesEquipo(long idEquipo,int startindex, int count) throws InstanceNotFoundException{
		List<Partido> listaPartidos = partidodao.getRemainingMatchesByTeam(idEquipo, startindex, count );		
		
		boolean existsMoreMatches = listaPartidos.size() == (Constants.cMaxListItems +1);

		if (existsMoreMatches) {
			listaPartidos.remove(listaPartidos.size()-1);
		}
		return new PartidoBlock(listaPartidos, existsMoreMatches);	
	}
	


	
//Tareas Admin:
	
	public void reiniciarPartido(long idPartido) throws InstanceNotFoundException,Exception{
		
		Partido p = partidodao.find(idPartido);
		for (ActaPartido ac : p.getActasPartido()){
			actapartidodao.remove(ac.getIdActaPartido());	
		}
		
		p.setActasPartido(null);
		
	}
	
	@Override
	@Transactional(readOnly=true)
	public List<MatchData> findGamesList(String busqueda, long idEquipo, boolean isValidado){
		return this.partidodao.findGamesList(busqueda, idEquipo, isValidado);
	}
	
	@Override
	public CustomGenericResponse sendValoracion(Equipo equipoValorador, long idPartido, Short valoracion, String comentario, boolean victoria){
		try {
			
			Partido partido = this.partidodao.find(idPartido);			
			ActaPartido acta = new ActaPartido();
			partido.setValidado(false);
			if (CollectionUtils.isNotEmpty(partido.getActasPartido())){
				for (ActaPartido ac : partido.getActasPartido()){
					//Si es nuestra propia valoración
					if ((ac.getEquipoValorador()!=null) && (equipoValorador.getUsuario()!=null) && (ac.getEquipoValorador().getIdEquipo()==equipoValorador.getIdEquipo())){
						acta = ac;
					}
					//Si hay valoración del otro usuario
					else if ((ac.getEquipoValorador()!=null) && (equipoValorador.getUsuario()!=null) && 
							((ac).getEquipoValorador().getIdEquipo()!=equipoValorador.getIdEquipo())){
						partido.setValidado(!ac.getVictoria().equals(victoria));	
						partido.setDiscrepancia(ac.getVictoria().equals(victoria));						
					}					
					
				}				
				

			}
			
			if (partido.getEquipoLocal().getIdEquipo()==equipoValorador.getIdEquipo()){
				acta.setUsuarioValorador(partido.getEquipoLocal().getUsuario());
				acta.setUsuarioValorado(partido.getEquipoVisitante().getUsuario());
				acta.setEquipoValorador(partido.getEquipoLocal());
				acta.setEquipoValorado(partido.getEquipoVisitante());
				
			}
			else if (partido.getEquipoVisitante().getIdEquipo()==equipoValorador.getIdEquipo()){				
				acta.setUsuarioValorador(partido.getEquipoVisitante().getUsuario());
				acta.setUsuarioValorado(partido.getEquipoLocal().getUsuario());
				acta.setEquipoValorador(partido.getEquipoVisitante());
				acta.setEquipoValorado(partido.getEquipoLocal());
			}	
			
			partido.setFecha(equipoValorador.getCompeticion().getActualDate());
			this.partidodao.update(partido);			
			acta.setPartido(partido);
			acta.setValoracion(valoracion);
			acta.setComentario(comentario);
			acta.setVictoria(victoria);
			
			this.actapartidodao.save(acta);
			
			partido.getActasPartido().add(acta);

			this.finanzasservice.ingresarPartido(acta);
			
			return new CustomGenericResponse(true, "Valoración enviada correctamente");
		} catch (InstanceNotFoundException e) {
			return new CustomGenericResponse(false, "Partido no disponible");
		}
	}
	
	@Override
	@Transactional(readOnly=true)
	public BigDecimal getValoracionLoyaltyUsuario(Long idUsuario, int loyaltyInterest){
		BigDecimal result = new BigDecimal(3);
		
		if (idUsuario!=null){
		
			final ResumenValoraciones valoracionesRecibidas = this.actapartidodao.findValoracionesRecibidasUsuario(idUsuario);		
			final ResumenValoraciones valoracionesRealizadas = this.actapartidodao.findValoracionesRealizadasUsuario(idUsuario);
			
			if ((valoracionesRecibidas.getNumeroValoraciones()>5) && (valoracionesRealizadas.getNumeroValoraciones()>5)){
				BigDecimal coeficienteRecibidas  = valoracionesRecibidas.getValoracionMedia().multiply(new BigDecimal(valoracionesRecibidas.getNumeroValoraciones()));
				BigDecimal coeficienteRealizadas = valoracionesRealizadas.getValoracionMedia().multiply(new BigDecimal(valoracionesRealizadas.getNumeroValoraciones()));
				
				BigDecimal dividendo = coeficienteRecibidas.add(coeficienteRealizadas);
				BigDecimal divisor = new BigDecimal (valoracionesRecibidas.getNumeroValoraciones()+valoracionesRealizadas.getNumeroValoraciones());
				
				BigDecimal valoracion = (dividendo.divide(divisor, RoundingMode.HALF_UP));
				
				if (loyaltyInterest!=5){
					final BigDecimal loyalty = new BigDecimal(loyaltyInterest);
					valoracion = valoracion.multiply(loyalty).divide(new BigDecimal(5), RoundingMode.HALF_UP);
				}
				
				return valoracion;
				
			}
			else {
				return result;
			}
		}
		
		return result;
	}
	
	@Override
	@Transactional(readOnly=true)
	public BigDecimal getValoracionEquipo(long idEquipo, int valoracionWinning ){
		
		
		try {
			BigDecimal valoracionMinima = (new BigDecimal(valoracionWinning)).multiply(new BigDecimal(0.2));
			
			Equipo e = this.equipodao.find(idEquipo);
			ResumenBalance balance = this.actapartidodao.findBalanceEquipo(idEquipo, e.getCompeticion().getIdTemporadaActual(), false);
			
			final BigDecimal totalPartidos = new BigDecimal(29);
			final BigDecimal winningInterest = new BigDecimal(valoracionWinning);
			BigDecimal valoracionEquipo = winningInterest.multiply(new BigDecimal(balance.getNumeroVictorias())).divide(totalPartidos, RoundingMode.HALF_UP);
			
			if (valoracionEquipo.compareTo(valoracionMinima)>0){
				return valoracionEquipo;
			}
			else {
				return valoracionMinima;
			}
			
		} catch (InstanceNotFoundException e1) {
			return new BigDecimal(0);
		}

		
	}
	
	@Override
	@Transactional(readOnly=true)
	public BigDecimal getValoracionesRecibidasManager(Long idUsuario){
		BigDecimal result = new BigDecimal(3);
		
		if (idUsuario!=null){
			result = this.actapartidodao.findValoracionesRecibidasUsuario(idUsuario).getValoracionMedia();
		}
		
		return result;
	}

	@Override
	@Transactional(readOnly=true)
	public BigDecimal getValoracionesRealizadasManager(Long idUsuario){
		BigDecimal result = new BigDecimal(3);
		
		if (idUsuario!=null){
			result = this.actapartidodao.findValoracionesRealizadasUsuario(idUsuario).getValoracionMedia();
		}
		
		return result;
	}
	
	
}
