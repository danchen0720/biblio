package com.danchen.biblio.hibernate.dao;

import java.util.List;
import java.util.Set;

import org.hibernate.Session;

import com.danchen.biblio.hibernate.bean.Article;
import com.danchen.biblio.hibernate.bean.Tag;
import com.danchen.biblio.misc.HibernateUtil;

public class TagDAO {
	private Session session;
	
	public TagDAO() {
		this.session = HibernateUtil.currentSession();
	}
	
	public Tag update(String name,Integer id) {
		Tag bean = (Tag) session.get(Tag.class, id);
		if(bean != null){
			bean.setName(name);
			session.flush();
			return bean;
		}
		return null;
	}
	
	public boolean delete(int id) {
		Tag bean = (Tag)session.get(Tag.class, id);
		if(bean != null){
			session.delete(bean);
			return true;
		}
		return false;
	}
	
	public Tag insert(Tag newTag) {
		session.save(newTag);
		session.flush();
		return newTag;
	}
	
	@SuppressWarnings("unchecked")
	public List<Tag> findAll() {
		return session.createQuery("from Tag").list();
	}
}
