package language;
 
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
 
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;

import org.springframework.beans.propertyeditors.LocaleEditor;

import user.dao.UserDAO;
import user.managed.bean.UserController;
import user.managed.bean.UserManagedBean;
import user.model.User;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import java.util.Locale;
import java.util.ResourceBundle;

import javax.servlet.http.Cookie;
@ManagedBean(name="language")
@SessionScoped
public class LanguageBean implements Serializable{
 
	private static final long serialVersionUID = 1L;
 
	private String localeCode;
	
    @ManagedProperty("#{userMB}")
    private UserManagedBean user;
    
    public LanguageBean(){
    	if(user != null){
    		localeCode = user.getLanguage();
			this.setLocaleCode(user.getLanguage());         
			LocaleEditor localeEditor = new LocaleEditor();
	        localeEditor.setAsText(user.getLanguage());
			FacesContext.getCurrentInstance()
				.getViewRoot().setLocale((Locale) localeEditor.getValue());	        	
			FacesContext.getCurrentInstance()
	        	 .getExternalContext()
	        	 .addResponseCookie("CookieLanguage", user.getLanguage(), null);
    	}
    	else{
/*    		localeCode = "zh_CN";
			this.setLocaleCode("zh_CN");	            
			LocaleEditor localeEditor = new LocaleEditor();
	        localeEditor.setAsText("zh_CN");
			FacesContext.getCurrentInstance()
				.getViewRoot().setLocale((Locale) localeEditor.getValue());    	
			FacesContext.getCurrentInstance()
			    .getExternalContext()
			    .addResponseCookie("CookieLanguage", "zh_CN", null);*/
        	Map<String, Object> cookies = FacesContext.getCurrentInstance().getExternalContext().getRequestCookieMap();
        	Cookie cookieLanguage = (Cookie) cookies.get("CookieLanguage");

        	if(cookieLanguage != null){
	        	System.out.println("----------LanguageBean.LanguageBean: trenutni cookie je: " + cookieLanguage.getValue());
				LocaleEditor localeEditor = new LocaleEditor();
		        localeEditor.setAsText(cookieLanguage.getValue());
				FacesContext.getCurrentInstance()
					.getViewRoot().setLocale((Locale) localeEditor.getValue());     
        	}
        	else
        	{
	        	System.out.println("----------LanguageBean.LanguageBean: trenutni cookie je: nema ga!");
        	}	
    	}
    	System.out.println("----------LanguageBean.LanguageBean: Jezik postavljen na : " + localeCode);
    }
    
    public void init(){
		/*this.setLocaleCode("zh_CN");	            
		LocaleEditor localeEditor = new LocaleEditor();
        localeEditor.setAsText("zh_CN");
		FacesContext.getCurrentInstance()
			.getViewRoot().setLocale((Locale) localeEditor.getValue());

    	FacesContext.getCurrentInstance()
       	 .getExternalContext()
       	 .addResponseCookie("CookieLanguage", "ch_ZN", null);
    	FacesContext.getCurrentInstance()
      	 .getExternalContext()
      	 .addResponseCookie("CookieLanguage1", "ch_ZN", null);*/
    	
    	Map<String, Object> cookies = FacesContext.getCurrentInstance().getExternalContext().getRequestCookieMap();
    	Cookie cookieLanguage = (Cookie) cookies.get("CookieLanguage");
    		
	    if (cookieLanguage != null) {	
			System.out.println("----------LanguageBean.init: cookie:" + cookieLanguage.getValue());

			this.setLocaleCode(cookieLanguage.getValue());	            
			LocaleEditor localeEditor = new LocaleEditor();
	        localeEditor.setAsText(cookieLanguage.getValue());
			FacesContext.getCurrentInstance()
				.getViewRoot().setLocale((Locale) localeEditor.getValue());  
			
	    	System.out.println("----------LanguageBean.init: LanguageBean-init1:Jezik postavljen na : " + 
	    			FacesContext.getCurrentInstance().getViewRoot().getLocale());  	
	    }
	    else{
	    	System.out.println("----------LanguageBean.init: LanguageBean-init2:Jezik postavljen na : " + 
    			FacesContext.getCurrentInstance().getViewRoot().getLocale());
	    }
    }
    
    public void setUser(UserManagedBean user) {
        this.user = user;
    }

    public UserManagedBean getUser() {
        return user;
    }
    
	private static Map<String,Object> countries;
	static{
		countries = new LinkedHashMap<String,Object>();
		Locale l = new Locale("hr", "HR");
		countries.put("Hrvatski", l);
		countries.put("Chinese", Locale.SIMPLIFIED_CHINESE);
		l = new Locale("en", "EN");
		countries.put("English", l);
	}

	public Map<String, Object> getCountriesInMap() {
		return countries;
	}
	
	public List<SelectItem> getCountriesInMap2(){
		/*List<String,Object> list = countries;
		List<SelectItem> resultList = new ArrayList<SelectItem>();
		for (ItemType it : list){
			SelectItem item = new SelectItem(it.getKey(), it.getValue()); 
			resultList.add(item);
		}
		return resultList;*/
		
		//ili ovo:
		List<SelectItem> resultList2 = new ArrayList<SelectItem>();
		resultList2.add(new SelectItem("Hrvatski", "hr_HR"));
		resultList2.add(new SelectItem("English", "en_EN"));
		resultList2.add(new SelectItem("Chinese", "ch_ZN"));
		return resultList2;
	}
  
	public String getLocaleCode() {
		return localeCode;
	}
 
	public void setLocaleCode(String localeCode) {
		this.localeCode = localeCode;
	}
 
	public void countryLocaleCodeChanged(ValueChangeEvent e){
 
		String newLocaleValue = e.getNewValue().toString();
 
		//loop country map to compare the locale code
        for (Map.Entry<String, Object> entry : countries.entrySet()) 
        {
        	if(entry.getValue().toString().equals(newLocaleValue))
        	{
        		FacesContext.getCurrentInstance()
        			.getViewRoot().setLocale((Locale)entry.getValue());

	        	System.out.println("----------LanguageBean.countryLocaleCodeChanged: Jezik postavljen na : " + entry.getValue().toString());
	        	
	        	Map<String, Object> cookies = FacesContext.getCurrentInstance().getExternalContext().getRequestCookieMap();
	        	Cookie cookieLanguage = (Cookie) cookies.get("CookieLanguage");

	        	if(cookieLanguage != null){
		        	System.out.println("----------LanguageBean.countryLocaleCodeChanged: trenutni cookie je: " + cookieLanguage.getValue());
		        	cookieLanguage.setValue(entry.getValue().toString());
		        	cookieLanguage.setPath("/");
		        	FacesContext facesContext = FacesContext.getCurrentInstance();
		    	  	((HttpServletResponse)facesContext.getExternalContext().getResponse()).addCookie(cookieLanguage);   
	        	}
	        	else
	        	{
		        	System.out.println("----------LanguageBean.countryLocaleCodeChanged: trenutni cookie je: nema ga!");
		        	Cookie c = new Cookie ("CookieLanguage", entry.getValue().toString());
		        	c.setMaxAge(24*60*60);
		        	c.setPath("/");
	        		FacesContext facesContext = FacesContext.getCurrentInstance();
		    	  	((HttpServletResponse)facesContext.getExternalContext().getResponse()).addCookie(c);  
	        	}	        	     	

	        	cookies = FacesContext.getCurrentInstance().getExternalContext().getRequestCookieMap();
	        	cookieLanguage = (Cookie) cookies.get("CookieLanguage");
	        	
        		System.out.println("----------LanguageBean.countryLocaleCodeChanged: " + (cookieLanguage != null ? "postavljen cookie:" + cookieLanguage.getValue() : 
        			"nema cookieja!"));     	
	    			
    			User current = user.getCurrent();
    			if(current != null)
    			{
	    			current.setLanguage(entry.getValue().toString());
	    			this.setLocaleCode(entry.getValue().toString());
	    			user.updateUser();
    			}
        	}
        }
	}
}