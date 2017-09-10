package es.ligasnba.app.model.maintenance;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name="maintenance")
public class Maintenance {

	private long idMaintenance;
	private Date startMaintenanceDate;
	private Date finishMaintenanceDate;
	
	public Maintenance(Date startDate) {
		this.startMaintenanceDate = startDate;		
	}
	
	
    @Column(name="idMaintenance")
    @SequenceGenerator(             
         name="MaintenanceIdGenerator",
         sequenceName="MaintenanceSeq")
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO,
                    generator="MaintenanceIdGenerator")
	public long getIdMaintenance() {
		return idMaintenance;
	}
    @Column(name="startMaintenance")
	public Date getStartMaintenanceDate() {
		return startMaintenanceDate;
	}
	@Column(name="finishMaintenance")
	public Date getFinishMaintenanceDate() {
		return finishMaintenanceDate;
	}
	
	public void setIdMaintenance(long idMaintenance) {
		this.idMaintenance = idMaintenance;
	}
	public void setStartMaintenanceDate(Date startMaintenanceDate) {
		this.startMaintenanceDate = startMaintenanceDate;
	}
	public void setFinishMaintenanceDate(Date finishMaintenanceDate) {
		this.finishMaintenanceDate = finishMaintenanceDate;
	}
	
}
