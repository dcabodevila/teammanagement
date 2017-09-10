package es.ligasnba.app.model.estadistica;

import java.util.List;


public class EstadisticaBlock {
	 private List<Estadistica> listaEstadisticas;
	 private boolean existenMasEstadisticas;

	    public EstadisticaBlock(List<Estadistica> l,boolean e){
	        this.listaEstadisticas=l;
	        this.existenMasEstadisticas=e;
	    }

	    public List<Estadistica> getEstadisticas(){
	        return this.listaEstadisticas;
	    }

	    public boolean getExistenMasEstadisticas(){
	        return this.existenMasEstadisticas;
	    }
}
