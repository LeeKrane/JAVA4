package labor11.num1.model.onetomany;

import labor11.num1.model.onetoone.Address;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "publisher")
public class Publisher implements Serializable {
	@Id
	@Column(name = "pub_name", nullable = false)
	private String name;
	
	@Column(name = "pub_description")
	private String description;
	
	@OneToMany(mappedBy = "publisher")
	private Set<Book> books = new HashSet<>();
	
	@OneToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "pub_addr",
			   joinColumns = {@JoinColumn(name = "pub_id")},
			   inverseJoinColumns = {@JoinColumn(name = "addr_id")})
	private Set<Address> addresses = new HashSet<>();
	
	public Publisher (String name, String description) {
		this.name = name;
		this.description = description;
	}
	
	@Override
	public boolean equals (Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Publisher publisher = (Publisher) o;
		return Objects.equals(name, publisher.name);
	}
	
	@Override
	public int hashCode () {
		return Objects.hash(name);
	}
	
	@Override
	public String toString () {
		return "Publisher{" +
				"name='" + name + '\'' +
				", description='" + description + '\'' +
				'}';
	}
}
