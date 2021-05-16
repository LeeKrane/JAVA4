package num2.db;

import lombok.NoArgsConstructor;
import num2.model.Answer;
import num2.model.Approval;
import num2.model.Employee;
import num2.model.Question;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.util.List;

@NoArgsConstructor
public class Repository implements AutoCloseable {
	private static final Repository INSTANCE = new Repository();
	
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
	
	public <T> void persist (T toPersist) {
		EntityManager em = db.JPAUtil.getEMF().createEntityManager();
		EntityTransaction tx = em.getTransaction();
		
		try {
			tx.begin();
			em.persist(toPersist);
			tx.commit();
		} catch (Exception ex) {
			if (tx.isActive()) {
				tx.rollback();
			}
			if (toPersist instanceof Answer)
				throw new IllegalArgumentException("The given employee has already answered the given question.");
		} finally {
			em.close();
		}
	}
	
	public <T> T findById (Integer id, Class<T> c) {
		if (c.equals(Employee.class) && (id < 100000 || id > 999999))
			throw new IllegalArgumentException("The id must not exceed the interval [100000, 999999].");
		if (c.equals(Answer.class))
			throw new IllegalArgumentException("An answer cannot be found through an integer id.");
		
		EntityManager em = db.JPAUtil.getEMF().createEntityManager();
		try {
			return em.find(c, id);
		} finally {
			em.close();
		}
	}
	
	public void answerQuestion (Question question, Employee employee, Approval approval) {
		Answer answer = new Answer(question, employee, approval);
		question.addAnswer(answer);
		employee.addAnswer(answer);
		persist(answer);
	}
	
	@Override
	public void close () {
		db.JPAUtil.close();
	}
}
