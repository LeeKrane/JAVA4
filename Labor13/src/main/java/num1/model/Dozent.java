package num1.model;

import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

@NoArgsConstructor
@Entity
public class Dozent implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "doz_id", nullable = false)
	private Integer dozId;
	
	@Basic
	@Column(name = "doz_zuname", length = 25)
	private String dozZuname;
	
	@Basic
	@Column(name = "doz_vorname", length = 25)
	private String dozVorname;
	
	@OneToMany(mappedBy = "dozent")
	private List<Kurs> kurse = new ArrayList<>();
	
	public Dozent (String dozZuname, String dozVorname) {
		this.dozZuname = dozZuname;
		this.dozVorname = dozVorname;
	}
	
	public Integer getDozId () {
		return dozId;
	}
	
	public void setDozId (Integer dozId) {
		this.dozId = dozId;
	}
	
	public String getDozZuname () {
		return dozZuname;
	}
	
	public void setDozZuname (String dozZuname) {
		this.dozZuname = dozZuname;
	}
	
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
		Dozent dozent = (Dozent) o;
		return Objects.equals(dozId, dozent.dozId) && Objects.equals(dozZuname, dozent.dozZuname) && Objects.equals(dozVorname, dozent.dozVorname);
	}
	
	@Override
	public int hashCode () {
		return Objects.hash(dozId, dozZuname, dozVorname);
	}
	
	public Collection<Kurs> getKurse () {
		return kurse;
	}
	
	public void setKurse (List<Kurs> kurse) {
		this.kurse = kurse;
	}
	
	@Override
	public String toString () {
		return "Dozent{" +
				"dozId=" + dozId +
				", dozZuname='" + dozZuname + '\'' +
				", dozVorname='" + dozVorname + '\'' +
				", kurse=" + kurse +
				'}';
	}
	
	public void addKurs (Kurs kurs) {
		kurse.add(kurs);
		kurs.setDozent(this);
	}
	
	public void removeKurs (Kurs kurs) {
		kurse.remove(kurs);
	}
}
