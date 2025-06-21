package truck.company.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import truck.company.entity.Truck;

public interface TruckTruckDao extends JpaRepository<Truck, Long> {

}
