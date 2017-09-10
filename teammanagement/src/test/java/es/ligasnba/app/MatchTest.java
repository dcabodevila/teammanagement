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
import es.ligasnba.app.util.exceptions.DuplicateInstanceException;
import es.ligasnba.app.util.exceptions.InstanceNotFoundException;

import java.io.IOException;
import java.lang.Long;
import java.math.BigDecimal;
import java.net.URL;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.ArrayList;
import java.util.Currency;
import java.util.Iterator;
import java.util.List;

import es.ligasnba.app.util.constants.Constants;
import es.ligasnba.app.util.constants.teams;
import es.ligasnba.app.util.exceptions.*;
import static es.ligasnba.app.model.util.GlobalNames.SPRING_CONFIG_FILE;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import es.ligasnba.app.util.Log;

@ContextConfiguration(locations = {SPRING_CONFIG_FILE})
@Transactional
public class MatchTest {
    
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
//		initializeBeans();
	}	
	
	@Test	
	public void CreateMatches(){
	
//		Usuario u1;
//		try {
//			u1 = userservice.userRegister("usuario2", "123456", "u@gmail.com");
			
//			try {
//				
//				
//				
//				int numTeams=6;
//				
//				Competicion c = competitionservice.createCompetition("Liga A", "www.2k.com", u1.getIdUsuario(),numTeams,1);
////						
////				userservice.joinUserCompetition(u1.getIdUsuario(), c.getListaEquipos().get(0).getIdEquipo() , c.getIdCompeticion());
////				
////				assert(c.getListaTemporadas().get(0).getListaPartidos().size() == numTeams*(numTeams-1)*c.getNumVueltas());
////				
////				Partido partidoSimulado = c.getListaEquipos().get(0).getListaPartidosLocal().get(0);
//				
////				actapartidoservice.Simulate(partidoSimulado.getIdPartido());
////				
////				assert(partidoSimulado.getActaPartido()!=null);		
////				
////				
////				assert(partidoSimulado.getActaPartido().getListaEstadisticas().size()>0);
//				
//				
//				
//				
//				
//				
//			} catch (InstanceNotFoundException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			} catch (Exception e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
		
//		} catch (DuplicateInstanceException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}


		
	}
	

//	@Test	
	public void MatchesWithAddRemoveTeams() throws NoSuchAlgorithmException{
	
		Usuario u1;
		try {
			u1 = userservice.userRegister("usuario2", "123456", "u@gmail.com");
			
			try {
				
				
				
				int numTeams=6;
				
				Competicion c = competitionservice.createCompetitionWithoutTeams("Liga A", "www.2k.com", u1.getIdUsuario(),new Date(),new Date(), new BigDecimal(0), new BigDecimal(0));

				
				userservice.joinUserCompetition(u1.getIdUsuario(), c.getListaEquipos().get(0).getIdEquipo() , c.getIdCompeticion());
				
				c = competitionservice.findById(c.getIdCompeticion());
				
								
				assert(competitionservice.getTemporadaActual(c.getIdCompeticion()).getListaPartidos().size() == c.getListaEquipos().size()*(c.getListaEquipos().size()-1)*c.getNumVueltas());
				
				for (int i=0;i<c.getListaEquipos().size();i++){
					assert(c.getListaEquipos().get(i).getListaPartidosLocal().size()== c.getListaEquipos().size()-1);
					assert(c.getListaEquipos().get(i).getListaPartidosVisitante().size()== c.getListaEquipos().size()-1);
					
					
					
					assert(c.getListaEquipos().get(i).getListaPartidosLocal().size()==c.getListaEquipos().get(i).getListaPartidosVisitante().size());
					

					
				}
				
				
				Equipo newTeam = competitionservice.addNewTeamToCompetition("aaaaa", c.getIdCompeticion());
				
				
				
				assertEquals(newTeam, teamservice.findById(newTeam.getIdEquipo()));
				
				assert(c.getListaTemporadas().get(0).getListaPartidos().size() == c.getListaEquipos().size()*(c.getListaEquipos().size()-1)*c.getNumVueltas());
								
				
				
				competitionservice.removeTeamFromCompetition(c.getListaEquipos().get(0).getIdEquipo(), c.getIdCompeticion());
											
				assert(c.getListaEquipos().size()==numTeams);
				
				
				
				assert(c.getListaTemporadas().get(0).getListaPartidos().size() == c.getListaEquipos().size()*(c.getListaEquipos().size()-1)*c.getNumVueltas());
				
				
				
//				//Simulaciones
//				Partido partidoSimulado = c.getListaEquipos().get(1).getListaPartidosLocal().get(0);
//				
//				actapartidoservice.Simulate(partidoSimulado.getIdPartido());
//				
//				assert(partidoSimulado.getActaPartido()!=null);		
//				
//				
//				assert(partidoSimulado.getActaPartido().getListaEstadisticas().size()>0);
				
				
				
				
				
				
			} catch (InstanceNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
		} catch (DuplicateInstanceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


		
	}	
	
	
}