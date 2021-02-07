package labor07.persistence;

import labor07.model.Dozent;
import labor07.model.Kunde;
import labor07.model.Kurs;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface KursRepository {

    // Liefert alle Kurse aus der DB
    List<Kurs> findAll() throws SQLException;

    // Liefrt alle Kurse die der Dozent d hält
    List<Kurs> findAllByDozent(Dozent dozent) throws SQLException;

    // Liefert den Kurs mit dem PK id
    Optional<Kurs> findById(Integer id) throws SQLException;

    // Speichert einen neuen Kurs in der DB
    // In k muss der PK auf null stehen, sonst IllegalArgumetException
    // Das Beginndatum der Kurses k darf nicht in der Vergangenheit liegen,
    // sonst IllegalArgumentException
    // Liefert im Erfolgsfall den um den PK ergänzten Kurs
    Kurs save(Kurs kurs) throws SQLException;

    // Liefert alle Kurse, die der Kunde mit der ID id gebucht hat.
    // Sollte ein Kunde mit dieser ID nicht existieren, so wird eine
    // IllegalArgumentException geworfen
    List<Kurs> getKurseByKunde(Integer id) throws SQLException;


    /**
     * Bucht einen Kunden auf einen Kurs
     * Ein Kunde kann nicht zwei mal auf einen Kurs gebucht werden
     *
     * @param kunde Kunde, der gebucht werden soll
     * @param kurs, der gebucht werden soll
     * @return true bei Erfolg, false sonst
     * @throws SQLException bei einem DB Fehler
     */
    boolean bucheKurs(Kunde kunde, Kurs kurs) throws SQLException;

    /**
     * Storniert einen Kunden fuer einen Kurs
     *
     * @param kunde Kunde, der storniert werden soll
     * @param kurs  Kurs aus dem storniert werden soll
     * @return true bei Erfolg, false sonst
     * @throws SQLException bei einem DB Fehler
     */
    boolean storniereKurs(Kunde kunde, Kurs kurs) throws SQLException;
}
