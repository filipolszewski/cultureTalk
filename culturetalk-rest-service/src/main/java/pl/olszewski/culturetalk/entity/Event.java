package pl.olszewski.culturetalk.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import pl.olszewski.culturetalk.webservice.data.NewEventData;

@Entity
@Table(name = "event", schema = "culturetalk")
public class Event {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_event")
	private Integer id;

	private String name;

	private Date date;

	@ManyToOne
	@JoinColumn(name = "id_institution")
	private Institution institution;

	@ManyToOne
	@JoinColumn(name = "id_province")
	private Province province;

	private String address;

	@Column(name = "ticketlink")
	private String ticketLink;

	private String description;

	@OneToMany
	@JoinTable(name = "tagforevent", joinColumns = @JoinColumn(name = "id_event"), inverseJoinColumns = @JoinColumn(name = "id"))
	private List<TagForEvent> tags;

	public Event() {
	};

	public Event(NewEventData data) {
		super();
		name = data.getName();
		date = data.getDate();
		institution = new Institution(data.getInstitution());
		province = new Province(data.getProvince());
		address = data.getAddress();
		ticketLink = data.getTicketLink();
		description = data.getDescription();
	}

	public Event(NewEventData data, Integer id) {
		super();
		this.id = id;
		name = data.getName();
		date = data.getDate();
		institution = new Institution(data.getInstitution());
		province = new Province(data.getProvince());
		address = data.getAddress();
		ticketLink = data.getTicketLink();
		description = data.getDescription();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
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

	public Institution getInstitution() {
		return institution;
	}

	public void setInstitution(Institution institution) {
		this.institution = institution;
	}

	public Province getProvince() {
		return province;
	}

	public void setProvince(Province province) {
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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<TagForEvent> getTfe() {
		return tags;
	}

	public void setTfe(List<TagForEvent> tfe) {
		this.tags = tfe;
	}

}
