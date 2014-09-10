package consumption;  
  
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

@ManagedBean(name="consumptionController")
@SessionScoped
public class ConsumptionController implements Serializable{  
	
	private static final long serialVersionUID = 1L;
	@ManagedProperty("#{userMB}")
    private UserManagedBean user;
    
    public void setUser(UserManagedBean user) {
        this.user = user;
    }

    public UserManagedBean getUser() {
        return user;
    }

	private String name; // +setter
	 
    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
    
	public void editAction() 
	{
		System.out.println("editAction: ");
		//Map<String,String> params = FacesContext.getExternalContext().getRequestParameterMap();
		//String action = params.get("name");
		setName("tetete");
	}
	  
	public void attrListener(ActionEvent event)
	{
		System.out.println("attrListener: ");
		String action = (String)event.getComponent().getAttributes().get("name");
		System.out.println("attrListener: " + action);
		setName(action);
	}
	
	//Spring Consumption Service is injected...
	@ManagedProperty(value="#{ConsumptionService}")
	IConsumptionService consumptionService;
	
    private TreeNode root;  
	List<ConsumptionGroup> consumptionList;
  
    public ConsumptionController() {  
/*        root = new DefaultTreeNode("root", null);  
  
        TreeNode documents = new DefaultTreeNode("grupa1", "Plata", root);  
        TreeNode pictures = new DefaultTreeNode("grupa2", "Rezije", root);  
        TreeNode movies = new DefaultTreeNode("grupa3", "Ostalo", root);  
  
        TreeNode work = new DefaultTreeNode("leaf11", "Plata", documents);  
        TreeNode primefaces = new DefaultTreeNode("leaf12", "Dividenda", documents);  
  
        TreeNode barca = new DefaultTreeNode("leaf21", "Struja", pictures);  
        TreeNode primelogo = new DefaultTreeNode("leaf22", "Voda", pictures);  
        TreeNode optimus = new DefaultTreeNode("leaf23", "Plin", pictures);  
  
        TreeNode pacino = new DefaultTreeNode("leaf31", "Bonovi", movies);  */ 
    	
        
    }  
    
    private MenuModel model;  

    /*model = new DefaultMenuModel();  
    
    //First submenu  
    Submenu submenu = new Submenu();  
    submenu.setLabel("Dynamic Submenu 1");  
      
    MenuItem item = new MenuItem();  
    item.setValue("Dynamic Menuitem 1.1");  
    item.setUrl("#");  
    submenu.getChildren().add(item);  
      
    model.addSubmenu(submenu);  
      
    //Second submenu  
    submenu = new Submenu();  
    submenu.setLabel("Dynamic Submenu 2");  
      
    item = new MenuItem();  
    item.setValue("Dynamic Menuitem 2.1");  
    item.setUrl("#");  
    submenu.getChildren().add(item);  
      
    item = new MenuItem();  
    item.setValue("Dynamic Menuitem 2.2");  
    item.setUrl("#");  
    submenu.getChildren().add(item);  
      
    model.addSubmenu(submenu); */
    
    private TreeNode createNode(String tag, Object data, TreeNode parent) {
        TreeNode node = new DefaultTreeNode(tag, data, parent); 
        // Create Dummy node, just to make the parent node expandable
        //new DefaultTreeNode("DUMMY", node);
        return node;
    }
    
    //@PostConstruct
    public void getConsumptionListByUser() {

    	FacesContext ctx = FacesContext.getCurrentInstance();
    	String viewId = ctx.getViewRoot().getViewId();
		System.out.println("Trenutna stranica: " + viewId);
    	if(viewId.equals("/consumptiongroups.xhtml")) return;
		
        System.out.println("getting consumption groups for user: " + user.getName() + user.getId());
        
		List<ConsumptionGroup> tempList = new ArrayList<ConsumptionGroup>();
		tempList.addAll(getConsumptionService().getConsumptionGroups(user.getId()));

        root = new DefaultTreeNode("Root", null); 
        
	    for (Object o: tempList) {         
		    Object values[] = (Object[]) o;
	        System.out.println("......" + " consumption group id: " + String.valueOf(values[0]) + 
	        		" user id: " + String.valueOf(values[1]) + 
	        		" name: " + String.valueOf(values[2]));
	         
	        
	        // u object data poslati id consumption i name consumption
	        String names[];
	        names = new String[] {String.valueOf(values[0]),String.valueOf(values[2])};
	        
	        TreeNode node = createNode("ConsumptionGroup", names, root);

			List<Consumption> tempListNode = new ArrayList<Consumption>();
			tempListNode.addAll(getConsumptionService().getConsumptionsByGroup(Integer.parseInt(String.valueOf(values[0]))));
			
		    for (Object oNode: tempListNode) {         
			    Object valuesNode[] = (Object[]) oNode;
		        System.out.println("......" + " consumption id: " + String.valueOf(valuesNode[0]) + 
		        		" user id: " + String.valueOf(valuesNode[1]) + 
		        		" consumption group id: " + String.valueOf(valuesNode[2]) + 
		        		" name: " + String.valueOf(valuesNode[3]) +
		        		" ie_default: " + String.valueOf(valuesNode[4]));

		        names = new String[] {String.valueOf(valuesNode[0]),String.valueOf(valuesNode[3])};
		        createNode("Consumption", names, node);
		    }
	    }
	}
    
    public void setRoot(TreeNode root) {
        this.root = root;
    }

    public TreeNode getRoot() {  	
        System.out.println("......" + " consumption groups: ");
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