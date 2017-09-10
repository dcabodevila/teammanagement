package es.ligasnba.app.model.actapartido;

import java.util.List;
import es.ligasnba.app.model.generic.GenericDao;
import es.ligasnba.app.model.partido.ResumenBalance;
import es.ligasnba.app.model.partido.ResumenValoraciones;
import es.ligasnba.app.model.partido.ValoracionData;

public interface ActaPartidoDao extends GenericDao<ActaPartido,Long>{		
	
	public List<ActaPartido> findByPartido(long idPartido);

	ValoracionData findValoracionByPartidoUsuario(long idPartido, long idUsuario);

	ResumenValoraciones findValoracionesRecibidasUsuario(long idUsuario);

	ResumenValoraciones findValoracionesRealizadasUsuario(long idUsuario);

	ResumenBalance findBalanceEquipo(long idEquipo, long idTemporada, Boolean isPlayOffs);

	ResumenBalance findBalanceUsuario(long idUsuario, Boolean isPlayOffs);

}
