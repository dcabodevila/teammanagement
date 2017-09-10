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

import es.ligasnba.app.model.temporada.Temporada;
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

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {SPRING_CONFIG_FILE, SPRING_CONFIG_TEST_FILE })
@Transactional
public class SeasonTest {
    
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
//	public void CompetitionCreate() throws Exception{
//		
//		int numEquipos = 10;
//		
//		Usuario u1 = userservice.userRegister("usuario2", "123456", "u@gmail.com");
//		Usuario u2 = userservice.userRegister("usuario3", "123456", "u@gmail.com");		
//
//
//
//		Competicion c = competitionservice.createCompetitionWithoutTeams("Liga A", "www.2k.com", u1.getIdUsuario(),new Date(), new Date(), new BigDecimal(0), new BigDecimal(0));
//
//		Competicion com2 = competitionservice.findByName("Liga A");
//
//				
//		Iterator<Equipo> itEquipos =  c.getListaEquipos().iterator();
//		while (itEquipos.hasNext()) {
//			Equipo e = (Equipo) itEquipos.next();	
//			assert(e.getListaClasificaciones().size() == c.getListaTemporadas().size());	
//		}
//
//		Iterator<Temporada> itTemporadas =  c.getListaTemporadas().iterator();
//		while (itTemporadas.hasNext()) {
//			Temporada t = (Temporada) itTemporadas.next();	
//			assert(t.getListaClasificaciones().size() == c.getListaEquipos().size());	
//		}
//
//		
//		competitionservice.removeTeamFromCompetition( c.getListaEquipos().get(0).getIdEquipo(), com2.getIdCompeticion());
//		
//		assert(c.getListaTemporadas().get(0).getListaClasificaciones().size() == c.getListaEquipos().size() );
//		assert(c.getListaEquipos().size() == (numEquipos-1));
//		
//		
//		
//		
//		assert(userservice.joinUserCompetition(u1.getIdUsuario(), c.getListaEquipos().get(0).getIdEquipo(), c.getIdCompeticion()));
//		assert(userservice.joinUserCompetition(u2.getIdUsuario(), c.getListaEquipos().get(1).getIdEquipo(), c.getIdCompeticion()));		
//		assert( competitionservice.getAdminCompetitionsOfUser(u1.getIdUsuario(), 0, 3).getCompeticiones().contains(com2) );
//		
//		
//		
//		
//		
//		
//	}
	

}
