package in.na.main.entities;

public class Admin {
	private String adminEmail;
	private String adminName;
	
	
	public Admin(String adminEmail, String adminName) {
		this.adminEmail = adminEmail;
		this.adminName = adminName;
	}


	public String getAdminEmail() {
		return adminEmail;
	}


	public String getAdminName() {
		return adminName;
	}
	

}
