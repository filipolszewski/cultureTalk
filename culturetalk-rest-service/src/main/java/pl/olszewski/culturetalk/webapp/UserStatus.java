
package pl.olszewski.culturetalk.webapp;

public class UserStatus {

	private Integer userId;
	private boolean active;
	
	public UserStatus() {
		// TODO Auto-generated constructor stub
	}
	
	public UserStatus(Integer userId, boolean active) {
		super();
		this.userId = userId;
		this.active = active;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}
	
	

}
