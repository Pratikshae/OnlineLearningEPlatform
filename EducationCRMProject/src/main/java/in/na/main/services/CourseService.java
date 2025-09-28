package in.na.main.services;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import in.na.main.entities.Course;
import in.na.main.repositories.CourseRepository;

@Service
public class CourseService {

	private static final String UPLOAD_DIR = "src/main/resources/static/uploads/";

	private static final String IMAGE_URL = "http://localhost:8080/uploads/";

	@Autowired
	private CourseRepository courseRepository;

	// retrieve list of all courses
	public List<Course> getAllCourseDetails() {
		return courseRepository.findAll();
	}

	// Pageable->(Interface) is used to specify information i.e page number, page
	// size, sorting order etc when quering with databases.
	// Page ->(Interface) represents the chunk of data that is fetched according to
	// pagination parameters defined by Pageable.
	public Page<Course> getAllCourseDetailsByPagination(Pageable pageable) {

		return courseRepository.findAll(pageable);
	}

	public void addCourse(Course course, MultipartFile courseImg) throws IOException {

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
			course.setImageURL(imgUrl);
		}

		// Save course in DB
		courseRepository.save(course);
	}

	public Course getCourseDetails(String courseName) {

		return courseRepository.findByCourseName(courseName);
	}

	public void updateCourseDetails(Course course) {

		// if old courseobj's id and new courseobj's id is same, then it will update and
		// save. else it will insert a new course object in the database.
		courseRepository.save(course);
	}

	public void deleteCourseDetails(String courseName) {

		Course course = courseRepository.findByCourseName(courseName);

		if (course != null) {
			courseRepository.delete(course);
		} else {
			throw new RuntimeException("Course not found with name: " + courseName);
		}
	}

	public List<String> getAllCourseNames() {
		List<Course> coursesList = courseRepository.findAll();
		List<String> courseNameList = new ArrayList<>();

		for (Course course : coursesList) {
//			System.out.println(course.getCourseName());
			courseNameList.add(course.getCourseName());
		}
		return courseNameList;
	}
}
