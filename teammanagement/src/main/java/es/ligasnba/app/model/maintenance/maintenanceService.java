package es.ligasnba.app.model.maintenance;

import java.util.Date;

import es.ligasnba.app.util.exceptions.InstanceNotFoundException;

public interface maintenanceService {
	
	public void newMaintenance(Date startDate);
	public Maintenance getLastMaintenance() throws InstanceNotFoundException;
	
}
