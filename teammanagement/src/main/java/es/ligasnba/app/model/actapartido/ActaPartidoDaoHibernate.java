package es.ligasnba.app.model.actapartido;

import es.ligasnba.app.model.generic.GenericDaoHibernate;
import es.ligasnba.app.model.partido.ResumenBalance;
import es.ligasnba.app.model.partido.ResumenValoraciones;
import es.ligasnba.app.model.partido.ValoracionData;

import java.math.BigDecimal;
import java.util.List;

import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;

@Repository("ActaPartidoDao")
public class ActaPartidoDaoHibernate extends GenericDaoHibernate<ActaPartido,Long> implements ActaPartidoDao {

	
	@SuppressWarnings("unchecked")
	@Override
	public List<ActaPartido> findByPartido(long idPartido){
		
		List<ActaPartido> ac = (List<ActaPartido>) getSession().createQuery("SELECT ac FROM ActaPartido ac, Partido p WHERE p.idPartido=:idPartido AND ac.partido=p ").setParameter("idPartido",idPartido ).list();
		return ac;
		
	}
	
	@Override
	public ValoracionData findValoracionByPartidoUsuario(long idPartido, long idUsuario){
		ValoracionData valoracion = (ValoracionData) ((SQLQuery) getSession().getNamedQuery("FIND_VALORACION_DATA_BY_PARTIDO_USUARIO").setParameter("idPartido",idPartido).setParameter("idUsuario",idUsuario)				
				.setResultTransformer( Transformers.aliasToBean(ValoracionData.class))).uniqueResult();
		
		if (valoracion!=null){
			return valoracion; 
		}
		
		return new ValoracionData();
	}
	
	@Override
	public ResumenValoraciones findValoracionesRecibidasUsuario(long idUsuario){
		return (ResumenValoraciones)((SQLQuery) getSession().getNamedQuery("FIND_VALORACIONES_RECIBIDAS_USUARIO").setParameter("idUsuario",idUsuario).setResultTransformer( Transformers.aliasToBean(ResumenValoraciones.class))).uniqueResult();
	}
	
	@Override
	public ResumenValoraciones findValoracionesRealizadasUsuario(long idUsuario){
		return (ResumenValoraciones)((SQLQuery) getSession().getNamedQuery("FIND_VALORACIONES_REALIZADAS_USUARIO").setParameter("idUsuario",idUsuario).setResultTransformer( Transformers.aliasToBean(ResumenValoraciones.class))).uniqueResult();
	}
	
	@Override
	public ResumenBalance findBalanceEquipo(long idEquipo, long idTemporada, Boolean isPlayOff){
		return (ResumenBalance)((SQLQuery) getSession().getNamedQuery("FIND_BALANCE_EQUIPO_TEMPORADA")
				.setParameter("idEquipo",idEquipo).setParameter("idTemporada",idTemporada).setParameter("isPlayOff",isPlayOff.toString() ).setResultTransformer( Transformers.aliasToBean(ResumenBalance.class))).uniqueResult();		
	}
	@Override
	public ResumenBalance findBalanceUsuario(long idUsuario, Boolean isPlayOff){
		return (ResumenBalance)((SQLQuery) getSession().getNamedQuery("FIND_BALANCE_USUARIO_GLOBAL")
				.setParameter("idUsuario",idUsuario).setParameter("isPlayOff",isPlayOff.toString()).setResultTransformer( Transformers.aliasToBean(ResumenBalance.class))).uniqueResult();		
	}
	
}
