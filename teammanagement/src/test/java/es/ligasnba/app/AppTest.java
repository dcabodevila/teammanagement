package es.ligasnba.app;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
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

import es.ligasnba.app.model.rol.Rol;
import es.ligasnba.app.model.temporada.seasonService;
import es.ligasnba.app.model.traspaso.Traspaso;
import es.ligasnba.app.model.traspaso.TraspasoBlock;
import es.ligasnba.app.model.traspaso.tradeService;
import es.ligasnba.app.model.noticia.newsService;
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

import es.ligasnba.app.util.Log;
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
public class AppTest {
    
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
//	@Autowired
//	private actaPartidoService actapartidoservice;
	
	public void initializeBeans(){
		try{
			
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
//			actapartidoservice= (actaPartidoService) context.getBean("actaPartidoService");

			

		}catch (Exception e){
			System.out.println("No instanciado");			
		}
	}
//	@Test
//	public void PopulateDB() throws NoSuchAlgorithmException{
//		initializeBeans();
//		
//		try{
//			
//			Usuario u1 = userservice.userRegister("sirgodnolimit", "123456", "sirgod@gmail.com");
//
////			userservice.userRegister("mazokie", "pass2", "mazok@gmail.com");
////			userservice.userRegister("vader", "pass3", "vader@gmail.com");					
//			
//
//			
//																		
//		}catch (DuplicateInstanceException e){
//			e.printStackTrace();
//			Log.LogFile(e.getMessage());
//		}
//		
//	}
//	@Test
//	public void assign(){
//		initializeBeans();
////		try{
////			Usuario u1 =userservice.findByLogin("sirgodnolimit");
////			Usuario u2 =userservice.findByLogin("mazokie");
////			Usuario u3 =userservice.findByLogin("vader");
////			
////			userservice.setUserValue(u1.getIdUsuario(),30);
////			userservice.setUserValue(u2.getIdUsuario(),15);
////			userservice.setUserValue(u3.getIdUsuario(),20);
//			
//
//
////			try{
//
//				
////				Competicion com1 = competitionservice.createCompetition("Liga 1", "www.2k.com", u1.getIdUsuario(),4);
////				Competicion com2 = competitionservice.createCompetition("Liga 2", "www.2k.com", u1.getIdUsuario(),6);
////				
////				
////				
////				userservice.joinUserCompetition(u1.getIdUsuario(), com1.getListaEquipos().get(0).getIdEquipo() , com1.getIdCompeticion());
////				userservice.joinUserCompetition(u2.getIdUsuario(), com1.getListaEquipos().get(1).getIdEquipo() , com1.getIdCompeticion());
////				
////				List<Jugador> jugadoresofrecidos = com1.getListaEquipos().get(0).getListaJugadores();
////				List<Jugador> jugadoresrecibidos = com1.getListaEquipos().get(1).getListaJugadores();
////				
////				
////				
////				Traspaso t = tradeservice.createTradeOffer(com1.getListaEquipos().get(0).getIdEquipo(),com1.getListaEquipos().get(1).getIdEquipo(), jugadoresofrecidos, jugadoresrecibidos, "",new BigDecimal(0));
////				tradeservice.AcceptTradeOffer(com1.getListaEquipos().get(1).getIdEquipo(), t.getIdTraspaso());
//				
//				
////				competitionservice.removeCompetition(com1.getIdCompeticion(), u1.getIdUsuario());
//
////				teamservice.removeJugador(com2.getListaEquipos().get(0).getListaJugadores().get(0).getIdJugador(), com2.getListaEquipos().get(0).getIdEquipo());
////				
////				List<Jugador> agentesLibres = playerservice.getAgentesLibres(com2.getIdCompeticion(),0, constants.cMaxPlayersByTeam).getJugadores();
////				
////				for (int i=0;i<agentesLibres.size();i++){
////					
////					Log.LogFile("Jugador: "+ agentesLibres.get(i).getNombre() );
////				}				
//				
//								
//				
//				
////				actapartidoservice.Simulate(com2.getListaEquipos().get(0).getListaPartidosLocal().get(0).getIdPartido());
////				
////				userservice.joinUserCompetition(u2.getIdUsuario(), com2.getListaEquipos().get(1).getIdEquipo() , com2.getIdCompeticion());
//				
////				competitionservice.removeTeamFromCompetition(com2.getListaEquipos().get(0).getIdEquipo(), com2.getIdCompeticion());
//				
////				competitionservice.newSeason(com2.getIdCompeticion());
//				
//				
//				
//				
////				competitionservice.addNewTeamToCompetition("Nuevo Equipo", com2.getIdCompeticion());
//				
//				
//				
////				Equipo e1 = competitionservice.teamRegister("Phoenix Suns 1", com1.getIdCompeticion());
////				Equipo e2 = competitionservice.teamRegister("Memphis Grizzlies 1",com1.getIdCompeticion());
////				Equipo e3 = competitionservice.teamRegister("Golden State Warriors 1",com1.getIdCompeticion());
////				Equipo e4= competitionservice.teamRegister(teams.bobcats, com2.getIdCompeticion());
////				Equipo e5= competitionservice.teamRegister(teams.bulls, com2.getIdCompeticion());
//
////				 try{
////					userservice.joinUserCompetition(u1.getIdUsuario(), e1.getIdEquipo(), com1.getIdCompeticion());
////					userservice.joinUserCompetition(u2.getIdUsuario(), e2.getIdEquipo(), com1.getIdCompeticion());
////					userservice.joinUserCompetition(u3.getIdUsuario(), e3.getIdEquipo(), com1.getIdCompeticion());
////					userservice.joinUserCompetition(u1.getIdUsuario(), e4.getIdEquipo(), com2.getIdCompeticion());
////					userservice.joinUserCompetition(u3.getIdUsuario(), e5.getIdEquipo(), com2.getIdCompeticion());
////					
////					matchservice.generateCalendar(com1.getIdCompeticion());
//					
//////					Contrato c1 = contractservice.generateContract(e2.getIdEquipo(), j3.getIdJugador(), 3,  new BigDecimal("1000.00"),  new BigDecimal("10"), true, true);
////					Contrato c4 = contractservice.generateContract(e1.getIdEquipo(), j2.getIdJugador(), 1,  new BigDecimal("1000.00"),  new BigDecimal("5"), false, false);
////					contractservice.signContract(j2.getIdJugador(), c4.getIdContrato());					
//
//					//**Bug al renovar un jugador.
//					
//					
////					Contrato c2 = contractservice.generateContract(e1.getIdEquipo(), j2.getIdJugador(), 3,  new BigDecimal("2000.00"),  new BigDecimal("5"), false, false);
////					Contrato c3 = contractservice.generateContract(e1.getIdEquipo(), j2.getIdJugador(), 3,  new BigDecimal("2000.00"),  new BigDecimal("5"), false, false);
//////					contractservice.signContract(j3.getIdJugador(), c1.getIdContrato());
//////					contractservice.signContract(j2.getIdJugador(), c2.getIdContrato());
//					
////////   	      		    JugadorBlock jb1 = playerservice.findPlayerByTeamId(e1.getIdEquipo(), constants.cMinPlayersByTeam, constants.cMaxPlayersByTeam);
////   	      		    JugadorBlock jb2 = playerservice.getPlantilla(e1.getIdEquipo(), constants.cMinPlayersByTeam, constants.cMaxPlayersByTeam);
//   	      		    
////////   	      		    assertTrue( jb1.getJugadores().size()==jb2.getJugadores().size());
////   	      		    
////   	      		    List<Jugador> listajugadoresOfrecidos = new ArrayList<Jugador>();
////   	      		    List<Jugador> listajugadoresRecibidos = new ArrayList<Jugador>();
////   	      		    
////   	      		    listajugadoresOfrecidos.add(j3);
////   	      		    listajugadoresRecibidos.add(j2);
////	
//////      		    	Traspaso tradeOffer = tradeservice.createTradeOffer(e2.getIdEquipo(), e1.getIdEquipo(), "", new BigDecimal("0"));
//////      		    	tradeservice.setJugadoresTrade(tradeOffer.getIdTraspaso(), listajugadoresOfrecidos, listajugadoresRecibidos);
//////      		    	tradeservice.sendTradeOffer(tradeOffer.getIdTraspaso());
////      		    	
//////      		    	tradeservice.AcceptTradeOffer(e1.getIdEquipo(), tradeOffer.getIdTraspaso());
////					
////
////   		    		PartidoBlock listaPartidosLocal =  matchservice.getPartidosLocal(e1.getIdEquipo(),0,constants.cMaxListItems);
////   		    		PartidoBlock listaPartidosVisitante =  matchservice.getPartidosVisitante(e1.getIdEquipo(),0,constants.cMaxListItems);
////   		    		
////   		    		if (!(listaPartidosLocal.getPartidos().size() > 0))
////   		    			throw new Exception("No se ha generado el calendario");
////      		    	
////      		    	Partido p = listaPartidosLocal.getPartidos().get(0);
////      		    	Partido p1 = listaPartidosVisitante.getPartidos().get(0);
////      		    	
////      		    	System.out.println("Numero partidos E1: "+matchservice.getNumTotalPartidosEquipo(e2.getIdEquipo()));
////      		    	matchservice.createActaPartido(u1.getIdUsuario(), p.getIdPartido(), 86, 101);
////      		    	matchservice.createActaPartido(u1.getIdUsuario(),listaPartidosLocal.getPartidos().get(1).getIdPartido(), 104, 77);
////      		    	matchservice.createActaPartido(u1.getIdUsuario(), p1.getIdPartido(), 86, 88);
////      		    	
////      		    	
////      		    	
////      		    	matchservice.getPartidosJugadosTemporada(com1.getIdCompeticion(), 0, 10);
////
////////      		    	assert(matchservice.getPartidosJugadosTemporada(com1.getIdCompeticion(), 0, 10).getPartidos().size()==3);
////      		    	int numTotalPartidosEquipo = matchservice.getNumTotalPartidosEquipo(e1.getIdEquipo());
////      		    	assert(numTotalPartidosEquipo== matchservice.getPartidosJugadosEquipo(e1.getIdEquipo(), 0, numTotalPartidosEquipo).getPartidos().size() + 
////      		    			matchservice.getPartidosPendientesEquipo(e1.getIdEquipo(), 0, numTotalPartidosEquipo).getPartidos().size());
////      		    	
////      		    	int numTotalPartidosTemporada = matchservice.getNumTotalPartidosTemporada(com1.getIdTemporadaActual());
////      		    	
////      		    	assert(numTotalPartidosTemporada == matchservice.getPartidosJugadosTemporada(com1.getIdTemporadaActual(), 0, numTotalPartidosTemporada).getPartidos().size() + 
////      		    			matchservice.getPartidosPendientesTemporada(com1.getIdTemporadaActual(), 0, numTotalPartidosTemporada).getPartidos().size());      		    	
////      		    	
////      		    	
////      		    	teamservice.getTotalBalances(com1.getIdCompeticion());      		    	
////      		    	
////    				try{
////////    					Competicion com3 = competitionservice.createRealCompetition("Liga 3", "www.2k.com", u2.getIdUsuario(),4,2);    				
////////    					matchservice.getPartidosLocal()
////////    					matchservice.createActaPartido(idUsuario, idPartido, resultadoLocal, resultadoVisitante)
////    					
////    				}catch(Exception e){
////    					System.out.println(e.getMessage());
////    				}
////      		    	
////      		    
////      		    	
////      		    	      		    	      		    	
////      		    	
////   	      		    TraspasoBlock tb = tradeservice.getTraspasosRecibidos(e2.getIdEquipo(),0,10);
////   	      		    
////   	      		    System.out.println("Traspasos del equipo "+e1.getnombre());
////   	      		    for (int i=0; i<tb.getTraspasos().size();i++){
////   	      		    	System.out.println(tb.getTraspasos().get(i).getIdTraspaso());
////   	      		    	
////   	      		    }
////   	      		    
////					
////					System.out.println("Jugadores del "+ e1.getnombre());
////					for (int i=0; i<jb2.getJugadores().size();i++){
////						
////						System.out.println(jb2.getJugadores().get(i).getNombre());
////	
////					}
////					
////////					playerservice.cancelContract(j2.getIdJugador(), c2.getIdContrato());																										
////					
////
//					
////					long idNuevaTemporada = competitionservice.newSeason(com1.getIdCompeticion());
////					competitionservice.removeTeam(e1.getIdEquipo(), com1.getIdCompeticion());
////					
////					competitionservice.teamRegister("New Team", com1.getIdCompeticion());
//				
////
////					
////////					System.out.println("Nueva Temporada de la "+ com1.getNombre()+" "+ idNuevaTemporada);
////					
////////					try{
////////						competitionservice.removeCompetition(com1.getIdCompeticion(), u2.getIdUsuario());
////////					}catch(Exception e){
////////						System.out.println(e.getMessage());
////////					}
////////					try{
////////						competitionservice.removeCompetition(com1.getIdCompeticion(), u1.getIdUsuario());
////////					}catch(Exception e){
////////						System.out.println(e.getMessage());
////////					}
////					
////					
////					
////		//
////		//			playerservice.signContract(j1.getIdJugador(), c1.getIdContrato());
////					//System.out.println("Contrato firmado entre "+ c1.getEquipo().getnombre() + " y " + c.getJugador().getNombre());
////				 }catch(Exception e){
////					 System.out.println("Exception: "+e.getMessage());
////				 }
////				 
////			}catch (DuplicateInstanceException e){
////				Log.LogFile("Exception en AppTest: " + e.getMessage());
////				e.printStackTrace();	
////			} catch (Exception e) {
////
////				Log.LogFile("Exception en AppTest: " + e.getMessage());
////				e.printStackTrace();
////			}			
////			
////////			JugadorBlock jb= teamservice.findRoster(e1.getIdEquipo(), 0, 10);
////
////			
////////			System.out.println("Jugadores del E1: "+jb.getJugadores().size());
////////			ContratoBlock cb1= contractservice.findContractsByTeam(e1.getIdEquipo(), 0, 3);
////////			ContratoBlock cb2= contractservice.findSignedContractsByTeam(e1.getIdEquipo(), 0, 3);
////			
////			
////			//playerservice.playerRemove(j1.getIdJugador());
////////			jb= teamservice.findRoster(e1.getIdEquipo(), 0, 10);			
////
////			CompeticionBlock comb1 = competitionservice.getCompetitionsOfUser(u1.getIdUsuario(),0,10);
////
////
////			
////////			for (int i=0;i< jb.getJugadores().size();i++){
////////				System.out.println("Jugadores del E1: "+jb.getJugadores().get(i).getNombre());								
////////			}
//////			
////////				System.out.println("Contratos del E1: "+cb1.getContratos().size());								
////////				System.out.println("Contratos Firmados del E1: "+cb2.getContratos().size());								
////				
////				
////							
////				
////
////
////			
////
////////			userservice.kickUser(u1.getIdUsuario());
////			
////			//competitionservice.kickUserFromCompetition(u1.getIdUsuario(), com1.getIdCompeticion());
////			
////			
////			
////		}catch (InstanceNotFoundException e){
////			Log.LogFile("Exception en AppTest: " + e.getMessage());
////			e.printStackTrace();
////		}
//	}
//	

}
