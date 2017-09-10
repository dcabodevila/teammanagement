package es.ligasnba.app.model.competicion;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;
import javax.validation.constraints.Max;

import org.hibernate.validator.constraints.NotEmpty;

import es.ligasnba.app.model.equipo.Equipo;

public class JoinCompetitionForm {
	@NotEmpty
	private String competitionName;

	private String password;
	
	private List<Equipo> availableTeams= new ArrayList<Equipo>();
	
	private long selectedIdTeam; 	
	
	public String getCompetitionName() {
		return competitionName;
	}
	public void setCompetitionName(String competitionName) {
		this.competitionName = competitionName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public List<Equipo> getAvailableTeams() {
		return availableTeams;
	}
	public void setAvailableTeams(List<Equipo> availableTeams) {
		this.availableTeams = availableTeams;
	}
	public long getSelectedIdTeam() {
		return selectedIdTeam;
	}
	public void setSelectedIdTeam(long selectedIdTeam) {
		this.selectedIdTeam = selectedIdTeam;
	}
	
}
