package in.na.main.controllers;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import in.na.main.entities.Employee;
import in.na.main.entities.EmployeeOrders;
import in.na.main.entities.Inquiry;
import in.na.main.entities.Orders;
import in.na.main.repositories.EmployeeOrdersRepository;
import in.na.main.repositories.EmployeeRepository;
import in.na.main.services.CourseService;
import in.na.main.services.EmployeeService;
import in.na.main.services.OrderService;

@Controller
@SessionAttributes("sessionEmp")
public class EmployeeController {

	@Autowired
	public EmployeeOrdersRepository employeeOrdersRepository;

	@Autowired
	public OrderService orderService;

	@Autowired
	private CourseService courseService;

	@Autowired
	private EmployeeRepository employeeRepository;

	@Autowired
	private EmployeeService employeeService;

	// handler method
	EmployeeController(EmployeeRepository employeeRepository) {
		this.employeeRepository = employeeRepository;
	}

	@GetMapping("/employeeLogin")
	public String openEmployeeLoginPage() {
		return "employee-login";
	}

	@PostMapping("/empLoginForm")
	public String employeeLoginForm(@RequestParam("emailId") String emailId, @RequestParam("password") String password,
			Model model) {

		boolean isAuthenticated = employeeService.loginEmpService(emailId, password);
		if (isAuthenticated) {
			Employee authenticatedEmp = employeeRepository.findByEmailId(emailId);
//			System.out.println("DEBUG" +authenticatedEmp);
			model.addAttribute("sessionEmp", authenticatedEmp);
			return "employee-profile";
		} else {
			model.addAttribute("errorMsg", "Incorrect email or password");
			return "employee-login";
		}
	}

	@GetMapping("/employeeProfile")
	public String openEmployeeProfilePage(Model model) {
		if (!model.containsAttribute("sessionEmp")) {
			return "redirect:/employeeLogin";
		}
		return "employee-profile";
	}

	@GetMapping("/employeeManagement")
	public String openEmployeeManagementPage(Model model, 
												@RequestParam(name = "page", defaultValue = "0") int page,
												@RequestParam(name = "size", defaultValue = "5") int size) {

		Pageable pageable = PageRequest.of(page, size);

		Page<Employee> employeePage = employeeService.getAllEmployeeDetailsByPagination(pageable);

		model.addAttribute("employeePage", employeePage);

		return "employee-management";
	}

	// -----------------Add Employee starts-----------------------
	@GetMapping("/addEmployee")
	public String openAddEmployeePage(Model model) {
		model.addAttribute("employee", new Employee());
		return "add-employee";

	}

	@PostMapping("/addEmployeeForm")
	public String addEmployeeForm(@ModelAttribute("employee") Employee employee,
			RedirectAttributes redirectAttributes) {

		try {
			// Manual validation for repeat password
			if (employee.getPassword() == null || employee.getRepeatPassword() == null
					|| !employee.getPassword().equals(employee.getRepeatPassword())) {
				redirectAttributes.addFlashAttribute("errorMsg", "Passwords do not match!");
				return "redirect:/addEmployee";
			}
			employeeService.addEmployee(employee);
			redirectAttributes.addFlashAttribute("successMsg", "Employee added successfully");
		} catch (Exception e) {
			redirectAttributes.addFlashAttribute("errorMsg", e.getMessage());
			e.printStackTrace();
		}
		return "redirect:/addEmployee";
	}
	// -----------------Add Employee ends-------------------------

	// ----------------Edit Employee starts-----------------------
	@GetMapping("/editEmployee")
	public String openEditEmployeePage(@RequestParam("employeeEmail") String employeeEmail, Model model) {

		Employee employee = employeeService.getEmployeeDetails(employeeEmail);

		model.addAttribute("employee", employee);
		model.addAttribute("newEmployeeObj", new Employee());

		return "edit-employee";
	}

	@PostMapping("/updateEmployeeDetailsForm")
	public String updateEmployeeDetailsForm(@ModelAttribute("newEmployeeObj") Employee newEmployeeObj,
			RedirectAttributes redirectAttributes) {
		try {
			Employee oldEmployeeObj = employeeService.getEmployeeDetails(newEmployeeObj.getEmailId());
			newEmployeeObj.setId(oldEmployeeObj.getId());

			employeeService.updateEmployeeDetails(newEmployeeObj);

			redirectAttributes.addFlashAttribute("successMsg", "Employee details updated successfully");
		} catch (Exception e) {
			redirectAttributes.addFlashAttribute("errorMsg", "Employee could not be updated due to some error");
			e.printStackTrace();
		}
		return "redirect:/employeeManagement";
	}
	// ----------------Edit Employee ends-------------------------

	// ----------------Delete Employee starts---------------------

	@GetMapping("/deleteEmployeeDetails")
	public String deleteEmployeeDetails(@RequestParam("employeeEmail") String employeeEmail,
			RedirectAttributes redirectAttributes) {

		try {
			employeeService.deleteEmployeeDetails(employeeEmail);
			redirectAttributes.addFlashAttribute("successMsg", "Employee deleted successfully");

		} catch (Exception e) {
			redirectAttributes.addFlashAttribute("errorMsg",
					"Employee could not be deleted due to some error, please try again later");
			e.printStackTrace();
		}

		return "redirect:/employeeManagement";
	}
	// ----------------Delete Employee ends-----------------------

	// ------------open sell course page-------------------------
	@GetMapping("/sellCourse")
	public String openSellCoursePage(Model model) {
		List<String> courseNameList = courseService.getAllCourseNames();
		model.addAttribute("courseNameList", courseNameList);

		String uuidOrderId = UUID.randomUUID().toString();
		model.addAttribute("uuidOrderId", uuidOrderId);

		model.addAttribute("orders", new Orders());
		return "sell-course";
	}

	@PostMapping("/sellCourseForm")
	public String sellCourseForm(@ModelAttribute("orders") Orders orders, RedirectAttributes redirectAttributes,
			@SessionAttribute("sessionEmp") Employee sessionEmp) {

		LocalDate ld = LocalDate.now();
		String pdate = ld.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));

		LocalTime lt = LocalTime.now();
		String ptime = lt.format(DateTimeFormatter.ofPattern("hh:mm:ss a"));

		String purchases_date_time = pdate + ", " + ptime;

		orders.setDateOfPurchase(purchases_date_time);

		try {
			orderService.storeUserOrders(orders);

			EmployeeOrders employeeOrders = new EmployeeOrders();
			employeeOrders.setOrderId(orders.getOrderId());
			employeeOrders.setEmployeeEmail(sessionEmp.getEmailId());

			employeeOrdersRepository.save(employeeOrders);

			redirectAttributes.addFlashAttribute("successMsg", "Course provided successfully");

		} catch (Exception e) {
			e.printStackTrace();
			redirectAttributes.addFlashAttribute("errorMsg", "Course not provided due to some error");
		}
		return "redirect:/sellCourse";
	}

	// ----------------inquiry management-------------------------
	@GetMapping("/inquiryManagement")
	public String openInquiryManagementPage(Model model) {

		model.addAttribute("inquiry", new Inquiry());
		return "inquiry-management";
	}

	// ----------------employee Logout----------------------------
	@GetMapping("/employeeLogout")
	public String employeeLogout(SessionStatus sessionStatus) {
		sessionStatus.setComplete();
		return "employee-login";
	}

}
