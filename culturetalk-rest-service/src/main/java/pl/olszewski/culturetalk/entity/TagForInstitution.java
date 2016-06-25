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
@Table(name = "tagforinstitution", schema = "culturetalk")
public class TagForInstitution {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;

	@ManyToOne
	@JoinColumn(name = "id_institution")
	private Institution idInstitution;

	@ManyToOne
	@JoinColumn(name = "id_tag")
	private Tag idTag;

	public TagForInstitution() {
		// TODO Auto-generated constructor stub
	}

	public TagForInstitution(Institution idInstitution, Tag idTag) {
		this.idInstitution = idInstitution;
		this.idTag = idTag;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Institution getIdInstitution() {
		return idInstitution;
	}

	public void setIdInstitution(Institution idInstitution) {
		this.idInstitution = idInstitution;
	}

	public Tag getIdTag() {
		return idTag;
	}

	public void setIdTag(Tag idTag) {
		this.idTag = idTag;
	}

}
