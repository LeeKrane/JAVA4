package num1;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Table(name = "kurs", schema = "public", catalog = "db_4chif_15_labor_13")
public class KursEntity {
	private int kursId;
	private String kursBezeichnung;
	private Date kursBeginndatum;
	
	@Id
	@Column(name = "kurs_id", nullable = false)
	public int getKursId () {
		return kursId;
	}
	
	public void setKursId (int kursId) {
		this.kursId = kursId;
	}
	
	@Basic
	@Column(name = "kurs_bezeichnung", nullable = true, length = 100)
	public String getKursBezeichnung () {
		return kursBezeichnung;
	}
	
	public void setKursBezeichnung (String kursBezeichnung) {
		this.kursBezeichnung = kursBezeichnung;
	}
	
	@Basic
	@Column(name = "kurs_beginndatum", nullable = true)
	public Date getKursBeginndatum () {
		return kursBeginndatum;
	}
	
	public void setKursBeginndatum (Date kursBeginndatum) {
		this.kursBeginndatum = kursBeginndatum;
	}
	
	@Override
	public boolean equals (Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		
		KursEntity that = (KursEntity) o;
		
		if (kursId != that.kursId) return false;
		if (kursBezeichnung != null ? !kursBezeichnung.equals(that.kursBezeichnung) : that.kursBezeichnung != null)
			return false;
		if (kursBeginndatum != null ? !kursBeginndatum.equals(that.kursBeginndatum) : that.kursBeginndatum != null)
			return false;
		
		return true;
	}
	
	@Override
	public int hashCode () {
		int result = kursId;
		result = 31 * result + (kursBezeichnung != null ? kursBezeichnung.hashCode() : 0);
		result = 31 * result + (kursBeginndatum != null ? kursBeginndatum.hashCode() : 0);
		return result;
	}
}
