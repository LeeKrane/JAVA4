package num2.db;

import num2.model.Car;
import num2.model.Customer;
import num2.model.Rental;
import num2.model.Station;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.time.LocalDate;
import java.util.List;

public class Repository implements AutoCloseable {
	private static final Repository INSTANCE = new Repository();
	
	private Repository () {
	}
	
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
	
	public <T> boolean persist (T toPersist) {
		EntityManager em = db.JPAUtil.getEMF().createEntityManager();
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
	
	// Speichert eine neue Station in der Datenbank
	// liefert true bei Erfolg, false bei einem Fehler
	public boolean insertStation (Station station) {
		return persist(station);
	}
	
	// Speichert ein neues Fahrzeug in der Datenbank
	// liefert true bei Erfolg, false bei einem Fehler
	public boolean insertCar (Car car) {
		return persist(car);
	}
	
	// Speichert einen neuen Mieter in der Datenbank
	// liefert true bei Erfolg, false bei einem Fehler
	public boolean insertCustomer (Customer customer) {
		return persist(customer);
	}
	
	// Speichert einen neuen Mietvorgang in der Datenbank
	// liefert true bei Erfolg, false bei einem Fehler
	public boolean insertRental (Rental rental) {
		return persist(rental);
	}
	
	// Liefert eine Liste aller Fahrzeuge, die in der Station st
	// verfuegbar sind.
	public List<Car> findCarsByStation (Station station) {
		EntityManager em = db.JPAUtil.getEMF().createEntityManager();
		try {
			return em.createQuery("""
					select c from Car c
					where c.location = :st
					""", Car.class)
					.setParameter("st", station)
					.getResultList();
		} finally {
			em.close();
		}
	}
	
	// Liefert eine Liste aller Mietvorg¨ange, die der Customer c
	// get¨atigt hat.
	public List<Rental> findRentalsByCustomer (Customer customer) {
		EntityManager em = db.JPAUtil.getEMF().createEntityManager();
		try {
			return em.createQuery("""
					select r from Rental r
					where r.driver = :cust
					""", Rental.class)
					.setParameter("cust", customer)
					.getResultList();
		} finally {
			em.close();
		}
	}
	
	// Schließt den Mietvorgang r mit dem Rueckgabedatum returnDate
	// bei der Station returnStation ab, wobei km die gefahrenen Kilometer
	// sind. Beachte dass auch die Daten im Mietfahrzeug aktualisiert
	// werden m¨ussen.
	// liefert true bei Erfolg, false bei einem Fehler
	public boolean returnCar (Rental rental, Station returnStation, LocalDate returnDate, Integer km) {
		EntityManager em = db.JPAUtil.getEMF().createEntityManager();
		try {
			return 0 < em.createQuery("""
					update Rental
					set returnStation = :retSt, returnDate = :retDate, km = :km
					where id = :id
					""")
					.setParameter("retSt", returnStation)
					.setParameter("retDate", returnDate)
					.setParameter("km", km)
					.setParameter("id", rental.getId())
					.executeUpdate()
				&& 0 < em.createQuery("""
					update Car
					set location = :retSt
					where registrationNr = :regNr
					""")
					.setParameter("regNr", rental.getCar().getRegistrationNr())
					.executeUpdate();
		} finally {
			em.close();
		}
	}
	
	@Override
	public void close () {
		db.JPAUtil.close();
	}
}
