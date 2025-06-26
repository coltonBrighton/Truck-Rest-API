package truck.company.controller.model;

import java.util.HashSet;
import java.util.Set;

import lombok.Data;
import lombok.NoArgsConstructor;
import truck.company.entity.Drivers;
import truck.company.entity.Truck;

@Data
@NoArgsConstructor
public class TruckCompanyTruck {

	private Long truckId;
	private String make;
	private String model;
	private String plateNumber;

	private Set<TruckCompanyDrivers> drivers = new HashSet<>();

	public TruckCompanyTruck(Truck truck) {
		truckId = truck.getTruckId();
		make = truck.getMake();
		model = truck.getModel();
		plateNumber = truck.getPlateNumber();

		if (truck.getDrivers() != null) {
			for (Drivers driver : truck.getDrivers()) {
				drivers.add(new TruckCompanyDrivers(driver));
			}
		}
	}

}
