package consumption.managed.bean;  
  
import java.io.Serializable;  
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
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

import user.managed.bean.UserManagedBean;
  
import consumption.model.Consumption;
import consumption.model.ConsumptionGroup;
import consumption.service.IConsumptionService;

@ManagedBean(name="consumptionMenuController")
@SessionScoped
public class ConsumptionMenuController implements Serializable{  
	
	private static final long serialVersionUID = 1L;
	@ManagedProperty("#{userMB}")
    private UserManagedBean user;
    
    public void setUser(UserManagedBean user) {
        this.user = user;
    }

    public UserManagedBean getUser() {
        return user;
    }

	private String name;
	 
    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
 
	//Spring Consumption Service is injected...
	@ManagedProperty(value="#{ConsumptionService}")
	IConsumptionService consumptionService;
	
    private TreeNode root;  
	List<ConsumptionGroup> consumptionList;
  
    public ConsumptionMenuController() {  
    }  
    
    private MenuModel model;  

    private TreeNode createNode(String tag, Object data, TreeNode parent) {
        TreeNode node = new DefaultTreeNode(tag, data, parent); 
        return node;
    }
    
    //@PostConstruct
    public void getConsumptionListByUser() {

    	FacesContext ctx = FacesContext.getCurrentInstance();
    	String viewId = ctx.getViewRoot().getViewId();
		System.out.println("----------ConsumptionMenuController.getConsumptionListByUser: Trenutna stranica: " + viewId);
		//ako je trenutna stranica ova onda staviti da se ne loada!!! jer inaèe dolazi do concurrency greške
		//u buduænosti složiti da se dodaje dinamièki list na consumption menu kad se dodaje consumption!!!
    	if(viewId.equals("/consumptiongroups.xhtml") || viewId.equals("/consumptions.xhtml") 
    		|| viewId.equals("/transaction.xhtml") || viewId.equals("/transactions.xhtml") ||viewId.equals("/consumption.xhtml") 
    		|| viewId.equals("/account.xhtml") || viewId.equals("/users.xhtml")) return;
		
        System.out.println("----------ConsumptionMenuController.getConsumptionListByUser: getting consumption groups for user: " + user.getName() + user.getId());
        
		List<ConsumptionGroup> tempList = new ArrayList<ConsumptionGroup>();
		tempList.addAll(getConsumptionService().getConsumptionGroups(user.getId()));

        root = new DefaultTreeNode("Root", null); 
        
	    for (Object o: tempList) {         
		    Object values[] = (Object[]) o;
	        //System.out.println("----------ConsumptionMenuController.getConsumptionListByUser: " + " consumption group id: " + String.valueOf(values[0]) + 
	        //		" user id: " + String.valueOf(values[1]) + " name: " + String.valueOf(values[2]));
	         
	        String names[];
	        names = new String[] {String.valueOf(values[0]),String.valueOf(values[2])};
	        
	        TreeNode node = createNode("ConsumptionGroup", names, root);

			List<Consumption> tempListNode = new ArrayList<Consumption>();
			tempListNode.addAll(getConsumptionService().getConsumptionsByGroup(Integer.parseInt(String.valueOf(values[0]))));
			
		    for (Object oNode: tempListNode) {         
			    Object valuesNode[] = (Object[]) oNode;
		        /*System.out.println("----------ConsumptionMenuController.getConsumptionListByUser: " + " consumption id: " + String.valueOf(valuesNode[0]) + 
		        		" user id: " + String.valueOf(valuesNode[1]) + 
		        		" consumption group id: " + String.valueOf(valuesNode[2]) + 
		        		" name: " + String.valueOf(valuesNode[3]) +
		        		" ie_default: " + String.valueOf(valuesNode[4]));*/

		        names = new String[] {String.valueOf(valuesNode[0]),String.valueOf(valuesNode[3])};
		        createNode("Consumption", names, node);
		    }
	    }
	}
    
    public void setRoot(TreeNode root) {
        this.root = root;
    }

    public TreeNode getRoot() {  	
        System.out.println("----------ConsumptionMenuController.getRoot: consumption groups: ");
        getConsumptionListByUser();
        return root;  
    }  	
    
	public IConsumptionService getConsumptionService() {
		return consumptionService;
	}

	public void setConsumptionService(IConsumptionService consumptionService) {
		this.consumptionService = consumptionService;
	}
	
	public void setConsumptionList(List<ConsumptionGroup> consumptionList) {
		this.consumptionList = consumptionList;
	}
}  