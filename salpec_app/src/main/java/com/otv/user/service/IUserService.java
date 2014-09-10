package com.otv.user.service;

import java.util.List;

import com.otv.model.User;

public interface IUserService {

	public void addUser(User user);

	public boolean login(User user);
	
	public User login(String user, String pass);

	public void updateUser(User user);

	public void deleteUser(User user);

	public User getUserById(int id);

	public List<User> getUsers();
}
