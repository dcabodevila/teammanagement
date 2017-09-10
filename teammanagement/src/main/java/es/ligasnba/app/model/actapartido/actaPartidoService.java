package es.ligasnba.app.model.actapartido;

import es.ligasnba.app.model.partido.ResumenBalance;

public interface actaPartidoService {
	ResumenBalance findBalanceEquipo(long idEquipo, long idTemporada, Boolean isPlayOffs);

}
