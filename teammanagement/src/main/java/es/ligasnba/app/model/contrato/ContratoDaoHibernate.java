package es.ligasnba.app.model.contrato;

import es.ligasnba.app.model.generic.GenericDaoHibernate;
import es.ligasnba.app.model.jugador.PlayerData;
import es.ligasnba.app.model.jugadordefault.JugadorDefault;
import es.ligasnba.app.model.lineacontrato.LineaContrato;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import es.ligasnba.app.util.exceptions.InstanceNotFoundException;

import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;

@Repository("ContratoDao")
public class ContratoDaoHibernate extends GenericDaoHibernate<Contrato, Long> implements ContratoDao {

	@SuppressWarnings("unchecked")
	public List<LineaContrato> findLineaContratoByContrato(long idContrato, int startindex, int count)
			throws InstanceNotFoundException {

		List<LineaContrato> lc = getSession()
				.createQuery(
						"SELECT lc FROM LineaContrato lc, Contrato c WHERE c.idContrato=:idContrato AND LineaContrato.contrato = c")
				.setParameter("idContrato", idContrato).setFirstResult(startindex).setMaxResults(count).list();

		if (lc == null)
			throw new InstanceNotFoundException(idContrato, Contrato.class.getName());
		else
			return lc;

	}

	@SuppressWarnings("unchecked")
	public List<Contrato> findByTeamId(long teamId, int startindex, int count) throws InstanceNotFoundException {

		List<Contrato> lc = getSession()
				.createQuery("SELECT c FROM Contrato c, Equipo e WHERE e.idEquipo=:teamId AND c.equipo = e")
				.setParameter("teamId", teamId).setFirstResult(startindex).setMaxResults(count).list();

		if (lc == null)
			throw new InstanceNotFoundException(teamId, Contrato.class.getName());
		else
			return lc;

	}

	@SuppressWarnings("unchecked")
	public List<Contrato> findBySignedTeamId(long teamId, int startindex, int count) throws InstanceNotFoundException {

		List<Contrato> lc = getSession()
				.createQuery(
						"SELECT c FROM Contrato c, Equipo e WHERE e.idEquipo=:teamId AND c.equipo = e AND c.firmado=true")
				.setParameter("teamId", teamId).setFirstResult(startindex).setMaxResults(count).list();

		if (lc == null)
			throw new InstanceNotFoundException(teamId, Contrato.class.getName());
		else
			return lc;

	}

	public Contrato findSignedByPlayerAndTeam(long idEquipo, long idJugador) throws InstanceNotFoundException {

		Contrato c = (Contrato) getSession()
				.createQuery(
						"SELECT c FROM Contrato c, Equipo e, Jugador j WHERE e.idEquipo = :idEquipo AND j.idJugador=:idJugador AND c.equipo=e AND c.jugador=j AND c.firmado=true ")
				.setParameter("idEquipo", idEquipo).setParameter("idJugador", idJugador).uniqueResult();

		if (c == null)
			throw new InstanceNotFoundException(idEquipo, Contrato.class.getName());
		else
			return c;
	}

	@Override
	public BigDecimal getSumSalaries(long idEquipo, long idTemporada, boolean pendiente) {
		
		return (BigDecimal) getSession()
				.createSQLQuery(
						"SELECT COALESCE(SUM(lc.salario),0) FROM jugador j INNER JOIN contrato c on c.idContrato=j.idContrato INNER JOIN lineacontrato lc on lc.idContrato = c.idContrato INNER JOIN temporada t on lc.idTemporada = t.idTemporada WHERE j.idEquipo=:idEquipo AND t.idTemporada = :idTemporada AND (c.firmado OR c.pendiente=:pendiente)")
				.setParameter("idEquipo", idEquipo).setParameter("idTemporada", idTemporada)
				.setParameter("pendiente", pendiente).uniqueResult();
		
//		return (BigDecimal) getSession()
//				.createQuery(
//						"SELECT SUM(lc.salario) FROM jugador j , contrato c, lineacontrato lc, temporada t  WHERE c.idContrato=j.idContrato AND lc.idContrato = c.idContrato AND lc.idTemporada = t.idTemporada AND j.idEquipo=:idEquipo AND t.idTemporada = :idTemporada AND (c.firmado OR c.pendiente=:pendiente) AND lc.salario is not null")
//				.setParameter("idEquipo", idEquipo).setParameter("idTemporada", idTemporada)
//				.setParameter("pendiente", pendiente).uniqueResult();		

	}

	@SuppressWarnings("unchecked")
	public List<Contrato> findExpiringContracts(long idCompeticion) {

		// List<Contrato> listaContratos = getSession().createQuery("SELECT c
		// FROM Contrato c, Equipo e, Competicion com WHERE
		// com.idCompeticion=:idCompeticion AND e.competicion=com AND c.equipo=e
		// AND 1=(SELECT Count(linea) FROM LineaContrato linea WHERE
		// linea.contrato = c)
		// ").setParameter("idCompeticion",idCompeticion).list();
		List<Contrato> listaContratos = getSession()
				.createQuery(
						"SELECT c FROM Contrato c, Equipo e, Competicion com WHERE com.idCompeticion=:idCompeticion AND e.competicion=com AND c.equipo=e AND 1=(SELECT Count(linea) FROM LineaContrato linea WHERE linea.contrato=c AND linea.temporada.idTemporada>=com.idTemporadaActual) ")
				.setParameter("idCompeticion", idCompeticion).list();

		if (listaContratos == null)
			return new ArrayList<Contrato>();

		return listaContratos;

	}

	@Override
	public BigDecimal findSumaSalarialJugadoresTemporada(List<Long> idsJugadores, long idTemporada) {

		BigDecimal sumaSalariosJugadores = (BigDecimal) getSession()
				.createSQLQuery(
						"SELECT COALESCE(SUM(lc.salario),0) FROM jugador j INNER JOIN contrato c on c.idContrato=j.idContrato INNER JOIN lineacontrato lc on lc.idContrato = c.idContrato INNER JOIN temporada t on lc.idTemporada = t.idTemporada WHERE j.idJugador in (:idsJugadores)	AND t.idTemporada = :idTemporada AND c.firmado	")
				.setParameterList("idsJugadores", idsJugadores).setParameter("idTemporada", idTemporada).uniqueResult();

		return sumaSalariosJugadores;

	}

	public int getContractYearsRemaining(long idJugador) {

		int yearsRemaining = ((Number) getSession()
				.createQuery(
						"SELECT Count(linea) FROM LineaContrato linea, Contrato c, Jugador j WHERE j.idJugador=:idJugador AND j.contrato=c AND linea.contrato=c  AND linea.temporada.idTemporada>=j.competicion.idTemporadaActual ")
				.setParameter("idJugador", idJugador).uniqueResult()).intValue();

		return yearsRemaining;

	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ContractData> findContractDataByTeamID(long idEquipo) {

		List<ContractData> j = (List<ContractData>) ((SQLQuery) getSession()
				.getNamedQuery("FIND_CONTRACT_DATA_BY_ID_EQUIPO").setParameter("idEquipo", idEquipo))
						.setResultTransformer(Transformers.aliasToBean(ContractData.class)).list();
		if (j == null) {
			return new ArrayList<ContractData>();
		}
		return j;

	}

	@Override
	public void registrarValoracionOferta(ValoracionOfertaContratoDto valoracionOferta) {

		getSession()
				.createSQLQuery(
						"INSERT INTO valoracion_ofertas (ID_CONTRATO, ID_COMPETICION, ID_EQUIPO, NOMBRE_EQUIPO, ID_JUGADOR, NOMBRE_JUGADOR, SALARIO_T1, SALARIO_T2, "
								+ " SALARIO_T3, VALORACION_MONEY, MONEY_INTEREST, VALORACION_WINNING, WINNING_INTEREST, VALORACION_LOYALTY, LOYALTY_INTEREST, VALORACION_GLOBAL, VALORACION_GLOBAL_EXIGIDA, VALORACION_MAXIMA, VALORACION_GLOBAL_PREVIA, ES_SIGN_AND_TRADE, FECHA) "
								+ " VALUES (:idContrato, :idCompeticion, :idEquipo, :nombreEquipo, :idJugador, :nombreJugador, :salary1, :salary2, :salary3, :valoracionMoney, :moneyInterest, :valoracionWinning, :winningInterest, :valoracionLoyalty, :loyaltyInterest, :valoracionGlobal, :valoracionGlobalExigida, :valoracionMaxima, :valoracionGlobalPrevia , :esSignAndTrade, :fecha);")
				.setParameter("idContrato", valoracionOferta.getIdContrato())
				.setParameter("idCompeticion", valoracionOferta.getIdCompeticion())
				.setParameter("idEquipo", valoracionOferta.getIdEquipo())
				.setParameter("nombreEquipo", valoracionOferta.getNombreEquipo())
				.setParameter("idJugador", valoracionOferta.getIdJugador())
				.setParameter("nombreJugador", valoracionOferta.getNombreJugador())
				.setParameter("salary1", valoracionOferta.getSalarioTemporada1())
				.setParameter("salary2", valoracionOferta.getSalarioTemporada2())
				.setParameter("salary3", valoracionOferta.getSalarioTemporada3())
				.setParameter("valoracionMoney", valoracionOferta.getValoracionMoney())
				.setParameter("moneyInterest", valoracionOferta.getMoneyInterest())
				.setParameter("valoracionWinning", valoracionOferta.getValoracionWinning())
				.setParameter("winningInterest", valoracionOferta.getWinningInterest())
				.setParameter("valoracionLoyalty", valoracionOferta.getValoracionLoyalty())
				.setParameter("loyaltyInterest", valoracionOferta.getLoyaltyInterest())
				.setParameter("valoracionGlobal", valoracionOferta.getValoracionGlobal())
				.setParameter("valoracionGlobalExigida", valoracionOferta.getValoracionGlobalExigida())
				.setParameter("valoracionMaxima", valoracionOferta.getValoracionMaxima())
				.setParameter("valoracionGlobalPrevia", valoracionOferta.getValoracionGlobalPrevia())				
				.setParameter("esSignAndTrade", valoracionOferta.isEsSignAndTrade())
				.setParameter("fecha", valoracionOferta.getFecha()).executeUpdate();

	}

}
