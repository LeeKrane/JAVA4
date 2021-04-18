package labor11.num2.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "benutzer")
public class Benutzer implements Serializable {
	@Id
	@Column(name = "b_email")
	@Email(message = "E-Mail ist ungültig!")
	private String email;
	
	@Column(name = "b_passwort")
	@Size(min = 6, message = "Passwort ist zu kurz! (Mindestens 6 Zeichen)")
	private String passwort;
	
	@Column(name = "b_newsletter")
	private boolean newsletter;
	
	@ManyToMany
	@JoinTable(name = "b_r",
			   joinColumns = {@JoinColumn(name = "b_email")},
			   inverseJoinColumns = {@JoinColumn(name = "r_id")})
	private Set<Reisetyp> reisetypen = new HashSet<>();
	
	public Benutzer (String email, String passwort, boolean newsletter) {
		this.email = email;
		this.passwort = passwort;
		this.newsletter = newsletter;
	}
	
	public void addReisetyp (Reisetyp reisetyp) {
		reisetypen.add(reisetyp);
	}
	
	@Override
	public boolean equals (Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Benutzer benutzer = (Benutzer) o;
		return Objects.equals(email, benutzer.email);
	}
	
	@Override
	public int hashCode () {
		return Objects.hash(email);
	}
	
	@Override
	public String toString () {
		return "Benutzer{" +
				"email='" + email + '\'' +
				", passwort='" + passwort + '\'' +
				", newsletter=" + newsletter +
				'}';
	}
}
