package account.model;

public class AccountView extends IAccount{

	private int id_user;
	private double balance;

	public AccountView() {
	}
	
	public AccountView(int id, int id_user, String name, double balance) {
		super(id, name);
		this.id_user = id_user;
		this.balance = balance;
	}

	public int getIdUser() {
		return id_user;
	}

	public void setIdUser(int id_user) {
		this.id_user = id_user;
	}	

	public double getBalance() {
		return balance;
	}

	public void setBalance(double balance) {
		this.balance = balance;
	}

	@Override
	public String toString() {
		StringBuffer strBuff = new StringBuffer();
		strBuff.append("account id : ").append(getId());
		strBuff.append(", id_user : ").append(getIdUser());
		strBuff.append(", username : ").append(getName());
		strBuff.append(", balance : ").append(getBalance());
		return strBuff.toString();
	}
}
