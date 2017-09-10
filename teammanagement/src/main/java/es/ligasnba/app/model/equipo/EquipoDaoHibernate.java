package es.ligasnba.app.model.equipo;

import java.util.ArrayList;
import java.util.List;

import es.ligasnba.app.model.generic.GenericDaoHibernate;
import es.ligasnba.app.model.jugador.PlayerData;
import es.ligasnba.app.model.usuario.Usuario;
import es.ligasnba.app.model.dto.EquipoSeleccionDto;
import es.ligasnba.app.model.equipo.Equipo;
import es.ligasnba.app.model.equipodefault.EquipoDefault;
import es.ligasnba.app.util.exceptions.InstanceNotFoundException;

import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;

@Repository("EquipoDao")
public class EquipoDaoHibernate extends GenericDaoHibernate<Equipo,Long> implements EquipoDao {
	
	public Equipo findByNameInCompetition(String nombre, long idCompeticion) throws InstanceNotFoundException{
		Equipo e = (Equipo) getSession().createQuery("SELECT a FROM Equipo a , Competicion c WHERE a.nombre = :nombre AND c.idCompeticion = :idCompeticion AND a.competicion = c").setParameter("nombre",nombre ).setParameter("idCompeticion",idCompeticion ).uniqueResult();
		
		if (e==null) throw new InstanceNotFoundException(nombre, Usuario.class.getName());
		else return e;
		}		
	
	@SuppressWarnings("unchecked")
	public List<Equipo> findByUserId(long userId, int startindex, int count) {
		
		return getSession().createQuery("SELECT e FROM Equipo e, Usuario u WHERE " + "e.usuario=u AND u.idUsuario= :userId").setParameter("userId", userId).setFirstResult(startindex).setMaxResults(count).list();
				
	}


	@SuppressWarnings("unchecked")
	public List<Equipo> getTeamsOfUser(long idUsuario, int startIndex, int count) {
		List<Equipo> equipo = null;
		
		equipo = (List<Equipo>) getSession().createQuery("SELECT e FROM Equipo e , Usuario u WHERE u.idUsuario=:idUsuario AND e.usuario=u").setParameter("idUsuario",idUsuario).setMaxResults(count).list();
		if (equipo==null) 
			return new ArrayList<Equipo>();
		else 
			return equipo;		
	}	
	
	public Equipo getTeamOfUserInCompetition(long idUsuario, long idCompeticion) throws InstanceNotFoundException{
		Equipo e = (Equipo) getSession().createQuery("SELECT e FROM Equipo e , Usuario u , Competicion c WHERE u.idUsuario=:idUsuario AND c.idCompeticion=:idCompeticion AND e.usuario=u AND e.competicion=c").setParameter("idCompeticion",idCompeticion).setParameter("idUsuario",idUsuario).uniqueResult();
		
		if (e==null) throw new InstanceNotFoundException(idUsuario, Equipo.class.getName());
		else return e;
		
	}
	
	@SuppressWarnings("unchecked")
	public List<EquipoDefault> selectEquiposDefaultByConferenceAndDivision(Short conference, Short division){
		
		List<EquipoDefault> equipos = null;
		if ((division==null) && (conference!=null)){
			equipos = (List<EquipoDefault>) getSession().createQuery("SELECT e FROM EquipoDefault e WHERE e.conference = :conference").setParameter("conference",conference).list();			
		}
		else if ((conference==null) && (division!=null)){
			equipos = (List<EquipoDefault>) getSession().createQuery("SELECT e FROM EquipoDefault e WHERE e.division = :division").setParameter("division",division).list();			
		}
		else {
			equipos = (List<EquipoDefault>) getSession().createQuery("SELECT e FROM EquipoDefault e ").list();
		}


		if (equipos==null) 
			return new ArrayList<EquipoDefault>();
		else 
			return equipos;
		
	}
	
	
	@Override
	public EquipoDefault findEquipoDefaultById(Integer idEquipo) {
		
		return (EquipoDefault) getSession().createQuery("SELECT e FROM EquipoDefault e WHERE e.idEquipo = :idEquipo").setParameter("idEquipo", idEquipo).uniqueResult();
				
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<EquipoSeleccionDto> findAllTeamsNotSelectedInCompetition(long idCompeticion){		 
		List<EquipoSeleccionDto> lj = (List<EquipoSeleccionDto>) ((SQLQuery) getSession().getNamedQuery("FIND_EQUIPOS_NOT_SELECTED").setParameter("idCompeticion",idCompeticion))
				.setResultTransformer( Transformers.aliasToBean(EquipoSeleccionDto.class)).list();

		if (lj==null) 
			return new ArrayList<EquipoSeleccionDto>();
		else 
			return lj;		
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<EquipoSeleccionDto> findAllEquiposFromCompetition(long idCompeticion){		 
		List<EquipoSeleccionDto> lj = (List<EquipoSeleccionDto>) ((SQLQuery) getSession().getNamedQuery("FIND_ALL_EQUIPOS_BY_COMPETICION").setParameter("idCompeticion",idCompeticion))
				.setResultTransformer( Transformers.aliasToBean(EquipoSeleccionDto.class)).list();

		if (lj==null) 
			return new ArrayList<EquipoSeleccionDto>();
		else 
			return lj;		
	}	



    

}
