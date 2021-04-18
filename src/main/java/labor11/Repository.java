package labor11;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.util.List;

public class Repository implements AutoCloseable {
	private static final Repository INSTANCE = new Repository();
	
	private Repository () {
	}
	
	public static Repository getINSTANCE () {
		return INSTANCE;
	}
	
	public <T> List<T> findAll (Class<T> c) {
		EntityManager em = JPAUtil.getEMF().createEntityManager();
		try {
			return em.createQuery("select t from " + c.getSimpleName() + " t", c).getResultList();
		} finally {
			em.close();
		}
	}
	
	public <T> boolean persist (T toPersist) {
		EntityManager em = JPAUtil.getEMF().createEntityManager();
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
		JPAUtil.close();
	}
}
