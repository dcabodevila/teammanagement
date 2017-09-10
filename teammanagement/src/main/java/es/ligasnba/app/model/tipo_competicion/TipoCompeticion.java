package es.ligasnba.app.model.tipo_competicion;

/**
 *
 * @author sir
 */

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="tipo_competicion")

public class TipoCompeticion {

private Short idTipoCompeticion;
private String nombre;
private String descripcion;
public TipoCompeticion() {
	// TODO Auto-generated constructor stub
}
public TipoCompeticion(Short idTipoCompeticion) {
	this.idTipoCompeticion = idTipoCompeticion;
}

@Column(name="idTipoCompeticion")
@Id
public Short getIdTipoCompeticion() {
	return idTipoCompeticion;
}
public void setIdTipoCompeticion(Short idTipoCompeticion) {
	this.idTipoCompeticion = idTipoCompeticion;
}
@Column(name="nombre")
public String getNombre() {
	return nombre;
}
public void setNombre(String nombre) {
	this.nombre = nombre;
}
@Column(name="descripcion")
public String getDescripcion() {
	return descripcion;
}
public void setDescripcion(String descripcion) {
	this.descripcion = descripcion;
}



}

