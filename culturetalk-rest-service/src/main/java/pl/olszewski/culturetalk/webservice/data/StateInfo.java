
package pl.olszewski.culturetalk.webservice.data;

import java.util.List;

import pl.olszewski.culturetalk.webapp.UserStatus;

public class StateInfo {

	private List<MessageData> newMessages;
	private List<UserStatus> userStatuses;

	public StateInfo() {
		// TODO Auto-generated constructor stub
	}
	
	public StateInfo(List<MessageData> newMessages, List<UserStatus> userStatuses) {
		super();
		this.newMessages = newMessages;
		this.userStatuses = userStatuses;
	}

	public List<MessageData> getNewMessages() {
		return newMessages;
	}

	public void setNewMessages(List<MessageData> newMessages) {
		this.newMessages = newMessages;
	}

	public List<UserStatus> getUserStatuses() {
		return userStatuses;
	}

	public void setUserStatuses(List<UserStatus> userStatuses) {
		this.userStatuses = userStatuses;
	}
	
	
}
