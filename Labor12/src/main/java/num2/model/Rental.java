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
	
	
}
