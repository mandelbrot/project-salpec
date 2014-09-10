package consumption;  
  
import java.io.Serializable;  
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.annotation.PostConstruct;

import org.primefaces.model.DefaultTreeNode;  
import org.primefaces.model.TreeNode;  
import org.primefaces.model.menu.DefaultMenuModel;
import org.primefaces.model.menu.MenuItem;
import org.primefaces.model.menu.MenuModel;
import org.primefaces.model.menu.Submenu;

import account.model.AccountSelectView;

import transaction.model.TransactionView;
import user.managed.bean.UserManagedBean;
  
import consumption.model.Consumption;
import consumption.model.ConsumptionGroup;
import consumption.model.ConsumptionSelectView;
import consumption.service.IConsumptionService;
import currency.model.CurrencySelectView;

@ManagedBean(name="consumptionController1")
@RequestScoped
public class ConsumptionController1 implements Serializable{  
	
	private static final long serialVersionUID = 1L;
	
	//Spring Consumption Service is injected...
	@ManagedProperty(value="#{ConsumptionService}")
	IConsumptionService consumptionService;
	
	@ManagedProperty("#{userMB}")
    private UserManagedBean user;

    @ManagedProperty(value = "#{param.cid}")
    private int cid;

    @ManagedProperty(value = "#{param.cgid}")
    private int cgid;
	 
	List<TransactionView> transactionList;
	private String consumptionGroup;
  
    public ConsumptionController1() {  
        
    }  
    
	public List<TransactionView> getTransactionList() {
		return transactionList;
	}
	
	public void setTransactionList(List<TransactionView> transactionList) {
		this.transactionList = transactionList;
	}
	
    public void setUser(UserManagedBean user) {
        this.user = user;
    }

    public UserManagedBean getUser() {
        return user;
    }
 
    public void setConsumptionGroup(String consumptionGroup) {
        this.consumptionGroup = consumptionGroup;
    }

    public String getConsumptionGroup() {
        return consumptionGroup;
    }

    public void setCid(int cid) {
        this.cid = cid;
    }

    public int getCid() {
        return cid;
    }

    public void setCgid(int cgid) {
        this.cgid = cgid;
    }

    public int getCgid() {
        return cgid;
    }

	public void editAction() 
	{
		System.out.println("c1.editAction: ");
		//Map<String,String> params = FacesContext.getExternalContext().getRequestParameterMap();
		//String action = params.get("name");
		setConsumptionGroup("tetete");
	}
	  
	public void attrListener(ActionEvent event)
	{
		System.out.println("c1.attrListener: ");
		String action = (String)event.getComponent().getAttributes().get("consumptionGroup");
		System.out.println("c1.attrListener: " + action);
		setConsumptionGroup(action);
	}

	public IConsumptionService getConsumptionService() {
		return consumptionService;
	}

	public void setConsumptionService(IConsumptionService consumptionService) {
		this.consumptionService = consumptionService;
	}
	
	public void init() {
		System.out.println("......value of parameter is: " + cid);
		System.out.println("......value of parameter is: " + cgid);
		System.out.println("......user id is: " + user.getId());
		
		List<TransactionView> tempList = new ArrayList<TransactionView>();
		if(cgid != 0)
			tempList.addAll(getConsumptionService().getTransactionViews(cgid, user.getId(), true));
		else
			tempList.addAll(getConsumptionService().getTransactionViews(cid, user.getId(), false));
        List<TransactionView> tList = new ArrayList();

        for (Object o: tempList) {         
    	    Object values[] = (Object[]) o;
    	    String[] tmp = new String[values.length];
    	    //System.out.println("Size of the object is: " + values.length);  
    	    for(int i = 0; i < values.length; ++i) {
    	         tmp[i] = String.valueOf(values[i]);
    	         System.out.println("......" + i + " value of object is: " + String.valueOf(values[i]));
    	    }
    		Date d1 = new Date();
    		Date d2 = new Date();	
    		DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    		try 
    		{
    			d1 = formatter.parse(tmp[12]);
    			d2 = formatter.parse(tmp[13]);
    			//System.out.println(d1);
    			//System.out.println(d2);
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
	}
}  