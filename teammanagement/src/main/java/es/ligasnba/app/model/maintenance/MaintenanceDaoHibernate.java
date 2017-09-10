package es.ligasnba.app.model.maintenance;


import es.ligasnba.app.model.generic.GenericDaoHibernate;
import es.ligasnba.app.model.jugador.Jugador;
import es.ligasnba.app.util.exceptions.InstanceNotFoundException;


import org.springframework.stereotype.Repository;

@Repository("MaintenanceDao")
public class MaintenanceDaoHibernate extends GenericDaoHibernate<Maintenance,Long> implements MaintenanceDao{

	public Maintenance getLastMaintenance() throws InstanceNotFoundException{
		
		Maintenance j = (Maintenance) getSession().createQuery("SELECT m FROM Maintenance m WHERE m.idMaintenance >= (SELECT m FROM Maintenance) ").uniqueResult();
		if (j==null) throw new InstanceNotFoundException("", Jugador.class.getName());
		else return j;	
		
	}
	
}