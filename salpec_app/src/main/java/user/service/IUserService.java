package user.service;

import java.util.List;

import user.model.User;

public interface IUserService {
	public void addUser(User user);
	//public boolean login(User user);
	public User login(String user, String pass);
	public void updateUser(User user);
	public void deleteUser(User user);
	public User existsUser(User user);
	public User getUserById(int id);
	public User getUserByName(String Name);
	public List<User> getUsers();
	public boolean hasAccounts(int id);
}
