package labor07;

import labor07.model.Dozent;
import labor07.model.Kunde;
import labor07.model.Kurs;
import labor07.persistence.JdbcKursRepository;
import labor07.persistence.KursRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

class JdbcKursRepositoryTest {
	private KursRepository kursRepository;
	private Connection connection;
	
	@BeforeEach
	void connect() throws SQLException {
		String jdbcUrl = "jdbc:h2:mem:test;INIT=RUNSCRIPT FROM 'classpath:/labor07/kurssystem_init_h2.sql'";
		connection = DriverManager.getConnection(jdbcUrl);
		kursRepository = new JdbcKursRepository(connection);
	}
	
	@AfterEach
	void disconnect() throws SQLException {
		connection.close();
	}
	
	@Test
	void testFindAllOk() throws SQLException {
		List<Kurs> allActors = kursRepository.findAll();
		
		assertThat(allActors).extracting(Kurs::getId)
				.containsExactlyInAnyOrder(1, 2, 3, 4, 5, 6);
	}
	
	@Test
	void testFindAllByDozentOk() throws SQLException {
		List<Kurs> allActors = kursRepository.findAllByDozent(new Dozent(2, "Gernhardt", "Wolfgang"));
		
		assertThat(allActors).extracting(Kurs::getId)
				.containsExactlyInAnyOrder(1, 3);
	}
	
	@Test
	void testFindByIdDozentExistsInDB() throws SQLException {
		Optional<Kurs> opt = kursRepository.findById(3);
		
		assertThat(opt.get().getBezeichnung()).isEqualTo("JDBC");
	}
	
	@Test
	void testFindByIdDozentDoesNotExistInDB() throws SQLException {
		Optional<Kurs> opt = kursRepository.findById(10);
		
		assertThat(opt).isEmpty();
	}
	
	@Test
	void saveActorOK() throws SQLException {
		Kurs toSave = new Kurs('P', 2, "Gamedevelopment mit Unity", Date.valueOf("2020-04-20"));
		Kurs saved = kursRepository.save(toSave);
		
		assertThat(saved.getId()).isEqualTo(7);
		Optional<Kurs> opt = kursRepository.findById(7);
		assertThat(opt.get().getBezeichnung()).isEqualTo("Gamedevelopment mit Unity");
	}
	
	@Test
	void saveActorhasId() {
		Kurs toSave = new Kurs(10, 'P', 2, "Gamedevelopment mit Unity", Date.valueOf("2020-04-20"));
		
		assertThatIllegalArgumentException().isThrownBy(() -> kursRepository.save(toSave));
	}
	
	@Test
	void testGetKurseByKundeOk() throws SQLException {
		List<Kurs> allActors = kursRepository.getKurseByKunde(5);
		
		assertThat(allActors).extracting(Kurs::getId)
				.containsExactlyInAnyOrder(2, 3);
	}
	
	@Test
	void bucheKursOk() throws SQLException {
		Kunde kunde = new Kunde(3, "Schmidt", "Lothar");
		Kurs kurs = new Kurs(5, 'P',5,"GUI-Programmierung mit Java", Date.valueOf("2010-10-09"));
		
		assertThat(kursRepository.bucheKurs(kunde, kurs)).isTrue();
		assertThat(kursRepository.getKurseByKunde(3))
				.extracting(Kurs::getId)
				.contains(5);
	}
	
	@Test
	void storniereKursOk() throws SQLException {
		Kunde kunde = new Kunde(3, "Schmidt", "Lothar");
		Kurs kurs = new Kurs(2, 'S',3,"JavaScript", Date.valueOf("2010-06-29"));
		
		assertThat(kursRepository.storniereKurs(kunde, kurs)).isTrue();
		assertThat(kursRepository.getKurseByKunde(3))
				.extracting(Kurs::getId)
				.containsExactlyInAnyOrder(1);
	}
	
	@Test
	void bucheKursDoppelterEintragReturnFalse() throws SQLException {
		Kunde kunde = new Kunde(6, "Kaiser", "Leo");
		Kurs kurs = new Kurs(1, 'P',2,"Objektorientierte Programmierung mit Java", Date.valueOf("2010-08-27"));
		
		assertThat(kursRepository.bucheKurs(kunde, kurs)).isFalse();
	}
}