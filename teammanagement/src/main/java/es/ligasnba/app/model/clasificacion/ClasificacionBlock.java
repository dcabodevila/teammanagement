package es.ligasnba.app.model.clasificacion;

import java.util.List;

public class ClasificacionBlock {
	 private List<Clasificacion> listaClasificaciones;
	 private boolean existenMasClasificaciones;

	    public ClasificacionBlock(List<Clasificacion> l,boolean e){
	        this.listaClasificaciones=l;
	        this.existenMasClasificaciones=e;
	    }

	    public List<Clasificacion> getClasificaciones(){
	        return this.listaClasificaciones;
	    }

	    public boolean getExistenMasClasificaciones(){
	        return this.existenMasClasificaciones;
	    }
}
