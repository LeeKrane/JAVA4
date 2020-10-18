package labor03.num3;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class TrackPoint implements Serializable {
	private LocalTime time;
	private double geoLongitude; // geografische Länge
	private double geoLatitude; // geografische Breite
	private double altitude; // Seehöhe
	private static DateTimeFormatter dtf = DateTimeFormatter.ofPattern("hh:mm:tt");
	
	public TrackPoint (String time, double geoLongitude, double geoLatitude, double altitude) {
		this.time = getTime(time);
		this.geoLongitude = geoLongitude;
		this.geoLatitude = geoLatitude;
		this.altitude = altitude;
	}
	
	private LocalTime getTime (String time) {
		String[] split = time.split(":");
		return LocalTime.of(Integer.parseInt(split[0]), Integer.parseInt(split[1]), Integer.parseInt(split[2]));
	}
	
	private void writeObject (ObjectOutputStream os) throws IOException {
		os.writeUTF(time.format(dtf));
		os.writeDouble(geoLongitude);
		os.writeDouble(geoLatitude);
		os.writeDouble(altitude);
	}
	
	private void readObject (ObjectInputStream is) throws IOException {
		time = getTime(is.readUTF());
		geoLongitude = is.readDouble();
		geoLatitude = is.readDouble();
		altitude = is.readDouble();
	}
	
	public double getAltitude () {
		return altitude;
	}
}
