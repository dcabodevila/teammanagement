package es.ligasnba.app.model.competicion;

import java.util.List;

public class CompeticionBlock {
	 private List<Competicion> listaCompeticiones;
	 private boolean existenMasCompeticiones;

	    public CompeticionBlock(List<Competicion> l,boolean e){
	        listaCompeticiones=l;
	        existenMasCompeticiones=e;
	    }

	    public List<Competicion> getCompeticiones(){
	        return listaCompeticiones;
	    }

	    public boolean getExistenMasCompeticiones(){
	        return existenMasCompeticiones;
	    }
}
