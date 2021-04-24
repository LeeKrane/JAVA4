package num2.model;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "reiseveranstaltung")
public class Reiseveranstaltung implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "rv_id")
	private Integer id;
	
	@Column(name = "rv_zielort", nullable = false, length = 30)
	private String zielort;
	
	@Column(name = "rv_beschreibung", length = 300)
	private String beschreibung;
	
	@Column(name = "rv_beginn", nullable = false)
	private LocalDate rv_beginn;
	
	@Column(name = "rv_ende")
	private LocalDate rv_ende;
	
	@Column(name = "rv_preis", nullable = false)
	private double preis;
	
	@ManyToOne
	@JoinColumn(name = "r_rv_id", nullable = false)
	private Reisetyp reisetyp;
	
	public Reiseveranstaltung (String zielort, String beschreibung, LocalDate rv_beginn, LocalDate rv_ende, double preis, Reisetyp reisetyp) {
		this.zielort = zielort;
		this.beschreibung = beschreibung;
		this.rv_beginn = rv_beginn;
		this.rv_ende = rv_ende;
		this.preis = preis;
		this.reisetyp = reisetyp;
	}
	
	@Override
	public boolean equals (Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Reiseveranstaltung that = (Reiseveranstaltung) o;
		return Objects.equals(id, that.id);
	}
	
	@Override
	public int hashCode () {
		return Objects.hash(id);
	}
	
	@Override
	public String toString () {
		return "Reiseveranstaltung{" +
				"rv_id=" + id +
				", zielort='" + zielort + '\'' +
				", rv_beschreibung='" + beschreibung + '\'' +
				", rv_beginn=" + rv_beginn +
				", rv_ende=" + rv_ende +
				", preis=" + preis +
				'}';
	}
}
