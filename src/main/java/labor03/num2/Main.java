package labor03.num2;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.List;
import java.util.TreeMap;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {
	private static final String filename = "schueler12.csv";
	static List<Schueler> schuelerSetFromFile;
	private static final List<Schueler> testKlasse = SchuelerUtils.getTestKlasse();
	
	static {
		try (Stream<String> reader = Files.lines(Paths.get("src", "main", "resources", "labor03", filename))) {
			schuelerSetFromFile = reader.map(SchuelerUtils::fromCSV)
					.collect(Collectors.toList());
		} catch (IOException e) {
			System.err.println("Error: " + e.getMessage());
		}
	}
	
	public static void main (String[] args) {
		try {
			
			a_femaleStudents();
			b_firstnames();
			c_csvStrings();
			d_abtSprecher();
			e_longestName();
			f_juliaFinder();
			g_getClasses();
			h_getFirstnameMap();
		} catch (IOException e) {
			System.err.println("Error: " + e.getMessage());
		}
	}
	
	static TreeMap<String, Long> h_getFirstnameMap () {
		System.out.println("Aufgabe 8/h");
		TreeMap<String, Long> ret = new TreeMap<>(schuelerSetFromFile.stream()
				.collect(Collectors.groupingBy(Schueler::getVorname, Collectors.counting())));
		ret.forEach((firstname, count) -> System.out.println(firstname + ": " + count));
		System.out.println();
		return ret;
	}
	
	static String g_getClasses () throws IOException {
		System.out.println("Aufgabe 7/g");
		try (Stream<String> lines = Files.lines(Paths.get("src", "main", "resources", "labor03", filename))) {
			String ret = lines.map(line -> line.split(",")[4])
					.distinct()
					.sorted()
					.reduce((a, b) -> a + ", " + b)
					.orElse("Error");
			System.out.println(ret + "\n");
			return ret;
		}
	}
	
	static String f_juliaFinder () throws IOException {
		System.out.println("Aufgabe 6/f");
		try (Stream<String> lines = Files.lines(Paths.get("src", "main", "resources", "labor03", filename))) {
			String ret = lines.filter(line -> line.split(",")[1].contains("Julia"))
					.findFirst()
					.orElse("");
			System.out.println(SchuelerUtils.fromCSV(ret) + "\n");
			return ret;
		}
	}
	
	static int e_longestName () {
		System.out.println("Aufgabe 5/e");
		int ret = schuelerSetFromFile.stream()
				.mapToInt(s -> s.getVorname().length() + s.getNachname().length())
				.max()
				.orElse(0);
		System.out.println(ret + "\n");
		return ret;
	}
	
	static List<Schueler> d_abtSprecher () {
		System.out.println("Aufgabe 4/d");
		List<Schueler> ret = schuelerSetFromFile.stream()
				.filter(s -> s.getKlasse().charAt(0) == '4'
						&& s.getGeschlecht() == 'M'
						&& s.getVorname().equals("Lukas"))
				.peek(System.out::println)
				.collect(Collectors.toList());
		System.out.println();
		return ret;
	}
	
	static List<Schueler> c_csvStrings () {
		System.out.println("Aufgabe 3/c");
		List<Schueler> ret = schuelerSetFromFile.stream()
				.filter(Schueler::isWeiblich)
				.sorted((a, b) -> Comparator.comparing(Schueler::getKlasse)
						.thenComparing(Schueler::getNr)
						.compare(a, b))
				.peek(System.out::println)
				.collect(Collectors.toList());
		System.out.println();
		return ret;
	}
	
	static String b_firstnames () {
		System.out.println("Aufgabe 2/b");
		String ret = testKlasse.stream()
				.filter(Schueler::isWeiblich)
				.map(Schueler::getVorname)
				.reduce((a, b) -> a + "," + b)
				.orElse("");
		System.out.println(ret + "\n");
		return ret;
	}
	
	static long a_femaleStudents () {
		System.out.println("Aufgabe 1/a");
		long count = testKlasse.stream()
				.filter(Schueler::isWeiblich)
				.count();
		System.out.println(count + "\n");
		return count;
	}
}