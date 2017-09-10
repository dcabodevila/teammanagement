package es.ligasnba.app.model.usuario;



import java.util.List;

import es.ligasnba.app.util.exceptions.InstanceNotFoundException;
import es.ligasnba.app.model.generic.GenericDao;
import es.ligasnba.app.model.usuario.Usuario;
public interface UsuarioDao extends GenericDao<Usuario,Long>{    
        
    Usuario findByLogin(String login) throws InstanceNotFoundException;
    public List<Usuario> getUsersOfCompetition(long idCompeticion, int startIndex, int count) throws InstanceNotFoundException;
    public Usuario getUserByActivationKey(String activationKey) throws InstanceNotFoundException;
    public Usuario findByMail(String mail) throws InstanceNotFoundException;
}
