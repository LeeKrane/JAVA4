package num1.db;

import num1.model.Kurstyp;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.util.List;

public class KurstypRepository extends Repository implements AutoCloseable {
	private static final KurstypRepository INSTANCE = new KurstypRepository();
	
	public static KurstypRepository getINSTANCE () {
		return INSTANCE;
	}
	
	public List<Kurstyp> findAll () {
		return super.findAll(Kurstyp.class);
	}
	
	public Kurstyp findById (String id) {
		return super.findById(id, Kurstyp.class);
	}
	
	public void delete (Kurstyp toDelete) {
		EntityManager em = db.JPAUtil.getEMF().createEntityManager();
		EntityTransaction tx = em.getTransaction();
		
		try {
			tx.begin();
			em.remove(em.merge(toDelete));
			tx.commit();
		} catch (Exception ex) {
			if (tx.isActive())
				tx.rollback();
			throw new KursDBException("Failed to delete:\n" + toDelete + "\n" + ex.getMessage());
		} finally {
			em.close();
		}
	}
	
	@Override
	public void close () {
		db.JPAUtil.close();
	}
}
