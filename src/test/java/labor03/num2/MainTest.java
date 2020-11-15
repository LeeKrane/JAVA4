package labor03.num2;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Comparator;
import java.util.List;
import java.util.TreeMap;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

import static labor03.num2.Main.*;

class MainTest {
	@Test
	void a_correctResult () {
		assertEquals(3, a_femaleStudents());
	}
	
	@Test
	void b_correctResult () {
		assertEquals("Sarah,Angela,Carina", b_firstnames());
	}
	
	@Test
	void c_correctResult () {
		var expected = schuelerListFromFile.stream()
				.filter(Schueler::isWeiblich)
				.sorted((a, b) -> Comparator.comparing(Schueler::getKlasse).thenComparing(Schueler::getNr).compare(a, b))
				.collect(Collectors.toList());
		
		assertEquals(expected, c_csvStrings());
	}
	
	@Test
	void d_correctResult () {
		var expected = List.of(
				SchuelerUtils.fromCSV("Kraushofer,Lukas,M,5,4CHIF"),
				SchuelerUtils.fromCSV("Schindlegger,Lukas,M,19,4BHIF")
		);
		assertEquals(expected, d_abtSprecher());
	}
	
	@Test
	void e_correctResult () {
		assertEquals(40, e_longestName());
	}
	
	@Test
	void f_correctResult () throws IOException {
		assertEquals("Schmatz,Julia Andrea,W,12,3BHIF", f_juliaFinder());
	}
	
	@Test
	void g_correctResult () throws IOException {
		assertEquals("1AHIF, 1BHIF, 1CHIF, 2AHIF, 2BHIF, 2CHIF, 3AHIF, 3BHIF, 4BHIF, 4CHIF, 5AHIF, 5BHIF", g_getClasses());
	}
	
	@Test
	void h_correctResult () {
		var expected = new TreeMap<>(schuelerListFromFile.stream()
											 .collect(Collectors.groupingBy(Schueler::getVorname, Collectors.counting())));
		assertEquals(expected, h_getFirstnameMap());
	}
}