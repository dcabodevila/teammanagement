package es.ligasnba.app.model.clasificacion;


import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;


import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.Transactional;

import es.ligasnba.app.model.temporada.Temporada;
import es.ligasnba.app.util.constants.Constants;
import es.ligasnba.app.util.exceptions.InstanceNotFoundException;


@Service("classificationService")
@Transactional
public class classificationServiceImpl implements classificationService{
	
	@Autowired
	private ClasificacionDao clasificaciondao;
	
	public void setClasificacionDao(ClasificacionDao clasificaciondao) {
		this.clasificaciondao = clasificaciondao;
	}
	
	@SuppressWarnings("unused")
	@Autowired
	private PlatformTransactionManager transactionManager;

	
	
	public void settransactionManager(PlatformTransactionManager t) {
		transactionManager = t;
	}
	
	@Transactional(readOnly = true)
	public ClasificacionBlock getClasificacionesTemporada(long idTemporada, int index, int count) throws InstanceNotFoundException{
		
		List<Clasificacion> listClassification = new ArrayList<Clasificacion>();
		
		listClassification = clasificaciondao.findClasifficationsBySeason(idTemporada, Constants.cMinTeamsInCompetition, Constants.cMaxTeamsInCompetition);
		
		boolean existMoreClassifications = listClassification.size() == (Constants.cMaxListItems + 1);
        if (existMoreClassifications) {
        	listClassification.remove(listClassification.size() - 1);
        }
        return new ClasificacionBlock(listClassification, existMoreClassifications);		
	
	}
	@Transactional(readOnly = true)
	public Clasificacion getClasificacionEquipoActual(long idEquipo, long idTemporada) throws InstanceNotFoundException{
		
		return clasificaciondao.findClassificationByTeamAndSeason(idEquipo, idTemporada);
		
	}
	




}