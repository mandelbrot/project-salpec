package account.managed.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;

import org.springframework.dao.DataAccessException;

import user.managed.bean.UserManagedBean;


import account.model.Account;
import account.service.IAccountService;

@ManagedBean(name="accountCRUD")
@SessionScoped
public class AccountCRUD implements Serializable{
		
		private static final long serialVersionUID = 1L;
		private static final String SUCCESS = "success";
		private static final String ERROR   = "error";
		
	    @ManagedProperty("#{userMB}")
	    private UserManagedBean user;
	    
	    public void setUser(UserManagedBean user) {
	        this.user = user;
	    }

	    public UserManagedBean getUser() {
	        return user;
	    }
	    
		//Spring Account Service is injected...
		@ManagedProperty(value="#{AccountService}")
		IAccountService accountService;
		
		List<Account> accountList;
		
		private int id;
		private int id_user;
		private String name;

		public String addAccount() {
			try {
				Account account = new Account();
				account.setId(getId());
				account.setName(getName());
				account.setIdUser(getIdUser());
				getAccountService().addAccount(account);
				return SUCCESS;
			} catch (DataAccessException e) {
				e.printStackTrace();
			} 	
			
			return ERROR;
		}
		
		public void reset() {
			this.setId(0);
			this.setIdUser(0);
			this.setName("");
		}
		
		public List<Account> getAccountList() {
			accountList = new ArrayList<Account>();
			accountList.addAll(getAccountService().getAccounts(user.getId()));
			return accountList;
		}
		
		public IAccountService getAccountService() {
			return accountService;
		}

		public void setAccountService(IAccountService accountService) {
			this.accountService = accountService;
		}
		
		public void setAccountList(List<Account> accountList) {
			this.accountList = accountList;
		}
		
		public int getId() {
			return id;
		}

		public void setId(int id) {
			this.id = id;
		}

		public String getName() {
			return name;
		}
		
		public void setName(String name) {
			this.name = name;
		}
		
		public int getIdUser() {
			return id_user;
		}
		
		public void setIdUser(int id_user) {
			this.id_user = id_user;
		}
	}