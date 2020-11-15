package labor03.num3;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class TrackPoint implements Serializable {
	private LocalTime time;
	private double geoLongitude; // geografische Länge
	private double geoLatitude; // geografische Breite
	private double altitude; // Seehöhe
	private static DateTimeFormatter dtf = DateTimeFormatter.ofPattern("hh:mm:ss");
	
	public TrackPoint (String time, double geoLongitude, double geoLatitude, double altitude) {
		if (!time.matches("^\\d{2}:\\d{2}:\\d{2}$") || geoLongitude < 0.0 || geoLatitude < 0.0 || altitude < 0.0)
			throw new IllegalArgumentException();
		this.time = getTime(time);
		this.geoLongitude = geoLongitude;
		this.geoLatitude = geoLatitude;
		this.altitude = altitude;
	}
	
	static LocalTime getTime (String time) {
		String[] split = time.split(":");
		if (split.length != 3)
			throw new IllegalArgumentException();
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
	
	public double getGeoLongitude () {
		return geoLongitude;
	}
	
	public double getGeoLatitude () {
		return geoLatitude;
	}
	
	public double getAltitude () {
		return altitude;
	}
	
	@Override
	public boolean equals (Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		TrackPoint that = (TrackPoint) o;
		return Double.compare(that.geoLongitude, geoLongitude) == 0 &&
				Double.compare(that.geoLatitude, geoLatitude) == 0 &&
				Double.compare(that.altitude, altitude) == 0 &&
				Objects.equals(time, that.time);
	}
	
	@Override
	public int hashCode () {
		return Objects.hash(time, geoLongitude, geoLatitude, altitude);
	}
}
