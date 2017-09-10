package es.ligasnba.app.util.constants;

import java.math.BigDecimal;
import java.util.HashMap;


import es.ligasnba.app.util.constants.playerNames;

public class DefaultContract {

	private int years;
	private BigDecimal baseSalary;
	private BigDecimal salaryIncrease;
	private boolean teamOption;
	private boolean playerOption;
	
	
	public DefaultContract(){
	};
	
	public DefaultContract(int y, BigDecimal sal, BigDecimal inc, boolean to, boolean po) {

		this.years = y;
		this.baseSalary= sal;
		this.salaryIncrease = inc;
		this.teamOption = to;
		this.playerOption = po;
		
	}	
	
	
	public static HashMap<String,DefaultContract> getDefaultContracts(){
		HashMap<String,DefaultContract> defaultContracts = new HashMap<String,DefaultContract>();

		DefaultContract contract = new DefaultContract( 1 , new BigDecimal(19000000), new BigDecimal(5),false,true);
		defaultContracts.put(playerNames.player1,contract);
		contract = new DefaultContract(2 , new BigDecimal(21400000), new BigDecimal(5),false,true);
		defaultContracts.put(playerNames.player8 , contract);
		contract = new DefaultContract(2 , new BigDecimal(18200000), new BigDecimal(5),false,true);
		defaultContracts.put(playerNames.player2 , contract);
		contract = new DefaultContract(2 , new BigDecimal(18200000), new BigDecimal(5),false,true);
		defaultContracts.put(playerNames.player3 , contract);
		contract = new DefaultContract(3 , new BigDecimal(17200000), new BigDecimal(5),false,true);
		defaultContracts.put(playerNames.player4 , contract);
		contract = new DefaultContract(1 , new BigDecimal(23200000), new BigDecimal(5),false,true);
		defaultContracts.put(playerNames.player8 , contract);
		return defaultContracts;
	}

	public int getYears() {
		return years;
	}
	public BigDecimal getBaseSalary() {
		return baseSalary;
	}
	public BigDecimal getSalaryIncrease() {
		return salaryIncrease;
	}
	public boolean isPlayerOption() {
		return playerOption;
	}
	public boolean isTeamOption() {
		return teamOption;
	}
	
	
}
