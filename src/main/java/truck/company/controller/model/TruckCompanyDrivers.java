package truck.company.controller.model;

import java.util.HashSet;
import java.util.Set;

import lombok.Data;
import lombok.NoArgsConstructor;
import truck.company.entity.Drivers;

@Data
@NoArgsConstructor
public class TruckCompanyDrivers {
  
  private Long driverId;
  private String driverName;
  private String driverAddress;
  private String driverCity;
  private String driverState;
  private String driverZip;
  private String driverEmail;
  private String driverPhone;
  
  private Set<TruckCompanyTruck> trucks = new HashSet<>();

  public TruckCompanyDrivers(Drivers driver) {
    driverId = driver.getDriverId();
    driverName = driver.getDriverName();
    driverAddress = driver.getDriverAddress();
    driverCity = driver.getDriverCity();
    driverState = driver.getDriverState();
    driverZip = driver.getDriverZip();
    driverEmail = driver.getDriverEmail();
    driverPhone = driver.getDriverPhone();
  }

  


}
