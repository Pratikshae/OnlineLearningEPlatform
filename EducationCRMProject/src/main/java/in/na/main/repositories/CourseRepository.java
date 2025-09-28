package in.na.main.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import in.na.main.entities.Course;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {

	// repository layer
	Course findByCourseName(String courseName);

}
