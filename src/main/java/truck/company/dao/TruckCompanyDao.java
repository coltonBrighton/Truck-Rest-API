package truck.company.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import truck.company.entity.Company;

public interface TruckCompanyDao extends JpaRepository<Company, Long> {
}
