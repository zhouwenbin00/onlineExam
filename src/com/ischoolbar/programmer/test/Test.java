package com.ischoolbar.programmer.test;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.ischoolbar.programmer.util.HibernateUtil;

public class Test {

	public static void main(String[] args) {
		SessionFactory sessionFactory=HibernateUtil.getSessionFactory();
		Session session=sessionFactory.openSession(); // ����һ��session
	    session.beginTransaction(); // ��������
	    
	   
	    
	    session.getTransaction().commit(); // �ύ����
	    session.close(); // �ر�session
	}
}
