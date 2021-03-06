package es.ligasnba.app.model.noticia;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import es.ligasnba.app.model.finanzas.AsientoDto;
import es.ligasnba.app.model.generic.GenericDaoHibernate;

import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;

@Repository("NoticiaDao")
public class NoticiaDaoHibernate extends GenericDaoHibernate<Noticia,Long> implements NoticiaDao {

	@SuppressWarnings("unchecked")
	public List<Noticia> getLastNews(long idEquipo, Date fecha, int numNoticias){		 
		
		List<Noticia> noticias= (List<Noticia>) getSession().createQuery("SELECT n FROM Noticia n , Equipo e WHERE e.idEquipo =:idEquipo AND n.equipo=e AND n.fecha>:fecha ORDER BY fecha DESC").setParameter("idEquipo",idEquipo).setParameter("fecha",fecha).setMaxResults(numNoticias).list();
		
		if (noticias==null) 
			return new ArrayList<Noticia>();
		else 
			return noticias;	
		
	}
	
	@Override
	public List<NoticiaDto> findAllNewsEquipo(long idEquipo) {		 

		@SuppressWarnings("unchecked")
		List<NoticiaDto> j = (List<NoticiaDto>) ((SQLQuery) getSession().getNamedQuery("FIND_ALL_NOTICIAS_BY_EQUIPO").setParameter("idEquipo", idEquipo))
				.setResultTransformer( Transformers.aliasToBean(NoticiaDto.class)).list();
		if (j==null) {
			return new ArrayList<NoticiaDto>();
		}
		return j;

	}
	
}
