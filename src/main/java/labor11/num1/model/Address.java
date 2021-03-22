package labor11.num1.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "address")
public class Address implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "addr_id")
	private Integer id;
	
	// Attribute für zip, street und city unverändert
	
	@Column(name = "addr_zip")
	private Integer zip;
	
	@Column(name = "addr_city", length = 129)
	private String city;
	
	@Column(name = "addr_street", length = 129)
	private String street;
	
	@OneToOne(mappedBy = "address")
	private Customer customer;
	
	// Konstruktoren, Getter, Setter, equals(), hashCode(), toString()
	
	public Address () {
	}
	
	public Address (Integer zip, String city, String street, Customer customer) {
		this.zip = zip;
		this.city = city;
		this.street = street;
		this.customer = customer;
	}
	
	public Address (Integer id, Integer zip, String city, String street, Customer customer) {
		this.id = id;
		this.zip = zip;
		this.city = city;
		this.street = street;
		this.customer = customer;
	}
	
	public Integer getId () {
		return id;
	}
	
	public void setId (Integer id) {
		this.id = id;
	}
	
	public Integer getZip () {
		return zip;
	}
	
	public void setZip (Integer zip) {
		this.zip = zip;
	}
	
	public String getCity () {
		return city;
	}
	
	public void setCity (String city) {
		this.city = city;
	}
	
	public String getStreet () {
		return street;
	}
	
	public void setStreet (String street) {
		this.street = street;
	}
	
	public Customer getCustomer () {
		return customer;
	}
	
	public void setCustomer (Customer customer) {
		this.customer = customer;
	}
	
	@Override
	public boolean equals (Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Address address = (Address) o;
		return Objects.equals(id, address.id);
	}
	
	@Override
	public int hashCode () {
		return Objects.hash(id);
	}
	
	@Override
	public String toString () {
		return "Address{" +
				"id=" + id +
				", zip=" + zip +
				", city='" + city + '\'' +
				", street='" + street + '\'' +
				", customer=" + customer +
				'}';
	}
}
