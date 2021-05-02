package num1;

import javax.persistence.*;

@Entity
@Table(name = "dozent", schema = "public", catalog = "db_4chif_15_labor_13")
public class DozentEntity {
	private int dozId;
	private String dozZuname;
	private String dozVorname;
	
	@Id
	@Column(name = "doz_id", nullable = false)
	public int getDozId () {
		return dozId;
	}
	
	public void setDozId (int dozId) {
		this.dozId = dozId;
	}
	
	@Basic
	@Column(name = "doz_zuname", nullable = true, length = 25)
	public String getDozZuname () {
		return dozZuname;
	}
	
	public void setDozZuname (String dozZuname) {
		this.dozZuname = dozZuname;
	}
	
	@Basic
	@Column(name = "doz_vorname", nullable = true, length = 25)
	public String getDozVorname () {
		return dozVorname;
	}
	
	public void setDozVorname (String dozVorname) {
		this.dozVorname = dozVorname;
	}
	
	@Override
	public boolean equals (Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		
		DozentEntity that = (DozentEntity) o;
		
		if (dozId != that.dozId) return false;
		if (dozZuname != null ? !dozZuname.equals(that.dozZuname) : that.dozZuname != null) return false;
		if (dozVorname != null ? !dozVorname.equals(that.dozVorname) : that.dozVorname != null) return false;
		
		return true;
	}
	
	@Override
	public int hashCode () {
		int result = dozId;
		result = 31 * result + (dozZuname != null ? dozZuname.hashCode() : 0);
		result = 31 * result + (dozVorname != null ? dozVorname.hashCode() : 0);
		return result;
	}
}
