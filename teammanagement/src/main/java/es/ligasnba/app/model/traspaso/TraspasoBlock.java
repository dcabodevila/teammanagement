package es.ligasnba.app.model.traspaso;

import java.util.List;

public class TraspasoBlock {
	 private List<Traspaso> listaTraspasos;
	 private boolean existenMasTraspasos;

	    public TraspasoBlock(List<Traspaso> l,boolean e){
	        this.listaTraspasos=l;
	        this.existenMasTraspasos=e;
	    }

	    public List<Traspaso> getTraspasos(){
	        return this.listaTraspasos;
	    }

	    public boolean getExistenMasTraspasos(){
	        return this.existenMasTraspasos;
	    }
}
