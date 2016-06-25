package pl.olszewski.culturetalk.webservice.data;

import java.util.ArrayList;

public class EventCriteria {

	Integer idProvince;
	
	ArrayList<Integer> tagList;

	Integer maxResults;

	public Integer getIdProvince() {
		return idProvince;
	}

	public void setIdProvince(Integer idProvince) {
		this.idProvince = idProvince;
	}

	public ArrayList<Integer> getTagList() {
		return tagList;
	}

	public void setTagList(ArrayList<Integer> tagList) {
		this.tagList = tagList;
	}

	public Integer getMaxResults() {
		return maxResults;
	}

	public void setMaxResults(Integer maxResults) {
		this.maxResults = maxResults;
	}
	
}
