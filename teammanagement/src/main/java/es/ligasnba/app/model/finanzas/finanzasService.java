package es.ligasnba.app.model.finanzas;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import es.ligasnba.app.model.actapartido.ActaPartido;
import es.ligasnba.app.model.competicion.Competicion;
import es.ligasnba.app.model.equipo.Equipo;
import es.ligasnba.app.model.temporada.Temporada;

public interface finanzasService {

	void nuevoAsiento(Equipo equipo, Temporada temporada, String concepto, Date fecha, BigDecimal importe);

	void ingresarParteFijaPrincipioTemporada(Equipo equipo);

	void ingresarPartido(ActaPartido actaPartido);

	void ingresarObjetivosPlayOffs(Equipo equipo);

	BigDecimal getBalanceEquipoTemporada(long idEquipo, long idTemporada);

	void hacerBalanceTemporadaCompeticion(Competicion c);

	void ingresarPresupuestoInicial(Competicion c);

	List<AsientoDto> getListaAsientosEquipoTemporada(long idEquipo, long idTemporada);
}
