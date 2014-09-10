package transaction.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import account.model.Account;

@Entity
@Table(name="TRANSACTION")
public class Transaction{

	private int id;
	//private int id_account1;
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
	
	//private Account account;

/*	@ManyToOne
	@JoinColumn(name="id", nullable=false)
	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}*/

	public Transaction() {
	}
	
	public Transaction(int id, int id_account, int id_currency, int id_consumption, double value, String ie, 
			String e, String active, String description, Date date, Date date_transaction) {
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

	@Id
	@Column(name="ID", unique = true, nullable = false)
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	//@ManyToOne
    //@JoinColumn(name="id_account")
	//private Account account;
	
	//@ManyToOne
    //@JoinColumn(name="id_account")
	@Column(name="id_account", nullable = false)
	public int getIdAccount() {
		return id_account;
	}

	public void setIdAccount(int id_account) {
		this.id_account = id_account;
	}

	//@ManyToOne
    //@JoinColumn(name="id_currency")
	@Column(name="id_currency", nullable = false)
	public int getIdCurrency() {
		return id_currency;
	}

	public void setIdCurrency(int id_currency) {
		this.id_currency = id_currency;
	}	

	//@ManyToOne
    //@JoinColumn(name="id_consumption")
	@Column(name="id_consumption", nullable = false)
	public int getIdConsumption() {
		return id_consumption;
	}

	public void setIdConsumption(int id_consumption) {
		this.id_consumption = id_consumption;
	}	

	@Column(name="value", nullable = false)
	public double getValue() {
		return value;
	}

	public void setValue(double value) {
		this.value = value;
	}	

	@Column(name="ie", nullable = false)
	public String getIe() {
		return ie;
	}

	public void setIe(String ie) {
		this.ie = ie;
	}	

	@Column(name="e")
	public String getE() {
		return e;
	}

	public void setE(String e) {
		this.e = e;
	}	

	@Column(name="active", nullable = false)
	public String getActive() {
		return active;
	}

	public void setActive(String active) {
		this.active = active;
	}	

	@Column(name="description")
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}	

	@Column(name="date")
	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}	

	@Column(name="date_transaction", nullable = false)
	public Date getDateTransaction() {
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
