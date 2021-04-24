package num2.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Range;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@NoArgsConstructor
@Setter
@Getter
@EqualsAndHashCode
@Entity
@Table(name = "customer")
public class Customer {
	@Id
	@Range(min = 111111, max = 1000000, message = "The given customer id is illegal. It should be in between 111111 and 999999.")
	@Column(name = "cust_id")
	private Integer id;
	
	@Column(name = "cust_last_name")
	private String lastName;
	
	@Column(name = "cust_first_name")
	private String firstName;
	
	public Customer (Integer id, String lastName, String firstName) {
		this.id = id;
		this.lastName = lastName;
		this.firstName = firstName;
	}
	
	@Override
	public String toString () {
		return "Customer{" +
				"id=" + id +
				", lastName='" + lastName + '\'' +
				", firstName='" + firstName + '\'' +
				'}';
	}
}
