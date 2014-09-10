package transaction.managed.bean;

import java.io.Serializable;
import java.util.Date;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

import org.springframework.dao.DataAccessException;

import account.model.AccountSelectView;
import consumption.model.ConsumptionSelectView;
import currency.model.CurrencySelectView;


import transaction.model.Transaction;
import transaction.model.TransactionView;
import transaction.service.ITransactionService;
import user.managed.bean.UserManagedBean;

@ManagedBean(name="transactionController")
@RequestScoped
public class TransactionController implements Serializable{
		
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

	    @ManagedProperty(value = "#{param.id}")
	    private int id;

		//Spring Transaction Service is injected...
		@ManagedProperty(value="#{TransactionService}")
		ITransactionService transactionService;
		
		private TransactionView transaction;
		private TransactionView tempTransaction;
		private TransactionView defaultTransaction = new TransactionView(-1, 0, 1, 0, 0, "1", "0", "Y", "", "", 
				"", "", new Date(), new Date(), new AccountSelectView(0, ""), 
				new CurrencySelectView(1, "hrk"), new ConsumptionSelectView(0, ""));

	    public TransactionController() {  
	    } 
		
	    @PostConstruct
		public void init() {
			System.out.println("----------ConsumptionController.init: 1.value of parameter id is: " + id);
			if(id == 0 && FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("id") != null)
				id = (Integer) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("id");
			else
			{
				FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("id", id);
			}
			
			System.out.println("----------ConsumptionController.init: 2.value of parameter id is: " + id);
			System.out.println("----------ConsumptionController.init: user id is: " + user.getId());

	    	if(tempTransaction == null)
	    		tempTransaction = transaction; 
	    	else
	    		System.out.println("----------TransactionViewController.init: TEMP: " + tempTransaction.toString());  
	    	
			if(id == 0)
				transaction = tempTransaction;
			else
				transaction = getTransactionService().getTransactionViewById(user.getId(), id);
		}

		public ITransactionService getTransactionService() {
			return transactionService;
		}

		public void setTransactionService(ITransactionService transactionService) {
			this.transactionService = transactionService;
		}

		public int getId() {
			return id;
		}

		public void setId(int id) {
			this.id = id;
		}
		
		public TransactionView getTransaction() {
			if(tempTransaction == null)
			{
				transaction = defaultTransaction;
			}
			else
			{
				transaction = tempTransaction;
			}
            return transaction;
        }

        public void setTransaction(TransactionView transaction) {
            this.transaction = transaction;
        }
        
        public void setTempTransaction(TransactionView tempTransaction) {
            this.tempTransaction = tempTransaction;
        }

        public TransactionView getTempTransaction() {
            return tempTransaction;
        }
        
        public void accountSelected(AccountSelectView a) {
    		//System.out.println("----------TransactionViewController.accountSelected: Selektiran account id: " + a.toString());
        }

        public void accountSelected() {
    		//System.out.println("----------TransactionViewController.accountSelected: Selektiran account id: " + selectedTransaction.getIdAccount());
        }

        public void currencySelected() {
    		//System.out.println("----------TransactionViewController.currencySelected: Selektiran currency id: " + selectedTransaction.getIdAccount());    	
        }
        
        public void consumptionSelected() {
    		//System.out.println("----------TransactionViewController.consumptionSelected: Selektiran consumption id: " + selectedTransaction.getIdAccount());      	
        }

        public void ieChange() {
    		//System.out.println("----------TransactionViewController.ieChange: Promijenjen ie: " + selectedTransaction.getIe());      	
        }
        
        public String transactions(){
			id = -1;
			FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("id", id);
    		System.out.println("----------TransactionController.transactions: id = " + id);
            return "transactions?faces-redirect=true";
        }
        
        public String updateTransaction(TransactionView t) {  
    		System.out.println("----------TransactionController.updateTransaction: Update transakcije: " + t.toString());
        	Transaction t1 = new Transaction(t.getId(), t.getIdAccount(), t.getIdCurrency(), t.getIdConsumption(),
	        	t.getValue(), t.getIe(), t.getE(), t.getActive(), t.getDescription(), new Date(), t.getDateTransaction());
        	
			try {
				getTransactionService().saveOrUpdateTransaction(t1);

				transaction = defaultTransaction;
				id = -1;
				FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("id", id);
				
				String poruka = "Transakcija ažurirana!";
				if(t.getId() == 0 || t.getId() == -1) poruka = "Transakcija dodana!";
				
				//init();
				tempTransaction = transaction;
				
	            FacesMessage msg = new FacesMessage(poruka, t1.toString());  
	            FacesContext.getCurrentInstance().addMessage("growlMessage", msg); 

	            return "transactions?faces-redirect=true";
			} catch (DataAccessException e) {
				e.printStackTrace();
				String poruka = "ažuriranja";
				if(t.getId() == 0) poruka = "dodavanja";
	            FacesMessage msg = new FacesMessage("Greška prilikom " + poruka + " transakcije!", t1.toString() + e.toString());  
	            FacesContext.getCurrentInstance().addMessage("growlMessage", msg); 

				//transaction = null;
	            return "";
			} 	     
        }  

        public String deleteTransaction(TransactionView t) {  
    		System.out.println("----------TransactionController.deleteTransaction: Brisanje transakcije: " + t.toString());
        	Transaction t1 = new Transaction(t.getId(), t.getIdAccount(), t.getIdCurrency(), t.getIdConsumption(),
	        	t.getValue(), t.getIe(), t.getE(), "N", t.getDescription(), new Date(), t.getDateTransaction());
        	
			try {
				//getTransactionService().deleteTransaction(t1);		
				getTransactionService().saveOrUpdateTransaction(t1);

				transaction = defaultTransaction;
				id = -1;
				FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("id", id);
				//init();
				tempTransaction = transaction;
				
	            FacesMessage msg = new FacesMessage("Transakcija obrisana!", t.toString());  
	            FacesContext.getCurrentInstance().addMessage("growlMessage", msg); 
	            
	            return "transactions?faces-redirect=true";
			} catch (DataAccessException e) {
				e.printStackTrace();
	            FacesMessage msg = new FacesMessage("Greška prilikom brisanja transakcije!", e.toString());  
	            FacesContext.getCurrentInstance().addMessage("growlMessage", msg); 
				
				//transaction = null;
				return "";
			} 	
        }  
        
		public void resetTransaction() {    		
			System.out.println("----------TransactionController.resetTransaction: Resetiranje transakcije o: " + transaction.toString()); 
	
			TransactionView t = new TransactionView(-1, transaction.getIdAccount(), transaction.getIdCurrency(), transaction.getIdConsumption(), 
        			0, transaction.getIe(), transaction.getE(), 
        			transaction.getActive(), transaction.getDescription(), transaction.getAccountName(), 
        			transaction.getConsumptionName(), transaction.getCode(), 
        			new Date(), new Date(), 
        			new AccountSelectView(transaction.getIdAccount(), transaction.getAccountName()), 
        			new CurrencySelectView(transaction.getIdCurrency(), "hrk"), 
        			new ConsumptionSelectView(transaction.getIdConsumption(), transaction.getConsumptionName()));
	    	
			transaction = t;
			id = -1;
			FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("id", id);
			tempTransaction = transaction;
			
			System.out.println("----------TransactionController.resetTransaction: Transakcija resetirana: " + transaction.toString());
			
	        FacesMessage msg = new FacesMessage("Transakcija resetirana!", transaction.toString());  
	        FacesContext.getCurrentInstance().addMessage("growlMessage", msg); 
		}
	}