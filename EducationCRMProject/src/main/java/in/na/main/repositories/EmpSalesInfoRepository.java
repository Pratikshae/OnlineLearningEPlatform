package in.na.main.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import in.na.main.entities.EmployeeOrders;

@Repository
public interface EmpSalesInfoRepository extends JpaRepository<EmployeeOrders, Long>{

	String SQL_QUERY1 = "SELECT SUM(course_amount) AS total_sum_of_courses_amount FROM orders WHERE order_id NOT LIKE 'order_%';";
	
	@Query(value = SQL_QUERY1, nativeQuery = true)
	String findTotalSalesByAllEmpployees();
	
	String SQL_QUERY2 = "SELECT e.employee_name AS employee_name, e.email_id AS empployee_email_id, e.phoneno AS employee_phoneno, SUM(o.course_amount) AS total_sale FROM employee e JOIN employee_orders eo ON e.email_id = eo.employee_email JOIN orders o ON eo.order_id= o.order_id GROUP BY e.employee_name, e.email_id, e.phoneno;";
	
	@Query(value = SQL_QUERY2, nativeQuery = true)
	List<Object[]> findTotalSalesByEachEmployee();
}
