import num1.db.*;
import num1.model.Dozent;
import num1.model.Kunde;
import num1.model.Kurs;
import num1.model.Kurstyp;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.time.LocalDate;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

public class Num1Test {
	private static Kurssystemservice kurssystemservice;
	private static final int dozent_sizeFromTestData = 4;
	private static final int kunde_sizeFromTestData = 5;
	private static final int kurstyp_sizeFromTestData = 2;
	private static final int kurs_sizeFromTestData = 4;
	
	@AfterEach
	void closeKurssystemservice () {
		kurssystemservice.close();
	}
	
	@BeforeEach
	void insertTestData () {
		kurssystemservice = Kurssystemservice.getINSTANCE();
		
		Dozent[] dozenten = new Dozent[]{
				new Dozent("Reichel", "Otto"),
				new Dozent("Schreiber", "Christoph"),
				new Dozent("Mauss", "Franz"),
				new Dozent("Raab", "Wolfgang")
		};
		
		Kunde[] kunden = new Kunde[]{
				new Kunde("Kranabetter", "Christian"),
				new Kunde("Stiefsohn", "Fabian"),
				new Kunde("Heindl", "Matteo"),
				new Kunde("Sysel", "Peter"),
				new Kunde("Gradinger", "Josef")
		};
		
		Kurstyp[] kurstypen = new Kurstyp[]{
				new Kurstyp("P", "Programmierung"),
				new Kurstyp("C", "Cybersecurity")
		};
		
		Kurs[] kurse = new Kurs[]{
				new Kurs("JPA", LocalDate.of(2015, 5, 13), kurstypen[0], dozenten[0]),
				new Kurs("JDBC", LocalDate.of(2014, 7, 3), kurstypen[0], dozenten[1]),
				new Kurs("CYSEC", LocalDate.of(2017, 10, 27), kurstypen[1], dozenten[3]),
				new Kurs("Java Basics", LocalDate.of(2009, 11, 11), kurstypen[0], dozenten[0])
		};
		
		kunden[0].addKurse(kurse[0], kurse[2]);
		kunden[1].addKurs(kurse[0]);
		kunden[2].addKurse(kurse[0], kurse[3]);
		kunden[3].addKurse(kurse[2], kurse[3]);
		
		Arrays.stream(dozenten).forEach(DozentRepository.getINSTANCE()::persist);
		Arrays.stream(kunden).forEach(KundeRepository.getINSTANCE()::persist);
		Arrays.stream(kurstypen).forEach(KurstypRepository.getINSTANCE()::persist);
		Arrays.stream(kurse).forEach(KursRepository.getINSTANCE()::persist);
	}
	
	@Test
	void getX_listsHaveCorrectSize () {
		assertEquals(dozent_sizeFromTestData, kurssystemservice.getDozenten().size());
		assertEquals(kunde_sizeFromTestData, kurssystemservice.getKunden().size());
		assertEquals(kurstyp_sizeFromTestData, kurssystemservice.getKurstypen().size());
		assertEquals(kurs_sizeFromTestData, kurssystemservice.getKurse().size());
	}
	
	@Test
	void insertX_successfulInsertion () {
		Dozent dozent = new Dozent("Test", "Dozent");
		Kunde kunde = new Kunde("Test", "Kunde");
		Kurstyp kurstyp = new Kurstyp("T", "Test-Kurstyp");
		Kurs kurs = new Kurs("Testkurs", LocalDate.now(), kurstyp, dozent);
		
		kurssystemservice.insertKunde(kunde);
		kurssystemservice.insertKurstyp(kurstyp);
		kurssystemservice.insertKurs(kurs);
		
		assertEquals(dozent_sizeFromTestData + 1, kurssystemservice.getDozenten().size());
		assertEquals(kunde_sizeFromTestData + 1, kurssystemservice.getKunden().size());
		assertEquals(kurstyp_sizeFromTestData + 1, kurssystemservice.getKurstypen().size());
		assertEquals(kurs_sizeFromTestData + 1, kurssystemservice.getKurse().size());
	}
	
	@Test
	void deleteKunde_successfulDeletion () {
		kurssystemservice.deleteKunde(KundeRepository.getINSTANCE().findById(1));
		assertEquals(kunde_sizeFromTestData - 1, kurssystemservice.getKunden().size());
	}
	
	@Test
	void deleteKurstyp_successfulDeletion () {
		Kurstyp kurstyp = new Kurstyp("T", "Test-Kurstyp");
		kurssystemservice.insertKurstyp(kurstyp);
		assertEquals(kurstyp_sizeFromTestData + 1, kurssystemservice.getKurstypen().size());
		kurssystemservice.deleteKurstyp(kurstyp);
		assertEquals(kurstyp_sizeFromTestData, kurssystemservice.getKurstypen().size());
	}
	
	@Test
	void deleteKurstyp_constraintViolation_throwsKursDBException () {
		assertThrows(KursDBException.class,
					 () -> kurssystemservice.deleteKurstyp(KurstypRepository.getINSTANCE().findById("C")));
	}
	
	@ParameterizedTest
	@CsvSource({"3,1", "0,2", "2,3", "2,4"})
	void getKundenFromKurs_listsHaveCorrectSize (int expected, int id) {
		assertEquals(expected, kurssystemservice.getKundenFromKurs(KursRepository.getINSTANCE().findById(id)).size());
	}
	
	@Test
	void bucheKurs_successfulBooking () {
		Kunde kunde = KundeRepository.getINSTANCE().findById(1);
		Kurs kurs = KursRepository.getINSTANCE().findById(2);
		kurssystemservice.bucheKurs(kunde, kurs);
		assertTrue(kunde.getKurse().contains(kurs));
		assertTrue(kurs.getKunden().contains(kunde));
	}
	
	@Test
	void storniereKurs_successfulCancellation () {
		Kunde kunde = KundeRepository.getINSTANCE().findById(1);
		Kurs kurs = KursRepository.getINSTANCE().findById(1);
		kurssystemservice.storniereKurs(kunde, kurs);
		assertFalse(kunde.getKurse().contains(kurs));
		assertFalse(kurs.getKunden().contains(kunde));
	}
}
