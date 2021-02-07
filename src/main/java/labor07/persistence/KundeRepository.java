package labor07.persistence;


import labor07.model.Kunde;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface KundeRepository {
    // Liefert alle Kunden aus der DB
    List<Kunde> findAll() throws SQLException;
    // Liefert den Kunden mit dem PK id
    Optional<Kunde> findById(Integer id) throws SQLException;
    // Speichert einen Kunden in der DB
    // In k muss der PK auf null stehen, sonst IllegalArgumetException
    // Liefert im Erfolgsfall den um den PK ergänzten Kunden
    Kunde save(Kunde kunde) throws SQLException;
    // Löscht den Kunden mit dem PK id aus der DB
    // liefert true bei Erfolg, false wenn der Kunde noch auf einen Kurs gebucht ist
    boolean deleteKunde(Integer id) throws SQLException;
}
