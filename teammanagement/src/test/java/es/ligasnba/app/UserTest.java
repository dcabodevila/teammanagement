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

import es.ligasnba.app.model.competicion.Competicion;
import es.ligasnba.app.model.competicion.CompeticionBlock;
import es.ligasnba.app.model.competicion.competitionService;
import es.ligasnba.app.model.contrato.Contrato;
import es.ligasnba.app.model.contrato.ContratoBlock;
import es.ligasnba.app.model.contrato.ContractService;
import es.ligasnba.app.model.equipo.Equipo;
import es.ligasnba.app.model.equipo.EquipoBlock;
import es.ligasnba.app.model.equipo.teamService;
import es.ligasnba.app.model.jugador.Jugador;
import es.ligasnba.app.model.jugador.JugadorBlock;
import es.ligasnba.app.model.jugador.playerService;

import es.ligasnba.app.model.temporada.seasonService;
import es.ligasnba.app.model.traspaso.Traspaso;
import es.ligasnba.app.model.traspaso.TraspasoBlock;
import es.ligasnba.app.model.traspaso.tradeService;
import es.ligasnba.app.model.partido.Partido;
import es.ligasnba.app.model.partido.PartidoBlock;
import es.ligasnba.app.model.partido.matchService; 
import es.ligasnba.app.model.usuario.*;

import java.io.IOException;
import java.lang.Long;
import java.math.BigDecimal;
import java.net.URL;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Currency;
import java.util.Iterator;
import java.util.List;

import es.ligasnba.app.util.constants.Constants;
import es.ligasnba.app.util.constants.teams;
import es.ligasnba.app.util.exceptions.*;
import static es.ligasnba.app.test.util.GlobalNames.SPRING_CONFIG_TEST_FILE;
import static es.ligasnba.app.model.util.GlobalNames.SPRING_CONFIG_FILE;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {SPRING_CONFIG_FILE, SPRING_CONFIG_TEST_FILE })
@Transactional
public class UserTest {
    
//	@Autowired
//	private teamService teamservice;
//	@Autowired
//	private userService userservice;
//	@Autowired
//	private playerService playerservice;
//	@Autowired
//	private ContractService contractservice;
//	@Autowired
//	private competitionService competitionservice;
//	@Autowired
//	private tradeService tradeservice;
//	@Autowired
//	private seasonService seasonservice;
//	@Autowired
//	private matchService matchservice;	
//
//	public void initializeBeans(){
//		try{
//			
//			String[] configlocations = {"classpath:/teammanagement-spring-config.xml","classpath:/teammanagement-spring-config-test.xml"};
//			ApplicationContext context = new ClassPathXmlApplicationContext(configlocations);
//			playerservice = (playerService) context.getBean("playerService");
//			teamservice = (teamService) context.getBean("teamService");
//			userservice=(userService) context.getBean("userService");
//			contractservice=(ContractService) context.getBean("contractService");
//			competitionservice=(competitionService) context.getBean("competitionService");
//			seasonservice=(seasonService) context.getBean("seasonService");
//			tradeservice=(tradeService) context.getBean("tradeService");
//			matchservice=(matchService) context.getBean("matchService");
//			
//
//		}catch (Exception e){
//			System.out.println("No instanciado");			
//		}
//	}
//	
//	@Test 
//	public void test(){
//		
//	}	
//	@Test
//	public void UserCreate() throws DuplicateInstanceException, InstanceNotFoundException, NoSuchAlgorithmException{
//		Usuario u1 = userservice.userRegister("usuario1", "123456", "u@gmail.com");
//		Usuario u2 =userservice.findByLogin("usuario1");
//		assertEquals(u1.getIdUsuario(),u2.getIdUsuario());	
//		
//		
//
//	}
//	
//	@Test
//	public void UserLogin() throws DuplicateInstanceException, InstanceNotFoundException, IncorrectPasswordException{
//		
////		Usuario u1 = userservice.userRegister("usuario2", "123456", "u@gmail.com");
////		ResultadoLogin rl = userservice.login(u1.getLogin(),u1.getPass(), true);
////		assertEquals(u1.getLogin(),rl.getNombre());
//		
//	}
////	@Test(expected = IncorrectPasswordException.class)
////	public void UserBadLogin() throws DuplicateInstanceException, InstanceNotFoundException, IncorrectPasswordException{
////		Usuario u1 = userservice.userRegister("usuario2", "123456", "u@gmail.com");
////
////		ResultadoLogin rl = userservice.login(u1.getLogin(),"123457", true);
////		assertEquals(u1.getLogin(),rl.getNombre());
////		
////	}
//	
////	@Test
////	public void UserJoinCompetition() throws DuplicateInstanceException, Exception{		
////		userservice.userRegister("sirgodnolimit", "pass1", "sirgod@gmail.com");
////		
////		Usuario u =userservice.findByLogin("sirgodnolimit");
////		Competicion com1 = competitionservice.createEmptyCompetition("Liga 2", "www.2k.com", u.getIdUsuario());
////		competitionservice.teamRegister(teams.cavaliers, com1.getIdCompeticion());
////		
////		Competicion c = competitionservice.findByName("Liga 2");
////		
////		Equipo e = teamservice.findByName(teams.cavaliers);			
////		
////		assert(!userservice.TeamIsAssigned(e.getIdEquipo()));
////		assert(!userservice.userHasTeamInCompetition(u.getIdUsuario(), c.getIdCompeticion()));
////		
////		assert(userservice.joinUserCompetition(u.getIdUsuario(), e.getIdEquipo(), c.getIdCompeticion()));
////		
////		assert(userservice.getUsersOfCompetition(com1.getIdCompeticion(), 0, constants.cMaxTeamsInCompetition).getUsuarioes().size()==1);
////		assert(userservice.TeamIsAssigned(e.getIdEquipo()));
////		assert(userservice.userHasTeamInCompetition(u.getIdUsuario(), c.getIdCompeticion()));					
////		assert(e.getUsuario().getIdUsuario()==u.getIdUsuario());
////		assert(u.getListaEquipos().contains(e));
////		
////		assert(u.getListaCompeticiones().containsAll(
////				competitionservice.getCompetitionsOfUser(u.getIdUsuario(),0,u.getListaCompeticiones().size()).getCompeticiones()));
////		
////		assert(c.getListaUsuarios().containsAll(userservice.getUsersOfCompetition(c.getIdCompeticion(), 0, c.getListaUsuarios().size()).getUsuarioes()));
////		
////		assert(u.getListaEquipos().containsAll(teamservice.getTeams(u.getIdUsuario(), 0, u.getListaEquipos().size()).getEquipos()));
////			
////		assert(u.getListaEquipos().contains(e));
////		
////		assert(teamservice.getTeams(u.getIdUsuario(), 0, u.getListaEquipos().size()).getEquipos().contains(e));
////				
////
////	}
//	
//	

}
