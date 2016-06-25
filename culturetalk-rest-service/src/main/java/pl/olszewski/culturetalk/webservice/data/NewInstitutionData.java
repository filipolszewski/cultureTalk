package pl.olszewski.culturetalk.webservice.data;

import java.util.ArrayList;
import java.util.List;

import pl.olszewski.culturetalk.entity.Institution;
import pl.olszewski.culturetalk.entity.TagForInstitution;

public class NewInstitutionData {

	private Integer id;

	private String name;
	
	private String password;

	private Integer province;
	
	private String address;
	
	private String number;
	
	private String email;
	
	private String page;
	
	private String facebook;
	
	private String description;
	
	private List<Integer> tags = new ArrayList<>();

	public NewInstitutionData() {
		// TODO Auto-generated constructor stub
	}

	public NewInstitutionData(Institution i) {
		id = i.getId();
		name = i.getName();
		password = i.getPassword();
		province = i.getProvince().getIdProvince();
		address = i.getAddress();
		number = i.getNumber();
		email = i.getEmail();
		page = i.getPage();
		facebook = i.getFacebook();
		description = i.getDescription();
		for (TagForInstitution tfi : i.getTfi()) {
			tags.add(tfi.getIdTag().getId());
		}
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

	public List<Integer> getTags() {
		return tags;
	}

	public void setTags(List<Integer> tags) {
		this.tags = tags;
	}

}
