package es.ligasnba.app.model.plantilla;


import es.ligasnba.app.model.generic.GenericDaoHibernate;
import org.springframework.stereotype.Repository;

@Repository("PlantillaDao")
public class PlantillaDaoHibernate extends GenericDaoHibernate<Plantilla,Long> implements PlantillaDao {

}
