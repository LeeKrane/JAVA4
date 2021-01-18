package labor07.persistence;


import labor07.domain.Dozent;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface DozentRepository {

    // Liefert alle Dozenten aus der DB
    List<Dozent> findAll() throws SQLException;
    // Liefert den Dozenten mit dem PK id
    Optional<Dozent> findById(Integer id) throws SQLException;
    // Speichert einen Dozenten in der DB
    // In d muss der PK auf null stehen, sonst IllegalArgumetException
    // Liefert im Erfolgsfall den um den PK ergänzten Dozenten
    Dozent save(Dozent dozent) throws SQLException;
    // Löscht den Dozenten mit dem PK id aus der DB
    // liefert true bei Erfolg, false wenn der Dozent noch in Kursen eingetragen ist
    boolean deleteDozent(Integer id) throws SQLException;
}
