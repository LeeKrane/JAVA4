package labor07;

import labor07.model.Dozent;
import labor07.persistence.DozentRepository;
import labor07.persistence.JdbcDozentRepository;
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


public class JdbcDozentRepositoryTest {

    private DozentRepository dozentRepository;
    private Connection connection;

    @BeforeEach
    void connect() throws SQLException {
        String jdbcUrl = "jdbc:h2:mem:test;INIT=RUNSCRIPT FROM 'classpath:/labor07/kurssystem_init_h2.sql'";
        connection = DriverManager.getConnection(jdbcUrl);
        dozentRepository = new JdbcDozentRepository(connection);
    }

    @AfterEach
    void disconnect() throws SQLException {
        connection.close();
    }

    @Test
    void testFindAllOk() throws SQLException {
        List<Dozent> allActors = dozentRepository.findAll();

        assertThat(allActors).extracting(Dozent::getId)
                .containsExactlyInAnyOrder(1, 2, 3, 4, 5, 6, 7);
    }

    @Test
    void testFindByIdDozentExistsInDB() throws SQLException {
        Optional<Dozent> opt = dozentRepository.findById(3);

        assertThat(opt.get().getZuname()).isEqualTo("Weizenbaum");
    }

    @Test
    void testFindByIdDozentDoesNotExistInDB() throws SQLException {
        Optional<Dozent> opt = dozentRepository.findById(10);

        assertThat(opt).isEmpty();
    }

    @Test
    void saveActorOK() throws SQLException {
        Dozent toSave = new Dozent("Mauß", "Franz");
        Dozent saved = dozentRepository.save(toSave);

        assertThat(saved.getId()).isEqualTo(8);
        Optional<Dozent> opt = dozentRepository.findById(8);
        assertThat(opt.get().getZuname()).isEqualTo("Mauß");
    }

    @Test
    void saveActorhasId() {
        Dozent toSave = new Dozent(10, "Mauß", "Franz");

        assertThatIllegalArgumentException().isThrownBy(() -> dozentRepository.save(toSave));
    }

    @Test
    void deleteDozentOk() throws SQLException {
        boolean deleted = dozentRepository.deleteDozent(1);

        assertThat(deleted).isTrue();
        assertThat(dozentRepository.findById(1)).isEmpty();
    }

    @Test
    void deleteDozentWithCourses() throws SQLException {
        boolean deleted = dozentRepository.deleteDozent(2);

        assertThat(deleted).isFalse();
    }
}
