package es.ligasnba.app.model.maintenance;

	
import java.util.Date;

import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;

import es.ligasnba.app.util.exceptions.InstanceNotFoundException;



@Service("maintenanceService")
@Transactional
public class maintenanceServiceImpl implements maintenanceService{
	
@Autowired
private MaintenanceDao maintenancedao;

@SuppressWarnings("unused")
@Autowired
private PlatformTransactionManager transactionManager;	

public void setMaintenanceDao(MaintenanceDao maintenancedao) {
	this.maintenancedao = maintenancedao;
}
public void setTransactionManager(PlatformTransactionManager transactionManager) {
	this.transactionManager = transactionManager;
}

public void newMaintenance(Date startDate) {
	Maintenance m = new Maintenance(startDate);
	maintenancedao.create(m);	
}

public Maintenance getLastMaintenance() throws InstanceNotFoundException {

	return maintenancedao.getLastMaintenance();
}


}
