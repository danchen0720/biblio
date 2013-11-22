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
	private Session session;
	
	public ArticleDAO() {
		this.session = HibernateUtil.currentSession();
	}
	
	public Article update(Date time, Integer state, String content,
			String title, Set<Tag> tags, Integer id) {
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
		Article bean = (Article)session.get(Article.class, id);
		if(bean != null){
			session.delete(bean);
			return true;
		}
		return false;
	}

	public Article insert(Article newArticle) {
		session.save(newArticle);
		session.flush();
		return newArticle;
	}

	@SuppressWarnings("unchecked")
	public List<Article> findAll() {
		return session.createQuery("from Article").list();
	}
	
	public List<Article> getByParent(int parentId) {
		Query query = session.createQuery("from Article as art where art.parent=:parentId");
		query.setInteger("parentId", parentId);
		List<Article> result = query.list();
		return result;
	}
	
	public List<Article> getByUser(int userId){
		Query query = session.createQuery("from Article as art where art.User=:userId");
		query.setInteger("userId", userId);
		List<Article> result = query.list();
		return result;
	}
}
