package num1.model;

import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@NoArgsConstructor
@Entity
public class Kurs implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "kurs_id", nullable = false)
	private Integer kursId;
	
	@Basic
	@Column(name = "kurs_bezeichnung", length = 100)
	private String kursBezeichnung;
	
	@Basic
	@Column(name = "kurs_beginndatum")
	private LocalDate kursBeginndatum;
	
	@ManyToOne
	@JoinColumn(name = "kurs_typ", referencedColumnName = "typ_id")
	private Kurstyp kurstyp;
	
	@ManyToOne
	@JoinColumn(name = "kurs_doz_id", referencedColumnName = "doz_id")
	private Dozent dozent;
	
	@ManyToMany
	@JoinTable(name = "kurs_kunde",
			//catalog = "db_4chif_15_labor_13", schema = "public",
			joinColumns = @JoinColumn(name = "kurs_id", referencedColumnName = "kurs_id", nullable = false),
			inverseJoinColumns = @JoinColumn(name = "kunde_id", referencedColumnName = "kunde_id", nullable = false))
	private List<Kunde> kunden = new ArrayList<>();
	
	public Kurs (String kursBezeichnung, LocalDate kursBeginndatum, Kurstyp kurstyp, Dozent dozent) {
		this.kursBezeichnung = kursBezeichnung;
		this.kursBeginndatum = kursBeginndatum;
		this.kurstyp = kurstyp;
		this.dozent = dozent;
		this.dozent.getKurse().add(this);
	}
	
	public Integer getKursId () {
		return kursId;
	}
	
	public void setKursId (Integer kursId) {
		this.kursId = kursId;
	}
	
	public String getKursBezeichnung () {
		return kursBezeichnung;
	}
	
	public void setKursBezeichnung (String kursBezeichnung) {
		this.kursBezeichnung = kursBezeichnung;
	}
	
	public LocalDate getKursBeginndatum () {
		return kursBeginndatum;
	}
	
	public void setKursBeginndatum (LocalDate kursBeginndatum) {
		this.kursBeginndatum = kursBeginndatum;
	}
	
	@Override
	public boolean equals (Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Kurs kurs = (Kurs) o;
		return Objects.equals(kursId, kurs.kursId) && Objects.equals(kursBezeichnung, kurs.kursBezeichnung) && Objects.equals(kursBeginndatum, kurs.kursBeginndatum);
	}
	
	@Override
	public int hashCode () {
		return Objects.hash(kursId, kursBezeichnung, kursBeginndatum);
	}
	
	public Kurstyp getKurstyp () {
		return kurstyp;
	}
	
	public void setKurstyp (Kurstyp kurstyp) {
		this.kurstyp = kurstyp;
	}
	
	public Dozent getDozent () {
		return dozent;
	}
	
	public void setDozent (Dozent dozent) {
		this.dozent = dozent;
	}
	
	public List<Kunde> getKunden () {
		return kunden;
	}
	
	public void setKunden (List<Kunde> kunden) {
		this.kunden = kunden;
	}
	
	public void addKunde (Kunde kunde) {
		kunden.add(kunde);
	}
	
	public void removeKunde (Kunde kunde) {
		kunden.remove(kunde);
	}
	
	@Override
	public String toString () {
		return "Kurs{" +
				"kursId=" + kursId +
				", kursBezeichnung='" + kursBezeichnung + '\'' +
				", kursBeginndatum=" + kursBeginndatum +
				", kurstyp=" + kurstyp +
				'}';
	}
}
