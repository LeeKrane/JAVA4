package num1;

import javax.persistence.*;

@Entity
@Table(name = "kurs_kunde", schema = "public", catalog = "db_4chif_15_labor_13")
@IdClass(KursKundeEntityPK.class)
public class KursKundeEntity {
	private int kundeId;
	private int kursId;
	
	@Id
	@Column(name = "kunde_id", nullable = false)
	public int getKundeId () {
		return kundeId;
	}
	
	public void setKundeId (int kundeId) {
		this.kundeId = kundeId;
	}
	
	@Id
	@Column(name = "kurs_id", nullable = false)
	public int getKursId () {
		return kursId;
	}
	
	public void setKursId (int kursId) {
		this.kursId = kursId;
	}
	
	@Override
	public boolean equals (Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		
		KursKundeEntity that = (KursKundeEntity) o;
		
		if (kundeId != that.kundeId) return false;
		if (kursId != that.kursId) return false;
		
		return true;
	}
	
	@Override
	public int hashCode () {
		int result = kundeId;
		result = 31 * result + kursId;
		return result;
	}
}
