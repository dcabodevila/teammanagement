package es.ligasnba.app.model.generic;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import es.ligasnba.app.util.exceptions.InstanceNotFoundException;

public class GenericDaoHibernate<E, PK extends Serializable> implements
GenericDao<E, PK> {
	
	private SessionFactory sessionFactory;

	private Class<E> entityClass;

	@SuppressWarnings("unchecked")
	public GenericDaoHibernate() {
		this.entityClass = (Class<E>) ((ParameterizedType) getClass()
				.getGenericSuperclass()).getActualTypeArguments()[0];		
	}

	@Autowired
	public void setSessionFactory(SessionFactory sessionfactory) {
		this.sessionFactory = sessionfactory;
	}

	protected Session getSession() {		
		return sessionFactory.getCurrentSession();
		//return sessionFactory.openSession();
		
	}

	public void save(E entity) {
		getSession().saveOrUpdate(entity);
	}

	public boolean exists(PK id) {
		return getSession().createCriteria(entityClass).add(
				Restrictions.idEq(id)).setProjection(Projections.id())
				.uniqueResult() != null;
	}

	@SuppressWarnings("unchecked")
	public E find(PK id) throws InstanceNotFoundException {
		E entity = (E) getSession().get(entityClass, id);
		if (entity == null) {
			throw new InstanceNotFoundException(id, entityClass.getName());
		}
		return entity;
	}
    public void create(E entity) {
        getSession().persist(entity);
    }
    
	public void remove(PK id) throws InstanceNotFoundException {
		getSession().delete(find(id));
	}
    @SuppressWarnings("unchecked")
    public E update(E entity) {
        return (E) getSession().merge(entity);
    }

}
