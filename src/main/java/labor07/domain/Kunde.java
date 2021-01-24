package labor07.domain;

import java.util.Objects;

public class Kunde {
	private int id;
	private String zuname;
	private String vorname;
	
	public Kunde (String zuname, String vorname) {
		this(0, zuname, vorname);
	}
	
	public Kunde (int id, String zuname, String vorname) {
		this.id = id;
		this.zuname = zuname;
		this.vorname = vorname;
	}
	
	public int getId () {
		return id;
	}
	
	public void setId (int id) {
		if (this.id == 0)
			this.id = id;
	}
	
	public String getZuname () {
		return zuname;
	}
	
	public void setZuname (String zuname) {
		this.zuname = zuname;
	}
	
	public String getVorname () {
		return vorname;
	}
	
	public void setVorname (String vorname) {
		this.vorname = vorname;
	}
	
	@Override
	public boolean equals (Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Kunde kunde = (Kunde) o;
		return id == kunde.id;
	}
	
	@Override
	public int hashCode () {
		return Objects.hash(id);
	}
}
