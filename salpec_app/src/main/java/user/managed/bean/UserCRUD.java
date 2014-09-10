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


@ManagedBean(name="userCRUD")
@RequestScoped
public class UserCRUD implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private static final String SUCCESS = "success";
	private static final String ERROR   = "error";
    //private User current;
	
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
            FacesMessage msg = new FacesMessage("User added!", user.toString());  
            FacesContext.getCurrentInstance().addMessage("growlMessage", msg); 
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
}