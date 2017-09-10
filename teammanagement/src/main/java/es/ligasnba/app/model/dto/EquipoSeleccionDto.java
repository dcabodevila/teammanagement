
package es.ligasnba.app.model.dto;

/**
 *
 * @author sir
 */
public class EquipoSeleccionDto {
    private Long idEquipo;
    private String nombre;
    private String logo;
    
    public Long getIdEquipo(){
        return idEquipo;
    }
    
    public void setIdEquipo(Long idEquipo) {
		this.idEquipo = idEquipo;
	}
    

    public String getnombre(){
        return nombre;
    }
    public void setnombre(String nombre){
        this.nombre=nombre;
    }

	public String getLogo() {
		return logo;
	}
	public void setLogo(String logo) {
		this.logo = logo;
	}
}
