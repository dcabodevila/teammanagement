package es.ligasnba.app.util;

import java.io.*;
import java.util.Calendar;
import java.util.Date;


public class Log {

public static void LogFile( String texto ){

	
	try{
		
		FileWriter w = new FileWriter("LogTest.txt",true);
		
		
		BufferedWriter bw = new BufferedWriter(w);

		
		bw.write("Log de " + new Date() + ":"+ Calendar.MINUTE + ":" + Calendar.SECOND + " " + texto + "\r\n");
		
		

		bw.close();
		
		
	}catch(IOException e){}
	
}


public static void LogScheduler(String texto){
	try{
		
		FileWriter w = new FileWriter("SchedulerLog.txt",true);
		
		
		BufferedWriter bw = new BufferedWriter(w);
	
		
		bw.write("Log de " + new Date() + ":"+ Calendar.MINUTE + ":" + Calendar.SECOND + " " + texto + "\r\n");
		
		
	
		bw.close();
		
		
	}catch(IOException e){}
}
	
}
