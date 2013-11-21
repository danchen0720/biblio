package com.danchen.biblio.test;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.danchen.biblio.misc.HibernateUtil;

public class TestDAO {
	
	public static void main(String args[]){
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction tx = session.beginTransaction();
		Query query = session.createQuery("from Tag");
		System.out.println(query.list().size());
		tx.commit();
		HibernateUtil.closeSessionFactory();
	}
}
