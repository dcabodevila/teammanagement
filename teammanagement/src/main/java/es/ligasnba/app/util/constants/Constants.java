package es.ligasnba.app.util.constants;

import java.math.BigDecimal;

public interface Constants {
	public static final int cMinListItems = 0;
	public static final int cMaxListItems = 10;
	public static final int cMaxTeamsByUser = 3;
	public static final int cMinTeamsInCompetition = 0;
	public static final int cMaxTeamsInCompetition = 30;	
	
	public static final int cMinPlayersByTeam = 14;
	public static final int cMaxPlayersByTeam = 16;
	public static final int cMinCompetitionsByUser = 0;
	public static final int cMaxCompetitionsByUser = cMaxTeamsByUser;
	public static final int cMaxSeasonsPerCompetition = 3;

	public static final int cMediaJugadorTop=86;
	public static final int cMediaJugadorBueno=80;
	public static final int cMediaJugadorNormal=70;
	public static final int cMediaJugadorFlojo=65;
	
	public static final Short cTipoCompeticionDraft=1;
	public static final Short cTipoCompeticionReal=2;
	
	public static final Short cTipoEstadoCompeticionPretemporada=1;
	public static final Short cTipoEstadoCompeticionRS=2;
	public static final Short cTipoEstadoCompeticionPlayOffs=3;
	public static final Short cTipoEstadoCompeticionPostTemporada=4;
	public static final Short cTipoEstadoCompeticionDraft=5;
	
	//userValues
	public static final int cValueKickedUser = -200;
	public static final int cValueUserQuitsFromCompetition = -70;
	public static final int cValueKickedUserFromCompetition = -100;
	
	//Default Contract
	public static final int cDefaultContractYears = 1;
	public static final BigDecimal cDefaultPresupuesto = new BigDecimal(110000000);
	public static final BigDecimal cDefaultPresupuestoProximaTemporada = new BigDecimal(0);
	public static final BigDecimal cDefaultContractSalary= new BigDecimal(5000000);
	public static final BigDecimal cDefaultIncrement= new BigDecimal(5);
	public static final boolean cDefaultContractTeamOption= false;
	public static final boolean cDefaultContractPlayerOption= false;
	
	public static final int cRolNoUsuarioCompeticion = 0;
	public static final int cRolUsuarioCompeticion = 1;
	public static final int cRolAdminCompeticion = 2;
	
	public static final int cMaxMatchesByPage = 10;
	
	public static final String cMatchListJugados = "Partidos jugados";
	public static final String cMatchListPendientes = "Partidos sin jugar";
	
	public static final int cTotalMinutesOfGame = 48;
	public static final int cMaxMinutesForPlayerByGame = 38;
	
	public static final int cNumDaysForwardByRealDay = 1;
	
	public static final int cUserNameMaxLength = 50;
	public static final int cUserNameMinLength = 3;
	
	public static final int cUserPassMaxLength = 20;
	public static final int cUserPassMinLength = 5;
	
	
	//Estados de la competición
	
	public static final int cStateNotStarted=0;
	public static final int cStateStarted=1;
	public static final int cStatePostSeasonRenew=2;
	public static final int cStatePostSeasonFA=3;
	public static final int cStateFinished=4;
		
	//Traspasos
	public static final int cMaxTrades=20;
	
	//Límite salarial
	public static final BigDecimal cDefaultSalaryCap = new BigDecimal(58679000);
	public static final BigDecimal cDefaultSalaryTop = new BigDecimal(71600000);
	
	//Salarios
	public static final BigDecimal cMaxSumaSalariosUltimaTemporada = new BigDecimal(150000000);
	public static final BigDecimal cMinSalaryPlayer = new BigDecimal(500000);
	public static final BigDecimal cSalaryExcepcionNivelMedioUTax = new BigDecimal(8406000);
	public static final BigDecimal cSalaryExcepcionNivelMedioOTax = new BigDecimal(5192000);
	
	
	public static final BigDecimal cMaxSalaryPlayer = new BigDecimal(34682550);
	public static final int cMaxDaysPlayerContractDecisionRS = 3;
	public static final int cMaxDaysPlayerContractDecisionPostSeason = 1;
	public static final int cDefaultMoneyInterest = 10;
	public static final int cDefaultWinningInterest = 5;
	public static final int cDefaultLoyaltyInterest = 5;
	public static final double cDefaultContractYearsInterest = 0.7;
	public static final double cMaxDifferenceOfSalaries = 0.25;
	
	
	public static final int cTipoOfertaRenovacion = 1;
	public static final int cTipoOfertaOfertaFA = 2;
	public static final int cTipoOfertaOfertaSignAndTrade = 3;
	public static final int cTipoOfertaOfertaFANoRestringido = 4;
	
	//jugadores
	public static final int cRetireAge = 38;
	
	//Noticias
	
	public static final String cNewsAdminDraft = "Has creado la competición. Accede al apartado del Comisionado para realizar el Draft";
	public static final String cNewsCompetitionCreated = "La competición comenzará en cuanto realices el draft";
	public static final String cNewsJoinCompetition = "Bienvenido a la competición";
	public static final int cMaxNews = 4;
	public static final int cNewsMaxOldDate = -10;
	
	
	//URL
	
	public static final String cMaintenanceURL = "/maintenance";
	
	public static final String BEAN_NAME_CORE_DYNAMIC_PROPERTIES_BEAN = "CoreDynamicPropertiesBean";
	
	//Mail
	
	public static final String cMailLgn = "ligasnba";
	public static final String cMailPwd = "8rG7AX47";
	public static final String cMailWelcomeSubject = "Bienvenido a LigasNBA.es";
	public static final String cMailRememberSubject = "Cambio de contraseña en LigasNBA.es";
	
	
	//Dominio
	public static final String cDomainName = "http://77.27.51.210:8080";
	
	
	//Contratos
		
	public static final BigDecimal cDefaultDificultadContratoSeason = new BigDecimal(6.5);
	public static final BigDecimal cDefaultDificultadContratoPostSeason = new BigDecimal(6);
	public static final BigDecimal cDefaultDificultadContratoFA = new BigDecimal(5);
	
	//Valoraciones
	public static final BigDecimal cNotaSueloValoraciones = new BigDecimal(2.5); 
	
		
}
