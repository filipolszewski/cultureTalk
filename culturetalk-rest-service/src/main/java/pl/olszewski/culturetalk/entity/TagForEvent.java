package pl.olszewski.culturetalk.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "tagforevent", schema = "culturetalk")
public class TagForEvent {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;

	@ManyToOne
	@JoinColumn(name = "id_event")
	private Event idEvent;

	@ManyToOne
	@JoinColumn(name = "id_tag")
	private Tag idTag;

	public TagForEvent() {
		// TODO Auto-generated constructor stub
	}

	public TagForEvent(Event idEvent, Tag idTag) {

		this.idEvent = idEvent;
		this.idTag = idTag;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Event getIdEvent() {
		return idEvent;
	}

	public void setIdEvent(Event idEvent) {
		this.idEvent = idEvent;
	}

	public Tag getIdTag() {
		return idTag;
	}

	public void setIdTag(Tag idTag) {
		this.idTag = idTag;
	}

}
