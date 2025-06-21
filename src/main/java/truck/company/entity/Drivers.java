package truck.company.entity;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Entity
@Data
public class Drivers {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long driverId;

	private String driverName;
	private String driverAddress;
	private String driverCity;
	private String driverState;
	private String driverZip;
	private String driverEmail;
	private String driverPhone;

	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	@ManyToMany(mappedBy = "drivers", cascade = CascadeType.ALL)
	Set<Truck> trucks = new HashSet<>();
	
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	@ManyToOne
	@JoinColumn(name = "company_id", nullable = false)
	private Company company;
}
