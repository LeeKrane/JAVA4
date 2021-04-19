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
	private Integer id;
	
	@Column(name = "r_bezeichnung")
	private String bezeichnung;
	
	@OneToMany(mappedBy = "reisetyp")
	private Set<Reiseveranstaltung> reiseveranstaltungen = new HashSet<>();
	
	public Reisetyp (String bezeichnung) {
		this.bezeichnung = bezeichnung;
	}
	
	public void addReiseveranstaltung (Reiseveranstaltung reiseveranstaltung) {
		reiseveranstaltungen.add(reiseveranstaltung);
	}
	
	@Override
	public boolean equals (Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Reisetyp reisetyp = (Reisetyp) o;
		return Objects.equals(id, reisetyp.id);
	}
	
	@Override
	public int hashCode () {
		return Objects.hash(id);
	}
	
	@Override
	public String toString () {
		return "Reisetyp{" +
				"r_id=" + id +
				", r_bezeichnung='" + bezeichnung + '\'' +
				'}';
	}
}
