package com.ischoolbar.programmer.action;


import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONObject;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.ServletRequestAware;

import com.ischoolbar.programmer.dao.PaperDao;
import com.ischoolbar.programmer.dao.QuestionDao;
import com.ischoolbar.programmer.model.PageBean;
import com.ischoolbar.programmer.model.Paper;
import com.ischoolbar.programmer.model.Question;
import com.ischoolbar.programmer.util.PageUtil;
import com.ischoolbar.programmer.util.PropertiesUtil;
import com.ischoolbar.programmer.util.ResponseUtil;
import com.ischoolbar.programmer.util.StringUtil;
import com.opensymphony.xwork2.ActionSupport;

public class QuestionAction extends ActionSupport implements ServletRequestAware{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private HttpServletRequest request;
	
	private QuestionDao questionDao=new QuestionDao();
	private PaperDao paperDao=new PaperDao();
	
	private List<Question> questionList;
	private List<Paper> paperList;
	private String mainPage;
	
	private String questionId;
	private Question question;
	private String title;
	
	private String page;
	private int total;
	private String pageCode;
	
	private Question s_question;
	private Paper s_paper;
	
	

	public List<Paper> getPaperList() {
		return paperList;
	}

	public void setPaperList(List<Paper> paperList) {
		this.paperList = paperList;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public List<Question> getQuestionList() {
		return questionList;
	}

	public void setQuestionList(List<Question> questionList) {
		this.questionList = questionList;
	}

	public String getMainPage() {
		return mainPage;
	}

	public void setMainPage(String mainPage) {
		this.mainPage = mainPage;
	}

	public String getPage() {
		return page;
	}

	public void setPage(String page) {
		this.page = page;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public String getPageCode() {
		return pageCode;
	}

	public void setPageCode(String pageCode) {
		this.pageCode = pageCode;
	}
	
	

	public Question getS_question() {
		return s_question;
	}

	public void setS_question(Question s_question) {
		this.s_question = s_question;
	}
	
	

	public String getQuestionId() {
		return questionId;
	}

	public void setQuestionId(String questionId) {
		this.questionId = questionId;
	}

	public Question getQuestion() {
		return question;
	}

	public void setQuestion(Question question) {
		this.question = question;
	}
	
	

	public Paper getS_paper() {
		return s_paper;
	}

	public void setS_paper(Paper s_paper) {
		this.s_paper = s_paper;
	}

	/**
	 * ��ѯ������Ϣ
	 * @return
	 * @throws Exception
	 */
	public String list()throws Exception{
		HttpSession session=request.getSession();
		if(StringUtil.isEmpty(page)){
			page="1";
		}
		if(s_paper!=null){
			session.setAttribute("s_paper", s_paper);
		}else{
			Object o=session.getAttribute("s_paper");
			if(o!=null){
				s_paper=(Paper)o;
			}else{
				s_paper=new Paper();
			}
		}
		if(s_question!=null){
			session.setAttribute("s_question", s_question);
		}else{
			Object o=session.getAttribute("s_question");
			if(o!=null){
				s_question=(Question)o;
			}else{
				s_question=new Question();
			}
		}
		PageBean pageBean=new PageBean(Integer.parseInt(page),Integer.parseInt(PropertiesUtil.getValue("pageSize")));
		PaperDao paperDao = new PaperDao();
		
		if (s_paper.getPaperName()==null||s_paper.getPaperName().equals("")) {
			questionList=questionDao.getQuestions(s_question,pageBean);
		}else {
			List<Paper> papers= paperDao.getPapers(s_paper);	
			questionList=questionDao.getQuestions(papers,pageBean);
		}
		
		total=questionDao.questionCount(s_question);
		pageCode=PageUtil.genPagation(request.getContextPath()+"/question!list",total, Integer.parseInt(page), Integer.parseInt(PropertiesUtil.getValue("pageSize")));
		mainPage="question/questionList.jsp";
		return SUCCESS;
	}
	
	/**
	 * ͨ��id��ȡ����
	 * @return
	 * @throws Exception
	 */
	public String getQuestionById()throws Exception{
		question=questionDao.getQuestion(questionId);
		mainPage="question/questionShow.jsp";
		return SUCCESS;
	}
	
	/**
	 * Ԥ�༭����
	 * @return
	 * @throws Exception
	 */
	public String preSave()throws Exception{
		paperList=paperDao.getPapers();
		if(StringUtil.isNotEmpty(questionId)){
			question=questionDao.getQuestion(questionId);
			title="�޸�������Ϣ";
		}else{
			title="���������Ϣ";
		}
		mainPage="question/questionSave.jsp";
		return SUCCESS;
	}
	
	/**
	 * ɾ������
	 * @return
	 * @throws Exception
	 */
	public String delete()throws Exception{
		question=questionDao.getQuestion(questionId);
		questionDao.deleteQuestion(question);
		JSONObject resultJson=new JSONObject();
		resultJson.put("success",true);
		ResponseUtil.write(resultJson,ServletActionContext.getResponse());
		return null;
	}
	
	/**
	 * ��������
	 * @return
	 * @throws Exception
	 */
	public String saveQuestion()throws Exception{
		if(StringUtil.isNotEmpty(questionId)){
			question.setId(Integer.parseInt(questionId));
		}
		question.setAnswer(question.getAnswer().toUpperCase());
		questionDao.saveQuestion(question);
		return "save";
	}

	public void setServletRequest(HttpServletRequest request) {
		this.request=request;
	}

}
