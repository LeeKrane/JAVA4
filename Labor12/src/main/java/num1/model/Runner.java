package num1.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Positive;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.*;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "runner")
public class Runner implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "runner_id")
	private Integer id;
	
	@Column(name = "runner_last_name")
	private String lastName;
	
	@Column(name = "runner_first_name")
	private String firstName;
	
	@Column(name = "runner_birth_day")
	private LocalDate birthDay;
	
	@Column(name = "runner_gender")
	@Pattern(regexp = "[MW]")
	private String gender;
	
	@Column(name = "runner_weight")
	@Positive
	private Integer weight;
	
	@OneToMany(mappedBy = "runner")
	private List<Run> runs = new ArrayList<>();
	
	public Runner (String lastName, String firstName, LocalDate birthDay, String gender, Integer weight) {
		this.lastName = lastName;
		this.firstName = firstName;
		this.birthDay = birthDay;
		this.gender = gender;
		this.weight = weight;
	}
	
	@Override
	public boolean equals (Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Runner runner = (Runner) o;
		return Objects.equals(id, runner.id);
	}
	
	@Override
	public int hashCode () {
		return Objects.hash(id);
	}
	
	@Override
	public String toString () {
		return "Runner{" +
				"id=" + id +
				", lastName='" + lastName + '\'' +
				", firstName='" + firstName + '\'' +
				", birthDay=" + birthDay +
				", gender=" + gender +
				", weight=" + weight +
				'}';
	}
}
