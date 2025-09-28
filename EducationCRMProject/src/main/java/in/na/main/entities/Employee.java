package in.na.main.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@Entity
public class Employee {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column
	@NotBlank(message = "Employee name is required")
	@Size(min = 3, message = "Employee name must be at least 3 characters long")
	private String employeeName;

	@Column(nullable = false, unique = true)
	@NotBlank(message = "Email is required")
	private String emailId;

	@Column(nullable = false)
	@NotBlank(message = "Password is required")
	@Size(min = 6, message = "Password must be at least 6 characters")
	private String password;

	@Transient
	private String repeatPassword;

	@Column
	@NotBlank(message = "Phone number is required")
	@Pattern(regexp = "^[0-9]{10}$", message = "Phone number must be 10 digits")
	private String phoneno;

	@Column(nullable = false)
	@NotBlank(message = "City is required")
	private String city;

	// getter and setter methods
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getEmployeeName() {
		return employeeName;
	}

	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRepeatPassword() {
		return repeatPassword;
	}

	public void setRepeatPassword(String repeatPassword) {
		this.repeatPassword = repeatPassword;
	}

	public String getPhoneno() {
		return phoneno;
	}

	public void setPhoneno(String phoneno) {
		this.phoneno = phoneno;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	@Override
	public String toString() {
		return "Employee{" + "employeeName='" + employeeName + '\'' + ",emailId='" + emailId + '\'' + ",phoneno='"
				+ phoneno + '\'' + ",city='" + city + '\'' + '}';
	}
}
