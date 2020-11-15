package labor03.num3;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class ToolsTest {
	@BeforeAll
	static void serializedFileSetup () throws IOException {
		Tools.serialize(Tools.readCsv("src/main/resources/labor03/GPS-Log.csv"), "src/main/resources/labor03/SerializedTrackPoints.bin");
	}
	
	@Test
	void readCsv_legalFile_correctResult () throws IOException {
		int actual = Tools.readCsv("src/main/resources/labor03/GPS-Log.csv").size();
		assertEquals(498, actual);
	}
	
	@Test
	void serialize_legalTrackPointList_correctSerialization () throws IOException, ClassNotFoundException {
		List<TrackPoint> trackPoints = Tools.readCsv("src/main/resources/labor03/GPS-Log.csv");
		
		Set<TrackPoint> expected = new HashSet<>(trackPoints);
		Set<TrackPoint> actual = serializeTestReading();
		
		assertEquals(expected, actual);
	}
	
	private Set<TrackPoint> serializeTestReading () throws IOException, ClassNotFoundException {
		try (InputStream is = Tools.class.getResourceAsStream("/labor03/SerializedTrackPoints.bin");
			 ObjectInputStream ois = new ObjectInputStream(is)) {
			Set<TrackPoint> ret = new HashSet<>();
			
			while (is.available() > 0) {
				TrackPoint tp = (TrackPoint) ois.readObject();
				ret.add(tp);
			}
			
			return ret;
		}
	}
	
	@Test
	void maxElevation_legalFile_correctResult () throws IOException {
		assertEquals(311.5, Tools.maxElevation("/labor03/SerializedTrackPoints.bin"));
	}
}