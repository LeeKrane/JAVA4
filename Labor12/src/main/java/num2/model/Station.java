package num2.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@NoArgsConstructor
@Setter
@Getter
@Entity
@Table(name = "station")
public class Station {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "st_id")
	private Integer id;
	
	@Column(name = "st_city")
	private String city;
	
	public Station (String city) {
		this.city = city;
	}
	
	@Override
	public String toString () {
		return "Station{" +
				"id=" + id +
				", city='" + city + '\'' +
				'}';
	}
}
