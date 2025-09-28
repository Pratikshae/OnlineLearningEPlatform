package in.na.main.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import in.na.main.entities.Course;
import in.na.main.entities.User;
import in.na.main.repositories.OrdersRepository;
import in.na.main.repositories.UserRepository;
import in.na.main.services.CourseService;
import in.na.main.services.UserService;
import in.sp.main.dto.PurchasedCourse;
import jakarta.validation.Valid;

@Controller
@SessionAttributes("sessionUser")
public class UserController {

	@Autowired
	private UserService userService;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private CourseService courseService;

	@Autowired
	private OrdersRepository ordersRepository;

	// handler methods
	@GetMapping({ "/", "/index" })
	public String openIndexPage(Model model,
			@SessionAttribute(name = "sessionUser", required = false) User sessionUser) {
		// print all course
		List<Course> course_list = courseService.getAllCourseDetails();
		model.addAttribute("courseList", course_list);

		if (sessionUser != null) {
			List<Object[]> purchasedCourseList = ordersRepository.findPurchasedCoursesByEmail(sessionUser.getEmail());
//			System.out.println(purchasedCourseList.get(0));

			// iterate over all the course and get the courses name
			List<String> purchasedCourseNameList = new ArrayList<>();
			for (Object[] course : purchasedCourseList) {
				String courseName = (String) course[0];
				purchasedCourseNameList.add(courseName);
			}

			model.addAttribute("purchasedCourseNameList", purchasedCourseNameList);
		}

		return "index";
	}

	// ---------Register operation starts here -----------

	@GetMapping("/register")
	public String openRegisterPage(Model model) {
		model.addAttribute("user", new User());
		return "register";
	}

	@PostMapping("/regForm")
	public String handleRegForm(@Valid @ModelAttribute("user") User user, BindingResult bindingResult, Model model) {

		// 1. Check if password meets pattern validation
		if (bindingResult.hasErrors()) {
			return "register"; // send back to form with validation messages
		}

		// 2. Check if password == repeatPassword
		if (!user.getPassword().equals(user.getRepeatPassword())) {
			bindingResult.rejectValue("repeatPassword", "error.user", "Passwords do not match");
			return "register";
		}

		// 3. If valid, proceed with saving
		try {
			userService.registerUserService(user);
			model.addAttribute("successMsg", "Register Successfully");
			return "register";

		} catch (Exception e) {

			e.printStackTrace();
			model.addAttribute("errorMsg", "Something went wrong, could not register user, please try later");
			return "error";
		}

	}
	// ---------Register operation ends here ----------------

	// -------------Login operation starts here--------------
	@GetMapping("/login")
	public String openLoginPage(Model model) {
		model.addAttribute("user", new User());
		return "login";
	}

	@PostMapping("/loginForm")
	public String handleLoginForm(@ModelAttribute("user") User user, Model model) {
		boolean isAuthenticated = userService.loginUserService(user.getEmail(), user.getPassword());
		if (isAuthenticated) {

			User authenticateUser = userRepository.findByEmail(user.getEmail());
			model.addAttribute("sessionUser", authenticateUser);
			return "user-profile";
		} else {

			model.addAttribute("errorMsg", "Incorrect email id or password");
			return "login";
		}
	}
	// ------------Login operation ends here--------------

	// ----------logout operation starts here ---------------

	@GetMapping("/logout")
	public String logout(SessionStatus sessionStatus) {
		sessionStatus.setComplete();
		return "login";
	}

	// ----------logout operation ends here -----------------

	// ---------My-Course page-------------
	@GetMapping("/myCourses")
	public String myCoursesPage(@SessionAttribute("sessionUser") User sessionUser, Model model) {
		List<Object[]> purchasedCourseDbList = ordersRepository.findPurchasedCoursesByEmail(sessionUser.getEmail());

		List<PurchasedCourse> purchasedCourseList = new ArrayList<>();

		for (Object[] course : purchasedCourseDbList) {

//			System.out.println("-----------------------"); 
//			System.out.println(course[0]);
//			System.out.println(course[1]);
//			System.out.println(course[2]);
//			System.out.println(course[3]);

			PurchasedCourse purchasedCourse = new PurchasedCourse();
			purchasedCourse.setCourseName((String) course[0]);
			purchasedCourse.setDescription((String) course[1]);
			purchasedCourse.setImageUrl((String) course[2]);
			purchasedCourse.setUpdatedOn((String) course[3]);
			purchasedCourse.setPurchasedOn((String) course[4]);

			purchasedCourseList.add(purchasedCourse);

		}

		model.addAttribute("purchasedCourseList", purchasedCourseList);

		return "my-courses";
	}

	// ---------Student-Profile page-----------
	@GetMapping("/userProfile")
	public String openUserProfilePage() {
		return "user-profile";
	}
}
