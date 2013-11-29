package com.danchen.biblio.hibernate.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import com.danchen.biblio.hibernate.bean.Article;
import com.danchen.biblio.hibernate.bean.User;
import com.danchen.biblio.misc.HibernateUtil;

public class UserDAO {
	
	public User update(String username, String password, Integer id) {
		Session session = HibernateUtil.currentSession();
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
		Session session = HibernateUtil.currentSession();
		User bean = (User)session.get(User.class, id);
		if (bean != null) {
			session.delete(bean);
			return true;
		}
		return false;
	}
	
	public User save(User newUser) {
		Session session = HibernateUtil.currentSession();
		session.save(newUser);
		session.flush();
		return newUser;
	}
	
	@SuppressWarnings("unchecked")
	public List<User> findAll() {
		Session session = HibernateUtil.currentSession();
		return session.createQuery("from User").list(); 
	}
	public User findOneBy(String userName) {
		Session session = HibernateUtil.currentSession();
		Query query = session.createQuery("from User where username=:userName");
		return (User) query.setString("userName", userName).uniqueResult(); 
	}
}
