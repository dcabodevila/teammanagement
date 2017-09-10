
package es.ligasnba.app.model.equipodefault;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.JoinColumn;
import javax.persistence.Table;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;

/**
 *
 * @author sir
 */
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Entity
@Table(name = "equipodefault")
public class EquipoDefault {
    private Integer idEquipo;
    private String nombre;
    private String logo;
    private short conference;
    private short division;
    private String abreviatura;
    

    public EquipoDefault(){}
    public EquipoDefault(String n){
        nombre=n;
        logo="";        
    }
    
    @Column(name="idEquipo")
    @SequenceGenerator(             
         name="EquipoIdGenerator",
         sequenceName="EquipoSeq")
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO,
                    generator="EquipoIdGenerator")
    public Integer getIdEquipo(){
        return idEquipo;
    }
    
    public void setIdEquipo(Integer idEquipo) {
		this.idEquipo = idEquipo;
	}
    
    @Column(name="nombreEquipo")
    public String getnombre(){
        return nombre;
    }
    public void setnombre(String nombre){
        this.nombre=nombre;
    }
	
	@JoinColumn(name="logo")
	public String getLogo() {
		return logo;
	}
	public void setLogo(String logo) {
		this.logo = logo;
	}

    public boolean equals(Object o){
    	EquipoDefault e = (EquipoDefault)o;
    	return (this.idEquipo== e.idEquipo);
    }
    @Column(name="conference")
	public short getConference() {
		return conference;
	}
	public void setConference(short conference) {
		this.conference = conference;
	}
	@Column(name="division")
	public short getDivision() {
		return division;
	}
	public void setDivision(short division) {
		this.division = division;
	}
	
	@Column(name="abreviatura")
	public String getAbreviatura() {
		return abreviatura;
	}
	public void setAbreviatura(String abreviatura) {
		this.abreviatura = abreviatura;
	}
//    @OneToMany
//    @JoinColumn(name = "idEquipoDefault")
//    @JsonIgnore    
//	public List<JugadorDefault> getListaJugadoresDefault() {
//		return listaJugadoresDefault;
//	}
//	public void setListaJugadoresDefault(List<JugadorDefault> listaJugadoresDefault) {
//		this.listaJugadoresDefault = listaJugadoresDefault;
//	}


}
