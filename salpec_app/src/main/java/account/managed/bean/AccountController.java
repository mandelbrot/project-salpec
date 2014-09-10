package account.managed.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.primefaces.event.SelectEvent;
import org.primefaces.event.UnselectEvent;
import org.springframework.dao.DataAccessException;

import consumption.model.Consumption;

import account.model.Account;
import account.model.AccountView;
import account.service.IAccountService;

import user.managed.bean.UserManagedBean;

@ManagedBean(name="accountController")
@ViewScoped
public class AccountController implements Serializable{
		
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
		
		List<AccountView> accountList;

		private AccountView selectedAccount;
		private AccountView temp;

		public AccountController() {
			//init();
		}
		
		public void init() {
			List<AccountView> tempList = new ArrayList<AccountView>();
			System.out.println("----------AccountController.init: user is: " + user.getId());
			tempList.addAll(getAccountService().getAccounts(user.getId()));
	        List<AccountView> tList = new ArrayList();

	        for (Object o: tempList) {         
	    	    Object values[] = (Object[]) o;
	    	    String[] tmp = new String[values.length];
	    	    System.out.println("----------AccountController: Size of the object is: " + values.length);  
	    	    for(int i = 0; i < values.length; ++i) {
	    	         tmp[i] = String.valueOf(values[i]);
	    	         System.out.println("----------AccountController: " + i + " value of object is: " + String.valueOf(values[i]));
	    	    }
	    	    AccountView tCG = new AccountView(Integer.parseInt(tmp[0]), Integer.parseInt(tmp[1]), 
	    	    	(String) tmp[2], Double.parseDouble(tmp[3]));

	        	tList.add(tCG);
	        }
	        
	        accountList = tList;
	    	
	    	selectedAccount = new AccountView(0, user.getId(), "", 0);
	    	if(temp == null)
	    		temp = selectedAccount; 
	    	else
	    		System.out.println("----------AccountController.init: TEMP: " + temp.toString());  
		}

		public IAccountService getAccountService() {
			return accountService;
		}

		public void setAccountService(IAccountService accountService) {
			this.accountService = accountService;
		}
		
		public List<AccountView> getAccountList() {
			return accountList;
		}
		
		public void setAccountList(List<AccountView> accountList) {
			this.accountList = accountList;
		}
		
		public AccountView getSelectedAccount() {
			if(temp == null)
			{
				selectedAccount = new AccountView(0, user.getId(), "", 0);
			}
			else
			{
				selectedAccount = temp;
			}
            return selectedAccount;
        }

        public void setSelectedAccount(AccountView cg) {
            this.selectedAccount = cg;
        }
        
        public void setTemp(AccountView temp) {
            this.temp = temp;
        }

        public AccountView getTemp() {
            return temp;
        }
        
        public void onRowSelect(SelectEvent event) {        
        	selectedAccount = (AccountView) event.getObject();
        	temp = (AccountView) event.getObject(); 
    	    
        	if(this.selectedAccount == null)
        	{
        		System.out.println("----------AccountController.onRowSelect: Account nije odabrana!");
        	}
        	else
        	{
        		System.out.println("----------AccountController.onRowSelect:" + selectedAccount.toString());
        	}
        }

        public void onRowUnselect(UnselectEvent event) {
    		System.out.println("----------AccountController.onRowUnselect: Account nije odabrana! selectedAccount = null");
        	this.selectedAccount = null; 
        }
        
        public String deleteAccount(AccountView t) {  
    		System.out.println("----------AccountController.deleteAccount: Brisanje Account: " + t.toString());
    		Account t1 = new Account(t.getId(), t.getIdUser(), t.getName());

    		if(t1.getIdUser() == 0) t1.setIdUser(user.getId());
    		
			try {		
				getAccountService().deleteAccount(t1);
	        	accountList.remove(t);  
	        	//accountList.remove(t1);  

				selectedAccount = new AccountView(0, user.getId(), "", 0);
				
				init();
				temp = selectedAccount;
				
	            FacesMessage msg = new FacesMessage("Account obrisan!", t.toString());  
	            FacesContext.getCurrentInstance().addMessage("growlMessage", msg); 
	            
				return SUCCESS;
			} catch (DataAccessException e) {
				e.printStackTrace();
	            FacesMessage msg = new FacesMessage("Greška prilikom brisanja Account!", e.toString());  
	            FacesContext.getCurrentInstance().addMessage("growlMessage", msg); 
			} 	
		
			//init();
			
			selectedAccount = new AccountView(0, user.getId(), "", 0);
            
            return ERROR;
        }  
        
        public String updateAccount(AccountView t) {  
    		System.out.println("----------AccountController.updateAccount: Update Account: " + t.toString());
    		Account t1 = new Account(t.getId(), t.getIdUser(), t.getName());
        	
    		if(t1.getIdUser() == 0) t1.setIdUser(user.getId());
    		
			try {
				getAccountService().saveOrUpdateAccount(t1); //t1

				selectedAccount = new AccountView(0, user.getId(), "", 0);

				String poruka = "Account ažurirana!";
				if(t1.getId() == 0) poruka = "Account dodan!";
				
				init();
				//temp = null;
				temp = selectedAccount;
				
	            FacesMessage msg = new FacesMessage(poruka, t1.toString());  
	            FacesContext.getCurrentInstance().addMessage("growlMessage", msg); 
	            
				return SUCCESS;
			} catch (DataAccessException e) {
				e.printStackTrace();
				String poruka = "ažuriranja";
				if(t1.getId() == 0) poruka = "dodavanja";
	            FacesMessage msg = new FacesMessage("Greška prilikom " + poruka + " Account!", t1.toString() + e.toString());  
	            FacesContext.getCurrentInstance().addMessage("growlMessage", msg); 
	            
	            selectedAccount = t;
			} 	     

			//init();
			
			//selectedAccount = new Account(0, user.getId(), "");
            
            return ERROR;
        }  

        public void saveAccount(AccountView t) {  
            selectedAccount = t;
    		System.out.println("----------AccountController.saveAccount: Account: " + selectedAccount.toString());
        }
        
        public void resetAccount() {  
    		System.out.println("----------AccountController.resetAccount: Resetiranje Account o: " + selectedAccount.toString());

    		selectedAccount = new AccountView(0, user.getId(), "", 0);
        	
    		System.out.println("----------AccountController.resetAccount: Account resetirana: " + selectedAccount.toString());

			temp = selectedAccount;
			
            FacesMessage msg = new FacesMessage("Account resetirana!", selectedAccount.toString());  
            FacesContext.getCurrentInstance().addMessage("growlMessage", msg); 
        }  
	}