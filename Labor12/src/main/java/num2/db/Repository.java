package num2.db;

import num2.model.Car;
import num2.model.Customer;
import num2.model.Rental;
import num2.model.Station;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.time.LocalDate;
import java.util.List;

public class Repository implements AutoCloseable {
	private static final Repository INSTANCE = new Repository();
	
	private Repository () {
	}
	
	public static Repository getINSTANCE () {
		return INSTANCE;
	}
	
	public <T> List<T> findAll (Class<T> c) {
		EntityManager em = db.JPAUtil.getEMF().createEntityManager();
		try {
			return em.createQuery("select t from " + c.getSimpleName() + " t", c).getResultList();
		} finally {
			em.close();
		}
	}
	
	public <T> boolean persist (T toPersist) {
		EntityManager em = db.JPAUtil.getEMF().createEntityManager();
		EntityTransaction tx = em.getTransaction();
		
		try {
			tx.begin();
			em.persist(toPersist);
			tx.commit();
			return true;
		} catch (Exception ex) {
			if (tx.isActive()) {
				tx.rollback();
			}
			return false;
		} finally {
			em.close();
		}
	}
	
	@Override
	public void close () {
		db.JPAUtil.close();
	}
}
