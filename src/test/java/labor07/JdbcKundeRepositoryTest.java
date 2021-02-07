package labor07;

import labor07.model.Dozent;
import labor07.model.Kunde;
import labor07.persistence.JdbcKundeRepository;
import labor07.persistence.KundeRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

class JdbcKundeRepositoryTest {
	private KundeRepository kundeRepository;
	private Connection connection;
	
	@BeforeEach
	void connect() throws SQLException {
		String jdbcUrl = "jdbc:h2:mem:test;INIT=RUNSCRIPT FROM 'classpath:/labor07/kurssystem_init_h2.sql'";
		connection = DriverManager.getConnection(jdbcUrl);
		kundeRepository = new JdbcKundeRepository(connection);
	}
	
	@AfterEach
	void disconnect() throws SQLException {
		connection.close();
	}
	
	@Test
	void testFindAllOk() throws SQLException {
		List<Kunde> allActors = kundeRepository.findAll();
		
		assertThat(allActors).extracting(Kunde::getId)
				.containsExactlyInAnyOrder(1, 2, 3, 4, 5, 6);
	}
	
	@Test
	void testFindByIdKundeExistsInDB () throws SQLException {
		Optional<Kunde> opt = kundeRepository.findById(3);
		
		assertThat(opt.get().getZuname()).isEqualTo("Schmidt");
	}
	
	@Test
	void testFindByIdKundeDoesNotExistInDB () throws SQLException {
		Optional<Kunde> opt = kundeRepository.findById(10);
		
		assertThat(opt).isEmpty();
	}
	
	@Test
	void saveActorOK() throws SQLException {
		Kunde toSave = new Kunde("Mauß", "Franz");
		Kunde saved = kundeRepository.save(toSave);
		
		assertThat(saved.getId()).isEqualTo(7);
		Optional<Kunde> opt = kundeRepository.findById(7);
		assertThat(opt.get().getZuname()).isEqualTo("Mauß");
	}
	
	@Test
	void saveActorhasId() {
		Kunde toSave = new Kunde(10, "Mauß", "Franz");
		
		assertThatIllegalArgumentException().isThrownBy(() -> kundeRepository.save(toSave));
	}
	
	@Test
	void deleteKundeOk () throws SQLException {
		boolean deleted = kundeRepository.deleteKunde(2);
		
		assertThat(deleted).isTrue();
		assertThat(kundeRepository.findById(2)).isEmpty();
	}
	
	@Test
	void deleteKundeWithCourses () throws SQLException {
		boolean deleted = kundeRepository.deleteKunde(1);
		
		assertThat(deleted).isFalse();
	}
}