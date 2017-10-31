package es.ligasnba.app.model.finanzas;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.Transactional;

import es.ligasnba.app.model.actapartido.ActaPartido;
import es.ligasnba.app.model.actapartido.actaPartidoService;
import es.ligasnba.app.model.arquetipoEquipo.ArquetipoEquipo;
import es.ligasnba.app.model.arquetipoEquipo.ArquetipoEquipoDao;
import es.ligasnba.app.model.competicion.Competicion;
import es.ligasnba.app.model.contrato.ContractService;
import es.ligasnba.app.model.contrato.contractServiceImpl;
import es.ligasnba.app.model.equipo.Equipo;
import es.ligasnba.app.model.equipo.EquipoDao;
import es.ligasnba.app.model.partido.ResumenBalance;
import es.ligasnba.app.model.temporada.Temporada;
import es.ligasnba.app.model.temporada.seasonService;
import es.ligasnba.app.util.constants.Constants;
import es.ligasnba.app.util.exceptions.InstanceNotFoundException;

@Service("finanzasService")
@Transactional(rollbackFor = Exception.class)
public class finanzasServiceImpl implements finanzasService{

	@SuppressWarnings("unused")
	@Autowired
	private PlatformTransactionManager transactionManager;
	@Autowired
	private AsientoDao asientodao;
	@Autowired
	private seasonService seasonservice;
	@Autowired
	private actaPartidoService actapartidoservice;
	@Autowired
	private ContractService contractservice;
	@Autowired
	private EquipoDao equipodao;
	@Autowired
	private ArquetipoEquipoDao arquetipodao;
	
	
	public void setTransactionManager(PlatformTransactionManager t) {
		transactionManager = t;
	}
	public void setSeasonService(seasonService seasonservice) {
		this.seasonservice = seasonservice;
	}
	public void setActaPartidoService(actaPartidoService actapartidoservice) {
		this.actapartidoservice = actapartidoservice;
	}
	public void setContractService(ContractService contractservice) {
		this.contractservice = contractservice;
	}
	public void setEquipoDao(EquipoDao equipodao) {
		this.equipodao = equipodao;
	}
	public void setArquetipoDao(ArquetipoEquipoDao arquetipodao) {
		this.arquetipodao = arquetipodao;
	}

	public AsientoDao getAsientodao() {
		return asientodao;
	}


	public void setAsientodao(AsientoDao asientodao) {
		this.asientodao = asientodao;
	}
	
	@Override
	public void ingresarParteFijaPrincipioTemporada(final Equipo equipo){	
		final Temporada temporadaSiguiente = this.seasonservice.getTemporadaSiguienteCompeticion(equipo.getCompeticion());
		
		if (temporadaSiguiente!=null){
			nuevoAsiento(equipo, temporadaSiguiente, "Ingresos fijos paquete de ingresos "+ equipo.getArquetipoElegido().getNombre(), equipo.getCompeticion().getActualDate(), equipo.getArquetipoElegido().getIngresoFijo());
		}
	}
	
	@Async
	@Override
	public void ingresarPartido(final ActaPartido actaPartido){
		
		if ((actaPartido!=null) && (actaPartido.getPartido().getValidado())){
			final Temporada temporadaSiguiente = this.seasonservice.getTemporadaSiguienteCompeticion(actaPartido.getEquipoValorador().getCompeticion());	
					
			nuevoAsiento(actaPartido.getEquipoValorador(), temporadaSiguiente, "Ingresos partido jugado vs "+ actaPartido.getEquipoValorado().getnombre(), 
					actaPartido.getEquipoValorador().getCompeticion().getActualDate(), actaPartido.getPartido().isPlayoff() ? actaPartido.getEquipoValorador().getArquetipoElegido().getIngresoPartidoJugadoPO() : actaPartido.getEquipoValorador().getArquetipoElegido().getIngresoPartidoJugadoRS());
			
			nuevoAsiento(actaPartido.getEquipoValorado(), temporadaSiguiente, "Ingresos partido jugado vs "+ actaPartido.getEquipoValorador().getnombre(), 
					actaPartido.getEquipoValorado().getCompeticion().getActualDate(), actaPartido.getPartido().isPlayoff() ? actaPartido.getEquipoValorado().getArquetipoElegido().getIngresoPartidoJugadoPO() : actaPartido.getEquipoValorado().getArquetipoElegido().getIngresoPartidoJugadoRS());
			
			
			if (actaPartido.getVictoria()){
				nuevoAsiento(actaPartido.getEquipoValorador(), temporadaSiguiente, "Ingresos victoria vs "+ actaPartido.getEquipoValorado().getnombre(), 
						actaPartido.getEquipoValorador().getCompeticion().getActualDate(), actaPartido.getPartido().isPlayoff() ? actaPartido.getEquipoValorador().getArquetipoElegido().getIngresoPartidoGanadoPO() : actaPartido.getEquipoValorador().getArquetipoElegido().getIngresoPartidoGanadoRS());

			}
			else {
				nuevoAsiento(actaPartido.getEquipoValorado(), temporadaSiguiente, "Ingresos victoria vs"+ actaPartido.getEquipoValorador().getnombre(), 
						actaPartido.getEquipoValorado().getCompeticion().getActualDate(), actaPartido.getPartido().isPlayoff() ? actaPartido.getEquipoValorado().getArquetipoElegido().getIngresoPartidoGanadoPO() : actaPartido.getEquipoValorado().getArquetipoElegido().getIngresoPartidoGanadoRS());				
			}
			
//			if (!actaPartido.getPartido().isPlayoff()){
//				ingresarObjetivosTemporadaRegular(actaPartido.getEquipoValorador());
//				ingresarObjetivosTemporadaRegular(actaPartido.getEquipoValorado());
//			}
			
			
		}
		
	}
	
	@Async
	@Override
	public void ingresarObjetivosTemporadaRegular(final Equipo equipo){
		
		final Temporada temporadaSiguiente = this.seasonservice.getTemporadaSiguienteCompeticion(equipo.getCompeticion());
		if (temporadaSiguiente==null){
			return;
		}	
		
		final ResumenBalance balance = this.actapartidoservice.findBalanceEquipo(equipo.getIdEquipo(), equipo.getCompeticion().getIdTemporadaActual(), false);
		
		final ArquetipoEquipo arquetipo = equipo.getArquetipoElegido();
		
		if (balance.getNumeroVictorias().shortValue()>=arquetipo.getNumPartidosGanadosObjetivo1()){
			if (arquetipo.getIngresoObjetivo1().compareTo(BigDecimal.ZERO)>0){
				nuevoAsiento(equipo, temporadaSiguiente, "Ingresos Objetivo 1", equipo.getCompeticion().getActualDate(), arquetipo.getIngresoObjetivo1());								
			}
		}
		if (balance.getNumeroVictorias().shortValue()>=arquetipo.getNumPartidosGanadosObjetivo2()){
			if (arquetipo.getIngresoObjetivo2().compareTo(BigDecimal.ZERO)>0){
				nuevoAsiento(equipo, temporadaSiguiente, "Ingresos Objetivo 2", equipo.getCompeticion().getActualDate(), arquetipo.getIngresoObjetivo2());								
			}
		}
		
	}
	@Async
	@Override
	public void ingresarObjetivosPlayOffs(final Equipo equipo){
		
		//TODO: Hacer manualmente
		
//		final Temporada temporadaSiguiente = this.seasonservice.getTemporadaSiguienteCompeticion(equipo.getCompeticion());
//		if (temporadaSiguiente==null){
//			return;
//		}	
//		
//		final ResumenBalance balance = this.actapartidoservice.findBalanceEquipo(equipo.getIdEquipo(), equipo.getCompeticion().getIdTemporadaActual(), true);
//		final ArquetipoEquipo arquetipo = equipo.getArquetipoElegido();
//		//Si se clasificó
//		if ((balance.getNumeroVictorias().shortValue()>0) || (balance.getNumeroDerrotas().shortValue()>0)){
//			if (arquetipo.getIngresoClasificacionPO().compareTo(BigDecimal.ZERO)>0){
//				nuevoAsiento(equipo, temporadaSiguiente, "Ingresos Clasificación PlayOffs", equipo.getCompeticion().getActualDate(), arquetipo.getIngresoClasificacionPO());
//			}
//			
//			if (arquetipo.getIngresoRondasPOGanadas().compareTo(BigDecimal.ZERO)>0){
//								
//				
//				if (balance.getNumeroVictorias().shortValue()>=2){
//					nuevoAsiento(equipo, temporadaSiguiente, "Ingresos ganar ronda de PlayOff", equipo.getCompeticion().getActualDate(), arquetipo.getIngresoRondasPOGanadas());
//					if (arquetipo.getIdArquetipo()==4){
//						nuevoAsiento(equipo, temporadaSiguiente, "Ingresos Objetivo pasar 1ª ronda", equipo.getCompeticion().getActualDate(), arquetipo.getIngresoObjetivo3());
//					}
//
//				}
//				if (balance.getNumeroVictorias().shortValue()>=5){
//					nuevoAsiento(equipo, temporadaSiguiente, "Ingresos ganar ronda de PlayOff", equipo.getCompeticion().getActualDate(), arquetipo.getIngresoRondasPOGanadas());
//					if (arquetipo.getIdArquetipo()==4){
//						nuevoAsiento(equipo, temporadaSiguiente, "Ingresos Objetivo pasar a final de conferencia", equipo.getCompeticion().getActualDate(), new BigDecimal(18000000));
//					}
//					
//				}
//				if (balance.getNumeroVictorias().shortValue()>=8){
//					nuevoAsiento(equipo, temporadaSiguiente, "Ingresos ganar ronda de PlayOff", equipo.getCompeticion().getActualDate(), arquetipo.getIngresoRondasPOGanadas());
//				}
//				if (balance.getNumeroVictorias().shortValue()>=11){
//					nuevoAsiento(equipo, temporadaSiguiente, "Ingresos ganar ronda de PlayOff", equipo.getCompeticion().getActualDate(), arquetipo.getIngresoRondasPOGanadas());
//					if (arquetipo.getIdArquetipo()==4){
//						nuevoAsiento(equipo, temporadaSiguiente, "Ingresos campeón", equipo.getCompeticion().getActualDate(), arquetipo.getIngresoCampeon());
//					}
//
//				}
//				
//			}
//			
//		}
		
		
	}

	
	@Override
	public void nuevoAsiento(final Equipo equipo,final Temporada temporada, final String concepto,final Date fecha, final BigDecimal importe){
		
		Asiento asiento = new Asiento();
		asiento.setEquipo(equipo);
		
		  
		
		asiento.setTemporada(temporada);
		asiento.setConcepto(concepto);
		asiento.setFecha(fecha);
		asiento.setImporte(importe);
		
		this.asientodao.create(asiento);
		
		final boolean isTemporadaActual = temporada.getIdTemporada()==equipo.getCompeticion().getIdTemporadaActual();
		
		actualizarPresupuestoEquipo(equipo, isTemporadaActual);
	}
	
	@Override
	public void actualizarPresupuestoEquipo(Equipo e, final boolean isTemporadaActual){
		
		Temporada t = isTemporadaActual ? this.seasonservice.getTemporadaActualCompeticion(e.getCompeticion()) : this.seasonservice.getTemporadaSiguienteCompeticion(e.getCompeticion());			
		BigDecimal balance = getBalanceEquipoTemporada(e.getIdEquipo(),t.getIdTemporada());
		
		if (isTemporadaActual){
		
			e.setPresupuestoActual(balance);
		}
		else {
			e.setPresupuestoProximaTemporada(balance);
		}		
	}
	
	@Override
	public BigDecimal getBalanceEquipoTemporada(long idEquipo, long idTemporada){
		return this.asientodao.findBalanceByEquipoTemporada(idEquipo, idTemporada);
	}
	
	@Override
	public void hacerBalanceTemporadaCompeticion(Competicion c){
		for (Equipo equipo : c.getListaEquipos()){		
			hacerBalanceEquipo(equipo);
		}
	}
	
	private void hacerBalanceEquipo(Equipo e){
		
		Temporada temporadaActual = seasonservice.getTemporadaActualCompeticion(e.getCompeticion());
		final Long idTemporada = temporadaActual.getIdTemporada();
		
		BigDecimal sumaSalarial = this.contractservice.getSumSalaries(e.getIdEquipo(), idTemporada, true);		
		final BigDecimal multaLuxury = this.contractservice.getMultaLuxuryTax(sumaSalarial, e.getCompeticion().getLimiteTope());			

		nuevoAsiento(e, temporadaActual, "Liquidación salarios jugadores", e.getCompeticion().getActualDate(), sumaSalarial);
		
		if (multaLuxury.compareTo(BigDecimal.ZERO)>0){
			nuevoAsiento(e, temporadaActual, "Multa por pasarse de la tasa de lujo", e.getCompeticion().getActualDate(), multaLuxury);
		}
		
		final BigDecimal balance = getBalanceEquipoTemporada(e.getIdEquipo(),idTemporada );			
		BigDecimal resultado = balance.subtract(sumaSalarial);
		
		e.setPresupuestoActual(BigDecimal.ZERO);
		
		if (resultado.compareTo(BigDecimal.ZERO)>0){
			resultado = resultado.divide(new BigDecimal(2));
		}
		
		e.setPresupuestoProximaTemporada( e.getPresupuestoProximaTemporada().add(resultado));
		this.equipodao.update(e);
		

	}
	
	@Override
	public void ingresarPresupuestoInicial(Competicion c){
		final Temporada temporadaActual = seasonservice.getTemporadaActualCompeticion(c);
		for( Equipo e : c.getListaEquipos()){
			nuevoAsiento(e, temporadaActual, "Ingreso presupuesto inicial", e.getCompeticion().getActualDate(), Constants.cDefaultPresupuesto);
		}
	}
	
	@Override
	public List<AsientoDto> getListaAsientosEquipoTemporada(long idEquipo, long idTemporada){
		return this.asientodao.findAsientosByEquipoTemporada(idEquipo, idTemporada);
	}
	
	@Override
	public void setPaqueteIngresos(Equipo e, long idPaqueteIngresos) throws InstanceNotFoundException{
		final ArquetipoEquipo arquetipo = this.arquetipodao.find(idPaqueteIngresos);
		e.setArquetipoElegido(arquetipo);
		
		this.equipodao.update(e);
		
	}
	
	
	
}
