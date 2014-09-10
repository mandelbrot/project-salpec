package com.otv.managed.bean;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.faces.application.ConfigurableNavigationHandler;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ComponentSystemEvent;

import language.LanguageBean;

import org.springframework.dao.DataAccessException;

import com.otv.model.User;
import com.otv.user.service.IUserService;

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
	
	List<User> userList;
	
	private int id;
	private String name;
	private String pass;
	private String email;
	private String language;

	public String addUser() {
		try {
			User user = new User();
			user.setId(getId());
			user.setName(getName());
			user.setPass(getPass());
			user.setEmail(getEmail());
			user.setLanguage(getLanguage());
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
			if(getCurrent() != null)
			{
				this.setId(getCurrent().getId());
				this.setName(getCurrent().getName());
				this.setPass(getCurrent().getPass());
				this.setEmail(getCurrent().getEmail());
				this.setLanguage(getCurrent().getLanguage());
				//Locale locale = new Locale(getCurrent().getLanguage());
				//FacesContext.getCurrentInstance().getViewRoot().setLocale(locale);
				
				//language_menu.triggerChange();
				return SUCCESS;
			}
			else
			{
				setCurrent(null);
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Unknown login, try again"));
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
}