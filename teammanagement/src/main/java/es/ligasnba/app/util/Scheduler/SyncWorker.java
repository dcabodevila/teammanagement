package es.ligasnba.app.util.Scheduler;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import es.ligasnba.app.controller.TeamController;
import es.ligasnba.app.model.actapartido.actaPartidoService;
import es.ligasnba.app.model.competicion.Competicion;
import es.ligasnba.app.model.competicion.competitionService;
import es.ligasnba.app.model.contrato.ContractService;
import es.ligasnba.app.model.jugador.Jugador;
import es.ligasnba.app.model.jugador.playerService;
import es.ligasnba.app.model.partido.Partido;
import es.ligasnba.app.model.partido.matchService;
import es.ligasnba.app.model.segundoPlano.SegundoPlanoService;
import es.ligasnba.app.model.util.DynamicPropertiesFileReader;
import es.ligasnba.app.util.Log;
import es.ligasnba.app.util.constants.Constants;
import es.ligasnba.app.util.exceptions.InstanceNotFoundException;

@Component("SchedulerImpl")
public class SyncWorker implements Worker{
	
	@Autowired
	private competitionService competitionservice;

//	@Autowired
//	private DynamicPropertiesFileReader properties;
	@Autowired
	private SegundoPlanoService segundoPlanoService;
	

	private static final Logger logger = Logger.getLogger(SyncWorker.class);
	
	public void setCompetitionService(competitionService competitionservice) {
		this.competitionservice = competitionservice;
	}

	
	@Override
	public void work() {
		
		logger.info("Scheduler realizando trabajos a las "+new Date());
		
		Timer timer = new Timer();
		
//		properties.setProperty("1");

		List<Competicion> competiciones = competitionservice.getAllCompetitions();
		
		if (competiciones!=null){
		
			for (Competicion competicion : competiciones){							
				
				try {
					this.segundoPlanoService.ejecutarActualizacionCompeticion(competicion.getIdCompeticion(), false);
				} catch (InstanceNotFoundException e) {
					logger.error(e.getMessage());
				}
			}			
		}
		
//	timer.schedule( new TimerTask(){   
//		@Override
//		  public void run() {										
//			properties.setProperty("0");					
//	  } }, 5*1000 );		

//	properties.setProperty("0");
		

	}
	public SegundoPlanoService getSegundoPlanoService() {
		return segundoPlanoService;
	}
	public void setSegundoPlanoService(SegundoPlanoService segundoPlanoService) {
		this.segundoPlanoService = segundoPlanoService;
	} 
		
}


