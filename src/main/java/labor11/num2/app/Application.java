package labor11.num2.app;

import labor11.db.Repository;
import labor11.num2.model.Benutzer;
import labor11.num2.model.Reisetyp;
import labor11.num2.model.Reiseveranstaltung;

import java.time.LocalDate;

public class Application {
	public static void main (String[] args) {
		Benutzer b1 = new Benutzer("tobi@gmail.com", "tobip64", false);
		Benutzer b2 = new Benutzer("made@gmail.com", "madmade", true);
		Benutzer b3 = new Benutzer("stepson@gmail.com", "sehrsicherespasswort", false);
		Benutzer b4 = new Benutzer("sosserl@gmail.com", "nochV1elSichereresP@55wor7", true);
		Benutzer b5 = new Benutzer("paliel@gmail.com", "011011000110111101101100", false);
		
		Reisetyp r1 = new Reisetyp("Städtereisen");
		Reisetyp r2 = new Reisetyp("Italien");
		Reisetyp r3 = new Reisetyp("Last Minute");
		
		Reiseveranstaltung rv1 = new Reiseveranstaltung("St. Pölten", "Gute Reise", LocalDate.now(), LocalDate.of(2021, 10, 15), 186.0, r1);
		Reiseveranstaltung rv2 = new Reiseveranstaltung("Wien", "Nice Reise", LocalDate.now(), LocalDate.of(2021, 11, 14), 142.0, r1);
		Reiseveranstaltung rv3 = new Reiseveranstaltung("Linz", "Genüssliche Reise", LocalDate.now(), LocalDate.of(2021, 12, 21), 165.0, r2);
		Reiseveranstaltung rv4 = new Reiseveranstaltung("Graz", "Klasse Reise", LocalDate.now(), LocalDate.of(2021, 8, 15), 132.0, r2);
		Reiseveranstaltung rv5 = new Reiseveranstaltung("Eisenstadt", "Mehr als gut", LocalDate.now(), LocalDate.of(2021, 10, 2), 102.0, r3);
		Reiseveranstaltung rv6 = new Reiseveranstaltung("Lilienfeld", "Besser gehts nicht", LocalDate.now(), LocalDate.of(2021, 9, 28), 115.0, r3);
		
		r1.addReiseveranstaltung(rv1);
		r1.addReiseveranstaltung(rv2);
		r2.addReiseveranstaltung(rv3);
		r2.addReiseveranstaltung(rv4);
		r3.addReiseveranstaltung(rv5);
		r3.addReiseveranstaltung(rv6);
		
		b1.addReisetyp(r1);
		b2.addReisetyp(r2);
		b3.addReisetyp(r3);
		b4.addReisetyp(r2);
		b5.addReisetyp(r3);
		
		try (Repository rep = Repository.getINSTANCE()) {
			rep.persist(r1);
			rep.persist(r2);
			rep.persist(r3);
			rep.persist(rv1);
			rep.persist(rv2);
			rep.persist(rv3);
			rep.persist(rv4);
			rep.persist(rv5);
			rep.persist(rv6);
			rep.persist(b1);
			rep.persist(b2);
			rep.persist(b3);
			rep.persist(b4);
			rep.persist(b5);
		}
	}
}
