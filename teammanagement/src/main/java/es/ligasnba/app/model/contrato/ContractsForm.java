package es.ligasnba.app.model.contrato;

import java.util.List;

import es.ligasnba.app.model.dto.EquipoSeleccionDto;

public class ContractsForm {

	private long idEquipo;
	private String nombreEquipo;
	private String logoEquipo;
	private List<EquipoSeleccionDto> listaEquipos;
	
	public String getNombreEquipo() {
		return nombreEquipo;
	}
	public void setNombreEquipo(String nombreEquipo) {
		this.nombreEquipo = nombreEquipo;
	}
	public String getLogoEquipo() {
		return logoEquipo;
	}
	public void setLogoEquipo(String logoEquipo) {
		this.logoEquipo = logoEquipo;
	}
	public long getIdEquipo() {
		return idEquipo;
	}
	public void setIdEquipo(long idEquipo) {
		this.idEquipo = idEquipo;
	}
	public List<EquipoSeleccionDto> getListaEquipos() {
		return listaEquipos;
	}
	public void setListaEquipos(List<EquipoSeleccionDto> listaEquipos) {
		this.listaEquipos = listaEquipos;
	}
	
}
