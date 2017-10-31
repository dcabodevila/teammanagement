package es.ligasnba.app.model.util;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Random;

public class CommonFunctions {
	
	public static Integer calcularEdad(Date d){
		
		if (d==null) return null; 
					
	    Calendar firstCal = GregorianCalendar.getInstance();
	    Calendar secondCal = GregorianCalendar.getInstance();

	    firstCal.setTime(d);
	    secondCal.setTime(new Date());

	    secondCal.add(Calendar.DAY_OF_YEAR, -firstCal.get(Calendar.DAY_OF_YEAR));

	    return secondCal.get(Calendar.YEAR) - firstCal.get(Calendar.YEAR);
		
	}
	
	public static int daysBetween(final Date fechaInicio,final Date fechaFin){
		final int daysBetween = (int)( (fechaFin.getTime() - fechaInicio.getTime()) / (1000 * 60 * 60 * 24));
		return daysBetween;
 
	}
	
	public static float getPorcentajeRandomMasMenos(int porcentaje){
		
		final float porcentajeMasMenos = (float)porcentaje/100;
		final float min = (1F - porcentajeMasMenos);
		final float max = (1F + porcentajeMasMenos);
		final Random r = new Random();
		return min + r.nextFloat() * (max - min);
	}
	
	
	public static float getPorcentajeRandomMas(int porcentaje){
		
		final float porcentajeMasMenos = (float)porcentaje/100;
		final float min = (1F);
		final float max = (1F + porcentajeMasMenos);
		final Random r = new Random();
		return min + r.nextFloat() * (max - min);
	}
	
	public static Date getStartOfDay(Date d) {
		Calendar calActual = Calendar.getInstance();
		calActual.setTime(d);
		calActual.set(Calendar.HOUR_OF_DAY, 0);
		calActual.set(Calendar.MINUTE, 0);
		calActual.set(Calendar.SECOND, 0);
		calActual.set(Calendar.MILLISECOND, 0);
		return calActual.getTime();
	}


}
 