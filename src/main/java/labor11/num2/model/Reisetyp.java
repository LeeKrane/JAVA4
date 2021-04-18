package labor11.num2.model;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "reisetyp")
public class Reisetyp implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "r_id")
	private Integer r_id;
	
	@Column(name = "r_bezeichnung")
	private String r_bezeichnung;
	
	@OneToMany(mappedBy = "reisetyp")
	private Set<Reiseveranstaltung> reiseveranstaltungen = new HashSet<>();
	
	public Reisetyp (String r_bezeichnung) {
		this.r_bezeichnung = r_bezeichnung;
	}
	
	public void addReiseveranstaltung (Reiseveranstaltung reiseveranstaltung) {
		reiseveranstaltungen.add(reiseveranstaltung);
	}
	
	@Override
	public boolean equals (Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Reisetyp reisetyp = (Reisetyp) o;
		return Objects.equals(r_id, reisetyp.r_id);
	}
	
	@Override
	public int hashCode () {
		return Objects.hash(r_id);
	}
	
	@Override
	public String toString () {
		return "Reisetyp{" +
				"r_id=" + r_id +
				", r_bezeichnung='" + r_bezeichnung + '\'' +
				'}';
	}
}
