package currency.managed.bean;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;

import user.managed.bean.UserManagedBean;
import currency.model.CurrencySelectView;
import currency.service.ICurrencyService;

@ManagedBean(name="currencySelect")
@SessionScoped
public class CurrencySelect {
    
	//Spring Currency Service is injected...
	@ManagedProperty(value="#{CurrencyService}")
	ICurrencyService currencyService;
	
	List<CurrencySelectView> currencyList;
	
	private int id;
	private String name;

	public void reset() {
		this.setId(1);
		this.setName("hrk");
	}
	
	public List<CurrencySelectView> getCurrencyList() {
		List<CurrencySelectView> tempList = new ArrayList<CurrencySelectView>();
		tempList.addAll(getCurrencyService().getCurrencySelectView());
		//tempList.addAll(getCurrencyService().getCurrencySelectView(user.getId()));
	    List<CurrencySelectView> tList = new ArrayList();
	
	    for (Object o: tempList) {         
		    Object values[] = (Object[]) o;
		    String[] tmp = new String[values.length];
		    //System.out.println("----------CurrencySelect.getCurrencyList: Size of the object is: " + values.length);  
		    for(int i = 0; i < values.length; ++i) {
		         tmp[i] = String.valueOf(values[i]);
		         //System.out.println("----------CurrencySelect.getCurrencyList: " + i + " value of object is: " + String.valueOf(values[i]));
		    }

		    CurrencySelectView a = new CurrencySelectView(Integer.parseInt(tmp[0]), (String) tmp[1]);
	    	tList.add(a);
	    }
	    
	    currencyList = tList;
	
		return currencyList;
	}
	
	public ICurrencyService getCurrencyService() {
		return currencyService;
	}

	public void setCurrencyService(ICurrencyService currencyService) {
		this.currencyService = currencyService;
	}
	
	public void setCurrencyList(List<CurrencySelectView> currencyList) {
		this.currencyList = currencyList;
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
