
package pl.olszewski.culturetalk.webservice.data;

public class MessageData {

	private Integer id;
	private UserData toUser;
	private String message;
	private String dateSent;

	public MessageData() {
		// TODO Auto-generated constructor stub
	}

	public MessageData(Integer id, UserData toUser, String message, String dateSent) {
		this.id = id;
		this.toUser = toUser;
		this.message = message;
		this.dateSent = dateSent;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public UserData getToUser() {
		return toUser;
	}

	public void setToUser(UserData fromUser) {
		this.toUser = fromUser;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getDateSent() {
		return dateSent;
	}

	public void setDateSent(String dateSent) {
		this.dateSent = dateSent;
	}
}
