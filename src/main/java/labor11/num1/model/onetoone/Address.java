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
@Table(name = "address")
public class Address implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "addr_id")
	private Integer id;
	
	@Column(name = "addr_zip")
	private Integer zip;
	
	@Column(name = "addr_city", length = 60)
	private String city;
	
	@Column(name = "addr_street", length = 60)
	private String street;
	
	@OneToOne(mappedBy = "address")
	private Customer customer;
	
	public Address (Integer zip, String city, String street) {
		this.zip = zip;
		this.city = city;
		this.street = street;
	}
	
	public Address (Integer zip, String city, String street, Customer customer) {
		this.zip = zip;
		this.city = city;
		this.street = street;
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
				'}';
	}
}
