package transaction.managed.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.primefaces.event.SelectEvent;
import org.primefaces.event.UnselectEvent;
import org.springframework.dao.DataAccessException;


import transaction.model.Transaction;
import transaction.service.ITransactionService;
import user.managed.bean.UserManagedBean;

@ManagedBean(name="transactionCRUD1")
@SessionScoped
public class TransactionCRUD implements Serializable{
		
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
	    
		//Spring Transaction Service is injected...
		@ManagedProperty(value="#{TransactionService}")
		ITransactionService transactionService;
		
		List<Transaction> transactionList;

        private Transaction selectedTransaction;
        
		private int id;
		private int id_account;
		private int id_consumption;
		  
	    public TransactionCRUD() {  
	    	transactionList = new ArrayList<Transaction>(); 
	    } 
	    
		public String addTransaction() {
			try {
				Transaction transaction = new Transaction();
				transaction.setId(getId());
				transaction.setIdAccount(getIdAccount());
				transaction.setIdConsumption(getIdConsumption());
				getTransactionService().addTransaction(transaction);
				return SUCCESS;
			} catch (DataAccessException e) {
				e.printStackTrace();
			} 	
			
			return ERROR;
		}
		
		public void reset() {
			this.setId(0);
			this.setIdAccount(0);
			this.setIdConsumption(0);
		}
		
		public List<Transaction> getTransactionList() {
			transactionList = new ArrayList<Transaction>();
			transactionList.addAll(getTransactionService().getTransactions(user.getId()));
			return transactionList;
		}
		
		public ITransactionService getTransactionService() {
			return transactionService;
		}

		public void setTransactionService(ITransactionService transactionService) {
			this.transactionService = transactionService;
		}
		
		public void setTransactionList(List<Transaction> transactionList) {
			this.transactionList = transactionList;
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
		
		public int getIdConsumption() {
			return id_consumption;
		}
		
		public void setIdConsumption(int id_consumption) {
			this.id_consumption = id_consumption;
		}        
		
		public Transaction getSelectedTransaction() {
            return selectedTransaction;
        }

        public void setSelectedTransaction(Transaction transaction) {
            this.selectedTransaction = transaction;
        }

        public String onRowSelect(SelectEvent event) {        
        	//FacesMessage msg = new FacesMessage("Car Selected", ((Transaction) event.getObject()).getModel());  
        	//FacesContext.getCurrentInstance().addMessage(null, msg);  
        	setSelectedTransaction((Transaction) event.getObject());        
        	FacesMessage msg = new FacesMessage("Item Edited",((Transaction) event.getObject()).getE()); 
            FacesContext.getCurrentInstance().addMessage(null, msg);  
            return "transaction?faces-redirect=true";
        }

        public void onRowUnselect(UnselectEvent event) {
            //FacesMessage msg = new FacesMessage("Student Unselected", ((User) event.getObject()).getFirstName());  
            //FacesContext.getCurrentInstance().addMessage("messages", msg); 
        	this.selectedTransaction = null; 
        }
        
        public void deleteTransaction() {  
        	transactionList.remove(selectedTransaction);  
        }  
	}