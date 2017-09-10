package es.ligasnba.app.model.temporada;


import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import es.ligasnba.app.model.competicion.Competicion;
import es.ligasnba.app.util.exceptions.InstanceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.Transactional;


@Service("seasonService")
@Transactional
public class seasonServiceImpl implements seasonService{
	
	@Autowired
	private TemporadaDao temporadadao;
	
	@SuppressWarnings("unused")
	@Autowired
	private PlatformTransactionManager transactionManager;		
	

	public void setTemporadaDao(TemporadaDao t){
		temporadadao = t;
	}
	
	public void settransactionManager(PlatformTransactionManager t) {
		transactionManager = t;
	}

	@Transactional(readOnly=true)	
	public Temporada findById(long id) throws InstanceNotFoundException {
		
		Temporada e = temporadadao.find(id);	
		return e;
		
	}
	@Transactional(readOnly = true)
	public Temporada findByName(String l) throws InstanceNotFoundException {
		Temporada e = temporadadao.findByName(l);
		return e;
	}

	@Override
	public Temporada getTemporadaActualCompeticion(Competicion c){
		List<Temporada> listaTemporadas = c.getListaTemporadas();
		Collections.sort(listaTemporadas);
			
		for (Temporada temporada : listaTemporadas){
			if (temporada.getIdTemporada()==c.getIdTemporadaActual()){
				return temporada;
			}
		}
		
		return null;

	}
	
	@Override
	public Temporada getTemporadaSiguienteCompeticion(Competicion c){
		List<Temporada> listaTemporadas = c.getListaTemporadas();
		Collections.sort(listaTemporadas);
		
		Iterator<Temporada> temporadas = c.getListaTemporadas().iterator();
		Temporada tsiguiente = (Temporada) temporadas.next();
		
		while (tsiguiente.getIdTemporada()!= c.getIdTemporadaActual()){
			tsiguiente = (Temporada) temporadas.next();
			if( !temporadas.hasNext()){
				return null;
			}
				
		}
		if (temporadas.hasNext()){
			tsiguiente = (Temporada) temporadas.next();
			return tsiguiente;
		}
		
		return null;
		
	}
	
	@Override
	public List<TemporadaData> findListaTemporadaDataByIdCompeticion(long idCompeticion){
		return this.temporadadao.findListaTemporadaDataByIdCompeticion(idCompeticion);
	}

}