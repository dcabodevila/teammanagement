package es.ligasnba.app.model.contrato;

import java.math.BigDecimal;
import java.util.List;

import es.ligasnba.app.util.exceptions.InstanceNotFoundException;
import es.ligasnba.app.model.generic.GenericDao;
import es.ligasnba.app.model.lineacontrato.LineaContrato;

public interface ContratoDao extends GenericDao<Contrato,Long>{
	public List<LineaContrato> findLineaContratoByContrato(long idContrato,int startindex,int count) throws InstanceNotFoundException;
	public List<Contrato> findByTeamId(long teamId, int startindex, int count) throws InstanceNotFoundException;
	public List<Contrato> findBySignedTeamId(long teamId, int startindex, int count) throws InstanceNotFoundException;
	public Contrato findSignedByPlayerAndTeam(long idEquipo,long idJugador)throws InstanceNotFoundException;
	public List<Contrato> findExpiringContracts(long idCompeticion);
	public int getContractYearsRemaining(long idJugador);
	BigDecimal findSumaSalarialJugadoresTemporada(List<Long> idsJugadores, long idTemporada);
	BigDecimal getSumSalaries(long idEquipo, long idTemporada, boolean pendiente);
	List<ContractData> findContractDataByTeamID(long idEquipo);
	void registrarValoracionOferta(ValoracionOfertaContratoDto valoracionOferta);

}
