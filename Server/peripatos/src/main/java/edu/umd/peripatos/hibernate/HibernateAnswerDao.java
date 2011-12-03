package edu.umd.peripatos.hibernate;

import java.util.List;

import org.hibernate.Query;
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
		Query query = session.createQuery(
				"FROM Answer as answer " +
						"WHERE answer.assignment.id = " + assignment.getId() + " " +
						"AND answer.user.username = " + user.getUsername() + " " +
						"AND answer.question.id = " + question.getId());

		@SuppressWarnings("unchecked")
		List<Answer> results = query.list();

		if(results.size() < 1){
			return null;
		}else{
			return results.get(0);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Answer> getAnswerByAssignmentAndUserAndCourse(Assignment assignment, User user, Course course) {
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery(
				"FROM Answer as answer " +
						"WHERE answer.assignment.id = " + assignment.getId() + " " +
						"AND answer.user.username = " + user.getUsername() + " " +
						"AND answer.course.id = " + course.getId()
				);

		return query.list();
	}

}
