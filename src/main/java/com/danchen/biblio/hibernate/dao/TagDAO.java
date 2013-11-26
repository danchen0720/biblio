package com.danchen.biblio.hibernate.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import com.danchen.biblio.hibernate.bean.Article;
import com.danchen.biblio.hibernate.bean.Tag;
import com.danchen.biblio.misc.HibernateUtil;

public class TagDAO {

	public Tag update(String name,Integer id) {
		Session session = HibernateUtil.currentSession();
		Tag bean = (Tag) session.get(Tag.class, id);
		if(bean != null){
			bean.setName(name);
			session.flush();
			return bean;
		}
		return null;
	}
	
	public boolean delete(int id) {
		Session session = HibernateUtil.currentSession();
		Tag bean = (Tag)session.get(Tag.class, id);
		if(bean != null){
			session.delete(bean);
			return true;
		}
		return false;
	}
	
	public Tag insert(Tag newTag) {
		Session session = HibernateUtil.currentSession();
		session.save(newTag);
		session.flush();
		return newTag;
	}
	
	public Tag findOne(int id) {
		Session session = HibernateUtil.currentSession();
		Query query = session.createQuery("from Tag as t where t.id=:id");
		query.setInteger("id", id);
		return (Tag) query.uniqueResult();
	}
	@SuppressWarnings("unchecked")
	public List<Tag> findAll() {
		Session session = HibernateUtil.currentSession();
		return session.createQuery("from Tag as t").list();
	}
}
