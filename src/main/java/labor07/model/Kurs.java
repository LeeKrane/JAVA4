package labor07.model;

import java.sql.Date;
import java.util.Objects;

public class Kurs {
	private final Integer id;
	private char typ;
	private int dozId;
	private String bezeichnung;
	private Date beginn;
	
	public Kurs (char typ, int dozId, String bezeichnung, Date beginn) {
		this(null, typ, dozId, bezeichnung, beginn);
	}
	
	public Kurs (Integer id, char typ, int dozId, String bezeichnung, Date beginn) {
		this.id = id;
		this.typ = typ;
		this.dozId = dozId;
		this.bezeichnung = bezeichnung;
		this.beginn = beginn;
	}
	
	public Integer getId () {
		return id;
	}
	
	public char getTyp () {
		return typ;
	}
	
	public void setTyp (char typ) {
		this.typ = typ;
	}
	
	public int getDozId () {
		return dozId;
	}
	
	public void setDozId (int dozId) {
		this.dozId = dozId;
	}
	
	public String getBezeichnung () {
		return bezeichnung;
	}
	
	public void setBezeichnung (String bezeichnung) {
		this.bezeichnung = bezeichnung;
	}
	
	public Date getBeginn () {
		return beginn;
	}
	
	public void setBeginn (Date beginn) {
		this.beginn = beginn;
	}
	
	@Override
	public boolean equals (Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Kurs kurs = (Kurs) o;
		return id.equals(kurs.id);
	}
	
	@Override
	public int hashCode () {
		return Objects.hash(id);
	}
}
