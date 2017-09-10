package es.ligasnba.app.model.traspaso;

import java.util.ArrayList;
import java.util.List;

import es.ligasnba.app.model.generic.GenericDaoHibernate;
import es.ligasnba.app.util.exceptions.InstanceNotFoundException;
import org.springframework.stereotype.Repository;

@Repository("TraspasoDao")
public class TraspasoDaoHibernate extends GenericDaoHibernate<Traspaso,Long> implements TraspasoDao {	
	
	@SuppressWarnings("unchecked")
	public List<Traspaso> getReceivedTradesByTeam(long equipoDestino,int startindex,int count){
		
		List<Traspaso> traspasosRecibidos =  getSession().createQuery("SELECT t FROM Traspaso t, Equipo e WHERE " + "e.idEquipo=:idTeam AND t.aprobado=false AND ((t.equipoDestino = e AND t.contrato is null) OR (t.equipoOrigen = e AND t.contrato is not null))").setParameter("idTeam", equipoDestino).setFirstResult(startindex).setMaxResults(count).list();
		
		if (traspasosRecibidos==null)
			return new ArrayList<Traspaso>();
		else 
			return traspasosRecibidos;
	
	}
	@SuppressWarnings("unchecked")
	public List<Traspaso> getSentTradesByTeam(long equipoOrigen, int startindex, int count){
		
		List<Traspaso> traspasosRecibidos =  getSession().createQuery("SELECT t FROM Traspaso t, Equipo e WHERE " + "e.idEquipo=:idTeam AND ((t.equipoOrigen = e AND t.contrato is null) OR (t.equipoDestino = e AND t.contrato is not null))").setParameter("idTeam", equipoOrigen).setFirstResult(startindex).setMaxResults(count).list();
		
		if (traspasosRecibidos==null)
			return new ArrayList<Traspaso>();
		else 
			return traspasosRecibidos;
	
	
	}
}
