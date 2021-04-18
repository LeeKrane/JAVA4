package labor11.num1.model.onetoone;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "customer")
public class Customer implements Serializable {
	@Id
	@Column(name = "cust_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(name = "cust_lastname", length = 65)
	private String lastname;
	
	@Column(name = "cust_firstname", length = 65)
	private String firstname;
	
	@Column(name = "cust_email")
	private String email;
	
	@OneToOne(optional = false, cascade = CascadeType.PERSIST)
	@JoinColumn(name = "cust_addr_id")
	private Address address;
	
	public Customer (String lastname, String firstname, String email) {
		this.lastname = lastname;
		this.firstname = firstname;
		this.email = email;
	}
	
	@Override
	public boolean equals (Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Customer customer = (Customer) o;
		return Objects.equals(id, customer.id);
	}
	
	@Override
	public int hashCode () {
		return Objects.hash(id);
	}
	
	@Override
	public String toString () {
		return "Customer{" +
				"id=" + id +
				", lastname='" + lastname + '\'' +
				", firstname='" + firstname + '\'' +
				", email='" + email + '\'' +
				'}';
	}
}
