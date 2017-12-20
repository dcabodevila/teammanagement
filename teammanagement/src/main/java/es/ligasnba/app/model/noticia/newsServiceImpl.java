package es.ligasnba.app.model.noticia;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.Transactional;

import es.ligasnba.app.model.equipo.Equipo;
import es.ligasnba.app.util.Log;
import es.ligasnba.app.util.constants.Constants;

@Service("newsService")
@Transactional
public class newsServiceImpl implements newsService{

	@SuppressWarnings("unused")
	@Autowired
	private PlatformTransactionManager transactionManager;
	@Autowired
	private NoticiaDao noticiadao;
	
	
	public void setTransactionManager(PlatformTransactionManager t) {
		transactionManager = t;
	}
	public void setNoticiaDao(NoticiaDao noticiadao) {
		this.noticiadao = noticiadao;
	}
	
	public void AddNewToUser(Equipo e, String texto, Date fecha){
		
		Noticia noticia = new Noticia(e,texto,fecha);
		noticia.setMensajeNuevo(true);
		e.addNoticia(noticia);
		
		noticiadao.create(noticia);		
		
	}
	
	@Transactional(readOnly=true)
	public List<Noticia> getLastNews(long idEquipo, Date fecha, int numNoticias){
		
		Calendar cal = Calendar.getInstance();
		cal.setTime(fecha);
		cal.add(Calendar.DATE, Constants.cNewsMaxOldDate);
		
		return noticiadao.getLastNews(idEquipo, cal.getTime(), numNoticias);
						
	}
	
	
	@Transactional(readOnly=true)
	@Override
	public List<NoticiaDto> getAllNews(long idEquipo){
		
		return noticiadao.findAllNewsEquipo(idEquipo);
						
	}	
	
}
