package es.ligasnba.app.model.competicion;


import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;
import javax.validation.constraints.Max;

import org.hibernate.validator.constraints.NotEmpty;


public class CreateCompetitionForm {
	@NotEmpty
	private String competitionName;
	@NotEmpty
	@Digits(integer=2, fraction=0)
	@Min(2)
	@Max(30)
	private int numTeams;	
	private String password;
	
	
	public String getCompetitionName() {
		return competitionName;
	}
	public int getNumTeams() {
		return numTeams;
	}
	public void setCompetitionName(String competitionName) {
		this.competitionName = competitionName;
	}
	public void setNumTeams(int numTeams) {
		this.numTeams = numTeams;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}

}
