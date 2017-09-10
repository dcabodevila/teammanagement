package es.ligasnba.app.model.usuario;

import java.util.List;

public class UsuarioBlock {
	 private List<Usuario> listaUsuarioes;
	 private boolean existenMasUsuarios;

	    public UsuarioBlock(List<Usuario> l,boolean e){
	        listaUsuarioes=l;
	        existenMasUsuarios=e;
	    }

	    public List<Usuario> getUsuarios(){
	        return listaUsuarioes;
	    }

	    public boolean getExistenMasUsuarioes(){
	        return existenMasUsuarios;
	    }
}
