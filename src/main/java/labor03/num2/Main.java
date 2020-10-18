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
	private static List<Schueler> schuelerSetFromFile;
	private static List<Schueler> testKlasse = SchuelerUtils.getTestKlasse();
	private static String filename = "schueler12.csv";
	
	//TODO: Main -> JUnit5 Tests
	
	/*
		try (Stream<String> lines = Files.lines(Paths.get("src", "main", "resources", "labor03", filename))) {
		
		}
	 */
	
	public static void main (String[] args) {
		try (Stream<String> reader = Files.lines(Paths.get("src", "main", "resources", "labor03", filename))) {
			schuelerSetFromFile = reader.map(SchuelerUtils::fromCSV)
					.collect(Collectors.toList());
			a();
			b();
			c();
			d();
			e();
			f();
			g();
			h();
		} catch (IOException e) {
			System.err.println("Error: " + e.getMessage());
		}
	}
	
	private static void h () {
		System.out.println("Aufgabe 8/h");
		new TreeMap<>(schuelerSetFromFile.stream()
				.collect(Collectors.groupingBy(Schueler::getVorname, Collectors.counting())))
				.forEach((firstname, count) -> System.out.println(firstname + ": " + count));
		System.out.println();
	}
	
	private static void g () throws IOException {
		System.out.println("Aufgabe 7/g");
		try (Stream<String> lines = Files.lines(Paths.get("src", "main", "resources", "labor03", filename))) {
			lines.map(line -> line.split(",")[4])
					.distinct()
					.sorted()
					.reduce((a, b) -> a + ", " + b)
					.ifPresentOrElse(System.out::println, () -> System.out.println("Error"));
			System.out.println();
		}
	}
	
	private static void f () throws IOException {
		System.out.println("Aufgabe 6/f");
		try (Stream<String> lines = Files.lines(Paths.get("src", "main", "resources", "labor03", filename))) {
			lines.filter(line -> line.split(",")[1].contains("Julia"))
					.findFirst()
					.ifPresentOrElse(System.out::println, () -> System.out.println("Keine Julia"));
			System.out.println();
		}
	}
	
	private static void e () {
		System.out.println("Aufgabe 5/e");
		schuelerSetFromFile.stream()
				.mapToInt(s -> s.getVorname().length() + s.getNachname().length())
				.max()
				.ifPresent(System.out::println);
		System.out.println();
	}
	
	private static void d () {
		System.out.println("Aufgabe 4/d");
		schuelerSetFromFile.stream()
				.filter(s -> s.getKlasse().charAt(0) == '4'
						&& s.getGeschlecht() == 'M'
						&& s.getVorname().equals("Lukas"))
				.forEach(System.out::println);
		System.out.println();
	}
	
	private static void c () {
		System.out.println("Aufgabe 3/c");
		schuelerSetFromFile.stream()
				.filter(Schueler::isWeiblich)
				.sorted((a, b) -> Comparator.comparing(Schueler::getKlasse).thenComparing(Schueler::getNr).compare(a, b))
				.forEach(System.out::println);
		System.out.println();
	}
	
	private static void b () {
		System.out.println("Aufgabe 2/b");
		System.out.println(testKlasse.stream()
						   .map(Schueler::getVorname)
						   .reduce((a, b) -> a + "," + b));
		System.out.println();
	}
	
	private static void a () {
		System.out.println("Aufgabe 1/a");
		System.out.println(testKlasse.stream()
				.filter(Schueler::isWeiblich)
				.count());
		System.out.println();
	}
}

class SchuelerWithCsv {
	private final String csv;
	private final Schueler schueler;
	
	public SchuelerWithCsv (String csv, Schueler schueler) {
		this.csv = csv;
		this.schueler = schueler;
	}
	
	public Schueler getSchueler () {
		return schueler;
	}
}