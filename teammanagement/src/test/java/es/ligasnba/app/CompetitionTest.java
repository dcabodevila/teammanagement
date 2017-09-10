package es.ligasnba.app;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import es.ligasnba.app.model.actapartido.actaPartidoService;
import es.ligasnba.app.model.clasificacion.ClasificacionBlock;
import es.ligasnba.app.model.competicion.Competicion;
import es.ligasnba.app.model.competicion.CompeticionBlock;
import es.ligasnba.app.model.competicion.competitionService;
import es.ligasnba.app.model.contrato.Contrato;
import es.ligasnba.app.model.contrato.ContratoBlock;
import es.ligasnba.app.model.contrato.ContractService;
import es.ligasnba.app.model.equipo.Equipo;
import es.ligasnba.app.model.equipo.EquipoBlock;
import es.ligasnba.app.model.equipo.EquipoDao;
import es.ligasnba.app.model.equipo.teamService;
import es.ligasnba.app.model.equipodefault.EquipoDefault;
import es.ligasnba.app.model.jugador.Jugador;
import es.ligasnba.app.model.jugador.JugadorBlock;
import es.ligasnba.app.model.jugador.playerService;
import es.ligasnba.app.model.jugadordefault.JugadorDefault;
import es.ligasnba.app.model.temporada.Temporada;
import es.ligasnba.app.model.temporada.seasonService;
import es.ligasnba.app.model.traspaso.Traspaso;
import es.ligasnba.app.model.traspaso.TraspasoBlock;
import es.ligasnba.app.model.traspaso.tradeService;
import es.ligasnba.app.model.partido.Partido;
import es.ligasnba.app.model.partido.PartidoBlock;
import es.ligasnba.app.model.partido.matchService; 
import es.ligasnba.app.model.usuario.*;
import es.ligasnba.app.util.exceptions.DuplicateInstanceException;
import es.ligasnba.app.util.exceptions.InstanceNotFoundException;

import java.io.IOException;
import java.lang.Long;
import java.math.BigDecimal;
import java.net.URL;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Currency;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import es.ligasnba.app.util.constants.Constants;
import es.ligasnba.app.util.constants.teams;
import es.ligasnba.app.util.exceptions.*;
import static es.ligasnba.app.test.util.GlobalNames.SPRING_CONFIG_TEST_FILE;
import static es.ligasnba.app.model.util.GlobalNames.SPRING_CONFIG_FILE;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import es.ligasnba.app.util.Log;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {SPRING_CONFIG_FILE, SPRING_CONFIG_TEST_FILE })
@Transactional
public class CompetitionTest {
    
	@Autowired
	private teamService teamservice;
	@Autowired
	private userService userservice;
	@Autowired
	private playerService playerservice;
	@Autowired
	private ContractService contractservice;
	@Autowired
	private competitionService competitionservice;
	@Autowired
	private tradeService tradeservice;
	@Autowired
	private seasonService seasonservice;
	@Autowired
	private matchService matchservice;	
	@Autowired
	private actaPartidoService actapartidoservice;	
	@Autowired
	private EquipoDao equipodao;
	
	public void setEquipoDao(EquipoDao equipodao) {
		this.equipodao = equipodao;
	}
	

	public void initializeBeans(){
		try{
			
			String[] configlocations = {"classpath:/teammanagement-spring-config.xml","classpath:/teammanagement-spring-config-test.xml"};
			ApplicationContext context = new ClassPathXmlApplicationContext(configlocations);
			playerservice = (playerService) context.getBean("playerService");
			teamservice = (teamService) context.getBean("teamService");
			userservice=(userService) context.getBean("userService");
			contractservice=(ContractService) context.getBean("contractService");
			competitionservice=(competitionService) context.getBean("competitionService");
			seasonservice=(seasonService) context.getBean("seasonService");
			tradeservice=(tradeService) context.getBean("tradeService");
			matchservice=(matchService) context.getBean("matchService");
			actapartidoservice = (actaPartidoService) context.getBean("actaPartidoService");

		}catch (Exception e){
			System.out.println("No instanciado");			
		}
	}
	
	@Test 
	public void test(){
		
	}
	@Test
	public void CompetitionCreate() throws Exception{

		int numEquipos = 10;
		
		Usuario u1 = userservice.userRegister("usuario2", "123456", "u@gmail.com");

		Competicion c = competitionservice.createCompetitionWithoutTeams("Liga A", "www.2k.com", u1.getIdUsuario(),new Date(), new Date(), new BigDecimal(0), new BigDecimal(0));

		Competicion com2 = competitionservice.findByName("Liga A");
		
		Equipo e = competitionservice.teamRegister(teams.cavaliers, c.getIdCompeticion());
		
		userservice.joinUserCompetition(u1.getIdUsuario(), e.getIdEquipo(), c.getIdCompeticion());
		
		assert(c.getIdCompeticion()==com2.getIdCompeticion());		
		assert(teamservice.getTeamsOfCompetition(c.getIdCompeticion(), 0, numEquipos).getEquipos().size() == numEquipos);
		assert(competitionservice.getSeasonsRemaining(c.getIdCompeticion()).size() == Constants.cMaxSeasonsPerCompetition);
		assert(competitionservice.getAdminCompetitionsOfUser(u1.getIdUsuario(),0,2).getCompeticiones().size()==1);
		assert(u1.getListaCompeticiones().size()==1);
		assert(com2.getListaUsuarios().size()==1);
		
		
	}
	
	@Test  
	public void CompetitionKickUser() throws Exception{

		int numEquipos = 10;
		
		Usuario u1 = userservice.userRegister("usuario1", "123456", "u@gmail.com");
		Usuario u2 = userservice.userRegister("usuario2", "123456", "u@gmail.com");
		Competicion c = competitionservice.createCompetitionWithoutTeams("Liga A", "www.2k.com", u1.getIdUsuario(),new Date(), new Date(), new BigDecimal(0), new BigDecimal(0));
		
		
		
		userservice.joinUserCompetition(u1.getIdUsuario(), c.getListaEquipos().get(0).getIdEquipo() , c.getIdCompeticion());
		userservice.joinUserCompetition(u2.getIdUsuario(), c.getListaEquipos().get(1).getIdEquipo() , c.getIdCompeticion());
		
		
		
		assert(c.getListaUsuarios().size() == 2);
		assert(u2.getListaEquipos().size() == u2.getListaCompeticiones().size());
		assert(competitionservice.getAvailableTeams(c.getIdCompeticion()).size() ==(numEquipos - c.getListaUsuarios().size()) );

		
		try{
		
			assert(competitionservice.banUserFromCompetition(u1.getIdUsuario(), c.getIdCompeticion()) == false);
			assert(competitionservice.banUserFromCompetition(u2.getIdUsuario(), c.getIdCompeticion()) == true);

		}catch(Exception e){
			e.printStackTrace();
		}
		
		
		assert(u2.getListaCompeticiones().size() == 0);
		assert (c.getListaEquipos().size() == numEquipos);
		assert (c.getListaUsuarios().size() == 1);

			
	
	}	
//	@SuppressWarnings("unused")	
//	@Test (expected =  DuplicateInstanceException.class)
//	public void CompetitionCreateDuplicate() throws Exception{
//
//		int numEquipos = 10;
//		
//		Usuario u1 = userservice.userRegister("usuario2", "123456", "u@gmail.com");
//
//		Competicion c = competitionservice.createCompetition("Liga A", "www.2k.com", u1.getIdUsuario(),numEquipos,1);
//		Competicion d = competitionservice.createCompetition("Liga A", "aaaa", u1.getIdUsuario(),numEquipos,1);
//	
//	}

	@Test
	public void CompetitionAddRemoveTeams() throws Exception{
		
		Usuario u1 = userservice.userRegister("usuario1", "123456", "u@gmail.com");
		Usuario u2 = userservice.userRegister("usuario2", "123456", "u@gmail.com");
		
		Competicion c = competitionservice.createCompetitionWithoutTeams("Liga 123", "www.2k.com", u2.getIdUsuario(),new Date(), new Date(), new BigDecimal(0), new BigDecimal(0));
		
				
		userservice.joinUserCompetition(u1.getIdUsuario(), c.getListaEquipos().get(0).getIdEquipo() , c.getIdCompeticion());
		userservice.joinUserCompetition(u2.getIdUsuario(), c.getListaEquipos().get(1).getIdEquipo() , c.getIdCompeticion());
		
		Equipo e = competitionservice.teamRegister("Equipo1", c.getIdCompeticion());
		
		competitionservice.addTeamToCompetition(e, c.getIdCompeticion());
		
		assert(competitionservice.getAvailableTeams(c.getIdCompeticion()).size()==(c.getListaEquipos().size()-c.getListaUsuarios().size()));
		
		competitionservice.banUserFromCompetition(u2.getIdUsuario(), c.getIdCompeticion());
				
		assert(competitionservice.getAvailableTeams(c.getIdCompeticion()).size()==(c.getListaEquipos().size()-c.getListaUsuarios().size()));
		
		Equipo equipoEliminado = competitionservice.getAvailableTeams(c.getIdCompeticion()).get(0);
		
		competitionservice.removeTeamFromCompetition(equipoEliminado.getIdEquipo() , c.getIdCompeticion());
		
		assert(!c.getListaEquipos().contains(equipoEliminado));
		
		assert(equipoEliminado.getListaPartidosLocal().isEmpty());
		assert(equipoEliminado.getListaPartidosVisitante().isEmpty());
		assert(equipoEliminado.getCompeticion()==null);
						
			
		
	}

	
	@Test
	public void ClassificationTest() throws NoSuchAlgorithmException {
		
		
		
		Usuario u1;
		try {
			u1 = userservice.userRegister("usuario1", "123456", "u@gmail.com");
			
			int numTeams=6;
			
			try {
			
				Competicion c = competitionservice.createCompetitionWithoutTeams("Liga A", "www.2k.com", u1.getIdUsuario(),new Date(), new Date(), new BigDecimal(0), new BigDecimal(0));
				
				Temporada tAnterior = competitionservice.getTemporadaActual(c.getIdCompeticion());
				
				assert(c.getListaEquipos().size()==numTeams);
				
				assert(tAnterior.getListaClasificaciones().size() == c.getListaEquipos().size());
				
				ClasificacionBlock cb = competitionservice.getActualClassification(c.getIdCompeticion(), 0, 20);
				assert(cb.getClasificaciones().size()==numTeams);
				
				competitionservice.removeTeamFromCompetition( c.getListaEquipos().get(0).getIdEquipo() , c.getIdCompeticion());
				
				long idTempAnterior = c.getIdTemporadaActual();
				
				competitionservice.newSeason(c.getIdCompeticion());
				
				Temporada tActual = competitionservice.getTemporadaActual(c.getIdCompeticion());
				
				assert(c.getIdTemporadaActual() != idTempAnterior );
				
				
				assert(teamservice.getTeamsOfCompetition(c.getIdCompeticion(), 0, numTeams).getEquipos().size()==numTeams-1);
				
				cb = competitionservice.getActualClassification(c.getIdCompeticion(), 0, 20);
				
				assert(cb.getClasificaciones().size()==numTeams-1);
				
				
				competitionservice.addNewTeamToCompetition("aaaa", c.getIdCompeticion());
				cb = competitionservice.getActualClassification(c.getIdCompeticion(), 0, 20);
				assert(cb.getClasificaciones().size()==numTeams);
				assert(tActual.getListaClasificaciones().size() == c.getListaEquipos().size());
				

			} catch (DuplicateInstanceException e) {
				Log.LogFile(e.getMessage());
				e.printStackTrace();
			} catch (InstanceNotFoundException e) {
				Log.LogFile(e.getMessage());
				e.printStackTrace();
			} catch (Exception e) {			
				Log.LogFile(e.getMessage());
				e.printStackTrace();
			}
						
			
			
			
		} catch (DuplicateInstanceException e) {
			// TODO Auto-generated catch block
			
			Log.LogFile("DuplicateInstanceException: "+ e.getMessage());
			e.printStackTrace();
		}
		
		
		

		
		
	}

	@Test
	public void findPlayersDefaultTeamTest() throws DuplicateInstanceException, InstanceNotFoundException{
		int numEquipos = 10;
		
		Usuario u1 = userservice.userRegister("usuario2", "123456", "u@gmail.com");

		Competicion c = competitionservice.createCompetitionWithoutTeams("Liga A", "www.2k.com", u1.getIdUsuario(),new Date(), new Date(), new BigDecimal(0), new BigDecimal(0));
		this.playerservice.createPlayers(c.getIdCompeticion());
		
		this.playerservice.findPlayersFromDefaultTeam(7,c.getIdCompeticion());
	}
	
	@Test
	public void findDefaultTeamsTest(){

		List<EquipoDefault> equiposDefault = new ArrayList<EquipoDefault>();
		Short conference = 1;
		Short division = 2;
		equiposDefault = this.teamservice.findEquiposDefaultByConferenceAndDivision(conference, division);
		assert(equiposDefault.size()!=0);
		conference = 2;
		division = null;
		equiposDefault = this.teamservice.findEquiposDefaultByConferenceAndDivision(conference, division);
		assert(equiposDefault.size()!=0);
		conference = null;
		division = 1;
		equiposDefault = this.teamservice.findEquiposDefaultByConferenceAndDivision(conference, division);
		assert(equiposDefault.size()!=0);
		conference = null;
		division = null;
		equiposDefault = this.teamservice.findEquiposDefaultByConferenceAndDivision(conference, division);
		assert(equiposDefault.size()!=0);
		
		
		
	}
	
	
	@Test
	public void findDefaultPlayersTest() {
			
		List<JugadorDefault> listaPlayersDefault = this.playerservice.findAllDefaultPlayers();
		List<JugadorDefault> listaPlayersDefaultBoston = this.playerservice.findAllDefaultPlayers(2);
		
		assert(listaPlayersDefault.size()!=0);
		assert(listaPlayersDefaultBoston.size()!=0);
		
	}
	


}

