package es.ligasnba.app.model.contrato;

import java.math.BigDecimal;
import java.util.List;

import es.ligasnba.app.model.equipo.Equipo;
import es.ligasnba.app.model.jugador.Jugador;
import es.ligasnba.app.model.jugador.PlayerContractData;
import es.ligasnba.app.model.jugadordefault.JugadorDefault;
import es.ligasnba.app.model.lineacontrato.LineaContrato;
import es.ligasnba.app.model.lineacontrato.LineaContratoBlock;
import es.ligasnba.app.model.temporada.Temporada;
import es.ligasnba.app.util.exceptions.InstanceNotFoundException;
import es.ligasnba.app.util.jqgrid.CustomGenericResponse;

public interface ContractService {
	public Contrato findById(long id) throws InstanceNotFoundException;
	public LineaContratoBlock getLineasContrato(long idContrato,int startindex,int count) throws InstanceNotFoundException; 
	public Contrato createEmptyContract(long idTeam, long idPlayer) throws InstanceNotFoundException;
	public Contrato generateContract(long idTeam, long idPlayer, long idTemporada, int years, BigDecimal baseSalary, BigDecimal salaryIncrease, boolean teamOption, boolean playerOption) throws InstanceNotFoundException, Exception;
	public Contrato generateDefaultContract(long idTeam, long idPlayer) throws InstanceNotFoundException, Exception;
	public ContratoBlock findContractsByTeam(long idTeam,int startindex,int count) throws InstanceNotFoundException;
	public ContratoBlock findSignedContractsByTeam(long idTeam,int startindex,int count) throws InstanceNotFoundException;
	public LineaContrato createLineContract(long idContrato,long idTemporada, BigDecimal salario, boolean teamOption, boolean playerOption) throws InstanceNotFoundException;
	public LineaContrato getLineContractBySeason(long idContrato, long idTemporada) throws InstanceNotFoundException;
	public Contrato signContract(long idPlayer, long idContract) throws InstanceNotFoundException;
	public void removeContract(long idContrato) throws InstanceNotFoundException;
	public BigDecimal getSumSalaries(long idEquipo, long idTemporada, boolean pendiente);
	public List<Contrato> findExpiringContracts(long idCompeticion);
	public boolean clearExpiringContracts(List<Contrato> listaContratos);
	public PlayerContractData getPlayerResignContractData(long idPlayer, long idEquipo) throws InstanceNotFoundException;
	public int getContractYearsRemaining(long idJugador);
	Contrato generateDefaultPlayerContract(Equipo e, Jugador j, JugadorDefault jd) throws InstanceNotFoundException;
	Contrato generateContractOffer(long idTeam, long idPlayer, long idTemporada, int years, BigDecimal baseSalary,
			BigDecimal salaryIncrease, boolean teamOption, boolean playerOption, boolean useMidLevelException)
					throws InstanceNotFoundException, Exception;
	Contrato generateContractOfferSignAndTrade(long idTeam, long idPlayer, long idTemporada, int years,
			BigDecimal baseSalary, BigDecimal salaryIncrease, boolean teamOption, boolean playerOption,
			boolean useMidLevelException, List<Long> idsJugadores) throws InstanceNotFoundException, Exception;
	boolean isContratoTemporadaActual(Contrato c);
	CustomGenericResponse generateGlobalContractOffer(long idTeam, long idPlayer, long idTemporada, int years, BigDecimal baseSalary,
			BigDecimal salaryIncrease, boolean teamOption, boolean playerOption, boolean useMidLevelException,
			List<Long> listaJugadoresST) throws InstanceNotFoundException, Exception;
	ResultadoValidacionContratoOfrecidoDto isValidOfertaContrato(long idEquipo, Contrato c)
			throws InstanceNotFoundException;
	Contrato firmaContrato(Contrato contrato) throws InstanceNotFoundException;
	BigDecimal getSumaSalarialTemporadaSalarioEntranteSaliente(Equipo e, Long idTemporada,
			List<Jugador> listaJugadoresEntran, List<Jugador> listaJugadoresSalen);
	BigDecimal getMultaLuxuryTax(BigDecimal sumaSalarial, BigDecimal luxuryTax);
	BigDecimal getSumaSalarialByIdsJugadores(List<Jugador> listaJugadores, long idTemporada);
	void deleteContract(long idContrato) throws InstanceNotFoundException;
	BigDecimal getMinSalary(Jugador j);
	Contrato anadeLineasContrato(Contrato contrato, boolean isPendiente);
	PlayerContractData getContractDataAgenteLibreNoRestringido(Jugador j, Equipo e) throws InstanceNotFoundException;
	PlayerContractData getContractDataAgente(Jugador j, Equipo e) throws InstanceNotFoundException;
	PlayerContractData getContractDataAgenteLibrePropio(Jugador j, Temporada t) throws InstanceNotFoundException;
	TeamContractData findTeamContractDataByIdEquipo(long idEquipo) throws InstanceNotFoundException;
	void registrarValoracionOferta(ValoracionOfertaContratoDto valoracionOferta);		
}
