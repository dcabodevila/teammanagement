
package es.ligasnba.app.model.jugadordefault;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.codehaus.jackson.annotate.JsonIgnore;

import es.ligasnba.app.model.equipodefault.EquipoDefault;


/**
 *
 * @author sir
 */
@Entity
@Table(name = "jugadordefault")
public class JugadorDefault {
    private Long idJugadorDefault;
    private String nombre;
    private String imagen;
    private EquipoDefault equipoDefault;
    private Date fechaNacimiento;
    private Integer media;
    private Integer pos1;
    private Integer pos2;
    private BigDecimal salario1;
    private BigDecimal salario2;
    private BigDecimal salario3;
    private BigDecimal salario4;
    private int moneyInterest;
    private int winningInterest;
    private int loyaltyInterest;
    private int yearsPro;
    private BigDecimal cache;    
    
    @Column(name="idJugadorDefault")
    @Id
	public Long getIdJugadorDefault() {
		return idJugadorDefault;
	}
    
    public void setIdJugadorDefault(Long idJugadorDefault) {
		this.idJugadorDefault = idJugadorDefault;
	}
	
    @Column(name="imagen")
	public String getImagen() {
		return this.imagen;
	}
	public void setImagen(String imagen) {
		this.imagen = imagen;
	}

    @ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="idEquipoDefault")
    public EquipoDefault getEquipoDefault() {
		return this.equipoDefault;
	}
	public void setEquipoDefault(EquipoDefault equipoDefault) {
		this.equipoDefault = equipoDefault;
	}
    
	@Column(name="nombre")
	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	@Column(name="salario1")
	public BigDecimal getSalario1() {
		return salario1;
	}

	public void setSalario1(BigDecimal salario1) {
		this.salario1 = salario1;
	}
	@Column(name="salario2")
	public BigDecimal getSalario2() {
		return salario2;
	}

	public void setSalario2(BigDecimal salario2) {
		this.salario2 = salario2;
	}
	@Column(name="salario3")
	public BigDecimal getSalario3() {
		return salario3;
	}

	public void setSalario3(BigDecimal salario3) {
		this.salario3 = salario3;
	}
	@Column(name="salario4")
	public BigDecimal getSalario4() {
		return salario4;
	}

	public void setSalario4(BigDecimal salario4) {
		this.salario4 = salario4;
	}
	@Column(name="fechaNacimiento")
	public Date getFechaNacimiento() {
		return fechaNacimiento;
	}

	public void setFechaNacimiento(Date fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}
	
	@Column(name="media")
	public Integer getMedia() {
		return media;
	}

	public void setMedia(Integer media) {
		this.media = media;
	}
	@Column(name="pos1")
	public Integer getPos1() {
		return pos1;
	}

	public void setPos1(Integer pos1) {
		this.pos1 = pos1;
	}
	@Column(name="pos2")
	public Integer getPos2() {
		return pos2;
	}

	public void setPos2(Integer pos2) {
		this.pos2 = pos2;
	}
	@Column(name="moneyInterest")
	public int getMoneyInterest() {
		return moneyInterest;
	}

	public void setMoneyInterest(int moneyInterest) {
		this.moneyInterest = moneyInterest;
	}
	@Column(name="winningInterest")
	public int getWinningInterest() {
		return winningInterest;
	}

	public void setWinningInterest(int winningInterest) {
		this.winningInterest = winningInterest;
	}
	@Column(name="loyaltyInterest")
	public int getLoyaltyInterest() {
		return loyaltyInterest;
	}

	public void setLoyaltyInterest(int loyaltyInterest) {
		this.loyaltyInterest = loyaltyInterest;
	}
	@Column(name="yearsPro")
	public int getYearsPro() {
		return yearsPro;
	}

	public void setYearsPro(int yearsPro) {
		this.yearsPro = yearsPro;
	}
	@Column(name="cache")
	public BigDecimal getCache() {
		return cache;
	}

	public void setCache(BigDecimal cache) {
		this.cache = cache;
	}


    
    

}
