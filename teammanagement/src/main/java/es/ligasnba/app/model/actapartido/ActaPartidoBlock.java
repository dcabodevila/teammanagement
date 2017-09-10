package es.ligasnba.app.model.actapartido;

import java.util.List;

public class ActaPartidoBlock {
	 private List<ActaPartido> listaActaPartidos;
	 private boolean existenMasActaPartidos;

	    public ActaPartidoBlock(List<ActaPartido> l,boolean e){
	        this.listaActaPartidos=l;
	        this.existenMasActaPartidos=e;
	    }

	    public List<ActaPartido> getActaPartidos(){
	        return this.listaActaPartidos;
	    }

	    public boolean getExistenMasActaPartidos(){
	        return this.existenMasActaPartidos;
	    }
}