package com.danchen.biblio.service;

import com.danchen.biblio.hibernate.bean.User;
import com.danchen.biblio.hibernate.dao.UserDAO;

public class UserService {
	private UserDAO userDao;
	
	public UserService() {
		userDao = new UserDAO();
	}
	
	public User getUser(String userName, String passWord) {
		User user = userDao.findOneBy(userName);
		if (user != null) {
			if(passWord.equals(user.getPassword()))
				return user;
		}
		return null;
	}
}
