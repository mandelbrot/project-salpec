package user.model;

import java.util.Date;

public class DataScrollModel {

	private Date date;
	private double value;
	private String consumption;
	private String ie;
	private int id;
	private int id_consumption;
	private String description;

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}
	
	public DataScrollModel() {
	}
	
	public DataScrollModel(Date date, double value, String consumption, String ie, int id, int id_consumption, String description) {
		this.date = date;
		this.value = value;
		this.consumption = consumption;
		this.ie = ie;
		this.id = id;
		this.id_consumption = id_consumption;
		this.description = description;
	}

	public double getValue() {
		return value;
	}

	public void setValue(double value) {
		this.value = value;
	}

	public String getConsumption() {
		return consumption;
	}

	public void setConsumption(String consumption) {
		this.consumption = consumption;
	}	

	public int getIdConsumption() {
		return id_consumption;
	}

	public void setIdConsumption(int id_consumption) {
		this.id_consumption = id_consumption;
	}	

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}	

	public String getIe() {
		return ie;
	}

	public void setIe(String ie) {
		this.ie = ie;
	}	

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}	

	@Override
	public String toString() {
		StringBuffer strBuff = new StringBuffer();
		strBuff.append(", date : ").append(getDate());
		strBuff.append(", value : ").append(getValue());
		strBuff.append(", consumption : ").append(getConsumption());
		strBuff.append(", ie : ").append(getIe());
		strBuff.append(", id : ").append(getId());
		strBuff.append(", id_consumption : ").append(getIdConsumption());
		strBuff.append(", description : ").append(getDescription());
		return strBuff.toString();
	}
}
