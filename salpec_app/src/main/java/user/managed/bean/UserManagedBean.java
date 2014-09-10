package user.managed.bean;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.faces.application.ConfigurableNavigationHandler;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ComponentSystemEvent;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import language.LanguageBean;

import org.springframework.beans.propertyeditors.LocaleEditor;
import org.springframework.dao.DataAccessException;

import user.model.User;
import user.service.IUserService;

@ManagedBean(name="userMB")
@SessionScoped
public class UserManagedBean implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private static final String SUCCESS = "success";
	private static final String ERROR   = "error";
    private User current;

	//Spring User Service is injected...
	@ManagedProperty(value="#{UserService}")
	IUserService userService;

/*    @ManagedProperty("#{language}")
    private LanguageBean languageBean;
    
    public void setLanguageBean(LanguageBean languageBean) {
        this.languageBean = languageBean;
    }

    public LanguageBean getLanguageBean() {
        return languageBean;
    }*/
    
	List<User> userList;
	
	private int id;
	private String name;
	private String pass;
	private String email;
	private String language;
	private String role;

	public String addUser() {
		try {
			User user = new User();
			user.setId(getId());
			user.setName(getName());
			user.setPass(getPass());
			user.setEmail(getEmail());
			user.setLanguage(getLanguage());
			user.setRole(getRole());
			getUserService().addUser(user);
			return SUCCESS;
		} catch (DataAccessException e) {
			e.printStackTrace();
		} 	
		
		return ERROR;
	}
	
	public String updateUser() {
		try {
			User user = new User();
			user.setId(getId());
			user.setName(getName());
			user.setPass(getPass());
			user.setEmail(getEmail());
			user.setLanguage(getLanguage());
			user.setRole(getRole());
			getUserService().updateUser(current);
			return SUCCESS;
		} catch (DataAccessException e) {
			e.printStackTrace();
		} 	
		
		return ERROR;
	}
	
	public String login() {
		try {
			setCurrent(getUserService().login(getName(), getPass()));
			User u = getCurrent();
			if(u != null)
			{
				this.setId(u.getId());
				this.setName(u.getName());
				this.setPass(u.getPass());
				this.setEmail(u.getEmail());
				this.setLanguage(u.getLanguage());
				this.setRole(u.getRole());
				//Locale locale = new Locale(getCurrent().getLanguage());
				//FacesContext.getCurrentInstance().getViewRoot().setLocale(locale);
				
				//language_menu.triggerChange();
	            FacesMessage msg = new FacesMessage("Login successful!", "");  
	            FacesContext.getCurrentInstance().addMessage("growlMessage", msg); 
	            
	            LocaleEditor localeEditor = new LocaleEditor();
	            localeEditor.setAsText(this.getLanguage());

	        	System.out.println("----------UserManagedBean.login: Jezik postavljen na : " + this.getLanguage());
	        	
        		FacesContext.getCurrentInstance()
    				.getViewRoot().setLocale((Locale) localeEditor.getValue());

	        	Map<String, Object> cookies = FacesContext.getCurrentInstance().getExternalContext().getRequestCookieMap();
	        	Cookie cookieLanguage = (Cookie) cookies.get("CookieLanguage");

	        	if(cookieLanguage != null){
		        	cookieLanguage.setValue(this.getLanguage());
		        	cookieLanguage.setPath("/");
	        	}
	        	else
	        	{
		        	Cookie c = new Cookie ("CookieLanguage", this.getLanguage());
		        	c.setMaxAge(24*60*60);
		        	c.setPath("/");
	        		FacesContext facesContext = FacesContext.getCurrentInstance();
		    	  	((HttpServletResponse)facesContext.getExternalContext().getResponse()).addCookie(c);  
	        	}

	        	cookies = FacesContext.getCurrentInstance().getExternalContext().getRequestCookieMap();
	        	cookieLanguage = (Cookie) cookies.get("CookieLanguage");
	        	
	        	if(cookieLanguage != null)
	        		System.out.println("----------UserManagedBean.login: postavljen cookie:" + cookieLanguage != null ? cookieLanguage.getValue() : "NE!");
	        	
				return SUCCESS;
			}
			else
			{
				setCurrent(null);
				FacesContext.getCurrentInstance().addMessage("growlMessage", new FacesMessage("Unknown login, try again"));
				return ERROR;
			}
		} catch (DataAccessException e) {
			e.printStackTrace();
		} 	
		
		setCurrent(null);
		return ERROR;
	}
	
    public String loginPage() {
        return "login?faces-redirect=true";
    }
	
    public String registerPage() {
        return "register";
    }
    
    public String userPage() {
        return "user?faces-redirect=true";
    }
    
    public String logout() {
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        return "logout";
    }

    public boolean isLoggedIn() {
    	if(getCurrent() != null)
    	{
			//Locale locale = new Locale(getCurrent().getLanguage());
			//FacesContext.getCurrentInstance().getViewRoot().setLocale(locale);
    	}
        return getCurrent() != null;
    }
    
    public void init()
    {
    	if(getCurrent() != null)
    	{
			//Locale locale = new Locale(getCurrent().getLanguage());
			//FacesContext.getCurrentInstance().getViewRoot().setLocale(locale);
    		if(getCurrent().getLanguage() != null)
    		{
				LocaleEditor localeEditor = new LocaleEditor();
		        localeEditor.setAsText(getCurrent().getLanguage());
				FacesContext.getCurrentInstance()
					.getViewRoot().setLocale((Locale) localeEditor.getValue());
    		}
    	}    	
    	else
    	{
	    	Map<String, Object> cookies = FacesContext.getCurrentInstance().getExternalContext().getRequestCookieMap();
	    	Cookie cookieLanguage = (Cookie) cookies.get("CookieLanguage");
	    		
		    if (cookieLanguage != null) {	
				System.out.println("----------UserManagedBean.init: cookie:" + cookieLanguage.getValue());
		            
				LocaleEditor localeEditor = new LocaleEditor();
		        localeEditor.setAsText(cookieLanguage.getValue());
				FacesContext.getCurrentInstance()
					.getViewRoot().setLocale((Locale) localeEditor.getValue());  
				
		    	System.out.println("----------UserManagedBean.init1: Jezik postavljen na : " + 
		    			FacesContext.getCurrentInstance().getViewRoot().getLocale());  	
		    }
		    else{
		    	System.out.println("----------UserManagedBean.init2: Jezik postavljen na : " + 
	    			FacesContext.getCurrentInstance().getViewRoot().getLocale());
		    }
    	}	        	
    }
    
    public void checkAuthentication() throws IOException {
        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();

        if (isLoggedIn()) {
            externalContext.redirect(externalContext.getRequestContextPath() + "/index.xhtml");
        }
    }
    
    public void checkAuthentication2() throws IOException {
        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();

        if (!isLoggedIn()) {
            externalContext.redirect(externalContext.getRequestContextPath() + "/index.xhtml");
        }
    }

	public boolean hasAccounts() {
		try {
			System.out.println("----------UserManagedBean.hasAccounts: user: " + getCurrent().toString());
			if(getCurrent() == null) return false;
			boolean t = getUserService().hasAccounts(getCurrent().getId());
			System.out.println("----------UserManagedBean.hasAccounts: user has accounts: " + t);
			return t;
		} catch (DataAccessException e) {
			e.printStackTrace();
			return false;
		} 	
	}
	
/*    public void isAdmin(ComponentSystemEvent event){
   
    	FacesContext fc = FacesContext.getCurrentInstance();
   
	  	if (!"admin".equals(fc.getExternalContext().getSessionMap().get("role"))){
	   
	  		ConfigurableNavigationHandler nav 
	  		   = (ConfigurableNavigationHandler) 
	  			fc.getApplication().getNavigationHandler();
	   
	  		nav.performNavigation("error");
	  	}		
    }*/
    
	public void reset() {
		this.setId(0);
		this.setName("");
		this.setPass("");
		this.setEmail("");
		this.setRole("3");
	}
	
	public List<User> getUserList() {
		userList = new ArrayList<User>();
		userList.addAll(getUserService().getUsers());
		return userList;
	}
	
	public IUserService getUserService() {
		return userService;
	}

	public void setUserService(IUserService userService) {
		this.userService = userService;
	}
	
	public void setUserList(List<User> userList) {
		this.userList = userList;
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
	
	public String getPass() {
		return pass;
	}
	
	public void setPass(String pass) {
		this.pass = pass;
	}
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getLanguage() {
		return language;
	}
	
	public void setLanguage(String language) {
		this.language = language;
	}

	public User getCurrent() {
		return current;
	}

	public void setCurrent(User current) {
		this.current = current;
	}		
	
	public String getRole() {
		return role;
	}
	
	public void setRole(String role) {
		this.role = role;
	}

	@Override
	public String toString() {
		StringBuffer strBuff = new StringBuffer();
		strBuff.append("id : ").append(getId());
		strBuff.append(", username : ").append(getName());
		strBuff.append(", pass : ").append(getPass());
		strBuff.append(", email : ").append(getEmail());
		strBuff.append(", language : ").append(getLanguage());
		strBuff.append(", role : ").append(getRole());
		return strBuff.toString();
	}
}