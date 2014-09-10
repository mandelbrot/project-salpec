package account.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import transaction.model.Transaction;

import java.util.Set;

@Entity
@Table(name="ACCOUNT")
public class Account {

	private int id;
	private int id_user;
	private String name;
	
	//private Set<Transaction> transactions;

	@Id
	@Column(name="ID", unique = true, nullable = false)
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public Account() {
	}
	
	public Account(int id, int id_user, String name) {
		this.id = id;
		this.id_user = id_user;
		this.name = name;
	}

	@Column(name="name", unique = true, nullable = false)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name="id_user", unique = true, nullable = false)
	public int getIdUser() {
		return id_user;
	}

	public void setIdUser(int id_user) {
		this.id_user = id_user;
	}	

/*	@OneToMany 
    @JoinColumn(name="id_account1", nullable=false)
	public Set<Transaction> getTransactions() {
		return transactions;
		}

	public void setTransactions(Set<Transaction> transactions) {
		this.transactions = transactions;
		}*/

	@Override
	public String toString() {
		StringBuffer strBuff = new StringBuffer();
		strBuff.append("id : ").append(getId());
		strBuff.append(", id_user : ").append(getIdUser());
		strBuff.append(", username : ").append(getName());
		return strBuff.toString();
	}
}
