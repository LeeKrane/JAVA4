package labor06;

public class Schueler {
	String familienname;
	String vorname;
	char geschlecht;
	int katNr;
	String klasse;
	
	public Schueler (String familienname, String vorname, char geschlecht, int katNr, String klasse) {
		this.familienname = familienname;
		this.vorname = vorname;
		this.geschlecht = geschlecht;
		this.katNr = katNr;
		this.klasse = klasse;
	}
	
	@Override
	public String toString () {
		return familienname + ";" + vorname + ";" + geschlecht + ";" + katNr + ";" + klasse;
	}
}
