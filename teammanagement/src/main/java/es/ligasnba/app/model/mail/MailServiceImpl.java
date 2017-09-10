package es.ligasnba.app.model.mail;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import es.ligasnba.app.model.usuario.Usuario;
import es.ligasnba.app.model.util.SendMailSSL;

@Service("MailService")
@Transactional
public class MailServiceImpl implements MailService {
	

	
	@SuppressWarnings("unused")
	@Autowired
	private PlatformTransactionManager transactionManager;
	
	public void setTransactionManager(PlatformTransactionManager tManager) {	
		transactionManager = tManager;
	}

	@Override
	public void sendActivationMail(Usuario u) {
		//Enviamos email de activación
		SendMailSSL mail = new SendMailSSL();
		mail.SendActivationMail(u);
	}

}