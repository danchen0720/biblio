package com.danchen.biblio.hibernate.dao;

import java.util.Date;
import java.util.List;
import java.util.Set;

import org.hibernate.Query;
import org.hibernate.Session;

import com.danchen.biblio.hibernate.bean.Article;
import com.danchen.biblio.hibernate.bean.Tag;
import com.danchen.biblio.misc.HibernateUtil;

public class ArticleDAO {
	
	public Article update(Date time, Integer state, String content,
			String title, Set<Tag> tags, Integer id) {
		Session session = HibernateUtil.currentSession();
		Article bean = (Article)session.get(Article.class, id);
		if (bean != null) {
			bean.setTime(time);
			bean.setState(state);
			bean.setContent(content);
			bean.setTitle(title);
			bean.setTags(tags);
			session.flush();
			
			return bean;
		}
		return null;
	}

	public boolean delete(int id) {
		Session session = HibernateUtil.currentSession();
		Article bean = (Article)session.get(Article.class, id);
		if(bean != null){
			session.delete(bean);
			return true;
		}
		return false;
	}

	public Article insert(Article newArticle) {
		Session session = HibernateUtil.currentSession();
		session.save(newArticle);
		session.flush();
		return newArticle;
	}

	@SuppressWarnings("unchecked")
	public List<Article> findAll() {
		Session session = HibernateUtil.currentSession();
		return session.createQuery("from Article as art where art.id <> 0").list();
	}
	
	public List<Article> getTree() {
		Session session = HibernateUtil.currentSession();
		return session.createQuery("from Article").list();
	}
	
	public List<Article> getByParent(int parentId) {
		Session session = HibernateUtil.currentSession();
		Query query = session.createQuery("from Article as art where art.parent=:parentId");
		query.setInteger("parentId", parentId);
		List<Article> result = query.list();
		return result;
	}
	
	public Article findOne(int articleId){
		Session session = HibernateUtil.currentSession();
		Query query = session.createQuery("from Article as art where art.id=:articleId");
		query.setInteger("articleId", articleId);
		Article result = (Article) query.uniqueResult();
		return result;
	}
	
	public List<Article> getByUser(int userId){
		Session session = HibernateUtil.currentSession();
		Query query = session.createQuery("from Article as art where art.User=:userId");
		query.setInteger("userId", userId);
		List<Article> result = query.list();
		return result;
	}
}
