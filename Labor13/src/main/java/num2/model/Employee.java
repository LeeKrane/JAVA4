package num2.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@NoArgsConstructor
@Getter
@Setter
@Entity
public class Employee implements Serializable {
	@Id
	@Min(value = 100000, message = "The id must be at least 100000.")
	@Max(value = 999999, message = "The id must not exceed 999999.")
	@Column(name = "e_id")
	private Integer id;
	
	@Column(name = "e_name")
	private String name;
	
	@Column(name = "e_firstname")
	private String firstname;
	
	@OneToMany(mappedBy = "id.employee")
	private List<Answer> answers = new ArrayList<>();
	
	public Employee (Integer id, String name, String firstname) {
		this.id = id;
		this.name = name;
		this.firstname = firstname;
	}
	
	@Override
	public boolean equals (Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Employee employee = (Employee) o;
		return Objects.equals(id, employee.id);
	}
	
	@Override
	public int hashCode () {
		return Objects.hash(id);
	}
	
	@Override
	public String toString () {
		return "Employee{" +
				"id=" + id +
				", name='" + name + '\'' +
				", firstname='" + firstname + '\'' +
				'}';
	}
	
	public void addAnswer (Answer answer) {
		answers.add(answer);
	}
}
