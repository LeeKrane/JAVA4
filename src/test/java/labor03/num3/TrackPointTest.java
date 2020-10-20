package labor03.num3;

import org.junit.jupiter.api.Test;

import java.io.*;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;
import static labor03.num3.TrackPoint.*;

class TrackPointTest {
	@Test
	void constructor_legalParameters_correctResult () {
		assertDoesNotThrow(() -> new TrackPoint("10:20:30", 15.3, 14.8, 100.57));
	}
	
	@Test
	void constructor_illegalTime_throwsIAE () {
		assertThrows(IllegalArgumentException.class, () -> new TrackPoint("42", 15.3, 14.8, 100.57));
	}
	
	@Test
	void constructor_illegalGeoLon_throwsIAE () {
		assertThrows(IllegalArgumentException.class, () -> new TrackPoint("10:20:30", -42, 14.8, 100.57));
	}
	
	@Test
	void constructor_illegalGeoLat_throwsIAE () {
		assertThrows(IllegalArgumentException.class, () -> new TrackPoint("10:20:30", 15.3, -42, 100.57));
	}
	
	@Test
	void constructor_illegalAlt_throwsIAE () {
		assertThrows(IllegalArgumentException.class, () -> new TrackPoint("10:20:30", 15.3, 14.8, -42));
	}
	
	@Test
	void read_write_correctWritingAndReconstructionOfObject () throws IOException, ClassNotFoundException {
		TrackPoint tp = new TrackPoint("10:20:30", 15.3, 14.8, 100.57);
		try (ByteArrayOutputStream bos = new ByteArrayOutputStream();
			 ObjectOutputStream os = new ObjectOutputStream(bos)) {
			os.writeObject(tp);
			try (ByteArrayInputStream bis = new ByteArrayInputStream(bos.toByteArray());
				 ObjectInputStream is = new ObjectInputStream(bis)) {
				assertEquals(tp, is.readObject());
			}
		}
	}
	
	@Test
	void getTime_illegalParameters_throwsIAE () {
		assertThrows(IllegalArgumentException.class, () -> getTime("42"));
	}
	
	@Test
	void getTime_legalParameters_correctResult () {
		assertEquals(LocalTime.of(10, 20, 30), getTime("10:20:30"));
	}
}