package num1.model;

import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@NoArgsConstructor
@Entity
public class Kurstyp implements Serializable {
	@Id
	@Column(name = "typ_id", nullable = false, length = 1)
	private String typId;
	
	@Basic
	@Column(name = "typ_bezeichnung", length = 100)
	private String typBezeichnung;
	
	@OneToMany(mappedBy = "kurstyp")
	private List<Kurs> kurse = new ArrayList<>();
	
	public Kurstyp (String typId, String typBezeichnung) {
		this.typId = typId;
		this.typBezeichnung = typBezeichnung;
	}
	
	public String getTypId () {
		return typId;
	}
	
	public void setTypId (String typId) {
		this.typId = typId;
	}
	
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
		Kurstyp kurstyp = (Kurstyp) o;
		return Objects.equals(typId, kurstyp.typId) && Objects.equals(typBezeichnung, kurstyp.typBezeichnung);
	}
	
	@Override
	public int hashCode () {
		return Objects.hash(typId, typBezeichnung);
	}
	
	public List<Kurs> getKurse () {
		return kurse;
	}
	
	public void setKurse (List<Kurs> kurse) {
		this.kurse = kurse;
	}
	
	@Override
	public String toString () {
		return "Kurstyp{" +
				"typId='" + typId + '\'' +
				", typBezeichnung='" + typBezeichnung + '\'' +
				'}';
	}
}
