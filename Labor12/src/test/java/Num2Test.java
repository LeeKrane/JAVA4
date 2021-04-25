import num2.db.Repository;
import num2.model.Car;
import num2.model.Customer;
import num2.model.Rental;
import num2.model.Station;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class Num2Test {
	private static Repository rep;
	
	// AfterEach aufgrund eines Tests der die zu testenden Daten verändert
	@AfterEach
	void closeRepository () {
		rep.close();
	}
	
	// BeforeEach aufgrund eines Tests der die zu testenden Daten verändert
	@BeforeEach
	void insertTestData () {
		rep = Repository.getINSTANCE();
		
		Station[] stations = new Station[]{
				new Station("St. Pölten"),
				new Station("Wien"),
				new Station("Melk")
		};
		
		Car[] cars = new Car[]{
				new Car("1FTKR1AD0AP340387", 2007, 7547, "M5", stations[0]),
				new Car("JM3TB2CA0E0449362", 2003, 4227, "Outback", stations[1]),
				new Car("3CZRE3H38BG562245", 2006, 8190, "xB", stations[0]),
				new Car("SCBLC37F25C065649", 1964, 9819, "GTO", stations[1]),
				new Car("JM1NC2EF1A0363396", 2004, 6077, "Galant", stations[0]),
				new Car("JN1CV6EK0DM950520", 1997, 7885, "Yukon", stations[1]),
				new Car("JTJBC1BA9E2912808", 2011, 2223, "CX-9", stations[0]),
				new Car("WBAEW53483P079071", 2003, 1554, "Lancer Evolution", stations[0]),
				new Car("5N1AR2MM0EC864129", 1994, 5633, "Concorde", stations[1]),
				new Car("1FMEU5BE6AU861265", 2012, 4005, "ZDX", stations[0])
		};
		
		Customer[] customers = new Customer[]{
				new Customer(123456, "Kranabetter", "Christian"),
				new Customer(654321, "Stiefsohn", "Fabian"),
				new Customer(975310, "Heindl", "Matteo")
		};
		
		Rental[] rentals = new Rental[]{
				new Rental(214, LocalDate.of(2020, 10, 10), LocalDate.of(2020, 11, 27), cars[0], customers[0], stations[1]),
				new Rental(178, LocalDate.of(2020, 11, 30), LocalDate.of(2021, 2, 21), cars[1], customers[1], stations[0]),
				new Rental(124, LocalDate.of(2021, 2, 1), LocalDate.of(2021, 3, 6), cars[2], customers[2], stations[2]),
				new Rental(LocalDate.of(2021, 5, 13), cars[8], customers[0]),
				new Rental(LocalDate.of(2021, 7, 15), cars[9], customers[1])
		};
		
		for (Station s : stations)
			assertTrue(rep.insertStation(s));
		
		for (Car c : cars)
			assertTrue(rep.insertCar(c));
		
		for (Customer c : customers)
			assertTrue(rep.insertCustomer(c));
		
		for (Rental r : rentals)
			assertTrue(rep.insertRental(r));
	}
	
	@ParameterizedTest
	@CsvSource({"1,4", "2,3", "3,1"})
	void findCarsByStation_correctListLengths (int stationId, int expectedLength) {
		List<Car> cars = rep.findCarsByStation(rep.findStationById(stationId));
		assertEquals(expectedLength, cars.size());
	}
	
	@ParameterizedTest
	@CsvSource({"123456,2", "654321,2", "975310,1"})
	void findRentalsByCustomer_correctListLengths (int customerId, int expectedLength) {
		List<Rental> rentals = rep.findRentalsByCustomer(rep.findCustomerById(customerId));
		assertEquals(expectedLength, rentals.size());
	}
	
	@ParameterizedTest
	@CsvSource({"4,42,1,2021,8,6,5N1AR2MM0EC864129", "5,47,2,2021,9,19,1FMEU5BE6AU861265"})
	void returnCar_correctChangesInDB (int rentalId, int km, int stationId, int year, int month, int day, String regNr) {
		rep.returnCar(rep.findRentalById(rentalId), rep.findStationById(stationId), LocalDate.of(year, month, day), km);
		
		Station station = rep.findStationById(stationId);
		Rental rental = rep.findRentalById(rentalId);
		Car car = rep.findCarByRegNr(regNr);
		
		assertEquals(station, car.getLocation());
		assertEquals(station, rental.getReturnStation());
	}
	
	@Test
	void insertX_successfulPersisting () {
		assertTrue(rep.insertStation(new Station("Test")));
		assertTrue(rep.insertCar(new Car("Test", 1234, 123, "Test", rep.findStationById(4))));
		assertTrue(rep.insertCustomer(new Customer(187265, "Test", "Test")));
		assertTrue(rep.insertRental(new Rental(LocalDate.of(2000, 1, 1), rep.findCarByRegNr("Test"), rep.findCustomerById(187265))));
	}
}
