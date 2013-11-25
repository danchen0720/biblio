package com.danchen.biblio.hibernate.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
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
	
	public List<Article> getOneArticle(int id) {
		List<Article> arts = null;
	
		Tag tag = (Tag) session.createQuery("from Tag as t where t.id=:id").setInteger("id", id).uniqueResult();
		if(tag != null)
			arts = new ArrayList<Article>(tag.getArticles());
		return arts;
	}

	public List<Tag> findOne(int id) {
		Query query = session.createQuery("from Tag as t where t.id=:id");
		query.setInteger("id", id);
		return query.list();
	}
	@SuppressWarnings("unchecked")
	public List<Tag> findAll() { 
		return session.createQuery("from Tag as t").setMaxResults(10).list();
	}
}
