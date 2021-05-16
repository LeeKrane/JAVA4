package num1.db;

import lombok.NoArgsConstructor;
import num1.model.Kunde;
import num1.model.Kurs;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.util.List;

@NoArgsConstructor
public abstract class Repository {
	<T> List<T> findAll (Class<T> c) {
		EntityManager em = db.JPAUtil.getEMF().createEntityManager();
		try {
			return em.createQuery("select t from " + c.getSimpleName() + " t", c).getResultList();
		} finally {
			em.close();
		}
	}
	
	public <T> void persist (T toPersist) {
		EntityManager em = db.JPAUtil.getEMF().createEntityManager();
		EntityTransaction tx = em.getTransaction();
		
		try {
			tx.begin();
			if (toPersist instanceof Kurs) {
				((Kurs) toPersist).setDozent(em.merge(((Kurs) toPersist).getDozent()));
				for (Kunde kunde : ((Kurs) toPersist).getKunden())
					em.persist(em.merge(kunde));
			}
			em.persist(toPersist);
			tx.commit();
		} catch (Exception ex) {
			if (tx.isActive())
				tx.rollback();
			throw new KursDBException("Failed to persist:\n" + toPersist + "\n" + ex.getMessage());
		} finally {
			em.close();
		}
	}
	
	<T> T findById (Object id, Class<T> c) {
		EntityManager em = db.JPAUtil.getEMF().createEntityManager();
		try {
			return em.find(c, id);
		} finally {
			em.close();
		}
	}
}
