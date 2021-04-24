package num1.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Min;
import java.time.LocalDate;
import java.util.Objects;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "run")
public class Run {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "run_id")
	private Integer id;
	
	@Column(name = "run_date")
	private LocalDate date;
	
	@Column(name = "run_minutes")
	@Min(value = 0)
	private Integer minutes;
	
	@Column(name = "run_distance")
	@Min(value = 0)
	private Integer distance;
	
	@ManyToOne
	@JoinColumn(name = "run_runner_id")
	private Runner runner;
	
	public Run (LocalDate date, Integer minutes, Integer distance) {
		this.date = date;
		this.minutes = minutes;
		this.distance = distance;
	}
	
	public Run (LocalDate date, Integer minutes, Integer distance, Runner runner) {
		this.date = date;
		this.minutes = minutes;
		this.distance = distance;
		this.runner = runner;
	}
	
	@Override
	public boolean equals (Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Run run = (Run) o;
		return Objects.equals(id, run.id);
	}
	
	@Override
	public int hashCode () {
		return Objects.hash(id);
	}
	
	@Override
	public String toString () {
		return "Run{" +
				"id=" + id +
				", date=" + date +
				", minutes=" + minutes +
				", distance=" + distance +
				", runner=" + runner +
				'}';
	}
}
