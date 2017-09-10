package es.ligasnba.app.model.noticia;

import java.util.Date;
import java.util.List;

import es.ligasnba.app.model.generic.GenericDao;

public interface NoticiaDao extends GenericDao<Noticia,Long>{	

	public List<Noticia> getLastNews(long idEquipo, Date fecha, int numNoticias);


}
