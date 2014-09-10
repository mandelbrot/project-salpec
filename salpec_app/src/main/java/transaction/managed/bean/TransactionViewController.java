package transaction.managed.bean;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.primefaces.event.SelectEvent;
import org.primefaces.event.UnselectEvent;
import org.springframework.dao.DataAccessException;

import account.model.AccountSelectView;
import currency.model.CurrencySelectView;
import consumption.model.ConsumptionSelectView;

import transaction.model.ITransaction;
import transaction.model.Transaction;
import transaction.model.TransactionView;
import transaction.service.ITransactionService;
import user.managed.bean.UserManagedBean;

@ManagedBean(name="transactionViewController")
@ViewScoped
public class TransactionViewController implements Serializable{
		
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
		
		//List<ITransaction> transactionList;
		List<TransactionView> transactionList;

		private TransactionView selectedTransaction;
		private TransactionView tempTransaction;
		private TransactionView defaultTransaction = new TransactionView(0, 0, 1, 0, 0, "1", "0", "Y", "", "", 
				"", "", new Date(), new Date(), new AccountSelectView(0, ""), 
				new CurrencySelectView(1, "hrk"), new ConsumptionSelectView(0, ""));
		
		public TransactionViewController() {
			//init();
		}
		
		//@PostConstrut
		public void init() {
			List<TransactionView> tempList = new ArrayList<TransactionView>();
			tempList.addAll(getTransactionService().getTransactionViews(user.getId()));
	        List<TransactionView> tList = new ArrayList();

	        for (Object o: tempList) {         
	    	    Object values[] = (Object[]) o;
	    	    String[] tmp = new String[values.length];
	    	    //System.out.println("----------TransactionViewController.init: Size of the object is: " + values.length);  
	    	    for(int i = 0; i < values.length; ++i) {
	    	         tmp[i] = String.valueOf(values[i]);
	    	         //System.out.println("----------TransactionViewController.init: " + i + " value of object is: " + String.valueOf(values[i]));
	    	    }
	    		Date d1 = new Date();
	    		Date d2 = new Date();	
	    		DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	    		try 
	    		{
	    			d1 = formatter.parse(tmp[12]);
	    			d2 = formatter.parse(tmp[13]);
	    			//System.out.println("----------TransactionViewController.init: " + d1);
	    			//System.out.println("----------TransactionViewController.init: " + d2);
	    		} 
	    		catch (ParseException e) 
	    		{
	    			e.printStackTrace();
	    			d1 = new Date();
		    		d2 = new Date();
	    		}
	    		TransactionView tTransaction = new TransactionView(Integer.parseInt(tmp[0]), 
	        			Integer.parseInt(tmp[1]), Integer.parseInt(tmp[2]), Integer.parseInt(tmp[3]), 
	        			Double.parseDouble(tmp[4]), (String) tmp[5], (String) tmp[6], 
	        			(String) tmp[7], (String) tmp[8], (String) tmp[9], 
	        			(String) tmp[10], (String) tmp[11], d1, d2, 
	        			new AccountSelectView(Integer.parseInt(tmp[1]), (String) tmp[9]),
    					new CurrencySelectView(Integer.parseInt(tmp[2]), (String) tmp[11]),
    					new ConsumptionSelectView(Integer.parseInt(tmp[3]), (String) tmp[10]));
	        	tList.add(tTransaction);
	        }
	        
	    	transactionList = tList;
			
	    	selectedTransaction = defaultTransaction;
	    	if(tempTransaction == null)
	    		tempTransaction = selectedTransaction; 
	    	else
	    		System.out.println("----------TransactionViewController.init: TEMP: " + tempTransaction.toString());  
		}

		public ITransactionService getTransactionService() {
			return transactionService;
		}

		public void setTransactionService(ITransactionService transactionService) {
			this.transactionService = transactionService;
		}
		
		public List<TransactionView> getTransactionList() {
			return transactionList;
		}
		
		public void setTransactionList(List<TransactionView> transactionList) {
			this.transactionList = transactionList;
		}
		
		public TransactionView getSelectedTransaction() {
			if(tempTransaction == null)
			{
				selectedTransaction = defaultTransaction;
			}
			else
			{
				selectedTransaction = tempTransaction;
			}
            return selectedTransaction;
        }

        public void setSelectedTransaction(TransactionView transaction) {
            this.selectedTransaction = transaction;
        }
        
        public void setTempTransaction(TransactionView tempTransaction) {
            this.tempTransaction = tempTransaction;
        }

        public TransactionView getTempTransaction() {
            return tempTransaction;
        }
        
        public void onRowSelect(SelectEvent event) {        
        	selectedTransaction = (TransactionView) event.getObject();
        	tempTransaction = (TransactionView) event.getObject();
    	    
        	if(this.selectedTransaction == null)
        	{
        		System.out.println("----------TransactionViewController.onRowSelect: Transakcija nije odabrana!");
        	}
        	else
        	{
        		System.out.println("----------TransactionViewController.onRowSelect: " + selectedTransaction.toString());
        	}
        }

        public void onRowUnselect(UnselectEvent event) {
    		System.out.println("----------TransactionViewController.onRowUnselect: Transakcija nije odabrana! selectedTransaction = null");
        	this.selectedTransaction = null; 
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
        
        public String deleteTransaction(TransactionView t) {  
    		System.out.println("----------TransactionViewController.deleteTransaction: Brisanje transakcije: " + t.toString());
        	Transaction t1 = new Transaction(t.getId(), t.getIdAccount(), t.getIdCurrency(), t.getIdConsumption(),
	        	t.getValue(), t.getIe(), t.getE(), "N", t.getDescription(), new Date(), t.getDateTransaction());
        	
			try {
				//getTransactionService().deleteTransaction(t1);		
				getTransactionService().saveOrUpdateTransaction(t1);
	        	transactionList.remove(t);  

				selectedTransaction = defaultTransaction;
				
				init();
				tempTransaction = selectedTransaction;
				
	            FacesMessage msg = new FacesMessage("Transakcija obrisana!", t.toString());  
	            FacesContext.getCurrentInstance().addMessage("growlMessage", msg); 
	            
				return SUCCESS;
			} catch (DataAccessException e) {
				e.printStackTrace();
	            FacesMessage msg = new FacesMessage("Greška prilikom brisanja transakcije!", e.toString());  
	            FacesContext.getCurrentInstance().addMessage("growlMessage", msg); 
			} 	
		
			//init();
			
			selectedTransaction = defaultTransaction;
            
            return ERROR;
        }  
        
        public String updateTransaction(TransactionView t) {  
    		System.out.println("----------TransactionViewController.updateTransaction: Update transakcije: " + t.toString());
        	Transaction t1 = new Transaction(t.getId(), t.getIdAccount(), t.getIdCurrency(), t.getIdConsumption(),
	        	t.getValue(), t.getIe(), t.getE(), t.getActive(), t.getDescription(), new Date(), t.getDateTransaction());
        	
			try {
				getTransactionService().saveOrUpdateTransaction(t1);

				selectedTransaction = defaultTransaction;

				String poruka = "Transakcija ažurirana!";
				if(t.getId() == 0) poruka = "Transakcija dodana!";
				
				init();
				tempTransaction = selectedTransaction;
				
	            FacesMessage msg = new FacesMessage(poruka, t1.toString());  
	            FacesContext.getCurrentInstance().addMessage("growlMessage", msg); 
	            
				return SUCCESS;
			} catch (DataAccessException e) {
				e.printStackTrace();
				String poruka = "ažuriranja";
				if(t.getId() == 0) poruka = "dodavanja";
	            FacesMessage msg = new FacesMessage("Greška prilikom " + poruka + " transakcije!", t1.toString() + e.toString());  
	            FacesContext.getCurrentInstance().addMessage("growlMessage", msg); 
	            
	            selectedTransaction = t;
			} 	     

			//init();
			
			//selectedTransaction = defaultTransaction;
            
            return ERROR;
        }  

/*        public void saveTransactionView(TransactionView t) {  
            selectedTransaction = t;
    		System.out.println("----------TransactionViewController.saveTransactionView: Transakcija: " + selectedTransaction.toString());
        }*/
        
        public void resetTransaction() {  
    		System.out.println("----------TransactionViewController.resetTransaction: Resetiranje transakcije o: " + selectedTransaction.toString());
        	//transactionList.remove((TransactionView) selectedTransaction);  

    		selectedTransaction = new TransactionView(0, selectedTransaction.getIdAccount(), selectedTransaction.getIdCurrency(), selectedTransaction.getIdConsumption(), 
        			0, selectedTransaction.getIe(), selectedTransaction.getE(), 
        			selectedTransaction.getActive(), selectedTransaction.getDescription(), selectedTransaction.getAccountName(), 
        			selectedTransaction.getConsumptionName(), selectedTransaction.getCode(), 
        			new Date(), new Date(), 
        			new AccountSelectView(selectedTransaction.getIdAccount(), selectedTransaction.getAccountName()), 
        			new CurrencySelectView(selectedTransaction.getIdCurrency(), "hrk"), 
        			new ConsumptionSelectView(selectedTransaction.getIdConsumption(), selectedTransaction.getConsumptionName()));
        	
    		System.out.println("----------TransactionViewController.resetTransaction: Transakcija resetirana: " + selectedTransaction.toString());

			tempTransaction = selectedTransaction;
			
            FacesMessage msg = new FacesMessage("Transakcija resetirana!", selectedTransaction.toString());  
            FacesContext.getCurrentInstance().addMessage("growlMessage", msg); 
        }  
	}