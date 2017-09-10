package es.ligasnba.app.model.mail;

import es.ligasnba.app.model.usuario.Usuario;

public interface MailService {

	void sendActivationMail(Usuario u);
	
}


