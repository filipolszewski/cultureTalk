package pl.olszewski.culturetalk.entity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "institution", schema = "culturetalk")
public class Institution {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;
	
	private String name;
	
	private String password;
	
	@ManyToOne
	@JoinColumn(name = "id_province")
	private Province province;
	
	private String address;
	
	private String number;
	
	private String email;
	
	private String page;
	
	private String facebook;
	
	private String description;

	@OneToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "tagforinstitution", joinColumns = @JoinColumn(name = "id_institution"), inverseJoinColumns = @JoinColumn(name = "id"))
	private List<TagForInstitution> tfi;

	public Institution() {}
	
	public Institution(Integer id) {
		
		this.id = id;
	}
	

	
	public Institution(String name, String password, Province province, String address, String number, String email,
			String page, String facebook, String description) {
		super();
		this.name = name;
		this.password = password;
		this.province = province;
		this.address = address;
		this.number = number;
		this.email = email;
		this.page = page;
		this.facebook = facebook;
		this.description = description;
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

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
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

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPage() {
		return page;
	}

	public void setPage(String page) {
		this.page = page;
	}

	public String getFacebook() {
		return facebook;
	}

	public void setFacebook(String facebook) {
		this.facebook = facebook;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<TagForInstitution> getTfi() {
		return tfi;
	}

	public void setTfi(List<TagForInstitution> tags) {
		this.tfi = tags;
	}
	
}
