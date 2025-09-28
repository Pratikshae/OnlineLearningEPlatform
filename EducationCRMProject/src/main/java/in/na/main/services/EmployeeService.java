package in.na.main.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import in.na.main.entities.Employee;
import in.na.main.repositories.EmployeeRepository;

@Service
public class EmployeeService {

	@Autowired
	private EmployeeRepository employeeRepository;

	public boolean loginEmpService(String email, String password) {
		Employee employee = employeeRepository.findByEmailId(email);
		if (employee != null) {
			return password.equals(employee.getPassword());
		}
		return false;
	}

	public Page<Employee> getAllEmployeeDetailsByPagination(Pageable pageable) {

		return employeeRepository.findAll(pageable);

	}

	public String addEmployee(Employee employee) {
		if (employeeRepository.existsByEmailId(employee.getEmailId())) {
			throw new IllegalArgumentException("Email already exist!");
		}

		employeeRepository.save(employee);
		return "Employee added successfully";
	}

	public Employee getEmployeeDetails(String employeeEmail) {

		return employeeRepository.findByEmailId(employeeEmail);
	}

	public void updateEmployeeDetails(Employee employee) {

		employeeRepository.save(employee);
	}

	public void deleteEmployeeDetails(String employeeEmail) {
		Employee employee = employeeRepository.findByEmailId(employeeEmail);
		if (employee != null) {
			employeeRepository.delete(employee);
		} else {
			throw new RuntimeException("Employee not found with email Id:" + employeeEmail);
		}
	}

}
