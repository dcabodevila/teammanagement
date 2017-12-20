package es.ligasnba.app.model.noticia;

import java.util.Date;
import java.util.List;

import es.ligasnba.app.model.equipo.Equipo;

public interface newsService {
	public void AddNewToUser(Equipo e, String texto, Date fecha);
	public List<Noticia> getLastNews(long idEquipo, Date fecha,int numNoticias);
	List<NoticiaDto> getAllNews(long idEquipo);
}
