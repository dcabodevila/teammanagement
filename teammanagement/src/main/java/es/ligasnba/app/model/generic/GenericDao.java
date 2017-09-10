package es.ligasnba.app.model.generic;

import java.io.Serializable;

import es.ligasnba.app.util.exceptions.InstanceNotFoundException;


public interface GenericDao <E, PK extends Serializable>{

	void save(E entity);

	E find(PK id) throws InstanceNotFoundException;
	boolean exists(PK id);
	void create(E entity);
	void remove(PK id) throws InstanceNotFoundException;
    public E update(E entity);

}
