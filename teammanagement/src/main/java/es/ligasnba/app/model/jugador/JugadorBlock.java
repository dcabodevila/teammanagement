package es.ligasnba.app.model.jugador;

import java.util.List;


public class JugadorBlock {
	 private List<Jugador> listaJugadores;
	 private boolean existenMasJugadores;

	    public JugadorBlock(List<Jugador> l,boolean e){
	        this.listaJugadores=l;
	        this.existenMasJugadores=e;
	    }

	    public List<Jugador> getJugadores(){
	        return this.listaJugadores;
	    }

	    public boolean getExistenMasJugadores(){
	        return this.existenMasJugadores;
	    }
}
