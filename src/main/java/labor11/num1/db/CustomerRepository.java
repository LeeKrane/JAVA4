package labor11.num1.db;

import labor11.num1.model.Customer;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.util.List;
import java.util.Optional;

public class CustomerRepository implements AutoCloseable {
	private static final CustomerRepository INSTANCE = new CustomerRepository();
	
	// private Constructor
	private CustomerRepository () {
	}
	
	public static CustomerRepository getINSTANCE () {
		return INSTANCE;
	}
	
	public Optional<Customer> getCustomerById (Integer id) {
		EntityManager em = JPAUtil.getEMF().createEntityManager();
		try {
			return Optional.ofNullable(em.find(Customer.class, id));
		} finally {
			em.close();
		}
	}
	
	public List<Customer> findAll () {
		EntityManager em = JPAUtil.getEMF().createEntityManager();
		try {
			return em.createQuery("select c from Customer c").getResultList();
		} finally {
			em.close();
		}
	}
	
	public boolean persist (Customer customer) {
		EntityManager em = JPAUtil.getEMF().createEntityManager();
		EntityTransaction tx = em.getTransaction();
		
		try {
			tx.begin();
			em.persist(customer.getAddress());
			em.persist(customer);
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
