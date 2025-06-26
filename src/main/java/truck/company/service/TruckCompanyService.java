package truck.company.service;

import java.util.HashSet;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import truck.company.controller.model.TruckCompanyData;
import truck.company.controller.model.TruckCompanyDrivers;
import truck.company.controller.model.TruckCompanyTruck;
import truck.company.dao.TruckCompanyDao;
import truck.company.dao.TruckDriversDao;
import truck.company.dao.TruckTruckDao;
import truck.company.entity.Company;
import truck.company.entity.Drivers;
import truck.company.entity.Truck;

@Service
public class TruckCompanyService {

	// inject TruckCompanyDao
	@Autowired
	TruckCompanyDao truckCompanyDao;
	// inject TruckDriversDao
	@Autowired
	TruckDriversDao truckDriversDao;
	// inject TruckTruckDao
	@Autowired
	TruckTruckDao truckTruckDao;

	/*
	 * create read update and delete company
	 */

	// save company data
	@Transactional(readOnly = false)
	public TruckCompanyData saveCompany(TruckCompanyData truckCompanyData) {
		Company company = findOrCreateCompany(truckCompanyData.getCompanyId());

		setCompanyFields(company, truckCompanyData);
		return new TruckCompanyData(truckCompanyDao.save(company));
	}
	
	// find or create company if there is none
	private Company findOrCreateCompany(Long companyId) {
		if (Objects.isNull(companyId)) {
			return new Company();
		} else {
			return findCompanyById(companyId);
		}
	}

	// find Company by id if company does not exist throw No such element
	// exception, if exists return Company
	private Company findCompanyById(Long companyId) {
		return truckCompanyDao.findById(companyId)
				.orElseThrow(() -> new NoSuchElementException(
						"Company with ID=" + companyId + " does not exist."));
	}

	private void setCompanyFields(Company company,
			TruckCompanyData truckCompanyData) {
		company.setName(truckCompanyData.getCompanyName());
		company.setAddress(truckCompanyData.getCompanyAddress());
		company.setCity(truckCompanyData.getCompanyCity());
		company.setState(truckCompanyData.getCompanyState());
		company.setZip(truckCompanyData.getCompanyZip());
		company.setPhone(truckCompanyData.getCompanyPhone());
	}

	@Transactional(readOnly = true)
	public List<TruckCompanyData> retrieveAllTruckCompanies() {
		// @formatter:off
		return truckCompanyDao.findAll().stream().map(TruckCompanyData::new)
				.toList();
		// @formatter:on
	}

	@Transactional(readOnly = true)
	public TruckCompanyData retrieveCompanyById(Long companyId) {
		Company company = findCompanyById(companyId);
		TruckCompanyData truckCompanyData = new TruckCompanyData(company);

		return truckCompanyData;
	}

	@Transactional(readOnly = false)
	public void deleteCompanyById(Long companyId) {
		Company company = findCompanyById(companyId);
		truckCompanyDao.delete(company);

	}

	/*
	 * Create, read, update, delete trucks
	 */

	@Transactional(readOnly = false)
	public TruckCompanyTruck saveTruck(Long companyId,
			TruckCompanyTruck truckCompanyTruck) {
		Company company = findOrCreateCompany(companyId);
		Long truckId = truckCompanyTruck.getTruckId();
		Truck truck = findOrCreateTruck(companyId, truckId);

		setTruckFields(truck, truckCompanyTruck);

		truck.setCompany(company);
		company.getTrucks().add(truck);

		return new TruckCompanyTruck(truckTruckDao.save(truck));
	}

	private Truck findOrCreateTruck(Long companyId, Long truckId) {
		if (Objects.isNull(truckId)) {
			return new Truck();
		} else {
			return findTruckById(companyId, truckId);
		}
	}

	private Truck findTruckById(Long companyId, Long truckId) {
		Truck truck = truckTruckDao.findById(truckId)
				.orElseThrow(() -> new NoSuchElementException(
						"Truck with ID " + truckId + " Does not exist"));
		if (truck.getCompany().getCompanyId().equals(companyId)) {
			return truck;
		} else {
			throw new IllegalArgumentException(
					"Truck does not belong to company with ID " + companyId
							+ ".");
		}
	}

	private void setTruckFields(Truck truck,
			TruckCompanyTruck truckCompanyTruck) {
		truck.setMake(truckCompanyTruck.getMake());
		truck.setModel(truckCompanyTruck.getModel());
		truck.setPlateNumber(truckCompanyTruck.getPlateNumber());

	}

	@Transactional(readOnly = true)
	public List<TruckCompanyTruck> retrieveAllTrucks() {
		return truckTruckDao.findAll().stream().map(TruckCompanyTruck::new)
				.toList();
	}

	@Transactional(readOnly = true)
	public TruckCompanyTruck retrieveTruckById(Long companyId, Long truckId) {
		Truck truck = findTruckById(companyId, truckId);
		TruckCompanyTruck truckCompanyTruck = new TruckCompanyTruck(truck);
		// get a all drivers assigned to each truck
		Set<TruckCompanyDrivers> drivers = new HashSet<>();
		for (Drivers driver : truck.getDrivers()) {
			drivers.add(new TruckCompanyDrivers(driver));
		}
		truckCompanyTruck.setDrivers(drivers);
		return truckCompanyTruck;
	}

	@Transactional(readOnly = false)
	public void deleteTruckById(Long companyId, Long truckId) {
		Truck truck = findTruckById(companyId, truckId);
		// Delete the truck from its assigned drivers
		truck.getDrivers()
				.forEach((driver) -> driver.getTrucks().remove(truck));
		// clear all drivers assigned to the truck
		truck.getDrivers().clear();
		// save changes
		truckTruckDao.save(truck);
		truckTruckDao.delete(truck);
	}

	/*
	 * Create, Read, Update, Delete, Drivers
	 */

	@Transactional(readOnly = true)
	public List<TruckCompanyDrivers> retrieveAllDrivers() {
		return truckDriversDao.findAll().stream().map(TruckCompanyDrivers::new)
				.toList();
	}

	@Transactional(readOnly = true)
	public TruckCompanyDrivers retrieveDriversById(Long companyId,
			Long driverId) {
		Drivers driver = findDriverById(companyId, driverId);
		TruckCompanyDrivers truckCompanyDrivers = new TruckCompanyDrivers(
				driver);
		// Get a all trucks that are assigned to each driver
		Set<TruckCompanyTruck> trucks = new HashSet<>();
		for (Truck truck : driver.getTrucks()) {
			trucks.add(new TruckCompanyTruck(truck));
		}
		truckCompanyDrivers.setTrucks(trucks);
		return truckCompanyDrivers;
	}

	private Drivers findDriverById(Long companyId, Long driverId) {
		Drivers driver = truckDriversDao.findById(driverId)
				.orElseThrow(() -> new NoSuchElementException(
						"Truck with ID " + driverId + " Does not exist"));
		if (driver.getCompany().getCompanyId().equals(companyId)) {
			return driver;
		} else {
			throw new IllegalArgumentException(
					"driver does not belong to company with ID " + companyId
							+ ".");
		}
	}

	public TruckCompanyDrivers saveDrivers(Long companyId,
			TruckCompanyDrivers truckCompanyDrivers) {
		Company company = findOrCreateCompany(companyId);
		Long driverId = truckCompanyDrivers.getDriverId();
		Drivers driver = findOrCreateDriver(companyId, driverId);

		setDriverFields(driver, truckCompanyDrivers);

		driver.setCompany(company);
		company.getDrivers().add(driver);

		return new TruckCompanyDrivers(truckDriversDao.save(driver));
	}

	private Drivers findOrCreateDriver(Long companyId, Long driverId) {
		if (Objects.isNull(driverId)) {
			return new Drivers();
		} else {
			return findDriverById(companyId, driverId);
		}
	}

	private void setDriverFields(Drivers driver,
			TruckCompanyDrivers truckCompanyDrivers) {
		driver.setDriverName(truckCompanyDrivers.getDriverName());
		driver.setDriverAddress(truckCompanyDrivers.getDriverAddress());
		driver.setDriverCity(truckCompanyDrivers.getDriverCity());
		driver.setDriverState(truckCompanyDrivers.getDriverState());
		driver.setDriverZip(truckCompanyDrivers.getDriverZip());
		driver.setDriverEmail(truckCompanyDrivers.getDriverEmail());
		driver.setDriverPhone(truckCompanyDrivers.getDriverPhone());
	}

	@Transactional(readOnly = false)
	public void deleteDriverById(Long companyId, Long driverId) {
		Drivers driver = findDriverById(companyId, driverId);
		// delete the driver from their assigned trucks
		driver.getTrucks()
				.forEach((truck) -> truck.getDrivers().remove(driver));
		// clear all trucks assigned to the driver
		driver.getTrucks().clear();
		// save all changes
		truckDriversDao.save(driver);
		truckDriversDao.delete(driver);
	}

	/*
	 * assign and unassign drivers and trucks
	 */

	@Transactional(readOnly = false)
	public void joinTrucksAndDrivers(Long driverId, Long truckId) {
		Truck truck = findTruckById(truckId);
		Drivers driver = findDriverById(driverId);

		driver.getTrucks().add(truck);
		truck.getDrivers().add(driver);
	}

	private Truck findTruckById(Long truckId) {
		Truck truck = truckTruckDao.findById(truckId)
				.orElseThrow(() -> new NoSuchElementException(
						"Truck with ID " + truckId + " Does not exist"));
		return truck;
	}

	private Drivers findDriverById(Long driverId) {
		Drivers driver = truckDriversDao.findById(driverId)
				.orElseThrow(() -> new NoSuchElementException(
						"Truck with ID " + driverId + " Does not exist"));
		return driver;
	}

	public void removeTruckFromDriver(Long truckId, Long driverId) {
		Truck truck = findTruckById(truckId);
		Drivers driver = findDriverById(driverId);

		// mark driver for deletion using .remove() from the truck entity
		truck.getDrivers().remove(driver);
		// mark truck for deletion using .remove() from the driver entity
		driver.getTrucks().remove(truck);

		// save truck and driver DTO
		truckTruckDao.save(truck);
		truckDriversDao.save(driver);

	}
}
