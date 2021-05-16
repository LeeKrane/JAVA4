package num1.db;

import num1.model.Dozent;
import num1.model.Kunde;
import num1.model.Kurs;
import num1.model.Kurstyp;

import java.util.List;

/**
 *
 * @author reio
 */
public interface IKurssystemService {
	
	/**
	 * Liefert alle Kunden aus der DB als typsichere Liste
	 * aufsteigend nach ihren IDs sortiert
	 * @return Liste aller Kunden
	 * @throws KursDBException bei einem Fehler
	 */
	List<Kunde> getKunden() throws KursDBException;
	
	
	/**
	 * Speichert einen neuen Kunden in die DB.
	 * Wenn der übergebene Kunde bereits eine ID ungleich
	 * 0 besitzt, wird eine IllegalArgumentException geworfen
	 * @param k ein neuer Kunde
	 * @throws KursDBException bei einem Fehler
	 * @throws IllegalArgumentException wenn der Kunde k eine ID ungleich 0 (null)
	 *                                  besitzt
	 */
	void insertKunde(Kunde k) throws KursDBException;
	
	
	/**
	 * Loescht einen Kunden aus der DB
	 * @param k zu loeschender Kunde
	 * @throws KursDBException bei einem Fehler
	 */
	void deleteKunde(Kunde k) throws KursDBException;
	
	/**
	 * Liefert alle Dozenten aus der DB als typsichere Liste
	 * aufsteigend nach ihren IDs sortiert
	 * @return Liste aller Dozenten
	 * @throws KursDBException bei einem DB Fehler
	 */
	List<Dozent> getDozenten() throws KursDBException;
	
	/**
	 * Liefert alle Kurstypen aus der DB als typsichere Liste
	 * aufsteigend nach ihren IDs sortiert
	 * @return Liste aller Kurstypen
	 * @throws KursDBException bei einem DB Fehler
	 */
	List<Kurstyp> getKurstypen() throws KursDBException;
	
	/**
	 * Liefert alle Kurse aus der DB als typsichere Liste
	 * aufsteigend nach ihren IDs sortiert
	 * @return Liste aller Kurse
	 * @throws KursDBException bei einem DB Fehler
	 */
	List<Kurs> getKurse() throws KursDBException;
	
	/**
	 * Speichert einen neuen Kurstypen in die DB
	 * @param kt ein neuer Kurstyp
	 * @throws KursDBException bei einem DB Fehler
	 */
	void insertKurstyp(Kurstyp kt) throws KursDBException;
	
	/**
	 * Loescht einen Kurstyp aus der DB
	 * @param kt zu loeschender Kurstyp
	 * @throws KursDBException bei einem DB Fehler
	 */
	void deleteKurstyp(Kurstyp kt) throws KursDBException;
	
	/**
	 * Speichert einen neuen Kurs in die DB
	 * @param kurs ein neuer Kurs
	 * @throws KursDBException bei einem DB Fehler
	 */
	void insertKurs(Kurs kurs) throws KursDBException;
	
	/**
	 * Liefert alle Kunden, die einen Kurs gebucht haben
	 * @param kurs Kurs an den Kunden teilnehmen
	 * @throws KursDBException bei einem DB Fehler
	 * @throws IllegalArgumentException wenn der Kurs kurs eine ID ungleich 0 (null)
	 *                                  besitzt
	 */
	List<Kunde> getKundenFromKurs(Kurs kurs) throws KursDBException;
	
	/**
	 * Bucht einen Kunden auf einen Kurs
	 * @param kunde Kunde, der gebucht werden soll
	 * @param kurs, der gebucht werden soll
	 * @throws KursDBException bei einem DB Fehler
	 */
	void bucheKurs(Kunde kunde, Kurs kurs) throws KursDBException;
	
	/**
	 * Storniert einen Kunden fuer einen Kurs
	 * @param kunde Kunde, der storniert werden soll
	 * @param kurs Kurs aus dem storniert werden soll
	 * @throws KursDBException bei einem DB Fehler
	 */
	void storniereKurs(Kunde kunde, Kurs kurs) throws KursDBException;

}
