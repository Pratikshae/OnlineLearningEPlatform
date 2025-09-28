package in.na.main.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import in.na.main.entities.EmployeeOrders;

@Repository
public interface EmployeeOrdersRepository extends JpaRepository<EmployeeOrders, Long> {

}
