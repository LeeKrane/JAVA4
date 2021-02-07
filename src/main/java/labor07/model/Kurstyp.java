package labor07.model;

import java.util.Objects;

public class Kurstyp {
	private final char id;
	private String bezeichnung;
	
	public Kurstyp (char id, String bezeichnung) {
		this.id = id;
		this.bezeichnung = bezeichnung;
	}
	
	public char getId () {
		return id;
	}
	
	public String getBezeichnung () {
		return bezeichnung;
	}
	
	public void setBezeichnung (String bezeichnung) {
		this.bezeichnung = bezeichnung;
	}
	
	@Override
	public boolean equals (Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Kurstyp kurstyp = (Kurstyp) o;
		return id == kurstyp.id;
	}
	
	@Override
	public int hashCode () {
		return Objects.hash(id);
	}
}
