
package pl.olszewski.culturetalk.webservice.data;

import java.util.List;

public class StateRequest {

	private Integer lastMessageId;
	private List<Integer> myUsersIds;
	
	public StateRequest() {
		// TODO Auto-generated constructor stub
	}
	
	public StateRequest(Integer lastMessageId, List<Integer> myUsersIds) {
		super();
		this.lastMessageId = lastMessageId;
		this.myUsersIds = myUsersIds;
	}

	public Integer getLastMessageId() {
		return lastMessageId;
	}

	public void setLastMessageId(Integer lastMessageId) {
		this.lastMessageId = lastMessageId;
	}

	public List<Integer> getMyUsersIds() {
		return myUsersIds;
	}

	public void setMyUsersIds(List<Integer> myUsersIds) {
		this.myUsersIds = myUsersIds;
	}
}
