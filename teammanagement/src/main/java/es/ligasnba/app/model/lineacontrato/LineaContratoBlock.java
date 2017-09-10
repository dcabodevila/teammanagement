package es.ligasnba.app.model.lineacontrato;

import java.util.List;

public class LineaContratoBlock {
	 private List<LineaContrato> listaLineaContratos;
	 private boolean existenMasLineaContratos;

	    public LineaContratoBlock(List<LineaContrato> l,boolean e){
	        this.listaLineaContratos=l;
	        this.existenMasLineaContratos=e;
	    }

	    public List<LineaContrato> getLineaContratos(){
	        return this.listaLineaContratos;
	    }

	    public boolean getExistenMasLineaContratos(){
	        return this.existenMasLineaContratos;
	    }
}