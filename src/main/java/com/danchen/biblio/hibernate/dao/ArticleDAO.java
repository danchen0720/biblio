package com.danchen.biblio.hibernate.dao;

import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.hibernate.Query;
import org.hibernate.Session;

import com.danchen.biblio.hibernate.bean.Article;
import com.danchen.biblio.hibernate.bean.Tag;
import com.danchen.biblio.hibernate.bean.User;
import com.danchen.biblio.misc.HibernateUtil;

public class ArticleDAO {
	public int update(int state, List<Article> arts) {
		Session session = HibernateUtil.currentSession();
		String hql="update Article art set art.state = :state where art IN (:arts)";
		Query query = session.createQuery(hql);
		query.setParameterList("arts", arts);
		query.setInteger("state", state);
		int count = query.executeUpdate();
		session.flush();
		return count;
	}
	public Article update(int state, int id) {
		Session session = HibernateUtil.currentSession();
		Article bean = (Article)session.get(Article.class, id);
		if (bean != null) {
			bean.setState(state);
			session.flush();
			return bean;
		}
		return null;
	}
	public Article update(Date time, String content, Tag tag, int id) {
		Session session = HibernateUtil.currentSession();
		Article bean = (Article)session.get(Article.class, id);
		if (bean != null) {
			bean.setTime(time);
			bean.setContent(content);
			Set tempSet = new HashSet();
			tempSet.add(tag);
			bean.setTags(tempSet);
			session.flush();
			return bean;
		}
		return null;
	}
	public Article update(Date time, String content, String title, Tag tag, int id) {
		Session session = HibernateUtil.currentSession();
		Article bean = (Article)session.get(Article.class, id);
		if (bean != null) {
			bean.setTime(time);
			bean.setContent(content);
			bean.setTitle(title);
			Set tempSet = new HashSet();
			tempSet.add(tag);
			bean.setTags(tempSet);
			session.flush();
			return bean;
		}
		return null;
	}
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
	
	public List<Article> findPosts() {
		Session session = HibernateUtil.currentSession();
		return session.createQuery("from Article as art where art.parent <> 0 ORDER BY art.time DESC").list();
	}
	public List<Article> findPosts(int quantity) {
		Session session = HibernateUtil.currentSession();
		return session.createQuery("from Article as art where art.parent <> 0 and state = 1 ORDER BY art.time DESC").setMaxResults(quantity).list();
	}
	
	public List<Article> findMainArts(){
		Session session = HibernateUtil.currentSession();
		return session.createQuery("from Article as art where art.parent=0 and state = 1 ORDER BY art.time DESC").list();
	}
	public List<Article> findMainArts(int quantity){
		Session session = HibernateUtil.currentSession();
		return session.createQuery("from Article as art where art.parent=0 and state = 1 ORDER BY art.time DESC").setMaxResults(quantity).list();
	}
	
	@SuppressWarnings("unchecked")
	public List<Article> findAll() {
		Session session = HibernateUtil.currentSession();
		return session.createQuery("from Article as art where art.id <> 0").list();
	}
	@SuppressWarnings("unchecked")
	public List<Article> findAll(int quantity) {
		Session session = HibernateUtil.currentSession();
		return session.createQuery("from Article as art where art.id <> 0 ORDER BY art.time DESC").setMaxResults(quantity).list();
	}
	
	public Article findUnprocessingOne(int articleId){
		Session session = HibernateUtil.currentSession();
		Query query = session.createQuery("from Article as art where art.id=:articleId and state = 0");
		query.setInteger("articleId", articleId);
		Article result = (Article) query.uniqueResult();
		return result;
	}
	public Article findOne(int articleId){
		Session session = HibernateUtil.currentSession();
		Query query = session.createQuery("from Article as art where art.id=:articleId and state = 1");
		query.setInteger("articleId", articleId);
		Article result = (Article) query.uniqueResult();
		return result;
	}
	public Article findRoot(int articleId){
		Session session = HibernateUtil.currentSession();
		Query query = session.createQuery("from Article as art where art.id=:articleId");
		query.setInteger("articleId", articleId);
		return (Article) query.uniqueResult();
	}
	
	public List<Article> getArtFamily(int articleId) {
		Session session = HibernateUtil.currentSession();
		Query query = session.createQuery("from Article as art where art.id = :articleId or art.topic = :articleId and state = 1 ORDER BY art.time ASC");
		query.setInteger("articleId", articleId);
		List<Article> result = query.list();
		return result;
	}
	
	public List<Article> getArtByUser(User user) {
		Session session = HibernateUtil.currentSession();
		Query query = session.createQuery("from Article as art where art.user=:user and art.id <> 0 and state = 1 ORDER BY art.time DESC");
		query.setEntity("user", user);
		return query.list();
	}
	public List<Article> getArtByUser(User user,int quantity) {
		Session session = HibernateUtil.currentSession();
		Query query = session.createQuery("from Article as art where art.user=:user and state = 1 ORDER BY art.time DESC");
		query.setEntity("user", user);
		return query.setMaxResults(quantity).list();
	}

	public Article findNewPost(int topicId) {
		Session session = HibernateUtil.currentSession();
		String hql = "from Article as art where art.topic = :topicId ORDER BY art.time desc";
		return (Article) session.createQuery(hql).setInteger("topicId", topicId).setMaxResults(1).uniqueResult();
	}
	public List<Article> getUnProcessingByUser(User user) {
		Session session = HibernateUtil.currentSession();
		Query query = session.createQuery("from Article as art where art.user=:user and state = 0 ORDER BY art.time DESC");
		query.setEntity("user", user);
		return query.list();
	}
	public Article findUnprocessArt(int articleId) {
		Session session = HibernateUtil.currentSession();
		Query query = session.createQuery("from Article as art where art.id=:articleId and state = 0");
		query.setInteger("articleId", articleId);
		return (Article) query.uniqueResult();
	}
	public void drop(Article art) {
		Session session = HibernateUtil.currentSession();
		session.delete(art);
		session.flush();
	}
}
