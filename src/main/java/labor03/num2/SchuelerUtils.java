package labor03.num2;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author reio
 */
public class SchuelerUtils {
    /**
     * Verwandelt den CSV-String csv in ein Schülerobjekt
     *
     * @param csv
     * @return
     */
    public static Schueler fromCSV (String csv) {
        String[] token = csv.split(",");
        String nachname = token[0];
        String vorname = token[1];
        char geschlecht = token[2].charAt(0);
        int kat = Integer.parseInt(token[3]);
        String klasse = token[4];
        return new Schueler(klasse, kat, nachname, vorname, geschlecht);
    }
    
    private static final List<Schueler> testklasse;
    
    static {
        testklasse = new ArrayList<>();
        
        testklasse.add(new Schueler("5BHIF", 1, "Ehn", "Wilhelm", 'M'));
        testklasse.add(new Schueler("5BHIF", 2, "Gruber", "Sarah", 'W'));
        testklasse.add(new Schueler("5BHIF", 3, "Guthan", "Raphael", 'M'));
        testklasse.add(new Schueler("5BHIF", 4, "Hamberger", "Sebastian", 'M'));
        testklasse.add(new Schueler("5BHIF", 5, "Harold", "Sascha", 'M'));
        testklasse.add(new Schueler("5BHIF", 6, "Kornberger", "Jürgen", 'M'));
        testklasse.add(new Schueler("5BHIF", 7, "Navratil", "Philipp", 'M'));
        testklasse.add(new Schueler("5BHIF", 8, "Pfeiffer", "Michael", 'M'));
        testklasse.add(new Schueler("5BHIF", 9, "Purker", "Angela", 'W'));
        testklasse.add(new Schueler("5BHIF", 10, "Rasch", "Patrick", 'M'));
        testklasse.add(new Schueler("5BHIF", 11, "Ringelhahn", "Carina", 'W'));
        testklasse.add(new Schueler("5BHIF", 12, "Sattler", "Benedikt", 'M'));
        testklasse.add(new Schueler("5BHIF", 13, "Schirmer", "Kurt", 'M'));
        testklasse.add(new Schueler("5BHIF", 14, "Schneider", "Florens", 'M'));
        testklasse.add(new Schueler("5BHIF", 15, "Simmer", "Patrick", 'M'));
        testklasse.add(new Schueler("5BHIF", 16, "Staudinger", "Patrik", 'M'));
        testklasse.add(new Schueler("5BHIF", 17, "Tatzreiter", "Oliver", 'M'));
        testklasse.add(new Schueler("5BHIF", 18, "Tröscher", "Dominik", 'M'));
    }
    
    /**
     * Liefert eine Liste von Schülern
     *
     * @return
     */
    public static List<Schueler> getTestKlasse () {
        return testklasse;
    }
}
