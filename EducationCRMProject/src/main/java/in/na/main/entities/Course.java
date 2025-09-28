package in.na.main.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@Entity
public class Course {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@Column
	@NotBlank(message = "Course name is required")
	@Size(min = 3, message = "Course name must be at least 3 characters long")
	private String courseName;

	@Column
	@NotBlank(message = "Description is required")
	@Size(min = 10, message = "Description must be at least 10 characters long")
	private String description;

	@Column
	@NotBlank(message = "Original price is required")
	@Pattern(regexp = "^[0-9]+(\\.[0-9]{1,2})?$", message = "Enter a valid price (e.g 100 or 100.50")
	private String originalPrice;

	@Column
	@NotBlank(message = "Discounted price is required")
	@Pattern(regexp = "^[0-9]+(\\.[0-9]{1,2})?$", message = "Enter a valid price (e.g 50 or 50.75")
	private String discountedPrice;

	@Column
	@NotBlank(message = "Updated on is required")
	@Pattern(regexp = "^\\d+\\s+(day|days|week|weeks|month|months|year|years)\\s+ago$", message = "Updated On must be like '1 week ago', '10 moths ago', 2 days ago'")
	private String updatedOn;

	@Column
	private String imageURL;

	// getter and setter methods
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getCourseName() {
		return courseName;
	}

	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getOriginalPrice() {
		return originalPrice;
	}

	public void setOriginalPrice(String originalPrice) {
		this.originalPrice = originalPrice;
	}

	public String getDiscountedPrice() {
		return discountedPrice;
	}

	public void setDiscountedPrice(String discountedPrice) {
		this.discountedPrice = discountedPrice;
	}

	public String getUpdatedOn() {
		return updatedOn;
	}

	public void setUpdatedOn(String updatedOn) {
		this.updatedOn = updatedOn;
	}

	public String getImageURL() {
		return imageURL;
	}

	public void setImageURL(String imageURL) {
		this.imageURL = imageURL;
	}

}
