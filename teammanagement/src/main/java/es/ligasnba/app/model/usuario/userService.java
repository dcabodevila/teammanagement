package es.ligasnba.app.model.usuario;

import es.ligasnba.app.util.exceptions.InstanceNotFoundException;
import es.ligasnba.app.util.exceptions.DuplicateInstanceException;

public interface userService {

	public Usuario findById(long id) throws InstanceNotFoundException;
	public Usuario findByLogin(String l) throws InstanceNotFoundException;
	public UsuarioBlock getUsersOfCompetition(long idCompetition,int startindex,int count) throws InstanceNotFoundException;
	public Usuario userRegister(String nombre, String pass, String email) throws DuplicateInstanceException;
	public void kickUser(long id) throws InstanceNotFoundException;
	public Usuario changeMail(Long id, String email) throws InstanceNotFoundException;
	public Usuario changeLogin(Long id, String login) throws InstanceNotFoundException;		
	public void setUserValue(long idUsuario, int points) throws InstanceNotFoundException;
	public boolean joinUserCompetition(long idUsuario, long idEquipo, long idCompeticion) throws InstanceNotFoundException;
	public void QuitCompetition(long idUsuario, long idCompeticion) throws InstanceNotFoundException;
	public boolean TeamIsAssigned(long idEquipo) throws InstanceNotFoundException;
	public boolean userHasTeamInCompetition(long idUsuario, long idCompeticion) throws InstanceNotFoundException;
	public boolean userIsKickedFromCompetition(long idUsuario, long idCompeticion) throws InstanceNotFoundException;
	public void activateAccount(String activateKey) throws InstanceNotFoundException;
	public boolean rememberPwd(String mail) throws InstanceNotFoundException;
	public boolean changePass(String activationKey, String pass) throws InstanceNotFoundException;
	public Usuario findByActivationKey(String activationKey) throws InstanceNotFoundException;
	
}


