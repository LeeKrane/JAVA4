package db;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class JPAUtil {
	private static EntityManagerFactory EMF = null;
	
	public static EntityManagerFactory getEMF() {
		if(EMF == null) {
			EMF = Persistence.createEntityManagerFactory("jpa-test-unit");
		}
		return EMF;
	}
	
	public static void close() {
		if (EMF.isOpen()) {
			EMF.close();
			EMF = null;
		}
	}
}
