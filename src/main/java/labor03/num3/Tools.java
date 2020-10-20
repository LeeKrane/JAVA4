package labor03.num3;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.List;
import java.util.OptionalDouble;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Tools {
	private static int illegalLinesCounter = 0;
	
	//TODO: JUnit5 Tests (This program has not been tested yet)
	
	public static List<TrackPoint> readCsv (String filename) throws IOException {
		try (Stream<String> lines = Files.lines(Paths.get("src", "main", "resources", "labor03", filename))) {
			List<TrackPoint> trackPoints = lines.filter(line -> {
						if (line.matches("(\\d{2}(:\\d{2}){2});(\\d+\\.\\d+);(\\d+\\.\\d+);(\\d+\\.\\d+)"))
							return true;
						System.err.println(line);
						incrementIllegalLinesCounter();
						return false;
					})
					.map(line -> {
						String[] split = line.split(";");
						return new TrackPoint(split[0], Double.parseDouble(split[1]), Double.parseDouble(split[2]), Double.parseDouble(split[3]));
					})
					.collect(Collectors.toList());
			System.err.println("Illegal Lines: " + illegalLinesCounter);
			illegalLinesCounter = 0;
			return trackPoints;
		}
	}
	
	private static void incrementIllegalLinesCounter () {
		illegalLinesCounter++;
	}
	
	public static void serialize (List<TrackPoint> tp, String filename) throws IOException {
		try (ObjectOutputStream os = new ObjectOutputStream(new FileOutputStream(filename))) {
			for (TrackPoint t : tp) {
				os.writeObject(t);
			}
		}
	}
	
	public static double maxElevation (String filename) throws IOException {
		Set<TrackPoint> trackPoints = new HashSet<>();
		try (ObjectInputStream is = new ObjectInputStream(Tools.class.getResourceAsStream(filename))) {
			while (is.available() > 0) {
				TrackPoint tp = (TrackPoint) is.readObject();
				trackPoints.add(tp);
			}
		} catch (ClassNotFoundException e) {
			System.err.println("Error: " + e.getMessage());
		}
		
		OptionalDouble maxElevation = trackPoints.stream()
				.mapToDouble(tp -> tp.getAltitude())
				.max();
		if (maxElevation.isPresent())
			return maxElevation.getAsDouble();
		throw new IllegalArgumentException("Illegal file!");
	}
	
	//TODO: TrackPointApp (Punkt d)
}
