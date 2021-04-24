package num2.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Positive;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "car")
public class Car {
	@Id
	@Column(name = "car_registration_nr")
	private String registrationNr;
	
	@Column(name = "car_construction_year")
	private Integer constructionYear;
	
	@Column(name = "car_milage")
	@Positive
	private Integer milage;
	
	@Column(name = "car_model")
	private String model;
	
	@ManyToOne
	@JoinColumn(name = "car_location")
	private Station location;
	
	@OneToMany(mappedBy = "car", cascade = CascadeType.ALL)
	private List<Rental> rentals = new ArrayList<>();
	
	
}
