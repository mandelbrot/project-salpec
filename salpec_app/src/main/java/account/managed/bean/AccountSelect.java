package account.managed.bean;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;

import user.managed.bean.UserManagedBean;
import account.model.AccountSelectView;
import account.service.IAccountService;

@ManagedBean(name="accountSelect")
@SessionScoped
public class AccountSelect {
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
	
	List<AccountSelectView> accountList;
	
	private int id;
	private String name;

	public void reset() {
		this.setId(0);
		this.setName("");
	}
	
	public List<AccountSelectView> getAccountList() {
		List<AccountSelectView> tempList = new ArrayList<AccountSelectView>();
		tempList.addAll(getAccountService().getAccountSelectView(user.getId()));
	    List<AccountSelectView> tList = new ArrayList();
	
	    for (Object o: tempList) {         
		    Object values[] = (Object[]) o;
		    String[] tmp = new String[values.length];
		    //System.out.println("Size of the object is: " + values.length);  
		    for(int i = 0; i < values.length; ++i) {
		         tmp[i] = String.valueOf(values[i]);
		         //System.out.println("----------AccountSelect.getAccountList: " + i + " value of object is: " + String.valueOf(values[i]));
		    }

		    AccountSelectView a = new AccountSelectView(Integer.parseInt(tmp[0]), (String) tmp[1]);
	    	tList.add(a);
	    }
	    
	    accountList = tList;
	
		//accountList = new ArrayList<AccountSelectView>();
		//accountList.addAll(getAccountService().getAccountSelectView(user.getId()));
		return accountList;
	}
	
	public IAccountService getAccountService() {
		return accountService;
	}

	public void setAccountService(IAccountService accountService) {
		this.accountService = accountService;
	}
	
	public void setAccountList(List<AccountSelectView> accountList) {
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
}
