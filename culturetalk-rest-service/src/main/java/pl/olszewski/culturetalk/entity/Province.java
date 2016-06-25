package pl.olszewski.culturetalk.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "province", schema = "culturetalk")
public class Province {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_province")
	private Integer idProvince;
	
	private String name;

	public Province() {}
	
	public Province(Integer idProvince) {
		this.idProvince = idProvince;
	}
	
	public Integer getIdProvince() {
		return idProvince;
	}

	public void setIdProvince(Integer idProvince) {
		this.idProvince = idProvince;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
}
