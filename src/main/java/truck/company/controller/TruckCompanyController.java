package truck.company.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;
import truck.company.controller.model.TruckCompanyData;
import truck.company.controller.model.TruckCompanyDrivers;
import truck.company.controller.model.TruckCompanyTruck;
import truck.company.service.TruckCompanyService;

//  Indicate TruckCompanyController is designed to handle restful web requests
@RestController
// map requests to /truck_driving
@RequestMapping("/truck_driving")
// provide logging
@Slf4j

// create class named TruckCompanyController
public class TruckCompanyController {

	// inject TruckCompanyService using autowired
	@Autowired
	TruckCompanyService truckCompanyService;

	/*
	 * Create, Read, Update, Delete companies
	 */

	// Get(read) all companies
	// map company to /truck_driving/company
	@GetMapping("/company")
	// get a list of all truck companies
	public List<TruckCompanyData> listTruckCompanies() {
		// return a result of retrieveAllTruckCompanies from TruckCompanyService
		return truckCompanyService.retrieveAllTruckCompanies();
	}

	// Get(read) company by id
	// map company to /truck_driving/company/{companyId}
	@GetMapping("/company/{companyId}")
	public TruckCompanyData getTruckCompanyById(@PathVariable Long companyId) {
		// return result of retrieveCompanyByID in TruckCompanyService
		return truckCompanyService.retrieveCompanyById(companyId);
	}

	// Post(create) a company
	// map to add a company to /truck_driving/company
	@PostMapping("/company")
	// if company is added return HTTP code 201
	@ResponseStatus(code = HttpStatus.CREATED)
	// createCompany by passing in the mapping of the DTO TruckCompanyData
	public TruckCompanyData createCompany(
			@RequestBody TruckCompanyData truckCompanyData) {
		log.info("Creating comapany: {}", truckCompanyData);
		// return the result of calling saveCompany in TruckCompanyService
		return truckCompanyService.saveCompany(truckCompanyData);
	}

	// Put(update) a company via id
	// mapping to update the company to /company/{companyId}
	@PutMapping("/company/{companyId}")
	// update company data
	public TruckCompanyData updateCompanyData(@PathVariable Long companyId,
			@RequestBody TruckCompanyData truckCompanyData) {
		log.info("Updating company with ID: {}", companyId);
		// setCompanyId by calling setCompanyId in TruckCompanyData
		truckCompanyData.setCompanyId(companyId);
		return truckCompanyService.saveCompany(truckCompanyData);
	}

	// Delete(delete) a company via Id
	// mapping to delete a company "/company/{companyId}
	@DeleteMapping("/company/{companyId}")
	// delete a company using a companyID
	public Map<String, String> deleteCompanyById(@PathVariable Long companyId) {
		log.info("Deleting company with ID: {}", companyId);
		// get result of deleteCompanyById method in truckCompanyService
		truckCompanyService.deleteCompanyById(companyId);
		// return the mapped result of the company being deleted successfully
		// and companyId
		return Map.of("message", "Company deleted successfully", "companyId",
				companyId.toString());
	}

	/*
	 * Create, Read, Update, Delete Trucks
	 */

	// Get(Read) all trucks
	// map trucks to /truck_driving/company/{companyId}/truck
	@GetMapping("/company/{companyId}/truck")
	// get a list of all trucks
	public List<TruckCompanyTruck> listAllTrucks() {
		// return the result of calling retrieveAllTrucks in TruckCompanyService
		return truckCompanyService.retrieveAllTrucks();
	}

	// Get(read) truck by id
	// map getting truck by id to
	// /truck_driving/company/{companyId}/truck/{truckId}
	@GetMapping("/company/{companyId}/truck/{truckId}")
	public TruckCompanyTruck getTruckById(@PathVariable Long companyId, @PathVariable Long truckId) {
		// return result of retrieveTruckById from truckCompanyService
		return truckCompanyService.retrieveTruckById(companyId, truckId);
	}

	// Post(create) a truck
	// map creating a company to /truck_drivers/company/{companyId}/truck
	@PostMapping("/company/{companyId}/truck")
	// return http code 201
	@ResponseStatus(code = HttpStatus.CREATED)
	public TruckCompanyTruck truckCompanyTruck(@PathVariable Long companyId,
			@RequestBody TruckCompanyTruck truckCompanyTruck) {
		log.info("Creating truck {} at company with ID={}", truckCompanyTruck,
				companyId);
		// return result of saveTruck in truckCompanyService
		return truckCompanyService.saveTruck(companyId, truckCompanyTruck);
	}

	// Put(update) a truck via id
	// map updating a company to
	// /truck_drivers/company/{companyId}/truck/{truckId}
	@PutMapping("/company/{companyId}/truck/{truckId}")
	public TruckCompanyTruck updateTruck(@PathVariable Long companyId,
			@PathVariable Long truckId,
			@RequestBody TruckCompanyTruck truckCompanyTruck) {
		log.info("Updating truck with ID{}", truckId);
		truckCompanyTruck.setTruckId(truckId);
		return truckCompanyService.saveTruck(companyId, truckCompanyTruck);
	}

	// Delete(delete) a truck
	// map deleting a company to
	// /truck_drivers/company/{companyId}/truck/{truckId}
	@DeleteMapping("/company/{companyId}/truck/{truckId}")
	public Map<String, String> deleteTruckById(@PathVariable Long truckId,
			@PathVariable Long companyId) {
		log.info("Deleting truck with ID {}", truckId);
		truckCompanyService.deleteTruckById(companyId, truckId);
		return Map.of("message", "Truck deleted successfully", "truckId",
				truckId.toString());
	}

	/*
	 * Create, Read, Update, Delete Drivers
	 */

	// Get(read) all drivers
	// map getting all drivers to /truck_drivers/company/{companyId}/driver
	@GetMapping("/company/{companyId}/driver")
	public List<TruckCompanyDrivers> listTruckDrivers() {
		return truckCompanyService.retrieveAllDrivers();
	}

	// Get(read) drivers by id
	// map get driver by id to
	// /truck_drivers/company/{companyId/driver/{driverId}
	@GetMapping("/company/{companyId}/driver/{driverId}")
	public TruckCompanyDrivers getDriversById(@PathVariable Long companyId, @PathVariable Long driverId) {
		return truckCompanyService.retrieveDriversById(companyId, driverId);
	}

	// Post(create) a driver
	// map create a driver to /truck_drivers/company/{companyId}/driver
	@PostMapping("/company/{companyId}/driver")
	@ResponseStatus(code = HttpStatus.CREATED)
	public TruckCompanyDrivers truckCompanyDriver(@PathVariable Long companyId,
			@RequestBody TruckCompanyDrivers truckCompanyDrivers) {
		log.info("Creating driver {} at company with ID={}",
				truckCompanyDrivers, companyId);
		return truckCompanyService.saveDrivers(companyId, truckCompanyDrivers);
	}

	// Put(update) a driver via id
	// map updateDriver to /truck_drivers/company/{companyId}/driver
	@PutMapping("/company/{companyId}/driver/{driverId}")
	public TruckCompanyDrivers updateDriver(@PathVariable Long companyId,
			@PathVariable Long driverId,
			@RequestBody TruckCompanyDrivers truckCompanyDriver) {

		log.info("Updating driver with ID{}", driverId);

		truckCompanyDriver.setDriverId(driverId);
		return truckCompanyService.saveDrivers(companyId, truckCompanyDriver);
	}

	// Delete(delete) a driver
	// map deleteDriverById to
	// /truck_drivers/company/{companyId}/driver/{driverId}
	@DeleteMapping("/company/{companyId}/driver/{driverId}")
	public Map<String, String> deleteDriverById(@PathVariable Long driverId,
			@PathVariable Long companyId) {
		log.info("Deleting driver with ID {}", driverId);
		truckCompanyService.deleteDriverById(companyId, driverId);
		return Map.of("message", "Driver deleted successfully", "driverId",
				driverId.toString());
	}

	/*
	 * assign and unassign drivers and trucks together
	 */
	// assign trucks to drivers and vice versa
	// map assigning trucks to drivers to
	// /truck_drivers/truck/{truckId}/{driverId}
	@PutMapping("/truck/{truckId}/{driverId}")
	public Map<String, String> joinTruckAndDriver(@PathVariable Long driverId,
			@PathVariable Long truckId) {
		log.info("Assigning truck driver with ID {} and truck with ID {}",
				driverId, truckId);
		truckCompanyService.joinTrucksAndDrivers(driverId, truckId);
		return Map.of("message", "Truck with ID=" + truckId
				+ " has been assigned to Driver with ID=" + driverId);
	}

	// Delete Truck and Driver from join table
	// map unassigning trucks from drivers to
	// /truck_drivers/truck/{truckId}/driver/{driverId}
	@DeleteMapping("/truck/{truckId}/driver/{driverId}")
	public Map<String, String> deleteTruckFromDriver(@PathVariable Long truckId,
			@PathVariable Long driverId) {
		log.info("Removing truck with ID {} from driver with ID {}", truckId,
				driverId);
		truckCompanyService.removeTruckFromDriver(truckId, driverId);
		return Map.of("message", "Truck with ID=" + truckId
				+ " has been unassigned from driver with ID=" + driverId);
	}

}
