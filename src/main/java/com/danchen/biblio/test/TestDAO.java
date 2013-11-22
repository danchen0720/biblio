package com.danchen.biblio.test;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.zkoss.util.logging.Log;

import com.danchen.biblio.hibernate.bean.Article;
import com.danchen.biblio.hibernate.dao.ArticleDAO;
import com.danchen.biblio.misc.HibernateUtil;

public class TestDAO {
	private static Log log = Log.lookup(TestDAO.class);
	public static void main(String args[]){
		Session session = HibernateUtil.currentSession();
		Transaction tx = session.beginTransaction();
		
//		User userBean = new User();
//		userBean.setUsername("aabbccd");
//		userBean.setPassword("123456");
//		
//		session.save(userBean);
		
//		Article ArtBean = new Article();
//		ArtBean.setParent(null);
//		ArtBean.setState(0);
//		
//		Set<Tag> tags = ArtBean.getTags();
//		Tag tag = new Tag();
//		tag.setId(0);
//		tags.add(tag);
//		
//		ArtBean.setTags(tags);
//		ArtBean.setContent("123");
//		ArtBean.setTime(new Date());
//		ArtBean.setTitle("New test article Title");
//		ArtBean.setUser(new User(0));
//		
//		session.save(ArtBean);
		
		ArticleDAO artDao = new ArticleDAO();
		Article bean = (Article) session.get(Article.class, 5);
		System.out.println(bean);
		
		tx.commit();
		HibernateUtil.closeSessionFactory();
	}
}
