package es.ligasnba.app.model.competicion;

import java.util.ArrayList;
import java.util.List;

import es.ligasnba.app.model.generic.GenericDaoHibernate;
import es.ligasnba.app.model.jugador.PlayerData;
import es.ligasnba.app.model.competicion.Competicion;
import es.ligasnba.app.model.equipo.Equipo;
import es.ligasnba.app.model.usuario.Usuario;
import es.ligasnba.app.util.exceptions.InstanceNotFoundException;

import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;

@Repository("CompeticionDao")
public class CompeticionDaoHibernate extends GenericDaoHibernate<Competicion,Long> implements CompeticionDao{
	

	@Override
	public MenuNavigationForm findMenuNavigationInfo(long idCompeticion, long idEquipo){		 
		MenuNavigationForm lj = (MenuNavigationForm) ((SQLQuery) getSession().getNamedQuery("FIND_MENU_NAVIGATION").setParameter("idCompeticion",idCompeticion)
				.setParameter("idCompeticion",idCompeticion).setParameter("idEquipo",idEquipo))
				.setResultTransformer( Transformers.aliasToBean(MenuNavigationForm.class)).uniqueResult();

		if (lj==null) 
			return new MenuNavigationForm();
		else 
			return lj;		
	}
	
	@Override
	public CompetitionForm findCompetitionInfo(long idCompeticion, long idEquipo){		 
		CompetitionForm lj = (CompetitionForm) ((SQLQuery) getSession().getNamedQuery("FIND_COMPETITION_INFO")
				.setParameter("idCompeticion",idCompeticion).setParameter("idEquipo",idEquipo))
				.setResultTransformer( Transformers.aliasToBean(CompetitionForm.class)).uniqueResult();

		if (lj==null) 
			return new CompetitionForm();
		else 
			return lj;		
	}
	
	public Competicion findByName(String nombreCompeticion) throws InstanceNotFoundException {
		
		Competicion c = (Competicion) getSession().createQuery("SELECT c FROM Competicion c WHERE c.nombre = :nombre ").setParameter("nombre",nombreCompeticion ).uniqueResult();
		
		if (c==null) 
			throw new InstanceNotFoundException(nombreCompeticion, Competicion.class.getName());
		else 
			return c;

	}
	
	
	@SuppressWarnings("unchecked")
	public List<Competicion> getAllCompetitions(){
		
		List<Competicion> competitions = null;
		
		competitions = (List<Competicion>) getSession().createQuery("SELECT c FROM Competicion c ").list();
		
		if (competitions==null) 
			return new ArrayList<Competicion>();
		else 
			return competitions;		

		
	}
	
	@SuppressWarnings("unchecked")
	public List<Competicion> getCompetitionsOfUser(long userId, int startIndex, int count) {
		
		List<Competicion> competitions = null;
		
		competitions = (List<Competicion>) getSession().createQuery("SELECT c FROM Competicion c , Usuario u, Equipo e WHERE e.competicion=c AND u.idUsuario=:userId AND e.usuario = u").setParameter("userId",userId).setMaxResults(count).list();
		
		if (competitions==null) 
			return new ArrayList<Competicion>();
		else 
			return competitions;		
		
	}
	

	

	
	public Equipo findUserInCompetition(long userId, long competitionId) throws InstanceNotFoundException{
		
		Equipo teamOfUser = null;
		teamOfUser = (Equipo) getSession().createQuery("SELECT e FROM Equipo e , Usuario u, Competicion c WHERE c.idCompeticion=:competitionId AND u.idUsuario=:userId AND e.usuario = u").setParameter("userId",userId).setParameter("competitionId", competitionId).uniqueResult();
		
		if (teamOfUser == null) 
			throw new InstanceNotFoundException(userId, Competicion.class.getName());
		else 
			return teamOfUser;
			
	}
	
	@SuppressWarnings("unchecked")
	public List<Competicion> competitionsAdministratedByUser(long userId, int startIndex, int count) throws InstanceNotFoundException{
		List<Competicion> competitions = null;
		competitions = (List<Competicion>) getSession().createQuery("SELECT c FROM Competicion c , Usuario u WHERE u.idUsuario =:userId AND c.admin = u").setParameter("userId",userId).setMaxResults(count).list();
		
		if (competitions==null) 
			throw new InstanceNotFoundException(userId, Competicion.class.getName());
		else 
			return competitions;
		
	}
	
	@SuppressWarnings("unchecked")
	public List<Usuario> findBestAdmin(long competitionId, int count) throws InstanceNotFoundException {
		List<Usuario> userList = null;
		userList = (List<Usuario>) getSession().createQuery("SELECT u FROM Usuario u , Equipo e, Competicion c WHERE c.idCompeticion =:competitionId AND e.competicion = c AND e.usuario = u AND u.userValue = (SELECT max(u1.userValue) FROM Usuario u1, Competicion c1, Equipo e1 WHERE c1.idCompeticion =:competitionId AND e1.competicion = c AND e1.usuario = u1 )").setParameter("competitionId",competitionId).setMaxResults(count).list();
		
		if (userList==null) 
			throw new InstanceNotFoundException(competitionId, Competicion.class.getName());
		else 
			return userList;
		
	}

	public void removeFreePlayers(long idCompeticion){
		getSession().createSQLQuery("DELETE FROM jugador WHERE idCompeticion=:competitionId AND idEquipo is NULL").setParameter("competitionId",idCompeticion).executeUpdate();
	}
	
}