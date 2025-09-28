package in.na.main.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Pattern;

@Entity
public class User {

	// data members & properties
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column
	@Pattern(regexp = "^[A-Z][a-zA-Z]+ [A-Z][a-zA-Z]+$", message = "Enter Firstname and Surname(e.g, John Doe).")
	private String name;

	@Column
	@Pattern(regexp = "^[^ ]+@[^ ]+\\.[a-z]{2,3}$", message = "Enter a valid email.")
	private String email;

	@Column
	@Pattern(regexp = "^.{6,}$", message = "Password must be at least 6 characters.")
	private String password;

	@Column
	private String repeatPassword;

	@Column
	@Pattern(regexp = "^[0-9]{10}$", message = "Enter a valid 10-digit phone number.")
	private String phoneno;

	@Column
	@Pattern(regexp = "^[A-Za-z\\s]{3,}$", message = "Enter a valid city name.")
	private String city;
	
	@Column
	private boolean banStatus;

	// getters and setters method
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
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

	public boolean isBanStatus() {
		return banStatus;
	}

	public void setBanStatus(boolean banStatus) {
		this.banStatus = banStatus;
	}

}
