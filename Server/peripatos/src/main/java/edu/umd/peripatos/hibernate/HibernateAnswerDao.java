package edu.umd.peripatos.hibernate;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.transaction.annotation.Transactional;

import edu.umd.peripatos.Answer;
import edu.umd.peripatos.Assignment;
import edu.umd.peripatos.Course;
import edu.umd.peripatos.Question;
import edu.umd.peripatos.User;
import edu.umd.peripatos.dao.AnswerDao;

@Transactional
public class HibernateAnswerDao implements AnswerDao{
	
	private static Logger logger = Logger.getLogger(HibernateAnswerDao.class);
	
	private SessionFactory sessionFactory;

	public void setSessionFactory(SessionFactory sessionFactory){
		this.sessionFactory = sessionFactory;
	}

	@Override
	public void store(Answer answer) {
		Session session = sessionFactory.getCurrentSession();
		session.saveOrUpdate(answer);
	}

	@Override
	public void delete(Answer answer) {
		Session session = sessionFactory.getCurrentSession();
		session.delete(answer);
	}

	@Override
	public Answer getAnswerById(Long id) {
		Session session = sessionFactory.getCurrentSession();
		Answer answer = (Answer) session.get(Answer.class, id);
		return answer;
	}

	@Override
	public Answer getAnswerByAssignmentAndUserAndQuestion(Assignment assignment, User user, Question question) {
		Session session = sessionFactory.getCurrentSession();
		Criteria crit = session.createCriteria(Answer.class);
		
		@SuppressWarnings("unchecked")
		List<Answer> answers = crit.list();
		
		for(Answer a : answers){
			if(a.getAssignment().getId().equals(assignment.getId()) &&
					a.getUser().getUsername().equals(user.getUsername()) &&
					a.getQuestion().getId().equals(question.getId())){
				return a;
			}
		}
		
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Answer> getAnswerByAssignmentAndUserAndCourse(Assignment assignment, User user, Course course) {
		logger.info("Attempting to get answers for user using [Assignment] [User] [Course]");
		Session session = sessionFactory.getCurrentSession();

		Criteria crit = session.createCriteria(Answer.class);
		
		List<Answer> answers = crit.list();
		
		List<Answer> results = new ArrayList<Answer>();
		
		for(Answer a : answers){
			if(a.getAssignment().getId().equals(assignment.getId()) &&
					a.getUser().getUsername().equals(user.getUsername()) &&
					a.getCourse().getId().equals(course.getId())){
				results.add(a);
			}
		}
		
		return results;
	}

}
