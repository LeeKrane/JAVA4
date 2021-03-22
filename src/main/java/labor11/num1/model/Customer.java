package labor11.num1.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;


@Entity
@Table(name = "customer")
public class Customer implements Serializable {
	@Id
	@Column(name = "cust_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	// Attribute firstname, lastname und email unverändert
	
	@Column(name = "cust_lastname", length = 65)
	private String lastname;
	
	@Column(name = "cust_firstname", length = 65)
	private String firstname;
	
	@Column(name = "cust_email", length = 129)
	private String email;
	
	@OneToOne(optional = false, cascade = CascadeType.PERSIST)
	@JoinColumn(name = "cust_addr_id")
	private Address address;
	
	// Konstruktoren, Getter, Setter, equals(), hashCode(), toString()
	
	
	public Customer () {
	}
	
	public Customer (String lastname, String firstname, String email, Address address) {
		this.lastname = lastname;
		this.firstname = firstname;
		this.email = email;
		this.address = address;
	}
	
	public Customer (Integer id, String lastname, String firstname, String email, Address address) {
		this.id = id;
		this.lastname = lastname;
		this.firstname = firstname;
		this.email = email;
		this.address = address;
	}
	
	public Integer getId () {
		return id;
	}
	
	public void setId (Integer id) {
		this.id = id;
	}
	
	public String getLastname () {
		return lastname;
	}
	
	public void setLastname (String lastname) {
		this.lastname = lastname;
	}
	
	public String getFirstname () {
		return firstname;
	}
	
	public void setFirstname (String firstname) {
		this.firstname = firstname;
	}
	
	public String getEmail () {
		return email;
	}
	
	public void setEmail (String email) {
		this.email = email;
	}
	
	public Address getAddress () {
		return address;
	}
	
	public void setAddress (Address address) {
		this.address = address;
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
				", address=" + address +
				'}';
	}
}
