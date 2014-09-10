package consumption.managed.bean;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;

import user.managed.bean.UserManagedBean;
import consumption.model.ConsumptionSelectView;
import consumption.service.IConsumptionService;

@ManagedBean(name="consumptionSelect")
@SessionScoped
public class ConsumptionSelect {
	@ManagedProperty("#{userMB}")
    private UserManagedBean user;
    
    public void setUser(UserManagedBean user) {
        this.user = user;
    }

    public UserManagedBean getUser() {
        return user;
    }
    
	//Spring Consumption Service is injected...
	@ManagedProperty(value="#{ConsumptionService}")
	IConsumptionService consumptionService;
	
	List<ConsumptionSelectView> consumptionList;
	
	private int id;
	private String name;

	public void reset() {
		this.setId(0);
		this.setName("");
	}
	
	public List<ConsumptionSelectView> getConsumptionList() {
		List<ConsumptionSelectView> tempList = new ArrayList<ConsumptionSelectView>();
		tempList.addAll(getConsumptionService().getConsumptionSelectView(user.getId()));
	    List<ConsumptionSelectView> tList = new ArrayList();
	
	    for (Object o: tempList) {         
		    Object values[] = (Object[]) o;
		    String[] tmp = new String[values.length];
		    //System.out.println("----------ConsumptionSelect.getConsumptionList: Size of the object is: " + values.length);  
		    for(int i = 0; i < values.length; ++i) {
		         tmp[i] = String.valueOf(values[i]);
		         //System.out.println("----------ConsumptionSelect.getConsumptionList:" + i + " value of object is: " + String.valueOf(values[i]));
		    }

		    ConsumptionSelectView a = new ConsumptionSelectView(Integer.parseInt(tmp[0]), (String) tmp[1]);
	    	tList.add(a);
	    }
	    
	    consumptionList = tList;
	
		return consumptionList;
	}
	
	public IConsumptionService getConsumptionService() {
		return consumptionService;
	}

	public void setConsumptionService(IConsumptionService consumptionService) {
		this.consumptionService = consumptionService;
	}
	
	public void setConsumptionList(List<ConsumptionSelectView> consumptionList) {
		this.consumptionList = consumptionList;
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
