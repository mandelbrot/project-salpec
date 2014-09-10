package user.managed.bean;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.tagcloud.DefaultTagCloudItem;
import org.primefaces.model.tagcloud.DefaultTagCloudModel;
import org.primefaces.model.tagcloud.TagCloudItem;
import org.primefaces.model.tagcloud.TagCloudModel;

import consumption.service.IConsumptionService;

import transaction.model.Transaction;
import transaction.service.ITransactionService;
import user.managed.bean.UserManagedBean;
import user.model.DataScrollModel;
 
@ManagedBean(name="dashboard")
@ViewScoped 
public class Dashboard implements Serializable{

	private static final long serialVersionUID = 1L;
    private TagCloudModel modelIncomeCount;
    private TagCloudModel modelExpenseCount;
    private TagCloudModel modelIncomeValue;
    private TagCloudModel modelExpenseValue;
	private ArrayList<DataScrollModel> transactions;
    
    @ManagedProperty("#{userMB}")
    private UserManagedBean user;
    
    public void setUser(UserManagedBean user) {
        this.user = user;
    }

    public UserManagedBean getUser() {
        return user;
    }
    
	@ManagedProperty(value="#{ConsumptionService}")
	IConsumptionService consumptionService;
    
	@ManagedProperty(value="#{TransactionService}")
	ITransactionService transactionService;
	
    @PostConstruct
    public void init() {
		System.out.println("----------TagCloudView.init: user is: " + user.getId());
		
		List<String[]> tempList = new ArrayList<String[]>();
		tempList.addAll(getConsumptionService().getConsumptionsCount(user.getId(), "1"));
		modelIncomeCount = new DefaultTagCloudModel();
        int count = 10;
        for (Object o: tempList) {         
    	    Object values[] = (Object[]) o;
    	    String[] tmp = new String[values.length];
    	    //System.out.println("----------TagCloudView: Size of the object is: " + values.length);  
    	    for(int i = 0; i < values.length; ++i) {
    	         tmp[i] = String.valueOf(values[i]);
    	         //System.out.println("----------TagCloudView: " + i + " value of object is: " + String.valueOf(values[i]));
    	    }
    	    //System.out.println("----------TagCloudView: " + (int)(Math.floor(count/2)));
    	    modelIncomeCount.addTag(new DefaultTagCloudItem(tmp[0], "consumption.xhtml?cid=" + tmp[1], count/2));
    	    count--;
        }
        
		tempList = new ArrayList<String[]>();
		tempList.addAll(getConsumptionService().getConsumptionsCount(user.getId(), "0"));
		modelExpenseCount = new DefaultTagCloudModel();
        count = 10;
        for (Object o: tempList) {         
    	    Object values[] = (Object[]) o;
    	    String[] tmp = new String[values.length];
    	    for(int i = 0; i < values.length; ++i) {
    	         tmp[i] = String.valueOf(values[i]);
    	    }
    	    modelExpenseCount.addTag(new DefaultTagCloudItem(tmp[0], "consumption.xhtml?cid=" + tmp[1], count/2));
    	    count--;
        }
        
		tempList = new ArrayList<String[]>();
		tempList.addAll(getConsumptionService().getConsumptionsValue(user.getId(), "1"));
		modelIncomeValue = new DefaultTagCloudModel();
        count = 10;
        for (Object o: tempList) {         
    	    Object values[] = (Object[]) o;
    	    String[] tmp = new String[values.length];
    	    for(int i = 0; i < values.length; ++i) {
    	         tmp[i] = String.valueOf(values[i]);
    	    }
    	    modelIncomeValue.addTag(new DefaultTagCloudItem(tmp[0], "consumption.xhtml?cid=" + tmp[1], count/2));
    	    count--;
        }        
        
		tempList = new ArrayList<String[]>();
		tempList.addAll(getConsumptionService().getConsumptionsValue(user.getId(), "0"));
		modelExpenseValue = new DefaultTagCloudModel();
        count = 10;
        for (Object o: tempList) {         
    	    Object values[] = (Object[]) o;
    	    String[] tmp = new String[values.length];
    	    for(int i = 0; i < values.length; ++i) {
    	         tmp[i] = String.valueOf(values[i]);
    	    }
    	    modelExpenseValue.addTag(new DefaultTagCloudItem(tmp[0], "consumption.xhtml?cid=" + tmp[1], count/2));
    	    count--;
        }

        transactions = new ArrayList<DataScrollModel>();
        DataScrollModel dtsm = new DataScrollModel(); 
		List<Transaction> transactionList = new ArrayList<Transaction>();
		transactionList.addAll(getTransactionService().getTransactions(user.getId()));
	    Date d = new Date();	
		DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        for (Object o: transactionList) {         
    	    Object values[] = (Object[]) o;
    	    String[] tmp = new String[7];
    	    tmp[0] = String.valueOf(values[5]);//date
    	    tmp[1] = String.valueOf(values[3]);//value
    	    tmp[2] = String.valueOf(values[2]);//consumption
    	    tmp[3] = String.valueOf(values[6]);//ie	   
    	    tmp[4] = String.valueOf(values[0]);//id_transaction	 
    	    tmp[5] = String.valueOf(values[7]);//id_consumption	  
    	    tmp[6] = String.valueOf(values[8]);//description	  		
    		try 
    		{
    			d = formatter.parse(tmp[0]);
    		} 
    		catch (ParseException e) 
    		{
    			e.printStackTrace();
    			d = new Date();
    		}
    	    dtsm = new DataScrollModel(d, Double.parseDouble(tmp[1]), tmp[2], tmp[3], 
	    		Integer.parseInt(tmp[4]), Integer.parseInt(tmp[5]), tmp[6]);
    	    transactions.add(dtsm);
        }
    }

	public IConsumptionService getConsumptionService() {
		return consumptionService;
	}

	public void setConsumptionService(IConsumptionService consumptionService) {
		this.consumptionService = consumptionService;
	}

	public ITransactionService getTransactionService() {
		return transactionService;
	}

	public void setTransactionService(ITransactionService transactionService) {
		this.transactionService = transactionService;
	}
	
    public TagCloudModel getModelIncomeCount() {
        return modelIncomeCount;
    }

    public TagCloudModel getModelIncomeValue() {
        return modelIncomeValue;
    }

    public TagCloudModel getModelExpenseCount() {
        return modelExpenseCount;
    }

    public TagCloudModel getModelExpenseValue() {
        return modelExpenseValue;
    }

    public ArrayList<DataScrollModel> getTransactions() {
        return transactions;
    }

    public void onSelect(SelectEvent event) {
        TagCloudItem item = (TagCloudItem) event.getObject();
        //FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Item Selected", item.getLabel());
        //FacesContext.getCurrentInstance().addMessage(null, msg);
    }
}