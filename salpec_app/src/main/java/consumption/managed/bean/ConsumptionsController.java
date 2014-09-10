package consumption.managed.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.primefaces.event.SelectEvent;
import org.primefaces.event.UnselectEvent;
import org.springframework.dao.DataAccessException;

import consumption.model.Consumption;
import consumption.service.IConsumptionService;

import user.managed.bean.UserManagedBean;

@ManagedBean(name="consumptionsController")
@ViewScoped
public class ConsumptionsController implements Serializable{
		
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
		
		List<Consumption> consumptionList;

		private Consumption selectedConsumption;
		private Consumption temp;
		
		public ConsumptionsController() {
			//init();
		}
		
		public void init() {
			List<Consumption> tempList = new ArrayList<Consumption>();
			System.out.println("----------ConsumptionsController.init: user is: " + user.getId());
			tempList.addAll(getConsumptionService().getConsumptions(user.getId()));
	        List<Consumption> tList = new ArrayList();

	        for (Object o: tempList) {         
	    	    Object values[] = (Object[]) o;
	    	    String[] tmp = new String[values.length];
	    	    //System.out.println("----------ConsumptionsController: Size of the object is: " + values.length);  
	    	    for(int i = 0; i < values.length; ++i) {
	    	         tmp[i] = String.valueOf(values[i]);
	    	         //System.out.println("----------ConsumptionsController: " + i + " value of object is: " + String.valueOf(values[i]));
	    	    }
	    	    Consumption tCG = new Consumption(Integer.parseInt(tmp[0]), Integer.parseInt(tmp[1]), Integer.parseInt(tmp[2]), 
	        			(String) tmp[3], (String) tmp[4], (String) tmp[5]);

	        	tList.add(tCG);
	        }
	        
	    	consumptionList = tList;
	    	
	    	selectedConsumption = new Consumption(0, user.getId(), 0, "", "1", "");
	    	if(temp == null)
	    		temp = selectedConsumption; 
	    	else
	    		System.out.println("----------ConsumptionsController.init: TEMP: " + temp.toString());  
		}

		public IConsumptionService getConsumptionService() {
			return consumptionService;
		}

		public void setConsumptionService(IConsumptionService consumptionService) {
			this.consumptionService = consumptionService;
		}
		
		public List<Consumption> getconsumptionList() {
			return consumptionList;
		}
		
		public void setconsumptionList(List<Consumption> consumptionList) {
			this.consumptionList = consumptionList;
		}
		
		public Consumption getSelectedConsumption() {
			if(temp == null)
			{
				selectedConsumption = new Consumption(0, user.getId(), 0, "", "1", "");
			}
			else
			{
				selectedConsumption = temp;
			}
            return selectedConsumption;
        }

        public void setSelectedConsumption(Consumption cg) {
            this.selectedConsumption = cg;
        }
        
        public void setTemp(Consumption temp) {
            this.temp = temp;
        }

        public Consumption getTemp() {
            return temp;
        }
        
        public void onRowSelect(SelectEvent event) {        
        	selectedConsumption = (Consumption) event.getObject();
        	temp = (Consumption) event.getObject(); 
    	    
        	if(this.selectedConsumption == null)
        	{
        		System.out.println("----------ConsumptionsController.onRowSelect: Consumption nije odabrana!");
        	}
        	else
        	{
        		System.out.println("----------ConsumptionsController.onRowSelect:" + selectedConsumption.toString());
        	}
        }

        public void onRowUnselect(UnselectEvent event) {
    		System.out.println("----------ConsumptionsController.onRowUnselect: Consumption nije odabrana! selectedConsumption = null");
        	this.selectedConsumption = null; 
        }

        public void ieChange() {
    		//System.out.println("----------ConsumptionsController: Promijenjen ie: " + selectedConsumption.getIeDefault());
        	
        }
        
        public void consumptionGroupSelected() {
    		//System.out.println("Selektiran consumptionGroup id: " + selectedConsumption.getIdConsumptionGroup());
        	
        }
        
        public String deleteConsumption(Consumption t) {  
    		System.out.println("----------ConsumptionsController.deleteConsumption: Brisanje Consumption: " + t.toString());
    		Consumption t1 = new Consumption(t.getId(), t.getIdUser(), t.getIdConsumptionGroup(), t.getName(), 
    			t.getIeDefault(), t.getDescription());

    		if(t1.getIdUser() == 0) t1.setIdUser(user.getId());
    		
			try {		
				getConsumptionService().deleteConsumption(t1);
	        	consumptionList.remove(t);  
	        	consumptionList.remove(t1);  

				selectedConsumption = new Consumption(0, user.getId(), 0, "", "1", "");
				
				init();
				temp = selectedConsumption;
				
	            FacesMessage msg = new FacesMessage("Consumption obrisana!", t.toString());  
	            FacesContext.getCurrentInstance().addMessage("growlMessage", msg); 
	            
				return SUCCESS;
			} catch (DataAccessException e) {
				e.printStackTrace();
	            FacesMessage msg = new FacesMessage("Greška prilikom brisanja Consumption!", e.toString());  
	            FacesContext.getCurrentInstance().addMessage("growlMessage", msg); 
			} 	
		
			//init();
			
			selectedConsumption = new Consumption(0, user.getId(), 0, "", "1", "");
            
            return ERROR;
        }  
        
        public String updateConsumption(Consumption t) {  
    		System.out.println("----------ConsumptionsController.updateConsumption: Update Consumption: " + t.toString());
    		Consumption t1 = new Consumption(t.getId(), t.getIdUser(), t.getIdConsumptionGroup(), t.getName(), 
        			t.getIeDefault(), t.getDescription());
        	
    		if(t1.getIdUser() == 0) t1.setIdUser(user.getId());
    		
			try {
				getConsumptionService().saveOrUpdateConsumption(t1); //t1

				selectedConsumption = new Consumption(0, user.getId(), 0, "", "1", "");

				String poruka = "Consumption ažurirana!";
				if(t1.getId() == 0) poruka = "Consumption dodana!";
				
				init();
				//temp = null;
				temp = selectedConsumption;
				
	            FacesMessage msg = new FacesMessage(poruka, t1.toString());  
	            FacesContext.getCurrentInstance().addMessage("growlMessage", msg); 
	            
				return SUCCESS;
			} catch (DataAccessException e) {
				e.printStackTrace();
				String poruka = "ažuriranja";
				if(t1.getId() == 0) poruka = "dodavanja";
	            FacesMessage msg = new FacesMessage("Greška prilikom " + poruka + " consumption!", t1.toString() + e.toString());  
	            FacesContext.getCurrentInstance().addMessage("growlMessage", msg); 
	            
	            selectedConsumption = t1;
			} 	     

			//init();
			
			//selectedConsumption = new Consumption(0, user.getId(), "", "1", "");
            
            return ERROR;
        }  

        public void saveConsumption(Consumption t) {  
            selectedConsumption = t;
    		System.out.println("----------ConsumptionsController.saveConsumption: Consumption: " + selectedConsumption.toString());
        }
        
        public void resetConsumption() {  
    		System.out.println("----------ConsumptionsController.resetConsumption: Resetiranje Consumption o: " + selectedConsumption.toString());

    		selectedConsumption = new Consumption(0, user.getId(), 0, "", "1", "");
        	
    		System.out.println("----------ConsumptionsController.resetConsumption: Consumption resetirana: " + selectedConsumption.toString());

			temp = selectedConsumption;
			
            FacesMessage msg = new FacesMessage("Consumption resetirana!", selectedConsumption.toString());  
            FacesContext.getCurrentInstance().addMessage("growlMessage", msg); 
        }  
	}