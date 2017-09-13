package es.ligasnba.app.util.Scheduler;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.Logger;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.StatefulJob;
import org.springframework.scheduling.quartz.QuartzJobBean;

import es.ligasnba.app.model.usuario.CustomUserDetailsService;
import es.ligasnba.app.util.Log;

/**
 * A custom job that extends from the QuartzJobBean
 * <p>
 * The fields are automatically mapped by Spring's {@link JobDetailsBean}
 * based on the properties we declared in the XML configuration for
 * the jobDataAsMap property. Make sure to add a setter for the property!
 *<p>
 * By default, Quartz Jobs are stateless, resulting in the possibility of jobs
 * interfering with each other. If you specify two triggers for the same
 * JobDetail, it might be possible that before the first job has finished, the
 * second one will start. If JobDetail classes implement the Stateful interface,
 * this won't happen. The second job will not start before the first one has finished.
 *
 * @see http://static.springsource.org/spring/docs/3.0.x/spring-framework-reference/html/scheduling.html
 */
public class CustomJob extends QuartzJobBean implements StatefulJob {
 
 private static final SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
 private static final Logger logger = Logger.getLogger(CustomJob.class);
 private Worker worker;
 
  
 @Override
 protected void executeInternal(JobExecutionContext ctx)
   throws JobExecutionException {
   
  // The job data map is available through the JobExecutionContext
  // (passed to you at execution time)
  @SuppressWarnings("unused")
JobDataMap jobDataMap = ctx.getJobDetail().getJobDataMap();
   
  try {
   // Retrieve the last date when the job was run
   Date lastDateRun  = ctx.getPreviousFireTime();
    
   // Job was run previously
   if (lastDateRun != null) {
	   
	   logger.info("Last date run: " + sdf.format(lastDateRun));
     
    // Retrieve the number of times this job has been attempted
    int refireCount = ctx.getRefireCount();
     
    if (refireCount > 0) {
    	logger.info("Total attempts: " + refireCount);
    }
   }
   else {
    // Job is run for the first time
	   logger.info("Job is run for the first time");
   }
    
   // Do the actual work
   logger.info("Delegating work to worker");
   worker.work();

   
    
   // Retrieve the next date when the job will be run
   String nextDateRun = sdf.format(ctx.getNextFireTime());
    
   logger.info("Next date run: " + nextDateRun);
 
  }
  catch (Exception e) {
	  logger.info("Unexpected exception " + e.getMessage());

  	JobExecutionException e2 =
  		new JobExecutionException(e);
  	// Quartz will automatically unschedule
  	// all triggers associated with this job
  	// so that it does not run again
  	e2.setUnscheduleAllTriggers(true);
  	throw e2;
  }
 }
 
 /**
  * The worker
  * <p>
  * This is required so that Spring's {@link JobDetailsBean} will
  * automatically inject the values
  */
 public void setWorker(Worker worker) {
  this.worker = worker;
 }



}