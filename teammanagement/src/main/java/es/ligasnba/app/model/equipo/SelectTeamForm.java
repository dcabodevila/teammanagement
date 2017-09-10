package es.ligasnba.app.model.equipo;

import java.util.ArrayList;
import java.util.List;

import es.ligasnba.app.model.dto.EquipoSeleccionDto;

public class SelectTeamForm {

	private short selectedIdTeam;	
	private long idCompeticion;
	private List<EquipoSeleccionDto> listAvailableTeams = new ArrayList<EquipoSeleccionDto>();
	private boolean success;
	private String message;
	
	public SelectTeamForm() {
		this.selectedIdTeam = 0;
		this.idCompeticion = 0;
	}
	
	public long getIdCompeticion() {
		return idCompeticion;
	}
	public void setIdCompeticion(long idCompeticion) {
		this.idCompeticion = idCompeticion;
	}
	public int getSelectedIdTeam() {
		return selectedIdTeam;
	}
	public void setSelectedIdTeam(short selectedIdTeam) {
		this.selectedIdTeam = selectedIdTeam;
	}
	public List<EquipoSeleccionDto> getListAvailableTeams() {
		return listAvailableTeams;
	}
	public void setListAvailableTeams(List<EquipoSeleccionDto> listAvailableTeams) {
		this.listAvailableTeams = listAvailableTeams;
	}
	public void setSuccess(boolean success) {
		this.success = success;
	}
	public boolean isSuccess() {
		return success;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
	
	
}
