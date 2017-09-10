package es.ligasnba.app.model.equipo;

import java.util.List;

public class EquipoBlock {
	 private List<Equipo> listaEquipos;
	 private boolean existenMasEquipos;

	    public EquipoBlock(List<Equipo> l,boolean e){
	        this.listaEquipos=l;
	        this.existenMasEquipos=e;
	    }

	    public List<Equipo> getEquipos(){
	        return this.listaEquipos;
	    }

	    public boolean getExistenMasEquipos(){
	        return this.existenMasEquipos;
	    }
}
