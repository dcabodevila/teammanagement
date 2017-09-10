package es.ligasnba.app.model.contrato;

import java.util.List;

public class ContratoBlock {
	 private List<Contrato> listaContratos;
	 private boolean existenMasContratos;

	    public ContratoBlock(List<Contrato> l,boolean e){
	        this.listaContratos=l;
	        this.existenMasContratos=e;
	    }

	    public List<Contrato> getContratos(){
	        return this.listaContratos;
	    }

	    public boolean getExistenMasContratos(){
	        return this.existenMasContratos;
	    }
}