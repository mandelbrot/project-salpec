package consumption.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="CONSUMPTION")
public class Consumption {

	private int id;
	private int id_user;
	private int id_consumption_group;
	private String name;
	private String ie_default;
	private String description;
	
	public Consumption() {
	}
	
	public Consumption(int id, int id_user, int id_consumption_group, String name, String ie_default, String description) {
		this.id = id;
		this.id_user = id_user;
		this.name = name;
		this.id_consumption_group = id_consumption_group;
		this.ie_default = ie_default;
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

	@Column(name="name", unique = true, nullable = false)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name="ie_default")
	public String getIeDefault() {
		return ie_default;
	}

	public void setIeDefault(String ie_default) {
		this.ie_default = ie_default;
	}

	@Column(name="description")
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Column(name="id_user", unique = true, nullable = false)
	public int getIdUser() {
		return id_user;
	}

	public void setIdUser(int id_user) {
		this.id_user = id_user;
	}	

	@Column(name="id_consumption_group")
	public int getIdConsumptionGroup() {
		return id_consumption_group;
	}

	public void setIdConsumptionGroup(int id_consumption_group) {
		this.id_consumption_group = id_consumption_group;
	}	
	
	@Override
	public String toString() {
		StringBuffer strBuff = new StringBuffer();
		strBuff.append("id : ").append(getId());
		strBuff.append(", id_user : ").append(getIdUser());
		strBuff.append(", name : ").append(getName());
		strBuff.append(", id_consumption_group : ").append(getIdConsumptionGroup());
		strBuff.append(", username : ").append(getIeDefault());
		strBuff.append(", description : ").append(getDescription());
		return strBuff.toString();
	}
}
