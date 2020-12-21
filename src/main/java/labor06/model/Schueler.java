package labor06.model;

public class Schueler {
	private String familienname;
	private String vorname;
	private char geschlecht;
	private int katNr;
	private String klasse;
	
	public Schueler (String familienname, String vorname, char geschlecht, int katNr, String klasse) {
		this.familienname = familienname;
		this.vorname = vorname;
		this.geschlecht = geschlecht;
		this.katNr = katNr;
		this.klasse = klasse;
	}
	
	public String getFamilienname () {
		return familienname;
	}
	
	public String getVorname () {
		return vorname;
	}
	
	public char getGeschlecht () {
		return geschlecht;
	}
	
	public int getKatNr () {
		return katNr;
	}
	
	public String getKlasse () {
		return klasse;
	}
	
	@Override
	public String toString () {
		return familienname + ";" + vorname + ";" + geschlecht + ";" + katNr + ";" + klasse;
	}
}
