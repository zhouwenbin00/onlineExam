package com.ischoolbar.programmer.dao;

import java.math.BigInteger;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import com.ischoolbar.programmer.model.PageBean;
import com.ischoolbar.programmer.model.Paper;
import com.ischoolbar.programmer.model.Question;
import com.ischoolbar.programmer.model.Student;
import com.ischoolbar.programmer.util.HibernateUtil;
import com.ischoolbar.programmer.util.StringUtil;

/**
 * ����DAO��
 * @author Administrator
 *
 */
public class QuestionDao {

	/**
	 * ͨ������id��ȡ����ʵ��
	 * @param questionId
	 * @return
	 * @throws Exception
	 */
	public Question getQuestion(String questionId)throws Exception{
		Session session=HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		Question question=(Question) session.get(Question.class, Integer.parseInt(questionId));
		session.getTransaction().commit();
		return question;
	}
	
	/**
	 * �ж�ִ�е��Ծ���������Ŀ
	 * @param paperId
	 * @return
	 * @throws Exception
	 */
	public boolean existQuestionByPaperId(String paperId)throws Exception{
		Session session=HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		Query query=session.createQuery("from Question as q where q.paper.id=:paperId");
		query.setString("paperId", paperId);
		@SuppressWarnings("unchecked")
		List<Student> studentList=(List<Student>)query.list();
		session.getTransaction().commit();
		if(studentList.size()>0){
			return true;
		}else{
			return false;
		}
	}
	
	public  List<Question> getQuestionByPaperId(int paperId)throws Exception{
		Session session=HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		Query query=session.createQuery("from Question as q where q.paper.id=:paperId");
		query.setInteger("paperId", paperId);
		@SuppressWarnings("unchecked")
		List<Question> questionList=(List<Question>)query.list();
		session.getTransaction().commit();
		return questionList;
	}
	
	/**
	 * ��ȡ������Ŀ
	 * @param s_question
	 * @param pageBean
	 * @return
	 * @throws Exception
	 */
	public List<Question> getQuestions(Question s_question,PageBean pageBean)throws Exception{
		Session session=HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		StringBuffer hql=new StringBuffer("from Question");
		if(StringUtil.isNotEmpty(s_question.getSubject())){
			hql.append(" and subject like '%"+s_question.getSubject()+"%'");
		}
		Query query=session.createQuery(hql.toString().replaceFirst("and", "where"));
		
		if(pageBean!=null){
			query.setFirstResult(pageBean.getStart());
			query.setMaxResults(pageBean.getPageSize());
		}
		@SuppressWarnings("unchecked")
		List<Question> questionList=(List<Question>)query.list();
		session.getTransaction().commit();
		return questionList;
	}
	
	public List<Question> getQuestions(List<Paper> papers,PageBean pageBean)throws Exception{
		Session session=HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		StringBuffer hql=new StringBuffer("from Question");
		hql.append(" and ");
		for (Paper paper : papers) {
			hql.append("paperId = "+paper.getId()+" or ");
		}
		hql.delete(hql.length()-3, hql.length());
		Query query=session.createQuery(hql.toString().replaceFirst("and", "where"));
		
		if(pageBean!=null){
			query.setFirstResult(pageBean.getStart());
			query.setMaxResults(pageBean.getPageSize());
		}
		@SuppressWarnings("unchecked")
		List<Question> questionList=(List<Question>)query.list();
		session.getTransaction().commit();
		return questionList;
	}
	
	/**
	 * ��ѯ�����¼��
	 * @param s_question
	 * @return
	 * @throws Exception
	 */
	public int questionCount(Question s_question)throws Exception{
		Session session=HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		StringBuffer sql=new StringBuffer("select count(*) from t_question");
		if(StringUtil.isNotEmpty(s_question.getSubject())){
			sql.append(" and subject like '%"+s_question.getSubject()+"%'");
		}
		Query query=session.createSQLQuery(sql.toString().replaceFirst("and", "where"));
		int count=((BigInteger)query.uniqueResult()).intValue();
		session.getTransaction().commit();
		return count;
	}
	

	
	/**
	 * ��������ʵ��
	 * @param question
	 * @throws Exception
	 */
	public void saveQuestion(Question question)throws Exception{
		Session session=HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		session.merge(question);
		session.getTransaction().commit();
	}
	
	/**
	 * ɾ������
	 * @param question
	 * @throws Exception
	 */
	public void deleteQuestion(Question question)throws Exception{
		Session session=HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		session.delete(question);
		session.getTransaction().commit();
	}
}
