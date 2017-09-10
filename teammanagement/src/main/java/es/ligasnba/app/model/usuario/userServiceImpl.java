package es.ligasnba.app.model.usuario;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;
import java.security.*;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import es.ligasnba.app.util.Log;
import es.ligasnba.app.util.PasswordEncrypter;

import es.ligasnba.app.util.exceptions.DuplicateInstanceException;
import es.ligasnba.app.util.exceptions.InstanceNotFoundException;
import es.ligasnba.app.model.competicion.Competicion;
import es.ligasnba.app.model.competicion.CompeticionDao;
import es.ligasnba.app.model.competitionrol.competitionRolService;
import es.ligasnba.app.model.equipo.Equipo;
import es.ligasnba.app.model.equipo.EquipoDao;
import es.ligasnba.app.model.mail.MailService;
import es.ligasnba.app.model.noticia.newsService;
import es.ligasnba.app.model.rol.Rol;
import es.ligasnba.app.model.util.SendMailSSL;
import es.ligasnba.app.util.constants.Constants;

@Service("userService")
@Transactional
public class userServiceImpl implements userService {

@SuppressWarnings("unused")
@Autowired
private PlatformTransactionManager transactionManager;

@Autowired
private UsuarioDao usuariodao;

@Autowired
private CompeticionDao competiciondao;

@Autowired
private EquipoDao equipodao;

@Autowired
private competitionRolService competitionrolservice;

@Autowired
private newsService newsservice;

@Autowired
private MailService mailService;

	public void setUsuarioDao (UsuarioDao u) {
		this.usuariodao = u; 
	}
	public void setCompeticionDao(CompeticionDao competiciondao) {
		this.competiciondao = competiciondao;
	}
	public void setTransactionManager(PlatformTransactionManager tManager) {	
		transactionManager = tManager;
	}
	public void setCompetitionRolService(
			competitionRolService competitionrolservice) {
		this.competitionrolservice = competitionrolservice;
	}
	public void setNewsservice(newsService newsservice) {
		this.newsservice = newsservice;
	}
	
	@Transactional(readOnly = true)
	public Usuario findById(long id) throws InstanceNotFoundException {
	
		Usuario u = usuariodao.find(id);
		return u;
		
	}
	@Transactional(readOnly = true)
	public Usuario findByLogin(String l) throws InstanceNotFoundException {
		Usuario u = usuariodao.findByLogin(l);
		return u;
	}
	
	@Transactional(readOnly = true)
	public Usuario findByMail(String l) throws InstanceNotFoundException {
		Usuario u = usuariodao.findByLogin(l);
		return u;
	}
	
	@Transactional(readOnly = true)
	public Usuario findByActivationKey(String activationKey) throws InstanceNotFoundException {
		Usuario u = usuariodao.getUserByActivationKey(activationKey);
		return u;
	}

	
	
	
	public boolean rememberPwd(String mail) throws InstanceNotFoundException{
		

		Usuario u = usuariodao.findByMail(mail);
		SendMailSSL sendmail = new SendMailSSL();
		return sendmail.SendRememberPasswordMail(u);		
		
		
	}
	
	@Transactional(readOnly = true)
	public UsuarioBlock getUsersOfCompetition(long idCompetition,int startindex,int count) throws InstanceNotFoundException {
		List<Usuario> listaUsuarios = usuariodao.getUsersOfCompetition(idCompetition, startindex, count);
		
        boolean existMoreUsers = listaUsuarios.size() == (Constants.cMaxListItems + 1);
        if (existMoreUsers) {
        	listaUsuarios.remove(listaUsuarios.size() - 1);
        }
        return new UsuarioBlock(listaUsuarios, existMoreUsers);
		
	}	
	
	
	public Usuario userRegister(String nombre, String pass, String email) throws DuplicateInstanceException{
		
		try{
			usuariodao.findByLogin(nombre);		
			throw new DuplicateInstanceException(nombre, Usuario.class.getName());
			
		}catch (InstanceNotFoundException e){

			Usuario u = register(nombre, pass, email);
			
			this.mailService.sendActivationMail(u);
			return u;
		}	
		
	}
	
	@Transactional
	private Usuario register(String nombre, String pass, String email) {
		String encryptedPwd;
		try {
			encryptedPwd = PasswordEncrypter.makeSHA1Hash(pass);
		} catch (NoSuchAlgorithmException e1) {

			e1.printStackTrace();
			return null;
		}

		
		Usuario u = new Usuario(nombre, encryptedPwd, email);
					
		
		u.setActivateKey(UUID.randomUUID().toString());
		
		Log.LogFile(u.getActivateKey());
		
		usuariodao.create(u);
		u.setRol(new Rol(u,2));
		return u;
	}
	
	@Transactional
	public void activateAccount(String activateKey) throws InstanceNotFoundException{
		
		Usuario u = findByActivationKey(activateKey);
		
		u.setActivated(true);
		
		usuariodao.save(u);
		
	}
	@Transactional
	public void setUserValue(long idUsuario, int points) throws InstanceNotFoundException{

			Usuario u = usuariodao.find(idUsuario);
			u.setuserValue(points);
			usuariodao.save(u);
		
	}
	@Transactional
	public void kickUser(long id) throws InstanceNotFoundException{
		
		try{
			Usuario u = usuariodao.find(id);
			
			List<Equipo> listaEquiposUsuario = u.getListaEquipos();		
			
	
			List<Competicion> listaCompeticionesUsuario = u.getListaCompeticiones();
			List<Competicion> listaCompeticionesSinAdmin = new ArrayList<Competicion>();
			
			for (int i=0;i<listaCompeticionesUsuario.size();i++){	
			
	
					if (listaCompeticionesUsuario.get(i).getAdmin() == u) {
						
						
						listaCompeticionesUsuario.get(i).setAdmin(null);
						listaCompeticionesSinAdmin.add(listaCompeticionesUsuario.get(i));
						
						
						competiciondao.save(listaCompeticionesUsuario.get(i));
					}
	
				listaCompeticionesUsuario.get(i).getListaUsuarios().remove(u);

				
				competiciondao.save(listaCompeticionesUsuario.get(i));
				
			
			}
			
			u.getListaEquipos().removeAll(listaEquiposUsuario);
					
			u.getListaCompeticiones().removeAll(listaCompeticionesUsuario);
			
			
			listaCompeticionesSinAdmin.clear();
			
			u.setUserState(1);
			u.setuserValue(Constants.cValueKickedUser);
			
			usuariodao.save(u);
			
		}catch(InstanceNotFoundException e){
			System.out.println("No encontrado");
			throw new InstanceNotFoundException(new String("El usuario a expulsar no existe"),Usuario.class.getName());
	
		}
	
	}
	@Transactional
	public Usuario changeMail(Long id, String email) throws InstanceNotFoundException{
			
		try{
			
			Usuario u = usuariodao.find(id);
			u.setMail(email);			
			usuariodao.update(u);		
			return u;
			
		}catch(InstanceNotFoundException e){
			throw new InstanceNotFoundException(new String("Usuario no encontrado"),Usuario.class.getName());
		}	
	
	}
	@Transactional
	public Usuario changeLogin(Long id, String login) throws InstanceNotFoundException{
		try{
			Usuario u = usuariodao.find(id);
			u.setLogin(login);
			usuariodao.update(u);
			return u;
	
		}catch(InstanceNotFoundException e){
			throw new InstanceNotFoundException(new String("Usuario no encontrado"),Usuario.class.getName());
		}		
	}
	@Transactional
	public boolean changePass(String activationKey, String pass) throws InstanceNotFoundException{
		try{
			Usuario u = findByActivationKey(activationKey);
			try {
				u.setPass( PasswordEncrypter.makeSHA1Hash(pass) );
			} catch (NoSuchAlgorithmException e) {
				return false;
			}
			
		}catch(InstanceNotFoundException e){
			throw new InstanceNotFoundException(new String("Usuario no encontrado"),Usuario.class.getName());
		}
		return true;
	}

	
	
	
	@Transactional
	public boolean joinUserCompetition(long idUsuario, long idEquipo, long idCompeticion) throws InstanceNotFoundException{
	
		Usuario u = usuariodao.find(idUsuario);
		Competicion c = competiciondao.find(idCompeticion);						

		if (TeamIsAssigned(idEquipo)) 
			return false;

		if ((c.getListaUsuarios().contains(u))){
			if (c.getEstado()==Constants.cStateStarted){
				return false;
			}
			else {
				c.getListaUsuarios().remove(u);
				competiciondao.save(c);
			}
		}
		
		if (userHasTeamInCompetition(idUsuario, idCompeticion))
			return false;					
		
		Equipo e1 = equipodao.find(idEquipo);
		e1.setUsuario(u);
		equipodao.save(e1);

		if (c.getAdmin().getIdUsuario()==idUsuario){
			
			newsservice.AddNewToUser(e1, Constants.cNewsAdminDraft, c.getActualDate());
			
		}
		
		u.getListaCompeticiones().add(c);		
		u.getListaEquipos().add(e1);
		
		competitionrolservice.createCompetitionRol(u, c, Constants.cRolUsuarioCompeticion);
		
		usuariodao.save(u);
				
		c.getListaUsuarios().add(u);
		competiciondao.save(c);
		
		if (e1.getCompeticion().getAdmin().getIdUsuario()==u.getIdUsuario())
			newsservice.AddNewToUser(e1, Constants.cNewsCompetitionCreated, e1.getCompeticion().getActualDate());
		else
			newsservice.AddNewToUser(e1, Constants.cNewsJoinCompetition, e1.getCompeticion().getActualDate());
		
		return true;				
					
		
	}
	
	@Transactional(readOnly = true)
	public boolean userHasTeamInCompetition(long idUsuario,long idCompeticion) throws InstanceNotFoundException{
		Usuario u = usuariodao.find(idUsuario);
		Competicion c = competiciondao.find(idCompeticion);
		
		Iterator i = c.getListaEquipos().iterator();		
		
		while (i.hasNext()) {
			Equipo e = (Equipo) i.next();
			if (e.getUsuario() == u) {
				return true;
			}
		}
		return false;
	}
	@Transactional(readOnly = true)
	public boolean userIsKickedFromCompetition(long idUsuario, long idCompeticion) throws InstanceNotFoundException{
		
		Usuario u = usuariodao.find(idUsuario);
		Competicion c = competiciondao.find(idCompeticion);
		
		if ((c.getListaUsuarios().contains(u)) && (Constants.cStateStarted == c.getEstado())){ 
			return true;
		}
		
		return false;
		
		
	}
	@Transactional(readOnly = true)
	public boolean TeamIsAssigned(long idEquipo) throws InstanceNotFoundException{
		 
		Equipo newUserTeam = equipodao.find(idEquipo);
		return (newUserTeam.getUsuario() != null);
					
	}
	@Transactional
	public void QuitCompetition(long idUsuario, long idCompeticion) throws InstanceNotFoundException{

		try{
			Usuario u = usuariodao.find(idUsuario);
			Competicion c = competiciondao.find(idCompeticion);
	

			Equipo userTeamInCompetition = null;
			for (int i=0;i<u.getListaEquipos().size();i++){
				if (u.getListaEquipos().get(i).getCompeticion() == c){
					userTeamInCompetition = u.getListaEquipos().get(i);
					u.getListaEquipos().get(i).setUsuario(null);

					
					equipodao.save(u.getListaEquipos().get(i));
				}
			}
			
			c.getListaUsuarios().remove(u);
			competiciondao.save(c);

			if (userTeamInCompetition != null){
				u.getListaEquipos().remove(userTeamInCompetition);
			}
			
			u.getListaCompeticiones().remove(c);
			u.setuserValue(Constants.cValueUserQuitsFromCompetition);
			usuariodao.save(u);
			
		}catch(InstanceNotFoundException e){
			throw new InstanceNotFoundException(new String("Usuario o Competición no encontrados"),Usuario.class.getName());
		}
	
		
	}


}