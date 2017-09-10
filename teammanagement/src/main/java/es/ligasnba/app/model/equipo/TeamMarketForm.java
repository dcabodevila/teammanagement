package es.ligasnba.app.model.equipo;


public class TeamMarketForm {

	private Equipo equipo;
	private int salarySpace;
	private int numPlayers;
		
	
	
	
	public TeamMarketForm() {
	}
	
	
	public Equipo getEquipo() {
		return equipo;
	}
	public void setEquipo(Equipo equipo) {
		this.equipo = equipo;
	}
	
	public int getSalarySpace() {
		return salarySpace;
	}
	public void setTotalSalaries(int totalSalaries) {
		this.salarySpace = totalSalaries;
	}
	public int getNumPlayers() {
		return numPlayers;
	}
	public void setNumPlayers(int numPlayers) {
		this.numPlayers = numPlayers;
	}

	
	
	
}
