package consumption.managed.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.tagcloud.DefaultTagCloudItem;
import org.primefaces.model.tagcloud.DefaultTagCloudModel;
import org.primefaces.model.tagcloud.TagCloudItem;
import org.primefaces.model.tagcloud.TagCloudModel;

import consumption.model.Consumption;
import consumption.service.IConsumptionService;

import user.managed.bean.UserManagedBean;
 
@ManagedBean(name="tagCloudView")
@ViewScoped 
public class TagCloudView implements Serializable{

	private static final long serialVersionUID = 1L;
    private TagCloudModel model;
    private TagCloudModel model1;
	
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
	
    @PostConstruct
    public void init() {
		List<String[]> tempList = new ArrayList<String[]>();
		System.out.println("----------ConsumptionsController.init: user is: " + user.getId());
		tempList.addAll(getConsumptionService().getConsumptionCount(user.getId()));
		
        model = new DefaultTagCloudModel();
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
    	    model.addTag(new DefaultTagCloudItem(tmp[0], "consumption.xhtml?cid=" + tmp[1], count/2));
    	    count--;
        }
        
        model1 = new DefaultTagCloudModel();
        model1.addTag(new DefaultTagCloudItem("Transformers", 5));
        model1.addTag(new DefaultTagCloudItem("RIA", "#", 4));
        model1.addTag(new DefaultTagCloudItem("AJAX", 3));
        model1.addTag(new DefaultTagCloudItem("jQuery", "#", 4));
        model1.addTag(new DefaultTagCloudItem("NextGen", 4));
        model1.addTag(new DefaultTagCloudItem("JSF 2.0", "#", 1));
        model1.addTag(new DefaultTagCloudItem("FCB", 5));
        model1.addTag(new DefaultTagCloudItem("Mobile",  1));
        model1.addTag(new DefaultTagCloudItem("Themes", "#", 2));
        model1.addTag(new DefaultTagCloudItem("Rocks", "#", 4));
    }

	public IConsumptionService getConsumptionService() {
		return consumptionService;
	}

	public void setConsumptionService(IConsumptionService consumptionService) {
		this.consumptionService = consumptionService;
	}
	
    public TagCloudModel getModel() {
        return model;
    }

    public TagCloudModel getModel1() {
        return model1;
    }
    
    public void onSelect(SelectEvent event) {
        TagCloudItem item = (TagCloudItem) event.getObject();
        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Item Selected", item.getLabel());
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }
}