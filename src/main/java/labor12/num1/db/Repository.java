package labor12.num1.db;

import labor12.num1.model.Runner;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;
import java.time.LocalDate;
import java.util.List;

public class Repository implements AutoCloseable {
	private static final Repository INSTANCE = new Repository();
	
	private Repository () {
	}
	
	public static Repository getINSTANCE () {
		return INSTANCE;
	}
	
	public List<Runner> findAll () {
		EntityManager em = labor12.db.JPAUtil.getEMF().createEntityManager();
		try {
			return em.createQuery("select r from Runner r", Runner.class).getResultList();
		} finally {
			em.close();
		}
	}
	
	public <T> boolean persist (T toPersist) {
		EntityManager em = labor12.db.JPAUtil.getEMF().createEntityManager();
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
	
	public List<Runner> longRun (Integer m) {
		if (m <= 0)
			throw new IllegalArgumentException("Die Meter dürfen nicht negativ sein.");
		EntityManager em = labor12.db.JPAUtil.getEMF().createEntityManager();
		try {
			TypedQuery<Runner> query = em.createQuery("""
					select distinct r from Runner r
					join Run run on r.id = run.runner.id
					where run.distance > :m
						""",
					Runner.class);
			query.setParameter("m", m);
			return query.getResultList();
		} finally {
			em.close();
		}
	}
	
	public Integer totalDistance (Runner runner, LocalDate from, LocalDate to) {
		EntityManager em = labor12.db.JPAUtil.getEMF().createEntityManager();
		try {
			TypedQuery<Long> query = em.createQuery("""
					select sum(r.distance) from Run r
					where r.runner.id = :rid and r.date >= :from and r.date <= :to
						""",
				   Long.class);
			query.setParameter("rid", runner.getId());
			query.setParameter("from", from);
			query.setParameter("to", to);
			return query.getSingleResult().intValue();
		} finally {
			em.close();
		}
	}
	
	public Runner findRunnerById (Integer id) {
		EntityManager em = labor12.db.JPAUtil.getEMF().createEntityManager();
		try {
			return em.find(Runner.class, id);
		} finally {
			em.close();
		}
	}
	
	@Override
	public void close () {
		labor12.db.JPAUtil.close();
	}
}
