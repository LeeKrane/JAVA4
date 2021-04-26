package db;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class JPAUtil {
	private static EntityManagerFactory EMF = null;
	
	public static EntityManagerFactory getEMF() {
		if(EMF == null) {
			try (InputStream inputStream = JPAUtil.class.getResourceAsStream("/connection/connection.properties")) {
				Properties properties = new Properties();
				properties.load(inputStream);
				EMF = Persistence.createEntityManagerFactory("jpa-test-unit", properties);
			} catch (IOException e) {
				System.err.println("IOException in JPAUtil Class: " + e.getMessage());
			}
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
