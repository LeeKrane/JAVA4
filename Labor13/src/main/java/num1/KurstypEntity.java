package num1;

import javax.persistence.*;

@Entity
@Table(name = "kurstyp", schema = "public", catalog = "db_4chif_15_labor_13")
public class KurstypEntity {
	private String typId;
	private String typBezeichnung;
	
	@Id
	@Column(name = "typ_id", nullable = false, length = -1)
	public String getTypId () {
		return typId;
	}
	
	public void setTypId (String typId) {
		this.typId = typId;
	}
	
	@Basic
	@Column(name = "typ_bezeichnung", nullable = true, length = 100)
	public String getTypBezeichnung () {
		return typBezeichnung;
	}
	
	public void setTypBezeichnung (String typBezeichnung) {
		this.typBezeichnung = typBezeichnung;
	}
	
	@Override
	public boolean equals (Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		
		KurstypEntity that = (KurstypEntity) o;
		
		if (typId != null ? !typId.equals(that.typId) : that.typId != null) return false;
		if (typBezeichnung != null ? !typBezeichnung.equals(that.typBezeichnung) : that.typBezeichnung != null)
			return false;
		
		return true;
	}
	
	@Override
	public int hashCode () {
		int result = typId != null ? typId.hashCode() : 0;
		result = 31 * result + (typBezeichnung != null ? typBezeichnung.hashCode() : 0);
		return result;
	}
}
