package es.ligasnba.app.model.partido;

import java.util.List;

public class PartidoBlock {
	 private List<Partido> listaPartidos;
	 private boolean existenMasPartidos;

	    public PartidoBlock(List<Partido> l,boolean e){
	        this.listaPartidos=l;
	        this.existenMasPartidos=e;
	    }

	    public List<Partido> getPartidos(){
	        return this.listaPartidos;
	    }

	    public boolean getExistenMasPartidos(){
	        return this.existenMasPartidos;
	    }
}