package com.wizco.interfaces;

import java.util.List;

import com.wizco.entity.User;


public interface UserInterface {
	
		public User addUser(User user);
		
		public List<User> list();
		
		public void deleteUser(int id);
		
		public User editUser(User user);
		
		public List<User> searchByName(User user);

}
