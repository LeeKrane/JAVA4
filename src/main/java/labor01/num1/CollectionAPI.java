package labor01.num1;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Collection;
import java.util.Comparator;
import java.util.TreeSet;

public class CollectionAPI {
	
	// TODO: get resources from class ( "ClassName".class.getResourceAsStream(...) + InputStreamReader )
	
	public static void main (String[] args) {
		Collection<PolBezirk> polBezirke;
		try {
			polBezirke = readCSV("src/main/resources/labor01/bezirke_noe.csv");
			collectionOutput(polBezirke);
			
			System.out.println();
			
			Collection<PolBezirk> sorted = new TreeSet<>(Comparator.comparingInt(PolBezirk::getEinwohnerzahl).reversed());
			sorted.addAll(polBezirke);
			collectionOutput(sorted);
		} catch (IOException e) {
			System.err.println(e);
		}
	}
	
	private static void collectionOutput (Collection<PolBezirk> c) {
		for (PolBezirk p : c) {
			System.out.println(p);
		}
	}
	
	public static Collection<PolBezirk> readCSV (String filename) throws IOException {
		try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
			Collection<PolBezirk> polBezirke = new TreeSet<>();
			
			reader.lines().forEach(
				line -> {
					String split[] = line.split(";");
					polBezirke.add(new PolBezirk(Integer.parseInt(split[0]), split[1], Integer.parseInt(split[2].replace(".", ""))));
				});
			return polBezirke;
		}
	}
}
