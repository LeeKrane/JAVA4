package labor06;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class SchuelerTools {
	public List<Schueler> readFromCSV (String filename) throws IOException {
		try (Stream<String> lines = Files.lines(Paths.get("src", "main", "resources", "labor06", filename))) {
			return lines.filter(s -> s.matches("^[\\wöß ÜüÖšćáéèóÐäđŽşç-]+;[\\wöß ÜüÖšćáéèóÐäđŽşç-]+;\\w;\\d+;\\d\\w{4}$"))
					.map(s -> {
						String[] split = s.split(";");
						return new Schueler(split[0], split[1], split[2].charAt(0), Integer.parseInt(split[3]), split[4]);
					})
					.collect(Collectors.toList());
		}
	}
}
