package pl.olszewski.culturetalk.webservice.data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import pl.olszewski.culturetalk.entity.Event;
import pl.olszewski.culturetalk.entity.TagForEvent;

public class NewEventData {

	private String name;

	private Date date;

	private Integer institution;

	private Integer province;

	private String address;

	private String ticketLink;
	
	private List<Integer> tags = new ArrayList<>();

	private String description;

	public NewEventData() {
		// TODO Auto-generated constructor stub
	}

	public NewEventData(Event event) {
		this.name = event.getName();
		this.date = event.getDate();
		this.institution = event.getInstitution().getId();
		this.province = event.getProvince().getIdProvince();
		this.address = event.getAddress();
		this.ticketLink = event.getTicketLink();
		for (TagForEvent tfe : event.getTfe()) {
			tags.add(tfe.getIdTag().getId());
		}
		this.description = event.getDescription();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Integer getInstitution() {
		return institution;
	}

	public void setInstitution(Integer institution) {
		this.institution = institution;
	}

	public Integer getProvince() {
		return province;
	}

	public void setProvince(Integer province) {
		this.province = province;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getTicketLink() {
		return ticketLink;
	}

	public void setTicketLink(String ticketLink) {
		this.ticketLink = ticketLink;
	}

	public List<Integer> getTags() {
		return tags;
	}

	public void setTags(List<Integer> tags) {
		this.tags = tags;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
