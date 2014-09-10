package account.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import account.model.Account;
import account.model.AccountSelectView;
import account.model.AccountView;
import account.dao.IAccountDAO;

@Transactional(readOnly = true)
public class AccountService implements IAccountService {

	// AccountDAO is injected...
	IAccountDAO accountDAO;
	
	@Transactional(readOnly = false)
	public void addAccount(Account account) {
		getAccountDAO().addAccount(account);
	}
	
	@Transactional(readOnly = false)
	public void saveOrUpdateAccount(Account account) {
		getAccountDAO().saveOrUpdateAccount(account);
	}
	
	@Transactional(readOnly = false)
	public void deleteAccount(Account account) {
		getAccountDAO().deleteAccount(account);
	}
	
	@Transactional(readOnly = false)
	public void updateAccount(Account account) {
		getAccountDAO().updateAccount(account);
	}

	public Account getAccountById(int id) {
		return getAccountDAO().getAccountById(id);
	}

	public List<AccountView> getAccounts(int id) {	
		return getAccountDAO().getAccounts(id);
	}

	public List<AccountSelectView> getAccountSelectView(int id) {	
		return getAccountDAO().getAccountSelectView(id);
	}

	public IAccountDAO getAccountDAO() {
		return accountDAO;
	}

	public void setAccountDAO(IAccountDAO accountDAO) {
		this.accountDAO = accountDAO;
	}
}
