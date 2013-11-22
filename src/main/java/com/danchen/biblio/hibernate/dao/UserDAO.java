package com.danchen.biblio.hibernate.dao;

import java.util.List;

import org.hibernate.Session;

import com.danchen.biblio.hibernate.bean.Article;
import com.danchen.biblio.hibernate.bean.User;
import com.danchen.biblio.misc.HibernateUtil;

public class UserDAO {
	private Session session;
	
	public UserDAO() {
		this.session = HibernateUtil.currentSession();
	}
	
	public User update(String username, String password, Integer id) {
		User bean = (User)session.get(User.class, id);
		if (bean != null) {
			bean.setUsername(username);
			bean.setPassword(password);
			session.flush();
			return bean;
		}
		return null;
	}
	
	public boolean delete(int id) {
		User bean = (User)session.get(User.class, id);
		if(bean != null){
			session.delete(bean);
			return true;
		}
		return false;
	}
	
	public User save(User newUser) {
		session.save(newUser);
		session.flush();
		return newUser;
	}
	
	@SuppressWarnings("unchecked")
	public List<User> findAll() {
		return session.createQuery("from User").list(); 
	}
}
