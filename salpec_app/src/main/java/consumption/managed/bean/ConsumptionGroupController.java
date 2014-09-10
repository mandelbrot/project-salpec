package consumption.managed.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.primefaces.event.SelectEvent;
import org.primefaces.event.UnselectEvent;
import org.springframework.dao.DataAccessException;

import consumption.model.ConsumptionGroup;
import consumption.service.IConsumptionService;

import user.managed.bean.UserManagedBean;

@ManagedBean(name="consumptionGroupController")
@ViewScoped
public class ConsumptionGroupController implements Serializable{
		
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
	    
		//Spring Consumption Service is injected...
		@ManagedProperty(value="#{ConsumptionService}")
		IConsumptionService consumptionService;
		
		List<ConsumptionGroup> consumptiongroupList;

		private ConsumptionGroup selectedConsumptionGroup;
		private ConsumptionGroup temp;
		
		public ConsumptionGroupController() {
			//init();
		}
		
		//@PostConstrut
		public void init() {
			List<ConsumptionGroup> tempList = new ArrayList<ConsumptionGroup>();
			System.out.println("----------ConsumptionsGroupController.init: user is: " + user.getId());
			tempList.addAll(getConsumptionService().getConsumptionGroups(user.getId()));
	        List<ConsumptionGroup> tList = new ArrayList();

	        for (Object o: tempList) {         
	    	    Object values[] = (Object[]) o;
	    	    String[] tmp = new String[values.length];
	    	    //System.out.println("----------ConsumptionsGroupController.init: Size of the object is: " + values.length);  
	    	    for(int i = 0; i < values.length; ++i) {
	    	         tmp[i] = String.valueOf(values[i]);
	    	         //System.out.println("----------ConsumptionsGroupController.init: " + i + " value of object is: " + String.valueOf(values[i]));
	    	    }
	        	ConsumptionGroup tCG = new ConsumptionGroup(Integer.parseInt(tmp[0]), Integer.parseInt(tmp[1]), 
	        			(String) tmp[2], (String) tmp[3], (String) tmp[4]);

	        	tList.add(tCG);
	        }
	        
	    	consumptiongroupList = tList;
	    	
	    	selectedConsumptionGroup = new ConsumptionGroup(0, user.getId(), "", "1", "");
	    	if(temp == null)
	    		temp = selectedConsumptionGroup; 
	    	else
	    		System.out.println("----------ConsumptionsGroupController.init: TEMP: " + temp.toString());  
		}

		public IConsumptionService getConsumptionService() {
			return consumptionService;
		}

		public void setConsumptionService(IConsumptionService consumptionService) {
			this.consumptionService = consumptionService;
		}
		
		public List<ConsumptionGroup> getconsumptiongroupList() {
			return consumptiongroupList;
		}
		
		public void setconsumptiongroupList(List<ConsumptionGroup> consumptiongroupList) {
			this.consumptiongroupList = consumptiongroupList;
		}
		
		public ConsumptionGroup getSelectedConsumptionGroup() {
			if(temp == null)
			{
				selectedConsumptionGroup = new ConsumptionGroup(0, user.getId(), "", "1", "");
			}
			else
			{
				selectedConsumptionGroup = temp;
			}
            return selectedConsumptionGroup;
        }

        public void setSelectedConsumptionGroup(ConsumptionGroup cg) {
            this.selectedConsumptionGroup = cg;
        }
        
        public void setTemp(ConsumptionGroup temp) {
            this.temp = temp;
        }

        public ConsumptionGroup getTemp() {
            return temp;
        }
        
        public void onRowSelect(SelectEvent event) {        
        	selectedConsumptionGroup = (ConsumptionGroup) event.getObject();
        	temp = (ConsumptionGroup) event.getObject(); 
    	    
        	if(this.selectedConsumptionGroup == null)
        	{
        		System.out.println("----------ConsumptionsGroupController.onRowSelect: ConsumptionGroup nije odabrana!");
        	}
        	else
        	{
        		System.out.println("----------ConsumptionsGroupController.onRowSelect: " + selectedConsumptionGroup.toString());
        	}
        }

        public void onRowUnselect(UnselectEvent event) {
    		System.out.println("----------ConsumptionsGroupController.onRowUnselect: ConsumptionGroup nije odabrana! selectedConsumptionGroup = null");
        	this.selectedConsumptionGroup = null; 
        }

        public void ieChange() {
    		//System.out.println("----------ConsumptionsGroupController.ieChange: Promijenjen ie: " + selectedConsumptionGroup.getIeDefault());
        }
        
        public String deleteConsumptionGroup(ConsumptionGroup t) {  
    		System.out.println("----------ConsumptionsGroupController.deleteConsumptionGroup: Brisanje ConsumptionGroup: " + t.toString());
    		ConsumptionGroup t1 = new ConsumptionGroup(t.getId(), t.getIdUser(), t.getName(), 
    			t.getIeDefault(), t.getDescription());

    		if(t1.getIdUser() == 0) t1.setIdUser(user.getId());
    		
			try {		
				getConsumptionService().deleteConsumptionGroup(t1);
	        	consumptiongroupList.remove(t);  
	        	consumptiongroupList.remove(t1);  

				selectedConsumptionGroup = new ConsumptionGroup(0, user.getId(), "", "1", "");
				
				init();
				temp = selectedConsumptionGroup;
				
	            FacesMessage msg = new FacesMessage("ConsumptionGroup obrisana!", t.toString());  
	            FacesContext.getCurrentInstance().addMessage("growlMessage", msg); 
	            
				return SUCCESS;
			} catch (DataAccessException e) {
				e.printStackTrace();
	            FacesMessage msg = new FacesMessage("Greška prilikom brisanja ConsumptionGroup!", e.toString());  
	            FacesContext.getCurrentInstance().addMessage("growlMessage", msg); 
			} 	
		
			//init();
			
			selectedConsumptionGroup = new ConsumptionGroup(0, user.getId(), "", "1", "");
            
            return ERROR;
        }  
        
        public String updateConsumptionGroup(ConsumptionGroup t) {  
    		System.out.println("----------ConsumptionsGroupController.updateConsumptionGroup: Update ConsumptionGroup: " + t.toString());
    		ConsumptionGroup t1 = new ConsumptionGroup(t.getId(), t.getIdUser(), t.getName(), 
        			t.getIeDefault(), t.getDescription());
        	
    		if(t1.getIdUser() == 0) t1.setIdUser(user.getId());
    		
			try {
				getConsumptionService().saveOrUpdateConsumptionGroup(t1); //t1

				selectedConsumptionGroup = new ConsumptionGroup(0, user.getId(), "", "1", "");

				String poruka = "ConsumptionGroup ažurirana!";
				if(t1.getId() == 0) poruka = "ConsumptionGroup dodana!";
				
				init();
				//temp = null;
				temp = selectedConsumptionGroup;
				
	            FacesMessage msg = new FacesMessage(poruka, t1.toString());  
	            FacesContext.getCurrentInstance().addMessage("growlMessage", msg); 
	            
				return SUCCESS;
			} catch (DataAccessException e) {
				e.printStackTrace();
				String poruka = "ažuriranja";
				if(t1.getId() == 0) poruka = "dodavanja";
	            FacesMessage msg = new FacesMessage("Greška prilikom " + poruka + " consumptiongroup!", t1.toString() + e.toString());  
	            FacesContext.getCurrentInstance().addMessage("growlMessage", msg); 
	            
	            selectedConsumptionGroup = t1;
			} 	     

			//init();
			
			//selectedConsumptionGroup = new ConsumptionGroup(0, user.getId(), "", "1", "");
            
            return ERROR;
        }  

        public void saveConsumptionGroup(ConsumptionGroup t) {  
            selectedConsumptionGroup = t;
    		System.out.println("----------ConsumptionsGroupController.saveConsumptionGroup: ConsumptionGroup: " + selectedConsumptionGroup.toString());
        }
        
        public void resetConsumptionGroup() {  
    		System.out.println("----------ConsumptionsGroupController.resetConsumptionGroup: Resetiranje ConsumptionGroup o: " + selectedConsumptionGroup.toString());

    		selectedConsumptionGroup = new ConsumptionGroup(0, user.getId(), "", "1", "");//selectedConsumptionGroup.getIdUser()
        	
    		System.out.println("----------ConsumptionsGroupController.resetConsumptionGroup: ConsumptionGroup resetirana: " + selectedConsumptionGroup.toString());

			temp = selectedConsumptionGroup;
			
            FacesMessage msg = new FacesMessage("ConsumptionGroup resetirana!", selectedConsumptionGroup.toString());  
            FacesContext.getCurrentInstance().addMessage("growlMessage", msg); 
        }  
	}