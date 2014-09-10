package user.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import user.dao.IUserDAO;
import user.model.User;


@Transactional(readOnly = true)
public class UserService implements IUserService {

	// UserDAO is injected...
	IUserDAO userDAO;
	
	//znaèi DI: user service je apstraktna klasa. u nju se injektira IuserDao koji je opet apstraktan i implementirana u UserDao
	//mogli smo npr: u novom fajlu: public class userTest implements IUserService i u nju injektirati iUserTest. to je DI!
	
	@Transactional(readOnly = false)
	public void addUser(User user) {
		getUserDAO().addUser(user);
	}
/*	
	@Transactional(readOnly = false)
	public boolean login(User user) {
		return getUserDAO().login(user);
	}
*/
	@Transactional(readOnly = false)
	public User login(String user, String pass) {
		return getUserDAO().login(user, pass);
	}
	
	@Transactional(readOnly = false)
	public void deleteUser(User user) {
		getUserDAO().deleteUser(user);
	}
	
	@Transactional(readOnly = false)
	public void updateUser(User user) {
		getUserDAO().updateUser(user);
	}

	public User existsUser(User user) {
		return getUserDAO().existsUser(user);
	}

	public User getUserById(int id) {
		return getUserDAO().getUserById(id);
	}

	public User getUserByName(String Name) {
		return getUserDAO().getUserByName(Name);
	}

	public List<User> getUsers() {	
		return getUserDAO().getUsers();
	}

	public IUserDAO getUserDAO() {
		return userDAO;
	}

	public void setUserDAO(IUserDAO userDAO) {
		this.userDAO = userDAO;
	}	
	
	public boolean hasAccounts(int id) {
		return getUserDAO().hasAccounts(id);
	}
	
}
