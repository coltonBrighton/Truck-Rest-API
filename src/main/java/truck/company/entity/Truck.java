package truck.company.entity;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Entity
@Data
public class Truck {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long truckId;
	
	private String make;
	private String model;
	private String plateNumber;
	
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	@ManyToMany(cascade = CascadeType.ALL)
  @JoinTable(
      name = "truck_driver",
      joinColumns = @JoinColumn(name = "truck_id"),
      inverseJoinColumns = @JoinColumn(name = "driver_id")
  )
	private Set<Drivers> drivers = new HashSet<>();
	
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	@ManyToOne
	@JoinColumn(name = "company_id", nullable = false)
	private Company company;
}
