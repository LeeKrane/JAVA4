package labor01.num1;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.Comparator;
import java.util.TreeSet;
import java.util.stream.Collectors;

public class CollectionAPI {
	public static void main (String[] args) {
		Collection<PolBezirk> polBezirke;
		try {
			polBezirke = readCSV("/labor01/bezirke_noe.csv");
			collectionOutput(polBezirke);
			
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
		System.out.println();
	}
	
	public static Collection<PolBezirk> readCSV (String filename) throws IOException {
		try (BufferedReader reader = new BufferedReader(new InputStreamReader(CollectionAPI.class.getResourceAsStream(filename)))) {
			return reader.lines()
					.map(line -> {
						String[] split = line.split(";");
						return new PolBezirk(Integer.parseInt(split[0]), split[1], Integer.parseInt(split[2].replace(".", "")));
					})
					.collect(Collectors.toCollection(TreeSet::new));
		}
	}
}
