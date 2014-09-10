package transaction.model;

import java.util.Date;

import account.model.AccountSelectView;
import currency.model.CurrencySelectView;
import consumption.model.ConsumptionSelectView;

public class TransactionViewInherited {

	private int id;
	private int id_account;
	private int id_currency;
	private int id_consumption;
	private double value;
	private String ie;
	private String e;
	private String active;
	private String description;
	private String accountName;
	private String consumptionName;
	private String code;
	private Date date;
	private Date date_transaction;
	private AccountSelectView account;
	private CurrencySelectView currency;
	private ConsumptionSelectView consumption;
	
	public TransactionViewInherited() {
	}
	
	public TransactionViewInherited(int id, int id_account, int id_currency, int id_consumption, double value, String ie, 
			String e, String active, String description, String accountName, String consumptionName, String code, 
			Date date, Date date_transaction, AccountSelectView account, CurrencySelectView currency, ConsumptionSelectView consumption) {
		this.id = id;
		this.id_account = id_account;
		this.id_currency = id_currency;
		this.id_consumption = id_consumption;
		this.value = value;
		this.ie = ie;
		this.e = e;
		this.active = active;
		this.description = description;
		this.accountName = accountName;
		this.consumptionName = consumptionName;
		this.code = code;
		this.date = date;
		this.date_transaction = date_transaction;
		this.account = account;
		this.currency = currency;
		this.consumption = consumption;
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
		if (ie.equals("null")) ie = "";
		return ie;
	}

	public void setIe(String ie) {
		this.ie = ie;
	}	

	public String getE() {
		if (e.equals("null")) e = "";
		return e;
	}

	public void setE(String e) {
		this.e = e;
	}	

	public String getActive() {
		if (active.equals("null")) active = "Y";
		return active;
	}

	public void setActive(String active) {
		this.active = active;
	}	

	public String getDescription() {
		if (description.equals("null")) description = "";
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}	

	public String getAccountName() {
		return accountName;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}	
	
	public String getConsumptionName() {
		return consumptionName;
	}

	public void setConsumptionName(String consumptionName) {
		this.consumptionName = consumptionName;
	}	
	
	public String getCode() {
		return code;
	}

	public void setCode1(String code) {
		this.code = code;
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
	
	public AccountSelectView getAccount() {
		return account;
	}

	public void setAccount(AccountSelectView account) {
		this.account = account;
	}	
	
	public ConsumptionSelectView getConsumption() {
		return consumption;
	}

	public void setConsumption(ConsumptionSelectView consumption) {
		this.consumption= consumption;
	}	
	
	public CurrencySelectView getCurrency() {
		return currency;
	}

	public void setCurrency(CurrencySelectView currency) {
		this.currency= currency;
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
		strBuff.append(", account name : ").append(getAccountName());
		strBuff.append(", consumption name : ").append(getConsumptionName());
		strBuff.append(", code : ").append(getCode());
		strBuff.append(", date : ").append(getDate());
		strBuff.append(", date_transaction : ").append(getDateTransaction());
		strBuff.append(", account : ").append(getAccount());
		strBuff.append(", currency : ").append(getCurrency());
		strBuff.append(", consumption : ").append(getConsumption());
		return strBuff.toString();
	}
}
