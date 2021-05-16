package num1.db;

import num1.model.Kunde;
import num1.model.Kurs;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.util.List;

public class KundeRepository extends Repository implements AutoCloseable {
	private static final KundeRepository INSTANCE = new KundeRepository();
	
	public static KundeRepository getINSTANCE () {
		return INSTANCE;
	}
	
	public List<Kunde> findAll () {
		return super.findAll(Kunde.class);
	}
	
	public Kunde findById (Integer id) {
		return super.findById(id, Kunde.class);
	}
	
	public List<Kunde> getKundenByKurs (Kurs kurs) {
		EntityManager em = db.JPAUtil.getEMF().createEntityManager();
		try {
			return em.createQuery("""
									select distinct k from Kunde k
									where :kurs member k.kurse
									""", Kunde.class)
					.setParameter("kurs", kurs)
					.getResultList();
		} catch (Exception e) {
			throw new KursDBException("Failed to query 'getKundenByKurs':\n" + kurs + "\n" + e.getMessage());
		} finally {
			em.close();
		}
	}
	
	public void bucheKurs (Kunde kunde, Kurs kurs) {
		EntityManager em = db.JPAUtil.getEMF().createEntityManager();
		EntityTransaction tx = em.getTransaction();
		
		kunde.addKurs(kurs);
		
		try {
			tx.begin();
			em.merge(kunde);
			em.merge(kurs);
			tx.commit();
		} catch (Exception ex) {
			if (tx.isActive())
				tx.rollback();
			throw new KursDBException("Failed to book Kurs:\n" + kunde + "\n" + kurs + "\n" + ex.getMessage());
		} finally {
			em.close();
		}
	}
	
	public void storniereKurs (Kunde kunde, Kurs kurs) {
		EntityManager em = db.JPAUtil.getEMF().createEntityManager();
		EntityTransaction tx = em.getTransaction();
		
		kunde.removeKurs(kurs);
		
		try {
			tx.begin();
			em.merge(kunde);
			em.merge(kurs);
			tx.commit();
		} catch (Exception ex) {
			if (tx.isActive())
				tx.rollback();
			throw new KursDBException("Failed to cancel Kurs:\n" + kunde + "\n" + kurs + "\n" + ex.getMessage());
		} finally {
			em.close();
		}
	}
	
	public void delete (Kunde toDelete) {
		EntityManager em = db.JPAUtil.getEMF().createEntityManager();
		EntityTransaction tx = em.getTransaction();
		
		try {
			tx.begin();
			for (Kurs kurs : toDelete.getKurse()) {
				kurs.removeKunde(toDelete);
				em.merge(kurs);
			}
			toDelete.getKurse().clear();
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
