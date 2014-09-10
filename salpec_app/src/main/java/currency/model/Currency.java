package currency.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="CURRENCY")
public class Currency {

	private int id;
	private String name;
	private String code;
	private String code2;
	
	@Id
	@Column(name="ID", unique = true, nullable = false)
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public Currency(){
	}
	
	public Currency(int id, String name, String id_user, String code, String code2) {
		this.id = id;
		this.name = name;
		this.code = code;
		this.code2 = code2;
	}

	@Column(name="name", unique = true, nullable = false)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name="code", unique = true, nullable = false)
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	@Column(name="id_user", unique = true, nullable = false)
	public String getCode2() {
		return code2;
	}

	public void setCode2(String code2) {
		this.code2 = code2;
	}	

	@Override
	public String toString() {
		StringBuffer strBuff = new StringBuffer();
		strBuff.append("id : ").append(getId());
		strBuff.append(", name : ").append(getName());
		strBuff.append(", code : ").append(getCode());
		strBuff.append(", code2 : ").append(getCode2());
		return strBuff.toString();
	}
}
