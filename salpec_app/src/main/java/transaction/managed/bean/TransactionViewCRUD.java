package transaction.managed.bean;

import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.lang.reflect.Array;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Date;
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
import transaction.model.TransactionView;
import transaction.service.ITransactionService;
import user.managed.bean.UserManagedBean;

@ManagedBean(name="transactionViewCRUD")
@ViewScoped
public class TransactionViewCRUD implements Serializable{
		
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
		
		List<TransactionView> transactionList;

		private TransactionView selectedTransaction;//
        
		private int id;
		private int id_account;
		private int id_currency;
		private int id_consumption;
		private double value;
		private String ie;
		private String e;
		private String active;
		private String description;
		private String accountName;
		private String consumptionName;
		private String code1;
		private Date date;
		private Date date_transaction;
		  
		public TransactionViewCRUD() {
			//transactionList = getTransactions();
		}
		
		//@PostConstrut
		private void init() {
			transactionList = getTransactions();
		}

		public TransactionViewCRUD(int id, int id_account, int id_currency, int id_consumption, double value, String ie, 
				String e, String active, String description, String accountName, String consumptionName, String code1, 
				Date date, Date date_transaction) {
			this.id = id;
			this.id_account = id_account;
			this.id_currency = id_currency;
			this.id_consumption = id_consumption;
			this.value = value;
			this.ie = ie;
			this.e = e;
			this.active = active;
			this.description = description;
			this.accountName = accountName;
			this.consumptionName = consumptionName;
			this.code1 = code1;
			this.date = date;
			this.date_transaction = date_transaction;
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

		public int getIdCurrency() {
			return id_currency;
		}

		public void setIdCurrency(int id_currency) {
			this.id_currency = id_currency;
		}	

		public int getIdConsumption() {
			return id_consumption;
		}

		public void setIdConsumption(int id_consumption) {
			this.id_consumption = id_consumption;
		}	

		public double getValue() {
			return value;
		}

		public void setValue(double value) {
			this.value = value;
		}	

		public String getIe() {
			return ie;
		}

		public void setIe(String ie) {
			this.ie = ie;
		}	

		public String getE() {
			return e;
		}

		public void setE(String e) {
			this.e = e;
		}	

		public String getActive() {
			return active;
		}

		public void setActive(String active) {
			this.active = active;
		}	

		public String getDescription() {
			return description;
		}

		public void setDescription(String description) {
			this.description = description;
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
		
		public String getCode1() {
			return code1;
		}

		public void setCode1(String code1) {
			this.code1 = code1;
		}	
		
		public Date getDate() {
			return date;
		}

		public void setDate(Date date) {
			this.date = date;
		}	

		public Date getDateTransaction() {
			return date_transaction;
		}

		public void setDateTransaction(Date date_transaction) {
			this.date_transaction = date_transaction;
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
		
		public String updateTransaction() {
			try {
	    		System.out.println("Update transaction: ");
				Transaction transaction = new Transaction();
				transaction.setId(selectedTransaction.getId());
				transaction.setIdAccount(selectedTransaction.getIdAccount());
				transaction.setIdCurrency(selectedTransaction.getIdCurrency());
				transaction.setIdConsumption(selectedTransaction.getIdConsumption());
				transaction.setValue(selectedTransaction.getValue());
				transaction.setIe(selectedTransaction.getIe());
				transaction.setE(selectedTransaction.getE());
				transaction.setActive(selectedTransaction.getActive());
				transaction.setDescription(selectedTransaction.getDescription());
				transaction.setDate(selectedTransaction.getDate());
				transaction.setDateTransaction(selectedTransaction.getDateTransaction());
	    		System.out.println("Update transaction: " + transaction.toString());
				getTransactionService().updateTransaction(transaction);
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
		
		public List<TransactionView> getTransactions() {
    		//System.console().writer().println("Odabrana transakcija je: ");
			transactionList = new ArrayList<TransactionView>();
			transactionList.addAll(getTransactionService().getTransactionViews(user.getId()));
			return (List<TransactionView>) transactionList;
		}
		
		public ITransactionService getTransactionService() {
			return transactionService;
		}

		public void setTransactionService(ITransactionService transactionService) {
			this.transactionService = transactionService;
		}
		
		public List<TransactionView> getTransactionList() {
			transactionList = new ArrayList<TransactionView>();
			transactionList.addAll(getTransactionService().getTransactionViews(user.getId()));
			return (List<TransactionView>) transactionList;
		}
		
		public void setTransactionList(List<TransactionView> transactionList) {
			this.transactionList = transactionList;
		}
		
		public TransactionView getSelectedTransaction() {
            return selectedTransaction;
        }

        public void setSelectedTransaction(TransactionView transaction) {
            this.selectedTransaction = transaction;
        }

        public void onRowSelect(SelectEvent event) {        
        	//FacesMessage msg = new FacesMessage("Car Selected", ((TransactionView) event.getObject()).toString());  
        	//FacesContext.getCurrentInstance().addMessage(null, msg);  
        	//selectedTransaction = (TransactionView) event.getObject();
    		System.out.println("Odabrana transakcija je: ");
		    //Logger  logger = Logger.getLogger("com.foo");
    		//logger.warn("Low fuel level.");
    		Class cls = event.getObject().getClass();  
    	    System.out.println("The type of the object is: " + cls.getName());  
    	    Object values[] = (Object[])event.getObject();
    	    String[] tmp = new String[values.length];
    	    System.out.println("Size of the object is: " + values.length);  
    	    for(int i = 0; i < tmp.length; ++i) {
    	         tmp[i] = String.valueOf(values[i]);
    	         System.out.println("......" + i + " value of object is: " + String.valueOf(values[i]));
    	    }
    		Date d = new Date();
        	TransactionView t = new TransactionView(Integer.parseInt(tmp[0]), 
        			Integer.parseInt(tmp[1]), Integer.parseInt(tmp[2]), Integer.parseInt(tmp[3]), 
        			Double.parseDouble(tmp[4]), (String) tmp[5], (String) tmp[6], 
        			(String) tmp[7], (String) tmp[8], (String) tmp[9]
        			, (String) tmp[10], (String) tmp[11], d, d);
        	//selectedTransaction.setId(t.getId());
        	setSelectedTransaction(t);   
        	selectedTransaction = t;
        	
        	if(this.selectedTransaction == null)
        	{
        		System.out.println("Transakcija nije odabrana!");
        	}
        	else
        	{
        		System.out.println(t.toString());
        	}
        	
        	//FacesMessage msg = new FacesMessage("Item Edited", "((TransactionView) event.getObject()).getE())"); 
            //FacesContext.getCurrentInstance().addMessage(null, msg);  
            //return "transaction?faces-redirect=true";
        }

        public void onRowUnselect(UnselectEvent event) {
            //FacesMessage msg = new FacesMessage("Student Unselected", ((User) event.getObject()).getFirstName());  
            //FacesContext.getCurrentInstance().addMessage("growlMessage", msg); 
    		System.out.println("Transakcija nije odabrana! selectedTransaction = null");
        	this.selectedTransaction = null; 
        }
        
        public void deleteTransaction(Object o) {  
    		System.out.println("Brisanje transakcije o: " + o.toString());
        	//transactionList.remove((TransactionView) selectedTransaction);  

    		Object[] objects = (Object[]) o;
    		String[] tmp = (String[]) objects[0];
    	    System.out.println("Size of the object is: " + tmp.length);  
    	    for(int i = 0; i < tmp.length; ++i) {
    	         tmp[i] = String.valueOf(tmp[i]);
    	         System.out.println("......" + i + " value of object is: " + String.valueOf(tmp[i]));
    	    }
    		Date d = new Date();
        	TransactionView t = new TransactionView(Integer.parseInt(tmp[0]), 
        			Integer.parseInt(tmp[1]), Integer.parseInt(tmp[2]), Integer.parseInt(tmp[3]), 
        			Double.parseDouble(tmp[4]), (String) tmp[5], (String) tmp[6], 
        			(String) tmp[7], (String) tmp[8], (String) tmp[9]
        			, (String) tmp[10], (String) tmp[11], d, d);
        	
        	//transactionList.remove(t); 
    		System.out.println("Transakcija obrisana: " + o.toString());
    		System.out.println("Transakcija obrisana: " + t.toString());
    		
            FacesMessage msg = new FacesMessage("Transakcija obrisana!", t.toString());  
            FacesContext.getCurrentInstance().addMessage("growlMessage", msg); 
        }  
        public void deleteTransaction() {  
    		System.out.println("Brisanje transakcije: ");
        	//transactionList.remove((TransactionView) selectedTransaction);  
            FacesMessage msg = new FacesMessage("Transakcija obrisana!", "");  
            FacesContext.getCurrentInstance().addMessage("growlMessage", msg); 
        }  
        public void deleteTransaction(TransactionView t) {  
    		System.out.println("Brisanje transakcije: " + t.toString());
        	Transaction t1 = new Transaction(t.getId(), t.getIdAccount(), t.getIdCurrency(), t.getIdConsumption(),
	        	t.getValue(), t.getIe(), t.getE(), t.getActive(), t.getDescription(), t.getDate(), t.getDateTransaction());
			//getTransactionService().updateTransaction(t1);
			//srediti za null - ako je u bazi null onda se u viewu i natrag kad se sprema u bazu pojavljuje 'null'
			//getTransactionService().deleteTransaction(t1);
        	//transactionList.remove(8);  
            FacesMessage msg = new FacesMessage("Transakcija obrisana!", t.toString());  
            FacesContext.getCurrentInstance().addMessage("growlMessage", msg); 
        }  
        public void updateTransaction(TransactionView t) {  
    		System.out.println("Update transakcije: " + t.toString());
        	Transaction t1 = new Transaction(t.getId(), t.getIdAccount(), t.getIdCurrency(), t.getIdConsumption(),
	        	t.getValue(), t.getIe(), t.getE(), t.getActive(), t.getDescription(), t.getDate(), t.getDateTransaction());
			getTransactionService().updateTransaction(t1);
			//srediti za null - ako je u bazi null onda se u viewu i natrag kad se sprema u bazu pojavljuje 'null'
			//getTransactionService().deleteTransaction(t1);
        	//transactionList.remove(8);
        	
        	//i kod update javlja da ne može srediti objekt u transactionview - srediti!!!
        	

            FacesMessage msg = new FacesMessage("Transakcija ažurirana!", t1.toString());  
            FacesContext.getCurrentInstance().addMessage("growlMessage", msg); 
        }  
        public void updateTransaction(Object o) {  
    		System.out.println("Brisanje transakcije o: " + o.toString());
        	//transactionList.remove((TransactionView) selectedTransaction);  

    		Object[] objects = (Object[]) o;
    		String[] tmp = (String[]) objects[0];
    	    System.out.println("Size of the object is: " + tmp.length);  
    	    for(int i = 0; i < tmp.length; ++i) {
    	         tmp[i] = String.valueOf(tmp[i]);
    	         System.out.println("......" + i + " value of object is: " + String.valueOf(tmp[i]));
    	    }
    		Date d = new Date();
        	TransactionView t = new TransactionView(Integer.parseInt(tmp[0]), 
        			Integer.parseInt(tmp[1]), Integer.parseInt(tmp[2]), Integer.parseInt(tmp[3]), 
        			Double.parseDouble(tmp[4]), (String) tmp[5], (String) tmp[6], 
        			(String) tmp[7], (String) tmp[8], (String) tmp[9]
        			, (String) tmp[10], (String) tmp[11], d, d);

        	Transaction t1 = new Transaction(t.getId(), t.getIdAccount(), t.getIdCurrency(), t.getIdConsumption(),
	        	t.getValue(), t.getIe(), t.getE(), t.getActive(), t.getDescription(), t.getDate(), t.getDateTransaction());
			getTransactionService().updateTransaction(t1);
        	//transactionList.remove(t); 
    		System.out.println("Transakcija obrisana: " + o.toString());
    		System.out.println("Transakcija obrisana: " + t.toString());

            FacesMessage msg = new FacesMessage("Transakcija ažurirana!", t1.toString());  
            FacesContext.getCurrentInstance().addMessage("growlMessage", msg); 
        }  
	}