package consumption.managed.bean;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;

import user.managed.bean.UserManagedBean;
import consumption.model.ConsumptionGroupSelectView;
import consumption.service.IConsumptionService;

@ManagedBean(name="consumptionGroupSelect")
@SessionScoped
public class ConsumptionGroupSelect {
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
	
	List<ConsumptionGroupSelectView> consumptionGroupList;
	
	private int id;
	private String name;

	public void reset() {
		this.setId(0);
		this.setName("");
	}
	
	public List<ConsumptionGroupSelectView> getConsumptionGroupList() {
		List<ConsumptionGroupSelectView> tempList = new ArrayList<ConsumptionGroupSelectView>();
		tempList.addAll(getConsumptionService().getConsumptionGroupSelectView(user.getId()));
	    List<ConsumptionGroupSelectView> tList = new ArrayList();
	
	    for (Object o: tempList) {         
		    Object values[] = (Object[]) o;
		    String[] tmp = new String[values.length];
		    System.out.println("----------ConsumptionGroupSelect.getConsumptionGroupList: Size of the object is: " + values.length);  
		    for(int i = 0; i < values.length; ++i) {
		         tmp[i] = String.valueOf(values[i]);
		         //System.out.println("----------ConsumptionGroupSelect.getConsumptionGroupList: " + i + " value of object is: " + String.valueOf(values[i]));
		    }

		    ConsumptionGroupSelectView a = new ConsumptionGroupSelectView(Integer.parseInt(tmp[0]), (String) tmp[1]);
	    	tList.add(a);
	    }
	    
	    consumptionGroupList = tList;
	
		return consumptionGroupList;
	}
	
	public IConsumptionService getConsumptionService() {
		return consumptionService;
	}

	public void setConsumptionService(IConsumptionService consumptionService) {
		this.consumptionService = consumptionService;
	}
	
	public void setConsumptionGroupList(List<ConsumptionGroupSelectView> consumptionGroupList) {
		this.consumptionGroupList = consumptionGroupList;
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
