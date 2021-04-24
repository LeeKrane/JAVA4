import num2.db.Repository;
import num2.model.Car;
import num2.model.Customer;
import num2.model.Rental;
import num2.model.Station;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Arrays;

public class Num2Test {
	private static Repository rep;
	
	@AfterAll
	static void closeRepository () {
		rep.close();
	}
	
	/*
	• wenigstens 3 Stationen
	• wenigstens 10 Fahrzeuge
	• wenigstens 3 Mieter
	• wenigstens 5 Fahrten, davon 3 abgeschlossene und 2 nicht abgeschlossene
	 */
	@BeforeAll
	static void insertTestData () {
		rep = Repository.getINSTANCE();
		
		Station[] stations = new Station[]{
				new Station("St. Pölten"),
				new Station("Wien"),
				new Station("Melk")
		};
		
		Car[] cars = new Car[]{
				new Car("1FTKR1AD0AP340387", 2007, 7547, "M5", stations[0]),
				new Car("JM3TB2CA0E0449362", 2003, 4227, "Outback", stations[1]),
				new Car("3CZRE3H38BG562245", 2006, 8190, "xB", stations[2]),
				new Car("SCBLC37F25C065649", 1964, 9819, "GTO", stations[0]),
				new Car("JM1NC2EF1A0363396", 2004, 6077, "Galant", stations[1]),
				new Car("JN1CV6EK0DM950520", 1997, 7885, "Yukon", stations[2]),
				new Car("JTJBC1BA9E2912808", 2011, 2223, "CX-9", stations[0]),
				new Car("WBAEW53483P079071", 2003, 1554, "Lancer Evolution", stations[1]),
				new Car("5N1AR2MM0EC864129", 1994, 5633, "Concorde"),
				new Car("1FMEU5BE6AU861265", 2012, 4005, "ZDX")
		};
		
		Customer[] customers = new Customer[]{
				new Customer(123456, "Kranabetter", "Christian"),
				new Customer(654321, "Stiefsohn", "Fabian"),
				new Customer(975310, "Heindl", "Matteo")
		};
		
		Rental[] rentals = new Rental[]{
				new Rental(214, LocalDate.of(2020, 10, 10), LocalDate.of(2020, 11, 27), cars[0], customers[0], stations[1], stations[0]),
				new Rental(178, LocalDate.of(2020, 11, 30), LocalDate.of(2021, 2, 21), cars[1], customers[1], stations[0], stations[1]),
				new Rental(124, LocalDate.of(2021, 2, 1), LocalDate.of(2021, 3, 6), cars[2], customers[2], stations[2], stations[2]),
				new Rental(0, LocalDate.of(2021, 5, 13), cars[8], customers[0], stations[2]),
				new Rental(0, LocalDate.of(2021, 7, 15), cars[9], customers[1], stations[0])
		};
		
		for (Station s : stations)
			rep.insertStation(s);
		
		for (Car c : cars)
			rep.insertCar(c);
		
		for (Customer c : customers)
			rep.insertCustomer(c);
		
		for (Rental r : rentals)
			rep.insertRental(r);
	}
	
	@Test
	void insertStation_() {
		System.out.println("lol");
	}
}
