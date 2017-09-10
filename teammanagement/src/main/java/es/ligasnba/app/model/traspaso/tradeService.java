package es.ligasnba.app.model.traspaso;


import java.math.BigDecimal;
import java.util.List;

import es.ligasnba.app.model.jugador.Jugador;
import es.ligasnba.app.util.exceptions.InstanceNotFoundException;
import es.ligasnba.app.util.jqgrid.CustomGenericResponse;

public interface tradeService {
	public Traspaso getTrade(long id) throws InstanceNotFoundException;
	public Traspaso createTradeOffer(long idEquipoOrigen, long idEquipoDestino, List<Jugador> jugadoresOfrecidos,List<Jugador> jugadoresRecibidos, String comentario, BigDecimal importe) throws InstanceNotFoundException;
	public CustomGenericResponse isValidTradeTomarDecisionContrato(long idTraspaso) throws InstanceNotFoundException;
	public Traspaso sendTradeOffer(long idTraspaso) throws InstanceNotFoundException, Exception;
	public TraspasoBlock getTraspasosOfrecidos(long idTeam,int startindex,int count);
	public TraspasoBlock getTraspasosRecibidos(long idTeam,int startindex,int count);
	public CustomGenericResponse rejectTradeOffer(long idTraspaso) throws InstanceNotFoundException;
	CustomGenericResponse isValidTradeIdsJugadores(long idTeamFrom, long idTeamTo, List<Long> idsJugadoresOfrecidos,
			List<Long> idsJugadoresRecibidos) throws InstanceNotFoundException;
	void removeTrade(long idTraspaso) throws InstanceNotFoundException;
	Traspaso createTradeOfferIdsJugadores(long idEquipoOrigen, long idEquipoDestino, List<Long> idsJugadoresOfrecidos,
			List<Long> idsJugadoresRecibidos, String comentario, BigDecimal importe) throws InstanceNotFoundException;
	CustomGenericResponse acceptTradeOffer(long idTraspaso, boolean ejecutarTraspasoST) throws Exception;
	CustomGenericResponse isValidTradeEnviarTrade(long idTraspaso, boolean isSignAndTrade)
			throws InstanceNotFoundException;

	
}
