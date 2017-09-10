package es.ligasnba.app.model.usuario;

import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.OneToMany;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.CascadeType;

import es.ligasnba.app.model.competicion.Competicion;
import es.ligasnba.app.model.competitionrol.CompetitionRol;
import es.ligasnba.app.model.equipo.Equipo;
import es.ligasnba.app.model.rol.Rol;
 

import javax.persistence.Table;


import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author sir
 */
@Entity
@Table(name = "usuario")

public class Usuario {
    private long idUsuario;
    private String login;
    private String pwd;
    private String mail;
    private int userValue;
    private int userState;
    private List<Equipo> listaEquipos=new ArrayList<Equipo>();
    private List<Competicion> listaCompeticiones=new ArrayList<Competicion>();
    private Rol rol;
    private List<CompetitionRol> competitionrol= new ArrayList<CompetitionRol>();
    private String activateKey;
    private boolean activated;
    
    public Usuario(){}
    
    
    public Usuario(String n, String p, String m){
        login = n;
        pwd   = p;
        mail  = m;
        activated = false;
    }
    
    @Column(name="idUsuario")
    @SequenceGenerator(             // It only takes effect for
         name="UsuarioIdGenerator", // databases providing identifier
         sequenceName="UsuarioSeq") // generators.
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO,
                    generator="UsuarioIdGenerator")
    public long getIdUsuario() {
        return idUsuario;
    }
    public void setIdUsuario(Long idUsuario) {
        this.idUsuario = idUsuario;
    }
    
    @Column(name="nombreUsuario")
    public String getLogin() {
        return login;
    }
    public void setLogin(String login) {
        this.login = login;
    }
    @OneToOne(mappedBy="usuario", cascade={CascadeType.ALL})
    public Rol getRol() {
		return rol;
	}
    public void setRol(Rol rol) {
		this.rol = rol;
	}
    @OneToMany(cascade={CascadeType.ALL})
    @JoinColumn(name="idUsuario")
	public List<CompetitionRol> getCompetitionrol() {
		return competitionrol;
	}
    public void setCompetitionrol(List<CompetitionRol> competitionrol) {
		this.competitionrol = competitionrol;
	}
    @Column(name="pass")
    public String getPass() {
		return pwd;
	}
    public void setPass(String pass) {
		this.pwd = pass;
	}
    @Column(name="userValue")
    public int getuserValue(){
    	return userValue;
    }
    
    public void setuserValue(int value){
    	userValue = userValue + value;
    }
    
    @Column(name="userState")
    public int getUserState() {
		return userState;
	}
    public void setUserState(int userState) {
		this.userState = userState;
	}
    
    @Column(name="email")
    public String getMail() {
		return mail;
	}
    public void setMail(String mail) {
		this.mail = mail;
	}
	@ManyToMany(
	cascade = {CascadeType.MERGE, CascadeType.PERSIST},
	mappedBy = "listaUsuarios",
	targetEntity = Competicion.class,
	fetch=FetchType.EAGER
	)
	public List<Competicion> getListaCompeticiones() {
		return listaCompeticiones;
	}
	public void setListaCompeticiones(List<Competicion> listaCompeticiones) {
		this.listaCompeticiones = listaCompeticiones;
	}
 

	@OneToMany(cascade = {CascadeType.PERSIST})
	@JoinColumn(name="idUsuario")
    public List<Equipo> getListaEquipos(){
        return this.listaEquipos;
    }
    public void setListaEquipos(List<Equipo> lista) {
        this.listaEquipos = lista;
    }
	  
    public void addEquipo(Equipo e){
        getListaEquipos().add(e);
        e.setUsuario(this);
    }
    public void remEquipo(Equipo e){
    	getListaEquipos().remove(e);
    	e.setUsuario(null);		
    }
    

    public void addCompeticion(Competicion com){
        getListaCompeticiones().add(com);        
        
    }
    public void remCompeticion(Competicion com){
        getListaCompeticiones().remove(com);
    }
    @Column(name="activateKey")
    public String getActivateKey() {
		return activateKey;
	}
    public void setActivateKey(String activateKey) {
		this.activateKey = activateKey;
	}
    
    @Column(name="activated")
    public boolean isActivated() {
		return activated;
	}
    public void setActivated(boolean activated) {
		this.activated = activated;
	}
    
    public boolean equals(Object o){
    	Usuario usuario = (Usuario)o;
    	return (
    			this.idUsuario == usuario.idUsuario &&
    			this.login.equals(usuario.login)
    			);
    }

}
