package com.ischoolbar.programmer.dao;

import org.hibernate.Query;
import org.hibernate.Session;

import com.ischoolbar.programmer.model.Manager;
import com.ischoolbar.programmer.util.HibernateUtil;

/**
 * ����ԱDAO��
 * @author Administrator
 *
 */
public class ManagerDao {

	/**
	 * ����Ա��¼��֤
	 * @param student
	 * @return
	 * @throws Exception
	 */
	public Manager login(Manager manager)throws Exception{
		Session session=HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		Query query=session.createQuery("from Manager as m where m.userName=:userName and m.password=:password ");
		query.setString("userName", manager.getUserName());
		query.setString("password", manager.getPassword());
		Manager resultManager=(Manager)query.uniqueResult();
		session.getTransaction().commit();
		return resultManager;
	}
}
