package com.danchen.biblio.test;

import java.util.Date;
import java.util.Iterator;

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
//		User user = new User(1);
//		newArticle.setUser(user);
//		newArticle.setTitle("test");
//		newArticle.setContent("nono");
//		newArticle.setParent(3);
//		newArticle.setState(0);
//		newArticle.setTime(new Date());
//		artDao.insert(newArticle);
		
		//Test getChildren()
//		Iterator itr = artDao.findOne(3).getChildren().iterator();
//		while (itr.hasNext()) {
//			Article bean = (Article)itr.next();
//			System.out.println(bean.getId());
//		}
		
		tx.commit();
		HibernateUtil.closeSessionFactory();
	}
}
