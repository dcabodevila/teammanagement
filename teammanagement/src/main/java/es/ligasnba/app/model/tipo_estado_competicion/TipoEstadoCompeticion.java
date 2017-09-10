package es.ligasnba.app.model.tipo_estado_competicion;

/**
 *
 * @author sir
 */

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="tipo_estado_competicion")

public class TipoEstadoCompeticion {

private Short idTipoEstadoCompeticion;
private String nombre;
private String descripcion;

public TipoEstadoCompeticion() {
	// TODO Auto-generated constructor stub
}
public TipoEstadoCompeticion(Short idTipoEstadoCompeticion) {
	this.idTipoEstadoCompeticion = idTipoEstadoCompeticion;
}
@Column(name="idTipoEstado")
@Id
public Short getIdTipoEstadoCompeticion() {
	return idTipoEstadoCompeticion;
}
public void setIdTipoEstadoCompeticion(Short idTipoEstadoCompeticion) {
	this.idTipoEstadoCompeticion = idTipoEstadoCompeticion;
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

