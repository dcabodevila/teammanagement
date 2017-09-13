package es.ligasnba.app.model.util;

import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.log4j.Logger;

import es.ligasnba.app.model.usuario.CustomUserDetailsService;
import es.ligasnba.app.model.usuario.Usuario;
import es.ligasnba.app.util.constants.Constants;


public class SendMailSSL {
	
	private static final Logger logger = Logger.getLogger(CustomUserDetailsService.class);
	
	public boolean SendActivationMail(Usuario u){
		
		return SendMail(u.getMail(), Constants.cMailWelcomeSubject, "Hola "+u.getLogin()+", bienvenido a LigasNBA.es.\r\n\r\n Para activar la cuenta pulsa en el siguiente enlace: \r\n "+ Constants.cDomainName +"/activate/"+u.getActivateKey());
				
	}
	
	public boolean SendRememberPasswordMail(Usuario u){
		
		return SendMail(u.getMail(), Constants.cMailRememberSubject, "Hola "+u.getLogin()+", \r\n\r\n Para cambiar la contraseña de tu cuenta pulsa en el siguiente enlace: \r\n "+ Constants.cDomainName +"/remember/"+u.getActivateKey());
		
	}	
	
	public boolean SendMail(String mailTo, String subject, String text){
		
		Properties props = new Properties();
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.socketFactory.port", "465");
		props.put("mail.smtp.socketFactory.class",
				"javax.net.ssl.SSLSocketFactory");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.port", "465");
 
		Session session = Session.getDefaultInstance(props,
			new javax.mail.Authenticator() {
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication(Constants.cMailLgn,Constants.cMailPwd);
				}
			});
 
		try {
 
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress("ligasnba@gmail.com"));
			message.setRecipients(Message.RecipientType.TO,
					InternetAddress.parse(mailTo));
			message.setSubject(subject);
			message.setText(text);
 
			Transport.send(message);
			
			
 
		} catch (MessagingException e) {
			logger.error(e.getMessage());
			return false;
		}
			
		
		return true;
		
	}
	
}
