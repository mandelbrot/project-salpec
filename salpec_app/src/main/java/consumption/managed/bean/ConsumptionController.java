package consumption.managed.bean;  
  
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

@ManagedBean(name="consumptionController")
@RequestScoped
public class ConsumptionController implements Serializable{  
	
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
	 
	List<TransactionView> consumptionList;
	private String consumptionGroup;
  
    public ConsumptionController() {  
        
    }  
    
	public List<TransactionView> getConsumptionList() {
		return consumptionList;
	}
	
	public void setConsumptionList(List<TransactionView> consumptionList) {
		this.consumptionList = consumptionList;
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

	public IConsumptionService getConsumptionService() {
		return consumptionService;
	}

	public void setConsumptionService(IConsumptionService consumptionService) {
		this.consumptionService = consumptionService;
	}
	
	public void init() {
		if(cid == 0 && FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("cid") != null)
			cid = (Integer) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("cid");
		else
		{
			FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("cid", cid);
			FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("cgid", 0);
		}
		
		if(cgid == 0 && FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("cgid") != null)
			cgid = (Integer) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("cgid");
		else
		{
			FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("cgid", cgid);
			FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("cid", 0);
		}
		
		System.out.println("----------ConsumptionController.init: value of parameter is: " + cid);
		System.out.println("----------ConsumptionController.init: value of parameter is: " + cgid);
		System.out.println("----------ConsumptionController.init: user id is: " + user.getId());
		
		List<TransactionView> tempList = new ArrayList<TransactionView>();
		if(cgid != 0)
		{
			tempList.addAll(getConsumptionService().getTransactionViews(cgid, user.getId(), true));
			setConsumptionGroup("All transactions for consumption group " + getConsumptionService().getConsumptionName(cgid, user.getId(), true));
		}
		else
		{
			tempList.addAll(getConsumptionService().getTransactionViews(cid, user.getId(), false));
			setConsumptionGroup("All transactions for consumption " + getConsumptionService().getConsumptionName(cid, user.getId(), false));
		}
        List<TransactionView> tList = new ArrayList();

        for (Object o: tempList) {         
    	    Object values[] = (Object[]) o;
    	    String[] tmp = new String[values.length];
    	    //System.out.println("----------ConsumptionController.init: Size of the object is: " + values.length);  
    	    for(int i = 0; i < values.length; ++i) {
    	         tmp[i] = String.valueOf(values[i]);
    	         //System.out.println("----------ConsumptionController.init: " + i + " value of object is: " + String.valueOf(values[i]));
    	    }
    		Date d1 = new Date();
    		Date d2 = new Date();	
    		DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    		try 
    		{
    			d1 = formatter.parse(tmp[12]);
    			d2 = formatter.parse(tmp[13]);
    			//System.out.println("----------ConsumptionController.init: " + d1);
    			//System.out.println("----------ConsumptionController.init: " + d2);
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
        
    	consumptionList = tList;
	}
}  