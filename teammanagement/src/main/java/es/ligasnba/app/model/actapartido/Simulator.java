package es.ligasnba.app.model.actapartido;


import java.util.Random;

import es.ligasnba.app.model.estadistica.Estadistica;
import es.ligasnba.app.model.jugador.Jugador;
import es.ligasnba.app.model.temporada.Temporada;
import es.ligasnba.app.util.constants.Constants;

public class Simulator {

	public static Estadistica simulatePlayerStats(ActaPartido ap, Jugador j, Temporada t){

		
		Random rand = new Random();
				
		int puntos = (rand.nextInt(50) * j.getMedia() * j.getMinutos()) / (100*Constants.cTotalMinutesOfGame) ;
		int asistencias = (rand.nextInt(15) * j.getMedia()* j.getMinutos())/(100*Constants.cTotalMinutesOfGame);
		int rebotes = (rand.nextInt(15) * j.getMedia()* j.getMinutos())/(100*Constants.cTotalMinutesOfGame);
		int minutos = j.getMinutos();
		Estadistica stat = new Estadistica(ap,j,t, puntos,asistencias,rebotes,minutos);
		return stat;
		
	}
	
}
