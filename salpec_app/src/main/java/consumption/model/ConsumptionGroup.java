package consumption.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="CONSUMPTION_GROUP")
public class ConsumptionGroup {
	
	private int id;
	private int idUser;
	private String name;
	private String ieDefault;
	private String description;
	
	public ConsumptionGroup() {
	}
	
	public ConsumptionGroup(int id, int idUser, String name, String ieDefault, String description) {
		this.id = id;
		this.idUser = idUser;
		this.name = name;
		this.ieDefault = ieDefault;
		this.description = description;
	}

	@Id
	@Column(name="ID", unique = true, nullable = false)
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Column(name="id_user", unique = true, nullable = false)
	public int getIdUser() {
		return idUser;
	}

	public void setIdUser(int idUser) {
		this.idUser = idUser;
	}

	@Column(name="name", unique = true, nullable = false)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name="ie_default", unique = true, nullable = false)
	public String getIeDefault() {
		return ieDefault;
	}

	public void setIeDefault(String ieDefault) {
		this.ieDefault = ieDefault;
	}

	@Column(name="description", unique = true, nullable = false)
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public String toString() {
		StringBuffer strBuff = new StringBuffer();
		strBuff.append("id : ").append(getId());
		strBuff.append("idUser : ").append(getIdUser());
		strBuff.append(", consumption name : ").append(getName());
		strBuff.append(", ieDefault : ").append(getIeDefault());
		strBuff.append(", description : ").append(getDescription());
		return strBuff.toString();
	}
}
