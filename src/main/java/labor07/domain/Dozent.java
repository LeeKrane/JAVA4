package labor07.domain;

public class Dozent {
	private final int id;
	private String zuname;
	private String vorname;
	
	public Dozent (String zuname, String vorname) {
		this(0, zuname, vorname); // TODO: Check how to get correct primary key
	}
	
	public Dozent (int id, String zuname, String vorname) {
		this.id = id;
		this.zuname = zuname;
		this.vorname = vorname;
	}
	
	public int getId () {
		return id;
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
}
