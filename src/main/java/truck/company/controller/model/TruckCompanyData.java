package truck.company.controller.model;

import java.util.HashSet;
import java.util.Set;

import lombok.Data;
import lombok.NoArgsConstructor;
import truck.company.entity.Company;
import truck.company.entity.Drivers;
import truck.company.entity.Truck;

@Data
@NoArgsConstructor
public class TruckCompanyData {
  private Long companyId;
  
  private String companyName;
  private String companyAddress;
  private String companyCity;
  private String companyState;
  private String companyZip;
  private String companyPhone;
  
  private Set<TruckCompanyDrivers> drivers = new HashSet<>();
  private Set<TruckCompanyTruck> trucks = new HashSet<>();
  
  public TruckCompanyData(Company company) {
    companyId = company.getCompanyId();
    companyName = company.getName();
    companyAddress = company.getAddress();
    companyCity = company.getCity();
    companyState = company.getState();
    companyZip = company.getZip();
    companyPhone = company.getPhone();
    
    // for truck in trucks add a new truck
    for (Truck truck : company.getTrucks()) {
      trucks.add(new TruckCompanyTruck(truck));
    }
    
    // for driver in drivers add a new driver
    for (Drivers driver : company.getDrivers()) {
      drivers.add(new TruckCompanyDrivers(driver));
    }
  }
  
}
