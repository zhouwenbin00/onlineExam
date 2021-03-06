package com.ischoolbar.programmer.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import com.ischoolbar.programmer.model.PageBean;
import com.ischoolbar.programmer.model.Paper;
import com.ischoolbar.programmer.model.Question;
import com.ischoolbar.programmer.util.HibernateUtil;
import com.ischoolbar.programmer.util.StringUtil;

/**
 * 试卷DAO类
 * @author Administrator
 *
 */
public class PaperDao {

	/**
	 * 获取所有试卷
	 * @return
	 * @throws Exception
	 */
	public List<Paper> getPapers()throws Exception{
		Session session=HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		Query query=session.createQuery("from Paper");
		@SuppressWarnings("unchecked")
		List<Paper> paperList=(List<Paper>)query.list();
		session.getTransaction().commit();
		return paperList;
	}
	
	public List<Paper> getPapers(Paper s_papre)throws Exception{
		Session session=HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		StringBuffer hql=new StringBuffer("from Paper");
		if(StringUtil.isNotEmpty(s_papre.getPaperName())){
			hql.append(" and paperName like '%"+s_papre.getPaperName()+"%'");
		}
		Query query=session.createQuery(hql.toString().replaceFirst("and", "where"));
		
		@SuppressWarnings("unchecked")
		List<Paper> questionList=(List<Paper>)query.list();
		session.getTransaction().commit();
		return questionList;
	}
	
	/**
	 * 获取指定试卷
	 * @param paperId
	 * @return
	 * @throws Exception
	 */
	public Paper getPaper(String paperId)throws Exception{
		Session session=HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		Paper paper=(Paper)session.get(Paper.class, Integer.parseInt(paperId));
		session.getTransaction().commit();
		return paper;
	}
	
	/**
	 * 保存试卷实体
	 * @param paper
	 * @throws Exception
	 */
	public void savePaper(Paper paper)throws Exception{
		Session session=HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		session.merge(paper);
		session.getTransaction().commit();
	}
	
	/**
	 * 删除试卷
	 * @param paper
	 * @throws Exception
	 */
	public void paperDelete(Paper paper)throws Exception{
		Session session=HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		session.delete(paper);
		session.getTransaction().commit();
	}
}
