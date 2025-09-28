package in.na.main.controllers;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import in.na.main.entities.Course;
import in.na.main.services.CourseService;
import jakarta.validation.Valid;

@Controller
public class CourseController {

	private static final String UPLOAD_DIR = "src/main/resources/static/uploads/";

	private static final String IMAGE_URL = "http://localhost:8080/uploads/";

	@Autowired
	private CourseService courseService;

	// handler methods

	// page ->means how many pages will be formed out of total courses according to
	// the size
	// size ->means how many rows will be generated.
	@GetMapping("/courseManagement")
	public String openCourseManagementPage(Model model, @RequestParam(name = "page", defaultValue = "0") int page,
			@RequestParam(name = "size", defaultValue = "5") int size) {

//			if(size < 1) size = 5;
		Pageable pageable = PageRequest.of(page, size);

		// retrieve course from database and reflect them here.
		Page<Course> coursesPage = courseService.getAllCourseDetailsByPagination(pageable);
		model.addAttribute("coursesPage", coursesPage);

		return "course-management";
	}

	// -------Add Course starts------------
	@GetMapping("/addCourse")
	public String openAddCoursePage(Model model) {
		model.addAttribute("course", new Course());
		return "add-course";
	}

	@PostMapping("/addCourseForm")
	public String addCourseForm(@Valid @ModelAttribute("course") Course course, BindingResult bindingResult,
			@RequestParam("courseImg") MultipartFile courseImg, RedirectAttributes redirectAttributes)
			throws IOException {

		try {

			if (bindingResult.hasErrors()) {
				return "add-course";
			}

			double original = Double.parseDouble(course.getOriginalPrice());
			double discounted = Double.parseDouble(course.getDiscountedPrice());

			if (discounted > original) {
				bindingResult.rejectValue("discountedPrice", "error.course",
						"Discounted price cannot exceed original price");
				return "add-course";
			}

			courseService.addCourse(course, courseImg);
			redirectAttributes.addFlashAttribute("successMsg", "Course added successfully!!");

		} catch (Exception e) {
			e.printStackTrace();
			redirectAttributes.addFlashAttribute("errorMsg", "Course not added due to some error.");
		}
		return "redirect:/addCourse";
	}
	// ------------Add Course ends-------------

	// -----------Edit Course starts-----------

	@GetMapping("/editCourse")
	public String openEditCoursePage(@RequestParam("courseName") String courseName, Model model) {
		Course course = courseService.getCourseDetails(courseName);

		model.addAttribute("course", course);
		model.addAttribute("newCourseObj", new Course());
		return "edit-course";

	}

	@PostMapping("/updateCourseDetailsForm")
	public String updateCourseDetailsForm(@ModelAttribute("course") Course newCourseObj,
			@RequestParam("courseImg") MultipartFile courseImg, RedirectAttributes redirectAttributes)
			throws IOException {

		try {
			Course oldCourseObj = courseService.getCourseDetails(newCourseObj.getCourseName());
			newCourseObj.setId(oldCourseObj.getId());

			if (courseImg != null && !courseImg.isEmpty()) {
				// Create folder if not exists
				Files.createDirectories(Paths.get(UPLOAD_DIR));

				// Extract original filename
				String imgName = courseImg.getOriginalFilename();

				// Save the file inside uploads folder
				Path imgPath = Paths.get(UPLOAD_DIR, imgName);
				Files.write(imgPath, courseImg.getBytes());

				// Build public URL
				String imgUrl = IMAGE_URL + imgName;
				newCourseObj.setImageURL(imgUrl);

			} else {

				newCourseObj.setImageURL(oldCourseObj.getImageURL());
			}

			courseService.updateCourseDetails(newCourseObj);

			redirectAttributes.addFlashAttribute("successMsg", "Course details updated successfully");

		} catch (Exception e) {
			redirectAttributes.addFlashAttribute("errorMsg",
					"Course details could not be updated due to some issue, please try later");
			e.printStackTrace();
		}
		return "redirect:/courseManagement";
	}

	// -----------Edit Course ends-------------

	// ------------Delete Course starts---------
	@GetMapping("/deleteCourseDetails")
	public String deleteCourseDetails(@RequestParam("courseName") String courseName,
			RedirectAttributes redirectAttributes) {
		try {
			courseService.deleteCourseDetails(courseName);
			redirectAttributes.addFlashAttribute("successMsg", "Course details deleted successfully");

		} catch (Exception e) {
			redirectAttributes.addFlashAttribute("errorMsg",
					"Course details could not be deleted due to some issue, please try later");
			e.printStackTrace();
		}

		return "redirect:/courseManagement";
	}
	// ------------Delete Course ends-----------
}
