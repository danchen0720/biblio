package com.danchen.biblio.test;

import java.util.Date;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.zkoss.util.logging.Log;

import com.danchen.biblio.hibernate.bean.Article;
import com.danchen.biblio.hibernate.bean.User;
import com.danchen.biblio.hibernate.dao.ArticleDAO;
import com.danchen.biblio.misc.HibernateUtil;

public class TestDAO {
	private static Log log = Log.lookup(TestDAO.class);
	public static void main(String args[]){
		Session session = HibernateUtil.currentSession();
		Transaction tx = session.beginTransaction();
		
		ArticleDAO artDao = new ArticleDAO();
//		Article newArticle = new Article();
//		User user = new User(0);
//		newArticle.setUser(user);
//		newArticle.setTitle("test");
//		newArticle.setContent("nono");
//		newArticle.setParent(0);
//		newArticle.setState(0);
//		newArticle.setTime(new Date());
//		artDao.insert(newArticle);
		System.out.println(artDao.findOne(2).getChildren());
		tx.commit();
		HibernateUtil.closeSessionFactory();
	}
}
