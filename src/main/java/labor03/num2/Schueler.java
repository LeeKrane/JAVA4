package labor03.num2;

/**
 *
 * @author reio
 */
public class Schueler {
  private final String klasse;
  private final int nr;
  private final String nachname;
  private final String vorname;
  private final char geschlecht;
  
  public Schueler (String klasse, int nr, String nachname, String vorname, char geschlecht) {
    this.klasse = klasse;
    this.nr = nr;
    this.nachname = nachname;
    this.vorname = vorname;
    this.geschlecht = geschlecht;
  }
  
  public String getNachname () {
    return nachname;
  }
  
  public int getNr () {
    return nr;
  }
  
  public String getVorname () {
    return vorname;
  }
  
  public char getGeschlecht () {
    return geschlecht;
  }
  
  public String getKlasse () {
    return klasse;
  }
  
  
  public boolean isWeiblich () {
    return geschlecht == 'W';
  }
  
  @Override
  public String toString () {
    return String.format("%s/%02d %s %s %c", klasse, nr, nachname, vorname, geschlecht);
  }
}