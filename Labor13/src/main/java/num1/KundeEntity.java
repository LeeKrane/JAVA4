package num1;

import javax.persistence.*;

@Entity
@Table(name = "kunde", schema = "public", catalog = "db_4chif_15_labor_13")
public class KundeEntity {
	private int kundeId;
	private String kundeZuname;
	private String kundeVorname;
	
	@Id
	@Column(name = "kunde_id", nullable = false)
	public int getKundeId () {
		return kundeId;
	}
	
	public void setKundeId (int kundeId) {
		this.kundeId = kundeId;
	}
	
	@Basic
	@Column(name = "kunde_zuname", nullable = true, length = 25)
	public String getKundeZuname () {
		return kundeZuname;
	}
	
	public void setKundeZuname (String kundeZuname) {
		this.kundeZuname = kundeZuname;
	}
	
	@Basic
	@Column(name = "kunde_vorname", nullable = true, length = 25)
	public String getKundeVorname () {
		return kundeVorname;
	}
	
	public void setKundeVorname (String kundeVorname) {
		this.kundeVorname = kundeVorname;
	}
	
	@Override
	public boolean equals (Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		
		KundeEntity that = (KundeEntity) o;
		
		if (kundeId != that.kundeId) return false;
		if (kundeZuname != null ? !kundeZuname.equals(that.kundeZuname) : that.kundeZuname != null) return false;
		if (kundeVorname != null ? !kundeVorname.equals(that.kundeVorname) : that.kundeVorname != null) return false;
		
		return true;
	}
	
	@Override
	public int hashCode () {
		int result = kundeId;
		result = 31 * result + (kundeZuname != null ? kundeZuname.hashCode() : 0);
		result = 31 * result + (kundeVorname != null ? kundeVorname.hashCode() : 0);
		return result;
	}
}
