package labor11.num1.db;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class JPAUtil {
	private static final EntityManagerFactory EMF;
	
	static {
		try {
			EMF = Persistence.createEntityManagerFactory("jpa-test-unit");
		} catch (Exception ex) {
			System.err.println("EntityManagerFactory creation failed: " + ex);
			throw new ExceptionInInitializerError(ex);
		}
	}
	
	public static EntityManagerFactory getEMF () {
		return EMF;
	}
	
	public static void close () {
		if (EMF.isOpen()) {
			EMF.close();
		}
	}
}
