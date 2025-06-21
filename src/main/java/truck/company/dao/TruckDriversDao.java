package truck.company.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import truck.company.entity.Drivers;

public interface TruckDriversDao extends JpaRepository<Drivers, Long> {

}
