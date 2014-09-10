package transaction.model;

import java.util.Date;

import account.model.AccountSelectView;
import currency.model.CurrencySelectView;
import consumption.model.ConsumptionSelectView;

public class TransactionView extends ITransaction{

	private String accountName;
	private String consumptionName;
	private String code;
	private AccountSelectView account;
	private CurrencySelectView currency;
	private ConsumptionSelectView consumption;
	
	public TransactionView() {
	    super();
	}
	
	public TransactionView(int id, int id_account, int id_currency, int id_consumption, double value, String ie, 
			String e, String active, String description, String accountName, String consumptionName, String code, 
			Date date, Date date_transaction, AccountSelectView account, CurrencySelectView currency, ConsumptionSelectView consumption) {
	    super(id, id_account, id_currency, id_consumption, value, ie, 
		 e, active, description, date, date_transaction);
		this.account = account;
		this.currency = currency;
		this.consumption = consumption;
		this.accountName = accountName;
		this.consumptionName = consumptionName;
		this.code = code;
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
