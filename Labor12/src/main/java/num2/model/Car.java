package num2.model;

import lombok.EqualsAndHashCode;
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
@EqualsAndHashCode
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
	
	@OneToMany(mappedBy = "car")
	private List<Rental> rentals = new ArrayList<>();
	
	public Car (String registrationNr, Integer constructionYear, Integer milage, String model) {
		this.registrationNr = registrationNr;
		this.constructionYear = constructionYear;
		this.milage = milage;
		this.model = model;
	}
	
	public Car (String registrationNr, Integer constructionYear, Integer milage, String model, Station location) {
		this.registrationNr = registrationNr;
		this.constructionYear = constructionYear;
		this.milage = milage;
		this.model = model;
		this.location = location;
	}
	
	@Override
	public String toString () {
		return "Car{" +
				"registrationNr='" + registrationNr + '\'' +
				", constructionYear=" + constructionYear +
				", milage=" + milage +
				", model='" + model + '\'' +
				", location=" + location +
				'}';
	}
	
	public void returnCar (Station returnStation, Integer km) {
		location = returnStation;
		milage += km;
	}
}
