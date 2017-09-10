package es.ligasnba.app.model.maintenance;

import es.ligasnba.app.model.generic.GenericDao;
import es.ligasnba.app.util.exceptions.InstanceNotFoundException;


public interface MaintenanceDao extends GenericDao<Maintenance,Long>{

	public Maintenance getLastMaintenance() throws InstanceNotFoundException;
	
}