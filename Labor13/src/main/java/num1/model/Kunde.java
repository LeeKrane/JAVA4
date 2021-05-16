package num1.model;

import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@NoArgsConstructor
@Entity
public class Kunde implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "kunde_id", nullable = false)
	private Integer kundeId;
	
	@Basic
	@Column(name = "kunde_zuname", length = 25)
	private String kundeZuname;
	
	@Basic
	@Column(name = "kunde_vorname", length = 25)
	private String kundeVorname;
	
	@ManyToMany(mappedBy = "kunden")
	private List<Kurs> kurse = new ArrayList<>();
	
	public Kunde (String kundeZuname, String kundeVorname) {
		this.kundeZuname = kundeZuname;
		this.kundeVorname = kundeVorname;
	}
	
	public Integer getKundeId () {
		return kundeId;
	}
	
	public void setKundeId (Integer kundeId) {
		this.kundeId = kundeId;
	}
	
	public String getKundeZuname () {
		return kundeZuname;
	}
	
	public void setKundeZuname (String kundeZuname) {
		this.kundeZuname = kundeZuname;
	}
	
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
		Kunde kunde = (Kunde) o;
		return Objects.equals(kundeId, kunde.kundeId) && Objects.equals(kundeZuname, kunde.kundeZuname) && Objects.equals(kundeVorname, kunde.kundeVorname);
	}
	
	@Override
	public int hashCode () {
		return Objects.hash(kundeId, kundeZuname, kundeVorname);
	}
	
	public List<Kurs> getKurse () {
		return kurse;
	}
	
	public void setKurse (List<Kurs> kurse) {
		this.kurse = kurse;
	}
	
	@Override
	public String toString () {
		return "Kunde{" +
				"kundeId=" + kundeId +
				", kundeZuname='" + kundeZuname + '\'' +
				", kundeVorname='" + kundeVorname + '\'' +
				", kurse=" + kurse +
				'}';
	}
	
	public void addKurs (Kurs kurs) {
		kurse.add(kurs);
		kurs.addKunde(this);
	}
	
	public void removeKurs (Kurs kurs) {
		kurse.remove(kurs);
		kurs.removeKunde(this);
	}
	
	/**
	 * This Method is for testing
	 * @param kurse - All kurse that will be added
	 */
	public void addKurse (Kurs... kurse) {
		for (Kurs kurs : kurse)
			addKurs(kurs);
	}
}
