package transaction.model;

import java.util.Date;

public abstract class ITransaction {

	private int id;
	private int id_account;
	private int id_currency;
	private int id_consumption;
	private double value;
	private String ie;
	private String e;
	private String active;
	private String description;
	private Date date;
	private Date date_transaction;
	
	public ITransaction() {}
			
	public ITransaction(int id, int id_account, int id_currency, int id_consumption, double value, String ie, 
			String e, String active, String description, Date date, Date date_transaction) 
	{
		this.id = id;
		this.id_account = id_account;
		this.id_currency = id_currency;
		this.id_consumption = id_consumption;
		this.value = value;
		this.ie = ie;
		this.e = e;
		this.active = active;
		this.description = description;
		this.date = date;
		this.date_transaction = date_transaction;
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getIdAccount() {
		return id_account;
	}

	public void setIdAccount(int id_account) {
		this.id_account = id_account;
	}

	public int getIdCurrency() {
		return id_currency;
	}

	public void setIdCurrency(int id_currency) {
		this.id_currency = id_currency;
	}	

	public int getIdConsumption() {
		return id_consumption;
	}

	public void setIdConsumption(int id_consumption) {
		this.id_consumption = id_consumption;
	}	

	public double getValue() {
		return value;
	}

	public void setValue(double value) {
		this.value = value;
	}	

	public String getIe() {
		if (ie == null) ie = "";
		if (ie.equals("null")) ie = "";
		return ie;
	}

	public void setIe(String ie) {
		this.ie = ie;
	}	

	public String getE() {
		if (e == null) e = "";
		if (e.equals("null")) e = "";
		return e;
	}

	public void setE(String e) {
		this.e = e;
	}	

	public String getActive() {
		if (active == null) active = "Y";
		if (active.equals("null")) active = "Y";
		return active;
	}

	public void setActive(String active) {
		this.active = active;
	}	

	public String getDescription() {
		if (description == null) description = "";
		if (description.equals("null")) description = "";
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}	

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}	

	public Date getDateTransaction() {
		if (date_transaction == null) date_transaction = new Date();
		return date_transaction;
	}

	public void setDateTransaction(Date date_transaction) {
		this.date_transaction = date_transaction;
	}	
	
	@Override
	public String toString() {
		StringBuffer strBuff = new StringBuffer();
		strBuff.append("id : ").append(getId());
		strBuff.append(", id_account : ").append(getIdAccount());
		strBuff.append(", id_currency : ").append(getIdCurrency());
		strBuff.append(", id_consumption : ").append(getIdConsumption());
		strBuff.append(", value : ").append(getValue());
		strBuff.append(", ie : ").append(getIe());
		strBuff.append(", e : ").append(getE());
		strBuff.append(", active : ").append(getActive());
		strBuff.append(", description : ").append(getDescription());
		strBuff.append(", date : ").append(getDate());
		strBuff.append(", date_transaction : ").append(getDateTransaction());
		return strBuff.toString();
	}
}
