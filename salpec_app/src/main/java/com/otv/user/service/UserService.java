package com.otv.user.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.otv.model.User;
import com.otv.user.dao.IUserDAO;

@Transactional(readOnly = true)
public class UserService implements IUserService {

	// UserDAO is injected...
	IUserDAO userDAO;
	
	
	//znaèi DI: user service je apstraktna klasa. u nju se injektira IuserDao koji je opet apstraktan i implementirana u UserDao
	//mogli smo npr: u novom fajlu: public class userTest implements IUserService i u nju injektirati iUserTest. to je DI!
	
	@Transactional(readOnly = false)
	//@Override
	public void addUser(User user) {
		getUserDAO().addUser(user);
	}
	
	@Transactional(readOnly = false)
	//@Override
	public boolean login(User user) {
		return getUserDAO().login(user);
	}
	
	@Transactional(readOnly = false)
	//@Override
	public User login(String user, String pass) {
		return getUserDAO().login(user, pass);
	}
	
	@Transactional(readOnly = false)
	//@Override
	public void deleteUser(User user) {
		getUserDAO().deleteUser(user);
	}
	
	@Transactional(readOnly = false)
	//@Override
	public void updateUser(User user) {
		getUserDAO().updateUser(user);
	}

	//@Override
	public User getUserById(int id) {
		return getUserDAO().getUserById(id);
	}

	//@Override
	public List<User> getUsers() {	
		return getUserDAO().getUsers();
	}

	public IUserDAO getUserDAO() {
		return userDAO;
	}

	public void setUserDAO(IUserDAO userDAO) {
		this.userDAO = userDAO;
	}
}
