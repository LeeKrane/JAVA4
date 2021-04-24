package num2.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Positive;
import java.time.LocalDate;

@NoArgsConstructor
@Getter
@Setter
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
	
	@Column(name = "rent_rental_date")
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
	
	public Rental (Integer km, LocalDate rentalDate, Car car, Customer driver, Station rentalStation) {
		this.km = km;
		this.rentalDate = rentalDate;
		this.car = car;
		this.driver = driver;
		this.rentalStation = rentalStation;
	}
	
	public Rental (Integer km, LocalDate rentalDate, LocalDate returnDate, Car car, Customer driver, Station rentalStation, Station returnStation) {
		this.km = km;
		this.rentalDate = rentalDate;
		this.returnDate = returnDate;
		this.car = car;
		this.driver = driver;
		this.rentalStation = rentalStation;
		this.returnStation = returnStation;
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
