package user.managed.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import org.springframework.dao.DataAccessException;

import user.model.User;
import user.service.IUserService;


@ManagedBean(name="userController")
@RequestScoped
public class UserController implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private static final String SUCCESS = "success";
	private static final String ERROR   = "error";
    //private User current;
	
	//Spring User Service is injected...
	@ManagedProperty(value="#{UserService}")
	IUserService userService;
	
    @ManagedProperty("#{userMB}")
    public UserManagedBean user;
    
    public void setUser(UserManagedBean user) {
        this.user = user;
    }

    public UserManagedBean getUser() {
        return user;
    }
    
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
            FacesMessage msg = new FacesMessage("User added!", user.toString());  
            FacesContext.getCurrentInstance().addMessage("growlMessage", msg);
            
			return SUCCESS;
		} catch (DataAccessException e) {
			e.printStackTrace();
		} 	
		
		return ERROR;
	}

	public String registerUser() {
		try {
			User user = new User();
			user.setId(getId());
			user.setName(getName());
			user.setPass(getPass());
			user.setEmail(getEmail());
			user.setLanguage(getLanguage());
			user.setRole(getRole() == null ? "3" : getRole());
			
			//ako veæ postoji onda ga loginaj
			if (getUserService().getUserByName(user.getName()) != null)
			{
    			System.out.println("----------UserController.registerUser: User postoji, login: " + user.toString());
    			user2User(user);
				this.user.login();
    			System.out.println("----------UserController.registerUser: User postoji, login UMB: " + this.user.toString());
				return SUCCESS;
			}

			if(user.getEmail().equals(""))
			{
				System.out.println("----------UserController.registerUser: User email is empty: " + user.getEmail());
				FacesMessage msg = new FacesMessage("Please enter valid email!", "");  
            	FacesContext.getCurrentInstance().addMessage("growlMessage", msg);
				this.user = null;
				return "register";
			}
			
			getUserService().addUser(user);
            FacesMessage msg = new FacesMessage("User added!", user.toString());  
            FacesContext.getCurrentInstance().addMessage("growlMessage", msg);

            User u;
            u = getUserService().getUserByName(getName());
            user2User(u);

			if(u != null)
			{
				this.user.setCurrent(u);
				//return "index?faces-redirect=true";
			}
			else
			{
				this.user = null;
				return ERROR;
			}
			
			return SUCCESS;
		} catch (DataAccessException e) {
			e.printStackTrace();
		} 	
		
		return ERROR;
	}

	public void user2User(User u)
	{
    	this.user.setId(u.getId());
    	this.user.setName(u.getName());
    	this.user.setPass(u.getPass());
    	this.user.setEmail(u.getEmail());
    	this.user.setLanguage(u.getLanguage());
    	this.user.setRole(u.getRole());
    	
		System.out.println("----------UserController.user2User: UserManagedBean: " + this.user.toString());
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
			getUserService().updateUser(user);
            FacesMessage msg = new FacesMessage("User updated!", user.toString());  
            FacesContext.getCurrentInstance().addMessage("growlMessage", msg); 
			return SUCCESS;
		} catch (DataAccessException e) {
			e.printStackTrace();
		} 	
		
		return ERROR;
	}

	public void reset() {
		this.setId(0);
		this.setName("");
		this.setPass("");
		this.setEmail("");
		this.setLanguage("");
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
	
	public String getRole() {
		return role;
	}
	
	public void setRole(String role) {
		this.role = role;
	}
}