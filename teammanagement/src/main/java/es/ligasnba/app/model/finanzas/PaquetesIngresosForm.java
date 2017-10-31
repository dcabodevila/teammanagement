package es.ligasnba.app.model.finanzas;

import java.util.List;
import es.ligasnba.app.model.arquetipoEquipo.ArquetipoEquipoDto;

public class PaquetesIngresosForm {
	
	private Long idArquetipoSeleccionado;
	private List<ArquetipoEquipoDto> arquetiposDisponibles;

	public Long getIdArquetipoSeleccionado() {
		return idArquetipoSeleccionado;
	}

	public void setIdArquetipoSeleccionado(Long idArquetipoSeleccionado) {
		this.idArquetipoSeleccionado = idArquetipoSeleccionado;
	}

	public List<ArquetipoEquipoDto> getArquetiposDisponibles() {
		return arquetiposDisponibles;
	}

	public void setArquetiposDisponibles(List<ArquetipoEquipoDto> arquetiposDisponibles) {
		this.arquetiposDisponibles = arquetiposDisponibles;
	}

	
	
}
