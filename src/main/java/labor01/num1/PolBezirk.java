package labor01.num1;

import java.util.Objects;

public class PolBezirk implements Comparable<PolBezirk> {
	private int kennzeichen;
	private String bezirksname;
	private int einwohnerzahl;
	
	public PolBezirk (int kennzeichen, String bezirksname, int einwohnerzahl) {
		this.kennzeichen = kennzeichen;
		this.bezirksname = bezirksname;
		this.einwohnerzahl = einwohnerzahl;
	}
	
	@Override
	public int compareTo (PolBezirk o) {
		return bezirksname.compareTo(o.bezirksname);
	}
	
	@Override
	public boolean equals (Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		PolBezirk polBezirk = (PolBezirk) o;
		return kennzeichen == polBezirk.kennzeichen &&
				einwohnerzahl == polBezirk.einwohnerzahl &&
				Objects.equals(bezirksname, polBezirk.bezirksname);
	}
	
	@Override
	public int hashCode () {
		return Objects.hash(kennzeichen, bezirksname, einwohnerzahl);
	}
	
	@Override
	public String toString () {
		return kennzeichen +
				";" + bezirksname +
				";" + einwohnerzahl;
	}
	
	public int getKennzeichen () {
		return kennzeichen;
	}
	
	public String getBezirksname () {
		return bezirksname;
	}
	
	public int getEinwohnerzahl () {
		return einwohnerzahl;
	}
}
