package in.na.main.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import in.na.main.entities.Employee;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

	Employee findByEmailId(String employeeEmail);

	boolean existsByEmailId(String emailId);
}
