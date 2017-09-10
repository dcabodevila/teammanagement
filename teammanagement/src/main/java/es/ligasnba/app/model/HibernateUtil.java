package es.ligasnba.app.model;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;



public class HibernateUtil {
	private static final SessionFactory sessionFactory;
	static {
		try {
			sessionFactory = new AnnotationConfiguration().configure("teammanagement-hibernate-config.xml").
			buildSessionFactory();
		} catch (Throwable ex) {
		throw new ExceptionInInitializerError(ex);
		}
	}
	
	public static SessionFactory getSessionFactory() {
		return sessionFactory;		
	}
	public static void shutdown() {
		getSessionFactory().close();
	}

}
