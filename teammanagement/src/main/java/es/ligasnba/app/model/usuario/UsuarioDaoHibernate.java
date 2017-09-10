package es.ligasnba.app.model.usuario;



import java.util.List;

import es.ligasnba.app.model.usuario.Usuario;
import es.ligasnba.app.util.exceptions.InstanceNotFoundException;
import es.ligasnba.app.model.competicion.Competicion;
import es.ligasnba.app.model.generic.GenericDaoHibernate;
import org.springframework.stereotype.Repository;

@Repository("UsuarioDao")
public class UsuarioDaoHibernate extends GenericDaoHibernate<Usuario,Long> implements UsuarioDao{


	public Usuario findByLogin(String login) throws InstanceNotFoundException{
		
		Usuario u = (Usuario) getSession().createQuery("SELECT a FROM Usuario a WHERE a.login = :login ").setParameter("login",login ).uniqueResult();
		
		if (u==null) throw new InstanceNotFoundException(login, Usuario.class.getName());
		else return u;
		
	}
	
	public Usuario findByMail(String mail) throws InstanceNotFoundException{
		
		Usuario u = (Usuario) getSession().createQuery("SELECT a FROM Usuario a WHERE a.mail = :mail ").setParameter("mail",mail ).uniqueResult();
		
		if (u==null) throw new InstanceNotFoundException(mail, Usuario.class.getName());
		else return u;
		
	}

	@SuppressWarnings("unchecked")
	public List<Usuario> getUsersOfCompetition(long idCompeticion, int startIndex, int count) throws InstanceNotFoundException {
		
		List<Usuario> usuarios = null;
																											
		usuarios = (List<Usuario>) getSession().createQuery("SELECT u FROM Usuario u, Competicion c ,Equipo e WHERE c.idCompeticion=:idCompeticion AND e.competicion=c AND e.usuario=u").setParameter("idCompeticion",idCompeticion).setMaxResults(count).list();
		if (usuarios==null) 
			throw new InstanceNotFoundException(idCompeticion, Competicion.class.getName());
		else 
			return usuarios;		
		
	}	
	
	public Usuario getUserByActivationKey(String activationKey) throws InstanceNotFoundException{
		
		Usuario u = (Usuario) getSession().createQuery("SELECT a FROM Usuario a WHERE a.activateKey = :activateKey ").setParameter("activateKey",activationKey ).uniqueResult();
		
		if (u==null) throw new InstanceNotFoundException(activationKey, Usuario.class.getName());
		else return u;
		
		
	}

}
