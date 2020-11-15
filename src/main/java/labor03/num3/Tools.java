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
	
	public static List<TrackPoint> readCsv (String filename) throws IOException {
		try (Stream<String> lines = Files.lines(Paths.get(filename))) {
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
			resetIllegalLinesCounter();
			return trackPoints;
		}
	}
	
	private static void resetIllegalLinesCounter () {
		illegalLinesCounter = 0;
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
		try (InputStream is = Tools.class.getResourceAsStream(filename);
			 ObjectInputStream ois = new ObjectInputStream(is)) {
			while (is.available() > 0) {
				TrackPoint tp = (TrackPoint) ois.readObject();
				trackPoints.add(tp);
			}
		} catch (ClassNotFoundException e) {
			System.err.println("Error: " + e.getMessage());
		}
		
		OptionalDouble maxElevation = trackPoints.stream()
				.mapToDouble(TrackPoint::getAltitude)
				.max();
		if (maxElevation.isPresent())
			return maxElevation.getAsDouble();
		throw new IllegalArgumentException("Illegal file!");
	}
	
	//TODO: TrackPointApp (Punkt d)
}
