package es.ligasnba.app.model.noticia;

import java.util.List;

public class NoticiaBlock {
	 private List<Noticia> listaNoticias;
	 private boolean existenMasNoticias;

	    public NoticiaBlock(List<Noticia> l,boolean e){
	        this.listaNoticias=l;
	        this.existenMasNoticias=e;
	    }

	    public List<Noticia> getEquipos(){
	        return this.listaNoticias;
	    }

	    public boolean getExistenMasEquipos(){
	        return this.existenMasNoticias;
	    }
}
