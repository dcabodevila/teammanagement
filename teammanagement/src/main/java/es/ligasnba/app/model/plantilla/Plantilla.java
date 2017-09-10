package es.ligasnba.app.model.plantilla;

import es.ligasnba.app.model.equipo.Equipo;



import javax.persistence.Entity;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;


@Entity
@Table(name="plantilla")
public class Plantilla {
private long idPlantilla;
private long idEquipo;
private long idJugador;

public Plantilla(){}
public Plantilla(Equipo e){
	this.idEquipo=e.getIdEquipo();
}
@Column(name="id")
@SequenceGenerator(             // It only takes effect for
     name="PlantillaIdGenerator", // databases providing identifier
     sequenceName="PlantillaSeq") // generators.
@Id
@GeneratedValue(strategy=GenerationType.AUTO,
                generator="PlantillaIdGenerator")

public long getIdPlantilla() {
	return idPlantilla;
}               
public void setIdPlantilla(long p) {
	this.idPlantilla = p;
}

public long getidEquipo() {
	return idEquipo;
}
public void setidEquipo(long equipo) {
	this.idEquipo = equipo;
}
public long getIdEquipo() {
	return idEquipo;
}
public long getIdJugador() {
	return idJugador;
}
public void setIdEquipo(long idEquipo) {
	this.idEquipo = idEquipo;
}
public void setIdJugador(long idJugador) {
	this.idJugador = idJugador;
}




}
