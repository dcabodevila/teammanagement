package es.ligasnba.app.model.temporada;

import java.util.List;

public class TemporadaBlock {
	 private List<Temporada> listaTemporadas;
	 private boolean existenMasTemporadas;

	    public TemporadaBlock(List<Temporada> l,boolean e){
	        this.listaTemporadas=l;
	        this.existenMasTemporadas=e;
	    }

	    public List<Temporada> getTemporadas(){
	        return this.listaTemporadas;
	    }

	    public boolean getExistenMasTemporadas(){
	        return this.existenMasTemporadas;
	    }
}
