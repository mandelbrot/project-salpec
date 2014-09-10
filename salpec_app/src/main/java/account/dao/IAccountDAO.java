package account.dao;

import java.util.List;

import account.model.Account;
import account.model.AccountSelectView;
import account.model.AccountView;

public interface IAccountDAO {
	public void addAccount(Account account);
	public void updateAccount(Account account);
	public void deleteAccount(Account account);
	public Account getAccountById(int id);
	public List<AccountView> getAccounts(int id_user);
	public List<AccountSelectView> getAccountSelectView(int id_user);
	public void saveOrUpdateAccount(Account a);	
}
