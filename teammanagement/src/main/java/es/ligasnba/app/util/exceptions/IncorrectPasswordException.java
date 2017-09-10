package es.ligasnba.app.util.exceptions;

@SuppressWarnings("serial")
public class IncorrectPasswordException extends Exception {

	private String loginName;
    
    public IncorrectPasswordException(String loginName) {
        super("Usuario o Contraseña Incorrecta: login: " + loginName);
        this.loginName = loginName;
    }
    
    public String getLoginName() {
        return loginName;
    }

	
}
