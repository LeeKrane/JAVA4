package num2.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Positive;
import java.time.LocalDate;

@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@Entity
@Table(name = "rental")
public class Rental {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "rent_id")
	private Integer id;
	
	@Column(name = "rent_km")
	@Positive
	private Integer km;
	
	@Column(name = "rent_rental_date", nullable = false)
	private LocalDate rentalDate;
	
	@Column(name = "rent_return_date")
	private LocalDate returnDate;
	
	@ManyToOne
	@JoinColumn(name = "rent_car", nullable = false)
	private Car car;
	
	@ManyToOne
	@JoinColumn(name = "rent_driver", nullable = false)
	private Customer driver;
	
	@ManyToOne
	@JoinColumn(name = "rent_rental_st", nullable = false)
	private Station rentalStation;
	
	@ManyToOne
	@JoinColumn(name = "rent_return_st")
	private Station returnStation;
	
	public Rental (LocalDate rentalDate, Car car, Customer driver) {
		if (car.getLocation() == null)
			throw new IllegalArgumentException("The car is currently already rented!");
		this.rentalDate = rentalDate;
		this.car = car;
		this.driver = driver;
		this.rentalStation = car.getLocation();
		car.rentCar(this);
	}
	
	// This constructor is for testing purposes only
	public Rental (Integer km, LocalDate rentalDate, LocalDate returnDate, Car car, Customer driver, Station returnStation) {
		if (car.getLocation() == null)
			throw new IllegalArgumentException("The car is currently already rented!");
		this.rentalDate = rentalDate;
		this.car = car;
		this.driver = driver;
		this.rentalStation = car.getLocation();
		rentCar();
		returnCar(km, returnStation, returnDate);
	}
	
	public void rentCar () {
		car.rentCar(this);
	}
	
	public void returnCar (Integer km, Station returnStation, LocalDate returnDate) {
		this.km = km;
		this.returnStation = returnStation;
		this.returnDate = returnDate;
		car.returnCar(returnStation, km);
	}
	
	@Override
	public String toString () {
		return "Rental{" +
				"id=" + id +
				", km=" + km +
				", rentalDate=" + rentalDate +
				", returnDate=" + returnDate +
				", car=" + car +
				", driver=" + driver +
				", rentalStation=" + rentalStation +
				", returnStation=" + returnStation +
				'}';
	}
}
