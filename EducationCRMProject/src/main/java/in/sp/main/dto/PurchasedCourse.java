package in.sp.main.dto;

public class PurchasedCourse {

	private String purchasedOn;
	private String description;
	private String imageUrl;
	private String courseName;
	private String updatedOn;

	public String getPurchasedOn() {
		return purchasedOn;
	}

	public void setPurchasedOn(String purchasedOn) {
		this.purchasedOn = purchasedOn;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public String getCourseName() {
		return courseName;
	}

	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}

	public String getUpdatedOn() {
		return updatedOn;
	}

	public void setUpdatedOn(String updatedOn) {
		this.updatedOn = updatedOn;
	}

}
