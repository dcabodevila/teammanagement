package es.ligasnba.app.model.noticia;

import java.util.Date;

public class NoticiaDto{

	private long idNoticia;
	private String texto;
	private Date fecha;
	private boolean mensajeNuevo;
	private boolean notificar;
	public long getIdNoticia() {
		return idNoticia;
	}
	public void setIdNoticia(long idNoticia) {
		this.idNoticia = idNoticia;
	}
	public String getTexto() {
		return texto;
	}
	public void setTexto(String texto) {
		this.texto = texto;
	}
	public Date getFecha() {
		return fecha;
	}
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}
	public boolean isMensajeNuevo() {
		return mensajeNuevo;
	}
	public void setMensajeNuevo(boolean mensajeNuevo) {
		this.mensajeNuevo = mensajeNuevo;
	}
	public boolean isNotificar() {
		return notificar;
	}
	public void setNotificar(boolean notificar) {
		this.notificar = notificar;
	}

	

	
	
}
