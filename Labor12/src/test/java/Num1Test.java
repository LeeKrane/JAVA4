import num1.db.Repository;
import num1.model.Run;
import num1.model.Runner;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class Num1Test {
	private static Repository rep;
	
	@AfterAll
	static void closeRepository () {
		rep.close();
	}
	
	@BeforeAll
	static void insertTestData () {
		rep = Repository.getINSTANCE();
		
		Runner r1 = new Runner("Huber", "Karl", LocalDate.of(1990,6,7), "M", 75);
		Runner r2 = new Runner("Schmidt", "Eva", LocalDate.of(1997, 10, 26), "W", 55);
		Run l1 = new Run(LocalDate.of(2019,6,11), 55, 10350);
		l1.setRunner(r1);
		Run l2 = new Run(LocalDate.of(2019,6,12), 172, 42195);
		l2.setRunner(r1);
		Run l3 = new Run(LocalDate.of(2019,6,13), 58, 8320);
		l3.setRunner(r2);
		Run l4 = new Run(LocalDate.of(2019,7,14), 83, 15000);
		l4.setRunner(r2);
		Run l5 = new Run(LocalDate.of(2019, 7, 15), 115, 21000);
		l5.setRunner(r2);
		
		rep.persist(r1);
		rep.persist(r2);
		rep.persist(l1);
		rep.persist(l2);
		rep.persist(l3);
		rep.persist(l4);
		rep.persist(l5);
	}
	
	// Testfall: testet DAO.longRun(25000)
	// Liefert eine Liste mit der L¨aufer Huber Karl
	@Test
	void testLongRun1() {
		List<Runner> runner = rep.longRun(25000);
		assertEquals(1, runner.size());
		assertEquals("Huber", runner.get(0).getLastName());
	}
	
	// Testfall: testet DAO.longRun(10000)
	// Liefert eine Liste mit zwei L¨aufern
	@Test
	void testLongRun2() {
		List<Runner> runner = rep.longRun(10000);
		assertEquals(2, runner.size());
	}
	
	// Testfall: testet DAO.longRun(50000)
	// Liefert eine leere Liste
	@Test
	void testLongRun3() {
		List<Runner> runner = rep.longRun(50000);
		assertTrue(runner.isEmpty());
	}
	
	// Testfall: testet DAO.longRun(-10000)
	// Erwartetes Ergebnis: wirft eine IllegalArgumentException
	@Test
	void testLongRun4() {
		assertThrows(IllegalArgumentException.class,
					 () -> rep.longRun(-10000));
	}
	
	// Testfall: liefert die Gesamtstrecke des L¨aufers mit der ID 1 im Juli 2019
	// Erwartetes Ergebnis: 36000
	@Test
	void testTotalDistance1() {
		Runner r = rep.findRunnerById(2);
		System.out.println(r);
		assertEquals(r.getLastName(), "Schmidt");
		int distance = rep.totalDistance(r, LocalDate.of(2019,7,1),
										 LocalDate.of(2019,7,31));
		assertEquals(36000, distance);
	}
	
	// TODO: Write more totalDistance tests
	
}
